package com.vortice.web.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vortice.core.exception.AplicacaoException;
import com.vortice.core.util.DateConverter;
import com.vortice.core.util.DateTimeConverter;
import com.vortice.core.util.DateUtil;
import com.vortice.core.util.FloatConverter;
import com.vortice.core.util.StringConverter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class BaseAction extends Action {
     public BaseAction() {
     }

   public ActionForward tratarExcecao(Exception e,
                                      ActionMapping pMapping,
                                      HttpServletRequest pRequest,
                                      HttpServletResponse pResponse,
                                      ActionForm form) throws IOException,ServletException{
        
   		ActionMessages erros = new ActionMessages();
        if (e instanceof AplicacaoException){
            erros.add("aplicacao", new ActionMessage(((AplicacaoException)e).getMessage()));
            saveErrors(pRequest, erros);
            try{
                ((BaseActionForm)form).carregarComponentes(pMapping, pRequest);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
        }else{
             return gerarTelaDeErro(e,pMapping,pRequest,pResponse);
        }

        return new ActionForward( pMapping.getInput());
  }

  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException, ServletException
  {
    try{
        Locale locale = getLocale();
        DateUtil.getInstance().setLocale(locale);
        
        ConvertUtils.register(new StringConverter(locale), String.class);
        ConvertUtils.register(new FloatConverter(locale), Float.class);
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
        ConvertUtils.register(new DateTimeConverter(), Timestamp.class);
                
        BaseActionForm baseForm = (BaseActionForm)form;
        if (baseForm != null){
            if(baseForm.getAcao()!=null && baseForm.getAcao().equals("L")){
                baseForm.limpar();
                try{
                    baseForm.carregarComponentes(mapping, request);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return mapping.getInputForward();
            }
            
            if (request.getParameter("pagerOffset")!=null){
                request.setAttribute("pagerOffset", request.getParameter("pagerOffset"));
                baseForm.setAcao("PAGINAR");
            }
            
        }
        return executar(mapping, form, request, response);
        
    }catch(Exception e){
        return tratarExcecao(e,mapping,(HttpServletRequest)request,(HttpServletResponse)response,form);
    }
  }

  protected abstract ActionForward executar(ActionMapping mapping,
                          ActionForm form,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception;

  public ActionForward gerarTelaDeErro(Throwable e,
                              ActionMapping pMapping,
                              HttpServletRequest pRequest,
                              HttpServletResponse pResponse) throws IOException,ServletException{
         e.printStackTrace();  
  		 ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
	     PrintStream printStream=new PrintStream(outputStream);
	     e.printStackTrace(printStream);
	     String stackTrace = outputStream.toString();
	     pRequest.setAttribute("STACK_TRACE",stackTrace);
	     
	     pRequest.setAttribute("MESSAGE",e.getMessage());
	     
	     pRequest.setAttribute("CLASS_NAME",e.getClass());
	     
         return pMapping.findForward("ERROR_PAGE");
	     
  }
  
  protected void salvarMensagem(HttpServletRequest request,String mensagem){
      ActionMessages erros = new ActionMessages();
      erros.add("aplicacao", new ActionMessage(mensagem));
      saveErrors(request, erros);
  }
  
  protected void salvarMensagem(HttpServletRequest request,String mensagem,String[] parametros){
      ActionMessages erros = new ActionMessages();
      erros.add("aplicacao", new ActionMessage(mensagem,parametros));
      saveErrors(request, erros);
  }
  
  protected ActionForward exibirMensagem(HttpServletRequest request,
                                         String mensagem,
                                         ActionMapping mapping,
                                         BaseActionForm form){
      ActionMessages erros = new ActionMessages();
      erros.add("aplicacao", new ActionMessage(mensagem));
      saveErrors(request, erros);
      try{
          form.carregarComponentes(mapping, request);
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return mapping.getInputForward();
  }
  
  protected ActionForward exibirMensagem(HttpServletRequest request,
                                         String mensagem,
                                         String[] parametros,
                                         ActionMapping mapping,
                                         BaseActionForm form){
      ActionMessages erros = new ActionMessages();
      erros.add("aplicacao", new ActionMessage(mensagem,parametros));
      saveErrors(request, erros);
      try{
          form.carregarComponentes(mapping, request);
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return mapping.getInputForward();
  }
  
  /**
   * Seta no request um c�digo javscript para executar um alert no cliente com a mensagem passada.
   * Deve ser colocado na p�gina (jsp)   <c:out escapeXml="false" value="${msg}" />
   * @param request
   * @param msg 
   */
  protected void registrarMensagemSucesso(HttpServletRequest request,String msg){
      StringBuffer script = new StringBuffer();
      script.append("<script> \n");
      script.append(" setTimeout(\"alert('"+msg+"')\",500); \n");
      script.append("</script> ");
      
      request.setAttribute("msg", script.toString());
  }
  
  /**
   * Seta no request um span HTML com a mensagem passada.
   * Deve ser colocado na p�gina (jsp)   <c:out escapeXml="false" value="${msg}" />
   * @param request
   * @param msg 
   */
  protected void registrarMensagemSucessoHTML(HttpServletRequest request,String msg){
      StringBuffer script = new StringBuffer();
      script.append("<span>");
      script.append(msg);
      script.append("</span> ");
      request.setAttribute("msg", script.toString());
  }
  
  
  protected boolean isCampoVazio(String valor){
      return (valor==null || valor.trim().length()==0);
  }
  
  protected boolean isCampoVazio(Number valor){
      return (valor==null || valor.doubleValue()==0);
  }
  
  /**
   * Retorna o locale configurado no web.xml. (LOCALE_LANGUAGE & LOCALE_COUNTRY)
   * @return java.util.Locale da Aplica��o
   */
  protected Locale getLocale(){
      String linguagem = getServlet().getServletConfig().getServletContext().getInitParameter("LOCALE_LANGUAGE");
      String pais = getServlet().getServletConfig().getServletContext().getInitParameter("LOCALE_COUNTRY");
      return new Locale(linguagem,pais); 
      
  }
  
  /**
   * Seta no request um bloco de javascript para ser executado no cliente.
   * Deve ser colocado na p�gina (jsp)   <c:out escapeXml="false" value="${script}" />
   * @param request
   * @param script (C�digo javascript SEM a tag '<SCRIPT>'
   */
  protected void registrarScript(HttpServletRequest request,String script){
      StringBuffer scriptBuff = new StringBuffer();
      scriptBuff.append("<script> \n");
      scriptBuff.append(script);
      scriptBuff.append("</script> ");
      
      request.setAttribute("script", scriptBuff.toString());
  }
  
  /**
   * Seta no request um c�digo javscript para executar um alert no cliente com a chave da mensagem do Resource passada.
   * Deve ser colocado na p�gina (jsp)   <c:out escapeXml="false" value="${msg}" />
   * @param request
   * @param msg 
   */
  protected void registrarMensagemResourceHTML(HttpServletRequest request,String msgKey){
      MessageResources resource = MessageResources.getMessageResources("ApplicationResources");
      String msg = resource.getMessage(msgKey);
      
      StringBuffer script = new StringBuffer();
      script.append("<span>");
      script.append(msg);
      script.append("</span> ");
      
      request.setAttribute("msg", script.toString());
  }
  
  protected WebApplicationContext getWebApplicationContext(){
	  ServletContext servletContext = this.servlet.getServletContext();
	  WebApplicationContext webAppContext = 
		WebApplicationContextUtils.getWebApplicationContext(servletContext);
	  return webAppContext;
  }

}

package com.vortice.web.view;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseActionForm extends ActionForm {
  private ActionErrors erros = new ActionErrors();
  public static final String NOME_ERROS = "erros";
  
  private String acao;
  
    public ActionErrors getErros() {
        return erros;
    }

    protected void setErros(ActionErrors newErros) {
        erros = newErros;
    }

    public void addErro(String newStringKey){
        erros.add(NOME_ERROS, new ActionMessage(newStringKey));
    }

    protected void addErro(String newStringKey, Object parametro){
        erros.add(NOME_ERROS, new ActionMessage(newStringKey,parametro));
    }

    public void addErro(String newStringKey, Object[] parametros){
        erros.add(NOME_ERROS, new ActionMessage(newStringKey,parametros));
    }

    protected void addErro(String newProperty, String newStringKey){
        erros.add(newProperty, new ActionMessage(newStringKey));
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){ 
    	ServletContext servletContext = this.servlet.getServletContext();
    	WebApplicationContext webAppContext = 
    		WebApplicationContextUtils.getWebApplicationContext(servletContext);
    	
    	if (getAcao()!=null && 
                (getAcao().equals("I") || getAcao().equals("U"))){
            if (getErros()!=null && !getErros().isEmpty()){
                try{
                    carregarComponentes(mapping, request, webAppContext);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            }    
            return getErros();
        }
                
        return new ActionErrors();
        
    }

    protected boolean ehNulo(Object pObj){
        return (pObj == null ? true : false);
    }

    protected boolean ehNumero(String pObj){
        char[] letras = pObj.toCharArray();
        boolean retorno = true;
        boolean primeiro = false;

        for (int i = 0 ; i < letras.length && retorno; ++i)  {

            if(!primeiro && letras[i]=='.'){
                primeiro = true;
            }
            else{
                retorno &= Character.isDigit(letras[i]);
            }
        }

        return !retorno;
    }

    protected void validarNumerico(String pObj, String pParametro){
        if(ehNumeroInteiro(pObj))
            addErro(MensagensErroIf.NUMERICO, new String[]{pParametro});
    }

    protected boolean ehNumeroInteiro(String pObj){
        char[] letras = pObj.toCharArray();
        boolean retorno = true;

        for (int i = 0 ; i < letras.length && retorno; ++i)  {
            retorno &= Character.isDigit(letras[i]);
        }

        return !retorno;
    }

    protected void validarNumeroInteiro(String pObj, String pParametro){
        if(pObj!=null && ehNumeroInteiro(pObj))
            addErro(MensagensErroIf.NUMERO_INTEIRO, new String[]{pParametro});
    }

    protected boolean ehNumeroInteiroPositivo(String pObj){
        char[] letras = pObj.toCharArray();
        boolean retorno = true;

        for (int i = 0 ; i < letras.length && retorno; ++i)  {
            retorno &= Character.isDigit(letras[i]);
        }

        return !retorno;
    }

    protected void validarNumeroInteiroPositivo(String pObj, String pParametro){
        if(pObj!=null && (ehNumeroInteiroPositivo(pObj)))
            addErro(MensagensErroIf.NUMERO_INTEIRO, new String[]{pParametro});
    }

    /**
     * Verifica somente se � numero flutuante 
     * @param pValor
     * @param pNome
     * @return
     */
    protected boolean validarNumeroFloat(String pValor, String pNome){
        int numErros = erros.size();
        if(pValor!=null && !isNumeroFloat(pValor))
            addErro(MensagensErroIf.NUMERO_FLOAT, new String[]{pNome});
        return (numErros==erros.size());
    }
    
    /**
     * Verifica se nulo ou se � numero flutuante 
     * @param pValor
     * @param pNome
     * @return
     */
    protected boolean validarVazioNumeroFloat(String pValor, String pNome){
        int numErros = erros.size();
        if(pValor == null || !isNumeroFloat(pValor))
            addErro(MensagensErroIf.NUMERO_FLOAT, new String[]{pNome});
        return (numErros==erros.size());
    }

    protected boolean validarData(String pValor, String pNomeCampo){
        int numErros = erros.size();
        if(pValor!=null && !isData(pValor))
            addErro(MensagensErroIf.DATA_INVALIDA, new String[]{pNomeCampo});
        return (numErros==erros.size());

    }

    protected boolean validarLetraObrigatorio(String pValor, String pNomeCampo){
        char[] letras = pValor.toCharArray();
        boolean temLetra = false;
        for (int i = 0 ; i < letras.length && !temLetra; ++i)  {
            temLetra |= Character.isLetter(letras[i]);
        }
        if(!temLetra){
            addErro(MensagensErroIf.LETRA_OBRIGATORIA, new String[]{pNomeCampo});
            return false;
        }
        return true;
    }

    /** Nao permite datas maiores que a atual */
    protected boolean validarDataFutura(String pValor, String pNomeCampo){
      try{
        int numErros = erros.size();
        if (pValor != null && !isData(pValor)) {
          addErro(MensagensErroIf.DATA_INVALIDA, new String[] {pNomeCampo});
          return false;
        }
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date data = (formatador.parse(pValor));
        java.util.Date dataAtual = new Date();

        if (data.compareTo(dataAtual) > 0) { //data maior que a atual
          addErro(MensagensErroIf.DATA_FUTURA_INVALIDA, new String[] {pNomeCampo});
        }

        return (numErros == erros.size());
      }catch(ParseException e){
        addErro(MensagensErroIf.DATA_INVALIDA, new String[] {pNomeCampo});
        return false;
      }
    }

    protected boolean validarData(String pValor, String pNomeCampo, String pFormatoData){
        int numErros = erros.size();
        if(pValor!=null && !isData(pValor,pFormatoData))
            addErro(MensagensErroIf.DATA_INVALIDA, new String[]{pNomeCampo});
        return (numErros==erros.size());
    }

    /**
     * Verifica se a string � nulo ou vazia. Retorna false se isso ocorrer.    
     * 
     * @param pObj
     * @param pParametro
     * @return boolean
     */
    protected boolean validarVazio(Object pObj, String pParametro){
        int numErros = erros.size();
        if(ehNulo(pObj) || pObj.toString().trim().length()==0)
            addErro(MensagensErroIf.VAZIO, new String[]{pParametro});
        return (numErros==erros.size());
    }
    
    /**
     * Vrifica se vazio ou � igual a zero
     * @param pObj
     * @param pParametro
     * @return
     */
    protected boolean validarVazioNumerico(Object pObj, String pParametro){
        int numErros = erros.size();
        if(ehNulo(pObj) || ((Number)pObj).intValue()==0)
            addErro(MensagensErroIf.VAZIO, new String[]{pParametro});
        return (numErros==erros.size());
    }
    
    /**
     * Verifica se a data est� vazia ou est� inv�lida
     * @param pObj
     * @param pParametro
     * @return
     */
    protected boolean validarVazioData(Object pObj, String pParametro){
        int numErros = erros.size();
        if(pObj == null || !isData((String)pObj))
            addErro(MensagensErroIf.DATA_INVALIDA, new String[]{pParametro});
        return (numErros==erros.size());
    }


    /**
     *  � importante retornar boolean para que a classe form filha possa
     *  saber se houve algum erro, para manipular alguma acao entre outras coisas.
     *
     **/

    protected boolean validarVazio(Object [] pObjs, String[] pParametros){
        int numErros = erros.size();
        for(int i = 0; i < pObjs.length; ++i){
            validarVazio(pObjs[i], pParametros[i]);
        }
        return (numErros==erros.size());
    }

    protected boolean validarData(String[] pValores, String[] pNomesCampo){
        int numErros = erros.size();
        for(int i = 0; i < pValores.length; ++i){
            validarData(pValores[i], pNomesCampo[i]);
        }
        return (numErros==erros.size());
    }

    protected boolean validarData(String[] pValores, String[] pNomesCampo, String formatoData){
        int numErros = erros.size();
        for(int i = 0; i < pValores.length; ++i){
            validarData(pValores[i], pNomesCampo[i],formatoData);
        }
        return (numErros==erros.size());
    }
    
    protected boolean validarDataHora(String pValor, String pNomeCampo){
        int numErros = erros.size();
        
        if (pValor!=null && !isData(pValor,"dd/MM/yyyy HH:mm")){
                addErro(MensagensErroIf.CAMPO_INVALIDO, new String[]{pNomeCampo});
        }
        
        return (numErros==erros.size());
    }

    protected void validarString(String pObj, boolean pPodeNulo, int pMax, Object[] parametros){
        if(!pPodeNulo && (ehNulo(pObj) || pObj.length() ==0))
            addErro(MensagensErroIf.VAZIO, parametros[0]);

        if(pObj != null && pMax != -1)
            if(pObj.length() > pMax)
                addErro(MensagensErroIf.TAMANHO_MAX, parametros);
    }

    @SuppressWarnings("unused")
	protected boolean isData(String pData){
        String formatoDt = "dd/MM/yyyy";
        try {
           SimpleDateFormat sdf = new SimpleDateFormat(formatoDt);
           sdf.setLenient(false);
           Date dt2 = sdf.parse(pData);
           if(pData.length() == 10)
            return true;
           else
            return false;
       }catch (ParseException e) {
             return false;
       }catch (IllegalArgumentException e) {
             return false;
       }

    }

    @SuppressWarnings("unused")
	protected boolean isData(String pData,String pFormato){
        String formatoDt = pFormato;
        try {
           SimpleDateFormat sdf = new SimpleDateFormat(formatoDt);
           sdf.setLenient(false);
           Date dt2 = sdf.parse(pData);
           return true;
       }catch (ParseException e) {
           return false;
       }catch (IllegalArgumentException e) {
           return false;
       }
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String newAcao) {
        acao = newAcao;
    }

    private boolean isNumeroFloat(String pValor){
         try{
            parseDouble(pValor,2);
            return true;
         }catch (Exception ex){
            return false;
         }

     }

    public void limpar(){
        try{
            BeanUtils.copyProperties(this, this.getClass().newInstance());
        }
        catch(IllegalAccessException e){
          e.printStackTrace();
        }
        catch(InvocationTargetException e){
          e.printStackTrace();
        }
        catch(InstantiationException e){
          e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimir(Object o){
            try{
                System.out.println("IN�CIO - IMPRIMINDO PROPRIEDADES DE ["+o.getClass().getName()+"]:");
                
                Map map = BeanUtils.describe(o);
                Set keys = map.keySet();
                List keyList = new ArrayList(keys);
                Collections.sort(keyList);
                Iterator itKeys = keyList.iterator();
                while(itKeys.hasNext()){
                    Object nome = itKeys.next();
                    System.out.println(nome+"="+map.get(nome));
                }
                System.out.println("FIM - IMPRIMINDO PROPRIEDADES DE ["+o.getClass().getName()+"]:");
            }catch (Exception ex)  {
               ex.printStackTrace();
          }
   }

   public double parseDouble(String text, int decimalPrecision)
   {
       String in = "";
       int decimal = -1;
       int milhar = -1;
       boolean negativo = false;
       text = text.trim();
       for(int i = 1; i <= text.length(); i++)
       {
           char chr = text.charAt(text.length() - i);
           if(negativo)
               throw new NumberFormatException(text);
           if(chr == '-')
           {
               in = '-' + in;
               negativo = true;
           } else
           if(",.".indexOf(chr) >= 0)
           {
               if(chr == decimal)
                   throw new NumberFormatException(text);
               if(i <= decimalPrecision + 1)
               {
                   if(decimal != -1)
                       throw new NumberFormatException(text);
                   decimal = chr;
                   in = '.' + in;
               } else
               if(milhar == -1)
                   milhar = chr;
               if(chr != milhar && chr != decimal)
                   throw new NumberFormatException(text);
           } else
           if("01234567890".indexOf(chr) >= 0)
               in = chr + in;
           else
               throw new NumberFormatException(text);
       }

       return Double.valueOf(in).doubleValue();
   }
   
   protected void carregarComponentes(ActionMapping mapping, 
                                      HttpServletRequest request) throws Exception{
       
   }
   
   protected void carregarComponentes(ActionMapping mapping, 
           HttpServletRequest request,
           WebApplicationContext webAppContext) throws Exception{

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
     
}

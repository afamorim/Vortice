<%@ page language="java" %>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title>Vortice - Sites din&acirc;nmicos e interativos.</title>
		<link href="tema01/default.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript" type="text/JavaScript">
		<!--
		function MM_swapImgRestore() { //v3.0
		  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		
		function MM_preloadImages() { //v3.0
		  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		
		function MM_findObj(n, d) { //v4.01
		  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		  if(!x && d.getElementById) x=d.getElementById(n); return x;
		}
		
		function MM_swapImage() { //v3.0
		  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
		//-->
		</script>
	</head>

	<body onload="MM_preloadImages('tema01/menu01_on.gif, tema01/menu02_on.gif, tema01/menu03_on.gif')">
		<div id="geral">
			<div id="topo">
				<img src="<c:url value="/site/tema01/menu01_on.gif"/>" name="menu01" border="0" id="menu01" />
				<a href="<c:url value="/site/servicos.jsp"/>" onmouseout="MM_swapImgRestore()" 
					onmouseover="MM_swapImage('menu02','','<c:url value="/site/tema01/menu02_on.gif"/>',1)">
					<img src="<c:url value="/site/tema01/menu02_off.gif"/>" name="menu02" border="0" id="menu02" />
				</a>
				<a href="<c:url value="/site/contato.jsp"/>" onmouseout="MM_swapImgRestore()" 
					onmouseover="MM_swapImage('menu03','','<c:url value="/site/tema01/menu03_on.gif"/>',1)">
					<img src="<c:url value="/site/tema01/menu03_off.gif"/>" name="menu03" border="0" id="menu03" />
				</a>
			</div>
			<div id="interna">
				<div id="interna_conteudo">
					<div id="cliente">
							<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				              <tr>
				                <td valign="top"><img src="<c:url value="/site/tema01/capa_muricySampaio.jpg"/>" /></td>
				                <td valign="top">
									<h1>Cliente:</h1> Muricy Sampaio<br />
									<h1>Descri&ccedil;&atilde;o:</h1> 
									Escritorio de advocacia composto de jovens advogados , investiram no serviço JureWeb da vortice como solução de 
									apoio o seu trabalho diario, para organizar e facilitar o trabalho diário com os processos do escritorio.<br />
									<h1>Site:</h1> 
								  <a href="http://www.muricysamapio.adv.br" target="_blank" class="link01">"http://www.muricysamapio.adv.br"</a>
								</td>
				              </tr>
				        </table>
					</div>
					<div id="cliente">
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td valign="top"><img src="<c:url value="/site/tema01/capa_tpne.jpg"/>" width="200" height="200" /></td>
								<td valign="top">
									<h1>Cliente:</h1>Primeira do Nordeste<br />
									<h1>Descri&ccedil;&atilde;o:</h1> 
									Maior empresa Bahia de Transporte a Primeira do Nordeste, decidiu pela Vortice para automatizar seu site e 
									facilitar a comunicação do sistema de transporte, por eles hoje utilizados, atraves da web, com uma solução 
									simples de Leitura de arquivos e envio de e-mails, seus clientes tem acesso aos transportes por eles agendas 
									assim como gerar novos pedidos de trabsporte.<br />
									<h1>Site:</h1> 
									<a href="http://www.primeiradonordeste.com.br" target="_blank" class="link01">http://www.primeiradonordeste.com.br</a>
								</td>
							</tr>
						</table>
					</div>
					<div id="cliente">
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td valign="top"><img src="<c:url value="/site/tema01/capa_balancete.jpg"/>" width="200" height="200" /></td>
								<td valign="top">
									<h1>Cliente:</h1>L. G. LOPES E GONÇALVES ADVOGADOS<br />
									<h1>Descri&ccedil;&atilde;o:</h1> 
									Empresa que presta serviço na área jurídica e contábil fimou conosco um contrato para desenvolvermos uma solução 
									customizada, simples, rápida e de fácil acesso para geração de balancetes mensais/plano de contas dos clientes do 
									tipo pessoa jurídica. Como resultado, obtiveram segurança das informações, dinamismo na resposta ao cliente e 
									organização.<br />
									<h1>Site:</h1> 
									<a href="http://balancete.lglopesegoncalves.adv.br:8080/" target="_blank" class="link01">http://balancete.lglopesegoncalves.adv.br:8080/</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	
	</body>
</html>

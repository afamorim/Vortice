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
				<a href="<c:url value="/site/clientes.jsp"/>" onmouseout="MM_swapImgRestore()" 
					onmouseover="MM_swapImage('menu01','','<c:url value="/site/tema01/menu01_on.gif"/>',1)">
					<img src="<c:url value="/site/tema01/menu01_off.gif"/>" name="menu01" border="0" id="menu01" />
				</a>
				<img src="<c:url value="/site/tema01/menu02_on.gif"/>" name="menu02" border="0" id="menu02" />
				<a href="<c:url value="/site/contato.jsp"/>" onmouseout="MM_swapImgRestore()" 
					onmouseover="MM_swapImage('menu03','','<c:url value="/site/tema01/menu03_on.gif"/>',1)">
					<img src="<c:url value="/site/tema01/menu03_off.gif"/>" name="menu03" border="0" id="menu03" />
				</a>
			</div>
			<div id="interna">
				<div id="interna_conteudo">
					<strong>Servi&ccedil;os:</strong><br />
						<ul>
							<li>Consultoria em Arquitetura/Desenvolvimento de sistemas .NET/J2EE;</li>
							<li>Consultoria em Gerenciamento de Projetos de TI;</li>
							<li>Treinamentos&nbsp; em .NET/J2EE;</li>
							<li>Solu&ccedil;&otilde;es de desenvolvimento de sistemas na plataforma Web.</li>
						</ul>
					<strong>Produtos:</strong><br /><br />
						<ul>
							<li><b>JuriWeb Vortice:</b> Sistema Jur&iacute;dico integrado na plataforma Web para controle de Processos e Acordos Judiciais; 
							<br />Apresentação: <a href="<c:url value="/nucleo/download/JureWebSO.ppt"/>" target="_blank">Clique Aqui</a>
							<br />Demonstração: <a href="http://www.vorticeservicos.info:8080/vortice/admin/index.jsp" target="_blank" class="link01">
							Clique Aqui</a>
							</li>
							<li><b>BalanceteWeb Vortice</b>: Sistema de Gera&ccedil;&atilde;o de Balancetes Cont&aacute;beis na plataforma Web.</li>
							<li><b>Gerencia de Documento</b>: Sistema que persisti e controla os documentos atraves de uma apllet, automatizando o trabalho 
							de atualiza&ccedil;&atilde;o dos mesmos e controlando todo o log sobre os trabalhos dos documentos. </li>
					  </ul>
				</div>
			</div>
		</div>
	
	</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>..:: ERRO ::..</title>
<link href='<c:url value="/styles/style.css"/>' rel="stylesheet" type="text/css">
</head>

<body>
<table width="700" border="0" align="center">
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="screen_title">Erro de Sistema </td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#0092DE" heigth="5px" ></td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#0092DE" heigth="5px"></td>
  </tr>
  <tr>
    <td width="97" class="textoTabela">Mensagem:</td>
    
    <td width="599" align="left" class="textoTabela"><%=request.getAttribute("MESSAGE")%></td>
  </tr>
  <tr>
    <td class="textoTabela">Classe:</td>
    <td align="left" class="textoTabela"><%=request.getAttribute("CLASS_NAME")%></td>
  </tr>
  <tr>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td colspan="2" class="textoTabela">Detalhamento:</td>
  </tr>
  <tr>
    <td colspan="2"><hr></td>
  </tr>
  <tr align="center" valign="top">
    <td height="200" colspan="2">
		<div align="left" id="Layer1" style="position:relative; left:0px; top:0px; width:700px; height:240px; z-index:1; overflow: auto;" >
			<%=request.getAttribute("STACK_TRACE")%>	
		</div>
	</td>
  </tr>
  <tr>
    <td colspan="2"><hr></td>
  </tr>
  <tr>
    <td colspan="2" align="right" valign="bottom"><input name="Submit" type="submit" class="button_default" value="Voltar" onClick="JavaScript:history.back()"></td>
  </tr>
</table>
</body>
</html>


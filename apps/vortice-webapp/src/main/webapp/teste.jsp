<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
AI VAI>>>>
<%
try{
	Class.forName("org.postgresql.Driver");
	
	Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/balance_balancetedb","balance_balancete","balancete!1");
	PreparedStatement stmt = con.prepareStatement("select * from estado");
	out.println("Rodou misera!!!");
}catch(SQLException sqlEx){
	out.println(sqlEx);
}
%>
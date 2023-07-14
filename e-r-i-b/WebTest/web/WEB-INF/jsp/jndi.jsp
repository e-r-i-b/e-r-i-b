<%@ page import="java.util.Hashtable" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%--
  User: Krenev
  Date: 17.03.2009
  Time: 16:01:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title></head>
  <body>
  <%
     Hashtable env = new Hashtable();
     env.put("java.naming.factory.initial","oracle.j2ee.rmi.RMIInitialContextFactory");
     env.put("java.naming.provider.url", "ormi://vlgwrk51.rsvologda.local:23791/PhizIC");
     env.put("java.naming.security.principal","oc4jadmin");
     env.put("java.naming.security.credentials","123");
     Context initial = new InitialContext(env);
     out.print(initial.lookup("hibernate/session-factory/PhizIC"));
//     initial.unbind("hibernate/session-factory/PhizIC");
//     out.print(initial.lookup("hibernate/session-factory/PhizIC"));
  %>
  </body>
</html>
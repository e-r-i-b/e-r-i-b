<%@ page import="com.rssl.phizic.test.fns.PacketEPDMaker" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PacketEPDMaker packet = new PacketEPDMaker();

    String xmlText = packet.makeXmlFNS(request.getParameter("countPayment"));
%>
    <head><title>Test FNS</title></head>
    <body>
        <c:set var="xmlText" value="<%=xmlText%>"/>

        <form action="${param.loginUrl}" method="POST">
            Сгенерирован XML с платёжными поручениями:
            <br>
            <textarea name="PayInfo" rows="40" cols="80">${xmlText}</textarea>
            <br>
            <input type="submit" value="Отправить"/>
        </form>
    </body>
</html>
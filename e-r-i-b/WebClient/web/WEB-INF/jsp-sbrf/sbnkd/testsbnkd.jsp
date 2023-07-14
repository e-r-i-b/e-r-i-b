<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="insert" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:form action="/private/sbnkd/test" method="POST" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    Идентификатор документа <br>
    <html:text property="id"/><br>

    Документ на отправку. <br>
    <html:textarea property="json" rows="20" cols="60"/><br>

    Инфо по картам. <br>
    <html:textarea property="cardJson" rows="20" cols="60"/><br>

    <input type="submit" value="Отправить."/>
</html:form>
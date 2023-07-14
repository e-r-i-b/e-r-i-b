<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Удаление контакта из адресной книги</title>
</head>

<body>
<h1>Удаление контакта из адресной книги</h1>

<html:form action="/mobileApi/contact/delete" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/contacts/delete.do"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr>
                    <td>Id контакта</td>
                    <td><html:text property="id" value="23"/></td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>

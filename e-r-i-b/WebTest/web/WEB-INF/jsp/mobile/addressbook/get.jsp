<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>ѕолучение контактов из адресной книги</title>
</head>

<body>
<h1>ѕолучение контактов из адресной книги</h1>

<html:form action="/mobileApi/contact/get" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/contacts/get.do"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr>
                    <td>phones</td>
                    <td><html:textarea property="phones" rows="20" cols="70" value='79001231234,79015555555'/></td>
                </tr>
                <tr>
                    <td>showBookmark</td>
                    <td><html:checkbox property="showBookmark"/></td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>

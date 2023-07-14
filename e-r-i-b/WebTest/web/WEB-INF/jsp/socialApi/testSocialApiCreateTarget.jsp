<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Тест-кейс upload-а изображения для цели в АЛФ.</title>
</head>

<body>
<h1>Тест-кейс upload-а изображения для цели в АЛФ.</h1>

<html:form action="/mobileApi/multipartFormData/newALFTarget" enctype="multipart/form-data" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/finances/targets/editTarget.do?operation=save"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr><td>type</td>             <td><html:text property="type"/></td></tr>
                <tr><td>name</td>             <td><html:text property="name"/></td></tr>
                <tr><td>comment</td>          <td><html:text property="comment"/></td></tr>
                <tr><td>amount</td>           <td><html:text property="amount"/></td></tr>
                <tr><td>date</td>             <td><html:text property="date"/></td></tr>
                <tr><td>file</td>             <td><html:file property="file"/></td></tr>
            </table>
            <br/>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>

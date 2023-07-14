<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Тест-кейс upload-а файла на примере сохранения письма.</title>
</head>

<body>
<h1>Тест-кейс upload-а файла на примере сохранения письма.</h1>

<html:form action="/mobileApi/multipartFormData" enctype="multipart/form-data" show="true">
    <tiles:insert definition="social" flush="false">
        <tiles:put name="address" value="/private/mail/edit.do?operation=save"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr><td>id</td>               <td><html:text property="id"/></td></tr>
                <tr><td>parentId</td>         <td><html:text property="parentId"/></td></tr>
                <tr><td>type</td>             <td><html:text property="type" value="OTHER"/></td></tr>
                <tr><td>themeId</td>          <td><html:text property="themeId" value="1"/></td></tr>
                <tr><td>responseMethod</td>   <td><html:text property="responseMethod" value="BY_PHONE"/></td></tr>
                <tr><td>phone</td>            <td><html:text property="phone" value="123"/></td></tr>
                <tr><td>eMail</td>            <td><html:text property="EMail"/></td></tr>
                <tr><td>subject</td>          <td><html:text property="subject" value="Ничего не работает!!!"/></td></tr>
                <tr><td>body</td>             <td><html:textarea property="body" value="Туловище письма"/></td></tr>
                <tr><td>file</td>             <td><html:file property="file"/></td></tr>
            </table>
            <br/>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>

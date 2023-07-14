<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Тест-кейс upload-а файла на примере загрузки аватара.</title>
    </head>

    <body>
    <h1>Тест-кейс upload-а файла на примере загрузки аватара.</h1>

    <html:form action="/socialApi/multipartFormData/loadAvatar" enctype="multipart/form-data" show="true">
        <tiles:insert definition="social" flush="false">
            <tiles:put name="address" value="/private/profile/avatar.do?operation=upload"/>
            <tiles:put name="operation" value="send"/>

            <tiles:put name="data">
                <table>
                    <tr>
                        <td>file</td>
                        <td><html:file property="file"/></td>
                    </tr>
                </table>
                <br/>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:html>
    <head>
        <title>Оповещение ЕРИБ о списании абонентской платы</title>
    </head>

    <body>
    <h1>ЕРМБ: списание абонентской платы</h1>
    Загрузка ФПП файла<br/>
    Из фпп файла берутся только номера карт из платежных транзакций (CSHTX). Данные по картам подгружаются из БД.<br/>

    <html:form action="/ermb/update/servicefeeresult" enctype="multipart/form-data" show="true">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <html:file property="file"/><br/><br/>
        <html:radio property="success" value="true">Успешное списание</html:radio><br/>
        <html:radio property="success" value="false">Неуспешное списание</html:radio><br/><br/>
        <html:submit property="operation" value="send"/><br/><br/>
        <html:textarea property="status" cols="100" rows="100" readonly="true" style="border:0px"/><br/>
    </html:form>

    </body>
</html:html>
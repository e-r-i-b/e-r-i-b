<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:html>
<head>
    <title>ЕРМБ: регистрация или удаление телефонов</title>
</head>

<body>
<h1>ЕРМБ: регистрация или удаление телефонов</h1>

<html:form action="/ermb/update/updatePhones" show="true">

    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td colspan="2">Информация о пользователе</td></tr>
        <tr><td>Фамилия:</td><td><html:text property="surName" size="20"/></td></tr>
        <tr><td>Имя:</td><td><html:text property="firstName" size="100"/></td></tr>
        <tr><td>Отчество:</td><td><html:text property="patrName" size="20"/></td></tr>
        <tr><td>Дата рождения (дд.мм.гггг чч:ММ:сс):</td><td><html:text property="birthDate" size="20"/></td></tr>
        <tr><td>ДУЛ:</td><td><html:text property="passport" size="20"/></td></tr>
        <tr><td>ТБ:</td><td><html:text property="cbCode" size="20"/></td></tr>
        <tr><td colspan="2">Регистрируемый основной телефон</td></tr>
        <tr><td>Номер основного телефона:</td><td><html:text property="phoneNumber" size="20"/></td></tr>
        <tr><td colspan="2">Удаляемые телефоны</td></tr>
        <tr><td>Удалять дубликаты:</td><td><html:checkbox property="deleteDuplicates"/></td></tr>
        <tr><td>Список добавляемых телефонов (через запятую):</td><td><html:text property="addPhones" size="20"/></td></tr>
        <tr><td>Список удаляемых телефонов (через запятую):</td><td><html:text property="removePhones" size="20"/></td></tr>
    </table>

    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr><td>Ошибки:</td></tr>
        <tr><td><html:textarea property="errors" readonly="true" rows="5" style="width:100%"/></td></tr>
    </table>

    <html:submit property="operation" value="send"/>
</html:form>

</body>
</html:html>
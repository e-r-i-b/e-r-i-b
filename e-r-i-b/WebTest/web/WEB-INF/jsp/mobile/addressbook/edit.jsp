<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Редактирование контакта в адресной книге</title>
</head>

<body>
<h1>Редактирование контакта в адресной книге</h1>

<html:form action="/mobileApi/contact/edit" show="true">
    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/contacts/edit.do"/>
        <tiles:put name="operation" value="send"/>

        <tiles:put name="data">
            <table>
                <tr>
                    <td>Id контакта</td>
                    <td><html:text property="id" value="23"/></td>
                </tr>
                <tr>
                    <td>Наименование контакта</td>
                    <td><html:text property="name" value="Вася Пупкин"/></td>
                </tr>
                <tr>
                    <td>Пользовательское имя</td>
                    <td><html:text property="alias" value="Привет мир"/></td>
                </tr>
                <tr>
                    <td>Короткое пользовательское имя</td>
                    <td><html:text property="smallalias" value="Гога"/></td>
                </tr>
                <tr>
                    <td>Сохраненный номер банковской карты</td>
                    <td><html:text property="cardnubmer" value="5454545465657676"/></td>
                </tr>
                <tr>
                    <td>Категория контакта</td>
                    <td><html:select property="category">
                        <html:option value="NONE">Нет категории</html:option>
                        <html:option value="BOOKMARK">Избранный</html:option>
                    </html:select></td>
                </tr>
                <tr>
                    <td>Статус</td>
                    <td><html:checkbox property="trusted"/>Доверенный</td>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>

</html:form>

</body>
</html:html>

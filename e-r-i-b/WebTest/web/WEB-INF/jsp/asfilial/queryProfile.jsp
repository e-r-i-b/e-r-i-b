<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:html>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/Templates.js"></script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery-1.4.2.js"></script>
    <script type="text/javascript">
        var dojoConfig = {
            async: true,
            parseOnLoad: true,
            baseUrl: "${initParam.resourcesRealPath}/scripts/",     // корень со всеми js
            packages: [
                { name: "dojo", location: "dojo" }                 // здесь лежит dojo

            ]
        };
    </script>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dojo/dojo.js"></script>
    <script type="text/javascript">
        require(["dojo/ready", "dojo/on", "dojo/query","dojo/dom-construct"], function(ready, on, query,dom)
        {
            function addOldPersonDataField(onClieckElement, toElementName, position)
            {
                on(onClieckElement, "click", function()
                {

                        dom.place('<div>Фамилия(*)</div><input name="oldLastName" maxlength="40" size="40"/></p>' +
                                  '<div>Имя(*)</div><input name="oldFirstName" maxlength="40" size="40"/></p>' +
                                  '<div>Отчество</div><input name="oldMiddleName" maxlength="40" size="40"/></p>' +
                                  '<div>Дата рождения(dd.mm.YYYY)(*)</div><input name="oldBirthday" maxlength="10" size="10"/></p>' +
                                  '<div>Серия документа</div><input name="oldIdSeries" maxlength="12" size="12"/></p>' +
                                  '<div>Номер документа(*)</div><input name="oldIdNum" maxlength="12" size="12"/></p>' +
                                  '<div>Типы документов, удостоверяющих личность(*)</div><input name="oldIdType" maxlength="4" size="4"/></p>'
                                ,toElementName, position);

                        dom.place('</p>', toElementName, position);
                });

            }
            ready(function()
            {
                addOldPersonDataField(query("#addIdentity"),"oldIdentity","last");
            });
        });
    </script>

    <script type="text/javascript">
        function testdDt()
        {
            if (!templateObj.DATA.validate($('input[name="birthday"]').val()))
            {
                alert("Введите корректное значение в поле Дата рождения.");
                return false;
            }

            var issueDt =   $('input[name="issueDt"]').val();

            if   (issueDt.length != 0 &&  !templateObj.DATA.validate(issueDt))
            {
                alert("Введите корректное значение в поле Дата выдачи.");
                return false;
            }
            return true;

        }
    </script>

    <head><title>Тестирование взаимодействия ЕРИБ с АС Филиал-Сбербанк</title></head>
    <body>
    <h1>получения профиля клиента в ЕРИБ</h1>

    <html:form action="/asfilial" show="true">
        <html:hidden property="ASListenerUrl"/> 
        <table>
            <tr>
                <td>
                    Создавать новый профиль, если клиент не найден
                </td>
                <td>
                    <html:checkbox property="createIfNone"/>
                </td>
            </tr>
            <tr>
                <td>Информация о подразделении, в рамках которого выполняется запрос[1]</td>
                <td><jsp:include page="bankInfo.jsp"/></td>
            </tr>
            <tr>
                <td>
                    Идентификационные данные клиента
                </td>
                <td>
                   <jsp:include page="clientIdentity.jsp"/>
                </td>
            </tr>
            <tr>
                <td>

                    Старые идентификационные данный клиента
                </td>
                <td>
                    <a id="addIdentity" href="#">добавить данные</a>
                    <ui id="oldIdentity"></ui>
                    </td>
            </tr>
        </table>
        <html:submit property="operation" value="QueryProfile"  onclick="return testdDt();"/>
        <html:submit property="operation" value="Back"/>
    </html:form>
    </body>
</html:html>
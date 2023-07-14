<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 23.01.14
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
<head>
    <title>Добавление транзации в приложение лимитов</title>
</head>
<body>
<H1>Лимиты: добавление тразакции</H1>
<html:form action="/limits/add" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <h2>Данные клиента:</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr>
            <td>Фамилия</td>
            <td><html:text property="surName" size="40"/></td>
        </tr>
        <tr>
            <td>Имя</td>
            <td><html:text property="firstName" size="40"/></td>
        </tr>
        <tr>
            <td>Отчество</td>
            <td><html:text property="patrName" size="40"/></td>
        </tr>
        <tr>
            <td>Дата рождения (YYYY-MM-DDTHH:mm:SS.fff)</td>
            <td><html:text property="birthDate" size="40"/></td>
        </tr>
        <tr>
            <td>Серия документа</td>
            <td><html:text property="docSeries" size="40"/></td>
        </tr>
        <tr>
            <td>Номер документа</td>
            <td><html:text property="docNumber" size="40"/></td>
        </tr>
        <tr>
            <td>Тербанк</td>
            <td><html:text property="tb" size="40"/></td>
        </tr>
    </table>

    <h2>Транзакция:</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
        <tr>
            <td>Внешний идентификатор записи</td>
            <td><html:text property="externalId" size="40"/></td>
        </tr>
        <tr>
            <td>Внешний идентификатор платежа</td>
            <td><html:text property="documentExternalId" size="40"/></td>
        </tr>
        <tr>
            <td>Значение суммы</td>
            <td><html:text property="amountValue" size="40"/></td>
        </tr>
        <tr>
            <td>Валюта</td>
            <td><html:text property="currency" size="40"/></td>
        </tr>
        <tr>
            <td>Дата исполнения (YYYY-MM-DDTHH:mm:SS.fff)</td>
            <td><html:text property="operationDate" size="40"/></td>
        </tr>
        <tr>
            <td>Тип канала подтверждения документа</td>
            <td>
                <html:select property="channelType">
                    <html:option value="MOBILE_API">Мобильные приложения</html:option>
                    <html:option value="INTERNET_CLIENT">Интернет клиент</html:option>
                    <html:option value="VSP">ВСП</html:option>
                    <html:option value="CALL_CENTR">КЦ</html:option>
                    <html:option value="SELF_SERVICE_DEVICE">Устройство самообслуживания</html:option>
                    <html:option value="ERMB_SMS">Смс-канал мобильного банка</html:option>
                </html:select>
            </td>
        </tr>
    </table>

    <h2>Лимиты</h2>
    <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
           <tr>
               <td>Тип лимита</td>
               <td>
                   <select id="limitType">
                       <option value="GROUP_RISK">лимит по группам риска</option>
                       <option value="OBSTRUCTION_FOR_AMOUNT_OPERATION">заградительный лимит по сумме операции</option>
                       <option value="OBSTRUCTION_FOR_AMOUNT_OPERATIONS">заградительный лимит по сумме операций</option>
                       <option value="IMSI">IMSI лимит</option>
                       <option value="EXTERNAL_PHONE">лимит на получателя для оплаты чужого телефона</option>
                       <option value="EXTERNAL_CARD">лимит на получателя для переводов на чужую карту</option>
                   </select>
               </td>
           </tr>
           <tr>
               <td>Тип ограницения</td>
               <td>
                   <select id="restrictionType">
                       <option value="DESCENDING">По убыванию лимита</option>
                       <option value="AMOUNT_IN_DAY">На сумму операций в сутки</option>
                       <option value="CARD_ALL_AMOUNT_IN_DAY">На сумму операций в сутки всех получателей платежа по карте</option>
                       <option value="PHONE_ALL_AMOUNT_IN_DAY">На сумму операций в сутки всех получателей платежа по телефону</option>
                       <option value="MIN_AMOUNT">На минимальную сумму операции</option>
                       <option value="OPERATION_COUNT_IN_DAY">На количество операций в сутки</option>
                       <option value="OPERATION_COUNT_IN_HOUR">На количество операций в час</option>
                       <option value="IMSI">Смена SIM-карты</option>
                       <option value="MAX_AMOUNT_BY_TEMPLATE">Превышена сумма оплаты по шаблону</option>
                   </select>
               </td>
           </tr>
           <tr>
               <td>Внешний идентификатор группы риска</td>
               <td><input type="text" id="groupRiskId" width="30"></td>
           </tr>
           <tr>
               <td colspan="2">
                   <button type="button" onclick="add()">Add</button>
               </td>
           </tr>
           <tr>
               <td colspan="2">
                   <html:text property="limits" styleId="limits" size="200"/>
               </td>
           </tr>
       </table>

    <html:textarea property="error" rows="10" cols="50"/>

    <script type="text/javascript">
        function add()
        {
            var limitType = document.getElementById('limitType');
            var restrictionType = document.getElementById('restrictionType');
            var groupRiskId = document.getElementById('groupRiskId');
            var limits = document.getElementById('limits');

            var result = limits.value;

            result += limitType.value + "," + restrictionType.value + "," + groupRiskId.value + ";";

            limitType.value = "GROUP_RISK";
            restrictionType.value = "DESCENDING";
            groupRiskId.value = "";
            limits.value = result;
            return true;
        }
    </script>

    <html:submit property="operation" value="send"/>
</html:form>
</body>
</html:html>
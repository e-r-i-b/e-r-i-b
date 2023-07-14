<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
    <head>
        <title>Отправка данных по расширенной заявке на кредит</title>
    </head>

    <body>
    <h1>Отправка данных по расширенной заявке на кредит</h1>

    <c:set var="useXsdRelease16" value="${phiz:isXsd16ReleaseForEtsmUse()}"/>
    <c:set var="useXsdRelease19" value="${phiz:isXsd19ReleaseForEtsmUse()}"/>
    <c:choose>
        <c:when test="${useXsdRelease19 == true}">
            Отправка сообщения в формате XSD 19 релиза (для переключения на формат 16 релиза необходимо изменить признак use-xsd-release-19-scheme в конфиге LoanClaimConfig)
        </c:when>
        <c:when test="${useXsdRelease16 == true}">
            Отправка сообщения в формате XSD 16 релиза (для переключения на формат 13 релиза необходимо изменить признак use-new-xsd-scheme в конфиге LoanClaimConfig)
        </c:when>
        <c:otherwise>
            Отправка сообщения в формате XSD 13 релиза (для переключения на формат 16 релиза необходимо изменить признак use-new-xsd-scheme в конфиге LoanClaimConfig)
        </c:otherwise>
    </c:choose>

    <br/>
    <br/>

    <html:form action="/credit/loanclaim" show="true">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
            <tr><td>Номер заявки:</td><td><html:text property="claimNumber" size="20"/></td></tr>
            <tr><td>Статус заявки:</td><td><html:text property="claimStatus" size="1"/></td></tr>
            <tr><td>operUID:</td><td><html:text property="operUID" size="32"/></td></tr>
        </table>
        <h2> Блок с данными по одобренному кредиту:</h2>
        <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
            <tr><td>Сумма:</td><td><html:text property="approvedAmount" size="20"/></td></tr>
            <tr><td>Ставка:</td><td><html:text property="approvedInterestRate" size="20"/></td></tr>
            <tr><td>Период:</td><td><html:text property="approvedPeriod" size="10"/></td></tr>
        </table>
        <c:if test="${useXsdRelease16 or useXsdRelease19}">
            <h2> Блок с данными по предварительно одобренному кредиту:</h2>
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td>Сумма:</td><td><html:text property="preApprovedAmount" size="20"/></td></tr>
                <tr><td>Ставка:</td><td><html:text property="preApprovedInterestRate" size="20"/></td></tr>
                <tr><td>Период:</td><td><html:text property="preApprovedPeriod" size="10"/></td></tr>
            </table>
            <c:if test="${useXsdRelease19}">
                <h2> Блок с данными</h2>
                <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <tr><td>Номер заявки в TSM:</td><td><html:text property="applicationNumber" size="20"/></td></tr>
                    <tr><td>Причина необходимости посещения офиса:</td><td><html:text property="needVisitOfficeReason" size="100"/></td></tr>
                    <tr><td>ФИО КИ:</td><td><html:text property="fioKI" size="20"/></td></tr>
                    <tr><td>Логин КИ:</td><td><html:text property="loginKI" size="10"/></td></tr>
                    <tr><td>ФИО ТМ:</td><td><html:text property="fioTM" size="10"/></td></tr>
                    <tr><td>Логин ТМ:</td><td><html:text property="loginTM" size="10"/></td></tr>
                    <tr><td>Подразделение для оформления заявки в формате: тер. Банк (2 цифры), ОСБ (4 цифры), ВСП (5 цифр):</td><td><html:text property="department" size="10"/></td></tr>
                    <tr><td>Канал продаж:</td><td><html:text property="channel" size="10"/></td></tr>
                    <tr><td>Дата подписания заявки клиентом:</td><td><html:text property="agreementDate" size="10"/></td></tr>
                    <tr><td>Способ выдачи кредита:</td><td><html:text property="applicationType" size="10"/></td></tr>
                    <tr><td>Номер счета вклада в Сбербанке:</td><td><html:text property="acctIdType" size="25"/></td></tr>
                    <tr><td>Номер банковской карты (кроме Сберкарт):</td><td><html:text property="cardNum" size="19"/></td></tr>
                    <tr><td>Страховка - Номер бизнес-процесса:</td><td><html:text property="businessProcess" size="20"/></td></tr>
                    <tr><td>Страховка - Артикул страхового продукта:</td><td><html:text property="insuranceProgram" size="20"/></td></tr>
                    <tr><td>Страховка - признак включения страховки в кредит:</td><td><html:checkbox property="includeInsuranceFlag"/></td></tr>
                    <tr><td>Страховка - Сумма страховой премии:</td><td><html:text property="insurancePremium" size="20"/></td></tr>
                    <tr><td>Тип продукта:</td><td><html:text property="type" size="10"/></td></tr>
                    <tr><td>Код продукта:</td><td><html:text property="productCode" size="10"/></td></tr>
                    <tr><td>Тип субпродукта:</td><td><html:text property="subProductCode" size="10"/></td></tr>
                    <tr><td>Сумма кредита:</td><td><html:text property="loanAmount" size="10"/></td></tr>
                    <tr><td>Срок кредита в месяцах:</td><td><html:text property="loanPeriod" size="10"/></td></tr>
                    <tr><td>Валюта кредита:</td><td><html:text property="currency" size="10"/></td></tr>
                    <tr><td>Тип погашения кредита:</td><td><html:text property="paymentType" size="10"/></td></tr>
                    <tr><td>Фамилия:</td><td><html:text property="surName" size="10"/></td></tr>
                    <tr><td>Имя:</td><td><html:text property="firstName" size="10"/></td></tr>
                    <tr><td>Отчество:</td><td><html:text property="patrName" size="10"/></td></tr>
                    <tr><td>День рождения:</td><td><html:text property="birthDay" size="12"/></td></tr>
                    <tr><td>Гражданство:</td><td><html:text property="citizen" size="10"/></td></tr>
                    <tr><td>Серия документа:</td><td><html:text property="documentSeries" size="4"/></td></tr>
                    <tr><td>Номер документа:</td><td><html:text property="documentNumber" size="6"/></td></tr>
                    <tr><td>Дата выдачи документа:</td><td><html:text property="passportIssueDate" size="10"/></td></tr>
                    <tr><td>Код подразделения:</td><td><html:text property="passportIssueByCode" size="10"/></td></tr>
                    <tr><td>Орган выдавший документ:</td><td><html:text property="passportIssueBy" size="10"/></td></tr>
                    <tr><td>Наличие в действующем паспорте отметки о ранее выданном:</td><td><html:checkbox property="hasOldPassport"/></td></tr>
                    <tr><td>Серия документа:</td><td><html:text property="oldDocumentSeries" size="4"/></td></tr>
                    <tr><td>Номер документа:</td><td><html:text property="oldDocumentNumber" size="6"/></td></tr>
                    <tr><td>Дата выдачи документа:</td><td><html:text property="oldPassportIssueDate" size="10"/></td></tr>
                    <tr><td>Орган выдавший документ:</td><td><html:text property="oldPassportIssueBy" size="10"/></td></tr>
                    <tr><td>Признак предодобренного предложения.:</td><td><html:checkbox property="peapprovedFlag"/></td></tr>
                </table>
            </c:if>
        </c:if>
        <br/>
        <br/>
        <html:submit property="operation" value="send"/>
    </html:form>
    <a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/messages')}">Вернуться к списку</a>
    </body>
</html:html>
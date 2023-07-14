<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/debitcard/claims/view" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="claim" value="${form.claim}"/>
    <c:set var="bundle" value="cardsBundle"/>

    <tiles:insert definition="cardProductList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.card.debit.claim.list" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="DebitCardClaims"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function openOperationLog()
                {
                    openGuestOperationConfirmLog('${claim.operationUID}');
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description"><bean:message bundle="personsBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">
                    <%--ФИО пользователя--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.owner.FIO" bundle="cardsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">${claim.personLastName} ${claim.personFirstName} ${claim.personMiddleName}</tiles:put>
                    </tiles:insert>

                    <%--Номер телефона--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.phone" bundle="cardsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">${claim.phone}</tiles:put>
                    </tiles:insert>

                    <%--Дата создания заявки--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.dateCreatedClaim" bundle="personsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data"><fmt:formatDate value="${claim.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/></tiles:put>
                    </tiles:insert>

                    <%--Номер заявки--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.numberClaim" bundle="personsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">${claim.id}</tiles:put>
                    </tiles:insert>

                    <%--Карты--%>
                    <fieldset>
                        <legend><bean:message key="view.cards.title" bundle="personsBundle"/></legend>
                        <div class="clear"></div>
                        <c:forEach var="cardInfo" items="${claim.cardInfos}" varStatus="lineInfo">
                            <c:set var="mobileBankTariff" value="${cardInfo.MBCContractType  == '0' ? 'full' : 'econom'}"/>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <c:out value="${cardInfo.cardName}"/>,
                                    <bean:message key="field.currency.${cardInfo.contractCurrency}.smallFirst" bundle="personsBundle"/>
                                </tiles:put>
                                <tiles:put name="needMargin" value="true"/>
                                <tiles:put name="data">
                                    <bean:message key="label.cardState.${cardInfo.status}" bundle="personsBundle"/>
                                </tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>
                        </c:forEach>
                    </fieldset>

                    <%--Услуга «Мобильный банк»--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.mobileBankService" bundle="personsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data"><bean:message key="label.${mobileBankTariff}Tariff.title" bundle="personsBundle"/></tiles:put>
                    </tiles:insert>

                    <%--Автоплатеж за сотовую связь--%>
                    <c:if test="${claim.cardAcctAutoPayInfo != null && claim.cardAcctAutoPayInfo != '' && claim.cardAcctAutoPayInfo != '0.0'}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title"><bean:message key="label.autopayments.title" bundle="personsBundle"/></tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <bean:message key="label.phoneNumber" bundle="personsBundle"/> ${claim.phone};
                                <bean:message key="label.sum" bundle="personsBundle"/> ${claim.cardAcctAutoPayInfo} <bean:message key="label.of.RUB" bundle="personsBundle"/>;
                                <bean:message key="label.minBalanceForPayment" bundle="personsBundle"/> ${claim.cardAcctAutoPayInfo} <bean:message key="label.of.RUB" bundle="personsBundle"/>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <%--Удостоверяющий документ--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.certifyingDocument" bundle="personsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:if test="${!(claim.identityCardType == null || claim.identityCardType == '')}"><bean:message key="document.type.${phiz:getClientDocumentType(claim.identityCardType)}" bundle="personsBundle"/>;</c:if>
                            <bean:message key="mask.docNumber" bundle="personsBundle"/> ${claim.identityCardSeries} - ${claim.identityCardNumber}
                            <c:if test="${!(claim.identityCardIssuedCode == null || claim.identityCardIssuedCode == '')}">;<bean:message key="field.officeCode" bundle="personsBundle"/> ${claim.identityCardIssuedCode}</c:if>
                        </tiles:put>
                    </tiles:insert>

                    <%--Адрес регистрации--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.address.registration" bundle="cardsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:set var="address" value="${claim.address[0]}"/>
                            ${address.region} <bean:message key="address.region" bundle="cardsBundle"/>,
                            <bean:message key="address.city" bundle="cardsBundle"/> ${address.city},
                            ${address.afterSityAdress}
                        </tiles:put>
                    </tiles:insert>

                    <%--Адрес проживания--%>
                    <c:if test="${fn:length(claim.address) > 1}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title"><bean:message key="label.address.live" bundle="cardsBundle"/></tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <c:set var="address" value="${claim.address[1]}"/>
                                ${address.region} <bean:message key="address.region" bundle="cardsBundle"/>,
                                <bean:message key="address.city" bundle="cardsBundle"/> ${address.city},
                                ${address.afterSityAdress}
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <%--Отделение выдачи карты--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.selectedOffice" bundle="personsBundle"/></tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:set var="cardOffice" value="${claim.contractCreditCardOffice}"/>
                            <div>${fn:substring(cardOffice, fn:indexOf(cardOffice, ",") + 1, fn:length(cardOffice))}</div>
                            <div>${fn:substring(cardOffice, 0, fn:indexOf(cardOffice, ","))}</div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.back"/>
                        <tiles:put name="commandHelpKey" value="button.back.help"/>
                        <tiles:put name="bundle"         value="personsBundle"/>
                        <tiles:put name="action"         value="/debitcard/claims/list.do"/>
                    </tiles:insert>
                    <c:if test="${claim.guest && phiz:impliesOperation('ListGuestOperationConfirmOperation', 'CreateSberbankForEveryDayClaimService')}">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.operationLog"/>
                            <tiles:put name="commandHelpKey" value="button.operationLog.help"/>
                            <tiles:put name="bundle"         value="personsBundle"/>
                            <tiles:put name="onclick"        value="openOperationLog();"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<html:form action="/persons/sbnkd/edit">
    <tiles:insert definition="personEdit">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="claim" value="${form.claim}"/>
        <tiles:put name="needSave" type="string" value="false"/>
        <tiles:put name="submenu" value="sbnkdClaim" type="string"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="personsBundle" key="listpage.title"/></tiles:put>

        <tiles:put name="menu" type="string">
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description"><bean:message bundle="personsBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.dateCreatedClaim" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <fmt:formatDate value="${claim.creationDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.numberClaim" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            ${claim.id}
                        </tiles:put>
                    </tiles:insert>


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
                                    <c:set var="cardStateClass" value="cardClaimState"/>
                                    <c:if test="${cardInfo.status == 'ERROR'}"><c:set var="cardStateClass" value="cardClaimState cardClaimErrorState"/></c:if>
                                    <div class="${cardStateClass}">
                                        <bean:message key="label.cardState.${cardInfo.status}" bundle="personsBundle"/>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>
                        </c:forEach>
                    </fieldset>

                    <%--Подключенные услуги--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.selectedServices" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">

                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.mobileBankService" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <bean:message key="label.${mobileBankTariff}Tariff.title" bundle="personsBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${claim.cardAcctAutoPayInfo != '' && claim.cardAcctAutoPayInfo != '0.0'}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.autopayments.title" bundle="personsBundle"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <bean:message key="label.phoneNumber" bundle="personsBundle"/> ${claim.phone};
                                <bean:message key="label.sum" bundle="personsBundle"/> ${claim.cardAcctAutoPayInfo} <bean:message key="label.of.RUB" bundle="personsBundle"/>;
                                <bean:message key="label.minBalanceForPayment" bundle="personsBundle"/> ${claim.cardAcctAutoPayInfo} <bean:message key="label.of.RUB" bundle="personsBundle"/>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.selectedOffice" bundle="personsBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:set var="cardOffice" value="${claim.contractCreditCardOffice}"/>
                            <div class="title_common">${fn:substring(cardOffice, fn:indexOf(cardOffice, ",") + 1, fn:length(cardOffice))}</div>
                            <div class="selectedOfficeTxt">${fn:substring(cardOffice, 0, fn:indexOf(cardOffice, ","))}</div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                       <tiles:put name="commandTextKey" value="button.back"/>
                       <tiles:put name="commandHelpKey" value="button.back.help"/>
                       <tiles:put name="bundle"         value="personsBundle"/>
                       <tiles:put name="action"         value="/persons/sbnkd/list.do?person=${form.person}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>

</html:form>
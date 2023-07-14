<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<%@ include file="/WEB-INF/jsp/common/layout/print-head.jsp"%>
<body>
    <tiles:insert definition="googleTagManager"/>
    <html:form action="/private/sberbankForEveryDay/printViewClaim" show="true">
        <tiles:importAttribute scope="request"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="claim" value="${form.claim}"/>
        <c:set var="imagesPath" value="${globalUrl}/commonSkin/images"/>

        <div class="printClaim">
            <div class="headerImage">
                <div>
                    <img src="${imagesPath}/sbrfLogo.png"/>
                </div>
            </div>

            <h3 class="titleClaim"><bean:message key="field.print.title.debet" bundle="sbnkdBundle"/></h3>

            <table class="viewCardsClaim">
                <tr>
                    <th colspan="2" class="tblTitleClaim"><bean:message key="view.cards.title" bundle="sbnkdBundle"/></th>
                </tr>
                <c:forEach var="cardInfo" items="${claim.cardInfos}" varStatus="lineInfo">
                    <tr>
                        <td class="cardClaimType">
                            <span>
                                <c:out value="${cardInfo.cardName}"/>,<bean:message key="field.currency.${cardInfo.contractCurrency}.smallFirst" bundle="sbnkdBundle"/>
                            </span>
                            <c:if test="${lineInfo.first}">
                                <c:set var="mobileBankTariff" value="${cardInfo.MBCContractType  == '0' ? 'full' : 'econom'}"/>
                                <p class="claimeClientName">
                                    <c:out value="${claim.personFirstName}"/>  <c:out value="${claim.personMiddleName}"/>    ${fn:substring(claim.personLastName, 0, 1)}.
                                </p>
                            </c:if>
                        </td>
                        <td class="claimDetail"><bean:message key="label.cardState.${cardInfo.status}" bundle="sbnkdBundle"/></td>
                    </tr>
                </c:forEach>

            </table>

            <table class="viewCardsClaim">
                <tr>
                    <th colspan="2" class="tblTitleClaim"><bean:message key="label.selectedYourPersonalData" bundle="sbnkdBundle"/></th>
                </tr>
                <tr>
                    <td class="cardClaimType">
                        <bean:message key="label.certifyingDocument" bundle="sbnkdBundle"/>
                    </td>
                    <td class="claimDetail">
                        <c:if test="${!(claim.identityCardType == null || claim.identityCardType == '')}"><bean:message key="document.type.${phiz:getClientDocumentType(claim.identityCardType)}" bundle="userprofileBundle"/>;</c:if>
                        <c:set var="seriesAndNumber">${claim.identityCardSeries} ${claim.identityCardNumber}</c:set>
                        <bean:message key="mask.docNumber" bundle="sbnkdBundle"/> ${phiz:maskSeriesAndNumber(seriesAndNumber)}
                        <c:if test="${!(claim.identityCardIssuedCode == null || claim.identityCardIssuedCode == '')}">;<bean:message key="field.officeCode" bundle="sbnkdBundle"/> ${claim.identityCardIssuedCode}</c:if>
                    </td>
                </tr>
                <c:if test="${not empty claim.address[0]}">
                    <tr>
                        <td class="cardClaimType"><bean:message key="label.residentialAddress.title" bundle="sbnkdBundle"/></td>
                        <td class="claimDetail">
                            <c:set var="address" value="${claim.address[0]}"/>
                            <c:if test="${address.postalCode != null && address.postalCode != ''}">
                                <bean:message key="field.index" bundle="sbnkdBundle"/> ${address.postalCode},
                            </c:if>
                            <c:if test="${address.region != null && address.region != ''}">
                                ${address.region},
                            </c:if>
                            <c:if test="${address.city != null && address.city != ''}">
                                ã. ${address.city},
                            </c:if>
                                ${address.afterSityAdress}
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty claim.address[1]}">
                    <tr>
                        <td class="cardClaimType"><bean:message key="label.registrationAddress.title" bundle="sbnkdBundle"/></td>
                        <td class="claimDetail">
                            <c:set var="address" value="${claim.address[1]}"/>
                            <c:if test="${address.postalCode != null && address.postalCode != ''}">
                                <bean:message key="field.index" bundle="sbnkdBundle"/> ${address.postalCode},
                            </c:if>
                            <c:if test="${address.region != null && address.region != ''}">
                                ${address.region},
                            </c:if>
                            <c:if test="${address.city != null && address.city != ''}">
                                ã. ${address.city},
                            </c:if>
                            ${address.afterSityAdress}
                        </td>
                    </tr>
                </c:if>
            </table>

            <table class="viewCardsClaim">
                <tr>
                    <th colspan="2" class="tblTitleClaim"><bean:message key="label.selectedServices" bundle="sbnkdBundle"/></th>
                </tr>
                <tr>
                    <td class="cardClaimType">
                        <bean:message key="label.mobileBankService" bundle="sbnkdBundle"/>
                    </td>
                    <td class="claimDetail"><bean:message key="label.${mobileBankTariff}Tariff.title" bundle="sbnkdBundle"/></td>
                </tr>
                <c:if test="${claim.cardAcctAutoPayInfo != ''&& claim.cardAcctAutoPayInfo != '0.0'}">
                    <tr>
                        <td class="cardClaimType"><bean:message key="label.autoPayment" bundle="sbnkdBundle"/></td>
                        <td class="claimDetail">
                            <bean:message key="label.phoneNumber" bundle="sbnkdBundle"/> +7 ${phiz:getCutPhoneForAddressBook(claim.phone)};
                            <bean:message key="label.sum" bundle="sbnkdBundle"/> ${claim.cardAcctAutoPayInfo} <bean:message key="label.of.RUB" bundle="sbnkdBundle"/>;
                            <bean:message key="label.minBalanceForPayment" bundle="sbnkdBundle"/> ${claim.balanceLessThan} <bean:message key="label.of.RUB" bundle="sbnkdBundle"/>
                        </td>
                    </tr>
                </c:if>
            </table>

            <table class="viewCardsClaim">
                <tr>
                    <th colspan="2" class="tblTitleClaim"><bean:message key="label.selectedOffice" bundle="sbnkdBundle"/></th>
                </tr>
                <tr>
                    <td class="cardClaimType">
                        <bean:message key="label.selectedOffice.address" bundle="sbnkdBundle"/>
                    </td>
                    <td class="claimDetail">
                        <c:set var="cardOffice" value="${claim.contractCreditCardOffice}"/>
                        ${fn:substring(cardOffice, fn:indexOf(cardOffice, ",") + 1, fn:length(cardOffice))}
                        ${fn:substring(cardOffice, 0, fn:indexOf(cardOffice, ","))}
                    </td>
                </tr>
            </table>
        </div>
    </html:form>
</body>
</html:html>
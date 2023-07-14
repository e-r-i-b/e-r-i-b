<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/sbnkd.js" charset="UTF-8"></script>
<div class="page_content">

    <%--Заказ дебетовых карт--%>
    <div class="title_common head_title titleNoDesc"><bean:message key="label.title" bundle="sbnkdBundle"/></div>

    <%--Выбранные карты--%>
    <div class="viewData">
        <div class="title_common subtitle_1_level"><bean:message key="view.cards.title" bundle="sbnkdBundle"/></div>
        <c:forEach var="cardInfo" items="${claim.cardInfos}" varStatus="lineInfo">
            <div class="view_field_data">
                <div class="addedCard viewCardClaimsBlock">
                    <div class="leftCardClaimData">
                        <span class="checkData">
                            <c:out value="${cardInfo.cardName}"/>,
                            <bean:message key="field.currency.${cardInfo.contractCurrency}.smallFirst" bundle="sbnkdBundle"/>
                        </span>

                        <c:if test="${lineInfo.first}">
                            <c:set var="mobileBankTariff" value="${cardInfo.MBCContractType  == '0' ? 'full' : 'econom'}"/>
                            <p class="descCardData">
                                <c:out value="${claim.personFirstName}"/>  <c:out value="${claim.personMiddleName}"/>    ${fn:substring(claim.personLastName, 0, 1)}.
                            </p>
                        </c:if>
                    </div>

                    <c:set var="cardStateClass" value="cardClaimState"/>
                    <c:if test="${cardInfo.status == 'ERROR'}"><c:set var="cardStateClass" value="cardClaimState cardClaimErrorState"/></c:if>
                    <div class="${cardStateClass}">
                        <bean:message key="label.cardState.${cardInfo.status}" bundle="sbnkdBundle"/>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </c:forEach>
    </div>

    <%--Ваши персональные данные--%>
    <div class="viewData">
        <div class="title_common subtitle_1_level"><bean:message key="label.selectedYourPersonalData" bundle="sbnkdBundle"/></div>
        <div class="view_field_data">
            <div class="title_common subtitle_2_level subtitle_2_view"><bean:message key="label.certifyingDocument" bundle="sbnkdBundle"/></div>
            <p class="service_data">
                <c:if test="${!(claim.identityCardType == null || claim.identityCardType == '')}"><bean:message key="document.type.${phiz:getClientDocumentType(claim.identityCardType)}" bundle="userprofileBundle"/>;</c:if>
                <c:set var="seriesAndNumber">${claim.identityCardSeries} ${claim.identityCardNumber}</c:set>
                <bean:message key="mask.docNumber" bundle="sbnkdBundle"/> ${phiz:maskSeriesAndNumber(seriesAndNumber)}
                <c:if test="${!(claim.identityCardIssuedCode == null || claim.identityCardIssuedCode == '')}">;<bean:message key="field.officeCode" bundle="sbnkdBundle"/> ${claim.identityCardIssuedCode}</c:if>
            </p>

            <div class="orderseparate orderseparate_view"></div>

            <c:if test="${claim.guest}">
                <c:if test="${not empty claim.address[1]}">
                    <div class="title_common subtitle_2_level subtitle_2_view"><bean:message key="label.registrationAddress.title" bundle="sbnkdBundle"/></div>
                    <p class="service_data">
                        <c:set var="address" value="${claim.address[1]}"/>
                        <c:if test="${address.postalCode != null && address.postalCode != ''}">
                            <bean:message key="field.index" bundle="sbnkdBundle"/> ${address.postalCode},
                        </c:if>
                        <c:if test="${address.region != null && address.region != ''}">
                            ${address.region},
                        </c:if>
                        <c:if test="${address.city != null && address.city != ''}">
                            г. ${address.city},
                        </c:if>
                            ${form.maskedAfterCityRegAddr}
                    </p>
                </c:if>
                <c:if test="${not empty claim.address[0]}">
                    <div class="title_common subtitle_2_level subtitle_2_view"><bean:message key="label.residentialAddress.title" bundle="sbnkdBundle"/></div>
                    <p class="service_data">
                        <c:set var="address" value="${claim.address[0]}"/>
                        <c:if test="${address.postalCode != null && address.postalCode != ''}">
                            <bean:message key="field.index" bundle="sbnkdBundle"/> ${address.postalCode},
                        </c:if>
                        <c:if test="${address.region != null && address.region != ''}">
                            ${address.region},
                        </c:if>
                        <c:if test="${address.city != null && address.city != ''}">
                            г. ${address.city},
                        </c:if>
                            ${form.maskedAfterCityResAddr}
                    </p>
                </c:if>
            </c:if>
        </div>
    </div>

    <%--Подключенные услуги--%>
    <div class="viewData">
        <div class="title_common subtitle_1_level"><bean:message key="label.selectedServices" bundle="sbnkdBundle"/></div>
        <div class="view_field_data">
            <div class="title_common subtitle_2_level subtitle_2_view"><bean:message key="label.mobileBankService" bundle="sbnkdBundle"/></div>
            <p class="service_data">
                <bean:message key="label.${mobileBankTariff}Tariff.title" bundle="sbnkdBundle"/>
            </p>
            <c:if test="${claim.cardAcctAutoPayInfo != '' && claim.cardAcctAutoPayInfo != '0.0'}">
                <div class="orderseparate orderseparate_view"></div>
                <div class="title_common subtitle_2_level subtitle_2_view"><bean:message key="label.autopayments.title" bundle="sbnkdBundle"/></div>
                <p class="service_data">
                    <bean:message key="label.phoneNumber" bundle="sbnkdBundle"/> +7 ${phiz:getCutPhoneForAddressBook(claim.phone)};
                    <bean:message key="label.sum" bundle="sbnkdBundle"/> ${claim.cardAcctAutoPayInfo} <bean:message key="label.of.RUB" bundle="sbnkdBundle"/>;
                    <bean:message key="label.minBalanceForPayment" bundle="sbnkdBundle"/> ${claim.balanceLessThan} <bean:message key="label.of.RUB" bundle="sbnkdBundle"/>
                </p>
            </c:if>
        </div>
    </div>

    <%--Отделение выдачи карты--%>
    <div class="viewData">
        <div class="title_common subtitle_1_level"><bean:message key="label.selectedOffice" bundle="sbnkdBundle"/></div>
        <div class="view_field_data">
            <c:set var="cardOffice" value="${claim.contractCreditCardOffice}"/>
            <div class="title_common subtitle_2_level subtitle_2_view">${fn:substring(cardOffice, fn:indexOf(cardOffice, ",") + 1, fn:length(cardOffice))}</div>
            <div class="selectedOfficeTxt">${fn:substring(cardOffice, 0, fn:indexOf(cardOffice, ","))}</div>
        </div>
    </div>

    <div class="printLeftBtn margin_top_60">
        <script type="text/javascript">
            function openPrintClaimInfo()
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/sberbankForEveryDay/printViewClaim')}?id=${form.id}";
                openWindow(null, url, "printViewClaim", "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=1024");
            }
        </script>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.print"/>
            <tiles:put name="commandHelpKey" value="button.print"/>
            <tiles:put name="bundle" value="sbnkdBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="onclick">openPrintClaimInfo();</tiles:put>
            <tiles:put name="image" value="print-check.gif"/>
            <tiles:put name="imageHover" value="print-check-hover.gif"/>
            <tiles:put name="imagePosition" value="left"/>
        </tiles:insert>
    </div>
    <div class="orderseparate orderseparate40"></div>
    <c:choose>
        <c:when test="${isGuest}">
            <c:set var="url" value="/guest/index.do"/>
        </c:when>
        <c:otherwise>
            <c:set var="url" value="/private/accounts.do"/>
        </c:otherwise>
    </c:choose>
    <div class="float">
        <span id="backToServicesButton">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.backToMain"/>
                <tiles:put name="commandHelpKey" value="button.backToMain"/>
                <tiles:put name="bundle"         value="sbnkdBundle"/>
                <tiles:put name="viewType"       value="darkGrayButton"/>
                <tiles:put name="action"         value="${url}"/>
                <tiles:put name="image"          value="back-to-catalog.png"/>
                <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                <tiles:put name="imagePosition"  value="left"/>
            </tiles:insert>
        </span>
    </div>
</div>
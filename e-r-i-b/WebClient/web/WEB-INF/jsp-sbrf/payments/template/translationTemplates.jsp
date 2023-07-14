<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<c:set var="fromFinanceCalendar" value="${frm.fromFinanceCalendar}"/>
<c:set var="extractId" value="${frm.extractId}"/>
<c:url var="paymentLink" value="/private/payments/template.do"/>
<c:url var="jurPaymentLink" value="/private/template/jurPayment/edit.do"/>
<c:if test="${fromFinanceCalendar}">
    <c:set var="paymentLink" value="${paymentLink}?fromFinanceCalendar=${fromFinanceCalendar}&extractId=${extractId}"/>
    <c:set var="jurPaymentLink" value="${jurPaymentLink}?fromFinanceCalendar=${fromFinanceCalendar}&extractId=${extractId}"/>
</c:if>
<c:set var="showInternalPayment" value="${phiz:impliesServiceRigid('InternalPayment')}"/>
<c:set var="showLoanPayment" value="${phiz:impliesServiceRigid('LoanPayment')}"/>
<c:set var="showConvertCurrencyPayment" value="${phiz:impliesServiceRigid('ConvertCurrencyPayment')}"/>
<c:set var="showIMAPayment" value="${phiz:impliesServiceRigid('IMAPayment')}"/>
<c:set var="showRurPayment" value="${phiz:impliesServiceRigid('RurPayment')}"/>
<c:set var="showJurPayment" value="${phiz:impliesServiceRigid('JurPayment')}"/>
<c:set var="showNewRurPayment" value="${phiz:impliesServiceRigid('NewRurPayment')}"/>

<c:if test="${showInternalPayment || showLoanPayment || showConvertCurrencyPayment || showIMAPayment || showRurPayment || showJurPayment}">
    <div class="translationsCategories">
        <div class="categoriesTitle">Переводы</div>
        <div class="clear"></div>
        <div>
            <c:if test="${showInternalPayment || showLoanPayment || showConvertCurrencyPayment || showIMAPayment}">
                <div class="paymentsTitles firstPaymentsTitles">
                    <tiles:insert definition="paymentTemplate">
                        <tiles:put name="title" value="<span>Перевод между своими счетами и картами</span>"/>
                        <tiles:put name="link" value="${paymentLink}"/>
                        <tiles:put name="image" value="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_InternalPayment48.jpg"/>
                        <tiles:put name="className" value="categoryTitle paymentLinkWithImage"/>
                        <tiles:put name="serviceName" value="InternalPayment"/>
                        <tiles:put name="showService" value="${showInternalPayment}"/>
                    </tiles:insert>
                    <div class="paymentLinksUnderTitle">
                        <tiles:insert definition="paymentTemplate">
                            <tiles:put name="title" value="<span>Обмен валюты</span>"/>
                            <tiles:put name="link" value="${paymentLink}"/>
                            <tiles:put name="serviceName" value="ConvertCurrencyPayment"/>
                            <tiles:put name="showService" value="${showConvertCurrencyPayment}"/>
                        </tiles:insert>
                        <tiles:insert definition="paymentTemplate">
                            <tiles:put name="title" value="<span>Покупка и продажа металлов</span>"/>
                            <tiles:put name="link" value="${paymentLink}"/>
                            <tiles:put name="serviceName" value="IMAPayment"/>
                            <tiles:put name="showService" value="${showIMAPayment}"/>
                        </tiles:insert>
                    </div>
                    <tiles:insert definition="paymentTemplate">
                        <tiles:put name="title" value="<span>Перевод на карту<br/> в другом банке</span>"/>
                        <tiles:put name="link" value="${paymentLink}"/>
                        <tiles:put name="image" value="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_RurPayment48.jpg"/>
                        <tiles:put name="className" value="categoryTitle paymentLinkWithImage"/>
                        <tiles:put name="serviceName" value="RurPayment"/>
                        <tiles:put name="showService" value="${showInternalPayment and not showNewRurPayment}"/>
                    </tiles:insert>

                </div>
            </c:if>
            <div class="paymentsTitles">
                <c:if test="${showRurPayment and not showNewRurPayment}">
                    <div class="categoryTitle paymentLinkWithImage">
                        <phiz:link url="${paymentLink}">
                            <phiz:param name="form" value="RurPayment"/>
                            <phiz:param name="receiverSubType" value="ourCard"/>
                            <div class="categoryIconWight">
                                <img class="icon" src="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_RurPaymentSber48.jpg"/>
                            </div>
                            <div class="paymentLink">
                                <span>Перевод клиенту Сбербанка</span>
                            </div>
                        </phiz:link>
                        <div class="clear"></div>
                    </div>
                    <div class="categoryTitle paymentLinkWithImage">
                        <phiz:link url="${paymentLink}">
                            <phiz:param name="form" value="RurPayment"/>
                            <phiz:param name="receiverSubType" value="externalAccount"/>
                            <div class="categoryIconWight">
                                <img class="icon" src="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_RurPayment48.jpg"/>
                            </div>
                            <div class="paymentLink">
                                <span>Перевод частному лицу в другой банк по реквизитам</span>
                            </div>
                        </phiz:link>
                        <div class="clear"></div>
                    </div>
                </c:if>
                <c:if test="${showNewRurPayment}">
                    <div class="categoryTitle paymentLinkWithImage">
                        <phiz:link url="${paymentLink}">
                            <phiz:param name="form" value="NewRurPayment"/>
                            <phiz:param name="receiverSubType" value="ourCard"/>

                            <div class="categoryIconWight">
                                <img class="icon" src="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_RurPayment48.jpg"/>
                            </div>
                            <div class="paymentLink">
                                <span><bean:message bundle="paymentsBundle" key="paymentform.NewRurPayment"/></span>
                            </div>
                            <div class="clear"></div>
                        </phiz:link>
                        <div class="paymentDescription"><bean:message bundle="paymentsBundle" key="paymentform.NewRurPayment.description"/></div>
                    </div>
                </c:if>
                <c:if test="${showJurPayment}">
                    <div class="categoryTitle paymentLinkWithImage">
                        <phiz:link url="${jurPaymentLink}">
                            <div class="categoryIconWight">
                                <img class="icon" src="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_JurPayment48.jpg"/>
                            </div>
                            <div class="paymentLink">
                                <span>Перевод организации</span>
                            </div>
                        </phiz:link>
                        <div class="clear"></div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</c:if>
<div class="clear"></div>

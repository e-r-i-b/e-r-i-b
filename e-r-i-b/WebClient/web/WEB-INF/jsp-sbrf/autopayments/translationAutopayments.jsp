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
<c:url var="paymentLink" value="/private/payments/autopayment.do"/>
<div class="translationsCategories">
    <div class="categoriesTitle">Переводы</div>
    <div class="clear"></div>
    <div>
        <div class="paymentsTitles">
            <c:set var="allowedAutoPayment" value="${phiz:impliesService('CreateLongOfferPayment')}"/>
            <c:if test="${phiz:impliesServiceRigid('InternalPayment') && allowedAutoPayment}">
                <div class="categoryTitle paymentLinkWithImage">
                    <phiz:link url="${paymentLink}">
                        <phiz:param name="form" value="InternalPayment"/>

                        <img class="icon" src="${imagePathGlobal}/iconPmntList_InternalPayment.jpg">
                        <div class="paymentLink">
                            <span>Перевод между своими счетами и картами</span>
                        </div>
                    </phiz:link>
                </div>
            </c:if>

            <div class="paymentLinksUnderTitle">
                <c:if test="${phiz:impliesServiceRigid('LoanPayment') && allowedAutoPayment}">
                    <div>
                        <phiz:link url="${paymentLink}">
                            <phiz:param name="form" value="LoanPayment"/>
                            <div class="paymentLink">
                                <span>Погашение кредита в Сбербанке</span>
                            </div>
                        </phiz:link>
                    </div>
                </c:if>
                <c:if test="${phiz:impliesServiceRigid('ConvertCurrencyPayment') && allowedAutoPayment}">
                    <div>
                        <phiz:link url="${paymentLink}">
                            <phiz:param name="form" value="ConvertCurrencyPayment"/>
                            <div class="paymentLink">
                                <span>Обмен валюты</span>
                            </div>
                        </phiz:link>
                    </div>
                </c:if>
                <c:if test="${phiz:impliesServiceRigid('IMAPayment')}">
                    <div>
                        <phiz:link url="${paymentLink}">
                            <phiz:param name="form" value="IMAPayment"/>
                            <div class="paymentLink">
                                <span>Покупка и продажа металлов</span>
                            </div>
                        </phiz:link>
                    </div>
                </c:if>
            </div>
        </div>

        <div class="paymentsTitles">
            <c:if test="${phiz:impliesServiceRigid('RurPayment') && phiz:impliesService('CreateLongOfferPaymentForRur')}">
                <div class="categoryTitle paymentLinkWithImage">
                    <phiz:link url="${paymentLink}">
                        <phiz:param name="form" value="RurPayment"/>

                        <img class="icon" src="${imagePathGlobal}/iconPmntList_RurPayment.jpg">
                        <div class="paymentLink">
                            <span>Перевод клиенту Сбербанка</span>
                        </div>
                    </phiz:link>
                </div>
                <div class="categoryTitle paymentLinkWithImage">
                    <phiz:link url="${paymentLink}">
                        <phiz:param name="form" value="RurPayment"/>
                        <phiz:param name="receiverSubType" value="externalAccount"/>

                        <img class="icon" src="${imagePathGlobal}/iconPmntList_RurPayment.jpg">
                        <div class="paymentLink">
                            <span>Перевод частному лицу в другой банк</span>
                        </div>
                    </phiz:link>
                </div>
            </c:if>
        </div>
    </div>
</div>
<div class="clear"></div>

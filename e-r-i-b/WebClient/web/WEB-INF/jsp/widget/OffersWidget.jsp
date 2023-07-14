<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="hasRegularPassportRF" value="${phiz:hasRegularPassportRF()}"/>
<c:set var="isShowBankOffersOnMain" value="${phiz:isShowBankOffersOnMain()}"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isLoanOffer" value="${not empty form.loanOffers}"/>
<c:set var="isLoanCardOffer" value="${not empty form.loanCardOffers}"/>
<c:set var="hasAndShow" value="${hasRegularPassportRF and isShowBankOffersOnMain and (isLoanOffer or isLoanCardOffer)}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.OffersWidget"/>
    <tiles:put name="cssClassname" value="OffersWidget"/>
    <tiles:put name="borderColor" value="greenTop"/>

    <c:if test="${hasAndShow}">
        <tiles:put name="linksControl">
            <c:set var="allLoanOfferUrl">${phiz:calculateActionURL(pageContext,"/private/loan/loanoffer/show.do")}</c:set>
            <a href="${allLoanOfferUrl}" title="К списку">все предложения</a>
        </tiles:put>
    </c:if>

    <c:set var="content">
        <c:choose>
            <c:when test="${hasAndShow}">
                <c:catch var="error">
                    <tiles:insert definition="loanOfferMultiColumn" flush="false">
                        <tiles:put name="loanOffers" beanName="form" beanProperty="loanOffers"/>
                        <tiles:put name="loanCardOffers" beanName="form" beanProperty="loanCardOffers"/>
                    </tiles:insert>
                </c:catch>
                <c:if test="${not empty error}">
                    ${phiz:setException(error, pageContext)}
                </c:if>
            </c:when>
            <c:otherwise>
                <div id="payments">
                    <div class="background-none">
                        <tiles:insert definition="paymentsPaymentCardsDiv" service="LoanCardProduct" operation="LoanOfferListOperation" flush="false">
                            <tiles:put name="serviceId" value="LoanCardProduct"/>
                            <tiles:put name="image" value="card_64.png"/>
                            <tiles:put name="action" value="/private/payments/income_level"/>
                            <tiles:put name="description" value="Для клиентов, не имеющих кредитных карт Сбербанка."/>
                        </tiles:insert>
                    </div>
                    <div class="background-none">
                        <tiles:insert definition="paymentsPaymentCardsDiv" service="LoanProduct" operation="LoanProductListOperation" flush="false">
                            <tiles:put name="serviceId" value="LoanProduct"/>
                            <tiles:put name="image" value="credit_64.png"/>
                            <tiles:put name="action" value="/private/payments/loan_product"/>
                        </tiles:insert>
                        <div class="clear"></div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </c:set>

    <tiles:put name="viewPanel">
        ${content}
    </tiles:put>

    <tiles:put name="editPanel">
        <%-- Та часть виджета, которая будет скроллиться в режиме редактирования--%>
        ${content}
    </tiles:put>
    <tiles:put name="additionalSetting">
        <div class="pagination">
            <h2>Количество записей:</h2>
            <table cellspacing="0" cellpadding="0" class="tblNumRec EventWidget">
                <tr>
                    <td><div button="numberOfItems3"><span class="paginationSize">3</span></div></td>
                    <td><div button="numberOfItems6"><span class="paginationSize">6</span></div></td>
                    <td><div button="numberOfItems12"><span class="paginationSize">12</span></div></td>
                </tr>
            </table>
        </div>
    </tiles:put>

</tiles:insert>

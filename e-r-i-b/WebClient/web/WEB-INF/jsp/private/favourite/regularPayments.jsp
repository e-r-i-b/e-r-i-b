<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${ListFavouriteForm}"/>
<c:set var="longOfferLinks" value="${form.longOfferLinks}"/>
<c:set var="defaultCollectionSize" value="10"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm17')}"/>
<c:if test="${empty fullList}">
    <c:set var="fullList" value="true"/>
</c:if>

<tiles:insert definition="simpleTableTemplate" flush="false">
    <c:set var="longOfferInfoLink"
           value="${phiz:calculateActionURL(pageContext, '/private/longOffers/info?id=')}"/>
    <tiles:put name="grid">
        <c:choose>
            <c:when test="${fullList}">
                <c:set var="model" value="simple-pagination"/>
                <c:set var="collectionSize" value="20"/>
                <c:set var="helpLink" value=""/>
                <c:set var="onclick"
                       value="ajaxTableList('RegularPayments', '%2$s', %1$d); return false;"/>
                <c:set var="description">
                    На этой странице Вы можете просмотреть выбранный регулярный платеж, для этого щелкните по его названию.
                    Если Вы хотите вернуться на страницу «<bean:message key="label.mainMenu.payments" bundle="commonBundle"/>», то нажмите на кнопку «Отменить».
                </c:set>
            </c:when>
            <c:otherwise>
                <c:set var="model" value="no-pagination"/>
                <c:set var="collectionSize" value="${defaultCollectionSize}"/>
                <c:set var="description">
                    Здесь отображается 10 регулярных платежей, созданных Вами в системе.
                    Чтобы просмотреть остальные регулярные платежи, нажмите на ссылку «Показать все регулярные платежи».
                </c:set>
                <c:set var="helpLink">
                    <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="javascript:openFAQ('${faqLink}');"></a>
                </c:set>
                <div class="regular-close" id="regularPaymentsDiv"
                     onclick="showHideElement(this); return false;">
                    cвернуть
                </div>
            </c:otherwise>
        </c:choose>
        <h1>Регулярные платежи</h1>
		${helpLink}
        <c:if test="${phiz:size(longOfferLinks) gt defaultCollectionSize}">
            <br/>${description}
        </c:if>
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="grid">
                <sl:collection id="longOfferLink" model="${model}" name="longOfferLinks" onClick="${onclick}"
                               collectionSize="${collectionSize}">
                    <sl:collectionItem styleClass="bold">
                        <a href='${longOfferInfoLink}<bean:write name="longOfferLink" property="id" ignore="true"/>'>
                            <bean:write name="longOfferLink" property="name" ignore="true"/>
                        </a>
                    </sl:collectionItem>
                    <sl:collectionItem>
                        <bean:write name="longOfferLink" property="executionEventType" ignore="true"/>
                    </sl:collectionItem>
                    <sl:collectionItem styleClass="bold align-right">
                        <c:set var="longOffer" value="${longOfferLink.value}"/>
                        <c:if test="${not empty longOffer.amount}">
                            <c:set var="currency" value="${longOffer.amount.currency.code}"/>
                            <bean:write name="longOffer" property="amount.decimal" ignore="true"/>
                            <c:choose>
                                <c:when test="${currency != 'RUB'}">
                                    ${currency}
                                </c:when>
                                <c:otherwise>
                                    р.
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </sl:collectionItem>
                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<c:if test="${phiz:size(longOfferLinks) gt defaultCollectionSize}">
    <c:choose>
        <c:when test="${fullList}">
            <div class="buttonArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close.help"/>
                    <tiles:put name="bundle" value="favouriteBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick">win.close('showAllRegularPayments');</tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.showAllRegularPayments"/>
                <tiles:put name="commandHelpKey" value="button.showAllRegularPayments.help"/>
                <tiles:put name="bundle" value="favouriteBundle"/>
                <tiles:put name="viewType" value="boldLink"/>
                <tiles:put name="onclick">win.open('showAllRegularPayments');</tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:if>
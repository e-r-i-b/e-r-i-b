<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>


<tiles:importAttribute/>
<c:set var="form" value="${ListFavouriteForm}"/>
<c:set var="personalPaymentProviders" value="${form.personalPaymentProviders}"/>
<c:set var="defaultCollectionSize" value="10"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:if test="${empty fullList}">
    <c:set var="fullList" value="true"/>
</c:if>

<tiles:insert definition="simpleTableTemplate" flush="false">
<%-- На макете этого нет
    <tiles:put name="hideable" value="true"/>
    <tiles:put name="regular" value="true"/>
--%>
    <tiles:put name="grid">
        <c:choose>
            <c:when test="${fullList}">
                <c:set var="model" value="simple-pagination"/>
                <c:set var="collectionSize" value="20"/>
                <c:set var="helpLink" value=""/>
                <c:set var="onclick"
                       value="ajaxTableList('PersonalPayments', '%2$s', %1$d); return false;"/>
                <c:set var="description">
                    На этой странице Вы можете просмотреть выбранную организацию, для этого щелкните по ее названию.
                    Если Вы хотите вернуться на страницу «<bean:message key="label.mainMenu.payments" bundle="commonBundle"/>», то нажмите на кнопку «Отменить».
                </c:set>
            </c:when>
            <c:otherwise>
                <c:set var="model" value="no-pagination"/>
                <c:set var="collectionSize" value="${defaultCollectionSize}"/>
                <c:set var="helpLink">
                    <a class="imgHintBlock" title="Часто задаваемые вопросы"></a>
                </c:set>
                <c:set var="description">
                    Здесь отображается 10 выбранных Вами организаций, в адрес которых можно оплачивать услуги.
                    Чтобы просмотреть остальные организации, нажмите на ссылку «Показать все персональные платежи».
                </c:set>
<%--       На макете этого нет
                <div class="regular-close" id="personalPaymentsDiv"
                     onclick="showHideElement(this); return false;">cвернуть
                </div>
--%>
            </c:otherwise>
        </c:choose>
        <h1>Персональные платежи</h1>
        ${helpLink}
        <c:if test="${phiz:size(personalPaymentProviders) gt defaultCollectionSize}">
            <br/>${description}
        </c:if>
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="grid">
                <sl:collection id="prov" model="${model}" name="personalPaymentProviders" onClick="${onclick}"
                               collectionSize="${collectionSize}">
                    <sl:collectionItem styleClass="align-left">
                        <c:url var="link" value="/private/payments/payment.do">
                            <c:param name="provider" value="${prov.id}"/>
                            <c:param name="personal" value="1"/>
                        </c:url>
                        <html:link href="${link}">
                            <bean:write name="prov" property="name" ignore="true"/>
                        </html:link>
                    </sl:collectionItem>
                </sl:collection>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<c:if test="${phiz:size(personalPaymentProviders) gt defaultCollectionSize}">
    <c:choose>
        <c:when test="${fullList}">
            <div class="buttonArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close.help"/>
                    <tiles:put name="bundle" value="favouriteBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick">win.close('showAllPersonalPayments');</tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.showAllPersonalPayments"/>
                <tiles:put name="commandHelpKey" value="button.showAllPersonalPayments.help"/>
                <tiles:put name="bundle" value="favouriteBundle"/>
                <tiles:put name="viewType" value="boldLink"/>
                <tiles:put name="onclick">win.open('showAllPersonalPayments');</tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:if>   
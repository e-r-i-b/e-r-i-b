<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/payments/view" onsubmit="return setEmptyAction(event)">
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="listFormName" value="${form.metadata.listFormName}"/>
    <c:set var="paymentFormName" value="${form.metadata.listFormName}" scope="request"/>
    <c:set var="metadataPath" value="${form.metadataPath}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="document" value="${form.document}"/>
    <c:set var="state" value="${document.state.code}"/>
    <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q13')}"/>
    <c:set var="mode" value="${phiz:getUserVisitingMode()}"/>

    <tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value="Payments" />
        <tiles:put name="submenu" type="string" value="${listFormName}"/>
        <%-- заголовок --%>
        <tiles:put name="pageTitle"><span class="size24">${form.title}</span></tiles:put>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="${metadata.form.name}"/>
                <tiles:put name="title">
                    <span class="size24">Создание подписки на инвойсы</span>
                </tiles:put>
                <tiles:put name="description">
                    На этой странице Вы можете посмотреть статус платежа. Также отследить ход выполнения операции можно в &laquo;Истории операций&raquo;.
                </tiles:put>
                <tiles:put name="stripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="выбор операции"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="заполнение заявки"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="подтверждение"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="статус заявки"/>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="name" value="${metadata.form.description}"/>
                <tiles:put name="docDate" value="${form.dateString}"/>
                <tiles:put name="isServicePayment" value="true"/>

                <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.document.receiverInternalId)}"/>
                <c:if test="${not empty serviceProvider}">
                    <%-- RurPayJurSb наследник JurPayment, значит здесь достпуен поставщик услуг --%>
                    <tiles:put name="imageId" value="${serviceProvider.imageId}"/>
                </c:if>

                <tiles:put name="data">
                    <div>
                        ${form.html}
                    </div>
                </tiles:put>

                <c:choose>
                    <c:when test="${state=='DELAYED_DISPATCH' || state=='OFFLINE_DELAYED'}">
                        <tiles:put name="stamp" value="delayed"/>
                    </c:when>
                    <c:when test="${state=='DISPATCHED' && isShortLoanClaim}">
                        <tiles:put name="stamp" value="processed"/>
                    </c:when>
                    <c:when test="${state=='DISPATCHED' || state=='STATEMENT_READY' || state=='UNKNOW' || state=='SENT' || state=='BILLING_CONFIRM_TIMEOUT'
                                                    || state=='BILLING_GATE_CONFIRM_TIMEOUT' || state=='ABS_RECALL_TIMEOUT' || state=='ABS_GATE_RECALL_TIMEOUT'}">
                        <tiles:put name="stamp" value="received"/>
                    </c:when>
                    <c:when test="${state == 'ADOPTED'}">
                        <tiles:put name="stamp" value="accepted"/>
                    </c:when>
                    <c:when test="${state=='EXECUTED' || state=='SUCCESSED' || state=='TICKETS_WAITING'}">
                        <tiles:put name="stamp" value="executed"/>
                    </c:when>
                    <c:when test="${state=='REFUSED'}">
                        <tiles:put name="stamp" value="refused"/>
                    </c:when>
                    <c:when test="${state=='WAIT_CONFIRM'}">
                        <tiles:put name="stamp" value="waitconfirm"/>
                    </c:when>
                </c:choose>
                <tiles:put name="bankBIC" value="${form.bankBIC}"/>
                <tiles:put name="bankName" value="${form.bankName}"/>
            </tiles:insert>

            <c:if test="${not empty state}">
                <c:set var="stateDescr">
                    <c:choose>
                        <c:when test="${state=='UNKNOW' or state=='SENT'}">${document.defaultStateDescription}</c:when>
                        <c:otherwise><bean:message key="payment.state.hint.${state}" bundle="paymentsBundle"/></c:otherwise>
                    </c:choose>
                </c:set>
                <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');" onmouseout="hideLayer('stateDescription');" class="layerFon stateDescription">
                    <div class="floatMessageHeader"></div>
                    <div class="layerFonBlock">
                        ${stateDescr}
                    </div>
                </div>
            </c:if>
            <c:set var="additionalMessage" value="${phiz:removeSessionAttribute('com.rssl.phizic.web.actions.SESSION_ADDITIONAL_MESSAGE_KEY')}"/>
            <script type="text/javascript">
                addAdditionalMessage('${additionalMessage}');
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
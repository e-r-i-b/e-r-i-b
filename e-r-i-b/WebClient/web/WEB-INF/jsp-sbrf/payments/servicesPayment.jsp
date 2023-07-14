<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/payments/servicesPayments/edit" >
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var ="isLongOffer" value="${form.document.longOffer || form.createLongOffer}"/>
<c:set var="serviceProvider" value="${phiz:getServiceProvider(form.recipient)}"/>
<c:set var="serviceProviderName" value="${serviceProvider.name}"/>
<c:set var="faqTemplate" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q13')}"/>
<c:set var="faqLongOffer" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm18')}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="paramsCategory" value=""/>
<c:if test="${not empty form.category}">
   <c:set var="paramsCategory" value="categoryId=${form.category}&"/>
</c:if>

<tiles:insert definition="paymentCurrent">
    <c:choose>
        <c:when test="${isLongOffer}">
            <tiles:put name="mainmenu" value=""/>
        </c:when>
        <c:otherwise>
            <tiles:put name="mainmenu" value="Payments"/>
        </c:otherwise>
    </c:choose>
<tiles:put name="breadcrumbs">
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="main" value="true"/>
        <tiles:put name="action" value="/private/accounts.do"/>
    </tiles:insert>

    <c:choose>
        <c:when test="${isLongOffer}">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мои автоплатежи"/>
                <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Подключение автоплатежа"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="action" value="/private/payments.do"/>
            </tiles:insert>
            <%--Ссылка на категорию услуги--%>
            <c:if test="${not empty form.category}">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name">Оплата: <bean:message key="category.operations.${form.category}" bundle="paymentServicesBundle"/></tiles:put>
                    <tiles:put name="action" value="/private/payments/category.do?categoryId=${form.category}"/>
                    <tiles:put name="last" value="false"/>
                </tiles:insert>
            </c:if>
            <%--Ссылка на услугу поставщика--%>
            <c:if test="${not empty form.service && form.service != 0}">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="name">Оплата: ${paymentService.name}</tiles:put>
                    <tiles:put name="action" value="/private/payments/servicesPayments.do?${paramsCategory}serviceId=${form.service}"/>
                    <tiles:put name="last" value="false"/>
                </tiles:insert>
            </c:if>

            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" >Оплата: ${serviceProviderName}</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</tiles:put>
<tiles:put name="data" type="string">
     <c:choose>
        <c:when test="${isLongOffer}">
            <c:set var="title">
                <span class="size24">Подключение автоплатежа</span>
            </c:set>
            <c:set var="linkBackToServicesButton" value="private/autopayment/select-category-provider"/>
            <c:set var="nextCommand" value="button.nextLongOffer"/>
        </c:when>
        <c:otherwise>
            <c:set var="title">
                <span class="size24">Оплата: ${serviceProviderName}</span>
            </c:set>
            <c:set var="linkBackToServicesButton" value="/private/payments"/>
            <c:set var="nextCommand" value="button.next"/>
        </c:otherwise>
    </c:choose>
    <html:hidden property="operationUID"/>
    <html:hidden property="originalPaymentId"/>
    <html:hidden property="createLongOffer"/>
    <html:hidden name="form" property="template"/>
    <tiles:insert page="paymentContext.jsp" flush="false"/>
    <div id="payment" onkeypress="onEnterKey(event)">
        <c:if test="${form.document != null}">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="${title}"/>
            <tiles:put name="data" type="string">
                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/servicesPaymentData.jsp" flush="false">
                    <tiles:put name="header" type="string">
                        <%@ include file="header.jsp" %>
                    </tiles:put>
                    <tiles:put name="stripe" type="string">
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="выбор услуги"/>
                            <tiles:put name="future" value="false"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="заполнение реквизитов"/>
                            <tiles:put name="current" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="подтверждение"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="статус операции"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="enableRegions" value="${true}"/>
                    <tiles:put name="fieldDisabled" value="${form.document.byTemplate && phiz:getEditMode(form.recipient) == 'static'}"/>
                    <tiles:put name="templateId" value="${form.template}"/>
                    <tiles:put name="paymentFieldsHtml">
                        <tiles:insert page="servicePaymentFields.jsp" flush="false">
                            <tiles:put name="noProviderMessage">
                                Не найдено ни одного поставщика, соответствующего заданному региону!
                            </tiles:put>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="backToServicesButton" type="string">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="${linkBackToServicesButton}"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="cancelButton" type="string">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="/private/payments"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="removeButton" type="string">
                        <c:if test="${not empty param.history and param.history eq 'true' and not empty form.document.id}">
                            <tiles:insert definition="confirmationButton" flush="false">
                                <tiles:put name="winId" value="confirmation"/>
                                <tiles:put name="title" value="Подтверждение удаления документа"/>
                                <tiles:put name="currentBundle"  value="commonBundle"/>
                                <tiles:put name="confirmCommandKey" value="button.remove"/>
                                <tiles:put name="buttonViewType" value="buttonGrey"/>
                                <tiles:put name="message"><bean:message key="confirm.text" bundle="paymentsBundle"/></tiles:put>
                            </tiles:insert>
                        </c:if>
                    </tiles:put>
                    <tiles:put name="nextButton" type="string">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="${nextCommand}"/>
                            <tiles:put name="commandTextKey" value="${nextCommand}"/>
                            <tiles:put name="commandHelpKey" value="${nextCommand}"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="validationFunction" value="checkRecipient();"/>
                            <tiles:put name="isDefault" value="true"/>
                            <c:if test="${not empty sessionScope['fromBanner']}">
                                <tiles:put name="fromBanner" value="${sessionScope['fromBanner']}"/>
                            </c:if>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
        </c:if>
    </div>
    <script type="text/javascript">
        function checkRecipient()
        {
            var recipientId = $("#recipientId");
            if ($(recipientId).val() == undefined || $(recipientId).val() == "")
            {
                addError("Введите значение в поле Выберите услугу");
                payInput.fieldError("recipient");
                return false;
            }
            return true;
        }
        $(document).ready(function(){initialize(); initPaymentTabIndex();});
    </script>
</tiles:put>
</tiles:insert>

</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/template/services-payments/edit" >

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
    <c:set var="paramsCategory" value=""/>
    <c:set var="fromFinanceCalendar" value="${form.fromFinanceCalendar}"/>
    <c:set var="extractId" value="${form.extractId}"/>

    <c:if test="${not empty form.category}">
       <c:set var="paramsCategory" value="categoryId=${form.category}&"/>
    </c:if>

    <tiles:insert definition="paymentCurrent">
    <tiles:put name="mainmenu" value=""/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="ћои шаблоны"/>
            <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="—оздание шаблона"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <c:set var="title"><span class="size24">—оздание шаблона</span></c:set>

        <html:hidden property="operationUID"/>
        <html:hidden name="form" property="templateId" />
        <html:hidden property="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
        <html:hidden property="extractId" value="${extractId}"/>

        <tiles:insert page="../paymentContext.jsp" flush="false"/>

        <div id="payment" onkeypress="onEnterKey(event)">
            <c:if test="${form.template != null}">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title"><span class="size24">${title}</span></tiles:put>
                <tiles:put name="data" type="string">
                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/servicesPaymentData.jsp" flush="false">
                        <tiles:put name="header" type="page" value="/WEB-INF/jsp-sbrf/payments/template/header.jsp"/>
                        <tiles:put name="stripe" type="string">
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="выбор операции"/>
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
                        <tiles:put name="fieldDisabled" value="${false}"/>
                        <tiles:put name="templateId" value="${form.templateId}"/>
                        <tiles:put name="paymentFieldsHtml">
                            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/template/template-service-payment-fields.jsp" flush="false">
                                <tiles:put name="noProviderMessage">
                                    Ќе найдено ни одного поставщика, соответствующего заданному региону!
                                </tiles:put>
                            </tiles:insert>
                        </tiles:put>

                        <c:choose>
                            <c:when test="${fromFinanceCalendar}">
                                <c:set var="action" value="/private/finances/financeCalendar.do?fromFinanceCalendar=true&extractId=${extractId}"/>
                                <c:set var="selectServiceAction" value="/private/template/select-category.do?fromFinanceCalendar=true&extractId=${extractId}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="action" value="/private/payments"/>
                                <c:set var="selectServiceAction" value="/private/payments"/>
                            </c:otherwise>
                        </c:choose>

                        <tiles:put name="backToServicesButton" type="string">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.goto.select.service"/>
                                <tiles:put name="commandHelpKey"    value="button.goto.select.service"/>
                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                <tiles:put name="viewType"          value="blueGrayLink"/>
                                <tiles:put name="action"            value="${selectServiceAction}"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="cancelButton" type="string">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.cancel"/>
                                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                <tiles:put name="viewType"          value="buttonGrey"/>
                                <tiles:put name="action"            value="${action}"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="removeButton" type="string">
                            <c:if test="${not empty param.history and param.history eq 'true' and not empty form.template.id}">
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey"     value="button.remove"/>
                                    <tiles:put name="commandHelpKey" value="button.remove"/>
                                    <tiles:put name="bundle"         value="paymentsBundle"/>
                                    <tiles:put name="viewType"       value="buttonGrey"/>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                        <tiles:put name="nextButton" type="string">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"        value="button.saveastemplate"/>
                                <tiles:put name="commandTextKey"    value="button.exit"/>
                                <tiles:put name="commandHelpKey"    value="button.exit"/>
                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                <tiles:put name="stateObject"       value="template"/>
                                <tiles:put name="isDefault"         value="true"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            </c:if>
        </div>
        <script type="text/javascript">
            doOnLoad( function() {initialize();} );
        </script>
    </tiles:put>
    </tiles:insert>

</html:form>

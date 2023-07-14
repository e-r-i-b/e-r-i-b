<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/private/userprofile/basket/subscription/create">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentsBasket">
        <tiles:put name="data" type="string">
            <c:set var="title">
                <span class="size24">Ќастройка автопоиска счетов по услуге</span>
            </c:set>
            <html:hidden property="operationUID"/>
            <html:hidden name="form" property="template"/>
            <div id="payment" onkeypress="onEnterKey(event);">
                <c:if test="${form.document != null}">
                    <tiles:insert definition="profileTemplate" flush="false">
                        <tiles:put name="activeItem" value="searchAccounts"/>
                        <tiles:put name="title" value="${title}"/>
                        <tiles:put name="data" type="string">
                            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/servicesPaymentData.jsp" flush="false">
                                <tiles:put name="header" type="string" value=""/>
                                <tiles:put name="isUseProfileDocuments" type="string" value="true"/>
                                <tiles:put name="stripe" type="string">
                                    <tiles:insert definition="stripe" flush="false">
                                        <tiles:put name="name" value="выбор услуги"/>
                                        <tiles:put name="width" value="230px"/>
                                        <tiles:put name="future" value="false"/>
                                    </tiles:insert>
                                    <tiles:insert definition="stripe" flush="false">
                                        <tiles:put name="name" value="заполнение формы"/>
                                        <tiles:put name="width" value="230px"/>
                                        <tiles:put name="current" value="true"/>
                                    </tiles:insert>
                                    <tiles:insert definition="stripe" flush="false">
                                        <tiles:put name="name" value="подтверждение"/>
                                        <tiles:put name="width" value="230px"/>
                                    </tiles:insert>
                                </tiles:put>
                                <tiles:put name="enableRegions" value="${true}"/>
                                <tiles:put name="fieldDisabled" value="${form.document.byTemplate && phiz:getEditMode(form.recipient) == 'static'}"/>
                                <tiles:put name="templateId" value="${form.template}"/>
                                <tiles:put name="paymentFieldsHtml">
                                    <tiles:insert page="/WEB-INF/jsp-sbrf/basket/invoiceSubscription/servicePaymentFields.jsp" flush="false">
                                        <tiles:put name="noProviderMessage">
                                            Ќе найдено ни одного поставщика, соответствующего заданному региону!
                                        </tiles:put>
                                    </tiles:insert>
                                </tiles:put>
                                <tiles:put name="cancelButton" type="string">
                                    <c:set var="accountingId" value="${form.accountingEntityId}"/>
                                    <c:set var="serviceId" value="${form.serviceId}"/>
                                    <c:set var="recipientId" value="${form.recipient}"/>
                                    <c:set var="autoId" value="${form.autoId}"/>

                                    <c:choose>
                                        <c:when test="${not empty autoId}">
                                            <c:set var="backActionUrl" value="/private/userprofile/basket.do"/>
                                        </c:when>
                                        <c:when test="${empty serviceId && not empty recipientId}">
                                            <c:set var="backActionUrl" value="/private/userprofile/listServices.do?accountingEntityId=${accountingId}"/>
                                        </c:when>
                                        <c:when test="${not empty serviceId}">
                                            <c:set var="backActionUrl" value="/private/userprofile/serviceContent.do?accountingEntityId=${accountingId}&serviceId=${serviceId}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="backActionUrl" value="/private/userprofile/basket.do"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.back.to.search.provider"/>
                                        <tiles:put name="commandHelpKey" value="button.back.to.search.provider"/>
                                        <tiles:put name="bundle" value="paymentsBundle"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                        <tiles:put name="action" value="${backActionUrl}"/>
                                    </tiles:insert>
                                </tiles:put>
                                <tiles:put name="nextButton" type="string">
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="commandKey" value="button.next"/>
                                        <tiles:put name="commandTextKey" value="button.start.autosearch"/>
                                        <tiles:put name="commandHelpKey" value="button.start.autosearch.help"/>
                                        <tiles:put name="bundle" value="paymentsBundle"/>
                                        <tiles:put name="isDefault" value="true"/>
                                        <tiles:put name="viewType" value="buttonGreen"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </c:if>
            </div>
            <script type="text/javascript">
                $(document).ready(function(){initialize(); initPaymentTabIndex();});
            </script>
        </tiles:put>
    </tiles:insert>

</html:form>

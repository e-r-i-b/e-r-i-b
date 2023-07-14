<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--@elvariable id="form" type="com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm"--%>
<%--@elvariable id="serviceProvider" type="com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider"--%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${serviceProvider == null}">
    <c:set var="serviceProvider" value="${phiz:getServiceProvider(form.recipient)}"/>
</c:if>
<c:set var="order" value="${form.order}"/>
<html:hidden name="form" property="operationUID"/>
<tiles:insert page="paymentContext.jsp" flush="false"/>
<div id="payment" onkeypress="onEnterKey(event);">
    <tiles:insert definition="mainWorkspace" flush="false">
        <tiles:put name="title">
            ������: <c:out value="${serviceProvider.name}"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/servicesPaymentData.jsp" flush="false">
                <%-- ��������� ����� ������ --%>
                <tiles:put name="header" type="string">
                    <c:set var="imageId" value="${serviceProvider.imageId}"/>
                    <c:choose>
                        <c:when test="${not empty imageId}">
                            <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                            <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="imagePath" value="${skinUrl}/images/IQWave-other.jpg"/>
                        </c:otherwise>
                    </c:choose>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}"/>
                        <tiles:put name="description">
                            � ������� ���� �������� �� ������� ��������� ������ �����������,
                            �������� ������ ��� ������, � ����� �������� ������.
                            ��� ���������� �������� ��������� ��������� ������� � ������� �� ������ ����������.
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </tiles:put>

                <%--����������� ������ ����� ����� �� ������ (� ������, ���� ������ ������� � ������ ��� ������� ����)--%>
                <tiles:put name="byCenter" value="${byCenter}"/>

                <%-- ����� ����� --%>
                <tiles:put name="stripe" type="string">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="����� ������"/>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="���������� ����������"/>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="�������������"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="������ ��������"/>
                    </tiles:insert>
                </tiles:put>

                <%--������� ����������������� �����--%>
                <tiles:put name="fieldDisabled" value="${form.document.byTemplate && phiz:getEditMode(form.recipient) == 'static'}"/>
                <tiles:put name="templateId" value="${form.template}"/>
                <%-- ���� ������� --%>
                <tiles:put name="paymentFieldsHtml">
                    <div id="paymentForm" style="display: none;" class="fullWidth"></div>
                    <html:hidden name="form" property="recipient" styleId="recipientId"/>
                    <html:hidden name="form" property="template"/>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">���<span id="asterisk_" class="asterisk">*</span>:</tiles:put>
                        <tiles:put name="description">����������������� ����� �����������������</tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:write name="order" property="INN"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">��������� ����<span id="asterisk_" class="asterisk">*</span>:</tiles:put>
                        <tiles:put name="description">����� ����� ����������</tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:write name="order" property="receiverAccount"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">���<span id="asterisk_" class="asterisk">*</span>:</tiles:put>
                        <tiles:put name="description">���������� ����������������� ���</tiles:put>
                        <tiles:put name="data">
                            <span class="bold"><bean:write name="order" property="BIC"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert page="fromResourceField.jsp" flush="false"/>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">�����<span id="asterisk_" class="asterisk">*</span>:</tiles:put>
                        <tiles:put name="description">����� �������</tiles:put>
                        <tiles:put name="data">
                            <span class="bold">${phiz:formatAmount(order.amount)}</span>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>

                <tiles:put name="cancelButton" type="string">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="action" value="/external/payments/system/end"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="nextButton" type="string">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.next"/>
                        <tiles:put name="commandTextKey" value="button.pay"/>
                        <tiles:put name="commandHelpKey" value="button.pay"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</div>
<script type="text/javascript">
    $(document).ready(function(){initialize(); initPaymentTabIndex();});
</script>

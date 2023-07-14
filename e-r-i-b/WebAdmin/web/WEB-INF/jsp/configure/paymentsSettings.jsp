<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<html:form action="/payments/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="editPaymentsCofigureAccess" value="${phiz:impliesService('PaymentsConfigureService') or phiz:impliesService('PaymentsConfigureServiceEmployee')}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="PaymentsConfig"/>
        <tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="personsConfigure"/>
                <tiles:put name="name"><bean:message bundle="configureBundle" key="settings.payments.menuitem"/></tiles:put>
                <tiles:put name="description" value=""/>
                <tiles:put name="data">
                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.payment.send.message.toReceiver.yandex"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.payments.field.sendMessageToReceiver.yandex"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">none</tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                            <tiles:put name="disabled" value="${not editPaymentsCofigureAccess}"/>
                        </tiles:insert>
                    </table>
                </tiles:put>
            </tiles:insert>
            <tiles:put name="formButtons">
                <c:if test="${editPaymentsCofigureAccess}">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </c:if>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<html:form action="/person/confirmSelfOperations"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="ConfirmSelfOperations"/>

        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">

        <tiles:importAttribute/>

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="personsConfigure"/>
            <tiles:put name="name"><bean:message bundle="configureBundle" key="settings.confirm.selfoperations.header"/></tiles:put>
            <tiles:put name="description" value=""/>
            <tiles:put name="data">
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.payment.confirm.selfOperation"/>
                        <tiles:put name="fieldDescription">Подтверждать операции вклад-вклад, вклад-карта</tiles:put>
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="showHint">none</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        <tiles:put name="disabled" value="${not phiz:impliesServiceRigid('EditConfirmPaymentOperationsService')}"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.iccs.payment.confirm.self.closeAccount"/>
                        <tiles:put name="fieldDescription">Подтверждать операцию «закрытие вклада»</tiles:put>
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="showHint">none</tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                        <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        <tiles:put name="disabled" value="${not phiz:impliesServiceRigid('EditConfirmPaymentOperationsService')}"/>
                    </tiles:insert>
                </table>
            </tiles:put>
        </tiles:insert>
        <tiles:put name="formButtons">
            <c:if test="${phiz:impliesService('EditConfirmPaymentOperationsService')}">
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

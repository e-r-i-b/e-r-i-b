<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/documents/refuse" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="auditMain">

   	<c:set var="form" value="${DocumentRefuseForm}"/>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="refusePayment"/>
            <tiles:put name="name">
                <bean:message key="refuse.document.title" bundle="paymentsBundle"/>
            </tiles:put>
            <tiles:put name="description">
                <bean:message key="refuse.document.description" bundle="paymentsBundle"/>
            </tiles:put>
            <tiles:put name="data">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.document.reason" bundle="paymentsBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(reason)" size="60" maxlength="255" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

            </tiles:put>
            <tiles:put name="buttons">
                <nobr class="rfsPmnt">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.refuse"/>
                        <tiles:put name="commandHelpKey" value="button.refuse.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="paymentsBundle"/>
                        <tiles:put name="action"         value="/audit/businessDocument.do?status=admin&needLoadData=true"/>
                    </tiles:insert>
                <nobr>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>

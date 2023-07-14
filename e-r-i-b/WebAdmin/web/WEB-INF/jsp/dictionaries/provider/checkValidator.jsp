<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/fields/validators/check" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle"><bean:message bundle="providerBundle" key="fields.title"/></tiles:put>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message bundle="providerBundle" key="label.validator.check.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="providerBundle" key="label.validator.check.message"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.field.name.select" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fieldName" size="40" readonly="true" styleClass="contactInput"/>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.field.code" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fieldCode" size="40" readonly="true" styleClass="contactInput"/>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.validator.check.value" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="checkingValue" size="40" styleClass="contactInput"/>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.validator.expression" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="validatorExpression" size="40" readonly="true" styleClass="contactInput"/>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.validator.check.result" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="checkingResult" size="40" readonly="true" styleClass="contactInput"/>&nbsp;
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel.help"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="onclick" value="window.close();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.check"/>
                        <tiles:put name="commandHelpKey" value="button.check.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
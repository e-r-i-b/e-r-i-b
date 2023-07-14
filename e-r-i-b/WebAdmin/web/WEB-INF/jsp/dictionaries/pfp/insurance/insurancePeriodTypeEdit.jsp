<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/insurance/periodTypeEdit" onsubmit="return setEmptyAction();">
    <tiles:insert definition="editPFPInsuranceProduct">
        <tiles:put name="submenu" type="string" value="insurancePeriodTypeEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"  value="pfpDictionary"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpInsuranceBundle" key="periodType.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpInsuranceBundle" key="periodType.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="periodType.label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="58" maxlength="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="periodType.label.edit.field.months"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(months)" size="58" maxlength="2"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListInsurancePeriodTypeOperation">
                        <tiles:put name="commandTextKey"    value="periodType.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="periodType.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpInsuranceBundle"/>
                        <tiles:put name="action"            value="/pfp/insurance/periodTypeList.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditInsurancePeriodTypeOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="periodType.button.save"/>
                        <tiles:put name="commandHelpKey"     value="periodType.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpInsuranceBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="isDefault"            value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
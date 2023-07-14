<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/riskProfile/ageCategory/edit" onsubmit="return setEmptyAction();">
    <tiles:insert definition="editPFPRiskProfile">
        <tiles:put name="submenu" type="string" value="ageCategoryProductEdit"/>
        <tiles:put name="menu" type="string">
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpDictionary"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.edit.field.age"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <b><bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.edit.field.minAge"/>&nbsp;<span class="asterisk">*</span></b>&nbsp;<html:text property="fields(minAge)" size="4" maxlength="3"/>
                            <b><bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.edit.field.maxAge"/></b>&nbsp;<html:text property="fields(maxAge)" size="4" maxlength="3"/>
                            <b><bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.edit.field.weight"/></b>&nbsp;<span class="asterisk">*</span>&nbsp;<html:text property="fields(weight)" size="4" maxlength="2"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListAgeCategoriesOperation">
                        <tiles:put name="commandTextKey"    value="ageCategory.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="ageCategory.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                        <tiles:put name="action"            value="/pfp/riskProfile/ageCategory/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditAgeCategoryOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="ageCategory.button.save"/>
                        <tiles:put name="commandHelpKey"     value="ageCategory.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpRiskProfileBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="isDefault"            value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

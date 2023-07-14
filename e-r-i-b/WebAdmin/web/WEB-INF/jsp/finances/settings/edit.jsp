<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<html:form action="/finances/settings/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="editCardOperationSettings"/>
        <tiles:put name="submenu" type="string" value="PersonalFinanceSettings"/>
        <tiles:put name="pageName" type="string"><bean:message bundle="editCardOperationSettingsBundle" key="label.pageTitle"/></tiles:put>
        <tiles:put name="pageDescription"  value="Используйте данную форму настройки функционала АЛФ."/>
        <tiles:put name="data" type="string">
                <table>
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.finances.log.card.operation.category"/>
                        <tiles:put name="fieldDescription"><bean:message key="log.card.operation.category" bundle="editCardOperationSettingsBundle"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="textMaxLength" value="4"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.finances.log.filter.outcome.period"/>
                        <tiles:put name="fieldDescription"><bean:message key="log.filter.outcome.period" bundle="editCardOperationSettingsBundle"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="textMaxLength" value="4"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="card.operation.max.time"/>
                        <tiles:put name="fieldDescription"><bean:message key="label.connection.timeout" bundle="editCardOperationSettingsBundle"/></tiles:put>
                        <tiles:put name="inputDesc"><bean:message key="period.type.day" bundle="editCardOperationSettingsBundle"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="4"/>
                        <tiles:put name="textMaxLength" value="4"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="connection.timeout"/>
                        <tiles:put name="fieldDescription"><bean:message key="label.connection.timeout" bundle="editCardOperationSettingsBundle"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="4"/>
                        <tiles:put name="textMaxLength" value="4"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>

                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="claim.execution.attempt.max.num"/>
                        <tiles:put name="fieldDescription"><bean:message key="label.claim.execution.attempt.max.num" bundle="editCardOperationSettingsBundle"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="4"/>
                        <tiles:put name="textMaxLength" value="4"/>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                </table>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    Периодичность: каждые
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="DeleteOldCardOperation.period"/>
                        <tiles:put name="textSize" value="3"/>
                        <tiles:put name="textMaxLength" value="3"/>
                    </tiles:insert>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="DeleteOldCardOperation.period.type"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">DAY@<bean:message bundle="editCardOperationSettingsBundle" key="period.type.day"/>|WEEK@<bean:message bundle="editCardOperationSettingsBundle" key="period.type.week"/>|MONTH@<bean:message bundle="editCardOperationSettingsBundle" key="period.type.month"/></tiles:put>
                    </tiles:insert>
                    &nbsp;в&nbsp;
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="DeleteOldCardOperation.time"/>
                        <tiles:put name="textSize" value="5"/>
                        <tiles:put name="textMaxLength" value="5"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            </tiles:put>

            <tiles:put name="formButtons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle"  value="editCardOperationSettingsBundle"/>
                    <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="editCardOperationSettingsBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
</html:form>

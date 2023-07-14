<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/configure/segments" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="pfpConfigure"/>
        <tiles:put name="submenu" type="string" value="editAvailableSegment"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"  value=""/>
                <tiles:put name="name">
                    <bean:message bundle="pfpConfigureBundle" key="segments.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpConfigureBundle" key="segments.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpConfigureBundle" key="segments.label.edit.field"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <table>
                                <c:forEach var="segment" items="${form.segments}">
                                    <tiles:insert definition="propertyField" flush="false">
                                        <tiles:put name="fieldName" value="${segment}"/>
                                        <tiles:put name="fieldDescription"><bean:message bundle="pfpConfigureBundle" key="segments.label.edit.field.${segment}"/></tiles:put>
                                        <tiles:put name="showHint" value="none"/>
                                        <tiles:put name="fieldType" value="checkbox"/>
                                    </tiles:insert>
                                </c:forEach>
                            </table>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" operation="EditSegmentAvailableOperation" flush="false">
                        <tiles:put name="commandTextKey"    value="segments.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="segments.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpConfigureBundle"/>
                        <tiles:put name="action"            value="/pfp/configure/segments.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" operation="EditSegmentAvailableOperation" flush="false" >
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="segments.button.save"/>
                        <tiles:put name="commandHelpKey"     value="segments.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpConfigureBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="isDefault"            value="true"/>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
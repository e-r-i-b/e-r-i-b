<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/configure/gate/services/list">
	<tiles:insert definition="listGateServiceConfig">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="gateServiceConfigBundle" key="label.pageTitle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="ListGateServicesRestriction"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="normalState"       value="NORMAL"/>
        <c:set var="degradationState"  value="DEGRADATION"/>
        <c:set var="inaccessibleState" value="INACCESSIBLE"/>

        <%-- данные --%>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="ListGateServicesRestriction"/>
                <tiles:put name="text">
                    <bean:message bundle="gateServiceConfigBundle" key="label.tableTitle"/>
                </tiles:put>
                <tiles:put name="grid">
                    <input type="hidden" name="newState" id="newStateContainer"/>
                    <sl:collection id="listElement" model="list" property="data" bundle="gateServiceConfigBundle">
                        <c:set var="stateKey" value="label.state.${listElement.state}"/>
                        <c:set var="currentServiceName" value="${listElement.serviceName}"/>
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="serviceName"/>
                        <sl:collectionItem styleClass="state" value="${listElement.state}" hidden="true"/>
                        <sl:collectionItem styleClass="use${degradationState}"  value="${listElement.degradationConfig.available}" hidden="true"/>
                        <sl:collectionItem styleClass="use${inaccessibleState}" value="${listElement.inaccessibleConfig.available}" hidden="true"/>
                        <sl:collectionItem styleClass="serviceName" title="label.table.name">
                            <c:if test="${not empty listElement}"><bean:message key="label.service.${fn:substringAfter(currentServiceName, '.')}" bundle="gateServiceConfigBundle"/></c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.table.use">
                            <c:set var="state" value="${listElement.state}"/>
                            <tiles:insert definition="roundedPlate" flush="false">
                                <tiles:put name="data">
                                    <c:if test="${not empty listElement}"><bean:message key="label.state.${state}" bundle="gateServiceConfigBundle"/></c:if>
                                </tiles:put>
                                <c:choose>
                                    <c:when test="${state eq inaccessibleState}"><tiles:put name="color" value="gray"/></c:when>
                                    <c:when test="${state eq degradationState}"><tiles:put name="color" value="red"/></c:when>
                                    <c:otherwise><tiles:put name="color" value="green"/></c:otherwise>
                                </c:choose>
                            </tiles:insert>
                        </sl:collectionItem>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function getTextInSelectedLine(className)
                            {
                                return $("[name=selectedIds]:checked").parent().parent().find(className).text();
                            }

                            function getOneSelectionMessage(state)
                            {
                                if (state == '${normalState}')
                                    return '<bean:message bundle="gateServiceConfigBundle"  key="label.table.normal.checkOneSelection"/>';
                                if (state == '${degradationState}')
                                    return '<bean:message bundle="gateServiceConfigBundle"  key="label.table.degradation.checkOneSelection"/>';
                                if (state == '${inaccessibleState}')
                                    return '<bean:message bundle="gateServiceConfigBundle"  key="label.table.inaccessible.checkOneSelection"/>';
                            }

                            function changeState(newState)
                            {
                                checkIfOneItem("selectedIds");
                                if (!(checkSelection("selectedIds", '<bean:message bundle="gateServiceConfigBundle" key="label.table.checkSelection"/>') &&
                                   checkOneSelection("selectedIds", getOneSelectionMessage(newState))))
                                    return false;

                                $('#newStateContainer').val(newState);
                                createCommandButton('button.changeState', '').click('', false);
                                return true;
                            }

                            function goToEdit(state)
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="gateServiceConfigBundle" key="label.table.checkSelection"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="gateServiceConfigBundle" key="label.table.checkOneSelection"/>')))
                                    return;

                                if (getTextInSelectedLine('.use' + state) == 'false')
                                {
                                    var message = '<bean:message bundle="gateServiceConfigBundle" key="label.table.edit.fail.state.message.prefix"/>';
                                    message += getTextInSelectedLine('.serviceName');
                                    message += '<bean:message bundle="gateServiceConfigBundle" key="label.table.edit.fail.state.message.center"/> ';
                                    if (state = '${degradationState}')
                                        message += '<bean:message key="label.intext.state.${degradationState}"  bundle="gateServiceConfigBundle"/>';
                                    else
                                        message += '<bean:message key="label.intext.state.${inaccessibleState}" bundle="gateServiceConfigBundle"/>';
                                        
                                    message += ' <bean:message bundle="gateServiceConfigBundle" key="label.table.edit.fail.state.message.postfix"/>';
                                    groupError(message);
                                    return;
                                }

                                var url = "${phiz:calculateActionURL(pageContext,'/configure/gate/services/edit')}";
                                var serviceName = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?field(serviceName)=" + serviceName + "&field(editingState)=" + state;
                            }

                            function goToRunGate()
                            {
                                window.location = "${phiz:calculateActionURL(pageContext,'/configure/gate/services/run')}";
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditMonitoringDegradationGateConfigOperation">
                            <tiles:put name="commandTextKey" value="button.edit.degradation"/>
                            <tiles:put name="commandHelpKey" value="button.edit.degradation.help"/>
                            <tiles:put name="bundle"         value="gateServiceConfigBundle"/>
                            <tiles:put name="onclick"        value="goToEdit('${degradationState}');"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="EditMonitoringInaccessibleGateConfigOperation">
                            <tiles:put name="commandTextKey" value="button.edit.inaccessible"/>
                            <tiles:put name="commandHelpKey" value="button.edit.inaccessible.help"/>
                            <tiles:put name="bundle"         value="gateServiceConfigBundle"/>
                            <tiles:put name="onclick"        value="goToEdit('${inaccessibleState}');"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="ChangeMonitoringGateServiceStateOperation">
                            <tiles:put name="commandTextKey" value="button.to.normal"/>
                            <tiles:put name="commandHelpKey" value="button.to.normal.help"/>
                            <tiles:put name="bundle"         value="gateServiceConfigBundle"/>
                            <tiles:put name="onclick"        value="changeState('${normalState}');"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="ChangeMonitoringGateServiceStateOperation">
                            <tiles:put name="commandTextKey" value="button.to.degradation"/>
                            <tiles:put name="commandHelpKey" value="button.to.degradation.help"/>
                            <tiles:put name="bundle"         value="gateServiceConfigBundle"/>
                            <tiles:put name="onclick"        value="changeState('${degradationState}');"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="ChangeMonitoringGateServiceStateOperation">
                            <tiles:put name="commandTextKey" value="button.to.inaccessible"/>
                            <tiles:put name="commandHelpKey" value="button.to.inaccessible.help"/>
                            <tiles:put name="bundle"         value="gateServiceConfigBundle"/>
                            <tiles:put name="onclick"        value="changeState('${inaccessibleState}');"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="RunGateMonitoringOperation">
                            <tiles:put name="commandTextKey" value="button.run.gate"/>
                            <tiles:put name="commandHelpKey" value="button.run.gate.help"/>
                            <tiles:put name="bundle"         value="gateServiceConfigBundle"/>
                            <tiles:put name="onclick"        value="goToRunGate();"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="false"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

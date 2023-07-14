<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/configure/gate/services/edit" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="serviceName" value="${form.fields['serviceName']}"/>
    <c:set var="editingState" value="${form.fields['editingState']}"/>
    <c:set var="serviceNameText"><bean:message key="label.service.${fn:substringAfter(serviceName, '.')}" bundle="gateServiceConfigBundle"/></c:set>
    <c:set var="editingStateInText"><bean:message key="label.intext.state.${editingState}" bundle="gateServiceConfigBundle"/></c:set>
    <c:set var="editingStateText"><bean:message key="label.state.${editingState}" bundle="gateServiceConfigBundle"/></c:set>
    <c:set var="degradationState" value="DEGRADATION"/>
    <c:set var="inaccessibleState" value="INACCESSIBLE"/>

    <tiles:insert definition="editGateServiceConfig">
        <tiles:put name="submenu" type="string" value="EditGateServiceRestriction"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"  value=""/>
                <tiles:put name="name">
                    <bean:message bundle="gateServiceConfigBundle" key="label.edit.name" arg0="${editingStateInText}" arg1="${serviceNameText}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="gateServiceConfigBundle" key="label.edit.description" arg0="${editingStateInText}" arg1="${serviceNameText}"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.state"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="state" value="${form.fields['state']}"/>
                            <tiles:insert definition="roundedPlate" flush="false">
                                <tiles:put name="data">
                                    <bean:message key="label.state.${state}" bundle="gateServiceConfigBundle"/>
                                </tiles:put>
                                <c:choose>
                                    <c:when test="${state == inaccessibleState}"><tiles:put name="color" value="gray"/></c:when>
                                    <c:when test="${state == degradationState}"><tiles:put name="color" value="red"/></c:when>
                                    <c:otherwise><tiles:put name="color" value="green"/></c:otherwise>
                                </c:choose>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.state.description"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.use" arg0="${editingStateInText}"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <div><html:radio value="true" property="fields(use)"/><bean:message bundle="gateServiceConfigBundle" key="label.edit.use.true"/></div>
                            <div><html:radio value="false" property="fields(use)"/><bean:message bundle="gateServiceConfigBundle" key="label.edit.use.false"/></div>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.use.description"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.timeout"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(timeout)" maxlength="5"/>
                            <span class="bold"><bean:message bundle="gateServiceConfigBundle" key="label.edit.field.second"/></span>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.timeout.description"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.count"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(count)" maxlength="5"/>
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.count.description" arg0="${editingStateText}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.percent"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(percent)" maxlength="5"/>
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.percent.description" arg0="${editingStateText}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.time"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(time)" maxlength="5"/>
                            <span class="bold"><bean:message bundle="gateServiceConfigBundle" key="label.edit.field.second"/></span>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.time.description"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.messageText"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(messageText)" cols="50" rows="5"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.${editingState}.field.messageText.description"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.recoveryTime"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(recoveryTime)" maxlength="4"/>
                            <span class="bold"><bean:message bundle="gateServiceConfigBundle" key="label.edit.field.minutes"/></span>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="gateServiceConfigBundle" key="label.edit.${editingState}.field.recoveryTime.description"/>
                        </tiles:put>
                    </tiles:insert>

                    <html:hidden property="fields(availableChangeInactiveType)"/>
                    <c:if test="${form.fields['availableChangeInactiveType']}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.inactiveType" arg0="${editingStateInText}"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(inactiveType)">
                                    <html:option value="offline"><bean:message bundle="gateServiceConfigBundle" key="label.edit.field.inactiveType.offline"/></html:option>
                                    <html:option value="inactive"><bean:message bundle="gateServiceConfigBundle" key="label.edit.field.inactiveType.inactive"/></html:option>
                                </html:select>
                            </tiles:put>
                            <tiles:put name="description">
                                <bean:message bundle="gateServiceConfigBundle" key="label.edit.field.inactiveType.description" arg0="${editingStateInText}"/>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListMonitoringGateConfigOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="gateServiceConfigBundle"/>
                        <tiles:put name="action"            value="/configure/gate/services/list.do"/>
                    </tiles:insert>
                    <c:set var="operationKey" value="EditMonitoringDegradationGateConfigOperation"/>
                    <c:if test="${editingState eq inaccessibleState}">
                        <c:set var="operationKey" value="EditMonitoringInaccessibleGateConfigOperation"/>
                    </c:if>
                    <tiles:insert definition="commandButton" flush="false" operation="${operationKey}">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="gateServiceConfigBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="languageSelectForEdit" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value="${form.fields.id}"/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/configure/gate/services/language/save')}?fields(serviceName)=${serviceName}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
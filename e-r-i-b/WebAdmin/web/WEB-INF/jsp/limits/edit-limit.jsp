<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/limits/group/risk/general/edit" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="limitsMain">
        <c:set var="bundle" value="limitsBundle"/>
        <c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
        <c:set var="channel">
            <c:choose>
                <c:when test="${param['channel'] == 'general'}">general</c:when>
                <c:when test="${param['channel'] == 'mobile'}">mobile</c:when>
                <c:when test="${param['channel'] == 'atm'}">atm</c:when>
                <c:when test="${param['channel'] == 'ermb'}">ermb</c:when>
                <c:when test="${param['channel'] == 'social'}">social</c:when>
            </c:choose>
        </c:set>
        <c:set var="securityType">
            <c:choose>
                <c:when test="${param['securityType'] == 'HIGHT'}">HIGHT</c:when>
                <c:when test="${param['securityType'] == 'MIDDLE'}">MIDDLE</c:when>
                <c:when test="${param['securityType'] == 'LOW'}">LOW</c:when>
            </c:choose>
        </c:set>
        <c:set var="isAccessEdit" value="${phiz:impliesOperation('EditLimitOperation', 'LimitsManagment') && form.fields.status == 'DRAFT'}"/>

        <tiles:put name="submenu"   type="string" value="GeneralGroupRiskList/${channel}/${securityType}"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="ListLimitsOperation" service="LimitsManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.list"/>
                <tiles:put name="commandHelpKey"    value="button.list.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="/limits/group/risk/${channel}/list.do?departmentId=${form.departmentId}&channel=${channel}&securityType=${securityType}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function changeRestrictionType()
                {
                    var elem = document.getElementById("field(restrictionType)");
                    if (elem != null)
                    {
                        var value = elem.value;
                        var isVisibleAmount = value.indexOf("COUNT") < 0;
                        hideOrShow("fieldOperationCount", isVisibleAmount);
                        hideOrShow("fieldAmount", !isVisibleAmount);
                        if (isVisibleAmount)
                        {
                            var isMinAmount = value.indexOf("MIN_AMOUNT") == 0;
                            if (isMinAmount)
                            {
                                document.getElementById("titleFieldAmount").innerHTML = '<bean:message bundle="${bundle}" key="edit.minimalamount"/>';
                            }
                            else
                            {
                                document.getElementById("titleFieldAmount").innerHTML = '<bean:message bundle="${bundle}" key="edit.amount"/>';
                            }
                            hideOrShow("fullOperationType", isMinAmount);
                            hideOrShow("minAmountOperationType", !isMinAmount);
                        }
                        else
                        {
                            hideOrShow("fullOperationType", isVisibleAmount);
                            hideOrShow("minAmountOperationType", !isVisibleAmount);
                        }
                    }
                }

                function checkChangedLimitData()
                {
                    if(isChangedLimitData())
                        $('#confirm-button').hide();
                    else
                        $('#confirm-button').show();
                }

                function isChangedLimitData()
                {
                    var restrictionType = ensureElement("field(restrictionType)").value;

                    if(restrictionType == "AMOUNT_IN_DAY"
                            && "${form.fields['amount']}" != ensureElementByName("field(amount)").value)
                        return true;

                    if((restrictionType == "OPERATION_COUNT_IN_DAY" || restrictionType == "OPERATION_COUNT_IN_HOUR")
                            && "${form.fields['operationCount']}" != ensureElementByName("field(operationCount)").value)
                        return true;

                    if(restrictionType != "MIN_AMOUNT"
                            && "${form.fields['operationType']}" != ensureElementByName("field(operationType)").value)
                        return true;

                    return "<bean:write name='form' property='field(startDate)' format='dd.MM.yyyy'/>" != ensureElementByName("field(startDate)").value
                            || "<bean:write name='form' property='field(startDateTime)' format='HH:mm:ss'/>" != ensureElementByName("field(startDateTime)").value;
                }

                doOnLoad(changeRestrictionType);
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="limitEdit"/>
                <tiles:put name="name">
                    <bean:message bundle="${bundle}" key="edit.title.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="${bundle}" key="edit.title.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <c:if test="${securityType != null}">

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="${bundle}" key="label.limit.security.type"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <div class="paymentInputDiv">
                                    <input type="text" name="securityType"
                                           value='<bean:message bundle="${bundle}" key="label.limit.security.type.${securityType}"/>'
                                           class="contactInput"
                                           size="8" disabled="disabled"
                                            />
                                </div>
                            </tiles:put>
                        </tiles:insert>

                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="label.limit.group.risk"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(groupRiskId)" name="form" disabled="${(not empty form.id)}">
                                <c:forEach var="groupRisk" items="${form.groupsRisk}">
                                    <html:option value="${groupRisk.id}">
                                        <c:out value="${groupRisk.name}"/>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.creation.date"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <input type="text" name="creationDate"
                                   value='<bean:write name="form" property="field(creationDate)" format="dd.MM.yyyy"/>'
                                   class="contactInput"
                                   size="8" disabled="disabled"
                                    />
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.start.date"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <input type="text" name="field(startDate)"
                                   value='<bean:write name="form" property="field(startDate)" format="dd.MM.yyyy"/>'
                                <%-- если редактирование действующего лимита, то дата начала действия не редактируема --%>
                                   <c:if test="${(not empty form.id) && !isAccessEdit}">disabled="disabled"</c:if>
                                   size="30" class="dot-date-pick contactInput"
                                   onkeyup="checkChangedLimitData();"
                                    />
                            <input type="text" name="field(startDateTime)"
                                   value='<bean:write name="form" property="field(startDateTime)" format="HH:mm:ss"/>'
                                <%-- если редактирование действующего лимита, то дата начала действия не редактируема --%>
                                   <c:if test="${(not empty form.id) && !isAccessEdit}">disabled="disabled"</c:if>
                                   size="8" class="contactInput time-template"
                                   onkeyup="checkChangedLimitData();"
                                    />
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.restriction.type"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="restrictionTypeForExpression">
                                <bean:write name="form" property="field(restrictionType)"/>
                            </c:set>
                            <input type="hidden" name="field(restrictionTypeForExpression)" id="hiddenRestrictionTypeForExpression" value="${restrictionTypeForExpression}"/>
                            <html:select property="field(restrictionType)" styleId="field(restrictionType)" styleClass="select" name="form" disabled="${(not empty form.id)}" onchange="changeRestrictionType()">
                                <html:option value="AMOUNT_IN_DAY"><bean:message bundle="${bundle}" key="edit.restriction.type.amount.day"/></html:option>
                                <html:option value="OPERATION_COUNT_IN_DAY"><bean:message bundle="${bundle}" key="edit.restriction.type.operation.сount.day"/></html:option>
                                <html:option value="MIN_AMOUNT"><bean:message bundle="${bundle}" key="edit.restriction.type.amount.min"/></html:option>
                                <html:option value="OPERATION_COUNT_IN_HOUR"><bean:message bundle="${bundle}" key="edit.restriction.type.operation.сount.hour"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <div id="fieldAmount" style="display:none">
                         <tiles:insert definition="simpleFormRow" flush="false">
                             <tiles:put name="title">
                                 <span id="titleFieldAmount"><span class="asterisk">*</span> </span>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(amount)" size="30" styleClass="moneyField" onkeyup="checkChangedLimitData();" disabled="${(not empty form.id) && !isAccessEdit}"/> р.
                            </tiles:put>
                        </tiles:insert>
                   </div>

                    <div id="fieldOperationCount" style="display:none">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="${bundle}" key="edit.operation.сount"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(operationCount)" size="30" maxlength="10" onkeyup="checkChangedLimitData();" disabled="${(not empty form.id) && !isAccessEdit}"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.operation.type"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <div id="fullOperationType">
                                <html:select property="field(operationType)" styleId="field(operationType)" name="form" disabled="${(not empty form.id) && !isAccessEdit}" onchange="checkChangedLimitData();">
                                    <html:option value="IMPOSSIBLE_PERFORM_OPERATION"><bean:message bundle="${bundle}" key="edit.operation.type.IMPOSSIBLE_PERFORM_OPERATION"/></html:option>
                                    <html:option value="NEED_ADDITIONAL_CONFIRN"><bean:message bundle="${bundle}" key="edit.operation.type.NEED_ADDITIONAL_CONFIRN"/></html:option>
                                </html:select>
                            </div>
                            <div id="minAmountOperationType" style="display:none">
                                <bean:message bundle="${bundle}" key="edit.operation.type.IMPOSSIBLE_PERFORM_OPERATION"/>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <c:if test="${form.fields.status == 'DRAFT' && phiz:impliesOperation('ConfirmLimitOperation', 'ConfirmLimitsManagment')}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="id"                    value="confirm-button"/>
                            <tiles:put name="commandKey"            value="button.confirm"/>
                            <tiles:put name="commandHelpKey"        value="button.confirm.help"/>
                            <tiles:put name="bundle"                value="commonBundle"/>
                            <tiles:put name="isDefault"               value="true"/>
                            <tiles:put name="postbackNavigation"    value="true"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${(empty form.id) || isAccessEdit}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"            value="button.save"/>
                            <tiles:put name="commandHelpKey"        value="button.save.help"/>
                            <tiles:put name="bundle"                value="commonBundle"/>
                            <tiles:put name="isDefault"               value="true"/>
                            <tiles:put name="postbackNavigation"    value="true"/>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="clientButton" operation="ListLimitsOperation" service="LimitsManagment" flush="false">
                        <tiles:put name="commandTextKey"        value="button.cancel"/>
                        <tiles:put name="commandHelpKey"        value="button.cancel.help"/>
                        <tiles:put name="bundle"                value="${bundle}"/>
                        <tiles:put name="action"                value="/limits/group/risk/${channel}/list.do?departmentId=${form.departmentId}&channel=${channel}&securityType=${securityType}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

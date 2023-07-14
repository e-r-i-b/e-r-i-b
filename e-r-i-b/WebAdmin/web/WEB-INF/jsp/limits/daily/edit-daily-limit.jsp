<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/limits/general/edit" onsubmit="return setEmptyAction(event);">
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
        <c:set var="isAccessEdit" value="${phiz:impliesOperation('EditLimitOperation', 'LimitsManagment') && form.fields.status == 'DRAFT'}"/>

        <tiles:put name="submenu"   type="string" value="GeneralList/${channel}"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="ListLimitsOperation" service="LimitsManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.list"/>
                <tiles:put name="commandHelpKey"    value="button.list.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="/limits/${channel}/list.do?departmentId=${form.departmentId}&channel=${channel}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function checkChangedLimitData()
                {
                    if(isChangedLimitData())
                        $('#confirm-button').hide();
                    else
                        $('#confirm-button').show();
                }

                function isChangedLimitData()
                {
                    return "<bean:write name='form' property='field(startDate)' format='dd.MM.yyyy'/>" != ensureElementByName("field(startDate)").value
                            || "<bean:write name='form' property='field(startDateTime)' format='HH:mm:ss'/>" != ensureElementByName("field(startDateTime)").value
                            || "${form.fields['amount']}" != ensureElementByName("field(amount)").value;
                }
            </script>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="limitEdit"/>
                <tiles:put name="name">
                    <bean:message bundle="${bundle}" key="edit.title.daily.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="${bundle}" key="edit.title.daily.description"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="general.label.typeLimit"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(limitType)" name="form" disabled="${(not empty form.id)}">
                                <html:option value="OBSTRUCTION_FOR_AMOUNT_OPERATIONS">Суточный кумулятивный заградительный лимит</html:option>
                                <html:option value="IMSI">Лимит для IMSI</html:option>
                                <c:if test="${channel == 'mobile'}">
                                    <html:option value="EXTERNAL_CARD">Лимит на получателя для переводов на чужую карту</html:option>
                                </c:if>
                                <c:if test="${channel == 'ermb'}">
                                    <html:option value="EXTERNAL_PHONE">Лимит на получателя ЕРМБ для оплаты чужого телефона</html:option>
                                    <html:option value="EXTERNAL_CARD">Лимит на получателя ЕРМБ для переводов на чужую карту</html:option>
                                </c:if>
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
                        <tiles:put name="data">
                            <div class="paymentInputDiv">
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
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.amount"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(amount)" size="30" styleClass="moneyField" onkeyup="checkChangedLimitData();" disabled="${(not empty form.id) && !isAccessEdit}"/> р.
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <c:if test="${form.fields.status == 'DRAFT' && phiz:impliesOperation('ConfirmLimitOperation', 'ConfirmLimitsManagment')}">
                        <tiles:insert definition="commandButton"    flush="false">
                            <tiles:put name="id"                    value="confirm-button"/>
                            <tiles:put name="commandKey"            value="button.confirm"/>
                            <tiles:put name="commandHelpKey"        value="button.confirm.help"/>
                            <tiles:put name="bundle"                value="commonBundle"/>
                            <tiles:put name="isDefault"               value="true"/>
                            <tiles:put name="postbackNavigation"    value="true"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${(empty form.id) || isAccessEdit}">
                        <tiles:insert definition="commandButton" operation="EditLimitOperation" service="LimitsManagment" flush="false">
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
                        <tiles:put name="action"                value="/limits/${channel}/list.do?departmentId=${form.departmentId}&channel=${channel}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

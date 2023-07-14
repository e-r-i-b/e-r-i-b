<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/limits/overall/edit" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="limitsMain">
        <c:set var="bundle" value="limitsBundle"/>
        <c:set var="form"   value="${phiz:currentForm(pageContext)}"/>

        <c:set var="isAccessEdit" value="${phiz:impliesOperation('EditLimitOperation', 'LimitsManagment')}"/>

        <tiles:put name="leftMenu" type="page" value="/WEB-INF/jsp/configure/leftMenu.jsp"/>
        <tiles:put name="submenu" type="string" value="EditPaymentRestrictionsSettings"/>
        <tiles:put name="mainmenu" type="string" value="Options"/>

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
                            || "<bean:write name='form' property='field(endDate)' format='dd.MM.yyyy'/>" != ensureElementByName("field(endDate)").value
                            || "${form.fields['amount']}" != ensureElementByName("field(amount)").value;
                }
            </script>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="limitEdit"/>
                <tiles:put name="name">
                    <bean:message bundle="${bundle}" key="edit.title.overall.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="${bundle}" key="edit.title.overall.description"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.overall.start.date"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <div class="inlineBlock">
                                <input type="text" name="field(startDate)"
                                       value='<bean:write name="form" property="field(startDate)" format="dd.MM.yyyy"/>'
                                       <c:if test="${!isAccessEdit}">disabled="disabled"</c:if>
                                       size="30" class="dot-date-pick contactInput" onkeyup="checkChangedLimitData();"/>
                            </div>
                            <bean:message bundle="${bundle}" key="edit.overall.end.date"/>&nbsp;
                            <div class="inlineBlock">
                                <input type="text" name="field(endDate)"
                                       value='<bean:write name="form" property="field(endDate)" format="dd.MM.yyyy"/>'
                                       <c:if test="${!isAccessEdit}">disabled="disabled"</c:if>
                                       size="30" class="dot-date-pick contactInput" onkeyup="checkChangedLimitData();"/>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="${bundle}" key="edit.amount"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(amount)" size="30" styleClass="moneyField" onkeyup="checkChangedLimitData();" disabled="${!isAccessEdit}"/> ð.
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

                    <tiles:insert definition="commandButton" operation="EditLimitOperation" service="LimitsManagment" flush="false">
                        <tiles:put name="commandKey"            value="button.save"/>
                        <tiles:put name="commandHelpKey"        value="button.save.help"/>
                        <tiles:put name="bundle"                value="commonBundle"/>
                        <tiles:put name="isDefault"               value="true"/>
                        <tiles:put name="postbackNavigation"    value="true"/>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" operation="ListLimitsOperation" service="LimitsManagment" flush="false">
                        <tiles:put name="commandTextKey"        value="button.cancel"/>
                        <tiles:put name="commandHelpKey"        value="button.cancel.help"/>
                        <tiles:put name="bundle"                value="${bundle}"/>
                        <tiles:put name="action"                value="/limits/overall/list.do"/>
                    </tiles:insert>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

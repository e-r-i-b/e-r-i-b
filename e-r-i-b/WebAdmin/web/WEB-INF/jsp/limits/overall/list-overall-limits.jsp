<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath"    value="${globalUrl}/images"/>
<c:set var="imagePath"          value="${skinUrl}/images"/>

<html:form action="/limits/overall/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="limitsMain">

        <tiles:put name="leftMenu" type="page" value="/WEB-INF/jsp/configure/leftMenu.jsp"/>
        <tiles:put name="submenu" type="string" value="EditPaymentRestrictionsSettings"/>
        <tiles:put name="mainmenu" type="string" value="Options"/>

        <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
        <c:set var="bundle"     value="limitsBundle" scope="request"/>
        <c:set var="baseUrl"    value="${phiz:calculateActionURL(pageContext, '/limits/overall/edit.do')}" scope="request"/>

        <tiles:put name="pageTitle" type="string">
            <bean:message key="page.overall.list.name" bundle="${bundle}"/>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.action.period"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <bean:message bundle="${bundle}" key="filter.period.from"/>
                    &nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                               size="10" name="filter(fromCreationDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(fromCreationDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                    &nbsp;<bean:message bundle="${bundle}" key="filter.period.to"/>&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                               size="10" name="filter(toCreationDate)" class="dot-date-pick"
                               value="<bean:write name="form" property="filter(toCreationDate)" format="dd.MM.yyyy"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"     value="grid.amount.overall"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name"      value="amount"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="filter.status"/>
                <tiles:put name="bundle"    value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="font-weight:normal;cursor:default;">
                        <html:select property="filter(status)">
                            <html:option value="">Все статусы</html:option>
                            <html:option value="1">Действует</html:option>
                            <html:option value="2">Введен</html:option>
                            <html:option value="3">Архив</html:option>
                            <html:option value="4">Черновик</html:option>
                        </html:select>
                    </span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" operation="EditLimitOperation" service="LimitsManagment" flush="false">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="${bundle}"/>
                <tiles:put name="action"            value="/limits/overall/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">

            <script type="text/javascript">
                function editLimit() {
                    var checkBoxList = $("form :checkbox:checked");
                    if(checkBoxList.size() != 1)
                    {
                        groupError('Выберите один лимит!');
                        return;
                    }
                    window.location = "${phiz:calculateActionURL(pageContext,'/limits/overall/edit.do?id=')}" + $("form :checkbox:checked")[0].value;;
                }
            </script>

            <tiles:insert page="/WEB-INF/jsp/limits/overall/overall-limit.jsp" flush="false">
                <tiles:put name="items"             value="data"/>
                <tiles:put name="selectedBeanName"  value="selectedObstructionIds"/>
            </tiles:insert>

            <div class="clear"></div>
            <div class="buttonsArea">
                <c:if test="${not empty form.data}">
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveLimitOperation" service="ConfirmLimitsManagment">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandTextKey"     value="button.remove.limit"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                var checkBoxList = $("form :checkbox:checked");
                                if(checkBoxList.size() < 1)
                                    return groupError('Выберите хотя бы один лимит!');
                                return true;
                            }
                        </tiles:put>
                        <tiles:put name="confirmText">Удалить выбранные лимиты?</tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="RemoveLimitOperation" service="ConfirmLimitsManagment">
                        <tiles:put name="commandTextKey"     value="button.edit.limit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="onclick" value="editLimit();"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                    </tiles:insert>
                </c:if>
            </div>
        </tiles:put>

    </tiles:insert>
</html:form>
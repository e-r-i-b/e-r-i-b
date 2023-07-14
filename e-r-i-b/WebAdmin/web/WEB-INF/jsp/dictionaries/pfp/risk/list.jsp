<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/risk/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="editAction" value="/pfp/risk/edit"/>
    <c:set var="editUrl" value="${phiz:calculateActionURL(pageContext, editAction)}"/>
    <tiles:insert definition="listPFPRisk">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpRiskBundle" key="form.list.page.title"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditRiskOperation">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="pfpRiskBundle"/>
                <tiles:put name="action"            value="${editAction}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="listPFPRisk"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpRiskBundle">
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="form.list.table.column.name">
                            <c:if test="${not empty listElement}">
                                <phiz:link action="${editAction}" operationClass="EditRiskOperation">
                                    <phiz:param name="id" value="${listElement.id}"/>
                                    <c:out value="${listElement.name}"/>
                                </phiz:link>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpRiskBundle" key="form.list.message.edit.selection.empty"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="pfpRiskBundle" key="form.list.message.edit.selection.many"/>')))
                                    return;

                                var url = "${editUrl}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }

                            function doRemove()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection("selectedIds", '<bean:message bundle="pfpRiskBundle" key="form.list.message.remove.selection.empty"/>');
                            }
                        </script>
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveRiskOperation">
                            <tiles:put name="commandKey"         value="button.remove"/>
                            <tiles:put name="commandHelpKey"     value="button.remove.help"/>
                            <tiles:put name="bundle"             value="pfpRiskBundle"/>
                            <tiles:put name="confirmText"><bean:message bundle="pfpRiskBundle" key="form.list.message.remove.agreement"/></tiles:put>
                            <tiles:put name="validationFunction" value="doRemove()"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="EditRiskOperation">
                            <tiles:put name="commandTextKey"    value="button.edit"/>
                            <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpRiskBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpRiskBundle" key="form.list.message.data.empty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

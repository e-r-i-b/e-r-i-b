<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/targets/list">
    <tiles:insert definition="listPFPTarget">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpTargetsBundle" key="label.pageTitle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="targetList"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditTargetOperation">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="pfpTargetsBundle"/>
                <tiles:put name="action"            value="/pfp/targets/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label"  value="label.filter.target"/>
                <tiles:put name="bundle" value="pfpTargetsBundle"/>
                <tiles:put name="name"   value="name"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="targetList"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpTargetsBundle">
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                                                                                                       
                        <sl:collectionItem title="label.table.target">
                            <phiz:link  action="/pfp/targets/edit" operationClass="EditTargetOperation">
                                <phiz:param name="id" value="${listElement.id}"/>
                                <c:out value="${listElement.name}"/>
                            </phiz:link>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.table.onlyOne" styleClass="align-center">
                            <input type="checkbox" <c:if test="${listElement.onlyOne}">checked=""</c:if> disabled=""/>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.table.laterAll" styleClass="align-center">
                            <input type="checkbox" <c:if test="${listElement.laterAll}">checked=""</c:if> disabled=""/>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.table.laterLoans" styleClass="align-center">
                            <input type="checkbox" <c:if test="${listElement.laterLoans}">checked=""</c:if> disabled=""/>
                        </sl:collectionItem>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function doRemove()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection("selectedIds", '<bean:message bundle="pfpTargetsBundle" key="label.table.checkSelection"/>');
                            }

                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpTargetsBundle" key="label.table.checkSelection"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="pfpTargetsBundle" key="label.table.checkOneSelection"/>')))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/pfp/targets/edit')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditTargetOperation">
                            <tiles:put name="commandTextKey"    value="button.edit"/>
                            <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpTargetsBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveTargetOperation">
                            <tiles:put name="commandKey"            value="button.remove"/>
                            <tiles:put name="commandHelpKey"        value="button.remove.help"/>
                            <tiles:put name="bundle"                value="pfpTargetsBundle"/>
                            <tiles:put name="validationFunction"    value="doRemove();"/>
                            <tiles:put name="confirmText"><bean:message bundle="pfpTargetsBundle" key="label.table.removeQuestion"/></tiles:put>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpTargetsBundle" key="label.tableEmpty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

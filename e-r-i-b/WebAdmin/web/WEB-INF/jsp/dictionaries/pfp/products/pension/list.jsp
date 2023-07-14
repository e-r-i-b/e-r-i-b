<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/pension/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="listPFPProduct">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpPensionBundle" key="form.list.page.title"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="pensionProductList"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditPensionProductOperation">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="pfpPensionBundle"/>
                <tiles:put name="action"            value="/pfp/products/pension/edit"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="pensionProductList"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpPensionBundle">
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <sl:collectionItem hidden="true"/>
                        <c:choose>
                            <c:when test="${empty listElement.id}">
                                <tr class="tblInfHeader">
                                    <th class="titleTable" style="width:20px" rowspan="2">
                                        <input type="checkbox" name="isSelectAll" onclick="switchSelection('isSelectAll','selectedIds');">
                                    </th>
                                    <th class="titleTable" rowspan="2">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.name"/>
                                    </th>
                                    <th class="titleTable" rowspan="2">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.fee.entry"/>
                                    </th>
                                    <th class="titleTable" rowspan="2">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.fee.quarterly"/>
                                    </th>
                                    <th class="titleTable" colspan="3">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.period"/>
                                    </th>
                                    <th class="titleTable" colspan="3">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.income"/>
                                    </th>
                                </tr>
                                <tr class="tblInfHeader">
                                    <th class="noBackground">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.period.min"/>
                                    </th>
                                    <th class="noBackground">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.period.max"/>
                                    </th>
                                    <th class="noBackground">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.period.default"/>
                                    </th>
                                    <th class="noBackground">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.income.min"/>
                                    </th>
                                    <th class="noBackground">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.income.max"/>
                                    </th>
                                    <th class="noBackground">
                                        <bean:message bundle="pfpPensionBundle" key="form.list.table.column.income.default"/>
                                    </th>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <input type="checkbox" name="selectedIds" value="${listElement.id}">
                                </td>
                                <td>
                                    <phiz:link action="/pfp/products/pension/edit" operationClass="EditPensionProductOperation">
                                        <phiz:param name="id" value="${listElement.id}"/>
                                        <c:out value="${listElement.name}"/>
                                        <c:if test="${listElement.universal}">
                                            <bean:message bundle="pfpProductBundle" key="product.base.universal.table.mark"/>
                                        </c:if>
                                    </phiz:link>
                                </td>
                                <td>
                                    <c:out value="${listElement.entryFee}"/>
                                </td>
                                <td>
                                    <c:out value="${listElement.quarterlyFee}"/>
                                </td>
                                <td>
                                    <c:out value="${listElement.minPeriod}"/>
                                </td>
                                <td>
                                    <c:out value="${listElement.maxPeriod}"/>
                                </td>
                                <td>
                                    <c:out value="${listElement.defaultPeriod}"/>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty listElement.minIncome}">
                                            <c:out value="${listElement.minIncome}"/>
                                        </c:when>
                                        <c:otherwise>
                                            &nbsp;
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty listElement.maxIncome}">
                                            <c:out value="${listElement.maxIncome}"/>
                                        </c:when>
                                        <c:otherwise>
                                            &nbsp;
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:out value="${listElement.defaultIncome}"/>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function doRemove()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection("selectedIds", '<bean:message bundle="pfpPensionBundle" key="form.list.message.remove.selection.empty"/>');
                            }
                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpPensionBundle" key="form.list.message.edit.selection.empty"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="pfpPensionBundle" key="form.list.message.edit.selection.many"/>')))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/pfp/products/pension/edit')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditPensionProductOperation">
                            <tiles:put name="commandTextKey"    value="button.edit"/>
                            <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpPensionBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="RemovePensionProductOperation">
                            <tiles:put name="commandKey"            value="button.remove"/>
                            <tiles:put name="commandTextKey"        value="button.remove"/>
                            <tiles:put name="commandHelpKey"        value="button.remove.help"/>
                            <tiles:put name="bundle"                value="pfpPensionBundle"/>
                            <tiles:put name="validationFunction"    value="doRemove();"/>
                            <tiles:put name="confirmText"><bean:message bundle="pfpPensionBundle" key="form.list.message.remove.agreement"/></tiles:put>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpPensionBundle" key="form.list.message.data.empty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

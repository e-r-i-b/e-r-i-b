<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="listPFPProduct"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/pfp/products/fund/list">
    <tiles:insert definition="${layout}">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpProductBundle" key="fund.label.pageTitle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="fundProductList"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="menu" type="string">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditFundProductOperation">
                        <tiles:put name="commandTextKey"    value="fund.button.add"/>
                        <tiles:put name="commandHelpKey"    value="fund.button.add.help"/>
                        <tiles:put name="bundle"            value="pfpProductBundle"/>
                        <tiles:put name="action"            value="/pfp/products/fund/edit.do"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel"/>
                        <tiles:put name="bundle"            value="commonBundle"/>
                        <tiles:put name="onclick"           value="window.close();"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="fundProductList"/>
                <tiles:put name="grid">
                    <c:set var="START_CAPITAL" value="${form.portfolioTypes['START_CAPITAL']}"/>
                    <c:set var="QUARTERLY_INVEST" value="${form.portfolioTypes['QUARTERLY_INVEST']}"/>
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpProductBundle">
                        <c:choose>
                            <c:when test="${standalone}">
                                <sl:collectionParam id="selectType"     value="checkbox"/>
                            </c:when>
                            <c:otherwise>
                                <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');" />
                                <sl:collectionParam id="onRowDblClick"  value="selectFund();"/>
                                <sl:collectionParam id="selectType"     value="radio"/>
                            </c:otherwise>
                        </c:choose>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="fund.label.table.name">
                            <c:set var="productName"><c:out value="${listElement.name}"/></c:set>
                            <c:if test="${listElement.universal}">
                                <c:set var="productName">${productName}<bean:message bundle="pfpProductBundle" key="product.base.universal.table.mark"/></c:set>
                            </c:if>
                            <c:choose>
                                <c:when test="${standalone}">
                                    <phiz:link action="/pfp/products/fund/edit" operationClass="EditFundProductOperation">
                                        <phiz:param name="id" value="${listElement.id}"/>
                                        ${productName}
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    ${productName}
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="fund.label.table.description" property="description"/>
                        <sl:collectionItem title="product.base.minSum.label.column.START_CAPITAL">
                            <sl:collectionItemParam id="value">
                                <bean:message bundle="pfpProductBundle" key="product.base.minSum.label.column.prefix"/>&nbsp;
                                <c:out value="${listElement.parameters[START_CAPITAL].minSum}"/>&nbsp;
                                <bean:message bundle="pfpProductBundle" key="product.base.minSum.label.column.unit"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="product.base.minSum.label.column.QUARTERLY_INVEST">
                            <sl:collectionItemParam id="value">
                                <bean:message bundle="pfpProductBundle" key="product.base.minSum.label.column.prefix"/>&nbsp;
                                <c:out value="${listElement.parameters[QUARTERLY_INVEST].minSum}"/>&nbsp;
                                <bean:message bundle="pfpProductBundle" key="product.base.minSum.label.column.unit"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="fund.label.table.forComplex" property="forComplex">
                            <sl:collectionItemParam id="value">
                                <c:choose>
                                    <c:when test="${listElement.forComplex != 'none'}">
                                        <bean:message bundle="pfpProductBundle" key="fund.label.table.forComplex.true"/>
                                    </c:when>
                                    <c:otherwise>
                                        <bean:message bundle="pfpProductBundle" key="fund.label.table.forComplex.false"/>
                                    </c:otherwise>
                                </c:choose>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            <c:choose>
                                <c:when test="${standalone}">
                                    function doRemove()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="fund.label.table.remove.checkSelection"/>');
                                    }
                                    function goToEdit()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="fund.label.table.edit.checkSelection"/>') ||
                                           (!checkOneSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="fund.label.table.edit.checkOneSelection"/>')))
                                            return;

                                        var url = "${phiz:calculateActionURL(pageContext,'/pfp/products/fund/edit')}";
                                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                                        window.location = url + "?id=" + id;
                                    }
                                </c:when>
                                <c:otherwise>
                                    function selectFund()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="fund.label.table.select.checkSelection"/>') ||
                                            (!checkOneSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="fund.label.table.select.checkOneSelection"/>')))
                                            return;


                                        var ids = document.getElementsByName("selectedIds");
                                        for (var i = 0; i < ids.length; i++)
                                        {
                                            if (ids.item(i).checked)
                                            {
                                                var r = ids.item(i).parentNode.parentNode;
                                                var a = new Array(2);
                                                a['id'] = trim(ids.item(i).value);
                                                a['name'] = trim(getElementTextContent(r.cells[1]));
                                                a['description'] = trim(getElementTextContent(r.cells[2]));
                                                window.opener.setFund(a);
                                                window.close();
                                                return true;
                                            }
                                        }
                                        alert("<bean:message bundle="pfpProductBundle" key="fund.label.table.select.checkSelection"/>");
                                        return false;
                                    }
                                </c:otherwise>
                            </c:choose>
                        </script>
                        <c:choose>
                            <c:when test="${standalone}">
                                <tiles:insert definition="clientButton" flush="false" operation="EditFundProductOperation">
                                    <tiles:put name="commandTextKey"    value="fund.button.edit"/>
                                    <tiles:put name="commandHelpKey"    value="fund.button.edit.help"/>
                                    <tiles:put name="bundle"            value="pfpProductBundle"/>
                                    <tiles:put name="onclick"           value="goToEdit();"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false" operation="RemoveFundProductOperation">
                                    <tiles:put name="commandKey"            value="button.remove"/>
                                    <tiles:put name="commandTextKey"        value="fund.button.remove"/>
                                    <tiles:put name="commandHelpKey"        value="fund.button.remove.help"/>
                                    <tiles:put name="bundle"                value="pfpProductBundle"/>
                                    <tiles:put name="validationFunction"    value="doRemove();"/>
                                    <tiles:put name="confirmText"><bean:message bundle="pfpProductBundle" key="fund.label.table.remove.question"/></tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="fund.button.select"/>
                                    <tiles:put name="commandHelpKey"    value="fund.button.select.help"/>
                                    <tiles:put name="bundle"            value="pfpProductBundle"/>
                                    <tiles:put name="onclick"           value="selectFund();"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpProductBundle" key="fund.label.tableEmpty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

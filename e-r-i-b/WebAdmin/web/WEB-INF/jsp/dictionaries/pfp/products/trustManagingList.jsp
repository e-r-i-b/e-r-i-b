<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/trustManaging/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="listPFPProduct">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpTrustManagingProductBundle" key="form.list.page.title"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="trustManagingProductList"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditTrustManagingProductOperation">
                <tiles:put name="commandTextKey"    value="button.add"/>
                <tiles:put name="commandHelpKey"    value="button.add.help"/>
                <tiles:put name="bundle"            value="pfpTrustManagingProductBundle"/>
                <tiles:put name="action"            value="/pfp/products/trustManaging/edit"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="trustManagingProductList"/>
                <tiles:put name="grid">
                    <c:set var="START_CAPITAL" value="${form.portfolioTypes['START_CAPITAL']}"/>
                    <c:set var="QUARTERLY_INVEST" value="${form.portfolioTypes['QUARTERLY_INVEST']}"/>
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpTrustManagingProductBundle">
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="form.list.table.column.name">
                            <phiz:link action="/pfp/products/trustManaging/edit" operationClass="EditTrustManagingProductOperation">
                                <phiz:param name="id" value="${listElement.id}"/>
                                <c:out value="${listElement.name}"/>
                                <c:if test="${listElement.universal}">
                                    <bean:message bundle="pfpProductBundle" key="product.base.universal.table.mark"/>
                                </c:if>
                            </phiz:link>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.column.description" property="description"/>
                        <sl:collectionItem title="form.list.table.column.minSum.START_CAPITAL">
                            <sl:collectionItemParam id="value">
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.list.table.column.minSum.prefix"/>&nbsp;
                                <c:out value="${listElement.parameters[START_CAPITAL].minSum}"/>&nbsp;
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.list.table.column.minSum.unit"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.column.minSum.QUARTERLY_INVEST">
                            <sl:collectionItemParam id="value">
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.list.table.column.minSum.prefix"/>&nbsp;
                                <c:out value="${listElement.parameters[QUARTERLY_INVEST].minSum}"/>&nbsp;
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.list.table.column.minSum.unit"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function doRemove()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection("selectedIds", '<bean:message bundle="pfpTrustManagingProductBundle" key="form.list.message.remove.selection.empty"/>');
                            }
                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpTrustManagingProductBundle" key="form.list.message.edit.selection.empty"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="pfpTrustManagingProductBundle" key="form.list.message.edit.selection.many"/>')))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/pfp/products/trustManaging/edit')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditTrustManagingProductOperation">
                            <tiles:put name="commandTextKey"    value="button.edit"/>
                            <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpTrustManagingProductBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveTrustManagingProductOperation">
                            <tiles:put name="commandKey"            value="button.remove"/>
                            <tiles:put name="commandTextKey"        value="button.remove"/>
                            <tiles:put name="commandHelpKey"        value="button.remove.help"/>
                            <tiles:put name="bundle"                value="pfpTrustManagingProductBundle"/>
                            <tiles:put name="validationFunction"    value="doRemove();"/>
                            <tiles:put name="confirmText"><bean:message bundle="pfpTrustManagingProductBundle" key="form.list.message.remove.agreement"/></tiles:put>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpTrustManagingProductBundle" key="form.list.message.data.empty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

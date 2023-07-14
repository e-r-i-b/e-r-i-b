<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/complex/investment/ima/list">
    <tiles:insert definition="listPFPComplexProduct">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.pageTitle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="complexIMAInvestmentProductList"/>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditComplexIMAInvestmentProductOperation">
                <tiles:put name="commandTextKey"    value="products.complex.investment.ima.button.add"/>
                <tiles:put name="commandHelpKey"    value="products.complex.investment.ima.button.add.help"/>
                <tiles:put name="bundle"            value="pfpProductBundle"/>
                <tiles:put name="action"            value="/pfp/products/complex/investment/ima/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="complexIMAInvestmentProductList"/>
                <tiles:put name="grid">
                    <c:set var="START_CAPITAL" value="${form.portfolioTypes['START_CAPITAL']}"/>
                    <c:set var="QUARTERLY_INVEST" value="${form.portfolioTypes['QUARTERLY_INVEST']}"/>
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpProductBundle">
                        <c:set var="link" value="/pfp/products/complex/investment/ima/edit.do?id=${listElement.id}"/>
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="products.complex.investment.ima.label.table.account" property="account" action="${link}">
                            <sl:collectionItemParam id="value">
                                <c:out value="${listElement.account.name}"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="products.complex.investment.ima.label.table.fund"  property="fundProducts">
                            <sl:collectionItemParam id="value">
                                <c:forEach var="product" items="${listElement.fundProducts}">
                                    <p><c:out value="${product.name}"/></p>
                                </c:forEach>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="products.complex.investment.ima.label.table.ima"  property="imaProducts">
                            <sl:collectionItemParam id="value">
                                <c:forEach var="product" items="${listElement.imaProducts}">
                                    <p><c:out value="${product.name}"/></p>
                                </c:forEach>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="products.complex.investment.ima.label.table.description" property="description"/>
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
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function doRemove()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.table.remove.checkSelection"/>');
                            }
                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.table.edit.checkSelection"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.table.edit.checkOneSelection"/>')))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/pfp/products/complex/investment/ima/edit')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditComplexIMAInvestmentProductOperation">
                            <tiles:put name="commandTextKey"    value="products.complex.investment.ima.button.edit"/>
                            <tiles:put name="commandHelpKey"    value="products.complex.investment.ima.button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpProductBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveComplexIMAInvestmentProductOperation">
                            <tiles:put name="commandKey"            value="button.remove"/>
                            <tiles:put name="commandTextKey"        value="products.complex.investment.ima.button.remove"/>
                            <tiles:put name="commandHelpKey"        value="products.complex.investment.ima.button.remove.help"/>
                            <tiles:put name="bundle"                value="pfpProductBundle"/>
                            <tiles:put name="validationFunction"    value="doRemove();"/>
                            <tiles:put name="confirmText"><bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.table.remove.question"/></tiles:put>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.tableEmpty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

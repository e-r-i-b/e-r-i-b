<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
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
<c:set var="filterMode" value="${param['action'] eq 'filter'}"/>

<html:form action="/pfp/loanKind/list">
    <tiles:insert definition="${layout}">
        <tiles:put name="submenu" type="string" value="loanKindProductList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpProductBundle" key="loanKind.label.pageTitle"/>
        </tiles:put>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="menu" type="string">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditLoanKindProductOperation">
                        <tiles:put name="commandTextKey"    value="loanKind.button.add"/>
                        <tiles:put name="commandHelpKey"    value="loanKind.button.add.help"/>
                        <tiles:put name="bundle"            value="pfpProductBundle"/>
                        <tiles:put name="action"            value="/pfp/loanKind/edit.do"/>
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
        <%-- данные --%>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="insuranceList"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpProductBundle">
                        <c:choose>
                            <c:when test="${filterMode}">
                                <sl:collectionParam id="selectType" value="radio"/>
                            </c:when>
                            <c:otherwise>
                                <sl:collectionParam id="selectType" value="checkbox"/>
                            </c:otherwise>
                        </c:choose>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="loanKind.label.table.name">
                            <c:choose>
                                <c:when test="${standalone}">
                                    <phiz:link action="/pfp/loanKind/edit" operationClass="EditLoanKindProductOperation">
                                        <phiz:param name="id" value="${listElement.id}"/>
                                        <c:out value="${listElement.name}"/>
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${listElement.name}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            <c:choose>
                                <c:when test="${standalone}">
                                    function doRemove()
                                    {
                                        checkIfOneItem("selectedIds");
                                            return checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkSelection"/>');
                                    }
                            
                                    function goToEdit()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkSelection"/>') ||
                                            (!checkOneSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkOneSelection"/>')))
                                            return;

                                        var url = "${phiz:calculateActionURL(pageContext,'/pfp/loanKind/edit')}";
                                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                                        window.location = url + "?id=" + id;
                                    }
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${filterMode}">
                                            function selectLoanKindProduct()
                                            {
                                                checkIfOneItem("selectedIds");
                                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkSelection"/>') ||
                                                    !checkOneSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkOneSelection"/>'))
                                                    return false;


                                                var ids = document.getElementsByName("selectedIds");
                                                for (var i = 0; i < ids.length; i++)
                                                {
                                                    if (ids.item(i).checked)
                                                    {
                                                        var r = ids.item(i).parentNode.parentNode;
                                                        var a = new Array(2);
                                                        a['id'] = trim(ids.item(i).value);
                                                        a['name'] = trim(getElementTextContent(r.cells[1]));
                                                        var message = window.opener.setLoanKindProduct(a);
                                                        if(message != null)
                                                        {
                                                            alert(message);
                                                            return false;
                                                        }
                                                        window.close();
                                                        return true;
                                                    }
                                                }
                                                alert("<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkOneSelection"/>");
                                                return false;
                                            }
                                        </c:when>
                                        <c:otherwise>
                                            function selectLoanKindProduct()
                                            {
                                                checkIfOneItem("selectedIds");
                                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkSelection"/>'))
                                                    return false;


                                                var ids = document.getElementsByName("selectedIds");
                                                var result = new Array();
                                                var resultCount = 0;
                                                for (var i = 0; i < ids.length; i++)
                                                {
                                                    if (ids.item(i).checked)
                                                    {
                                                        var r = ids.item(i).parentNode.parentNode;
                                                        var a = new Array(2);
                                                        a['id'] = trim(ids.item(i).value);
                                                        a['name'] = trim(getElementTextContent(r.cells[1]));
                                                        result[resultCount++] = a;
                                                    }
                                                }
                                                result['size'] = resultCount;
                                                if (resultCount == 0)
                                                {
                                                    alert("<bean:message bundle="pfpProductBundle" key="loanKind.label.table.checkSelection"/>");
                                                    return false;
                                                }
                                                var message = window.opener.setLoanKindProduct(result);
                                                if(message != null && message != undefined)
                                                {
                                                    alert(message);
                                                    return false;
                                                }
                                                window.close();
                                                return true;
                                            }
                                        </c:otherwise>
                                    </c:choose>

                                </c:otherwise>
                            </c:choose>
                        </script>
                        <c:choose>
                            <c:when test="${standalone}">
                                <tiles:insert definition="clientButton" flush="false" operation="EditLoanKindProductOperation">
                                    <tiles:put name="commandTextKey"    value="loanKind.button.edit"/>
                                    <tiles:put name="commandHelpKey"    value="loanKind.button.edit.help"/>
                                    <tiles:put name="bundle"            value="pfpProductBundle"/>
                                    <tiles:put name="onclick"           value="goToEdit();"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false" operation="RemoveLoanKindProductOperation">
                                    <tiles:put name="commandKey"            value="button.remove"/>
                                    <tiles:put name="commandTextKey"        value="loanKind.button.remove"/>
                                    <tiles:put name="commandHelpKey"        value="loanKind.button.remove.help"/>
                                    <tiles:put name="bundle"                value="pfpProductBundle"/>
                                    <tiles:put name="validationFunction"    value="doRemove();"/>
                                    <tiles:put name="confirmText"><bean:message bundle="pfpProductBundle" key="loanKind.label.table.removeQuestion"/></tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="loanKind.button.select"/>
                                    <tiles:put name="commandHelpKey"    value="loanKind.button.select.help"/>
                                    <tiles:put name="bundle"            value="pfpProductBundle"/>
                                    <tiles:put name="onclick"           value="selectLoanKindProduct();"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpProductBundle" key="loanKind.label.tableEmpty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

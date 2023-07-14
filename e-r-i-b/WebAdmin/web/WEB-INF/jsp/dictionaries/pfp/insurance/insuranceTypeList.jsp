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
		<c:set var="layout" value="listPFPInsuranceProduct"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/pfp/insurance/typeList">
	<tiles:insert definition="${layout}">
        <tiles:put name="submenu" type="string" value="insuranceTypeList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpInsuranceBundle" key="type.label.pageTitle"/>
        </tiles:put>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="menu" type="string">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditInsuranceTypeOperation">
                        <tiles:put name="commandTextKey"    value="type.button.add"/>
                        <tiles:put name="commandHelpKey"    value="type.button.add.help"/>
                        <tiles:put name="bundle"            value="pfpInsuranceBundle"/>
                        <tiles:put name="action"            value="/pfp/insurance/typeEdit.do"/>
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
                <tiles:put name="id" value="insuranceTypeList"/>
                <tiles:put name="grid">
                    <tiles:importAttribute/>
                    <c:set var="globalImagePath" value="${globalUrl}/images"/>
                    <c:set var="imagePath" value="${skinUrl}/images"/>

                    <sl:collection id="listElement" model="list" property="data" bundle="pfpInsuranceBundle">
                        <c:choose>
                            <c:when test="${standalone}">
                                <sl:collectionParam id="selectType"     value="checkbox"/>
                            </c:when>
                            <c:otherwise>
                                <sl:collectionParam id="selectType"     value="radio"/>
                                <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');" />
                                <sl:collectionParam id="onRowDblClick"  value="selectInsuranceType();"          />
                            </c:otherwise>
                        </c:choose>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="type.label.table.name">
                            <sl:collectionItemParam id="value">
                                <c:set var="listName">
                                   <c:choose>
                                         <c:when test="${standalone}">
                                             <phiz:link action="/pfp/insurance/typeEdit" operationClass="EditInsuranceTypeOperation" styleClass="float">
                                                 <phiz:param name="id" value="${listElement.id}"/>
                                                 <c:out value="${listElement.name}"/>
                                             </phiz:link>
                                         </c:when>
                                         <c:otherwise>
                                             <c:out value="${listElement.name}"/>
                                         </c:otherwise>
                                     </c:choose>
                                </c:set>
                                <c:choose>
                                    <c:when test="${empty listElement.parent}">
                                         ${listName}
                                    </c:when>
                                    <c:otherwise>
                                        &nbsp;&nbsp;&nbsp;${listName}
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
                                        return checkSelection("selectedIds", '<bean:message bundle="pfpInsuranceBundle" key="type.label.table.remove.checkSelection"/>');
                                    }
                                    function goToEdit()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpInsuranceBundle" key="type.label.table.edit.checkSelection"/>') ||
                                            (!checkOneSelection("selectedIds", '<bean:message bundle="pfpInsuranceBundle" key="type.label.table.edit.checkOneSelection"/>')))
                                            return;

                                        var url = "${phiz:calculateActionURL(pageContext,'/pfp/insurance/typeEdit')}";
                                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                                        window.location = url + "?id=" + id;
                                    }
                                </c:when>
                                <c:otherwise>
                                    function selectInsuranceType()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpInsuranceBundle" key="type.label.table.select.checkSelection"/>') ||
                                            (!checkOneSelection("selectedIds", '<bean:message bundle="pfpInsuranceBundle" key="type.label.table.select.checkOneSelection"/>')))
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
                                                window.opener.setInsuranceType(a);
                                                window.close();
                                                return true;
                                            }
                                        }
                                        alert("<bean:message bundle="pfpInsuranceBundle" key="type.label.table.select.checkSelection"/>");
                                        return false;
                                    }
                                </c:otherwise>
                            </c:choose>
                        </script>
                        <c:choose>
                            <c:when test="${standalone}">
                                <tiles:insert definition="clientButton" flush="false" operation="EditInsuranceTypeOperation">
                                    <tiles:put name="commandTextKey"    value="type.button.edit"/>
                                    <tiles:put name="commandHelpKey"    value="type.button.edit.help"/>
                                    <tiles:put name="bundle"            value="pfpInsuranceBundle"/>
                                    <tiles:put name="onclick"           value="goToEdit();"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false" operation="RemoveInsuranceTypeOperation">
                                    <tiles:put name="commandKey"            value="button.remove"/>
                                    <tiles:put name="commandTextKey"        value="type.button.remove"/>
                                    <tiles:put name="commandHelpKey"        value="type.button.remove.help"/>
                                    <tiles:put name="bundle"                value="pfpInsuranceBundle"/>
                                    <tiles:put name="validationFunction"    value="doRemove();"/>
                                    <tiles:put name="confirmText"><bean:message bundle="pfpInsuranceBundle" key="type.label.table.removeQuestion"/></tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="clientButton" flush="false" operation="EditInsuranceProductOperation">
                                    <tiles:put name="commandTextKey"    value="type.button.select"/>
                                    <tiles:put name="commandHelpKey"    value="type.button.select.help"/>
                                    <tiles:put name="bundle"            value="pfpInsuranceBundle"/>
                                    <tiles:put name="onclick"           value="selectInsuranceType();"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpInsuranceBundle" key="type.label.tableEmpty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

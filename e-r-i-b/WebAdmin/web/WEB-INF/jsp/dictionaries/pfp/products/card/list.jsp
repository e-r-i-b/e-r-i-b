<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="listPFPCardProduct"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/pfp/products/card/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="editAction" value="/pfp/products/card/edit"/>
    <c:set var="editUrl" value="${phiz:calculateActionURL(pageContext, editAction)}"/>
    <tiles:insert definition="${layout}">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpCardBundle" key="form.list.page.title"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <c:choose>
                <c:when test="${standalone}">
                    <tiles:insert definition="clientButton" flush="false" operation="EditPFPCardOperation">
                        <tiles:put name="commandTextKey"    value="button.add"/>
                        <tiles:put name="commandHelpKey"    value="button.add.help"/>
                        <tiles:put name="bundle"            value="pfpCardBundle"/>
                        <tiles:put name="action"            value="${editAction}"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel"/>
                        <tiles:put name="bundle"            value="pfpCardBundle"/>
                        <tiles:put name="onclick"           value="window.close();"/>
                        <tiles:put name="viewType" value="blueBorder"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="listPFPCardProduct"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpCardBundle">
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <c:set var="boldStyleClass" value=""/>
                        <c:if test="${not empty listElement && listElement.showAsDefault}">
                            <c:set var="boldStyleClass" value="bold"/>
                        </c:if>
                        <sl:collectionItem title="form.list.table.column.name">
                            <span class="${boldStyleClass}">
                            <c:choose>
                                <c:when test="${standalone}">
                                    <c:if test="${not empty listElement}">
                                        <phiz:link action="${editAction}" operationClass="EditPFPCardOperation">
                                            <phiz:param name="id" value="${listElement.id}"/>
                                            <c:out value="${listElement.name}"/>
                                        </phiz:link>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${listElement.name}"/>
                                </c:otherwise>
                            </c:choose>
                            </span>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.column.programmType">
                            <sl:collectionItemParam id="value">
                                <c:if test="${not empty listElement}">
                                    <span class="${boldStyleClass}">
                                        <bean:message bundle="pfpCardBundle" key="type.${listElement.programmType}"/>
                                    </span>
                                </c:if>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                    </sl:collection>
                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            <c:choose>
                                <c:when test="${standalone}">
                                    function goToEdit()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpCardBundle" key="form.list.message.edit.selection.empty"/>') ||
                                           (!checkOneSelection("selectedIds", '<bean:message bundle="pfpCardBundle" key="form.list.message.edit.selection.many"/>')))
                                            return;

                                        var url = "${editUrl}";
                                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                                        window.location = url + "?id=" + id;
                                    }

                                    function doRemove()
                                    {
                                        checkIfOneItem("selectedIds");
                                        return checkSelection("selectedIds", '<bean:message bundle="pfpCardBundle" key="form.list.message.remove.selection.empty"/>');
                                    }
                                </c:when>
                                <c:otherwise>
                                    function selectCard()
                                    {
                                        checkIfOneItem("selectedIds");
                                        if (!checkSelection("selectedIds", '<bean:message bundle="pfpCardBundle" key="form.list.message.edit.selection.empty"/>'))
                                            return false;

                                        var result = new Array();
                                        $('[name=selectedIds]:checked').each(function(){
                                            var self = $(this);
                                            result.push({id: trim(self.val()), name: trim($(self.parents('tr[class^=ListLine]').find('td')[1]).text())});
                                        });
                                        var message = window.opener.setCards(result);
                                        if (message != null)
                                        {
                                            alert(message);
                                            return false;
                                        }
                                        window.close();
                                        return true;
                                    }
                                </c:otherwise>
                            </c:choose>
                        </script>
                        <c:choose>
                            <c:when test="${standalone}">
                                <tiles:insert definition="commandButton" flush="false" operation="RemoveCardOperation">
                                    <tiles:put name="commandKey"         value="button.remove"/>
                                    <tiles:put name="commandHelpKey"     value="button.remove.help"/>
                                    <tiles:put name="bundle"             value="pfpCardBundle"/>
                                    <tiles:put name="confirmText"><bean:message bundle="pfpCardBundle" key="form.list.message.remove.agreement"/></tiles:put>
                                    <tiles:put name="validationFunction" value="doRemove()"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false" operation="EditPFPCardOperation">
                                    <tiles:put name="commandTextKey"    value="button.edit"/>
                                    <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                                    <tiles:put name="bundle"            value="pfpCardBundle"/>
                                    <tiles:put name="onclick"           value="goToEdit();"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.select"/>
                                    <tiles:put name="commandHelpKey"    value="button.select.help"/>
                                    <tiles:put name="bundle"            value="pfpCardBundle"/>
                                    <tiles:put name="onclick"           value="selectCard();"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpCardBundle" key="form.list.message.data.empty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

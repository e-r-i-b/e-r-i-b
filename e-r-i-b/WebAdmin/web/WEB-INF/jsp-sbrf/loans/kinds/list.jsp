<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="loansList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/loans/kinds/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="loansBundle"/>

<tiles:insert definition="${layout}">
	<tiles:put name="pageTitle" type="string">
		<bean:message key="title.kind.list" bundle="${bundle}"/>
	</tiles:put>
	<tiles:put name="submenu" type="string" value="LoanKinds"/>

<tiles:put name="menu" type="string">
    <c:choose>
        <c:when test="${standalone}">
            <tiles:insert definition="clientButton" flush="false" operation="EditLoanKindOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle"         value="${bundle}"/>
                <tiles:put name="action"         value="/loans/kinds/edit.do"/>
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
    <script type="text/javascript">
        function doEdit()
        {
            checkIfOneItem("selectedIds");
            if(!checkSelection("selectedIds", "Выберите вид кредитного продукта"))
                return;
            var id = getFirstSelectedId("selectedIds");
            window.location = '/${phiz:loginContextName()}/loans/kinds/edit.do?id=' + id;
        }
    </script>
	<c:set var="canEdit" value="${phiz:impliesOperation('EditLoanKindOperation','LoanKinds')}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="loanKindsList"/>
        <tiles:put name="buttons">
            <c:choose>
                <c:when test="${standalone}">
            <tiles:insert definition="commandButton" flush="false" operation="RemoveLoanKindOperation">
                <tiles:put name="commandKey"     value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle"         value="${bundle}"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите вид кредитного продукта');
                    }
                </tiles:put>
                <tiles:put name="confirmText"    value="Удалить вид кредитного продукта?"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditLoanKindOperation">
                <tiles:put name="commandTextKey" value="button.edit"/>
                <tiles:put name="commandHelpKey" value="button.edit"/>
                <tiles:put name="bundle"         value="${bundle}"/>
                <tiles:put name="onclick">doEdit();</tiles:put>
            </tiles:insert>
                </c:when>
                <c:otherwise>
                    <script type="text/javascript">
                        function selectLoanKind()
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkSelection("selectedIds", 'Укажите одну запись') ||
                                (!checkOneSelection("selectedIds", 'Укажите только одну запись.')))
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
                                    window.opener.setLoanKind(a);
                                    window.close();
                                    return true;
                                }
                            }
                            return false;
                        }
                    </script>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="button.choose"/>
                        <tiles:put name="commandHelpKey"    value="button.choose"/>
                        <tiles:put name="bundle"            value="commonBundle"/>
                        <tiles:put name="onclick"           value="selectLoanKind();"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>
		<tiles:put name="grid">
			<sl:collection id="item" property="data" model="list">
				<sl:collectionParam id="selectType" value="radio" condition="${canEdit || !standalone}"/>

				<sl:collectionParam id="selectProperty" value="id" condition="${canEdit || !standalone}"/>
				<sl:collectionParam id="selectName" value="selectedIds" condition="${canEdit || !standalone}"/>

				<sl:collectionItem title="Вид продукта" property="name">
					<sl:collectionItemParam id="action" value="/loans/kinds/edit.do?id=${item.id}" condition="${canEdit && standalone}"/>
				</sl:collectionItem>
			</sl:collection>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного вида кредитного продукта!"/>
	</tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>
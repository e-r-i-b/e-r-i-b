<%--
  User: gladishev
  Date: 17.12.2007
  Time: 18:33:04
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/loans/products/list" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="loansList">
	<tiles:put name="pageTitle" type="string">
		<bean:message key="list.products.title" bundle="loansBundle"/>
	</tiles:put>
	<tiles:put name="submenu" type="string" value="LoanProducts"/>

<!--меню-->
<tiles:put name="menu" type="string">

	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/loans/products/edit')}";

		function editLP()
		{
            checkIfOneItem("selectedIds");
			if(!checkSelection("selectedIds", "Выберите кредитный продукт"))
				return;

			var id = getRadioValue(document.getElementsByName("selectedIds"))

			window.location = addUrl + "?id=" + id;
		}
	</script>

	<tiles:insert definition="clientButton" flush="false" operation="EditLoanProductOperation">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add.product.help"/>
		<tiles:put name="bundle"         value="loansBundle"/>
		<tiles:put name="image"          value=""/>
		<tiles:put name="action"         value="/loans/products/edit.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="canEdit" value="${phiz:impliesOperation('EditLoanProductOperation','LoanProducts')}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="loanProductsList"/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false" operation="EditLoanProductOperation">
                <tiles:put name="commandTextKey" value="button.edit"/>
                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                <tiles:put name="bundle"         value="loansBundle"/>
                <tiles:put name="onclick"        value="editLP()"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false" operation="RemoveLoanProductOperation">
                <tiles:put name="commandKey"     value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove.product.help"/>
                <tiles:put name="bundle"         value="loansBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите кредитные продукты');
                    }
                </tiles:put>
                <tiles:put name="confirmText"    value="Удалить выбранные кредитные продукты?"/>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="grid">
			<sl:collection id="item" property="data" model="list">
				<c:set var="kind" value="${form.loanKinds[item.loanKindId]}"/>

				<sl:collectionParam id="selectType" value="checkbox" condition="${canEdit}"/>
				<sl:collectionParam id="selectProperty" value="id" condition="${canEdit}"/>
				<sl:collectionParam id="selectName" value="selectedIds" condition="${canEdit}"/>

				<sl:collectionItem title="Наименование" property="name">
					<sl:collectionItemParam id="action" value="/loans/products/edit.do?id=${item.id}" condition="${canEdit}"/>
				</sl:collectionItem>
				<sl:collectionItem title="Вид кредита" value="${kind}"/>
				<sl:collectionItem title="Описание" property="briefDescription"/>
			</sl:collection>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного кредитного продукта!"/>
	</tiles:insert>
</tiles:put>

</tiles:insert>
</html:form>
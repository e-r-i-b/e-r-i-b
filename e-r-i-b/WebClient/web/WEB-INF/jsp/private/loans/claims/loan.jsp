<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/loans/claim" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="loanMain">
<tiles:put name="mainmenu" value="Loans"/>

<!-- заголовок -->
<tiles:put name="pageTitle" type="string">
	<c:out value="Заявка на кредит"/>
</tiles:put>
<!-- меню -->

<!-- собственно данные -->
<tiles:put name="data" type="string">
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="CreditClaim"/>
		<tiles:put name="name" value="Заявка на кредит"/>
		<tiles:put name="description" value="Подача в банк заявки на получение кредита"/>
		<tiles:put name="buttons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.next"/>
				<tiles:put name="commandHelpKey" value="button.next"/>
				<tiles:put name="bundle" value="paymentsBundle"/>
				<tiles:put name="image" value="iconSm_next.gif"/>
				<tiles:put name="isDefault" value="true"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="data">
			<td class="LabelAll">Офис получения кредита</td>
			<td>
				<c:set var="loanOffiсes" value="${form.loanOffices}"/>
				<html:select property="fields(office)" onchange="refreshProducts()">
					<html:options collection="loanOffiсes" property="synchKey" labelProperty="name"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="LabelAll">Вид кредита</td>
			<td>
				<c:set var="loanKinds" value="${form.loanKinds}"/>
				<html:select property="fields(kind)" onchange="refreshProducts()">
					<html:options collection="loanKinds" property="key" labelProperty="value.name"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="LabelAll">Кредитный продукт</td>
			<td>
				<select id="fields(product)" name="fields(product)"/>
			</td>
		</tr>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>

	<script type="text/javascript">
		var products = new Array();

		<c:set var="loanProducts" value="${form.loanProducts}"/>
		<c:forEach var="product" items="${loanProducts}">
		var product = new Object();
		product.id = '${product.key}';
		product.name = '${product.value.name}';
		product.loankind = '${product.value.loanKindId}';
		product.offises = new Array();
		<c:forEach var="office" items="${product.value.offices}">
		product.offises[product.offises.length] = '${office.synchKey}';
		</c:forEach>
		products[products.length] = product;
		</c:forEach>

		function isEmptyString(value)
		{
			return value == null || value == "";
		}

		function createOptions(array, func, nullOptionText)
		{
			var options = new Array();
			var option;
			var k = 0;
			for (var i = 0; i < array.length; i++)
			{
				option = func(array[i]);
				if (option != null)
				{
					options[options.length] = option;
				}
			}

			if (options.length == 0 && !isEmptyString(nullOptionText))
				options[options.length] = new Option(nullOptionText, "");

			return options;
		}

		function updateSelect(id, options)
		{
			var select = document.getElementById(id);
			select.options.length = 0;

			for (var i = 0; i < options.length; i++)
			{
				select.options[i] = options[i];
			}
		}

		var productCreator = function(product)
		{
			var office = document.getElementsByName("fields(office)")[0].value;
			var kind = document.getElementsByName("fields(kind)")[0].value;
			if (product.loankind != kind)
			{
				return null;
			}
			for (var i = 0; i < product.offises.length; i++)
			{
				if (product.offises[i] == office){
					return new Option(product.name, product.id);
				}
			}
			return null;
		}
		function refreshProducts(){
			updateSelect("fields(product)", createOptions(products, productCreator, "Нет доступных кредитных продуктов"));
		}
		refreshProducts();
	</script>
</tiles:put>
</tiles:insert>
</html:form>

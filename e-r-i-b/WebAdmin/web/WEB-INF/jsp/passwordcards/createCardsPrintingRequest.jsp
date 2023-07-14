<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--<html:form action="/passwordcards/createCardsPrintingRequest" onsubmit="return setEmptyAction(event);">--%>
<html:form action="/passwordcards/createCardsPrintingRequest" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="passwordCards">
<tiles:put name="submenu" value="createCardsPrintingRequest"/>
<tiles:put name="pageTitle" value="Создание запроса на карты ключей"/>
<tiles:put name="data" type="string">
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="createCardsPrintingRequest"/>
		<tiles:put name="name" value="Создание карт"/>
		<tiles:put name="description" value="Используйте данную форму для создания карт ключей."/>
		<tiles:put name="data">
			<tr>
		        <td class="Width160 LabelAll">Количество&nbsp;карт<br>в&nbsp;запросе<span class="asterisk">*</span></td>
			    <td><html:text property="field(cardsCount)" size="4"/></td>
	        </tr>
			<tr>
				<td class="Width160 LabelAll">Количество&nbsp;ключей<br>в&nbsp;карте<span class="asterisk">*</span></td>
				<td><html:text property="field(keysCount)" size="4"/></td>
	        </tr>
			<tr>
				<td class="Width160 LabelAll"><nobr>Подразделение<span class="asterisk">*</span></nobr></td>
		        <td>
			        <html:hidden property="field(id)" value=""/>
					<html:hidden property="field(departmentName)"/>
			        <nobr>
			        <input type="text" readonly="true" style="width:300px" id="departmentDescription"
					       value="${frm.fields.departmentName}"/>
			       		<input type="button" class="buttWhite smButt"
			              onclick="openDepartmentsDictionary(setDepartmentInfo, getElementValue('field(departmentName)'));"
			              value="..."/>
			        </nobr>
		        </td>
	    </tiles:put>
		<tiles:put name="buttons">
		    <tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.download"/>
				<tiles:put name="commandHelpKey" value="button.download"/>
				<tiles:put name="bundle"  value="passwordcardsBundle"/>
				<tiles:put name="isDefault" value="true"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>
	<script>
		   function setDepartmentInfo(result)
		   {
			   var text = document.getElementById('departmentDescription');
			   var desc = '';
			   if (result['name'] != '')desc = result['name'];
			   else desc = result['branch'] + '/' + result['office'];

			   text.value = desc;

			   setElement("field(id)"  , result['id']);
			   setElement("field(departmentName)"  , desc);

		   }

		   function checkData()
		   {
				var count = document.getElementsByName("field(count)")[0];
				var dep = document.getElementById("departmentDescription");

				if (count.value.length == 0)
				{
					alert("Введите значения в поле \"Количество PIN-конвертов\"");
					return false;
				}
			    if (dep.value.length == 0)
				{
					alert("Введите значения в поле \"Подразделение\"");
					return false;
				}
			    return true;
		   }
	   </script>
</tiles:put>
</tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% pageContext.getRequest().setAttribute("mode","PINs");%>
<% pageContext.getRequest().setAttribute("userMode","createPINRequest");%>
<html:form action="/pin/createRequest" onsubmit="return checkData();">
	<tiles:insert definition="pinMain">
		<tiles:put name="needSave" value="false"/>
		<tiles:put name="submenu" type="string" value="Create"/>
		<tiles:put type="string" name="messagesBundle" value="pinBundle"/>
		<tiles:put name="pageTitle" type="string">
			Создание запроса на печать ПИН-конвертов
		</tiles:put>

	<tiles:put name="menu" type="string">
	</tiles:put>

		<tiles:put name="data" type="string">
			<bean:define id="frm" name="CreatePINPrintingRequestForm"/>
			<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="PIN"/>
			<tiles:put name="name" value="Запрос на печать ПИН-конвертов"/>
			<tiles:put name="description" value="Используйте данную форму для создания запроса на печать ПИН-конверта."/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.download"/>
                    <tiles:put name="commandHelpKey" value="button.download"/>
                    <tiles:put name="bundle" value="logBundle"/>
                    <tiles:put name="autoRefresh" value="true"/>
                </tiles:insert>
            </tiles:put>
			<tiles:put name="data">			
			<tr>
				<td class="Width120 LabelAll">&nbsp;</td>
		        <td><nobr>Количество&nbsp;ПИН-конвертов&nbsp;в&nbsp;запросе<span class="asterisk">*</span></nobr></td>
	        </tr>
			<tr>
		        <td class="Width120 LabelAll">&nbsp;</td>
		        <td>
			       <html:text property="field(count)" styleClass="filterInput" size="10"/>
		        </td>
	        </tr>
			<tr>
				<td class="Width120 LabelAll">&nbsp;</td>
		        <td><nobr>Подразделение<span class="asterisk">*</span></nobr></td>
	        </tr>
			<tr>
		        <td class="Width120 LabelAll">&nbsp;</td>
		        <td>
			        <html:hidden property="field(id)"/>
					<html:hidden property="field(departmentName)"/>
			        <nobr>
			        <input type="text" readonly="true" style="width:300px" id="departmentDescription"
					       value="${frm.fields.departmentName}"/>
			       		<input type="button" class="buttWhite smButt"
			              onclick="openDepartmentsDictionary(setDepartmentInfo, getElementValue('field(departmentName)'));"
			              value="..."/>
			        </nobr>
		        </td>
	        </tr>
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
					alert("Введите значения в поле \"Количество ПИН-конвертов\"");
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
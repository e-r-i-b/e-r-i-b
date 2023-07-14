<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 29.08.2008
  Time: 12:32:15
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/dictionaries/editOffice" onsubmit="return setEmptyAction(event);">

<c:set var="frm" value="${EditOfficeForm}"/>
<tiles:insert definition="dictionariesEdit">
	<tiles:put name="submenu" type="string" value="Edit"/>
	<tiles:put name="pageTitle"   type="string" value="Редактирование офиса"/>
	<!--меню-->
	<tiles:put name="menu" type="string">

		<tiles:insert definition="commandButton" flush="false" operation="EditOfficeOperation">
			<tiles:put name="commandKey"     value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="bundle"  value="dictionariesBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"         value="commonBundle"/>
			<tiles:put name="image"          value=""/>
			<tiles:put name="action"         value="/private/dictionary/offices.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

	</tiles:put>

	<tiles:put name="data" type="string">


	<table cellspacing="2" cellpadding="1" style="margin-bottom:2px" class="fieldBorder"
		   onkeypress="onEnterKey(event);">
		<tr>
			<td class="Width120 Label">
				Наименование<span class="asterisk">*</span>
			</td>
		</tr>
	</table>

	</tiles:put>

</tiles:insert>

</html:form>
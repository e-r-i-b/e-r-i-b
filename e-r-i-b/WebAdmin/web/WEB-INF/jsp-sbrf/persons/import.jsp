<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/persons/import" enctype="multipart/form-data">
	
<tiles:insert definition="dictionary">
	<tiles:put name="pageTitle" type="string" value="Импорт клиентов из файла."/>

	<tiles:put name="menu" type="string">
		<tiles:insert definition="commandButton" flush="false">
			 <tiles:put name="commandKey"     value="button.import"/>
			 <tiles:put name="commandHelpKey" value="button.import"/>
			 <tiles:put name="bundle"         value="personsBundle"/>
             <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</tiles:put>

	<tiles:put name="data" type="string">
		<table cellspacing="4" cellpadding="2">
			<tr>
				<td class="LightBlueBG">
					<b><bean:message key="label.person.import.file" bundle="personsBundle"/></b><br>
					<html:file property="xmlFile" style="width:500px;" size="80"/><br>&nbsp;
				</td>
			 </tr>
		 </table>
	</tiles:put>
</tiles:insert>

</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html:form action="/passwordcards/createRequest" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="passwordCards">
<tiles:put name="submenu" value="CreateRequest"/>
<tiles:put name="pageTitle" value="Создание запроса на карты ключей"/>

<tiles:put name="data" type="string">
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="createRequest"/>
		<tiles:put name="name" value="Создание карт"/>
		<tiles:put name="description" value="Используйте данную форму для создания карт ключей."/>
		<tiles:put name="data">
			<tr>
		        <td class="Width160 LabelAll">Количество&nbsp;карт<br>в&nbsp;запросе</td>
			    <td><html:text property="field(cardsCount)" size="4"/></td>
	        </tr>
			<tr>
				<td class="Width160 LabelAll">Количество&nbsp;ключей<br>в&nbsp;карте</td>
				<td><html:text property="field(keysCount)" size="4"/></td>
	        </tr>			
		</tiles:put>
		<tiles:put name="buttons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.createCards"/>
				<tiles:put name="commandHelpKey" value="button.createCards"/>
				<tiles:put name="bundle"  value="personsBundle"/>
				<tiles:put name="isDefault" value="true"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
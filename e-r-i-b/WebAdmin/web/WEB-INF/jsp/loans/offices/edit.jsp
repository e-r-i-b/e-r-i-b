<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/dictionary/offices/loans/edit"  onsubmit="return setEmptyAction(event);">
<tiles:insert definition="loansEdit">
    <tiles:put name="pageTitle" type="string">
        <bean:message key="edit.office.title" bundle="loansBundle"/>
    </tiles:put>
	<tiles:put name="submenu" type="string" value="LoanOffices"/>

    <tiles:put name="menu" type="string">
	    <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.list"/>
			<tiles:put name="commandHelpKey" value="button.list.office.help"/>
			<tiles:put name="bundle"  value="loansBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="action" value="/private/dictionary/offices/loans"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </tiles:put>

	<tiles:put name="data" type="string">
		<html:hidden property="id"/>
		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="creditOffices"/>
			<tiles:put name="name" value="Кредитный офис"/>
			<tiles:put name="description" value="Используйте данную форму редактирования данных кредитного офиса."/>
			<tiles:put name="data">
			<tr>
				<td class="Width120 LabelAll">ID</td>
				<td><html:text  property="field(synchKey)" disabled="true" size="10" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Название кредитного офиса<span class="asterisk">*</span></td>
                <td><html:text property="field(name)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Адрес</td>
				<td><html:text property="field(address)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Телефон</td>
				<td><html:text property="field(telephone)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Примечание</td>
				<td><html:text property="field(description)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
			</tr>
		</tiles:put>
		<tiles:put name="buttons">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.office.help"/>
				<tiles:put name="bundle"  value="loansBundle"/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>

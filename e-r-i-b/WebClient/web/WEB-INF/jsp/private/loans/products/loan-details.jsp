<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form  action="/private/loans/products/details"  onsubmit="return setEmptyAction(event)">

<tiles:insert definition="loanMain">
	<tiles:put name="submenu" type="string" value="loanProducts"/>
 <!-- заголовок -->
 <tiles:put name="pageTitle" type="string">
	 Кредит "<bean:write name="LoanProductDetailsForm" property="name"/>"
 </tiles:put>

 <!-- Меню -->
	<tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.loanProductsList"/>
			<tiles:put name="commandHelpKey" value="button.loanProductsList.help"/>
			<tiles:put name="bundle" value="loansBundle"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="action" value="/private/loans/products/list.do"/>
		</tiles:insert>
	</tiles:put>

 <!-- данные -->
	<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="LoanDetailsList"/>
		<tiles:put name="image" value="iconMid_creditsInfo.gif"/>
		<tiles:put name="text" value='Кредит "${LoanProductDetailsForm.name}"'/>		
		<tiles:put name="data">
			${LoanProductDetailsForm.html}
		</tiles:put>
	</tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>
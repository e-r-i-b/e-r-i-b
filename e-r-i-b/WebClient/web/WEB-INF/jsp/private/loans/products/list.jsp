<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form  action="/private/loans/products/list"  onsubmit="return setEmptyAction(event)">

<tiles:insert definition="loanMain">
	<tiles:put name="submenu" type="string" value="loanProducts"/>
 <!-- заголовок -->
 <tiles:put name="pageTitle" type="string">
	<bean:message key="bankLoanProducts.title" bundle="loansBundle"/>
 </tiles:put>

 <!-- Меню -->

	<!-- данные -->
	<tiles:put name="data" type="string">
		${ListLoanProductForm.listHtml}
    </tiles:put>
</tiles:insert>
</html:form>
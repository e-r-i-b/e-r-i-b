<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form  action="/private/deposits/products/list"  onsubmit="return setEmptyAction(event)">

<tiles:insert definition="depositMain">
	<tiles:put name="submenu" type="string" value="depositProducts"/>
 <!-- заголовок -->
 <tiles:put name="pageTitle" type="string">
	¬клады
 </tiles:put>

 <!-- ћеню -->
	<tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.depositsList"/>
			<tiles:put name="commandHelpKey" value="button.depositsList"/>
			<tiles:put name="bundle" value="depositsBundle"/>
			<tiles:put name="action" value="/private/deposits.do"/>
		</tiles:insert>
    </tiles:put>

	<!-- данные -->
	<tiles:put name="data" type="string">
		${ListDepositProductsForm.listHtml}	
    </tiles:put>
</tiles:insert>
</html:form>
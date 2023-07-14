<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/deposits/edit" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="deposits">
    <tiles:put name="submenu" type="string" value="List"/>
	<tiles:put name="pageTitle" type="string">–едактирование депозитного продукта</tiles:put>

<!--меню-->
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.cancel"/>
		<tiles:put name="commandHelpKey" value="button.cancel.help"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/deposits/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>

<c:set var="form" value="${EditDepositProductBankForm}"/>
<!-- данные -->
<tiles:put name="data" type="string">    
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="editDeposits"/>
		<tiles:put name="text" value="–едактирование депозитного продукта"/>
        <tiles:put name="buttons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"         value="depositsBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("field(selectedIds)");
                        return checkSelection('field(selectedIds)', '¬ыберите виды вкладов');
                    }
                </tiles:put>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="data">
			${form.html}
		</tiles:put>
	</tiles:insert>
</tiles:put>

</tiles:insert>

</html:form>
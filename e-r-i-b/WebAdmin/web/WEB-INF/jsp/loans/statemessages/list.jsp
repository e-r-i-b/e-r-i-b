<%--
  Created by IntelliJ IDEA.
  User: mihaylov
  Date: 21.07.2008
  Time: 11:56:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loans/statemess/list" >

<tiles:insert definition="loansList">
	<tiles:put name="pageTitle" type="string">
		<bean:message key="list.stateMess.title" bundle="loansBundle"/>
	</tiles:put>
	<tiles:put name="submenu" type="string" value="StateMess"/>

<!--меню-->
<tiles:put name="menu" type="string">
	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/loans/statemess/edit')}";

		function editMessage()
		{
            checkIfOneItem("selectedIds");
			if(!checkOneSelection("selectedIds", "Выберите один статус заявки!"))
				return;

			var key = getRadioValue(document.getElementsByName("selectedIds"))

			window.location = addUrl+"?key="+key;
		}
	</script>


</tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="statemessList"/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandHelpKey"     value="button.edit"/>
                <tiles:put name="commandTextKey"     value="button.edit"/>
                <tiles:put name="bundle"             value="loansBundle"/>
                <tiles:put name="onclick" value="editMessage()"/>
            </tiles:insert>
        </tiles:put>
		<tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="properties">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="key"/>

                <sl:collectionItem title="Статус заявки">
                    <sl:collectionItemParam
                            id="action"
                            value="/loans/statemess/edit.do?key=${listElement.key}"
                            condition="${phiz:impliesService('LoanKinds')}"
                            />
                    <sl:collectionItemParam id="value" value="Принята"        condition="${listElement.key == 'claim.state.DISPATCHED.message'}"/>
                    <sl:collectionItemParam id="value" value="Отказана"       condition="${listElement.key == 'claim.state.REFUSED.message'}"/>
                    <sl:collectionItemParam id="value" value="Утверждена"     condition="${listElement.key == 'claim.state.APPROVED.message'}"/>
                    <sl:collectionItemParam id="value" value="В рассмотрении" condition="${listElement.key == 'claim.state.CONSIDERATION.message'}"/>
                    <sl:collectionItemParam id="value" value="Кредит выдан"   condition="${listElement.key == 'claim.state.EXECUTED.message'}"/>
                    <sl:collectionItemParam id="value" value="Требуется доработка" condition="${listElement.key == 'claim.state.COMPLETION.message'}"/>
                </sl:collectionItem>
                <sl:collectionItem title="Текст сообщения" name="listElement" property="value"/>
            </sl:collection>
		</tiles:put>

        <tiles:put name="isEmpty" value="${empty ListLoanStateMessagesForm.properties}"/>
        <tiles:put name="emptyMessage" value="Не найдено ни одного сообщения о результатах рассмотрения заявки в банке."/>
    </tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
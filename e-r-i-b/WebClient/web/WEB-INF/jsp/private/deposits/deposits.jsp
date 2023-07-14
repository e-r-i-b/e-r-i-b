<%--
  User: IIvanova
  Date: 16.05.2006
  Time: 13:04:53
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/private/deposits" onsubmit="return checkData();">
<tiles:insert definition="depositMain">
	<tiles:put name="pageTitle" type="string">
		������
	</tiles:put>
	<tiles:put name="submenu" type="string" value="deposits"/>
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.sum"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="name" value="Sum"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.duration"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(duration)" styleClass="select">
				<html:option value="">���</html:option>
				<html:option value="30">�� 30 ����</html:option>
				<html:option value="90">�� 31 �� 90 ����</html:option>
				<html:option value="180">�� 91 �� 180 ����</html:option>
				<html:option value="360">�� 181 ���� �� 1 ����</html:option>
				<html:option value="1080">�� 1 �� 3 ���</html:option>
				<html:option value="more">����� 3 ���</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.state"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="select">
				<html:option value="">���</html:option>
				<html:option value="O">��������</html:option>
				<html:option value="C">��������</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.openDate"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="name" value="OpenDate"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.depositType"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(depositType)" styleClass="select">
				<html:option value="">���</html:option>
				<logic:iterate id="kind" name="DepositsForm" property="depositKinds">
					<html:option value="${kind}">${kind}</html:option>
				</logic:iterate>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.sumCurrency"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="data">
			<html:select property="filter(sumCurrency)" styleClass="select">
				<html:option value="">���</html:option>
				<html:option value="RUB">RUB</html:option>
				<html:option value="USD">USD</html:option>
				<html:option value="EUR">EUR</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.closeDate"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="name" value="CloseDate"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>

<script type="text/javascript">
	function initTemplates()
	{
	}
	function clearMasks(event)
	{
		clearInputTemplate("filter(fromOpenDate)", DATE_TEMPLATE);
		clearInputTemplate("filter(toOpenDate)", DATE_TEMPLATE);
		clearInputTemplate("filter(fromCloseDate)", DATE_TEMPLATE);
		clearInputTemplate("filter(toCloseDate)", DATE_TEMPLATE);
	}

	function checkData()
	{
		if (!checkSumRange("filter(fromSum)", "filter(toSum)", "����� �", "����� ��")) return false;
		if (!checkPeriod("filter(fromOpenDate)", "filter(toOpenDate)", "���� �������� �", "���� �������� ��")) return false;
		return (checkPeriod("filter(fromCloseDate)", "filter(toCloseDate)", "���� �������� �", "���� �������� ��"));
	}
	function submit(event)
	{
		var frm = window.opener.document.forms.item(0);
		frm.action = '';
		frm.submit();
	}
	initTemplates();
</script>
</tiles:put>

<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add.help"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="action" value="/private/claims/claim.do?form=DepositOpeningClaim"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.replenish"/>
		<tiles:put name="commandHelpKey" value="button.replenish.help"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="action" value="/private/claims/claim.do?form=DepositReplenishmentClaim"/>
	</tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.products"/>
		<tiles:put name="commandHelpKey" value="button.products.help"/>
		<tiles:put name="bundle" value="depositsBundle"/>
		<tiles:put name="action" value="/private/deposits/products/list.do"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">

<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="DepositsTable"/>
	<tiles:put name="image" value="iconMid_deposits.gif"/>
	<tiles:put name="text" value="������"/>
	<tiles:put name="buttons">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.remove"/>
			<tiles:put name="commandHelpKey" value="button.remove.help"/>
			<tiles:put name="image" value="iconSm_delete.gif"/>
			<tiles:put name="bundle" value="depositsBundle"/>
			<tiles:put name="action" value="/private/claims/claim.do?form=DepositClosingClaim"/>
		</tiles:insert>
	</tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="list" property="data" selectBean="deposit">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <c:set var="deposit" value="${listElement.deposit}"/>

            <sl:collectionItem title="��� ������" name="deposit" property="description"/>
            <sl:collectionItem width="60px" title="�����">
                &nbsp;
                <c:if test="${not empty deposit.amount}">
				     <bean:write name="deposit" property="amount.decimal" format="0.00"/>
				</c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="40px" title="������">
                &nbsp;
                <c:if test="${not empty deposit.amount}">
				     <bean:write name="deposit" property="amount.currency.code"/>
				  </c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="60px" title="����">
                &nbsp;
                <c:if test="${not empty deposit.duration}">
			         <bean:write name="deposit" property="duration"/>&nbsp;����
				</c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="40px" title="%" name="deposit" property="interestRate"/>
            <sl:collectionItem width="100px" title="���� ��������">
                &nbsp;
                <c:if test="${not empty deposit.openDate}">
				     <bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>
				</c:if>
                &nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="100px" title="���� ���������">
                &nbsp;
                <c:if test="${(not empty deposit.openDate) && (not empty deposit.duration)}">
				     <c:set var="closeDate" value="${phiz:addToDate(deposit.openDate.time, 0,0, deposit.duration)}"/>
				     <bean:write name="closeDate" format="dd.MM.yyyy"/>
				</c:if>
				&nbsp;
            </sl:collectionItem>
            <sl:collectionItem width="60px" title="�����c">
                <sl:collectionItemParam id="value" value="������" condition="${deposit.state=='open'}"/>
                <sl:collectionItemParam id="value" value="������" condition="${deposit.state=='closed'}"/>
            </sl:collectionItem>
            <sl:collectionItem width="120px" title="�������">
                &nbsp;
                <c:if test="${not empty listElement.depositInfo.agreementNumber}">
				     <bean:write name="listElement" property="depositInfo.agreementNumber"/>
				</c:if>
                &nbsp;
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="emptyMessage" value="�� ������� �� ������ ������,<br/>���������������� ��������� �������!"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
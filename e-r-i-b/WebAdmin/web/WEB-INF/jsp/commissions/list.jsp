<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/commissions/list" onsubmit="return setEmptyAction(event);">

<tiles:insert definition="commissionList">
	<tiles:put name="pageTitle" type="string">������ ������������� �����</tiles:put>

<!--����-->
<tiles:put name="menu" type="string">

	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandHelpKey"     value="button.edit"/>
		<tiles:put name="commandTextKey"     value="button.edit"/>
		<tiles:put name="bundle"             value="commonBundle"/>
		<tiles:put name="image"              value=""/>
		<tiles:put name="onclick">goEdit()</tiles:put>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>

</tiles:put>
<!-- ������ -->
<tiles:put name="data" type="string">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="rulesByType" value="${form.rulesByType}"/>

	<sl:collection model="list"
				   property="types"
				   id="type"
				   bundle="commonBundle"
				   emptyKey="list.empty"
				   selectName="selectedIds"
				   selectProperty="id">

		<sl:collectionItem title="��� ��������">
			<html:link styleId="lnk${type.id}" action="/commissions/edit?id=${type.id}">
				<c:out value="${type.name}"/>
			</html:link>
		</sl:collectionItem>
		<sl:collectionItem title="��������">
			<c:set var="rules" value="${rulesByType[type.key]}"/>

			<c:choose>
				<c:when test="${fn:length(rules) == 0}"/>
				<c:when test="${rules[0]['class'].name == 'com.rssl.phizic.business.commission.GateRule'}">
					�� RS-Retail
				</c:when>
				<c:when test="${rules[0]['class'].name == 'com.rssl.phizic.business.commission.FixedRule'}">
					������������� �����
					<c:forEach var="rule" items="${rules}">
						<li>
							<c:out value="${rule.currencyCode}: ${rule.amount}"/>
						</li>
					</c:forEach>
				</c:when>
				<c:when test="${rules[0]['class'].name == 'com.rssl.phizic.business.commission.RateRule'}">
					% �� ����� �������
					<c:forEach var="rule" items="${rules}">
						<li>
							<c:out value="${rule.currencyCode}: ${rule.rate}% ���:${rule.minAmount} ����:${rule.maxAmount}"/>
						</li>
					</c:forEach>
				</c:when>
			</c:choose>
		</sl:collectionItem>

	</sl:collection>

	<script type="text/javascript">
		function goEdit()
		{
            checkIfOneItem("selectedIds");
			if (!checkOneSelection('selectedIds', '�������� ���� ��������'))
				return;

			var id = getFirstSelectedId("selectedIds");
			var link = document.getElementById("lnk" + id);
			window.location = link.href;
		}
	</script>
</tiles:put>

</tiles:insert>

</html:form>
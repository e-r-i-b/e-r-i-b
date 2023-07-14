<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/deposit/print">
<c:set var="deposit" value="${PrintDepositInfoForm.deposit}"/>
<bean:define id="depositInfo" name="PrintDepositInfoForm" property="depositInfo"/>
<c:set var="client" value="${PrintDepositInfoForm.fullNameClient}"/>

<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">
<br/>
<table width="100%">
	<tr>
		<td>���������� �� ������ <b><c:out value="${deposit.description}"/></b> � ��� "�������" ���</td>
	</tr>
	<tr>
		<td>������: <bean:write name="client"/></td>
	</tr>
	<tr>
		<td>����: <b><%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%></b></td>
	</tr>
	<tr>
		<td>
			<br/>
		</td>
	</tr>
	<tr>
		<td>
			<br/>
		</td>
	</tr>
	<tr>
		<td>
			<table cellspacing="0" cellpadding="0" class="userTab">
				<tr>
					<td class="ListItem">��� ������</td>
					<td class="ListItem">
						<bean:write name="deposit" property="description"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">�����</td>
					<td class="ListItem">
						<nobr>
							<bean:write name="deposit" property="amount.decimal" format="0.00"/>&nbsp;
						</nobr>
					</td>
				</tr>
				<tr>
					<td class="ListItem">������ ������</td>
					<td class="ListItem">
						<bean:write name="deposit" property="amount.currency.code"/>
					</td>
				</tr>
				<tr>
					<td class="ListItem">����</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${not empty deposit.duration}">
								<bean:write name="deposit" property="duration"/>
								����&nbsp;
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="ListItem">������</td>
					<td class="ListItem">
							<bean:write name="deposit" property="interestRate"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">����� �����</td>
					<td class="ListItem">
						<bean:write name="depositInfo" property="account.number"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">���������������� ������</td>
					<td class="ListItem">
						<bean:define id="renewal" name="depositInfo" property="renewalAllowed"/>
						<c:choose>
						<c:when test="${renewal}">
						��������������
						</c:when>
						<c:otherwise>
						����������������
						</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">����� ������������ �������</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${!empty depositInfo.minBalance}">
								<bean:write name="depositInfo" property="minBalance.decimal"/>&nbsp;
							</c:when>
							<c:otherwise>
								<bean:write name="deposit" property="amount.decimal" format="0.00"/>&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="ListItem">���� ��� ������������ ������������ ���������</td>
					<td class="ListItem">
						<c:if test="${!empty depositInfo.percentAccount}">
							<bean:write name="depositInfo" property="percentAccount.number"/>&nbsp;
						</c:if>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">���� ��� ���������� ������������</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${not empty PrintDepositInfoForm.accountsSet}">
								<logic:iterate id="entry" name="PrintDepositInfoForm" property="accountsSet">
									<bean:define id="account" name="entry" property="key"/>
									<bean:write name="account" property="number"/>&nbsp;
								</logic:iterate>
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="ListItem">���������</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${deposit.state=='open'}">
								������
							</c:when>
							<c:otherwise>
								������
							</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">���� ��������</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${not empty deposit.openDate}">
								<bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>&nbsp;
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="ListItem">���� ��������</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${deposit.state=='closed'}">
								<c:if test="${!empty deposit.closeDate}">
									<bean:write name="deposit" property="closeDate.time" format="dd.MM.yyyy"/>
								</c:if>
							</c:when>
							<c:otherwise>
								<bean:write name="deposit" property="endDate.time" format="dd.MM.yyyy"/>
							</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">����� ��������</td>
					<td class="ListItem">
						<bean:write name="depositInfo" property="agreementNumber"/>
						&nbsp;
					</td>
				</tr>				
			</table>
		</td>
	</tr>
</table>
</tiles:put>
</tiles:insert>
</html:form>
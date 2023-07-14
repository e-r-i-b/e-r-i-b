<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="com.rssl.phizic.gate.bankroll.AccountTransaction" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 08.12.2006
  Time: 11:43:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/accounts/print">

<tiles:insert definition="printDoc">
	<tiles:put name="data" type="string">
	<!--������� �� ������ -->
	<%int lineNumber; %>
	<c:forEach items="${PrintAccountAbstractForm.accountAbstract}" var="listElement">
		<c:set var="account" value="${listElement.key}"/>
		<c:set var="abstract" value="${listElement.value}"/>
		____________________________________________________________________________________________________<br/>

		<span class="filter">������� �� ����� � </span><b>
		<c:out value="${account.value.number}"/>
	</b>
		<nobr>
			<span class="filter">�� ����</span>
			<b>
				�<u><%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>
			</u>�<u>&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
				&nbsp;</u>&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
				&nbsp;�.
			</b>
			<span class="filter">(�� ������ �</span>
			<b>
				�<u>
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd"/>
			</u>
				�<u>&nbsp;<span id=monthStrFrom></span>&nbsp;</u>&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="yyyy"/>
				&nbsp;�.
			</b>
			<span class="filter">��</span>
			<b>
				�<u>
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd"/>
			</u>
				�<u>&nbsp;<span id=monthStrTo></span>&nbsp;</u>&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="yyyy"/>
				&nbsp;�.
			</b>
			<span class="filter">)</span>
		</nobr>
		<br/>
		<br/>
		������ �����:
		<c:out value="${account.value.currency.code}"/>
		<br/>
		<br/>
		�������� �������:&nbsp;
		<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
		<br/>
		<c:choose>
		<c:when test="${phiz:size(abstract.transactions)==0}">
			<span class="messageTab">
				��� �������� �� ����� �� �������� ������.
			</span>
		</c:when>
		<c:otherwise>
		<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
			<tr class="titleTable" bgcolor="#aca7a7">
				<td height="20px" align="center" valign="middle">���� ��������</td>
				<td align="center" valign="middle">����� �������</td>
				<td align="center" valign="middle">����� �������</td>
				<td align="center" valign="middle">�������������</td>
				<td align="center" valign="middle">����������������� ����</td>
				<td align="center" valign="middle">���� ��������������</td>
				<td align="center" valign="middle">���������� �������</td>
			</tr>
			<%lineNumber = 0;%>
			<logic:iterate id="transaction" name="abstract" property="transactions">
				<% lineNumber++; %>
				<tr class="ListLine<%=lineNumber%2%>">
					<td class="ListItem" align="center" valign="center">
                      <nobr>
						<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="right" valign="center">
                      <nobr>
						<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="right" valign="center">
                      <nobr>
						<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
                      <nobr>
						<bean:write name="transaction" property="counteragent"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
                      <nobr>
						<bean:write name="transaction" property="counteragentAccount"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
                      <nobr>
						<bean:write name="transaction" property="counteragentBank"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
		</c:otherwise>
		</c:choose>
		<br/>
		��������� �������:&nbsp;
		<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
		<br/>
		<br/>
	</c:forEach>
	<!--������� �� ������ -->
	<c:forEach items="${PrintAccountAbstractForm.cardAbstract}" var="listElement" >
	<c:set var="cardlink" value="${listElement.key}"/>
	<c:set var="card" value="${cardlink.value}"/>
	<c:set var="cardaccount" value="${cardlink.cardAccount}"/>
	<c:set var="currentOverdraftInfo" value="${cardlink.currentOverdraftInfo}"/>
	<c:set var="abstr" value="${listElement.value}"/>
	____________________________________________________________________________________________________<br/>

	<c:if test="${card.main}">
		<span >����: ��� "�������" ���</span>
		<br/>
		<span style="text-align:center;width:100%">������� �� �����</span>
		<br/>
	</c:if>
	<c:if test="${not card.main}">
		<span class="filter">������ �������� ����� </span>
	</c:if>


	<table width="100%">
		<tr>
			<td style="text-align:left;width:20%">
				��� ���������:
			</td>
			<td style="text-align:left;width:80%">
				<c:out value="${cardlink.cardClient.fullName}"/>
			</td>
		</tr>
		<tr>
			<td style="text-align:left;width:20%">
			    ����� �����:
			</td>
			<td style="text-align:left;width:20%">
				<c:set var="num" value="${card.number}"/>
				<c:set var="len" value="${fn:length(num)}"/>
				<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
				&nbsp;[<c:out value="${card.description}"/>]
			</td>
		</tr>
		<tr>
			<c:if test="${card.main}">
				<td style="text-align:left;width:20%">
					����� �������� �����:
				</td>
				<td style="text-align:left;width:20%">
					<c:out value="${cardlink.cardAccount.number}"/>
				</td>
			</c:if>
		</tr>
		<tr>
			<td style="text-align:left;width:20%">
			   	������:
			</td>
			<td style="text-align:left;width:20%">
				c:&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>&nbsp;
				�.&nbsp;��&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>&nbsp;
			    �.
			</td>
		</tr>
	</table>
	<br/>�������� �������:&nbsp;
		<bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/>
	<br/>
	<c:choose>
		<c:when test="${phiz:size(abstr.unsettledOperations)==0 && phiz:size(abstr.transactions)==0}">
			<span class="messageTab">
			    ��� �������� �� ����� �� �������� ������.
			</span>
		</c:when>
		<c:otherwise>
				<table border="1" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
					<tr  bgcolor="#aca7a7">
						<td height="20px" align="center">���� ��������</td>
						<td height="20px" align="center">���� ��������</td>
						<td align="center">������</td>
						<td align="center">������</td>
						<td align="center">����� � ������ �����</td>
						<td align="center">���</td>
						<td align="center">����� ��������</td>
						<td align="center">���</td>
						<td align="center">���������� ��������</td>
					</tr>
					<%lineNumber = 0;%>
					<logic:iterate id="transaction" name="abstr" property="transactions" type="com.rssl.phizic.gate.bankroll.CardOperation">
						<c:set var="sum" value="${transaction.creditSum.decimal + transaction.debitSum.decimal}"/>
						<% lineNumber++; %>
						<tr style="border:1px solid black;vertical-align:middle;">
							<td class="ListItem" align="center" valign="center">
                              <nobr>
								<c:if test="${not empty transaction.date.time}">
									<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
								</c:if>
								&nbsp;
                              </nobr>
							</td>
							<td class="ListItem" align="center" valign="center">
                              <nobr>
								<c:if test="${not empty transaction.operationDate.time}">
									<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
								</c:if>
								&nbsp;
                              </nobr>
							</td>
							<td class="ListItem" align="right" valign="center">
                              <nobr>
								<c:if test="${not empty transaction.creditSum}">
									<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
								</c:if>
								&nbsp;
                              </nobr>
							</td>
							<td class="ListItem" align="right" valign="center">
                              <nobr>
								<c:if test="${not empty transaction.debitSum}">
									<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
								</c:if>
								&nbsp;
                              </nobr>
							</td>

							<td class="ListItem" align="center">
                              <nobr>
								<bean:write name="sum"  format="0.00"/>
								&nbsp;
                              </nobr>
							</td>

							<td class="ListItem" align="center">
                              <nobr>
								<c:if test="${not empty transaction.debitSum}">
									<bean:write name="transaction" property="debitSum.currency.code" format="0.00"/>
								</c:if>
								&nbsp;
                              </nobr>
							</td>
							<td class="ListItem" align="center">
                              <nobr>
									<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
								&nbsp;
                              </nobr>
							</td>
							<td class="ListItem" align="center">
                              <nobr>
									<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
								&nbsp;
                              </nobr>
							</td>
							<td class="ListItem" valign="center">
								<bean:write name="transaction" property="description"/>
								&nbsp;
							</td>
						</tr>
					</logic:iterate>
				</table>
		</c:otherwise>
	</c:choose>
	<c:if test="${card.main}">
		<br/>��������� �������:&nbsp;
		<bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>&nbsp;<bean:write name="cardaccount" property="currency.code"/>
		<br/>
		<br/>
		<c:if test="${phiz:size(abstr.unsettledOperations)>0}">
			<span class="filter">��������, �� ������� ��������� ��������:</span>
			<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
				<tr class="titleTable" bgcolor="#aca7a7">
					<td height="20px" align="center">���� ��������</td>
					<td align="center">����� � ������ �����</td>
					<td align="center">����� ������</td>
					<td align="center">��� ������</td>
					<td align="center">���������� ��������</td>
				</tr>
				<%lineNumber = 0;%>
				<logic:iterate id="transaction" name="abstr" property="unsettledOperations" type="com.rssl.phizic.gate.bankroll.CardOperation">
					<c:set var="istrndebit" value="${transaction.creditSum.decimal + transaction.debitSum.decimal == transaction.debitSum.decimal}"/>
					<% lineNumber++; %>
					<tr class="ListLine<%=lineNumber%2%>">
						<td class="ListItem" align="center" valign="center">
                          <nobr>
							<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
							&nbsp;
                          </nobr>
						</td>

						<td class="ListItem" align="center">
                          <nobr>
							<c:if test="${istrndebit}">
								<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
							</c:if>
							<c:if test="${not istrndebit}">
								<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
							</c:if>
							&nbsp;
                          </nobr>
						</td>
						<td class="ListItem" align="center">
                          <nobr>
							<c:if test="${istrndebit}">
								<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
							</c:if>
							<c:if test="${not istrndebit}">
								<bean:write name="transaction" property="accountCreditSum.decimal" format="0.00"/>
							</c:if>
							&nbsp;
                          </nobr>
						</td>
						<td class="ListItem" align="center">
                          <nobr>                            
							<c:if test="${istrndebit}">
								<bean:write name="transaction" property="accountDebitSum.currency.code" format="0.00"/>
							</c:if>
							<c:if test="${not istrndebit}">
								<bean:write name="transaction" property="accountCreditSum.currency.code" format="0.00"/>
							</c:if>
							&nbsp;
                          </nobr>
						</td>
						<td class="ListItem" valign="center">
							<bean:write name="transaction" property="description"/>
							&nbsp;
						</td>
					</tr>
				</logic:iterate>
			</table>
			<br/>
		</c:if>

		<c:if test="${card.cardType=='overdraft'}">
			<span>�������� �� ���������� � �������:</span>
			<table cellspacing="0" cellpadding="0" class="userTab" width="60%" align="left">
				<tr class="ListLine0">
					<td class="ListItem">���� �������� ����������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="openDate.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine1">
					<td class="ListItem">����� ����������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="limit.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine0">
					<td class="ListItem">����� ������������� �����</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="unsettledDebtSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine1">
					<td class="ListItem">����� ������ �� ���������</td>
					<td class="ListItem">
						<c:if test="${not empty currentOverdraftInfo.unsettledPenalty}">
							<bean:write name="currentOverdraftInfo" property="unsettledPenalty.decimal" format="0.00"/>
							&nbsp;
						</c:if>
					</td>
				</tr>
				<tr class="ListLine0">
					<td class="ListItem">����� �������� ����������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="currentOverdraftSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine1">
					<td class="ListItem">����� ��������� �� ����������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="rate.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine0">
					<td class="ListItem">����� ������������ ����������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="technicalOverdraftSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine1">
					<td class="ListItem">����� ������ �� ����������� ���������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="technicalPenalty.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine0">
					<td class="ListItem">����� ����� �����</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="totalDebtSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine1">
					<td class="ListItem">���� ������ ����� �� ���������</td>
					<td class="ListItem">
						<c:if test="${not empty currentOverdraftInfo.unsetltedDebtCreateDate}">
							<bean:write name="currentOverdraftInfo" property="unsetltedDebtCreateDate.decimal" format="dd.MM.yyyy"/>
						</c:if>
						&nbsp;
					</td>
				</tr>
				<tr class="ListLine0">
					<td class="ListItem">����� ���������� ������</td>
					<td class="ListItem">
						<bean:write name="currentOverdraftInfo" property="availableLimit.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
			</table>
		</c:if>
	</c:if>
	<br/><br/><br/><br/><br/>
	<span style="text-align:center;width:100%">
		��������� �������� �����!    <br/>
		�������� ���, ��� ���� �������� ����� ����� ������������� <bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/> �. <br/>
		��� ������ �����, ���������� ��� �������� ���� �� 5 ����                         <br/>
		�� ��������� ����� �� ��������.                            <br/>
		 <br/>

		� ������ ���������� � ��������� � ������� ����� �������� ��������� ����������� � ����    <br/>
		�� ������� 30 ����������� ���� �� ��� ��������. ���������, ����������� � ���� �� ���������   <br/>
		30 ����������� ���� �� ��� ��������, ������ �� ���������������.                           <br/>
	</span>
        <br/><br/><br/><br/>
	</c:forEach>

	<!--������� �� ������� -->
	<c:forEach items="${PrintAccountAbstractForm.depositAbstract}" var="listElement">
		<c:set var="depositLink" value="${listElement.key}"/>
		<c:set var="abstract" value="${listElement.value}"/>
		____________________________________________________________________________________________________<br/>

		<span class="filter">������� �� ����� � </span><b>
		<c:out value="${depositLink.depositInfo.account.number}"/>
	</b>
		<nobr>
			<span class="filter">�� ����</span>
			<b>
				�<u><%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%>
			</u>�<u>&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%>
				&nbsp;</u>&nbsp;<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>
				&nbsp;�.
			</b>
			<span class="filter">(�� ������ �</span>
			<b>
				�<u>
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd"/>
			</u>
				�<u>&nbsp;<span id=monthStrFrom></span>&nbsp;</u>&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="yyyy"/>
				&nbsp;�.
			</b>
			<span class="filter">��</span>
			<b>
				�<u>
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd"/>
			</u>
				�<u>&nbsp;<span id=monthStrTo></span>&nbsp;</u>&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="yyyy"/>
				&nbsp;�.
			</b>
			<span class="filter">)</span>
		</nobr>
		<br/>
		<br/>
		������ �����:
		<c:out value="${depositLink.depositInfo.account.currency.code}"/>
		<br/>
		<br/>
		�������� �������:&nbsp;
		<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
		<br/>
		<table class="userTab" id="listTbl" cellspacing="0" cellpadding="0" width="100%">
			<tr class="titleTable" bgcolor="#aca7a7">
				<td height="20px" align="center" valign="middle">���� ��������</td>
				<td align="center" valign="middle">����� �������</td>
				<td align="center" valign="middle">����� �������</td>
				<td align="center" valign="middle">�������������</td>
				<td align="center" valign="middle">����������������� ����</td>
				<td align="center" valign="middle">���� ��������������</td>
				<td align="center" valign="middle">���������� �������</td>
			</tr>
			<%lineNumber = 0;%>
			<logic:iterate id="transaction" name="abstract" property="transactions">
				<% lineNumber++; %>
				<tr class="ListLine<%=lineNumber%2%>">
					<td class="ListItem" align="center" valign="center">
                      <nobr>
						<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="right" valign="center">
                      <nobr>
						<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" align="right" valign="center">
                      <nobr>
						<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
                      <nobr>
						<bean:write name="transaction" property="counteragent"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
                      <nobr>
						<bean:write name="transaction" property="counteragentAccount"/>
						&nbsp;
                      </nobr>
					</td>
					<td class="ListItem" valign="center">
                      <nobr>                        
						<bean:write name="transaction" property="counteragentBank"/>
						&nbsp;
                      </nobr>                        
					</td>
					<td class="ListItem" valign="center">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
		<br/>
		��������� �������:&nbsp;
		<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
		<br/>
		<br/>
	</c:forEach>
	<script>
		var monthStrFrom = document.all('monthStrFrom');
		var monthStrTo = document.all('monthStrTo');
		var fromDate = '<bean:write name='PrintAccountAbstractForm' property="fromDateString"/>';
		var toDate = '<bean:write name='PrintAccountAbstractForm' property="toDateString"/>';
		if (monthStrFrom && monthStrTo)
			if (monthStrFrom.length > 0)
				for (var i = 0; i < monthStrFrom.length; i++)
				{
					monthStrFrom[i].innerHTML = monthToStringOnly(fromDate);
					monthStrTo[i].innerHTML = monthToStringOnly(toDate);
				}
			else
			{
				monthStrFrom.innerHTML = monthToStringOnly(fromDate);
				monthStrTo.innerHTML = monthToStringOnly(toDate);
			}
	</script>
	</tiles:put>
</tiles:insert>
</html:form>
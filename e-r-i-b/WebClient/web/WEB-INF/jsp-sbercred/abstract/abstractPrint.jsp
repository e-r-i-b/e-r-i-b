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
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
	<tiles:put name="data" type="string">
	<!-- ����� -->
	<table id="logoTbl" cellspacing="0" cellpadding="0" width="100%" style="font-size:9pt">
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="3" align="center">
			<img src="${globalImagePath}/logoBank.gif" >
		</td>
	</tr>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="3" align="left">
			<b>�������������� �������� ���������� ������ ����� (495) 785-5160, (495) 135-4312</b>
		</td>
	</tr>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr>
		<td align="center">����������� ������������</td>
		<td colspan="2">&nbsp;</td>
	<tr>
		<td align="center">���� ���������� � ������� (���)</td>
		<td align="center">����:&nbsp;</td>
		<td align="left"><b>${PrintAccountAbstractForm.user.residenceAddress}</b></td>
	</tr>
	<tr>
		<td align="center">�.������</td>
		<td colspan="2">&nbsp;</td>
	<tr>
		<td>&nbsp;</td>
		<td align="center">����:&nbsp;</td>
		<td align="left">
			<b>${PrintAccountAbstractForm.user.surName}&nbsp;
				${PrintAccountAbstractForm.user.firstName}&nbsp;
				 ${PrintAccountAbstractForm.user.patrName}</b>
		</td>
	</tr>
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	</table>


	<!--������� �� ������ -->
	<%int lineNumber; %>
	<c:forEach items="${PrintAccountAbstractForm.accountAbstract}" var="listElement">
		<c:set var="account" value="${listElement.key}"/>
		<c:set var="abstract" value="${listElement.value}"/>

		<span style="font-size:9pt;">��� "���� ���������� � �������" ���, �. ������</span>
		<br/>
		<span style="padding-left:90px;font-size:9pt;">
		 	������� �� ����� � <c:out value="${account.number}"/><br/>
		</span>
		<span style="padding-left:90px;font-size:9pt;">
			�� ������ c:&nbsp;
			<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>&nbsp;
			�.&nbsp;��&nbsp;
			<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>&nbsp;�.
		</span>
		</br>
		<span style="font-size:9pt;">
		��������:&nbsp;<bean:write name='PrintAccountAbstractForm' property="user.surName"/>
			     &nbsp;<bean:write name='PrintAccountAbstractForm' property="user.firstName"/>
			     &nbsp;<bean:write name='PrintAccountAbstractForm' property="user.patrName"/><br/>
		��������:&nbsp;

		<logic:iterate id="document" name="PrintAccountAbstractForm" property="user.personDocuments" >
			<c:if test="${document.documentMain}">
		        <c:choose>
					<c:when test="${document.documentType == 'REGULAR_PASSPORT_RF'}">
						��������������� ������� ��
					</c:when>
					<c:when test="${document.documentType == 'MILITARY_IDCARD'}">
						������������� �������� ���������������
					</c:when>
					<c:when test="${document.documentType == 'SEAMEN_PASSPORT'}">
						������� ������
					</c:when>
					<c:when test="${document.documentType == 'RESIDENTIAL_PERMIT_RF'}">
						��� �� ���������� ��
					</c:when>
					<c:when test="${document.documentType == 'FOREIGN_PASSPORT_RF'}">
						����������� ������� ��
					</c:when>
					<c:when test="${document.documentType == 'OTHER'}">
						���� ��������
					</c:when>
				</c:choose>
			    &nbsp;<bean:write name='document' property="documentSeries"/>
				&nbsp;<bean:write name='document' property="documentNumber"/>;
				&nbsp;<bean:write name='document' property="documentIssueBy"/>
			</c:if>
		</logic:iterate>
		<br/>
		���� ������:&nbsp;<bean:write name='account' property="openDate.time" format="dd.MM.yyyy"/><br/>
		���� ��������� ��������:&nbsp;<fmt:formatDate value="${abstract.previousOperationDate.time}" pattern="dd.MM.yyyy"/><br/>
		<br/>
		�������� �������:&nbsp;&nbsp;<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
		<br/>
		<table id="listTbl" cellspacing="0" cellpadding="0" width="70%" style="border:1px;font-size:9pt;">
			<tr>
				<td class="bdCellFirst bdCell" align="center" valign="middle" height="20px" rowspan="2"><b>&nbsp;� ���.&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" height="20px" rowspan="2"><b>&nbsp;���� ��������&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;���������� ��������&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;����������������� �����&nbsp;</b></td>
				<%--<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;���&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;������������ �����&nbsp;</b></td>--%>
				<td class="bdCell" align="center" valign="middle" colspan="2"><b>&nbsp;����� � ������ �����&nbsp;</b></td>
			</tr>
			<tr>
			    <td class="bdCellLast" align="center" valign="middle"><b>&nbsp;������&nbsp;</b></td>
				<td class="bdCellLast" align="center" valign="middle"><b>&nbsp;������&nbsp;</b></td>
			</tr>
			<c:set var="debit" value="0"/>
			<c:set var="credit" value="0"/>
			<logic:iterate id="transaction" name="abstract" property="transactions">
				<c:set var="debit" value="${debit + transaction.debitSum.decimal}"/>
				<c:set var="credit" value="${credit + transaction.creditSum.decimal}"/>
				<tr>
					<td class="bdCellFirst bdCellLast" align="center" valign="center">
						<bean:write name="transaction" property="documentNumber"/>
						&nbsp;
					</td>
					<td class="bdCellLast" align="center" valign="center">
						<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
					<td class="bdCellLast" align="center" valign="center" width="35%">
						<bean:write name="transaction" property="description"/>
						&nbsp;
					</td>
					<td class="bdCellLast" valign="center">
						<bean:write name="transaction" property="counteragentAccount"/>
						&nbsp;
					</td>
					<%--<td class="bdCellLast" valign="center">
						<bean:write name="transaction" property="counteragentBank"/>
						&nbsp;
					</td>
					<td class="bdCellLast" valign="center">
						<bean:write name="transaction" property="counteragentBankName"/>
						&nbsp;
					</td>--%>
					<td class="bdCellLast" align="right" valign="center">
						<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td class="bdCellLast" align="right" valign="center">
						<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td class="bdCellFirst bdCellLast" colspan="4" align="left">����� �� ������</td>
				<td class="bdCellLast" align="right">
					<c:out value="${debit}"/>
				</td>
				<td class="bdCellLast" align="right">
					<c:out value="${credit}"/>
				</td>
			</tr>
		    <tr>
				<td class="bdCellFirst bdCellLast" colspan="4" align="left">��������� �������</td>
				<td class="bdCellLast" colspan="2" align="center">
					<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>&nbsp;
					<bean:write name="abstract" property="closingBalance.currency.code"/>
				</td>
			</tr>

		</table>
		<br/>
		<br/>
	</c:forEach>
	</span>

	<!--������� �� ������ -->
	<c:if test="${not empty(PrintAccountAbstractForm.cardAbstract)}">
		<c:forEach items="${PrintAccountAbstractForm.cardAbstract}" var="listElement">
		<c:set var="card" value="${listElement.key}"/>
		<c:set var="abstr" value="${listElement.value}"/>
		<c:set var="cardinfo" value="${PrintAccountAbstractForm.cardInfoMap[card]}"/>
		<c:set var="cardaccount" value="${PrintAccountAbstractForm.cardAccountMap[card]}"/>
		<c:set var="cardclient" value="${PrintAccountAbstractForm.cardClientMap[card]}"/>

		<table cellspacing="0" cellpadding="0" width="100%" style="font-size:9pt">
	    <tr>
			<td align="center" colspan="2">
				<b>�������&nbsp;��&nbsp;�����&nbsp;��&nbsp;������&nbsp;�&nbsp;
				<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>
				��
				<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/></b>
	        </td>
		    <td width="50%">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>


		<table id="dataTbl" cellspacing="0" cellpadding="0" width="50%" style="border:1px; font-size:9pt">
		    <tr>
				<td class="bdCellFirst bdCell bdCellLast">� ��������</td>
				<td class="bdCellLast bdCell"><c:out value="${card.agreementNumber}"/></td>
		    </tr>
			<tr>
				<td class="bdCellFirst bdCellLast">�������� ����</td>
				<td class="bdCellLast"><c:out value="${cardaccount.number}"/></td>
			</tr>
			<tr>
				<td class="bdCellFirst bdCellLast">������ �����</td>
				<td class="bdCellLast"><c:out value="${cardaccount.currency.code}"/></td>
			</tr>
			<tr>
				<td class="bdCellFirst bdCellLast">����� �����</td>
				<td class="bdCellLast">
			<c:set var="num" value="${card.number}"/>
			<c:set var="len" value="${fn:length(num)}"/>
			<c:out value="${fn:substring(num,0,4)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
			    </td>
		    </tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="left">��������������� ������</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		    <c:if test="${card.main}">
				<tr>
					<td class="bdCellFirst bdCell bdCellLast">����� �������:&nbsp;</td>
					<td class="bdCell bdCellLast"><c:out value="${cardinfo.limit.decimal}"/></td>
				</tr>
			</c:if>
		</table>
		<c:choose>
			<c:when test="${phiz:size(abstr.transactions)==0}">
				<span class="messageTab">
				    ��� �������� �� ����� �� �������� ������.
				</span>
			</c:when>
		<c:otherwise>
			<br/>
				<table id="listTbl" cellspacing="0" cellpadding="0" width="70%" style="border:1px; font-size:9pt">
					<tr>
						<td class="bdCellFirst bdCell" height="20px" align="center">���� ��������</td>
						<td class="bdCell" align="center">��� �������� �� �����</td>
						<td class="bdCell" align="center">����� (������)</td>
						<td class="bdCell" align="center">������ (������)</td>
						<c:if test="${card.main}">
							<td class="bdCell" align="center">������ (�����)</td>
						</c:if>
					</tr>
					<%lineNumber = 0;%>
					<c:if test="${card.main}">
						<tr class="ListLine<%=lineNumber%2%>">
							<td colspan="4" class="bdCellFirst bdCellLast">�������� ������</td>
							<td align="right" class="bdCellLast"><bean:write name="abstr" property="openingBalance.decimal" format="0.00"/>&nbsp;</td>
						</tr>
					</c:if>
					<logic:iterate id="transaction" name="abstr" property="transactions">
						<% lineNumber++; %>
						<tr <%=lineNumber%2%>>
							<td class="bdCellFirst bdCellLast" align="center" valign="center">
								<bean:write name="transaction" property="operationDate.time" format="dd.MM.yyyy"/>
								&nbsp;
							</td>
							<td class="bdCellLast" valign="center">
								<bean:write name="transaction" property="description"/>
								&nbsp;
							</td>
							<td class="bdCellLast" align="right" valign="center">
								<bean:write name="transaction" property="accountDebitSum.decimal" format="0.00"/>
								&nbsp;
							</td>
							<td class="bdCellLast" align="right" valign="center">
								<bean:write name="transaction" property="accountCreditSum.decimal" format="0.00"/>
								&nbsp;
							</td>
							<c:if test="${card.main}">
								<td class="bdCellLast" align="right" valign="center">
									&nbsp;
								</td>
							</c:if>
						</tr>
					</logic:iterate>
					<c:if test="${card.main}">
						<%lineNumber ++;%>
						<tr <%=lineNumber%2%>>
							<td colspan="4" class="bdCellFirst bdCellLast">��������� ������</td>
							<td align="right" class="bdCellLast"><bean:write name="abstr" property="closingBalance.decimal" format="0.00"/>&nbsp;</td>
						</tr>
						<%lineNumber ++;%>
						<tr <%=lineNumber%2%>>
							<td colspan="4" class="bdCellFirst bdCellLast">��������� ����� ������������</td>
							<td align="right" class="bdCellLast"><c:out value="${cardinfo.limit.decimal}"/>&nbsp;</td>
						</tr>
						<%lineNumber ++;%>
						<tr <%=lineNumber%2%>>
							<td colspan="4" class="bdCellFirst bdCellLast">�������� ������ � ������ ������ ������������</td>
							<td align="right" class="bdCellLast">
								<c:out value="${cardinfo.limit.decimal + abstr.closingBalance.decimal}"/>&nbsp;
							</td>
						</tr>
					</c:if>
				</table>
			</c:otherwise>
		</c:choose>
				<table id="listTbl" cellspacing="0" cellpadding="0" width="70%" style="border:1px; font-size:9pt">
				<tr>
					<td width="70">&nbsp;</td><td>&nbsp;</td>
				</tr>
			    </table>
		<br/>
	</c:forEach>
	</table>
	</c:if>
		<!--������� �� ������� -->
	<c:forEach items="${PrintAccountAbstractForm.depositAbstract}" var="listElement">
		<c:if test="${break > 0}">
			 <br style="page-break-after:always;">
		</c:if>
		<c:set var="break" value="1"/>
		<c:set var="deposit"  value="${listElement.key}"/>
		<c:set var="abstract" value="${listElement.value}"/>
		<c:set var="account"  value="${PrintAccountAbstractForm.depositInfoMap[deposit.id].account}"/>

		<span style="font-size:9pt;">��� "���� ���������� � �������" ���, �. ������<br/></span>

		<span style="padding-left:90px;font-size:9pt;">
		 	������� �� ����� � <c:out value="${account.number}"/><br/>
		</span>
		<span style="padding-left:90px;font-size:9pt;">
		 	�� ������:"<c:out value="${deposit.description}"/>" � <c:out value="${deposit.amount.currency.code}"/><br/>
		</span>
		<span style="padding-left:90px;font-size:9pt;">
			�� ������ c:&nbsp;
			<bean:write name='PrintAccountAbstractForm' property="fromDate.time" format="dd.MM.yyyy"/>&nbsp;
			�.&nbsp;��&nbsp;
			<bean:write name='PrintAccountAbstractForm' property="toDate.time" format="dd.MM.yyyy"/>&nbsp;�.
		</span>
		</br>
		<span style="font-size:9pt;">
		��������:&nbsp;<bean:write name='PrintAccountAbstractForm' property="user.surName"/>
			     &nbsp;<bean:write name='PrintAccountAbstractForm' property="user.firstName"/>
			     &nbsp;<bean:write name='PrintAccountAbstractForm' property="user.patrName"/><br/>
		��������:&nbsp;

		<logic:iterate id="document" name="PrintAccountAbstractForm" property="user.personDocuments" >
			<c:if test="${document.documentMain}">
		        <c:choose>
					<c:when test="${document.documentType == 'REGULAR_PASSPORT_RF'}">
						��������������� ������� ��
					</c:when>
					<c:when test="${document.documentType == 'MILITARY_IDCARD'}">
						������������� �������� ���������������
					</c:when>
					<c:when test="${document.documentType == 'SEAMEN_PASSPORT'}">
						������� ������
					</c:when>
					<c:when test="${document.documentType == 'RESIDENTIAL_PERMIT_RF'}">
						��� �� ���������� ��
					</c:when>
					<c:when test="${document.documentType == 'FOREIGN_PASSPORT_RF'}">
						����������� ������� ��
					</c:when>
					<c:when test="${document.documentType == 'OTHER'}">
						���� ��������
					</c:when>
				</c:choose>
			    &nbsp;<bean:write name='document' property="documentSeries"/>
				&nbsp;<bean:write name='document' property="documentNumber"/>;
				&nbsp;<bean:write name='document' property="documentIssueBy"/>
			</c:if>
		</logic:iterate>
		<br/>
		���� ������:&nbsp;<bean:write name='deposit' property="openDate.time" format="dd.MM.yyyy"/><br/>
		<br/>
		�������� �������:&nbsp;<bean:write name="abstract" property="openingBalance.decimal" format="0.00"/>
		<br/>
		<table id="listTbl" cellspacing="0" cellpadding="0" style="border:1px;font-size:9pt;">
			<tr>
				<td class="bdCellFirst bdCell" align="center" valign="middle" height="20px" rowspan="2"><b>&nbsp;� ���.&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" height="20px" rowspan="2"><b>&nbsp;���� ��������&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;����������������� �����&nbsp;</b></td>
				<%--<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;���&nbsp;</b></td>
				<td class="bdCell" align="center" valign="middle" rowspan="2"><b>&nbsp;������������ �����&nbsp;</b></td>--%>
				<td class="bdCell" align="center" valign="middle" colspan="2"><b>&nbsp;����� � ������ �����&nbsp;</b></td>
			</tr>
			<tr>
			    <td class="bdCellLast" align="center" valign="middle"><b>&nbsp;����� �������&nbsp;</b></td>
				<td class="bdCellLast" align="center" valign="middle"><b>&nbsp;����� �������&nbsp;</b></td>
			</tr>

			<c:set var="credit" value="0"/>
			<c:set var="debit" value="0"/>
			<logic:iterate id="transaction" name="abstract" property="transactions">
				<c:set var="credit" value="${credit + transaction.creditSum.decimal}"/>
				<c:set var="debit" value="${debit + transaction.debitSum.decimal}"/>
				<tr>
					<td class="bdCellFirst bdCellLast" align="center" valign="center">
						<bean:write name="transaction" property="documentNumber"/>
						&nbsp;
					</td>
					<td class="bdCellLast" align="center" valign="center">
						<bean:write name="transaction" property="date.time" format="dd.MM.yyyy"/>
						&nbsp;
					</td>
					<td class="bdCellLast" valign="center">
						<bean:write name="transaction" property="counteragentAccount"/>
						&nbsp;
					</td>
					<%--<td class="bdCellLast" valign="center">
						<bean:write name="transaction" property="counteragentBank"/>
						&nbsp;
					</td>
					<td class="bdCellLast" valign="center">
						<bean:write name="transaction" property="counteragentBankName"/>
						&nbsp;
					</td>--%>
					<td class="bdCellLast" align="right" valign="center">
						<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
						&nbsp;
					</td>
					<td class="bdCellLast" align="right" valign="center">
						<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
						&nbsp;
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<td class="bdCellFirst bdCellLast" colspan="3" align="left">����� �� ������</td>
				<td class="bdCellLast" align="right">
					<c:out value="${credit}"/>
				</td>
				<td class="bdCellLast" align="right">
					<c:out value="${debit}"/>
				</td>
			</tr>
		    <tr>
				<td class="bdCellFirst bdCellLast" colspan="4" align="left">��������� �������</td>
				<td class="bdCellLast" align="right">
					<bean:write name="abstract" property="closingBalance.decimal" format="0.00"/>
				</td>
			</tr>

		</table>
		<br/>
		<br/>
	</c:forEach>
	</span>
</tiles:put>
</tiles:insert>
</html:form>
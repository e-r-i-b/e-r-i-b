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
		<td>Информация по вкладу <b><c:out value="${deposit.description}"/></b> в АКБ "Держава" ОАО</td>
	</tr>
	<tr>
		<td>Клиент: <bean:write name="client"/></td>
	</tr>
	<tr>
		<td>Дата: <b><%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%></b></td>
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
					<td class="ListItem">Тип вклада</td>
					<td class="ListItem">
						<bean:write name="deposit" property="description"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">Сумма</td>
					<td class="ListItem">
						<nobr>
							<bean:write name="deposit" property="amount.decimal" format="0.00"/>&nbsp;
						</nobr>
					</td>
				</tr>
				<tr>
					<td class="ListItem">Валюта вклада</td>
					<td class="ListItem">
						<bean:write name="deposit" property="amount.currency.code"/>
					</td>
				</tr>
				<tr>
					<td class="ListItem">Срок</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${not empty deposit.duration}">
								<bean:write name="deposit" property="duration"/>
								дней&nbsp;
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="ListItem">Ставка</td>
					<td class="ListItem">
							<bean:write name="deposit" property="interestRate"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">Номер счета</td>
					<td class="ListItem">
						<bean:write name="depositInfo" property="account.number"/>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">Пролонгируемость вклада</td>
					<td class="ListItem">
						<bean:define id="renewal" name="depositInfo" property="renewalAllowed"/>
						<c:choose>
						<c:when test="${renewal}">
						Пролонгируемый
						</c:when>
						<c:otherwise>
						Непролонгируемый
						</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">Сумма неснижаемого остатка</td>
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
					<td class="ListItem">Счет для перечисления причисленных процентов</td>
					<td class="ListItem">
						<c:if test="${!empty depositInfo.percentAccount}">
							<bean:write name="depositInfo" property="percentAccount.number"/>&nbsp;
						</c:if>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">Счет для финального перечисления</td>
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
					<td class="ListItem">Состояние</td>
					<td class="ListItem">
						<c:choose>
							<c:when test="${deposit.state=='open'}">
								Открыт
							</c:when>
							<c:otherwise>
								Закрыт
							</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="ListItem">Дата открытия</td>
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
					<td class="ListItem">Дата закрытия</td>
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
					<td class="ListItem">Номер договора</td>
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
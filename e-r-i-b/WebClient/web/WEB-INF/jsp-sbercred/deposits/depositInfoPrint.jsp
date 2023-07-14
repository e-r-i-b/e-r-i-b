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
	<table>
	 <tr>
	  <td width="20px"> </td>
	  <td>
		<table>
		 <tr>
		  <td colspan="2">
			  Информация по вкладу № <b><bean:write name="depositInfo" property="agreementNumber"/>&nbsp;</b> в АКБ «Банк Сбережений и Кредита»
		  <td>
		 </tr>
		 <tr>
		  <td>Клиент:</td>
		  <td width="100%">&nbsp;<b><bean:write name="client"/></b></td>
		 </tr>
		 <tr>
		   <td>Дата:</td>
		   <td>&nbsp;<b><%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%></b></td>
		 </tr>
	    </table><br><br>
	  </td>
	 </tr>
	 <tr>
	  <td></td>
	  <td>
	  <table cellspacing="0" cellpadding="0" class="userTab" width="80%" align="center">
	  <tr>
		  <td class="ListItem">Название вида вклада</td>
		  <td class="ListItem">
			  <bean:write name="deposit" property="description"/>&nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Сумма вклада</td>
		  <td class="ListItem">
			  <bean:write name="deposit" property="amount.decimal" format="0.00"/>&nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Валюта вклада</td>
		  <td class="ListItem">
			  <bean:write name="deposit" property="amount.currency.code"/>&nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Срок вклада</td>
		  <td class="ListItem">
			  <c:if test="${not empty deposit.duration}">
				  <bean:write name="deposit" property="duration"/>&nbsp;дней
			  </c:if>
			  &nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Процентная ставка</td>
		  <td class="ListItem">
			  <bean:write name="deposit" property="interestRate"/>&nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Номер депозитного счета</td>
		  <td class="ListItem">
			  <bean:write name="depositInfo" property="account.number"/>&nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Признак пролонгации вклада на следующий срок</td>
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
		  <td class="ListItem">Сумма неснижаемого остатка вклада</td>
		  <td class="ListItem">
			  <c:choose>
				  <c:when test="${!empty depositInfo.minBalance}">
					  <bean:write name="depositInfo" property="minBalance.decimal"/>
				  </c:when>
				  <c:otherwise>
					  <bean:write name="deposit" property="amount.decimal" format="0.00"/>
				  </c:otherwise>
			  </c:choose>
			  &nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Номер счета для перечисления причисленных процентов</td>
		  <td class="ListItem">
			  <c:if test="${!empty depositInfo.percentAccount}">
				  <bean:write name="depositInfo" property="percentAccount.number"/>
			  </c:if>
			  &nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Номер счета для перечисления средств по истечении срока вклада</td>
		  <td class="ListItem">
			<logic:iterate id="entry" name="PrintDepositInfoForm" property="accountsSet">
				<bean:define id="account" name="entry" property="key"/>
				<bean:write name="account" property="number"/>;&nbsp;
			</logic:iterate>
			  &nbsp;
		  </td>
	  </tr>
	  <tr>
		  <td class="ListItem">Текущее состояние</td>
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
			  <c:if test="${!empty deposit.openDate}">
				  <bean:write name="deposit" property="openDate.time" format="dd.MM.yyyy"/>
			  </c:if>&nbsp;
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
			  <bean:write name="depositInfo" property="agreementNumber"/>&nbsp;
		  </td>
	  </tr>
	  </table>
	  </td>
    </table>
</tiles:put>
</tiles:insert>
</html:form>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/cards/print" onsubmit="return setEmptyAction(event)">
<bean:define id="cardAccount" name="PrintCardInfoForm" property="cardAccount"/>
<bean:define id="card" name="PrintCardInfoForm" property="card"/>
<bean:define id="cardInfo" name="PrintCardInfoForm" property="card"/>
<bean:define id="overdraftInfo" name="PrintCardInfoForm" property="overdraftInfo"/>
<bean:define id="cardClient" name="PrintCardInfoForm" property="cardClient"/>
<bean:define id="user" name="PrintCardInfoForm" property="user"/>
<bean:define id="cardAccountClient" name="PrintCardInfoForm" property="cardAccountClient"/>
<bean:define id="additionalCardInfo" name="PrintCardInfoForm" property="additionalCardInfoSet"/>

<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">
<br/>
<table >
	<tr>
		<td width="20px"></td>
		<td>
			<table>
				<tr>
					<td colspan="2">
						Информация по карте № <b>
						<c:set var="num" value="${card.number}"/>
						<c:set var="len" value="${fn:length(num)}"/>
						<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
						<%--<bean:write name="card" property="number"/>--%>
					</b> в АКБ «Московский Залоговый банк»»
					<td>
				</tr>
				<tr>
					<td>Клиент:</td>
					<td width="100%">&nbsp;<b>
						<bean:write name="user"  property="fullName"/>
					</b></td>
				</tr>
				<tr>
					<td>Дата:</td>
					<td>&nbsp;<b><%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%>
					</b></td>
				</tr>
			</table>
			<br>
		</td>
	</tr>
	<tr>
		<td ></td>
		<td>
		<table cellspacing="0" cellpadding="0" class="userTab" width="50%" align="left">
			<c:if test="${not card.main}">
				<tr>
					<td class="ListItem">ФИО держателя основной карты</td>
					<td class="ListItem">
						<nobr>
							<bean:write name="cardAccountClient" property="fullName"/>
						</nobr>
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="ListItem">ФИО держателя карты</td>
				<td class="ListItem">
					<nobr>
						<bean:write name="cardClient" property="fullName"/>
					</nobr>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Тип карты</td>
				<td class="ListItem">
					<bean:write name="card" property="description"/>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Номер и дата договора</td>
				<td class="ListItem">
					<nobr>
						<bean:write name="card" property="agreementNumber"/>
						&nbsp;от&nbsp;
						<bean:write name="card" property="agreementDate.time" format="dd.MM.yyyy"/>
					</nobr>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Номер карты</td>
				<td class="ListItem">
					<c:set var="num" value="${card.number}"/>
					<c:set var="len" value="${fn:length(num)}"/>
					<c:out value="${fn:substring(num,0,1)}"/><c:out value=".."/><c:out value="${fn:substring(num,len-4,len)}"/>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Срок действия карты</td>
				<td class="ListItem">
					<bean:write name="card" property="expireDate.time" format="dd.MM.yyyy"/>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Номер спецкарточного счета</td>
				<td class="ListItem">
					<bean:write name="cardAccount" property="number"/>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Доступные средства</td>
				<td class="ListItem">
					<nobr>
						<c:choose>
							<c:when test="${card.main}">
								Текущий остаток:
								<bean:write name="cardInfo" property="balance.decimal" format="0.00"/>
								<c:if test="${card.cardType=='overdraft'}">
									&nbsp;
									Сумма текущего овердрафта:
									<bean:write name="overdraftInfo" property="currentOverdraftSum.decimal"
												format="0.00"/>

								</c:if>
							</c:when>
							<c:otherwise>
								<bean:write name="cardInfo" property="limit.decimal"
											format="0.00"/>
							</c:otherwise>
						</c:choose>
					</nobr>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Валюта</td>
				<td class="ListItem">
					<bean:write name="cardInfo" property="balance.currency.name"/>
				</td>
			</tr>
			<tr>
				<td class="ListItem">Дата последней операции</td>
				<td class="ListItem">
					<c:if test="${not empty cardInfo.lastOperationDate}">
						<bean:write name="cardInfo" property="lastOperationDate.time" format="dd.MM.yyyy"/>
					</c:if>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr><td></td></tr>

<c:if test="${card.main and (not empty additionalCardInfo)}">
	 <tr>
		 <td ></td>
		 <td>
			 <br/><br/>
		  <table cellspacing="0" cellpadding="0" class="userTab" width="70%" align="left">
			  <tr>
				  <td class="ListItem">
					  Номер карты
				  </td>
				  <td class="ListItem">
					  Вид карты
				  </td>
				  <td class="ListItem">
					  Тип карты
				  </td>
				  <td class="ListItem">
					  Валюта
				  </td>
				  <td class="ListItem">
					  Статус
				  </td>
				  <td class="ListItem">
					  Действ.
				  </td>
			  </tr>
			  <logic:iterate id="entry" name="PrintCardInfoForm" property="additionalCardInfoSet">
				  <c:set var="cardlink" value="${entry}"/>
				  <c:set var="card" value="${cardlink.value}"/>
				  <c:set var="cardinfo" value="${card}"/>
				  <c:set var="expireDate" value="${card.expireDate.time}"/>
				  <c:set var="cardType" value="${card.cardType}"/>
				  <c:set var="num" value="${card.number}"/>
				  <c:set var="len" value="${fn:length(num)}"/>
				  <tr>
					  <td class="ListItem">
						<c:out value="${fn:substring(num,0,1)}"/><c:out value="XXXXXXXXXXXX"/><c:out value="${fn:substring(num,len-4,len)}"/>
					  </td>
					  <td class="ListItem">
							<c:choose>
								<c:when test="${cardType=='overdraft'}">
									"Овердрафт"
								</c:when>
								<c:when test="${cardType=='debit'}">
									"Дебетовая"
								</c:when>
								<c:when test="${cardType=='credit'}">
									"Кредитная"
								</c:when>
							</c:choose>
					  </td>
					  <td class="ListItem">
						    <c:out value="${card.description}"/>
							&nbsp;(дополнит.)
					  </td>
					  <td class="ListItem">
						  <c:out value="${cardinfo.balance.currency.name}"/>
					  </td>
					  <td class="ListItem">
						  <c:out value="${cardinfo.statusDescription}"/>
					  </td>
					  <td class="ListItem">
						  <fmt:formatDate value="${expireDate}" pattern="MM.yyyy"/>
					  </td>
				  </tr>
			  </logic:iterate>
		 </table>

</c:if>
</td>
</tr>
</table>
</tiles:put>
</tiles:insert>
</html:form>

<%--
  Created by IntelliJ IDEA.
  User: Kosyakova
  Date: 08.02.2007
  Time: 14:25:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.rssl.phizic.web.client.payments.forms.ShowMetaPaymentListForm" %>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/common" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${ShowCommonPaymentListForm}"/>


<tiles:insert definition="print">
<!-- данные -->
<tiles:put name="data" type="string">
<div style="width:160mm;">
		<table cellpadding="0" cellspacing="0" class="textDoc" style="width:100%; font-size:10pt;">
		<!-- Шапка документа -->
			<tr>
			   <c:set var="fromDate" value="${form.filters.fromDate}"/>
			   <c:set var="toDate" value="${form.filters.toDate}"/>
			   <c:set var="curDate" value="${form.curDate}"/>
               <td align="center">
	                 Журнал операций по системе АКБ "Московский Залоговый банк"<br/>
	               <c:choose>
		               <c:when test="${fromDate == toDate}" >
			               <c:choose>
				               <c:when test="${empty fromDate}">
					               за весь период с момента подключения к системе
				               </c:when>
				               <c:otherwise>
					               за &nbsp;&ldquo;<input value='${fn:substring(form.filters.toDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
		                           <input id='monthStr1' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.filters.toDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
					               <script>
							           document.getElementById('monthStr1').value = monthToStringOnly('<bean:write name="form" property="filter(fromDate)"/>');
					               </script>
				               </c:otherwise>
			               </c:choose>
		               </c:when>
		               <c:otherwise>
			               <c:choose>
				               <c:when test="${empty fromDate}">
					               за период<br/> c момента подключения к системе
					               <br/>
					                по&ldquo;<input value='${fn:substring(form.filters.toDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
		                           <input id='monthStr2' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.filters.toDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
					               <script>
								       document.getElementById('monthStr2').value = monthToStringOnly('<bean:write name="form" property="filter(toDate)"/>');
					               </script>
				               </c:when>
				               <c:when test="${empty toDate}">
					               <c:choose>
						               <c:when test="${fromDate == curDate}">
							               за &nbsp;&ldquo;<input value='${fn:substring(form.filters.fromDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
				                           <input id='monthStr3' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.filters.fromDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
							               <script>
									           document.getElementById('monthStr3').value = monthToStringOnly('<bean:write name="form" property="filter(fromDate)"/>');
							               </script>
						               </c:when>
						               <c:otherwise>
							               за период<br/> c &nbsp;&ldquo;<input value='${fn:substring(form.filters.fromDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
				                           <input id='monthStr4' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.filters.fromDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
							               <br/>
							               по&ldquo;<input value='${fn:substring(form.curDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
				                          <input id='monthStr5' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.curDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
							              <script>
										      document.getElementById('monthStr4').value = monthToStringOnly('<bean:write name="form" property="filter(fromDate)"/>');
										      document.getElementById('monthStr5').value = monthToStringOnly('<bean:write name="form" property="curDate"/>');
 					                      </script>
						               </c:otherwise>
					               </c:choose>
				               </c:when>
				               <c:otherwise>
					               за период<br/> c &nbsp;&ldquo;<input value='${fn:substring(form.filters.fromDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
		                           <input id='monthStr6' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.filters.fromDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
					               <br/>
					                по&ldquo;<input value='${fn:substring(form.filters.toDate, 0, 2)}' type="Text" readonly="true" class="insertInput font11" style="width:4%">&rdquo;
		                           <input id='monthStr7' value='' type="Text" readonly="true" class="insertInput font11" style="width:13%">20<input value='${fn:substring(form.filters.toDate, 8, 10)}' type="Text" readonly="true" class="insertInput font11" style="width:3%">г.
					               <script>
								       document.getElementById('monthStr6').value = monthToStringOnly('<bean:write name="form" property="filter(fromDate)"/>');
								       document.getElementById('monthStr7').value = monthToStringOnly('<bean:write name="form" property="filter(toDate)"/>');
					               </script>
				               </c:otherwise>
			               </c:choose>
		               </c:otherwise>
	               </c:choose>
	            </td>
			</tr>
			<tr>
				<td align="center"><br/>
					<c:out value="${form.person.fullName}"/><br/>
				</td>
			</tr>
		</table>
<br/>
<table class="docTableBorder" style="font-family:Times New Roman;font-size:8pt;width:140mm;" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="docTdBorder" align="center" valign="top">Дата и время операции</td>
	<td class="docTdBorder" align="center" valign="top">Номер</td>
	<td class="docTdBorder" align="center" valign="top">Вид операции</td>
	<td class="docTdBorder" align="center" valign="top">Счет списания</td>
	<td class="docTdBorder" align="center" valign="top">Наименование<br/>получателя платежа</td>
	<td class="docTdBorder" align="center" valign="top">Сумма платежа</td>
	<td class="docTdBorder" align="center" valign="top">Сумма комиссии</td>
	<td class="docTdBorder" align="center" valign="top">Сумма операции</td>
	<td class="docTdBorder" align="center" valign="top">Валюта</td>
	<td class="docTdBorder" align="center" valign="top">Статус</td>
	<td class="docTdBorder" align="center" valign="top">Дата приема к исполнению</td>
</tr>
<c:set var="lineNumber" value="0"/>
<c:set var="totalDocAmount" value="0"/>
<c:set var="totalCommissionAmount" value="0"/>
<c:set var="totalAmount" value="0"/>

<c:set var="paymentList" value="${form.payments}"/>
<c:forEach items="${paymentList}" var="listElement" varStatus="ls">
	<c:set var="payment" value="${listElement[0]}"/>
	<c:set var="form" value="${listElement[1]}"/>
	<c:set var="lineNumber" value="${lineNumber+1}"/>
	<tr>
		<c:if test="${lineNumber<=listLimit}">
			<c:set var="totalDocAmount" value="${totalDocAmount+payment.chargeOffAmount.decimal}"/>
			<c:set var="totalCommissionAmount"
		           value="${totalCommissionAmount + payment.commission.decimal}"/>
			<c:set var="totalAmount"
			       value="${totalAmount + payment.chargeOffAmount.decimal + payment.commission.decimal}"/>
			<c:set var="docCurrency" value="${payment.chargeOffAmount.currency.code}"/>
			<td class="docTdBorder" align="center" >
				<c:if test="${! empty payment.operationDate}">
					<bean:write name="payment" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
				</c:if>
				&nbsp;
			</td>
			<td class="docTdBorder" align="center" >
				&nbsp;
				<c:out value="${payment.documentNumber}"/>
				&nbsp;
			</td>
			<td class="docTdBorder" align="center" >
				<c:out value="${form.description}"/>
			</td>
			<td class="docTdBorder" align="center" >
				<c:out value="${payment.chargeOffAccount}"/>
			</td>
			<td class="docTdBorder" align="center" >
				<c:out value="${payment.receiverName}"/>
				&nbsp;
			</td>
			<td class="docTdBorder" align="right" >
				<c:set var="docAmount" value="${payment.chargeOffAmount.decimal}"/>
				<bean:write name="docAmount" format="0.00"/>
				&nbsp;
			</td>
			<td class="docTdBorder" align="right" >
				<c:set var="docCommissionAmount" value="0"/>
				<c:set var="docCommissionAmount" value="${docCommissionAmount + payment.commission.decimal}"/>
				<bean:write name="docCommissionAmount" format="0.00"/>
				&nbsp;
			</td>
			<td class="docTdBorder" align="right" >
				<c:set var="docTotalAmount" value="${payment.chargeOffAmount.decimal + payment.commission.decimal}"/>
				<bean:write name="docTotalAmount" format="0.00"/>
				&nbsp;
			</td>
			<td class="docTdBorder" align="right">
				<bean:write name="docCurrency"/>
				&nbsp;
			</td>
			<td class="docTdBorder" align="center">
				<c:set var="code" value="${payment.state.code}"/>
				<c:choose>
					<c:when test="${code=='E'}">Отказан</c:when>
					<c:when test="${code=='D'}">Отказан</c:when>
					<c:when test="${code=='I'}">Введен</c:when>
					<c:when test="${code=='W'}">На исполнении</c:when>
					<c:when test="${code=='S'}">Исполнен</c:when>
					<c:when test="${code=='V'}">Отозван</c:when>
					<c:when test="${code=='Z'}">На исполнении</c:when>
				</c:choose>
			</td>
			<td class="docTdBorder" align="center">
				<c:if test="${! empty payment.admissionDate}">
					<bean:write name="payment" property="admissionDate" format="dd.MM.yyyy"/>
				</c:if>
				&nbsp;
			</td>
		</c:if>
	</tr>
</c:forEach>
</table>
<c:if test="${lineNumber==0}">
	Нет операций.
</c:if>
</div>
</tiles:put>

</tiles:insert>
</html:form>

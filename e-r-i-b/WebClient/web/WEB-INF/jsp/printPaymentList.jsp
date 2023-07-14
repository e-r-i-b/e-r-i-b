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
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
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
<div style="width:160mm; font-size:9pt;" >
		<table cellpadding="0" cellspacing="0" class="textDoc" style="width:100%; font-size:10pt;">
		<!-- Шапка документа -->
			<tr>
			   <c:set var="fromDate" value="${form.filters.fromDate}"/>
			   <c:set var="toDate" value="${form.filters.toDate}"/>
			   <c:set var="curDate" value="${form.curDate}"/>
               <td align="center">
	                Журнал операций по системе<br/>
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
    <c:choose>
        <c:when test="${empty form.data}">
            <table cellspacing="2" cellpadding="0" style="margin:10px;">
				<tr>
					<td class="messageTab" align="center">Не найдено ни одной операции за данный период!</td>
				</tr>
	        </table>
        </c:when>
        <c:otherwise>
            <c:set var="docCommissionAmount" value="0"/>
            <c:set var="totalDocAmount" value="0"/>
            <c:set var="totalCommissionAmount" value="0"/>
            <c:set var="totalAmount" value="0"/>

            <table class="docTableBorder" cellpadding="0" cellspacing="0">
                <tr  class="tblInfHeaderAbstrPrint">
                    <td class="docTdBorder" align="center">Дата и время операции</td>
                    <td class="docTdBorder" align="center">Дата приема к исполнению</td>
                    <td class="docTdBorder" align="center">Номер</td>
                    <td class="docTdBorder" align="center">Вид операции</td>
                    <td class="docTdBorder" align="center">Счет списания</td>
                    <td class="docTdBorder" align="center">Наименование получателя платежа</td>
                    <td class="docTdBorder" align="center">Сумма платежа</td>
                    <td class="docTdBorder" align="center">Сумма комиссии</td>
                    <td class="docTdBorder" align="center">Сумма операции</td>
                    <td class="docTdBorder" align="center">Валюта</td>
                    <td class="docTdBorder" align="center">Статус </td>
                </tr>
            <c:forEach var="listElement" items="${form.data}">
                <c:set var="form" value="${listElement[1]}"/>
                <c:set var="businessDocument" value="${listElement[0]}"/>

                    <tr>

                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
				                <c:set var="totalDocAmount" value="${totalDocAmount+businessDocument.chargeOffAmount.decimal}"/>
				                <c:set var="totalCommissionAmount" value="${totalCommissionAmount + businessDocument.commission.decimal}"/>
				                <c:set var="totalAmount" value="${totalAmount + businessDocument.chargeOffAmount.decimal + businessDocument.commission.decimal}"/>
				                <c:set var="docCurrency" value="${businessDocument.chargeOffAmount.currency.code}"/>
			                </c:if>
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
			                    <c:if test="${!empty businessDocument.chargeOffAmount}">
				                    <c:set var="amount" value="${businessDocument.chargeOffAmount.decimal}"/>
				                    <c:set var="docCurrency" value="${businessDocument.chargeOffAmount.currency.code}"/>
			                    </c:if>
			                </c:if>
                            <c:if test="${(form.name=='SaleCurrencyPayment' ||  form.name=='PurchaseCurrencyPayment')&& !empty businessDocument.destinationAmount && businessDocument.destinationAmount!=''}">
				                <c:set var="amount" value="${businessDocument.destinationAmount.decimal}"/>
				                <c:set var="docCurrency" value="${businessDocument.destinationAmount.currency.code}"/>
			                </c:if>
                        <td class="docTdBorder" align="center">
                            <c:if test="${! empty businessDocument.operationDate}">
					            <bean:write name="businessDocument" property="operationDate.time" format="dd.MM.yyyy HH:mm:ss"/>
				            </c:if>
                            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            <c:if test="${! empty businessDocument.admissionDate}">
					            <bean:write name="businessDocument" property="admissionDate.time" format="dd.MM.yyyy"/>
				            </c:if>
				            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                               <a href="/${phiz:loginContextName()}/private/payments/default-action.do?id=${businessDocument.id}&objectFormName=${businessDocument.formName}&stateCode=${businessDocument.state.code}">
                                ${businessDocument.documentNumber}
                               </a>
                            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            ${form.description}
                        </td>
                        <td class="docTdBorder" align="center">
                             <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					            <c:out value="${businessDocument.chargeOffAccount}"/>
				            </c:if>
				            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
					            <c:out value="${businessDocument.receiverName}"/>
				            </c:if>
				            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					            <c:out value="${businessDocument.chargeOffAmount.decimal}"/>
				            </c:if>
				            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					            <c:set var="docCommissionAmount" value="0"/>
					            <c:set var="docCommissionAmount" value="${docCommissionAmount + businessDocument.commission.decimal}"/>
					            <bean:write name="docCommissionAmount" format="0.00"/>
				            </c:if>
				            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
					            <c:set var="docTotalAmount" value="${businessDocument.chargeOffAmount.decimal+businessDocument.commission.decimal}"/>
					            <bean:write name="docTotalAmount" format="0.00"/>
				            </c:if>
                            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument')}">
                                <c:out value="${businessDocument.chargeOffAmount.currency.code}"/>
                            </c:if>
                            &nbsp;
                        </td>
                        <td class="docTdBorder" align="center">
                           <c:set var="code" value="${businessDocument.state.code}"/>
                             <c:set var="isInstance" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DefaultClaim') || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.DepositOpeningClaim') || phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.LoanClaim')}"/>

                             <c:choose>
                                 <c:when test="${code=='DRAFT'}">
                                     Черновик
                                 </c:when>
                                 <c:when test="${code=='SAVED' || code=='INITIAL'}">
                                     Введен
                                 </c:when>
                                 <c:when test="${code=='EXECUTED'}">
                                     Исполнен
                                 </c:when>
                                 <c:when test="${code=='REFUSED'}">
                                     Отказан
                                 </c:when>
                                 <c:when test="${code=='RECALLED'}">
                                     Отозван
                                 </c:when>
                                 <c:when test="${code=='ACCEPTED'}">
                                     Одобренн
                                 </c:when>
                                 <c:when test="${code=='SUCCESSED'}">
                                     Выдан
                                 </c:when>
                                 <c:when test="${code=='MODIFICATION'}">
                                     Изменен
                                 </c:when>
                                 <c:when test="${code=='RETURNED'}">
                                     Возвращен
                                 </c:when>
                                 <c:when test="${code=='APPROVED'}">
                                     Утвержден
                                 </c:when>
                                  <c:when test="${isInstance && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'))}">
                                     Принят
                                 </c:when>
                                 <c:when test="${code=='CANCELATION'}">
                                     Аннулирован
                                 </c:when>
                                 <c:when test="${code=='CONSIDERATION'}">
                                     В рассмотрении
                                 </c:when>
                                 <c:when test="${code=='COMPLETION'}">
                                     Требуется доработка
                                 </c:when>
                                 <c:when test="${code=='RECEIVED'}">
                                     Получен банком получателем
                                 </c:when>
                                 <c:when test="${not isInstance && ((code=='SENDED') || (code=='DISPATCHED'||code=='STATEMENT_READY'))}">
                                     Обрабатывается
                                 </c:when>
                                 <c:otherwise>&nbsp;</c:otherwise>
                             </c:choose>
                        </td>
                    </tr>

            </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

</div>
</tiles:put>

</tiles:insert>
</html:form>

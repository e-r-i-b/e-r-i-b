<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 17.06.2008
  Time: 13:36:50
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

<html:form action="/payments/print">

<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">
<c:set var="form"  value="${PaymentViewForm}"/>

<style>
.textStyle{font-size:11pt; font-weight:bold; font-family:'Times New Roman';}
.text11{font-size:11pt; font-family:'Times New Roman';}
.text8{font-size:8pt; font-family:'Times New Roman';}
.text9{font-size:9pt; font-family:'Times New Roman';}
.bdCell {border-bottom:1px solid black; border-right:1px solid black;}
.bdCellLast {border-bottom:1px solid black; vertical-align:top; text-align:center}
.bdCellLastBold{border-bottom:2px solid black; vertical-align:top; text-align:center}
.bdRight{border-right:1px solid black}
.tdPadding{padding-top:1mm; padding-bottom:1mm}
</style>

<script type="text/javascript">
	function printSquare(str,last)
	{
		var resultStr = "";
		for ( var i = 0; i < str.length-1; i++ )
		{
			resultStr += "<td class='bdRight tdPadding'>&nbsp;"+str.charAt(i)+"</td>";
		}
		if (last)
			resultStr += "<td>&nbsp;"+str.charAt(str.length-1)+"</td>";
		else
		    resultStr += "<td class='bdRight'>&nbsp;"+str.charAt(str.length-1)+"</td>";
		return resultStr;
	}
	function breakString(str, len)
	{
		if (str.length <= len)
		{
			return str;
		}
		var subLen = 0;
		var lastLen = subLen;
		do
		{
	     lastLen = subLen;
		 subLen =  str.indexOf(" ",subLen+1);
			if ((lastLen == subLen) || ((subLen == -1) && (lastLen == 0)))
				subLen = len;
			if ((subLen == -1) && (lastLen != 0))
				break;
		}
		while (subLen < len);
		if (subLen == len)
			return str.substring(0,subLen);
		return str.substring(0,lastLen);
	}
</script>

<table cellpadding="0" cellspacing="0" style="width: 180mm;" class="text11">
	<tr>
		<td style="text-align:right">
			<!--Верхняя шапка формы-->
			<table style="width:50%;" class="textStyle" cellpadding="0" cellspacing="0">
				<tr><td style="text-align:right">Приложение 3</td></tr>
				<tr><td>&nbsp;В АКБ «Московский залоговый банк» (ЗАО)</td></tr>
				<tr>
					<td>
						<table>
							<tr>
								<td class="textStyle">от</td>
								<td class="bdCellLast" style="width:100%">&nbsp;<c:out value="${form.owner.fullName}"/></td>
								<td>,</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td class="text8" style="font-weight:normal; text-align:center">(Ф.И.О.)</td></tr>
				<tr><td>&nbsp;проживающего по </td></tr>
				<tr>
					<td>
						<table style="width:100%">
							<tr>
								<td class="textStyle">адресу:&nbsp;</td>
								<script type="text/javascript">
									var str = '${form.owner.residenceAddress}';
									var adress = breakString(str,30);
									document.write("<td class='bdCellLast' style='width:85%; text-align:left'><nobr>"+adress+"</nobr></td>");
									str = str.substring(adress.length, str.length);
								</script>
							</tr>
							<script type="text/javascript">
									 while (str.length > 20)
									 {
										adress = breakString(str,35);
										document.write("<tr><td colspan='2' class='bdCellLast' style='width:100%; text-align:left'><nobr>"+adress+"</nobr></td></tr>");
										str = str.substring(adress.length, str.length);
									 }
							</script>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td class="bdCellLast" style="width:60%"><nobr>
									<script type="text/javascript">
											document.write(str);
									</script>
								&nbsp;</nobr>
								</td>
								<td class="textStyle">тел.</td>
								<td class="bdCellLast" style="width:40%">&nbsp;<c:out value="${form.owner.homePhone}"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td>&nbsp;Документ, удостоверяющий личность:</td></tr>
				<tr>
					<td>
						<table>
							<tr>
								<td class="textStyle">наименование</td>
								<td class="bdCellLast"><nowrap>
									<c:if test="${form.owner.identityType == 'REGULAR_PASSPORT_RF'}">Общегражданский паспорт РФ</c:if>
									<c:if test="${form.owner.identityType == 'MILITARY_IDCARD'}">Удостоверение личности военнослужащего</c:if>
									<c:if test="${form.owner.identityType == 'SEAMEN_PASSPORT'}">Паспорт моряка</c:if>
									<c:if test="${form.owner.identityType == 'RESIDENTIAL_PERMIT_RF'}">Вид на жительство РФ</c:if>
									<c:if test="${form.owner.identityType == 'FOREIGN_PASSPORT_RF'}">Заграничный паспорт РФ</c:if>
									<c:if test="${form.owner.identityType == 'OTHER'}"><c:out value="${form.owner.identityTypeName}"/></c:if>
									</nowrap>
								</td></tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr><td class="textStyle">серия</td><td class="bdCellLast" style="width:30%"><c:out value="${form.owner.passportSeries}"/></td>
								<td class="textStyle">№</td><td class="bdCellLast" style="width:70%"><c:out value="${form.owner.passportNumber}"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr><td class="textStyle">выдан «</td>
								<td class="bdCellLast" style="width:10mm">
								 ${fn:substring(form.owner.passportIssueDate,8,10)}
								</td>
								<td class="textStyle">»</td>
								<td class="bdCellLast" style="width:30mm">
								<script type="text/javascript">									
									document.write(monthToStringOnly('<fmt:formatDate value="${form.owner.passportIssueDate}" pattern="dd.MM.yyyy"/>'));
								</script>
								</td>
								<td>&nbsp;&nbsp;&nbsp;</td>
								<td class="bdCellLast" style="width:15mm">${fn:substring(form.owner.passportIssueDate,0,4)}</td>
								<td class="textStyle">г.</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%">
							<tr>
								<td class="textStyle">кем&nbsp;</td>
								<script type="text/javascript">
									var str = '${form.owner.passportIssueBy}';											
									var issueBy = breakString(str,30);
									document.write("<td class='bdCellLast' style='width:90%; text-align:left'><nobr>"+issueBy+"</nobr></td>");
									str = str.substring(issueBy.length, str.length);
								</script>
							</tr>
							<script type="text/javascript">
									while(str.length > 0)
									{
										issueBy = breakString(str,35);
										document.write("<tr><td colspan='2' class='bdCellLast' style='width:100%; text-align:left'><nobr>"+issueBy+"</nobr></td></tr>");
										str = str.substring(issueBy.length, str.length);
									}
							</script>
						</table>

					</td>
				</tr>
			</table>
			<!--конец шапки заголовка-->
		</td>
	</tr>
	<tr>
		<td style="text-align:center; font-weight:bold;" colspan="2"><br/>
			ЗАЯВЛЕНИЕ<br/>на перевод денежных средств
		</td>
	</tr>
	<tr>
		<td style="padding-top:2mm">
			<table  class="text11" style="width:100%;">
				<tr>
					<td>Прошу перечислить с банковского счета №</td>
					<td class="bdCellLast" style="width:53%">${form.payment.chargeOffAccount}</td>
					<td>,</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="text11">
				<tr>
					<td>Открытого в </td>
					<td class="bdCellLast" style="width:152mm; text-align:left"><nobr>&nbsp;
						<c:if test="${not empty form.account}">
							<script type="text/javascript">
								var str = '${form.account.office.name}';
								<c:if test="${not empty form.account.office.address}">
									str+=',&nbsp;${form.account.office.address}';
								</c:if>
								var office = breakString(str,65);
								document.write(office);
								str = str.substring(office.length, str.length);
							</script>
						</c:if>
					</nobr>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td class="text8" style="padding-left:80mm">(наименование подразделения)</td></tr>
	<tr>
		<td>
			<table class="text11" style="width:100%">
				<script type="text/javascript">
					   while(str.length > 50)
					   {
						  office = breakString(str,80);
						  document.write("<tr><td class='bdCellLast' style='width:100%; text-align:left'><nobr>"+office+"</nobr></td></tr>");
						  str = str.substring(office.length, str.length);
					   }
				</script>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="text11">
				<tr>
					<td class="bdCellLast" style="width:100mm; text-align:left">
						<script type="text/javascript">
							if (str!="")
							{
								document.write(str);
							}
						</script>&nbsp;
					</td>
					<td class="bdCellLast" style="width:80mm">АКБ «Московский залоговый банк» (ЗАО)</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="text11" style="width:100%">
				<tr>
					<td>денежные средства в сумме </td>
					<td class="bdCellLast" style="width:40mm">
						<bean:write name="form" property="payment.chargeOffAmount.decimal" format="0.00"/>
					</td>
					<td>&nbsp;(&nbsp;</td>
					<td class="bdCellLast" style="width:75mm"><nobr>
						<script type="text/javascript">
							var str = '${form.paymentSumInwords}';
							var paymentSumInwords = breakString(str,25);
							document.write(paymentSumInwords);
							str = str.substring(paymentSumInwords.length, str.length);
						</script>
					</nobr>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text8" style="padding-left:60mm">(сумма цифрами)</td>
					<td colspan="2" class="text8" style="padding-left:30mm">(сумма прописью)</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="text11">
				<script type="text/javascript">
					   while(str.length > 40)
					   {
						  paymentSumInwords = breakString(str,50);
						  document.write("<tr><td class='bdCellLast' style='width:100%; text-align:left'><nobr>"+paymentSumInwords+"</nobr></td></tr>");
						  str = str.substring(paymentSumInwords.length, str.length);
					   }
				</script>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="text11">
				<tr>
					<td class="bdCellLast" style="width:120mm; text-align:left">
						<script type="text/javascript">
							if (str!="")
							{
								document.write(str);
							}
						</script>&nbsp;
					</td>
					<td>&nbsp;)&nbsp;</td>
					<td class="bdCellLast" style="width:60mm">${form.payment.chargeOffAmount.currency.name}</td>
				</tr>
				<tr>
					<td colspan="3" class="text8" style="padding-left:130mm">(наименование валюты)</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td>
		<table style="width:100%">
			<tr>
				<td class="bdCellLast" style="text-align:left">
					&nbsp; *
				</td>
			</tr>
		</table>
	</td></tr>
	<tr><td style="padding-top:2mm">&nbsp; по следующим реквизитам:</td></tr>
	<tr>
		<td>
			<table style="width:100%; border-top: 1px solid black; border-left: 1px solid black" cellpadding="0" cellspacing="0">
				<tr>
					<td class="bdCell tdPadding">Получатель</td>
					<td class="bdCell">ООО НКО «Платежная сиcтема РАПИДА»</td>
				</tr>
				<tr>
					<td class="bdCell" ><nobr>ИНН Получателя</nobr></td>
					<td class="bdCell">
						<table style="width:100%" cellspacing="0">
							<tr>
								<script type="text/javascript">
									document.write(printSquare("7736008918", false));   //ИНН Рапиды
								</script>
								<td class='bdRight' style="width:9mm">&nbsp;</td>
								<td style="width:9mm">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="bdCell">Счет №</td>
					<td class="bdCell">
						<table style="width:100%" cellspacing="0">
							<tr>
								<script type="text/javascript">
									document.write(printSquare("${form.payment.receiverAccount}", true));
								</script>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="bdCell tdPadding">Банк получателя</td>
					<td class="bdCell">${form.bankName}</td>
				</tr>
				<tr>
					<td class="bdCell">БИК</td>
					<td class="bdCell">
						<table style="width:100%" cellspacing="0">
							<tr>
								<script type="text/javascript">
									document.write(printSquare("${form.bankBic}", true));
								</script>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="bdCell">к/с</td>
					<td class="bdCell">
						<table style="width:100%" cellspacing="0">
							<tr>
								<script type="text/javascript">
									document.write(printSquare("${form.bankCorAccount}", true));
								</script>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="bdCell tdPadding">Назначение платежа</td>
					<td class="bdCell">&nbsp;
						<c:if test="${not empty form.paymentDestination}">
							<script type="text/javascript">
								var destination = '${form.paymentDestination}';
								var		subStr = '${form.owner.residenceAddress}';
								document.write(destination.substring(destination.lastIndexOf('${form.owner.residenceAddress}') + subStr.length + 2, destination.length));
							</script>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table style="width:100%">
				<tr>
					<td style="padding-top:3mm">Приложение**</td>
					<td style="width:90%" class="bdCellLast">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="padding-top:1mm">
			<table style="width:100%">
				<tr>
				  <td>«</td>
				  <td class="bdCellLast" style="width:10mm"><%=Integer.toString(DateHelper.getCurrentDate().getTime().getDate())%></td>
				  <td>»</td>
				  <td class="bdCellLast" style="width:35mm">&nbsp;<%=DateHelper.toFormDate(DateHelper.getCurrentDate())%></td>
				  <td>200</td>
					<c:set value="<%=Integer.toString(DateHelper.getCurrentDate().getTime().getYear() + 1900)%>" var="year"/>
				  <td class="bdCellLast" style="width:5mm">${fn:substring(year,3,4)}</td>
				  <td>&nbsp;г.</td>
				  <td style="width:65mm; text-align:right">Подпись клиента&nbsp;&nbsp;</td>
				  <td class="bdCellLast" style="width:40mm">&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="text8" style="padding-left:2mm; padding-top:3mm">Получено по системе Банк-Клиент <bean:write name="form" property="payment.operationDate.time" format="dd.mm.yyyy hh:mm:ss"/></td>
	</tr>
	<tr>
		<td class="bdCellLastBold">&nbsp;</td>
	</tr>
	<tr>
		<td class="textStyle">&nbsp;Отметки банка</td>
	</tr>
	<tr>
		<td>&nbsp;Подпись соответствует заявленному образцу.<br/>&nbsp;Режим работы и сальдо счета позволяют.</td>
	</tr>
	<tr>
		<td style="padding-top:10mm;">&nbsp;«___» _____________ 200 _г. ___________________________________<span style="padding-left:15mm" class="text8">Оттиск штампа</span></td>
	</tr>
	<tr>
		<td class="text8" style="padding-left:12mm">(дата приема заявления)<span style="padding-left:30mm">(подпись операционного работника)</span></td>
	</tr>
	<tr>
		<td class="bdCellLastBold">&nbsp;</td>
	</tr>
	<tr>
		<td class="text9">
			* После заполняется в следующих случаях:<br/>
			     &nbsp;&nbsp;&nbsp;&nbsp;если заявление подано наследником, указывается: «причитающиеся мне в связи со вступлением в права наследства»;<br/>
			     &nbsp;&nbsp;&nbsp;&nbsp;если заявление заполняется физическим лицом, действующим на основании доверенности, указывается: «на основании доверенности № <> <дата доверенности>».<br/>
			** Заполняется в обязательном порядке в случае подачи заявления наследником. Указывается наименование, номер и дата документа, подтверждающего вступление в права наследства.<br/>
		</td>
	</tr>
</table>
</tiles:put>
</tiles:insert>
</html:form>
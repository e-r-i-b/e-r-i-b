<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 23.01.2007
  Time: 13:04:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<c:if test="${headerMenu == 'true'}">

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

<div>
<table cellpadding="0" cellspacing="0" class="MaxSize">
<tr>
	<td class="bunnersSpace">
		<table cellpadding="0" cellspacing="0" class="MaxSize">
		<tr>
			<td  class="bannerTD" align="center"><a href="http://www.russlavbank.com/about/address.html"><img src="${imagePath}/Office.gif" alt="" width="80" height="37" border="0"><br>офисы<br>обслуживания</a></td>
		</tr>
		<tr>
			<td  class="bannerTD" align="center"><a href="/PhizIC/private/templates.do"><img src="${imagePath}/RurPayment.gif" alt="" height="37" width="37" border="0"><br>мои<br>постоянные&nbsp;платежи</a></td>
		</tr>
		<tr>
			<td  class="bannerTD" align="center"><a href="http://www.russlavbank.com/onlinesrv/cbrexchange.html"><img src="${imagePath}/CCPayment.gif" alt="" height="37" width="37" border="0"><br>курсы<br>валют</a></td>
		</tr>
		<tr>
			<td  class="bannerTD" align="center"><a href="/PhizIC/private/deposits/calculator.do"><img src="${imagePath}/calculator.gif" alt="" height="37" width="37" border="0"><br>калькулятор<br>вкладов</a></td>
		</tr>
		<tr>
			<td  class="bannerTD" align="center"><a href="/PhizIC/private/payments/payment.do?form=ContactPayment"><img src="${imagePath}/Contact2.gif" alt="" width="80" height="37" border="0"><br>cовершить&nbsp;платеж<br>по&nbsp;системе&nbsp;Сontact</a></td>
		</tr>
		<tr>
			<td height="100%">&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table cellspacing="0" cellpadding="0" class="footerRegion" width="100%">
				<tr>
				   <td>
				      <a href="http://www.softlab.ru/solutions/InterBank/">&copy;InterBank</a>
				   </td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>
</div>
</c:if>
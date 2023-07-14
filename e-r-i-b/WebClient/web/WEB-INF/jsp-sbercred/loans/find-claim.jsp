<%--
  User: IIvanova
  Date: 16.05.2006
  Time: 13:04:53
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loan/claim/find">
<tiles:insert definition="loanAnonymousMain">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
	<tiles:put name="pageTitle" type="string">
		Поиск заявки на кредит.
	</tiles:put>
	<tiles:put name="submenu" type="string" value="LoanClaimList"/>
<script language="JavaScript">
	function checkData()
	{
		var patrName = document.getElementsByName("patrName");
		var firstName = document.getElementsByName("firstName");
		var surName = document.getElementsByName("surName");
		var number = document.getElementsByName("number");

		if (number[0].value.length == 0)
		{
			alert("Не заполнено поле [Номер заявки]");
			return false;
		}

		if (surName[0].value.length == 0)
		{
			alert("Не заполнено поле [Фамилия]");
			return false;
		}

		if (firstName[0].value.length == 0)
		{
			alert("Не заполнено поле [Имя]");
			return false;
		}
		
		if (patrName[0].value.length == 0)
		{
			alert("Не заполнено поле [Отчество]");
			return false;
		}

		return true;
	}
</script>

<tiles:put name="menu" type="string">
	<tiles:insert definition="commandButton" flush="false">
		<tiles:put name="commandKey" value="button.next"/>
		<tiles:put name="commandHelpKey" value="button.next"/>
		<tiles:put name="image" value="next.gif"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="validationFunction">
			checkData();
		</tiles:put>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon" style="width:420px;">
	<tr>
		<td align="right" valign="middle"><img src="${imagePath}/SecuritiesOperationsClaim.gif" border="0"/></td>
		<td colspan="2" >
			<table class="MaxSize paymentTitleFon">
			<tr>
				<td align="center" class="silverBott paperTitle">
					<table height="100%" width="340px" cellspacing="0" cellpadding="0" class="paymentTitleFon">
						<tr>
							<td class="titleHelp">
								<span class="formTitle">Поиск&nbsp;заявки&nbsp;на&nbsp;кредит.</span>
								<br/>
								Используйте данную форму для поиска анонимной заявки на кредит.
							</td>
						</tr>
					</table>
				</td>
				<td width="100%">&nbsp;</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Номер заявки:</b></td>
		<td>
			<input type="text" name="number"/>
		</td>
	</tr>
	<tr>
		<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Фамилия:</b></td>
		<td>
			<input type="text" name="surName"/>
		</td>
	</tr>
	<tr>
	    <td class="Width120 LabelAll"><b>&nbsp;&nbsp;Имя:</b></td>
		<td>
			<input type="text" name="firstName"/>
		</td>
	</tr>
	<tr>
		<td class="Width120 LabelAll"><b>&nbsp;&nbsp;Отчество:</b></td>
		<td>
			<input type="text" name="patrName"/>
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
</table>
</tiles:put>
</tiles:insert>
</html:form>
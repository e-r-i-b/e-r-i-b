<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">
	<bean:define id="account"     name="ShowAccountInfoForm" property="account"/>

<tiles:insert definition="accountInfo">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:put name="pageTitle" type="string">
	<bean:message key="application.title" bundle="accountInfoBundle"/>  
</tiles:put>
<tiles:put name="mainmenu" value="Info"/>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
	    <tiles:put name="commandTextKey" value="button.print"/>
	    <tiles:put name="commandHelpKey" value="button.print.help"/>
	    <tiles:put name="bundle"         value="accountInfoBundle"/>
		<tiles:put name="onclick"        value="Print()"/>
    </tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
	    <tiles:put name="commandTextKey" value="button.toList"/>
	    <tiles:put name="commandHelpKey" value="button.toList.help"/>
	    <tiles:put name="bundle"         value="accountInfoBundle"/>
		<tiles:put name="action"         value="/private/accounts.do"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<script type="text/javascript" language="JavaScript">
   function Print()
   {
	   var win = window.open("${phiz:calculateActionURL(pageContext, '/private/accounts/printAcc.do')}?accountId=<%=request.getParameter("accountId")%>");
	   win.resizeTo(800,700);
	   win.moveTo(100,10);
	}
	</script>
  <br/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="text" value="Информация по счету"/>
		<tiles:put name="data">
			 <tr class="ListLine0">
				  <td class="ListItem">Номер счета</td>
				  <td class="ListItem"><bean:write name="account" property="number" />&nbsp;</td>
			  </tr>
			  <tr class="ListLine1">
				  <td class="ListItem">Текущий остаток по счету</td>
				  <td class="ListItem"><nobr><bean:write name="account" property="balance.decimal" format="0.00"/>&nbsp;
											  <bean:write name="account" property="currency.code"/></nobr>
				  </td>
			  </tr>
			  <tr class="ListLine0">
				  <td class="ListItem">Валюта счета</td>
				  <td class="ListItem"><bean:write name="account" property="currency.name" />&nbsp;</td>
			  </tr>
			  <tr class="ListLine1">
				  <td class="ListItem">Текущее состояние</td>
				  <td class="ListItem">
						 <c:if test="${account.isOpen && !account.isLock}">открыт</c:if>
						 <c:if test="${!account.isOpen || account.isLock}">закрыт &nbsp;
							<img src="${imagePath}/iconSm_lock.gif" width="12px" height="12px" alt="" border="0"/>
						 </c:if>
			  </tr>
			  <tr class="ListLine0">
				  <td class="ListItem">Дата открытия</td>
				  <td class="ListItem">
					  <c:if test="${!empty account.openDate}">
						 <bean:write name="account" property="openDate.time" format="dd.MM.yyyy"/>
					  </c:if>&nbsp;
				  </td>
			  </tr>
			  <c:if test="${!account.isOpen || account.isLock}">
			  <tr class="ListLine1">
				  <td class="ListItem">Дата закрытия</td>
				  <td class="ListItem">
					  <c:if test="${!empty account.closeDate}">
						<bean:write name="account" property="closeDate.time" format="dd.MM.yyyy"/>
					  </c:if>&nbsp;
				  </td>
			  </tr>
			  </c:if>
			  <tr class="ListLine0">
				  <td class="ListItem">Номер договора</td>
				  <td class="ListItem"><bean:write name="account" property="agreementNumber" />&nbsp;</td>
			  </tr>
		</tiles:put>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>

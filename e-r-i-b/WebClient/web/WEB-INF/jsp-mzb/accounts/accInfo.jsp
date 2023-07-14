<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">
	<bean:define id="account" name="ShowAccountInfoForm" property="account"/>
	<bean:define id="accountInfo" name="ShowAccountInfoForm" property="accountInfo"/>

<tiles:insert definition="accountInfo">
<tiles:put name="pageTitle" type="string">
	<bean:message key="application.title" bundle="accountInfoBundle"/>  
</tiles:put>
<tiles:put name="mainmenu" value="Info"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false">
	    <tiles:put name="commandTextKey" value="button.toList"/>
	    <tiles:put name="commandHelpKey" value="button.toList.help"/>
	    <tiles:put name="bundle"         value="accountInfoBundle"/>
		<tiles:put name="action"         value="/private/accounts.do"/>
	    <tiles:put name="image"          value="back.gif"/>
    </tiles:insert>
	<tiles:insert definition="clientButton" flush="false">
	    <tiles:put name="commandTextKey" value="button.print"/>
	    <tiles:put name="commandHelpKey" value="button.print.help"/>
	    <tiles:put name="bundle"         value="accountInfoBundle"/>
		<tiles:put name="onclick"        value="Print()"/>
	    <tiles:put name="image"          value="print.gif"/>
    </tiles:insert>	
</tiles:put>

<tiles:put name="data" type="string">
	<script type="text/javascript" language="JavaScript">
   function Print()
   {
	   var win = window.open("${phiz:calculateActionURL(pageContext, '/private/accounts/printAcc.do')}?accountId=<%=request.getParameter("accountId")%>");
	}
	</script>
  <br/>
  <table cellspacing="0" cellpadding="0" class="userTab" width="80%" align="center">
	  <tr class="ListLine0">
		  <td class="ListItem">Номер счета</td>
		  <td class="ListItem"><bean:write name="account" property="number" />&nbsp;</td>
      </tr>
	  <tr class="ListLine1">
		  <td class="ListItem">Остаток</td>
		  <td class="ListItem"><nobr><bean:write name="accountInfo" property="balance.decimal" format="0.00"/>&nbsp;
			                          <bean:write name="account" property="currency.code"/></nobr>
		  </td>
      </tr>
	  <tr class="ListLine0">
		  <td class="ListItem">Валюта</td>
		  <td class="ListItem"><bean:write name="account" property="currency.name" />&nbsp;</td>
      </tr>
	  <tr class="ListLine1">
		  <td class="ListItem">Состояние</td>
		  <td class="ListItem">
                 <c:if test="${accountInfo.isOpen && !accountInfo.isLock}">Открыт</c:if>
			     <c:if test="${!accountInfo.isOpen || accountInfo.isLock}">Закрыт &nbsp;
	                <img src="${imagePath}/lock.gif" width="12px" height="12px" alt="" border="0"/>
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
	  <tr class="ListLine1">
		  <td class="ListItem">Дата закрытия</td>
		  <td class="ListItem">
			  <c:if test="${!empty accountInfo.closeDate}">
			    <bean:write name="accountInfo" property="closeDate.time" format="dd.MM.yyyy"/>
			  </c:if>&nbsp;
		  </td>
      </tr>
	  <tr class="ListLine0">
		  <td class="ListItem">Номер договора</td>
		  <td class="ListItem"><bean:write name="accountInfo" property="agreementNumber" />&nbsp;</td>
      </tr>
  </table>
  </span>
</tiles:put>
</tiles:insert>
</html:form>

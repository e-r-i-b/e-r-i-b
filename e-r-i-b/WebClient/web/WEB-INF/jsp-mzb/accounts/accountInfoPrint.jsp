<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts/printAcc">
	<bean:define id="account" name="PrintAccountInfoForm" property="account"/>
	<bean:define id="accountInfo" name="PrintAccountInfoForm" property="accountInfo"/>
	<bean:define id="client" name="PrintAccountInfoForm" property="fullNameClient"/>

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
			���������� �� ����� � <b><bean:write name="account" property="number"/></b> � ��� ����������� ��������� ����
		<td>
	   </tr>
	   <tr>
	    <td>������:</td>
		<td width="100%">&nbsp;<b><bean:write name="client"/></b></td>
	   </tr>
	   <tr>
	     <td>����:</td>
		 <td>&nbsp;<b><%=DateHelper.toString(DateHelper.getCurrentDate().getTime())%></b></td>
	   </tr>
      </table><br><br>
    </td>
   </tr>
   <tr>
    <td></td>
	<td>
		<table cellspacing="0" cellpadding="0" class="userTab" width="60%" align="left">
			<tr>
				<td class="ListItem">����� �����</td>
				<td class="ListItem"><bean:write name="account" property="number" />&nbsp;</td>
		    </tr>
			<tr>
				<td class="ListItem">�������</td>
				<td class="ListItem"><nobr><bean:write name="accountInfo" property="balance.decimal" format="0.00"/>&nbsp;
					                        <bean:write name="account" property="currency.code"/></nobr>
				</td>
		    </tr>
			<tr>
				<td class="ListItem">������</td>
				<td class="ListItem"><bean:write name="account" property="currency.name" />&nbsp;</td>
		    </tr>
			<tr>
				<td class="ListItem">���������</td>
				<td class="ListItem">
		               <c:if test="${accountInfo.isOpen && !accountInfo.isLock}">������</c:if>
					   <c:if test="${!accountInfo.isOpen || accountInfo.isLock}">������</c:if>
		    </tr>
			<tr>
				<td class="ListItem">���� ��������</td>
				<td class="ListItem">
					<c:if test="${!empty account.openDate}">
					   <bean:write name="account" property="openDate.time" format="dd.MM.yyyy"/>
					</c:if>&nbsp;
				</td>
		    </tr>
			<tr>
				<td class="ListItem">���� ��������</td>
				<td class="ListItem">
					<c:if test="${!empty accountInfo.closeDate}">
					  <bean:write name="accountInfo" property="closeDate.time" format="dd.MM.yyyy"/>
					</c:if>&nbsp;
				</td>
		    </tr>
			<tr>
				<td class="ListItem">����� ��������</td>
				<td class="ListItem"><bean:write name="accountInfo" property="agreementNumber" />&nbsp;</td>
		    </tr>
		</table>
	</td>  
  </table>
</tiles:put>
</tiles:insert>
</html:form>

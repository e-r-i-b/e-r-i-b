<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 24.11.2006
  Time: 11:33:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/persons/cards/unblock" >
  <tiles:insert definition="personEdit">

	<tiles:put name="pageTitle" type="string">
	    <bean:message key="cardUnBlock.title" bundle="passwordcardsBundle"/>
    </tiles:put>

   <tiles:put name="menu" type="string">
        <nobr>
            <tiles:insert definition="commandButton" flush="false">
				 <tiles:put name="commandKey"     value="button.unlock"/>
				 <tiles:put name="commandHelpKey" value="button.unlock"/>
				 <tiles:put name="bundle"         value="personsBundle"/>
	 		     <tiles:put name="image"          value=""/>
	             <tiles:put name="isDefault"            value="true"/>
		         <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
	        <tiles:insert definition="clientButton" flush="false">
				 <tiles:put name="commandTextKey"     value="button.close"/>
				 <tiles:put name="commandHelpKey" value="button.close"/>
				 <tiles:put name="bundle"         value="personsBundle"/>
	 		     <tiles:put name="image"          value=""/>
                 <tiles:put name="action"         value="/cardspersons/cards.do??person=${person.id}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
        </nobr>
    </tiles:put>

	<tiles:put name="submenu" type="string" value="PasswordCards"/>
	<tiles:put name="data" type="string">
		<html:hidden property="unusedPasswordNumber"/>
		<c:set var="form" value="${PasswordCardUnblockPasswordForm}"/>
		<c:set var="person" value="${form.person}"/>
		<c:set var="pswNumber" value="${form.unusedPasswordNumber}"/>
		<c:set var="cardNumber" value="${form.cardNumber}"/>
		<input type="hidden" name="card" value="${form.card}"/>
		<input type="hidden" name="person" value="${person}"/>
		<table cellspacing="0" cellpadding="0" id="tableTitle" style="margin-bottom:2px" class="fieldBorder">
			<tr>
			  <td class="Width120 Label" >
				  <nobr>Пароль № ${pswNumber} c карты № ${cardNumber}</nobr>
			  </td>
              <td>
                   <input type="text" id="unblockPassword" name="unblockPassword" size="100" maxlength="256"/>
              </td>
 		    </tr>
	    </table>
	 </tiles:put>
 </tiles:insert>
</html:form>
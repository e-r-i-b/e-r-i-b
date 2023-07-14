<%@ page import="com.rssl.phizic.web.persons.*"%>
<%@ page import="com.rssl.phizic.web.actions.*"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<% pageContext.getRequest().setAttribute("mode","Cards");%>
<html:form action="/persons/card" onsubmit="return setEmptyAction(event)">
<c:set var="personId" value="${PrintPassworCardForm.personId}"/>
<c:set var="isUser" value="${personId!=null}"/>
<c:if test="${isUser}">
 <% pageContext.getRequest().setAttribute("mode","Users");%>
</c:if>
<tiles:insert definition="personEdit">
   <tiles:put name="pageTitle" type="string">
      Карта ключей <c:if test="${isUser}">пользователя</c:if>
   </tiles:put>

   <tiles:put name="menu" type="string">
     <script language="JavaScript">
         function printData(event) {
           var msg ="<HTML><HEAD><META http-equiv=Content-Type content='text/html; charset=windows-1251'>\n" +
                    "<TITLE>" + document.title + "</TITLE></HEAD><BODY>" +
                    document.getElementById("Data").innerHTML + "</BODY></HTML>";
           var pwin=openWindow(event,"","","resizable=1,menubar=1,toolbar=1,scrollbars=1");
           with ( pwin.document)
           {
            open();
            writeln(msg);
            close();
           }
           pwin.focus();
       }
     </script>
     <input type="hidden" name="person" value="<c:out value="${personId}"/>"/>
        <tiles:insert definition="clientButton" flush="false">
		   <tiles:put name="commandTextKey" value="button.printPasswordCard"/>
		   <tiles:put name="commandHelpKey" value="button.printPasswordCard.help"/>
		   <tiles:put name="bundle"         value="personsBundle"/>
		   <tiles:put name="onclick">
		     javascript:printData(event);
		   </tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
	    </tiles:insert>
     <c:if test="${isUser}">
        <tiles:insert definition="clientButton" flush="false">
		   <tiles:put name="commandTextKey" value="button.cancel"/>
		   <tiles:put name="commandHelpKey" value="button.cancel.help"/>
		   <tiles:put name="bundle"         value="personsBundle"/>
		   <tiles:put name="onclick">
		     javascript:callOperation(event,'<bean:message key="button.close" bundle="commonBundle"/>');
		   </tiles:put>
            <tiles:put name="viewType" value="blueBorder"/>
	    </tiles:insert>
     </c:if>
     <c:if test="${!isUser}">
         <tiles:insert definition="clientButton" flush="false">
		   <tiles:put name="commandTextKey" value="button.cancel"/>
		   <tiles:put name="commandHelpKey" value="button.cancel.help"/>
		   <tiles:put name="bundle"         value="personsBundle"/>
		   <tiles:put name="onclick">
		     document.location='../passwordcards/list.do'
		   </tiles:put>
             <tiles:put name="viewType" value="blueBorder"/>
	    </tiles:insert>      
     </c:if>
   </tiles:put>

   <tiles:put name="data" type="string">
	   <table cellpadding="0" cellspacing="0" class="MaxSize">
    <tr>
	<td height="100%">

    <table cellspacing="2" cellpadding="0" border=0 style="font-family:MS Sans Serif;font-size:8pt">
      <c:if test="${PrintPassworCardForm.personId!=null}">
        <tr>
         <td class="Filter"><nobr><bean:message key="label.client" bundle="personsBundle"/>:&nbsp;</nobr></td>
         <td><b><bean:write name="PrintPassworCardForm" property="person"/></b></td>
        </tr>
     </c:if>
        <tr>
         <td class="Filter"><nobr><bean:message key="label.cardIssueDate" bundle="personsBundle"/>&nbsp;</nobr></td>
         <td><b><bean:write name="PrintPassworCardForm" property="card.issueDate" format="dd.MM.yyyy"/></b></td>
        </tr>
        <tr>
         <td class="Filter"><nobr><bean:message key="label.cardNumber" bundle="personsBundle"/>&nbsp;</nobr></td>
         <td><b><bean:write name="PrintPassworCardForm" property="card.number"/></b></td>
        </tr>
        <c:if test="${PrintPassworCardForm.personId!=null}">
        <tr>
         <td class="Filter"><nobr>Статус&nbsp;</nobr></td>
         <td><b>
            <c:set var="card" value="${PrintPassworCardForm.card}"/>
            <c:choose>
    			<c:when test="${card.state == 'R'}">Неактивна</c:when>
				<c:when test="${card.state == 'B'}">Блокирована</c:when>
				<c:when test="${card.state == 'A'}">Активна</c:when>
	            <c:when test="${card.state == 'E'}">Использована</c:when>
			</c:choose>
         </b></td>
        </tr>
        </c:if>
        <tr>
         <td class="Filter"><nobr>Всего ключей&nbsp;</nobr></td>
         <td><b><bean:write name="PrintPassworCardForm" property="passwordsCount"/></b></td>
        </tr>
        <tr>
         <td class="Filter"><nobr>Из них неиспользовано:&nbsp;</nobr></td>
         <td><b><bean:write name="PrintPassworCardForm" property="passwordsAvailableCount"/></b></td>
        </tr>
    </table>
     <br/>
     <table cellspacing="0" cellpadding="0" border="0"  style="font-family:MS Sans Serif;font-size:8pt; border:1px solid #d3d3d3;border-collapse:collapse" >
               <c:set var="passTab" value="${PrintPassworCardForm.password}"/>
       <c:set var="i" value="0"/>
       <% PrintPassworCardForm form = (PrintPassworCardForm) StrutsUtils.currentForm(pageContext); %>
       <% for (int i=1; i <= form.getMaxRow() ; i++ ){%>
           <tr>
           <c:set var="j" value="0"/>
          <% for (int j=1; j <= form.getMaxColumn(); j++ ){%>
                 <c:set var="idx" value="${20*j+i}"/>
                 <td bgcolor="silver" width="24px" align="center" style="border:1px solid #d3d3d3"><c:out value="${passTab[idx].number}"/></td>
                 <td bgcolor="white"  style="border:1px solid #d3d3d3">&nbsp;
                  <c:choose>
                    <c:when test="${passTab[idx].used}">
                        <c:out value="${passTab[idx].stringValue}"/>
                    </c:when>
                    <c:otherwise>
                        <span style="text-decoration:line-through"><c:out value="${passTab[idx].stringValue}"/></span>
                    </c:otherwise>
                   </c:choose>&nbsp;
                 </td>
                 <c:set var="j" value="${j+1}"/>
          <%}%>
          </tr>
          <c:set var="i" value="${i+1}"/>
       <%}%>
       </table>
	</td>
	</tr>
	</table>
   </tiles:put>

</tiles:insert>
</html:form>


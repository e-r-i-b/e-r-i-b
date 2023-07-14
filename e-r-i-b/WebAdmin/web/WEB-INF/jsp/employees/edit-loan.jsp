<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/employees/edit"  onsubmit="return setEmptyAction(event);">
	<c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="employeesEdit">
	<tiles:put name="submenu" type="string" value="EmployeesEdit"/>
   <tiles:put name="pageTitle" type="string">
	  <bean:message key="edit.title" bundle="employeesBundle"/>
   </tiles:put>

   <c:set var="employeeId" value="${form.employeeId}"/>
   <c:set var="isNew" value="${employeeId == null || employeeId == 0}"/>

   <tiles:put name="menu" type="string">
	   <tiles:insert definition="clientButton" flush="false">
		   <tiles:put name="commandTextKey" value="button.cancel"/>
		   <tiles:put name="commandHelpKey" value="button.cancel.help"/>
		   <tiles:put name="bundle"         value="commonBundle"/>
		   <tiles:put name="image"   value=""/>
		   <tiles:put name="onclick">
		     javascript:resetForm(event);
		   </tiles:put>
           <tiles:put name="viewType" value="blueBorder"/>
	   </tiles:insert>
	                                                                     
	   	<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"  value="commonBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="action"  value="employees/list.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

   <c:choose>
        <c:when test="${!empty form.employee.login.blocks}">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.unlock"/>
				<tiles:put name="commandHelpKey" value="button.unlock.help"/>
				<tiles:put name="bundle"  value="employeesBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="confirmText"    value="Вы действительно хотите снять блокировку с пользователя?"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
        </c:when>
	    <c:when test="${!isNew}">
			<tiles:insert definition="clientButton" flush="false" operation="ChangeLockEmployeeOperation">
                <tiles:put name="commandTextKey" value="button.lock"/>
                <tiles:put name="commandHelpKey" value="button.lock"/>
                <tiles:put name="bundle" value="employeesBundle"/>
                <tiles:put name="onclick" value="callReasonWindow()"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
	    </c:when>
   </c:choose>
   </tiles:put>


   <tiles:put name="data" type="string">
    <script type="text/javascript">
		function callReasonWindow()
		{
			window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock.do')}", "", "width=900,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=1");
		}

		function setReason(reason, startDate, endDate)
		{
			var blockReason = document.getElementById("blockReason");
			blockReason.value = reason;

			var blockStartDate = document.getElementById("blockStartDate");
			blockStartDate.value = startDate;
			if (endDate!=null)
			{
				var blockEndDate = document.getElementById("blockEndDate");
				blockEndDate.value = endDate;
			}

			var button = new CommandButton("button.lock", "");
			button.click();
		}
	</script>
	<input type="hidden" name="blockReason" id="blockReason" value=""/>
	<input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
	<input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>   

    <html:hidden property="employeeId" />
	<tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value="editEmployees"/>
		<tiles:put name="name" value="Сведения о сотруднике"/>
		<tiles:put name="description" value="Используйте данную форму редактирования сведений о сотруднике."/>
		<tiles:put name="data">
		   <tr>
			   <td class="Width120 LabelAll"><nobr><bean:message key="label.id" bundle="personsBundle"/></nobr></td>
			   <td>
				   <html:text property="field(id)" disabled="true"/> 
			   </td>
		   </tr>
	       <tr>
		       <td class="Width120 LabelAll">Подразделение<nobr><span class="asterisk">*</span></nobr></td>
		       <td>
			       <html:hidden property="field(departmentId)"/>
			       <html:text  property="field(departmentDescription)" readonly="true" style="width:300px"/>
			       <input type="button" class="buttWhite" style="height:18px;width:18px;"onclick="openDepartmentsDictionary(setDepartmentInfo, getElementValue('field(departmentDescription)'));" value="..."/>
		       </td>
	       </tr>
	       <tr>
		       <td class="Width120 LabelAll">Кредитный офис<span class="asterisk">*</span></td>
		       <td>
			       <html:hidden property="field(loanOfficeId)"/>
			       <html:text  property="field(loanOfficeDescription)" readonly="true" style="width:300px"/>
			       <input type="button" class="buttWhite" style="height:18px;width:18px;"onclick="openLoanOfficesDictionary(setLoanOfficeInfo);" value="..."/>
		       </td>
	       </tr>
	       <tr>
		       <td class="Width120 LabelAll"><bean:message key="label.sName" bundle="employeesBundle"/><span class="asterisk">*</span></td>
               <td><html:text property="field(surName)" /></td>
           </tr>
           <tr>
               <td class="Width120 LabelAll"><bean:message key="label.fName" bundle="employeesBundle"/><span class="asterisk">*</span></td>
               <td><html:text property="field(firstName)" /></td>
           </tr>
           <tr>
               <td class="Width120 LabelAll"><bean:message key="label.pName" bundle="employeesBundle"/></td>
               <td><html:text property="field(patrName)" /></td>
           </tr>
            <tr>
               <td class="Width120 LabelAll"><bean:message key="label.email" bundle="personsBundle"/></td>
               <td><html:text  property="field(email)" size="40" styleClass="contactInput"/></td>
          </tr>
           <tr>
               <td class="Width120 LabelAll"><bean:message key="label.mobilePhone" bundle="personsBundle"/></td>
               <td><html:text  property="field(mobilePhone)" size="20" styleClass="contactInput"/></td>
          </tr>
          <tr>
              <td class="Width120 LabelAll"><bean:message key="label.SMSFormat" bundle="personsBundle"/></td>
              <td><html:radio  property="field(SMSFormat)" value="DEFAULT" style="border:0"/> <bean:message key="label.SMSFormatRus" bundle="personsBundle"/>
                  <html:radio  property="field(SMSFormat)" value="TRANSLIT" style="border:0"/> <bean:message key="label.SMSFormatTranslit" bundle="personsBundle"/> </td>
           </tr>
           <tr>
               <td valign="top" class="Width120 LabelAll"><bean:message key="label.info"  bundle="employeesBundle"/></td>
               <td><html:textarea property="field(info)" style="width:300px;height:50px;" rows="3" /></td>
           </tr>
	       <tr>
		       <td valign="top" class="Width120 LabelAll">Логин<c:if test="${isNew}"><span class="asterisk">*</span></c:if></td>
		       <td>
			       <c:if test="${isNew}">
				       <html:text property="field(userId)" size="20" styleClass="contactInput"/>
			       </c:if>
			       <c:if test="${not isNew}">
				       <html:text disabled="true" property="field(login)" size="20" styleClass="contactInput"/>
			       </c:if>
		       </td>
	       </tr>
	       <tr>
		       <td valign="top" class="Width120 LabelAll">Пароль<span class="asterisk">*</span></td>
		       <td>
			       <html:password property="field(password)" size="20" styleClass="contactInput"/>
		       </td>
	       </tr>
	       <tr>
		       <td valign="top" class="Width120 LabelAll">Подтверждение пароля<span class="asterisk">*</span></td>
		       <td>
                   <table cellpadding="0" cellspacing="0">
                   <tr>
                       <td>
                            <html:password property="field(confirmPassword)" size="20" styleClass="contactInput"/>
                       </td>
                       <td>
                           <div class="buttDiv">
                               <c:if test="${not isNew}">
                                  <tiles:insert definition="commandButton" flush="false">
                                      <tiles:put name="commandKey" value="button.employee.changePassword"/>
                                      <tiles:put name="commandHelpKey" value="button.employee.changePassword.help"/>
                                      <tiles:put name="bundle" value="employeesBundle"/>
                                  </tiles:insert>
                               </c:if>
                           </div>
                       </td>
                   </tr>
                   </table>
		       </td>
	       </tr>
        </tiles:put>
		<tiles:put name="buttons">
		    <tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>
	   <script type="text/javascript">
		   function setDepartmentInfo(result)
		   {
			   setElement("field(departmentDescription)",result['name']);
			   setElement("field(departmentId)"  , result['id']);
		   }

		   function setLoanOfficeInfo(result)
		   {
			   setElement("field(loanOfficeDescription)",result['name']);
			   setElement("field(loanOfficeId)"  , result['id']);
		   }
	   </script>      
   </tiles:put>
</tiles:insert>
</html:form>


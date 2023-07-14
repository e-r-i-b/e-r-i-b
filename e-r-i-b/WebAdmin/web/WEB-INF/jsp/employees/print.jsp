<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<tiles:insert definition="employeesPrint">
<tiles:put name="data" type="string">

    <body  scroll="no" onLoad="showMessage();" Language="JavaScript">
    <html:form action="/employees/print">
      <p style="font-size:12pt;font-weight:bold;padding:20px;">
        Сведения о сотруднике банка
      </p>
      <table cellspacing="2" cellpadding="1" class="fieldBorder" style="margin:20px;font-size:9pt" >
           <tr>
               <td class="Width120" style="font-weight:bold"><bean:message key="label.sName" bundle="employeesBundle"/>:</td>
               <td><bean:write name="EmployeeEditForm" property="field(surName)"/></td>
           </tr>
           <tr>
               <td class="Width120" style="font-weight:bold"><bean:message key="label.fName" bundle="employeesBundle"/>:</td>
               <td><bean:write name="EmployeeEditForm" property="field(firstName)"/></td>
           </tr>
           <tr>
               <td class="Width120" style="font-weight:bold"><bean:message key="label.pName" bundle="employeesBundle"/>:</td>
               <td><bean:write name="EmployeeEditForm" property="field(patrName)"/></td>
           </tr>
           <tr>
               <td valign="top" class="Width120" style="font-weight:bold"><bean:message key="label.info"  bundle="employeesBundle"/>:</td>
               <td><bean:write name="EmployeeEditForm" property="field(info)"/></td>
           </tr>
           <tr>
               <td valign="top" class="Width120" style="font-weight:bold">Логин:</td>
               <td><bean:define id="login" name="EmployeeEditForm" property="field(login)"/>
                   <bean:write name="login" property="userId"/>
               </td>
           </tr>
          <tr>
               <td valign="top" class="Width120" style="font-weight:bold">Пароль:</td>
               <td><logic:present name="EmployeeEditForm"  property="field(password)">
                     <bean:define id="password" name="EmployeeEditForm" property="field(password)"/>
                     <bean:write name="password"/>
                   </logic:present>

               </td>
           </tr>
       </table>
 </html:form>
</body>
</tiles:put>
</tiles:insert>

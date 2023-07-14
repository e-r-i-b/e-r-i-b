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

    <c:set var="employeeId" value="${form.employeeId}"/>
    <c:set var="isNew" value="${employeeId == null || employeeId == 0}"/>

    <tiles:put name="menu" type="string">

        <tiles:insert definition="clientButton" flush="false">
           <tiles:put name="commandTextKey" value="button.close"/>
           <tiles:put name="commandHelpKey" value="button.close.help"/>
           <tiles:put name="bundle" value="commonBundle"/>
           <tiles:put name="image" value=""/>
           <tiles:put name="action" value="employees/list.do"/>
           <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>

        <c:choose>
            <c:when test="${!empty form.employee.login.blocks}">
                <tiles:insert definition="commandButton" flush="false" operation="ChangeLockEmployeeOperation">
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
                    <tiles:put name="image" value=""/>
                    <tiles:put name="bundle" value="employeesBundle"/>
                    <tiles:put name="onclick" value="CallReasonWindow()"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
             </c:when>
        </c:choose>
    </tiles:put>

    <tiles:put name="data" type="string">
        <script type="text/javascript">
            function CallReasonWindow()
            {
                window.open("${phiz:calculateActionURL(pageContext, '/blocks/userblock')}", "", "width=900,height=270,resizable=0,menubar=0,toolbar=0,scrollbars=1");
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

            doOnLoad(function() {
                refreshAllowCAOrVSP();
            });

        function setDepartmentInfo(result)
        {
            setElement("field(departmentDescription)",result['name']);
            setElement("field(departmentTB)"  , result['tb']);
            setElement("field(departmentOSB)"  , result['osb']);
            setElement("field(departmentVSP)"  , result['vsp']);
        }

            function refreshAllowCAOrVSP()
            {
                <c:if test="${form.allowEditCA and not form.vspEmployee}">
                    var caEmployee = $('*[name=field(CAAdmin)]')[0];
                    var vspEmployee = $('*[name=field(VSPEmployee)]')[0];

                    vspEmployee.disabled = caEmployee.checked;
                    caEmployee.disabled = vspEmployee.checked;
                </c:if>
            }
        </script>
        <input type="hidden" name="blockReason" id="blockReason" value=""/>
        <input type="hidden" name="blockStartDate" id="blockStartDate" value=""/>
        <input type="hidden" name="blockEndDate" id="blockEndDate" value=""/>
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value=""/>
            <tiles:put name="name" value="Сведения о сотруднике"/>
            <tiles:put name="description" value="Используйте данную форму редактирования сведений о сотруднике."/>
            <tiles:put name="data">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.id" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(id)" disabled="true"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Подразделение
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:hidden property="field(departmentTB)"/>
                        <html:hidden property="field(departmentOSB)"/>
                        <html:hidden property="field(departmentVSP)"/>
                        <html:text  property="field(departmentDescription)" readonly="true" style="width:300px"/>
                        <input type="button" class="buttWhite smButt" onclick="openDepartmentsDictionary(setDepartmentInfo, 'null');" value="..."/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <html:checkbox property="field(CAAdmin)" disabled="${not form.allowEditCA}" onclick="refreshAllowCAOrVSP();"/>
                        <bean:message key="label.CAEmployee" bundle="employeesBundle"/>
                        <html:checkbox property="field(VSPEmployee)" onclick="refreshAllowCAOrVSP();" disabled="${form.vspEmployee}"/>
                        <bean:message key="label.VSPEmployee" bundle="employeesBundle"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.sName" bundle="employeesBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(surName)" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.fName" bundle="employeesBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(firstName)" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.pName" bundle="employeesBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(patrName)" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.email" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text  property="field(email)" size="40" maxlength="40" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.mobilePhone" bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text  property="field(mobilePhone)" size="20" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.info"  bundle="employeesBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea property="field(info)" style="width:300px;height:50px;" rows="3" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Логин
                    </tiles:put>
                    <c:if test="${isNew}">
                        <tiles:put name="isNecessary" value="true"/>
                    </c:if>
                    <tiles:put name="data">
                        <c:if test="${isNew}">
                            <html:text property="field(userId)" size="20" maxlength="${form.maxLengthLogins}" styleClass="contactInput"/>
                        </c:if>
                        <c:if test="${not isNew}">
                            <c:out value="${form.employee.login.userId}"/>
                        </c:if>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.SUDIRLogin"  bundle="personsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text property="field(sudirLogin)" size="20" maxlength="100"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Пароль
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:password property="field(password)" size="20" maxlength="30" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Подтверждение пароля
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:password property="field(confirmPassword)" size="20" maxlength="30" styleClass="contactInput float"/>
                        <span class="buttDiv">
                           <c:if test="${not isNew}">
                               <tiles:insert definition="commandButton" flush="false">
                                   <tiles:put name="commandKey" value="button.employee.changePassword"/>
                                   <tiles:put name="commandHelpKey" value="button.employee.changePassword.help"/>
                                   <tiles:put name="bundle" value="employeesBundle"/>
                               </tiles:insert>
                           </c:if>
                        </span>
                    </tiles:put>
                </tiles:insert>

            </tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false" operation="EditEmployeeOperation">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" operation="EditEmployeeOperation">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle"         value="commonBundle"/>
                    <tiles:put name="onclick">
                        javascript:resetForm(event);
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="alignTable" value="center"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>


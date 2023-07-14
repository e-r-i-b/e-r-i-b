<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<% pageContext.getRequest().setAttribute("mode","Services");%>
<% pageContext.getRequest().setAttribute("userMode","PasswordChange");%>
<html:form action="/passwordchange" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="logMain">
    <tiles:put name="submenu" type="string" value="ChangePassword"/>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="passwordchange"/>
            <tiles:put name="name" value="Изменение пароля"/>
            <tiles:put name="description" value="Используйте данную форму для изменения пароля на вход в систему."/>
            <tiles:put name="data">
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Введите новый пароль:
                    </tiles:put>
                    <tiles:put name="data">
                        <html:password property="field(newPassword)" />
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Повторите новый пароль:
                    </tiles:put>
                    <tiles:put name="data">
                        <html:password property="field(repeatedPassword)" />
                    </tiles:put>
                </tiles:insert>
             </tiles:put>
             <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.changePassword"/>
                    <tiles:put name="commandHelpKey" value="button.changePassword"/>
                    <tiles:put name="bundle"  value="employeesBundle"/>
                </tiles:insert>
             </tiles:put>
        </tiles:insert>
   </tiles:put>
</tiles:insert>
</html:form>


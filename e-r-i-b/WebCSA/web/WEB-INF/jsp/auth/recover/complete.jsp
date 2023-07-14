<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<html:form action="/recover-password">
    <c:set var="form" value="${csa:currentForm(pageContext)}"/>
    <c:set var="operationInfo" value="${form.operationInfo}"/>

    <div class="complete-message">
        <c:if test="${operationInfo.connectorType == 'TERMINAL'}">
            Новый пароль выслан вам в SMS-сообщении
        </c:if>
        <c:if test="${operationInfo.connectorType == 'CSA'}">
            Пароль успешно изменен
        </c:if>
    </div>

    <div class="buttonsArea">
        <div class="clientButton" onclick="win.close('stageForm');">
            <div class="buttonGrey">
                <div class="left-corner"></div>
                <div class="text">
                    <span>Закрыть</span>
                </div>
                <div class="right-corner"></div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    
    <script type="text/javascript">
        function closeCallback()
        {
            authForm.showForm("login-form", true);
            return true;
        }

        if(window.win)
            win.aditionalData('stageForm', {closeCallback: closeCallback});
    </script>
</html:form>
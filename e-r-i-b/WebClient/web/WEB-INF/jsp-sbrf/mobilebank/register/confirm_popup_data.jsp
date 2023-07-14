<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.ConfirmRegistrationForm"--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${form.confirmRequest}"/>
<c:url var="nextURL" value="${form.nextActionPath}"/>

<c:choose>
    <c:when test="${not empty confirmRequest and confirmRequest.preConfirm}">
        <tiles:insert definition="confirm_sms" flush="false">
            <tiles:put name="title">Подтверждение подключения</tiles:put>
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            <tiles:put name="confirmableObject" value=""/>
            <tiles:put name="byCenter" value="Center"/>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${form.confirmActionPath}"/>
            <tiles:put name="ajaxOnCompleteCallback">goTo('${nextURL}');</tiles:put>
            <tiles:put name="preConfirmCommandKey" value="preconfirm"/>
            <tiles:put name="message">
                Для подтверждения операции введите SMS-пароль, полученный на Ваш мобильный телефон,
                и нажмите на кнопку &laquo;Подтвердить&raquo;.
            </tiles:put>
            <tiles:put name="data">
                <%@ include file="view_fields.jsp"  %>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        &nbsp;
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
                var error = '<bean:write name="error" filter="false"/>';
                if (window.addError != undefined)
                    addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
                var message = '<bean:write name="error" filter="false"/>';
                if (window.addMessage != undefined)
                    addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:otherwise>
</c:choose>

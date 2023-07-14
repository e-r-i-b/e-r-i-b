<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<c:set var="form" value="${BusinessEnvironmentForm}"/>
<c:set var="isGlobalLoginNotRestriction" value="${not csa:isGlobalLoginRestriction()}"/>
<c:set var="blockingRuleMessage" value="${csa:getBlockingRuleNotification()}"/>
<c:set var="formOperationInfo" value="${form.operationInfo}"/>
<c:set var="isBusinessEnvironmentNotRestriction" value="${not empty formOperationInfo}"/>

<tiles:insert definition="loginMain" flush="false">
    <c:choose>
        <c:when test="${isGlobalLoginNotRestriction and isBusinessEnvironmentNotRestriction}">
            <%-- если доступен вход --%>
            <tiles:put name="loginFormHeader">
                <span class="b-ie">
                <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                </span><!-- // b-ie -->

                <div class="auth_header invoice_uecard">
                    <span class="auth_invoice">
                        <img src="${skinUrl}/skins/sbrf/images/csa/loginPage/tmp/invoice_ds-pic.png" alt="Деловая Среда"/>
                    </span>
                </div>
            </tiles:put>
            <tiles:put name="showLoginAndPasswordForm" value="true"/>
            <tiles:put name="customFooterLogo">
                <div class="b-invoice-logo">
                    <img class="invoice-logo_pic" src="${skinUrl}/skins/sbrf/images/csa/loginPage/tmp/footer-ds-pic.png" alt="">
                </div><!-- // b-invoice-logo -->
            </tiles:put>
            <tiles:put name="showRegistration" value="false"/>
            <tiles:put name="showRecoverPassword" value="false"/>
        </c:when>
        <c:when test="${not isBusinessEnvironmentNotRestriction}">
            <tiles:put name="loginFormHeader">
                <span class="b-ie">
                <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                </span><!-- // b-ie -->
                <div class="auth_header invoice_disabled">
                    <span class="b-ie">
                    <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                    </span><!-- // b-ie -->
                    <div class="relative-ie">
                        <bean:message bundle="businessEnvironmentBundle" key="form.login.fail.message" arg0="${csa:getBusinessEnvironmentUserURL()}"/>
                    </div>
                </div>
                <div class="auth_body">
                    <div class="auth_redirect relative-ie">
                        <a href="${csa:calculateActionURL(pageContext, '/index')}" class="auth_link aBlack">Войти в Сбербанк Онлайн c логином и паролем</a>
                    </div>
                </div>
            </tiles:put>
            <tiles:put name="showRegistration" value="false"/>
        </c:when>
        <c:otherwise>
            <%-- если стоит глобальная блокировка входа --%>
            <tiles:put name="loginFormHeader">
                <span class="b-ie">
                <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                </span><!-- // b-ie -->
                <div class="auth_header invoice_disabled">
                    <span class="b-ie">
                    <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
                    </span><!-- // b-ie -->
                    <div class="relative-ie">
                        <h4>Вход закрыт</h4>
                        <p><bean:message key="message.global.bloking.login" bundle="commonBundle"/></p>
                    </div>
                </div>
                <div class="auth_body">
                    <div class="auth_redirect relative-ie">
                        <a href="${csa:calculateActionURL(pageContext, '/index')}" class="auth_link aBlack">Войти в Сбербанк Онлайн c логином и паролем</a>
                    </div>
                </div>
            </tiles:put>
            <tiles:put name="showRegistration" value="false"/>
        </c:otherwise>
    </c:choose>
    <tiles:put name="formAction"    value="${csa:calculateActionURL(pageContext, '/businessEnvironment/login')}"/>
    <c:if test="${not empty blockingRuleMessage}">
        <tiles:put name="message" type="string">
            ${blockingRuleMessage.message}
        </tiles:put>
    </c:if>
    <tiles:put name="scripts">
        <script type="text/javascript">
           function submitData()
           {
               var loginField = document.getElementById("login");
               var passwordField = document.getElementById("password");
               var hiddenPasswordField = document.getElementById("hiddenPasswordField");
               hiddenPasswordField.value = passwordField.value;
               var form = document.createElement("form");
               form.setAttribute("method", "post");
               form.setAttribute("action", "${csa:calculateActionURL(pageContext, '/businessEnvironment/login')}");
               var hiddenField = document.createElement("input");
               hiddenField.setAttribute("type", "hidden");
               hiddenField.setAttribute("name", "operation");
               hiddenField.setAttribute("value", "button.login");

               var hiddenLogin = document.createElement("input");
               hiddenLogin.setAttribute("type", "hidden");
               hiddenLogin.setAttribute("name", "field(login)");
               hiddenLogin.setAttribute("value", loginField.value);

               form.appendChild(hiddenField);
               form.appendChild(hiddenLogin);
               form.appendChild(hiddenPasswordField);

               document.body.appendChild(form);
               showOrHideAjaxPreloader();
               form.submit();
           }
        </script>
    </tiles:put>
</tiles:insert>

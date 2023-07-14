<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert page="/WEB-INF/jsp/common/authBlock.jsp" flush="false">
    <c:set var="isGlobalLoginRestriction" value="${csa:isGlobalLoginRestriction()}"/>
    <c:set var="blockingRuleMessage" value="${csa:getBlockingRuleNotification()}"/>
    <c:set var="isAccessRegistration" value="${csa:isAccessInternalRegistration()}"/>
    <c:set var="isAccessRecoverPassword" value="${csa:isAccessRecoverPassword()}"/>
    <c:set var="registrationAccessType" value="${csa:getRegistrationAccessType()}"/>
    <c:set var="form" value="${AuthenticationFormBase}"/>

    <tiles:put name="id" value="login-form"/>
    <script type="text/javascript" language="javascript">
        function focusPassword()
        {
            $("#passText").hide();
        }

        function blurPassword()
        {
            if ($("#password").val() == "")
                $("#passText").show();
        }
    </script>

    <c:if test="${!form.fields.payOrder && isAccessRegistration}">
        <tiles:put name="title" type="string">
            <div class="crystalTopCBT">
                <c:choose>
                    <c:when test="${registrationAccessType == 'popup'}">
                        <a href="#" onclick="authForm.showForm('registration-form', true); return false;" tabindex="15">Регистрация</a>
                    </c:when>
                    <c:when test="${registrationAccessType == 'page'}">
                        <a href="${csa:calculateActionURL(pageContext, '/internal/page/registration')}" tabindex="15">Регистрация</a>
                    </c:when>
                    <c:when test="${registrationAccessType == 'asyncPage'}">
                        <a href="${csa:calculateActionURL(pageContext, '/async/page/registration')}" tabindex="15">Регистрация</a>
                    </c:when>
                </c:choose>
            </div>
            <div class="crystalTopTitle grayPoint">
                Впервые в Сбербанк Онлайн?
            </div>
        </tiles:put>
    </c:if>

    <tiles:put name="data" type="string">
        <form action="${csa:calculateActionURL(pageContext, form.fields.payOrder?'/payOrderLogin.do':'/login.do')}" onkeypress="authForm.onEnterKeyPress(event);" id="loginForm">
            <div class="login" onkeypress="authForm.onEnterKeyPress(event, 'button.begin', true);">
                <h2>
                    <tiles:insert definition="authIcon" flush="false"/>
                    <span class="auth-title">
                        <c:out value="${csa:getAuthTitle()}"/>
                    </span>
                </h2>
                <c:choose>
                    <c:when test="${!isGlobalLoginRestriction}">
                        <%-- если доступна самостоятельная регистрация --%>
                        <input id="login" type="text" class="customPlaceholder csaRegistration" name="field(login)" maxlength="30" title="Идентификатор или логин" tabindex="11"/>
                        <div id="passBlock">
                            <span id="passText" onclick="$('#password').focus();">Пароль</span>
                            <input id="password" type="password" maxlength="30" onfocus="focusPassword();" onblur="blurPassword();" tabindex="12"/>
                        </div>
                        <input id="hiddenPasswordField" type="hidden" name="field(password)" />
                    </c:when>
                    <c:otherwise>
                        <%-- если стоит глобальная блокировка входа --%>
                        <div id="blocking-message-block">
                            <tiles:insert definition="roundBorder" flush="false">
                                <tiles:put name="color" value="yellowTop"/>
                                <tiles:put name="data" type="string">
                                    <bean:message key="message.global.bloking.login" bundle="commonBundle"/>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="buttonsArea">
                <c:if test="${isAccessRecoverPassword && (registrationAccessType == 'page' || registrationAccessType == 'popup')}">
                    <div id="recoverPasswordButton" class="clientButton" onclick="authForm.showForm('recover-password-form', true);" onkeypress="clickIfEnterKeyPress(this,event);" tabindex="14">
                        <div class="buttonGrey">
                            <div class="left-corner"></div>
                            <div class="text">
                                <span>забыли пароль?</span>
                            </div>
                            <div class="right-corner"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </c:if>
                <c:if test="${registrationAccessType == 'asyncPage'}">
                    <div id="recoverPasswordButton" class="clientButton" tabindex="14">
                        <div class="buttonGrey">
                            <div class="left-corner"></div>
                            <div class="text">
                                <span><a href="${csa:calculateActionURL(pageContext, '/page/login-error')}" tabindex="15">не могу войти</a></span>
                            </div>
                            <div class="right-corner"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </c:if>

                <c:if test="${!isGlobalLoginRestriction}">
                    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
                    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
                    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>
                    <script type="text/javascript">

                        function setElementValue(name, value)
                        {
                            var frm = $("#loginForm")[0];
                            var field = getElementFromCollection(frm.elements, name);
                            if (field == null)
                            {
                                addField('hidden', name, value);
                            }
                            else
                            {
                                field.value = value;
                            }
                        }

                        function submitLogin(event)
                        {
                            new RSAObject().toHiddenParameters();

                            authForm.hidePassword();
                            authForm.submit(event, 'button.begin');
                        }
                       </script>


                    <div id="loginButton" class="commandButton" onclick="submitLogin(this);" onkeypress="clickIfEnterKeyPress(this,event);" tabindex="13">
                        <div class="buttonCarroty">
                            <div class="left-corner"></div>
                            <div class="text">
                                <span>Войти</span>
                            </div>
                            <div class="right-corner"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </c:if>
            </div>
        </form>
        <%-- блок формы для редиректа при оплате с внешней ссылки --%>
        <form action="" method="POST" name="$$POSTRedirect" id="redirectPayOrderForm" class="hidden-form" accept-charset="utf-8" enctype="multipart/form-data">
            <%--т.к. IE 6, 7, 8 игнорирует accept-charset, приходится добавлять hidden input с utf-символом в значении--%>
            <%--взято отсюда: http://stackoverflow.com/questions/153527/setting-the-character-encoding-in-form-submit-for-internet-explorer--%>
            <input name="iehack" type="hidden" value="&#9760;"/>
            <c:set var="fields" value="${form.fields}"/>
            <c:choose>
                <c:when test="${not empty fields.PayInfo}">
                    <html:textarea name="fields" property="PayInfo"/>
                </c:when>
                <c:when test="${not empty fields.ReqId}">
                    <html:text name="fields" property="ReqId"/>
                </c:when>
                <c:when test="${not empty fields.UECPayInfo}">
                   <html:text name="fields" property="UECPayInfo"/>
                </c:when>
            </c:choose>            
         </form>
    </tiles:put>
    <c:if test="${not empty blockingRuleMessage}">
        <tiles:put name="blockingMessage" type="string">
            ${blockingRuleMessage.message}
        </tiles:put>
    </c:if>
</tiles:insert>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert page="/WEB-INF/jsp/common/authBlock.jsp" flush="false">
    <c:set var="form" value="${AuthenticationFormBase}"/>
    <c:set var="isAccessRegistration" value="${csa:isAccessInternalRegistration()}"/>
    <c:set var="registrationAccessType" value="${csa:getRegistrationAccessType()}"/>

    <tiles:put name="id" value="recover-password-form"/>
    <c:if test="${!form.fields.payOrder && isAccessRegistration}">
        <tiles:put name="title" type="string">
            <div class="crystalTopCBT">
                <c:choose>
                    <c:when test="${registrationAccessType == 'popup'}">
                        <a href="#" onclick="authForm.showForm('registration-form', true); return false;" tabindex="15">Регистрация</a>
                    </c:when>
                    <c:when test="${registrationAccessType == 'page'}">
                        <a href="${csa:calculateActionURL(pageContext, '/internal/page/registration')}"  tabindex="15">Регистрация</a>
                    </c:when>
                </c:choose>
            </div>
            <div class="crystalTopTitle grayPoint">
                Впервые в Сбербанк Онлайн?
            </div>
        </tiles:put>
    </c:if>

    <tiles:put name="data" type="string">
        <form action="${csa:calculateActionURL(pageContext, '/recover-password.do')}" onkeypress="authForm.onEnterKeyPress(event);">
            <div class="login">
                <h2>
                    <tiles:insert definition="authIcon" flush="false"/>
                    <span class="auth-title">Восстановление пароля</span>
                </h2>
                <%-- если доступна самостоятельная регистрация --%>
                <div class="float">
                    <input type="text" id="login" name="field(login)" class="customPlaceholder" maxlength="30" size="41" title="Идентификатор или логин" tabindex="11"/>
                </div>
                <div style="float:right;">
                    <tiles:insert definition="floatMessageShadow" flush="false">
                        <tiles:put name="id" value="login-pupupHelp"/>
                        <tiles:put name="hintClass" value=""/>
                        <tiles:put name="data"><a href="#" class="imgHintBlock" onclick="return false;"></a></tiles:put>
                        <tiles:put name="showHintImg" value="false"/>
                        <tiles:put name="text">
                            <bean:message key="message.recover.password.login.help" bundle="commonBundle"/>
                        </tiles:put>
                        <tiles:put name="dataClass" value="dataHint"/>
                    </tiles:insert>
                </div>
                <div class="clear"></div>
                <div style="display:none" class="captcha-block">
                    <%-- блок с капчей --%>
                    <div id="captcha" style="margin: 0 auto; width: 170px; height: 55px;"></div>
                    <div class="update-captcha">
                        <a href="#" onclick="authForm.updateCaptcha(); return false;">обновить код</a>
                    </div>    
                    <%-- поля для капчи --%>
                    <input id="ccode" class="customPlaceholder codeCaptcha" type="text" name="field(ccode)" maxlength="22" title="Введите код с картинки" onkeyup="convertCode(this);" tabindex="12"/>
                    <input id="captchaCode" type="hidden" name="field(captchaCode)"/>
                </div>
            </div>

            <div class="buttonsArea">
                <div id="cancelButton" class="clientButton authFormButton" onclick="authForm.showForm('login-form', true);" onkeypress="clickIfEnterKeyPress(this,event)" tabindex="14">
                    <div class="buttonGrey">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Отменить</span>
                        </div>
                        <div class="right-corner"></div>
                    </div>
                    <div class="clear"></div>
                </div>

                <div id="confirmButton" class="clientButton authFormButton" onclick="authForm.submit(this, 'button.begin');" onkeypress="clickIfEnterKeyPress(this,event)" tabindex="13">
                    <div class="buttonGreen">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Далее</span>
                        </div>
                        <div class="right-corner"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
        </form>
    </tiles:put>
</tiles:insert>

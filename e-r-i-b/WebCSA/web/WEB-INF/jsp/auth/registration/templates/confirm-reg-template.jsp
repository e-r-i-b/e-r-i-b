<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<tiles:useAttribute name="formId"/>
<tiles:useAttribute name="form"/>
<tiles:insert definition="registration" flush="false">
    <tiles:put name="data" type="string">
        <tiles:insert definition="step-form" flush="false">
            <tiles:put name="id" type="string" value="${formId}"/>
            <tiles:put name="steps">
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="Проверка карты"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="Подтверждение по SMS"/>
                    <tiles:put name="current" value="true"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="Логин и пароль"/>
                </tiles:insert>
                <tiles:insert definition="stripe" flush="false">
                    <tiles:put name="name" value="Звонок в контакт-центр"/>
                    <tiles:put name="abs" value="true"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data" type="string">
                <div class="actions_item">
                    <i class="actions_cnt">3</i>
                    <c:set var="attempts" value="${form.operationInfo.confirmParams['Attempts']}"/>
                    <c:set var="timeout" value="${form.operationInfo.confirmParams['Timeout']}"/>
                    <h2 class="actions_title">Введите SMS-пароль. <c:if test="${not empty attempts}">Осталось попыток: ${attempts}</c:if></h2>
                    <div class="actions_description">SMS-пароль отправлен на мобильный телефон, привязанный к карте <b>${csa:getCutCardNumber(form.operationInfo.cardNumber)}</b>.</div>
                    <!-- Таймер в секундах, появляется при <= 120s -->
                    <c:if test="${not empty timeout}">
                        <div id="SmsTimer" class="actions_timer" onclick="return{time: ${timeout}}">
                            <span class="current">Время действия пароля истекает: <b class="time"></b></span>
                        </div>
                    </c:if>

                    <div class="b-sms">
                        <i class="sms_bg"></i>
                        <tiles:useAttribute name="action"/>
                        <html:form styleId="${formId}" action="${action}" styleClass="form sms_form">
                            <div class="sms_block">
                                <label for="SmsInput" class="label">SMS-пароль</label>
                                <!-- Если нужно сгенерировать поле с ошибкой в балуне, можно в onclick атрибут передать свойство error c текстом ошибки -->
                                <div class="field" onclick="return{type:'sms', validMap: ['required']}">
                                    <input id="SmsInput" type="text" class="input" autocomplete="off" maxlength="7" name="field(confirmPassword)"/>
                                    <i class="field_ico"></i>
                                </div>
                            </div>

                            <tiles:insert definition="nextButtonReg" flush="false">
                                <tiles:put name="text" value="Подтвердить"/>
                                <tiles:put name="commandKey" value="button.next"/>
                            </tiles:insert>
                            <div class="sms_helper">
                                <!-- по клику будет раскрывать попап с соотв. ID -->
                                <div class="dot" onclick="utils.showPopup('SmsDoesNotCome')">SMS-пароль не&nbsp;приходит</div>
                            </div>

                        </html:form>
                    </div><!-- // b-sms -->

                </div>
            </tiles:put>
        </tiles:insert>

        <c:set var="recoverPasswordUrl" value="${csa:calculateActionURL(pageContext, '/index')}?form=recover-password-form"/>
        <c:set var="loginUrl" value="${csa:calculateActionURL(pageContext, '/index')}?form=login-form"/>
        <tiles:put name="overlay" type="string">
            <csa:popupCollection defaultUrl="${csa:calculateActionURL(pageContext, action)}">
                <csa:popupItem id="SmsDoesNotCome" page="/WEB-INF/jsp/auth/registration/popups/lost-sms.jsp"/>
                <csa:popupItem id="Registered" closable="false">
                    <html:form action="${action}" style="display:none">
                        <input id="RegisteredFormSubmit" type="submit" name="operation" value="button.next" style="position:absolute; opacity: 0">
                    </html:form>
                    <h1 class="m25">Вы уже являетесь пользователем «Сбербанк Онлайн»</h1>
                    <p>Если вы&nbsp;помните свой <i>логин и&nbsp;пароль</i>, перейдите на&nbsp;<a href="${loginUrl}">страницу входа в&nbsp;«Сбербанк Онлайн»</a>.</p>
                    <p class="m25">Если вы&nbsp;<i>забыли пароль</i>&nbsp;&mdash; воспользуйтесь <a href="${recoverPasswordUrl}">восстановлением пароля</a>.</p>
                    <div class="moved m4">
                        <i class="posit">либо</i>
                        <p><a href="#" onclick="$('#RegisteredFormSubmit').click();return false;">Создайте новые логин и&nbsp;пароль</a> на&nbsp;следующем шаге. Возможности «Сбербанк Онлайн» будут доступны с&nbsp;ограничениями. Ограничения можно снять, подтвердив любую операцию звонком в&nbsp;Контактный центр Сбербанка.</p>
                    </div>
                </csa:popupItem>
                <c:set var="passRecoveryHelpText"><bean:message bundle="commonBundle" key="text.password.recovery.help"/></c:set>
                <csa:popupItem id="alreadyRegRestrict" onclose="location.href='${recoverPasswordUrl}';">
                    ${fn:replace(passRecoveryHelpText, '%rPU%', recoverPasswordUrl)}
                </csa:popupItem>
            </csa:popupCollection>
        </tiles:put>
    </tiles:put>
</tiles:insert>

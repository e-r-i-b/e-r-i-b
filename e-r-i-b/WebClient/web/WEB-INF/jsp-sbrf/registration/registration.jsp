<%--
  User: bogdanov
  Date: 30.04.2013

  страница самостоятельной регистрации.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="appName"><bean:message key="application.title" bundle="commonBundle"/></c:set>

<tiles:insert definition="login">
    <tiles:put name="headerGroup" value="true"/>
    <tiles:put name="needHelp" value="true"/>
    <tiles:put name="pageTitle" type="string" value="${appName}"/>
    <tiles:put type="string" name="data">
        <html:form action="/private/registration" styleClass="SelfRegistration" show="true" onsubmit="return setEmptyAction();" styleId="workspace">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

            <tiles:insert page="../common/layout/messages.jsp" flush="false">
                <tiles:importAttribute name="messagesBundle" ignore="true"/>
                <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                <c:set var="bundleName" value="${messagesBundle}"/>
            </tiles:insert>
            <br/>
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="title">Регистрация в ${appName}</tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript" src="${globalUrl}/scripts/complexValueIndicator.js"></script>
                    <style type="text/css">
                        .simpleField{padding: 5px 10px 0px 7px;}
                        .indic{padding: 0px 10px 5px 10px;}
                        .passHintList{list-style:disc; margin-left:15px;}
                    </style>
                    <br>

                    <table class="shield">
                        <tr>
                            <td><html:img src="${globalImagePath}/to_sait_64.jpg"/></td>
                            <td class="iconsTitle">${form.formMessage}</td>
                        </tr>
                    </table>

                    <table class="widthAuto alignAuto">
                        <tr>
                            <td class="text-align-right field">Логин:</td>
                            <td width="85%" class="simpleField">
                                <html:text property="field(login)" name="form" size="30" styleId="login" styleClass="login" maxlength="30" value="${form.fields.login}" style="width: 210px;" tabindex="1" autocomplete="off"/>
                                <a href="#" onclick="win.close('passwordHelp'); win.open('loginHelp');" tabindex="5"><bean:message bundle="userprofileBundle" key="label.login.help"/></a>
                            </td>
                        </tr>

                        <tr>
                            <td></td>
                            <td class="indic">
                                <tiles:insert definition="complexIndicator" flush="false">
                                    <tiles:put name="id" value="loginField"/>
                                </tiles:insert>
                            </td>
                        </tr>

                        <tr>
                            <td class="text-align-right field">Пароль:</td>
                            <td width="85%" class="simpleField">
                                <html:password property="field(password)" styleId="password" styleClass="login" maxlength="30" value="${form.fields.password}" style="width: 210px;" tabindex="2" onfocus="checkLogin();" autocomplete="off"/>
                                <a onclick="win.close('loginHelp'); win.open('passwordHelp');" href="#" tabindex="6"><bean:message bundle="userprofileBundle" key="label.password.help"/></a>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="indic">
                                <tiles:insert definition="complexIndicator" flush="false">
                                    <tiles:put name="id" value="passwordField"/>
                                </tiles:insert>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-align-right field">Повторите пароль:</td>
                            <td class="simpleField">
                                <html:password property="field(confirmPassword)" styleId="confirmPassword" styleClass="login" maxlength="30" value="${form.fields.confirmPassword}" style="width: 210px;" tabindex="3" onfocus="checkLogin();" autocomplete="off"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-align-right field"></td>
                            <td class="indic">
                                <input type="checkbox" id="showPassword" name="showPassword" <c:if test="${not empty form.fields.show and form.fields.show=='true'}">checked</c:if> tabindex="7" onclick="checkLogin();"/>отобразить пароли
                                <input type="hidden" id="hiddenShowPassword" name="field(show)"/>
                            </td>
                        </tr>

                        <c:if test="${form.needShowEmailAddress}">
                            <tr>
                                <td class="text-align-right field">Электронная почта:</td>
                                <td width="85%" class="simpleField">
                                    <html:text property="field(email)" name="field(email)" size="30" styleId="email" styleClass="login" maxlength="30" value="${form.fields.email}" style="width: 210px;" tabindex="4" onfocus="checkLogin();"/>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="passwordHelp"/>
                        <tiles:put name="styleClass" value="passwordHelp"/>
                        <tiles:put name="data" type="string">
                            <bean:message key="password.recomendationForGood" bundle="securityBundle"/>
                            <ul class="passHintList">
                                <bean:message key="password.recomendationForGood.list" bundle="securityBundle"/>
                            </ul>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.close"/>
                                    <tiles:put name="commandHelpKey"    value="button.close"/>
                                    <tiles:put name="bundle"            value="securityBundle"/>
                                    <tiles:put name="viewType"          value="simpleLink"/>
                                    <tiles:put name="onclick"           value="win.close('passwordHelp');"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="loginHelp"/>
                        <tiles:put name="styleClass" value="loginHelp"/>
                        <tiles:put name="data" type="string">
                            <bean:message key="login.recomendationForGood" bundle="securityBundle"/>
                            <ul class="passHintList">
                                <bean:message key="login.recomendationForGood.list" bundle="securityBundle"
                                              arg0="8" arg1="30"/>
                            </ul>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.close"/>
                                    <tiles:put name="commandHelpKey"    value="button.close"/>
                                    <tiles:put name="bundle"            value="securityBundle"/>
                                    <tiles:put name="viewType"          value="simpleLink"/>
                                    <tiles:put name="onclick"           value="win.close('loginHelp');"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                        function onCloseLoginExistsWindow()
                        {
                            $('#login').focus();

                            return true;
                        }
                    </script>

                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="loginExists"/>
                        <tiles:put name="styleClass" value="loginExists"/>
                        <tiles:put name="closeCallback" value="onCloseLoginExistsWindow"/>
                        <tiles:put name="data" type="string">
                            <div class="description">
                            К сожалению, придуманный Вами логин уже занят. Пожалуйста, попробуйте другой вариант.
                            </div>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.close"/>
                                    <tiles:put name="commandHelpKey"    value="button.close"/>
                                    <tiles:put name="bundle"            value="securityBundle"/>
                                    <tiles:put name="viewType"          value="simpleLink"/>
                                    <tiles:put name="onclick"           value="win.close('loginExists');"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="hasSMS" value="${phiz:isContainStrategy(form.confirmStrategy,'sms')}"/>
                    <c:set var="hasCard" value="${phiz:isContainStrategy(form.confirmStrategy,'card')}"/>

                    <div class="buttonsArea">
                        <c:if test="${not form.hardRegistrationMode}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.cancel"/>
                                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                                <tiles:put name="bundle"            value="securityBundle"/>
                                <tiles:put name="viewType"          value="simpleLink"/>
                                <tiles:put name="action"            value="/private/accounts.do"/>
                            </tiles:insert>
                        </c:if>

                        <c:set var="confirmRequest" value="${form.confirmRequest}"/>
                        <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>

                        <c:set var="showUserData">
                            <div class="form-row">
                                <div class="paymentLabel"><span class="paymentTextLabel">Логин:&nbsp;</span></div>
                                <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${form.fields.login}"/></b></div></div>
                                <div class="clear"></div>
                            </div>

                            <div class="form-row">
                                <div class="paymentLabel"><span class="paymentTextLabel">Пароль:&nbsp;</span></div>
                                <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${phiz:mask('*', fn:length(form.fields.password))}"/></b></div></div>
                                <div class="clear"></div>
                            </div>

                            <c:if test="${form.needShowEmailAddress}">
                                <div class="form-row">
                                    <div class="paymentLabel"><span class="paymentTextLabel">Электронная почта:&nbsp;</span></div>
                                    <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${form.fields.email}"/></b></div></div>
                                    <div class="clear"></div>
                                </div>
                            </c:if>
                        </c:set>

                        <c:set var="confirmByCardButton">
                            <c:if test="${hasCard}">
                                <tiles:insert  definition="confirm_card" flush="false">
                                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                    <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                                    <tiles:put name="confirmableObject" value="login"/>
                                    <tiles:put name="message" value="Для обеспечения безопасности регистрации в системе необходимо ввести одноразовый пароль. Для подтверждения операции нажмите на кнопку «Подтвердить»."/>
                                    <tiles:put name="cardNumber" value="${phiz:getCutCardNumber(form.fields.confirmCard)}"/>
                                    <tiles:put name="data" value="${showUserData}"/>
                                    <tiles:put name="useAjax" value="false"/>
                                    <tiles:put name="ajaxUrl" value="private/registration"/>
                                    <tiles:put name="title" value="Подтверждение регистрации"/>
                                </tiles:insert>
                            </c:if>
                        </c:set>

                        <c:set var="confirmBySmsButton">
                            <c:if test="${hasSMS}">
                                <tiles:insert  definition="confirm_sms" flush="false">
                                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                    <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                                    <tiles:put name="confirmableObject" value="login"/>
                                    <tiles:put name="message" value="Для обеспечения безопасности регистрации в системе необходимо ввести одноразовый пароль. Для подтверждения операции нажмите на кнопку «Подтвердить»."/>
                                    <tiles:put name="useAjax" value="false"/>
                                    <tiles:put name="data" value="${showUserData}"/>
                                    <tiles:put name="title" value="Подтверждение регистрации"/>
                                </tiles:insert>
                            </c:if>
                        </c:set>

                        <c:choose>
                            <c:when test="${form.userOptionType == 'card'}">
                                ${confirmBySmsButton}
                                ${confirmByCardButton}
                            </c:when>
                            <c:otherwise>
                                ${confirmByCardButton}
                                ${confirmBySmsButton}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <script type="text/javascript">
                        var smsButtonIsDefault = <c:out value="${form.userOptionType eq 'card' ? 'false' : 'true'}"/>;

                        $(document).ready(function(){

                            if(window.Indicator)
                            {
                                var passwordIndicator = new Indicator("passwordField", stateComplexPasswordCsa, enableNextButtonForPass);
                                passwordIndicator.init("password");

                                var loginIndicator = new Indicator("loginField", stateComplexLoginCsa, enableNextButtonForLogin);
                                loginIndicator.init("login");
                            }

                            $("#showPassword").change(onShowOrHidePasswrods);

                            onShowOrHidePasswrods();

                            enableNextButton();

                            if (findCommandButton('button.confirm'))
                            {
                                findCommandButton('button.confirm').validationFunction=function() {showOrHideWaitDiv(true); return true;};
                            }

                        });

                        function onShowOrHidePasswrods()
                        {
                            var checked = $("#showPassword").get(0).checked;
                            showHidePassword('password', checked);
                            showHidePassword('confirmPassword', checked);
                            $('#hiddenShowPassword').val(checked);
                        }

                        var passValid = false;
                        var loginValid = false;

                        function enableNextButtonForPass(state)
                        {
                            passValid = state == "green";
                            enableNextButton();
                        }

                        function enableNextButtonForLogin(state)
                        {
                            loginValid = state == "green";
                            enableNextButton();
                        }

                        $("#confirmPassword").keyup(function(e){
                             enableNextButton();
                        })
                        .bind('paste', function(e){
                             enableNextButton();
                        })
                        .blur(function(e){
                            enableNextButton();
                        });

                        function enableNextButton()
                        {
                            if(loginValid && passValid && $("#confirmPassword").val()!='')
                            {
                                <c:if test="${hasSMS}">
                                    document.getElementById('confirmSMS').children[0].onclick = function(){findCommandButton('button.confirmSMS').click('', false); showOrHideWaitDiv(true);};
                                    document.getElementById('confirmSMS').children[0].className = smsButtonIsDefault ? "buttonGreen" : "simpleLink";
                                </c:if>
                                <c:if test="${hasCard}">
                                    document.getElementById('confirmCard').children[0].onclick = function(){findCommandButton('button.confirmCard').click('', false); showOrHideWaitDiv(true);};
                                    document.getElementById('confirmCard').children[0].className = smsButtonIsDefault ? "simpleLink" : "buttonGreen";
                                </c:if>
                            }
                            else
                            {
                                <c:if test="${hasSMS}">
                                    document.getElementById('confirmSMS').children[0].onclick = null;
                                    document.getElementById('confirmSMS').className = (smsButtonIsDefault ? "commandButton " : "") + " severalLocked";
                                    document.getElementById('confirmSMS').children[0].className = (smsButtonIsDefault ? "buttonGreen" : "simpleLink") + " disabled";
                                </c:if>
                                <c:if test="${hasCard}">
                                    document.getElementById('confirmCard').children[0].onclick = null;
                                    document.getElementById('confirmCard').className = (smsButtonIsDefault ? "commandButton" : "") + " severalLocked";
                                    document.getElementById('confirmCard').children[0].className = (smsButtonIsDefault ? "simpleLink" : "buttonGreen") + " disabled";
                                </c:if>
                            }
                        }

                        var checkLoginCount = 0;
                        var loginInfo = new Array();
                        <%-- проверяем логин на совпадение. --%>
                        function checkLogin()
                        {
                            var login = $('#login').val();
                            if (stateComplexLoginCsa(login) == "red" || loginInfo[login] == "valid" || checkLoginCount >= ${form.checkLoginMaxCount})
                            {
                                return;
                            }
                            else if (loginInfo[login] == "notValid")
                            {
                                win.open('loginExists');
                                return;
                            }

                            checkLoginCount++;
                            ajaxQuery("operation=ajax.checkLogin&login=" + decodeURItoWin(login),"${phiz:calculateActionURL(pageContext, '/private/async/registration.do')}",
                                function(data)
                                {
                                    if (data.indexOf('login_exists') >= 0)
                                    {
                                        win.open('loginExists');
                                        loginInfo[login] = "notValid";
                                    }
                                    else if (data.indexOf('login_not_exists') >= 0)
                                    {
                                        loginInfo[login] = "valid";
                                    }
                                    else
                                    {
                                        window.location.reload();
                                    }
                                }
                            );
                        }

						doOnLoad(function(){
                            $("#login").focus();
                             setCaretPosition($("#login")[0], [0,0]);
                        });
                    </script>
                </tiles:put>
            </tiles:insert>
        </html:form>
    </tiles:put>
</tiles:insert>
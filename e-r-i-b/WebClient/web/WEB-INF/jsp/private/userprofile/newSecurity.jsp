<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/userprofile/accountSecurity">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="confirmName" value="${form.confirmName}"/>
    <c:set var="hasCapButton" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    </c:if>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="faqDelivery" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm15')}"/>
    <c:set var="faqConfirm" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm6')}"/>
    <c:set var="bundle" value="userprofileBundle"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/accountSecurity/templeteViewShow')}"/>
    <c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>

    <tiles:insert definition="newUserProfile">
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="title">Настройки безопасности и доступов</tiles:put>
                <tiles:put name="activeItem">security</tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/complexValueIndicator.js"></script>
                    <tiles:insert definition="userSettings" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="recomendationLogin"/>
                                <tiles:put name="data" type="string">
                                    <bean:message key="login.recomendationForGood" bundle="securityBundle"/>
                                    <ul style="list-style:disc; margin-left:15px;">
                                        <bean:message key="login.recomendationForGood.list" bundle="securityBundle"
                                                      arg0="${form.minLoginLength}" arg1="30"/>
                                    </ul>
                                    <div class="buttonsArea">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.close.window"/>
                                            <tiles:put name="commandHelpKey" value="button.close.window"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="onclick" value="win.close('recomendationLogin')"/>
                                            <tiles:put name="viewType" value="simpleLink"/>
                                        </tiles:insert>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                            <c:if test="${phiz:impliesOperation('ChangeClientLoginOperation', 'ClientChangeLogin')}">
                                <%@ include file="clientChangeClientLoginOperation.jsp" %>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('GenerateIPasPasswordOperation', 'ClientChangeLogin')}">
                                <div class="securityOptions">
                                    <tiles:insert definition="newUserProfileSecurity" flush="false" operation="GenerateIPasPasswordOperation">
                                        <tiles:put name="title" value="Смена пароля"/>
                                        <tiles:put name="defaultCommandButon" value="button.saveIPasPassword"/>
                                        <tiles:put name="data">
                                            <fieldset>
                                                <table>
                                                    <tr><td>&nbsp;</td></tr>
                                                    <tr>
                                                        <td class="align-left valign-middle descRow">
                                                            <div class="passChangeLable"><bean:message bundle="userprofileBundle" key="label.password.current"/>:&nbsp;</div>
                                                        </td>
                                                        <c:if test="${not empty confirmRequest}">
                                                            <html:hidden name="form" property="field(oldPassword)" style="display:none"/>
                                                        </c:if>
                                                        <td class="align-left" colspan="2">
                                                            <html:password name="form" styleId="oldPassword2" property="field(oldPassword)" style="width: 200px;float: left;margin-right: 10px;"  disabled="${not empty confirmRequest}" onkeyup="enableNextButtonForPassword2()"/>
                                                        </td>
                                                    </tr>

                                                    <script type="text/javascript">
                                                        function enableNextButtonForPassword2()
                                                        {
                                                            var passwordText = $('#oldPassword2')[0];
                                                            var buttonForPassword2 = $('#nextButtonPassword2')[0];

                                                            if(passwordText.value != '')
                                                            {
                                                                if (buttonForPassword2 != null)
                                                                {
                                                                    buttonForPassword2.onclick = function(){findCommandButton('button.saveIPasPassword').click('', false);};
                                                                    $(buttonForPassword2).find('.buttonGreen').removeClass("disabled");
                                                                }
                                                                setDefaultCommandButon(findCommandButton('button.saveIPasPassword'));
                                                                $('#passwordSaveText2').show();
                                                            }
                                                            else
                                                            {
                                                                if (buttonForPassword2 != null)
                                                                {
                                                                    buttonForPassword2.onclick = null;
                                                                    $(buttonForPassword2).find('.buttonGreen').addClass("disabled");
                                                                }
                                                                setDefaultCommandButon(null);
                                                                $('#passwordSaveText2').hide();
                                                            }
                                                        }

                                                        $(document).ready(function(){
                                                            enableNextButtonForPassword2();
                                                        });
                                                    </script>

                                                    <tr><td>&nbsp;</td></tr>
                                                    <tr>
                                                        <td></td>
                                                        <td colspan="2">
                                                            <div class="buttonArea">
                                                                <c:choose>
                                                                    <c:when test="${not empty confirmRequest  and confirmName=='ipasPassword'}">
                                                                        <tiles:insert definition="confirmButtons" flush="false">
                                                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/ipasPasswordConfirm"/>
                                                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                                            <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                                            <tiles:put name="anotherStrategy" value="false"/>
                                                                        </tiles:insert>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <tiles:insert definition="commandButton" flush="false">
                                                                            <tiles:put name="commandKey" value="button.saveIPasPassword"/>
                                                                            <tiles:put name="commandTextKey" value="button.next"/>
                                                                            <tiles:put name="commandHelpKey" value="button.next.help"/>
                                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                                            <tiles:put name="id" value="nextButtonPassword2"/>
                                                                        </tiles:insert>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <c:if test="${empty confirmRequest  and confirmName!='ipasPassword'}">
                                                                <span class="rightButtonText" id="passwordSaveText2">
                                                                    Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
                                                                </span>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </fieldset>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('ChangeClientPasswordOperation', 'ClientChangeLogin')}">
                                <div class="securityOptions">
                                    <tiles:insert definition="newUserProfileSecurity" flush="false" operation="ChangeClientPasswordOperation">
                                        <tiles:put name="title" value="Смена пароля"/>
                                        <tiles:put name="helpText" value="Как правильно составить пароль?"/>
                                        <tiles:put name="helpTextOnclick" value="win.open('recomendationPassword');"/>
                                        <tiles:put name="data">
                                            <fieldset>
                                                <table>
                                                    <tr>
                                                        <td class="align-left valign-middle descRow">
                                                            <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.password.current"/>&nbsp;</div>
                                                        </td>
                                                        <c:if test="${not empty confirmRequest}">
                                                            <html:hidden name="form" property="field(oldPassword)" style="display:none"/>
                                                        </c:if>
                                                        <td class="align-left">
                                                            <html:password styleId="oldPassword" name="form" property="field(oldPassword)" style="width: 200px;float: left;margin-right: 10px;" maxlength="30" onkeyup="enableNextButton(null)"/>
                                                            <div id="oldPassworedEye" onclick="changeEye(this, 'oldPassword');" class="eyeClose"/>
                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <td class="align-left valign-middle descRow">
                                                            <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.password.new"/>&nbsp;</div>
                                                        </td>
                                                        <c:if test="${not empty confirmRequest}">
                                                            <html:hidden name="form" property="field(newPassword)" style="display:none"/>
                                                        </c:if>
                                                        <td  class="align-left">
                                                            <html:password styleId="newPassword" name="form" property="field(newPassword)" style="width: 200px;float: left;margin-right: 10px;" maxlength="30"/>
                                                            <div id="newPasswordEye" onclick="changeEye(this, 'newPassword');" class="eyeClose"/>
                                                        </td>
                                                        <td class="align-left valign-top">
                                                            <tiles:insert definition="complexIndicator" flush="false">
                                                                <tiles:put name="id" value="passwordField"/>
                                                                <tiles:put name="notShowIndicator" value="true"/>
                                                            </tiles:insert>
                                                        </td>
                                                    </tr>

                                                    <script type="text/javascript">
                                                        $(document).ready(function(){
                                                            var indicator = new Indicator("passwordField", stateComplexPasswordCsa, enableNextButton, "new");
                                                            indicator.init("newPassword");
                                                        });
                                                        function enableNextButton(state)
                                                        {
                                                            var buttonsavePassword = $('#nextButton')[0];
                                                            var oldPasswordText = $('#oldPassword')[0];
                                                            var newPasswordText = $('#newPassword')[0];
                                                            var newReplayPasswordText = $('#newPasswordReplay')[0];

                                                            if (newPasswordText.value != '' && newReplayPasswordText.value != '' && newPasswordText.value != newReplayPasswordText.value)
                                                            {
                                                                $('#newPasswordReplay').addClass("errorInput");
                                                                $('#replayPasswordMessage').show();
                                                            }
                                                            else
                                                            {
                                                                $('#newPasswordReplay').removeClass("errorInput");
                                                                $('#replayPasswordMessage').hide();
                                                            }

                                                            if (newPasswordText.value == '')
                                                                $('#passwordFieldIndicator').children('.div-indicator').children('.image-indicator').removeClass("safeImage");

                                                            if(oldPasswordText.value != '' || newPasswordText.value != '' || newReplayPasswordText.value != '')
                                                            {
                                                                if (buttonsavePassword)
                                                                {
                                                                    buttonsavePassword.onclick = function(){findCommandButton('button.savePassword').click('', false);};
                                                                    $(buttonsavePassword).find('.buttonGreen').removeClass("disabled");
                                                                }
                                                                setDefaultCommandButon(findCommandButton('button.savePassword'));
                                                                $('#passwordSaveText').show();
                                                                if (state != null && state != "green")
                                                                {
                                                                    $('#newPassword').addClass("errorInput");
                                                                    $('#passwordFieldIndicator').children('.div-indicator').children('.image-indicator').removeClass("safeImage");
                                                                    $('#passwordFieldIndicator').children('.div-indicator').children('.message-indicator').removeClass("marginLeft20");
                                                                }
                                                                else if (state == "green")
                                                                {
                                                                    $('#passwordFieldIndicator').children('.div-indicator').children('.image-indicator').addClass("safeImage");
                                                                    $('#passwordFieldIndicator').children('.div-indicator').children('.message-indicator').addClass("marginLeft20");
                                                                    $('#newPassword').removeClass("errorInput");
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (buttonsavePassword)
                                                                {
                                                                    buttonsavePassword.onclick = null;
                                                                    $(buttonsavePassword).find('.buttonGreen').addClass("disabled");
                                                                }
                                                                setDefaultCommandButon(null);
                                                                $('#passwordSaveText').hide();
                                                            }
                                                        }
                                                    </script>
                                                    <tr>
                                                        <td class="align-left valign-top">
                                                            <div class="loginChangeLabel"><span class="word-wrap"><bean:message bundle="userprofileBundle" key="label.password.new.replay"/>&nbsp;</span></div>
                                                        </td>
                                                        <c:if test="${not empty confirmRequest}">
                                                            <html:hidden name="form" property="field(newPasswordReplay)" style="display:none"/>
                                                        </c:if>
                                                        <td  class="align-left">
                                                            <html:password styleId="newPasswordReplay" name="form" property="field(newPasswordReplay)" style="width: 200px;float: left;margin-right: 10px;" maxlength="30" onkeyup="enableNextButton(null)"/>
                                                            <div id="newPasswordReplayEye" onclick="changeEye(this, 'newPasswordReplay');" class="eyeClose"/>
                                                        </td>
                                                        <td class="align-left valign-top">
                                                            <div id="replayPasswordMessage" class="indicator-complexity " style="width:210px">
                                                                <span class="message-indicator red-text">Пароли не совпадают</span>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <script type="text/javascript">
                                                        $(document).ready(function(){
                                                            changeEnableStatePasswordFields();
                                                        });
                                                        function changeEnableStatePasswordFields()
                                                        {
                                                           document.getElementById('oldPassword').disabled = ${not empty confirmRequest};
                                                           document.getElementById('newPasswordReplay').disabled = ${not empty confirmRequest};
                                                           document.getElementById('newPassword').disabled = ${not empty confirmRequest};
                                                        }

                                                        function changeEye(element, name)
                                                        {
                                                            if (element.className == "eyeClose")
                                                            {
                                                                element.className = "eyeOpen";
                                                                showHidePassword(name, true)
                                                            }
                                                            else
                                                            {
                                                                element.className = "eyeClose";
                                                                showHidePassword(name, false)
                                                            }
                                                        }
                                                    </script>
                                                    <tr><td>&nbsp;</td></tr>
                                                    <tr>
                                                        <td colspan="3">
                                                            <div class="buttonsArea strategy">
                                                                <c:choose>
                                                                    <c:when test="${not empty confirmRequest  and confirmName=='password'}">
                                                                        <tiles:insert definition="commandButton" flush="false">
                                                                            <tiles:put name="commandKey" value="button.backToEdit"/>
                                                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                                            <tiles:put name="enabled" value="true"/>
                                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                                        </tiles:insert>
                                                                        <tiles:insert definition="confirmButtons" flush="false">
                                                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/passwordConfirm"/>
                                                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                                            <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                                            <tiles:put name="anotherStrategy" value="false"/>
                                                                        </tiles:insert>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <tiles:insert definition="commandButton" flush="false">
                                                                            <tiles:put name="commandKey" value="button.savePassword"/>
                                                                            <tiles:put name="commandTextKey" value="button.save"/>
                                                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                                            <tiles:put name="enabled" value="false"/>
                                                                            <tiles:put name="id" value="nextButton"/>
                                                                        </tiles:insert>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <span class="rightButtonText" id="passwordSaveText">
                                                                Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </fieldset>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <tiles:insert definition="window" flush="false">
                                    <tiles:put name="id" value="recomendationPassword"/>
                                    <tiles:put name="data" type="string">
                                        <bean:message key="password.recomendationForGood" bundle="securityBundle"/>
                                        <ul style="list-style:disc; margin-left:15px;">
                                            <bean:message key="password.recomendationForGood.list" bundle="securityBundle"/>
                                        </ul>
                                        <div class="buttonsArea">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.close.window"/>
                                                <tiles:put name="commandHelpKey" value="button.close.window"/>
                                                <tiles:put name="bundle" value="commonBundle"/>
                                                <tiles:put name="onclick" value="win.close('recomendationPassword')"/>
                                                <tiles:put name="viewType" value="simpleLink"/>
                                            </tiles:insert>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>
                            </c:if>

                            <div class="securityOptions">
                                <tiles:insert definition="newUserProfileSecurity" flush="false">
                                    <tiles:put name="defaultCommandButon" value="button.save"/>
                                    <tiles:put name="title" value="Подтверждение входа в &laquo;Сбербанк Онлайн&raquo;"/>
                                    <tiles:put name="data">
                                        <c:if test="${not empty confirmRequest}">
                                            <html:hidden name="form" property="field(oneTimePassword)" style="display:none"/>
                                        </c:if>
                                        <div class="chkboxAlign">
                                            <html:checkbox styleId="oneTimePassword" onclick="confirmToogle()" name="form" property="field(oneTimePassword)" value="true" disabled="${not empty confirmRequest}"/>
                                            <label for="oneTimePassword" class="size14">Требовать одноразовый пароль при входе</label>
                                        </div>
                                        <div class="clear"></div>
                                        <div>&nbsp;</div>

                                        <div class="buttonArea marginLeft150">
                                            <c:choose>
                                                <c:when test="${not empty confirmRequest  and confirmName=='settings'}">
                                                    <tiles:insert definition="confirmButtons" flush="false">
                                                        <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity"/>
                                                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                        <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                        <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                        <tiles:put name="anotherStrategy" value="false"/>
                                                    </tiles:insert>
                                                </c:when>
                                                <c:otherwise>
                                                    <tiles:insert definition="commandButton" flush="false">
                                                        <tiles:put name="commandKey" value="button.save"/>
                                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="enabled" value="false"/>
                                                        <tiles:put name="id" value="nextButtonForConfirm"/>
                                                    </tiles:insert>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <c:if test="${empty confirmRequest  and confirmName!='settings'}">
                                            <span class="rightButtonText" id="confirmSaveText">
                                                Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
                                            </span>
                                        </c:if>
                                    </tiles:put>
                                </tiles:insert>

                                <script type="text/javascript">
                                    function confirmToogle()
                                    {
                                        if ($("#oneTimePassword").attr("checked") == startConfirm)
                                            enableNextButtonForConfirm(false);
                                        else
                                            enableNextButtonForConfirm(true);
                                    }

                                    function enableNextButtonForConfirm(state)
                                    {
                                        var nextButtonForConfirm = $('#nextButtonForConfirm')[0];
                                        if (nextButtonForConfirm)
                                        {
                                            if(state == true)
                                            {
                                                nextButtonForConfirm.onclick = function(){findCommandButton('button.save').click('', false);};
                                                $(nextButtonForConfirm).find('.buttonGreen').removeClass("disabled");
                                                $('#confirmSaveText').show();
                                            }
                                            else
                                            {
                                                nextButtonForConfirm.onclick = null;
                                                $(nextButtonForConfirm).find('.buttonGreen').addClass("disabled");
                                                $('#confirmSaveText').hide();
                                            }
                                        }
                                    }

                                    startConfirm = $("#oneTimePassword").attr("checked");
                                    $(document).ready(function(){
                                        confirmToogle();
                                    });

                                </script>
                            </div>

                            <div class="securityOptions">
                                <tiles:insert definition="newUserProfileSecurity" flush="false">
                                    <tiles:put name="defaultCommandButon" value="button.save"/>
                                    <tiles:put name="title" value="Предпочтительный способ подтверждения"/>
                                    <tiles:put name="helpText" value="Что такое способ подтверждения?"/>
                                    <tiles:put name="helpTextOnclick" value="openFAQ('${faqConfirm}');"/>
                                    <tiles:put name="data">
                                        <div class="profileMarginBlock">
                                            <c:if test="${not empty confirmRequest}">
                                                <span class="descRow">
                                                    <div class="circle float">
                                                        <html:hidden name="form" property="field(confirmType)" style="display:none"/>
                                                    </div>
                                                </span>
                                            </c:if>

                                            <span class="descRow">
                                                <div class="circle float">
                                                    <html:radio styleClass="circleInput" styleId="SMSConfirm" onclick="confirm2Toogle()" name="form" property="field(confirmType)" value="sms" disabled="${not empty confirmRequest}"/>
                                                </div>
                                                <label for="SMSConfirm" class="size14"><bean:message bundle="userprofileBundle" key="label.confirm.sms"/></label>
                                            </span>
                                            <span class="descRow">
                                                <div class="circle float">
                                                    <html:radio styleClass="circleInput" styleId="checkConfirm" onclick="confirm2Toogle()" name="form" property="field(confirmType)" value="card" disabled="${not empty confirmRequest}"/>
                                                </div>
                                                <label for="checkConfirm" class="size14"><bean:message bundle="userprofileBundle" key="label.confirm.card"/></label>
                                            </span>
                                            <c:if test="${phiz:impliesService('ClientProfilePush')}">
                                                <span class="descRow">
                                                    <div class="circle float">
                                                        <html:radio styleClass="circleInput" styleId="pushConfirm" onclick="confirm2Toogle()" name="form" property="field(confirmType)" value="push" disabled="${not empty confirmRequest}"/>
                                                    </div>
                                                    <label for="pushConfirm" class="size14"><bean:message bundle="userprofileBundle" key="label.confirm.push"/></label>
                                                </span>
                                            </c:if>
                                        </div>
                                        <div class="clear">&nbsp;</div>
                                        <div class="buttonArea marginLeft150">
                                            <c:choose>
                                                <c:when test="${not empty confirmRequest  and confirmName=='settingsConfirm'}">
                                                    <tiles:insert definition="confirmButtons" flush="false">
                                                        <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity"/>
                                                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                        <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                        <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                        <tiles:put name="anotherStrategy" value="false"/>
                                                    </tiles:insert>
                                                </c:when>
                                                <c:otherwise>
                                                    <tiles:insert definition="commandButton" flush="false">
                                                        <tiles:put name="commandKey" value="button.saveConfirmSettings"/>
                                                        <tiles:put name="commandTextKey" value="button.save"/>
                                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                                        <tiles:put name="bundle" value="commonBundle"/>
                                                        <tiles:put name="enabled" value="false"/>
                                                        <tiles:put name="id" value="nextButtonForConfirm2"/>
                                                    </tiles:insert>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <span class="rightButtonText" id="confirm2SaveText">
                                            Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
                                        </span>
                                    </tiles:put>
                                </tiles:insert>

                                <script type="text/javascript">
                                    function confirm2Toogle()
                                    {
                                        if (getCheckedRadioId('field(confirmType)') == startConfirm2)
                                            enableNextButtonForConfirm2(false);
                                        else
                                            enableNextButtonForConfirm2(true);
                                    }

                                    function enableNextButtonForConfirm2(state)
                                    {
                                        var nextButtonForConfirm2 = $('#nextButtonForConfirm2')[0];
                                        if(state == true)
                                        {
                                            if (nextButtonForConfirm2 != null)
                                            {
                                                nextButtonForConfirm2.onclick = function(){findCommandButton('button.saveConfirmSettings').click('', false);};
                                                $(nextButtonForConfirm2).find('.buttonGreen').removeClass("disabled");
                                            }
                                            $('#confirm2SaveText').show();
                                        }
                                        else
                                        {
                                            if (nextButtonForConfirm2 != null)
                                            {
                                                nextButtonForConfirm2.onclick = null;
                                                $(nextButtonForConfirm2).find('.buttonGreen').addClass("disabled");
                                            }
                                            $('#confirm2SaveText').hide();
                                        }
                                    }

                                    function getCheckedRadioId(name) {
                                        var elements = document.getElementsByName(name);
                                        var num = 0;
                                        for (var i=0, len=elements.length; i<len; ++i)
                                        {
                                            elements[i].parentNode.parentNode.className = " radioTriggers";
                                            if (elements[i].checked)
                                                num = i;
                                        }

                                        elements[num].parentNode.parentNode.className = "selectConfirmRaidoButton radioTriggers";
                                        return elements[num].value;
                                    }

                                    startConfirm2 = getCheckedRadioId('field(confirmType)');
                                    $(document).ready(function(){
                                        confirm2Toogle();
                                    });
                                </script>
                            </div>

                            <c:if test="${phiz:impliesService('IncognitoSetting')}">
                                <div class="securityOptions">
                                    <tiles:insert definition="newUserProfileSecurity" flush="false">
                                        <tiles:put name="defaultCommandButon" value="button.save"/>
                                        <tiles:put name="title" value="Настройка приватности"/>
                                        <tiles:put name="data">
                                            <c:if test="${not empty confirmRequest}">
                                                <html:hidden name="form" property="field(incognitoSetting)" style="display:none"/>
                                            </c:if>
                                            <div class="profileMarginBlock chkboxAlign">
                                                <html:checkbox onclick="incognitoToogle()" styleId="incognitoSetting" name="form" property="field(incognitoSetting)" disabled="${not empty confirmRequest}"/>
                                                <label for="incognitoSetting" class="size14">Не отображать меня как клиента Сбербанк в чужих адресных книгах.</label>
                                            </div>
                                            <div class="clear"></div>
                                            <div class="updatingPeriod"><bean:message bundle="userprofileBundle" key="label.incognito.updatingPeriod"/></div>
                                            <div class="incognitoNotification"><bean:message bundle="userprofileBundle" key="label.incognito.notification"/></div>
                                            <div class="clear"></div>

                                            <div>&nbsp;</div>
                                            <div class="buttonArea marginLeft150">
                                                <c:choose>
                                                    <c:when test="${not empty confirmRequest  and confirmName=='incognitoSetting'}">
                                                        <tiles:insert definition="confirmButtons" flush="false">
                                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/incognitoConfirm"/>
                                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                            <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                            <tiles:put name="anotherStrategy" value="false"/>
                                                        </tiles:insert>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tiles:insert definition="commandButton" flush="false">
                                                            <tiles:put name="commandKey" value="button.saveIncognitoSetting"/>
                                                            <tiles:put name="commandTextKey" value="button.save"/>
                                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="enabled" value="false"/>
                                                            <tiles:put name="id" value="nextButtonForIncognito"/>
                                                        </tiles:insert>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <span class="rightButtonText relative" id="incognitoSaveText">
                                                Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
                                            </span>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>

                                <script type="text/javascript">
                                    function incognitoToogle()
                                    {
                                        if ($("#incognitoSetting").attr("checked") == startIncognito)
                                            enableNextButtonForIncognito(false);
                                        else
                                            enableNextButtonForIncognito(true);
                                    }

                                    function enableNextButtonForIncognito(state)
                                    {
                                        var nextButtonForIncognito = $('#nextButtonForIncognito')[0];
                                        if(state == true)
                                        {
                                            if (nextButtonForIncognito != null)
                                            {
                                                nextButtonForIncognito.onclick = function(){findCommandButton('button.saveIncognitoSetting').click('', false);};
                                                $(nextButtonForIncognito).find('.buttonGreen').removeClass("disabled");
                                            }
                                            $('#incognitoSaveText').show();
                                        }
                                        else
                                        {
                                            if (nextButtonForIncognito != null)
                                            {
                                                nextButtonForIncognito.onclick = null;
                                                $(nextButtonForIncognito).find('.buttonGreen').addClass("disabled");
                                            }
                                            $('#incognitoSaveText').hide();
                                        }
                                    }

                                    startIncognito = $("#incognitoSetting").attr("checked");
                                    $(document).ready(function(){
                                        incognitoToogle();
                                    });
                                </script>
                            </c:if>
                            <div class="securityOptions" id="visibleProducts">
                                <tiles:insert definition="newUserProfileSecurity" flush="false">
                                    <tiles:put name="title" value="Настройка видимости продуктов"/>
                                    <tiles:put name="nameForOpen" value="productView"/>
                                    <tiles:put name="text">
                                        Для того чтобы изменить настройки видимости ваших продуктов в «Сбербанк Онлайн», банкоматах, терминалах, мобильных устройствах и виджетах социальных сетей, отметьте интересующие вас продукты и нажмите на кнопку «Сохранить».
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <script type="text/javascript">
                                            function hideOrShowClosed(element, name, product)
                                            {
                                                var closedTable = ensureElement(name);

                                                if (product == null)
                                                {
                                                    return;
                                                }
                                                if (element == null)
                                                {
                                                    closedTable.style.display = 'none';
                                                }
                                                else
                                                {
                                                    if (element.className == "notShowHidden")
                                                    {
                                                        element.className = "showHidden"
                                                        closedTable.style.display = '';

                                                        for(var childItem in element.childNodes) {
                                                            if (element.childNodes[childItem].tagName == "I")
                                                                element.childNodes[childItem].className = "showHiddenImage2";
                                                            if (element.childNodes[childItem].tagName == "SPAN")
                                                                element.childNodes[childItem].innerHTML = "Скрыть закрытые";
                                                        }
                                                    }
                                                    else
                                                    {
                                                        element.className = "notShowHidden"
                                                        for(var childItem in element.childNodes) {
                                                            if (element.childNodes[childItem].tagName == "I")
                                                                element.childNodes[childItem].className = "showHiddenImage";
                                                            if (element.childNodes[childItem].tagName == "SPAN")
                                                                element.childNodes[childItem].innerHTML = "Показать закрытые";
                                                        }
                                                        closedTable.style.display = 'none';
                                                    }
                                                }

                                                var elem = ensureElement('hidden' + product);
                                                if(elem != null && ensureElement(product))
                                                {
                                                    elem.value = ensureElement(product).checked ? product: '';
                                                }
                                            };

                                            function hideShowClosedProducts(element, hide)
                                            {
                                                if (hide == "true" && element != null)
                                                {
                                                    element.style.display = 'none';
                                                }
                                            };

                                            var card = new Array();
                                            var loan = new Array();
                                            var depo = new Array();
                                            var account = new Array();
                                            var imaccount = new Array();

                                            function addClosed(product, value, element)
                                            {
                                                switch (product)
                                                {
                                                    case "CARD":
                                                    {
                                                        card[card.length] = value;
                                                        break;
                                                    }
                                                    case "CARD_MOB":
                                                    {
                                                        card[card.length] = value;
                                                        break;
                                                    }
                                                    case "CARD_SOB":
                                                    {
                                                        card[card.length] = value;
                                                        break;
                                                    }
                                                    case "CARD_ES":
                                                    {
                                                        card[card.length] = value;
                                                        break;
                                                    }
                                                    case "LOAN":
                                                    {
                                                        loan[loan.length] = value;
                                                        break;
                                                    }
                                                   case "LOAN_MOB":
                                                    {
                                                        loan[loan.length] = value;
                                                        break;
                                                    }
                                                   case "LOAN_SOB":
                                                    {
                                                        loan[loan.length] = value;
                                                        break;
                                                    }
                                                   case "LOAN_ES":
                                                    {
                                                        loan[loan.length] = value;
                                                        break;
                                                    }
                                                    case "DEPO_ACCOUNT":
                                                    {
                                                        depo[depo.length] = value;
                                                        break;
                                                    }
                                                    case "ACCOUNT":
                                                    {
                                                        account[account.length] = value;
                                                        break;
                                                    }
                                                     case "ACCOUNT_MOB":
                                                    {
                                                        account[account.length] = value;
                                                        break;
                                                    }
                                                      case "ACCOUNT_SOB":
                                                    {
                                                        account[account.length] = value;
                                                        break;
                                                    }
                                                     case "ACCOUNT_ES":
                                                    {
                                                        account[account.length] = value;
                                                        break;
                                                    }
                                                    case "IM_ACCOUNT":
                                                    {
                                                        imaccount[imaccount.length] = value;
                                                        break;
                                                    }
                                                    case "IM_ACCOUNT_ES":
                                                    {
                                                        imaccount[imaccount.length] = value;
                                                        break;
                                                    }
                                                }
                                            };

                                            function getElementsByProduct(product)
                                            {
                                                switch (product)
                                                {
                                                    case "CARD":
                                                    {
                                                        return document.getElementsByName('selectedCardIds');
                                                    }
                                                    case "CARD_MOB":
                                                    {
                                                        return document.getElementsByName('selectedCardMobileIds');
                                                    }
                                                    case "CARD_SOB":
                                                    {
                                                        return document.getElementsByName('selectedCardSocialIds');
                                                    }
                                                    case "CARD_ES":
                                                    {
                                                        return document.getElementsByName('selectedCardESIds');
                                                    }
                                                    case "CARD_SMS":
                                                    {
                                                        return document.getElementsByName('selectedCardSmsIds');
                                                    }
                                                    case "LOAN":
                                                    {
                                                        return document.getElementsByName('selectedLoanIds');
                                                    }
                                                    case "LOAN_MOB":
                                                    {
                                                        return document.getElementsByName('selectedLoanMobileIds');
                                                    }
                                                    case "LOAN_SOB":
                                                    {
                                                        return document.getElementsByName('selectedLoanSocialIds');
                                                    }
                                                    case "LOAN_ES":
                                                    {
                                                        return document.getElementsByName('selectedLoanESIds');
                                                    }
                                                    case "LOAN_SMS":
                                                    {
                                                        return document.getElementsByName('selectedLoanSmsIds');
                                                    }
                                                    case "DEPO_ACCOUNT":
                                                    {
                                                        return document.getElementsByName('selectedDepoAccountIds');
                                                    }
                                                    case "ACCOUNT":
                                                    {
                                                        return document.getElementsByName('selectedAccountIds');
                                                    }
                                                    case "ACCOUNT_MOB":
                                                    {
                                                        return document.getElementsByName('selectedAccountMobileIds');
                                                    }
                                                    case "ACCOUNT_SOB":
                                                    {
                                                        return document.getElementsByName('selectedAccountSocialIds');
                                                    }
                                                    case "ACCOUNT_ES":
                                                    {
                                                        return document.getElementsByName('selectedAccountESIds');
                                                    }
                                                    case "ACCOUNT_SMS":
                                                    {
                                                        return document.getElementsByName('selectedAccountSmsIds');
                                                    }
                                                    case "IM_ACCOUNT":
                                                    {
                                                        return document.getElementsByName('selectedIMAccountIds');
                                                    }
                                                    case "IM_ACCOUNT_ES":
                                                    {
                                                        return document.getElementsByName('selectedIMAccountESIds');
                                                    }
                                                    default:
                                                    {
                                                        return null;
                                                    }
                                                }
                                            };

                                            function canRedirect()
                                            {
                                                if (tableProductsChanged() || ${not empty confirmRequest})
                                                {
                                                    win.open('redirectRefused');
                                                    return false;
                                                }
                                                return true;
                                            }

                                            function tableProductsChanged()
                                            {
                                                return selectDataChanged("selectedCardIds,selectedAccountIds,selectedLoanIds,selectedIMAccountIds,selectedDepoAccountIds,selectedSecurityAccountIds,pfrLinkSelected," +
                                                                            "selectedCardESIds,selectedAccountESIds,selectedLoanESIds,selectedIMAccountESIds," +
                                                                            "selectedCardMobileIds,selectedAccountMobileIds,selectedLoanMobileIds,selectedIMAccountMobileIds," +
                                                                            "selectedCardSocialIds,selectedAccountSocialIds,selectedLoanSocialIds,selectedIMAccountSocialIds," +
                                                                            "selectedCardSMSIds,selectedAccountSMSIds,selectedLoanSMSIds,selectedIMAccountSMSIds,newProductsShowInSms");
                                            }

                                            function showDescSys(elementId)
                                            {
                                                if (tableProductsChanged())
                                                    showAllProductsButtons(elementId);
                                                else
                                                    hideAllProductsButtons();
                                            }

                                            function hideAllProductsButtons()
                                            {
                                               $('#saveTextSys').hide();
                                               $('#saveTextSMS').hide();
                                               $('#saveTextES').hide();
                                               $('#saveTextMobile').hide();
                                               $('#saveTextSocial').hide();

                                               var nextButtonForES = $('#nextButtonForES')[0];
                                               if (nextButtonForES != null)
                                               {
                                                   nextButtonForES.onclick = null;
                                                   $(nextButtonForES).find('.buttonGreen').addClass("disabled");
                                               }

                                               var nextButtonForMobile = $('#nextButtonForMobile')[0];
                                               if (nextButtonForMobile != null)
                                               {
                                                   nextButtonForMobile.onclick = null;
                                                   $(nextButtonForMobile).find('.buttonGreen').addClass("disabled");
                                               }

                                               var nextButtonForSocial = $('#nextButtonForSocial')[0];
                                               if (nextButtonForSocial != null)
                                               {
                                                   nextButtonForSocial.onclick = null;
                                                   $(nextButtonForSocial).find('.buttonGreen').addClass("disabled");
                                               }

                                               var nextButtonForErmb = $('#nextButtonForErmb')[0];
                                               if (nextButtonForErmb != null)
                                               {
                                                   nextButtonForErmb.onclick = null;
                                                   $(nextButtonForErmb).find('.buttonGreen').addClass("disabled");
                                               }

                                               var nextButtonForSystem = $('#nextButtonForSystem')[0];
                                               if (nextButtonForSystem != null)
                                               {
                                                   nextButtonForSystem.onclick = null;
                                                   $(nextButtonForSystem).find('.buttonGreen').addClass("disabled");
                                               }
                                            }

                                            function showAllProductsButtons(elementId)
                                            {
                                               $('#saveTextSys').show();
                                               $('#saveTextSMS').show();
                                               $('#saveTextES').show();

                                               if (elementId.indexOf("Social") != -1) {
                                                   if (isMoreShown(elementId)){
                                                       $('#saveTextSocial').show();
                                                   }
                                                   $('#saveTextMobile').show();
                                               }

                                               if (elementId.indexOf("Mobile") != -1) {
                                                   if (isMoreShown(elementId)){
                                                       $('#saveTextMobile').show();
                                                   }
                                                   $('#saveTextSocial').show();
                                               }

                                                var nextButtonForES = $('#nextButtonForES')[0];
                                               if (nextButtonForES != null)
                                               {
                                                   nextButtonForES.onclick = function(){findCommandButton('button.saveShowInES').click('', false);};
                                                   $(nextButtonForES).find('.buttonGreen').removeClass("disabled");
                                               }

                                                var nextButtonForMobile = $('#nextButtonForMobile')[0];
                                               if (nextButtonForMobile != null)
                                               {
                                                   nextButtonForMobile.onclick = function(){findCommandButton('button.saveShowInMobile').click('', false);};
                                                   $(nextButtonForMobile).find('.buttonGreen').removeClass("disabled");
                                               }

                                                 var nextButtonForSocial = $('#nextButtonForSocial')[0];
                                               if (nextButtonForSocial != null)
                                               {
                                                   nextButtonForSocial.onclick = function(){findCommandButton('button.saveShowInSocial').click('', false);};
                                                   $(nextButtonForSocial).find('.buttonGreen').removeClass("disabled");
                                               }

                                                var nextButtonForErmb = $('#nextButtonForErmb')[0];
                                               if (nextButtonForErmb != null)
                                               {
                                                   nextButtonForErmb.onclick = function(){findCommandButton('button.saveShowInErmb').click('', false);};
                                                   $(nextButtonForErmb).find('.buttonGreen').removeClass("disabled");
                                               }

                                                var nextButtonForSystem = $('#nextButtonForSystem')[0];
                                               if (nextButtonForSystem != null)
                                               {
                                                   nextButtonForSystem.onclick = function(){findCommandButton('button.saveShowInSystem').click('', false);};
                                                   $(nextButtonForSystem).find('.buttonGreen').removeClass("disabled");
                                               }
                                            }
                                            var productItemsSelector = 'div .profile-user-products';
                                            function removeActiveMenuClass(mode, modeId, modeItemId, obj)
                                            {
                                                $('#inSystem').removeClass('activeSecondMenu');
                                                $('#inMobile').removeClass('activeSecondMenu');
                                                $('#inSocial').removeClass('activeSecondMenu');
                                                $('#inES').removeClass('activeSecondMenu');
                                                $('#inSMS').removeClass('activeSecondMenu');
                                                $('#inES-items').hide();
                                                $('#inMobile-items').hide();
                                                $('#inSocial-items').hide();
                                                $('#inSystem-items').hide();
                                                $('#inSMS-items').hide();

                                                $('#pageType').val(mode);
                                                $('#' + modeId).addClass('activeSecondMenu');
                                                $('#' + modeItemId).show();

                                                $(productItemsSelector).trigger('onTabClick', obj);
                                                profileProductName(modeItemId);
                                            }

                                            function profileProductName(modeItemId)
                                            {
                                                $('#' + modeItemId).find('td.productText').each(function()
                                                {
                                                    var indent = 30;
                                                    var productText = $(this).width();
                                                    var imageType = $(this).find('.visaImage').width();
                                                    var prodNameWidth = $(this).find('.productProfileWidth').width();
                                                    var prodNum = $(this).find('.card-number').width();

                                                    var maxProdName = productText - imageType - prodNum - indent;

                                                    if(prodNameWidth >= maxProdName) {
                                                        $(this).find('.productProfileWidth').css("width", maxProdName);
                                                        $(this).find('.card-number').addClass("s-name");
                                                    }
                                                    else{
                                                        $(this).find('.productProfileWidth').css("width", "");
                                                        $(this).find('.card-number').removeClass("s-name");
                                                    }
                                                })
                                            }


                                            $(document).ready(function(){
                                                initData();

                                                $(productItemsSelector).bind('onTabClick', function(event, originEventEl, options)
                                                {
                                                    var target = $(event.target);

                                                    if (options && options.showAll == false)
                                                    {
                                                        if (target.attr('class').indexOf("inSocial") > -1)
                                                        {
                                                            target.show();
                                                        }
                                                        else
                                                        {
                                                            target.hide();
                                                        }
                                                    }

                                                    if (!options || options.showAll === undefined || options.showAll == true)
                                                    {
                                                        target.show();
                                                    }
                                                });

                                                $('#inSystem').click(function(){
                                                    if (!canRedirect())
                                                        return;

                                                    removeActiveMenuClass('system', 'inSystem', 'inSystem-items', this);
                                                });
                                                $('#inMobile').click(function(){
                                                    if (!canRedirect())
                                                        return;

                                                    removeActiveMenuClass('mobile', 'inMobile', 'inMobile-items', this);
                                                });

                                                $('#inSocial').click(function(){
                                                    if (!canRedirect())
                                                        return;

                                                    removeActiveMenuClass('social', 'inSocial', 'inSocial-items', [this, {showAll : false}]);
                                                });

                                                $('#inSMS').click(function(){
                                                    if (!canRedirect())
                                                        return;

                                                    removeActiveMenuClass('SMS', 'inSMS', 'inSMS-items', this);
                                                }) ;
                                                $('#inES').click(function(){
                                                    if (!canRedirect())
                                                        return;

                                                    removeActiveMenuClass('ES', 'inES', 'inES-items', this);
                                                }) ;

                                                if ($("#pageType").val() == 'ES')
                                                {
                                                    removeActiveMenuClass('ES', 'inES', 'inES-items', this);
                                                }
                                                if ($("#pageType").val() == 'mobile')
                                                {
                                                    removeActiveMenuClass('mobile', 'inMobile', 'inMobile-items', this);
                                                }
                                                if ($("#pageType").val() == 'social')
                                                {
                                                    removeActiveMenuClass('social', 'inSocial', 'inSocial-items', [this, {showAll : false}]);
                                                }
                                                if ($("#pageType").val() == 'SMS')
                                                {
                                                    removeActiveMenuClass('SMS', 'inSMS', 'inSMS-items', this);
                                                }
                                                hideAllProductsButtons();

                                                <c:if test="${form.needOpenTab == 'mobileProductView' && empty confirmRequest}">
                                                    var elem = $('div[name=productView]')[0];
                                                    if (elem != null)
                                                        elem.onclick();
                                                    $('#inMobile').click();
                                                </c:if>
                                                <c:if test="${form.needOpenTab == 'socialProductView' && empty confirmRequest}">
                                                    var elem = $('div[name=productView]')[0];
                                                    if (elem != null)
                                                        elem.onclick();
                                                    $('#inSocial').click();
                                                </c:if>
                                                <c:if test="${form.needOpenTab == 'productView' && empty confirmRequest}">
                                                    var elem = $('div[name=productView]')[0];
                                                    if (elem != null)
                                                        elem.onclick();
                                                </c:if>
                                                <c:if test="${form.needOpenTab == 'templateView' && empty confirmRequest}">
                                                    var elem = $('div[name=templateView]')[0];
                                                    if (elem != null)
                                                        elem.onclick();
                                                </c:if>
                                            });
                                        </script>

                                        <html:hidden property="field(unsavedData)" name="form"/>
                                        <html:hidden property="field(pageType)" styleId="pageType" name="form"/>


                                        <div class="payments-tabs newProfileTabs">
                                            <div class="filter triggerFilter" onkeypress="onEnterKey(event);">
                                                <tiles:insert definition="paymentTabs" flush="false">
                                                    <tiles:put name="count" value="5"/>
                                                    <tiles:put name="tabItems">
                                                        <c:if test="${phiz:impliesServiceRigid('ProductsRCSView')}">
                                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                                <tiles:put name="position" value="first"/>
                                                                <tiles:put name="active" value="true"/>
                                                                <tiles:put name="title" value="Сбербанк Онлайн"/>
                                                                <tiles:put name="id" value="inSystem"/>
                                                            </tiles:insert>
                                                        </c:if>
                                                        <c:if test="${phiz:impliesServiceRigid('ProductsSiriusView')}">
                                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                                <tiles:put name="active" value="false"/>
                                                                <tiles:put name="title" value="Банкоматы, терминалы"/>
                                                                <tiles:put name="id" value="inES"/>
                                                            </tiles:insert>
                                                        </c:if>
                                                        <c:if test="${phiz:impliesServiceRigid('HideProductMobileService')}">
                                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                                <tiles:put name="active" value="false"/>
                                                                <tiles:put name="title" value="Мобильные устройства "/>
                                                                <tiles:put name="id" value="inMobile"/>
                                                            </tiles:insert>
                                                        </c:if>
                                                        <tiles:insert definition="paymentTabItem" flush="false">
                                                            <tiles:put name="active" value="false"/>
                                                            <tiles:put name="title" value="Соц. приложения "/>
                                                            <tiles:put name="id" value="inSocial"/>
                                                        </tiles:insert>
                                                        <c:if test="${isERMBConnectedPerson}">
                                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                                <tiles:put name="position" value="last"/>
                                                                <tiles:put name="active" value="false"/>
                                                                <tiles:put name="title" value="СМС"/>
                                                                <tiles:put name="id" value="inSMS"/>
                                                            </tiles:insert>
                                                        </c:if>
                                                    </tiles:put>
                                                </tiles:insert>
                                            </div>
                                            <div class="clear"></div>

                                            <tiles:insert definition="window" flush="false">
                                                <tiles:put name="id" value="oneTimePasswordWindow"/>
                                            </tiles:insert>

                                             <%@ include file="accountsNewViewInSys.jsp"%>
                                             <%@ include file="accountsNewViewInES.jsp"%>
                                             <%@ include file="accountsNewViewInMobile.jsp"%>
                                             <%@ include file="accountsNewViewInSocial.jsp"%>
                                             <c:if test="${isERMBConnectedPerson}">
                                                <%@ include file="accountsNewViewInSMS.jsp"%>
                                             </c:if>

                                        </div>

                                        <tiles:insert definition="window" flush="false">
                                            <tiles:put name="id" value="redirectRefused"/>
                                            <tiles:put name="data">
                                                 <h2>Внимание!</h2>
                                                 Для перехода на другую страницу, пожалуйста, сохраните изменения. Если Вы не хотите сохранять изменения, нажмите на кнопку «Отменить».
                                                <div class="buttonsArea">
                                                    <tiles:insert definition="clientButton" flush="false">
                                                        <tiles:put name="commandTextKey" value="button.close"/>
                                                        <tiles:put name="commandHelpKey" value="button.close"/>
                                                        <tiles:put name="bundle" value="pfrBundle"/>
                                                        <tiles:put name="viewType" value="buttonGrey"/>
                                                        <tiles:put name="onclick" value="win.close('redirectRefused');"/>
                                                    </tiles:insert>
                                                </div>
                                            </tiles:put>
                                        </tiles:insert>
                                    </tiles:put>
                                    <tiles:put name="onClickHandler" value="profileProductName('inSystem-items');"/>
                                </tiles:insert>
                            </div>

                            <div class="securityOptions">
                                <tiles:insert definition="newUserProfileSecurity" flush="false">
                                    <tiles:put name="title" value="Настройка видимости шаблонов"/>
                                    <tiles:put name="nameForOpen" value="templateView"/>
                                    <tiles:put name="text">
                                        Управляйте доступом к вашим шаблонам в &laquo;Сбербанк Онлайн&raquo;, банкоматах, терминалах и мобильных устройствах.
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <html:hidden property="field(channelType)" styleId="channelType" name="form"/>
                                            <html:hidden property="field(changedIds)" styleId="changedIds" name="form"/>

                                            <script type="text/javascript">
                                                var changedIds = new Array();
                                                var channels = ['atm', 'internet', 'sms', 'mobile'];

                                                function displayChannel(name)
                                                {
                                                    $('#channelType').val(name);

                                                    for (var i = 0; i < channels.length; i++)
                                                    {
                                                        if (channels[i] == name)
                                                        {
                                                            $('#' + name).addClass('activeSecondMenu');
                                                            $('div[id^="use' + name + '"]').show();
                                                        }
                                                        else
                                                        {
                                                            $('#' + channels[i]).removeClass('activeSecondMenu');
                                                            $('div[id^="use' + channels[i] + '"]').hide();
                                                        }
                                                    }

                                                    checkedChannel(name);
                                                }

                                                function checkedChannel(channel)
                                               {
                                                   var startId = channel + 'CheckBox';
                                                   var channelArray = $('input[id^=' + startId + ']:not(input[id^=' + startId + 'Draft])');
                                                   var draftArray = $('input[id^=' + startId + 'Draft]');

                                                   updateCheckBox(channelArray, startId);
                                                   updateCheckBox(draftArray, startId + 'Draft');
                                               }

                                               function updateCheckBox(channelArray, startId)
                                               {
                                                   for (var i = 0; i <channelArray.size(); i++)
                                                   {
                                                       var id = channelArray[i].id;
                                                       var templateId = id.substr(startId.length, id.length);
                                                       var lineCheckBox = $("input[type=checkbox][value=" + templateId + "]");

                                                       if (channelArray[i].value == 'true')
                                                           lineCheckBox.attr('checked', 'checked');
                                                       else
                                                           lineCheckBox.removeAttr('checked');
                                                   }
                                               }

                                               function initTemplateView()
                                               {
                                                   var channelType = document.getElementById('channelType');
                                                   displayChannel(channelType.value);

                                                   var startId = 'internetCheckBoxDraft';
                                                   var draftArray = $('input[id^=' + startId + ']');
                                                   for (var i = 0; i < draftArray.size(); i++)
                                                   {
                                                       var id = draftArray[i].id;
                                                       var templateId = id.substr(startId.length, id.length);
                                                       $('input[type=checkbox][value='+ templateId + ']').attr('disabled', 'disabled');
                                                   }

                                                   initializeChangedIds();
                                               }

                                               function initializeChangedIds()
                                               {
                                                   var array = $('input[name=selectedIds]');
                                                   for (var i = 0; i < array.length; i++)
                                                   {
                                                       changedIds[i] = new Array();
                                                       changedIds[i][0] = array[i].value;
                                                       changedIds[i][1] = array[i].checked;
                                                   }
                                               }

                                                $(document).ready(function()
                                                {
                                                    $('#atm').click(function()
                                                    {
                                                        $('#channelType').val('atm');
                                                        changeChannel()
                                                    });

                                                    $('#internet').click(function()
                                                    {
                                                        $('#channelType').val('internet');
                                                        changeChannel()
                                                    });

                                                    $('#sms').click(function()
                                                    {
                                                        $('#channelType').val('sms');
                                                        changeChannel()
                                                    });

                                                    $('#mobile').click(function()
                                                    {
                                                        $('#channelType').val('mobile');
                                                        changeChannel()
                                                    });

                                                    $('input[type=checkbox]:first').click(function()
                                                    {
                                                        var channelType = document.getElementById('channelType');
                                                        var startId = channelType.value + 'CheckBox';

                                                        $('input[id^=' + startId + ']:not(input[id^=' + startId + 'Draft])').val(this.checked);

                                                        switchSelection(this, 'selectedIds');
                                                        updateCheckBox($('input[id^=' + startId + 'Draft]'), startId + 'Draft');
                                                    });
                                                    initTemplateView();
                                                });

                                                function updateChangedIds()
                                                {
                                                    var result = "";
                                                    var array = $('input[name=selectedIds]');
                                                    for (var i = 0; i < array.length; i++)
                                                    {
                                                        if (array[i].checked ^ changedIds[i][1])
                                                        {
                                                            if (result != "")
                                                                result += ",";
                                                            result += changedIds[i][0];
                                                        }
                                                    }

                                                    document.getElementById('changedIds').value = result;
                                                    return true;
                                                }

                                                function onTemplateClick(checkbox)
                                                {
                                                    var channelType = document.getElementById('channelType');
                                                    var templateId = checkbox.value;
                                                    templateId = templateId.replace(/^\s+/, "").replace(/\s+$/, "");

                                                    var hiddenCheckBox = $('#' + channelType.value + 'CheckBox' + templateId);

                                                    if (hiddenCheckBox.val() == null)
                                                        return;

                                                    hiddenCheckBox.val(checkbox.checked);

                                                    var nextButtonTemplate = $('#nextButtonTemplate')[0];
                                                    if (tableTemplateChanged())
                                                    {

                                                        if (nextButtonTemplate != null)
                                                        {
                                                            nextButtonTemplate.onclick = function(){findCommandButton('button.saveTemplates').click('', false);};
                                                            $(nextButtonTemplate).find('.buttonGreen').removeClass("disabled");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (nextButtonTemplate != null)
                                                        {
                                                            nextButtonTemplate.onclick = null;
                                                            $(nextButtonTemplate).find('.buttonGreen').addClass("disabled");
                                                        }
                                                    }
                                                }

                                                function tableTemplateChanged()
                                                {
                                                    var result = "";
                                                    var array = $('input[name=selectedIds]');
                                                    for (var i = 0; i < array.length; i++)
                                                    {
                                                        if (array[i].checked ^ changedIds[i][1])
                                                        {
                                                            return true;
                                                        }
                                                    }
                                                    return false;
                                                }

                                                function sendedAjaxResult(data)
                                                {
                                                    if (trim(data) == '')
                                                    {
                                                        window.location.reload();
                                                        return;
                                                    }
                                                    $('#TableViewTemplates').html(data);

                                                    $('#internet').removeClass('activeSecondMenu');
                                                    $('#atm').removeClass('activeSecondMenu');
                                                    $('#mobile').removeClass('activeSecondMenu');
                                                    $('#sms').removeClass('activeSecondMenu');
                                                    var currentChannel = $('#channelType').val();
                                                    $('#' + currentChannel).addClass('activeSecondMenu');

                                                    initTemplateView();
                                                }

                                                function changeChannel()
                                                {
                                                    ajaxTableList("$$pagination_size0", 20,"$$pagination_offset0",0);
                                                }

                                                function ajaxTableList(sizeFieldName,paginationSize,offsetFieldName, offset)
                                                {
                                                    //окно о том что не сохранили результат
                                                    var params = offsetFieldName + '=' + offset + '&' + sizeFieldName + '=' + paginationSize + "&operation=asyncTemplateTable" +"&field(channelType)=" + $('#channelType').val();
                                                    ajaxQuery(params, '${url}', sendedAjaxResult);
                                                }

                                            </script>

                                            <div class="payments-tabs newProfileTabs">
                                                <div class="filter triggerFilter" onkeypress="onEnterKey(event);">
                                                    <tiles:insert definition="paymentTabs" flush="false">
                                                        <tiles:put name="count" value="5"/>
                                                        <tiles:put name="tabItems">
                                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                                <tiles:put name="position" value="first"/>
                                                                <tiles:put name="active" value="true"/>
                                                                <tiles:put name="title" value="Сбербанк Онлайн"/>
                                                                <tiles:put name="id" value="internet"/>
                                                            </tiles:insert>
                                                            <c:if test="${phiz:impliesServiceRigid('ProductsSiriusView')}">
                                                                <tiles:insert definition="paymentTabItem" flush="false">
                                                                    <tiles:put name="active" value="false"/>
                                                                    <tiles:put name="title" value="Банкоматы, терминалы"/>
                                                                    <tiles:put name="id" value="atm"/>
                                                                </tiles:insert>
                                                            </c:if>
                                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                                <tiles:put name="active" value="false"/>
                                                                <tiles:put name="title" value="Мобильные устройства "/>
                                                                <tiles:put name="id" value="mobile"/>
                                                            </tiles:insert>
                                                            <c:if test="${isERMBConnectedPerson}">
                                                                <tiles:insert definition="paymentTabItem" flush="false">
                                                                    <tiles:put name="position" value="last"/>
                                                                    <tiles:put name="active" value="false"/>
                                                                    <tiles:put name="title" value="СМС"/>
                                                                    <tiles:put name="id" value="sms"/>
                                                                </tiles:insert>
                                                            </c:if>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                </div>
                                                <div class="clear"></div>
                                                <tiles:insert definition="window" flush="false">
                                                    <tiles:put name="id" value="oneTimePasswordWindow"/>
                                                </tiles:insert>

                                                <div class="listContainer">
                                                    <div id="TableViewTemplates">
                                                        <%@ include file="tableViewTemplates.jsp"%>
                                                    </div>
                                                </div>
                                            </div>

                                            <tiles:insert definition="window" flush="false">
                                                <tiles:put name="id" value="redirectRefused"/>
                                                <tiles:put name="data">
                                                     <h2>Внимание!</h2>
                                                     Для перехода на другую страницу, пожалуйста, сохраните изменения. Если Вы не хотите сохранять изменения, нажмите на кнопку «Отменить».
                                                    <div class="buttonsArea">
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey"    value="button.close"/>
                                                            <tiles:put name="commandHelpKey"    value="button.close"/>
                                                            <tiles:put name="bundle"            value="pfrBundle"/>
                                                            <tiles:put name="viewType"          value="buttonGrey"/>
                                                            <tiles:put name="onclick"           value="win.close('redirectRefused');"/>
                                                        </tiles:insert>
                                                    </div>
                                                </tiles:put>
                                            </tiles:insert>

                                            <%--для работы пагинации делаем скрытую кнопку button.filter, тк фильтр на странице не нужен--%>
                                            <div style="display:none">
                                                <tiles:insert definition="commandButton" operation="EditClientTemplatesShowSettingsOperation" flush="false">
                                                    <tiles:put name="commandKey"        value="button.filter"/>
                                                    <tiles:put name="commandHelpKey"    value="button.filter.help"/>
                                                    <tiles:put name="bundle"            value="commonBundle"/>
                                                </tiles:insert>
                                            </div>
                                            <div class="buttonsArea">
                                                <tiles:insert definition="commandButton" operation="EditClientTemplatesShowSettingsOperation" flush="false">
                                                    <tiles:put name="commandKey"        value="button.saveTemplates"/>
                                                    <tiles:put name="commandTextKey"    value="button.save"/>
                                                    <tiles:put name="commandHelpKey"    value="button.save.help"/>
                                                    <tiles:put name="isDefault"         value="true"/>
                                                    <tiles:put name="bundle"            value="commonBundle"/>
                                                    <tiles:put name="validationFunction" value="updateChangedIds()"/>
                                                    <tiles:put name="enabled" value="false"/>
                                                    <tiles:put name="id" value="nextButtonTemplate"/>
                                                </tiles:insert>
                                            </div>
                                    </tiles:put>
                                </tiles:insert>
                            </div>

                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <c:if test="${phiz:isScriptsRSAActive()}">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>
    </c:if>
    <script type="text/javascript">
        function checkData()
        {
            var unsavedData = ensureElementByName("field(unsavedData)").value;
            if (unsavedData) return true;
            var changedFieldNames = getChangedFieldNames();
            for (var i = 0; i <changedFieldNames.length; i++)
            {
                var changedFieldName = changedFieldNames[i];
                if ($.inArray(changedFieldName, ['field(pageType)', 'selectedClosedCards', 'selectedClosedAccounts', 'selectedClosedLoans', 'selectedClosedIMAccounts','selectedClosedDepoAccounts']) == -1)
                    return true;
            }
            addMessage("Вы не внесли никаких изменений в настройки видимости продуктов.");
            return false;
        }

        $(document).ready(function(){
            <%-- формирование основных данных для ФМ --%>
            <c:if test="${phiz:isScriptsRSAActive()}">
                new RSAObject().toHiddenParameters();
            </c:if>
        });
    </script>
</html:form>
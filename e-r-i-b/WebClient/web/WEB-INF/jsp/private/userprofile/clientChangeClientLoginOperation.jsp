<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<div class="securityOptions">
    <tiles:insert definition="newUserProfileSecurity" flush="false" operation="ChangeClientLoginOperation">
        <tiles:put name="title" value="Смена логина"/>
        <tiles:put name="helpText" value="Как правильно составить логин?"/>
        <tiles:put name="helpTextOnclick" value="win.open('recomendationLogin');"/>
        <tiles:put name="defaultCommandButon" value="button.saveLogin"/>
        <tiles:put name="data">
            <fieldset>
                <table>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="align-left valign-middle descRow">
                            <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.login.new"/>&nbsp;</div>
                        </td>
                        <c:if test="${not empty confirmRequest}">
                            <html:hidden name="form" property="field(newLogin)" style="display:none"/>
                        </c:if>
                        <td class="align-left" style="width: 200px;">
                            <html:text name="form" property="field(newLogin)" style="width: 200px;float: left;margin-right: 10px;" styleId="newLogin" disabled="${not empty confirmRequest}"
                                       maxlength="30"/>
                        </td>
                        <td class="align-left valign-top">
                            <tiles:insert definition="complexIndicator" flush="false">
                                <tiles:put name="id" value="loginField"/>
                                <tiles:put name="notShowIndicator" value="true"/>
                            </tiles:insert>
                        </td>
                    </tr>

                    <script type="text/javascript">
                        function enableNextButtonForLogin(state)
                        {
                            var buttonForLogin = $('#nextButtonForLogin')[0];
                            var loginText = $('#newLogin')[0];
                            var loginReplayText = $('#newLoginReplay')[0];

                            if (loginText.value != '' && loginReplayText.value != '' && loginReplayText.value != loginText.value)
                            {
                                $('#newLoginReplay').addClass("errorInput");
                                $('#replayLoginMessage').show();
                            }
                            else
                            {
                                $('#newLoginReplay').removeClass("errorInput");
                                $('#replayLoginMessage').hide();
                            }

                            if (loginText.value == '')
                                $('#loginFieldIndicator').children('.div-indicator').children('.image-indicator').removeClass("safeImage");

                            if (loginText.value != '' || loginReplayText.value != '')
                            {
                                if (buttonForLogin != null)
                                {
                                    buttonForLogin.onclick = function () {findCommandButton('button.saveLogin').click('', false);};
                                    $(buttonForLogin).find('.buttonGreen').removeClass("disabled");
                                }
                                setDefaultCommandButon(findCommandButton('button.saveLogin'));
                                $('#loginSaveText').show();
                                if (state != null && state != "green")
                                {
                                    $('#newLogin').addClass("errorInput");
                                    $('#loginFieldIndicator').children('.div-indicator').children('.image-indicator').removeClass("safeImage");
                                    $('#loginFieldIndicator').children('.div-indicator').children('.message-indicator').removeClass("marginLeft20");
                                }
                                else if (state == "green")
                                {
                                    $('#loginFieldIndicator').children('.div-indicator').children('.image-indicator').addClass("safeImage");
                                    $('#loginFieldIndicator').children('.div-indicator').children('.message-indicator').addClass("marginLeft20");
                                    $('#newLogin').removeClass("errorInput");
                                }
                            }
                            else
                            {
                                if (buttonForLogin != null)
                                {
                                    buttonForLogin.onclick = null;
                                    $(buttonForLogin).find('.buttonGreen').addClass("disabled");
                                }
                                setDefaultCommandButon(null);
                                $('#loginSaveText').hide();
                            }
                        }

                        $(document).ready(function ()
                        {
                            var indicator = new Indicator("loginField", stateComplexLoginCsa, enableNextButtonForLogin, "new");
                            indicator.init("newLogin");
                            indicator.setMinLength(${form.minLoginLength});
                        });
                    </script>
                    <tr>
                        <td class="align-left valign-middle descRow">
                            <div class="loginChangeLabel"><span class="word-wrap"><bean:message bundle="userprofileBundle" key="label.login.new.replay"/>&nbsp;</span></div>
                        </td>
                        <c:if test="${not empty confirmRequest}">
                            <html:hidden name="form" property="field(newLoginReplay)" style="display:none"/>
                        </c:if>
                        <td class="align-left">
                            <html:text name="form" property="field(newLoginReplay)" styleId="newLoginReplay" style="width: 200px;float: left;margin-right: 10px;" disabled="${not empty confirmRequest}"
                                       maxlength="30" onkeyup="enableNextButtonForLogin(null)"/>
                        </td>
                        <td class="align-left valign-top">
                            <div id="replayLoginMessage" class="indicator-complexity" style="width:210px">
                                <span class="message-indicator red-text">Логины не совпадают</span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2">
                            <div class="buttonArea">
                                <c:choose>
                                    <c:when test="${not empty confirmRequest and confirmName=='login'}">
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.backToEdit"/>
                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="enabled" value="true"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                        </tiles:insert>
                                        <tiles:insert definition="confirmButtons" flush="false">
                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/loginConfirm"/>
                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                            <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                            <tiles:put name="anotherStrategy" value="false"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.saveLogin"/>
                                            <tiles:put name="commandTextKey" value="button.save"/>
                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="enabled" value="false"/>
                                            <tiles:put name="id" value="nextButtonForLogin"/>
                                        </tiles:insert>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${empty confirmRequest and confirmName!='login'}">
                               <span class="rightButtonText" id="loginSaveText">
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
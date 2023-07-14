<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="faqDelivery" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm15')}"/>
<c:set var="faqConfirm" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm6')}"/>


<html:form action="/private/userprofile/accountSecurity">
<c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:set var="confirmName" value="${form.confirmName}"/>
<c:set var="hasCapButton" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>
<c:if test="${not empty form.confirmableObject}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<tiles:insert definition="userProfile">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройки"/>
            <tiles:put name="action" value="/private/userprofile/userSettings.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройка безопасности"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/complexValueIndicator.js"></script>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="bundle" value="userprofileBundle"/>
        <html:hidden property="field(unsavedData)" name="form"/>
        <div id="profile" onkeypress="onEnterKey(event);">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Настройки"/>
                <tiles:put name="data">
                    <tiles:insert definition="userSettings" flush="false">
                        <tiles:put name="data">
                            <c:set var="selectedTab" value="securetySettings"/>
                            <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                            <div class="clear"></div>
                            <div class="securityOptionsArea">
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
                                <div class="securityOptions">
                                    <tiles:insert definition="userProfileSecurity" flush="false" operation="GeneratePasswordOperation">
                                        <tiles:put name="title" value="Смена пароля"/>
                                        <tiles:put name="defaultCommandButon" value="button.password.new"/>
                                        <tiles:put name="text">
                                            Изменение пароля для входа в систему. Новый пароль будет создан автоматически и выслан на Ваш мобильный телефон.
                                        </tiles:put>
                                        <tiles:put name="style" value="red"/>
                                        <tiles:put name="data">
                                            <tr class="form-row">
                                                <td class="align-right valign-top">
                                                    <div class="passChangeLable"><bean:message bundle="userprofileBundle" key="label.password.current"/>:&nbsp;</div>
                                                </td>
                                                <td class="align-left" colspan="2">
                                                    <html:password name="form" property="field(password)" style="width: 200px;float: left;margin-right: 10px;"/>
                                                    <div class="changePasswordOption">
                                                        <tiles:insert definition="commandButton" flush="false">
                                                            <tiles:put name="commandKey" value="button.password.new"/>
                                                            <tiles:put name="commandTextKey" value="button.password.new"/>
                                                            <tiles:put name="commandHelpKey" value="button.password.new.help"/>
                                                            <tiles:put name="bundle" value="${bundle}"/>
                                                        </tiles:insert>
                                                    </div>
                                                    <div class="errorDiv" style="display:none;"></div>
                                                </td>
                                            </tr>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <c:if test="${phiz:impliesOperation('ChangeClientLoginOperation', 'ClientChangeLogin')}">
                                    <div class="securityOptions">
                                        <tiles:insert definition="userProfileSecurity" flush="false" operation="ChangeClientLoginOperation">
                                            <tiles:put name="title" value="Смена логина"/>
                                            <tiles:put name="defaultCommandButon" value="button.saveLogin"/>
                                            <tiles:put name="text">
                                                Изменение логина входа в систему.
                                            </tiles:put>
                                            <tiles:put name="style" value="red"/>
                                            <tiles:put name="data">
                                                <tr class="form-row">
                                                    <fieldset><table>
                                                        <tr>
                                                            <td class="align-right valign-top">
                                                                <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.login.new"/>:&nbsp;</div>
                                                            </td>
                                                            <c:if test="${not empty confirmRequest}">
                                                                <html:hidden name="form" property="field(newLogin)" style="display:none"/>
                                                            </c:if>
                                                            <td class="align-left">
                                                                <html:text name="form" property="field(newLogin)" style="width: 200px;float: left;margin-right: 10px;" styleId="newLogin" disabled="${not empty confirmRequest}" maxlength="30"/>
                                                            </td>
                                                            <td class="align-left valign-top">
                                                                <a class="blueGrayLinkDotted" onclick="javascript:win.open('recomendationLogin');">Как правильно составить логин?</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="2">
                                                                <tiles:insert definition="complexIndicator" flush="false">
                                                                    <tiles:put name="id" value="loginField"/>
                                                                </tiles:insert>
                                                                <script type="text/javascript">
                                                                    function enableNextButtonForLogin(state)
                                                                    {
                                                                        var buttonForLogin = $('#nextButtonForLogin')[0];
                                                                        if(state == "green")
                                                                        {
                                                                            if (buttonForLogin != null)
                                                                            {
                                                                                buttonForLogin.onclick = function(){findCommandButton('button.saveLogin').click('', false);};
                                                                                $(buttonForLogin).find('.buttonGreen').removeClass("disabled");
                                                                            }
                                                                            setDefaultCommandButon(findCommandButton('button.saveLogin'));
                                                                        }
                                                                        else
                                                                        {
                                                                            if (buttonForLogin != null)
                                                                            {
                                                                                buttonForLogin.onclick = null;
                                                                                $(buttonForLogin).find('.buttonGreen').addClass("disabled");
                                                                            }
                                                                            setDefaultCommandButon(null);
                                                                        }
                                                                    }

                                                                    $(document).ready(function(){
                                                                        var indicator = new Indicator("loginField", stateComplexLoginCsa, enableNextButtonForLogin);
                                                                        indicator.init("newLogin");
                                                                        indicator.setMinLength(${form.minLoginLength});
                                                                    });
                                                                </script>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="align-right valign-top">
                                                                <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.login.new.replay"/>:&nbsp;</div>
                                                            </td>
                                                            <c:if test="${not empty confirmRequest}">
                                                                <html:hidden name="form" property="field(newLoginReplay)" style="display:none"/>
                                                            </c:if>
                                                            <td  class="align-left" colspan="2">
                                                                <html:text name="form" property="field(newLoginReplay)" style="width: 200px;float: left;margin-right: 10px;"  disabled="${not empty confirmRequest}" maxlength="30"/>
                                                            </td>
                                                        </tr>
                                                    </table></fieldset>
                                                    <div class="clear"></div>
                                                    <div class="buttonsArea">
                                                        <c:choose>
                                                            <c:when test="${not empty confirmRequest and confirmName=='login'}">
                                                                <tiles:insert definition="commandButton" flush="false">
                                                                    <tiles:put name="commandKey" value="button.backToEdit"/>
                                                                    <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
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
                                                                <tiles:insert definition="clientButton" flush="false">
                                                                    <tiles:put name="commandTextKey" value="button.cancel"/>
                                                                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                                    <tiles:put name="action" value="/private/accounts.do"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                </tiles:insert>
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
                                                </tr>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                                <c:if test="${phiz:impliesOperation('ChangeClientPasswordOperation', 'ClientChangeLogin')}">
                                    <div class="securityOptions">
                                        <tiles:insert definition="userProfileSecurity" flush="false" operation="ChangeClientPasswordOperation">
                                            <tiles:put name="title" value="Смена пароля"/>
                                            <tiles:put name="text">
                                                Изменение пароля входа в систему.
                                            </tiles:put>
                                            <tiles:put name="style" value="red"/>
                                            <tiles:put name="data">
                                                <tr class="form-row">
                                                    <fieldset><table>
                                                        <tr>
                                                            <td class="align-right valign-top">
                                                                <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.password.current"/>:&nbsp;</div>
                                                            </td>
                                                            <c:if test="${not empty confirmRequest}">
                                                                <html:hidden name="form" property="field(oldPassword)" style="display:none"/>
                                                            </c:if>
                                                            <td class="align-left" colspan="2">
                                                                <html:password styleId="oldPassword" name="form" property="field(oldPassword)" style="width: 200px;float: left;margin-right: 10px;" maxlength="30"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="align-right valign-top">
                                                                <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.password.new"/>:&nbsp;</div>
                                                            </td>
                                                            <c:if test="${not empty confirmRequest}">
                                                                <html:hidden name="form" property="field(newPassword)" style="display:none"/>
                                                            </c:if>
                                                            <td  class="align-left">
                                                                <html:password styleId="newPassword" name="form" property="field(newPassword)" style="width: 200px;float: left;margin-right: 10px;" maxlength="30"/>
                                                            </td>
                                                            <td class="align-left valign-top">
                                                                <a class="blueGrayLinkDotted" onclick="javascript:win.open('recomendationPassword');"><bean:message bundle="userprofileBundle" key="label.password.help"/></a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="2">
                                                                <tiles:insert definition="complexIndicator" flush="false">
                                                                    <tiles:put name="id" value="passwordField"/>
                                                                </tiles:insert>
                                                                <script type="text/javascript">
                                                                    $(document).ready(function(){
                                                                        var indicator = new Indicator("passwordField", stateComplexPasswordCsa, enableNextButton);
                                                                        indicator.init("newPassword");
                                                                    });
                                                                    function enableNextButton(state)
                                                                    {
                                                                        var buttonsavePassword = $('#nextButton')[0];
                                                                        if(state == "green")
                                                                        {
                                                                            if (buttonsavePassword)
                                                                            {
                                                                                buttonsavePassword.onclick = function(){findCommandButton('button.savePassword').click('', false);};
                                                                                $(buttonsavePassword).find('.buttonGreen').removeClass("disabled");
                                                                            }
                                                                            setDefaultCommandButon(findCommandButton('button.savePassword'));
                                                                        }
                                                                        else
                                                                        {
                                                                            if (buttonsavePassword)
                                                                            {
                                                                                buttonsavePassword.onclick = null;
                                                                                $(buttonsavePassword).find('.buttonGreen').addClass("disabled");
                                                                            }
                                                                            setDefaultCommandButon(null);
                                                                        }
                                                                    }
                                                                </script>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="align-right valign-top">
                                                                <div class="loginChangeLabel"><bean:message bundle="userprofileBundle" key="label.password.new.replay"/>:&nbsp;</div>
                                                            </td>
                                                            <c:if test="${not empty confirmRequest}">
                                                                <html:hidden name="form" property="field(newPasswordReplay)" style="display:none"/>
                                                            </c:if>
                                                            <td  class="align-left" colspan="2">
                                                                <html:password styleId="newPasswordReplay" name="form" property="field(newPasswordReplay)" style="width: 200px;float: left;margin-right: 10px;" maxlength="30"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <td class="align-left valign-top" colspan="2">
                                                                <html:checkbox styleId="noMaskPassword" value="false" name="form" property="field(noMaskPassword)"/>
                                                                <label for="noMaskPassword">отобразить пароли</label>
                                                                <script type="text/javascript">
                                                                    $(document).ready(function(){
                                                                       $("#noMaskPassword").click(function(){
                                                                           showHidePassword('oldPassword', this.checked);
                                                                           showHidePassword('newPasswordReplay', this.checked);
                                                                           showHidePassword('newPassword', this.checked);
                                                                           changeEnableStatePasswordFields();
                                                                        });
                                                                        var flag = document.getElementById('noMaskPassword').checked;
                                                                        showHidePassword('oldPassword', flag);
                                                                        showHidePassword('newPasswordReplay', flag);
                                                                        showHidePassword('newPassword', flag);
                                                                        changeEnableStatePasswordFields();
                                                                    });
                                                                    function changeEnableStatePasswordFields()
                                                                    {
                                                                       document.getElementById('oldPassword').disabled = ${not empty confirmRequest};
                                                                       document.getElementById('newPasswordReplay').disabled = ${not empty confirmRequest};
                                                                       document.getElementById('newPassword').disabled = ${not empty confirmRequest};
                                                                    }
                                                                </script>
                                                            </td>
                                                        </tr>
                                                    </table></fieldset>
                                                    <div class="clear"></div>
                                                    <div class="buttonsArea">
                                                        <c:choose>
                                                            <c:when test="${not empty confirmRequest  and confirmName=='password'}">
                                                                <tiles:insert definition="commandButton" flush="false">
                                                                    <tiles:put name="commandKey" value="button.backToEdit"/>
                                                                    <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                                </tiles:insert>
                                                                <tiles:insert definition="confirmButtons" flush="false">
                                                                    <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/passwordConfirm"/>
                                                                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                                    <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                                    <tiles:put name="anotherStrategy" value="false"/>
                                                                </tiles:insert>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <tiles:insert definition="clientButton" flush="false">
                                                                    <tiles:put name="commandTextKey" value="button.cancel"/>
                                                                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                                    <tiles:put name="action" value="/private/accounts.do"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                </tiles:insert>
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
                                                </tr>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:if>

                                <c:if test="${phiz:impliesOperation('GenerateIPasPasswordOperation', 'ClientChangeLogin')}">
                                    <div class="securityOptions">
                                        <tiles:insert definition="userProfileSecurity" flush="false" operation="GenerateIPasPasswordOperation">
                                            <tiles:put name="defaultCommandButon" value="button.saveIPasPassword"/>
                                            <tiles:put name="title" value="Смена пароля"/>
                                            <tiles:put name="text">
                                                Изменение пароля для входа в систему. Новый пароль будет создан автоматически и выслан на Ваш мобильный телефон.
                                            </tiles:put>
                                            <tiles:put name="style" value="red"/>
                                            <tiles:put name="data">
                                                <tr class="form-row">
                                                    <td class="align-right valign-top">
                                                        <div class="passChangeLable"><bean:message bundle="userprofileBundle" key="label.password.current"/>:&nbsp;</div>
                                                    </td>
                                                    <c:if test="${not empty confirmRequest}">
                                                        <html:hidden name="form" property="field(oldPassword)" style="display:none"/>
                                                    </c:if>
                                                    <td class="align-left" colspan="2">
                                                        <html:password name="form" property="field(oldPassword)" style="width: 200px;float: left;margin-right: 10px;"  disabled="${not empty confirmRequest}"/>
                                                    </td>
                                                </tr>
                                                <br/>
                                                <div class="clear"></div>
                                                <div class="buttonsArea">
                                                    <c:choose>
                                                        <c:when test="${not empty confirmRequest  and confirmName=='ipasPassword'}">
                                                            <tiles:insert definition="commandButton" flush="false">
                                                                <tiles:put name="commandKey" value="button.backToEdit"/>
                                                                <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                                <tiles:put name="viewType" value="buttonGrey"/>
                                                                <tiles:put name="bundle" value="commonBundle"/>
                                                            </tiles:insert>
                                                            <tiles:insert definition="confirmButtons" flush="false">
                                                                <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/ipasPasswordConfirm"/>
                                                                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                                <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                                <tiles:put name="anotherStrategy" value="false"/>
                                                            </tiles:insert>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tiles:insert definition="clientButton" flush="false">
                                                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                                                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                                <tiles:put name="bundle" value="commonBundle"/>
                                                                <tiles:put name="action" value="/private/accounts.do"/>
                                                                <tiles:put name="viewType" value="buttonGrey"/>
                                                            </tiles:insert>
                                                            <tiles:insert definition="commandButton" flush="false">
                                                                <tiles:put name="commandKey" value="button.saveIPasPassword"/>
                                                                <tiles:put name="commandTextKey" value="button.next"/>
                                                                <tiles:put name="commandHelpKey" value="button.next.help"/>
                                                                <tiles:put name="bundle" value="commonBundle"/>
                                                            </tiles:insert>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="errorDiv" style="display:none;"></div>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:if>

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
                                <div class="securityOptions">
                                    <tiles:insert definition="userProfileSecurity" flush="false">
                                        <tiles:put name="defaultCommandButon" value="button.save"/>
                                        <tiles:put name="title" value="Настройка подтверждений в системе"/>
                                        <tiles:put name="text">
                                            Настройка подтверждения входа в систему и способа подтверждения операций.
                                        </tiles:put>
                                        <tiles:put name="style" value="turquoise"/>
                                        <tiles:put name="data">
                                            <tr>
                                                <td class="align-right">
                                                    <bean:message bundle="userprofileBundle" key="title.miscsettings"/>:
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td colspan="3" class="align-left">
                                                <c:if test="${not empty confirmRequest}">
                                                    <html:hidden name="form" property="field(oneTimePassword)" style="display:none"/>
                                                </c:if>
                                                </td>
                                                <div>
                                                    <html:checkbox styleId="oneTimePassword" name="form" property="field(oneTimePassword)" value="true" disabled="${not empty confirmRequest}"/>
                                                    <label for="oneTimePassword"><bean:message bundle="userprofileBundle" key="label.password.onetime"/></label>
                                                </div>
                                            </tr>
                                            <div>&nbsp;</div>
                                            <tr>
                                                <td class="align-right">
                                                    <bean:message bundle="userprofileBundle" key="label.confirm.type"/>:
                                                </td>
                                                <td style="vertical-align: bottom;"><a class="blueGrayLinkDotted" onclick="javascript:openFAQ('${faqConfirm}');"><bean:message bundle="userprofileBundle" key="label.confirm.message"/></a></td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td colspan="3" class="align-left">
                                                     <c:if test="${not empty confirmRequest}">
                                                        <html:hidden name="form" property="field(confirmType)" style="display:none"/>
                                                    </c:if>

                                                    <div>
                                                        <html:radio styleId="SMSConfirm" name="form" property="field(confirmType)" value="sms" disabled="${not empty confirmRequest}"/>
                                                        <label for="SMSConfirm"><bean:message bundle="userprofileBundle" key="label.confirm.sms"/></label>
                                                    </div>
                                                    <div>
                                                        <html:radio styleId="checkConfirm" name="form" property="field(confirmType)" value="card" disabled="${not empty confirmRequest}"/>
                                                        <label for="checkConfirm"><bean:message bundle="userprofileBundle" key="label.confirm.card"/></label>
                                                    </div>
                                                    <c:if test="${phiz:impliesService('ClientProfilePush')}">
                                                        <div>
                                                            <html:radio styleId="pushConfirm" name="form" property="field(confirmType)" value="push" disabled="${not empty confirmRequest}"/>
                                                            <label for="pushConfirm"><bean:message bundle="userprofileBundle" key="label.confirm.push"/></label>
                                                        </div>
                                                    </c:if>
                                                    <%--<div>--%>
                                                        <%--<html:radio name="form" property="field(confirmType)" value="cap" disabled="${not empty confirmRequest}"/>--%>
                                                        <%--<bean:message bundle="userprofileBundle" key="label.confirm.cap"/>--%>
                                                    <%--</div>--%>
                                                </td>
                                            </tr>
                                            <div>&nbsp;</div>
                                            <div class="buttonsArea">
                                                <c:choose>
                                                    <c:when test="${not empty confirmRequest  and confirmName=='settings'}">
                                                        <tiles:insert definition="commandButton" flush="false">
                                                            <tiles:put name="commandKey" value="button.backToEdit"/>
                                                            <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="confirmButtons" flush="false">
                                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity"/>
                                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                            <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                            <tiles:put name="anotherStrategy" value="false"/>
                                                        </tiles:insert>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                            <tiles:put name="action" value="/private/accounts.do"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="commandButton" flush="false">
                                                            <tiles:put name="commandKey" value="button.save"/>
                                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                                            <tiles:put name="validationFunction" value="checkData()"/>
                                                            <tiles:put name="bundle" value="commonBundle"/>
                                                        </tiles:insert>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <c:if test="${phiz:impliesService('IncognitoSetting')}">
                                    <div class="securityOptions">
                                        <tiles:insert definition="userProfileSecurity" flush="false">
                                            <tiles:put name="defaultCommandButon" value="button.save"/>
                                            <tiles:put name="title" value="Приватность"/>
                                            <tiles:put name="text">
                                                Здесь можно настроить видимость Ваших телефонов с признаком «Сбербанк России» в списке контактов других клиентов Сбербанка.
                                            </tiles:put>
                                            <tiles:put name="style" value="turquoise"/>
                                            <tiles:put name="data">
                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td colspan="3" class="align-left">
                                                    <c:if test="${not empty confirmRequest}">
                                                        <html:hidden name="form" property="field(incognitoSetting)" style="display:none"/>
                                                    </c:if>
                                                    </td>
                                                    <div>
                                                        <html:checkbox styleId="incognitoSetting" name="form" property="field(incognitoSetting)" disabled="${not empty confirmRequest}"/>
                                                        <label for="incognitoSetting"><bean:message bundle="userprofileBundle" key="label.incognito.setting"/></label>
                                                    </div>
                                                </tr>

                                                <div>&nbsp;</div>
                                                    <div class="buttonsArea">
                                                        <c:choose>
                                                            <c:when test="${not empty confirmRequest  and confirmName=='incognitoSetting'}">
                                                                <tiles:insert definition="commandButton" flush="false">
                                                                    <tiles:put name="commandKey" value="button.backToEdit"/>
                                                                    <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                                </tiles:insert>
                                                                <tiles:insert definition="confirmButtons" flush="false">
                                                                    <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/incognitoConfirm"/>
                                                                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                                    <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                                                    <tiles:put name="anotherStrategy" value="false"/>
                                                                </tiles:insert>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <tiles:insert definition="clientButton" flush="false">
                                                                    <tiles:put name="commandTextKey" value="button.cancel"/>
                                                                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                                    <tiles:put name="action" value="/private/accounts.do"/>
                                                                    <tiles:put name="viewType" value="buttonGrey"/>
                                                                </tiles:insert>
                                                                <tiles:insert definition="commandButton" flush="false">
                                                                    <tiles:put name="commandKey" value="button.saveIncognitoSetting"/>
                                                                    <tiles:put name="commandTextKey" value="button.next"/>
                                                                    <tiles:put name="commandHelpKey" value="button.next.help"/>
                                                                    <tiles:put name="bundle" value="commonBundle"/>
                                                                </tiles:insert>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>

                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                                <div class="securityOptions">
                                    <tiles:insert definition="userProfileSecurity" flush="false">
                                        <tiles:put name="title" value="Настройка видимости продуктов"/>
                                        <tiles:put name="text">
                                            Изменение списка продуктов, которые будут отображаться на Вашей личной странице и в устройствах самообслуживания. В целях безопасности для доступа к данному пункту меню необходимо ввести SMS-пароль.
                                        </tiles:put>
                                        <tiles:put name="style" value="green"/>
                                        <tiles:put name="URL" value="${phiz:calculateActionURL(pageContext, '/private/userprofile/accountsSystemView.do')}"/>
                                    </tiles:insert>
                                </div>

                                <div class="securityOptions">
                                    <tiles:insert definition="userProfileSecurity" flush="false">
                                        <tiles:put name="title" value="Настройка видимости шаблонов"/>
                                        <tiles:put name="text">
                                            Изменение списка шаблонов, которые будут доступны для операций в разных каналах обслуживания.
                                        </tiles:put>
                                        <tiles:put name="style" value="green"/>
                                        <tiles:put name="URL" value="${phiz:calculateActionURL(pageContext, '/private/userprofile/templatesShowSettings.do')}"/>
                                    </tiles:insert>
                                </div>

                                <c:if test="${phiz:impliesOperation('SetupOTPRestrictionOperation', 'OTPRestriction')}">
                                    <div class="securityOptions profileSettings">
                                        <tiles:insert definition="userProfileSecurity" flush="false" operation="SetupOTPRestrictionOperation">
                                            <tiles:put name="title" value="Установка ограничений на одноразовые пароли с чека"/>
                                            <tiles:put name="text">
                                                Ограничения, которые можно установить на печать одноразовых паролей через банкоматы и терминалы.
                                            </tiles:put>
                                            <tiles:put name="style" value="otp"/>
                                            <tiles:put name="URL" value="${phiz:calculateActionURL(pageContext, '/private/userprofile/otpRestriction.do')}"/>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                            </div>
                            <div class="clear"></div>

                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </div>
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
        if (!isDataChanged())
        {
            addMessage("Вы не внесли никаких изменений в персональной информации.");
            return false;
        }
        return true;
    }

    $(document).ready(function(){
        <c:if test="${phiz:isScriptsRSAActive()}">
            <%-- формирование основных данных для ФМ --%>
            new RSAObject().toHiddenParameters();
        </c:if>
    });
</script>

<div></div></html:form>

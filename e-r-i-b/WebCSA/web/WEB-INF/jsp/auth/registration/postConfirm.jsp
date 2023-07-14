<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="registrationTitle"><bean:message bundle="commonBundle" key="label.registration"/></c:set>
<c:set var="registrationDescr"><bean:message bundle="commonBundle" key= "label.registration.description"/></c:set>
<tiles:insert definition="stage" flush="false">
    <tiles:put name="title" value="${registrationTitle}" type="string"/>
    <tiles:put name="description" value="${registrationDescr}" type="string"/>
    <tiles:put name="data" type="string">
        <html:form action="/popup/registration" styleClass="safetyRegulations" onsubmit="return false;">
            <c:set var="form" value="${csa:currentForm(pageContext)}"/>
            <c:set var="operationInfo" value="${form.operationInfo}"/>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="Номер карты:"/>
                <tiles:put name="data">
                    <span class="bold">${csa:getCutCardNumber(operationInfo.cardNumber)}</span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="Логин:"/>
                <tiles:put name="data">
                    <html:text property="field(login)" name="form" size="30" styleId="login" maxlength="30" tabindex="1"/>
                    <a href="#" onclick="win.close('passwordHelp'); win.open('loginHelp'); return false;" tabindex="2">Как правильно составить логин?</a>
                    <tiles:insert definition="complexIndicator" flush="false">
                        <tiles:put name="id" value="loginField"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <%-- всплывающая подсказка --%>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="loginHelp"/>
                <tiles:put name="data">
                    <div class="help-message">
                        <bean:message key="message.valid.login" bundle="commonBundle"/>
                        <ul>
                            <bean:message key="message.valid.login.list" bundle="commonBundle"/>    
                        </ul>
                    </div>
                    <div class="buttonsArea">
                        <div class="clientButton" onclick="win.close('loginHelp');">
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
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="Пароль:"/>
                <tiles:put name="data">
                    <input type="password" name="field(password)" id="password" size="30" maxlength="30" tabindex="1">
                    <a href="#" onclick="win.close('loginHelp'); win.open('passwordHelp'); return false;" tabindex="4">Как правильно составить безопасный пароль?</a>
                    <tiles:insert definition="complexIndicator" flush="false">
                        <tiles:put name="id" value="passwordField"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <%-- всплывающая подсказка --%>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="passwordHelp"/>
                <tiles:put name="data">
                    <div class="help-message">
                        <bean:message key="message.valid.password" bundle="commonBundle"/>
                        <ul>
                            <bean:message key="message.valid.password.requirementlist" bundle="commonBundle"/>
                        </ul>
                    </div>
                    <div class="buttonsArea">
                        <div class="clientButton" onclick="win.close('passwordHelp');">
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
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="Повторите пароль:"/>
                <tiles:put name="data">
                    <input type="password" name="field(confirmPassword)" id="confirmPassword" size="30" maxlength="30" tabindex="1">
                </tiles:put>
            </tiles:insert>

             <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="&nbsp;"/>
                <tiles:put name="data">
                    <input id="changePasswordShow" type="checkbox"/> отобразить пароли
                </tiles:put>
            </tiles:insert>

            <div class="buttonsArea">
                <div class="clientButton" onclick="stageForm.close();" onkeypress="clickIfEnterKeyPress(this,event)" tabindex="3">
                    <div class="buttonGrey">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Отменить</span>
                        </div>
                        <div class="right-corner"></div>
                        <div class="clear"></div>
                    </div>
                </div>

                <div id="nextButton" class="commandButton" onkeypress="clickIfEnterKeyPress(this,event)" tabindex="2">
                    <div class="buttonGreen disabled">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span>Сохранить</span>
                        </div>
                        <div class="right-corner"></div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </html:form>
        <script type="text/javascript">
            if(window.win)
                win.init(ensureElement("stageForm"));

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

            function enableNextButton()
            {
                if(passValid && loginValid)
                {
                    document.getElementById('nextButton').onclick = function(){stageForm.send('button.next');};
                    document.getElementById('nextButton').children[0].className = "buttonGreen";
                }
                else
                {
                    document.getElementById('nextButton').onclick = null;
                    document.getElementById('nextButton').children[0].className = "buttonGreen disabled";
                }
            }

            if(window.Indicator)
            {
                var passwordIndicator = new Indicator("passwordField", stateComplexPasswordCsa, enableNextButtonForPass);
                passwordIndicator.init("password", "stageForm");

                var loginIndicator = new Indicator("loginField", stateComplexLoginCsa, enableNextButtonForLogin);
                loginIndicator.init("login", "stageForm");
            }

            if(window.$)
            {
                $("#changePasswordShow").change(function(){
                    showHidePassword('password', this.checked, 'stageForm');
                    showHidePassword('confirmPassword', this.checked, 'stageForm');
                });
            }
        </script>
        <%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
    </tiles:put>
</tiles:insert>


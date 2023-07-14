<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert page="/WEB-INF/jsp/common/authBlock.jsp" flush="false">
    <c:set var="form" value="${AuthenticationFormBase}"/>
    <tiles:put name="id" value="registration-form"/>
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

        $(document).ready(function(){
            <%-- для номера карты ввод возможен только цифр --%>
            $(".enterBlock#registration-form").find("#cardNumber").each(function(){
                restrictInputKeys(this,
                /* функий-ограничитель */
                function(e){
                    return /^\d+$/.test(String.fromCharCode(e.which || e.keyCode));
                },
                function(){
                    $(".enterBlock#registration-form").find("#cardNumber").each(function(){
                        this.value = this.value.replace(/\D/gi, '');
                    });
                });
            });
        });
    </script>

    <tiles:put name="data" type="string">
        <form action="${csa:calculateActionURL(pageContext, '/popup/registration.do')}" onkeypress="authForm.onEnterKeyPress(event);">
            <div class="login">
                <h2>
                    <tiles:insert definition="authIcon" flush="false"/>
                    <span class="auth-title"><bean:message bundle="commonBundle" key="label.registration"/></span>
                </h2>
                <%-- блок с описанием к форме регистрации --%>
                <div class="description-block">
                    <bean:message key="message.registration.description.block" bundle="commonBundle"/>
                </div>
                <input id="cardNumber" type="text" name="field(cardNumber)" class="customPlaceholder" maxlength="18" size="41" align="left" title="Номер карты" tabindex="11"/>

                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="card-number-pupupHelp"/>
                    <tiles:put name="hintClass" value=""/>
                    <tiles:put name="data"><img src="${skinUrl}/skins/commonSkin/images/hint.png" alt="" class="hint"/></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">
                        <bean:message key="message.registration.card.number.popup.help" bundle="commonBundle"/>
                    </tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                </tiles:insert>

                <div class="captcha-block" style="display:none">
                    <%-- блок с капчей --%>
                    <div class="clear"></div>
                    <div id="captcha" style="margin: 0 auto; width: 170px; height: 55px;"></div>
                    <div class="update-captcha">
                        <a href="#" onclick="authForm.updateCaptcha(); return false;">обновить код</a>
                    </div>     
                    <%-- поля для капчи --%>
                    <input id="ccode" class="customPlaceholder codeCaptcha" type="text" name="field(ccode)" maxlength="10" title="Введите код с картинки" onkeyup="convertCode(this);" tabindex="12"/>
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
                            <span>Подтвердить по SMS</span>
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

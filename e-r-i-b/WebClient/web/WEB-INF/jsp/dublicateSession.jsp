<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="appTitle"><bean:message bundle="commonBundle" key="application.title"/></c:set>
<tiles:insert definition="login">
    <tiles:put name="pageTitle" value="${appTitle}"/>
    <tiles:put name="data" type="string">
        <br/>
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="<div class='castleWhite'>Обратите внимание</div>"/>
            <tiles:put name="data">
                <bean:message bundle="commonBundle" key="text.duplicateSession.notification"/>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.repeatLogin"/>
                        <tiles:put name="commandHelpKey" value="button.repeatLogin.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="action" value="/login.do"/>
                    </tiles:insert>
                </div>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange"/>            
            <tiles:put name="data">
                        <div class="castleOrange"></div><p class="loginWarningMessage">&nbsp;Важная информация для пользователей «Сбербанк Онлайн»!</p>
                        <br/>
                        <p>
                            Система никогда <span class="bold">не запрашивает номер мобильного телефона и другую дополнительную информацию при входе</span> в личный кабинет, кроме идентификатора, постоянного и одноразового паролей.
                        </p>
                        <br/>
                        <p>
                            Сбербанк никогда <span class="bold">не запрашивает пароли для отмены операций или шаблонов</span> в «Сбербанк Онлайн». Если Вам предлагается ввести пароль для отмены или подтверждения операций, которые Вы не совершали, то прекратите сеанс использования услуги и срочно обратитесь в Банк.
                        </p>
                        <br/>
                        <p>
                            Обязательно <span class="bold">сверьте реквизиты Вашей операции с реквизитами в полученном SMS-сообщении</span>. Помните, что вводя одноразовый SMS-пароль, Вы подтверждаете операцию с реквизитами, указанными в SMS.
                        </p>
                        <br/>
                        <p>
                            Подробнее о мерах безопасности при работе в «Сбербанк Онлайн» читайте <a href="http://sberbank.ru/ru/person/dist_services/warning/" target="_blank">здесь</a>.
                        </p>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

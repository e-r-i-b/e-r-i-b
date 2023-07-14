<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/userprofile/userNotification" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="productsSmsNotificationConfirmStrategy" value="${form.productsSmsNotificationConfirmStrategy}"/>
    <c:set var="pushSupported" value="${phiz:impliesService('ClientProfilePush')}"/>

    <c:set var="confirmableObjectExist" value="${not empty form.confirmableObject}"/>
    <c:if test="${confirmableObjectExist && form.confirmedType == 'allNotification'}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
        <c:set var="notificationConfirmStrategy" value="${form.notificationConfirmStrategy}"/>
    </c:if>
    <c:if test="${not empty form.productsSmsNotificationConfirmableObject}">
        <c:set var="productsSmsNotificationConfirmRequest" value="${phiz:currentConfirmRequest(form.productsSmsNotificationConfirmableObject)}"/>
    </c:if>
    <c:set var="messages">
        <div class="profileSubTitle">
            <c:if test="${form.fields.loginNotification == 'sms' and not empty form.fields.phone}">
               SMS-сообщения о входе в Сбербанк Онлайн направляются на номер:<b class="textNobr">${fn:replace(form.fields.phone, ',', '</b>,<b class="textNobr">')}</b><br/>
            </c:if>
            <c:if test="${form.fields.mailNotification == 'sms' and not empty form.fields.phoneForMail}">
                При получении писем от службы помощи SMS-сообщения отправляются на номер: <b class="textNobr">${fn:replace(form.fields.phoneForMail,',', '</b>,<b class="textNobr">')}</b> <br/>
            </c:if>
            <c:if test="${form.fields.deliveryNotificationType == 'sms' and not empty form.fields.phoneForMail}">
                Статусы исполнения операций направляются на номер: <b class="textNobr">${fn:replace(form.fields.phoneForMail, ',', '</b>,<b class="textNobr">')}</b> <br/>
            </c:if>
            <c:if test="${not empty form.fields.email && (form.fields.loginNotification == 'email' or form.fields.mailNotification == 'email' or form.fields.operationNotification == 'email' or form.fields.newsNotification == 'email')}">
                <span class="float">E-mail сообщения отправляются на адрес <b>${form.fields.email}</b> в формате <b>${form.fields.emailFormat}</b><b></b> &nbsp;</span>
                <div class="float">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.change"/>
                        <tiles:put name="commandHelpKey" value="button.change"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="onclick" value="win.open('editEmailDiv');"/>
                        <tiles:put name="viewType" value="lightGrayProfileButton"/>
                    </tiles:insert>
                </div>
                <c:set var="editEmail" value="${phiz:calculateActionURL(pageContext,'/private/async/userprofile/editEmail')}"/>
                <tiles:insert definition="window" flush="false">
                    <tiles:put name="id" value="editEmailDiv"/>
                    <tiles:put name="loadAjaxUrl" value="${editEmail}"/>
                    <tiles:put name="styleClass" value="editEmailDiv"/>
                    <tiles:put name="closeCallback" value="closeEmailWin"/>
                </tiles:insert>
                <div class="clear"></div>
            </c:if>
            <c:if test="${pushSupported}">
                <p>Push-уведомления отправляются на подключенные <html:link action="/private/mobileApplications/view.do">мобильные устройства</html:link>.</p>
            </c:if>
                <p><bean:message bundle='userprofileBundle' key='message.productNotification.useInSMS'/> <a onclick='confirmRedirect()' href = '#'><bean:message bundle='userprofileBundle' key='message.productNotification.viewSettingsPage'/></a></p>
        </div>
    </c:set>
    <tiles:insert definition="newUserProfile">
        <tiles:put name="data" type="string">
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="title">Оповещения</tiles:put>
                <tiles:put name="activeItem">notification</tiles:put>
                <tiles:put name="data">
                    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                    <c:set var="globalImagePath" value="${globalUrl}/images"/>

                    <c:if test="${phiz:isScriptsRSAActive()}">
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

                        <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
                        <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>
                    </c:if>

                    <div class="notifications">
                        ${messages}

                        <h3>Оповещать меня о следующих событиях</h3>
                        <div class="simpleTable">
                            <table>
                                <tr class="tblInfHeader">
                                    <th class="titleTable title first">&nbsp;</th>
                                    <th class="titleTable smsTitle first">SMS</th>
                                    <th class="titleTable emailTitle first">E-MAIL</th>
                                    <c:if test="${pushSupported}">
                                        <th class="titleTable pushTitle first">PUSH
                                            <span class="hint">
                                                <span class="textHint">
                                                    Для получения Push-уведомлений включите данную услугу в приложении «Сбербанк Онлайн» на вашем мобильном устройстве.
                                                    <span class="triangle-with-shadow"></span>
                                                </span>
                                            </span>
                                        </th>
                                    </c:if>

                                    <th class="titleTable first">НЕ ДОСТАВЛЯТЬ</th>
                                </tr>
                                <tr>
                                    <td class="title">
                                        Вход в «Сбербанк Онлайн»
                                    </td>
                                    <td colspan="${pushSupported ? '4' : '3'}">
                                        <html:hidden name="form" property="field(email)" style="display:none"/>
                                        <html:hidden name="form" property="field(phone)" style="display:none"/>
                                        <html:hidden name="form" property="field(phoneForMail)" style="display:none"/>
                                        <html:hidden name="form" property="field(emailFormat)" style="display:none"/>
                                        <html:hidden name="form" property="field(push)" style="display:none"/>
                                        <html:hidden name="form" property="field(mobileDevices)" style="display:none"/>
                                        <c:if test="${not empty confirmRequest}">
                                            <html:hidden name="form" property="field(loginNotification)" style="display:none"/>
                                            <html:hidden name="form" property="field(mailNotification)" style="display:none"/>
                                            <html:hidden name="form" property="field(operationNotification)" style="display:none"/>
                                            <html:hidden name="form" property="field(newsNotification)" style="display:none"/>
                                        </c:if>
                                        <div class="circleBlock relative">
                                            <span class="delimiter"></span>
                                            <span class="circle ${form.fields.loginNotification == 'sms' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio name="form" property="field(loginNotification)" value="sms" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>SMS</span>
                                            </span>
                                        </div>
                                        <div class="relative ${pushSupported ? 'circleBlock' : 'circleBlock2 last'}">
                                            <c:if test="${pushSupported}">
                                                <span class="delimiter"></span>
                                            </c:if>
                                            <span class="circle ${form.fields.loginNotification == 'email' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio name="form" property="field(loginNotification)" value="email" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>E-mail</span>
                                            </span>
                                        </div>
                                        <c:if test="${pushSupported}">
                                            <div class="circleBlock relative last">
                                                <span class="circle ${form.fields.loginNotification == 'push' ? 'activeGreen defaultSelect' : ''}">
                                                    <html:radio name="form" property="field(loginNotification)" value="push" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                    <span class='hintNotify'>PUSH</span>
                                                </span>
                                            </div>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="title">
                                        Пришло письмо от службы помощи
                                    </td>
                                    <td colspan="${pushSupported ? '4' : '3'}">
                                        <div class="circleBlock relative">
                                            <span class="delimiter"></span>
                                            <span class="circle ${form.fields.mailNotification == 'sms' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio  name="form" property="field(mailNotification)" value="sms" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>SMS</span>
                                            </span>
                                        </div>
                                        <div class="relative ${pushSupported ? 'circleBlock' : 'circleBlock2'}">
                                            <span class="${pushSupported ? 'delimiter' : 'delimiter2'}"></span>
                                            <span class="circle ${form.fields.mailNotification == 'email' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio name="form" property="field(mailNotification)" value="email" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>E-mail</span>
                                            </span>
                                        </div>
                                        <c:if test="${pushSupported}">
                                            <div class="circleBlock2 relative">
                                                <span class="delimiter delimiter2"></span>
                                                <span class="circle ${form.fields.mailNotification == 'push' ? 'activeGreen defaultSelect' : ''}">
                                                    <html:radio name="form" property="field(mailNotification)" value="push" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                    <span class='hintNotify'>PUSH</span>
                                                </span>
                                            </div>
                                        </c:if>
                                        <div class="circleBlock relative last">
                                            <span class="circle ${form.fields.mailNotification == 'none' ? 'activeRed defaultSelect' : ''}">
                                                <html:radio name="form" styleClass="circleInput notDeliver" property="field(mailNotification)" value="none" disabled="${not empty confirmRequest}"/>
                                                <span class='hintNotify hintNotify2'>Не доставлять</span>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="title">
                                        Статус исполнения операций
                                    </td>
                                    <td colspan="${pushSupported ? '4' : '3'}">
                                        <div class="circleBlock relative">
                                            <span class="delimiter"></span>
                                            <span class="circle ${form.fields.operationNotification == 'sms' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio name="form" property="field(operationNotification)" value="sms" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>SMS</span>
                                            </span>
                                        </div>
                                        <div class="relative ${pushSupported ? 'circleBlock' : 'circleBlock2'}">
                                            <span class="${pushSupported ? 'delimiter' : 'delimiter2'}"></span>
                                            <span class="circle ${form.fields.operationNotification == 'email' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio name="form" property="field(operationNotification)" value="email" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>E-mail</span>
                                            </span>
                                        </div>
                                        <c:if test="${pushSupported}">
                                            <div class="circleBlock2 relative">
                                                <span class="delimiter delimiter2"></span>
                                                <span class="circle ${form.fields.operationNotification == 'push' ? 'activeGreen defaultSelect' : ''}">
                                                    <html:radio name="form" property="field(operationNotification)" value="push" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                    <span class='hintNotify'>PUSH</span>
                                                </span>
                                            </div>
                                        </c:if>
                                        <div class="circleBlock relative last">
                                            <span class="circle ${form.fields.operationNotification == 'none' ? 'activeRed defaultSelect' : ''}">
                                                <html:radio name="form" styleClass="circleInput notDeliver" property="field(operationNotification)" value="none" disabled="${not empty confirmRequest}"/>
                                                <span class='hintNotify hintNotify2'>Не доставлять</span>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="title bottomBorder">
                                        Обновление новостей банка
                                    </td>
                                    <td colspan="${pushSupported ? '4' : '3'}" class="bottomBorder">
                                        <div class="relative ${pushSupported ? 'circleBlock3' : 'circleBlock4'}">
                                            <span class="${pushSupported ? 'delimiter delimiter3' : 'delimiter2'}"></span>
                                            <span class="circle ${form.fields.newsNotification == 'email' ? 'activeGreen defaultSelect' : ''}">
                                                <html:radio styleId="bank" name="form" property="field(newsNotification)" value="email" disabled="${not empty confirmRequest}" styleClass="circleInput"/>
                                                <span class='hintNotify'>E-mail</span>
                                            </span>
                                        </div>
                                        <div class="circleBlock relative last">
                                            <span class="circle ${form.fields.newsNotification == 'none' ? 'activeRed defaultSelect' : ''}">
                                                <html:radio styleClass="circleInput notDeliver" name="form" property="field(newsNotification)" value="none" disabled="${not empty confirmRequest}"/>
                                                <span class='hintNotify hintNotify2'>Не доставлять</span>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="buttonsArea">
                            <c:choose>
                                <c:when test="${not empty confirmRequest}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.backToEdit"/>
                                        <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                        <tiles:put name="bundle" value="commonBundle"/>
                                        <tiles:put name="action" value="/private/userprofile/userNotification.do"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                    </tiles:insert>
                                    <tiles:insert definition="confirmButtons" flush="false">
                                        <tiles:put name="ajaxUrl" value="/private/async/userprofile/userNotification"/>
                                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                        <tiles:put name="confirmStrategy" beanName="notificationConfirmStrategy"/>
                                        <tiles:put name="anotherStrategy" value="false"/>
                                        <tiles:put name="preConfirmCommandKey" value="button.preConfirmAllNotification"/>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="id" value="submitNotifications"/>
                                        <tiles:put name="commandKey" value="button.saveAllNotification"/>
                                        <tiles:put name="commandTextKey" value="button.save"/>
                                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                                        <tiles:put name="bundle" value="commonBundle"/>
                                    </tiles:insert>
                                    <div class='hintSms'>
                                        Для сохранения изменений необходимо подтверждение <b>SMS-паролем</b>.
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="redirectRefused"/>
                <tiles:put name="data">
                    <h2>Внимание!</h2>
                    Изменения настроек не сохранены. Перейти на другую страницу?».
                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.confirm"/>
                            <tiles:put name="commandHelpKey" value="button.confirm"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="onclick" value="redirect()"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.closeWindow"/>
                            <tiles:put name="commandHelpKey" value="button.closeWindow"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="onclick" value="win.close('redirectRefused');"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
<script type="text/javascript">
    $(document).ready(function(){
        if (document.getElementById('submitNotifications') != null)
        {
            document.getElementById('submitNotifications').children[0].className = document.getElementById('submitNotifications').children[0].className + " disabled";
            document.getElementById('submitNotifications').children[0].onclick = null;
        }

        $(".hint").hover(
            function(){
                $(this).find(".textHint").css("display", "block");
            },
            function(){
                $(this).find(".textHint").css("display", "none");
            }
        );

        $(".circle input").hover(
            function(){
                $(this).parent().not('.activeGreen').not('.activeRed').addClass("activeSelect");
            },
            function(){
                $(this).parent().not('.activeGreen').not('.activeRed').removeClass("activeSelect");
            }
        );

        $(".circle input").click(
            function(){

                if ($('.defaultSelect').children(':checked').length == 4) {
                    document.getElementById('submitNotifications').children[0].className = document.getElementById('submitNotifications').children[0].className + " disabled";
                    document.getElementById('submitNotifications').children[0].onclick = null;
                    $('.hintSms').hide();
                }
                else {
                    document.getElementById('submitNotifications').children[0].className = "buttonGreen";
                    document.getElementById('submitNotifications').children[0].onclick = function(){findCommandButton('button.saveAllNotification').click('', false);};
                    $('.hintSms').show();
                }

                if (!$(this).hasClass('notDeliver')) {
                    $(this).parent().parent().parent().find('.activeGreen, .activeRed').removeClass('activeGreen activeRed');
                    $(this).parent().removeClass('activeSelect').addClass("activeGreen");
                }
                else {
                    $(this).parent().parent().parent().find('.activeGreen').removeClass('activeGreen');
                    $(this).parent().removeClass('activeSelect').addClass("activeRed");
                }
            }
        );
        initData();
    });
    function closeEmailWin(){
        window.location.reload();
    }

    function confirmRedirect()
    {
        if (isDataChanged('simpleTable') || ${confirmableObjectExist})
        {
            win.open('redirectRefused');
            return false;
        }
        redirect();
        return true;
    }

    function redirect()
    {
        window.location = "${phiz:calculateActionURL(pageContext, '/private/userprofile/accountSecurity.do')}";
    }

</script>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="thisActionUrl" value="/private/async/userprofile/userNotification"/>
<html:form action="${thisActionUrl}">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmableObjectExist" value="${not empty form.confirmableObject}"/>

<%--подтверждение настройки уведомлений о входе на личную страницу--%>
<c:if test="${confirmableObjectExist && form.confirmedType == 'loginNotification'}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:choose>
    <c:when test="${not empty confirmRequest and confirmRequest.preConfirm}">
        <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
        <tiles:insert definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="confirmableObject" value="mailNotificationSettings"/>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirmNotificationSMS"/>
            <tiles:put name="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType=='sms'}">
                        <bean:message key="sms.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='cap'}">
                        <bean:message key="cap.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='push'}">
                        <bean:message key="push.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.delivery.type"/> : </tiles:put>
                    <tiles:put name="needMark" value="false"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${form.fields.loginNotification == 'sms'}">
                                <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phone)}"/>
                            </c:when>
                            <c:when test="${form.fields.loginNotification == 'email'}">
                                <c:set var="arg0" value="${form.fields.email}"/>
                            </c:when>
                            <c:when test="${form.fields.loginNotification == 'push'}">
                                <c:set var="arg0" value="${form.fields.push}"/>
                            </c:when>
                        </c:choose>
                        <span class="bold"><bean:message bundle="userprofileBundle" key="label.notification.${form.fields.loginNotification}" arg0="${arg0}"/></span>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        </tiles:insert>
    </c:when>
    <c:when test="${not empty confirmRequest and not confirmRequest.preConfirm}">
        &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            if (window.addError != undefined)
                addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
            if (window.addMessage != undefined)
                addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:when>
</c:choose>

<%--подтверждение настройки оповещений из службы помощи--%>
<c:if test="${confirmableObjectExist && form.confirmedType == 'mailNotification'}">
    <c:set var="mailConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:choose>
    <c:when test="${not empty mailConfirmRequest and mailConfirmRequest.preConfirm}">
        <c:set var="confirmTemplate" value="confirm_${mailConfirmRequest.strategyType}"/>
        <tiles:insert  definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="mailConfirmRequest"/>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="confirmableObject" value="mailNotificationSettings"/>
            <tiles:put name="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType=='sms'}">
                        <bean:message key="sms.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='cap'}">
                        <bean:message key="cap.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='push'}">
                        <bean:message key="cap.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirmMailSMS"/>
            <tiles:put name="confirmCommandKey" value="button.confirmMail"/>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.delivery.type"/> : </tiles:put>
                    <tiles:put name="needMark" value="false"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${form.fields.mailNotification == 'sms'}">
                                <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phoneForMail)}"/>
                            </c:when>
                            <c:when test="${form.fields.mailNotification == 'email'}">
                                <c:set var="arg0" value="${form.fields.email}"/>
                            </c:when>
                            <c:when test="${form.fields.mailNotification == 'push'}">
                                <c:set var="arg0" value="${form.fields.push}"/>
                            </c:when>
                        </c:choose>
                        <span class="bold"><bean:message bundle="userprofileBundle" key="label.notification.${form.fields.mailNotification}" arg0="${arg0}"/></span>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        </tiles:insert>
    </c:when>
    <c:when test="${not empty mailConfirmRequest and not mailConfirmRequest.preConfirm}">
        &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            if (window.addError != undefined)
                addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
            if (window.addMessage != undefined)
                addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:when>
</c:choose>

<%--подтверждение настройки оповещений об исполнении операций--%>
<c:if test="${confirmableObjectExist && form.confirmedType == 'operationNotification'}">
    <c:set var="deliveryConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:choose>
    <c:when test="${not empty deliveryConfirmRequest and deliveryConfirmRequest.preConfirm}">
        <c:set var="confirmTemplate" value="confirm_${deliveryConfirmRequest.strategyType}"/>
        <tiles:insert  definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="deliveryConfirmRequest"/>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="confirmableObject" value="mailNotificationSettings"/>
            <tiles:put name="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType=='sms'}">
                        <bean:message key="sms.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='cap'}">
                        <bean:message key="cap.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='push'}">
                        <bean:message key="push.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirmDeliveryNotificationSMS"/>
            <tiles:put name="confirmCommandKey" value="button.confirmDelivery"/>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.delivery.type"/> : </tiles:put>
                    <tiles:put name="needMark" value="false"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${form.fields.operationNotification == 'sms'}">
                                <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phoneForMail)}"/>
                            </c:when>
                            <c:when test="${form.fields.operationNotification == 'email'}">
                                <c:set var="arg0" value="${form.fields.email}"/>
                            </c:when>
                            <c:when test="${form.fields.operationNotification == 'push'}">
                                <c:set var="arg0" value="${form.fields.push}"/>
                            </c:when>
                        </c:choose>
                        <span class="bold"><bean:message bundle="userprofileBundle" key="label.notification.${form.fields.operationNotification}" arg0="${arg0}"/></span>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        </tiles:insert>
    </c:when>
    <c:when test="${not empty deliveryConfirmRequest and not deliveryConfirmRequest.preConfirm}">
        &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            if (window.addError != undefined)
                addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
            if (window.addMessage != undefined)
                addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:when>
</c:choose>

<%--подтверждение настройки SMS-оповещений по продуктам--%>
<c:if test="${not empty form.productsSmsNotificationConfirmableObject}">
    <c:set var="productsSmsNotificationConfirmRequest" value="${phiz:currentConfirmRequest(form.productsSmsNotificationConfirmableObject)}"/>
</c:if>
<c:choose>
    <c:when test="${not empty productsSmsNotificationConfirmRequest and productsSmsNotificationConfirmRequest.preConfirm}">
        <c:set var="confirmTemplate" value="confirm_${productsSmsNotificationConfirmRequest.strategyType}"/>
        <tiles:insert  definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="productsSmsNotificationConfirmRequest"/>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="confirmableObject" value="mailNotificationSettings"/>
            <tiles:put name="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType=='sms'}">
                        <bean:message key="sms.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='push'}">
                        <bean:message key="push.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirmProductsSmsNotificationSMS"/>
            <tiles:put name="confirmCommandKey" value="button.confirmProductsSms"/>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.delivery.type"/> : </tiles:put>
                    <tiles:put name="needMark" value="false"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${form.fields.operationNotification == 'sms'}">
                                <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phoneForMail)}"/>
                            </c:when>
                        </c:choose>
                        <span class="bold"><bean:message bundle="userprofileBundle" key="label.notification.sms" arg0="${arg0}"/></span>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        </tiles:insert>
    </c:when>
    <c:when test="${not empty productsSmsNotificationConfirmRequest and not productsSmsNotificationConfirmRequest.preConfirm}">
        &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            if (window.addError != undefined)
                addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
            if (window.addMessage != undefined)
                addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:when>
</c:choose>

<%--подтверждение настройки рассылки новостей банка--%>
<c:if test="${confirmableObjectExist && form.confirmedType == 'newsNotification'}">
    <c:set var="bankNewsConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:choose>
    <c:when test="${not empty bankNewsConfirmRequest and bankNewsConfirmRequest.preConfirm}">
        <c:set var="confirmTemplate" value="confirm_${bankNewsConfirmRequest.strategyType}"/>
        <tiles:insert  definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="bankNewsConfirmRequest"/>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="confirmableObject" value="mailNotificationSettings"/>
            <tiles:put name="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType=='sms'}">
                        <bean:message key="sms.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='cap'}">
                        <bean:message key="cap.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType=='push'}">
                        <bean:message key="push.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirmBankNewsNotificationSMS"/>
            <tiles:put name="confirmCommandKey" value="button.confirmBankNews"/>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.delivery.type"/> : </tiles:put>
                    <tiles:put name="needMark" value="false"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${form.fields.newsNotification == 'email'}">
                                <c:set var="arg0" value="${form.fields.email}"/>
                            </c:when>
                        </c:choose>
                        <span class="bold"><bean:message bundle="userprofileBundle" key="label.notification.${form.fields.newsNotification}" arg0="${arg0}"/></span>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        </tiles:insert>
    </c:when>
    <c:when test="${not empty bankNewsConfirmRequest and not bankNewsConfirmRequest.preConfirm}">
        &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            if (window.addError != undefined)
                addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
            if (window.addMessage != undefined)
                addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:when>
</c:choose>

<%--подтверждение всех настроек--%>
<c:if test="${confirmableObjectExist && form.confirmedType == 'allNotification'}">
    <c:set var="allConfirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
</c:if>
<c:choose>
    <c:when test="${not empty allConfirmRequest and allConfirmRequest.preConfirm}">
        <c:set var="confirmTemplate" value="confirm_${allConfirmRequest.strategyType}"/>
        <tiles:insert definition="${confirmTemplate}" flush="false">
            <tiles:put name="confirmRequest" beanName="allConfirmRequest"/>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="confirmableObject" value="mailNotificationSettings"/>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirmAllNotificationSMS"/>
            <tiles:put name="message">
                <c:choose>
                    <c:when test="${allConfirmRequest.strategyType=='sms'}">
                        <bean:message key="sms.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${allConfirmRequest.strategyType=='cap'}">
                        <bean:message key="cap.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                    <c:when test="${allConfirmRequest.strategyType=='push'}">
                        <bean:message key="push.settings.security.message" bundle="securityBundle"/>
                    </c:when>
                </c:choose>
            </tiles:put>
            <tiles:put name="data">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.delivery.type"/> : </tiles:put>
                    <tiles:put name="needMark" value="false"/>
                    <tiles:put name="data">
                        <div class="widePaymentValue">
                            <c:choose>
                                <c:when test="${form.fields.loginNotification == 'sms'}">
                                    <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phone)}"/>
                                </c:when>
                                <c:when test="${form.fields.loginNotification == 'email'}">
                                    <c:set var="arg0" value="${form.fields.email}"/>
                                </c:when>
                                <c:when test="${form.fields.loginNotification == 'push'}">
                                    <c:set var="arg0" value="${form.fields.push}"/>
                                </c:when>
                            </c:choose>
                            <span class="bold">Вход в «Сбербанк Онлайн» - <bean:message bundle="userprofileBundle" key="label.notification.${form.fields.loginNotification}" arg0="${arg0}"/></span><br/>
                            <c:choose>
                               <c:when test="${form.fields.mailNotification == 'sms'}">
                                   <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phoneForMail)}"/>
                               </c:when>
                               <c:when test="${form.fields.mailNotification == 'email'}">
                                   <c:set var="arg0" value="${form.fields.email}"/>
                               </c:when>
                               <c:when test="${form.fields.mailNotification == 'push'}">
                                   <c:set var="arg0" value="${form.fields.push}"/>
                               </c:when>
                            </c:choose>
                            <span class="bold">Пришло письмо от службы помощи - <bean:message bundle="userprofileBundle" key="label.notification.${form.fields.mailNotification}" arg0="${arg0}"/></span><br/>
                            <c:choose>
                                <c:when test="${form.fields.operationNotification == 'sms'}">
                                    <c:set var="arg0" value="${phiz:getCutPhoneNumber(form.fields.phoneForMail)}"/>
                                </c:when>
                                <c:when test="${form.fields.operationNotification == 'email'}">
                                    <c:set var="arg0" value="${form.fields.email}"/>
                                </c:when>
                                <c:when test="${form.fields.operationNotification == 'push'}">
                                    <c:set var="arg0" value="${form.fields.push}"/>
                                </c:when>
                            </c:choose>
                            <span class="bold">Статус исполнения операций - <bean:message bundle="userprofileBundle" key="label.notification.${form.fields.operationNotification}" arg0="${arg0}"/></span><br/>
                            <c:choose>
                                <c:when test="${form.fields.newsNotification == 'email'}">
                                    <c:set var="arg0" value="${form.fields.email}"/>
                                </c:when>
                            </c:choose>
                            <span class="bold">Обновление новостей банка - <bean:message bundle="userprofileBundle" key="label.notification.${form.fields.newsNotification}" arg0="${arg0}"/></span>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
        </tiles:insert>
    </c:when>
    <c:when test="${not empty allConfirmRequest and not allConfirmRequest.preConfirm}">
        &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
        <script type="text/javascript">

            <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            if (window.addError != undefined)
                addError('<bean:write name="error" filter="false"/>', null, true);
            </phiz:messages>

            <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
            if (window.addMessage != undefined)
                addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
            </phiz:messages>

            if (window.win != undefined)
                win.close(confirmOperation.windowId);
        </script>
    </c:when>
</c:choose>
</html:form>
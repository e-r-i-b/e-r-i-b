<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/userprofile/productNotification">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

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
                <tiles:put name="ajaxUrl" value="/private/async/userprofile/productNotification"/>
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
</html:form>
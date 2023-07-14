<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/notificationSettings">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <login>
                <c:set var="notificationType" value="${form.notificationType}"/>
                <c:set var="editable" value="${phiz:impliesService('MApiLoginSetupNotification')}"/>
                <tiles:insert page="notification.jsp" flush="false">
                    <tiles:put name="notificationSetting" value="login"/>
                    <tiles:put name="notificationType" value="${notificationType}"/>
                    <tiles:put name="notificationEditable" value="${editable}"/>
                </tiles:insert>
            </login>

            <help>
                <c:set var="mailNotificationType" value="${form.mailNotificationType}"/>
                <c:set var="editable" value="${phiz:impliesService('MApiMailSetupNotification')}"/>
                <tiles:insert page="notification.jsp" flush="false">
                    <tiles:put name="notificationSetting" value="help"/>
                    <tiles:put name="notificationType" value="${mailNotificationType}"/>
                    <tiles:put name="notificationEditable" value="${editable}"/>
                </tiles:insert>
            </help>

            <operations>
                <c:set var="deliveryNotificationType" value="${form.deliveryNotificationType}"/>
                <c:set var="editable" value="${phiz:impliesService('MApiOperationSetupNotification')}"/>
                <tiles:insert page="notification.jsp" flush="false">
                    <tiles:put name="notificationSetting" value="operations"/>
                    <tiles:put name="notificationType" value="${deliveryNotificationType}"/>
                    <tiles:put name="notificationEditable" value="${editable}"/>
                </tiles:insert>
            </operations>

            <c:if test="${phiz:getApiVersionNumber().major >= 9}">
                <needLoginConfirm>
                    <c:set var="needLoginConfirm" value="${form.needLoginConfirm}"/>
                    ${needLoginConfirm}
                </needLoginConfirm>

                <operationConfirm>
                    <c:set var="operationConfirmNotificationType" value="${form.operationConfirmNotificationType}"/>
                    <tiles:insert page="notification.jsp" flush="false">
                        <tiles:put name="notificationSetting" value="operationConfirm"/>
                        <tiles:put name="notificationType" value="${operationConfirmNotificationType}"/>
                        <tiles:put name="notificationEditable" value="true"/>
                    </tiles:insert>
                </operationConfirm>
            </c:if>

        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="thisActionUrl" value="/private/async/userprofile/accountSecurity"/>
<html:form action="${thisActionUrl}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    </c:if>
    <c:choose>
        <c:when test="${not empty confirmRequest and confirmRequest.preConfirm}">
            <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
            <tiles:insert  definition="${confirmTemplate}" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="anotherStrategy" value="false"/>
                <tiles:put name="confirmableObject" value="securitySettings"/>
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
                        <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.password.onetime"/> : </tiles:put>
                        <tiles:put name="needMark" value="false"/>
                        <tiles:put name="data">
                            <c:choose>
                                <c:when test="${form.fields.oneTimePassword == true}">
                                     <span class="bold">Да</span>
                                </c:when>
                                <c:when test="${form.fields.oneTimePassword == false}">
                                     <span class="bold">Нет</span>
                                </c:when>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title"><bean:message bundle="userprofileBundle" key="label.confirm.type"/> : </tiles:put>
                        <tiles:put name="needMark" value="false"/>
                        <tiles:put name="data">
                            <span class="bold"><bean:message bundle="userprofileBundle" key="label.confirm.${form.fields.confirmType}"/></span>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="useAjax" value="true"/>
                <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
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
        </c:otherwise>
    </c:choose>
</html:form>
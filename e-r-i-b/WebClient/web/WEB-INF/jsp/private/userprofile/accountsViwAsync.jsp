<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="thisActionUrl" value="/private/async/userprofile/accountSecurity/accountsViewConfirm"/>
<html:form action="${thisActionUrl}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmableObject"  value="${form.confirmableObject}"/>
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(confirmableObject)}"/>
        <c:choose>
        <c:when test="${confirmableObject.viewType == 'showInSystem'}">
            <c:set var="confirmButtonKey" value="button.confirmSystem"/>
            <c:set var="preConfirmButtonKey" value="button.preConfirmSystem"/>
        </c:when>
        <c:when test="${confirmableObject.viewType == 'showInES'}">
            <c:set var="confirmButtonKey" value="button.confirmES"/>
            <c:set var="preConfirmButtonKey" value="button.preConfirmES"/>
        </c:when>
        <c:when test="${confirmableObject.viewType == 'showInMobile'}">
            <c:set var="confirmButtonKey" value="button.confirmMobile"/>
            <c:set var="preConfirmButtonKey" value="button.preConfirmMobile"/>
        </c:when>
        <c:when test="${confirmableObject.viewType == 'showInSocial'}">
            <c:set var="confirmButtonKey" value="button.confirmSocial"/>
            <c:set var="preConfirmButtonKey" value="button.preConfirmSocial"/>
        </c:when>
        <c:when test="${confirmableObject.viewType == 'showInErmb'}">
            <c:set var="confirmButtonKey" value="button.confirmErmb"/>
            <c:set var="preConfirmButtonKey" value="button.preConfirmErmb"/>
        </c:when>
    </c:choose>
    </c:if>
    <c:set var="hasErrors" value="false"/>
    &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
    <script type="text/javascript">
        <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            <c:set var="errorEscape" value="${phiz:escapeForJS(error, true)}"/>
            if (window.addError != undefined)
            {
                var error = "${phiz:processBBCodeAndEscapeHtml(errorEscape,false)}";
                addError(error, null, true);
            }
            <c:set var="hasErrors" value="true"/>
        </phiz:messages>

        <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
             <c:set var="messageEscape" value="${phiz:escapeForJS(messages, true)}"/>
             if (window.addMessage != undefined)
             {
                 var message = "${phiz:processBBCodeAndEscapeHtml(messageEscape,false)}";
                 addMessage(message);
             }
            <c:set var="hasErrors" value="true"/>
        </phiz:messages>
    </script>
    <div class="securityPasswordWindow">
        <c:choose>
            <c:when test="${not hasErrors and not empty confirmRequest and confirmRequest.preConfirm}">

                <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                <tiles:insert  definition="${confirmTemplate}" flush="false">
                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                    <tiles:put name="anotherStrategy" value="false"/>
                    <tiles:put name="confirmableObject" value="securitySettings"/>
                    <tiles:put name="confirmCommandKey" value="${confirmButtonKey}"/>
                    <tiles:put name="preConfirmCommandKey" value="${preConfirmButtonKey}${fn:toUpperCase(confirmRequest.strategyType)}"/>
                    <tiles:put name="message">
                        <bean:message key="${confirmRequest.strategyType}.settings.security.message" bundle="securityBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <c:set var="availableProducts" value="${confirmableObject.availableProducts}"/>
                        <c:set var="unavailableProducts" value="${confirmableObject.unavailableProducts}"/>
                        <c:if test="${not empty availableProducts || not empty unavailableProducts}">
                            <c:choose>
                                <c:when test="${confirmableObject.viewType == 'showInSystem'}">
                                    <span class="connectionSettings"><bean:message bundle="commonBundle" key="label.showInSBOL"/></span>
                                </c:when>
                               <c:when test="${confirmableObject.viewType == 'showInES'}">
                                    <span class="connectionSettings">Отображение в устройствах самообслуживания:</span>
                                </c:when>
                               <c:when test="${confirmableObject.viewType == 'showInMobile'}">
                                    <span class="connectionSettings">Отображение в мобильном приложении:</span>
                                </c:when>
                                <c:when test="${confirmableObject.viewType == 'showInSocial'}">
                                    <span class="connectionSettings">Отображение в соц. приложении:</span>
                                </c:when>
                                <c:when test="${confirmableObject.viewType == 'showInErmb'}">
                                    <span class="bold">Отображение в смс-канале:</span>
                                </c:when>
                            </c:choose>
                            <c:if test="${not empty availableProducts}">
                                <div class="connection"> Вы подключили:</div>
                                <c:forEach var="value" items="${fn:split(availableProducts,';')}">
                                    <div class="connectedProducts"><c:out value="${value}"/></div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty unavailableProducts}">
                                <div class="connection">Вы отключили:</div>
                                <c:forEach var="value" items="${fn:split(unavailableProducts,';')}">
                                    <div class="connectedProducts"><c:out value="${value}"/></div>
                                </c:forEach>
                            </c:if>

                        </c:if>
                    </tiles:put>
                    <tiles:put name="useAjax" value="true"/>
                    <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <script type="text/javascript">
                    if (window.win != undefined)
                        win.close(confirmOperation.windowId);
                </script>

            </c:otherwise>
        </c:choose>
    </div>
</html:form>

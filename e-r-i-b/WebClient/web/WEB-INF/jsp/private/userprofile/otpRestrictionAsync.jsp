<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 14.12.2011
  Time: 9:11:28
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="thisActionUrl" value="/private/async/userprofile/otpRestriction"/>
<html:form action="${thisActionUrl}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmableObject"  value="${form.confirmableObject}"/>
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(confirmableObject)}"/>
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

        try {
            $(document).ready(function()
            {
                if ($('.word-wrap') != undefined) {
                    $('.word-wrap').breakWords();
                }
            });
        } catch (e) { }

    </script>
    <div class="securityPasswordWindow">
        <c:choose>
            <c:when test="${not hasErrors and not empty confirmRequest and confirmRequest.preConfirm}">
                <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                <tiles:insert definition="${confirmTemplate}" flush="false">
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
                        <div class="otpRestrictionsConfirm">
                            <table width="100%">
                                <tr>
                                    <th>&nbsp;</th>
                                    <th><bean:message bundle="userprofileBundle" key="title.OTPGet"/></th>
                                    <th><bean:message bundle="userprofileBundle" key="title.OTPUse"/></th>
                                </tr>
                                <c:forEach items="${form.changedCards}" var="listElement">
                                    <c:set var="card" value="${listElement.card}"/>
                                    <c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
                                    <c:set var="id" value="${listElement.id}"/>
                                        <tr>
                                            <td class="align-left" width="300px">
                                                <div class="products-text-style">
                                                    <span class="word-wrap"><bean:write name="listElement" property="name"/>&nbsp;</span>
                                                    <span class="card-number">${phiz:getCutCardNumber(listElement.number)}</span>&nbsp;
                                                    <c:set var="spanClass" value="text-green"/>
                                                    <c:if test="${listElement.card.availableLimit.decimal < 0 || isLock}">
                                                        <c:set var="spanClass" value="text-red"/>
                                                    </c:if>
                                                    <span class="${spanClass}"><nobr>${phiz:formatAmount(listElement.card.availableLimit)}</nobr></span>
                                                </div>
                                            </td>
                                            <td class="align-left">
                                                <div class="products-text-style">
                                                    <c:set var="mess" value="label.OTPGet"/>
                                                    <c:if test="${listElement.OTPGet == false}">
                                                        <c:set var="mess" value="label.notOTPGet"/>
                                                    </c:if>
                                                    <span><bean:message bundle="userprofileBundle" key="${mess}"/></span>
                                                </div>
                                            </td>
                                            <td class="align-left">
                                                <div id="divOTPUse${id}" <c:if test="${listElement.OTPGet}">style="display: none"</c:if>>
                                                    <c:set var="mess" value="label.OTPUse"/>
                                                    <c:if test="${listElement.OTPUse == false}">
                                                        <c:set var="mess" value="label.notOTPUse"/>
                                                    </c:if>
                                                    <span><bean:message bundle="userprofileBundle" key="${mess}"/></span>
                                                </div>
                                            </td>
                                        </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </tiles:put>
                    <tiles:put name="useAjax" value="true"/>
                    <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <script type="text/javascript">
                    if (window.win != undefined)
                    {
                        win.close(confirmOperation.windowId);
                    }
                </script>

            </c:otherwise>
        </c:choose>
    </div>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

&nbsp; <%-- ie --%>
<c:set var="form" value="${RemoveTargetAjaxForm}"/>
<c:set var="defaultActionURL" value="${phiz:calculateActionURL(pageContext, '/private/payments/default-action')}"/>
<c:set var="paymentActionURL" value="${phiz:calculateActionURL(pageContext, '/private/payments/payment')}"/>
<c:set var="removeState">${form.removeState}</c:set>

<c:choose>
    <c:when test="${removeState eq 'CLAIM_ERROR'}">                        <%-- если ошибка то выводим ее --%>
        <c:set var="bundle" value="commonBundle"/>

        <%-- Сообщения --%>
        <c:set var="message" value=""/>
        <phiz:messages id="messages" bundle="${bundle}" field="field" message="message">
            <c:set var="message">${message}<div class = "itemDiv">${phiz:processBBCodeAndEscapeHtml(messages, false)} </div></c:set>
        </phiz:messages>

        <c:set var="messagesLength" value="${fn:length(message)}"/>

        <tiles:insert definition="warningBlock" flush="false">
            <tiles:put name="regionSelector" value="warnings"/>
            <tiles:put name="isDisplayed" value="${messagesLength gt 0 ? true : false}"/>
            <tiles:put name="data">
                <bean:write name="message" filter="false"/>
            </tiles:put>
        </tiles:insert>

        <%-- Ошибки --%>
        <c:set var="errors" value=""/>
        <phiz:messages  id="error" bundle="${bundle}" field="field" message="error">
            <c:set var="errors">${errors}<div class = "itemDiv">${phiz:processBBCodeAndEscapeHtml(error, false)} </div></c:set>
        </phiz:messages>

        <c:set var="errorsLength" value="${fn:length(errors)}"/>

        <tiles:insert definition="errorBlock" flush="false">
            <tiles:put name="regionSelector" value="errors"/>
            <tiles:put name="isDisplayed" value="${errorsLength gt 0 ? true : false}"/>
            <tiles:put name="data">
                <bean:write name="errors" filter="false"/>
            </tiles:put>
        </tiles:insert>

        <%-- Недоступность ВС --%>
        <c:set var="inactiveESMessages" value=""/>
        <jsp:useBean id="inactiveExternalSystemMessages" scope="request" class="java.util.ArrayList" />
        <phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
            <c:set var="prepareMessage" value="${phiz:processBBCode(inactiveES)}" scope="request"/>
            <c:if test="${not phiz:contains(inactiveExternalSystemMessages, prepareMessage)}">
                <% inactiveExternalSystemMessages.add(request.getAttribute("prepareMessage")); %>
                <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${prepareMessage} </div></c:set>
            </c:if>
        </phiz:messages>

        <c:set var="inactiveESMessagesLength" value="${fn:length(inactiveESMessages)}"/>

        <tiles:insert definition="inactiveESMessagesBlock" flush="false">
            <tiles:put name="regionSelector" value="inactiveMessages"/>
            <tiles:put name="isDisplayed" value="${inactiveESMessagesLength gt 0 ? true : false}"/>
            <tiles:put name="data">
                <bean:write name='inactiveESMessages' filter='false'/>
            </tiles:put>
        </tiles:insert>

        <c:set var="responseState" value="ERROR"/>
    </c:when>
    <c:when test="${removeState eq 'CLAIM_REQUIRE_CLIENT_ACTION'}">        <%-- если требуются действия пользователя, то редиректим на заявку --%>
        <script type="text/javascript">
            $(document).ready(function(){
                <c:choose>
                    <c:when test="${not empty form.claimId}">
                        goTo('${defaultActionURL}?id=${form.claimId}&objectFormName=AccountClosingPayment&stateCode=${form.claimStateCode}');
                    </c:when>
                    <c:otherwise>
                        goTo('${paymentActionURL}?form=AccountClosingPayment&fromResource=account:${form.accountLinkId}');
                    </c:otherwise>
                </c:choose>
            });
        </script>
        <c:set var="responseState" value="REDIRECT"/>
    </c:when>
    <c:otherwise>                                                               <%-- иначе все хорошо --%>
        <c:set var="responseState" value="SUCCESS"/>
    </c:otherwise>
</c:choose>

<div class="hidden">
    <input type="hidden" id="responceState" value="${responseState}"/>
</div>


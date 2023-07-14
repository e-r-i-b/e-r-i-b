<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/payments/accountOpeningClaim/confirm" updateAtributes="false">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="formName" value="${metadata.form.name}"/>
    <c:set var="document" value="${form.document}"/>
    <c:set var="documentType">заявку</c:set>

    <script type="text/javascript">
        function accountOpeningSuccess()
        {
            enableInputFields();
            new CommandButton('afterAccountOpening', '').click();
            win.close('oneTimePasswordWindow');
        }

        function accountOpeningError()
        {
            enableInputFields();
            removeAllErrors();
            addError("Операция не выполнена. Попробуйте позднее или выберите другой счет для зачисления");
            win.close('oneTimePasswordWindow');
        }

        function enableInputFields()
        {
            showOrHideAjaxPreloader(false);showOrHideWaitDiv(false);
            var paymentInputs = $(":input");
            for (var index = 0; index < paymentInputs.length; index++)
            {
                paymentInputs[index].disabled = false;
            }
        }
    </script>

    <c:choose>
        <c:when test="${(not empty confirmRequest and confirmRequest.preConfirm) or (not empty form.fields and form.fields.confirmByCard and (empty form.fields.confirmCardId))}">
            <c:set var="message">
                <c:choose>
                    <c:when test="${confirmRequest.strategyType eq 'sms'}">
                        <bean:message key="sms.payments.security.param.message" bundle="securityBundle"
                                      arg0="${documentType}"/>
                    </c:when>
                    <c:when test="${confirmRequest.strategyType eq 'cap'}">
                        <bean:message key="cap.payments.security.param.message" bundle="securityBundle"
                                      arg0="${documentType}"/>
                    </c:when>
                    <c:otherwise>
                        Внимательно проверьте, правильно ли Вы заполнили ${documentType}. Для подтверждения операции нажмите на кнопку &laquo;Подтвердить&raquo;.
                    </c:otherwise>
                </c:choose>
            </c:set>

            <c:choose>
                <c:when test="${not empty form.fields and form.fields.confirmByCard}">
                    <c:set var="confirmTemplate" value="confirm_card"/>
                </c:when>
                <c:otherwise>
                    <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                </c:otherwise>
            </c:choose>
            <tiles:insert definition="${confirmTemplate}" flush="false">
                <c:if test="${not empty form.autoConfirmRequestType}">
                    <tiles:put name="showActionMessages" value="true"/>
                </c:if>
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                <tiles:put name="message" value="${message}"/>
                <tiles:put name="guest" value="${form.guest}"/>
                <c:set var="documentShortNumber" value=""/>
                <c:if test="${confirmRequest.strategyType == 'card'}">
                    <tiles:put name="cardNumber" value="${phiz:getCutCardNumber(form.fields.confirmCard)}"/>
                </c:if>
                <c:set var="buttonName"><bean:message key="button.dispatch" bundle="securityBundle"/></c:set>
                <tiles:put name="validationFunction" value="checkClientAgreesCondition('${buttonName}');"/>
                <tiles:put name="confirmableObject" value="accountOpeningClaimInLoanClaim"/>

                <tiles:put name="byCenter" value="'Center'"/>
                <tiles:put name="confirmCommandKey" value="button.dispatch"/>
                <tiles:put name="data">${form.html}</tiles:put>
                <tiles:put name="useAjax" value="true"/>
                <tiles:put name="ajaxUrl" value="/private/async/payments/accountOpeningClaim/confirm"/>
                <tiles:put name="ajaxOnCompleteCallback">accountOpeningSuccess()</tiles:put>
                <tiles:put name="ajaxOnErrorCallback">accountOpeningError()</tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
            <script type="text/javascript">

                <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
                    <c:set var="errorEscape" value="${phiz:escapeForJS(error, true)}"/>
                    if (window.addError != undefined)
                    {
                        var error = "${errorEscape}";
                        addError(error, null, true);
                    }
                </phiz:messages>

                <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
                <c:set var="messageEscape" value="${phiz:escapeForJS(messages, true)}"/>
                    if (window.addMessage != undefined)
                    {
                        var message = "${messageEscape}";
                        addMessage(message);
                    }
                </phiz:messages>

                if (window.win != undefined)
                    win.close(confirmOperation.windowId);
            </script>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript">
        removeStateAttr();
    </script>
</html:form>
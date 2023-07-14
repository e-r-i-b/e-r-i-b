<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

&nbsp; <%-- IE6 этим все сказано --%>
<c:set var="thisActionUrl" value="/private/async/payments/quicklyCreateTemplate"/>
<html:form action="${thisActionUrl}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <input type="hidden" name="payment" id="payment" value="${form.payment}">
    <input type="hidden" name="templateName" id="templateName" value="${form.templateName}">
    <input type="hidden" name="templateId" id="templateId" value="${form.confirmableObject.id}">
    <c:set var="forName" value="${form.confirmableObject.formType.name}"/>
    <c:set var="errors" value=""/>

    <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
        <c:set var="errors">${errors}${error}</c:set>
    </phiz:messages>
    <c:choose>
    <c:when test="${not empty form.confirmRequest && (not empty form.confirmableObject && (empty errors || not empty (phiz:currentConfirmRequest(form.confirmableObject).errorMessage)))}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
        <tiles:insert definition="confirm_${confirmRequest.strategyType}" flush="false">
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
            <tiles:put name="title" value="Подтверждение шаблона"/>
            <tiles:put name="message">
                <bean:message key="sms.payments.security.param.message" bundle="securityBundle"
                                              arg0="реквизиты шаблона"/>
            </tiles:put>
            <tiles:put name="anotherStrategy" value="false"/>
            <tiles:put name="data">
                ${form.html}
                <c:choose>
                    <c:when test="${forName == 'NewRurPayment'}">
                        <div class="form-row-addition form-row-new">
                            <div class="paymentLabelNew">
                                <span class="paymentTextLabel">
                                    Название шаблона:
                                </span>
                            </div>
                            <div class="paymentValue paymentValueNew confirmCreateTemplate">
                                <div class="paymentInputDiv autoInputWidth allWidth">
                                    <div class="linear">
                                        <span><c:out value="${form.templateName}"/> </span>
                                    </div>
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <span class="paymentTextLabel">
                                    Название шаблона:
                                </span>
                            </div>
                            <div class="paymentValue">
                                <div class="paymentInputDiv">
                                    <c:out value="${form.templateName}"/>
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
            <tiles:put name="confirmableObject" value=""/>
            <tiles:put name="preConfirmCommandKey" value="button.preConfirm${fn:toUpperCase(confirmRequest.strategyType)}"/>
            <tiles:put name="confirmCommandKey" value="button.confirm"/>
            <tiles:put name="useAjax" value="true"/>
            <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
            <tiles:put name="onKeyPressFunction">
                var kk = navigator.appName == 'Netscape' ? event.which : event.keyCode;
                    if(kk == 13)
                        onConfirm();
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:otherwise>
        <c:set var="template" value="${form.confirmableObject}"/>
        <%-- Если при создании произошла ошибка (такой шаблон уже есть или имя превышает допустимый размер) то вернется сообщение об ошибкe, шаблон создан не будет --%>
        <c:if test="${not empty template}">
            <c:set var="temp" value="${phiz:getTemplateLinkByTemplate(pageContext, template)}"/>
        </c:if>
        <c:set var="titleTag" value="<span>${form.templateName}</span>"/>
        <script type="text/javascript">
                $(document).ready(function(){
                    removeAllErrors();
                <c:choose>
                    <c:when test="${not empty errors}">
                        addError('${errors}');
                        win.close('oneTimePasswordWindow');
                        confirmOperation.initLoadedData();
                    </c:when>
                    <c:otherwise>
                        addMessage('<bean:message key="message.success.quicly.create.template"
                                  bundle="paymentsBundle"
                                  arg0="${phiz:calculateActionURL(pageContext, '/private/favourite/list/PaymentsAndTemplates.do')}"/>');

                        <c:if test="${not empty template}">
                            var templatesList = document.getElementById("templatesLinkList");
                            if (templatesList)
                            {
                                templatesList.innerHTML = '<phiz:linksListItem title="${titleTag}" href="${temp}" outputByItself="true" onClick="return personalMenuItemClick(event);"/>' + templatesList.innerHTML;
                            }
                        </c:if>

                        win.close('oneTimePasswordWindow');
                        confirmOperation.initLoadedData();
                        closeTemplateForm(${form.payment});
                    </c:otherwise>
                </c:choose>
                });
        </script>
    </c:otherwise>
    </c:choose>
</html:form>

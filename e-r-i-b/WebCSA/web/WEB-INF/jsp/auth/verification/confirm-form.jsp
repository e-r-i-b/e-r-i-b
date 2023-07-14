<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert definition="stage" flush="false">
    <tiles:put name="title" type="string">
        <bean:message bundle='businessEnvironmentBundle' key='form.confirm.title'/>
    </tiles:put>
    <tiles:put name="description" type="string">
        <bean:message bundle='businessEnvironmentBundle' key='form.confirm.description'/>
    </tiles:put>
    <tiles:put name="data" type="string">
        <html:form action="/businessEnvironment/verify">
            <c:set var="form" value="${csa:currentForm(pageContext)}"/>
            <c:set var="redirectURL" value="${form.redirect}"/>
            <c:set var="confirmationError" value="${form.confirmationError}"/>
            <c:choose>
                <%-- нужно редиректить --%>
                <c:when test="${not empty redirectURL}">
                    <script type="text/javascript">
                        doOnLoad(function(){
                            win.close('confirm');
                            goTo('${redirectURL}');
                        });
                    </script>
                </c:when>
                <c:when test="${not empty confirmationError}">
                    <span id="confirmationError"><bean:message bundle='businessEnvironmentBundle' key='form.confirm.error.${confirmationError}'/></span>
                </c:when>
                <%-- иначе, рисуем обычную страницу подтверждения --%>
                <c:otherwise>
                    <c:set var="operationInfo" value="${form.operationInfo}"/>
                    <c:set var="confirmType" value="${operationInfo.confirmType}"/>
                    <div id="stageForm" onkeypress="clickDefButtonIfEnterKeyPress(this, event);">
                        <c:set var="confirmParams" value="${operationInfo.confirmParams}"/>
                        <c:set var="attemps" value="${confirmParams['Attempts']}"/>
                        <hr/>
                        <c:if test="${not empty confirmType}">
                            <tiles:insert definition="formRow" flush="false">
                                <tiles:put name="title">
                                    <span class="fieldTitle middleLine" id="fieldTitle">
                                        <bean:message bundle="businessEnvironmentBundle"
                                                      key="form.confirm.field.password.${confirmType}"
                                                      arg0="${confirmParams['PasswordNo']}"
                                                      arg1="${confirmParams['ReceiptNo']}"/>
                                    </span>
                                </tiles:put>
                                <tiles:put name="data">
                                    <input type="text" name="field(confirmCode)" size="10" tabindex="1"/>
                                    <bean:message bundle='businessEnvironmentBundle' key='form.confirm.field.password.alternative.${confirmType}' arg0="changeConfirmType()"/>
                                </tiles:put>
                            </tiles:insert>
                            <script type="text/javascript">
                                function changeConfirmType()
                                {
                                    <c:choose>
                                        <c:when test="${confirmType ne 'sms' and confirmType ne 'push'}">
                                            getButton('button.initializeVerify.alternative.${confirmType}').click();
                                        </c:when>
                                        <c:otherwise>
                                            win.close('confirm');
                                            /* описана на agreement-form.jsp */
                                            tryCardConfirm();
                                        </c:otherwise>
                                    </c:choose>
                                }

                                function reloadConfirmWindow(data)
                                {
                                    var actualToken = $(data).find('input[name="org.apache.struts.taglib.html.TOKEN"]').val();
                                    if (actualToken != undefined)
                                        $('input[name="org.apache.struts.taglib.html.TOKEN"]').val(actualToken);
                                    $(win.active).find("#confirmData").html(data);
                                }

                                function validateForm()
                                {
                                    if ($('input[name="field(confirmCode)"]').val() == '')
                                    {
                                        payInput.fieldError('field(confirmCode)', "<bean:message bundle='businessEnvironmentBundle' key='form.confirm.field.password.required.message.${confirmType}'/>", null);
                                        return false;
                                    }
                                    return true;
                                }

                                if(window.payInput != undefined && window.$ != undefined)
                                {
                                    payInput.setEvents(payInput, null);
                                    $('input[name="field(confirmCode)"]')[0].focus();
                                }
                            </script>

                        </c:if>
                        <div class="buttonsArea">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="id" value="button.close"/>
                                <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.confirm.button.cancel'/></tiles:put>
                                <tiles:put name="type" value="Grey"/>
                                <tiles:put name="onclick" value="win.close('confirm');"/>
                                <tiles:put name="tabindex" value="2"/>
                            </tiles:insert>
                            <c:if test="${not empty confirmType}">
                                <c:if test="${confirmType ne 'sms' and confirmType ne 'push'}">
                                    <tiles:insert definition="commandButton" flush="false">
                                        <tiles:put name="id" value="button.initializeVerify.alternative.${confirmType}"/>
                                        <tiles:put name="type" value="Hidden"/>
                                        <tiles:put name="useAjax" value="true"/>
                                        <tiles:put name="afterAjax" value="reloadConfirmWindow"/>
                                        <tiles:put name="tabindex" value="2"/>
                                    </tiles:insert>
                                </c:if>
                                <c:choose>
                                    <c:when test="${attemps ne null and attemps gt 0}">
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="id" value="button.verify"/>
                                            <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.confirm.button.confirm'/></tiles:put>
                                            <tiles:put name="type" value="Green"/>
                                            <tiles:put name="isDefault" value="true"/>
                                            <tiles:put name="useAjax" value="true"/>
                                            <tiles:put name="validationFunction" value="validateForm"/>
                                            <tiles:put name="afterAjax" value="reloadConfirmWindow"/>
                                            <tiles:put name="tabindex" value="3"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="id" value="button.initializeVerify.${confirmType}"/>
                                            <tiles:put name="title"><bean:message bundle='businessEnvironmentBundle' key='form.confirm.button.new.${confirmType}'/></tiles:put>
                                            <tiles:put name="type" value="Green"/>
                                            <tiles:put name="isDefault" value="true"/>
                                            <tiles:put name="useAjax" value="true"/>
                                            <tiles:put name="afterAjax" value="reloadConfirmWindow"/>
                                            <tiles:put name="tabindex" value="3"/>
                                        </tiles:insert>
                                     </c:otherwise>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </html:form>
    </tiles:put>
</tiles:insert>
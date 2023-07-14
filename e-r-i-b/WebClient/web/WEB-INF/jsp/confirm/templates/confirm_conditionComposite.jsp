<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:if test="${not confirmRequest.error}">
    <c:set var="cardNotEmpty" value="false" scope="page"/>
    <logic:iterate id="requestId" name="confirmRequest" property="requests">
        <c:if test="${requestId.strategyType=='card'}">
            <c:set var="cardNotEmpty" value="true" scope="page"/>
        </c:if>

        <c:if test="${requestId.strategyType=='sms'}">
            <c:set var="smsp" value="${requestId.send}" scope="page"/>
        </c:if>

        <c:if test="${requestId.strategyType=='push'}">
            <c:set var="pushp" value="${requestId.send}" scope="page"/>
        </c:if>

        <c:if test="${requestId.strategyType=='cap'}">
        <c:set var="cardNotEmpty" value="true" scope="page"/>
        </c:if>

    </logic:iterate>

    <c:if test="${!cardNotEmpty}">
        <c:set var="smsp" value="true" scope="page"/>
    </c:if>
    <script type="text/javascript">
        $(document).ready(function(){
            $('.confirmConditionComposite').val('').focus();
            $('.conditionComposite').trigger('onclick');  
        })

    </script>

    <logic:iterate id="requestId" name="confirmRequest" property="requests">
        <c:if test="${requestId.strategyType=='card'}">
            <c:if test="${!smsp && !pushp}">
                <div id="paymentForm">
                    <div class="form-row conditionComposite" id="$$confirmCardPassword" onclick="payInput.onClick(this)">
                        <div class="paymentLabel">
                            <span class="paymentTextLabel">
                                Для завершения платежа введите пароль № <c:out value="${requestId.passwordNumber}"/> с чека № <c:out value="${requestId.cardNumber}"/>
                            </span>
                            <span class="asterisk" id="asterisk_$$confirmCardPassword">*</span>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <input type="text" class="confirmConditionComposite" name="$$confirmCardPassword" maxlength="10"/>
                            </div>
                            <div style="display: none" class="description">Введите пароль с чека.
                                <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
                                <div class="detail" style="display: none">
                                    Для подтверждения перевода введите пароль.
                                </div>
                            </div>
                            <div style="display: none;" class="errorDiv"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            <script type="text/javascript">
                if (document.getElementsByName('$$confirmCardPassword').length == 1)
                    document.getElementsByName('$$confirmCardPassword')[0].onfocus = function()
                    {
                        payInput.onFocus(this);
                    };
            </script>
            </c:if>
        </c:if>

        <c:if test="${requestId.strategyType=='cap'}">
            <c:if test="${!smsp && !pushp}">
                <div id="paymentForm">
                    <div class="form-row conditionComposite" id="$$confirmCapPassword" onclick="payInput.onClick(this)">
                        <div class="paymentLabel">
                            <span class="paymentTextLabel">
                                Для завершения платежа введите пароль c карты
                            </span>
                            <span class="asterisk" id="asterisk_$$confirmCapPassword">*</span>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <input type="text" class="confirmConditionComposite" name="$$confirmCapPassword" maxlength="10"/>
                            </div>
                            <div style="display: none" class="description">Введите пароль.
                                <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
                                <div class="detail" style="display: none">
                                    Для подтверждения перевода введите пароль.
                                </div>
                            </div>
                            <div style="display: none;" class="errorDiv"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <script type="text/javascript">
                    if (document.getElementsByName('$$confirmCapPassword').length == 1)
                        document.getElementsByName('$$confirmCapPassword')[0].onfocus = function()
                        {
                            payInput.onFocus(this);
                        };
                </script>
            </c:if>
        </c:if>

        <c:if test="${requestId.strategyType=='sms'}">
        <c:if test="${smsp}">
                <div id="paymentForm">
                <div class="form-row" id="$$confirmSmsPassword" onclick="payInput.onClick(this)">
                <div class="paymentLabel">
                    <span class="paymentTextLabel">
                        <span style="font-size:12px">Для завершения платежа получите пароль по SMS</span>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.preConfirm"/>
                            <tiles:put name="commandTextKey" value="button.preConfirm"/>
                            <tiles:put name="commandHelpKey" value="button.preConfirm.help"/>
                            <tiles:put name="viewType"       value="simpleLink"/>
                            <tiles:put name="bundle"         value="securityBundle"/>
                        </tiles:insert>
                    </span>
                    
                </div>
                <div class="paymentValue">
                    <div class="paymentInputDiv">
                        <input type="text" name="$$confirmSmsPassword" checked="true"/>&nbsp;
                   </div>
                        <%--<div style="display: none" class="description">Введите SMS-пароль.--%>
                            <%--<a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>--%>
                            <%--<div class="detail" style="display: none">--%>
                                <%--Для подтверждения перевода введите пароль, полученный в SMS-сообщении.<br/>Пароль нужно ввести в течение 300 секунд.--%>
                            <%--</div>--%>

                        <%--</div>--%>
                    <div style="display: none;" class="errorDiv"></div>
                </div>
                <div class="clear"></div>
            </div>

            <script type="text/javascript">
                if (document.getElementsByName('$$confirmSmsPassword').length == 1)
                    document.getElementsByName('$$confirmSmsPassword')[0].onfocus = function()
                    {
                        payInput.onFocus(this);
                    };
            </script>
            </div>
         </c:if>
        </c:if>
        <c:if test="${requestId.strategyType=='push'}">
            <c:if test="${pushp}">
                <div id="paymentForm">
                    <div class="form-row" id="$$confirmPushPassword" onclick="payInput.onClick(this)">
                        <div class="paymentLabel">
                            <span class="paymentTextLabel">
                                <span style="font-size:12px">Для завершения платежа получите пароль через Push-сообщение</span>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey"     value="button.preConfirm"/>
                                    <tiles:put name="commandTextKey" value="button.preConfirm"/>
                                    <tiles:put name="commandHelpKey" value="button.preConfirm.help"/>
                                    <tiles:put name="viewType"       value="simpleLink"/>
                                    <tiles:put name="bundle"         value="securityBundle"/>
                                </tiles:insert>
                            </span>

                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <input type="text" name="$$confirmPushPassword" checked="true"/>&nbsp;
                           </div>
                            <div style="display: none;" class="errorDiv"></div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <script type="text/javascript">
                        if (document.getElementsByName('$$confirmPushPassword').length == 1)
                            document.getElementsByName('$$confirmPushPassword')[0].onfocus = function()
                            {
                                payInput.onFocus(this);
                            };
                    </script>
                </div>
             </c:if>
        </c:if>
    </logic:iterate>
</c:if>
<c:if test="${not empty message}">
    <div class="titleHelp">
        ${message}
    </div>
</c:if>
<c:if test="${confirmRequest.error}">
    <div class="error">
        <c:out value="${confirmRequest.errorMessage}"/>
    </div>
</c:if>

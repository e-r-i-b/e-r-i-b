

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<%--
  Шаблон для подтверждения операции паролем с чека
    confirmRequest      - запрос на подтверждение
    confirmStrategy     - стратегия подтверждения
    message             - сообщение отображаемое пользователю
    anotherStrategy     - возможно ли подтверждение другой стратегией
    confirmableObject   - подтверждаемый объект
    data                - данные отображаемые пользователю
    confirmCommandKey   - ключ для кнопки подтверждения
    useAjax             - признак открытия окна ввода одноразового пароля с помощью ajax (true - использовать ajax, false - использовать commandButton)
    ajaxUrl             - url, вызываемый через ajax
    cardNumber          - номер карты для подтверждения чековым паролем
    onKeyPressFunction  - свой обработчик события нажатия на клавишу к форме. если не задан, по умолчанию будет выставлн onEnterKey();
    showCancelButton    - показывать ли кнопку "Отменить"
    buttonType          - тип отображения блока кнопок (standart -- кнопки ниже поля ввода пароля, singleRow -- кнопки на одном уровне с полем)
--%>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="bundle" value="userprofileBundle"/>
<c:set var="hasSMS" value="${phiz:isContainStrategy(confirmStrategy, 'sms')}"/>
<c:set var="hasCard" value="${phiz:isContainStrategy(confirmStrategy,'card')}"/>
<c:set var="hasCap" value="${phiz:isContainStrategy(confirmStrategy, 'cap')}"/>
<c:set var="hasPush" value="${phiz:isContainStrategy(confirmStrategy, 'push')}"/>

<c:if test="${not empty confirmStrategy}">
    <c:set var="anotherStrategy" value="${phiz:isContainStrategy(confirmStrategy,'sms')}"/>
</c:if>

<c:set var="inactiveESMessages" value=""/>
<phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
    <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${phiz:processBBCode(inactiveES)} </div></c:set>
</phiz:messages>

<c:set var="errorMessage" value=""/>
<c:set var="informMessage" value=""/>
<c:if test="${showActionMessages}">
    <phiz:messages id="errorItem" bundle="paymentsBundle" field="field" message="error">
        <c:if test="${field == null}">
            <c:set var="errorMessage">${errorMessage}<div class = "itemDiv"><bean:write name="errorItem" filter="false" ignore="true"/></div></c:set>
        </c:if>
    </phiz:messages>
    <phiz:messages id="infMessage" bundle="paymentsBundle" field="field" message="message">
        <c:if test="${field == null}">
            <c:set var="informMessage">${informMessage}<div class = "itemDiv"><bean:write name="infMessage" filter="false" ignore="true"/></div></c:set>
        </c:if>
    </phiz:messages>

</c:if>

<c:if test="${!useAjax}">
    <c:choose>
        <c:when test="${hasPush}">
            <tiles:insert definition="confirm_not_ajax_all" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.confirmCard"/>
                <tiles:put name="commandTextKey" value="button.confirmCard"/>
                <tiles:put name="commandHelpKey" value="button.confirmCard"/>
                <tiles:put name="bundle" value="securityBundle"/>
                <tiles:put name="id" value="confirmCard"/>
            </tiles:insert>
            <c:if test="${phiz:isContainStrategy(confirmStrategy,'cap')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.confirmCap"/>
                    <tiles:put name="commandTextKey" value="button.confirmCap"/>
                    <tiles:put name="commandHelpKey" value="button.confirmCap"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="bundle" value="securityBundle"/>
                </tiles:insert>
            </c:if>
            <c:if test="${phiz:isContainStrategy(confirmStrategy,'sms')}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.confirmSMS"/>
                    <tiles:put name="commandTextKey" value="button.confirmSMS"/>
                    <tiles:put name="commandHelpKey" value="button.confirmSMS"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="bundle" value="securityBundle"/>
                </tiles:insert>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:if>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${form.fields.confirmByCard}">
    <c:choose>
        <c:when test="${confirmRequest.preConfirm}">
            <c:set var="fullData">
            <h2>
                <c:choose>
                    <c:when test="${not empty title}">
                        <c:out value="${title}"/>
                    </c:when>
                    <c:when test="${confirmableObject == 'login'}">
                        Подтверждение входа
                    </c:when>
                    <c:when test="${confirmableObject == 'securitySettings'}">
                        Подтверждение настроек безопасности
                    </c:when>
                    <c:when test="${confirmableObject == 'personalSettings'}">
                        Подтверждение персональной информации
                    </c:when>
                    <c:when test="${confirmableObject == 'viewSettings'}">
                        Подтверждение настроек интерфейса
                    </c:when>
                    <c:when test="${confirmableObject == 'activePerson'}">
                        Подтверждение операции
                    </c:when>
                    <c:when test="${confirmableObject == 'SMSTemplate'}">
                        Подтверждение создания SMS-шаблона
                    </c:when>
                    <c:when test="${confirmableObject == 'individualLimitSettings'}">
                        Подтверждение индивидуального лимита
                    </c:when>
                    <c:when test="${confirmableObject == 'claim'}">
                        Подтверждение заявки
                    </c:when>
                    <c:when test="${confirmableObject == 'recall'}">
                        Подтверждение отзыва
                    </c:when>
                    <c:when test="${confirmableObject == 'securitiesTransferClaim'
                                    || confirmableObject == 'SecurityRegistrationClaim'
                                    || confirmableObject == 'DepositorFormClaim'
                                    || confirmableObject == 'RecallDepositaryClaim'}">
                        Подтверждение поручения
                    </c:when>
                    <c:when test="${confirmableObject == 'RefuseAutopayment'}">
                        Отмена автоплатежа
                    </c:when>
                    <c:when test="${confirmableObject == 'LossPassbookApplication'}">
                        Подтверждение заявления
                    </c:when>
                    <c:when test="${confirmableObject == 'createAutoSubscription'}">
                        Подтверждение заявки
                    </c:when>
                    <c:when test="${confirmableObject == 'CardReportDeliveryClaim'}">
                        <bean:message bundle="cardInfoBundle" key="email.report.delivery.win.title"/>
                    </c:when>
                    <c:when test="${confirmableObject == 'translateToExternalAccount'}">
                        Подтверждение перевода на банковский счёт
                    </c:when>
                    <c:when test="${confirmableObject == 'translateToExternalCard'}">
                        Подтверждение перевода на карту другого банка
                    </c:when>
                    <c:when test="${confirmableObject == 'translateYandexWallet'}">
                        Подтверждение перевода на Яндекс.Деньги
                    </c:when>
                    <c:when test="${confirmableObject == 'translateOurClient'}">
                        Подтверждение перевода клиенту Сбербанка
                    </c:when>
                    <c:when test="${confirmableObject == 'RemoteConnectionUDBO'}">
                        Подключение всех возможностей Сбербанк Онлайн
                    </c:when>
                    <c:otherwise>
                        Подтверждение платежа
                    </c:otherwise>
                </c:choose>
            </h2>

        <div class="warningMessage" id="warningMessages">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="red"/>
                <tiles:put name="data">
                    <div class="messageContainer">
                        ${errorMessage}
                        ${confirmRequest.errorMessage}
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>

        <%--Сначала отображаем сообщение переданное из jsp--%>
        <div>
            ${message}
        </div>
        <c:if test="${inactiveESMessages != '' || informMessage != '' || not empty confirmRequest.messages}">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orangeBlock"/>
                <tiles:put name="data">
                    <c:set var="inactiveESMessage"><bean:write name='inactiveESMessages' filter='false'/></c:set>
                    <c:if test="${not empty inactiveESMessage}">
                        <div class="messageContainer">
                            ${inactiveESMessage}
                        </div>
                    </c:if>
                    <c:if test="${not empty informMessage}">
                        <div class="messageContainer">
                            ${informMessage}
                        </div>
                    </c:if>
                    <c:forEach items="${confirmRequest.messages}" var="infoMessage">
                        <div>
                            ${infoMessage}
                        </div>
                    </c:forEach>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </c:if>                
        <c:set var="buttons">
            <div class="buttonsArea <c:if test='${buttonType eq "singleRow"}'>float</c:if>
            <c:if test="${confirmableObject == 'translateToExternalAccount' ||
                            confirmableObject == 'translateToExternalCard' ||
                            confirmableObject == 'translateYandexWallet' ||
                            confirmableObject == 'translateOurClient'}"> confirmP2PButtons</c:if>">
                <c:set var="ajaxActionUrl">${phiz:calculateActionURL(pageContext, ajaxUrl)}</c:set>
                <script type="text/javascript">
                    function onConfirm()
                    {
                        <c:if test="${phiz:isScriptsRSAActive()}">
                            if (typeof RSAObject != "undefined")
                            {
                                new RSAObject().toHiddenParameters();
                            }
                        </c:if>
                        confirmOperation.validateConfirmPassword('button.confirm', '${ajaxActionUrl}', confirmOperation.type.card);
                    }
                </script>
                <c:choose>
                    <c:when test="${useAjax}">
                        <c:if test="${showCancelButton}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="win.close(confirmOperation.windowId);"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="onclick">onConfirm();</tiles:put>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${showCancelButton}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="win.close('confirm_card');"/>
                            </tiles:insert>
                        </c:if>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="bundle" value="securityBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="validationFunction" value="validatePasswordField();"/>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
                <div class="clear"></div>
            </div>
        </c:set>
        <div id="paymentForm" onkeypress="
        <c:choose>
            <c:when test="${onKeyPressFunction == null}">
                onEnterKey(event);
            </c:when>
            <c:otherwise>
                ${onKeyPressFunction}
            </c:otherwise>
        </c:choose>
        ">
                ${data}

            <c:choose>
                <c:when test="${buttonType eq 'singleRow'}">
                    <c:if test="${not empty cardNumber}">
                        <div class="form-row notMark">
                            <div class="paymentLabel">
                                <span class="paymentTextLabel"> Номер карты:&nbsp;</span>
                            </div>
                            <div class="paymentValue">
                                <div class="paymentInputDiv"><b><c:out value="${cardNumber}"/></b></div>
                                <html:hidden property="field(confirmCard)" value="${cardNumber}"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:if>
                    <div class="singleRowPasswordBlock">
                        <div class="singleRowPassword">
                            <span class="fieldTitle" style="color: ${textColor}" id="fieldTitle">Введите пароль № <c:out value="${confirmRequest.passwordNumber}"/> с чека <c:out value="${confirmRequest.cardNumber}"/></span>
                            <input type="text" name="$$confirmCardPassword" size="10" class="confirmInput"/>
                        </div>
                        ${buttons}
                    </div>
                </c:when>
                <c:otherwise>
                    <hr/>
                    <c:if test="${not empty cardNumber}">
                        <c:if test="${confirmableObject == 'translateToExternalAccount' ||
                            confirmableObject == 'translateToExternalCard' ||
                            confirmableObject == 'translateYandexWallet' ||
                            confirmableObject == 'translateOurClient'}"><div class="confirmP2PPasswordBlock"></c:if>
                        <div class="form-row">
                            <div class="paymentLabel">
                                <span class="paymentTextLabel"> Номер карты:&nbsp;</span>
                            </div>
                            <div class="paymentValue">
                                <div class="paymentInputDiv"><b><c:out value="${cardNumber}"/></b></div>
                                <html:hidden property="field(confirmCard)" value="${cardNumber}"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:if>
                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">
                            <c:set var="textColor" value="black"/>
                            <c:if test="${confirmRequest.errorFieldPassword}">
                                <c:set var="textColor" value="red"/>
                            </c:if>
                            <c:if test="${phiz:isInstance(confirmRequest, 'com.rssl.phizic.auth.modes.PasswordCardConfirmRequest')}">
                                <span class="fieldTitle" style="color: ${textColor}" id="fieldTitle">
                                    Введите пароль № <span class="bold"><c:out value="${confirmRequest.passwordNumber}"/></span> с чека <c:out value="${confirmRequest.cardNumber}"/>:
                                </span>
                            </c:if>
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="confirm_hint">
                            </div>
                            <input type="text" name="$$confirmCardPassword" size="10" class="confirmInput"/>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                    <c:if test="${confirmableObject == 'translateToExternalAccount' ||
                            confirmableObject == 'translateToExternalCard' ||
                            confirmableObject == 'translateYandexWallet' ||
                            confirmableObject == 'translateOurClient'}"></div></c:if>
                </c:otherwise>
            </c:choose>
            <div class="clear"></div>
        </div>
        <c:if test="${buttonType eq 'standart'}">${buttons}</c:if>
    </c:set>
        </c:when>
        <c:when test="${empty form.fields.confirmCardId}">
            <c:set var="fullData">
                <c:set var="thisActionUrl" value="${ajaxUrl}"/>
                <c:set var="cards" value="${form.fields.confirmCards}"/>
                <c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
                <div class="oneTimePasswordWindow">
                    <h2>Подтверждение операции</h2>
                </div>
                <c:if test="${not empty cards}">
                    <div style="clear: both; padding-top:14px; padding-bottom:14px">
                        Пожалуйста, выберите карту для подтверждения.
                    </div>
                </c:if>
                <c:if test="${not empty form.fields.cardConfirmError || errorMessage != ''}">
                    <tiles:insert definition="errorBlock" flush="false">
                        <tiles:put name="regionSelector" value="errors"/>
                        <tiles:put name="isDisplayed" value="true"/>
                        <tiles:put name="data">
                            ${errorMessage}
                            ${form.fields.cardConfirmError}
                        </tiles:put>
                    </tiles:insert>
                </c:if>
                <c:if test="${inactiveESMessages != '' || informMessage != ''}">
                    <tiles:insert definition="roundBorderLight" flush="false">
                        <tiles:put name="color" value="orangeBlock"/>
                        <tiles:put name="data">
                            <div class="messageContainer">
                                <bean:write name='inactiveESMessages' filter='false'/>
                            </div>
                            <div class="messageContainer">
                                ${informMessage}
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </c:if>

                <div>
                <c:set var="cardLength" value="${phiz:size(cards)}"/>
                <c:choose>
                    <c:when test="${cardLength>0}">
                        <c:set var="i" value="1"/>
                        <c:forEach var="card" items="${cards}">
                            <c:set var="description" value="${card.description}"/>
                            <c:set var="cardNumber">${phiz:getCutCardNumber(card.number)}</c:set>

                            <c:choose>
                                <c:when test="${not empty card.card.cardLevel and card.card.cardLevel == 'MQ'}">
                                    <c:set var="imgCode" value="mq" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
                                </c:otherwise>
                            </c:choose>

                            </br>
                            <div class="pruductImg">
                                <img src="${imagePath}/cards_type/icon_cards_${imgCode}64.gif" alt="${alt}" onerror="onImgError(this)" border="0"/>
                            </div>
                            <div class="productTitle" style="padding-top:24px">
                                <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext, thisActionUrl)}"/>

                                <c:choose>
                                    <c:when test="${empty card.id}">
                                        <c:set var="reqParams" value="?field(confirmByPan)=true"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="reqParams" value="?field(confirmCardId)=${card.id}"/>
                                    </c:otherwise>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${useAjax}">
                                        <c:set var="onclick" value="confirmOperation.openConfirmWindow('confirmBySelectedCard', '${actionUrl}${reqParams}');"/>
                                        <div class="buttonGrey" style="text-decoration:underline" onclick="${onclick}">
                                            <b><c:out value="${cardNumber}"/></b>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="url" value="${actionUrl}${reqParams}"/>
                                        <div class="buttonGrey" style="text-decoration:underline" onclick="changeFormAction('${url}'); createCommandButton('confirmBySelectedCard','').click('', false)">
                                            <b><c:out value="${cardNumber}"/></b>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="clear"></div>
                            <c:if test="${cardLength>i}">
                                <c:set var="i" value="${i+1}"/>
                                <div class="productDivider"></div>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        У вас нет карт, по которым было бы возможно получить чековый пароль.
                    </c:otherwise>
                </c:choose>
                </div>
                <div class="buttonsArea" style="text-align:center">
                    <c:choose>
                        <c:when test="${useAjax}">
                            <c:set var="ajaxActionUrl">${phiz:calculateActionURL(pageContext, ajaxUrl)}</c:set>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="win.close(confirmOperation.windowId);"/>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="win.close('confirm_card');"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                    <div class="clear"></div>
                </div>
            </c:set>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${useAjax}">
            ${fullData}
        </c:when>
        <c:otherwise>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="confirm_card"/>
                <tiles:put name="data">${fullData}</tiles:put>
            </tiles:insert>

            <script type="text/javascript">
                function validatePasswordField()
                {
                    var pass = getElement('$$confirmCardPassword');
                    if (pass.value == '' || pass.value == null)
                    {
                        addError("Введите одноразовый пароль с чека.", 'warningMessages');
                        var errorDiv = document.getElementById('warningMessages');
                        var errorField = document.getElementById('fieldTitle');
                        errorField.style.color = 'red';
                        hideOrShow(errorDiv, false);
                        return false;
                    }
                    return true;
                }
                function openCardWindow()
                {
                    win.open('confirm_card');
                    if (document.getElementsByName('$$confirmCardPassword').length == 1)
                        document.getElementsByName('$$confirmCardPassword')[0].focus();

                    var confirmHeight = $(document.getElementById('confirm_cardWin')).height();
                    var browserWinHeight = screen.height;
                    if (confirmHeight > browserWinHeight)
                    {
                        window.scrollTo(0, document.body.scrollHeight);
                    }
                    ;
                    var errorDiv = document.getElementById('warningMessages');
                    var hide = true;
                <c:if test="${confirmRequest.error}">
                    hide = false;
                </c:if>
                    hideOrShow(errorDiv, hide);
                }
                doOnLoad(openCardWindow);
            </script>
        </c:otherwise>
    </c:choose>
</c:if>
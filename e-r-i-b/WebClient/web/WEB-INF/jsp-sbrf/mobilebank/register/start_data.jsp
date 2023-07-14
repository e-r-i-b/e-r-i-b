<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<%--@elvariable id="globalUrl" type="java.lang.String"--%>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<%--@elvariable id="skinUrl" type="java.lang.String"--%>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.StartRegistrationForm"--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<c:set var="nextButton">
    <c:choose>
        <c:when test="${not empty form.returnURL}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.register.next"/>
                <tiles:put name="commandHelpKey" value="button.register.next"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="onclick">goTo('${form.returnURL}');</tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="skip"/>
                <tiles:put name="commandTextKey" value="button.register.next"/>
                <tiles:put name="commandHelpKey" value="button.register.next"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:set>

<c:set var="nextLink">
    <c:choose>
        <c:when test="${not empty form.returnURL}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.register.skip"/>
                <tiles:put name="commandHelpKey" value="button.register.skip"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
                <tiles:put name="onclick">goTo('${form.returnURL}');</tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="skip"/>
                <tiles:put name="commandTextKey" value="button.register.skip"/>
                <tiles:put name="commandHelpKey" value="button.register.skip"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <tiles:put name="viewType" value="blueGrayLink"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:set>

<tiles:insert definition="roundBorder" flush="false">
    <c:if test="${param.afterLogin == 'true'}">
        <tiles:put name="color" value="greenTop"/>
    </c:if>
    <tiles:put name="title">
        <div class="align-left">Подключение услуги "Мобильный Банк"</div>
    </tiles:put>
    <tiles:put name="data">

        <c:choose>
            <%-- A. С предыдущей попытки ещё не прошло достаточно времени --%>
            <c:when test="${not form.canRepeatClaim}">
                <%-- Заголовок --%>
                <tiles:insert definition="formHeader" flush="false">
                    <tiles:put name="description">
                        <h3>
                            Заявка на подключение услуги "Мобильный банк"
                            по карте <b>${phiz:getCutCardNumber(form.previousClaim.cardNumber)}</b>
                            на телефон <b>${phiz:getCutPhoneNumber(form.previousClaim.phoneNumber)}</b> принята.
                            Сообщение о подключении будет отправлено в течение суток.
                            Получение SMS-паролей будет доступно только после подключения услуги "Мобильный банк".
                        </h3>
                    </tiles:put>
                </tiles:insert>

                <%-- Кнопки --%>
                <div class="buttonsArea">${nextButton}</div>
            </c:when>

            <%-- B. Нет доступных карт --%>
            <c:when test="${not form.cardsAvailable}">
                <%-- Заголовок --%>
                <tiles:insert definition="formHeader" flush="false">
                    <tiles:put name="description">
                        <h3>
                            <bean:message bundle="commonBundle" key="text.aboutLogSMSInforming"/>
                            <a class="orangeText" target="_blank"
                               href="http://sberbank.ru/ru/person/dist_services/inner_mbank/"
                               title="Условия предоставления услуги"><span>Мобильный банк</span></a>.
                            Подключите услугу "Мобильный банк" (полный или экономный пакет)
                            в любом отделении Сбербанка.
                        </h3>
                    </tiles:put>
                </tiles:insert>

                <%-- Кнопки --%>
                <div class="buttonsArea">${nextButton}</div>
            </c:when>

            <%-- C. Мобильный телефон не доступен --%>
            <c:when test="${not form.phoneAvailable}">
                <%-- Заголовок --%>
                <tiles:insert definition="formHeader" flush="false">
                    <tiles:put name="description">
                        <h3>
                            <bean:message bundle="commonBundle" key="text.aboutLogSMSInforming"/>
                            <a target="_blank"
                               href="http://sberbank.ru/ru/person/dist_services/inner_mbank/"
                               title="Условия предоставления услуги">Мобильный банк</a>.
                            Подключите услугу "Мобильный банк" (полный или экономный пакет):
                                <ul class="list-bank-registration">
                                    <li>через банкоматы и терминалы Сбербанка;</li>
                                    <li>в отделении Сбербанка.</li>
                                </ul>
                        </h3>
                    </tiles:put>
                </tiles:insert>

                <%-- Кнопки --%>
                <div class="buttonsArea">${nextButton}</div>
            </c:when>
            
            <%-- D. Для подключения всё есть, выводим список тариф --%>
            <c:otherwise>
                <%-- Заголовок --%>
                <tiles:insert definition="formHeader" flush="false">
                    <tiles:put name="description">
                        <h3>
                            <bean:message bundle="commonBundle" key="text.aboutLogSMSInforming.alt"/>
                            <a target="_blank"
                               href="http://sberbank.ru/ru/person/dist_services/inner_mbank/"
                               title="Условия предоставления услуги">Мобильный банк</a>.
                            Более подробно с описанием тарифов Вы можете ознакомиться по
                            <a target="_blank"
                               href="http://www.sberbank.ru/common/img/uploaded/files/pdf/Tarify_na_predostavlenie_uslugi_MB.pdf"
                               title="Описание тарифов">ссылке</a>.<br/>
                            <br/>
                            Пожалуйста, выберите пакет обслуживания.
                        </h3>
                    </tiles:put>
                </tiles:insert>

                <%-- Линия жизни --%>
                <div id="paymentStripe" <c:if test="${param.afterLogin == 'true'}">class="login-register-stripe"</c:if>>
                   <tiles:insert definition="stripe" flush="false">
                       <tiles:put name="name" value="выбор пакета"/>
                       <tiles:put name="current" value="true"/>
                   </tiles:insert>
                   <tiles:insert definition="stripe" flush="false">
                       <tiles:put name="name" value="заполнение заявки"/>
                   </tiles:insert>
                   <tiles:insert definition="stripe" flush="false">
                       <tiles:put name="name" value="подтверждение"/>
                   </tiles:insert>
                   <tiles:insert definition="stripe" flush="false">
                       <tiles:put name="name" value="регистрация заявки"/>
                   </tiles:insert>
                   <div class="clear"></div>
                </div>

                <%-- Экономный тариф --%>
                <c:url var="url" value="${form.nextActionPath}">
                    <c:param name="tariff" value="ECONOM"/>
                    <c:if test="${not empty form.returnURL}">
                        <c:param name="returnURL" value="${form.returnURL}"/>
                    </c:if>
                </c:url>
                <div class="tariff-econom grayLine">
                    <tiles:insert page="tariff.jsp" flush="false">
                        <tiles:put name="title">Экономный пакет</tiles:put>
                        <tiles:put name="description">
                            Тариф является бесплатным, ежемесячная комиссия отсутствует,
                            взимается комиссия за запрос баланса и выписки.<br/>
                            Не включает SMS-оповещения об операциях по карте.
                        </tiles:put>
                        <tiles:put name="url">${url}</tiles:put>
                        <tiles:put name="tariffIcon">${image}/icon_mb_econom.jpg</tiles:put>
                        <tiles:put name="showLine" value="true"/>
                    </tiles:insert>
                </div>

                <br/>

                <%-- Полный тариф --%>
                <c:url var="url" value="${form.nextActionPath}">
                    <c:param name="tariff" value="FULL"/>
                    <c:if test="${not empty form.returnURL}">
                        <c:param name="returnURL" value="${form.returnURL}"/>
                    </c:if>
                </c:url>
                <div class="tariff-full grayLine">
                    <tiles:insert page="tariff.jsp" flush="false">
                        <tiles:put name="title">Полный пакет</tiles:put>
                        <tiles:put name="description">
                            Ежемесячная комиссия составляет от 0 до 60 руб. в зависимости от типа карты.
                            Услуга "Мобильный Банк" является бесплатной для всех типов премиальных дебетовых карт
                            (Visa Gold/Platinum/Infinite; MasterCard Gold/Platinum), а также для всех типов кредитных карт Сбербанка.
                            Включает все типы SMS-оповещений, в том числе уведомления по операциям по карте.
                        </tiles:put>
                        <tiles:put name="url">${url}</tiles:put>
                        <tiles:put name="tariffIcon">${image}/icon_mb_full.jpg</tiles:put>
                    </tiles:insert>
                </div>
                <%-- Кнопки --%>
                <div class="buttonsArea">${nextLink}</div>
            </c:otherwise>
        </c:choose>

    </tiles:put>
</tiles:insert>

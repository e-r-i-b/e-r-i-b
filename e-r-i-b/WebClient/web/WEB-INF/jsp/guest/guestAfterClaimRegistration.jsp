<%--
  User: kichinova
  Date: 27.07.15
  Description: Шаблон для страницы, отображающейся после успешной подачи экспресс-заявки
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--topMessage - место для шаблона roundBorderLight--%>
<%--contentHeader - заголовок контента--%>
<%--content - контент--%>
<%--documentID - id документа--%>

<tiles:importAttribute/>
<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest"/>
<c:set var="headerOfRegistration" value="${showRegBtn ? 'Зарегистрируйтесь' : 'Теперь нужно зарегистрироваться'}"/>
<c:if test="${showRegBtn}">
    <c:set var="redirect" value="${phiz:getLinkOnSelfRegistrationInCSA()}"/>
</c:if>


<div id="workspace" class="fullHeight" style="float: none; width: 100%;">

<%--Всплывашка "Правила по составлению логина и пароля"--%>
<div id="fullRulesWin" class="fullRules window farAway">
    <div class="crossButton"><img src="${imagesPath}/buttonCross.png" class="crossButtonAction"></div>
    <div class="b-rules" id="AuthRules">
        <h3 class="rules_title">Правила составления логина и пароля</h3>

        <div class="rules_cols">
            <div class="rules_col floatLeft">
                <h4 class="rules_col-title">Логин</h4>
                <ul class="rules_list">
                    <li class="rules_item"><span class="wrapperLi">Длина от 8 до 30 символов.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Логин должен содержать как минимум одну цифру и как минимум одну букву.
                        </span></li>
                    <li class="rules_item"><span class="wrapperLi">Буквы должны быть только из латинского алфавита.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Не может состоять из 10 цифр.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Не должен содержать более 3-х одинаковых символов подряд.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Может содержать элементы пунктуации из списка – « – @ _ - .»</span></li>
                    <li class="rules_item"><span class="wrapperLi">Не чувствителен к регистру.</span></li>
                </ul>
            </div>
            <div class="rules_col floatRight">
                <h4 class="rules_col-title">Пароль</h4>
                <ul class="rules_list">
                    <li class="rules_item"><span class="wrapperLi">Длина не менее 8 символов.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Пароль должен содержать как минимум одну цифру и как минимум одну
                            букву.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Не должен содержать более 3-х одинаковых символов подряд, и более
                            3-х символов, расположенных рядом в одном ряду клавиатуры.
                        </span></li>
                    <li class="rules_item"><span class="wrapperLi">Должен отличаться от логина.</span></li>
                    <li class="rules_item"><span class="wrapperLi">Может содержать элементы пунктуации из списка « – ! @ # $ % ^ & * ( ) _ - + : ; , . »</span></li>
                    <li class="rules_item"><span class="wrapperLi">Не должен повторять старые пароли за последние 3 месяца.
                        </span></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- // b-rules -->
</div>
<%--Конец Всплывашка "Правила по составлению логина и пароля"--%>

<%--Всплывашка "Непредвиденные ошибки"--%>
<div id="popupErrorWin" class="popupError window farAway">
    <div class="crossButton"><img src="${imagesPath}/buttonCross.png" class="crossButtonAction"></div>
    <div>
        <div class="textArea">
            <div class="headerErrorMessage">Ошибка</div>
            <div class="errorMessage">
                <ul id="popupErrorDescription"></ul>
            </div>
        </div>
    </div>
    <!-- // b-rules -->
</div>
<%--Конец Всплывашка "Непредвиденные ошибки"--%>

<div class="mainContent">
    <div class="header">
        <a class="logoSB" href="http://www.sberbank.ru/">
            <img src="${imagesPath}/logoSB.png" height="72" width="289"/>
        </a>

        <div class="headPhones">
            <div class="federal">
                <span class="phoneIco"></span>
                8 (800) 555 55 50
            </div>
            <div class="regional">+7 (495) 500-55-50</div>
        </div>
    </div>

    <%--Блоки для клиента, у которого есть учётка в СБОЛ--%>
    <div class="topPanelWhitMessage">
        ${topMessage}
    </div>
    <c:if test="${!showRegistration}">
        <div class="fullWidth registrationBanner">
            <div class="fon1">
                <div class="base">
                    <div class="bannerRegistration">
                        <p class="bannerHeader">${headerOfRegistration}</p>

                        <div class="bannerText bannerTextDesc">
                            <div class="floatLeft bannerLeftColumn">
                                <c:choose>
                                    <c:when test="${showRegBtn}">
                                        <p class="textAlignLeft headerConnectSBOL">Подключитесь к Сбербанк Онлайн прямо сейчас</p>
                                        <a href="${redirect}">
                                            <div class="registrationButton marginTop20">Зарегистрироваться</div>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="headerLoginAndPassword fontFamilyTrebuchet fontSize19 marginBottom20">Придумайте логин и пароль</p>
                                        <input type="hidden" value="${documentID}" id="documentID"/>
                                        <input type="hidden" value="${documentType}" id="documentType"/>

                                        <div class="pairFieldError relative">
                                            <div class="error"></div>
                                            <label class="placeholderRegistration">Логин</label>
                                            <input class="inputTextField" type="text" id="loginField" name="field(login)"/>
                                        </div>
                                        <div class="pairFieldError relative">
                                            <div class="error"></div>
                                            <label class="placeholderRegistration">Введите пароль</label>
                                            <input class="inputTextField" type="password" id="passwordField" name="field(password)"/>

                                        </div>
                                        <div class="pairFieldError relative">
                                            <div class="error"></div>
                                            <label class="placeholderRegistration">Повторите пароль</label>
                                            <input class="inputTextField" type="password" id="confirmPasswordField" name="field(confirmPassword)"/>

                                        </div>
                                        <div id="blockCaptcha">
                                            <div class="pairFieldError relative">
                                                <img id="idCaptchaImg" class="captchaImgRegistration" height="35" width="105"/>

                                                <p id="updateCaptchaButtonRegistration">Обновить код</p>

                                                <div class="error"></div>
                                                <input class="customPlaceholder inputTextField" type="text" title="Введите код с картинки" name="field(captchaCode)" id="captchaCodeField"
                                                       maxlength="40"/>
                                            </div>
                                        </div>
                                        <div class="registrationButton" id="buttonRegistrationNewClient">Зарегистрироваться</div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="floatRight items relative">
                                <c:choose>
                                    <c:when test="${showRegBtn}">
                                        <p class="additionText textAlignLeft">У вас уже есть дебетовая карта Сбербанка. Быстрая регистрация в Сбербанк Онлайн расширит ваши возможности и даст
                                            неоспоримые преимущества</p>
                                        <ul class="itemUL">
                                            <li class="itemLI">
                                                <span class="itemHeader">Оплачивайте коммунальные услуги в любой момент</span>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">Открывайте вклады на выгодных условиях</span>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">Управляйте структурой своих расходов</span>
                                            </li>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <ul class="itemUL" id="IdItemUL">
                                            <li class="itemLI">
                                                <span class="itemHeader">Заполняем</span>

                                                <p class="itemText">Быстрое оформление заявок</p>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">Сохраняем</span>

                                                <p class="itemText">Надёжное хранение заявок в Сбербанк Онлайн</p>
                                            </li>
                                            <li class="itemLI">
                                                <span class="itemHeader">Проверяем</span>

                                                <p class="itemText">Лёгкая проверка состояний заявок</p>
                                            </li>
                                        </ul>
                                        <div class="b-reg-helper" id="Id-b-reg-helper">
                                            <h3 class="reg-helper_head">Краткие правила</h3>

                                            <div class="reg-helper_inner">
                                                <div class="reg-helper_item">
                                                    <h4 class="reg-helper_title">sberbank21</h4>

                                                    <p class="reg-helper_description">Логин и пароль<br/> должны содержать буквы<br/> и цифры.
                                                        Минимум 8 символов.</p>

                                                    <h4 class="reg-helper_title">A=a</h4>

                                                    <p class="reg-helper_description">Логин и пароль<br/> не чувствительны к регистру</p>

                                                </div>
                                                <div class="reg-helper_item floatRight">
                                                    <img class="reg-helper_pic" src="${imagesPath}/qwer.png" alt="" width="114" height="44"/>

                                                    <p class="reg-helper_description">Нельзя использовать более<br/> 3-х символов подряд в одном<br/> ряду
                                                        клавиатуры.</p>
                                                    <span id="buttonFullRules">Правила полностью</span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="clear"></div>

                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="wrapper">
        <div class="ears-content">
            <div class="formContent">
                <div class="formContentMargin">
                    <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
                        <tiles:put name="bundle" type="string" value="messagesBundle"/>
                    </tiles:insert>
                    <p class="contentHeader">${contentHeader}</p>
                    ${content}
                </div>
            </div>
        </div>
        <div class="base">
            <div class="main_row">
                <div class="content_row"></div>
            </div>
            <!--ears-s/ears-b-->
            <div class="ears ears-b"></div>
        </div>
    </div>
</div>

<div class="clear"></div>
<div class="footer">
    <div class="references">
        <a href="http://www.sberbank.ru/moscow/ru/person/contributions/max_stavka/">Информация о процентных ставках по договорам банковского вклада с физическими
            лицами</a><br/>
        <a href="http://www.sberbank.ru/moscow/ru/investor_relations/disclosure/shareholders/">Информация о лицах, под контролем которых находится банк</a>

        <div class="copyright">
            &copy 1997 — 2015 Сбербанк России, Россия, Москва, 117997, ул. Вавилова, д.19, тел. +7(495) 500
            5550 <br/>
            8 800 555 5550. Генеральная лицензия на осуществление банковских операций от 8 августа 2012
            года. <br/>
            Регистрационный номер - 1481.
            <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>Обратная связь</span>.</a>
        </div>
    </div>
</div>
<div id="loading" style="left:-3300px;">
    <div id="loadingImg"><img src="${globalUrl}/commonSkin/images/ajax-loader64.gif"/></div>
    <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
</div>
</div>
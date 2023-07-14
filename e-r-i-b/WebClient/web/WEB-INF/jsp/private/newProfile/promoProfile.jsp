<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${globalUrl}/commonSkin/images/profile"/>
<c:set var="uploadAvatarAccess" value="${phiz:impliesOperation('UploadAvatarOperation', '')}"/>
<c:set var="viewPaymentsBasketAccess" value="${phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment')}"/>
<c:set var="addressBookAccess" value="${phiz:impliesOperation('ViewAddressBookOperation', 'AddressBook')}"/>
<c:set var="promoCodeAccess" value="${phiz:impliesService('ClientPromoCode') or phiz:impliesService('CreatePromoAccountOpeningClaimService') or phiz:impliesService('ShowClientPromoCodeList')}"/>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/tutorial.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var tutorial = new Tutorial({
            showTrigger: '.showTutorial',
            tutorialElem: '#SbolTutorial',
            removeTrigger: '.closePromo'
            <c:if test="${phiz:isPromoState('SHOWING')}">
                , showOnInit: true
            </c:if>
        });
        callAjaxActionMethod('${url}', 'setPromoMinimized');
    });

    function showMinimizedPromo()
    {
        $('.promo-minimized').css('display', '');
    }

    function showAvatarLoadButton()
    {
        <c:if test="${uploadAvatarAccess}">
            <c:choose>
                <c:when test="${form.hasAvatar}">
                    $('.grayProfileButton').show();
                </c:when>
                <c:otherwise>
                    $('.whiteProfileButton').show();
                </c:otherwise>
            </c:choose>
        </c:if>
    }

    function hideAvatarLoadButton()
        {
            <c:if test="${uploadAvatarAccess}">
                <c:choose>
                    <c:when test="${form.hasAvatar}">
                        $('.grayProfileButton').css('display', '');
                    </c:when>
                    <c:otherwise>
                        $('.whiteProfileButton').css('display', '');
                    </c:otherwise>
                </c:choose>
            </c:if>
        }
</script>

<div id="SbolTutorial" class="b-tutorial" style="display: none;">
    <div class="tutorial_inner">
        <div class="tutorial_left-bg"></div>
        <div class="tutorial_right-bg"></div>
        <div class="tutorial_steps">

            <div class="tutorial_step tutorial_step-hello">
                <img class="tutorial_step-image" src="${imagePath}/step_hello.png"/>
                <div class="tutorial_bg"></div>
            </div>

            <div class="tutorial_step tutorial_step-1">
                <img class="tutorial_step-image" src="${imagePath}/step1.png"/>
                <div class="tutorial_step-content">
                    <h1>Основное меню <br>вашего профиля</h1>
                    <p>В основном меню Вашего профиля <br>
                        <c:choose>
                            <c:when test="${viewPaymentsBasketAccess or addressBookAccess}">
                                появились новые разделы:
                            </c:when>
                            <c:otherwise>
                                появился новый раздел:
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <ul>
                        <li>— Личная информация</li>
                        <c:if test="${addressBookAccess}">
                            <li>— Адресная книга</li>
                        </c:if>
                        <c:if test="${viewPaymentsBasketAccess}">
                            <li>— Автопоиск счетов к оплате</li>
                        </c:if>
                    </ul>
                </div>
                <div class="tutorial_bg"></div>
            </div>

            <c:if test="${uploadAvatarAccess}">
                <div class="tutorial_step tutorial_step-2">
                    <img class="tutorial_step-image" src="${imagePath}/step2.png"/>
                    <div class="tutorial_step-content">
                        <div class="tutorial_about">Личная информация</div>
                        <h1>Ваш лучший портрет</h1>
                        <p>Загрузите Ваше фото, и&nbsp;тогда Ваши <br>знакомые легко найдут Вас в&nbsp;своих <br>адресных книгах.
                        </p>
                    </div>
                    <div class="tutorial_bg"></div>
                </div>
            </c:if>

            <div class="tutorial_step tutorial_step-3">
                <c:choose>
                    <c:when test="${not empty form.personDocumentList}">
                        <img class="tutorial_step-image" src="${imagePath}/step3.png"/>
                    </c:when>
                    <c:otherwise>
                        <img class="tutorial_step-image" src="${imagePath}/step3_1.png"/>
                    </c:otherwise>
                </c:choose>

                <div class="tutorial_step-content">
                    <div class="tutorial_about">Личная информация</div>
                    <h1>Ваши документы и идентификаторы </h1>
                    <p>Вам больше не&nbsp;придется каждый раз заново набирать номер паспорта, мучительно вспоминать ИНН или&nbsp;искать
                       страховое свидетельство — «Сбербанк Онлайн» будет надежно хранить ваши данные и&nbsp;подсказывать в&nbsp;нужный
                       момент. Оплата государственных услуг станет для Вас простой и&nbsp;удобной.</p>
                    <h2>Заполните карточки с данными, не откладывайте на потом!</h2></div>
                <div class="tutorial_bg"></div>
            </div>

            <c:if test="${addressBookAccess}">
                <div class="tutorial_step tutorial_step-4">
                    <img class="tutorial_step-image" src="${imagePath}/step4.png">
                    <div class="tutorial_step-content">
                        <h1>Адресная книга</h1>
                        <p>Теперь у&nbsp;вас есть быстрый доступ <br>ко&nbsp;всем вашим контактам – <br>переводите деньги друзьям <br>в&nbsp;считанные секунды!</p>
                    </div>
                    <div class="tutorial_bg"></div>
                </div>
            </c:if>

            <c:if test="${viewPaymentsBasketAccess}">
                <div class="tutorial_step tutorial_step-5 <c:if test='${promoCodeAccess}'>havePromoCode</c:if>">  <%--Проверяем доступность промо-кодов--%>
                    <c:if test="${addressBookAccess}">
                        <div class="tutorial_bg_top_filler"></div>
                    </c:if>
                    <img class="tutorial_step-image" src="${imagePath}/step5.png"/>
                    <c:set var="textClassName" value="tutorial_step-content"/>
                    <c:if test="${addressBookAccess}">
                        <c:set var="textClassName" value="tutorial_step-content tutorial_step-5_textIndent"/>
                    </c:if>
                    <div class="${textClassName}">
                        <h1>Поиск неоплаченных счетов</h1>
                        <p>«Сбербанк Онлайн» научился находить неоплаченные счета и&nbsp;тактично напоминать об&nbsp;этом. Поэтому
                           вы больше не&nbsp;забудете вовремя оплатить коммунальные услуги, налоги или счета за&nbsp;квартиру и&nbsp;машину.
                           Обязательно загляните на&nbsp;эту страницу.</p>
                        <h2>Управляйте личными финансами с&nbsp;удовольствием!</h2>
                        <a class="tutorial_link-btn" href="#" onclick="showMinimizedPromo()"><span>Перейти к личной информации</span><i></i></a>
                    </div>
                    <div class="tutorial_bg"></div>
                </div>
            </c:if>

        </div>
    </div>
    <div class="tutorial_cursor">
        <div class="tutorial_cursor-inner">Дальше</div>
    </div>
    <a class="tutorial_exit" title="Закрыть" onclick="showMinimizedPromo()"></a>
</div><!-- // b-tutorial -->

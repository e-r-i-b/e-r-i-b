<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest/promo"/>

<html:form action="${actionUrl}">
    <tiles:insert definition="${definitionName}">
        <c:choose>
            <c:when test="${pageType == 'More_SBOL_UDBO'}">
                <tiles:put name="headerGroup" value="true"/>
                <tiles:put name="mainmenu" value="moreSbol"/>
                <tiles:put name="enabledLink" value="false"/>
            </c:when>
            <c:when test="${pageType == 'More_SBOL_LOGIN'}">
                <tiles:put name="pageTitle" type="string" value="Сбербанк Онлайн"/>
                <tiles:put name="headerGroup" value="true"/>
                <tiles:put name="needHelp" value="true"/>
            </c:when>
            <c:when test="${pageType == 'guestPromo'}">
                <c:set var="guestEntryLinkOnRegistration" value="${phiz:getLinkOnSelfRegistrationInCSA()}"/>
                <tiles:put name="mainMenuType" value="guestMainMenu"/>
                <tiles:put name="mainmenu" value="Abilities"/>
            </c:when>
        </c:choose>

        <tiles:put name="data" type="string">
            <div class="b-promo-page marginTop20">

                <h1 class="b-page-title">Сбербанк Онлайн&nbsp;&mdash; ваше личное банковское пространство!</h1>
                <p class="b-page-descr">Управляйте денежными средствами без обращения в&nbsp;отделение банка через Сбербанк Онлайн при помощи мобильных приложений, СМС-банкинга, банкоматов и&nbsp;социальных сетей.</p>

                <div class="b-section b-benefits float" id="SectionBenefits">
                    <h2 class="section_title">Сбербанк Онлайн всегда под рукой</h2>
                    <a class="section_anc" href="#SectionRegistration" onclick="return doSmoothScroll(this);">Как подключиться</a>
                    <i class="benefits_promo"></i>

                    <div class="section_inner">
                        <ul class="section_list">
                            <li class="section_item">
                                В&nbsp;вашем <br/><a target="_blank" href="http://www.sberbank.ru/moscow/ru/person/dist_services/#sectionMobile">смартфоне или&nbsp;планшете</a>
                            </li>
                            <li class="section_item">
                                В&nbsp;телефонах с&nbsp;управлением счетами с&nbsp;помощью простых <a target="_blank"                                                                                                                           href="http://www.sberbank.ru/moscow/ru/person/dist_services/#sectionBank">SMS-сообщений</a>
                            </li>
                            <li class="section_item">
                                В&nbsp;тысячах <br/><a target="_blank" href="http://www.sberbank.ru/moscow/ru/person/dist_services/#sectionTerminals">банкоматов
                                и&nbsp;терминалов</a> <br/>по&nbsp;всему городу
                            </li>
                            <li class="section_item">
                                В&nbsp;ваших любимых социальных сетях в&nbsp;
                                <a target="_blank" href="http://ok.ru/bankdruzey">Одноклассниках</a> и&nbsp;
                                <a target="_blank" href="https://vk.com/bankdruzey">Вконтакте</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- // b-section -->
                <div class="b-section b-operations" id="SectionOperations">
                    <h3 class="section_title">Где&nbsp;бы вы&nbsp;не&nbsp;находились, сможете</h3>

                    <div class="b-group">
                        <h4 class="group_title"><span>Совершать финансовые операции</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic1.png" alt=""/>
                                <span class="group_text">Переводы между своими счетами, картами, вкладами</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic2.png" alt=""/>
                                <span class="group_text">Пополнение электронных кошельков</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic3.png" alt=""/>
                                <span class="group_text">Платежи по&nbsp;кредитам и&nbsp;кредитным картам</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g1-pic4.png" alt=""/>
                                <span class="group_text">Платежи по&nbsp;кредитам и&nbsp;кредитным картам</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                    <div class="b-group">
                        <h4 class="group_title"><span>Всегда быть в&nbsp;курсе своих финансов</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g2-pic1.png" alt=""/>
                                <span class="group_text">Постоянный доступ к&nbsp;выписке по&nbsp;всем счетам и&nbsp;истории операций</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g2-pic2.png" alt=""/>
                                <span class="group_text">Информация о&nbsp;личном бонусном счете &laquo;Спасибо от&nbsp;Сбербанка&raquo;</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                    <div class="b-group">
                        <h4 class="group_title"><span>Экономить свое время</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g3-pic1.png" alt=""/>
                                <span class="group_text">Оплата услуг в&nbsp;один клик с&nbsp;помощью созданных вами шаблонов</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g3-pic2.png" alt=""/>
                                <span class="group_text">Автоматические регулярные платежи за&nbsp;квартиру, интернет, сотовую связь и&nbsp;другие услуги</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                    <div class="b-group">
                        <h4 class="group_title"><span>Зарабатывать и&nbsp;сохранять</span></h4>
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g4-pic1.png" alt=""/>
                                <span class="group_text">Вклады с&nbsp;повышенной процентной ставкой</span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g4-pic2.png" alt=""/>
                                <span class="group_text">Металлические счета</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                </div>
                <!-- // b-section -->

                <div class="b-section b-start" id="SectionRegistration">
                    <h3 class="section_title">Подключиться с&nbsp;дебетовой <br/>картой Сбербанка</h3>

                    <div class="section_descr">Зарегистрируйтесь в&nbsp;два <br/>шага в&nbsp;Сбербанк Онлайн</div>

                    <c:choose>
                        <c:when test="${guestEntryLinkOnRegistration != null}">
                            <div class="b-btn">
                                <a class="btn_inner" target="_blank" href="${guestEntryLinkOnRegistration}">Зарегистрироваться</a>
                                <i class="btn_bg"></i>
                            </div>
                            <!-- // b-btn -->
                        </c:when>
                        <c:otherwise>
                            <div class="promoUDBO">
                                <div class="buttonAreaPromo">
                                    <div class="b-btn">
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.connect"/>
                                            <tiles:put name="commandTextKey" value="button.connect"/>
                                            <tiles:put name="commandHelpKey" value="button.connect.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="viewType" value="orangePromo"/>
                                        </tiles:insert>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.notNow"/>
                                            <tiles:put name="commandTextKey" value="button.notNow"/>
                                            <tiles:put name="commandHelpKey" value="button.notNow.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="viewType" value="lightGrayPromo"/>
                                        </tiles:insert>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="clear"></div>

                    <i class="start_promo"></i>
                </div>
                <!-- // b-section -->

                <div class="b-section b-alternate" id="SectionAlternate">
                    <h3 class="section_title">У меня нет дебетовой карты Сбербанка :’(</h3>

                    <div class="b-group">
                        <ul class="group_list">
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g5-pic1.png" alt=""/>
                                <span class="group_text">Закажите любую из&nbsp;дебетовых карт <a target="_blank" href="http://www.sberbank.ru/moscow/ru/person/bank_cards/debet/">на&nbsp;сайте
                                    Сбербанка</a></span>
                            </li>
                            <li class="group_item">
                                <img class="group_ico" src="${imagesPath}/g5-pic2.png" alt=""/>
                                <span class="group_text">Откройте <a target="_blank" href="http://www.sberbank.ru/moscow/ru/about/today/oib/">в&nbsp;ближайшем отделении Сбербанка</a> одну из&nbsp;дебетовых или карту мгновенной выдачи</span>
                            </li>
                        </ul>
                    </div>
                    <!-- // b-group -->

                </div>
                <!-- // b-section -->
                <script>
                    function doSmoothScroll(elem)
                    {
                        if (!window.$ || !(window.$ = window.jQuery)) return;
                        var $target = $(elem.href.replace(/.+(#.+)/g, '$1'));
                        $('html,body').animate({scrollTop: $target.offset().top}, 650, function ()
                        {
                            if ($target[0].id) location.hash = $target[0].id;
                        });
                        return false;
                    }
                </script>
            </div>
            <!-- // b-promo-page -->
            <!-- Конец контента!!! -->
        </tiles:put>
    </tiles:insert>
</html:form>
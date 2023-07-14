<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="moveOutHeadInfo" flush="false">
    <tiles:put name="note" type="string" value="РЕГИСТРАЦИЯ В СБЕРБАНК ОНЛАЙН"/>
    <tiles:put name="data" type="string">
        <div class="promo_head">
            <div class="promo_head-inner">
                <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_bg.png" alt="" class="promo_pic" />
                <div class="promo_about">Регистрация в сбербанк онлайн</div>
                <div class="promo_title">Просто <br/>и&nbsp;невероятно удобно пользоваться</div>
            </div>
        </div>
        <i class="promo_divider"></i>
        <div class="promo_items">
            <div class="promo_items-inner">
                <div class="promo_item">
                    <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_1.png" alt="" class="promo_img"/>
                    <h3 class="promo_text">Открывайте вклады <br/>на выгодных <br/>условиях</h3>
                    <div class="promo_description">До 7.5% по онлайн вкладам в рублях с ежедневной капитализацией процентов.</div>
                </div>
                <div class="promo_item">
                    <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_2.png" alt="" class="promo_img"/>
                    <h3 class="promo_text">Оплачивайте <br/>услуги с меньшей <br/>комиссией</h3>
                    <div class="promo_description">У нас нет комиссии на все партнерские услуги и коммунальные платежи</div>
                </div>
                <div class="promo_item">
                    <img src="${skinUrl}/skins/sbrf/images/csa/reg-promo_3.png" alt="" class="promo_img"/>
                    <h3 class="promo_text">Управляйте <br/>структурой своих <br/>расходов</h3>
                    <div class="promo_description">Наглядная система контроля расходов и круглосуточные мгновенные переводы.</div>
                </div>
            </div>
            <div class="promo_buttons">
                <a onclick="return utils.slide('#AboutRegistration')" class="b-btn btn_yellow" href="#AboutRegistration">
                    <span class="btn_text">Зарегистрироваться</span>
                    <i class="btn_crn"></i>
                </a>
            </div>
        </div>
    </tiles:put>
    <tiles:put name="dashOff" type="string" value="Свернуть этот блок"/>
    <tiles:put name="dashOn" type="string" value="Почему это так удобно?"/>
</tiles:insert>


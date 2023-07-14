<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:importAttribute/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<div class="connectUDBO">
    <div class="titleForm">Подключите все возможности <br/><span class="noWrap">Сбербанк Онлайн</span></div>
    <p class="titleDesc">Вам станут доступны вклады, кредиты, металлические счета и еще множество других
        полезных продуктов и сервисов Сбербанка. Подключайтесь!</p>

    <div class="promoUDBO">
        <img width="669" height="279" src="${image}/connectUDBO.png" alt=""/>
        <div class="buttonAreaPromo">
            <div class="centerBtn">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"        value="button.connect"/>
                    <tiles:put name="commandTextKey"    value="button.connect"/>
                    <tiles:put name="commandHelpKey"    value="button.connect.help"/>
                    <tiles:put name="bundle"            value="commonBundle"/>
                    <tiles:put name="viewType"          value="orangePromo"/>
                </tiles:insert>
            </div>
            <div class="rightBtn">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"        value="button.notNow"/>
                    <tiles:put name="commandTextKey"    value="button.notNow"/>
                    <tiles:put name="commandHelpKey"    value="button.notNow.help"/>
                    <tiles:put name="bundle"            value="commonBundle"/>
                    <tiles:put name="viewType"          value="lightGrayPromo"/>
                </tiles:insert>
            </div>
        </div>
    </div>
    <div class="clear"></div>

    <div class="accomplishGoal">
        <p class="promoMotivation">Вы сможете легко и быстро</p>

        <div class="promoGoals">
            <div id="goalAccounts" class="goalsType">
                <p class="goalTitle">Открыть сберегательный <br />счет или вклад онлайн</p>
                <p class="goalDesc">Вам станут доступны <br />онлайн-вклады по более <br />выгодным процентным ставкам</p>
            </div>
            <div id="goalTarget" class="goalsType">
                <p class="goalTitle">Достичь поставленной <br />цели</p>
                <p class="goalDesc">Накопить на дом, машину или современный гаджет помогут <br />сервисы «Цели и копилка»</p>
            </div>
            <div id="goalIma" class="goalsType">
                <p class="goalTitle">Сохранить сбережения <br />в металлических счетах</p>
                <p class="goalDesc">Металлические счета защищают сбережения от инфляции и дают возможность получить доход за <br />счет роста стоимости <br />драгоценных металлов.</p>
            </div>
            <div id="goalLoan" class="goalsType">
                <p class="goalTitle">Взять подходящий <br />кредит онлайн</p>
                <p class="goalDesc">Вы сможете оформить кредит <br />без похода в отделение <br />Сбербанка.</p>
            </div>
            <div id="goalPfr" class="goalsType">
                <p class="goalTitle">Подключить пенсионную <br />программу, получить <br />сертификат</p>
                <p class="goalDesc">Дополнительная гарантия<br /> сохранения качества жизни после<br /> окончания трудового периода.</p>
            </div>
        </div>
    </div>
</div>
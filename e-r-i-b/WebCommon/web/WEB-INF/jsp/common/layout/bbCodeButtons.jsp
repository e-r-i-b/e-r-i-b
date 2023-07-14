<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    textAreaId - идентификатор textArea к которой привязаны кнопки работы с BB кодами
    showBirthday - кнопка Дата рождения
    showPhone - кнопка Номер телефона клиента
    showFio - кнопка ФИО
    showBold - кнопка жирного текста
    showItalics - кнопку курсивного текста
    showUnderline - кнопка подчеркнутого текста
    showColor - кнопка выбора цвета
    showHyperlink - кнопка добавления гиперссылки
    showTextAlign - кнопки выравнивания текста
    showPercentAccount - кнопки процента
    showPercentThanks - кнопки процента
    showPercentRate - кнопка "процентаня ставка"
    showCreditLimit -  кнопка "Кредитный лимит"
    showCreditDuration - кнопка "Срок кредита"
    showTarifPlan - кнопка "Тарифный план"
--%>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<div class="bbCodeButtons">
    <div class="mainButtons">
        <c:if test="${showBirthday}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Birthday.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'birhtDay');"/>
            </div>
        </c:if>
        <c:if test="${showPhone}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Phone.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'phone');"/>
            </div>
        </c:if>
        <c:if test="${showFio}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_FIO.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'fio');"/>
            </div>
        </c:if>
        <c:if test="${showCreditFio}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'fio');" value="<ФИО клиента>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showDULSeries}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'dulSeries');" value="<Серия ДУЛ>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showDULNumber}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'dulNumber');" value="<Номер ДУЛ>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showDULLocation}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'dulLocation');" value="<Место выдачи ДУЛ>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showDULIssue}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'dulIssue');" value="<Дата выдачи ДУЛ>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showPSK}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'psk');" value="<ПСК>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showAmount}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'amount');" value="<Сумма>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showDuration}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'duration');" value="<Срок>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showInterest}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'interest');" value="<Ставка>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showOrder}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'order');" value="<Порядок погашения>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showAccountIssuance}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'issuance');" value="<Счет для выдачи>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showBorrower}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'borrower');" value="<Заемщик>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showRegistration}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'registration');" value="<Адрес регистрации>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showTopUp}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('topUp', ['${textAreaId}']);" value="<Шаблон погашаемого кредита>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showAgreementNumber}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'agreementNumber');" value="<№ кредитного договора>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showProductName}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'productName');" value="<Название продукта>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showTermStart}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'termStart');" value="<Дата выдачи кредита>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showIdContract}">
            <div class="button">
                <input type="button" onmousedown="changeSelText('client', ['${textAreaId}'], 'idContract');" value="<№ ссудного счета>" class="buttWhite smButt">
            </div>
        </c:if>
        <c:if test="${showPercentRate}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/icon_interest_rate.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'percentRate');"/>
            </div>
        </c:if>
        <c:if test="${showCreditLimit}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/icon_credit_limit.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'creditLimit');"/>
            </div>
        </c:if>
        <c:if test="${showCreditDuration}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/icon_credit_time.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'creditDuration');"/>
            </div>
        </c:if>
        <c:if test="${showTarifPlan}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Tarif_Plan.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'tarifPlan');"/>
            </div>
        </c:if>
        <c:if test="${showBold}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Bold.gif" onmousedown="changeSelText('b', ['${textAreaId}']);"/>
            </div>
        </c:if>
        <c:if test="${showItalics}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Italic.gif" onmousedown="changeSelText('i', ['${textAreaId}']);"/>
            </div>
        </c:if>
        <c:if test="${showUnderline}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Underline.gif" onmousedown="changeSelText('u', ['${textAreaId}']);"/>
            </div>
        </c:if>
        <c:if test="${showColor}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Colored.gif" onmousedown="hideOrShow('${textAreaId}ColorText');"/>
            </div>
        </c:if>
        <c:if test="${showHyperlink}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Link.gif" onmousedown="addHyperlink(['${textAreaId}']);"/>
            </div>
        </c:if>
        <c:if test="${showTextAlign}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Left.gif" onmousedown="changeSelText('left', ['${textAreaId}']);"/>
            </div>
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Center.gif" onmousedown="changeSelText('center', ['${textAreaId}']);"/>
            </div>
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Right.gif" onmousedown="changeSelText('right', ['${textAreaId}']);"/>
            </div>
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Justify.gif" onmousedown="changeSelText('justify', ['${textAreaId}']);"/>
            </div>
        </c:if>
        <c:if test="${showPercentAccount}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Percent.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'percent name=\'account\'');"/>
            </div>
        </c:if>
        <c:if test="${showPercentThanks}">
            <div class="button">
                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Percent.gif" onmousedown="changeSelText('client', ['${textAreaId}'], 'percent name=\'thanks\'');"/>
            </div>
        </c:if>
    </div>
    <div class="clear"></div>
    <c:if test="${showColor}">        
        <div id="${textAreaId}ColorText" class="colorButtons" style="display:none;">
            <div class="colorButton" style="background-color:yellow"  onmousedown="changeSelText('c', ['${textAreaId}'], 'yellow');"></div>
            <div class="colorButton" style="background-color:olive"   onmousedown="changeSelText('c', ['${textAreaId}'], 'olive');"></div>
            <div class="colorButton" style="background-color:fuchsia" onmousedown="changeSelText('c', ['${textAreaId}'], 'fuchsia');"></div>
            <div class="colorButton" style="background-color:red"     onmousedown="changeSelText('c', ['${textAreaId}'], 'red');"></div>
            <div class="colorButton" style="background-color:purple"  onmousedown="changeSelText('c', ['${textAreaId}'], 'purple');"></div>
            <div class="colorButton" style="background-color:maroon"  onmousedown="changeSelText('c', ['${textAreaId}'], 'maroon');"></div>
            <div class="colorButton" style="background-color:navy"    onmousedown="changeSelText('c', ['${textAreaId}'], 'navy');"></div>
            <div class="colorButton" style="background-color:blue"    onmousedown="changeSelText('c', ['${textAreaId}'], 'blue');"></div>
            <div class="colorButton" style="background-color:aqua"    onmousedown="changeSelText('c', ['${textAreaId}'], 'aqua');"></div>
            <div class="colorButton" style="background-color:lime"    onmousedown="changeSelText('c', ['${textAreaId}'], 'lime');"></div>
            <div class="colorButton" style="background-color:green"   onmousedown="changeSelText('c', ['${textAreaId}'], 'green');"></div>
            <div class="colorButton" style="background-color:teal"    onmousedown="changeSelText('c', ['${textAreaId}'], 'teal');"></div>
            <div class="colorButton" style="background-color:silver"  onmousedown="changeSelText('c', ['${textAreaId}'], 'silver');"></div>
            <div class="colorButton" style="background-color:gray"    onmousedown="changeSelText('c', ['${textAreaId}'], 'gray');"></div>
        </div>
    </c:if>
</div>
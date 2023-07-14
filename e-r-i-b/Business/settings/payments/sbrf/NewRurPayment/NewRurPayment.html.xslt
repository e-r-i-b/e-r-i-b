<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:su="java://com.rssl.phizic.utils.StringHelper"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:pru="java://com.rssl.phizic.business.profile.Utils"
                xmlns:dep="java://com.rssl.phizic.web.util.DepartmentViewUtil"
                xmlns:veu="java://com.rssl.phizic.web.util.ValidationErrorUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">

<xsl:import href="commonFieldTypes.template.xslt"/>
<xsl:output method="html" version="1.0"  indent="yes"/>
<xsl:param name="mode" select="'edit'"/>
<xsl:param name="webRoot" select="'webRoot'"/>
<xsl:param name="application" select="'application'"/>
<xsl:param name="app">
   <xsl:value-of select="$application"/>
</xsl:param>
<xsl:param name="resourceRoot" select="'resourceRoot'"/>
<xsl:param name="personAvailable" select="true()"/>
<xsl:param name="isTemplate" select="'isTemplate'"/>
<xsl:param name="byTemplate" select="'byTemplate'"/>
<xsl:param name="documentState" select="'documentState'"/>
<xsl:param name="postConfirmCommission" select="postConfirmCommission"/>

<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
<xsl:variable name="styleSpecial" select="''"/>

<xsl:template match="/">

    <script type="text/javascript">
        function isEmpty(value)
        {
            return (value == null || value == "");
        }
    </script>

    <xsl:choose>
        <xsl:when test="$mode = 'edit'">
           <xsl:apply-templates mode="edit-simple-payment"/>
        </xsl:when>
        <xsl:when test="$mode = 'view'">
            <xsl:apply-templates mode="view-simple-payment"/>
        </xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="resources">
    <xsl:param name="name"/>
    <xsl:param name="accountNumber"/>
    <xsl:param name="linkId"/>
    <xsl:param name="activeAccounts"/>
    <xsl:param name="activeCards"/>

    <select id="{$name}" name="{$name}" class="selectSbtM" onchange="refreshForm();">
        <xsl:variable name="recType" select="receiverSubType"/>

        <xsl:choose>
            <xsl:when test="count($activeCards) = 0 and ($recType='ourContact' or $recType='ourCard' or $recType='ourPhone' or $recType='ourContactToOtherCard')">
                <option value="">Нет доступных карт</option>
            </xsl:when>
            <xsl:when test="count($activeCards) = 0 and count($activeAccounts) = 0">
                <option value="">Нет доступных карт и счетов</option>
            </xsl:when>
            <xsl:when test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                <xsl:choose>
                    <xsl:when test="$recType='ourContact' or $recType='ourPhone' or $recType='ourCard' or $recType='masterCardExternalCard' or $recType='visaExternalCard' or $recType='ourContactToOtherCard'">
                        <xsl:if test="count($activeCards) > 0" >
                            <option value="">Выберите карту списания</option>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                            <xsl:when test="count($activeCards) = 0 and count($activeAccounts) > 0">
                                <option value="">Выберите счет списания</option>
                            </xsl:when>
                            <xsl:when test="count($activeAccounts) + count($activeCards) > 0">
                                <option value="">Выберите счет/карту списания</option>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
        <xsl:if test="$recType='ourAccount' or $recType='externalAccount'">
            <xsl:for-each select="$activeAccounts">
                <option>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber=./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="au:getShortAccountNumber(./@key)"/>&nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
        </xsl:if>
        <xsl:for-each select="$activeCards">
            <xsl:variable name="id" select="field[@name='code']/text()"/>
            <option>
                <xsl:attribute name="value">
                    <xsl:value-of select="$id"/>
                </xsl:attribute>
                <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                    <xsl:attribute name="selected"/>
                </xsl:if>
                <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                [<xsl:value-of select="./field[@name='name']"/>]
                <xsl:if test="./field[@name='amountDecimal'] != ''">
                    <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                </xsl:if>
                <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
            </option>
        </xsl:for-each>
    </select>
</xsl:template>

<xsl:template match="/form-data" mode="edit-simple-payment">
    <script type="text/javascript" src="{$resourceRoot}/scripts/PaymentReceiver.js"/>
    <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
    <xsl:variable name="availableAddressBook"><xsl:value-of select="pu:impliesService('AddressBook')"/></xsl:variable>
    <xsl:variable name="availableYandex" select="pu:impliesService('NewRurPaymentYandex') = 'true' and yandexExist = 'true'"/>
    <xsl:variable name="availableOtherCardPayment" select="pu:impliesService('MastercardMoneySendService') = 'true' or pu:impliesService('VisaMoneySendService') = 'true'"/>

    <input type="hidden" name="exactAmount" value="{exactAmount}" id="exactAmount"/>
    <input type="hidden" name="buyAmountCurrency"  value="{buyAmountCurrency}"/>
    <input type="hidden" name="isErrorCurrency" id="isErrorCurrency" value="false"/>
    <input type="hidden" name="isCardTransfer" value="{isCardTransfer}"/>
    <input type="hidden" name="documentNumber" value="{documentNumber}"/>
    <input type="hidden" name="documentDate" value="{documentDate}"/>
    <input type="hidden" id="externalCardCurrency" name="externalCardCurrency" value="{buyAmountCurrency}"/>
    <input type="hidden" id="contactName" name="contactName" value="{contactName}"/>
    <input type="hidden" id="contactSberbank" name="contactSberbank" value="{contactSberbank}"/>
    <input type="hidden" id="avatarPath" name="avatarPath" value="{avatarPath}"/>
    <input type="hidden" id="yandexExist" name="yandexExist" value="{yandexExist}"/>

    <xsl:variable name="recType" select="receiverSubType"/>
    <div style="display: none;" id="currencyErrorDiv"></div>

    <xsl:call-template name="receiverSubTypeSelect">
        <xsl:with-param name="recType" select="$recType"/>
        <xsl:with-param name="availableOtherCardPayment" select="$availableOtherCardPayment"/>
        <xsl:with-param name="availableYandex" select="$availableYandex"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverIdentifier'"/>
        <xsl:with-param name="lineId" select="'receiverIdentifierRow'"/>
        <xsl:with-param name="spacerHint">
            <xsl:call-template name="topHint">
                <xsl:with-param name="topHintText">Телефон с +7</xsl:with-param>
                <xsl:with-param name="topHintId">phoneHint</xsl:with-param>
                <xsl:with-param name="topHintValue">
                    Укажите номер в формате<br /> +79211234567
                </xsl:with-param>
            </xsl:call-template>
            &nbsp;
            или

            <xsl:call-template name="topHint">
                <xsl:with-param name="addHintClass">mediumHint</xsl:with-param>
                <xsl:with-param name="topHintId">cardHint</xsl:with-param>
            </xsl:call-template>
        </xsl:with-param>

        <xsl:with-param name="rowName"><div class="float">Получатель</div></xsl:with-param>
        <xsl:with-param name="rowNameHelp">
            <xsl:call-template name="hintTemplate">
                <xsl:with-param name="hintText">
                    <div class="hintListText">
                        <div class="bold">Номер телефона</div>
                        Формат ввода +79211234567
                    </div>
                    <div class="hintListText textLast">
                        <div class="bold" id="receiverHelpFormatType"></div>
                        <span class="float">Формат ввода&nbsp; — &nbsp;</span><span id="receiverHelpFormatDetail" class="float"></span>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:with-param>

        <xsl:with-param name="rowValue">
            <div class="float itemsWidth">
                <div class="contactData">
                    <input type="hidden" id="externalContactId" name="externalContactId" value="{externalContactId}"/>
                    <input type="hidden" id="externalPhoneNumber" name="externalPhoneNumber" value="{externalPhoneNumber}"/>
                    <input type="hidden" id="externalCardNumber" name="externalCardNumber" value="{externalCardNumber}"/>
                    <input type="hidden" id="externalWalletNumber" name="externalWalletNumber" value="{externalWalletNumber}"/>
                    <input type="hidden" name="receiverSubType" id="receiverSubType" value="{receiverSubType}"/>
                    <div id="yandexPay" style="display: none;"></div>
                    <div class="yaStyle">
                        <span id="receiverIdentifierContaner"></span>
                        <a class="cancelInput" id="cancelIdentifierInput" onclick="paymentReceiver.cancelInput();" style="display: none;"></a>
                    </div>
                    <div class="clear"></div>

                    <div id="enteredIdentifier"  class="float" style="display:none;">
                        <xsl:if test="$byTemplate != 'true'">
                            <xsl:attribute name="onclick">paymentReceiver.editEnteredIdentifier();</xsl:attribute>
                            <xsl:attribute name="onmouseover">$('#editEnteredIdentifierEl').show();</xsl:attribute>
                            <xsl:attribute name="onmouseout">$('#editEnteredIdentifierEl').hide();</xsl:attribute>
                        </xsl:if>
                        <xsl:call-template name="roundBorder">
                            <xsl:with-param name="content">
                                <span id="cardIcon"></span><div id="enteredIdentifierContent"></div>
                                <div class="sugestShadow"></div>
                                <div id="editEnteredIdentifierEl" class="sugestEdit" style="top:8px;"></div>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>

                    <div id="selectedContactEl" onmouseover="paymentReceiver.showEditContact(true);" onmouseout="paymentReceiver.showEditContact(false);" onclick="paymentReceiver.editSelectedContact();" style="display:none;">
                        <xsl:call-template name="roundBorder">
                            <xsl:with-param name="content">
                                <div id="editContactEl" class="sugestEdit"></div>
                                <xsl:call-template name="showContactTemplate"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>

                    <xsl:call-template name="errorContainer">
                        <xsl:with-param name="sujestHintText">
                            <div class="logicError" id="notOurClientError">
                                Данный номер телефона не зарегистрирован ни за одним клиентом Сберанка.
                                <xsl:if test="$availableOtherCardPayment">
                                    Но, Вы можете выполнить: <br/>
                                    <ul class="hintList">
                                        <li>
                                            <a href="#" class="dashedLink" onclick="clickPaymentType('ourContactToOtherCard', true);">Перевод на карту другого банка</a>
                                        </li>
                                    </ul>
                                    <xsl:if test="$availableYandex">
                                        <ul class="hintList">
                                            <li><a href="#" class="dashedLink" onclick="clickPaymentType('yandexWallet', false);">Перевод на кошелек Яндекс.Деньги</a> по номеру </li>
                                            телефона или номеру лицевого счета Яндекс.Деньги
                                        </ul>
                                    </xsl:if>
                                </xsl:if>
                            </div>

                            <div class="logicError" id="notOurCardError">
                                Похоже, это не карта клиента Сбербанка.
                                <xsl:if test="$availableOtherCardPayment">
                                    Можно выполнить:
                                    <ul class="hintList">
                                        <li>
                                            <a href="#" class="dashedLink" onclick="clickPaymentType('ourContactToOtherCard', false);">Перевод на карту другого банка</a>
                                        </li>
                                    </ul>
                                </xsl:if>
                                Проверьте введенный номер карты или укажите другого получателя
                            </div>

                            <div class="logicError" id="noCardErrorOurClient">
                                Можно отправить перевод:
                                <ul class="hintList">
                                    <li>
                                        <a href="#" class="dashedLink" onclick="clickPaymentType('', false);">Клиенту Сбербанка</a>
                                    </li>
                                </ul>
                                <xsl:if test="$availableYandex">
                                    <ul class="hintList">
                                        <li><a href="#" class="dashedLink" onclick="clickPaymentType('yandexWallet', false);">Кошелёк Яндекс-Деньги</a></li>
                                    </ul>
                                    &nbsp;по номеру телефона
                                </xsl:if>
                                Укажите другого получателя или введите полный номер карты вручную
                            </div>

                            <div class="logicError" id="noCardErrorNotOurClient">
                                <xsl:if test="$availableYandex">
                                    Можно отправить перевод на&nbsp;
                                    <ul class="hintList">
                                        <li><a href="#" class="dashedLink" onclick="clickPaymentType('yandexWallet', false);">Яндекс.Деньги</a></li>
                                    </ul>
                                </xsl:if>
                                Укажите другого получателя или введите полный номер карты вручную
                            </div>

                            <div class="logicError" id="isOurClientError">
                                Данный номер телефона зарегистрирован за клиентом Сбербанка. Вы можете выполнить:
                                <ul class="hintList">
                                    <li>
                                        <a href="#" class="dashedLink" onclick="clickPaymentType('', false);">Перевод клиенту Сбербанка</a>
                                    </li>
                                </ul>
                                <xsl:if test="$availableYandex">
                                    <ul class="hintList">
                                        <li><a href="#" class="dashedLink" onclick="clickPaymentType('yandexWallet', false);">Перевод на кошелек Яндекс.Деньги</a></li>
                                    </ul>
                                    &nbsp;по номеру телефона
                                </xsl:if>
                            </div>

                            <div class="logicError" id="notFoundClientError">
                                Пользователь с таким номером телефона не найден.
                                <xsl:if test="$availableYandex">
                                    Но, можно отправить
                                        <a href="#" class="dashedLink" onclick="clickPaymentType('yandexWallet', false);">перевод на кошелек Яндекс.Деньги</a>
                                    по номеру телефона
                                    <br/>
                                </xsl:if>
                                Укажите другого получателя или введите полный номер карты вручную
                            </div>

                            <div class="logicError" id="isOurCardError">
                                Карта с таким номером зарегистрирована в Сбербанке. Перевод средств будет осуществлен, как клиенту Сбербанка
                            </div>
                            <div class="logicError" id="isLimitExceededError">
                                <span class="bold">Переводы по номеру телефона заблокированы до 00:00 по московскому времени.</span><br/>
                                <xsl:value-of select="dh:getPersonPaymentLimitMessage()"/>
                            </div>
                            <div class="logicError" id="isExternalSystemError">
                                <span class="bold">По техническим причинам вы не можете выполнить данную операцию. Пожалуйста, повторите попытку позже.</span><br/>
                                    Но, Вы можете выполнить: <br/>
                                    <ul class="hintList">
                                        <li>
                                            <a href="#" class="dashedLink" onclick="clickPaymentType('ourContactToOtherCard', false);">Перевод на карту другого банка</a>
                                        </li>
                                    </ul>
                            </div>

                        </xsl:with-param>
                    </xsl:call-template>

                    <div id="identifyByPhone" style="display:none;" class="float">
                        Идентификация по номеру телефона
                        <div class="shine"> </div>
                    </div>

                    <div id="mbContactInfo" class="mbContactInfo linear">
                        <table class="contactWidth findContact">
                            <tr>
                                <td class="fixTblItem">
                                    <div class="sugestAvatar whiteSmall" id="mbAvatarIcon">
                                        <div class="relative">
                                           <img class="float avatarIcon" id="mbAvatarIconImg"/>
                                           <div class="roundAvatar mbEditAvatar"></div>
                                        </div>
                                    </div>
                                </td>
                                <td class="valign-top">
                                    <div id="mbContactInfoFIO" class="mbContactInfoFIO fixNameWidth"></div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

            </div>
            <xsl:if test="$availableAddressBook = 'true' and $byTemplate != 'true'">
                <span class="openAddressBook floatRight" onclick="paymentReceiver.openAddressBook();">
                    <span class="iconAB"></span>
                    <span>ВЫБРАТЬ ИЗ АДРЕСНОЙ КНИГИ</span>
                </span>
            </xsl:if>
        </xsl:with-param>
        <xsl:with-param name="spacerClass" select="'spacer'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverAccountRow'"/>
        <xsl:with-param name="rowName"><div class="float">Номер счета</div></xsl:with-param>
        <xsl:with-param name="rowNameHelp">
            <xsl:call-template name="hintTemplate">
                <xsl:with-param name="hintText">
                    <div class="hintListText textLast">
                        <div class="bold">Номер банковского счета</div>
                        Формат ввода - 20 цифр
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:with-param>
        <xsl:with-param name="rowValue">
            <input name="receiverAccountCurrency" type="hidden"/>
            <input id="receiverAccountInternal" name="receiverAccountInternal" type="text" value="{receiverAccountInternal}" maxlength="25" class="fixInputSize"
                   onchange="javascript:checkAccountCurrency(getElementValue('receiverAccountInternal'));" onkeyup="javascript:checkAccountCurrency(getElementValue('receiverAccountInternal'));">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
        <xsl:with-param name="spacerClass" select="'spacer'"/>
        <xsl:with-param name="spacerHint">&nbsp;</xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="isBankTransfer" select="$recType='ourAccount' or $recType='externalAccount'"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverSurnameRow'"/>
        <xsl:with-param name="rowName">Фамилия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverSurname" name="receiverSurname" type="text" class="fixInputSize" maxlength="20">
               <xsl:if test="$isBankTransfer">
                    <xsl:attribute name="value">
                        <xsl:value-of select="receiverSurname"/>
                    </xsl:attribute>
               </xsl:if>
               <xsl:if test="$byTemplate = 'true'">
                   <xsl:attribute name="disabled"/>
               </xsl:if>
            </input>
        </xsl:with-param>
        <xsl:with-param name="spacerClass" select="'spacer'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverFirstNameRow'"/>
        <xsl:with-param name="rowName">Имя</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverFirstName" name="receiverFirstName" type="text" class="fixInputSize" maxlength="20">
                <xsl:if test="$isBankTransfer">
                    <xsl:attribute name="value">
                        <xsl:value-of select="receiverFirstName"/>
                    </xsl:attribute>
               </xsl:if>
               <xsl:if test="$byTemplate = 'true'">
                   <xsl:attribute name="disabled"/>
               </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId"   select="'receiverPatrNameRow'"/>
        <xsl:with-param name="rowName">Отчество</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverPatrName" name="receiverPatrName" type="text" class="fixInputSize" maxlength="20">
               <xsl:if test="$isBankTransfer">
                    <xsl:attribute name="value">
                        <xsl:value-of select="receiverPatrName"/>
                    </xsl:attribute>
               </xsl:if>
               <xsl:if test="$byTemplate = 'true'">
                   <xsl:attribute name="disabled"/>
               </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id" select="'receiverINN'"/>
        <xsl:with-param name="lineId" select="'receiverINNRow'"/>
        <xsl:with-param name="rowName">ИНН</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="14" maxlength="12" class="fixInputSize customPlaceholder" title="Заполняется по желанию">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'receiverAddressRow'"/>
        <xsl:with-param name="rowName">Адрес</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverAddress" name="receiverAddress" type="text" value="{receiverAddress}" weigth="250px" class="fixInputSize customPlaceholder" maxlength="200" title="Заполняется по желанию" >
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankNameRow'"/>
        <xsl:with-param name="rowName">Наименование банка</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="bank" name="bank" type="text" value="{bank}" class="fixInputSize" maxlength="100">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>

            <span class="openAddressBook floatRight" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('bank'),getFieldValue('receiverBIC'), false);">
                <span class="iconAB"></span>
                <span>ВЫБРАТЬ ИЗ СПРАВОЧНИКА</span>
            </span>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankBIKRow'"/>
        <xsl:with-param name="description">Введите Банковский Идентификационный Код. БИК может состоять только из 9 цифр.</xsl:with-param>
        <xsl:with-param name="rowName">БИК</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" maxlength="9" class="fixInputSize" onchange="getBankByBIC();">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'bankCorAccountRow'"/>
        <xsl:with-param name="description"><cut/>Укажите корреспондентский счет банка (20 цифр без точек и пробелов)</xsl:with-param>
        <xsl:with-param name="rowName">Корр. счет</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" maxlength="20" class="fixInputSize">
                <xsl:if test="$byTemplate = 'true'">
                    <xsl:attribute name="disabled"/>
                </xsl:if>
            </input>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">fromResource</xsl:with-param>
        <xsl:with-param name="lineId" select="'fromResourceRow'"/>
        <xsl:with-param name="rowName">Счет списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:if test="$personAvailable">
                <xsl:call-template name="resources">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                    <xsl:with-param name="linkId" select="fromResource"/>
                    <xsl:with-param name="activeCards" select="$activeCards"/>
                    <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                </xsl:call-template>
            </xsl:if>
            <xsl:if test="not($personAvailable)">
                <select id="fromAccountSelect" name="fromAccountSelect" disabled="disabled">
                    <option selected="selected">Счет клиента</option>
                </select>
            </xsl:if>
        </xsl:with-param>
        <xsl:with-param name="spacerClass" select="'spacer'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="spacerClass" select="'spacer'"/>
        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
        <xsl:with-param name="id">sellAmount</xsl:with-param>
        <xsl:with-param name="rowName">Сумма в валюте списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="sellAmount" name="sellAmount" type="text" value="{sellAmount}"
                   onchange="javascript:sellCurrency();" onkeyup="javascript:sellCurrency();" class="moneyField"/>&nbsp;
            <span id="sellAmountCurrency"></span>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
        <xsl:with-param name="id">buyAmount</xsl:with-param>
        <xsl:with-param name="rowName">Сумма в валюте зачисления</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input id="buyAmount" name="buyAmount" type="text" value="{buyAmount}" size="24"
                   onchange="javascript:buyCurrency();" onkeyup="javascript:buyCurrency();" class="moneyField"/>&nbsp;
            <span id="buyAmountCurrency"></span>
        </xsl:with-param>
        <xsl:with-param name="spacerClass" select="'spacer'"/>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'postConfirmCommissionMessage'"/>
        <xsl:with-param name="rowName">Комиссия</xsl:with-param>
        <xsl:with-param name="rowValue">
            <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.transfer.message')"/></i>
        </xsl:with-param>
        <xsl:with-param name="rowStyle">display: none</xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'groundRow'"/>
        <xsl:with-param name="rowName"><div class="float">Назначение</div></xsl:with-param>
        <xsl:with-param name="rowNameHelp">
            <xsl:call-template name="hintTemplate">
                <xsl:with-param name="hintText">
                    <div class="hintListText">
                        Укажите, с какой целью Вы переводите<br/>деньги. Например, благотворительный<br/>взнос
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:with-param>
        <xsl:with-param name="rowValue">
            <textarea id="ground" name="ground" cols="40"><xsl:value-of select="ground"/></textarea>
            <div class="newAreaHeight"></div>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="pu:impliesService('MessageWithCommentToReceiverService')">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">messageToReceiver</xsl:with-param>
            <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
            <xsl:with-param name="rowName">&nbsp;
                <div id="smsAvatarContainer" class="smsAvatarContainer">
                    <xsl:variable name="imagePath" select="pru:getAvatarPath('ICON')"/>
                    <xsl:value-of select="pru:buildUserImage($resourceRoot, $imagePath, 'ICON', false, 'float avatarIcon css3', '', '')" disable-output-escaping="yes"/>
                </div>
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <script type="text/javascript">
                    function focusIn(){
                        document.getElementById("messBlock").className = "messBlock messBlockFocus";
                    }
                    function focusLost(){
                        document.getElementById("messBlock").className = "messBlock";
                    }
                </script>
                <div class="messBlock" id="messBlock">
                    <input id="messageToReceiver" name="messageToReceiver" type="text" value="{messageToReceiver}" maxlength="40"
                        title="Написать сообщение получателю" class="customPlaceholder" onfocus="focusIn();" onBlur="focusLost();"/>
                    <div class="messageItem"></div>
                </div>
            </xsl:with-param>
            <xsl:with-param name="spacerClass" select="'spacer'"/>
        </xsl:call-template>
    </xsl:if>

    <script type="text/javascript" language="JavaScript">

        var EMPTY_ACCOUNT_ERROR = "Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.";
        var REGEXP_BIC_MESSAGE = "Вы неправильно указали БИК. Пожалуйста, введите ровно 9 цифр.";
        var BANKS_NOT_FOUND_MESSAGE = "БИК банка получателя не найден в справочнике банков.";

        var accounts    = new Array();
        var currencies  = new Array();
        var resourceCurrencies  = new Array();
        var cardAccounts = new Array();
        var accountList = new Array();
        var cardList = new Array();
        var wasShowLink = false;
        var countNotEmptyAcc = 0;
        var indexNotEmptyAcc = 0;
        var countNotEmptyCards = 0;
        var indexNotEmptyCards = 0;
        var receiverSubTypeButton = '<xsl:value-of select="receiverSubType"/>';

        function init()
        {
            <xsl:for-each select="$activeAccounts">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                accounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="@key"/>';
                resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';

                var account = new Object();
                account.id = '<xsl:value-of select="field[@name='code']/text()"/>';
                account.number = '<xsl:value-of select="au:getShortAccountNumber(./@key)"/>';
                account.name = '[<xsl:value-of select="su:formatStringForJavaScript(./field[@name = 'name'], true())"/>]';
                <xsl:choose>
                    <xsl:when test="./field[@name='amountDecimal'] != ''">
                        account.amount = '<xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>';
                    </xsl:when>
                    <xsl:otherwise>
                        account.amount = '';
                    </xsl:otherwise>
                </xsl:choose>
                account.currency = '<xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>';
                accountList[accountList.length] = account;
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyAcc ++;
                    indexNotEmptyAcc = accountList.length;
                </xsl:if>
                <xsl:if test="field[@name='isUseStoredResource'] = 'true'">
                    addMessage('Информация по Вашим счетам может быть неактуальной.');
                </xsl:if>
            </xsl:for-each>

            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                resourceCurrencies['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='currencyCode']/text()"/>';
                cardAccounts['<xsl:value-of select="$id"/>'] = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';

                var card = new Object();
                card.id = '<xsl:value-of select="field[@name='code']/text()"/>';
                card.number = '<xsl:value-of select="mask:getCutCardNumber(./@key)"/>';
                card.name = '[<xsl:value-of select="su:formatStringForJavaScript(./field[@name='name'], true())"/>]';
                <xsl:choose>
                    <xsl:when test="./field[@name='amountDecimal'] != ''">
                        card.amount = '<xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>';
                    </xsl:when>
                    <xsl:otherwise>
                        card.amount = '';
                    </xsl:otherwise>
                </xsl:choose>
                card.currency = '<xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>';
                card.type = '<xsl:value-of select="./field[@name='cardType']"/>';
                cardList[cardList.length] = card;
                <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                    addMessage('Информация по Вашим картам может быть неактуальной.');
                </xsl:if>
                <xsl:if test="field[@name='amountDecimal'] > 0 ">
                    countNotEmptyCards ++;
                    indexNotEmptyCards = cardList.length;
                </xsl:if>
            </xsl:for-each>

            //массив с разрешенными валютами
            currencies["810"] = "RUB";
            currencies["840"] = "USD";
            currencies["978"] = "EUR";

            <xsl:choose>
                <xsl:when test="not($isBankTransfer)">
                    var initData = {
                        "contactName": "<xsl:value-of select="contactName"/>",
                        <xsl:if test="externalPhoneNumber != ''">
                            <xsl:choose>
                                <xsl:when test="receiverSubType = 'ourPhone'">
                                    "externalPhoneNumber": "<xsl:value-of select="externalPhoneNumber"/>",
                                    <xsl:if test="contactName != ''">
                                        "maskedPhoneNumber": "<xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>",
                                    </xsl:if>
                                </xsl:when>
                                <xsl:when test="receiverSubType = 'ourContact' or receiverSubType = 'ourContactToOtherCard' or receiverSubType = 'yandexWalletOurContact'">
                                    "externalPhoneNumber": "<xsl:value-of select="externalPhoneNumber"/>",
                                    "maskedPhoneNumber": "<xsl:value-of select="externalPhoneNumber"/>",
                                </xsl:when>
                                <xsl:when test="receiverSubType = 'yandexWalletByPhone'">
                                    "externalPhoneNumber": "<xsl:value-of select="externalPhoneNumber"/>",
                                    "maskedPhoneNumber": "<xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>",
                                </xsl:when>
                                <xsl:when test="receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard'">
                                    "externalPhoneNumber": "<xsl:value-of select="externalPhoneNumber"/>",
                                    "maskedPhoneNumber": "<xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>",
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:variable name="maskPhone" select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>
                                    "externalPhoneNumber": "<xsl:value-of select="$maskPhone"/>",
                                    "maskedPhoneNumber": "<xsl:value-of select="$maskPhone"/>",
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                        <xsl:choose>
                            <xsl:when test="receiverSubType = 'yandexWallet'">
                                "externalWalletNumber": "<xsl:value-of select="externalWalletNumber"/>",
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="cardNum">
                                    <xsl:choose>
                                        <xsl:when test="externalCardNumber != ''"><xsl:value-of select="externalCardNumber"/></xsl:when>
                                        <xsl:otherwise><xsl:value-of select="receiverAccount"/></xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>
                                <xsl:choose>
                                    <xsl:when test="receiverSubType = 'ourCard' or receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard'">
                                        "externalCardNumber": "<xsl:value-of select="$cardNum"/>",
                                    </xsl:when>
                                    <xsl:otherwise>
                                        "externalCardNumber": "<xsl:value-of select="mask:getCutCardNumber($cardNum)"/>",
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:otherwise>
                        </xsl:choose>

                        "contactSberbank": "<xsl:value-of select="contactSberbank"/>",
                        "receiverName": "<xsl:value-of select="receiverName"/>",
                        "avatarPath": "<xsl:value-of select="avatarPath"/>",
                        "yandexMessageShow": "<xsl:value-of select="yandexMessageShow"/>",
                        "isByTemplate": "<xsl:value-of select="$byTemplate"/>"
                    };
                    paymentReceiver.init(receiverSubTypeButton, initData, '<xsl:value-of select="isOurBankCard"/>',
                                            '<xsl:value-of select="pru:getStaticImageUri()"/>', '<xsl:value-of select="$resourceRoot"/>');
                </xsl:when>
                <xsl:otherwise>
                    paymentReceiver.init(receiverSubTypeButton, null, '', '<xsl:value-of select="pru:getStaticImageUri()"/>', '<xsl:value-of select="$resourceRoot"/>');
                </xsl:otherwise>
            </xsl:choose>
            refreshForm();

            if (!isEmpty(document.getElementById("sellAmount").value))
            {
                sellCurrency();
            }
            if (!isEmpty(document.getElementById("buyAmount").value))
            {
                buyCurrency();
            }

            <xsl:variable name="formErrors" select="veu:getValidationError()"/>
            <xsl:if test="$formErrors = ''">
                var account = getElementValue('receiverAccountInternal');
                if (!isEmpty(account))
                {
                    checkAccountCurrency(account);
                }
                else if(!isEmpty(document.getElementById("externalCardNumber").value) &amp;&amp; (receiverSubTypeButton == 'ourCard'))
                {
                    checkCurrency("field(type)=card&amp;field(cardNumber)=" + document.getElementById("externalCardNumber").value);
                }
                else if(receiverSubTypeButton=='ourPhone')
                {
                    var template = document.getElementsByName("template").item(0);
                    if(template != null)
                        checkCurrency("field(type)=phone&amp;field(phoneNumber)=" + document.getElementById("externalPhoneNumber").value
                        + "&amp;field(template)=" + document.getElementsByName("template").item(0).getAttribute("value"));
                    else
                        checkCurrency("field(type)=phone&amp;field(phoneNumber)=" + document.getElementById("externalPhoneNumber").value);
                }
            </xsl:if>

            updateFromResourse();
            <xsl:choose>
                <xsl:when test="$isBankTransfer">
                    selectDefaultFromResource(countNotEmptyCards+countNotEmptyAcc, indexNotEmptyAcc > 0 ? indexNotEmptyAcc : accountList.length + indexNotEmptyCards);
                </xsl:when>
                <xsl:otherwise>
                    selectDefaultFromResource(countNotEmptyCards, indexNotEmptyCards);
                </xsl:otherwise>
            </xsl:choose>

            payInput.additErrorBlock = "errorDivBase";
            payInput.ACTIVE_CLASS_NAME = "";
            payInput.ERROR_CLASS_NAME = "form-row-addition form-row-new error-row-new";
        }

        function setBankInfo(bankInfo)
        {
            removeError(REGEXP_BIC_MESSAGE);
            removeError(BANKS_NOT_FOUND_MESSAGE);
            setElement("bank", bankInfo["name"]);
            setElement("receiverBIC", bankInfo["BIC"]);
            setElement("receiverCorAccount", bankInfo["account"]);
            if (bankInfo["our"] == 'true')
                updateReceiverSubType('ourAccount');
            else
                updateReceiverSubType('externalAccount');
        }

        function Payment(isCardToAccount, fromCurrency, toCurrency)
        {
            this.fromCurrency   = fromCurrency;
            this.toCurrency = toCurrency;
            this.isCardToAccount = isCardToAccount;

            this.refresh = function()
            {
                //Перевод клиенту Сбербанка РФ
                var isOurClient = isOurClientPaymentType();
                // Перевод на карту в другом банке
                var isPhizExternalCardPaymentType = (receiverSubTypeButton == "visaExternalCard" || receiverSubTypeButton == "masterCardExternalCard" || receiverSubTypeButton == "ourContactToOtherCard");
                // Карточный перевод
                var isCardTransfer = isOurClient || isPhizExternalCardPaymentType || isYandexPayment();

                hideOrShow(ensureElement("receiverAccountRow"),     isCardTransfer);
                hideOrShow(ensureElement("receiverSurnameRow"),     isCardTransfer);
                hideOrShow(ensureElement("receiverFirstNameRow"),   isCardTransfer);
                hideOrShow(ensureElement("receiverPatrNameRow"),    isCardTransfer);
                hideOrShow(ensureElement("receiverINNRow"),         isCardTransfer);
                hideOrShow(ensureElement("receiverAddressRow"),     isCardTransfer);
                hideOrShow(ensureElement("bankTitleRow"),           isCardTransfer);
                hideOrShow(ensureElement("bankNameRow"),            isCardTransfer);
                hideOrShow(ensureElement("bankBIKRow"),             isCardTransfer);
                hideOrShow(ensureElement("bankCorAccountRow"),      isCardTransfer);
                hideOrShow(ensureElement("groundRow"),              isCardTransfer);
                hideOrShow(ensureElement("messageToReceiverRow"),   isAccountPayment() || !paymentReceiver.showMessageField());
                hideOrShow(ensureElement("receiverIdentifierRow"),  !isCardTransfer);

                var isBothAmount = isOurClient &amp;&amp; isConversion(this.fromCurrency, this.toCurrency);
                hideOrShow(ensureElement("buyAmountRow"),           !isBothAmount  &amp;&amp; !(isCardToAccount || isPhizExternalCardPaymentType))
                hideOrShow(ensureElement("sellAmountRow"),          !isBothAmount  &amp;&amp; (isCardToAccount || isPhizExternalCardPaymentType));

                if (!isBothAmount  &amp;&amp; !(isCardToAccount || isPhizExternalCardPaymentType))
                {
                    sellCurrency();
                }
                else if (!isBothAmount  &amp;&amp; (isCardToAccount || isPhizExternalCardPaymentType))
                {
                    buyCurrency();
                }

                <!-- меняем значение метки у суммы, для перевода c вклада на счет в сбербанке и на счет в другом банке
                должно отобаражаться метка "Сумма", а также для перевода на карту если валюты совпадают -->
                $("#sellAmountRow .paymentTextLabel").text(isBothAmount ? "Сумма в валюте списания" : "Сумма");
                $("#buyAmountRow .paymentTextLabel").text(isPhizExternalCardPaymentType ? "Сумма" : "Сумма в валюте зачисления");

                $("#sellAmountCurrency").text(currencySignMap.get(this.fromCurrency));
                $("#buyAmountCurrency").text(currencySignMap.get(this.toCurrency));
                getElement('buyAmountCurrency').value = this.toCurrency;

                //показываем/скрываем кнопку "Сохранить как регулярный платеж"
                //ДП доступны для переводов: карта/вклад - физ. лицо, только если счет списания не пуст
                var fromResource = ensureElement('fromResource').value

                <xsl:if test="$isTemplate != 'true'">
                    var supportedCommissionPaymentTypes = "<xsl:value-of select="dh:getSupportedCommissionPaymentTypes()"/>".split('|');
                    var paymentType;
                    if(receiverSubTypeButton == "externalAccount" &amp;&amp; fromResource.indexOf('card') != -1)
                        paymentType = "CardToOtherBank";
                    else if(receiverSubTypeButton == "ourAccount" &amp;&amp; fromResource.indexOf('card') != -1)
                        paymentType = "CardToOurBank";
                    else
                        paymentType = null;

                    if(paymentType!=null &amp;&amp; (this.fromCurrency == 'RUB' || this.fromCurrency == 'RUR'))
                        paymentType = 'Rur' + paymentType;

                    var isSupportedCommissionPaymentType = supportedCommissionPaymentTypes.contains(paymentType);
                    hideOrShow(ensureElement("postConfirmCommissionMessage"), !isSupportedCommissionPaymentType);
                    $("#buyAmountRow .paymentTextLabel").text(isSupportedCommissionPaymentType? "Сумма перевода": "Сумма в валюте зачисления");
                </xsl:if>

                function isConversion(fromCurrency, toCurrency)
                {
                    if (isEmpty(fromCurrency) || isEmpty(toCurrency))
                        return false;
                    return fromCurrency != toCurrency;
                }
            };
        }

        function isYandexPayment()
        {
            return receiverSubTypeButton == "yandexWallet" || receiverSubTypeButton == "yandexWalletOurContact" || receiverSubTypeButton == "yandexWalletByPhone";
        }

        function isAccountPayment()
        {
            return receiverSubTypeButton == 'ourAccount' || receiverSubTypeButton == 'externalAccount';
        }

        function isOurClientPaymentType()
        {
            return (receiverSubTypeButton == "" || receiverSubTypeButton == "ourCard" || receiverSubTypeButton == "ourPhone" || receiverSubTypeButton == "ourContact");
        }

        function refreshForm()
        {
            var toCurrency;
            var fromResource = getElement("fromResource");
            var fromCurrency = resourceCurrencies[fromResource.value];

            //перевод на карту в сбербанке или через номер мобильного телефона
            if (isOurClientPaymentType())
            {
                toCurrency  = getElementValue("externalCardCurrency");
            }
            else if(receiverSubTypeButton == "visaExternalCard" || receiverSubTypeButton == "masterCardExternalCard" || receiverSubTypeButton == "ourContactToOtherCard")
            {
                <!-- при переводе на карту в другом банке, валюта всегда рубли -->
                toCurrency  = 'RUB';
            }
            else
            {
                toCurrency  = getElementValue("receiverAccountCurrency");
            }

            if (receiverSubTypeButton == '' || receiverSubTypeButton == 'ourCard')
                $('.p2p-promo').show();
            else
                $('.p2p-promo').hide();
            var isCardToAccount = (fromResource.value.indexOf('card:') != -1) &amp;&amp; (isAccountPayment());

            var payment = new Payment(isCardToAccount, fromCurrency, toCurrency);
            payment.refresh();
        }

        //обновить список ресурсов клиента
        function updateFromResourse()
        {
            var fromResource = getElement("fromResource");
            var lastSelected = fromResource.options[fromResource.selectedIndex].value;
            fromResource.options.length = 0;
            var index = 0;

            for (var i=0; i &lt; cardList.length; i++)
            {
                var card = cardList[i];

                <!-- для переводов на карту в другой банк операция возможнна только для личных дебетовых карт(BUG050868 и для овердрафтных) -->
                if((receiverSubTypeButton == "visaExternalCard" || receiverSubTypeButton == "masterCardExternalCard" || receiverSubTypeButton == 'ourContactToOtherCard')  &amp;&amp; card.type != 'debit' &amp;&amp; card.type != 'overdraft')
                    continue;

                fromResource.options[index++] = createOption(cardList[i]);
                if (fromResource.options[index - 1].value == lastSelected)
                    fromResource.selectedIndex = index - 1;
            }

            if (isAccountPayment())
            {
               for (var i=0; i &lt; accountList.length; i++)
               {
                   fromResource.options[index++] = createOption(accountList[i]);
                   if (fromResource.options[index - 1].value == lastSelected)
                        fromResource.selectedIndex = index - 1;
               }
            }

            if(fromResource.length == 0)
            {
                fromResource.options[0] = new Option(receiverSubTypeButton == '' || receiverSubTypeButton == "visaExternalCard" || receiverSubTypeButton == "masterCardExternalCard" || receiverSubTypeButton == 'ourContactToOtherCard' ? "Нет доступных карт" : "Нет доступных счетов и карт","");
            }
            else if (lastSelected.indexOf('card:') == -1 &amp;&amp; lastSelected.indexOf('account:') == -1)
            {
                fromResource.options.add(new Option(receiverSubTypeButton == '' || receiverSubTypeButton == "visaExternalCard" || receiverSubTypeButton == "masterCardExternalCard"  || receiverSubTypeButton == 'ourContactToOtherCard' ? "Выберите карту списания" : "Выберите счет/карту списания",""), 0);
                fromResource.selectedIndex = 0;
            }
            hideError();
        }

        function updateReceiverSubType(newType)
        {
            $('#receiverSubType').val(newType);
        }

        function clickPaymentType(newReceiverSubType, clearReceiver)
        {
            receiverSubTypeButton = newReceiverSubType;
            var oldReceiverSubType = $('#receiverSubType').val();
            updateReceiverSubType(newReceiverSubType);
            $('#receiverSubTypeControl').find('div').removeClass('activeButton');

            var buttonPrefix = newReceiverSubType;
            if (newReceiverSubType == 'visaExternalCard' || newReceiverSubType == 'masterCardExternalCard')
                buttonPrefix = 'ourContactToOtherCard';

            $('#' + buttonPrefix + 'ReceiverSubTypeButton').addClass('activeButton');
            updateFromResourse();
            paymentReceiver.changePaymentType(oldReceiverSubType, newReceiverSubType, clearReceiver);
            refreshForm();
        }

        function hideError()
        {
            $("#fromResourceRow .paymentValue .errorDiv").hide();
            var fromResourceRow = document.getElementById("fromResourceRow");
            fromResourceRow.error = false;
            fromResourceRow.className = "form-row form-row-new";
        }

        //запись счета/карты в списке ресурсов клиента
        function createOption(resource)
        {
            var name = resource.number + ' ' + resource.name + ' ' + resource.amount + ' ' + resource.currency;
            return new Option(name, resource.id);
        }

        function buyCurrency()
        {
            var sellAmount  = ensureElement("sellAmount");
            var buyAmount   = ensureElement("buyAmount");
            var exactAmount = ensureElement("exactAmount");

            if (sellAmount.value != "")
            {
                buyAmount.value = sellAmount.value;
                sellAmount.value = "";
            }
            exactAmount.value  = "destination-field-exact"
        }

        function sellCurrency()
        {
            var buyAmount   = ensureElement("buyAmount");
            var sellAmount  = ensureElement("sellAmount");
            var exactAmount = ensureElement("exactAmount");
            if (buyAmount.value != "")
            {
                sellAmount.value = buyAmount.value;
                buyAmount.value = "";
            }
            exactAmount.value  = "charge-off-field-exact";
        }

        var oldExternalNumber = "";
        var externalNumber = "";
        var oldCurrencyMessage = [];

        function checkCurrency(params)
        {
            var url = document.webRoot + "/private/cards/currency/code.do";
            ajaxQuery(params, url, updateReceiverCardCurency, null, true);

            function updateReceiverCardCurency(currencyData)
            {
                if (trim(currencyData) == '')
                {
                    return false;
                }
                var htmlData = trim(currencyData).replace(/^&nbsp;+/, "");
                $("#currencyErrorDiv").html(htmlData);
                for (var i=0; i &lt; currencyErrorMessageAr.length; i++)
                {
                    payInput.fieldError("receiverIdentifier", currencyErrorMessageAr[i]);
                }
                oldCurrencyMessage = currencyErrorMessageAr;

                //обработка данных о валюте
                var currencyVal = $("#currency").val();
                var currency = getElement("externalCardCurrency");
                if (currencyErrorMessageAr.length > 0)   //пришла ошибка с описанием
                {
                    ensureElement("isErrorCurrency").value = "true";
                    currency.value = "";
                }
                else
                {
                    ensureElement("isErrorCurrency").value = "false";
                    currency.value = currencyVal;
                }
                refreshForm();
                return true;
            }
        }

        function checkAccountCurrency(account)
        {
            if (account.length &lt; 19)
                return;

            var accountCurrency = getElement("receiverAccountCurrency");
            var currency = currencies[account.substring(5, 8)];

            if (isEmpty(currency))
            {
                addError(EMPTY_ACCOUNT_ERROR);
                accountCurrency.value = "";
            }
            else
            {
                removeError(EMPTY_ACCOUNT_ERROR);
                accountCurrency.value = currency;
            }

            refreshForm();
        }

        function getBankByBIC()
        {
            var receiverBIC = getFieldValue("receiverBIC");
            ensureElement("bank").value = "";
            ensureElement("receiverCorAccount").value = "";
            if (!(/^\d{9}$/.test(receiverBIC)))
            {
                addError(REGEXP_BIC_MESSAGE);
                removeError(BANKS_NOT_FOUND_MESSAGE);
                return;
            }

            var url = document.webRoot + "/private/dictionary/banks/national.do";
            var params = "operation=button.getByBIC" + "&amp;field(BIC)=" + receiverBIC;

            ajaxQuery(params, url, updateBankInfo, "json");
        }

        function updateBankInfo(bank)
        {
            removeError(REGEXP_BIC_MESSAGE);
            if (isEmpty(bank))
            {
                addError(BANKS_NOT_FOUND_MESSAGE);
                return;
            }
            removeError(BANKS_NOT_FOUND_MESSAGE);

            ensureElement("bank").value = bank.name;
            ensureElement("receiverCorAccount").value = bank.account;
            if (bank["our"] == 'true')
                updateReceiverSubType('ourAccount');
            else
                updateReceiverSubType('externalAccount');
        }

        doOnLoad(function(){
            init();
        });
    </script>

</xsl:template>

<xsl:template match="/form-data" mode="view-simple-payment">

    <xsl:variable name="isBankAccountType" select="receiverSubType='ourAccount' or receiverSubType='externalAccount'"/>
    <xsl:variable name="state" select="state"/>

    <div class="confirmData">

        <xsl:if test="(state = 'DISPATCHED' or state = 'EXECUTED' or state = 'DELAYED_DISPATCH' or state = 'OFFLINE_DELAYED' or state = 'WAIT_CONFIRM' or state = 'REFUSED' or state = 'RECALLED' or state = 'UNKNOW') and $app != 'PhizIA'">
            <div class="payData">
                <div id="state" class="inlineBlock p2pState">
                    <div class="stateType state_{$state}"></div>
                    <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link stateColor_{$state}">
                        <xsl:call-template name="clientState2text">
                            <xsl:with-param name="code">
                                <xsl:value-of select="state"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </span>
                </div>


                <xsl:if test="state != 'WAIT_CONFIRM'">
                    <div class="payDate"><xsl:value-of  select="documentDate"/></div>
                </xsl:if>
                <div class="payDoc">Платежный документ № <xsl:value-of  select="documentNumber"/></div>
                <div class="clear"> </div>

               <div class="bankDetailData">
                    <xsl:variable name="osb" select="dep:getOsb(departmentId)"/>
                    <xsl:choose>
                        <xsl:when test="$osb != 'null'">
                            <div class="b_name"><xsl:value-of  select="dep:getNameFromOsb($osb)"/></div>
                            <xsl:variable name="bic" select="dep:getBicFromOsb($osb)"/>
                            <div>БИК: <xsl:value-of  select="$bic"/></div>
                            <div>Корр. счет: <xsl:value-of  select="dep:getCorrByBIC($bic)"/></div>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="defaultBic" select="dep:getDefaultBankBic()"/>
                            <div class="b_name"><xsl:value-of  select="dep:getDefaultBankName()"/></div>
                            <div>БИК: <xsl:value-of  select="$defaultBic"/></div>
                            <div>Корр. счет: <xsl:value-of  select="dep:getCorrByBIC($defaultBic)"/></div>
                        </xsl:otherwise>
                    </xsl:choose>
                </div>


            </div>
        </xsl:if>

        <xsl:if test="$app = 'PhizIA'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Номер документа</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="documentNumber"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата документа</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="documentDate"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

             <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="not($isBankAccountType)">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Получатель</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="receiverSubType = 'ourContact' or (receiverSubType = 'ourPhone' and contactName != '') or (receiverSubType = 'yandexWalletByPhone' and contactName != '')">
                            <xsl:variable name="phoneValue">
                                <xsl:choose>
                                    <xsl:when test="receiverSubType = 'ourPhone' or receiverSubType = 'yandexWalletByPhone'"><xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="externalPhoneNumber"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <table class="contactWidth">
                                <tr>
                                    <td colspan="2">
                                        <div class="linear viewPmntSujest">
                                            <xsl:variable name="isYandexPayment" select="receiverSubType = 'yandexWalletByPhone'"/>
                                            <xsl:call-template name="showContactTemplate">
                                                <xsl:with-param name="contactName" select="contactName"/>
                                                <xsl:with-param name="contactPhone" select="$phoneValue"/>
                                                <xsl:with-param name="isView" select="'true'"/>
                                                <xsl:with-param name="contactSberbank" select="contactSberbank"/>
                                                <xsl:with-param name="avatarPath" select="avatarPath"/>
                                                <xsl:with-param name="isYandex" select="$isYandexPayment"/>
                                            </xsl:call-template>
                                        </div>
                                    </td>
                                </tr>
                                <xsl:if test="receiverSubType != 'yandexWalletByPhone'">
                                    <tr>
                                        <td class="graySeparate">
                                            <div id="mbContactInfo" class="mbContactInfo ">
                                                <div id="mbContactInfoFIO" class="mbContactInfoFIO secondLineName"><xsl:value-of select="receiverName"/></div>
                                            </div>
                                        </td>
                                        <td class="graySeparate">
                                            <div id="mbContactInfoCard" class="mbContactInfoCard">
                                                <span class="cardIcon"></span>
                                                <div id="mbContactInfoCardEl" style="float:right;"><xsl:value-of  select="mask:getCutCardNumber(receiverAccount)"/></div>
                                            </div>
                                        </td>
                                    </tr>
                                </xsl:if>
                            </table>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourContactToOtherCard'">
                            <div class="linear viewPmntSujest">
                                <xsl:call-template name="showContactTemplate">
                                    <xsl:with-param name="contactName" select="contactName"/>
                                    <xsl:with-param name="contactCard" select="mask:getCutCardNumber(receiverAccount)"/>
                                    <xsl:with-param name="isView" select="'true'"/>
                                    <xsl:with-param name="contactSberbank" select="contactSberbank"/>
                                    <xsl:with-param name="avatarPath" select="avatarPath"/>
                                </xsl:call-template>
                            </div>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'masterCardExternalCard'">
                            <div class="linear viewPmntSujest">
                                <div class="cardMasterCard"></div>
                                <span class="payCardNum"><xsl:value-of select="mask:getCutCardNumber(receiverAccount)"/></span>
                            </div>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'visaExternalCard'">
                            <div class="linear viewPmntSujest">
                                <div class="cardVisa"></div>
                                <span class="payCardNum"><xsl:value-of select="mask:getCutCardNumber(receiverAccount)"/></span>
                            </div>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'yandexWallet'">
                            <div class="linear viewPmntSujest">
                                <div class="yandexPay"></div>
                                <span class="payCardNum"><xsl:value-of select="externalWalletNumber"/></span>
                            </div>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'yandexWalletOurContact'">
                            <div class="linear viewPmntSujest">
                                <xsl:call-template name="showContactTemplate">
                                    <xsl:with-param name="contactName" select="contactName"/>
                                    <xsl:with-param name="contactPhone" select="externalPhoneNumber"/>
                                    <xsl:with-param name="isView" select="'true'"/>
                                    <xsl:with-param name="contactSberbank" select="contactSberbank"/>
                                    <xsl:with-param name="avatarPath" select="avatarPath"/>
                                    <xsl:with-param name="isYandex" select="'true'"/>
                                </xsl:call-template>
                            </div>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'yandexWalletByPhone'">
                            <div class="linear viewPmntSujest">
                                <div class="yandexPay"></div>
                                <span class="clientPhoneNumber size18 payCardNum">
                                    +7 <xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>
                                </span>
                            </div>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourPhone'">
                            <table class="contactWidth">
                                <tr>
                                    <td colspan="3">
                                        <div class="linear viewPmntSujest">
                                            <span class="clientPhoneNumber size18">
                                                +7 <xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/>
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="graySeparate iconWidth">
                                        <div id="mbContactInfo" class="mbContactInfo">
                                            <div class="sugestAvatar whiteSmall">
                                                <div class="relative">
                                                    <xsl:choose>
                                                        <xsl:when test="avatarPath != ''">
                                                            <img class="float avatarIcon" src="{pru:getStaticImageUri()}/SMALL{avatarPath}"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <img class="float avatarIcon" src="{$resourceRoot}/commonSkin/images/profile/ICON.png"/>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                   <div class="roundAvatar"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="graySeparate">
                                        <div id="mbContactInfo" class="mbContactInfo marginLeft0">
                                            <div id="mbContactInfoFIO" class="mbContactInfoFIO"><xsl:value-of select="receiverName"/></div>
                                        </div>
                                    </td>
                                    <td class="graySeparate">
                                        <div id="mbContactInfoCard" class="mbContactInfoCard">
                                            <span class="cardIcon"></span>
                                            <div id="mbContactInfoCardEl" style="float:right;"><xsl:value-of  select="mask:getCutCardNumber(receiverAccount)"/></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourCard'">
                            <table class="contactWidth">
                                <tr>
                                    <td colspan="2" class="no-avatar">
                                        <div class="linear viewPmntSujest">
                                            <xsl:choose>
                                                <xsl:when test="substring(receiverAccount, 1, 1) = '4'"><div class="cardVisa"></div></xsl:when>
                                                <xsl:otherwise><div class="cardMasterCard"></div></xsl:otherwise>
                                            </xsl:choose>
                                            <span class="payCardNum"><xsl:value-of select="mask:getCutCardNumber(receiverAccount)"/></span>
                                        </div>
                                     </td>
                                </tr>
                                <tr>
                                    <td class="graySeparate iconWidth">
                                        <div id="mbContactInfo" class="mbContactInfo">
                                            <div class="sugestAvatar whiteSmall">
                                                <div class="relative">
                                                   <img class="float avatarIcon" src="{$resourceRoot}/commonSkin/images/profile/ICON.png"/>
                                                   <div class="roundAvatar"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="graySeparate">
                                        <div id="mbContactInfoFIO" class="mbContactInfoFIO floatNone"><xsl:value-of select="receiverName"/></div>
                                    </td>
                                </tr>
                            </table>
                        </xsl:when>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="$isBankAccountType">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Номер счета</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="au:getFormattedAccountNumber(receiverAccount)"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Фамилия</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="substring(receiverSurname, 1, 1)"/>.</b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Имя</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="receiverFirstName"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Отчество</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="receiverPatrName"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">ИНН</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="receiverINN"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Адрес</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="receiverAddress"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование банка</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="bank"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">БИК</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="receiverBIC"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Корр. счет</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <b><xsl:value-of  select="receiverCorAccount"/></b>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="$app = 'PhizIA'">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName"><b>Перевод</b></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="$isBankAccountType">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Назначение платежа</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear fixWightLinear">
                        <span class="word-wrap bold"><xsl:value-of select="ground"/></span>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет списания</xsl:with-param>
            <xsl:with-param name="readField">allWidth</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div class="linear">
                    <xsl:if test="not($fromAccountSelect= '')">
                        <b>
                            <xsl:choose>
                                <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                    <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                                </xsl:when>
                                <xsl:otherwise><xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/></xsl:otherwise>
                            </xsl:choose>
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                            <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                        </b>
                    </xsl:if>
                </div>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="isFullNameAmount" select="not($isBankAccountType) and fromResourceCurrency != buyAmountCurrency"/>

        <xsl:choose>
            <xsl:when test="$postConfirmCommission">
                <xsl:call-template name="transferSumRows">
                    <xsl:with-param name="fromResourceCurrency" select="fromResourceCurrency"/>
                    <xsl:with-param name="toResourceCurrency" select="buyAmountCurrency"/>
                    <xsl:with-param name="chargeOffAmount" select="sellAmount"/>
                    <xsl:with-param name="destinationAmount" select="buyAmount"/>
                    <xsl:with-param name="documentState" select="state"/>
                    <xsl:with-param name="exactAmount" select="exactAmount"/>
                    <xsl:with-param name="needUseTotalRow" select="'false'"/>
                    <xsl:with-param name="tariffPlanESB" select="tariffPlanESB"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="(exactAmount = 'charge-off-field-exact') or (exactAmount != 'destination-field-exact')">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">
                             Сумма<xsl:if test="$isFullNameAmount"> в валюте списания</xsl:if>
                        </xsl:with-param>
                        <xsl:with-param name="readField">allWidth</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="string-length(sellAmount)>0">
                                <div class="linear">
                                    <span class="summ">
                                        <xsl:value-of select="format-number(translate(sellAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mu:getCurrencySign(sellAmountCurrency)"/>
                                    </span>
                                </div>
                                <xsl:if test="string-length(commission)>0 and $isTemplate!='true'">
                                    <div class="commissionForPay fixWightLinear">
                                        Комиссия за перевод: <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
                                    </div>
                                </xsl:if>
                            </xsl:if>
                        </xsl:with-param>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="exactAmount = 'destination-field-exact'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">buyAmountRow</xsl:with-param>
                        <xsl:with-param name="rowName">Сумма<xsl:if test="not(receiverSubType = 'visaExternalCard' or receiverSubType = 'masterCardExternalCard' or receiverSubType = 'ourContactToOtherCard')"> в валюте зачисления</xsl:if></xsl:with-param>
                        <xsl:with-param name="readField">allWidth</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="string-length(buyAmount)>0">
                                <div class="linear">
                                    <span class="summ">
                                        <xsl:value-of select="format-number(translate(buyAmount, ',','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:value-of select="mu:getCurrencySign(buyAmountCurrency)"/>
                                    </span>
                                </div>
                                 <xsl:if test="string-length(commission)>0 and $isTemplate!='true'">
                                    <div class="commissionForPay fixWightLinear">
                                        Комиссия за перевод: <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
                                    </div>
                                </xsl:if>
                            </xsl:if>
                        </xsl:with-param>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="receiverSubType='ourCard' or receiverSubType='ourPhone' or receiverSubType='ourContact' or receiverSubType='ourContactToOtherCard'
                            or receiverSubType='yandexWalletOurContact' or receiverSubType='yandexWalletByPhone' or isOurBankCard = 'true'">
            <xsl:if test="string-length(messageToReceiver) > 0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">messageToReceiverRow</xsl:with-param>
                    <xsl:with-param name="rowName">&nbsp;
                        <div id="smsAvatarContainer" class="smsAvatarContainer">
                            <xsl:variable name="imagePath" select="pru:getAvatarPath('ICON')"/>
                            <xsl:value-of select="pru:buildUserImage($resourceRoot, $imagePath, 'ICON', false, 'float avatarIcon css3', '', '')" disable-output-escaping="yes"/>
                        </div>
                    </xsl:with-param>
                    <xsl:with-param name="readField">allWidth</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div class="linear">
                            <xsl:value-of select="messageToReceiver"/>&nbsp;
                        </div>
                        <xsl:if test="(state = 'EXECUTED' or state = 'DISPATCHED' or state = 'UNKNOW')">
                            <xsl:variable name="existMessageToReceiverStatus" select="string-length(messageToReceiverStatus) > 0"/>
                            <xsl:variable name="sentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'sent'"/>
                            <xsl:variable name="notSentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'not_sent'"/>
                            <xsl:choose>
                                <xsl:when test="$sentMessageToReceiverStatus">
                                    <div id="messageToReceiverStatus">
                                        <span class="messageToReceiverStatus sendMessState"
                                              onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                              onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                            Сообщение отправлено
                                        </span>
                                    </div>
                                </xsl:when>
                                <xsl:when test="$notSentMessageToReceiverStatus">
                                    <div id="messageToReceiverStatus">
                                        <span class="messageToReceiverStatus sendMessState"
                                              onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription');"
                                              onmouseout="hideLayer('messageToReceiverStatusDescription');">
                                            Сообщение не отправлено
                                        </span>
                                    </div>
                                </xsl:when>
                                <xsl:when test="$existMessageToReceiverStatus">
                                    <span class="messageToReceiverStatus sendMessState"><xsl:value-of select="messageToReceiverStatus"/></span>
                                </xsl:when>
                                <xsl:otherwise><span class="messageToReceiverStatus sendMessState">сообщение будет отправлено</span></xsl:otherwise>
                            </xsl:choose>

                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:if test="(state = 'EXECUTED' or state = 'DISPATCHED' or state = 'UNKNOW')">
                    <xsl:variable name="existMessageToReceiverStatus" select="string-length(messageToReceiverStatus) > 0"/>
                    <xsl:variable name="sentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'sent'"/>
                    <xsl:variable name="notSentMessageToReceiverStatus" select="$existMessageToReceiverStatus and messageToReceiverStatus = 'not_sent'"/>
                    <div id="messageToReceiverStatusDescription"
                         onmouseover="showLayer('messageToReceiverStatus','messageToReceiverStatusDescription','default');"
                         onmouseout="hideLayer('messageToReceiverStatusDescription');" class="layerFon stateDescription">
                        <div class="floatMessageHeader"></div>
                        <div class="layerFonBlock">
                            <xsl:choose>
                                <xsl:when test="$sentMessageToReceiverStatus">
                                    Сообщение успешно отправлено получателю средств.
                                </xsl:when>
                                <xsl:when test="$notSentMessageToReceiverStatus">
                                    Сообщение не удалось отправить получателю средств.
                                </xsl:when>
                            </xsl:choose>
                        </div>
                    </div>
                 </xsl:if>
            </xsl:if>
        </xsl:if>

        <xsl:if test="$app = 'PhizIA'">
            <xsl:if test="not($postConfirmCommission and state = 'SAVED')">
             <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">
                    Статус
                    <xsl:choose>
                        <xsl:when test="$isTemplate != 'true'">
                            платежа
                        </xsl:when>
                        <xsl:otherwise>
                            шаблона
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
                 <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                    <div id="state">
                        <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                            <xsl:call-template name="employeeState2text">
                                <xsl:with-param name="code">
                                    <xsl:value-of select="state"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </span>
                    </div>
                    </div>
                </xsl:with-param>
             </xsl:call-template>
            </xsl:if>
        </xsl:if>

        <xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowStyle">display: none</xsl:with-param>
                <xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of  select="admissionDate"/>
                </xsl:with-param>
            </xsl:call-template>
         </xsl:if>
    </div>
</xsl:template>

<xsl:template name="employeeState2text">
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
        <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
        <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
        <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
        <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
    </xsl:choose>
</xsl:template>

<xsl:template name="clientState2text">
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='SAVED'">Черновик</xsl:when>
        <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
        <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
        <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
        <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM_TEMPLATE'">Активный</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='DRAFTTEMPLATE'">Черновик</xsl:when>
        <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
    </xsl:choose>
</xsl:template>

    <!--добавление option для select'a-->
    <xsl:template name="addOption">
    <xsl:param name="value"/>
    <xsl:param name="selected"/>
    <xsl:param name="source"/>

    <option>
        <xsl:attribute name="value">
            <xsl:value-of select="$value"/>
        </xsl:attribute>
        <xsl:if test="contains($value, $selected)">
            <xsl:attribute name="selected"/>
        </xsl:if>
        <xsl:call-template name="monthsToString">
            <xsl:with-param name="value"  select="$value"/>
            <xsl:with-param name="source" select="$source"/>
        </xsl:call-template>
    </option>
</xsl:template>

    <!--получение списка месяцев в строку-->
    <xsl:template name="monthsToString">
    <xsl:param name="value"/>
    <xsl:param name="source"/>

    <xsl:variable name="delimiter" select="'|'"/>

    <xsl:choose>
        <xsl:when test="contains($value, $delimiter)">
            <xsl:for-each select="xalan:tokenize($value, $delimiter)">
                <xsl:value-of select="concat($source[@key = current()]/text(), ' ')"/>
            </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="concat($source[@key = $value]/text(), ' ')"/>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

    <!-- шаблон формирующий div описания -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

    <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

   <xsl:for-each select="$nodeText/node()">

   <xsl:choose>
		<xsl:when test=" name() = 'cut' and position() = 1 ">
		    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
		</xsl:when>
        <xsl:when test="name() = 'cut' and position() != 1">
            <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
        </xsl:when>
   		<xsl:otherwise>
		<xsl:copy />
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>                      <!--идентификатор строки-->
	<xsl:param name="rowName"/>                     <!--описание поля-->
	<xsl:param name="rowNameHelp"/>                 <!--подсказка к полю-->
	<xsl:param name="rowValue"/>                    <!--данные-->
	<xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
	<xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
	<xsl:param name="spacerClass" select="''"/>     <!-- доп класс -->
    <xsl:param name="spacerHint"/>                  <!-- Подсказка к полю ввода -->
    <xsl:param name="readField"/>                   <!-- нередактируемые поля -->
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
    <xsl:param name="isAllocate" select="'false'"/>  <!-- Выделить при нажатии -->

	<xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
	<!-- Определение имени инпута или селекта передаваемого в шаблон -->
	<!-- inputName - fieldName или имя поле вытащеное из rowValue -->
	<xsl:variable name="inputName">
	<xsl:choose>
		<xsl:when test="string-length($fieldName) = 0">
				<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
					<xsl:value-of select="$nodeRowValue/input/@name" />
					<xsl:if test="count($nodeRowValue/select[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/select/@name" />
					</xsl:if>
                    <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/textarea/@name" />
					</xsl:if>
				</xsl:if>
		</xsl:when>
		<xsl:otherwise>
				<xsl:copy-of select="$fieldName"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

    <xsl:variable name="readonly">
		<xsl:choose>
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

 <xsl:variable name="styleClass">
		<xsl:choose>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row form-row-new'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition form-row-new'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

<div class="{$styleClass}">
    <xsl:if test="string-length($lineId) > 0">
        <xsl:attribute name="id">
            <xsl:copy-of select="$lineId"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="string-length($rowStyle) > 0">
        <xsl:attribute name="style">
            <xsl:copy-of select="$rowStyle"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

    <xsl:if test="$spacerClass != ''">
        <div class="{$spacerClass}"></div>
    </xsl:if>

    <xsl:if test="$spacerHint != ''">
        <div class="spacerHint">
            <xsl:copy-of select="$spacerHint"/>

        </div>
    </xsl:if>
	<div class="paymentLabelNew">
	    <span class="paymentTextLabel">
	        <xsl:if test="string-length($id) > 0">
	            <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
            <xsl:if test="string-length($rowName) > 0">
                <xsl:copy-of select="$rowName"/>
            </xsl:if>
	    </span>
        <xsl:if test="string-length($rowNameHelp) > 0">
            <xsl:copy-of select="$rowNameHelp"/>
        </xsl:if>
    </div>
	<div class="paymentValue paymentValueNew">
                <div class="paymentInputDiv autoInputWidth {$readField}"><xsl:copy-of select="$rowValue"/>

                    <xsl:if test="$readonly = 0 and $mode = 'edit'">
                        <xsl:call-template name="buildDescription">
                            <xsl:with-param name="text" select="$description"/>
                        </xsl:call-template>
                    </xsl:if>
                    <xsl:if test="$mode = 'edit'">
                        <script type="text/javascript">
                            $(document).ready(function(){
                                $('.errorRed').hover(function(){
                                        $(this).find('.errorDiv').addClass('autoHeight');
                                        $(this).find('.showFullText').hide();
                                   }, function() {
                                        $(this).find('.errorDiv').removeClass('autoHeight');
                                        $(this).find('.showFullText').show();
                                });
                            });
                        </script>
                    </xsl:if>
                    <div class="errorDivBase errorDivPmnt">
                        <div class="errorRed">
                            <div class="errMsg">
                                <div class="errorDiv"></div>
                                <div class="showFullText">
                                    <div class="pointers"></div>
                                </div>
                            </div>
                            <div class="errorRedTriangle"></div>
                        </div>
                    </div>
                </div>
	</div>
    <div class="clear"></div>
</div>

<!-- Устанавливаем события onfocus поля -->
	<xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>

</xsl:template>

<xsl:template name="titleRow">
    <xsl:param name="lineId"/>
    <xsl:param name="rowName"/>
    <xsl:param name="rowValue"/>
    <div id="{$lineId}" class="{$styleClassTitle}">
        <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
    </div>
</xsl:template>

<xsl:template name="receiverSubTypeSelect">
    <xsl:param name="recType"/>
    <xsl:param name="availableOtherCardPayment"/>
    <xsl:param name="availableYandex"/>

    <div id='receiverSubTypeControl' class="bigTypecontrol">
        <div id="ReceiverSubTypeButton" class="inner firstButton">
            <xsl:if test="$byTemplate != 'true'">
                <xsl:attribute name="onclick">clickPaymentType('', true);</xsl:attribute>
            </xsl:if>
            <xsl:if test="$recType='' or $recType='ourContact' or $recType='ourPhone' or $recType='ourCard'">
                <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
            </xsl:if>
            Клиенту Сбербанка
        </div>
        <xsl:if test="$availableYandex">
            <div id="yandexWalletReceiverSubTypeButton" class="inner hoverButton">
                <xsl:if test="$byTemplate != 'true'">
                    <xsl:attribute name="onclick">clickPaymentType('yandexWallet', true);</xsl:attribute>
                </xsl:if>
                <xsl:if test="$recType='yandexWallet' or $recType='yandexWalletOurContact' or $recType='yandexWalletByPhone'">
                    <xsl:attribute name="class">inner hoverButton activeButton</xsl:attribute>
                </xsl:if>
                На Яндекс.Деньги
            </div>
        </xsl:if>
        <xsl:if test="$availableOtherCardPayment">
            <div id="ourContactToOtherCardReceiverSubTypeButton" class="inner hoverButton">
                <xsl:if test="$byTemplate != 'true'">
                    <xsl:attribute name="onclick">clickPaymentType('ourContactToOtherCard', true);</xsl:attribute>
                </xsl:if>
                <xsl:if test="$recType='masterCardExternalCard' or $recType='visaExternalCard' or $recType='ourContactToOtherCard'">
                    <xsl:attribute name="class">inner hoverButton activeButton</xsl:attribute>
                </xsl:if>
                На карту другого банка
            </div>
        </xsl:if>
        <div id="ourAccountReceiverSubTypeButton" class="inner lastButton">
            <xsl:if test="$byTemplate != 'true'">
                <xsl:attribute name="onclick">clickPaymentType('ourAccount', true);</xsl:attribute>
            </xsl:if>
            <xsl:if test="$recType='ourAccount' or $recType='externalAccount'">
                <xsl:attribute name="class">inner lastButton activeButton</xsl:attribute>
            </xsl:if>
            На банковский счет
        </div>
    </div>
    <div class="clear"></div>
</xsl:template>

<xsl:template name="hintTemplate">
    <xsl:param name="hintText"/>
    <script type="text/javascript">
        function closeHint()
        {
            $('.hintsBlock').hide();
        }
    </script>
    <div class="showHintBlock paymentHints">
        <a href="#" class="imgHintBlock newHints" onclick="return false;"></a>
        <div class="hintsBlock" style="display: none;">
            <div class="hintShadowTriangle"></div>
            <div class="workspace-box hintShadow">
                <div class="hintShadowRT r-top">
                    <div class="hintShadowRTL r-top-left">
                        <div class="hintShadowRTR r-top-right">
                            <div class="hintShadowRTC r-top-center">
                                <div class="closeHint" onclick="closeHint();"></div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="hintShadowRCL r-center-left">
                    <div class="hintShadowRCR r-center-right">
                        <div class="hintShadowRC r-content">
                            <xsl:copy-of select="$hintText"/>
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
                <div class="hintShadowRBL r-bottom-left">
                    <div class="hintShadowRBR r-bottom-right">
                        <div class="hintShadowRBC r-bottom-center"></div>
                    </div>
                 </div>
            </div>
        </div>
    </div>
</xsl:template>

<xsl:template name="topHint">
    <xsl:param name="topHintText" select="''"/>
    <xsl:param name="topHintValue" select="''"/>
    <xsl:param name="addHintClass"/>
    <xsl:param name="topHintId"/>
    <a href="#" class="topHint" onclick="return false;">

       <xsl:choose>
			<xsl:when test="$topHintId = ''">
				<span><xsl:value-of select="$topHintText"/></span>
			</xsl:when>
			<xsl:otherwise>
				<span id="{$topHintId}Span"><xsl:value-of select="$topHintText"/></span>
			</xsl:otherwise>
		</xsl:choose>

        <div class="topHintsBlock {$addHintClass}">
            <div class="hintShadowTriangle"></div>
            <div class="workspace-box hintShadow">
                <div class="hintShadowRT r-top">
                    <div class="hintShadowRTL r-top-left">
                        <div class="hintShadowRTR r-top-right">
                            <div class="hintShadowRTC r-top-center"></div>
                        </div>
                    </div>
                </div>
                <div class="hintShadowRCL r-center-left">
                    <div class="hintShadowRCR r-center-right">
                        <div class="hintShadowRC r-content">
                            <div class="topPosition" id="{$topHintId}">
                                <xsl:value-of select="$topHintValue"/>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
                <div class="hintShadowRBL r-bottom-left">
                    <div class="hintShadowRBR r-bottom-right">
                        <div class="hintShadowRBC r-bottom-center"></div>
                    </div>
                 </div>
            </div>
        </div>
    </a>
</xsl:template>

<xsl:template name="roundBorder">
    <xsl:param name="content"/>

    <div class="workspace-box bluePointer">
        <div class="bluePointerRT r-top">
            <div class="bluePointerRTL r-top-left">
                <div class="bluePointerRTR r-top-right">
                    <div class="bluePointerRTC r-top-center">
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="bluePointerRCL r-center-left">
            <div class="bluePointerRCR r-center-right">
                <div class="bluePointerRC r-content">
                    <xsl:copy-of select="$content"/>

                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="bluePointerRBL r-bottom-left">
            <div class="bluePointerRBR r-bottom-right">
                <div class="bluePointerRBC r-bottom-center"></div>
            </div>
         </div>
    </div>
</xsl:template>

<xsl:template name="errorContainer">
    <xsl:param name="sujestHintText"/>
    <div class="clear"></div>
    <div id="errorContainer" class="sujestHint">
        <div class="workspace-box roundYellow">
            <div class="roundYellowRT r-top">
                <div class="roundYellowRTL r-top-left">
                    <div class="roundYellowRTR r-top-right">
                        <div class="roundYellowRTC r-top-center">
                            <div class="clear"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="roundYellowRCL r-center-left">
                <div class="roundYellowRCR r-center-right">
                    <div class="roundYellowRC r-content">
                        <div class="sujestHintText">
                            <xsl:copy-of select="$sujestHintText"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="roundYellowRBL r-bottom-left">
                <div class="roundYellowRBR r-bottom-right">
                    <div class="roundYellowRBC r-bottom-center"></div>
                </div>
             </div>
        </div>

    </div>
</xsl:template>

<xsl:template name="showContactTemplate">
    <xsl:param name="isView"/>
    <xsl:param name="contactName"/>
    <xsl:param name="contactPhone"/>
    <xsl:param name="contactCard"/>
    <xsl:param name="contactSberbank"/>
    <xsl:param name="avatarPath"/>
    <xsl:param name="isYandex" select="'false'"/>

    <xsl:variable name="additClass"><xsl:if test="$isView = 'true'">float</xsl:if></xsl:variable>
    <table class="sugestTbl">
        <tr>
            <td>
                <xsl:if test="$isView != 'true' or $isYandex != 'true'">
                    <div class="sugestAvatar" id="contactAvatarContainer">
                        <div class="relative {$additClass}">
                            <xsl:choose>
                                <xsl:when test="$isView = 'true'">
                                    <xsl:choose>
                                        <xsl:when test="$avatarPath != ''">
                                            <img class="float avatarIcon" id="contactAvatar" src="{pru:getStaticImageUri()}/SMALL{$avatarPath}"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <img class="float avatarIcon" src="{$resourceRoot}/commonSkin/images/profile/SMALL.png"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:when>
                                <xsl:otherwise>
                                    <img class="float avatarIcon" id="contactAvatar"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <div class="roundAvatar"></div>
                        </div>
                    </div>
                </xsl:if>
                <xsl:if test="$isView != 'true' or $isYandex = 'true'">
                    <div id="yandexIcon"></div>
                </xsl:if>
            </td>
            <td>
                <div class="float">
                    <div id="selectedContactNameEl" class="clientName"><xsl:copy-of select="$contactName"/></div>
                    <div class="clear"></div>
                    <xsl:choose>
                        <xsl:when test="$isView = 'true'">
                            <xsl:choose>
                                <xsl:when test="$contactCard != ''">
                                    <span class="float clientNumber contactCard" id="selectedContactCardEl"><xsl:copy-of select="$contactCard"/></span>
                                </xsl:when>
                                <xsl:otherwise>
                                    <span class="float clientNumber" id="selectedContactPhone">+7 <span id="selectedContactPhoneEl"><xsl:copy-of select="$contactPhone"/></span></span>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="$contactSberbank = 'true'">
                                <span id="isOurClient"></span>
                            </xsl:if>
                        </xsl:when>
                        <xsl:otherwise>
                            <span class="float clientNumber" id="selectedContactPhone">+7 <span id="selectedContactPhoneEl"> </span></span>
                            <div id="contactCardContainer">
                                <span class="float clientNumber contactCard" id="selectedContactCardEl"> </span>
                                <span class="float clientNumber contactCard" id="NoCardEl" style="display:none;">
                                    <span class="noCard"></span> нет сохраненной карты
                                </span>
                            </div>
                            <span id="isOurClient"></span>
                        </xsl:otherwise>
                    </xsl:choose>
                </div>
            </td>
        </tr>
    </table>
</xsl:template>
</xsl:stylesheet>

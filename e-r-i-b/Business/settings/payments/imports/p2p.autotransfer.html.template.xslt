<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<!-- шаблоны полей для платежей -->
<!-- подключается тэгом <xsl:import href="commonTypes.html.template.xslt"/> -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mf="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:xth="java://com.rssl.phizic.business.documents.metadata.XmlTransformResourceHelper"
                xmlns:pru="java://com.rssl.phizic.business.profile.Utils"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:import href="commonTypes.html.template.xslt"/>
    <xsl:import href="p2p.html.template.xslt"/>

    <xsl:output method="html" version="1.0" indent="yes"/>
    <xsl:param name="loginedFromEmployeeByCard" select="loginedFromEmployeeByCard"/>

    <!--шаблон выбора получателя платежа-->
    <xsl:template name="select-receiver-template">
        <xsl:param name="severalReceiverOnClick"/>
        <xsl:param name="phReceiverOnClick"/>
        <div id='receiverTypeControl' class="bigTypecontrol">
            <div id="severalReceiverTypeButton" class="inner firstButton">
                <xsl:if test="string-length($severalReceiverOnClick) > 0">
                    <xsl:attribute name="onclick">
                        <xsl:value-of select="$severalReceiverOnClick"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="receiverType='several'">
                    <xsl:attribute name="class">inner firstButton activeButton</xsl:attribute>
                </xsl:if>
                На свою карту
            </div>
            <div id="phReceiverTypeButton" class="inner lastButton">
                <xsl:if test="string-length($phReceiverOnClick) > 0">
                    <xsl:attribute name="onclick">
                        <xsl:value-of select="$phReceiverOnClick"/>
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="receiverType='ph'">
                    <xsl:attribute name="class">inner lastButton activeButton</xsl:attribute>
                </xsl:if>
                Клиенту Сбербанка
            </div>
        </div>
        <div class="clear"></div>
    </xsl:template>

    <!--шаблон поиска получателя платежа-->
    <xsl:template name="select-contact-receiver-template">
        <xsl:param name="availableAddressBook"/>

        <xsl:call-template name="p2p-StandartRow">
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
                    <xsl:with-param name="topHintText">
                        номер карты
                    </xsl:with-param>
                    <xsl:with-param name="topHintValue">
                        Введите 15-19 цифр номера карты получателя
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:with-param>

            <xsl:with-param name="rowName"><div class="float">Получатель</div></xsl:with-param>

            <xsl:with-param name="rowValue">
                <div class="float itemsWidth">
                    <div class="contactData">
                        <input type="hidden" name="externalContactId"       id="externalContactId"      value="{externalContactId}"/>
                        <input type="hidden" name="externalPhoneNumber"     id="externalPhoneNumber"    value="{externalPhoneNumber}"/>
                        <input type="hidden" name="externalCardNumber"      id="externalCardNumber"     value="{externalCardNumber}"/>

                        <span id="receiverIdentifierContaner"></span>
                        <a class="cancelInput" id="cancelIdentifierInput" onclick="paymentReceiver.cancelInput();" style="display: none;"></a>
                        <div class="clear"></div>

                        <div id="enteredIdentifier"  class="float" style="display:none;" onclick="paymentReceiver.editEnteredIdentifier();"
                                 onmouseover="$('#editEnteredIdentifierEl').show();" onmouseout="$('#editEnteredIdentifierEl').hide();">
                        <xsl:call-template name="roundBorder">
                            <xsl:with-param name="content">
                                <span id="cardIcon"></span><div id="enteredIdentifierContent"></div>
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

                        <div class="clear"></div>
                        <xsl:call-template name="errorContainer">
                            <xsl:with-param name="sujestHintText">
                                <div class="logicError" id="notOurClientError">
                                    Данный номер телефона не зарегистрирован ни за одним клиентом Сберанка.
                                </div>

                                <div class="logicError" id="notOurCardError">
                                    Похоже, это не карта клиента Сбербанка.
                                    Проверьте введенный номер карты или укажите другого получателя
                                </div>

                                <div class="logicError" id="noCardErrorOurClient">
                                    Укажите другого получателя или введите полный номер карты вручную
                                </div>

                                <div class="logicError" id="noCardErrorNotOurClient">
                                    Укажите другого получателя или введите полный номер карты вручную
                                </div>

                                <div class="logicError" id="isOurClientError">
                                    Данный номер телефона зарегистрирован за клиентом Сбербанка.
                                </div>

                                <div class="logicError" id="notFoundClientError">
                                    Пользователь с таким номером телефона не найден.
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
                <xsl:if test="$availableAddressBook = 'true'">
                    <span class="openAddressBook floatRight" onclick="paymentReceiver.openAddressBook();">
                        <span class="iconAB"></span>
                        <span>ВЫБРАТЬ ИЗ АДРЕСНОЙ КНИГИ</span>
                    </span>
                </xsl:if>
            </xsl:with-param>
            <xsl:with-param name="spacerClass" select="'spacer'"/>
            <xsl:with-param name="isNeedHint">true</xsl:with-param>
            <xsl:with-param name="textHint">
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
    </xsl:template>

    <!--шаблон отображения информации по получателю-->
    <xsl:template name="view-receiver-info-template">

        <xsl:if test="receiverType = 'ph'">
            <xsl:call-template name="p2p-StandartRow">
                <xsl:with-param name="rowName">Получатель</xsl:with-param>
                <xsl:with-param name="readField">allWidth</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="receiverSubType = 'ourContact' or (receiverSubType = 'ourPhone' and contactName != '')">
                            <xsl:variable name="phoneValue">
                                <xsl:choose>
                                    <xsl:when test="receiverSubType = 'ourPhone'"><xsl:value-of select="mpnu:getCutPhoneForAddressBook(externalPhoneNumber)"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="externalPhoneNumber"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <table class="contactWidth">
                                <tr>
                                    <td colspan="2">
                                        <div class="linear viewPmntSujest">
                                            <xsl:call-template name="showContactTemplate">
                                                <xsl:with-param name="contactName"      select="contactName"/>
                                                <xsl:with-param name="contactPhone"     select="$phoneValue"/>
                                                <xsl:with-param name="avatarPath"       select="avatarPath"/>
                                                <xsl:with-param name="isView"           select="'true'"/>
                                                <xsl:with-param name="contactSberbank"  select="contactSberbank"/>
                                            </xsl:call-template>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="graySeparate">
                                        <div id="mbContactInfo" class="mbContactInfo">
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
                                                            <xsl:variable name="rRoot">
                                                                <xsl:value-of select="concat($resourceRoot, '/commonSkin/images/profile/ICON.png')"/>
                                                            </xsl:variable>
                                                            <img class="float avatarIcon" src="{$rRoot}"/>
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
                                                    <xsl:variable name="rRoot">
                                                        <xsl:value-of select="concat($resourceRoot, '/commonSkin/images/profile/ICON.png')"/>
                                                    </xsl:variable>
                                                    <img class="float avatarIcon" src="{$rRoot}"/>
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

                            <xsl:if test="$documentState = 'INITIAL' or $documentState = 'INITIAL_LONG_OFFER'">
                                <input type="hidden" name="externalCardNumber"  id="externalCardNumber" value="{externalCardNumber}"/>
                                <input type="hidden" name="receiverAccount"     id="receiverAccount"    value="{receiverAccount}"/>
                                <input type="hidden" name="receiverName"        id="receiverName"       value="{receiverName}"/>
                            </xsl:if>
                        </xsl:when>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="receiverType = 'several'">
            <xsl:choose>
                <xsl:when test="$documentState = 'INITIAL' or $documentState = 'INITIAL_LONG_OFFER'">
                    <xsl:call-template name="p2p-StandartRow">
                        <xsl:with-param name="readField">allWidth</xsl:with-param>
                        <xsl:with-param name="rowName">Карта зачисления</xsl:with-param>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="resourceLink" select="xth:getResourceAsStringByLinkCode(toResourceLink)"/>
                            <xsl:if test="string-length($resourceLink) > 0">
                                <div class="bold linear">
                                    <xsl:variable name="resourceNumber">
                                        <xsl:call-template name="link-property">
                                            <xsl:with-param name="source"   select="$resourceLink"/>
                                            <xsl:with-param name="name"     select="'number'"/>
                                        </xsl:call-template>
                                    </xsl:variable>
                                    <xsl:value-of select="mask:getCutCardNumber($resourceNumber)"/>

                                    <xsl:variable name="resourceName">
                                        <xsl:call-template name="link-property">
                                            <xsl:with-param name="source"   select="$resourceLink"/>
                                            <xsl:with-param name="name"     select="'name'"/>
                                        </xsl:call-template>
                                    </xsl:variable>
                                    &nbsp;[<xsl:value-of select="$resourceName"/>]&nbsp;

                                    <xsl:variable name="resourceCurrency">
                                        <xsl:call-template name="link-property">
                                            <xsl:with-param name="source"   select="$resourceLink"/>
                                            <xsl:with-param name="name"     select="'currencyCode'"/>
                                        </xsl:call-template>
                                    </xsl:variable>
                                    <xsl:value-of select="mf:getCurrencySign($resourceCurrency)"/>
                                </div>
                                <input type="hidden" name="toResourceLink"  value="{toResourceLink}"/>
                                <input type="hidden" name="toResource"      value="{toResourceLink}"/>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="p2p-StandartRow">
                        <xsl:with-param name="rowName">Карта зачисления</xsl:with-param>
                        <xsl:with-param name="readField">allWidth</xsl:with-param>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                        <xsl:with-param name="rowValue">
                            <xsl:if test="string-length(toAccountSelect) > 0">
                                <div class="bold linear">
                                    <xsl:value-of select="mask:getCutCardNumber(toAccountSelect)"/>
                                    &nbsp;[<xsl:value-of select="toAccountName"/>]&nbsp;
                                    <xsl:value-of select="mf:getCurrencySign(toResourceCurrency)"/>
                                </div>
                            </xsl:if>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>

    <!--шаблон отображения информации по платильщику-->
    <xsl:template name="view-payer-info-template">
        <xsl:choose>
            <xsl:when test="$documentState = 'INITIAL' or $documentState = 'INITIAL_LONG_OFFER'">
                <xsl:call-template name="p2p-StandartRow">
                    <xsl:with-param name="rowName">Карта списания</xsl:with-param>
                    <xsl:with-param name="readField">allWidth</xsl:with-param>
                    <xsl:with-param name="spacerClass" select="'spacer'"/>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="resourceLink" select="xth:getResourceAsStringByLinkCode(fromResourceLink)"/>
                        <xsl:if test="string-length($resourceLink) > 0">
                            <div class="bold linear">
                                <xsl:variable name="resourceNumber">
                                    <xsl:call-template name="link-property">
                                        <xsl:with-param name="source"   select="$resourceLink"/>
                                        <xsl:with-param name="name"     select="'number'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                <xsl:value-of select="mask:getCutCardNumber($resourceNumber)"/>

                                <xsl:variable name="resourceName">
                                    <xsl:call-template name="link-property">
                                        <xsl:with-param name="source"   select="$resourceLink"/>
                                        <xsl:with-param name="name"     select="'name'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                &nbsp;[<xsl:value-of select="$resourceName"/>]&nbsp;

                                <xsl:variable name="resourceCurrency">
                                    <xsl:call-template name="link-property">
                                        <xsl:with-param name="source"   select="$resourceLink"/>
                                        <xsl:with-param name="name"     select="'currencyCode'"/>
                                    </xsl:call-template>
                                </xsl:variable>
                                <xsl:value-of select="mf:getCurrencySign($resourceCurrency)"/>
                            </div>
                            <input type="hidden" name="fromResourceLink"    value="{fromResourceLink}"/>
                            <input type="hidden" name="fromResource"        value="{fromResourceLink}"/>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="p2p-StandartRow">
                    <xsl:with-param name="rowName">Карта списания</xsl:with-param>
                    <xsl:with-param name="readField">allWidth</xsl:with-param>
                    <xsl:with-param name="spacerClass" select="'spacer'"/>
                    <xsl:with-param name="rowValue">
                        <xsl:if test="string-length(fromAccountSelect) > 0">
                            <div class="bold linear">
                                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                                &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(fromResourceCurrency)"/>
                            </div>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!--шаблон отображения информации по периодичности исполнения-->
    <xsl:template name="view-event-template">
        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="readField">allWidth</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div class="linear line20">
                    Перевод будет осуществляться<br/>
                    <span id="report1" class="report1Class bold"></span><br/>
                    <span id="report2" class="report2Class"></span>
                </div>
                <xsl:if test="$documentState = 'INITIAL' or $documentState = 'INITIAL_LONG_OFFER'">
                    <input type="hidden" name="longOfferEventType" id="longOfferEventType" value="{longOfferEventType}"/>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--шаблон ввода наименования автоперевода-->
    <xsl:template name="edit-autotransfer-name-template">
        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="lineId">autoSubNameRow</xsl:with-param>
            <xsl:with-param name="isNeedHint">true</xsl:with-param>
            <xsl:with-param name="textHint">Данное название будет отображаться в SMS-оповещениях по услуге и в общем списке автоплатежей</xsl:with-param>
            <xsl:with-param name="rowName"><div class="float">Название</div>
            </xsl:with-param>
            <xsl:with-param name="spacerClass" select="'spacer'"/>
            <xsl:with-param name="rowValue">
                <xsl:variable name="autoSubName" select="autoSubName"/>
                <span id="autoSubNameView" class="size16 bold">
                    <xsl:if test="string-length($autoSubName) > 0">
                        <xsl:value-of select="$autoSubName"/>
                    </xsl:if>
                </span>
                <span id="productNameText" name="productNameText" class="productTitleMargin word-wrap payViewPadd productTitleDetailInfoText">
                    <a class="productTitleDetailInfoEditBullet" onclick="showEditSubscriptionName();"></a>
                </span>
                <span id="productNameEdit" name="productNameEdit" class="productTitleMargin">
                    <input type="text" id="autoSubName" name="autoSubName" value="{$autoSubName}" size="30" maxlength="50"/>&nbsp;
                </span>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--шаблон просмотра имени автоперевода-->
    <xsl:template name="view-autotransfer-name-template">
        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="lineId">autoSubNameRow</xsl:with-param>
            <xsl:with-param name="rowName">Название</xsl:with-param>
            <xsl:with-param name="readField">allWidth</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div class="linear line20">
                    <xsl:value-of select="autoSubName"/>&nbsp;
                    <xsl:if test="$documentState = 'INITIAL' or $documentState = 'INITIAL_LONG_OFFER'">
                        <input type="hidden" name="autoSubName" id="autoSubName" value="{autoSubName}"/>
                    </xsl:if>
                </div>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--шаблон просмотра сыммы по документу-->
    <xsl:template name="view-sell-amount-template">
        <xsl:call-template name="p2p-StandartRow">
            <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
            <xsl:with-param name="rowName">Сумма</xsl:with-param>
            <xsl:with-param name="readField">allWidth</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="string-length(sellAmount)>0">
                    <div class="fixWightLinear">
                        <div class="linear">
                            <span class="summ">
                                <xsl:value-of select="format-number(translate(sellAmount, ', ','.'), '### ##0,00', 'sbrf')"/>&nbsp;
                                <xsl:value-of select="mf:getCurrencySign(sellAmountCurrency)"/>
                            </span>
                            <br/>
                        </div>
                        <div class="commissionForPay">
                            <xsl:choose>
                                <xsl:when test="string-length(commission) > 0">
                                    <span name="commission">
                                        Комиссия: <xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mf:getCurrencySign(commissionCurrency)"/>
                                    </span>
                                </xsl:when>
                                <xsl:otherwise>
                                    <span name="commission">
                                        Комиссия: 0-1% от суммы перевода. Точная сумма комиссии будет рассчитана и указана в SMS перед ближайшим переводом.
                                    </span>
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </div>
                    <xsl:if test="$documentState = 'INITIAL' or $documentState = 'INITIAL_LONG_OFFER'">
                        <input type="hidden" name="sellAmount" id="sellAmount" value="{sellAmount}"/>
                    </xsl:if>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <!--источники списания/зачисления клиента-->
    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeCards"/>
        <xsl:param name="activeCardsNotCredit"/>
        <xsl:param name="resourceView"/>
        <xsl:param name="application-mode"/>

        <xsl:variable name="loginedCard"      select="document('entered-p2p-autotransfer-cards.xml')/entity-list/*"/>
        <xsl:variable name="defaultFunctions" select="''"/>

        <select id="{$name}" name="{$name}">
            <xsl:attribute name="class">
                <xsl:if test="$application-mode = 'PhizIC'">
                    <xsl:value-of select="'selectSbtM'"/>
                </xsl:if>
            </xsl:attribute>
            <xsl:attribute name="onchange">
                <xsl:value-of select="$defaultFunctions"/>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="count($activeCards) = 0 and count($activeCardsNotCredit) = 0">
                    <option value="">Нет доступных карт</option>
                </xsl:when>
                <xsl:when test="(string-length($accountNumber) = 0 and string-length($linkId) = 0) and $name = 'fromResource' and (($loginedFromEmployeeByCard and count($loginedCard) = 0) or (not($loginedFromEmployeeByCard) and $application-mode = 'PhizIA'))">
                    <option value="">Выберите карту <xsl:value-of select="$resourceView"/></option>
                </xsl:when>
                <xsl:when test="(string-length($accountNumber) = 0 and string-length($linkId) = 0) and $name != 'fromResource'">
                    <option value="">Выберите карту <xsl:value-of select="$resourceView"/></option>
                </xsl:when>
            </xsl:choose>
            <xsl:choose>
                <xsl:when test="$name = 'fromResource'">
                    <xsl:call-template name="resourcesCards">
                        <xsl:with-param name="activeCards"      select="$activeCardsNotCredit"/>
                        <xsl:with-param name="name"             select="$name"/>
                        <xsl:with-param name="accountNumber"    select="$accountNumber"/>
                        <xsl:with-param name="linkId"           select="$linkId"/>
                        <xsl:with-param name="loginedCard"      select="$loginedCard"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="resourcesCards">
                        <xsl:with-param name="activeCards"      select="$activeCards"/>
                        <xsl:with-param name="name"             select="$name"/>
                        <xsl:with-param name="accountNumber"    select="$accountNumber"/>
                        <xsl:with-param name="linkId"           select="$linkId"/>
                        <xsl:with-param name="loginedCard"      select="$loginedCard"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </select>
    </xsl:template>

    <xsl:template name="resourcesCards">
        <xsl:param name="activeCards"/>
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="loginedCard"/>

        <xsl:for-each select="$activeCards">
            <xsl:if test="./field[@name='isMain'] = 'true' or ./field[@name='additionalCardType'] = 'CLIENTTOCLIENT' or $name = 'toResource' or ($name = 'fromResource' and ./field[@name='additionalCardType'] = 'OTHERTOCLIENT')">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:if test="./field[@name='isMain'] = 'false' and $name = 'toResource'">
                        <xsl:attribute name="class">hideable</xsl:attribute>
                    </xsl:if>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$name = 'fromResource' and ($loginedFromEmployeeByCard = 'true' and count($loginedCard) > 0 and (string-length($accountNumber) = 0 and string-length($linkId) = 0))">
                        <xsl:for-each select="$loginedCard">
                            <xsl:if test="$id = ./field[@name='code']/text()"><xsl:attribute name="selected"/></xsl:if>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>
                    &nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]

                    <xsl:if test="string-length(./field[@name='amountDecimal']) > 0">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                    </xsl:if>
                    &nbsp;
                    <xsl:value-of select="mf:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="link-property">
        <xsl:param name="source"/>
        <xsl:param name="name"/>

        <xsl:variable name="param-delimiter" select="';'"/>
        <xsl:variable name="value-delimiter" select="'|'"/>

        <xsl:for-each select="xalan:tokenize($source, $param-delimiter)">
            <xsl:if test="contains(current(), $name)">
                <xsl:value-of select="substring-after(current(), $value-delimiter)"/>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="stateStamp">
        <xsl:param name="state"/>
        <xsl:param name="stateData"/>
        <xsl:param name="documentDate"/>
        <xsl:param name="documentNumber"/>
        <xsl:param name="bankName"/>
        <xsl:param name="bic"/>
        <xsl:param name="corrByBIC"/>

        <div class="payData">
            <div id="state" class="inlineBlock p2pState">
                <div class="stateType state_{$state}"></div>
                <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link stateColor_{$state}">
                    <xsl:value-of  select="$stateData"/>
                </span>
            </div>

            <xsl:if test="state != 'WAIT_CONFIRM'">
                <div class="payDate"><xsl:value-of  select="$documentDate"/></div>
            </xsl:if>
            <div class="payDoc">Платежный документ № <xsl:value-of  select="$documentNumber"/></div>
            <div class="clear"> </div>
            <div class="bankDetailData">
                <div class="b_name"><xsl:value-of  select="$bankName"/></div>
                <div>БИК: <xsl:value-of  select="$bic"/></div>
                <div>Корр. счет: <xsl:value-of  select="$corrByBIC"/></div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
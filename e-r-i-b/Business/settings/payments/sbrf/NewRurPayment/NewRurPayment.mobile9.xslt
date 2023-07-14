<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:pnu="java://com.rssl.phizic.utils.PhoneNumberUtil"
                exclude-result-prefixes="xalan mask pu au ph pnu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="isByTemplate"/> <!--платеж создается по шаблону-->
    <xsl:param name="isMobileLimitedScheme" select="true()"/> <!--доавторизационная зона?-->
    <xsl:param name="isMobileLightScheme" select="false()"/> <!--Light-схема приложения?-->

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <initialData>
            <form>NewRurPayment</form>
            <NewRurPayment>
                <xsl:call-template name="initialData"/>
            </NewRurPayment>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

        <documentDate>
            <xsl:call-template name="dateField">
                <xsl:with-param name="name">documentDate</xsl:with-param>
                <xsl:with-param name="title">Дата документа</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="documentDate"/>
            </xsl:call-template>
        </documentDate>
        <documentNumber>
            <xsl:call-template name="integerField">
                <xsl:with-param name="name">documentNumber</xsl:with-param>
                <xsl:with-param name="title">Номер документа</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="documentNumber"/>
            </xsl:call-template>
        </documentNumber>
        <receiverSubType>
            <xsl:call-template name="receiverSubTypeList">
                <xsl:with-param name="name">receiverSubType</xsl:with-param>
                <xsl:with-param name="title">Тип перевода</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
            </xsl:call-template>
        </receiverSubType>
        <cardFields>
            <fromResource>
                <xsl:call-template name="resourceField">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="title">Списать со счета</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="link" select="fromResource"/>
                    <xsl:with-param name="linkCode" select="fromResourceLink"/>
                    <xsl:with-param name="cards" select="$activeCards"/>
                </xsl:call-template>
            </fromResource>
            <ourCard>
                <externalCardNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate) and not($isMobileLightScheme)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="externalCardNumber"/>
                        <xsl:with-param name="fieldDictType">
                            <xsl:choose>
                                <xsl:when test="not($isByTemplate)"><xsl:value-of select="'ourCard'"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="fieldInfoType">
                            <xsl:choose>
                                <xsl:when test="not($isByTemplate)"><xsl:value-of select="'ourCard'"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                    </xsl:call-template>
                </externalCardNumber>
            </ourCard>
            <ourPhone>
               <externalPhoneNumber>
                   <xsl:call-template name="stringField">
                       <xsl:with-param name="name">externalPhoneNumber</xsl:with-param>
                       <xsl:with-param name="title">Номер телефона получателя</xsl:with-param>
                       <xsl:with-param name="required" select="true()"/>
                       <xsl:with-param name="editable" select="not($isByTemplate) and not($isMobileLightScheme)"/>
                       <xsl:with-param name="visible" select="true()"/>
                       <xsl:with-param name="value" select="pnu:getFullSimplePhoneNumber(externalPhoneNumber)"/>
                       <xsl:with-param name="fieldDictType">
                           <xsl:choose>
                               <xsl:when test="not($isByTemplate)"><xsl:value-of select="'phone'"/></xsl:when>
                               <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                           </xsl:choose>
                       </xsl:with-param>
                       <xsl:with-param name="fieldInfoType">
                           <xsl:choose>
                               <xsl:when test="not($isByTemplate)"><xsl:value-of select="'phone'"/></xsl:when>
                               <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                           </xsl:choose>
                       </xsl:with-param>
                       <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                   </xsl:call-template>
               </externalPhoneNumber>
           </ourPhone>
            <xsl:if test="pu:impliesService('MasterCardMoneyTransferService')">
                <masterCardExternalCard>
                    <externalCardNumber>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="not($isByTemplate) and not($isMobileLightScheme)"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="externalCardNumber"/>
                            <xsl:with-param name="fieldDictType">
                                <xsl:choose>
                                    <xsl:when test="not($isByTemplate)"><xsl:value-of select="'masterCardExternalCard'"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                            <xsl:with-param name="fieldInfoType">
                                <xsl:choose>
                                    <xsl:when test="not($isByTemplate)"><xsl:value-of select="'masterCardExternalCard'"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                        </xsl:call-template>
                    </externalCardNumber>
                </masterCardExternalCard>
            </xsl:if>
            <xsl:if test="pu:impliesService('VisaMoneyTransferService')">
                <visaExternalCard>
                    <externalCardNumber>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="not($isByTemplate) and not($isMobileLightScheme)"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="externalCardNumber"/>
                            <xsl:with-param name="fieldDictType">
                                <xsl:choose>
                                    <xsl:when test="not($isByTemplate)"><xsl:value-of select="'visaExternalCard'"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                            <xsl:with-param name="fieldInfoType">
                                <xsl:choose>
                                    <xsl:when test="not($isByTemplate)"><xsl:value-of select="'visaExternalCard'"/></xsl:when>
                                    <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                        </xsl:call-template>
                    </externalCardNumber>
                </visaExternalCard>
            </xsl:if>
            <ourContactToOtherCard>
                <externalCardNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate) and not($isMobileLightScheme)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="externalCardNumber"/>
                        <xsl:with-param name="fieldDictType">
                            <xsl:choose>
                                <xsl:when test="not($isByTemplate)"><xsl:value-of select="'ourContactToOtherCard'"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="fieldInfoType">
                            <xsl:choose>
                                <xsl:when test="not($isByTemplate)"><xsl:value-of select="'ourContactToOtherCard'"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                    </xsl:call-template>
                </externalCardNumber>
            </ourContactToOtherCard>
            <ourContact>
                <externalCardNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate) and not($isMobileLightScheme)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="externalCardNumber"/>
                        <xsl:with-param name="fieldDictType">
                            <xsl:choose>
                                <xsl:when test="not($isByTemplate)"><xsl:value-of select="'ourContact'"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="fieldInfoType">
                            <xsl:choose>
                                <xsl:when test="not($isByTemplate)"><xsl:value-of select="'ourContact'"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="''"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                    </xsl:call-template>
                </externalCardNumber>
            </ourContact>
            <xsl:if test="pu:impliesService('MessageWithCommentToReceiverService')">
               <messageToReceiver>
                   <xsl:call-template name="stringField">
                       <xsl:with-param name="name">messageToReceiver</xsl:with-param>
                       <xsl:with-param name="title">Сообщение получателю</xsl:with-param>
                       <xsl:with-param name="hint">Введите сообщение для получателя средств, которое будет направлено ему на мобильный телефон</xsl:with-param>
                       <xsl:with-param name="required" select="false()"/>
                       <xsl:with-param name="editable" select="true()"/>
                       <xsl:with-param name="visible" select="true()"/>
                       <xsl:with-param name="maxLength" select="40"/>
                       <xsl:with-param name="value" select="messageToReceiver"/>
                   </xsl:call-template>
               </messageToReceiver>
           </xsl:if>
        </cardFields>
        <buyAmount>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">buyAmount</xsl:with-param>
                <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="buyAmount"/>
            </xsl:call-template>
        </buyAmount>
        <isCardTransfer>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">isCardTransfer</xsl:with-param>
                <xsl:with-param name="title">Является ли операция переводом с карты на карту</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="isCardTransfer"/>
            </xsl:call-template>
        </isCardTransfer>
        <isErrorCurrency>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">isErrorCurrency</xsl:with-param>
                <xsl:with-param name="title">Ошибка при получении валюты</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="isErrorCurrency"/>
            </xsl:call-template>
        </isErrorCurrency>
        <buyAmountCurrency>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">buyAmountCurrency</xsl:with-param>
                <xsl:with-param name="title">Валюта счета получателя</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="buyAmountCurrency"/>
            </xsl:call-template>
        </buyAmountCurrency>
        <sellAmount>
            <xsl:call-template name="moneyField">
                <xsl:with-param name="name">sellAmount</xsl:with-param>
                <xsl:with-param name="title">Сумма списания, заполняется только в случае разновалютной операции</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="sellAmount"/>
            </xsl:call-template>
        </sellAmount>
        <exactAmount>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">exactAmount</xsl:with-param>
                <xsl:with-param name="title">Признак, обозначающий какое из полей суммы заполнил пользователь</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="value" select="exactAmount"/>
            </xsl:call-template>
        </exactAmount>
        <longOfferAllowed><xsl:value-of select="ph:isUdboSupported()"/></longOfferAllowed>
        <dictField>
            <xsl:call-template name="dictField">
                <xsl:with-param name="name" select="'externalContactId'"/>
                <xsl:with-param name="title" select="'Получатель'"/>
                <xsl:with-param name="value" select="externalContactId"/>
            </xsl:call-template>
        </dictField>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

        <document>
            <form>NewRurPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <NewRurPaymentDocument>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>
                <receiverAccount>
                    <xsl:choose>
                        <xsl:when test="receiverSubType = 'ourCard' or receiverSubType = 'ourContact' or receiverSubType = 'ourContactToOtherCard'">
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">Номер счета/карты получателя</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="mask:getCutCardNumber(receiverAccount)"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">Номер счета/карты получателя</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverAccount"/>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                </receiverAccount>
                <xsl:if test="string-length(externalPhoneNumber)>0">
                    <receiverPhone>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">externalPhoneNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер телефона получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="pnu:getFullSimplePhoneNumber(externalPhoneNumber)"/>
                        </xsl:call-template>
                    </receiverPhone>
                </xsl:if>
                <xsl:variable name="receiverName" select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)"/>
                <xsl:if test="string-length($receiverName)>0">
                    <receiverName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverName</xsl:with-param>
                            <xsl:with-param name="title">ФИО получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$receiverName"/>
                        </xsl:call-template>
                    </receiverName>
                </xsl:if>
                <xsl:if test="string-length(receiverINN)>0">
                    <receiverINN>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverINN</xsl:with-param>
                            <xsl:with-param name="title">ИНН получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverINN"/>
                        </xsl:call-template>
                    </receiverINN>
                </xsl:if>
                <xsl:if test="string-length(receiverAddress)>0">
                    <receiverAddress>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverAddress</xsl:with-param>
                            <xsl:with-param name="title">Адрес получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAddress"/>
                        </xsl:call-template>
                    </receiverAddress>
                </xsl:if>
                <xsl:if test="string-length(bank)>0 and string-length(receiverBIC)>0 and string-length(receiverCorAccount)>0">
                    <bankInfo>
                        <bank>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">bank</xsl:with-param>
                                <xsl:with-param name="title">Наименование банка</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="bank"/>
                            </xsl:call-template>
                        </bank>
                        <receiverBIC>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                <xsl:with-param name="title">БИК</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverBIC"/>
                            </xsl:call-template>
                        </receiverBIC>
                        <receiverCorAccount>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                <xsl:with-param name="title">Кор. счет</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverCorAccount"/>
                            </xsl:call-template>
                        </receiverCorAccount>
                    </bankInfo>
                </xsl:if>
                <xsl:if test="string-length(ground)>0">
                    <ground>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">ground</xsl:with-param>
                            <xsl:with-param name="title">Назначение платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="ground"/>
                        </xsl:call-template>
                    </ground>
                </xsl:if>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Счет списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="linkCode" select="fromResourceLink"/>
                        <xsl:with-param name="accounts" select="$activeAccounts"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="isView" select="true()"/>
                    </xsl:call-template>
                </fromResource>
                <xsl:if test="string-length(sellAmount)>0">
                    <sellAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма в валюте списания</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </sellAmount>
                </xsl:if>
                <xsl:if test="string-length(sellAmountCurrency)>0">
                    <sellCurrency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">sellAmountCurrency</xsl:with-param>
                            <xsl:with-param name="title">Валюта списания</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmountCurrency"/>
                        </xsl:call-template>
                    </sellCurrency>
                </xsl:if>

                <xsl:variable name="buyAmountVisible">
                    <xsl:choose>
                        <xsl:when test="string-length(buyAmount)>0">
                            <xsl:value-of select="true()"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="false()"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <buyAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">buyAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="$buyAmountVisible"/>
                        <xsl:with-param name="value" select="buyAmount"/>
                    </xsl:call-template>
                </buyAmount>

                <xsl:call-template name="commission">
                    <xsl:with-param name="commissionAmount"><xsl:value-of select="commission"/></xsl:with-param>
                    <xsl:with-param name="commissionCurrency"><xsl:value-of select="commissionCurrency"/></xsl:with-param>
                </xsl:call-template>
                <xsl:if test="string-length(admissionDate)>0">
                    <admissionDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">admissionDate</xsl:with-param>
                            <xsl:with-param name="title">Плановая дата исполнения</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="admissionDate"/>
                        </xsl:call-template>
                    </admissionDate>
                </xsl:if>
                <xsl:if test="pu:impliesService('MessageWithCommentToReceiverService') and (receiverSubType = 'ourCard' or receiverSubType = 'ourContact' or receiverSubType = 'ourContactToOtherCard')">
                    <xsl:if test="string-length(messageToReceiver)>0">
                        <messageToReceiver>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">messageToReceiver</xsl:with-param>
                                <xsl:with-param name="title">Сообщение получателю</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="maxLength" select="40"/>
                                <xsl:with-param name="value" select="messageToReceiver"/>
                            </xsl:call-template>
                        </messageToReceiver>
                    </xsl:if>

                    <xsl:if test="state = 'EXECUTED' or state = 'DISPATCHED' or state = 'UNKNOW'">
                        <messageToReceiverStatus>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">messageToReceiverStatus</xsl:with-param>
                                <xsl:with-param name="title">Статус SMS-сообщения</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value">
                                    <xsl:choose>
                                        <xsl:when test="string-length(messageToReceiverStatus) > 0">
                                            <xsl:choose>
                                                <xsl:when test="messageToReceiverStatus = 'sent'">
                                                    сообщение отправлено
                                                </xsl:when>
                                                <xsl:when test="messageToReceiverStatus = 'not_sent'">
                                                    сообщение не отправлено
                                                </xsl:when>
                                                <xsl:otherwise><xsl:value-of select="messageToReceiverStatus"/></xsl:otherwise>
                                            </xsl:choose>
                                        </xsl:when>
                                        <xsl:otherwise>сообщение будет отправлено</xsl:otherwise>
                                    </xsl:choose>
                                </xsl:with-param>
                            </xsl:call-template>
                        </messageToReceiverStatus>
                    </xsl:if>
                </xsl:if>
            </NewRurPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template name="receiverSubTypeList">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>

        <name><xsl:value-of select="$name"/></name>
        <title><xsl:value-of select="$title"/></title>
        <xsl:if test="string-length($description)>0">
            <description><xsl:value-of select="$description"/></description>
        </xsl:if>
        <xsl:if test="string-length($hint)>0">
            <hint><xsl:value-of select="$hint"/></hint>
        </xsl:if>
        <type>list</type>
        <required><xsl:value-of select="$required"/></required>
        <editable><xsl:value-of select="$editable"/></editable>
        <visible><xsl:value-of select="$visible"/></visible>

        <listType>
            <availableValues>
                <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                    <xsl:with-param name="key">ourCard</xsl:with-param>
                    <xsl:with-param name="title">На карту Сбербанка</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                    <xsl:with-param name="key">ourPhone</xsl:with-param>
                    <xsl:with-param name="title">На карту Сбербанка по номеру телефона</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                    <xsl:with-param name="key">ourContact</xsl:with-param>
                    <xsl:with-param name="title">На карту Сбербанка по номеру телефона контакта из АК</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                    <xsl:with-param name="key">ourContactToOtherCard</xsl:with-param>
                    <xsl:with-param name="title">На карту другого банка по номеру карты контакта из АК</xsl:with-param>
                </xsl:call-template>

                <xsl:if test="pu:impliesService('MasterCardMoneyTransferService')">
                    <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                        <xsl:with-param name="key">masterCardExternalCard</xsl:with-param>
                        <xsl:with-param name="title">На карту MasterCard другого банка</xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="pu:impliesService('VisaMoneyTransferService')">
                    <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                        <xsl:with-param name="key">visaExternalCard</xsl:with-param>
                        <xsl:with-param name="title">На карту VISA другого банка</xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </availableValues>
        </listType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
    </xsl:template>

    <xsl:template name="receiverSubTypeListPrintValueTitle">
        <xsl:param name="key"/>
        <xsl:param name="title"/>
        <valueItem>
            <value><xsl:value-of select="$key"/></value>
            <title><xsl:value-of select="$title"/></title>
            <selected><xsl:value-of select="string($key = receiverSubType)"/></selected>
        </valueItem>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cards"/>
        <xsl:param name="accounts"/>
        <xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
        <xsl:param name="link" select="''"/> <!--Ресурс. Ожидается link.getCode() (строка вида "card:1234"), но здесь может оказаться link.toString() (строка вида "Карта № ..."). при редактировании-->
        <xsl:param name="linkCode"/> <!--Код карты или счета (card:1234). при просмотре и редактировании-->

        <xsl:variable name="isValidLinkCode" select="string-length($link)>0 and (starts-with($link, 'card:') or starts-with($link, 'account:'))"/>
        <xsl:variable name="isFromCard" select="starts-with($link, 'card:') or starts-with($linkCode, 'card:')"/>
        <xsl:variable name="isFromAccount" select="starts-with($link, 'account:') or starts-with($linkCode, 'account:')"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'resource'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <xsl:variable name="showCards" select="string-length($cards) > 0 and (not($isMobileLimitedScheme) or not($isByTemplate) or $isFromCard)"/>
                    <xsl:variable name="showAccounts" select="string-length($accounts)>0 and (not($isMobileLimitedScheme) or not($isByTemplate) or $isFromAccount)"/>
                    <xsl:if test="$showCards or $showAccounts">
                        <availableValues>
                            <xsl:if test="$showCards">
                                <xsl:for-each select="$cards">
                                    <xsl:variable name="code" select="field[@name='code']/text()"/>
                                    <xsl:variable name="selected" select="($isValidLinkCode and $code=$link) or (not($isValidLinkCode) and $code=$linkCode)"/>
                                    <xsl:if test="not($isView) or $selected">
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <selected><xsl:value-of select="string($selected)"/></selected>
                                            <displayedValue>
                                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                            </displayedValue>
                                        </valueItem>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:if>

                            <xsl:if test="$showAccounts">
                                <xsl:for-each select="$accounts">
                                    <xsl:variable name="code" select="field[@name='code']/text()"/>
                                    <xsl:variable name="selected" select="($isValidLinkCode and $code=$link) or (not($isValidLinkCode) and $code=$linkCode)"/>
                                    <xsl:if test="not($isView) or $selected">
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <selected><xsl:value-of select="string($selected)"/></selected>
                                            <displayedValue>
                                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                            </displayedValue>
                                        </valueItem>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:if>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dictionaryField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="dictionary"/>
        <xsl:param name="values" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isView" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:choose>
                            <xsl:when test="not($isView)">
                                <xsl:for-each select="xalan:tokenize($values, '|')">
                                    <valueItem>
                                        <value><xsl:value-of select="current()"/></value>
                                        <title><xsl:value-of select="$dictionary[@key=current()]/text()"/></title>
                                        <selected><xsl:value-of select="string(current()=$value)"/></selected>
                                    </valueItem>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <valueItem>
                                    <value><xsl:value-of select="$value"/></value>
                                    <title><xsl:value-of select="$dictionary[@key=$value]/text()"/></title>
                                    <selected>true</selected>
                                </valueItem>
                            </xsl:otherwise>
                        </xsl:choose>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dictField">
            <xsl:param name="name"/>
            <xsl:param name="title" select="''"/>
            <xsl:param name="value"/>

            <xsl:call-template name="simpleField">
                <xsl:with-param name="name" select="$name"/>
                <xsl:with-param name="title" select="$title"/>
                <xsl:with-param name="type" select="'dict'"/>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="false()"/>
                <xsl:with-param name="valueTag">
                    <dictType>
                        <xsl:if test="string-length($value)>0">
                            <value><xsl:value-of select="$value"/></value>
                        </xsl:if>
                    </dictType>
                </xsl:with-param>
            </xsl:call-template>
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

</xsl:stylesheet>
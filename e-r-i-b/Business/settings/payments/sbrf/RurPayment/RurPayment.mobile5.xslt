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
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="isByTemplate"/> <!--платеж создается по шаблону-->
    <xsl:param name="isMobileLimitedScheme" select="true()"/> <!--доавторизационная зона?-->

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit' and not($longOffer)">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'edit' and $longOffer">
                <xsl:apply-templates mode="edit-long-offer"/>
            </xsl:when>
            <xsl:when test="$mode = 'view' and not($longOffer)">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
            <xsl:when test="$mode = 'view' and $longOffer">
                <xsl:apply-templates mode="view-long-offer"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <initialData>
            <form>RurPayment</form>
            <RurPayment>
                <xsl:call-template name="initialData"/>
            </RurPayment>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
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
                <xsl:with-param name="editable" select="not($isByTemplate)"/>
                <xsl:with-param name="visible" select="true()"/>
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
                        <xsl:with-param name="editable" select="not($isByTemplate)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="externalCardNumber"/>
                    </xsl:call-template>
                </externalCardNumber>
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
            </ourCard>
            <ourPhone>
                <externalPhoneNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">externalPhoneNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер телефона</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="pnu:getFullSimplePhoneNumber(externalPhoneNumber)"/>
                    </xsl:call-template>
                </externalPhoneNumber>
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
            </ourPhone>
            <xsl:if test="pu:impliesService('MastercardMoneySendService')">
                <masterCardExternalCard>
                    <externalCardNumber>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="not($isByTemplate)"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="externalCardNumber"/>
                        </xsl:call-template>
                    </externalCardNumber>
                </masterCardExternalCard>
            </xsl:if>
            <xsl:if test="pu:impliesService('VisaMoneySendService')">
                <visaExternalCard>
                    <externalCardNumber>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="not($isByTemplate)"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="externalCardNumber"/>
                        </xsl:call-template>
                    </externalCardNumber>
                </visaExternalCard>
            </xsl:if>
        </cardFields>
        <accountFields>
            <fromResource>
                <xsl:call-template name="resourceField">
                    <xsl:with-param name="name">fromResource</xsl:with-param>
                    <xsl:with-param name="title">Списать со счета</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="link" select="fromResource"/>
                    <xsl:with-param name="linkCode" select="fromResourceLink"/>
                    <xsl:with-param name="accounts" select="$activeAccounts"/>
                    <xsl:with-param name="cards" select="$activeCards"/>
                </xsl:call-template>
            </fromResource>
            <receiverAccountInternal>
                <xsl:call-template name="integerField">
                    <xsl:with-param name="name">receiverAccountInternal</xsl:with-param>
                    <xsl:with-param name="title">Номер счета</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="not($isByTemplate)"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverAccountInternal"/>
                </xsl:call-template>
            </receiverAccountInternal>
            <receiverSurname>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">receiverSurname</xsl:with-param>
                    <xsl:with-param name="title">Фамилия получателя</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="not($isByTemplate)"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverSurname"/>
                </xsl:call-template>
            </receiverSurname>
            <receiverFirstName>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">receiverFirstName</xsl:with-param>
                    <xsl:with-param name="title">Имя получателя</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="not($isByTemplate)"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverFirstName"/>
                </xsl:call-template>
            </receiverFirstName>
            <receiverPatrName>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">receiverPatrName</xsl:with-param>
                    <xsl:with-param name="title">Отчество получателя</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="not($isByTemplate)"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverPatrName"/>
                </xsl:call-template>
            </receiverPatrName>
            <receiverINN>
                <xsl:call-template name="integerField">
                    <xsl:with-param name="name">receiverINN</xsl:with-param>
                    <xsl:with-param name="title">ИНН получателя</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="not($isByTemplate)"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverINN"/>
                </xsl:call-template>
            </receiverINN>
            <receiverAddress>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">receiverAddress</xsl:with-param>
                    <xsl:with-param name="title">Адрес получателя</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="not($isByTemplate)"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverAddress"/>
                </xsl:call-template>
            </receiverAddress>
            <bankInfo>
                <bank>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">bank</xsl:with-param>
                        <xsl:with-param name="title">Наименование банка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="bank"/>
                    </xsl:call-template>
                </bank>
                <receiverBIC>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">receiverBIC</xsl:with-param>
                        <xsl:with-param name="title">БИК</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverBIC"/>
                    </xsl:call-template>
                </receiverBIC>
                <receiverCorAccount>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                        <xsl:with-param name="title">Кор. счет</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="not($isByTemplate)"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverCorAccount"/>
                    </xsl:call-template>
                </receiverCorAccount>
            </bankInfo>
            <ground>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">ground</xsl:with-param>
                    <xsl:with-param name="title">Назначение платежа</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="ground"/>
                </xsl:call-template>
            </ground>
        </accountFields>
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
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
        
        <document>
            <form>RurPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <RurPaymentDocument>
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
                        <xsl:when test="receiverSubType = 'ourCard' or receiverSubType = 'ourPhone' or receiverSubType = 'masterCardExternalCard' or receiverSubType = 'visaExternalCard'">
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
                <xsl:if test="string-length(buyAmount) > 0">
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                </xsl:if>
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
                <xsl:if test="pu:impliesService('MessageWithCommentToReceiverService') and (receiverSubType = 'ourCard' or receiverSubType = 'ourPhone')">
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
                                                <xsl:when test="messageToReceiverStatus = 'sent'">сообщение отправлено</xsl:when>
                                                <xsl:when test="messageToReceiverStatus = 'not_sent'">сообщение не отправлено</xsl:when>
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
            </RurPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-long-offer">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
        <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>
        <xsl:variable name="priorities" select="document('priority.xml')/entity-list/entity"/>

        <initialData>
            <form>RurPaymentLongOffer</form>
            <RurPaymentLongOffer>
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
                <receiverSubType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverSubType</xsl:with-param>
                        <xsl:with-param name="title">Тип перевода</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="receiverSubType"/>
                    </xsl:call-template>
                </receiverSubType>
                <receiver>
                    <account>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverAccountInternal</xsl:with-param>
                            <xsl:with-param name="title">Номер счета</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAccountInternal"/>
                        </xsl:call-template>
                    </account>
                    <accountCurrency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">buyAmountCurrency</xsl:with-param>
                            <xsl:with-param name="title">Валюта счета получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="buyAmountCurrency"/>
                        </xsl:call-template>
                    </accountCurrency>
                    <surname>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverSurname</xsl:with-param>
                            <xsl:with-param name="title">Фамилия</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value"><xsl:value-of select="substring(receiverSurname, 1, 1)"/>.</xsl:with-param>
                        </xsl:call-template>
                    </surname>
                    <firstName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverFirstName</xsl:with-param>
                            <xsl:with-param name="title">Имя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverFirstName"/>
                        </xsl:call-template>
                    </firstName>
                    <patrName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverPatrName</xsl:with-param>
                            <xsl:with-param name="title">Отчество</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverPatrName"/>
                        </xsl:call-template>
                    </patrName>
                    <inn>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverINN</xsl:with-param>
                            <xsl:with-param name="title">ИНН</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverINN"/>
                        </xsl:call-template>
                    </inn>
                    <address>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverAddress</xsl:with-param>
                            <xsl:with-param name="title">Адрес</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAddress"/>
                        </xsl:call-template>
                    </address>
                </receiver>
                <bank>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">bank</xsl:with-param>
                            <xsl:with-param name="title">Наименование</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="bank"/>
                        </xsl:call-template>
                    </name>
                    <bic>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverBIC</xsl:with-param>
                            <xsl:with-param name="title">БИК</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverBIC"/>
                        </xsl:call-template>
                    </bic>
                    <corAccount>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                            <xsl:with-param name="title">Корр. счет</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverCorAccount"/>
                        </xsl:call-template>
                    </corAccount>
                </bank>
                <paymentDetails>
                    <ground>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">ground</xsl:with-param>
                            <xsl:with-param name="title">Назначение платежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="ground"/>
                        </xsl:call-template>
                    </ground>
                    <fromResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Счет списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="linkCode" select="fromResourceLink"/>
                            <xsl:with-param name="accounts" select="$activeAccounts"/>
                            <xsl:with-param name="cards" select="$activeCards"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </fromResource>
                    <xsl:if test="string-length(admissionDate)>0">
                        <admissionDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">admissionDate</xsl:with-param>
                                <xsl:with-param name="title">Плановая дата исполнения</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="admissionDate"/>
                            </xsl:call-template>
                        </admissionDate>
                    </xsl:if>
                </paymentDetails>
                <longOfferDetails>
                    <startDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата начала действия</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferStartDate"/>
                        </xsl:call-template>
                    </startDate>
                    <endDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferEndDate</xsl:with-param>
                            <xsl:with-param name="title">Дата окончания</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferEndDate"/>
                        </xsl:call-template>
                    </endDate>
                    <firstPaymentDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                            <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="firstPaymentDate"/>
                        </xsl:call-template>
                    </firstPaymentDate>
                    <ourAccount>
                        <fromAccountFields>
                            <eventType>
                                <xsl:call-template name="dictionaryField">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="dictionary" select="$eventTypes"/>
                                    <xsl:with-param name="values" select="'ONCE_IN_MONTH|ONCE_IN_QUARTER|ONCE_IN_HALFYEAR|ONCE_IN_YEAR|BY_ANY_RECEIPT|ON_REMAIND|ON_OVER_DRAFT|BY_CAPITAL|BY_PERCENT'"/>
                                    <xsl:with-param name="value" select="longOfferEventType"/>
                                </xsl:call-template>
                            </eventType>
                            <periodic>
                                <payDay>
                                    <xsl:call-template name="integerField">
                                        <xsl:with-param name="name">longOfferPayDay</xsl:with-param>
                                        <xsl:with-param name="title">Число месяца</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="longOfferPayDay"/>
                                    </xsl:call-template>
                                </payDay>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA|FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </periodic>
                            <byAnyReceipt>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA|FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byAnyReceipt>
                            <onRemaind>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </onRemaind>
                            <onOverDraft>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'OVER_DRAFT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </onOverDraft>
                            <byCapital>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'PERCENT_OF_CAPITAL'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byCapital>
                            <byPercent>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byPercent>
                        </fromAccountFields>
                        <fromCardFields>
                            <eventType>
                                <xsl:call-template name="dictionaryField">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="dictionary" select="$eventTypes"/>
                                    <xsl:with-param name="values" select="'ONCE_IN_MONTH|ONCE_IN_QUARTER|ONCE_IN_HALFYEAR|ONCE_IN_YEAR|BY_ANY_RECEIPT|ON_REMAIND|ON_OVER_DRAFT|BY_SALARY|BY_PENSION'"/>
                                    <xsl:with-param name="value" select="longOfferEventType"/>
                                </xsl:call-template>
                            </eventType>
                            <periodic>
                                <payDay>
                                    <xsl:call-template name="integerField">
                                        <xsl:with-param name="name">longOfferPayDay</xsl:with-param>
                                        <xsl:with-param name="title">Число месяца</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="longOfferPayDay"/>
                                    </xsl:call-template>
                                </payDay>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </periodic>
                            <byAnyReceipt>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byAnyReceipt>
                            <onRemaind>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </onRemaind>
                            <onOverDraft>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'OVER_DRAFT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </onOverDraft>
                            <bySalary>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT|REMAIND_IN_RECIP|REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </bySalary>
                            <byPension>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT|REMAIND_IN_RECIP|REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byPension>
                        </fromCardFields>
                    </ourAccount>
                    <externalAccount>
                        <fromAccountFields>
                            <eventType>
                                <xsl:call-template name="dictionaryField">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="dictionary" select="$eventTypes"/>
                                    <xsl:with-param name="values" select="'ONCE_IN_MONTH|ONCE_IN_QUARTER|ONCE_IN_HALFYEAR|ONCE_IN_YEAR|BY_ANY_RECEIPT|ON_REMAIND|BY_CAPITAL|BY_PERCENT'"/>
                                    <xsl:with-param name="value" select="longOfferEventType"/>
                                </xsl:call-template>
                            </eventType>
                            <periodic>
                                <payDay>
                                    <xsl:call-template name="integerField">
                                        <xsl:with-param name="name">longOfferPayDay</xsl:with-param>
                                        <xsl:with-param name="title">Число месяца</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="longOfferPayDay"/>
                                    </xsl:call-template>
                                </payDay>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA|FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </periodic>
                            <byAnyReceipt>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA|FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byAnyReceipt>
                            <onRemaind>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </onRemaind>
                            <byCapital>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'PERCENT_OF_CAPITAL'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byCapital>
                            <byPercent>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byPercent>
                        </fromAccountFields>
                        <fromCardFields>
                            <eventType>
                                <xsl:call-template name="dictionaryField">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Повторяется</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="dictionary" select="$eventTypes"/>
                                    <xsl:with-param name="values" select="'ONCE_IN_MONTH|ONCE_IN_QUARTER|ONCE_IN_HALFYEAR|ONCE_IN_YEAR|BY_ANY_RECEIPT|ON_REMAIND|BY_SALARY|BY_PENSION'"/>
                                    <xsl:with-param name="value" select="longOfferEventType"/>
                                </xsl:call-template>
                            </eventType>
                            <periodic>
                                <payDay>
                                    <xsl:call-template name="integerField">
                                        <xsl:with-param name="name">longOfferPayDay</xsl:with-param>
                                        <xsl:with-param name="title">Число месяца</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="longOfferPayDay"/>
                                    </xsl:call-template>
                                </payDay>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </periodic>
                            <byAnyReceipt>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byAnyReceipt>
                            <onRemaind>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'REMAIND_OVER_SUMMA'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </onRemaind>
                            <bySalary>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </bySalary>
                            <byPension>
                                <sumType>
                                    <xsl:call-template name="dictionaryField">
                                        <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                                        <xsl:with-param name="title">Тип суммы</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="dictionary" select="$sumTypes"/>
                                        <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|SUMMA_OF_RECEIPT'"/>
                                        <xsl:with-param name="value" select="longOfferSumType"/>
                                    </xsl:call-template>
                                </sumType>
                            </byPension>
                        </fromCardFields>
                    </externalAccount>
                    <priority>
                        <xsl:call-template name="simpleField">
                            <xsl:with-param name="name">longOfferPrioritySelect</xsl:with-param>
                            <xsl:with-param name="title">Выполняется</xsl:with-param>
                            <xsl:with-param name="type">list</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="valueTag">
                                <listType>
                                    <xsl:if test="count($priorities)>0">
                                        <availableValues>
                                            <xsl:variable name="priority" select="longOfferPrioritySelect"/>
                                            <xsl:for-each select="$priorities">
                                                <valueItem>
                                                    <value><xsl:value-of select="./@key"/></value>
                                                    <title><xsl:value-of select="./text()"/></title>
                                                    <selected><xsl:value-of select="string(./@key = $priority)"/></selected>
                                                </valueItem>
                                            </xsl:for-each>
                                        </availableValues>
                                    </xsl:if>
                                </listType>
                            </xsl:with-param>
                        </xsl:call-template>
                    </priority>
                    <isSumModify>
                        <xsl:call-template name="booleanField">
                            <xsl:with-param name="name">isSumModify</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="isSumModify"/>
                        </xsl:call-template>
                    </isSumModify>
                    <sellAmountFields>
                        <sellAmount>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">sellAmount</xsl:with-param>
                                <xsl:with-param name="title">Сумма</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="sellAmount"/>
                            </xsl:call-template>
                        </sellAmount>
                        <exactAmount>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">exactAmount</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="'charge-off-field-exact'"/>
                            </xsl:call-template>
                        </exactAmount>
                    </sellAmountFields>
                    <buyAmountFields>
                        <buyAmount>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">buyAmount</xsl:with-param>
                                <xsl:with-param name="title">Сумма в валюте зачисления</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="buyAmount"/>
                            </xsl:call-template>
                        </buyAmount>
                        <exactAmount>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">exactAmount</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="'destination-field-exact'"/>
                            </xsl:call-template>
                        </exactAmount>
                    </buyAmountFields>
                    <percentFields>
                        <percent>
                            <xsl:call-template name="numberField">
                                <xsl:with-param name="name">longOfferPercent</xsl:with-param>
                                <xsl:with-param name="title">Процент</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="longOfferPercent"/>
                            </xsl:call-template>
                        </percent>
                    </percentFields>
                </longOfferDetails>
            </RurPaymentLongOffer>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-long-offer">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
        <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>
        <xsl:variable name="priorities" select="document('priority.xml')/entity-list/entity"/>
        <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>

        <document>
            <form>RurPaymentLongOffer</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <RurPaymentLongOfferDocument>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>
                <receiver>
                    <account>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverAccountInternal</xsl:with-param>
                            <xsl:with-param name="title">Номер счета</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAccountInternal"/>
                        </xsl:call-template>
                    </account>
                    <fio>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">fio</xsl:with-param>
                            <xsl:with-param name="title">ФИО</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="ph:getFormattedPersonName(receiverFirstName, receiverSurname, receiverPatrName)"/>
                        </xsl:call-template>
                    </fio>
                    <inn>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverINN</xsl:with-param>
                            <xsl:with-param name="title">ИНН</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverINN"/>
                        </xsl:call-template>
                    </inn>
                    <address>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverAddress</xsl:with-param>
                            <xsl:with-param name="title">Адрес</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAddress"/>
                        </xsl:call-template>
                    </address>
                </receiver>
                <bank>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">bank</xsl:with-param>
                            <xsl:with-param name="title">Наименование</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="bank"/>
                        </xsl:call-template>
                    </name>
                    <bic>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverBIC</xsl:with-param>
                            <xsl:with-param name="title">БИК</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverBIC"/>
                        </xsl:call-template>
                    </bic>
                    <corAccount>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                            <xsl:with-param name="title">Корр. счет</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverCorAccount"/>
                        </xsl:call-template>
                    </corAccount>
                </bank>
                <paymentDetails>
                    <ground>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">ground</xsl:with-param>
                            <xsl:with-param name="title">Назначение платежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="ground"/>
                        </xsl:call-template>
                    </ground>
                    <fromResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Счет списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="linkCode" select="fromResourceLink"/>
                            <xsl:with-param name="accounts" select="$activeAccounts"/>
                            <xsl:with-param name="cards" select="$activeCards"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </fromResource>
                    <xsl:if test="((exactAmount = 'charge-off-field-exact') or (exactAmount != 'destination-field-exact'))">
                        <xsl:if test="(isSumModify != 'true' or longOfferSumType='FIXED_SUMMA' or longOfferSumType='REMAIND_OVER_SUMMA')
                                and string-length(sellAmount)>0">
                            <sellAmount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">sellAmount</xsl:with-param>
                                    <xsl:with-param name="title">Сумма</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="sellAmount"/>
                                </xsl:call-template>
                            </sellAmount>
                        </xsl:if>
                        <xsl:if test="longOfferSumType='PERCENT_OF_REMAIND'">
                            <percent>
                                <xsl:call-template name="numberField">
                                    <xsl:with-param name="name">longOfferPercent</xsl:with-param>
                                    <xsl:with-param name="title">Процент</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="longOfferPercent"/>
                                </xsl:call-template>
                            </percent>
                        </xsl:if>
                    </xsl:if>
                    <xsl:if test="exactAmount = 'destination-field-exact' and string-length(buyAmount)>0">
                        <buyAmount>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">buyAmount</xsl:with-param>
                                <xsl:with-param name="title">Сумма в валюте зачисления</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="buyAmount"/>
                            </xsl:call-template>
                        </buyAmount>
                        <buyAmountCurrency>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">buyAmountCurrency</xsl:with-param>
                                <xsl:with-param name="title">Валюта зачисления</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="buyAmountCurrency"/>
                            </xsl:call-template>
                        </buyAmountCurrency>
                    </xsl:if>
                    <xsl:if test="string-length(commission)>0">
                        <commission>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">commission</xsl:with-param>
                                <xsl:with-param name="title">Комиссия</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="commission"/>
                            </xsl:call-template>
                        </commission>
                    </xsl:if>
                </paymentDetails>
                <longOfferDetails>
                    <startDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата начала действия</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferStartDate"/>
                        </xsl:call-template>
                    </startDate>
                    <endDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferEndDate</xsl:with-param>
                            <xsl:with-param name="title">Дата окончания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferEndDate"/>
                        </xsl:call-template>
                    </endDate>
                    <eventType>
                        <xsl:call-template name="dictionaryField">
                            <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                            <xsl:with-param name="title">Повторяется</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="dictionary" select="$eventTypes"/>
                            <xsl:with-param name="value" select="longOfferEventType"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </eventType>
                    <firstPaymentDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                            <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="firstPaymentDate"/>
                        </xsl:call-template>
                    </firstPaymentDate>
                    <priority>
                        <xsl:call-template name="dictionaryField">
                            <xsl:with-param name="name">longOfferPrioritySelect</xsl:with-param>
                            <xsl:with-param name="title">Выполняется</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="dictionary" select="$priorities"/>
                            <xsl:with-param name="value" select="longOfferPrioritySelect"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </priority>
                    <sumType>
                        <xsl:call-template name="dictionaryField">
                            <xsl:with-param name="name">longOfferSumType</xsl:with-param>
                            <xsl:with-param name="title">Тип суммы</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="dictionary" select="$sumTypes"/>
                            <xsl:with-param name="value" select="longOfferSumType"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </sumType>
                    <isSumModify>
                        <xsl:call-template name="booleanField">
                            <xsl:with-param name="name">isSumModify</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="isSumModify"/>
                        </xsl:call-template>
                    </isSumModify>

                    <xsl:variable name="event"  select="longOfferEventType"/>
                    <xsl:variable name="firstDate"  select="firstPaymentDate"/>
                    <xsl:variable name="day"    select="substring($firstDate, 9, 2)"/>
                    <xsl:variable name="month"  select="substring($firstDate, 6, 2)"/>
                    <xsl:if test="$event = 'ONCE_IN_MONTH' or $event = 'ONCE_IN_YEAR' or $event = 'ONCE_IN_QUARTER' or $event = 'ONCE_IN_HALFYEAR'">
                        <payDayDescription>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">payDayDescription</xsl:with-param>
                                <xsl:with-param name="title">Дата оплаты</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value">
                                    <xsl:choose>
                                        <xsl:when test="$event = 'ONCE_IN_MONTH' or $event = 'ONCE_IN_YEAR'">
                                            <xsl:call-template name="monthsToString">
                                                <xsl:with-param name="value"  select="$month"/>
                                                <xsl:with-param name="source" select="$months"/>
                                            </xsl:call-template>
                                            <xsl:value-of select="concat('. ', $day, ' число')"/>
                                        </xsl:when>
                                        <xsl:when test="$event = 'ONCE_IN_QUARTER'">
                                            <xsl:variable name="period">
                                                <xsl:choose>
                                                    <xsl:when test="($month mod 3) = 1">
                                                        <xsl:value-of select="'01|04|07|10'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 3) = 2">
                                                        <xsl:value-of select="'02|05|08|11'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 3) = 0">
                                                        <xsl:value-of select="'03|06|09|12'"/>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </xsl:variable>

                                            <xsl:call-template name="monthsToString">
                                                <xsl:with-param name="value"  select="$period"/>
                                                <xsl:with-param name="source" select="$months"/>
                                            </xsl:call-template>
                                            <xsl:value-of select="concat('. ', $day, ' число')"/>
                                        </xsl:when>
                                        <xsl:when test="$event = 'ONCE_IN_HALFYEAR'">
                                            <xsl:variable name="period">
                                                <xsl:choose>
                                                    <xsl:when test="($month mod 6) = 1">
                                                        <xsl:value-of select="'01|07'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 6) = 2">
                                                        <xsl:value-of select="'02|08'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 6) = 3">
                                                        <xsl:value-of select="'03|09'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 6) = 4">
                                                        <xsl:value-of select="'04|10'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 6) = 5">
                                                        <xsl:value-of select="'05|11'"/>
                                                    </xsl:when>
                                                    <xsl:when test="($month mod 6) = 0">
                                                        <xsl:value-of select="'06|12'"/>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </xsl:variable>

                                            <xsl:call-template name="monthsToString">
                                                <xsl:with-param name="value"  select="$period"/>
                                                <xsl:with-param name="source" select="$months"/>
                                            </xsl:call-template>
                                            <xsl:value-of select="concat('. ', $day, ' число')"/>
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:with-param>
                            </xsl:call-template>
                        </payDayDescription>
                    </xsl:if>
                </longOfferDetails>
            </RurPaymentLongOfferDocument>
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
                    <xsl:with-param name="key">ourAccount</xsl:with-param>
                    <xsl:with-param name="title">На счет в Сбербанке России</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                    <xsl:with-param name="key">externalAccount</xsl:with-param>
                    <xsl:with-param name="title">На счет в другом банке</xsl:with-param>
                </xsl:call-template>

                <xsl:if test="pu:impliesService('MastercardMoneySendService')">
                    <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                        <xsl:with-param name="key">masterCardExternalCard</xsl:with-param>
                        <xsl:with-param name="title">на карту MasterCard другого банка</xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="pu:impliesService('VisaMoneySendService')">
                    <xsl:call-template name="receiverSubTypeListPrintValueTitle">
                        <xsl:with-param name="key">visaExternalCard</xsl:with-param>
                        <xsl:with-param name="title">на карту VISA другого банка</xsl:with-param>
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

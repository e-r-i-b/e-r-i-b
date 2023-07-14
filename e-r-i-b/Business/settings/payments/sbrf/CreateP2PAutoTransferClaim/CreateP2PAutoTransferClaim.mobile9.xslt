<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                exclude-result-prefixes="xalan mask">

    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>

    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentStatus" select="'documentStatus'"/>
    <xsl:param name="documentId" select="''"/>

    <xsl:param name="longOffer" select="true()"/>
    <xsl:param name="personAvailable" select="true()"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit' and $documentStatus = 'INITIAL'">
                <xsl:apply-templates mode="edit"/>
            </xsl:when>
            <xsl:when test="$mode = 'edit' and $documentStatus = 'INITIAL_LONG_OFFER'">
                <xsl:apply-templates mode="edit-long-offer"/>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>

    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="activeCards" select="document('stored-active-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeCardsNotCredit" select="document('stored-p2p-autotransfer-cards.xml')/entity-list/*"/>
        <initialData>
            <form>CreateP2PAutoTransferClaim</form>
            <CreateP2PAutoTransferClaim>
                <receiver>
                    <receiverType>
                        <xsl:call-template name="receiverTypeList">
                            <xsl:with-param name="name">receiverType</xsl:with-param>
                            <xsl:with-param name="title">Тип получателя платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                        </xsl:call-template>
                    </receiverType>
                    <receiverSubType>
                        <xsl:call-template name="receiverSubTypeList">
                            <xsl:with-param name="name">receiverSubType</xsl:with-param>
                            <xsl:with-param name="title">Тип перевода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                        </xsl:call-template>
                    </receiverSubType>
                    <toResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">toResource</xsl:with-param>
                            <xsl:with-param name="title">Карта зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="cards" select="$activeCards"/>
                            <xsl:with-param name="link" select="toResource"/>
                            <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                        </xsl:call-template>
                    </toResource>
                    <externalCardNumber>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                            <xsl:with-param name="title">Карта клиента Сбербанка</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="externalCardNumber"/>
                        </xsl:call-template>
                    </externalCardNumber>
                    <externalPhoneNumber>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">externalPhoneNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер телефона получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="externalPhoneNumber"/>
                        </xsl:call-template>
                    </externalPhoneNumber>
                </receiver>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Карта списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="cards" select="$activeCardsNotCredit"/>
                        <xsl:with-param name="link" select="fromResource"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                    </xsl:call-template>
                </fromResource>
            </CreateP2PAutoTransferClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-long-offer">
        <document>
            <form>CreateP2PAutoTransferClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <CreateP2PAutoTransferClaimDocument>

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

                <xsl:if test="string-length(number) > 0">
                    <number>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">number</xsl:with-param>
                            <xsl:with-param name="title">Номер автоперевода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="number"/>
                        </xsl:call-template>
                    </number>
                </xsl:if>

                <receiver>

                    <receiverType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverType</xsl:with-param>
                            <xsl:with-param name="title">Тип получателя платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverType"/>
                        </xsl:call-template>
                    </receiverType>

                    <receiverSubType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverSubType</xsl:with-param>
                            <xsl:with-param name="title">Подтип получателя платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverSubType"/>
                        </xsl:call-template>
                    </receiverSubType>

                    <xsl:choose>
                        <xsl:when test="receiverSubType = 'severalCard'">
                            <toResource>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">toResource</xsl:with-param>
                                    <xsl:with-param name="title">Линк счета получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="toResourceLink"/>
                                </xsl:call-template>
                            </toResource>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourCard'">
                            <externalCardNumber>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                                    <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="externalCardNumber"/>
                                </xsl:call-template>
                            </externalCardNumber>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourPhone'">
                            <externalPhoneNumber>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">externalPhoneNumber</xsl:with-param>
                                    <xsl:with-param name="title">Номер телефона получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="externalPhoneNumber"/>
                                </xsl:call-template>
                            </externalPhoneNumber>
                        </xsl:when>
                    </xsl:choose>

                </receiver>

                <fromResource>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Линк счета списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="fromResourceLink"/>
                    </xsl:call-template>
                </fromResource>

                <messageToReceiver>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">messageToReceiver</xsl:with-param>
                        <xsl:with-param name="title">Сообщение получателю</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="messageToReceiver"/>
                    </xsl:call-template>
                </messageToReceiver>

                <autoSubDetails>
                    <longOfferStartDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата начала действия</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferStartDate"/>
                        </xsl:call-template>
                    </longOfferStartDate>
                    <xsl:if test="string-length(updateDate) > 0">
                        <updateDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">updateDate</xsl:with-param>
                                <xsl:with-param name="title">Дата изменения</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="updateDate"/>
                            </xsl:call-template>
                        </updateDate>
                    </xsl:if>
                    <autoSubName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubName</xsl:with-param>
                            <xsl:with-param name="title">Название автоперевода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubName"/>
                        </xsl:call-template>
                    </autoSubName>
                    <autoSubType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubType</xsl:with-param>
                            <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubType"/>
                        </xsl:call-template>
                    </autoSubType>
                    <xsl:if test="autoSubType = 'ALWAYS'">
                        <always>
                            <longOfferEventType>
                                <xsl:call-template name="longOfferEventTypeList">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                </xsl:call-template>
                            </longOfferEventType>
                            <xsl:if test="string-length(nextPayDate)>0">
                                <nextPayDate>
                                    <xsl:call-template name="dateField">
                                       <xsl:with-param name="name">nextPayDate</xsl:with-param>
                                       <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                       <xsl:with-param name="required" select="false()"/>
                                       <xsl:with-param name="editable" select="false()"/>
                                       <xsl:with-param name="visible" select="true()"/>
                                       <xsl:with-param name="value" select="nextPayDate"/>
                                   </xsl:call-template>
                                </nextPayDate>
                            </xsl:if>
                            <sellAmount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">sellAmount</xsl:with-param>
                                    <xsl:with-param name="title">Сумма платежей</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="sellAmount"/>
                                </xsl:call-template>
                            </sellAmount>
                        </always>
                    </xsl:if>
                </autoSubDetails>
            </CreateP2PAutoTransferClaimDocument>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>CreateP2PAutoTransferClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <CreateP2PAutoTransferClaimDocument>

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

                <xsl:if test="string-length(number) > 0">
                    <number>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">number</xsl:with-param>
                            <xsl:with-param name="title">Номер автоперевода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="number"/>
                        </xsl:call-template>
                    </number>
                </xsl:if>

                <receiver>

                    <receiverType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverType</xsl:with-param>
                            <xsl:with-param name="title">Тип получателя платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverType"/>
                        </xsl:call-template>
                    </receiverType>

                    <receiverSubType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverSubType</xsl:with-param>
                            <xsl:with-param name="title">Подтип получателя платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverSubType"/>
                        </xsl:call-template>
                    </receiverSubType>

                    <xsl:choose>
                        <xsl:when test="receiverSubType = 'severalCard'">
                            <toResource>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">toResource</xsl:with-param>
                                    <xsl:with-param name="title">Линк счета получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="toResourceLink"/>
                                </xsl:call-template>
                            </toResource>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourCard'">
                            <externalCardNumber>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">externalCardNumber</xsl:with-param>
                                    <xsl:with-param name="title">Номер карты получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="externalCardNumber"/>
                                </xsl:call-template>
                            </externalCardNumber>
                        </xsl:when>
                        <xsl:when test="receiverSubType = 'ourPhone'">
                            <externalPhoneNumber>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">externalPhoneNumber</xsl:with-param>
                                    <xsl:with-param name="title">Номер телефона получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="externalPhoneNumber"/>
                                </xsl:call-template>
                            </externalPhoneNumber>
                        </xsl:when>
                    </xsl:choose>

                </receiver>

                <fromResource>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Линк счета списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="fromResourceLink"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:if test="string-length(messageToReceiver) > 0">
                    <messageToReceiver>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">messageToReceiver</xsl:with-param>
                            <xsl:with-param name="title">Сообщение получателю</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="messageToReceiver"/>
                        </xsl:call-template>
                    </messageToReceiver>
                </xsl:if>

                <autoSubDetails>
                    <longOfferStartDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name">longOfferStartDate</xsl:with-param>
                            <xsl:with-param name="title">Дата начала действия</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="longOfferStartDate"/>
                        </xsl:call-template>
                    </longOfferStartDate>
                    <xsl:if test="string-length(updateDate) > 0">
                        <updateDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">updateDate</xsl:with-param>
                                <xsl:with-param name="title">Дата изменения</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="updateDate"/>
                            </xsl:call-template>
                        </updateDate>
                    </xsl:if>
                    <autoSubName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubName</xsl:with-param>
                            <xsl:with-param name="title">Название автоперевода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubName"/>
                        </xsl:call-template>
                    </autoSubName>
                    <autoSubType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubType</xsl:with-param>
                            <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubType"/>
                        </xsl:call-template>
                    </autoSubType>
                    <xsl:if test="autoSubType = 'ALWAYS'">
                        <always>
                            <longOfferEventType>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">longOfferEventType</xsl:with-param>
                                    <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="longOfferEventType"/>
                                </xsl:call-template>
                            </longOfferEventType>
                            <xsl:if test="string-length(nextPayDate)>0">
                                <nextPayDate>
                                    <xsl:call-template name="dateField">
                                       <xsl:with-param name="name">nextPayDate</xsl:with-param>
                                       <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                       <xsl:with-param name="required" select="false()"/>
                                       <xsl:with-param name="editable" select="false()"/>
                                       <xsl:with-param name="visible" select="true()"/>
                                       <xsl:with-param name="value" select="nextPayDate"/>
                                   </xsl:call-template>
                                </nextPayDate>
                            </xsl:if>
                            <sellAmount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">sellAmount</xsl:with-param>
                                    <xsl:with-param name="title">Сумма платежей</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="sellAmount"/>
                                </xsl:call-template>
                            </sellAmount>
                            <xsl:if test="string-length(commission) > 0">
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
                        </always>
                    </xsl:if>
                </autoSubDetails>
            </CreateP2PAutoTransferClaimDocument>
        </document>
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
        <xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
        <xsl:param name="link" select="''"/> <!--Ресурс. Ожидается link.getCode() (строка вида "card:1234"), но здесь может оказаться link.toString() (строка вида "Карта № ..."). при редактировании-->
        <xsl:param name="linkNumber"/> <!--Номер карты или счета. при просмотре и редактировании-->

        <xsl:variable name="isValidLinkCode" select="string-length($link)>0 and (starts-with($link, 'card:') or starts-with($link, 'account:'))"/>

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
                    <availableValues>

                        <xsl:choose>
                            <xsl:when test="not($isView)">

                                <xsl:if test="string-length($cards)>0">
                                    <xsl:for-each select="$cards">
                                        <xsl:variable name="code" select="field[@name='code']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <selected><xsl:value-of select="string(($isValidLinkCode and $link=$code) or (not($isValidLinkCode) and @key=$linkNumber))"/></selected>
                                            <displayedValue>
                                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                            </displayedValue>
                                            <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                                <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                            </xsl:if>
                                            <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                                <currency><xsl:value-of select="toResourceCurrency"/></currency>
                                            </xsl:if>
                                        </valueItem>
                                    </xsl:for-each>
                                </xsl:if>

                            </xsl:when>
                            <xsl:otherwise>

                                <!--Просмотр. Доступен только номер карты/счета, поэтому код и displayedValue нужно вычислять -->
                                <xsl:variable name="cardNode" select="$cards[@key = $linkNumber]"/>

                                <xsl:variable name="code">
                                    <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                                </xsl:variable>

                                <xsl:variable name="displayedValue">
                                    <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/> [<xsl:value-of select="$cardNode/field[@name='name']"/>]
                                </xsl:variable>

                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <selected>true</selected>
                                    <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                    <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                        <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                    </xsl:if>
                                    <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                        <currency><xsl:value-of select="toResourceCurrency"/></currency>
                                    </xsl:if>
                                </valueItem>

                            </xsl:otherwise>
                        </xsl:choose>

                    </availableValues>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="longOfferEventTypeList">
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
                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ONCE_IN_MONTH</xsl:with-param>
                    <xsl:with-param name="title">Раз в месяц</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ONCE_IN_WEEK</xsl:with-param>
                    <xsl:with-param name="title">Раз в неделю</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ONCE_IN_QUARTER</xsl:with-param>
                    <xsl:with-param name="title">Раз в квартал</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ONCE_IN_YEAR</xsl:with-param>
                    <xsl:with-param name="title">Раз в год</xsl:with-param>
                </xsl:call-template>

            </availableValues>
        </listType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
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

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">severalCard</xsl:with-param>
                    <xsl:with-param name="title">На свою карту</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ourCard</xsl:with-param>
                    <xsl:with-param name="title">На карту Сбербанка</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ourPhone</xsl:with-param>
                    <xsl:with-param name="title">На карту сбербанка по номеру телефона</xsl:with-param>
                </xsl:call-template>

            </availableValues>
        </listType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
    </xsl:template>

    <xsl:template name="receiverTypeList">
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
                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">several</xsl:with-param>
                    <xsl:with-param name="title">На свою карту</xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="listPrintValueTitle">
                    <xsl:with-param name="key">ph</xsl:with-param>
                    <xsl:with-param name="title">Клиенту Сбербанка</xsl:with-param>
                </xsl:call-template>
            </availableValues>
        </listType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
    </xsl:template>

    <xsl:template name="listPrintValueTitle">
        <xsl:param name="key"/>
        <xsl:param name="title"/>
        <valueItem>
            <value><xsl:value-of select="$key"/></value>
            <title><xsl:value-of select="$title"/></title>
            <selected><xsl:value-of select="string($key = longOfferEventType)"/></selected>
        </valueItem>
    </xsl:template>

</xsl:stylesheet>

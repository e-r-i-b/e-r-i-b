<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:xalan="http://xml.apache.org/xalan"
                exclude-result-prefixes="xalan mask">

    <xsl:template name="autoTransferClaimFields">

        <xsl:if test="string-length(documentNumber) > 0">
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
        </xsl:if>

        <xsl:if test="string-length(documentDate) > 0">
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
        </xsl:if>

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
                    <xsl:with-param name="required" select="false()"/>
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
                    <nextPayDate>
                        <xsl:call-template name="dateField">
                           <xsl:with-param name="name">nextPayDate</xsl:with-param>
                           <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                           <xsl:with-param name="required" select="true()"/>
                           <xsl:with-param name="editable" select="false()"/>
                           <xsl:with-param name="visible" select="true()"/>
                           <xsl:with-param name="value" select="nextPayDate"/>
                       </xsl:call-template>
                    </nextPayDate>
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
                </always>
            </xsl:if>
        </autoSubDetails>

    </xsl:template>
</xsl:stylesheet>
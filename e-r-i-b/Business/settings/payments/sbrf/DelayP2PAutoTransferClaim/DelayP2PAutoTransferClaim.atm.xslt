<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:su="java://com.rssl.phizic.web.actions.StrutsUtils"
                exclude-result-prefixes="xalan mu mask au sh su">

    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:import href="p2p.autotransfer.atm.template.xslt"/>

    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>

	<xsl:param name="mode"                      select="'edit'"/>
	<xsl:param name="documentId"                select="''"/>
	<xsl:param name="documentStatus"            select="'documentStatus'"/>
    <xsl:param name="checkAvailable"            select="''"/>
    <xsl:param name="quickLongOfferAvailable"   select="''"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="longOffer"                 select="true()"/>

	<xsl:variable name="formData" select="/form-data"/>

    <xsl:template match="/">
        <xsl:choose>
            <!--Создание заявки закрытие на автоперевода-->
            <xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
            <!--Просмотр заявки на закрытие автоперевода-->
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <initialData>
            <form>DelayP2PAutoTransferClaim</form>
            <DelayP2PAutoTransferClaim>
                <xsl:call-template name="view-claim-data"/>
            </DelayP2PAutoTransferClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>DelayP2PAutoTransferClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <DelayP2PAutoTransferClaim>
                <xsl:call-template name="view-claim-data"/>
            </DelayP2PAutoTransferClaim>
        </document>
    </xsl:template>

    <xsl:template name="view-claim-data">
        <documentNumber>
            <xsl:call-template name="integerField">
                 <xsl:with-param name="name"        select="'documentNumber'"/>
                 <xsl:with-param name="title"       select="'Номер документа'"/>
                 <xsl:with-param name="required"    select="true()"/>
                 <xsl:with-param name="editable"    select="false()"/>
                 <xsl:with-param name="visible"     select="true()"/>
                 <xsl:with-param name="value"       select="documentNumber"/>
             </xsl:call-template>
        </documentNumber>

        <documentDate>
            <xsl:call-template name="dateField">
                <xsl:with-param name="name"         select="'documentDate'"/>
                <xsl:with-param name="title"        select="'Дата документа'"/>
                <xsl:with-param name="required"     select="true()"/>
                <xsl:with-param name="editable"     select="false()"/>
                <xsl:with-param name="visible"      select="true()"/>
                <xsl:with-param name="value"        select="documentDate"/>
            </xsl:call-template>
        </documentDate>

        <xsl:if test="string-length(number) > 0">
            <number>
                <xsl:call-template name="integerField">
                    <xsl:with-param name="name"     select="'number'"/>
                    <xsl:with-param name="title"    select="'Номер автоперевода'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="number"/>
                </xsl:call-template>
            </number>
        </xsl:if>

        <xsl:variable name="activeCards" select="document('atm-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>

        <receiver>
            <receiverType>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name"     select="'receiverType'"/>
                    <xsl:with-param name="title"    select="'Тип получателя платежа'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="receiverType"/>
                </xsl:call-template>
            </receiverType>
            <receiverSubType>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name"     select="'receiverSubType'"/>
                    <xsl:with-param name="title"    select="'Подтип получателя платежа'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="receiverSubType"/>
                </xsl:call-template>
            </receiverSubType>

            <xsl:choose>
                <xsl:when test="receiverSubType = 'severalCard'">
                    <toResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name"     select="'toResource'"/>
                            <xsl:with-param name="title"    select="'Зачислить на карту'"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible"  select="true()"/>
                            <xsl:with-param name="link"     select="toResource"/>
                            <xsl:with-param name="linkCode" select="toResource"/>
                            <xsl:with-param name="cards"    select="$activeCards"/>
                        </xsl:call-template>
                    </toResource>
                </xsl:when>
                <xsl:when test="receiverSubType = 'ourCard'">
                    <externalCardNumber>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name"     select="'externalCardNumber'"/>
                            <xsl:with-param name="title"    select="'Номер карты получателя'"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible"  select="true()"/>
                            <xsl:with-param name="value"    select="externalCardNumber"/>
                        </xsl:call-template>
                    </externalCardNumber>
                </xsl:when>
            </xsl:choose>
        </receiver>

        <fromResource>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name"     select="'fromResource'"/>
                <xsl:with-param name="title"    select="'Списать со счета'"/>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible"  select="true()"/>
                <xsl:with-param name="link"     select="fromResource"/>
                <xsl:with-param name="linkCode" select="fromResourceLink"/>
                <xsl:with-param name="cards"    select="$activeCards"/>
            </xsl:call-template>
        </fromResource>

        <xsl:if test="string-length(messageToReceiver) > 0">
            <messageToReceiver>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name"     select="'messageToReceiver'"/>
                    <xsl:with-param name="title"    select="'Сообщение получателю'"/>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="messageToReceiver"/>
                </xsl:call-template>
            </messageToReceiver>
        </xsl:if>

        <autoSubDetails>
            <startDate>
                <xsl:call-template name="dateField">
                    <xsl:with-param name="name"     select="'longOfferStartDate'"/>
                    <xsl:with-param name="title"    select="'Дата начала действия'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="longOfferStartDate"/>
                </xsl:call-template>
            </startDate>
            <xsl:if test="string-length(updateDate) > 0">
                <updateDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name"     select="'updateDate'"/>
                        <xsl:with-param name="title"    select="'Дата изменения'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible"  select="true()"/>
                        <xsl:with-param name="value"    select="updateDate"/>
                    </xsl:call-template>
                </updateDate>
            </xsl:if>
            <name>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name"     select="'autoSubName'"/>
                    <xsl:with-param name="title"    select="'Название автоперевода'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="autoSubName"/>
                </xsl:call-template>
            </name>
            <type>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name"     select="'autoSubType'"/>
                    <xsl:with-param name="title"    select="'Тип автоплатежа'"/>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible"  select="true()"/>
                    <xsl:with-param name="value"    select="autoSubType"/>
                </xsl:call-template>
            </type>
            <xsl:if test="autoSubType = 'ALWAYS'">
                <always>
                    <eventType>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name"     select="'longOfferEventType'"/>
                            <xsl:with-param name="title"    select="'Тип события исполнения автоплатежа'"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible"  select="true()"/>
                            <xsl:with-param name="value"    select="longOfferEventType"/>
                        </xsl:call-template>
                    </eventType>
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name"     select="'sellAmount'"/>
                            <xsl:with-param name="title"    select="'Сумма платежей'"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible"  select="true()"/>
                            <xsl:with-param name="value"    select="sellAmount"/>
                        </xsl:call-template>
                    </amount>
                    <nextPayDate>
                        <xsl:call-template name="dateField">
                            <xsl:with-param name="name"     select="'nextPayDate'"/>
                            <xsl:with-param name="title"    select="'Дата ближайшего платежа'"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible"  select="true()"/>
                            <xsl:with-param name="value"    select="nextPayDate"/>
                        </xsl:call-template>
                    </nextPayDate>
                </always>
            </xsl:if>
        </autoSubDetails>
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
        <xsl:param name="accounts" select="''"/>
        <xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
        <xsl:param name="link" select="''"/> <!--Ресурс. Ожидается link.getCode() (строка вида "card:1234"), но здесь может оказаться link.toString() (строка вида "Карта № ..."). при редактировании-->
        <xsl:param name="linkCode"/> <!--Код карты или счета (card:1234). при просмотре и редактировании-->
        <xsl:param name="validators" select="''"/>

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
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <xsl:if test="string-length($cards)>0 or string-length($accounts)>0">
                        <availableValues>

                            <xsl:if test="string-length($cards)>0">
                                <xsl:for-each select="$cards">
                                    <xsl:variable name="code" select="field[@name='code']/text()"/>
                                    <xsl:variable name="selected" select="($isValidLinkCode and $code=$link) or (not($isValidLinkCode) and $code=$linkCode)"/>
                                    <xsl:if test="not($isView) or $selected">
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <productValue>
                                                <cards>
                                                    <xsl:call-template name="products-type-card">
                                                        <xsl:with-param name="activeCard" select="."/>
                                                    </xsl:call-template>
                                                </cards>
                                            </productValue>
                                            <selected><xsl:value-of select="string($selected)"/></selected>
                                            <displayedValue>
                                                <xsl:variable name="cardNumber" select="mask:getCutCardNumber(./@key)"/>
                                                <xsl:variable name="cardName" select="./field[@name='name']"/>
                                                <xsl:variable name="cardBal">
                                                    <xsl:choose>
                                                        <xsl:when test="./field[@name='amountDecimal'] != ''">
                                                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="''"/>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <xsl:variable name="cardCur" select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                                                <xsl:value-of select="concat($cardNumber, ' [', $cardName, '] ', $cardBal, ' ', $cardCur)"/>
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
</xsl:stylesheet>
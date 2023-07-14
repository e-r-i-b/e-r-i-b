<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                exclude-result-prefixes="xalan mask au mu ph">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="templateAvailable" select="''"/>
    <xsl:param name="quickLongOfferAvailable" select="''"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/> <!-- поддержка отображения комисий из ЦОД -->
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit' and not($longOffer)">
                <xsl:apply-templates mode="edit-simple-payment"/>
            </xsl:when>
			<xsl:when test="$mode = 'edit' and $longOffer">
				<xsl:apply-templates mode="edit-long-offer"/>
			</xsl:when>
            <xsl:when test="$mode = 'view' and not($longOffer)">
                <xsl:apply-templates mode="view-simple-payment"/>
            </xsl:when>
			<xsl:when test="$mode = 'view' and $longOffer">
				<xsl:apply-templates mode="view-long-offer"/>
			</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-simple-payment">
        <xsl:variable name="activeDebitAccounts" select="document('stored-active-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCreditAccounts" select="document('stored-active-credit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('atm-stored-active-cards.xml')/entity-list/*"/>
        <xsl:variable name="activeCardsNotCredit" select="document('atm-stored-active-not-credit-cards.xml')/entity-list/*"/>

        <initialData>
            <form>InternalPayment</form>
            <InternalPayment>
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

                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Списать с</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="accounts" select="$activeDebitAccounts"/>
                        <xsl:with-param name="cards" select="$activeCardsNotCredit"/>
                        <xsl:with-param name="linkCode" select="fromResource"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                    </xsl:call-template>
                </fromResource>

                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Зачислить на</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="accounts" select="$activeCreditAccounts"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="linkCode" select="toResource"/>
                        <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                    </xsl:call-template>
                </toResource>

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
                <sellAmount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">sellAmount</xsl:with-param>
                        <xsl:with-param name="title">Сумма списания</xsl:with-param>
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
            </InternalPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-simple-payment">
        <xsl:variable name="activeDebitAccounts" select="document('stored-active-debit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCreditAccounts" select="document('stored-active-credit-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('atm-stored-cards.xml')/entity-list/*"/>

        <document>
            <form>InternalPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>
            <xsl:if test="string-length($templateAvailable)>0">
                <templateAvailable><xsl:value-of select="$templateAvailable"/></templateAvailable>
            </xsl:if>
            <xsl:if test="string-length($quickLongOfferAvailable)>0">
                <autopayable><xsl:value-of select="$quickLongOfferAvailable"/></autopayable>
            </xsl:if>
            <xsl:if test="string-length(documentNumber)>0">
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
            <autoTransferAllowed><xsl:value-of select="boolean(fromResourceType = 'com.rssl.phizic.business.resources.external.CardLink' and toResourceType = 'com.rssl.phizic.business.resources.external.CardLink')"/></autoTransferAllowed>

            <InternalPaymentDocument>
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Счет списания</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="accounts" select="$activeDebitAccounts"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="isView" select="true()"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                    </xsl:call-template>
                </fromResource>

                <toResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">toResource</xsl:with-param>
                        <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="accounts" select="$activeCreditAccounts"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="isView" select="true()"/>
                        <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                    </xsl:call-template>
                </toResource>

                <xsl:if test="string-length(course) > 0">
                    <course>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">course</xsl:with-param>
                            <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="course"/>
                        </xsl:call-template>
                    </course>
                </xsl:if>

                <xsl:if test="string-length(buyAmount) > 0">
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                </xsl:if>

                <xsl:if test="string-length(sellAmount) > 0">
                    <sellAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </sellAmount>
                </xsl:if>

                <xsl:if test="$postConfirmCommission">
                    <xsl:variable name="operations" select="document('writeDownOperations.xml')/Operations/Operation[./Name != 'Частичная выдача' and ./Name != 'Закрытие счета']"/>  <!-- отображаемые микрооперации списания -->
                    <xsl:call-template name="writeDownOperations">
                        <xsl:with-param name="operations" select="$operations"/>
                    </xsl:call-template>
                </xsl:if>

                <xsl:if test="string-length(authorizeCode)>0">
                	<authCode>
                		<xsl:call-template name="stringField">
                		<xsl:with-param name="name">authCode</xsl:with-param>
                		<xsl:with-param name="title">Код авторизации</xsl:with-param>
                		<xsl:with-param name="required" select="true()"/>
                		<xsl:with-param name="editable" select="false()"/>
                		<xsl:with-param name="visible" select="true()"/>
                		<xsl:with-param name="changed" select="false()"/>
                		<xsl:with-param name="value" select="authorizeCode"/>
                	</xsl:call-template>
                	</authCode>
                </xsl:if>
            </InternalPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-long-offer">
        <xsl:variable name="accounts" select="document('stored-active-accounts.xml')/entity-list/*"/>
        <xsl:variable name="cards" select="document('atm-stored-active-cards.xml')/entity-list/*"/>
        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
        <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>
        <xsl:variable name="priorities" select="document('priority.xml')/entity-list/entity"/>

        <initialData>
            <form>InternalPaymentLongOffer</form>
            <InternalPaymentLongOffer>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>
                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="documentDate"/>
                    </xsl:call-template>
                </documentDate>
                <paymentDetails>
                    <fromResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Счет списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="accounts" select="$accounts"/>
                            <xsl:with-param name="cards" select="$cards"/>
                            <xsl:with-param name="isView" select="true()"/>
                            <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                        </xsl:call-template>
                    </fromResource>
                    <toResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">toResource</xsl:with-param>
                            <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="accounts" select="$accounts"/>
                            <xsl:with-param name="cards" select="$cards"/>
                            <xsl:with-param name="isView" select="true()"/>
                            <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                        </xsl:call-template>
                    </toResource>
                    <xsl:if test="string-length(admissionDate) > 0">
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
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="firstPaymentDate"/>
                        </xsl:call-template>
                    </firstPaymentDate>
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
                                    <xsl:with-param name="values" select="'FIXED_SUMMA|FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT'"/>
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
                                    <xsl:with-param name="values" select="'FIXED_SUMMA|FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT'"/>
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
                                    <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
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
                                    <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
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
                                    <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|REMAIND_OVER_SUMMA|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT'"/>
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
                                    <xsl:with-param name="values" select="'FIXED_SUMMA_IN_RECIP_CURR|PERCENT_OF_REMAIND|REMAIND_IN_RECIP|SUMMA_OF_RECEIPT|REMAIND_OVER_SUMMA'"/>
                                    <xsl:with-param name="value" select="longOfferSumType"/>
                                </xsl:call-template>
                            </sumType>
                        </byPension>
                    </fromCardFields>
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
                            <xsl:call-template name="moneyField">
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
            </InternalPaymentLongOffer>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-long-offer">
        <xsl:variable name="accounts" select="document('stored-active-accounts.xml')/entity-list/*"/>
        <xsl:variable name="cards" select="document('atm-stored-cards.xml')/entity-list/*"/>
        <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
        <xsl:variable name="sumTypes" select="document('long-offer-sum-types.xml')/entity-list/entity"/>
        <xsl:variable name="priorities" select="document('priority.xml')/entity-list/entity"/>
        <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>

        <document>
            <form>InternalPaymentLongOffer</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>
            <xsl:if test="string-length($templateAvailable)>0">
                <templateAvailable><xsl:value-of select="$templateAvailable"/></templateAvailable>
            </xsl:if>
            <xsl:if test="string-length($quickLongOfferAvailable)>0">
                <autopayable><xsl:value-of select="$quickLongOfferAvailable"/></autopayable>
            </xsl:if>
            <xsl:if test="string-length(documentNumber)>0">
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
            <InternalPaymentLongOfferDocument>
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
                <paymentDetails>
                    <fromResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="title">Счет списания</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="accounts" select="$accounts"/>
                            <xsl:with-param name="cards" select="$cards"/>
                            <xsl:with-param name="isView" select="true()"/>
                            <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                        </xsl:call-template>
                    </fromResource>
                    <toResource>
                        <xsl:call-template name="resourceField">
                            <xsl:with-param name="name">toResource</xsl:with-param>
                            <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="accounts" select="$accounts"/>
                            <xsl:with-param name="cards" select="$cards"/>
                            <xsl:with-param name="isView" select="true()"/>
                            <xsl:with-param name="linkNumber" select="toAccountSelect"/>
                        </xsl:call-template>
                    </toResource>
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
                                <xsl:call-template name="moneyField">
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
                    </xsl:if>
                    <xsl:if test="string-length(course) > 0">
                        <course>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">course</xsl:with-param>
                                <xsl:with-param name="title">Курс конверсии</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="course"/>
                            </xsl:call-template>
                        </course>
                    </xsl:if>
                    <xsl:if test="string-length(commission) > 0">
                        <xsl:call-template name="commission">
                            <xsl:with-param name="commissionAmount" select="commission"/>
                            <xsl:with-param name="commissionCurrency" select="commissionCurrency"/>
                        </xsl:call-template>
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
                            <xsl:with-param name="visible" select="true()"/>
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
            </InternalPaymentLongOfferDocument>
        </document>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="linkCode"/> <!--Код ресурса (card:1234, account:4321). при редактировании-->
        <xsl:param name="visible"/>
        <xsl:param name="cards"/>
        <xsl:param name="accounts"/>
        <xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
        <xsl:param name="linkNumber"/> <!--Номер карты или счета. при просмотре-->
        <xsl:param name="validators" select="''"/>

        <xsl:variable name="isValidLinkCode" select="string-length($linkCode)>0 and (starts-with($linkCode, 'card:') or starts-with($linkCode, 'account:'))"/>

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
                    <availableValues>

                        <xsl:choose>
                            <xsl:when test="not($isView)">

                                <xsl:if test="string-length($accounts)>0">
                                    <xsl:for-each select="$accounts">
                                        <xsl:variable name="code" select="field[@name='code']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <productValue>
                                                <accounts>
                                                    <xsl:call-template name="products-type-account">
                                                        <xsl:with-param name="activeAccount" select="."/>
                                                    </xsl:call-template>
                                                </accounts>
                                            </productValue>
                                            <selected><xsl:value-of select="string(($isValidLinkCode and $code=$linkCode) or (not($isValidLinkCode) and @key=$linkNumber))"/></selected>
                                            <displayedValue>
                                                <xsl:variable name="accNumber" select="au:getFormattedAccountNumber(./@key)"/>
                                                <xsl:variable name="accName" select="./field[@name='name']"/>
                                                <xsl:variable name="accBal">
                                                    <xsl:choose>
                                                        <xsl:when test="./field[@name='amountDecimal'] != ''">
                                                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <xsl:value-of select="''"/>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </xsl:variable>
                                                <xsl:variable name="accCur" select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                                                <xsl:value-of select="concat($accNumber, ' [', $accName, '] ', $accBal, ' ', $accCur)"/>
                                            </displayedValue>
                                        </valueItem>
                                    </xsl:for-each>
                                </xsl:if>

                                <xsl:if test="string-length($cards)>0">
                                    <xsl:for-each select="$cards">
                                        <xsl:variable name="code" select="field[@name='code']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$code"/></value>
                                            <productValue>
                                                <cards>
                                                    <xsl:call-template name="products-type-card">
                                                        <xsl:with-param name="activeCard" select="."/>
                                                    </xsl:call-template>
                                                </cards>
                                            </productValue>
                                            <selected><xsl:value-of select="string(($isValidLinkCode and $code=$linkCode) or (not($isValidLinkCode) and @key=$linkNumber))"/></selected>
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
                                    </xsl:for-each>
                                </xsl:if>

                            </xsl:when>
                            <xsl:otherwise>

                                <!--Просмотр. Доступен только номер карты/счета, поэтому код нужно вычислять -->
                                <xsl:variable name="accountNode" select="$accounts[@key = $linkNumber]"/>
                                <xsl:variable name="cardNode" select="$cards[@key = $linkNumber]"/>

                                <xsl:variable name="code">
                                    <xsl:choose>
                                        <xsl:when test="$accountNode">
                                            <xsl:value-of select="$accountNode/field[@name='code']/text()"/>
                                        </xsl:when>
                                        <xsl:when test="$cardNode">
                                            <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:variable>

                                <xsl:variable name="displayedValue">
                                    <xsl:choose>
                                        <xsl:when test="$accountNode">
                                            <xsl:variable name="accNumber" select="au:getFormattedAccountNumber($accountNode/@key)"/>
                                            <xsl:variable name="accName" select="$accountNode/field[@name='name']"/>
                                            <xsl:variable name="accBal">
                                                <xsl:choose>
                                                    <xsl:when test="$accountNode/field[@name='amountDecimal'] != ''">
                                                        <xsl:value-of select="format-number($accountNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="''"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:variable>
                                            <xsl:variable name="accCur" select="mu:getCurrencySign($accountNode/field[@name='currencyCode'])"/>
                                            <xsl:value-of select="concat($accNumber, ' [', $accName, '] ', $accBal, ' ', $accCur)"/>
                                        </xsl:when>
                                        <xsl:when test="$cardNode">
                                            <xsl:variable name="cardNumber" select="mask:getCutCardNumber($cardNode/@key)"/>
                                            <xsl:variable name="cardName" select="$cardNode/field[@name='name']"/>
                                            <xsl:variable name="cardBal">
                                                <xsl:choose>
                                                    <xsl:when test="$cardNode/field[@name='amountDecimal'] != ''">
                                                        <xsl:value-of select="format-number($cardNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="''"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:variable>
                                            <xsl:variable name="cardCur" select="mu:getCurrencySign($cardNode/field[@name='currencyCode'])"/>
                                            <xsl:value-of select="concat($cardNumber, ' [', $cardName, '] ', $cardBal, ' ', $cardCur)"/>
                                        </xsl:when>
                                    </xsl:choose>
                                </xsl:variable>

                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <productValue>
                                        <xsl:choose>
                                            <xsl:when test="starts-with($code,'card')">
                                                <cards>
                                                    <xsl:call-template name="products-type-card">
                                                        <xsl:with-param name="activeCard" select="$cardNode"/>
                                                    </xsl:call-template>
                                                </cards>
                                            </xsl:when>
                                            <xsl:when test="starts-with($code,'account')">
                                                <accounts>
                                                    <xsl:call-template name="products-type-account">
                                                        <xsl:with-param name="activeAccount" select="$accountNode"/>
                                                    </xsl:call-template>
                                                </accounts>
                                            </xsl:when>
                                       </xsl:choose>
                                    </productValue>
                                    <selected>true</selected>
                                    <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                </valueItem>

                            </xsl:otherwise>
                        </xsl:choose>

                    </availableValues>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>
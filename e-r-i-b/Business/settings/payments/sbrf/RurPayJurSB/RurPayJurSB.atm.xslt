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
	<xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="documentId" select="''"/>
	<xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="templateAvailable" select="''"/>
    <xsl:param name="quickLongOfferAvailable" select="''"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="isByTemplate"/> <!--платеж создается по шаблону-->
    <xsl:param name="isTemplate" select="'isTemplate'"/>
    <xsl:param name="groupRiskRank" select="''"/> <!--степень риска ПУ-->

	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="isInitialLongOfferState" select="$documentStatus = 'INITIAL_LONG_OFFER'"/>

    <xsl:template match="/">
        <xsl:choose>
            <!--Создание обычного платежа-->
            <xsl:when test="$mode = 'edit' and not($isInitialLongOfferState)">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
            <!--Просмотр обычного платежа-->
            <xsl:when test="$mode = 'view' and not($longOffer)">
                <xsl:apply-templates mode="view"/>
			</xsl:when>
            <!--Создание шинного автоплатежа-->
            <xsl:when test="$mode = 'edit' and $isInitialLongOfferState">
                <xsl:apply-templates mode="edit-auto-sub-info"/>
            </xsl:when>
            <!--Просмотр шинного автоплатежа-->
            <xsl:when test="$mode = 'view' and $longOffer">
                <xsl:apply-templates mode="view-auto-sub-info"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards.xml')/entity-list"/>
        <xsl:variable name="bankDetails" select="bankDetails"/>

        <document>
            <!-- 1. Реквизиты документа -->
            <form>RurPayJurSB</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
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

            <RurPayJurSBDocument>
                <!-- 2. Информация по получателю -->
                <receiver>
                    <!-- (2.1) Текстовка с описанием получателя -->
                    <xsl:if test="string-length(receiverDescription)>0">
                        <description>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverDescription</xsl:with-param>
                                <xsl:with-param name="title">Информация по получателю средств</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverDescription"/>
                            </xsl:call-template>
                        </description>
                    </xsl:if>

                    <!-- (2.2) Наименование получателя -->
                    <xsl:if test="string-length(receiverName)>0">
                        <name>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverName</xsl:with-param>
                                <xsl:with-param name="title">Наименование</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverName"/>
                            </xsl:call-template>
                        </name>
                    </xsl:if>

                    <!-- (2.3) Услуга  -->
                    <xsl:if test="string-length(nameService)>0">
                        <serviceName>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">nameService</xsl:with-param>
                                <xsl:with-param name="title">Услуга</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="nameService"/>
                            </xsl:call-template>
                        </serviceName>
                    </xsl:if>

                    <!-- (2.4) Информация по банку получателя -->
                    <xsl:if test="$bankDetails = 'true'">
                        <bankDetails>
                            <!-- 2.4.1 ИНН получателя -->
                            <INN>
                                <xsl:call-template name="integerField">
                                    <xsl:with-param name="name">receiverINN</xsl:with-param>
                                    <xsl:with-param name="title">ИНН</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverINN"/>
                                </xsl:call-template>
                            </INN>

                            <!-- 2.4.2 Счёт получателя -->
                            <account>
                                <xsl:call-template name="integerField">
                                    <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                    <xsl:with-param name="title">Счёт</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverAccount"/>
                                </xsl:call-template>
                            </account>

                            <bank>
                                <!-- 2.4.3 Наименование банка получателя -->
                                <name>
                                    <xsl:call-template name="stringField">
                                        <xsl:with-param name="name">receiverBankName</xsl:with-param>
                                        <xsl:with-param name="title">Наименование</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="receiverBankName"/>
                                    </xsl:call-template>
                                </name>

                                <!-- 2.4.4 БИК банка получателя -->
                                <BIC>
                                    <xsl:call-template name="integerField">
                                        <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                        <xsl:with-param name="title">БИК</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="receiverBIC"/>
                                    </xsl:call-template>
                                </BIC>

                                <!-- (2.4.5) Корсчёт банка получателя -->
                                <xsl:if test="string-length(receiverCorAccount)>0">
                                    <corAccount>
                                        <xsl:call-template name="integerField">
                                            <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                            <xsl:with-param name="title">Корсчёт</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="receiverCorAccount"/>
                                        </xsl:call-template>
                                    </corAccount>
                                </xsl:if>
                            </bank>
                        </bankDetails>
                    </xsl:if>
                </receiver>

                <!-- 3. Источник списания -->
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:variable name="fromResourceLink" select="fromResourceLink"/>
                <xsl:variable name="cardNode" select="$activeCards/entity[$fromResourceLink=./field[@name='code'] or $fromAccountSelect/text() = ./@key]"/>
                <xsl:variable name="accountNode" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list/entity[$fromResourceLink=./field[@name='code']/text() or $fromAccountSelect/text() = ./@key]"/>
                <xsl:variable name="fromResourceCode">
                    <xsl:choose>
                        <xsl:when test="starts-with($fromResourceLink, 'account')">
                            <xsl:value-of select="$accountNode/field[@name='code']/text()"/>
                        </xsl:when>
                        <xsl:when test="$cardNode">
                            <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="starts-with($fromResourceLink, 'account')">
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

                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Списать со счета</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$fromResourceCode"/>
                        <xsl:with-param name="displayedValue" select="$displayedValue"/>
                        <xsl:with-param name="activeCard" select="$cardNode"/>
                        <xsl:with-param name="activeAccount" select="$accountNode"/>
                    </xsl:call-template>
                </fromResource>

                <!-- 4. Детали платежа -->
                <paymentDetails>

                    <externalFields>
                        <field>
                            <xsl:call-template name="booleanField">
                                <xsl:with-param name="name">bankDetails</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="bankDetails"/>
                            </xsl:call-template>
                        </field>

                        <field>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">recipient</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="recipient"/>
                            </xsl:call-template>
                        </field>

                        <xsl:variable name="isOurProvider" select="recipient and string(recipient)"/>

                        <!-- 4.1 Дополнительные поля из биллинга -->
                        <xsl:for-each select="$extendedFields">
                            <xsl:variable name="id" select="./NameBS"/>
                            <xsl:variable name="val">
                                <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                                <xsl:choose>
                                    <!--если задано значение явно, юзаем его-->
                                    <xsl:when test="string-length($currentValue) > 0">
                                        <xsl:value-of select="$currentValue"/>
                                    </xsl:when>
                                    <!--иначе - инициализирующее значение-->
                                    <xsl:otherwise>
                                        <xsl:value-of select="./DefaultValue"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>

                            <xsl:variable name="editable" select="./IsEditable='true' and (not($isByTemplate) or ./IsMainSum = 'true' or not($isOurProvider) or $groupRiskRank='LOW')"/>

                            <!--Для рискованных полей приходит признак "IsRiskRequisite"-->
                            <xsl:variable name="requisiteTypes" select="./RequisiteTypes"/>
                            <xsl:variable name="isRiskField" select="string-length($requisiteTypes) > 0 and contains($requisiteTypes, 'IsRiskRequisite')"/>

                            <xsl:choose>
                                <xsl:when test="./Type = 'date'">
                                    <field>
                                        <xsl:call-template name="dateField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'calendar'">
                                    <field>
                                        <xsl:call-template name="calendarField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'string'">
                                    <field>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="minLength" select="./MinLength"/>
                                            <xsl:with-param name="maxLength" select="./MaxLength"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="subType" select="./BusinessSubType"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'integer'">
                                    <field>
                                        <xsl:call-template name="integerField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="subType" select="./BusinessSubType"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'number'">
                                    <field>
                                        <xsl:call-template name="numberField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="subType" select="./BusinessSubType"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'money'">
                                    <field>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsMainSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'list'">
                                    <field>
                                        <xsl:call-template name="listField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="listValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="default" select="./DefaultValue"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'set'">
                                    <field>
                                        <xsl:call-template name="setField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="availableValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="checkedValues" select="$formData/*[name()=$id]/text()"/>
                                            <xsl:with-param name="validators" select="./Validators/*"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                    </externalFields>
                </paymentDetails>

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

            </RurPayJurSBDocument>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>RurPayJurSB</form>
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
            <xsl:if test="string-length(billingDocumentNumber)>0">
                <billingDocumentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">billingDocumentNumber</xsl:with-param>
                        <xsl:with-param name="title">номер операции</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="billingDocumentNumber"/>
                    </xsl:call-template>
                </billingDocumentNumber>
            </xsl:if>
            <RurPayJurSBDocument>
                <xsl:call-template name="viewPayment"/>
            </RurPayJurSBDocument>
        </document>
    </xsl:template>

    <xsl:template name="viewPayment">
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards.xml')/entity-list"/>
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <!-- 2. Информация по получателю -->
        <receiver>
            <!-- (2.1) Текстовка с описанием получателя -->

            <!-- (2.2) Наименование получателя -->
            <xsl:if test="string-length(receiverName)>0">
                <name>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverName</xsl:with-param>
                        <xsl:with-param name="title">Наименование</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverName"/>
                    </xsl:call-template>
                </name>
            </xsl:if>

            <!-- (2.3) Услуга  -->
            <xsl:if test="string-length(nameService)>0">
                <serviceName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">nameService</xsl:with-param>
                        <xsl:with-param name="title">Услуга</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="nameService"/>
                    </xsl:call-template>
                </serviceName>
            </xsl:if>

            <!-- (2.4) Информация по банку получателя -->
            <xsl:if test="$bankDetails = 'true'">
                <bankDetails>
                    <!-- 2.4.1 ИНН получателя -->
                    <INN>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverINN</xsl:with-param>
                            <xsl:with-param name="title">ИНН</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverINN"/>
                        </xsl:call-template>
                    </INN>

                    <!-- 2.4.2 Счёт получателя -->
                    <account>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverAccount</xsl:with-param>
                            <xsl:with-param name="title">Счёт</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAccount"/>
                        </xsl:call-template>
                    </account>

                    <bank>
                        <!-- 2.4.3 Наименование банка получателя -->
                        <name>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverBankName</xsl:with-param>
                                <xsl:with-param name="title">Наименование</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverBankName"/>
                            </xsl:call-template>
                        </name>

                        <!-- 2.4.4 БИК банка получателя -->
                        <BIC>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                <xsl:with-param name="title">БИК</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverBIC"/>
                            </xsl:call-template>
                        </BIC>

                        <!-- (2.4.5) Корсчёт банка получателя -->
                        <xsl:if test="string-length(receiverCorAccount)>0">
                            <corAccount>
                                <xsl:call-template name="integerField">
                                    <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                    <xsl:with-param name="title">Корсчёт</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverCorAccount"/>
                                </xsl:call-template>
                            </corAccount>
                        </xsl:if>
                    </bank>
                </bankDetails>
            </xsl:if>
        </receiver>

        <!-- 3. Источник списания -->
        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
        <xsl:variable name="fromResourceLink" select="fromResourceLink"/>
        <xsl:variable name="cardNode" select="$activeCards/entity[$fromResourceLink=./field[@name='code'] or $fromAccountSelect/text() = ./@key]"/>
        <xsl:variable name="accountNode" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list/entity[$fromResourceLink=./field[@name='code']/text() or $fromAccountSelect/text() = ./@key]"/>
        <xsl:variable name="fromResourceCode">
            <xsl:choose>
                <xsl:when test="starts-with($fromResourceLink, 'account')">
                    <xsl:value-of select="$accountNode/field[@name='code']/text()"/>
                </xsl:when>
                <xsl:when test="$cardNode">
                    <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="starts-with($fromResourceLink, 'account')">
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

        <fromResource>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name">fromResource</xsl:with-param>
                <xsl:with-param name="title">Списать со счета</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="$fromResourceCode"/>
                <xsl:with-param name="displayedValue" select="$displayedValue"/>
                <xsl:with-param name="activeCard" select="$cardNode"/>
                <xsl:with-param name="activeAccount" select="$accountNode"/>
            </xsl:call-template>
        </fromResource>

        <!-- 4. Детали платежа -->
        <paymentDetails>

            <xsl:variable name="message" select="su:removeSessionAttribute('com.rssl.phizic.web.actions.SESSION_ADDITIONAL_MESSAGE_KEY')"/>
            <externalFields>
                <!-- 4.1 Дополнительные поля из биллинга -->
                <xsl:for-each select="$extendedFields">
                    <xsl:variable name="id" select="./NameBS"/>
                    <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
                    <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                    <!--<xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>-->
                    <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                    <xsl:variable name="changed" select="$formData/*[name()=$id]/@changed = 'true'"/>
                    <!--Для рискованных полей приходит признак "IsRiskRequisite"-->
                    <xsl:variable name="requisiteTypes" select="./RequisiteTypes"/>
                    <xsl:variable name="isRiskField" select="string-length($requisiteTypes) > 0 and contains($requisiteTypes, 'IsRiskRequisite')"/>

                    <xsl:choose>
                        <xsl:when test="$isHideInConfirmation or $hidden">
                            <!-- пропускаем поле -->
                        </xsl:when>
                        <xsl:when test="./Type = 'date'">
                            <field>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'calendar'">
                            <field>
                                <xsl:call-template name="calendarField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'string'">
                            <!--получаем предупреждение о смене мобильного оператора-->
                            <xsl:variable name="desc">
                                <xsl:choose>
                                    <xsl:when test="./NameBS = 'RecIdentifier' and $message != ''">
                                        <xsl:value-of select="$message"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="./Description"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>

                            <field>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="$desc"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="subType" select="./BusinessSubType"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'link'">
                            <field>
                                <xsl:call-template name="linkField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="minLength" select="./MinLength"/>
                                    <xsl:with-param name="maxLength" select="./MaxLength"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'integer'">
                            <field>
                                <xsl:call-template name="integerField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="subType" select="./BusinessSubType"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'number'">
                            <field>
                                <xsl:call-template name="numberField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="subType" select="./BusinessSubType"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'money'">
                            <field>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="./IsMainSum"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'list'">
                            <field>
                                <xsl:call-template name="listField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="listValues" select="./Menu/MenuItem"/>
                                    <xsl:with-param name="default" select="./DefaultValue"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'set'">
                            <field>
                                <xsl:call-template name="setField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="availableValues" select="./Menu/MenuItem"/>
                                    <xsl:with-param name="checkedValues" select="$currentValue"/>
                                    <xsl:with-param name="validators" select="./Validators/*"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
            </externalFields>

            <!-- (4.3) Комиссия -->
            <xsl:if test="not($longOffer or $isTemplate = 'true')">
                <xsl:call-template name="commission">
                    <xsl:with-param name="commissionAmount"><xsl:value-of select="commission"/></xsl:with-param>
                    <xsl:with-param name="commissionCurrency"><xsl:value-of select="commissionCurrency"/></xsl:with-param>
                </xsl:call-template>
            </xsl:if>

        </paymentDetails>

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

    </xsl:template>

    <xsl:template match="/form-data" mode="edit-auto-sub-info">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="accessAutoSubTypes" select="document('supportedAutoPaysATM.xml')/entity-list"/>
        <document>
            <form>CreateAutoSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
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

            <CreateAutoSubscriptionDocument>
                <xsl:call-template name="viewPayment"/>

                <autoSubParameters>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubName</xsl:with-param>
                            <xsl:with-param name="title">Название автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="maxLength" select="20"/>
                            <xsl:with-param name="value">
                                <xsl:choose>
                                    <xsl:when test="string-length(autoSubName)>0">
                                        <xsl:value-of select="autoSubName"/>
                                    </xsl:when>
                                    <xsl:when test="20>=string-length(receiverName)">
                                        <xsl:value-of select="sh:replaceQuotes(receiverName)"/>
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </name>

                    <xsl:choose>
                        <xsl:when test="count($accessAutoSubTypes/entity) > 0">
                            <type>
                                <xsl:call-template name="autoSubTypeField">
                                    <xsl:with-param name="name">autoSubType</xsl:with-param>
                                    <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="autoSubTypes" select="$autoSubTypes"/>
                                    <xsl:with-param name="accessAutoSubTypes" select="$accessAutoSubTypes"/>
                                    <xsl:with-param name="autoSubType" select="autoSubType"/>
                                </xsl:call-template>
                            </type>
                        </xsl:when>
                        <xsl:otherwise>
                            <type>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">autoSubType</xsl:with-param>
                                    <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value">В адрес данного поставщика услуг невозможно оформить автоплатеж</xsl:with-param>
                                </xsl:call-template>
                            </type>
                        </xsl:otherwise>
                    </xsl:choose>

                    <always>
                        <eventType>
                            <xsl:call-template name="autoSubEventTypeField">
                                <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="autoSubEventTypes" select="$autoSubTypes/entity[@key='ALWAYS']"/>
                                <xsl:with-param name="autoSubEventType" select="autoSubEventType"/>
                            </xsl:call-template>
                        </eventType>
                        <nextPayDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">nextPayDateAlways</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="nextPayDateAlways"/>
                            </xsl:call-template>
                        </nextPayDate>
                        <amount>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">alwaysAmount</xsl:with-param>
                                <xsl:with-param name="title">Сумма</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="alwaysAmount"/>
                            </xsl:call-template>
                        </amount>
                    </always>

                    <invoice>
                        <eventType>
                            <xsl:call-template name="autoSubEventTypeField">
                                <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="autoSubEventTypes" select="$autoSubTypes/entity[@key='INVOICE']"/>
                                <xsl:with-param name="autoSubEventType" select="autoSubEventType"/>
                            </xsl:call-template>
                        </eventType>
                        <startDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">nextPayDateInvoice</xsl:with-param>
                                <xsl:with-param name="title">Ожидаемая дата оплаты счета</xsl:with-param>
                                <xsl:with-param name="description">За сутки до указанной Вами даты при наличии выставленных счетов Вам будет направлено SMS с информацией и возможностью отказа от платежа. Начиная с указанной даты раз в три дня будет осуществляться проверка наличия выставленного счета. Оплата счета произойдет по факту его выставления.</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="nextPayDateInvoice"/>
                            </xsl:call-template>
                        </startDate>
                        <amount>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">invoiceMaxAmount</xsl:with-param>
                                <xsl:with-param name="title">Максимальный размер платежа</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="invoiceMaxAmount"/>
                            </xsl:call-template>
                        </amount>
                    </invoice>

                    <xsl:choose>
                        <xsl:when test="string-length(commission)>0 and commission &gt; 0.00">
                            <commission>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">commission</xsl:with-param>
                                    <xsl:with-param name="title">Сумма комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="commission"/>
                                </xsl:call-template>
                            </commission>
                            <commissionCurrency>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">commissionCurrency</xsl:with-param>
                                    <xsl:with-param name="title">Валюта комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="commissionCurrency"/>
                                </xsl:call-template>
                            </commissionCurrency>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="isWithCommission = 'true'">
                                    <commission>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name">commission</xsl:with-param>
                                            <xsl:with-param name="title">Комиссия</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="'При осуществлении платежей взимается комиссия согласно тарифам банка'"/>
                                        </xsl:call-template>
                                   </commission>
                                </xsl:when>
                                <xsl:otherwise>
                                    <commission>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name">commission</xsl:with-param>
                                            <xsl:with-param name="title">Сумма комиссии</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="'0.00'"/>
                                        </xsl:call-template>
                                    </commission>
                                    <commissionCurrency>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name">commissionCurrency</xsl:with-param>
                                            <xsl:with-param name="title">Валюта комиссии</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="'руб.'"/>
                                        </xsl:call-template>
                                    </commissionCurrency>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>

                </autoSubParameters>
            </CreateAutoSubscriptionDocument>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="view-auto-sub-info">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <document>
            <form>CreateAutoSubscriptionPayment</form>
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
            <xsl:if test="string-length(billingDocumentNumber)>0">
                <billingDocumentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">billingDocumentNumber</xsl:with-param>
                        <xsl:with-param name="title">номер операции</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="billingDocumentNumber"/>
                    </xsl:call-template>
                </billingDocumentNumber>
            </xsl:if>
            <CreateAutoSubscriptionDocument>
                <xsl:call-template name="viewPayment"/>

                <autoSubParameters>
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoSubName</xsl:with-param>
                            <xsl:with-param name="title">Название автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoSubName"/>
                        </xsl:call-template>
                    </name>

                    <type>
                        <xsl:call-template name="autoSubTypeField">
                            <xsl:with-param name="name">autoSubType</xsl:with-param>
                            <xsl:with-param name="title">Тип автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="autoSubTypes" select="$autoSubTypes"/>
                            <xsl:with-param name="autoSubType" select="autoSubType"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </type>

                    <xsl:if test="autoSubType = 'ALWAYS'">
                        <always>
                            <eventType>
                                <xsl:call-template name="autoSubEventTypeField">
                                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                    <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="autoSubEventTypes" select="$autoSubTypes/entity[@key='ALWAYS']"/>
                                    <xsl:with-param name="autoSubEventType" select="autoSubEventType"/>
                                    <xsl:with-param name="isView" select="true()"/>
                                </xsl:call-template>
                            </eventType>
                            <nextPayDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">nextPayDateAlways</xsl:with-param>
                                    <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="nextPayDateAlways"/>
                                </xsl:call-template>
                            </nextPayDate>
                            <amount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">alwaysAmount</xsl:with-param>
                                    <xsl:with-param name="title">Сумма</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="alwaysAmount"/>
                                </xsl:call-template>
                            </amount>
                        </always>
                    </xsl:if>

                    <xsl:if test="autoSubType = 'INVOICE'">
                        <invoice>
                            <eventType>
                                <xsl:call-template name="autoSubEventTypeField">
                                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                                    <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="autoSubEventTypes" select="$autoSubTypes/entity[@key='INVOICE']"/>
                                    <xsl:with-param name="autoSubEventType" select="autoSubEventType"/>
                                    <xsl:with-param name="isView" select="true()"/>
                                </xsl:call-template>
                            </eventType>
                            <startDate>
                                <xsl:call-template name="dateField">
                                    <xsl:with-param name="name">nextPayDateInvoice</xsl:with-param>
                                    <xsl:with-param name="title">Ожидаемая дата оплаты счета</xsl:with-param>
                                    <xsl:with-param name="description">За сутки до указанной Вами даты при наличии выставленных счетов Вам будет направлено SMS с информацией и возможностью отказа от платежа. Начиная с указанной даты раз в три дня будет осуществляться проверка наличия выставленного счета. Оплата счета произойдет по факту его выставления.</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="nextPayDateInvoice"/>
                                </xsl:call-template>
                            </startDate>
                            <amount>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">invoiceMaxAmount</xsl:with-param>
                                    <xsl:with-param name="title">Максимальный размер платежа</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="invoiceMaxAmount"/>
                                </xsl:call-template>
                            </amount>
                        </invoice>
                    </xsl:if>

                    <xsl:choose>
                        <xsl:when test="string-length(commission)>0 and commission &gt; 0.00">
                            <commission>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">commission</xsl:with-param>
                                    <xsl:with-param name="title">Сумма комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="commission"/>
                                </xsl:call-template>
                            </commission>
                            <commissionCurrency>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">commissionCurrency</xsl:with-param>
                                    <xsl:with-param name="title">Валюта комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="commissionCurrency"/>
                                </xsl:call-template>
                            </commissionCurrency>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="isWithCommission = 'true'">
                                    <commission>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name">commission</xsl:with-param>
                                            <xsl:with-param name="title">Комиссия</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="'При осуществлении платежей взимается комиссия согласно тарифам банка'"/>
                                        </xsl:call-template>
                                   </commission>
                                </xsl:when>
                                <xsl:otherwise>
                                    <commission>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name">commission</xsl:with-param>
                                            <xsl:with-param name="title">Сумма комиссии</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="'0.00'"/>
                                        </xsl:call-template>
                                    </commission>
                                    <commissionCurrency>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name">commissionCurrency</xsl:with-param>
                                            <xsl:with-param name="title">Валюта комиссии</xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="'руб.'"/>
                                        </xsl:call-template>
                                    </commissionCurrency>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>

                </autoSubParameters>
            </CreateAutoSubscriptionDocument>
        </document>
    </xsl:template>

    <xsl:template name="linkField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="maxLength" select="''"/>
        <xsl:param name="minLength" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'link'"/>
            <xsl:with-param name="maxLength" select="$maxLength"/>
            <xsl:with-param name="minLength" select="$minLength"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="valueTag">
                <linkType>
                    <text><xsl:value-of select="substring-before($value,'|')"/></text>
                    <url><xsl:value-of select="substring-after($value,'|')"/></url>
                </linkType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="calendarField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'calendar'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="valueTag">
                <calendarType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </calendarType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>
        <xsl:param name="displayedValue"/>
        <xsl:param name="activeCard"/>
        <xsl:param name="activeAccount"/>

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
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <xsl:if test="string-length($value)>0">
                        <availableValues>
                            <valueItem>
                                <value><xsl:value-of select="$value"/></value>
                                <productValue>
                                    <xsl:choose>
                                        <xsl:when test="starts-with($value,'card')">
                                            <cards>
                                                <xsl:call-template name="products-type-card">
                                                    <xsl:with-param name="activeCard" select="$activeCard"/>
                                                </xsl:call-template>
                                            </cards>
                                        </xsl:when>
                                        <xsl:when test="starts-with($value,'account')">
                                            <accounts>
                                                <xsl:call-template name="products-type-account">
                                                    <xsl:with-param name="activeAccount" select="$activeAccount"/>
                                                </xsl:call-template>
                                            </accounts>
                                       </xsl:when>
                                    </xsl:choose>
                                </productValue>
                                <selected>true</selected>
                                <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                            </valueItem>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="listField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="isSum"/>
        <xsl:param name="listValues"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="default" select="''"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

         <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="isSum" select="$isSum"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <xsl:if test="count($listValues)>0">
                        <availableValues>
                            <xsl:for-each select="$listValues">
                                <xsl:variable name="code">
                                    <xsl:choose>
                                        <xsl:when test="./Id != ''">
                                            <xsl:value-of select="./Id"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="./Value"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>
                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <title><xsl:value-of select="./Value"/></title>
                                    <selected><xsl:value-of select="string($code = $value)"/></selected>
                                    <xsl:choose>
                                        <xsl:when test="string-length($default) > 0">
                                            <default><xsl:value-of select="string($default = $code)"/></default>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <default>false</default>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </valueItem>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </listType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="autoSubTypeField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="accessAutoSubTypes"/>
        <xsl:param name="autoSubTypes"/>
        <xsl:param name="autoSubType"/>
        <xsl:param name="isView" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:choose>
                            <xsl:when test="not($isView)">
                                <xsl:for-each select="$accessAutoSubTypes/entity">
                                    <xsl:variable name="key" select="./@key"/>
                                    <xsl:if test="$autoSubTypes/entity[@key=$key]">
                                        <valueItem>
                                            <value><xsl:value-of select="$key"/></value>
                                            <title><xsl:value-of select="$autoSubTypes/entity[@key=$key]/@description"/></title>
                                            <selected><xsl:value-of select="string($key = $autoSubType)"/></selected>
                                        </valueItem>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <valueItem>
                                    <value><xsl:value-of select="$autoSubType"/></value>
                                    <title><xsl:value-of select="$autoSubTypes/entity[@key=$autoSubType]/@description"/></title>
                                    <selected>true</selected>
                                </valueItem>
                            </xsl:otherwise>
                        </xsl:choose>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="autoSubEventTypeField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="autoSubEventTypes"/>
        <xsl:param name="autoSubEventType"/>
        <xsl:param name="isView" select="false()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="validators" select="$validators"/>
            <xsl:with-param name="changed" select="$changed"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <xsl:choose>
                            <xsl:when test="not($isView)">
                                <xsl:for-each select="$autoSubEventTypes/*">
                                    <xsl:variable name="nameEvent" select="./@name"/>
                                    <valueItem>
                                        <value><xsl:value-of select="$nameEvent"/></value>
                                        <title><xsl:value-of select="./text()"/></title>
                                        <selected><xsl:value-of select="string(string-length($autoSubEventType) > 0 and $nameEvent = $autoSubEventType)"/></selected>
                                    </valueItem>
                                </xsl:for-each>
                            </xsl:when>
                            <xsl:otherwise>
                                <valueItem>
                                    <value><xsl:value-of select="$autoSubEventType"/></value>
                                    <title><xsl:value-of select="$autoSubEventTypes/field[@name=$autoSubEventType]/text()"/></title>
                                    <selected>true</selected>
                                </valueItem>
                            </xsl:otherwise>
                        </xsl:choose>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>

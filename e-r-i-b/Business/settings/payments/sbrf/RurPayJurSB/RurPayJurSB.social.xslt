<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:xalan = "http://xml.apache.org/xalan"
                exclude-result-prefixes="mask au xalan">
    <!--порядок импорта важен-->
    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="isByTemplate"/> <!--платеж создается по шаблону-->
    <xsl:param name="isTemplate" select="'isTemplate'"/>
    <xsl:param name="groupRiskRank" select="''"/> <!--степень риска ПУ-->
    <xsl:param name="isMobileLimitedScheme" select="true()"/> <!--доавторизационная зона?-->
    <xsl:param name="isMobileLightScheme" select="false()"/> <!--Light-схема приложения?-->
    <xsl:param name="additionInfo" select="''"/> <!-- доп информация для формы просмотра -->

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
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list"/>
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
                <xsl:variable name="linkId" select="fromResource"/>
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:variable name="accountNode" select="$activeAccounts/entity[$linkId=./field[@name='code']/text() or $fromAccountSelect/text() = ./@key]"/>
                <xsl:variable name="cardNode" select="$activeCards/entity[$linkId=./field[@name='code'] or $fromAccountSelect/text() = ./@key]"/>
                <xsl:variable name="fromResourceCode">
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
                            <xsl:value-of select="au:getFormattedAccountNumber($accountNode/@key)"/> [<xsl:value-of select="$accountNode/field[@name='name']"/>]
                        </xsl:when>
                        <xsl:when test="$cardNode">
                            <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/> [<xsl:value-of select="$cardNode/field[@name='name']"/>]
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
                        <xsl:with-param name="curCode" select="fromResourceCurrency"/>
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

                            <xsl:variable name="isDictField" select="not($isByTemplate) and $isOurProvider and (./BusinessSubType = 'phone' or ./BusinessSubType = 'wallet')"/>
                            <xsl:variable name="editable" select="./IsEditable='true' and ((not($isByTemplate) and not($isMobileLightScheme and $isDictField and $groupRiskRank!='LOW')) or ./IsMainSum = 'true'
                                    or ($isOurProvider and not($isMobileLimitedScheme) and ($groupRiskRank='LOW' or (./SaveInTemplate='false' and ./IsKey='false')))
                                    or (not($isOurProvider) and ./Type='money'))"/>

                            <xsl:if test="$isDictField">
                               <field>
                                   <xsl:call-template name="dictField">
                                       <xsl:with-param name="name" select="'dictFieldId'"/>
                                       <xsl:with-param name="value" select="$formData/*[name()='dictFieldId']"/>
                                   </xsl:call-template>
                               </field>
                            </xsl:if>

                            <!--Для рискованных полей приходит признак "IsRiskRequisite"-->
                            <xsl:variable name="requisiteTypes" select="./RequisiteTypes"/>
                            <xsl:variable name="isRiskField" select="string-length($requisiteTypes) > 0 and contains($requisiteTypes, 'IsRiskRequisite')"/>

                            <xsl:choose>
                                <xsl:when test="./Type = 'date'">
                                    <field>
                                        <xsl:call-template name="dateBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'calendar'">
                                    <field>
                                        <xsl:call-template name="calendarBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'string'">
                                    <field>
                                        <xsl:call-template name="stringBillingField">
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
                                            <xsl:with-param name="fieldDictType">
                                                <xsl:if test="$isDictField">
                                                    <xsl:value-of select="./BusinessSubType"/>
                                                </xsl:if>
                                            </xsl:with-param>
                                            <xsl:with-param name="fieldInfoType">
                                                <xsl:if test="$isDictField">
                                                    <xsl:value-of select="./BusinessSubType"/>
                                                </xsl:if>
                                            </xsl:with-param>
                                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'integer'">
                                    <field>
                                        <xsl:call-template name="integerBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="fieldDictType">
                                                <xsl:if test="$isDictField">
                                                    <xsl:value-of select="./BusinessSubType"/>
                                                </xsl:if>
                                            </xsl:with-param>
                                            <xsl:with-param name="fieldInfoType">
                                                <xsl:if test="$isDictField">
                                                    <xsl:value-of select="./BusinessSubType"/>
                                                </xsl:if>
                                            </xsl:with-param>
                                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'number'">
                                    <field>
                                        <xsl:call-template name="numberBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="fieldDictType">
                                                <xsl:if test="$isDictField">
                                                    <xsl:value-of select="./BusinessSubType"/>
                                                </xsl:if>
                                            </xsl:with-param>
                                            <xsl:with-param name="fieldInfoType">
                                                <xsl:if test="$isDictField">
                                                    <xsl:value-of select="./BusinessSubType"/>
                                                </xsl:if>
                                            </xsl:with-param>
                                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'money'">
                                    <field>
                                        <xsl:call-template name="moneyBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'list'">
                                    <field>
                                        <xsl:call-template name="listBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="listValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'set'">
                                    <field>
                                        <xsl:call-template name="setBillingField">
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
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'choice'">
                                    <field>
                                        <xsl:call-template name="agreementField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="'Я согласен с правилами покупки билетов'"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="extendedDescId" select="./ExtendedDescriptionId"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'graphicset'">
                                    <xsl:variable name="parameters">
                                        <params>
                                            <xsl:for-each select="./Menu/MenuItem">
                                                <param key="{./Id}"><xsl:value-of select="./Value"/></param>
                                            </xsl:for-each>
                                        </params>
                                    </xsl:variable>
                                    <field>
                                        <xsl:call-template name="placesField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="params" select="xalan:nodeset($parameters)"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'email'">
                                    <field>
                                        <xsl:call-template name="stringBillingField">
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
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                    </externalFields>
                </paymentDetails>
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

            <RurPayJurSBDocument>
                <xsl:call-template name="viewPayment"/>
            </RurPayJurSBDocument>
        </document>
    </xsl:template>

    <xsl:template name="viewPayment">
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list"/>
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
        <xsl:variable name="fromResource" select="fromResource"/>
        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
        <xsl:variable name="accountNode" select="$activeAccounts/entity[$fromResource=./field[@name='code']/text() or $fromAccountSelect/text() = ./@key]"/>
        <xsl:variable name="cardNode" select="$activeCards/entity[$fromResource=./field[@name='code'] or $fromAccountSelect/text() = ./@key]"/>
        <xsl:variable name="fromResourceCode">
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
                    <xsl:value-of select="au:getFormattedAccountNumber($accountNode/@key)"/> [<xsl:value-of select="$accountNode/field[@name='name']"/>]
                </xsl:when>
                <xsl:when test="$cardNode">
                    <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/> [<xsl:value-of select="$cardNode/field[@name='name']"/>]
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
                <xsl:with-param name="curCode" select="fromResourceCurrency"/>
            </xsl:call-template>
        </fromResource>

        <!-- 4. Детали платежа -->
        <paymentDetails>

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
                                <xsl:call-template name="dateBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'calendar'">
                            <field>
                                <xsl:call-template name="calendarBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="changed" select="$changed"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'string'">
                            <field>
                                <xsl:call-template name="stringBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'link'">
                            <field>
                                <xsl:call-template name="linkBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'integer'">
                            <field>
                                <xsl:call-template name="integerBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'number'">
                            <field>
                                <xsl:call-template name="numberBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'money'">
                            <field>
                                <xsl:call-template name="moneyBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'list'">
                            <field>
                                <xsl:call-template name="listBillingField">
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
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'set'">
                            <field>
                                <xsl:call-template name="setBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'choice'">
                            <field>
                                <xsl:call-template name="agreementField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="'Я согласен с правилами покупки билетов'"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="extendedDescId" select="./ExtendedDescriptionId"/>
                                    <xsl:with-param name="value" select="$currentValue"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'email'">
                            <field>
                                <xsl:call-template name="stringBillingField">
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
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
            </externalFields>

            <!-- (4.2) Комиссия -->
            <xsl:if test="not($longOffer or $isTemplate = 'true')">
                <xsl:call-template name="commission">
                    <xsl:with-param name="commissionAmount"><xsl:value-of select="commission"/></xsl:with-param>
                    <xsl:with-param name="commissionCurrency"><xsl:value-of select="commissionCurrency"/></xsl:with-param>
                </xsl:call-template>
            </xsl:if>

        </paymentDetails>
        <!-- если не создание автоподписки и есть что показывать -->
        <xsl:if test="not($longOffer) and string-length($additionInfo) > 0">
            <additionData>
                <xsl:text disable-output-escaping="yes">&lt;![CDATA[</xsl:text>
                    <xsl:value-of select="$additionInfo" disable-output-escaping="yes"/>
                <xsl:text disable-output-escaping="yes">]]&gt;</xsl:text>
            </additionData>
        </xsl:if>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-auto-sub-info">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="accessAutoSubTypes" select="document('supportedAutoPaysApi.xml')/entity-list"/>
        <document>
            <form>CreateAutoSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
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
                                        <xsl:value-of select="receiverName"/>
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
                                <xsl:with-param name="isSum" select="false()"/>
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
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="isSum" select="false()"/>
                                <xsl:with-param name="value" select="invoiceMaxAmount"/>
                            </xsl:call-template>
                        </amount>
                    </invoice>

                    <xsl:choose>
                        <xsl:when test="string-length(commission)>0">
                            <commission>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">commission</xsl:with-param>
                                    <xsl:with-param name="title">Сумма комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="false()"/>
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
                            <isWithCommission>
                                <xsl:call-template name="booleanField">
                                    <xsl:with-param name="name">isWithCommission</xsl:with-param>
                                    <xsl:with-param name="title">Признак взимания комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="false()"/>
                                    <xsl:with-param name="value" select="isWithCommission"/>
                                </xsl:call-template>
                            </isWithCommission>
                            <xsl:if test="isWithCommission = 'false'">
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
                            </xsl:if>
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
                                    <xsl:with-param name="isSum" select="false()"/>
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
                                    <xsl:with-param name="isSum" select="false()"/>
                                    <xsl:with-param name="value" select="invoiceMaxAmount"/>
                                </xsl:call-template>
                            </amount>
                        </invoice>
                    </xsl:if>

                    <xsl:choose>
                        <xsl:when test="string-length(commission)>0">
                            <commission>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">commission</xsl:with-param>
                                    <xsl:with-param name="title">Сумма комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="isSum" select="false()"/>
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
                            <isWithCommission>
                                <xsl:call-template name="booleanField">
                                    <xsl:with-param name="name">isWithCommission</xsl:with-param>
                                    <xsl:with-param name="title">Признак взимания комиссии</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="false()"/>
                                    <xsl:with-param name="value" select="isWithCommission"/>
                                </xsl:call-template>
                            </isWithCommission>
                            <xsl:if test="isWithCommission = 'false'">
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
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>

                </autoSubParameters>
            </CreateAutoSubscriptionDocument>
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
        <xsl:param name="value"/>
        <xsl:param name="displayedValue"/>
        <xsl:param name="curCode" select="''"/>

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
                    <xsl:if test="string-length($value)>0">
                        <availableValues>
                            <valueItem>
                                <value><xsl:value-of select="$value"/></value>
                                <selected>true</selected>
                                <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                <xsl:if test="string-length($curCode)>0">
                                    <currency><xsl:value-of select="$curCode"/></currency>
                                </xsl:if>
                            </valueItem>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
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
        <changed>false</changed>
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
        <changed>false</changed>
    </xsl:template>

    <xsl:template name="agreementField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="extendedDescId"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'agreement'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <agreementType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                    <xsl:if test="string-length($extendedDescId)>0">
                        <agreementId><xsl:value-of select="$extendedDescId"/></agreementId>
                    </xsl:if>
                </agreementType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="placesField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="params"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'places'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <placesType>
                    <maxCount><xsl:value-of select="$params/params/param[@key = 'maxCount']/text()"/></maxCount>
                    <places>
                        <xsl:for-each select="$params/params/param[@key = 'place']">
                            <xsl:sort select="substring-before(./text(), ';')" data-type="number"/>
                            <place>
                                <number><xsl:value-of select="substring-before(./text(), ';')"/></number>
                                <state><xsl:value-of select="substring-after(./text(), ';')"/></state>
                            </place>
                        </xsl:for-each>
                    </places>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </placesType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="dictField">
            <xsl:param name="name"/>
            <xsl:param name="value"/>

            <xsl:call-template name="simpleField">
                <xsl:with-param name="name" select="$name"/>
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

</xsl:stylesheet>
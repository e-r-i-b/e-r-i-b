<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:xalan = "http://xml.apache.org/xalan"
                exclude-result-prefixes="au mu xalan">
    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="mainSumKey" select="document('extendedFields.xml')/Attributes/Attribute[./IsMainSum='true']/NameBS/text()"/>
    <xsl:variable name="mainSumValue" select="$formData/*[name()=$mainSumKey]"/>
    <xsl:param name="documentStatus" select="''"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="isMobileLightScheme" select="false()"/> <!--Light-схема приложения?-->
    <xsl:param name="isMobileLimitedScheme" select="true()"/> <!--доавторизационная зона?-->
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>InvoicePayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>
            <InvoicePaymentDocument>
                <!--Наименование услуги в биллинговой системе-->
                <nameService>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">nameService</xsl:with-param>
                        <xsl:with-param name="title">Наименование услуги</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="nameService"/>
                    </xsl:call-template>
                </nameService>
                <!--Счет выставлен по услуге-->
                <subscriptionName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">subscriptionName</xsl:with-param>
                        <xsl:with-param name="title">Наименование услуги</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="subscriptionName"/>
                    </xsl:call-template>
                </subscriptionName>
                <!--Получатель-->
                <reseiver>
                    <!--Наименование получателя-->
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverName</xsl:with-param>
                            <xsl:with-param name="title">Наименование получателя</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverName"/>
                        </xsl:call-template>
                    </name>
                    <!--ИНН получателя-->
                    <inn>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverINN</xsl:with-param>
                            <xsl:with-param name="title">ИНН</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverINN"/>
                        </xsl:call-template>
                    </inn>
                    <!--Счет зачисления-->
                    <account>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverAccount</xsl:with-param>
                            <xsl:with-param name="title">Счет</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverAccount"/>
                        </xsl:call-template>
                    </account>

                    <!--Банк получателя-->
                    <bank>
                        <!--Наименование-->
                        <xsl:if test="string-length(receiverBankName)>0">
                            <name>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverBankName</xsl:with-param>
                                    <xsl:with-param name="title">Наименование</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverBankName"/>
                                </xsl:call-template>
                            </name>
                        </xsl:if>
                        <!--БИК-->
                        <bic>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                <xsl:with-param name="title">БИК</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverBIC"/>
                            </xsl:call-template>
                        </bic>

                        <xsl:if test="string-length(receiverCorAccount)>0">
                            <corAccount>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                    <xsl:with-param name="title">Корсчет</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverCorAccount"/>
                                </xsl:call-template>
                            </corAccount>
                        </xsl:if>
                    </bank>
                </reseiver>

                <!--Детали платежа-->
                <requisites>
                    <extendedFields>
                        <xsl:for-each select="$extendedFields">
                            <xsl:variable name="name" select="./NameVisible"/>
                            <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
                            <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                            <xsl:variable name="type" select="./Type"/>
                            <xsl:variable name="id" select="./NameBS"/>
                            <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
                            <xsl:variable name="editable" select="./IsEditable='true' and (not($isMobileLightScheme) or (not($isMobileLimitedScheme) and (./IsKey='false')))"/>
                            <!--Для рискованных полей приходит признак "IsRiskRequisite"-->
                            <xsl:variable name="requisiteTypes" select="./RequisiteTypes"/>
                            <xsl:variable name="isRiskField" select="string-length($requisiteTypes) > 0 and contains($requisiteTypes, 'IsRiskRequisite')"/>

                            <xsl:variable name="val">
                                <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                                <xsl:variable name="sum" select="./IsSum"/>
                                <xsl:if test="string-length($currentValue)>0 ">
                                    <xsl:variable name="formattedValue">
                                        <xsl:choose>
                                            <xsl:when test="$type='list'">
                                                <xsl:for-each select="./Menu/MenuItem">
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
                                                    <xsl:if test="$code = $currentValue">
                                                        <xsl:value-of select="./Value"/>
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </xsl:when>

                                            <xsl:when test="$type='set'">
                                                <xsl:choose>
                                                    <xsl:when test="contains($currentValue, '@')">
                                                        <xsl:for-each select="xalan:tokenize($currentValue, '@')">
                                                            <xsl:value-of select="current()"/>
                                                            <br/>
                                                        </xsl:for-each>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="$currentValue"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="$currentValue"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>
                                    <xsl:choose>
                                        <xsl:when test="$formData/*[name()=$id]/@changed='true'">
                                            <b><xsl:copy-of select="$formattedValue"/></b>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:copy-of select="$formattedValue"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:if test="$sum='true'">&nbsp;руб.</xsl:if>
                                </xsl:if>
                            </xsl:variable>

                            <xsl:if test="not($mainSum) and not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
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
                            </xsl:if>
                        </xsl:for-each>
                    </extendedFields>
                    <!--Счет списания-->
                    <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                    <xsl:variable name="fromAccount">
                        <xsl:if test="not($fromAccountSelect= '')">
                            <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                            <xsl:value-of select="fromResourceRest"/>&nbsp;
                            <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                        </xsl:if>
                    </xsl:variable>
                    <xsl:if test="string-length($fromAccount)>0">
                        <fromResource>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">fromAccount</xsl:with-param>
                                <xsl:with-param name="title">Счет списания</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="fromAccount"/>
                            </xsl:call-template>
                        </fromResource>
                    </xsl:if>
                    <!--Сумма платежа-->
                    <amount>
                        <xsl:variable name="amount">
                            <xsl:value-of select="format-number($mainSumValue, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                        </xsl:variable>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">amount</xsl:with-param>
                            <xsl:with-param name="title">Сумма платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$amount"/>
                        </xsl:call-template>
                    </amount>
                    <!-- Статус -->
                    <state>
                        <xsl:call-template name="stringField">
                           <xsl:with-param name="name">state</xsl:with-param>
                           <xsl:with-param name="title">Статус платежа</xsl:with-param>
                           <xsl:with-param name="required" select="true()"/>
                           <xsl:with-param name="editable" select="false()"/>
                           <xsl:with-param name="visible" select="true()"/>
                           <xsl:with-param name="value" select="state"/>
                       </xsl:call-template>
                    </state>
                </requisites>
            </InvoicePaymentDocument>
        </document>
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
</xsl:stylesheet>
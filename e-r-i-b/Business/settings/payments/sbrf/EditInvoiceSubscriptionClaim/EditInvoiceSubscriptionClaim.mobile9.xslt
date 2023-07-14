<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                exclude-result-prefixes="xalan mask au dh">
    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'view'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="longOffer" select="true()"/>
    <xsl:param name="personAvailable" select="true()"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list/*"/>
    <xsl:variable name="accountingEntities" select="document('accounting-entities-list.xml')/entity-list/*"/>
    <xsl:variable name="formData" select="/form-data"/>
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
            <form>EditInvoiceSubscriptionClaim</form>
            <EditInvoiceSubscriptionClaim>
                <!--Наименование услуги в биллинговой системе-->
                <nameService>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">nameService</xsl:with-param>
                        <xsl:with-param name="title">Услуга</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="nameService"/>
                    </xsl:call-template>
                </nameService>

                <!--Название выставляемого счета-->
                <invoiceAccountName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">invoiceAccountName</xsl:with-param>
                        <xsl:with-param name="title">Название выставляемого счета</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="string-length(invoiceAccountName) > 0">
                                    <xsl:value-of select="invoiceAccountName"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    Другие услуги
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </invoiceAccountName>

                <!--Данные получателя-->
                <receiver>
                    <!--Наименование получателя-->
                    <xsl:if test="string-length(receiverName)>0">
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
                    </xsl:if>

                    <!--ИНН-->
                    <xsl:if test="string-length(receiverINN)>0">
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
                    </xsl:if>

                    <!--Счет зачисления-->
                    <xsl:if test="string-length(receiverAccount)>0">
                        <account>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverAccount"/>
                            </xsl:call-template>
                        </account>
                    </xsl:if>

                    <!--Банк получателя-->
                    <recipientBank>
                        <!--Наименование банка Получателя-->
                        <xsl:if test="string-length(receiverBankName)>0">
                            <name>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverBankName</xsl:with-param>
                                    <xsl:with-param name="title">Наименование банка Получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverBankName"/>
                                </xsl:call-template>
                            </name>
                        </xsl:if>

                        <!--БИК-->
                        <xsl:if test="string-length(receiverBIC)>0">
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
                        </xsl:if>

                        <!--Кор.Счет-->
                        <xsl:if test="string-length(receiverCorAccount)>0">
                            <corAccount>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                    <xsl:with-param name="title">Кор.Счет</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverCorAccount"/>
                                </xsl:call-template>
                            </corAccount>
                        </xsl:if>

                    </recipientBank>

                </receiver>

                <!--Реквизиты платежа-->
                <requisites>
                    <xsl:for-each select="$extendedFields">
                            <xsl:variable name="name" select="./NameVisible"/>
                            <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
                            <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                            <xsl:variable name="type" select="./Type"/>
                            <xsl:variable name="id" select="./NameBS"/>
                            <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
                            <xsl:variable name="editable" select="false()"/>
                            <xsl:variable name="required" select="false()"/>
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
                                            <xsl:copy-of select="$formattedValue"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:copy-of select="$formattedValue"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                </requisites>

                <!--Наименование подписки-->
                <subscriptionName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">subscriptionName</xsl:with-param>
                        <xsl:with-param name="title">Наименование подписки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="subscriptionName"/>
                    </xsl:call-template>
                </subscriptionName>

                <!--Карта списания-->
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Карта списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="link" select="fromResource"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                    </xsl:call-template>
                </fromResource>

                <!--Объект учета-->
                <accountingEntity>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name">accountingEntityId</xsl:with-param>
                        <xsl:with-param name="title">Объект учета</xsl:with-param>
                        <xsl:with-param name="type" select="'list'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="valueTag">
                            <listType>
                                <availableValues>
                                    <valueItem>
                                        <value>0</value>
                                        <title>Другие услуги</title>
                                        <selected>
                                            <xsl:value-of select="string(string-length($accountingEntities) = 0 or string-length(accountingEntityId) = 0)"/>
                                        </selected>
                                    </valueItem>
                                    <xsl:variable name="accountingEntityId" select="accountingEntityId"/>
                                    <xsl:for-each select="$accountingEntities">
                                        <xsl:variable name="id" select="field[@name='id']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$id"/></value>
                                            <title><xsl:value-of select="./field[@name='name']"/></title>
                                            <selected>
                                                <xsl:value-of select="string($accountingEntityId = $id)"/>
                                            </selected>
                                        </valueItem>
                                    </xsl:for-each>
                                </availableValues>
                            </listType>
                        </xsl:with-param>
                    </xsl:call-template>
                </accountingEntity>

                <!--Период выполнения платежа-->
                <xsl:variable name="eventType">
                    <xsl:choose>
                        <xsl:when test="string-length(eventType) > 0">
                            <xsl:value-of select="eventType"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'ONCE_IN_MONTH'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <eventType>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name" select="'eventType'"/>
                        <xsl:with-param name="title">Период выполнения платежа</xsl:with-param>
                        <xsl:with-param name="type" select="'list'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="valueTag">
                            <listType>
                                <availableValues>
                                    <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
                                    <xsl:for-each select="$eventTypes">
                                        <xsl:if test="./@key = 'ONCE_IN_WEEK' or ./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER'">
                                            <valueItem>
                                                <value><xsl:value-of select="./@key"/></value>
                                                <title><xsl:value-of select="./text()"/></title>
                                                <selected>
                                                    <xsl:value-of select="string($eventType = ./@key)"/>
                                                </selected>
                                            </valueItem>
                                        </xsl:if>
                                    </xsl:for-each>
                                </availableValues>
                            </listType>
                        </xsl:with-param>
                    </xsl:call-template>
                </eventType>

                <!--Дата платежа-->
                <xsl:variable name="dayPay">
                    <xsl:choose>
                        <xsl:when test="string-length(dayPay) > 0">
                            <xsl:value-of select="dh:convertIfXmlDateFormat(dayPay)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="dh:formatDateToStringWithPoint(dh:getNearDateByMonthWithoutCurrent(10))"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <dayPay>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">dayPay</xsl:with-param>
                        <xsl:with-param name="title">Дата платежа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$dayPay"/>
                    </xsl:call-template>
                </dayPay>

            </EditInvoiceSubscriptionClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>EditInvoiceSubscriptionClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <EditInvoiceSubscriptionClaim>
                <!--Наименование услуги в биллинговой системе-->
                <nameService>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">nameService</xsl:with-param>
                        <xsl:with-param name="title">Услуга</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="nameService"/>
                    </xsl:call-template>
                </nameService>

                <!--Название выставляемого счета-->
                <invoiceAccountName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">invoiceAccountName</xsl:with-param>
                        <xsl:with-param name="title">Название выставляемого счета</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:choose>
                                <xsl:when test="string-length(invoiceAccountName) > 0">
                                    <xsl:value-of select="invoiceAccountName"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    Другие услуги
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </invoiceAccountName>

                <!--Данные получателя-->
                <receiver>
                    <!--Наименование получателя-->
                    <xsl:if test="string-length(receiverName)>0">
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
                    </xsl:if>

                    <!--ИНН-->
                    <xsl:if test="string-length(receiverINN)>0">
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
                    </xsl:if>

                    <!--Счет зачисления-->
                    <xsl:if test="string-length(receiverAccount)>0">
                        <account>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">Счет зачисления</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverAccount"/>
                            </xsl:call-template>
                        </account>
                    </xsl:if>

                    <!--Банк получателя-->
                    <recipientBank>
                        <!--Наименование банка Получателя-->
                        <xsl:if test="string-length(receiverBankName)>0">
                            <name>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverBankName</xsl:with-param>
                                    <xsl:with-param name="title">Наименование банка Получателя</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverBankName"/>
                                </xsl:call-template>
                            </name>
                        </xsl:if>

                        <!--БИК-->
                        <xsl:if test="string-length(receiverBIC)>0">
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
                        </xsl:if>

                        <!--Кор.Счет-->
                        <xsl:if test="string-length(receiverCorAccount)>0">
                            <corAccount>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                    <xsl:with-param name="title">Кор.Счет</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverCorAccount"/>
                                </xsl:call-template>
                            </corAccount>
                        </xsl:if>

                    </recipientBank>

                </receiver>

                <!--Реквизиты платежа-->
                <requisites>
                    <xsl:for-each select="$extendedFields">
                            <xsl:variable name="name" select="./NameVisible"/>
                            <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
                            <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
                            <xsl:variable name="type" select="./Type"/>
                            <xsl:variable name="id" select="./NameBS"/>
                            <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
                            <xsl:variable name="editable" select="false()"/>
                            <xsl:variable name="required" select="false()"/>
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
                                            <xsl:copy-of select="$formattedValue"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:copy-of select="$formattedValue"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
                                                <xsl:with-param name="editable" select="true()"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                                                <xsl:with-param name="required" select="$required"/>
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
                </requisites>

                <!--Наименование подписки-->
                <subscriptionName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">subscriptionName</xsl:with-param>
                        <xsl:with-param name="title">Наименование подписки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="subscriptionName"/>
                    </xsl:call-template>
                </subscriptionName>

                <!--Карта списания-->
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Карта списания</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="cards" select="$activeCards"/>
                        <xsl:with-param name="link" select="fromResource"/>
                        <xsl:with-param name="linkNumber" select="fromAccountSelect"/>
                    </xsl:call-template>
                </fromResource>

                <!--Объект учета-->
                <accountingEntity>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name">accountingEntityId</xsl:with-param>
                        <xsl:with-param name="title">Объект учета</xsl:with-param>
                        <xsl:with-param name="type" select="'list'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="valueTag">
                            <listType>
                                <availableValues>
                                    <valueItem>
                                        <value>0</value>
                                        <title>Другие услуги</title>
                                        <selected>
                                            <xsl:value-of select="string(string-length($accountingEntities) = 0 or string-length(accountingEntityId) = 0)"/>
                                        </selected>
                                    </valueItem>
                                    <xsl:for-each select="$accountingEntities">
                                        <xsl:variable name="id" select="field[@name='id']/text()"/>
                                        <valueItem>
                                            <value><xsl:value-of select="$id"/></value>
                                            <title><xsl:value-of select="./field[@name='name']"/></title>
                                            <selected>
                                                <xsl:value-of select="string(accountingEntityId = $id)"/>
                                            </selected>
                                        </valueItem>
                                    </xsl:for-each>
                                </availableValues>
                            </listType>
                        </xsl:with-param>
                    </xsl:call-template>
                </accountingEntity>

                <!--Тип платежа-->
                <xsl:variable name="eventType">
                    <xsl:choose>
                        <xsl:when test="string-length(eventType) > 0">
                            <xsl:value-of select="eventType"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'ONCE_IN_MONTH'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <eventType>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name">eventType</xsl:with-param>
                        <xsl:with-param name="title">Тип платежа</xsl:with-param>
                        <xsl:with-param name="type" select="'list'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="valueTag">
                            <listType>
                                <availableValues>
                                    <xsl:variable name="eventTypes" select="document('long-offer-events-types.xml')/entity-list/entity"/>
                                    <xsl:for-each select="$eventTypes">
                                        <xsl:if test="./@key = 'ONCE_IN_WEEK' or ./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER'">
                                            <valueItem>
                                                <value><xsl:value-of select="./@key"/></value>
                                                <title><xsl:value-of select="./text()"/></title>
                                                <selected>
                                                    <xsl:value-of select="string($eventType = ./@key)"/>
                                                </selected>
                                            </valueItem>
                                        </xsl:if>
                                    </xsl:for-each>
                                </availableValues>
                            </listType>
                        </xsl:with-param>
                    </xsl:call-template>
                </eventType>

                <!--День платежа-->
                <xsl:variable name="dayPay">
                    <xsl:choose>
                        <xsl:when test="string-length(dayPay) > 0">
                            <xsl:value-of select="dh:convertIfXmlDateFormat(dayPay)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="dh:formatDateToStringWithPoint(dh:getNearDateByMonthWithoutCurrent(10))"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <dayPay>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">dayPay</xsl:with-param>
                        <xsl:with-param name="title">День платежа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$dayPay"/>
                    </xsl:call-template>
                </dayPay>

            </EditInvoiceSubscriptionClaim>
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

        <xsl:variable name="isValidLinkCode" select="string-length($link)>0 and (starts-with($link, 'card:'))"/>

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
                                            <currency><xsl:value-of select="./field[@name='currencyCode']"/></currency>
                                        </valueItem>
                                    </xsl:for-each>
                                </xsl:if>

                            </xsl:when>
                            <xsl:otherwise>

                                <!--Просмотр. Доступен только номер карты/счета, поэтому код и displayedValue нужно вычислять -->
                                <xsl:variable name="cardNode" select="$cards[@key = $linkNumber]"/>

                                <xsl:variable name="code">
                                    <xsl:if test="$cardNode">
                                        <xsl:value-of select="$cardNode/field[@name='code']/text()"/>
                                    </xsl:if>
                                </xsl:variable>

                                <xsl:variable name="displayedValue">
                                    <xsl:if test="$cardNode">
                                        <xsl:value-of select="mask:getCutCardNumber($cardNode/@key)"/> [<xsl:value-of select="$cardNode/field[@name='name']"/>]
                                    </xsl:if>
                                </xsl:variable>

                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <selected>true</selected>
                                    <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                    <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                        <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                    </xsl:if>
                                </valueItem>

                            </xsl:otherwise>
                        </xsl:choose>

                    </availableValues>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="availableValue">
        <xsl:param name="resource"/>
        <xsl:param name="linkId"/>
        <xsl:param name="type"/>
        <xsl:param name="name"/>
        <xsl:if test="count($resource)>0">
            <xsl:for-each select="$resource">
                <valueItem>
                    <xsl:variable name="id" select="field[@name='code']/text()"/>
                    <value><xsl:value-of select="$id"/></value>
                    <selected><xsl:value-of select="string($linkId=$id)"/></selected>
                    <displayedValue>
                        <xsl:choose>
                            <xsl:when test="$type = 'card'">
                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                            </xsl:otherwise>
                        </xsl:choose>
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
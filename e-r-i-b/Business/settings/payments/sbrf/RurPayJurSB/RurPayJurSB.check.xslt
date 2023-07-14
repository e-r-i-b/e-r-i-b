<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:pu="java://com.rssl.phizic.business.persons.PersonHelper"
                              xmlns:xalan = "http://xml.apache.org/xalan"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                              xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                              xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                              extension-element-prefixes="pu xalan mask mpnu mu ai">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:key name="subService" match="Attribute[./Type != 'calendar']" use="GroupName"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="changedFields"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printDocumentCheck'">
				<xsl:apply-templates mode="printDocumentCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printDocumentCheck">
        <xsl:variable name="application"><xsl:value-of select="ai:getCurrentApplication()"/></xsl:variable>
        <DocumentCheck>
            <form>RurPayJurSB</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>безналичная  оплата услуг</title>
            <xsl:if test="string-length(operationDate)>0">
                <operationDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">operationDate</xsl:with-param>
                        <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="operationDate"/>
                    </xsl:call-template>
                </operationDate>
            </xsl:if>
            <xsl:if test="string-length(operationTime)>0">
                <operationTime>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">operationTime</xsl:with-param>
                        <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="operationTime"/>
                    </xsl:call-template>
                </operationTime>
            </xsl:if>
            <xsl:if test="string-length(documentNumber)>0">
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">идентификатор операции</xsl:with-param>
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

            <RurPayJurSBDocumentCheck>

                <xsl:variable name="fromResourceType" select="fromResourceType"/>
                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                <xsl:variable name="fromResourceCode">
                    <xsl:choose>
                        <xsl:when test="contains($fromResourceType, 'Account')">
                           <xsl:value-of  select="fromAccountType"/>
                        </xsl:when>
                        <xsl:otherwise>
                            № карты
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="contains($fromResourceType, 'Account')">
                            <xsl:value-of  select="$fromAccountSelect"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <fromResource>
                    <xsl:call-template name="resourceCheckField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Отправитель</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="fromResourceLink"/>
                        <xsl:with-param name="displayedValue" select="concat($fromResourceCode, ': ', $displayedValue)"/>
                    </xsl:call-template>
                </fromResource>

                <amount>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Сумма операции</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value">
                            <xsl:variable name="id" select="$extendedFields[./IsMainSum = 'true']/NameBS"/>
                            <xsl:value-of select="concat($formData/*[name()=$id], ' ', mu:getCurrencySign(destinationCurrency))"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </amount>

                <xsl:if test="string-length(commission)>0">
                    <xsl:variable name="commissionString" select="concat(commission, ' ', mu:getCurrencySign(commissionCurrency))"/>
                    <comission>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">comission</xsl:with-param>
                            <xsl:with-param name="title">Комиссия</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="$commissionString"/>
                        </xsl:call-template>
                    </comission>
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

                <keyFields>                     <!--Реквизиты плательщика (ФИО + ключевые поля)-->
                    <field>
                         <xsl:call-template name="stringField">
                                <xsl:with-param name="name">FIO</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="changed" select="false()"/>
                                <xsl:with-param name="value" select="pu:getFormattedPersonName()"/>
                         </xsl:call-template>
                    </field>
                     <xsl:call-template name="showExtendedFieldByKeyProperty">
                            <xsl:with-param name="fields" select="$extendedFields"/>
                            <xsl:with-param name="onlyKey">true</xsl:with-param>
                            <xsl:with-param name="currency" select="destinationCurrency"/>
                     </xsl:call-template>
                </keyFields>

                <receiverFields>                <!--Реквизиты платежа-->
                    <field>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">nameService</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="nameService"/>
                        </xsl:call-template>
                    </field>
                    <xsl:call-template name="showExtendedFieldByKeyProperty">
                        <xsl:with-param name="fields" select="$extendedFields"/>
                        <xsl:with-param name="onlyKey">false</xsl:with-param>
                        <xsl:with-param name="currency" select="destinationCurrency"/>
                    </xsl:call-template>
                </receiverFields>

                <xsl:if test="$extendedFields/Type = 'calendar'">
                    <period>
                        <xsl:call-template name="calendarField">
                            <xsl:with-param name="name">period</xsl:with-param>
                            <xsl:with-param name="title">Период оплаты</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="period"/>
                        </xsl:call-template>
                    </period>
                </xsl:if>

                <xsl:if test="string-length(receiverName)>0">
                    <receiverName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverName</xsl:with-param>
                            <xsl:with-param name="title">Получатель платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value">
                                <xsl:choose>
                                    <xsl:when test="string-length(nameOnBill) > 0">
                                        <xsl:value-of  select="nameOnBill"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of  select="receiverName"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </receiverName>
                </xsl:if>

                <xsl:variable name="bankDetails" select="bankDetails"/>
                <xsl:if test="$bankDetails = 'true'">
                    <receiverBIC>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverBIC</xsl:with-param>
                            <xsl:with-param name="title">БИК</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="receiverBIC"/>
                        </xsl:call-template>
                    </receiverBIC>

                   <receiverINN>
                       <xsl:call-template name="stringField">
                           <xsl:with-param name="name">receiverINN</xsl:with-param>
                           <xsl:with-param name="title">ИНН</xsl:with-param>
                           <xsl:with-param name="required" select="true()"/>
                           <xsl:with-param name="editable" select="false()"/>
                           <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                           <xsl:with-param name="value" select="receiverINN"/>
                       </xsl:call-template>
                   </receiverINN>

                   <receiverAccount>
                       <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverAccount</xsl:with-param>
                            <xsl:with-param name="title">Счет</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="receiverAccount"/>
                       </xsl:call-template>
                   </receiverAccount>

                   <receiverCorAccount>
                       <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                            <xsl:with-param name="title">Корр. счет</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="receiverCorAccount"/>
                       </xsl:call-template>
                   </receiverCorAccount>
                </xsl:if>

                <receiverBankName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverBankName</xsl:with-param>
                        <xsl:with-param name="title">Наименование банка получателя</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverBankName"/>
                    </xsl:call-template>
                </receiverBankName>

            </RurPayJurSBDocumentCheck>

        </DocumentCheck>

    </xsl:template>

    <xsl:template name="showExtendedFieldByKeyProperty">
        <xsl:param name="fields"/>
        <xsl:param name="onlyKey"/>
        <xsl:param name="currency"/>
        <xsl:variable name="showOnlyKey" select="($onlyKey ='true')"/>
        <xsl:for-each select="$fields[./Type != 'calendar']">
            <xsl:choose>
                <xsl:when test="preceding-sibling::Attribute[./GroupName = current()/GroupName]"/>
                <xsl:otherwise>
                    <xsl:for-each select="self::node()|key('subService',current()/GroupName)">
                        <xsl:variable name="name" select="./NameVisible"/>
                        <xsl:variable name="isVisible" select="./IsVisible = 'true'"/>
                        <xsl:variable name="isForBill" select="./IsForBill='true'"/>
                        <xsl:variable name="type" select="./Type"/>
                        <xsl:variable name="id" select="./NameBS"/>
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:variable name="isKey" select="./IsKey = 'true'"/>
                        <xsl:variable name="description" select="./Description"/>
                        <xsl:variable name="comment" select="./Comment"/>
                        <xsl:variable name="isRequired" select="./IsRequired"/>
                        <xsl:variable name="isSum" select="./IsSum"/>
                        <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>

                        <xsl:if test="(($isForBill) or ($isKey)) and ($isKey=$showOnlyKey)">
                            <xsl:choose>
                                <xsl:when test="$isHideInConfirmation">
                                    <!-- пропускаем поле -->
                                </xsl:when>
                                <xsl:when test="./IsMainSum = 'true'"/> <!--пропускаем поле 002-->
                                <xsl:when test="$type = 'date'">
                                    <field>
                                        <xsl:call-template name="dateField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type = 'string'">
                                    <field>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="isSum" select="$isSum"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type = 'link'">
                                    <field>
                                        <xsl:call-template name="linkField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="minLength" select="./MinLength"/>
                                            <xsl:with-param name="maxLength" select="./MaxLength"/>
                                            <xsl:with-param name="isSum" select="$isSum"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type = 'integer'">
                                    <field>
                                        <xsl:call-template name="integerField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="isSum" select="$isSum"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type = 'number'">
                                    <field>
                                        <xsl:call-template name="numberField">
                                           <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="isSum" select="$isSum"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type='list'">
                                    <field>
                                        <xsl:call-template name="listField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="listValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="isSum" select="$isSum"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type='set'">
                                    <field>
                                        <xsl:call-template name="setField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="isSum" select="$isSum"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="availableValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="checkedValues" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="$type = 'money'">
                                    <field>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="value" select="$currentValue"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:otherwise>
                                    <field>
                                        <xsl:call-template name="stringField">
                                            <xsl:with-param name="name" select="$id"/>
                                            <xsl:with-param name="title" select="$name"/>
                                            <xsl:with-param name="description" select="$description"/>
                                            <xsl:with-param name="hint" select="$comment"/>
                                            <xsl:with-param name="required" select="$isRequired"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="changed" select="false()"/>
                                            <xsl:with-param name="value">
                                                <xsl:choose>
                                                    <xsl:when test="$isKey='true'">
                                                        <xsl:value-of select="mpnu:getCutPhoneIfOur($currentValue)"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="$currentValue"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:with-param>
                                        </xsl:call-template>
                                    </field>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="listField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="isSum" select="false()"/>
        <xsl:param name="listValues"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="changed" select="false()"/>
        <xsl:param name="value"/>

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
                                </valueItem>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
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
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>
<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    
    <xsl:variable name="autoPaymentTypes" select="document('auto-payment-types.xml')/entity-list"/>

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
            <form>EditAutoPaymentPayment</form>
            <EditAutoPaymentPayment>
                <xsl:variable name="cardNumber" select="cardNumber"/>
                <xsl:variable name="card" select="document('cards.xml')/entity-list/entity[@key=$cardNumber]"/>

                <recipient>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">recipient</xsl:with-param>
                        <xsl:with-param name="title">Идентифкатор поставщика услуг</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="recipient"/>
                    </xsl:call-template>
                </recipient>

                <receiverName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverName</xsl:with-param>
                        <xsl:with-param name="title">Наименование получателя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverName"/>
                    </xsl:call-template>
                </receiverName>

                <!--  Источник списания -->
                <xsl:variable name="fromResourceCode" select="$card/field[@name='code']/text()"/>
                <xsl:variable name="displayedValue">
                    <xsl:value-of select="mask:getCutCardNumber($card/@key)"/> [<xsl:value-of select="$card/field[@name='name']"/>]
                </xsl:variable>
                <!--  Ресурс оплаты -->
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Списать со счета</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$fromResourceCode"/>
                        <xsl:with-param name="displayedValue" select="$displayedValue"/>
                    </xsl:call-template>
                </fromResource>

                <requisiteName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">requisiteName</xsl:with-param>
                        <xsl:with-param name="title">Имя ключевого поля</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="requisiteName"/>
                    </xsl:call-template>
                </requisiteName>

                <requisite>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">requisite</xsl:with-param>
                        <xsl:with-param name="title">Значение ключевого поля</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="requisite"/>
                    </xsl:call-template>
                </requisite>

                <xsl:if test="executionEventType!='BY_INVOICE'">
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </amount>
                </xsl:if>

                <xsl:variable name="executionEventType" select="executionEventType"/>
                <autoPaymentParameters>
                    <executionEventType>
                        <xsl:call-template name="executionEventTypeField">
                            <xsl:with-param name="name">executionEventType</xsl:with-param>
                            <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$executionEventType"/>
                            <xsl:with-param name="listValues" select="$autoPaymentTypes/entity[./field[@name=$executionEventType]]/field"/>
                        </xsl:call-template>
                    </executionEventType>

                    <xsl:choose>
                        <xsl:when test="executionEventType='ONCE_IN_YEAR' or executionEventType='ONCE_IN_QUARTER' or executionEventType='ONCE_IN_MONTH'">
                            <periodic>
                                <autoPaymentStartDate>
                                    <xsl:call-template name="dateField">
                                        <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                        <xsl:with-param name="title">Дата начала действия</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="autoPaymentStartDate"/>
                                    </xsl:call-template>
                                </autoPaymentStartDate>
                                <firstPaymentDate>
                                    <xsl:call-template name="dateField">
                                        <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                        <xsl:with-param name="title">Ближайший платеж</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="false()"/>
                                        <xsl:with-param name="value">
                                            <xsl:if test="firstPaymentDate != '..'">
                                                <xsl:value-of select="firstPaymentDate"/>
                                            </xsl:if>
                                        </xsl:with-param>
                                    </xsl:call-template>
                                </firstPaymentDate>
                            </periodic>
                        </xsl:when>
                        <xsl:when test="executionEventType='REDUSE_OF_BALANCE'">
                            <xsl:variable name="serviceProviderEntity" select="document(concat('serviceProvider.xml?id=',recipient))/entity-list/entity"/>
                            <xsl:variable name="floorLimits" select="$serviceProviderEntity/field[@name = 'thresholdAutoPayLimit']"/>
                            <reduseOfBalance>
                                <autoPaymentFloorLimit>
                                    <xsl:choose>
                                        <xsl:when test="count($floorLimits)>0">
                                            <xsl:call-template name="autoPaymentFloorLimitListField">
                                                <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                                <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                                                <xsl:with-param name="listValues" select="$floorLimits"/>
                                            </xsl:call-template>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:call-template name="moneyField">
                                                <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                                <xsl:with-param name="title">Минимальный баланс</xsl:with-param>
                                                <xsl:with-param name="required" select="true()"/>
                                                <xsl:with-param name="editable" select="true()"/>
                                                <xsl:with-param name="visible" select="true()"/>
                                                <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </autoPaymentFloorLimit>
                                <xsl:if test="number($mobileApiVersion) >= 5.20 and $serviceProviderEntity/field[@name = 'ThresholdAutoPayScheme#IsTotalMaxSumSupported'] = 'true'">
                                    <autoPaymentTotalAmountLimit>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name">autoPaymentTotalAmountLimit</xsl:with-param>
                                            <xsl:with-param name="title">
                                                Максимальная сумма платежей в
                                                <xsl:call-template name="periodTotalAmount2text">
                                                    <xsl:with-param name="code" select="$serviceProviderEntity/field[@name = 'ThresholdAutoPayScheme#TotalMaxSumPeriod']"/>
                                                </xsl:call-template>
                                            </xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="true()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="autoPaymentTotalAmountLimit"/>
                                        </xsl:call-template>
                                    </autoPaymentTotalAmountLimit>
                                </xsl:if>
                            </reduseOfBalance>
                        </xsl:when>
                        <xsl:when test="executionEventType='BY_INVOICE'">
                            <byInvoice>
                                <autoPaymentFloorLimit>
                                    <xsl:call-template name="moneyField">
                                        <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                        <xsl:with-param name="title">Максимальная сумма</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                                    </xsl:call-template>
                                </autoPaymentFloorLimit>
                            </byInvoice>
                        </xsl:when>
                    </xsl:choose>

                    <autoPaymentName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoPaymentName</xsl:with-param>
                            <xsl:with-param name="title">Название</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoPaymentName"/>
                        </xsl:call-template>
                    </autoPaymentName>
                </autoPaymentParameters>
            </EditAutoPaymentPayment>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="cardNumber" select="cardNumber"/>
        <xsl:variable name="card" select="document('cards.xml')/entity-list/entity[@key=$cardNumber]"/>

        <document>
            <form>EditAutoPaymentPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <EditAutoPaymentDocument>
                <receiverName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">receiverName</xsl:with-param>
                        <xsl:with-param name="title">Наименование получателя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverName"/>
                    </xsl:call-template>
                </receiverName>

                <!--  Источник списания -->
                <xsl:variable name="fromResourceCode" select="$card/field[@name='code']/text()"/>
                <xsl:variable name="displayedValue">
                    <xsl:value-of select="mask:getCutCardNumber($card/@key)"/> [<xsl:value-of select="$card/field[@name='name']"/>]
                </xsl:variable>
                <!--  Ресурс оплаты -->
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Списать со счета</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$fromResourceCode"/>
                        <xsl:with-param name="displayedValue" select="$displayedValue"/>
                    </xsl:call-template>
                </fromResource>

                <requisiteName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">requisiteName</xsl:with-param>
                        <xsl:with-param name="title">Имя ключевого поля</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="requisiteName"/>
                    </xsl:call-template>
                </requisiteName>

                <requisite>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">requisite</xsl:with-param>
                        <xsl:with-param name="title">Значение ключевого поля</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="requisite"/>
                    </xsl:call-template>
                </requisite>

                <xsl:if test="executionEventType!='BY_INVOICE'">
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">sellAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="sellAmount"/>
                        </xsl:call-template>
                    </amount>
                </xsl:if>

                <xsl:variable name="executionEventType" select="executionEventType"/>
                <autoPaymentParameters>
                    <executionEventType>
                        <xsl:call-template name="executionEventTypeField">
                            <xsl:with-param name="name">executionEventType</xsl:with-param>
                            <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$executionEventType"/>
                            <xsl:with-param name="listValues" select="$autoPaymentTypes/entity[./field[@name=$executionEventType]]/field"/>
                            <xsl:with-param name="isView" select="true()"/>
                        </xsl:call-template>
                    </executionEventType>

                    <xsl:choose>
                        <xsl:when test="executionEventType='ONCE_IN_YEAR' or executionEventType='ONCE_IN_QUARTER' or executionEventType='ONCE_IN_MONTH'">
                            <periodic>
                                <autoPaymentStartDate>
                                    <xsl:call-template name="dateField">
                                        <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                        <xsl:with-param name="title">Дата начала действия</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="autoPaymentStartDate"/>
                                    </xsl:call-template>
                                </autoPaymentStartDate>
                                <firstPaymentDate>
                                    <xsl:call-template name="dateField">
                                        <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                        <xsl:with-param name="title">Ближайший платеж</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="firstPaymentDate"/>
                                    </xsl:call-template>
                                </firstPaymentDate>
                            </periodic>
                        </xsl:when>
                        <xsl:when test="executionEventType='REDUSE_OF_BALANCE'">
                            <reduseOfBalance>
                                <autoPaymentFloorLimit>
                                    <xsl:call-template name="moneyField">
                                        <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                        <xsl:with-param name="title">Минимальный баланс</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                                    </xsl:call-template>
                                </autoPaymentFloorLimit>
                                <xsl:if test="number($mobileApiVersion) >= 5.20 and isAutoPaymentTotalAmountLimit = 'true'">
                                    <autoPaymentTotalAmountLimit>
                                        <xsl:call-template name="moneyField">
                                            <xsl:with-param name="name">autoPaymentTotalAmountLimit</xsl:with-param>
                                            <xsl:with-param name="title">
                                                Максимальная сумма платежей в
                                                <xsl:call-template name="periodTotalAmount2text">
                                                    <xsl:with-param name="code" select="autoPaymentTotalAmountPeriod"/>
                                                </xsl:call-template>
                                            </xsl:with-param>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="false()"/>
                                            <xsl:with-param name="visible" select="true()"/>
                                            <xsl:with-param name="value" select="autoPaymentTotalAmountLimit"/>
                                        </xsl:call-template>
                                    </autoPaymentTotalAmountLimit>
                                </xsl:if>
                            </reduseOfBalance>
                        </xsl:when>
                        <xsl:when test="executionEventType='BY_INVOICE'">
                            <byInvoice>
                                <autoPaymentFloorLimit>
                                    <xsl:call-template name="moneyField">
                                        <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                        <xsl:with-param name="title">Максимальная сумма</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                                    </xsl:call-template>
                                </autoPaymentFloorLimit>
                            </byInvoice>
                        </xsl:when>
                    </xsl:choose>
                    
                    <autoPaymentName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoPaymentName</xsl:with-param>
                            <xsl:with-param name="title">Название</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoPaymentName"/>
                        </xsl:call-template>
                    </autoPaymentName>
                </autoPaymentParameters>
            </EditAutoPaymentDocument>
            
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
                                <xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
                                    <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                </xsl:if>
                                <xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
                                    <currency><xsl:value-of select="toResourceCurrency"/></currency>
                                </xsl:if>
                            </valueItem>
                        </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="executionEventTypeField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="listValues"/>
        <xsl:param name="value"/>
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
            <xsl:if test="count($listValues)>0">
                <availableValues>
                    <xsl:choose>
                        <xsl:when test="not($isView)">
                            <xsl:for-each select="$listValues">
                                <xsl:variable name="code">
                                    <xsl:choose>
                                        <xsl:when test="@name != ''">
                                            <xsl:value-of select="@name"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="."/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>
                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <title><xsl:value-of select="."/></title>
                                    <selected><xsl:value-of select="string($code = $value)"/></selected>
                                </valueItem>
                            </xsl:for-each>
                        </xsl:when>
                        <xsl:otherwise>
                            <valueItem>
                                <value><xsl:value-of select="$value"/></value>
                                <title><xsl:value-of select="$listValues[@name=$value]/text()"/></title>
                                <selected>true</selected>
                            </valueItem>
                        </xsl:otherwise>
                    </xsl:choose>
                </availableValues>
            </xsl:if>
        </listType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
    </xsl:template>

    <xsl:template name="periodTotalAmount2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='IN_DAY'">день</xsl:when>
            <xsl:when test="$code='IN_WEEK'">неделю</xsl:when>
            <xsl:when test="$code='IN_TENDAY'">декаду</xsl:when>
            <xsl:when test="$code='IN_MONTH'">декаду</xsl:when>
            <xsl:when test="$code='IN_QUARTER'">квартал</xsl:when>
            <xsl:when test="$code='IN_YEAR'">год</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="autoPaymentFloorLimitListField">
        <xsl:param name="name"/>
        <xsl:param name="listValues"/>
        <xsl:param name="value"/>

        <name><xsl:value-of select="$name"/></name>
        <title>Минимальный баланс</title>
        <type>list</type>
        <required>true</required>
        <editable>true</editable>
        <visible>true</visible>
        <listType>
            <availableValues>
                <xsl:for-each select="$listValues">
                    <xsl:sort select="/text()" data-type="number"/>
                    <valueItem>
                        <value><xsl:value-of select="./text()"/></value>
                        <title><xsl:value-of select="./text()"/></title>
                        <selected><xsl:value-of select="string(./text() = $value)"/></selected>
                    </valueItem>
                </xsl:for-each>
            </availableValues>
        </listType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
    </xsl:template>

</xsl:stylesheet>
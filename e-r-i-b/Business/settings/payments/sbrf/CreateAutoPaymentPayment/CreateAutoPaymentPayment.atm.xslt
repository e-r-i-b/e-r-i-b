<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                extension-element-prefixes="xalan mu mask au">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
	<xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="documentId" select="''"/>
	<xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="templateAvailable" select="''"/>
    <xsl:param name="changedFields"/>

	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="autoPaymentTypes" select="document('auto-payment-types.xml')/entity-list"/>
    <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>


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

        <xsl:variable name="activeAccounts" select="document('active-debit-rur-accounts.xml')/entity-list"/>
        <xsl:variable name="activeCards" select="document('active-not-virtual-cards.xml')/entity-list"/>
        <xsl:variable name="serviceProvider" select="document(concat('serviceProvider.xml?id=',recipient))/entity-list/entity"/>
        <xsl:variable name="codeService" select="$serviceProvider/field[@name = 'code']"/>
        <xsl:variable name="nameService" select="$serviceProvider/field[@name = 'nameService']"/>
        <xsl:variable name="receiverName" select="$serviceProvider/field[@name = 'name']"/>
        <xsl:variable name="supportedTypes" select="document(concat('auto-payment-allowed-types-atm.xml?provider-id=', recipient, '&amp;requisite=', requisite, '&amp;card-number=', fromAccountSelect))/entity-list"/>

        <document>
            <form>CreateAutoPaymentPayment</form>
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

            <CreateAutoPaymentDocument>
                <!-- Информация о получателе -->
                <receiver>

                    <xsl:if test="string-length(receiverDescription)>0">
                        <!-- Описание -->
                        <description>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverDescription</xsl:with-param>
                                <xsl:with-param name="title">Описание</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverDescription"/>
                            </xsl:call-template>
                        </description>
                    </xsl:if>

                    <xsl:if test="string-length($receiverName)>0">
                        <!-- Наименование -->
                        <name>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverName</xsl:with-param>
                                <xsl:with-param name="title">Наименование</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="$receiverName"/>
                            </xsl:call-template>
                        </name>
                    </xsl:if>

                    <xsl:if test="string-length($nameService)>0">
                        <!-- Наименование услуги -->
                        <serviceName>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">nameService</xsl:with-param>
                                <xsl:with-param name="title">Наименование услуги</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="$nameService"/>
                            </xsl:call-template>
                        </serviceName>
                    </xsl:if>
                </receiver>

                <!--  Источник списания -->
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
                        <xsl:with-param name="activeAccount" select="$accountNode"/>
                        <xsl:with-param name="activeCard" select="$cardNode"/>
                    </xsl:call-template>
                </fromResource>

                <paymentDetails>
                    <externalFields>
                        <!-- Сумма -->
                        <field>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">sellAmount</xsl:with-param>
                                <xsl:with-param name="title">Сумма</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="sellAmount"/>
                            </xsl:call-template>
                        </field>
                        <!-- Ключевое поле -->
                        <field>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">requisite</xsl:with-param>
                                <xsl:with-param name="title"><xsl:value-of select="requisiteName"/></xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="requisite"/>
                            </xsl:call-template>
                        </field>
                        <field>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">codeService</xsl:with-param>
                                <xsl:with-param name="title">Идентификатор услуги в биллинговой системе</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="$codeService"/>
                            </xsl:call-template>
                        </field>
                    </externalFields>
                    <xsl:if test="string-length(operationCode)>0">
                        <operationCode>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">operationCode</xsl:with-param>
                                <xsl:with-param name="title">Код валютной операции</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="operationCode"/>
                            </xsl:call-template>
                        </operationCode>
                    </xsl:if>
                    <xsl:if test="string-length(commission)>0">
                        <xsl:call-template name="commission">
                            <xsl:with-param name="commissionAmount"><xsl:value-of select="commission"/></xsl:with-param>
                            <xsl:with-param name="commissionCurrency"><xsl:value-of select="commissionCurrency"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </paymentDetails>

                <autoPaymentParameters>
                    <autoPaymentType>
                        <xsl:call-template name="listFieldType">
                            <xsl:with-param name="name">autoPaymentType</xsl:with-param>
                            <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="listValues" select="$supportedTypes"/>
                            <xsl:with-param name="value" select="autoPaymentType"/>
                        </xsl:call-template>
                    </autoPaymentType>

                    <onceInYear>
                        <autoPaymentStartDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                <xsl:with-param name="title">Дата начала</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentStartDate"/>
                            </xsl:call-template>
                        </autoPaymentStartDate>
                        <month>
                            <xsl:call-template name="month">
                                <xsl:with-param name="name">month</xsl:with-param>
                                <xsl:with-param name="title">Месяц платежа</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                            </xsl:call-template>
                        </month>
                        <firstPaymentDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="firstPaymentDate"/>
                            </xsl:call-template>
                        </firstPaymentDate>
                    </onceInYear>

                    <onceInQuarter>
                        <autoPaymentStartDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                <xsl:with-param name="title">Дата начала</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentStartDate"/>
                            </xsl:call-template>
                        </autoPaymentStartDate>
                        <months>
                            <xsl:call-template name="quarterMonths">
                                <xsl:with-param name="name">months</xsl:with-param>
                                <xsl:with-param name="title">Месяцы</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                            </xsl:call-template>
                        </months>
                        <firstPaymentDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="firstPaymentDate"/>
                            </xsl:call-template>
                        </firstPaymentDate>
                    </onceInQuarter>

                    <onceInMonth>
                        <autoPaymentStartDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                <xsl:with-param name="title">Дата начала</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentStartDate"/>
                            </xsl:call-template>
                         </autoPaymentStartDate>
                        <firstPaymentDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="firstPaymentDate"/>
                            </xsl:call-template>
                        </firstPaymentDate>
                    </onceInMonth>

                    <reduseOfBalance>
                        <floorLimit>
                            <xsl:variable name="floorLimits" select="$supportedTypes/entity[@key='REDUSE_OF_BALANCE']/field[@name = 'thresholdAutoPayLimit']"/>
                            <xsl:choose>
                                <xsl:when test="count($floorLimits)>0">
                                    <xsl:call-template name="autoPaymentFloorLimitListField">
                                        <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                        <xsl:with-param name="title">Минимальный баланс</xsl:with-param>
                                        <xsl:with-param name="required" select="true()"/>
                                        <xsl:with-param name="editable" select="true()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="listValues" select="$floorLimits"/>
                                        <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
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
                        </floorLimit>
                        <xsl:variable name="reduseOfBalanceEntity" select="$supportedTypes/entity[@key='REDUSE_OF_BALANCE']"/>
                        <xsl:if test="$reduseOfBalanceEntity/field[@name = 'isTotalMaxSum'] = 'true'">
                            <autoPaymentTotalAmountLimit>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">autoPaymentTotalAmountLimit</xsl:with-param>
                                    <xsl:with-param name="title">
                                        Максимальная сумма платежей в
                                        <xsl:call-template name="periodTotalAmount2text">
                                            <xsl:with-param name="code" select="$reduseOfBalanceEntity/field[@name = 'totalMaxSumPeriod']"/>
                                        </xsl:call-template>
                                    </xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="true()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="autoPaymentTotalAmountLimit"/>
                                </xsl:call-template>
                            </autoPaymentTotalAmountLimit>
                        </xsl:if>
                    </reduseOfBalance>

                    <byInvoice>
                        <maxSum>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                <xsl:with-param name="title">Максимальная сумма</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                            </xsl:call-template>
                        </maxSum>
                    </byInvoice>

                    <autoPaymentName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">autoPaymentName</xsl:with-param>
                            <xsl:with-param name="title">Название регулярного платежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value">
                                <xsl:choose>
                                    <xsl:when test="string-length(autoPaymentName)>0">
                                        <xsl:value-of select="autoPaymentName"/>
                                    </xsl:when>
                                    <xsl:when test="20 >= string-length(requisite)">
                                        <xsl:value-of select="requisite"/>
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </autoPaymentName>

                </autoPaymentParameters>

            </CreateAutoPaymentDocument>

        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
    <xsl:variable name="activeAccounts" select="document('active-debit-rur-accounts.xml')/entity-list"/>
    <xsl:variable name="activeCards" select="document('active-not-virtual-cards.xml')/entity-list"/>
    <xsl:variable name="serviceProvider" select="document(concat('serviceProvider.xml?id=',recipient))/entity-list/entity"/>
    <xsl:variable name="nameService" select="$serviceProvider/field[@name = 'nameService']"/>
    <xsl:variable name="receiverName" select="$serviceProvider/field[@name = 'name']"/>
        <document>
            <form>CreateAutoPaymentPayment</form>
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

            <CreateAutoPaymentDocument>
                <!-- Информация о получателе -->
                <receiver>

                    <xsl:if test="string-length(receiverDescription)>0">
                        <!-- Описание -->
                        <description>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverDescription</xsl:with-param>
                                <xsl:with-param name="title">Описание</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverDescription"/>
                            </xsl:call-template>
                        </description>
                    </xsl:if>

                    <xsl:if test="string-length($receiverName)>0">
                        <!-- Наименование -->
                        <name>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">receiverName</xsl:with-param>
                                <xsl:with-param name="title">Наименование</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="$receiverName"/>
                            </xsl:call-template>
                        </name>
                    </xsl:if>

                    <xsl:if test="string-length($nameService)>0">
                        <!-- Наименование услуги -->
                        <serviceName>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">nameService</xsl:with-param>
                                <xsl:with-param name="title">Наименование услуги</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="$nameService"/>
                            </xsl:call-template>
                        </serviceName>
                    </xsl:if>
                </receiver>

                <!--  Источник списания -->
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
                        <xsl:with-param name="activeAccount" select="$accountNode"/>
                        <xsl:with-param name="activeCard" select="$cardNode"/>
                    </xsl:call-template>
                </fromResource>

               <paymentDetails>
                    <externalFields>
                        <!-- Сумма -->
                        <field>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">sellAmount</xsl:with-param>
                                <xsl:with-param name="title">Сумма</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="sellAmount"/>
                            </xsl:call-template>
                        </field>
                        <!-- Ключевое поле -->
                        <field>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">requisite</xsl:with-param>
                                <xsl:with-param name="title"><xsl:value-of select="requisiteName"/></xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="requisite"/>
                            </xsl:call-template>
                        </field>
                        <field>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">codeService</xsl:with-param>
                                <xsl:with-param name="title">Идентификатор услуги в биллинговой системе</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="codeService"/>
                            </xsl:call-template>
                        </field>
                    </externalFields>
                    <xsl:if test="string-length(operationCode)>0">
                        <operationCode>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">operationCode</xsl:with-param>
                                <xsl:with-param name="title">Код валютной операции</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="operationCode"/>
                            </xsl:call-template>
                        </operationCode>
                    </xsl:if>
                    <xsl:if test="string-length(commission)>0">
                        <xsl:call-template name="commission">
                            <xsl:with-param name="commissionAmount"><xsl:value-of select="commission"/></xsl:with-param>
                            <xsl:with-param name="commissionCurrency"><xsl:value-of select="commissionCurrency"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </paymentDetails>

               <autoPaymentParameters>
                    <autoPaymentType>
                        <xsl:call-template name="listFieldTypeElement">
                            <xsl:with-param name="name">autoPaymentType</xsl:with-param>
                            <xsl:with-param name="title">Тип события исполнения автоплатежа</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="autoPaymentType"/>
                        </xsl:call-template>
                    </autoPaymentType>

                    <onceInYear>
                        <autoPaymentStartDate>
                            <xsl:call-template name="dateField">
                               <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                               <xsl:with-param name="title">Дата начала</xsl:with-param>
                               <xsl:with-param name="required" select="true()"/>
                               <xsl:with-param name="editable" select="false()"/>
                               <xsl:with-param name="visible" select="true()"/>
                               <xsl:with-param name="value" select="autoPaymentStartDate"/>
                            </xsl:call-template>  
                        </autoPaymentStartDate>
                        <month>
                            <xsl:call-template name="month">
                                <xsl:with-param name="name">month</xsl:with-param>
                                <xsl:with-param name="title">Месяц платежа</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                            </xsl:call-template>
                        </month>
                        <firstPaymentDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="firstPaymentDate"/>
                            </xsl:call-template>
                        </firstPaymentDate>
                    </onceInYear>

                    <onceInQuarter>
                        <autoPaymentStartDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                 <xsl:with-param name="title">Дата начала</xsl:with-param>
                                 <xsl:with-param name="required" select="true()"/>
                                 <xsl:with-param name="editable" select="false()"/>
                                 <xsl:with-param name="visible" select="true()"/>
                                 <xsl:with-param name="value" select="autoPaymentStartDate"/>
                            </xsl:call-template>
                        </autoPaymentStartDate>
                        <months>
                            <xsl:call-template name="quarterMonths">
                                <xsl:with-param name="name">months</xsl:with-param>
                                <xsl:with-param name="title">Месяцы</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                            </xsl:call-template>
                        </months>
                        <firstPaymentDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="firstPaymentDate"/>
                            </xsl:call-template>
                        </firstPaymentDate>
                    </onceInQuarter>

                    <onceInMonth>
                        <autoPaymentStartDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">autoPaymentStartDate</xsl:with-param>
                                <xsl:with-param name="title">Дата начала</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentStartDate"/>
                            </xsl:call-template>
                        </autoPaymentStartDate>
                        <firstPaymentDate>
                            <xsl:call-template name="dateField">
                                <xsl:with-param name="name">firstPaymentDate</xsl:with-param>
                                <xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="firstPaymentDate"/>
                            </xsl:call-template>
                        </firstPaymentDate>
                    </onceInMonth>

                    <reduseOfBalance>
                        <floorLimit>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                <xsl:with-param name="title">Минимальный баланс</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                            </xsl:call-template>
                        </floorLimit>
                        <xsl:if test="isAutoPaymentTotalAmountLimit = 'true'">
                            <autoPaymentTotalAmountLimit>
                                <xsl:call-template name="moneyField">
                                    <xsl:with-param name="name">autoPaymentTotalAmountLimit</xsl:with-param>
                                    <xsl:with-param name="title">
                                        Максимальная сумма платежей в
                                        <xsl:call-template name="periodTotalAmount2text">
                                            <xsl:with-param name="code" select="autoPaymentTotalAmountPeriod"/>
                                        </xsl:call-template>
                                    </xsl:with-param>
                                    <xsl:with-param name="required" select="true()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="autoPaymentTotalAmountLimit"/>
                                </xsl:call-template>
                            </autoPaymentTotalAmountLimit>
                        </xsl:if>

                    </reduseOfBalance>

                    <byInvoice>
                        <maxSum>
                            <xsl:call-template name="moneyField">
                                <xsl:with-param name="name">autoPaymentFloorLimit</xsl:with-param>
                                <xsl:with-param name="title">Максимальная сумма</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentFloorLimit"/>
                            </xsl:call-template>
                        </maxSum>
                    </byInvoice>

                    <autoPaymentName>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">autoPaymentName</xsl:with-param>
                                <xsl:with-param name="title">Название регулярного платежа</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="autoPaymentName"/>
                            </xsl:call-template>
                    </autoPaymentName>

                </autoPaymentParameters>

            </CreateAutoPaymentDocument>
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
        <xsl:param name="validators" select="''"/>
        <xsl:param name="activeAccount"/>
        <xsl:param name="activeCard"/>

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

    <xsl:template name="listFieldType">
         <xsl:param name="name"/>
         <xsl:param name="title"/>
         <xsl:param name="description" select="''"/>
         <xsl:param name="hint" select="''"/>
         <xsl:param name="required"/>
         <xsl:param name="editable"/>
         <xsl:param name="visible"/>
         <xsl:param name="listValues"/>
         <xsl:param name="validators" select="''"/>
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
            <xsl:with-param name="valueTag">
                 <listType>
                     <xsl:if test="count($listValues)>0">
                         <availableValues>
                             <xsl:for-each select="$listValues/entity">
                                <xsl:variable name="key" select="./@key"/>
                                <valueItem>
                                    <value><xsl:value-of select="$key"/></value>
                                    <title><xsl:value-of select="$autoPaymentTypes/entity/field[@name=$key]/text()"/></title>
                                    <selected>
                                        <xsl:choose>
                                            <xsl:when test="$key = $value"><xsl:value-of select="true()"/></xsl:when>
                                            <xsl:otherwise><xsl:value-of select="false()"/></xsl:otherwise>
                                        </xsl:choose>
                                    </selected>
                                </valueItem>
                             </xsl:for-each>
                         </availableValues>
                     </xsl:if>
                 </listType>
            </xsl:with-param>
        </xsl:call-template>
     </xsl:template>

    <xsl:template name="listFieldTypeElement">
         <xsl:param name="name"/>
         <xsl:param name="title"/>
         <xsl:param name="description" select="''"/>
         <xsl:param name="hint" select="''"/>
         <xsl:param name="required"/>
         <xsl:param name="editable"/>
         <xsl:param name="visible"/>
         <xsl:param name="validators" select="''"/>
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
            <xsl:with-param name="valueTag">
                 <listType>
                     <xsl:if test="string-length($value)>0">
                         <availableValues>
                            <valueItem>
                                <value><xsl:value-of select="$value"/></value>
                                <title><xsl:value-of select="$autoPaymentTypes/entity/field[@name=$value]/text()"/></title>
                                <selected><xsl:value-of select="true()"/></selected>
                            </valueItem>
                         </availableValues>
                     </xsl:if>
                 </listType>
            </xsl:with-param>
        </xsl:call-template>
     </xsl:template>

    <xsl:template name="quarterMonths">
          <xsl:param name="name"/>
          <xsl:param name="title"/>
          <xsl:param name="description" select="''"/>
          <xsl:param name="hint" select="''"/>
          <xsl:param name="required"/>
          <xsl:param name="editable"/>
          <xsl:param name="visible"/>
          <xsl:param name="validators" select="''"/>

          <xsl:variable name="firstPaymentDate">
             <xsl:choose>
                 <xsl:when test="contains(firstPaymentDate, '-')">
                     <xsl:copy-of select="concat(substring(firstPaymentDate, 9, 2), '.', substring(firstPaymentDate, 6, 2), '.', substring(firstPaymentDate, 1, 4))"/>
                 </xsl:when>
                 <xsl:otherwise>
                     <xsl:value-of select="firstPaymentDate"/>
                 </xsl:otherwise>
             </xsl:choose>
          </xsl:variable>

          <xsl:variable name="month"      select="substring($firstPaymentDate, 4, 2)"/>

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
            <xsl:with-param name="valueTag">
                 <listType>
                    <availableValues>
                      <valueItem>
                          <xsl:variable name="quart">01|04|07|10</xsl:variable>
                          <value><xsl:value-of select="$quart"/></value>
                          <title>
                               <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"><xsl:value-of select="$quart"/></xsl:with-param>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                          </title>
                          <selected>
                            <xsl:choose>
                                <xsl:when test="contains($quart, $month)">
                                    <xsl:value-of select="true()"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="false()"/>
                                </xsl:otherwise>
                            </xsl:choose>
                          </selected>
                      </valueItem>
                      <valueItem>
                          <xsl:variable name="quart">02|05|08|11</xsl:variable>
                          <value><xsl:value-of select="$quart"/></value>
                          <title>
                               <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"><xsl:value-of select="$quart"/></xsl:with-param>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                          </title>
                          <selected>
                            <xsl:choose>
                                <xsl:when test="contains($quart, $month)">
                                    <xsl:value-of select="true()"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="false()"/>
                                </xsl:otherwise>
                            </xsl:choose>
                          </selected>
                      </valueItem>
                      <valueItem>
                          <xsl:variable name="quart">03|06|09|12</xsl:variable>
                          <value><xsl:value-of select="$quart"/></value>
                          <title>
                               <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"><xsl:value-of select="$quart"/></xsl:with-param>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                          </title>
                          <selected>
                            <xsl:choose>
                                <xsl:when test="contains($quart, $month)">
                                    <xsl:value-of select="true()"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="false()"/>
                                </xsl:otherwise>
                            </xsl:choose>
                          </selected>
                      </valueItem>
                    </availableValues>
                  </listType>
            </xsl:with-param>
        </xsl:call-template>
      </xsl:template>

    <xsl:template name="month">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="validators" select="''"/>

        <xsl:variable name="firstPaymentDate">
            <xsl:choose>
                <xsl:when test="contains(firstPaymentDate, '-')">
                    <xsl:copy-of select="concat(substring(firstPaymentDate, 9, 2), '.', substring(firstPaymentDate, 6, 2), '.', substring(firstPaymentDate, 1, 4))"/>
                </xsl:when>
                <xsl:otherwise>
                     <xsl:value-of select="firstPaymentDate"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="month"      select="substring($firstPaymentDate, 4, 2)"/>

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
            <xsl:with-param name="valueTag">
                 <listType>
                    <availableValues>
                        <xsl:for-each select="$months">
                            <valueItem>
                                <value><xsl:value-of select="./@key"/></value>
                                <title><xsl:value-of select="./text()"/></title>
                                <selected>
                                    <xsl:choose>
                                        <xsl:when test="$month = ./@key"><xsl:value-of select="true()"/></xsl:when>
                                        <xsl:otherwise><xsl:value-of select="false()"/></xsl:otherwise>
                                    </xsl:choose>
                                </selected>
                            </valueItem>
                        </xsl:for-each>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>
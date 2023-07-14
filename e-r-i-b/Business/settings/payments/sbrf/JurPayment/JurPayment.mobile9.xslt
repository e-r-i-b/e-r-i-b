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
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>
    <xsl:param name="isByTemplate"/> <!--платеж создается по шаблону-->
    <xsl:param name="isTemplate" select="'isTemplate'"/>

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
        <xsl:variable name="activeAccounts" select="document('stored-active-debit-rur-allowed-external-jur-accounts.xml')/entity-list/*"/>

        <document>
            <!-- 1. Реквизиты документа -->
            <form>JurPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <JurPaymentDocument>
                <!-- 1.4 Номер документа -->
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
                <!-- 1.5 Дата документа -->
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

                <!-- 2. Информация по получателю -->
                <receiver>
                    <!-- 2.1 Наименование получателя -->
                    <receiverName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverName</xsl:with-param>
                            <xsl:with-param name="title">Наименование</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="not($isByTemplate)"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverName"/>
                        </xsl:call-template>
                    </receiverName>

                    <!-- 2.2 Банковские реквизиты -->
                    <bankDetails>
                        <!-- 2.2.1 Расчетный счет получателя -->
                        <account>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">Номер счёта</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverAccount"/>
                            </xsl:call-template>
                        </account>

                        <!-- 2.2.2 Валюта счета получателя -->
                        <accountCurrency>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">buyAmountCurrency</xsl:with-param>
                                <xsl:with-param name="title" select="''"/>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value">RUB</xsl:with-param>
                            </xsl:call-template>
                        </accountCurrency>

                        <!-- 2.2.3 ИНН -->
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

                        <!-- 2.2.4 КПП -->
                        <KPP>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverKPP</xsl:with-param>
                                <xsl:with-param name="title">КПП</xsl:with-param>
                                <xsl:with-param name="maxLength" select="9"/>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="not($isByTemplate)"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverKPP"/>
                            </xsl:call-template>
                        </KPP>

                        <bank>
                            <!-- 2.2.5 Наименование банка получателя -->
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

                            <!-- 2.2.6 БИК банка получателя -->
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

                            <!-- 2.4.7 Корсчёт банка получателя -->
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
                        </bank>
                    </bankDetails>
                </receiver>

                <!-- 3. Источник списания -->
                <xsl:variable name="linkId" select="fromResource"/>
                <xsl:variable name="accountNumber" select="fromAccountSelect"/>

                <fromResource>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Счет списания</xsl:with-param>
                        <xsl:with-param name="type" select="'resource'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="valueTag">
                            <resourceType>
                                <xsl:if test="count($activeAccounts)>0">
                                    <availableValues>
                                        <xsl:for-each select="$activeAccounts">
                                            <valueItem>
                                                <xsl:variable name="id" select="field[@name='code']/text()"/>
                                                <value><xsl:value-of select="$id"/></value>
                                                <selected><xsl:value-of select="string($accountNumber=./@key or $linkId=$id)"/></selected>
                                                <displayedValue>
                                                    <xsl:value-of select="au:getFormattedAccountNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                                </displayedValue>
                                                <xsl:if test="number($mobileApiVersion) >= 5.20">
                                                    <currency><xsl:value-of select="field[@name='currencyCode']"/></currency>
                                                </xsl:if>
                                            </valueItem>
                                        </xsl:for-each>
                                    </availableValues>
                                </xsl:if>
                            </resourceType>
                        </xsl:with-param>
                    </xsl:call-template>
                </fromResource>

                <!-- 4. Детали платежа -->
                <paymentDetails>
                    <!-- 4.1 Сумма зачисления -->
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма в валюте зачисления</xsl:with-param>
                            <xsl:with-param name="hint">Введите сумму, которую необходимо перевести</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                    <!-- 4.4 Назначение платежа -->
                    <ground>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">ground</xsl:with-param>
                            <xsl:with-param name="title">Назначение перевода</xsl:with-param>
                            <xsl:with-param name="hint">Укажите, с какой целью Вы переводите деньги. Например, благотворительный взнос</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="not($isByTemplate)"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="ground"/>
                        </xsl:call-template>
                    </ground>
                    <!-- 4.5 Флажок "налоговый платёж" -->
                    <taxPayment>
                        <xsl:call-template name="booleanField">
                            <xsl:with-param name="name">taxPayment</xsl:with-param>
                            <xsl:with-param name="title" select="''"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="false()"/> <!--доработку не заказывали-->
                        </xsl:call-template>
                    </taxPayment>
                </paymentDetails>
            </JurPaymentDocument>
        </document>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <!-- 1. Реквизиты документа -->
            <form>JurPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <JurPaymentDocument>
                <!-- 1.4 Номер документа -->
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
                <!-- 1.5 Дата документа -->
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

                <!-- 2. Информация по получателю -->
                <receiver>
                    <!-- 2.1 Наименование получателя -->
                    <receiverName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverName</xsl:with-param>
                            <xsl:with-param name="title">Наименование</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverName"/>
                        </xsl:call-template>
                    </receiverName>

                    <!-- 2.2 Банковские реквизиты -->
                    <bankDetails>
                        <!-- 2.2.1 Расчетный счет получателя -->
                        <account>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverAccount</xsl:with-param>
                                <xsl:with-param name="title">Номер счёта</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverAccount"/>
                            </xsl:call-template>
                        </account>

                        <!-- 2.2.2 Валюта счета получателя -->
                        <accountCurrency>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">buyAmountCurrency</xsl:with-param>
                                <xsl:with-param name="title" select="''"/>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="false()"/>
                                <xsl:with-param name="value" select="buyAmountCurrency"/>
                            </xsl:call-template>
                        </accountCurrency>

                        <!-- 2.2.3 ИНН -->
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

                        <!-- 2.2.4 КПП -->
                        <KPP>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverKPP</xsl:with-param>
                                <xsl:with-param name="title">КПП</xsl:with-param>
                                <xsl:with-param name="maxLength" select="12"/>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverKPP"/>
                            </xsl:call-template>
                        </KPP>

                        <bank>
                            <!-- 2.2.5 Наименование банка получателя -->
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

                            <!-- 2.2.6 БИК банка получателя -->
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

                            <!-- 2.4.7 Корсчёт банка получателя -->
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
                        </bank>
                    </bankDetails>
                </receiver>

                <!-- 3. Источник списания -->
                <xsl:variable name="fromResource" select="fromResourceLink"/>
                <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>
                <xsl:variable name="displayedValue">
                    <xsl:choose>
                        <xsl:when test="(fromResourceType='com.rssl.phizic.business.resources.external.CardLink') or (starts-with(fromResource, 'card:'))">
                            <xsl:value-of select="mask:getCutCardNumber($fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="au:getFormattedAccountNumber($fromAccountSelect)"/> [<xsl:value-of select="fromAccountName"/>]
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <fromResource>
                    <xsl:call-template name="simpleField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">Счет списания</xsl:with-param>
                        <xsl:with-param name="type" select="'resource'"/>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="valueTag">
                            <resourceType>
                                <availableValues>
                                    <valueItem>
                                        <value><xsl:value-of select="$fromResource"/></value>
                                        <selected>true</selected>
                                        <displayedValue><xsl:value-of select="$displayedValue"/></displayedValue>
                                        <xsl:if test="number($mobileApiVersion) >= 5.20 and string-length(fromResourceCurrency)>0">
                                            <currency><xsl:value-of select="fromResourceCurrency"/></currency>
                                        </xsl:if>
                                    </valueItem>
                                </availableValues>
                            </resourceType>
                        </xsl:with-param>
                    </xsl:call-template>
                </fromResource>

                <!-- 4. Детали платежа -->
                <paymentDetails>
                    <!-- 4.1 Сумма зачисления -->
                    <buyAmount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">buyAmount</xsl:with-param>
                            <xsl:with-param name="title">Сумма в валюте зачисления</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="buyAmount"/>
                        </xsl:call-template>
                    </buyAmount>
                    <!-- 4.2 Комиссия -->
                    <xsl:if test="$isTemplate != 'true'">
                        <xsl:call-template name="commission">
                            <xsl:with-param name="commissionAmount"><xsl:value-of select="commission"/></xsl:with-param>
                            <xsl:with-param name="commissionCurrency"><xsl:value-of select="commissionCurrency"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                    <!-- 4.4 Назначение платежа -->
                    <ground>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">ground</xsl:with-param>
                            <xsl:with-param name="title">Назначение перевода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="ground"/>
                        </xsl:call-template>
                    </ground>
                    <!-- 4.5 Флажок "налоговый платёж" -->
                    <taxPayment>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">taxPayment</xsl:with-param>
                            <xsl:with-param name="title" select="''"/>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="taxPayment"/>
                        </xsl:call-template>
                    </taxPayment>
                </paymentDetails>
            </JurPaymentDocument>
        </document>
    </xsl:template>

</xsl:stylesheet>

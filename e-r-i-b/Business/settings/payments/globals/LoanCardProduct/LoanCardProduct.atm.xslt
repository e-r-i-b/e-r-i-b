<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au mu">

    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>

    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="templateAvailable" select="''"/>
    <xsl:param name="changedFields"/>

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
            <form>LoanCardProduct</form>
            <LoanCardProduct>
                <xsl:if test="income != ''">
                    <income>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">income</xsl:with-param>
                            <xsl:with-param name="title">Идентификатор уровня дохода</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="income"/>
                        </xsl:call-template>
                    </income>
                </xsl:if>
                <loan>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">loan</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор условий заявки на кредитную карту</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="loan"/>
                    </xsl:call-template>
                </loan>
                <changeDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">changeDate</xsl:with-param>
                        <xsl:with-param name="title">Дата создания документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="changeDate"/>
                    </xsl:call-template>
                </changeDate>

                <xsl:variable name="loanInfo" select="document(concat('loanCardProduct.xml?loan=',loan))/entity-list/entity"/>
                <xsl:variable name="limit" select="document(concat('cardAmountStep.xml?loan=',loan))"/>

                <xsl:variable name="min-limit" select="$loanInfo/field[@name = 'min-limit']"/>
                <minLimit>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">min-limit</xsl:with-param>
                        <xsl:with-param name="title">Доступный кредитный лимит от</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents($min-limit,'.')"/>
                    </xsl:call-template>
                </minLimit>

                <xsl:variable name="max-limit" select="$loanInfo/field[@name = 'max-limit']"/>
                <maxLimit>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">max-limit</xsl:with-param>
                        <xsl:with-param name="title">Доступный кредитный лимит до</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents($max-limit,'.')"/>
                    </xsl:call-template>
                </maxLimit>

                <xsl:variable name="max-limit-include" select="$loanInfo/field[@name = 'max-limit-include']"/>
                <maxLimitInclude>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">max-limit-include</xsl:with-param>
                        <xsl:with-param name="title">Включая правую границу лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$max-limit-include"/>
                    </xsl:call-template>
                </maxLimitInclude>

                <xsl:variable name="additionalTerms" select="$loanInfo/field[@name = 'additionalTerms']"/>
                <xsl:if test="$additionalTerms != ''">
                    <additionalTerms>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">additionalTerms</xsl:with-param>
                            <xsl:with-param name="title">Дополнительные условия</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="false()"/>
                            <xsl:with-param name="value" select="$additionalTerms"/>
                        </xsl:call-template>
                    </additionalTerms>
                </xsl:if>

                <xsl:variable name="grace-period-duration" select="$loanInfo/field[@name = 'grace-period-duration']"/>
                <gracePeriodDuration>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">grace-period-duration</xsl:with-param>
                        <xsl:with-param name="title">Льготный период до</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$grace-period-duration"/>
                    </xsl:call-template>
                </gracePeriodDuration>

                <xsl:variable name="grace-period-interest-rate" select="$loanInfo/field[@name = 'grace-period-interest-rate']"/>
                <gracePeriodInterestRate>
                    <xsl:call-template name="numberField">
                        <xsl:with-param name="name">grace-period-interest-rate</xsl:with-param>
                        <xsl:with-param name="title">Процентная ставка в льготный период</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$grace-period-interest-rate"/>
                    </xsl:call-template>
                </gracePeriodInterestRate>

                <xsl:variable name="cardProductId" select="$loanInfo/field[@name = 'cardProductId']"/>
                <cardProductId>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">cardProductId</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор карточного продукта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$cardProductId"/>
                    </xsl:call-template>
                </cardProductId>

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

                <xsl:variable name="creditCard" select="$loanInfo/field[@name = 'name']"/>
                <creditCard>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditCard</xsl:with-param>
                        <xsl:with-param name="title">Кредитная карта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$creditCard"/>
                    </xsl:call-template>
                </creditCard>

                <xsl:variable name="interest-rate" select="$loanInfo/field[@name = 'interestRate']"/>
                <interestRate>
                    <xsl:call-template name="numberField">
                        <xsl:with-param name="name">interest-rate</xsl:with-param>
                        <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$interest-rate"/>
                    </xsl:call-template>
                </interestRate>

                <xsl:variable name="first-year-service" select="$loanInfo/field[@name = 'firstYearPaymentDecimal']"/>
                <firstYearPayment>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">first-year-service</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание первый год</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$first-year-service"/>
                    </xsl:call-template>
                </firstYearPayment>

                <xsl:variable name="first-year-service-currency" select="$loanInfo/field[@name = 'firstYearPaymentCurrency']"/>
                <firstYearPaymentCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">first-year-service-currency</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание первый год. Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$first-year-service-currency"/>
                    </xsl:call-template>
                </firstYearPaymentCurrency>

                <xsl:variable name="next-year-service" select="$loanInfo/field[@name = 'nextYearPaymentDecimal']"/>
                <nextYearPayment>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">next-year-service</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание последующие годы.</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$next-year-service"/>
                    </xsl:call-template>
                </nextYearPayment>

                <xsl:variable name="next-year-service-currency" select="$loanInfo/field[@name = 'nextYearPaymentCurrency']"/>
                <nextYearPaymentCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">next-year-service-currency</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание последующие годы.Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$next-year-service-currency"/>
                    </xsl:call-template>
                </nextYearPaymentCurrency>

                <xsl:variable name="amountLimit">
                    <xsl:for-each select="xalan:distinct($limit/entity-list/entity)">
                        <xsl:element name="entity">
                            <xsl:attribute name="key"><xsl:value-of select="field[@name='decimal']/text()"/></xsl:attribute>
                            <xsl:value-of select="field[@name='decimal']/text()"/>
                        </xsl:element>
                    </xsl:for-each>
                </xsl:variable>

                <amount>
                    <xsl:call-template name="listField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Кредитный лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="listValues" select="xalan:nodeset($amountLimit)/node()"/>
                        <xsl:with-param name="value" select="amount"/>
                    </xsl:call-template>
                </amount>

                <xsl:variable name="currency" select="$loanInfo/field[@name = 'currency']"/>
                <currency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currency</xsl:with-param>
                        <xsl:with-param name="title">Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$currency"/>
                    </xsl:call-template>
                </currency>

                <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>

                <xsl:variable name="surName" select="substring($person/field[@name = 'surName'], 1, 1)"/>
                <surName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">surName</xsl:with-param>
                        <xsl:with-param name="title">Фамилия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$surName"/>
                    </xsl:call-template>
                </surName>

                <xsl:variable name="firstName" select="$person/field[@name = 'firstName']"/>
                <firstName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">firstName</xsl:with-param>
                        <xsl:with-param name="title">Имя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$firstName"/>
                    </xsl:call-template>
                </firstName>

                <xsl:variable name="patrName" select="$person/field[@name = 'patrName']"/>
                <patrName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">patrName</xsl:with-param>
                        <xsl:with-param name="title">Отчество</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$patrName"/>
                    </xsl:call-template>
                </patrName>

                <xsl:variable name="homePhone">
                    <xsl:choose>
                        <xsl:when test="homePhone != ''">
                            <xsl:value-of select="homePhone"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'homePhone']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <homePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">homePhone</xsl:with-param>
                        <xsl:with-param name="title">Домашний телефон</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$homePhone"/>
                    </xsl:call-template>
                </homePhone>

                <xsl:variable name="workPhone">
                    <xsl:choose>
                        <xsl:when test="workPhone != ''">
                            <xsl:value-of select="workPhone"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'jobPhone']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <workPhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">workPhone</xsl:with-param>
                        <xsl:with-param name="title">Рабочий телефон</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$workPhone"/>
                    </xsl:call-template>
                </workPhone>

                <xsl:variable name="mobilePhone">
                    <xsl:choose>
                        <xsl:when test="workPhone != ''">
                            <xsl:value-of select="workPhone"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'mobilePhone']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <mobilePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">mobilePhone</xsl:with-param>
                        <xsl:with-param name="title">Мобильный телефон</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$mobilePhone"/>
                    </xsl:call-template>
                </mobilePhone>

                <xsl:variable name="email">
                    <xsl:choose>
                        <xsl:when test="email != ''">
                            <xsl:value-of select="email"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'email']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <email>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">email</xsl:with-param>
                        <xsl:with-param name="title">E-mail</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$email"/>
                    </xsl:call-template>
                </email>

                <freeTime>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">freeTime</xsl:with-param>
                        <xsl:with-param name="title">Желательная дата и время звонка от сотрудника банка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="freeTime"/>
                    </xsl:call-template>
                </freeTime>

            </LoanCardProduct>

        </initialData>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>LoanCardProduct</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
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

            <LoanCardProductDocument>

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

                <creditCard>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditCard</xsl:with-param>
                        <xsl:with-param name="title">Кредитная карта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="creditCard"/>
                    </xsl:call-template>
                </creditCard>

                <interestRate>
                    <xsl:call-template name="numberField">
                        <xsl:with-param name="name">interest-rate</xsl:with-param>
                        <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="interest-rate"/>
                    </xsl:call-template>
                </interestRate>

                <firstYearPayment>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">first-year-service</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание первый год</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="first-year-service"/>
                    </xsl:call-template>
                </firstYearPayment>

                <firstYearPaymentCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">first-year-service-currency</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание первый год. Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="first-year-service-currency"/>
                    </xsl:call-template>
                </firstYearPaymentCurrency>

                <nextYearPayment>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">next-year-service</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание последующие годы.</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="next-year-service"/>
                    </xsl:call-template>
                </nextYearPayment>

                <nextYearPaymentCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">next-year-service-currency</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание последующие годы.Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="next-year-service-currency"/>
                    </xsl:call-template>
                </nextYearPaymentCurrency>

                <amount>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">amount</xsl:with-param>
                        <xsl:with-param name="title">Кредитный лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents(amount,'.')"/>
                    </xsl:call-template>
                </amount>

                <currency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currency</xsl:with-param>
                        <xsl:with-param name="title">Валюта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="currency"/>
                    </xsl:call-template>
                </currency>

                <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>
                <xsl:variable name="surName" select="substring($person/field[@name = 'surName'], 1, 1)"/>
                <surName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">surName</xsl:with-param>
                        <xsl:with-param name="title">Фамилия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$surName"/>
                    </xsl:call-template>
                </surName>

                <xsl:variable name="firstName" select="$person/field[@name = 'firstName']"/>
                <firstName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">firstName</xsl:with-param>
                        <xsl:with-param name="title">Имя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$firstName"/>
                    </xsl:call-template>
                </firstName>

                <xsl:variable name="patrName" select="$person/field[@name = 'patrName']"/>
                <patrName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">patrName</xsl:with-param>
                        <xsl:with-param name="title">Отчество</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$patrName"/>
                    </xsl:call-template>
                </patrName>

                <xsl:variable name="homePhone">
                    <xsl:choose>
                        <xsl:when test="homePhone != ''">
                            <xsl:value-of select="homePhone"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'homePhone']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <homePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">homePhone</xsl:with-param>
                        <xsl:with-param name="title">Домашний телефон</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$homePhone"/>
                    </xsl:call-template>
                </homePhone>

                <xsl:variable name="workPhone">
                    <xsl:choose>
                        <xsl:when test="workPhone != ''">
                            <xsl:value-of select="workPhone"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'jobPhone']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>

                <workPhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">workPhone</xsl:with-param>
                        <xsl:with-param name="title">Рабочий телефон</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$workPhone"/>
                    </xsl:call-template>
                </workPhone>

                <xsl:variable name="mobilePhone">
                    <xsl:choose>
                        <xsl:when test="workPhone != ''">
                            <xsl:value-of select="workPhone"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'mobilePhone']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <mobilePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">mobilePhone</xsl:with-param>
                        <xsl:with-param name="title">Мобильный телефон</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$mobilePhone"/>
                    </xsl:call-template>
                </mobilePhone>

                <xsl:variable name="email">
                    <xsl:choose>
                        <xsl:when test="email != ''">
                            <xsl:value-of select="email"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$person/field[@name = 'email']"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <email>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">email</xsl:with-param>
                        <xsl:with-param name="title">E-mail</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$email"/>
                    </xsl:call-template>
                </email>

                <freeTime>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">freeTime</xsl:with-param>
                        <xsl:with-param name="title">Желательная дата и время звонка от сотрудника банка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="freeTime"/>
                    </xsl:call-template>
                </freeTime>

            </LoanCardProductDocument>

        </document>

    </xsl:template>

</xsl:stylesheet>
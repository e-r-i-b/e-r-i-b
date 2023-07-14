<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                exclude-result-prefixes="xalan mask au mu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="mobileApiVersion"/>
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
            <form>LoanOffer</form>
            <LoanOffer>
                <loan>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">loan</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор кредита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="loan"/>
                    </xsl:call-template>
                </loan>

                <xsl:variable name="loanInfo" select="document(concat('loanOffer.xml?offer=',loan))/entity-list/entity"/>
                <xsl:variable name="conditions" select="$loanInfo/entity-list/entity"/>
                <xsl:variable name="passport-number" select="$loanInfo/field[@name = 'pasportNumber']"/>
                <passportNumber>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">passport-number</xsl:with-param>
                        <xsl:with-param name="title">Номер клиента</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$passport-number"/>
                    </xsl:call-template>
                </passportNumber>

                <xsl:variable name="passport-series" select="$loanInfo/field[@name = 'pasportSeries']"/>
                <passportSeries>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">passport-series</xsl:with-param>
                        <xsl:with-param name="title">Номер серии паспорта клиента</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$passport-series"/>
                    </xsl:call-template>
                </passportSeries>

                <xsl:variable name="tb" select="$loanInfo/field[@name = 'tb']"/>
                <tb>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">tb</xsl:with-param>
                        <xsl:with-param name="title">Терреториальный банк</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$tb"/>
                    </xsl:call-template>
                </tb>

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

                <xsl:variable name="creditType" select="$loanInfo/field[@name = 'name']"/>
                <creditType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditType</xsl:with-param>
                        <xsl:with-param name="title">Наименование кредита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$creditType"/>
                    </xsl:call-template>
                </creditType>

                <duration>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">duration</xsl:with-param>
                        <xsl:with-param name="title">Срок в месяцах</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="duration"/>
                    </xsl:call-template>
                </duration>

                <sum>
                    <xsl:variable name="amount">
                        <xsl:choose>
                            <xsl:when test="amount != ''">
                                <xsl:value-of select="amount"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="selectedDuration">
                                    <xsl:value-of select="duration"/>
                                </xsl:variable>
                                <xsl:for-each select="$conditions/entity-list/entity">
                                    <xsl:variable name="durationValue" select="field[@name='duration']/text()"/>
                                    <xsl:if test="$durationValue = $selectedDuration">
                                        <xsl:value-of select="field[@name='amount']/text()"/>
                                    </xsl:if>
                                </xsl:for-each>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">amount</xsl:with-param>
                            <xsl:with-param name="title">Сумма</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents($amount,'.')"/>
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
                </sum>
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
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$mobilePhone"/>
                    </xsl:call-template>
                </mobilePhone>

                <hirer>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">hirer</xsl:with-param>
                        <xsl:with-param name="title">Работодатель</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="hirer"/>
                    </xsl:call-template>
                </hirer>
                    
                <averageIncomePerMonth>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">averageIncomePerMonth</xsl:with-param>
                        <xsl:with-param name="title">Средний доход в месяц</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="averageIncomePerMonth"/>
                    </xsl:call-template>
                </averageIncomePerMonth>
                    
                <getPaidOnAccount>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">getPaidOnAccount</xsl:with-param>
                        <xsl:with-param name="title">получаю зарплату на карту/счет в Сбербанке</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="getPaidOnAccount"/>
                    </xsl:call-template>
                </getPaidOnAccount>
            </LoanOffer>

        </initialData>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>LoanOffer</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <LoanOfferDocument>

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

                <creditType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditType</xsl:with-param>
                        <xsl:with-param name="title">Наименование кредита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="creditType"/>
                    </xsl:call-template>
                </creditType>

                <duration>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">duration</xsl:with-param>
                        <xsl:with-param name="title">Срок в месяцах</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="duration"/>
                    </xsl:call-template>
                </duration>

                <sum>
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">amount</xsl:with-param>
                            <xsl:with-param name="title">Сумма</xsl:with-param>
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
                </sum>

                <surName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">surName</xsl:with-param>
                        <xsl:with-param name="title">Фамилия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="surName"/>
                    </xsl:call-template>
                </surName>

                <firstName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">firstName</xsl:with-param>
                        <xsl:with-param name="title">Имя</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="firstName"/>
                    </xsl:call-template>
                </firstName>

                <patrName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">patrName</xsl:with-param>
                        <xsl:with-param name="title">Отчество</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="patrName"/>
                    </xsl:call-template>
                </patrName>

                <mobilePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">mobilePhone</xsl:with-param>
                        <xsl:with-param name="title">Мобильный телефон</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="mobilePhone"/>
                    </xsl:call-template>
                </mobilePhone>

                <hirer>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">hirer</xsl:with-param>
                        <xsl:with-param name="title">Работодатель</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="hirer"/>
                    </xsl:call-template>
                </hirer>
                    
                <averageIncomePerMonth>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">averageIncomePerMonth</xsl:with-param>
                        <xsl:with-param name="title">Средний доход в месяц</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="averageIncomePerMonth"/>
                    </xsl:call-template>
                </averageIncomePerMonth>
                    
                <getPaidOnAccount>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">getPaidOnAccount</xsl:with-param>
                        <xsl:with-param name="title">Получаю зарплату на карту/счет в Сбербанке:</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="getPaidOnAccount"/>
                    </xsl:call-template>
                </getPaidOnAccount>
            </LoanOfferDocument>
        </document>
    </xsl:template>

</xsl:stylesheet>
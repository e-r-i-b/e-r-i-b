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
            <form>LoanProduct</form>
            <LoanProduct>
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

                <changeDate>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">changeDate</xsl:with-param>
                        <xsl:with-param name="title">Дата создания документа</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="changeDate"/>
                    </xsl:call-template>
                </changeDate>

                <xsl:variable name="loanInfo" select="document(concat('loanProduct.xml?loan=',loan,'&amp;changeDate=',changeDate))/entity-list/entity"/>

                <xsl:variable name="creditKind" select="$loanInfo/field[@name = 'kind']"/>
                <creditKind>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditKind</xsl:with-param>
                        <xsl:with-param name="title">Вид кредитного продукта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="$creditKind"/>
                    </xsl:call-template>
                </creditKind>

                <xsl:variable name="productId" select="$loanInfo/field[@name = 'productId']"/>
                <productId>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">productId</xsl:with-param>
                        <xsl:with-param name="title">Идентификатор кредитного продукта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="$productId"/>
                    </xsl:call-template>
                </productId>

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

                <sum>
                    <amount>
                        <xsl:call-template name="moneyField">
                            <xsl:with-param name="name">amount</xsl:with-param>
                            <xsl:with-param name="title">Сумма</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="true()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="mu:getFormatAmountWithNoCents(amount,'.')"/>
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

                <duration>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">duration</xsl:with-param>
                        <xsl:with-param name="title">Срок</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="duration"/>
                    </xsl:call-template>
                </duration>

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
            </LoanProduct>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <document>
            <form>LoanProduct</form>
            <status><xsl:value-of select="$documentStatus"/></status>

            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <LoanProductDocument>

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

                <duration>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">duration</xsl:with-param>
                        <xsl:with-param name="title">Срок</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="duration"/>
                    </xsl:call-template>
                </duration>

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

            </LoanProductDocument>

        </document>

    </xsl:template>

</xsl:stylesheet>
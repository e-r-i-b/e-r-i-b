<?xml version="1.0" encoding="windows-1251"?>
<!-- Чек для заявки на предодобренную кредитную карту -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                extension-element-prefixes="mask au mu">

    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>

	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
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
        <DocumentCheck>
            <form>LoanCardOffer</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>Заявка на предодобренную кредитную карту</title>
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

            <LoanCardOfferDocumentCheck>

                <!--Тип карты-->
                <cardName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditCard</xsl:with-param>
                        <xsl:with-param name="title">Тип карты</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="creditCard"/>
                    </xsl:call-template>
                </cardName>

                <!--Кредитный лимит-->
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

                <!--Процентная ставка-->
                <interestRate>
                    <xsl:call-template name="numberField">
                        <xsl:with-param name="name">interestRate</xsl:with-param>
                        <xsl:with-param name="title">Процентная ставка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="interest-rate"/>
                    </xsl:call-template>
                </interestRate>

                <!--Годовое обслуживание. Первый год.-->
                <firstYearService>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">first-year-service</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание первый год</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="first-year-service"/>
                    </xsl:call-template>
                </firstYearService>

                <!--Годовое обслуживание. Последующие годы.-->
                <nextYearService>
                    <xsl:call-template name="moneyField">
                        <xsl:with-param name="name">next-year-service</xsl:with-param>
                        <xsl:with-param name="title">Годовое обслуживание последующие годы</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="next-year-service"/>
                    </xsl:call-template>
                </nextYearService>

                <surName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">surName</xsl:with-param>
                        <xsl:with-param name="title">Фамилия</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
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
                        <xsl:with-param name="changed" select="false()"/>
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
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="patrName"/>
                    </xsl:call-template>
                </patrName>

                <!--Место получения карты-->
                <office>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">office</xsl:with-param>
                        <xsl:with-param name="title">Место получения карты</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="credit-card-office"/>
                    </xsl:call-template>
                </office>

            </LoanCardOfferDocumentCheck>
        </DocumentCheck>
    </xsl:template>
</xsl:stylesheet>
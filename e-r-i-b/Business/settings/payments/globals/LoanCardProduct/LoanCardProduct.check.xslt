<?xml version="1.0" encoding="windows-1251"?>
<!-- Чек для заявки на кредитную карту -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:ai="java://com.rssl.phizic.common.types.ApplicationInfo"
                extension-element-prefixes="mask au mu mpnu ai">

    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>

	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="webRoot"      select="'webRoot'"/>
    <xsl:param name="mode"         select="'edit'"/>

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
            <form>LoanCardProduct</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>Заявка на кредитную карту</title>
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

            <LoanCardProductDocumentCheck>

                <!--Тип карты-->
                <cardName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">cardName</xsl:with-param>
                        <xsl:with-param name="title">Кредитная карта</xsl:with-param>
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
                        <xsl:with-param name="name">firstYearService</xsl:with-param>
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
                        <xsl:with-param name="name">nextYearService</xsl:with-param>
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
                        <xsl:with-param name="value"><xsl:value-of select="substring(surName, 1, 1)"/>.</xsl:with-param>
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
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="patrName"/>
                    </xsl:call-template>
                </patrName>

                <homePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">homePhone</xsl:with-param>
                        <xsl:with-param name="title">Домашний телефон</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="homePhone"/>
                    </xsl:call-template>
                </homePhone>
                <workPhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">workPhone</xsl:with-param>
                        <xsl:with-param name="title">Рабочий телефон</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="workPhone"/>
                    </xsl:call-template>
                </workPhone>
                <mobilePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">mobilePhone</xsl:with-param>
                        <xsl:with-param name="title">Мобильный телефон</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="mpnu:maskPhone(mobilePhone)"/>
                    </xsl:call-template>
                </mobilePhone>

                <!--Желательное время звонка сотрудника-->
                <freeTime>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">freeTime</xsl:with-param>
                        <xsl:with-param name="title">Желательная дата и время звонка от сотрудника банка</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="freeTime"/>
                    </xsl:call-template>
                </freeTime>
            </LoanCardProductDocumentCheck>
        </DocumentCheck>
    </xsl:template>
</xsl:stylesheet>
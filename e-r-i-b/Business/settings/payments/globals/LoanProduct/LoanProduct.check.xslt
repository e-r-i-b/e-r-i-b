<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:pu="java://com.rssl.phizic.business.persons.PersonHelper"
                              xmlns:xalan = "http://xml.apache.org/xalan"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                              xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                              extension-element-prefixes="pu xalan mask mpnu mu">
    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
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
        <DocumentCheck>
            <form>LoanProduct</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <title>Заявка на кредит</title>
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

            <LoanProductDocumentCheck>

                <creditType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">creditType</xsl:with-param>
                        <xsl:with-param name="title">Наименование кредита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
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
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="amount"/>
                        </xsl:call-template>
                    </amount>
                    <currency>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">currency</xsl:with-param>
                            <xsl:with-param name="title">Валюта</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="changed" select="false()"/>
                            <xsl:with-param name="value" select="currency"/>
                        </xsl:call-template>
                    </currency>
                </sum>

                <duration>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">duration</xsl:with-param>
                        <xsl:with-param name="title">Срок в месяцах</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="duration"/>
                    </xsl:call-template>
                </duration>

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

                <xsl:if test="string-length(patrName)>0">
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
                </xsl:if>

                <mobilePhone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">mobilePhone</xsl:with-param>
                        <xsl:with-param name="title">Мобильный телефон</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
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
                        <xsl:with-param name="changed" select="false()"/>
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
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="averageIncomePerMonth"/>
                    </xsl:call-template>
                </averageIncomePerMonth>

                <getPaidOnAccount>
                    <xsl:call-template name="booleanField">
                        <xsl:with-param name="name">getPaidOnAccount</xsl:with-param>
                        <xsl:with-param name="title">получаю зарплату на карту/счет в Сбербанке</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="changed" select="false()"/>
                        <xsl:with-param name="value" select="getPaidOnAccount"/>
                    </xsl:call-template>
                </getPaidOnAccount>

            </LoanProductDocumentCheck>
        </DocumentCheck>
    </xsl:template>
</xsl:stylesheet>
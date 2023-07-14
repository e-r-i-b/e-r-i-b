<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au mu">

    <xsl:import href="commonFieldTypes.atm.template.xslt"/>
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
            <form>LoyaltyProgramRegistrationClaim</form>
            <LoyaltyProgramRegistrationClaim>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="false()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="value"      select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>

                <xsl:variable name="phoneList" select="document('phones.xml')/entity-list/*"/>
                <phone>
                    <xsl:call-template name="listField">
                        <xsl:with-param name="name">phone</xsl:with-param>
                        <xsl:with-param name="title">Номер телефона</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="true()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="listValues" select="$phoneList"/>
                    </xsl:call-template>
                </phone>

                <email>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">email</xsl:with-param>
                        <xsl:with-param name="title">Электронная почта</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="true()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="value"      select="email"/>
                    </xsl:call-template>
                </email>

                <rulesUrl>
                    <xsl:call-template name="linkField">
                        <xsl:with-param name="name">rulesUrl</xsl:with-param>
                        <xsl:with-param name="title">Ссылка на правила участия в программе</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="false()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="text">Ссылка на правила участия в программе</xsl:with-param>
                        <xsl:with-param name="url">http://sberbank.ru/ru/person/spasibo/rules</xsl:with-param>
                    </xsl:call-template>
                </rulesUrl>
            </LoyaltyProgramRegistrationClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>LoyaltyProgramRegistrationClaim</form>
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

            <LoyaltyProgramRegistrationClaim>
                <documentNumber>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">documentNumber</xsl:with-param>
                        <xsl:with-param name="title">Номер документа</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="false()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="value"      select="documentNumber"/>
                    </xsl:call-template>
                </documentNumber>

                <documentDate>
                    <xsl:call-template name="dateField">
                        <xsl:with-param name="name">documentDate</xsl:with-param>
                        <xsl:with-param name="title">Дата исполнения</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="false()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="value"      select="documentDate"/>
                    </xsl:call-template>
                </documentDate>

                <phone>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">phone</xsl:with-param>
                        <xsl:with-param name="title">Номер телефона</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="false()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="value"      select="phone"/>
                    </xsl:call-template>
                </phone>

                <email>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">email</xsl:with-param>
                        <xsl:with-param name="title">Электронная почта</xsl:with-param>
                        <xsl:with-param name="required"   select="true()"/>
                        <xsl:with-param name="editable"   select="false()"/>
                        <xsl:with-param name="visible"    select="true()"/>
                        <xsl:with-param name="value"      select="email"/>
                    </xsl:call-template>
                </email>
            </LoyaltyProgramRegistrationClaim>
        </document>
    </xsl:template>

    <xsl:template name="listField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="validators" select="''"/>
        <xsl:param name="listValues"/>

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
                            <xsl:for-each select="$listValues">
                                <valueItem>
                                    <value><xsl:value-of select="./@key"/></value>
                                    <title><xsl:value-of select="./field[@name='value']/text()"/></title>
                                    <selected>true</selected>
                                </valueItem>
                            </xsl:for-each>
                        </availableValues>
                    </xsl:if>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
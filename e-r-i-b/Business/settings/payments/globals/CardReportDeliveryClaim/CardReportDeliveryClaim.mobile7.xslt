<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:cu="java://com.rssl.phizic.business.cards.CardsUtil"
                exclude-result-prefixes="xalan mask au cu">
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId"/>
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
            <form>CardReportDeliveryClaim</form>
            <CardReportDeliveryClaim>
                <xsl:call-template name="initialDataEdit"/>
            </CardReportDeliveryClaim>
        </initialData>
    </xsl:template>

    <xsl:template name="initialDataEdit">
        <email>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">email</xsl:with-param>
                <xsl:with-param name="title">Электронный адрес</xsl:with-param>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="email"/>
            </xsl:call-template>
        </email>
        <use>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">use</xsl:with-param>
                <xsl:with-param name="title">Использование подписки</xsl:with-param>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="use"/>
            </xsl:call-template>
        </use>
        <xsl:if test="cu:isShowAdditionalReportDeliveryParameters()">
            <type>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">type</xsl:with-param>
                    <xsl:with-param name="title">Тип подписки</xsl:with-param>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="type"/>
                </xsl:call-template>
            </type>
            <language>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">language</xsl:with-param>
                    <xsl:with-param name="title">Язык подписки</xsl:with-param>
                    <xsl:with-param name="editable" select="true()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="language"/>
                </xsl:call-template>
            </language>
        </xsl:if>
    </xsl:template>



    <xsl:template match="/form-data" mode="view">
        <document>
            <form>CardReportDeliveryClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <CardReportDeliveryClaimDocument>
                <xsl:call-template name="initialDataView"/>
            </CardReportDeliveryClaimDocument>
        </document>
    </xsl:template>

    <xsl:template name="initialDataView">
        <email>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">email</xsl:with-param>
                <xsl:with-param name="title">Электронный адрес</xsl:with-param>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="email"/>
            </xsl:call-template>
        </email>
        <use>
            <xsl:call-template name="booleanField">
                <xsl:with-param name="name">use</xsl:with-param>
                <xsl:with-param name="title">Использование подписки</xsl:with-param>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="use"/>
            </xsl:call-template>
        </use>
        <xsl:if test="cu:isShowAdditionalReportDeliveryParameters()">
            <type>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">type</xsl:with-param>
                    <xsl:with-param name="title">Тип подписки</xsl:with-param>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="type"/>
                </xsl:call-template>
            </type>
            <language>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">language</xsl:with-param>
                    <xsl:with-param name="title">Язык подписки</xsl:with-param>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="language"/>
                </xsl:call-template>
            </language>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
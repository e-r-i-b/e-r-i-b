<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                exclude-result-prefixes="xalan mask au">
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
            <form>BlockingCardClaim</form>
            <BlockingCardClaim>
                <xsl:call-template name="initialData"/>
            </BlockingCardClaim>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:variable name="cards" select="document('stored-active-not-virtual-cards-with-owner.xml')/entity-list/entity"/>
        <xsl:variable name="blockinCardClaimReasons" select="document('blocking-card-claim-reasons.xml')/entity-list/entity"/>

        <card>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name">card</xsl:with-param>
                <xsl:with-param name="title">Карта</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="listValues" select="$cards"/>
                <xsl:with-param name="linkId" select="card"/>
                <xsl:with-param name="isView" select="false()"/>
            </xsl:call-template>
        </card>
        <reason>
            <xsl:call-template name="listField">
                <xsl:with-param name="name">reason</xsl:with-param>
                <xsl:with-param name="title">Причина блокировки</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="listValues" select="$blockinCardClaimReasons"/>
                <xsl:with-param name="value" select="reason"/>
            </xsl:call-template>
        </reason>
        <fullName>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">fullName</xsl:with-param>
                <xsl:with-param name="title">ФИО владельца</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="fullName"/>
            </xsl:call-template>
        </fullName>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="cards" select="document('stored-not-virtual-cards-with-owner.xml')/entity-list/entity"/>
        <xsl:variable name="blockinCardClaimReasons" select="document('blocking-card-claim-reasons.xml')/entity-list/entity"/>

        <document>
            <form>BlockingCardClaim</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <id><xsl:value-of select="$documentId"/></id>
            <BlockingCardClaimDocument>
                <card>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">card</xsl:with-param>
                        <xsl:with-param name="title">Карта</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="listValues" select="$cards"/>
                        <xsl:with-param name="linkId" select="cardLink"/>
                        <xsl:with-param name="isView" select="true()"/>
                    </xsl:call-template>
                </card>
                <reason>
                    <xsl:call-template name="listField">
                        <xsl:with-param name="name">reason</xsl:with-param>
                        <xsl:with-param name="title">Причина блокировки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="listValues" select="$blockinCardClaimReasons"/>
                        <xsl:with-param name="value" select="reason"/>
                    </xsl:call-template>
                </reason>
                <fullName>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">fullName</xsl:with-param>
                        <xsl:with-param name="title">ФИО владельца</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="fullName"/>
                    </xsl:call-template>
                </fullName>
            </BlockingCardClaimDocument>
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
        <xsl:param name="listValues"/>
        <xsl:param name="linkId"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="isView"/> <!--false=редактирование; true=просмотр-->

        <name><xsl:value-of select="$name"/></name>
        <title><xsl:value-of select="$title"/></title>
        <xsl:if test="string-length($description)>0">
            <description><xsl:value-of select="$description"/></description>
        </xsl:if>
        <xsl:if test="string-length($hint)>0">
            <hint><xsl:value-of select="$hint"/></hint>
        </xsl:if>
        <type>resource</type>
        <required><xsl:value-of select="$required"/></required>
        <editable><xsl:value-of select="$editable"/></editable>
        <visible><xsl:value-of select="$visible"/></visible>
        <resourceType>
            <xsl:if test="count($listValues)>0">
                <availableValues>
                    <xsl:for-each select="$listValues">
                        <xsl:variable name="code" select="field[@name='code']/text()"/>
                        <xsl:variable name="isSelected" select="$accountNumber= ./@key or $linkId=$code"/>
                        <xsl:if test="not($isView) or ($isView and $isSelected)">
                            <valueItem>
                                <value><xsl:value-of select="$code"/></value>
                                <selected><xsl:value-of select="string($isSelected)"/></selected>
                                <displayedValue>
                                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                </displayedValue>
                            </valueItem>
                        </xsl:if>
                    </xsl:for-each>
                </availableValues>
            </xsl:if>
        </resourceType>
        <changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
    </xsl:template>

</xsl:stylesheet>
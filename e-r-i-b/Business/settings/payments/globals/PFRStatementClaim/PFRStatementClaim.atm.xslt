<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                exclude-result-prefixes="xalan mask au mu ph">

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
            <form>PFRStatementClaim</form>
            <PFRStatementClaim>
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

                <xsl:choose>
                    <xsl:when test="claimSendMethod = 'USING_PASPORT_WAY'">
                        <passport>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">passport</xsl:with-param>
                                <xsl:with-param name="title">Номер паспорта клиента</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="ph:getCutDocumentSeries(concat(personDocSeries,' ', personDocNumber))"/>
                            </xsl:call-template>
                        </passport>
                    </xsl:when>
                    <xsl:when test="claimSendMethod = 'USING_SNILS'">
                        <SNILS>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">SNILS</xsl:with-param>
                                <xsl:with-param name="title">Страховой Номер Индивидуального Лицевого Счёта</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="true()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="SNILS"/>
                            </xsl:call-template>
                        </SNILS>
                    </xsl:when>
                </xsl:choose>

                <claimSendMethod>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">claimSendMethod</xsl:with-param>
                        <xsl:with-param name="title">способ отправки заявки</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="false()"/>
                        <xsl:with-param name="value" select="claimSendMethod"/>
                    </xsl:call-template>
                </claimSendMethod>
            </PFRStatementClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>PFRStatementClaim</form>
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

            <PFRStatementClaim>
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


                <xsl:choose>
                    <xsl:when test="claimSendMethod = 'USING_PASPORT_WAY'">
                        <passport>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">passport</xsl:with-param>
                                <xsl:with-param name="title">Номер паспорта клиента</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="ph:getCutDocumentSeries(concat(personDocSeries,' ', personDocNumber))"/>
                            </xsl:call-template>
                        </passport>
                    </xsl:when>
                    <xsl:when test="claimSendMethod = 'USING_SNILS'">
                        <SNILS>
                            <xsl:call-template name="stringField">
                                <xsl:with-param name="name">SNILS</xsl:with-param>
                                <xsl:with-param name="title">Страховой Номер Индивидуального Лицевого Счёта</xsl:with-param>
                                <xsl:with-param name="required" select="true()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="SNILS"/>
                            </xsl:call-template>
                        </SNILS>
                    </xsl:when>
                </xsl:choose>

            </PFRStatementClaim>
        </document>
    </xsl:template>
</xsl:stylesheet>
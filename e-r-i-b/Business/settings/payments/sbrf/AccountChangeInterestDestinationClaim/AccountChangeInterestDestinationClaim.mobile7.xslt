<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:c="http://www.w3.org/1999/XSL/Transform"
                exclude-result-prefixes="xalan mask c">
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
            <form>AccountChangeInterestDestinationClaim</form>
            <AccountChangeInterestDestinationClaim>
                <xsl:call-template name="initialData"/>
            </AccountChangeInterestDestinationClaim>
        </initialData>
    </xsl:template>

    <xsl:template name="initialData">
        <xsl:variable name="activeWithTBLoginedCards" select="document(concat('active-with-tblogined-without-interest-cards.xml?accountId=', accountCode))/entity-list/*"/>

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

        <accountId>
            <xsl:call-template name="stringField">
                <xsl:with-param name="name">accountCode</xsl:with-param>
                <xsl:with-param name="title">Идентификатор счета, для которого изменяется порядок уплаты процентов</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="value" select="accountCode"/>
            </xsl:call-template>
        </accountId>

        <percentTransferSource>
            <xsl:call-template name="percentTransferSourceTypeField">
                <xsl:with-param name="name">interestDestinationSource</xsl:with-param>
                <xsl:with-param name="title">Вариант перечисления процентов</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="cardsForPercent" select="$activeWithTBLoginedCards"/>
            </xsl:call-template>
        </percentTransferSource>

        <percentTransferCardSource>
            <xsl:call-template name="interestCardField">
                <xsl:with-param name="name">cardLink</xsl:with-param>
                <xsl:with-param name="title">Номер карты для перечисления процентов</xsl:with-param>
                <xsl:with-param name="required" select="false()"/>
                <xsl:with-param name="editable" select="true()"/>
                <xsl:with-param name="visible" select="true()"/>
                <xsl:with-param name="cardsForPercent" select="$activeWithTBLoginedCards"/>
            </xsl:call-template>
        </percentTransferCardSource>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>AccountChangeInterestDestinationClaim</form>

            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>

            <AccountChangeInterestDestinationClaimDocument>

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

                <percentTransferSource>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">interestDestinationSource</xsl:with-param>
                        <xsl:with-param name="title">Вариант перечисления процентов</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="interestDestinationSource"/>
                    </xsl:call-template>
                </percentTransferSource>

                <xsl:if test="interestDestinationSource='card' and string-length(cardLink) > 0">
                    <xsl:variable name="cardNumberDisplayedValue">
                        <xsl:value-of select="mask:getCutCardNumber(interestCardNumber)"/>
                    </xsl:variable>
                    <percentTransferCardSource>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">interestCardNumber</xsl:with-param>
                            <xsl:with-param name="title">Номер карты для перечисления процентов</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="$cardNumberDisplayedValue"/>
                        </xsl:call-template>
                    </percentTransferCardSource>
                </xsl:if>
            </AccountChangeInterestDestinationClaimDocument>
        </document>

    </xsl:template>

    <xsl:template name="percentTransferSourceTypeField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cardsForPercent"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <listType>
                    <availableValues>
                        <valueItem>
                            <value>account</value>
                            <title>account</title>
                            <selected>true</selected>
                        </valueItem>
                        <!--Есть ли карты для перечисления процентов-->
                        <xsl:if test="count($cardsForPercent)>0">
                             <valueItem>
                                 <value>card</value>
                                 <title>card</title>
                                 <selected>false</selected>
                             </valueItem>
                         </xsl:if>
                    </availableValues>
                </listType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="interestCardField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cardsForPercent"/>
        <xsl:variable name="value">
            <listType>
                <availableValues>
                    <xsl:choose>
                        <xsl:when test="count($cardsForPercent) = 0">
                            <valueItem>
                                <value>""</value>
                                <title>Нет доступных карт</title>
                                <selected>true</selected>
                            </valueItem>
                        </xsl:when>
                        <xsl:otherwise>
                            <valueItem>
                                <value>""</value>
                                <title>Выберите карту</title>
                                <selected>true</selected>
                            </valueItem>

                            <xsl:for-each select="$cardsForPercent">
                                <xsl:if test="./field[@name='isMain'] = 'true' or ./field[@name='additionalCardType'] = 'CLIENTTOCLIENT'">
                                    <valueItem>
                                        <value><xsl:value-of select="field[@name='code']/text()"/></value>
                                        <title><xsl:value-of select="mask:getCutCardNumber(./@key)"/></title>
                                        <selected>false</selected>
                                    </valueItem>
                                </xsl:if>
                            </xsl:for-each>
                        </xsl:otherwise>
                    </xsl:choose>
                </availableValues>
            </listType>
        </xsl:variable>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'list'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag" select="$value"/>
        </xsl:call-template>
    </xsl:template>

</xsl:stylesheet>
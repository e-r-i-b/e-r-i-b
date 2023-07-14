<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
        >
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="documentId"/>
    <xsl:param name="documentStatus"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="mobileApiVersion"/>
    <xsl:param name="changedFields"/>

    <xsl:param name="resourceRoot" select="'resourceRoot'"/>
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
            <form>ChangeCreditLimitClaim</form>
            <ChangeCreditLimitClaim>
                <offerId>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">offerId</xsl:with-param>
                        <xsl:with-param name="title">offerId</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="offerId"/>
                    </xsl:call-template>
                </offerId>

                <cardId>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">card-id</xsl:with-param>
                        <xsl:with-param name="title">card-id</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="card-id"/>
                    </xsl:call-template>
                </cardId>

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

                <currentLimit>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currentLimit</xsl:with-param>
                        <xsl:with-param name="title">Текущий лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="currentLimit"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </currentLimit>

                <currentLimitCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currentLimitCurrency</xsl:with-param>
                        <xsl:with-param name="title">Текущая валюта лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="mu:getCurrencySign(currentLimitCurrency)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </currentLimitCurrency>

                <newLimit>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">newLimit</xsl:with-param>
                        <xsl:with-param name="title">Новый лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="newLimit"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </newLimit>

                <newLimitCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">newLimitCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта нового лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="mu:getCurrencySign(newLimitCurrency)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </newLimitCurrency>

                <cardInfo>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">cardInfo</xsl:with-param>
                        <xsl:with-param name="title">Карта, для изменения лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="cardName"/> <xsl:value-of select="mu:getCurrencySign(cardCurrency)"/>, <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </cardInfo>

                <feedbackType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">feedbackType</xsl:with-param>
                        <xsl:with-param name="title">Тип отклика</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value"/>
                    </xsl:call-template>
                </feedbackType>
            </ChangeCreditLimitClaim>
        </initialData>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>ChangeCreditLimitClaim</form>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <status><xsl:value-of select="state"/></status>
            <ChangeCreditLimitClaim>
                <offerId>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">offerId</xsl:with-param>
                        <xsl:with-param name="title">offerId</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="offerId"/>
                    </xsl:call-template>
                </offerId>

                <cardId>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">card-id</xsl:with-param>
                        <xsl:with-param name="title">card-id</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="card-id"/>
                    </xsl:call-template>
                </cardId>

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

                <currentLimit>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currentLimit</xsl:with-param>
                        <xsl:with-param name="title">Текущий лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="currentLimit"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </currentLimit>

                <currentLimitCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">currentLimitCurrency</xsl:with-param>
                        <xsl:with-param name="title">Текущая валюта лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="mu:getCurrencySign(currentLimitCurrency)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </currentLimitCurrency>

                <newLimit>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">newLimit</xsl:with-param>
                        <xsl:with-param name="title">Новый лимит</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="newLimit"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </newLimit>

                <newLimitCurrency>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">newLimitCurrency</xsl:with-param>
                        <xsl:with-param name="title">Валюта нового лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="mu:getCurrencySign(newLimitCurrency)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </newLimitCurrency>

                <cardInfo>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">cardInfo</xsl:with-param>
                        <xsl:with-param name="title">Карта, для изменения лимита</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value">
                            <xsl:value-of select="cardName"/> <xsl:value-of select="mu:getCurrencySign(cardCurrency)"/>, <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </cardInfo>

                <feedbackType>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">feedbackType</xsl:with-param>
                        <xsl:with-param name="title">Тип отклика</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="true()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value"/>
                    </xsl:call-template>
                </feedbackType>
            </ChangeCreditLimitClaim>
        </document>
    </xsl:template>
</xsl:stylesheet>
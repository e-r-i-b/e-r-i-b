<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="mode" select="'view'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	 <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">

        <input id="documentNumber" name="documentNumber" type="hidden"/>
        <style type="text/css">
            td {line-height: 16pt;}
            table {border-collapse: separate!important;}
            .underlineTableBlock {border-bottom-width: 1px; border-bottom-color: #000000; border-bottom-style: solid;}
            .smallText {font-family: Times; font-size: 9pt; vartical-align: top;}
            .textF{font-family: Times; font-size: 12pt; font-style: italic;}
            .centring {text-align: center;}
            .width2mm {max-width: 2mm; width: 2mm;}
            .width4mm {max-width: 4mm; width: 4mm;}
            .width7mm {max-width: 7mm; width: 7mm;}
            .width9mm {max-width: 9mm; width: 9mm;}
            .width10mm {max-width: 10mm; width: 10mm;}
            .width11mm {max-width: 11mm; width: 11mm;}
            .width13mm {max-width: 13mm; width: 13mm;}
            .width15mm {max-width: 15mm; width: 15mm;}
            .width18mm {max-width: 18mm; width: 18mm;}
            .width3cm {width: 30mm;}
            .bold{font-weight: bold; font-family: Times; font-size: 12pt;}
            .bigText {font-size: big;}
            .uppercase {text-transform: uppercase;}
        </style>

        <table cellpadding="0" cellspacing="0">
            <tr>
                <td style="width:40%;">Карта</td>
                <td><xsl:value-of select="cardName"/>&nbsp;<xsl:value-of select="mask:getCutCardNumber(cardNumber)"/></td>
            </tr>
            <tr>
                <td>Валюта</td>
                <td>
                    <xsl:value-of select="mu:getCurrencySign(cardCurrency)"/>
                </td>
            </tr>
            <tr>
                <td>Действующий кредитный лимит</td>
                <td>
                    <xsl:value-of select="currentLimit"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(currentLimitCurrency)"/>
                </td>
            </tr>
            <tr>
                <td>Новый кредитный лимит</td>
                <td>
                    <xsl:value-of select="newLimit"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(newLimitCurrency)"/>
                </td>
            </tr>

            <tr>
                <td>Статус заявки</td>
                <td>
                    <xsl:call-template name="state2text">
                        <xsl:with-param name="code">
                            <xsl:value-of select="state"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </td>
            </tr>
        </table>
	</xsl:template>

    <xsl:template name="stamp">
        <xsl:param name="state"/>
        <xsl:if test="$state = 'EXECUTED' or $state='REFUSED'">
            <br/>
            <div id="stamp" class="forBankStamp" style="position: relative; top : 0; right: 0;">
                <xsl:variable name="imagesPath" select="concat($resourceRoot, '/commonSkin/images/transaction_status/')"/>
                <span class="stampTitle"><xsl:value-of select="bn:getBankNameForPrintCheck()"/></span>
                <div style="text-align:center;">
                    <img src="{concat($imagesPath, 'stampExecuted_noBorder.gif')}" alt=""/>
                </div>
            </div>
        </xsl:if>
    </xsl:template>
     <xsl:template name="state2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='EXECUTED'">Принято</xsl:when>
            <xsl:when test="$code='REFUSED'">Не принято</xsl:when>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
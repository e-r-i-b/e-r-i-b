<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:cu="java://com.rssl.phizic.utils.CurrencyUtils"
                xmlns:imau="java://com.rssl.phizic.business.ima.IMAccountUtils"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                extension-element-prefixes="phizic">
    <xsl:import href="commonFieldTypes.template.xslt"/>
    <xsl:output method="html" version="1.0" indent="yes"/>

    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="postConfirmCommission" select="postConfirmCommission"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

     <xsl:template match="/form-data" mode="printCheck">
         <xsl:variable name="fromResourceType" select="fromResourceType"/>
         <xsl:variable name="toResourceType" select="toResourceType"/>
         <xsl:variable name="isBuy" select="contains(toResourceType, 'IMA')"/>

         <xsl:choose>
             <xsl:when test="$isBuy">
                 <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">Покупка обезличенного металла</xsl:with-param>
                </xsl:call-template>
             </xsl:when>
             <xsl:otherwise>
                 <xsl:call-template name="printTitle">
                    <xsl:with-param name="value">Продажа обезличенного металла</xsl:with-param>
                </xsl:call-template>
             </xsl:otherwise>
         </xsl:choose>

         <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="operationDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">идентификатор операции</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of select="documentNumber"/></xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:call-template name="printText">
            <xsl:with-param name="value">Отправитель:</xsl:with-param>
        </xsl:call-template>
        
        <xsl:choose>
            <xsl:when test="$isBuy">
                <xsl:if test="contains($fromResourceType, 'Account')">
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value"><xsl:value-of  select="fromAccountType"/>,</xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value"><xsl:value-of  select="fromAccountSelect"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="contains($fromResourceType, 'Card')">
                    <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">№ карты</xsl:with-param>
                        <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value">
                        <xsl:value-of select="fromAccountName"/>
                        (<xsl:value-of select="cu:normalizeCurrencyCode(fromResourceCurrency)"/>),
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of select="imau:getFormattedIMAccountNumber(fromAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
        <br/>
        <xsl:call-template name="printText">
            <xsl:with-param name="value">Получатель:</xsl:with-param>
        </xsl:call-template>

        <xsl:choose>
            <xsl:when test="$isBuy">
                <xsl:call-template name="printText">
                    <xsl:with-param name="value">
                        <xsl:value-of select="toAccountName"/>
                        (<xsl:value-of select="cu:normalizeCurrencyCode(toResourceCurrency)"/>),
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of select="imau:getFormattedIMAccountNumber(toAccountSelect)"/></xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:if test="contains($toResourceType, 'Account')">
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value"><xsl:value-of  select="toAccountType"/>,</xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="printText">
                        <xsl:with-param name="value"><xsl:value-of  select="toAccountSelect"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="contains($toResourceType, 'Card')">
                    <xsl:variable name="toAccountSelect"><xsl:value-of  select="toAccountSelect"/></xsl:variable>
                    <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">№ карты</xsl:with-param>
                        <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($toAccountSelect)"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:otherwise>
        </xsl:choose>
        <br/>

         <xsl:call-template name="printText">
            <xsl:with-param name="value">
                <table>
                    <tr>
                        <td valign="top">Сумма</td>
                        <td align="right">
                            <xsl:choose>
                               <xsl:when test="$isBuy">
                                    <xsl:value-of select="sellAmount"/>&nbsp;
                                    <xsl:value-of select="sellAmountCurrency"/>
                               </xsl:when>
                               <xsl:otherwise>
                                   <xsl:value-of select="buyAmount"/>
                                    &nbsp;
                                   <xsl:value-of select="buyAmountCurrency"/>
                               </xsl:otherwise>
                            </xsl:choose>
                        </td>
                     </tr>
                    <tr>
                        <td valign="top">Масса</td>
                        <td align="right">
                            <xsl:choose>
                               <xsl:when test="$isBuy">
                                   <xsl:value-of select="format-number(buyAmount, '### ##0.0')"/>
                               &nbsp;
                               <xsl:value-of select="cu:normalizeCurrencyCode(buyAmountCurrency)"/>
                               </xsl:when>
                               <xsl:otherwise>
                                   <xsl:value-of select="format-number(sellAmount, '### ##0.0')"/>&nbsp;
                                   <xsl:value-of select="cu:normalizeCurrencyCode(sellAmountCurrency)"/>
                               </xsl:otherwise>
                            </xsl:choose>
                        </td>
                    </tr>
                </table>
            </xsl:with-param>
        </xsl:call-template>

         <table>
            <tr><td>
                 <xsl:if test="$postConfirmCommission">
                     <xsl:call-template name="writeDownOperationsCheck">
                         <xsl:with-param name="fromResourceCurrency" select="sellAmountCurrency"/>
                     </xsl:call-template>
                 </xsl:if>
            </td></tr>
         </table>

     </xsl:template>



    <xsl:template name="printTitle">
        <xsl:param name="value"/>
        <div class="checkSize title"><xsl:copy-of select="$value"/></div>
        <br/>
    </xsl:template>

     <xsl:template name="printText">
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$value"/></div>
    </xsl:template>

     <xsl:template name="paramsCheck">
        <xsl:param name="title"/>
        <xsl:param name="value"/>
        <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
    </xsl:template>

</xsl:stylesheet>

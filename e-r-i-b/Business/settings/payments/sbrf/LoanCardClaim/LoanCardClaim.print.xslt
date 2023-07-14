<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'edit'"/>


    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
 		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
        <style type="text/css">
            .loan_print_form {
                font-family: Times;
                font-size: 12pt;
            }
            .loan_print_form h2, .loan_print_form h3 {
                text-align:center;
            }
            td:first-child {
                max-width: 126px;
                word-wrap: break-word;
            }
            span.word-wrap {
                display: inline-block;
                max-width: 100px;
                word-wrap: break-word;
            }
        </style>
        <xsl:variable name="currency" select="mu:getCurrencySign(currency)"/>
        <table class="loan_print_form bottomStamp">
            <tr>
                <td colspan="2"><xsl:value-of select="bn:getBankNameForPrintCheck()"/></td>
            </tr>
            <tr>
                <td class="alignRight" colspan="2">Дата&nbsp;<xsl:value-of  select="documentDate"/></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h2 class="loan_print_form">Заявка на получение кредитной карты</h2></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">Данные по кредитной карте</h3></td>
            </tr>
            <tr>
                <td>Кредитная карта:</td>
                <td><span class="word-wrap"><xsl:value-of  select="creditCard"/></span></td>
            </tr>
             <tr>
                <td>Кредитный лимит:</td>
                <td><xsl:value-of select="mu:formatAmountWithNoCents(string(amount))"/>&nbsp;<xsl:value-of select="$currency"/></td>
            </tr>
            <tr>
                <td>Процентная ставка:</td>
                <td><xsl:value-of  select="interest-rate"/>%</td>
            </tr>
            <tr>
                <td>Годовое обслуживание (Первый/последующие годы):</td>
                <td>
                    <xsl:value-of select="mu:formatAmountWithNoCents(string(first-year-service))"/>&nbsp;<xsl:value-of select="$currency"/> /
                    <xsl:value-of select="mu:formatAmountWithNoCents(string(next-year-service))"/>&nbsp;<xsl:value-of select="$currency"/>
                </td>
            </tr>

            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">Дополнительная информация</h3></td>
            </tr>
            <tr>
                <td>ФИО получателя:</td>
                <td><span class="word-wrap"><xsl:value-of  select="ph:getFormattedPersonName()"/></span></td>
            </tr>

            <xsl:variable name="isGuest" select="ph:isGuest()"/>
            <tr>
                <td>Мобильный телефон:</td>
                <td>
                    <xsl:choose>
                        <xsl:when test="$isGuest">
                            <xsl:value-of select="mask:maskPhoneNumber(ownerGuestPhone)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="mask:maskPhoneNumber(mobilePhone)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </td>
            </tr>

            <tr>
                <td>Электронная почта:</td>
                <td><span class="word-wrap"><xsl:value-of select="email"/></span></td>
            </tr>

            <tr>
                <td>Удобное время для звонка:</td>
                <td><xsl:value-of select="freeTime"/></td>
            </tr>

            <xsl:variable name="preapproved" select="preapproved = 'true'"/>
            <xsl:if test="$preapproved">
                <tr>
                    <td>Где я хочу получить карту:</td>
                    <td><xsl:value-of  select="credit-card-office"/></td>
                </tr>
            </xsl:if>
        </table>
    </xsl:template>
</xsl:stylesheet>
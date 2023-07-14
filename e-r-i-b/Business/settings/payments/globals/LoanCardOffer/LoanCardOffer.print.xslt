<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="mode" select="'view'"/>


    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
 		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <table class="loan_print_form">
            <tr>
                <td><xsl:value-of select="bn:getBankNameForPrintCheck()"/></td>
                <td></td>
            </tr>
            <tr>
                <td><xsl:value-of  select="document('currentPerson.xml')//field[@name='office-name']"/></td>
                <td class="loan_print_form_right">Дата&nbsp;<xsl:value-of  select="documentDate"/></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"> <h2 class="loan_print_form">Заявка на получение кредитной карты</h2></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">Данные по кредитной карте</h3></td>
            </tr>
            <tr>
                <td>Кредитная карта на специальных условиях</td>
                <td><xsl:value-of  select="creditCard"/></td>
            </tr>
            <tr>
                <td>Кредитный лимит</td>
                <td><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of  select="mu:getCurrencySign(currency)"/></td>
            </tr>

            <xsl:if test="offerType != 2">
            <tr>
                <td>Процентная ставка</td>
                <td>
                    <xsl:value-of  select="interestRate"/>
                    <xsl:if test="interestRate != ''">
                        %
                    </xsl:if>
                </td>
            </tr>
            <tr>

            <td>Годовое обслуживание (Первый/последующие годы)</td>
                <td>
                    <xsl:value-of  select="first-year-service"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(first-year-service-currency)"/>
                    <xsl:if test="mu:getCurrencySign(first-year-service-currency) != '' or
                                mu:getCurrencySign(next-year-service-currency) != ''">
                        /
                    </xsl:if>
                    <xsl:value-of  select="next-year-service"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(next-year-service-currency)"/>
                </td>
            </tr>
            </xsl:if>
            
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">Данные заявителя</h3></td>
            </tr>
            <tr>
                <td>Фамилия</td>
                <td><xsl:value-of  select="substring(surName, 1, 1)"/>.</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td><xsl:value-of  select="firstName"/></td>
            </tr>
            <tr>
                <td>Отчество</td>
                <td><xsl:value-of  select="patrName"/></td>
            </tr>

            <xsl:if test="string-length(credit-card-office)>0">
                <tr>
                    <td>Место получения карты</td>
                    <td><xsl:value-of select="credit-card-office"/></td>
                </tr>
            </xsl:if>
            <xsl:if test="offerType != '2'">
                <tr>
                    <td>Срок получения карты</td>
                    <td>В течение  5- 10 рабочих дней</td>
                </tr>
            </xsl:if>
        </table>

    </xsl:template>

</xsl:stylesheet>
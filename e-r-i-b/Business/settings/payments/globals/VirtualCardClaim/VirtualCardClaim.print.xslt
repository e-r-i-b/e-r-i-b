<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
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
                <td>&nbsp;</td>
                <td class="loan_print_form_right"><xsl:value-of  select="documentDate"/></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h2 class="loan_print_form">Заявление  на получение виртуальной карты Сбербанка России</h2></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">Данные по виртуальной карте</h3></td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Вид карты</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of  select="сardProduct"/></td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Валюта счета карты</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of select="mu:getCurrencySign(currencyNameCardProduct)"/></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">Данные заявителя</h3></td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Фамилия</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of  select="substring(surName, 1, 1)"/>.</td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Имя</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of  select="firstName"/></td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Отчество</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of  select="patrName"/></td>
            </tr>

            <tr>
                <td class="virtual_cards_print_form_left">Мобильный телефон</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of  select="mobilePhone"/></td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Оператор сотовой связи</td>
                <td class="virtual_cards_print_form_right_text"><xsl:value-of  select="mobileOperator"/></td>
            </tr>
            <tr>
                <td class="virtual_cards_print_form_left">Тариф мобильного банка</td>
                <td class="virtual_cards_print_form_right_text">
                    <xsl:if test="mobileTariff = '1'">
					    Экономный пакет
				    </xsl:if>
				    <xsl:if test="mobileTariff = '2'">
					    Полный пакет
				    </xsl:if>
                </td>
            </tr>
        </table>

    </xsl:template>

</xsl:stylesheet>
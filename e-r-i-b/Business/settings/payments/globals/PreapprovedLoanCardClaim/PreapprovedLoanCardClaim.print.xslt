<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
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
        <xsl:variable name="currency" select="mu:getCurrencySign(currency)"/>
        <table class="loan_print_form bottomStamp">
            <tr>
                <td><xsl:value-of select="bn:getBankNameForPrintCheck()"/></td>
                <td></td>
            </tr>
            <tr>
                <td><xsl:value-of  select="document('currentPerson.xml')//field[@name='office-name']"/></td>
                <td class="alignRight">����&nbsp;<xsl:value-of  select="documentDate"/></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"> <h2 class="loan_print_form">������ �� ��������� ��������� �����</h2></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">������ �� ��������� �����</h3></td>
            </tr>
            <tr>
                <td>��������� �����:</td>
                <td><xsl:value-of  select="creditCard"/></td>
            </tr>
             <tr>
                <td>��������� �����:</td>
                <td><xsl:value-of select="mu:formatAmountWithNoCents(string(amount))"/>&nbsp;<xsl:value-of select="$currency"/></td>
            </tr>
            <tr>
                <td>���������� ������:</td>
                <td><xsl:value-of  select="interest-rate"/>%</td>
            </tr>
            <tr>
                <td>������� ������������ (������/����������� ����):</td>
                <td>
                    <xsl:value-of select="mu:formatAmountWithNoCents(string(first-year-service))"/>&nbsp;<xsl:value-of select="$currency"/> /
                    <xsl:value-of select="mu:formatAmountWithNoCents(string(next-year-service))"/>&nbsp;<xsl:value-of select="$currency"/>
                </td>
            </tr>

            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">�������������� ����������</h3></td>
            </tr>
            <tr>
                <td>��� ����������:</td>
                <td><xsl:value-of  select="ph:getFormattedPersonName()"/></td>
            </tr>

            <tr>
                <td>����� ��������� �����:</td>
                <td><xsl:value-of select="credit-card-office"/></td>
            </tr>
        </table>

    </xsl:template>

</xsl:stylesheet>
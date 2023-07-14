<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
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
                <td><xsl:value-of select="bn:getBankNameForPrintCheck()"/></td>
                <td></td>
            </tr>
            <tr>
                <td><xsl:value-of  select="document('currentPerson.xml')//field[@name='office-name']"/></td>
                <td class="loan_print_form_right">����&nbsp;<xsl:value-of  select="documentDate"/></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"> <h2 class="loan_print_form">������ �� ��������� �������</h2></td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">������ �� �������</h3></td>
            </tr>
            <tr>
                <td>������������ �������</td>
                <td><xsl:value-of  select="creditType"/></td>
            </tr>
            <tr>
                <td>����� �������</td>
                <td><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(currency)"/></td>
            </tr>
            <tr>
                <td>����</td>
                <td><xsl:value-of  select="duration"/> ���.</td>
            </tr>
            <tr>
                <td class="loan_print_2td" colspan="2"><h3 class="loan_print_form">������ ���������</h3></td>
            </tr>
            <tr>
                <td>�������</td>
                <td><xsl:value-of  select="substring(surName, 1, 1)"/>.</td>
            </tr>
            <tr>
                <td>���</td>
                <td><xsl:value-of  select="firstName"/></td>
            </tr>
            <tr>
                <td>��������</td>
                <td><xsl:value-of  select="patrName"/></td>
            </tr>

      		<tr>
                <td>��������� �������</td>
                <td><xsl:value-of  select="mobilePhone"/></td>
            </tr>
            <tr>
                <td>������������</td>
                <td><xsl:value-of  select="hirer"/></td>
            </tr>
            <tr>
                <td>������� ����� � �����</td>
                <td><xsl:value-of  select="averageIncomePerMonth"/></td>
            </tr>
            <tr>
                <td>������� �������� �� �����/���� � ���������</td>
                <td>
                    <xsl:if test="getPaidOnAccount='true'">��</xsl:if>
                    <xsl:if test="getPaidOnAccount='false'">���</xsl:if>
                </td>
            </tr>
        </table>

    </xsl:template>

</xsl:stylesheet>
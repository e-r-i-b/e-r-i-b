<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:param name="mode" select="'initial'"/>

    <xsl:template match="/">
        <xsl:choose>
			<xsl:when test="$mode = 'update'">
				<xsl:apply-templates mode="update"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="initial"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="initial">
        <document>
            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <receiver-account>
                <xsl:value-of select="receiverAccount"/>
            </receiver-account>

            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>

            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <exact-amount>
                <xsl:value-of select="'destination-field-exact'"/>
            </exact-amount>

            <extra-parameters>
                <parameter name="subscription-autopay-id"       value="{subscriptionAutopayId}"     type="string"/>
                <parameter name="invoice-id"                    value="{invoiceId}"                 type="long"/>
                <parameter name="invoice-autopay-id"            value="{invoiceAutopayId}"          type="string"/>
                <parameter name="invoice-status"                value="{invoiceStatus}"             type="string"/>
                <parameter name="subscription-name"             value="{subscriptionName}"          type="string"/>
                <parameter name="nameService"                   value="{nameService}"               type="string"/>
                <parameter name="receiver-inn"                  value="{receiverINN}"               type="string"/>
                <parameter name="receiver-bic"                  value="{receiverBIC}"               type="string"/>
                <parameter name="receiver-bank"                 value="{receiverBankName}"          type="string"/>
                <parameter name="receiver-cor-account"          value="{receiverCorAccount}"        type="string"/>
                <parameter name="from-account-name"             value="{fromAccountName}"           type="string"/>
                <parameter name="from-resource-currency"        value="{fromResourceCurrency}"      type="string"/>
                <parameter name="from-resource-rest"            value="{fromResourceRest}"          type="string"/>
                <parameter name="from-resource-type"            value="{fromResourceType}"          type="string"/>
            </extra-parameters>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="update">
        <document>
            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>
            <exact-amount>
                <xsl:value-of select="'destination-field-exact'"/>
            </exact-amount>
            <extra-parameters>
                <parameter name="from-account-name"             value="{fromAccountName}"           type="string"/>
                <parameter name="from-resource-currency"        value="{fromResourceCurrency}"      type="string"/>
                <parameter name="from-resource-rest"            value="{fromResourceRest}"          type="string"/>
                <parameter name="from-resource-type"            value="{fromResourceType}"          type="string"/>
            </extra-parameters>
        </document>
    </xsl:template>

</xsl:stylesheet>
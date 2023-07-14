<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>
            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>

			<destination-amount-currency>
                <xsl:value-of select="currency"/>
            </destination-amount-currency>

			<destination-amount>
                <xsl:value-of select="amount"/>
            </destination-amount>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<receiver-account>
				<xsl:value-of select="receiverAccount"/>
			</receiver-account>

            <exact-amount>
                <xsl:text>destination-field-exact</xsl:text>
            </exact-amount>

            <receiverId>
                <xsl:value-of select="receiverId"/>
            </receiverId>

            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <extra-parameters>
                <parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
                <parameter name="from-account-type" value="{fromAccountType}" type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}" type="string"/>
                <parameter name="from-resource" value="{fromResource}" type="string"/>
                <parameter name="from-resource-currency" value="{fromResourceCurrency}" type="string"/>
                <parameter name="AUTHORIZE_CODE" value="{authorizeCode}" type="string"/>
                <parameter name="receiver-phone-number" value="{phoneNumber}" type="string"/>
                <parameter name="name-on-bill"          value="{nameOnBill}"  type="string"/>
                <parameter name="print-desc" value="{printDesc}" type="string"/>
                <parameter name="is-einvoicing" value="{isEinvoicing}" type="boolean"/>
                <parameter name="receiver-bic"          value="{receiverBankCode}"          type="string"/>
                <parameter name="receiver-bank"         value="{receiverBankName}"          type="string"/>
                <parameter name="receiver-cor-account"  value="{receiverCorAccount}"        type="string"/>
                <xsl:apply-templates select="/form-data/*[name() != '']"/>
            </extra-parameters>
        </document>

    </xsl:template>

        <!-- доп поля -->
    <xsl:template match="/form-data/*" priority="-99">
		<parameter name="{name()}" value="{text()}" type="string"/>
	</xsl:template>

</xsl:stylesheet>

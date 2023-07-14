<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:template match="/form-data">
    <document>
        <!-- оставляем для того, чтоб не сеттилось null  -->
        <receiver-name>
            <xsl:value-of select="receiverName"/>
        </receiver-name>

        <payer-account>
            <xsl:value-of select="fromAccountSelect"/>
        </payer-account>

        <extra-parameters>
            <parameter name="codeService" value="{codeService}" type="string"/>
            <parameter name="nameService" value="{nameService}" type="string"/>
            <parameter name="provider-billing-code" value="{billingCode}" type="string"/>
            <parameter name="long-offer-number" value="{autoSubNumber}" type="string"/>
            <parameter name="invoice-sub-id" value="{subscriptionId}" type="string"/>
            <parameter name="from-account-name" value="{fromAccountName}" type="string"/>
            <parameter name="from-resource-currency" value="{fromResourceCurrency}" type="string"/>
            <parameter name="from-resource-rest" value="{fromResourceRest}" type="string"/>
            <parameter name="group-name" value="{groupName}" type="string"/>
            <parameter name="invoice-account-name" value="{invoiceAccountName}" type="string"/>
            <parameter name="invoice-info" value="{invoiceInfo}" type="string"/>
        </extra-parameters>
    </document>
    </xsl:template>
</xsl:stylesheet>
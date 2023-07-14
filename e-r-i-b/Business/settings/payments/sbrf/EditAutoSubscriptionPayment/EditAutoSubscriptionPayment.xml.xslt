<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
    <document>
        <!-- оставляем для того, чтоб не сеттилось null  -->
        <receiver-name>
            <xsl:value-of select="receiverName"/>
        </receiver-name>
        <!-- оставляем для того, чтоб не сеттилось null  -->
        <receiver-account>
            <xsl:value-of select="receiverAccount"/>
        </receiver-account>

        <initial-card>
            <xsl:value-of select="initialCard"/>
        </initial-card>

        <payer-account>
            <xsl:value-of select="fromAccountSelect"/>
        </payer-account>

        <xsl:if test="autoSubType = 'ALWAYS'">
            <destination-amount>
                <xsl:value-of select="alwaysAmount"/>
            </destination-amount>

            <destination-amount-currency>
                <xsl:value-of select="alwaysAmountCurrency"/>
            </destination-amount-currency>

            <exact-amount>
                <xsl:value-of select="'destination-field-exact'"/>
            </exact-amount>
        </xsl:if>

        <extra-parameters>

            <parameter name="operation-code"                value="{operationCode}"             type="string"/>

            <parameter name="from-resource-type"            value="{fromResourceType}"          type="string"/>
            <parameter name="from-account-name"             value="{fromAccountName}"           type="string"/>
            <parameter name="from-resource"                 value="{fromResource}"              type="string"/>
            <parameter name="from-resource-currency"        value="{fromResourceCurrency}"      type="string"/>
            <parameter name="from-account-type"             value="{fromAccountType}"           type="string"/>
            
            <parameter name="auto-sub-number"               value="{autoSubNumber}"             type="string"/>
            <parameter name="auto-sub-friendy-name"         value="{autoSubName}"               type="string"/>
            <parameter name="auto-sub-type"                 value="{autoSubType}"               type="string"/>
            <parameter name="long-offer-event-type"         value="{autoSubEventType}"          type="string"/>
            <parameter name="long-offer-sum-type"           value="{autoSubSumType}"            type="string"/>
            <xsl:if test="autoSubType = 'ALWAYS'">
                <parameter name="auto-sub-next-pay-date"    value="{nextPayDateAlways}"         type="date"/>
            </xsl:if>
            <xsl:if test="autoSubType = 'INVOICE'">
                <parameter name="auto-sub-next-pay-date"    value="{nextPayDateInvoice}"        type="date"/>
            </xsl:if>
            <parameter name="auto-sub-invoice-max-decimal"  value="{invoiceMaxAmount}"          type="decimal"/>
            <parameter name="auto-sub-invoice-max-currency" value="{invoiceMaxAmountCurrency}"  type="string"/>
            <parameter name="reason-for-additional-confirm" value="{reasonForAdditionalConfirm}"        type="string"/>
        </extra-parameters>
    </document>            
    </xsl:template>
</xsl:stylesheet>    
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
<xsl:template match="/form-data">
    <document>

        <payer-account>
            <xsl:value-of select="fromAccountSelect"/>
        </payer-account>

        <receiver-account>
            <xsl:value-of select="toAccountSelect"/>
        </receiver-account>

        <receiver-name>
            <xsl:value-of select="toAccountName"/>
        </receiver-name>

        <ground>
            <xsl:value-of select="ground"/>
        </ground>

        <amount>
            <xsl:value-of select="sellAmount"/>
        </amount>

        <amount-currency>
            <xsl:value-of select="sellAmountCurrency"/>
        </amount-currency>

        <exact-amount>
            <xsl:value-of select="exactAmount"/>
        </exact-amount>

        <state>
            <xsl:value-of select="state"/>
        </state>

        <extra-parameters>
            <parameter name="from-resource" value="{fromResource}" type="string"/>
            <parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
            <parameter name="from-resource-link" value="{fromResourceLink}" type="string"/>
            <parameter name="from-account-type" value="{fromAccountType}" type="string"/>
            <parameter name="from-account-name" value="{fromAccountName}" type="string"/>

            <parameter name="to-resource" value="{toResource}" type="string"/>
            <parameter name="to-resource-type" value="{toResourceType}" type="string"/>
            <parameter name="to-resource-link" value="{toResourceLink}" type="string"/>
            <parameter name="to-account-type" value="{toAccountType}" type="string"/>
            <parameter name="to-account-name" value="{toAccountName}" type="string"/>

            <parameter name="money-box-name"        value="{moneyBoxName}" type="string"/>
            <parameter name="money-box-sum-type"    value="{moneyBoxSumType}" type="string"/>
            <parameter name="long-offer-start-date" value="{longOfferStartDate}"    type="date"/>
            <xsl:choose>
                <xsl:when test="moneyBoxSumType = 'FIXED_SUMMA'">
                    <parameter name="long-offer-event-type" value="{longOfferEventType}" type="string"/>
                </xsl:when>
                <xsl:when test="moneyBoxSumType = 'PERCENT_BY_ANY_RECEIPT'">
                    <parameter name="long-offer-event-type" value="BY_ANY_RECEIPT" type="string"/>
                </xsl:when>
                <xsl:when test="moneyBoxSumType = 'PERCENT_BY_DEBIT'">
                    <parameter name="long-offer-event-type" value="BY_DEBIT"    type="string"/>
                </xsl:when>
            </xsl:choose>
            <parameter name="long-offer-percent"    value="{percent}"  type="int"/>
            <parameter name="long-offer-first-payment-date"   value="{firstPaymentDate}" type="date"/>
            <parameter name="reason-for-additional-confirm"   value="{reasonForAdditionalConfirm}"      type="string"/>
            <parameter name="auto-sub-number"                 value="{autoSubNumber}"                   type="string"/>
        </extra-parameters>
    </document>
</xsl:template>
</xsl:stylesheet>

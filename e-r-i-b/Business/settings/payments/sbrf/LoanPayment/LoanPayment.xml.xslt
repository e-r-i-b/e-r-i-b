<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:template match="/form-data">
        <payment>

            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>

            <amount>
                <xsl:value-of select="amount"/>
            </amount>

            <amount-currency>
                <xsl:value-of select="amountCurrency"/>
            </amount-currency>

            <ground>
                <xsl:value-of select="ground"/>
            </ground>

            <exact-amount>
                <xsl:value-of select="'charge-off-field-exact'"/>
            </exact-amount>

            <state>
                <xsl:value-of select="state"/>
            </state>

            <receiver-name>
                <xsl:value-of select="loanType"/>&#160;<xsl:value-of select="loanAccountNumber"/>
            </receiver-name>

            <extra-parameters>
                <parameter name="from-resource-type"    value="{fromResourceType}"  type="string"/>
                <parameter name="from-resource-link"    value="{fromResourceLink}"  type="string"/>
                <parameter name="from-resource"         value="{fromResource}"      type="string"/>
                <parameter name="from-account-name"     value="{fromAccountName}"   type="string"/>
                <parameter name="resource-currency"     value="{resourceCurrency}"  type="string"/>
                <parameter name="loan-type"             value="{loanType}"          type="string"/>
                <parameter name="loan-amount"           value="{loanAmount}"        type="string"/>
                <parameter name="loan-currency"         value="{loanCurrency}"      type="string"/>
                <parameter name="loan-name"             value="{loanName}"          type="string"/>
                <parameter name="loan"                  value="{loan}"              type="string"/>
                <parameter name="loan-account-number"   value="{loanAccountNumber}" type="string"/>
                <parameter name="loan-external-id"      value="{loanExternalId}"    type="string"/>
                <parameter name="agreement-number"      value="{agreementNumber}"   type="string"/>
                <parameter name="office"                value="{office}"            type="string"/>
                <parameter name="loan-link-id"          value="{loanLinkId}"        type="long"/>


                <parameter name="long-offer-start-date" value="{longOfferStartDate}"    type="date"/>
                <parameter name="long-offer-end-date"   value="{longOfferEndDate}"  type="date"/>
                <parameter name="long-offer-pay-day"    value="{longOfferPayDay}"   type="long"/>
                <parameter name="long-offer-sum-type"   value="{longOfferSumType}"  type="string"/>
                <parameter name="long-offer-priority"   value="{longOfferPrioritySelect}" type="long"/>
                <parameter name="long-offer-event-type" value="ONCE_IN_MONTH"       type="string"/>
                <parameter name="is-sum-modify"         value="{isSumModify}"       type="boolean"/>
                <parameter name="long-offer-first-payment-date"   value="{firstPaymentDate}" type="date"/>
            </extra-parameters>
        </payment>
    </xsl:template>
</xsl:stylesheet>

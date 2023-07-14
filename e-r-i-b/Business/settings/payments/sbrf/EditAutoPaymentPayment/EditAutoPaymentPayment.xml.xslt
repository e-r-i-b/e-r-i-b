<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>

            <amount>
                <xsl:value-of select="sellAmount"/>
            </amount>

            <payer-account>
                <xsl:value-of select="cardNumber"/>
            </payer-account>

            <exact-amount>
                <xsl:value-of select="'charge-off-field-exact'"/>
            </exact-amount>

            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <extra-parameters>
                 <parameter name="from-resource"                        value="{fromResource}"                  type="string"/>
                 <parameter name="from-resource-type"                   value="{fromResourceType}"              type="string"/>
                 <parameter name="long-offer-sum-type"                  value="{autoPaymentSumType}"            type="string"/>
                 <parameter name="auto-payment-card-number"             value="{cardNumber}"                    type="string"/>
                 <parameter name="card-name"                            value="{cardName}"                      type="string"/>
                 <parameter name="long-offer-event-type"                value="{executionEventType}"            type="string"/>
                 <parameter name="long-offer-pay-day"                   value="{autoPaymentPayDay}"             type="long"/>
                 <parameter name="long-offer-start-date"                value="{autoPaymentStartDate}"          type="date"/>
                 <parameter name="auto-payment-execute-date"            value="{strAutoPaymentExecuteDate}"     type="string"/>
                 <parameter name="recipient"                            value="{recipient}"                     type="long"/>
                 <parameter name="requisite"                            value="{requisite}"                     type="string"/>
                 <parameter name="requisite-name"                       value="{requisiteName}"                 type="string"/>
                 <parameter name="auto-payment-friendly-name"           value="{autoPaymentName}"               type="string"/>
                 <parameter name="auto-payment-decimal-floor-limit"     value="{autoPaymentFloorLimit}"         type="decimal"/>
                 <parameter name="auto-payment-currency-floor-limit"    value="{autoPaymentFloorCurrency}"      type="string"/>
                 <parameter name="auto-payment-link-id"                 value="{linkId}"                        type="string"/>
                 <parameter name="long-offer-first-payment-date"        value="{firstPaymentDate}"              type="date"/>
                 <parameter name="reason-for-additional-confirm"        value="{reasonForAdditionalConfirm}"    type="string"/>

                 <parameter name="autopay-total-amount-period"          value="{autoPaymentTotalAmountPeriod}"      type="string"/>
                 <parameter name="is-autopay-total-amount-limit"        value="{isAutoPaymentTotalAmountLimit}"     type="boolean"/>
                 <parameter name="autopay-client-total-amount-limit"    value="{autoPaymentTotalAmountLimit}"       type="decimal"/>
                 <parameter name="autopay-client-total-amount-currency" value="{autoPaymentTotalAmountCurrency}"    type="string"/>
            </extra-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>
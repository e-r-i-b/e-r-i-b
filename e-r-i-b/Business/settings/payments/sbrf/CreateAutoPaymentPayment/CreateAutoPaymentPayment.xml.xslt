<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>

            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>

            <xsl:if test="string-length(sellAmount) &gt; 0">
                <amount>
                    <xsl:value-of select="sellAmount"/>
                </amount>
            </xsl:if>

            <amount-currency>
				<xsl:value-of select="sellAmountCurrency"/>
			</amount-currency>

            <receiver-account>
                <xsl:value-of select="receiverAccount"/>
            </receiver-account>

            <exact-amount>
                <xsl:value-of select="'charge-off-field-exact'"/>
            </exact-amount>

            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <receiverId>
                <xsl:value-of select="receiverId"/>
            </receiverId>

            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <extra-parameters>
				 <parameter name="from-resource"                        value="{fromResource}"              type="string"/>
				 <parameter name="from-resource-type"                   value="{fromResourceType}"          type="string"/>
                 <parameter name="auto-payment-card-number"             value="{fromAccountSelect}"         type="string"/>
                 <parameter name="auto-payment-code-service"            value="{codeService}"               type="string"/>
                 <parameter name="requisite"                            value="{requisite}"                 type="string"/>
                 <parameter name="requisite-name"                       value="{requisiteName}"             type="string"/>

                 <parameter name="from-resource-name"                   value="{fromResourceName}"          type="string"/>
                 <parameter name="from-resource-rest"                   value="{fromResourceRest}"          type="decimal"/>
                 <!-- Данные получателя -->
                 <parameter name="receiver-description"                 value="{receiverDescription}"       type="string"/>
                 <parameter name="bank-details"                         value="{bankDetails}"               type="boolean"/>
                 <parameter name="receiver-inn"                         value="{receiverINN}"               type="string"/>
                 <parameter name="receiver-kpp"                         value="{receiverKPP}"               type="string"/>
                 <parameter name="receiver-bic"                         value="{receiverBIC}"               type="string"/>
                 <parameter name="receiver-bank"                        value="{receiverBankName}"          type="string"/>
                 <parameter name="receiver-cor-account"                 value="{receiverCorAccount}"        type="string"/>
                 <parameter name="reason-for-additional-confirm"        value="{reasonForAdditionalConfirm}"      type="string"/>

                <!--параметры автоплатежа заполняем только при выбранном типе автоплатежа-->
                <xsl:if test="string-length(autoPaymentType)!=0">
                    <parameter name="long-offer-event-type"                value="{autoPaymentType}"           type="string"/>
                    <parameter name="long-offer-pay-day"                   value="{autoPaymentPayDay}"         type="long"/>
                    <parameter name="long-offer-start-date"                value="{autoPaymentStartDate}"      type="date"/>
                    <parameter name="auto-payment-friendly-name"           value="{autoPaymentName}"           type="string"/>
                    <parameter name="auto-payment-decimal-floor-limit"     value="{autoPaymentFloorLimit}"     type="decimal"/>
                    <parameter name="auto-payment-currency-floor-limit"    value="{autoPaymentFloorCurrency}"  type="string"/>
                    <parameter name="autopay-total-amount-period"         value="{autoPaymentTotalAmountPeriod}"      type="string"/>
                    <parameter name="is-autopay-total-amount-limit"       value="{isAutoPaymentTotalAmountLimit}"     type="boolean"/>
                    <parameter name="autopay-client-total-amount-limit"    value="{autoPaymentTotalAmountLimit}"       type="decimal"/>
                    <parameter name="autopay-client-total-amount-currency" value="{autoPaymentTotalAmountCurrency}"    type="string"/>
                    <parameter name="long-offer-sum-type"                  value="{autoPaymentSumType}"        type="string"/>
                    <parameter name="long-offer-first-payment-date"        value="{firstPaymentDate}"          type="date"/>
                </xsl:if>
            </extra-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>
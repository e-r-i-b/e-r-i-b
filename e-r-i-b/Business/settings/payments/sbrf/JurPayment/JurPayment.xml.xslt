<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:template match="/form-data">
        <payment>

            <payer-account>
                <xsl:value-of select="fromAccountSelect"/>
            </payer-account>

            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <receiver-account>
                <xsl:value-of select="receiverAccount"/>
            </receiver-account>

            <amount>
			    <xsl:value-of select="sellAmount"/>
            </amount>

			<amount-currency>
				<xsl:value-of select="sellAmountCurrency"/>
			</amount-currency>

            <destination-amount>
                <xsl:value-of select="buyAmount"/>
            </destination-amount>

            <destination-amount-currency>
                <xsl:value-of select="buyAmountCurrency"/>
            </destination-amount-currency>

            <ground>
                <xsl:value-of select="ground"/>
            </ground>

            <exact-amount>
                <xsl:value-of select="exactAmount"/>
            </exact-amount>

            <extra-parameters>
                <parameter name="receiver-inn"          value="{receiverINN}"               type="string"/>
                <parameter name="receiver-kpp"          value="{receiverKPP}"               type="string"/>
                <parameter name="receiver-bic"          value="{receiverBIC}"               type="string"/>
                <parameter name="receiver-bank"         value="{receiverBankName}"          type="string"/>
                <parameter name="receiver-cor-account"  value="{receiverCorAccount}"        type="string"/>
                <parameter name="receiver-type"         value="jur"                         type="string"/>
                <parameter name="tax-payment"           value="{taxPayment}"                type="string"/>
                <parameter name="tax-status"            value="{taxStatus}"                 type="string" />
				<parameter name="tax-kbk"               value="{taxKBK}"                    type="string"/>
				<parameter name="tax-okato"             value="{taxOKATO}"                  type="string"/>
				<parameter name="tax-ground"            value="{taxGround}"                 type="string"/>
				<parameter name="tax-type"              value="{taxType}"                   type="string"/>
				<parameter name="tax-document-date"     value="{taxDocumentDate}"           type="string"/>
				<parameter name="tax-document-number"   value="{taxDocumentNumber}"         type="string"/>
				<parameter name="tax-period1"           value="{taxPeriod1}"                type="string"/>
				<parameter name="tax-period2"           value="{taxPeriod2}"                type="string"/>
				<parameter name="tax-period3"           value="{taxPeriod3}"                type="string"/>
				<parameter name="tax-period"            value="{taxPeriod}"                 type="string"/>
                <parameter name="from-resource-type"    value="{fromResourceType}"          type="string"/>
                <parameter name="from-resource"         value="{fromResource}"              type="string"/>
                <parameter name="from-account-type"     value="{fromAccountType}"           type="string"/>
                <parameter name="from-account-name"     value="{fromAccountName}"           type="string"/>
                <parameter name="operation-code"        value="{operationCode}"             type="string"/>
                <parameter name="receiver-cor-account"  value="{receiverCorAccount}"        type="string"/>
                <parameter name="AUTHORIZE_CODE"        value="{authorizeCode}"             type="string"/>
                <parameter name="is-our-bank"           value="{isOurBank}"                 type="boolean"/>
                <parameter name="long-offer-start-date" value="{longOfferStartDate}"        type="date"/>
                <parameter name="long-offer-end-date"   value="{longOfferEndDate}"          type="date"/>
                <parameter name="long-offer-pay-day"    value="{longOfferPayDay}"           type="long"/>
                <parameter name="long-offer-event-type" value="{longOfferEventType}"        type="string"/>
                <parameter name="long-offer-sum-type"   value="{longOfferSumType}"          type="string"/>
                <parameter name="is-sum-modify"         value="{isSumModify}"               type="boolean"/>
                <parameter name="long-offer-percent"    value="{longOfferPercent}"          type="decimal"/>
                <parameter name="long-offer-priority"   value="{longOfferPrioritySelect}"   type="long"/>
                <parameter name="long-offer-first-payment-date"   value="{firstPaymentDate}" type="date"/>
                <parameter name="depo-link-id"          value="{depoLinkId}"                type="string"/>
                <parameter name="debt-rec-number"       value="{debtRecNumber}"             type="string"/>
                <parameter name="reason-for-additional-confirm"         value="{reasonForAdditionalConfirm}"        type="string"/>
            </extra-parameters>
        </payment>
    </xsl:template>
</xsl:stylesheet>

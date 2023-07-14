<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>
            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <receiverId>
                <xsl:value-of select="receiverId"/>
            </receiverId>

            <payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>

            <receiver-account>
                <xsl:value-of select="receiverAccount"/>
            </receiver-account>

            <extra-parameters>
                <parameter name="nameService"                    value="{nameService}"                       type="string"/>
                <parameter name="codeService"                    value="{codeService}"                       type="string"/>
                <parameter name="provider-group-service"         value="{groupService}"                      type="string"/>
                <parameter name="provider-billing-code"          value="{billingCode}"                       type="string"/>
                <parameter name="invoice-account-name"           value="{invoiceAccountName}"                type="string"/>
                <parameter name="subscription-name"              value="{subscriptionName}"                  type="string"/>
                <parameter name="invoice-sub-id"                 value="{subscriptionId}"                    type="long"/>
                <parameter name="autoPay-number"                 value="{autoSubNumber}"                     type="string"/>
                <parameter name="invoice-info"                   value="{invoiceInfo}"                       type="string"/>
                <parameter name="group-name"                     value="{groupName}"                         type="string"/>
                <parameter name="account-entity-id"              value="{accountingEntityId}"                type="long"/>
                <parameter name="day-pay"                        value="{dayPay}"                            type="date"/>
                <parameter name="long-offer-event-type"          value="{eventType}"                         type="string"/>
                <parameter name="long-offer-sum-type"            value="{autoSubSumType}"                    type="string"/>
                <parameter name="from-resource"                  value="{fromResource}"                      type="string"/>
                <parameter name="from-account-name"              value="{fromAccountName}"                   type="string"/>
                <parameter name="from-resource-type"             value="{fromResourceType}"                  type="string"/>
                <parameter name="from-account-type"              value="{fromAccountType}"                   type="string"/>
                <parameter name="from-resource-currency"         value="{fromResourceCurrency}"              type="string"/>
                <parameter name="from-resource-rest"             value="{fromResourceRest}"                  type="string"/>

                <parameter name="old-subscription-name"          value="{oldSubscriptionName}"               type="string"/>
                <parameter name="old-account-entity-id"          value="{oldAccountingEntityId}"             type="long"/>
                <parameter name="old-from-resource"              value="{oldFromResource}"                   type="string"/>
                <parameter name="old-day-pay"                    value="{oldDayPay}"                         type="date"/>
                <parameter name="old-event-type"                 value="{oldEventType}"                      type="string"/>

                <parameter name="receiver-inn"                   value="{receiverINN}"                       type="string"/>
                <parameter name="receiver-kpp"                   value="{receiverKPP}"                       type="string"/>
                <parameter name="receiver-cor-account"           value="{receiverCorAccount}"                type="string"/>
                <parameter name="receiver-bic"                   value="{receiverBIC}"                       type="string"/>
                <parameter name="receiver-office-region-code"    value="{receiverOfficeRegionCode}"          type="string"/>
                <parameter name="receiver-bank"                  value="{receiverBankName}"                  type="string"/>
                <parameter name="receiver-phone-number"          value="{phoneNumber}"                       type="string"/>


                <parameter name="is-auto-sub-execution-now"     value="{isAutoSubExecuteNow}"                type="boolean"/>
                <parameter name="bank-details"                  value="{bankDetails}"                        type="boolean"/>
			</extra-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>
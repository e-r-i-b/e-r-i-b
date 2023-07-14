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
                <xsl:choose>
                    <xsl:when test="receiverType = 'ph'">
                        <xsl:value-of select="externalCardNumber"/>
                    </xsl:when>
                    <xsl:when test="receiverType = 'several'">
                        <xsl:value-of select="toAccountSelect"/>
                    </xsl:when>
                </xsl:choose>
            </receiver-account>

            <amount>
			    <xsl:value-of select="sellAmount"/>
            </amount>

			<amount-currency>
				<xsl:value-of select="sellAmountCurrency"/>
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

            <commission>
                 <xsl:value-of select="commission"/>
            </commission>

            <extra-parameters>
                <parameter name="receiver-type"                 value="{receiverType}"                      type="string"/>
                <parameter name="receiver-subtype"              value="{receiverSubType}"                   type="string"/>

                <parameter name="is-external-card"              value="{externalCard}"                      type="boolean"/>

                <parameter name="from-resource-type"            value="{fromResourceType}"                  type="string"/>
                <parameter name="from-resource"                 value="{fromResource}"                      type="string"/>
                <parameter name="from-account-type"             value="{fromAccountType}"                   type="string"/>
                <parameter name="from-account-name"             value="{fromAccountName}"                   type="string"/>
                <parameter name="from-resource-link"            value="{fromResourceLink}"                  type="string"/>

                <xsl:if test="receiverType = 'several'">
                    <parameter name="to-resource"                   value="{toResource}"                        type="string"/>
                    <parameter name="to-resource-type"              value="{toResourceType}"                    type="string"/>
                    <parameter name="to-account-type"               value="{toAccountType}"                     type="string"/>
                    <parameter name="to-account-name"               value="{toAccountName}"                     type="string"/>
                    <parameter name="to-account-select"             value="{toAccountSelect}"                   type="string"/>
                    <parameter name="to-resource-currency"          value="{toResourceCurrency}"                type="string"/>
                    <parameter name="to-resource-link"              value="{toResourceLink}"                    type="string"/>
                </xsl:if>

                <xsl:if test="receiverType = 'ph'">
                    <parameter name="external-card-number"          value="{externalCardNumber}"                type="string"/>
                    <parameter name="receiver-surname"              value="{receiverSurname}"                   type="string"/>
                    <parameter name="receiver-first-name"           value="{receiverFirstName}"                 type="string"/>
                    <parameter name="receiver-patr-name"            value="{receiverPatrName}"                  type="string"/>
                    <parameter name="external-contact-id"           value="{externalContactId}"                 type="long"/>
                    <parameter name="contact-name"                  value="{contactName}"                       type="string"/>
                    <parameter name="contact-phone"                 value="{contactPhone}"                      type="string"/>
                    <parameter name="contact-card"                  value="{contactCard}"                       type="string"/>
                    <parameter name="message-to-receiver"           value="{messageToReceiver}"                 type="string"/>
                    <parameter name="message-to-receiver-status"    value="{messageToReceiverStatus}"           type="string"/>
                    <parameter name="mobile-number"                 value="{externalPhoneNumber}"               type="string"/>

                </xsl:if>

                <parameter name="operation-code"                value="{operationCode}"                     type="string"/>
                <parameter name="AUTHORIZE_CODE"                value="{authorizeCode}"                     type="string"/>
                <parameter name="is-our-bank"                   value="{isOurBank}"                         type="boolean"/>
                <parameter name="long-offer-start-date"         value="{longOfferStartDate}"                type="date"/>
                <parameter name="long-offer-pay-day"            value="{longOfferPayDay}"                   type="long"/>
                <parameter name="long-offer-event-type"         value="{longOfferEventType}"                type="string"/>
                <parameter name="long-offer-sum-type"           value="RUR_SUMMA"                           type="string"/>
                <parameter name="long-offer-first-payment-date" value="{firstPaymentDate}"                  type="date"/>
                <parameter name="is-card-transfer"              value="{isCardTransfer}"                    type="boolean"/>
                <parameter name="reason-for-additional-confirm" value="{reasonForAdditionalConfirm}"        type="string"/>
                <parameter name="auto-sub-friendly-name"        value="{autoSubName}"                       type="string"/>
                <parameter name="auto-sub-number"               value="{autoSubNumber}"                     type="string"/>
                <parameter name="auto-sub-type"                 value="ALWAYS"                              type="string"/>
                <parameter name="contact-sberbank"              value="true"                                type="string"/>
                <parameter name="avatar-path"                   value="{avatarPath}"                        type="string"/>
            </extra-parameters>
        </payment>
    </xsl:template>
</xsl:stylesheet>

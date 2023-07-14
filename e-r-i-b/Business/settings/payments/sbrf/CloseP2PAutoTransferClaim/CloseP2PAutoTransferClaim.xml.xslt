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
                        <xsl:value-of select="receiverAccount"/>
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

            <extra-parameters>
                <parameter name="is-external-card"              value="{externalCard}"                      type="boolean"/>

                <parameter name="from-resource-type"            value="{fromResourceType}"                  type="string"/>
                <parameter name="from-resource"                 value="{fromResource}"                      type="string"/>
                <parameter name="from-account-type"             value="{fromAccountType}"                   type="string"/>
                <parameter name="from-account-name"             value="{fromAccountName}"                   type="string"/>
                <parameter name="from-resource-link"            value="{fromResourceLink}"                  type="string"/>

                <parameter name="receiver-type"                 value="{receiverType}"                      type="string"/>
                <parameter name="receiver-subtype"              value="{receiverSubType}"                   type="string"/>

                <xsl:if test="receiverType = 'several'">
                    <parameter name="to-resource"                   value="{toResource}"                        type="string"/>
                    <parameter name="to-resource-type"              value="{toResourceType}"                    type="string"/>
                    <parameter name="to-account-type"               value="{toAccountType}"                     type="string"/>
                    <parameter name="to-account-name"               value="{toAccountName}"                     type="string"/>
                    <parameter name="to-account-select"             value="{receiverAccount}"                   type="string"/>
                    <parameter name="to-resource-currency"          value="{toResourceCurrency}"                type="string"/>
                    <parameter name="to-resource-link"              value="{toResourceLink}"                    type="string"/>
                </xsl:if>

                <xsl:if test="receiverType = 'ph'">
                    <parameter name="external-card-number"          value="{externalCardNumber}"                type="string"/>
                </xsl:if>

                <parameter name="is-our-bank"                   value="{isOurBank}"                         type="boolean"/>
                <parameter name="auto-sub-next-pay-date"        value="{nextPayDate}"                       type="date"/>
                <parameter name="long-offer-start-date"         value="{longOfferStartDate}"                type="date"/>
                <parameter name="long-offer-event-type"         value="{longOfferEventType}"                type="string"/>
                <parameter name="long-offer-sum-type"           value="RUR_SUMMA"                           type="string"/>
                <parameter name="is-card-transfer"              value="{isCardTransfer}"                    type="boolean"/>
                <parameter name="auto-sub-friendly-name"        value="{autoSubName}"                       type="string"/>
                <parameter name="auto-sub-number"               value="{autoSubNumber}"                     type="string"/>
                <parameter name="auto-sub-type"                 value="ALWAYS"                              type="string"/>
            </extra-parameters>
        </payment>
    </xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:param name="documentState" select="''"/>

    <xsl:template match="/">
        <xsl:choose>
			<xsl:when test="$documentState = 'INITIAL_LONG_OFFER'">
				<xsl:apply-templates mode="edit-longOffer"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="edit"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

	<xsl:template match="/form-data" mode="edit">
        <document>
            <payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

            <receiver-account>
                <xsl:value-of select="receiverAccount"/>
            </receiver-account>

            <receiver-name>
                <xsl:value-of select="receiverName"/>
            </receiver-name>

            <exact-amount>
                <xsl:value-of select="'destination-field-exact'"/>
            </exact-amount>

            <receiverId>
                <xsl:value-of select="receiverId"/>
            </receiverId>

            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <extra-parameters>
                <parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
                <parameter name="from-account-type" value="{fromAccountType}" type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}" type="string"/>
                <parameter name="from-resource" value="{fromResource}" type="string"/>
                <parameter name="from-resource-currency" value="{fromResourceCurrency}" type="string"/>

                <parameter name="operation-code" value="{operationCode}" type="string"/>

                <parameter name="provider-billing-code" value="{billingCode}"               type="string"/>
                <parameter name="name-on-bill"          value="{nameOnBill}"                type="string"/>
                <parameter name="bank-details"          value="{bankDetails}"               type="boolean"/>
                <parameter name="receiver-inn"          value="{receiverINN}"               type="string"/>
                <parameter name="receiver-kpp"          value="{receiverKPP}"               type="string"/>
                <parameter name="receiver-bic"          value="{receiverBIC}"               type="string"/>
                <parameter name="receiver-bank"         value="{receiverBankName}"          type="string"/>
                <parameter name="receiver-cor-account"  value="{receiverCorAccount}"        type="string"/>
                <parameter name="receiver-description"  value="{receiverDescription}"       type="string"/>
                <parameter name="receiver-phone-number" value="{phoneNumber}"               type="string"/>

                <!--параметры автоплатежа заполняем только при выбранном типе автоплатежа-->
                <parameter name="account-entity-id"             value="{accountingEntityId}"        type="long"/>
                <parameter name="person-region-name"            value="{personRegionName}"          type="string"/>
                <parameter name="from-resource-link"            value="{fromResourceLink}"          type="string"/>
                <parameter name="reason-for-additional-confirm" value="{reasonForAdditionalConfirm}"        type="string"/>
                <xsl:call-template name="autoSubParameters">
                    <xsl:with-param name="form-data" select="."/>
                </xsl:call-template>
				<xsl:apply-templates select="/form-data/*[name() != '']"/>

			</extra-parameters>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit-longOffer">
        <document>
            <extra-parameters>
                <xsl:call-template name="autoSubParameters">
                    <xsl:with-param name="form-data" select="."/>
                </xsl:call-template>
            </extra-parameters>
        </document>
    </xsl:template>

    <xsl:template name="autoSubParameters">
        <xsl:param name="form-data"/>

        <parameter name="auto-sub-friendy-name"         value="{$form-data/autoSubName}"               type="string"/>
        <parameter name="auto-sub-type"                 value="{$form-data/autoSubType}"               type="string"/>
        <parameter name="long-offer-event-type"         value="{$form-data/autoSubEventType}"          type="string"/>
        <parameter name="long-offer-sum-type"           value="{$form-data/autoSubSumType}"            type="string"/>
        <parameter name="is-auto-sub-execution-now"     value="{$form-data/isAutoSubExecuteNow}"       type="boolean"/>

        <parameter name="auto-sub-choose-date-invoice"      value="{$form-data/chooseDateInvoice}"             type="date"/>
        <parameter name="auto-sub-nextpay-dayOfWeek"        value="{$form-data/nextPayDayOfWeekInvoice}"       type="int"/>
        <parameter name="auto-sub-nextpay-dayOfMonth"       value="{$form-data/nextPayDayOfMonthInvoice}"      type="int"/>
        <parameter name="auto-sub-nextpay-monthOfQuarter"   value="{$form-data/nextPayMonthOfQuarterInvoice}"  type="int"/>
    </xsl:template>

    <xsl:template match="/form-data/fromResourceLink"/>
    <xsl:template match="/form-data/fromResource"/>
    <xsl:template match="/form-data/fromResourceType"/>
    <xsl:template match="/form-data/fromAccountSelect"/>
    <xsl:template match="/form-data/fromAccountType"/>
    <xsl:template match="/form-data/fromAccountName"/>
    <xsl:template match="/form-data/amountCurrency"/>
    <xsl:template match="/form-data/fromResourceCurrency"/>
    <xsl:template match="/form-data/operationCode"/>
    <xsl:template match="/form-data/longOfferStartDate"/>
    <xsl:template match="/form-data/longOfferEndDate"/>
    <xsl:template match="/form-data/longOfferEventType"/>
    <xsl:template match="/form-data/longOfferPrioritySelect"/>
    <xsl:template match="/form-data/firstPaymentDate"/>
    <xsl:template match="/form-data/personRegionName"/>

    <!--2 юзер их не должен присылать-->
    <xsl:template match="/form-data/billingDocumentNumber"/>
    <xsl:template match="/form-data/documentNumber"/>
    <xsl:template match="/form-data/documentDate"/>
    <xsl:template match="/form-data/codeService"/>
    <xsl:template match="/form-data/nameService"/>
    <xsl:template match="/form-data/phoneNumber"/>
    <xsl:template match="/form-data/authorizeCode"/>
    <xsl:template match="/form-data/billingCode"/>
    <xsl:template match="/form-data/receiverName"/>
    <xsl:template match="/form-data/receiverId"/>
    <xsl:template match="/form-data/recipient"/>
    <xsl:template match="/form-data/receiverDescription"/>
    <xsl:template match="/form-data/receiverAccount"/>
    <xsl:template match="/form-data/receiverINN"/>
    <xsl:template match="/form-data/receiverKPP"/>
    <xsl:template match="/form-data/receiverBIC"/>
    <xsl:template match="/form-data/receiverBankName"/>
    <xsl:template match="/form-data/receiverCorAccount"/>
    <xsl:template match="/form-data/commission"/>
    <xsl:template match="/form-data/admissionDate"/>
    <xsl:template match="/form-data/state"/>
    <xsl:template match="/form-data/operationDate"/>
    <xsl:template match="/form-data/operationTime"/>
    <xsl:template match="/form-data/chargeOffDate"/>
    <xsl:template match="/form-data/bankDetails"/>
    <xsl:template match="/form-data/fromAccountNumber"/>
    <xsl:template match="/form-data/regions"/>

    <xsl:template match="/form-data/receiver-office-region-code"/>
    <xsl:template match="/form-data/receiver-office-branch-code"/>
    <xsl:template match="/form-data/receiver-office-office-code"/>

    <xsl:template match="/form-data/transit-receiver-account"/>
    <xsl:template match="/form-data/transit-receiver-name"/>
    <xsl:template match="/form-data/transit-receiver-bank-bic"/>
    <xsl:template match="/form-data/transit-receiver-bank-corraccount"/>

    <!-- остальные поля заполнянем-->
    <xsl:template match="/form-data/*" priority="-99">
		<parameter name="{name()}" value="{text()}" type="string"/>
	</xsl:template>
</xsl:stylesheet>
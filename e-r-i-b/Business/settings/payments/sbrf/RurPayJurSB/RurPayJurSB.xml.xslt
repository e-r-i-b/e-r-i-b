<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">

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

            <xsl:if test="autoSubType = 'ALWAYS'">
                <destination-amount>
                    <xsl:value-of select="alwaysAmount"/>
                </destination-amount>

                <destination-amount-currency>
                    <xsl:value-of select="alwaysAmountCurrency"/>
                </destination-amount-currency>
            </xsl:if>

            <receiverId>
                <xsl:value-of select="receiverId"/>
            </receiverId>

            <recipient>
                <xsl:value-of select="recipient"/>
            </recipient>

            <state>
                <xsl:value-of select="state"/>
            </state>

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
                <xsl:if test="string-length(autoSubType)!=0">
                    <parameter name="auto-sub-friendy-name"         value="{autoSubName}"               type="string"/>
                    <parameter name="auto-sub-type"                 value="{autoSubType}"               type="string"/>
                    <parameter name="long-offer-event-type"         value="{autoSubEventType}"          type="string"/>
                    <parameter name="long-offer-sum-type"           value="{autoSubSumType}"            type="string"/>
                    <parameter name="auto-sub-invoice-max-decimal"  value="{invoiceMaxAmount}"          type="decimal"/>
                    <parameter name="auto-sub-invoice-max-currency" value="{invoiceMaxAmountCurrency}"  type="string"/>
                </xsl:if>
                <xsl:if test="autoSubType = 'ALWAYS'">
                    <parameter name="auto-sub-next-pay-date"        value="{nextPayDateAlways}"         type="date"/>
                </xsl:if>
                <xsl:if test="autoSubType = 'INVOICE'">
                    <parameter name="auto-sub-next-pay-date"        value="{nextPayDateInvoice}"        type="date"/>
                </xsl:if>

                <parameter name="person-region-name"            value="{personRegionName}"          type="string"/>
                <parameter name="from-resource-link"            value="{fromResourceLink}"          type="string"/>
                <parameter name="reason-for-additional-confirm" value="{reasonForAdditionalConfirm}"        type="string"/>
                <parameter name="operation-description"         value="{operationDescription}"      type="string"/>
				<xsl:apply-templates select="/form-data/*[name() != '']"/>

			</extra-parameters>
		</document>
	</xsl:template>

    <!--Вырезаем поля из форм-дата, которые не должны приходить от юзера-->
    <!--1 хранятся под другим именем. дубликаты не нужны-->
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
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->

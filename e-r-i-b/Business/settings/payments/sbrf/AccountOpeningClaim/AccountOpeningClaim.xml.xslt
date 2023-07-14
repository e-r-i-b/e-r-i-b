<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
           
			<payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>

            <code-account>
                <xsl:value-of select="fromCodeAccountSelect"/>
            </code-account>

            <receiver-account>
				<xsl:value-of select="receiverAccountSelect"/>
			</receiver-account>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

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

            <exact-amount>
                <xsl:value-of select="exactAmount"/>
            </exact-amount>

            <receiver-name>
                <xsl:value-of select="depositName"/>
            </receiver-name>

            <extra-parameters>
                <!--Курс передаем в платежку для сравнения расчитанного с тем, что видел клиент на форме редактирования-->
                <parameter name="from-resource" value="{fromResource}" type="string"/>
                <parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
                <parameter name="deposit-account" value="{depositAccount}" type="string"/>       
                <parameter name="convertion-rate"   value="{course}"   type="decimal"/>
                <parameter name="to-resource-currency"   value="{toResourceCurrency}"   type="string"/>
                <parameter name="min-deposit-balance"   value="{minDepositBalance}"   type="decimal"/>
                <parameter name="closing-date"   value="{closingDate}"   type="string"/>
                <parameter name="period-days"   value="{periodDays}"   type="string"/>
                <parameter name="period-months"   value="{periodMonths}"   type="string"/>
                <parameter name="period-years"   value="{periodYears}"   type="string"/>
                <parameter name="interest-rate"   value="{interestRate}"   type="string"/>
                <parameter name="open-date"   value="{openDate}"   type="string"/>
                <parameter name="deposit-type"   value="{depositType}"   type="string"/>
                <parameter name="deposit-group"   value="{depositGroup}"   type="string"/>
                <parameter name="deposit-name"   value="{depositName}"   type="string"/>
                <parameter name="deposit-sub-type"   value="{depositSubType}"   type="string"/>
                <parameter name="min-additional-fee"   value="{minAdditionalFee}"   type="string"/>
                <parameter name="operation-code" value="{operationCode}" type="string"/>
                <parameter name="deposit-id" value="{depositId}" type="string"/>
                <parameter name="atm-place" value="{atmPlace}" type="string"/>
                <parameter name="atm-tb" value="{atmTB}" type="string"/>
                <parameter name="atm-osb" value="{atmOSB}" type="string"/>
                <parameter name="atm-vsp" value="{atmVSP}" type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}" type="string"/>
                <parameter name="need-initial-fee" value="{needInitialFee}" type="boolean"/>
                <parameter name="template-id" value="{templateId}" type="string"/>
                <parameter name="incoming-transactions" value="{incomingTransactions}" type="string"/>
                <parameter name="frequency-add" value="{frequencyAdd}" type="string"/>
                <parameter name="debit-transactions" value="{debitTransactions}" type="string"/>
                <parameter name="frequency-percent" value="{frequencyPercent}" type="string"/>
                <parameter name="percent-order" value="{percentOrder}" type="string"/>
                <parameter name="income-order" value="{incomeOrder}" type="string"/>
                <parameter name="renewals" value="{renewals}" type="string"/>
                <parameter name="percent" value="{percent}" type="string"/>
                <parameter name="with-minimum-balance" value="{withMinimumBalance}" type="boolean"/>
                <parameter name="pension" value="{isPension}" type="boolean"/>
                <parameter name="tarif-plan-code-type" value="{tarifPlanCodeType}" type="string"/>
                <parameter name="premier-show-msg" value="{premierShowMsg}" type="string"/>
                <parameter name="standart-convertion-rate"   value="{standartCourse}"   type="decimal"/>
                <parameter name="pfpId" value="{pfpId}" type="long"/>
                <parameter name="pfpPortfolioId" value="{pfpPortfolioId}" type="long"/>
                <parameter name="from-personal-finance" value="{fromPersonalFinance}" type="boolean"/>
                <parameter name="from-resource-link" value="{fromResourceLink}"  type="string"/>
                <parameter name="percentTransfer-source" value="{percentTransferSourceRadio}" type="string"/>
                <parameter name="percentCard-source" value="{percentTransferCardSource}" type="string"/>
                <parameter name="percentCard-number" value="{percentTransferCardNumber}" type="string"/>
                <parameter name="deposit-tariff-plan-code" value="{depositTariffPlanCode}" type="string"/>
                <parameter name="create-money-box" value="{createMoneyBox}" type="boolean"/>
                <parameter name="money-box-from-resource" value="{moneyBoxFromResource}" type="string"/>
                <parameter name="money-box-name"        value="{moneyBoxName}" type="string"/>
                <parameter name="money-box-sum-type"    value="{moneyBoxSumType}" type="string"/>
                <parameter name="money-box-sell-amount" value="{moneyBoxSellAmount}" type="decimal"/>
                <parameter name="money-box-long-offer-start-date" value="{longOfferStartDate}"    type="string"/>
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
                <parameter name="long-offer-percent"    value="{moneyBoxPercent}"  type="int"/>
                <parameter name="segment"   value="{segment}"   type="string"/>
                <parameter name="promo-code-name"   value="{promoCodeName}"   type="string"/>
                <parameter name="use-promo-rate" value="{usePromoRate}" type="boolean"/>
                <parameter name="from-extended-loan-claim" value="{fromExtendedLoanClaim}" type="boolean"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
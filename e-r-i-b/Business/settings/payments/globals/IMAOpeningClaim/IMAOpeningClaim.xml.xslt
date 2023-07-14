<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

			<payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>

			<receiver-account>
				<xsl:value-of select="toAccountSelect"/>
			</receiver-account>

            <receiver-name>
                <xsl:value-of select="buyIMAProduct"/>&#160;<xsl:value-of select="valueOfExactAmount"/>
            </receiver-name>

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

            <extra-parameters>
				<parameter name="from-resource" value="{fromResource}" type="string"/>
                <parameter name="from-resource-code" value="{fromResourceCode}" type="string"/>
				<parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
                <parameter name="from-account-type" value="{fromAccountType}" type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}" type="string"/>

                <parameter name="to-resource-currency" value="{toResourceCurrency}" type="string"/>
                <parameter name="buyCurrency" value="{buyCurrency}" type="string"/>
                <parameter name="sellCurrency" value="{sellCurrency}" type="string"/>
                <parameter name="buyIMAProduct" value="{buyIMAProduct}" type="string"/>
                <parameter name="defaultLocaleImaName" value="{defaultLocaleImaName}" type="string"/>
                <parameter name="from-resource-currency" value="{fromResourceCurrency}" type="string"/>
                <parameter name="imaId" value="{imaId}" type="string"/>
                <parameter name="opening-date" value="{openingDate}" type="string"/>

                <parameter name="office-name" value="{officeName}" type="string"/>
                <parameter name="office-address" value="{officeAddress}" type="string"/>
                <parameter name="office-region" value="{officeRegion}" type="string"/>
                <parameter name="office-branch" value="{officeBranch}" type="string"/>
                <parameter name="office" value="{office}" type="string"/>

                <parameter name="operation-code" value="{operationCode}" type="string"/>
                <!--Курс передаем в платежку для сравнения расчитанного с тем, что видел клиент на форме редактирования-->
                <parameter name="convertion-rate"   value="{course}"   type="decimal"/>
                <parameter name="premier-show-msg"   value="{premierShowMsg}"   type="string"/>
                <parameter name="standart-convertion-rate"   value="{standartCourse}"   type="decimal"/>
                <parameter name="course-sell"   value="{courseSell}"   type="decimal"/>
                <parameter name="value-of-exact-amount"   value="{valueOfExactAmount}"   type="decimal"/>
                <parameter name="AUTHORIZE_CODE" value="{authorizeCode}" type="string"/>
                <parameter name="ima-type" value="{IMAType}" type="string"/>
                <parameter name="ima-sub-type" value="{IMASubType}" type="string"/>
                <parameter name="tarif-plan-code-type" value="{tarifPlanCodeType}" type="string"/>
                <parameter name="pfpId" value="{pfpId}" type="long"/>
                <parameter name="pfpPortfolioId" value="{pfpPortfolioId}" type="long"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
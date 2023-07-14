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
                <xsl:value-of select="toAccountName"/>
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
                <parameter name="from-resource-link" value="{fromResourceLink}" type="string"/>
				<parameter name="from-resource-type" value="{fromResourceType}" type="string"/>
                <parameter name="from-account-type" value="{fromAccountType}" type="string"/>
                <parameter name="from-account-name" value="{fromAccountName}" type="string"/>

                <parameter name="to-resource" value="{toResource}" type="string"/>
				<parameter name="to-resource-type" value="{toResourceType}" type="string"/>
                <parameter name="to-resource-link" value="{toResourceLink}" type="string"/>
                <parameter name="to-account-type" value="{toAccountType}" type="string"/>
                <parameter name="to-account-name" value="{toAccountName}" type="string"/>

                <parameter name="operation-code" value="{operationCode}" type="string"/>
                <!--Курс передаем в платежку для сравнения расчитанного с тем, что видел клиент на форме редактирования-->
                <parameter name="convertion-rate"   value="{course}"   type="decimal"/>
                <parameter name="premier-show-msg" value="{premierShowMsg}" type="string"/>
                <parameter name="standart-convertion-rate"   value="{standartCourse}"   type="decimal"/>
                <parameter name="value-of-exact-amount"   value="{valueOfExactAmount}"   type="decimal"/>
                <parameter name="tarif-plan-code-type" value="{tarifPlanCodeType}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
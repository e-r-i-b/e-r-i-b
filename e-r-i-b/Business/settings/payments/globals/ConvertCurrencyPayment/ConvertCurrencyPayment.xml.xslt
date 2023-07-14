<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
            <document-date>
                <xsl:value-of select="documentDate"/>
            </document-date>

			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<receiver-account>
				<xsl:value-of select="receiverAccountSelect"/>
			</receiver-account>

			<amount>
				<xsl:value-of select="sellAmount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="sellAmountCurrency"/>
			</amount-currency>

			<extra-parameters>
				<parameter name="buy-amount-currency" value="{buyAmountCurrency}" type="string"/>
				<parameter name="operation-code" value="{operationCode}" type="string"/>
				<parameter name="buy-amount" value="{buyAmount}" type="string"/>
				<parameter name="course" value="{course}" type="string"/>
				<parameter name="type" value="{type}" type="string"/>
				<parameter name="payer-account-type" value="{payerAccountType}" type="string"/>
				<parameter name="receiver-account-type" value="{receiverAccountType}" type="string"/>
				<parameter name="payer-account-currency" value="{payerAccountCurrency}" type="string"/>
				<parameter name="receiver-account-currency" value="{receiverAccountCurrency}" type="string"/>
				<!--если вторая сумма, посчитанное в джава-скрипте, позже, в макросе, посчиталась иначе - выделяем ее -->
				<parameter name="recalculated-amount-changed" value="{recalculatedAmountChanged}" type="boolean"/>
                <parameter name="course" value="{course}" type="string"/>
                <parameter name="tarif-plan-code-type"        value="{tarifPlanCodeType}"         type="string"/>                
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>

<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
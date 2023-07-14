<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payerName>
				<xsl:value-of select="payerName"/>
			</payerName>

			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<receiver-inn>
				<xsl:value-of select="receiverINN"/>
			</receiver-inn>

			<receiver-kpp>
				<xsl:value-of select="receiverKPP"/>
			</receiver-kpp>

			<receiver-account>
				<xsl:value-of select="receiverAccount"/>
			</receiver-account>

			<receiver-bic>
				<xsl:value-of select="receiverBIC"/>
			</receiver-bic>

			<receiver-cor-account>
				<xsl:value-of select="receiverCorAccount"/>
			</receiver-cor-account>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

			<extra-parameters>
				<parameter name="currency"
						   value="{currency}"
						   type="string"
						   />
				<parameter name="receiver-bank"
						   value="{receiverBank}"
						   type="string"
						   />
				<parameter name="tax-status"
						   value="{taxStatus}"
						   type="string"
						   />
				<parameter name="tax-kbk"
						   value="{taxKBK}"
						   type="string"
						   />
				<parameter name="tax-okato"
						   value="{taxOKATO}"
						   type="string"
						   />
				<parameter name="tax-ground"
						   value="{taxGround}"
						   type="string"
						   />
				<parameter name="tax-type"
						   value="{taxType}"
						   type="string"
						   />
				<parameter name="tax-document-date"
						   value="{taxDocumentDate}"
						   type="string"
						   />
				<parameter name="tax-document-number"
						   value="{taxDocumentNumber}"
						   type="string"
						   />
				<parameter name="tax-period1"
						   value="{taxPeriod1}"
						   type="string"
						   />
				<parameter name="tax-period2"
						   value="{taxPeriod2}"
						   type="string"
						   />
				<parameter name="tax-period3"
						   value="{taxPeriod3}"
						   type="string"
						   />
				<parameter name="from-account-type"
                           value="{fromAccountType}"
                           type="string"
                           />
				<parameter name="from-account-amount"
                           value="{fromAccountAmount}"
                           type="string"
                           />
				<parameter name="amount-currency"
				           value="{amountCurrency}"
				           type="string"
						/>
			</extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>

<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
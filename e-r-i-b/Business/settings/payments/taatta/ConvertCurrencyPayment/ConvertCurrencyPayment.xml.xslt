<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>
			
			<receiver-account>
				<xsl:value-of select="receiverAccountSelect"/>	
			</receiver-account>
			
			<sell-amount>
				<xsl:value-of select="sellAmount"/>
			</sell-amount>

			<extra-parameters>
				<parameter name="sell-amount-currency" 
						   value="{sellAmountCurrency}" 
						   type="string"
						   />

				<parameter name="buy-amount-currency" 
						   value="{buyAmountCurrency}" 
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
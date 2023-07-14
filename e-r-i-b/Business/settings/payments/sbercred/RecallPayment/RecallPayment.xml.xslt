<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
	<payment>
		<document-date>
			<xsl:value-of select="documentDate"/>
		</document-date>

		<amount>
			<xsl:value-of select="amount"/>
		</amount>

		<payer-account>
			<xsl:value-of select="payerAccountSelect"/>
		</payer-account>

		<extra-parameters>
			<parameter name="amount-currency"
					value="{amountCurrency}"
					type="string"/>

			<parameter name="recalled-document-number"
					value="{recalledDocumentNumber}"
					type="string"/>

			<parameter name="recalled-document-form-name"
					value="{recalled-document-form-name}"
					type="string"/>

			<parameter name="parent-id"
					value="{parentId}"
					type="string"/>
		</extra-parameters>
	</payment>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.

  <metaInformation>

  <scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>

  </metaInformation>

  -->

<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

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
				<parameter name="receiver-bank"
						   value="{receiverBank}"
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
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<extra-parameters>
			 <parameter name="receiver-key"
                   value="{receiverKey}"
                   type="string"
               />
				<xsl:apply-templates select="/form-data/*[name() != '']"/>
			</extra-parameters>
		</payment>
	</xsl:template>

	<!-- эти поля уже вставили -->
	<xsl:template match="/form-data/payerAccountSelect">
	</xsl:template>
	<xsl:template match="/form-data/receiverName"/>
	<xsl:template match="/form-data/amount"/>

	<!-- доп поля -->
	<xsl:template match="/form-data/*" priority="-99">
		<parameter name="{name()}" value="{text()}" type="string"/>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
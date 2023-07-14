<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<parameter name="deposit-id" value="{deposit}" type="string"/>
				<parameter name="deposit-description" value="{depositDescription}" type="string"/>
				<parameter name="contract-number" value="{contractNumber}" type="string"/>
				<parameter name="opening-date" value="{openingDate}" type="string"/>
				<parameter name="finishing-date" value="{finishingDate}" type="string"/>
				<parameter name="closing-date" value="{closingDate}" type="date"/>
				<parameter name="destination-account" value="{destination-account}" type="string"/>
				<parameter name="destination-amount" value="{destination-amount}" type="string"/>
				<parameter name="destination-amountCurrency" value="{destination-amountCurrency}" type="string"/>
				<parameter name="destination-accountType" value="{destination-accountType}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="xml" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
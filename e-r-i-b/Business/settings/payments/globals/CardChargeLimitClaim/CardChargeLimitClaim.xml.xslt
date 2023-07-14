<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<xsl:variable name="bcardId" select="baseCardNumber"/>
				<xsl:variable name="bcards"  select="document('cards.xml')/entity-list/entity[@key=$bcardId]"/>
				<xsl:variable name="baseCardType"   select="$bcards/field[@name='type']/text()"/>
				<xsl:variable name="acardId" select="additCardNumber"/>
				<xsl:variable name="acards"  select="document('cards.xml')/entity-list/entity[@key=$acardId]"/>
				<xsl:variable name="additCardType"   select="$acards/field[@name='type']/text()"/>
				<parameter name="base-card" value="{baseCardNumber}" type="string"/>
				<parameter name="addit-card" value="{additCardNumber}" type="string"/>
				<parameter name="base-card-type"  value="{$baseCardType}"  type="string"/>
				<parameter name="addit-card-type" value="{$additCardType}" type="string"/>
				<parameter name="account" value="{account}" type="string"/>
				<parameter name="owner-name" value="{ownerName}" type="string"/>
				<parameter name="full-name" value="{fullName}" type="string"/>
				<parameter name="phone" value="{phone}" type="string"/>
				<parameter name="limit" value="{limit}" type="string"/>
				<parameter name="amount" value="{amount}" type="string"/>
				<parameter name="amountv" value="{amountv}" type="string"/>
				<parameter name="period" value="{period}" type="string"/>
				<parameter name="currency" value="{currency}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="xml" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
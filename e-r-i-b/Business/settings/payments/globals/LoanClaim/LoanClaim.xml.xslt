<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<xsl:variable name="kinds" select="document('loan-product-kinds.xml')"/>
		<xsl:variable name="kind" select="kind"/>
		<xsl:variable name="kind-name" select="$kinds/loan-kinds/loan-kind[@id=$kind]/@name"/>

		<xsl:variable name="products" select="document('loan-products.xml')"/>
		<xsl:variable name="product" select="product"/>
		<xsl:variable name="product-name" select="$products/loan-products/loan-product[@id=$product]/@name"/>

		<xsl:variable name="offices" select="document('loan-offices.xml')"/>
		<xsl:variable name="office" select="office"/>
		<xsl:variable name="office-name" select="$offices/loan-offices/loan-office[@id=$office]/@name"/>

		<document>
			<extra-parameters>
				<parameter name="office-name" value="{$office-name}" type="string"/>
				<parameter name="product-name" value="{$product-name}" type="string"/>
				<parameter name="kind-name" value="{$kind-name}" type="string"/>
				<parameter name="bank-comment" value="{bank-comment}" type="string"/>
				<parameter name="rs-loan-number" value="{rs-loan-number}" type="string"/>
				<xsl:apply-templates select="/form-data/*[name() != '' and name() != 'product-name' and name() != 'kind-name' and name() != 'office-name']"/>
			</extra-parameters>
		</document>
	</xsl:template>
	<!-- доп поля -->
	<xsl:template match="/form-data/*" priority="-99">
		<parameter name="{name()}" value="{text()}" type="string"/>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="xml" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositClosingClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
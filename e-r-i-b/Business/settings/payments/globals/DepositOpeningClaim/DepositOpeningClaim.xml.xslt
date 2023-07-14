<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<payer-account>
				<xsl:value-of select="fromAccount"/>
			</payer-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="currency"/>
			</amount-currency>

			<extra-parameters>
				<xsl:variable name="data-path" select="''"/>
				<xsl:variable name="productId" select="product"/>
				<xsl:variable name="contribution" select="document(concat($data-path,'products.xml'))/products/product[@id=$productId]/@name"/>
				<xsl:variable name="officeId" select="department"/>
				<xsl:variable name="office" select="document('departments.xml')/entity-list/entity[@key=$officeId]/field[@name='fullName']/text()"/>

				<parameter name="full-name" value="{fullName}" type="string"/>
				<parameter name="visit-date" value="{visitDate}" type="date"/>
				<parameter name="office" value="{$office}" type="string"/>
				<parameter name="department" value="{department}" type="string"/>
				<parameter name="citizenship" value="{citizenship}" type="string"/>
				<parameter name="phone" value="{phone}" type="string"/>
				<parameter name="email" value="{email}" type="string"/>
				<parameter name="deposit-product-id" value="{product}" type="string"/>
				<parameter name="period" value="{period}" type="string"/>
				<parameter name="from-account-type" value="{fromAccountType}" type="string"/>
				<parameter name="is-renewal"        value="{radio}"          type="boolean"/>
				<parameter name="to-account" value="{toAccount}" type="string"/>
				<parameter name="to-account-type"   value="{toAccountType}"   type="string"/>
				<parameter name="min-amount" value="{minAmount}" type="string"/>
				<parameter name="account-type" value="{accountType}" type="string"/>
				<parameter name="contribution" value="{$contribution}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
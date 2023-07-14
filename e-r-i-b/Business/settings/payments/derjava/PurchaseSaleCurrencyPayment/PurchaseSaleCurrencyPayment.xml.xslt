<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>

			<receiver-account>
				<xsl:value-of select="toAccountSelect"/>
			</receiver-account>

			<amount>
				<xsl:value-of select="chargeOffAmount"/>
			</amount>

			<xsl:variable name="account"  select="fromAccountSelect"/>
			<xsl:variable name="currency" select="document('all-accounts.xml')/entity-list/entity[@key=$account]/field[@name='currencyCode']"/>

			<amount-currency>
				<xsl:value-of select="$currency"/>
			</amount-currency>

			<extra-parameters>
				<xsl:variable name="fromAccountSelectId" select="fromAccountSelect"/>
				<xsl:variable name="toAccountSelectId"   select="toAccountSelect"/>
				<!--Счет списания средств на покупку/продажу валюты-->
				<xsl:variable name="payerAccount"    select="document('all-accounts.xml')/entity-list/entity[@key=$fromAccountSelectId]"/>
				<!--Счет для зачисления средств полученных от покупки/продажи валюты-->
				<xsl:variable name="receiverAccount" select="document('all-accounts.xml')/entity-list/entity[@key=$toAccountSelectId]"/>

				<xsl:variable name="fromAccountType"      select="$payerAccount/field[@name='type']/text()"/>
				<xsl:variable name="fromAccountCurrency" select="$payerAccount/field[@name='currencyCode']/text()"/>
				<xsl:variable name="toAccountType"      select="$receiverAccount/field[@name='type']/text()"/>
				<xsl:variable name="toAccountCurrency" select="$receiverAccount/field[@name='currencyCode']/text()"/>

				<parameter name="buy-amount-currency" value="{$toAccountCurrency}" type="string"/>
				<parameter name="buy-amount" value="{destinationAmount}" type="string"/>
				<parameter name="course"           value="{currencyRate}"      type="string"/>
				<parameter name="rur-currency"        value="RUB"        type="string" />
				<parameter name="foreign-currency"     value="{foreignCurrency}"    type="string"/>
				<parameter name="payer-account-type"    value="{$fromAccountType}"    type="string"/>
				<parameter name="payer-account-currency" value="{$fromAccountCurrency}" type="string"/>
				<parameter name="receiver-account-type"   value="{$toAccountType}"        type="string"/>
                <parameter name="type" value="{type}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>

		<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
  <metaInformation>
  <scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
  </metaInformation>
  -->

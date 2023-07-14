<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-account>
				<xsl:value-of select="depositAccount"/>
			</payer-account>

			<receiver-account>
				<!--TODO ���� ���������� ������ ������� ����� ������������ �����������, � ��� ����� ����� ���������� ���������, � �� ��������� �� ������������-->
				<xsl:value-of select="account"/>
			</receiver-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>
				<xsl:value-of select="currency"/>
			</amount-currency>
			<extra-parameters>
				<xsl:variable name="data-path" select="''"/>
				<xsl:variable name="accountId" select="account"/>
				<xsl:variable name="payerAccount" select="document(concat($data-path,'active-accounts.xml'))/entity-list/entity[@key=$accountId]"/>
				<xsl:variable name="accountType" select="$payerAccount/field[@name='type']/text()"/>

				<parameter name="full-name" value="{fullName}" type="string"/>
				<parameter name="deposit-id" value="{deposit}" type="string"/>
				<parameter name="deposit-description" value="{depositDescription}" type="string"/>
				<parameter name="contract-number" value="{contractNumber}" type="string"/>
				<parameter name="opening-date" value="{openingDate}" type="string"/>
				<parameter name="finishing-date" value="{finishingDate}" type="string"/>
				<parameter name="min-balance" value="{minBalance}" type="string"/>
				<parameter name="account-type" value="{$accountType}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="xml" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->

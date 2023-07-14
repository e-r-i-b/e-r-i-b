<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
<!--
			<amount>0.00</amount>
-->
			<extra-parameters>
                <parameter name="deposit-account" value="{accountSelect}" type="string"/>
                <parameter name="amount-or-passbook" value="{closingAmountOrPassbookDuplicateRadio}" type="string"/>
				<parameter name="money-or-transfer" value="{moneyOrTransferToAccountRadio}" type="string"/>
                <parameter name="amount-currency" value="{amountCurrency}" type="string"/>
                <parameter name="account-number" value="{accountNumber}" type="string"/>
                <parameter name="deposit-account-link" value="{accountLink}" type="string"/>
                <parameter name="deposit-account-type" value="{accountSelectType}" type="string"/>
                <parameter name="from-account-name" value="{accountSelectName}" type="string"/>
				<parameter name="receiver-account" value="{toAccountSelect}" type="string"/>
				<parameter name="account-rest" value="{accountRest}" type="string"/>
			</extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data &#x2D;> xml" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->

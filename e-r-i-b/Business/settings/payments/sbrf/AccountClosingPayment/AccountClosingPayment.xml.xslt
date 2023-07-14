<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

            <document-number>
                <xsl:value-of select="documentNumber"/>
            </document-number>

            <payer-account>
				<xsl:value-of select="fromAccountSelect"/>
			</payer-account>

            <payer-name>
                <xsl:value-of select="fromAccountName"/>
            </payer-name>

            <amount>
                <xsl:value-of select="amount"/>
            </amount>

            <amount-currency>
                <xsl:value-of select="fromResourceCurrency"/>
            </amount-currency>

            <destination-amount>
                <xsl:value-of select="destinationAmount"/>
            </destination-amount>

            <destination-amount-currency>
                <xsl:value-of select="toResourceCurrency"/>
            </destination-amount-currency>

            <receiver-account>
                <xsl:value-of select="toAccountSelect"/>
            </receiver-account>

            <receiver-name>
                <xsl:value-of select="toAccountName"/>
            </receiver-name>

            <ground>
                <xsl:value-of select="ground"/>
            </ground>

            <exact-amount>
                <xsl:value-of select="'charge-off-field-exact'"/>
            </exact-amount>

            <extra-parameters>
                <parameter name="convertion-rate"             value="{course}"                    type="decimal"/>
                <parameter name="to-resource-type"            value="{toResourceType}"            type="string"/>
                <parameter name="to-resource-link"            value="{toResourceLink}"            type="string"/>
                <parameter name="recalculated-amount-changed" value="{recalculatedAmountChanged}" type="boolean"/>
                <parameter name="closing-date"                value="{closingDate}"               type="string"/>
                <parameter name="operation-code"              value="{operationCode}"             type="string"/>
                <parameter name="from-resource-type"          value="{fromResourceType}"          type="string"/>
                <parameter name="tarif-plan-code-type"        value="{tarifPlanCodeType}"         type="string"/>
                <parameter name="client-target-name"          value="{clientTargetName}"          type="string"/>
                <parameter name="client-target-comment"       value="{clientTargetNameComment}"   type="string"/>
                <parameter name="client-target-date"          value="{clientTargetDate}"          type="string"/>
                <parameter name="client-target-amount"        value="{clientTargetAmount}"        type="string"/>
                <parameter name="from-resource-code"          value="{fromResourceCode}"          type="string"/>
                <parameter name="premier-show-msg"            value="{premierShowMsg}"            type="string"/>
                <parameter name="standart-convertion-rate"    value="{standartCourse}"            type="decimal"/>
                <parameter name="from-personal-finance"       value="{fromPersonalFinance}"       type="boolean"/>
            </extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
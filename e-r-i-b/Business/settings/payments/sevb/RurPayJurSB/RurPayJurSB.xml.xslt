<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">

		<document>
			<document-date>
				<xsl:value-of select="documentDate"/>
			</document-date>

			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<amount-currency>RUB</amount-currency>

			<ground>
				<xsl:value-of select="ground"/>
			</ground>

			<xsl:variable name="receiverId" select="receiverId"/>
			<xsl:variable name="appointment" select="appointment"/>

			<!--<xsl:variable name="paymentReceiver"
			              select="document( concat(concat(concat('paymentReceiverJur.xml?ReceiverId=',$receiverId),'&amp;appointment='),$appointment) )"/>-->
			<receiver-account>
				<xsl:value-of select="receiverAccount"/>												
			</receiver-account>

			<receiver-name>				
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<extra-parameters>
				<parameter name="receiverId" value="{receiverId}" type="string"/>
				<parameter name="appointment" value="{appointment}" type="string"/>


				<parameter name="receiver-name" value="{receiverName}" type="string"/>
				<parameter name="operation-code" value="{operationCode}" type="string"/>
				<parameter name="receiver-kpp" value="{receiverKPP}" type="string"/>
				<parameter name="receiver-inn" value="{receiverINN}" type="string"/>
				<parameter name="receiver-bank" value="{receiverBankName}" type="string"/>
				<parameter name="receiver-bic" value="{receiverBIC}" type="string"/>
				<parameter name="receiver-cor-account" value="{receiverCorAccount}" type="string"/>
				<parameter name="receiver-description" value="{receiverDescription}" type="string"/>

				<parameter name="register-number" value="{register-number}" type="string"/>
				<parameter name="register-string" value="{register-string}" type="string"/>
				<parameter name="receiver-pay-type" value="{receivPayType}" type="string"/>


				<xsl:apply-templates select="/form-data/*[name() != '']"/>

				<parameter name="register-number" value="{register-number}" type="string"/>
				<parameter name="register-string" value="{register-string}" type="string"/>
				<!--<parameter name="receiver-pay-type" value="{receivPayType}" type="string"/>
				<parameter name="receiver-kpp"
				           value="{$paymentReceiver/entity-list/entity/field[@name='KPP']}" type="string"/>
				<parameter name="receiver-inn"
				           value="{$paymentReceiver/entity-list/entity/field[@name='INN']}" type="string"/>
				<parameter name="receiver-bank"
				           value="{$paymentReceiver/entity-list/entity/field[@name='bankName']}"
				           type="string"/>
				<parameter name="receiver-bic"
				           value="{$paymentReceiver/entity-list/entity/field[@name='bankCode']}"
				           type="string"/>
				<parameter name="receiver-cor-account"
				           value="{$paymentReceiver/entity-list/entity/field[@name='correspondentAccount']}"
				           type="string"/>
				<parameter name="receiver-description"
				           value="{$paymentReceiver/entity-list/entity/field[@name='receiverDescription']}"
				           type="string"/>-->
			</extra-parameters>
		</document>
	</xsl:template>

	<!-- доп поля -->
	<xsl:template match="/form-data/*" priority="-99">
		<parameter name="{name()}" value="{text()}" type="string"/>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
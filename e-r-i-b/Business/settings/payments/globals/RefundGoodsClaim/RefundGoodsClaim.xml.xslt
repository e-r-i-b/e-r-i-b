<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
	<document>
		<extra-parameters>
			<parameter name="recalled-document-payer-account" value="{payerAccountSelect}" type="string"/>
			<parameter name="recalled-document-amount" value="{amount}" type="string"/>
			<parameter name="recalled-document-amount-currency" value="{amountCurrency}" type="string"/>
			<parameter name="recalled-document-number" value="{recalledDocumentNumber}" type="string"/>
			<parameter name="recalled-document-form-name" value="{recalledDocumentFormName}" type="string"/>
			<parameter name="parent-id" value="{parentId}" type="string"/>
            <parameter name="refund-uuid" value="{refundUuid}" type="string"/>
			<parameter name="returned-goods" value="{returnedGoods}" type="string"/>
			<parameter name="external-document-id" value="{externalDocumentId}" type="string"/>
		</extra-parameters>
	</document>
	</xsl:template>
</xsl:stylesheet>
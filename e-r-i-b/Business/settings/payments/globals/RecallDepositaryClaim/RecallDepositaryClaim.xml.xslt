<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

            <extra-parameters>
                <parameter name="claim-external-id" value="{claimExternalId}" type="string"/>
                <parameter name="revoke-purpose" value="{revokePurpose}" type="string"/>

                <parameter name="document-type" value="{documentType}" type="string"/>
                <parameter name="document-form-name" value="{documentFormName}" type="string"/>
                <parameter name="recall-document-date" value="{recallDocumentDate}" type="string"/>
                <parameter name="depo-account" value="{recallDocumentDepoAccount}" type="string"/>
                <parameter name="deponent-fio" value="{deponentFIO}" type="string"/>
                <parameter name="recall-document-number" value="{recallDocumentNumber}" type="string"/>
                <parameter name="recall-document-id" value="{recallDocumentId}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
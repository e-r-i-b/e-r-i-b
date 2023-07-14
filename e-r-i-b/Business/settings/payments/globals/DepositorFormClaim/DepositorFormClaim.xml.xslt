<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

            <extra-parameters>
                
                <parameter name="depositor" value="{depositor}" type="string"/>
                <parameter name="depo-account" value="{depoAccount}" type="string"/>
                <parameter name="depo-external-id" value="{depoExternalId}" type="string"/>
                <parameter name="depo-id" value="{depoId}" type="string"/>
 			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
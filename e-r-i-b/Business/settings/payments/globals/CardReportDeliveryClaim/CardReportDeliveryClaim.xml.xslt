<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
            <extra-parameters>
                <parameter name="use-delivery"          value="{use}"          type="boolean"/>
                <parameter name="email-delivery"        value="{email}"        type="string"/>
                <parameter name="type-delivery"         value="{type}"         type="string"/>
                <parameter name="language-delivery"     value="{language}"     type="string"/>
            </extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
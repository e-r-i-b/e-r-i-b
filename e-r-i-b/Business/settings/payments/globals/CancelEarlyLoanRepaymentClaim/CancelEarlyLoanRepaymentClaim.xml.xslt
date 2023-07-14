<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
	<document>
		<extra-parameters>
            <parameter name="termination-request-id" value="{terminationRequestId}" type="string"/>
            <parameter name="parent-id" value="{parentId}" type="string"/>
		</extra-parameters>
	</document>
	</xsl:template>
</xsl:stylesheet>
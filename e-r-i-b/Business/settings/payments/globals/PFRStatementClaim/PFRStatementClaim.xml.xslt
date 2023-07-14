<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
            <receiver-name>
                <xsl:value-of select="'Пенсионный фонд РФ'"/>
            </receiver-name>

			<extra-parameters>
                <parameter name="SNILS" value="{SNILS}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
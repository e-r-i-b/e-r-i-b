<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="payer-account"/>
			</payer-account>
			<receiver-name>ОАО "МГТС"</receiver-name>
            <receiver-account>40702810800020106631</receiver-account>
			<receiver-bic>044525225</receiver-bic>
			<receiver-inn>7710016640</receiver-inn>

			<amount><xsl:value-of select="amount"/></amount>
			
			<ground>
                <xsl:value-of select="areaCode"/>
				<xsl:value-of select="phoneNumber"/>
                <xsl:value-of select="flatNumber"/>
            </ground>
			
			<extra-parameters>
				<parameter 
					name="areaCode"
					value="{areaCode}"
					type="string"
				/>

				<parameter 
					name="phoneNumber"
					value="{phoneNumber}"
					type="string"
				/>
                <parameter
					name="flatNumber"
					value="{flatNumber}"
					type="string"
				/>
            </extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>

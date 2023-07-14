<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="clientAccount"/>
			</payer-account>
			<receiver-name>
				<xsl:value-of select="tvProvider"/>	
			</receiver-name>
			<xsl:choose>
				<xsl:when test="tvProvider='NTV+'">
					<receiver-account>4568745697</receiver-account>
					<receiver-bic>045687</receiver-bic>				
				</xsl:when>
				<xsl:when test="tvProvider='KomkorTV'">
					<receiver-account>14568745697</receiver-account>
					<receiver-bic>0045687</receiver-bic>				
				</xsl:when>
                <xsl:when test="tvProvider='KosmosTV'">
					<receiver-account>57646789650</receiver-account>
					<receiver-bic>0025433</receiver-bic>
				</xsl:when>
                <xsl:otherwise>
					<receiver-account/>
					<receiver-bic/>
				</xsl:otherwise>					
			</xsl:choose>	
			<receiver-kpp>12345</receiver-kpp>
			<amount><xsl:value-of select="amount"/></amount>
			<ground>
				<xsl:value-of select="ground"/>	
			</ground>
			<extra-parameters>
				<parameter name="tvProvider" value="{tvProvider}" type="string"/>
                <parameter name="contractNumber" value="{contractNumber}" type="string"/>
            </extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>


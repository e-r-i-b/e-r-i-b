<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="accountsSelect"/>
			</payer-account>
			
			<receiver-name>
				<xsl:value-of select="providersSelect"/>	
			</receiver-name>

			<xsl:choose>
				<xsl:when test="providersSelect = 'MTS'">
					<receiver-account>4568745697</receiver-account>
					<receiver-bic>045687</receiver-bic>				
				</xsl:when>
				<xsl:when test="providersSelect = 'MEGAFON'">
					<receiver-account>14568745697</receiver-account>
					<receiver-bic>0045687</receiver-bic>				
				</xsl:when>
				<xsl:when test="providersSelect = 'BEELINE'">
					<receiver-account>24568745697</receiver-account>
					<receiver-bic>00045687</receiver-bic>				
				</xsl:when>
                <xsl:when test="providersSelect = 'SKYLINK'">
					<receiver-account>34567886540</receiver-account>
					<receiver-bic>0053677</receiver-bic>
				</xsl:when>
				<xsl:when test="providersSelect = 'CORBINAM'">
					<receiver-account>92759008367</receiver-account>
					<receiver-bic>00038566</receiver-bic>
				</xsl:when>
                <xsl:otherwise>
					<receiver-account/>
					<receiver-bic/>
				</xsl:otherwise>					
			</xsl:choose>	

			<receiver-kpp>12345</receiver-kpp>
			
			<amount><xsl:value-of select="amount"/></amount>
			
			<ground>
				<xsl:value-of select="substring(concat(providersSelect,'                  '),  1, 10)"/>
				<xsl:value-of select="areaCodesSelect"/>	
				<xsl:value-of select="phoneNumber"/>	
			</ground>
			
			<extra-parameters>
				<parameter 
					name="provider"
					value="{providersSelect}"
					type="string"
				/>
				<parameter 
					name="areaCode"
					value="{areaCodesSelect}"
					type="string"
				/>

				<parameter 
					name="phoneNumber"
					value="{phoneNumber}"
					type="string"
				/>
			</extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>

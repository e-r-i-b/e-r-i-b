<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="clientAccount"/>
			</payer-account>
			<receiver-name>
				<xsl:value-of select="internetProvider"/>	
			</receiver-name>
			<xsl:choose>
				<xsl:when test="internetProvider='Stream'">
					<receiver-account>2231458543</receiver-account>
					<receiver-bic>846390</receiver-bic>
				</xsl:when>
				<xsl:when test="internetProvider='Corbina'">
					<receiver-account>3279566831</receiver-account>
					<receiver-bic>006378</receiver-bic>
				</xsl:when>
                <xsl:when test="internetProvider='Zebra'">
					<receiver-account>64008234532</receiver-account>
					<receiver-bic>084648</receiver-bic>
				</xsl:when>
                <xsl:when test="internetProvider='ROL'">
					<receiver-account>0547437311</receiver-account>
					<receiver-bic>610085</receiver-bic>
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
				<parameter name="internetProvider" value="{internetProvider}" type="string"/>
				<!--<parameter name="clientAccount" value="{clientAccount}" type="string"/>-->
                <parameter name="numberClPayment" value="{numberClPayment}" type="string"/>
            </extra-parameters>
		</payment>
	</xsl:template>
</xsl:stylesheet>

<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
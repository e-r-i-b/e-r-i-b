<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<xsl:variable name="cardId" select="card"/>
				<xsl:variable name="cards" select="document('cards.xml')/entity-list/entity[@key=$cardId]"/>
				<xsl:variable name="cardType" select="$cards/field[@name='type']/text()"/>
				<parameter name="full-name" value="{fullName}"    type="string"/>
				<parameter name="phone"     value="{phone}"       type="string"/>
				<parameter name="card-type" value="{$cardType}"    type="string"/>
				<parameter name="card"      value="{card}"        type="string"/>
				<parameter name="account"   value="{account}"     type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
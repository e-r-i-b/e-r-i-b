<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<xsl:variable name="cardId" select="card"/>
				<xsl:variable name="cards" select="document('cards.xml')/entity-list/entity[@key=$cardId]"/>
				<xsl:variable name="cardType" select="$cards/field[@name='type']/text()"/>
				<xsl:variable name="externalId" select="$cards/field[@name='id']/text()"/>
				<parameter name="card" value="{card}" type="string"/>
				<parameter name="cardType"        value="{$cardType}"       type="string"/>
				<parameter name="anotherCardType" value="{anotherCardType}" type="string"/>
				<parameter name="currency" value="{currency}" type="string"/>
				<parameter name="reason" value="{reason}" type="string"/>
				<parameter name="account" value="{account}" type="string"/>
				<parameter name="externalId" value="{$externalId}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
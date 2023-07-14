<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<xsl:variable name="cardId" select="card"/>
				<xsl:variable name="cards" select="document('cards.xml')/entity-list/entity[@key=$cardId]"/>
				<xsl:variable name="cardType" select="$cards/field[@name='type']/text()"/>
				<parameter name="full-name"         value="{fullName}"        type="string"/>
				<parameter name="phone"             value="{phone}"           type="string"/>
				<parameter name="card"              value="{card}"            type="string"/>
				<parameter name="card-type"         value="{$cardType}"       type="string"/>
				<parameter name="action-type"       value="{actionType}"      type="string"/>
				<parameter name="expire-card-date"  value="{expireCardDate}"  type="string"/>
				<parameter name="expire-block-date" value="{expireBlockDate}" type="string"/>
				<parameter name="other"             value="{other}"           type="string"/>
				<parameter name="region1"           value="{region1}"         type="boolean"/>
				<parameter name="region2"           value="{region2}"         type="boolean"/>
				<parameter name="region3"           value="{region3}"         type="boolean"/>
				<parameter name="region4"           value="{region4}"         type="boolean"/>
				<parameter name="region5"           value="{region5}"         type="boolean"/>
				<parameter name="region6"           value="{region6}"         type="boolean"/>
				<parameter name="region7"           value="{region7}"         type="boolean"/>
				<parameter name="region8"           value="{region8}"         type="boolean"/>
				<parameter name="reason1"           value="{reason1}"         type="boolean"/>
				<parameter name="reason2"           value="{reason2}"         type="boolean"/>
				<parameter name="reason3"           value="{reason3}"         type="boolean"/>
				<parameter name="reason4"           value="{reason4}"         type="boolean"/>
				<parameter name="reason5"           value="{reason5}"         type="boolean"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
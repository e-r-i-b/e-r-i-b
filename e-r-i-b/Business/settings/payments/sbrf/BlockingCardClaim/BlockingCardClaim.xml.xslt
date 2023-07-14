<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<parameter name="full-name" value="{fullName}" type="string"/>
				<parameter name="card" value="{card}" type="string"/>
                <parameter name="card-link" value="{cardLink}" type="string"/>
				<parameter name="reason" value="{reason}" type="string"/>
				<parameter name="externalId" value="{externalId}" type="string"/>
                <parameter name="card-number" value="{cardNumber}" type="string"/>
                <parameter name="card-type" value="{cardType}" type="string"/>
                <parameter name="from-account-name" value="{cardName}" type="string"/>
				<parameter name="account" value="{account}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
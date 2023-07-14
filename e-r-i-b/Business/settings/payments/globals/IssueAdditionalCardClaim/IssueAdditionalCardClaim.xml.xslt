<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<xsl:variable name="cardId" select="cardNumber"/>
				<xsl:variable name="cards" select="document('cards.xml')/entity-list/entity[@key=$cardId]"/>
				<xsl:variable name="cardType" select="$cards/field[@name='type']/text()"/>
				<parameter name="card-number" value="{cardNumber}" type="string"/>
				<parameter name="account" value="{account}" type="string"/>
				<parameter name="card-type"   value="{$cardType}"   type="string"/>
				<parameter name="new-card-type"   value="{newCardType}"   type="string"/>
				<parameter name="urgency" value="{urgency}" type="string"/>

				<parameter name="full-name" value="{fullName}" type="string"/>
				<parameter name="nameInLatin" value="{nameInLatin}" type="string"/>
				<parameter name="surnameInLatin" value="{surnameInLatin}" type="string"/>
				<parameter name="birthDay" value="{birthDay}" type="string"/>
				<parameter name="status" value="{status}" type="string"/>
				<parameter name="gender" value="{gender}" type="string"/>
				<parameter name="citizen" value="{citizen}" type="string"/>
				<parameter name="inn" value="{inn}" type="string"/>
				<parameter name="code-word" value="{codeWord}" type="string"/>
				<parameter name="e-mail" value="{eMail}" type="string"/>

				<parameter name="passport-series" value="{passportSeries}" type="string"/>
				<parameter name="passport-number" value="{passportNumber}" type="string"/>
				<parameter name="passport-issue-date" value="{passportIssueDate}" type="string"/>
				<parameter name="passport-issue-by" value="{passportIssueBy}" type="string"/>
				<parameter name="registrationAddress" value="{registrationAddress}" type="string"/>
				<parameter name="registrationPhone" value="{registrationPhone}" type="string"/>

				<parameter name="residenceAddress" value="{residenceAddress}" type="string"/>
				<parameter name="residencePhone" value="{residencePhone}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
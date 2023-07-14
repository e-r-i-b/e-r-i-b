<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<parameter name="select-card" value="{selectCard}" type="string"/>
				<parameter name="select-design" value="{selectDesign}" type="string"/>
				<parameter name="currency" value="{currency}" type="string"/>
				<parameter name="select-urgency" value="{selectUrgency}" type="string"/>
				<parameter name="card-type" value="{cardType}" type="string"/>
				<parameter name="sms-inform" value="{smsInform}" type="string"/>
				<parameter name="currency" value="{currency}" type="string"/>

				<parameter name="nameInLatin" value="{nameInLatin}" type="string"/>
				<parameter name="surnameInLatin" value="{surnameInLatin}" type="string"/>
				<parameter name="gender" value="{gender}" type="string"/>
				<parameter name="inn" value="{inn}" type="string"/>
				<parameter name="code-word" value="{codeWord}" type="string"/>
				<parameter name="e-mail" value="{eMail}" type="string"/>
				<parameter name="citizen" value="{citizen}" type="string"/>
				<parameter name="status" value="{status}" type="string"/>

				<parameter name="full-name" value="{fullName}" type="string"/>
				<parameter name="birthDay" value="{birthDay}" type="string"/>

				<parameter name="registrationPostalCode" value="{registrationPostalCode}" type="string"/>
				<parameter name="registrationAddress" value="{registrationAddress}" type="string"/>
				<parameter name="registrationPhone" value="{registrationPhone}" type="string"/>

				<parameter name="residencePostalCode" value="{residencePostalCode}" type="string"/>
				<parameter name="residenceAddress" value="{residenceAddress}" type="string"/>
				<parameter name="residencePhone" value="{residencePhone}" type="string"/>

				<parameter name="passport-series" value="{passportSeries}" type="string"/>
				<parameter name="passport-number" value="{passportNumber}" type="string"/>
				<parameter name="passport-issue-date" value="{passportIssueDate}" type="string"/>
				<parameter name="passport-issue-by" value="{passportIssueBy}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
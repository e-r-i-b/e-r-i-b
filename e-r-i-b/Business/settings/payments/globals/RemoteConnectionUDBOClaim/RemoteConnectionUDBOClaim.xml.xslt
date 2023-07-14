<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
                <parameter name="userFullName"         value="{userFullName}"          type="string"/>
                <parameter name="firstName"            value="{firstName}"             type="string"/>
                <parameter name="surName"              value="{surName}"               type="string"/>
                <parameter name="patrName"             value="{patrName}"              type="string"/>
                <parameter name="gender"               value="{gender}"                type="string"/>
                <parameter name="inn"                  value="{inn}"                   type="string"/>
                <parameter name="isResident"           value="{isResident}"            type="string"/>
                <parameter name="address"              value="{address}"               type="string"/>
                <parameter name="registrationAddress-city" value="{registrationAddress-city}"   type="string"/>
                <parameter name="email"                value="{email}"                 type="string"/>
                <parameter name="region"               value="{region}"                type="string"/>
                <parameter name="office"               value="{office}"                type="string"/>
                <parameter name="branch"               value="{branch}"                type="string"/>
                <parameter name="passportIssuedBy"     value="{passportIssuedBy}"      type="string"/>
                <parameter name="mobileOperator"       value="{mobileOperator}"        type="string"/>
                <parameter name="birthDay"             value="{birthDay}"              type="string"/>
                <parameter name="birthDayString"       value="{birthDayString}"        type="string"/>
                <parameter name="citizenship"          value="{citizenship}"           type="string"/>
                <parameter name="birthPlace"           value="{birthPlace}"            type="string"/>
                <parameter name="cardNumber"           value="{cardNumber}"            type="string"/>
                <parameter name="mobilePhone"          value="{mobilePhone}"           type="string"/>
                <parameter name="passportIssueDate"    value="{passportIssueDate}"     type="string"/>
                <parameter name="passportSeries"       value="{passportSeries}"        type="string"/>
                <parameter name="passportNumber"       value="{passportNumber}"        type="string"/>
                <parameter name="identityType"         value="{identityType}"          type="string"/>
                <parameter name="identityTypeName"     value="{identityTypeName}"      type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
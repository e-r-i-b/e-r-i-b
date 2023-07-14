<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
				<parameter name="card"                      value="{cardLink}"         type="string"/>
                <parameter name="is-commission"             value="{reissueReason != 'F' and reissueReason != 'Y'}"     type="boolean"/>
                <parameter name="reason-code"               value="{reissueReason}"    type="string"/>
                <parameter name="card-number"               value="{cardNumber}"       type="string"/>
                <parameter name="destination-office-region" value="{officeCodeRegion}" type="string"/>
                <parameter name="destination-office-branch" value="{officeCodeBranch}" type="string"/>
                <parameter name="destination-office-office" value="{officeCodeOffice}" type="string"/>
                <parameter name="destination-office-name"   value="{officeName}"       type="string"/>
                <parameter name="destination-office-address" value="{officeAddress}"   type="string"/>
                <parameter name="source-office-region"      value="{srcOfficeCodeRegion}" type="string"/>
                <parameter name="source-office-branch"      value="{srcOfficeCodeBranch}" type="string"/>
                <parameter name="source-office-office"      value="{srcOfficeCodeOffice}" type="string"/>
                <parameter name="office-info"               value="{placeForGetCard}"  type="string"/>
                <parameter name="card-block-reason"         value="{blockReason}"      type="string"/>
                <parameter name="card-name"                 value="{cardName}"         type="string"/>
                <parameter name="card-type"                 value="{cardType}"         type="string"/>
                <parameter name="card-type-name"            value="{cardTypeName}"     type="string"/>
                <parameter name="card-currency"             value="{cardCurrency}"     type="string"/>
                <parameter name="issue-date"                value="{issueDate}"        type="string"/>
                <parameter name="displayed-expire-date"      value="{displayedExpireDate}" type="string"/>
                <parameter name="expire-date"               value="{expireDate}"       type="string"/>
                <parameter name="user-first-name"           value="{userFirstName}"    type="string"/>
                <parameter name="user-sur-name"             value="{userSurname}"      type="string"/>
                <parameter name="user-patr-name"            value="{userPatrName}"     type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
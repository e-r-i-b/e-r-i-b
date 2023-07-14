<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>

			<destination-amount-currency>
                <xsl:value-of select="currency"/>
            </destination-amount-currency>

			<destination-amount>
                <xsl:value-of select="amount"/>
            </destination-amount>

            <exact-amount>
                <xsl:value-of select="'destination-field-exact'"/>
            </exact-amount>

            <receiver-name>
                <xsl:value-of select="'Сбербанк России'"/>
            </receiver-name>

            <extra-parameters>
                <parameter name="income" value="{income}" type="long"/>
				<parameter name="credit-card" value="{creditCard}" type="string"/>
                <parameter name="creditAmount" value="{creditAmount}" type="string"/>
                <parameter name="surName" value="{surName}" type="string"/>
                <parameter name="firstName" value="{firstName}" type="string"/>
                <parameter name="patrName" value="{patrName}" type="string"/>
                <parameter name="homePhone" value="{homePhone}" type="string"/>
                <parameter name="workPhone" value="{workPhone}" type="string"/>
                <parameter name="mobilePhone" value="{mobilePhone}" type="string"/>
                <parameter name="email" value="{email}" type="string"/>
                <parameter name="freeTime" value="{freeTime}" type="string"/>
                <parameter name="passport-number" value="{passport-number}" type="string"/>
                <parameter name="passport-series" value="{passport-series}" type="string"/>
                <parameter name="loan" value="{loan}" type="long"/>
                <parameter name="interest-rate" value="{interest-rate}" type="string"/>
                <parameter name="first-year-service" value="{first-year-service}" type="string"/>
                <parameter name="next-year-service" value="{next-year-service}" type="string"/>
                <parameter name="first-year-service-currency" value="{first-year-service-currency}" type="string"/>
                <parameter name="next-year-service-currency" value="{next-year-service-currency}" type="string"/>                 
                <parameter name="min-limit" value="{min-limit}" type="string"/>
                <parameter name="max-limit" value="{max-limit}" type="string"/>
                <parameter name="max-limit-include" value="{max-limit-include}" type="string"/>
                <parameter name="additionalTerms" value="{additionalTerms}" type="string"/>
                <parameter name="grace-period-duration" value="{grace-period-duration}" type="string"/>
                <parameter name="grace-period-interest-rate" value="{grace-period-interest-rate}" type="string"/>
                <parameter name="tb" value="{tb}" type="string"/>
                <parameter name="card-number" value="{card-number}" type="string"/>
                <parameter name="changeDate" value="{changeDate}" type="long"/>
                <parameter name="cardProductId" value="{cardProductId}" type="long"/>
			</extra-parameters>

        </document>
    </xsl:template>

</xsl:stylesheet>

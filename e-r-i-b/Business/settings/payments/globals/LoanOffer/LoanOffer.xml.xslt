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
                <xsl:value-of select="exactAmount"/>
            </exact-amount>

            <receiver-name>
                <xsl:value-of select="'Сбербанк России'"/>
            </receiver-name>

            <extra-parameters>
				<parameter name="type-of-credit" value="{creditType}" type="string"/>
                <parameter name="passport-number" value="{passport-number}" type="string"/>
                <parameter name="passport-series" value="{passport-series}" type="string"/>
                <parameter name="creditAmount" value="{creditAmount}" type="string"/>
                <parameter name="duration" value="{duration}" type="long"/>
                <parameter name="surName" value="{surName}" type="string"/>
                <parameter name="firstName" value="{firstName}" type="string"/>
                <parameter name="patrName" value="{patrName}" type="string"/>
                <parameter name="homePhone" value="{homePhone}" type="string"/>
                <parameter name="workPhone" value="{workPhone}" type="string"/>
                <parameter name="mobilePhone" value="{mobilePhone}" type="string"/>
                <parameter name="email" value="{email}" type="string"/>
                <parameter name="freeTime" value="{freeTime}" type="string"/>
                <parameter name="loan" value="{loan}" type="string"/>
                <parameter name="tb" value="{tb}" type="string"/>
                <parameter name="hirer" value="{hirer}" type="string"/>
                <parameter name="averageIncomePerMonth" value="{averageIncomePerMonth}" type="string"/>
                <parameter name="getPaidOnAccount" value="{getPaidOnAccount}" type="boolean"/>
			</extra-parameters>

        </document>
    </xsl:template>

</xsl:stylesheet>

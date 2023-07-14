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
				<parameter name="credit-card" value="{creditCard}" type="string"/>
                <parameter name="surName" value="{surName}" type="string"/>
                <parameter name="firstName" value="{firstName}" type="string"/>
                <parameter name="patrName" value="{patrName}" type="string"/>
                <parameter name="freeTime" value="{freeTime}" type="string"/>
                <parameter name="loan" value="{loan}" type="long"/>
                <parameter name="offer-id" value="{offerId}" type="string"/>
                <parameter name="offer-type" value="{offerType}" type="long"/>
                <parameter name="interest-rate"  value="{interestRate}" type="string"/>
                <parameter name="first-year-service" value="{first-year-service}" type="string"/>
                <parameter name="next-year-service" value="{next-year-service}" type="string"/>
                <parameter name="first-year-service-currency" value="{first-year-service-currency}" type="string"/>
                <parameter name="next-year-service-currency" value="{next-year-service-currency}" type="string"/>
                <parameter name="Id-way" value="{idWay}" type="string"/>
                <parameter name="tb" value="{tb}" type="string"/>
                <parameter name="osb" value="{osb}" type="string"/>
                <parameter name="vsp" value="{vsp}" type="string"/>
                <parameter name="tb-offer" value="{tbOffer}" type="string"/>
                <parameter name="osb-offer" value="{osbOffer}" type="string"/>
                <parameter name="vsp-offer" value="{vspOffer}" type="string"/>
                <parameter name="passport-number" value="{passportNumber}" type="string"/>
                <parameter name="card-number" value="{card-number}" type="string"/>
                <parameter name="changeLimit" value="{changeLimit}" type="string"/>
                <parameter name="credit-card-office" value="{credit-card-office}" type="string"/>
                <parameter name="impliesOperation" value="{impliesOperation}" type="boolean"/>            
                <parameter name="card-type-code" value="{cardTypeCode}" type="string"/>            
			</extra-parameters>

        </document>
    </xsl:template>

</xsl:stylesheet>

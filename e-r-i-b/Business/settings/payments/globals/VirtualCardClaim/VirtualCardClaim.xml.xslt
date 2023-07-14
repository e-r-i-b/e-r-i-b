<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
        <document>
            
            <exact-amount>
                <xsl:value-of select="exactAmount"/>
            </exact-amount>

            <receiver-name>
                <xsl:value-of select="'Сбербанк России'"/>
            </receiver-name>

            <extra-parameters>
				<parameter name="сardProduct" value="{сardProduct}" type="string"/>
                <parameter name="cardProductId" value="{cardProductId}" type="long"/>
                <parameter name="kindCardProduct" value="{kindCardProduct}" type="long"/>
                <parameter name="subKindCardProduct" value="{subKindCardProduct}" type="long"/>
                <parameter name="currencyCodeCardProduct" value="{currencyCodeCardProduct}" type="string"/>
                <parameter name="currencyNameCardProduct" value="{currencyNameCardProduct}" type="string"/>
                <parameter name="surName" value="{surName}" type="string"/>
                <parameter name="firstName" value="{firstName}" type="string"/>
                <parameter name="patrName" value="{patrName}" type="string"/>
                <parameter name="mobilePhone" value="{mobilePhone}" type="string"/>
                <parameter name="mobileOperator" value="{mobileOperator}" type="string"/>
                <parameter name="mobileTariff" value="{mobileTariff}" type="string"/>
                <parameter name="codeMobileOperator" value="{codeMobileOperator}" type="string"/>
                <parameter name="card-number" value="{card-number}" type="string"/> 
			</extra-parameters>

        </document>
    </xsl:template>

</xsl:stylesheet>

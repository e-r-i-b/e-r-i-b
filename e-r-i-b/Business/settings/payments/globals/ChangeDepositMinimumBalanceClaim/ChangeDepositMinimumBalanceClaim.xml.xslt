<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output version="1.0" indent="yes" encoding="windows-1251" method="xml"/>

    <xsl:template match="/form-data">
        <document>
            <extra-parameters>
                <parameter name="account-id"                   value="{accountId}"                 type="long"/>
                <parameter name="interest-rate"                value="{interestRate}"              type="string"/>
                <parameter name="account-number"               value="{accountNumber}"             type="string"/>
                <parameter name="account-description"          value="{accountDescription}"        type="string"/>
                <parameter name="min-deposit-balance"          value="{minDepositBalance}"         type="decimal"/>
                <parameter name="min-deposit-balance-currency" value="{minDepositBalanceCurrency}" type="string"/>
            </extra-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>
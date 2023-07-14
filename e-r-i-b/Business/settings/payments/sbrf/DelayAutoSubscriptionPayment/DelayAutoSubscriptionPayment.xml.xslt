<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:template match="/form-data">
    <document>
        <!-- оставляем для того, чтоб не сеттилось null  -->
        <receiver-name>
            <xsl:value-of select="receiverName"/>
        </receiver-name>
        <!-- оставляем для того, чтоб не сеттилось null  -->
        <receiver-account>
            <xsl:value-of select="receiverAccount"/>
        </receiver-account>

        <payer-account>
            <xsl:value-of select="fromAccountSelect"/>
        </payer-account>

        <exact-amount>
            <xsl:value-of select="'destination-field-exact'"/>
        </exact-amount>

        <extra-parameters>
            <parameter name="auto-sub-number"                       value="{autoSubNumber}"                     type="string"/>
            <parameter name="reason-for-additional-confirm"         value="{reasonForAdditionalConfirm}"        type="string"/>
            <xsl:if test="autoSubType = 'ALWAYS'">
                <parameter name="auto-sub-next-pay-date"        value="{nextPayDateAlways}"         type="date"/>
            </xsl:if>
            <xsl:if test="autoSubType = 'INVOICE'">
                <parameter name="auto-sub-next-pay-date"        value="{nextPayDateInvoice}"        type="date"/>
            </xsl:if>
        </extra-parameters>
    </document>
    </xsl:template>
</xsl:stylesheet>
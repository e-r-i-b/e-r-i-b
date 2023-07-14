<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>

    <xsl:template match="/form-data">
        <document>
            <extra-parameters>
                <!-- Идентификатор предодобренного кредитного предложения -->
                <parameter name="loanOfferId"       value="{loanOfferId}"       type="string"/>
                <parameter name="condId"            value="{condId}"            type="long"/>
                <parameter name="condCurrId"        value="{condCurrId}"        type="long"/>

                <parameter name="ensuring"          value="{ensuring}"          type="string"/>
                <parameter name="productPeriod"     value="{productPeriod}"     type="string"/>
                <parameter name="loanPeriod"        value="{loanPeriod}"        type="long"/>
                <parameter name="additionalInfo"    value="{additionalInfo}"    type="string"/>
                <parameter name="loanAmount"        value="{loanAmount}"        type="decimal"/>
                <parameter name="loanCurrency"      value="{loanCurrency}"      type="string"/>
                <parameter name="loanRate"          value="{loanRate}"          type="decimal"/>

                <!-- Краткая информация -->
                <parameter name="fio"               value="{fio}"               type="string"/>
                <parameter name="jobLocation"       value="{jobLocation}"       type="string"/>
                <parameter name="phoneNumber"       value="{phoneNumber}"       type="string"/>
                <parameter name="incomePerMonth"    value="{incomePerMonth}"    type="string"/>
                <parameter name="processingEnabled" value="{processingEnabled}" type="boolean"/>

                <parameter name="onlyShortClaim"    value="{onlyShortClaim}"    type="boolean"/>

            </extra-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>
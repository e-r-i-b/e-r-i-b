<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

            <extra-parameters>
                <parameter name="client-fio"          value="{client-fio}" type="string"/>
                <parameter name="account-number"      value="{account-number}" type="string"/>
                <parameter name="amount"              value="{amount}" type="string"/>
                <parameter name="authorisation-code"  value="{authorisation-code}" type="string"/>
                <parameter name="e-mail"              value="{e-mail}" type="string"/>
                <parameter name="debit"               value="{debit}" type="string"/>
                <parameter name="send-operation-date" value="{send-operation-date}" type="date"/>
            </extra-parameters>

		</document>
	</xsl:template>
</xsl:stylesheet>
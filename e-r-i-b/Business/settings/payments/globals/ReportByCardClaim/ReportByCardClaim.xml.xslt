<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
			<extra-parameters>
                <parameter name="e-mail"             value="{e-mail}"             type="string"/>
                <parameter name="report-format"      value="{report-format}"      type="string"/>
                <parameter name="report-lang"        value="{report-lang}"        type="string"/>
                <parameter name="report-order-type"  value="{report-order-type}"  type="string"/>
                <parameter name="report-start-date-formated"  value="{report-start-date-formated}"  type="string"/>
                <parameter name="report-end-date-formated"    value="{report-end-date-formated}"    type="string"/>
                <parameter name="report-card-number" value="{report-card-number}" type="string"/>
                <parameter name="report-card-name"   value="{report-card-name}" type="string"/>
                <parameter name="report-card-contract" value="{report-card-contract}" type="string"/>
                <parameter name="report-card-id"     value="{report-card-id}"     type="long"/>
                <parameter name="report-start-date"  value="{report-start-date}"  type="date"/>
                <parameter name="report-end-date"    value="{report-end-date}"    type="date"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
    <xsl:template match="/form-data">
        <payment>
            <extra-parameters>
				<parameter name="long-offer-id"     value="{longOfferId}"       type="string"/>
			</extra-parameters>
        </payment>
    </xsl:template>
</xsl:stylesheet>
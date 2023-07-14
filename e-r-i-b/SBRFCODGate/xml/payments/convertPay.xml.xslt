<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="payment">
		<executePayment_q>
            <clientId>12</clientId>

            <alias>
                <xsl:value-of select="receiv-pay-alias"/>
            </alias>

            <sum>
                <xsl:value-of select="amount"/>
            </sum>

            <commision></commision>

            <debitAccount>
                <xsl:value-of select="payer-account"/>
            </debitAccount>

            <purpuce>
                <xsl:value-of select="ground"/>
            </purpuce>

            <beneficiar>
                <xsl:value-of select="receiverName"/>
            </beneficiar>

            <specialClient>
              <specialClientNplat></specialClientNplat>
              <vvodNumber></vvodNumber>
                <xsl:for-each select="./extra-parameters/parameter">
                  <xsl:variable name="parametr" select="./@name"/>
                  <xsl:if test="substring($parametr,1,9)='addField_'">
                   <requisites>
                     <name><xsl:value-of select="./@name"/></name>
                     <field>
                       <enteredData><xsl:value-of select="./@value"/></enteredData>
                     </field>
                   </requisites>
                  </xsl:if>
	            </xsl:for-each>
           </specialClient>
        </executePayment_q>
	</xsl:template>
</xsl:stylesheet>
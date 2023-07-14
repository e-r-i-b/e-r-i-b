<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>

	<xsl:template match="/form-data">

		<payment>

			<payer-account>
				<xsl:value-of select="accountsSelect"/>
			</payer-account>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<receiver-inn>
				<xsl:value-of select="receiverINN"/>
			</receiver-inn>

			<receiver-account>
				<xsl:value-of select="receiverAccount"/>
			</receiver-account>

			<receiver-bic>
				<xsl:value-of select="receiverBIC"/>
			</receiver-bic>

			<receiver-cor-account>
				<xsl:value-of select="receiverCorAccount"/>
			</receiver-cor-account>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>

			<ground>
			</ground>

			<extra-parameters>
				<parameter name="code1Select" value="{code1Select}" type="string"/>
				<parameter name="code2Select" value="{code2Select}" type="string"/>
				<parameter name="receiver-bank" value="{bankName}" type="string"/>
				<parameter name="abonentBook" value="{abonentBook}" type="string"/>
				<parameter name="abonentNumber" value="{abonentNumber}" type="string"/>
				<parameter name="abonentCD" value="{abonentCD}" type="string"/>
				<parameter name="abonentName" value="{abonentName}" type="string"/>
				<parameter name="abonentStreet" value="{abonentStreet}" type="string"/>
				<parameter name="abonentHouse" value="{abonentHouse}" type="string"/>
				<parameter name="abonentBuilding" value="{abonentBuilding}" type="string"/>
				<parameter name="abonentFlat" value="{abonentFlat}" type="string"/>
				<parameter name="counterCurr" value="{counterCurr}" type="string"/>
				<parameter name="counterPrev" value="{counterPrev}" type="string"/>
				<parameter name="consumption" value="{consumption}" type="string"/>
				<parameter name="counterPrev" value="{counterPrev}" type="string"/>
				<parameter name="tarif" value="{tarif}" type="string"/>
				<parameter name="dayPartSelect" value="{dayPartSelect}" type="string"/>
				<parameter name="monthSelect" value="{monthSelect}" type="string"/>
				<parameter name="yearSelect" value="{yearSelect}" type="string"/>
			</extra-parameters>

		</payment>

	</xsl:template>

</xsl:stylesheet>

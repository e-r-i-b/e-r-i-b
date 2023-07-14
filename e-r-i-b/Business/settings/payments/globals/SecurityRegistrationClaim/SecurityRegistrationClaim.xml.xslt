<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

            <extra-parameters>

                <parameter name="issuer" value="{issuer}" type="string"/>
                <parameter name="manager-fio" value="{managerFIO}" type="string"/>
                <parameter name="depositor" value="{depositor}" type="string"/>
                <parameter name="depo-account" value="{depoAccount}" type="string"/>
                <parameter name="depo-external-id" value="{depoExternalId}" type="string"/>
                <parameter name="security-name" value="{securityName}" type="string"/>
                <parameter name="security-number" value="{securityNumber}" type="string"/>
                <parameter name="inside-code" value="{insideCode}" type="string"/>
                <parameter name="corr-depositary" value="{corrDepositary}" type="string"/>
                <parameter name="depositary-operation" value="{depositaryOperation}" type="boolean"/>
                <parameter name="deposit-operation" value="{depositOperation}" type="boolean"/>
                <parameter name="account-operation" value="{accountOperation}" type="boolean"/>
                <parameter name="client-operation" value="{clientOperation}" type="boolean"/>
                <parameter name="trade-operation" value="{tradeOperation}" type="boolean"/>
                <parameter name="client-operation-desc" value="{clientOperationDesc}" type="string"/>

			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
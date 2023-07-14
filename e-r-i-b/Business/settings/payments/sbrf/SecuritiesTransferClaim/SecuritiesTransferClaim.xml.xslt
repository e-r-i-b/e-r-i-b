<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>

            <extra-parameters>

                <parameter name="operation-type" value="{operationType}" type="string"/>
                <parameter name="operation-sub-type" value="{operationSubType}" type="string"/>
                <parameter name="depositor" value="{depositor}" type="string"/>
                <parameter name="operation-initiator" value="{operationInitiator}" type="string"/>
                <parameter name="manager-fio" value="{managerFIO}" type="string"/>
                <parameter name="depo-account" value="{depoAccount}" type="string"/>
                <parameter name="registration-number" value="{registrationNumber}" type="string"/>                
                <parameter name="security-name" value="{securityName}" type="string"/>
                <parameter name="inside-code" value="{insideCode}" type="string"/>
                <parameter name="storage-method" value="{storageMethod}" type="string"/>
                <parameter name="security-count" value="{securityCount}" type="string"/>               
                <parameter name="currency-code" value="{currencyCode}" type="string"/>
                <parameter name="operation-reason" value="{operationReason}" type="string"/>
                <parameter name="count-of-sheet" value="{countOfSheet}" type="string"/>
                <parameter name="corr-depositary" value="{corrDepositary}" type="string"/>
                <parameter name="corr-depo-account" value="{corrDepoAccount}" type="string"/>
                <parameter name="corr-depo-account-owner" value="{corrDepoAccountOwner}" type="string"/>
                <parameter name="additional-info" value="{additionalInfo}" type="string"/>
                <parameter name="operation-desc" value="{operationDesc}" type="string"/>                
                <parameter name="delivery-type" value="{deliveryType}" type="string"/>
                <parameter name="nominal-amount" value="{nominalAmount}" type="string"/>
                <parameter name="division-number" value="{divisionNumber}" type="string"/>
                <parameter name="division-type" value="{divisionType}" type="string"/>
                <parameter name="depo-external-id" value="{depoExternalId}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
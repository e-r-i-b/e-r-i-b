<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<document>
            <extra-parameters>
                <parameter name="account-code" value="{accountCode}" type="string"/>
                <parameter name="card"         value="{cardLink}"    type="string"/>
                <parameter name="interest-card-number" value="{interestCardNumber}" type="string"/>
                <parameter name="interest-destination-source" value="{interestDestinationSource}" type="string"/>
			</extra-parameters>
		</document>
	</xsl:template>
</xsl:stylesheet>
        <!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
        <metaInformation>
        <scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
        </metaInformation>
        -->
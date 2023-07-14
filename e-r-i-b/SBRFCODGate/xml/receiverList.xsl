<?xml version='1.0'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>

<xsl:param name="BIC"/>
<xsl:param name="CorAccount"/>
<xsl:param name="INN"/>

<xsl:template match="recipientList_a">
	<table cellspacing="0" cellpadding="0" width="100%" class="userTab">
    	<tr style="height:20px" id="tableTitle" class="titleTable">
			<td>&#xA0;</td>
            <td>Наименование</td>
			<td>Идентификатор</td>
		</tr>
	<xsl:apply-templates select="Recipient"/>
	</table>
</xsl:template>

<xsl:template match="Recipient">
	<xsl:variable name="name" select="name"/>
	<xsl:variable name="uniqueNumber" select="uniqueNumber"/>
	<tr onclick="selectRow(this);"
		ondblclick="sendData();">
		<td class="ListItem">
			<input type="radio"
			name="selectedId"
			value="{$name}|*|{$uniqueNumber}"
            style="border:none">
            <xsl:if test="position()=1">
			    <xsl:attribute name="checked"/>
			</xsl:if>
		    </input>
		</td>
		<td class="listItem">
			<xsl:value-of select="$name"/>
		</td>
		<td class="listItem">
			<xsl:value-of select="$uniqueNumber"/>
		</td>
	</tr>
</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\src\com\rssl\phizicgate\sbrf\ws\mock\xml\recipientList_a.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
--><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\bin\com\rssl\phizicgate\sbrf\ws\mock\xml\recipientList_a.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
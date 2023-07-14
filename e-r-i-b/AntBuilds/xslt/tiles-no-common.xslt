<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:date="xalan://java.util.Date"
	xmlns:exsl="http://exslt.org/functions"
	extension-element-prefixes="exsl"
	>
<xsl:output
		method="xml"
		indent="yes"
		encoding="windows-1251"
		doctype-public="-//Apache Software Foundation//DTD Tiles Configuration 1.1//EN"
		doctype-system="http://jakarta.apache.org/struts/dtds/tiles-config_1_1.dtd"
		/>
<xsl:param name="overrideFile" select="''"/>

<exsl:function name="exsl:key">
	<xsl:param name="key"/>
	<xsl:param name="value"/>
	<xsl:param name="context"/>

	<xsl:for-each select="$context">
		<exsl:result select="key($key,$value)"/>
	</xsl:for-each>
</exsl:function>

<xsl:strip-space elements="*"/>

<xsl:key name="definitionKey" match="/tiles-definitions/definition" use="@name"/>
<xsl:key name="putKey" match="/tiles-definitions/definition/put" use="concat(parent::*/@name,'/',@name)"/>

<xsl:variable name="global"   select="."/>

<xsl:variable name="override" select="document($overrideFile)"/>

<xsl:template match="/">
<xsl:comment>
##############################################################################################################
# DON'T EDIT THIS FILE
# НЕ РЕДАКТИРУЙТЕ ЭТОТ ФАЙЛ
# ВСЕ ИЗМЕНЕНИЯ ВСЕ РАВНО НЕ СОХРАНЯТСЯ
#
# Generated: <xsl:value-of select="date:new()"/>
# Global   : config/global-tiles-definitions.xml
# Override : <xsl:value-of select="$overrideFile"/>
##############################################################################################################
</xsl:comment>

<xsl:apply-templates select="$override//definition" mode="validate"/>
<xsl:apply-templates/>
</xsl:template>

<xsl:template match="/tiles-definitions">
	<xsl:copy>
		<xsl:comment>##### from config/global-tiles-definitions.xml ####</xsl:comment>
		<xsl:apply-templates select="$global//definition"/>
	</xsl:copy>
</xsl:template>

<!-- шаблоны -->
<xsl:template match="/tiles-definitions/definition">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'definitionKey'"/>
		<xsl:with-param name="value" select="@name"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/tiles-definitions/definition" mode="validate">
	<xsl:call-template name="validateOverride">
		<xsl:with-param name="key" select="'definitionKey'"/>
		<xsl:with-param name="value" select="@name"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/tiles-definitions/definition/put">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'putKey'"/>
		<xsl:with-param name="value" select="concat(parent::*/@name,'/',@name)"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="comment()">
	<xsl:comment><xsl:value-of select="."/></xsl:comment>
</xsl:template>

<xsl:template name="processOverride">
	<xsl:param name="key"/>
	<xsl:param name="value"/>

	<xsl:variable name="contextEmem" select="."/>
	<xsl:variable name="overridedElem" select="exsl:key($key,$value,$override)"/>

	<!--<xsl:message>-->
		<!--key      : <xsl:value-of select="$key"/> -->
		<!--value    : <xsl:value-of select="$value"/>-->
		<!--overrided: <xsl:value-of select="boolean($overridedElem)"/>-->
	<!--</xsl:message>-->

	<xsl:if test="boolean($overridedElem)">
		<xsl:comment><xsl:value-of select="$overrideFile"/></xsl:comment>
		<xsl:for-each select="$overridedElem">
			<xsl:copy>
				<xsl:apply-templates select="./@*"/>
				<xsl:apply-templates select="$contextEmem/*"/>
			</xsl:copy>
		</xsl:for-each>
	</xsl:if>

	<xsl:if test="not(boolean($overridedElem))">
		<xsl:copy-of select="."/>
	</xsl:if>
</xsl:template>

<xsl:template match="*/@*" priority="-99">
	<xsl:attribute name="{name()}"><xsl:value-of select="."/></xsl:attribute>
</xsl:template>

<xsl:template name="validateOverride">
	<xsl:param name="key"/>
	<xsl:param name="value"/>

	<xsl:variable name="globalElem" select="exsl:key($key, $value, $global)"/>

	<xsl:if test="not(boolean($globalElem))">
		<xsl:message terminate="yes">
##############################################################################################################
# В global-tiles-definitions.xml не определен
# <xsl:value-of select="$key"/>:<xsl:value-of select="$value"/>
# Убедитесь в наличии этого элемента.
# Выполнение прервано в соответствии с правилами описанными в документе "Работа с конфигурациями"
##############################################################################################################
		</xsl:message>
	</xsl:if>

</xsl:template>

</xsl:stylesheet><!-- Stylesheet edited using Stylus Studio - (c) 2004-2005. Progress Software Corporation. All rights reserved. --><!-- Stylesheet edited using Stylus Studio - (c) 2004-2005. Progress Software Corporation. All rights reserved. --><!-- Stylesheet edited using Stylus Studio - (c) 2004-2005. Progress Software Corporation. All rights reserved. --><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="..\..\WebMobile\web\WEB&#x2D;INF\config\global&#x2D;tiles&#x2D;definitions.xml" htmlbaseurl="" outputurl="..\..\..\..\Documents and Settings\rydvanskiy\Desktop\xslt\tiles&#x2D;definitions.xml" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
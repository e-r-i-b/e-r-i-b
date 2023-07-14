<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:axsl="file://namespace.alias">
	<xsl:output method="xml" version="1.0" indent="yes"/>
	<xsl:namespace-alias stylesheet-prefix="axsl" result-prefix="xsl"/>

	<!-- шаблон для создания контрола для ввода денех -->
	<xsl:template name="moneybox" match="field[@type='money']" mode="control">
		<xsl:param name="name" select="@name"/>
		<xsl:param name="readOnly" select="false()"/>

		<xsl:if test="$readOnly">
			<axsl:value-of select="{$name}"/>
		</xsl:if>
		
		<xsl:if test="not($readOnly)">
			<axsl:variable name="value" select="format-number({$name}, '0.00')"/>
			<input type="text" name="{$name}" size="10">
				<axsl:attribute name="value">
					<axsl:choose>
						<axsl:when test="$value = 'NaN'"></axsl:when>
						<axsl:otherwise><axsl:value-of select="$value"/></axsl:otherwise>
					</axsl:choose>
				</axsl:attribute>
			</input>
		</xsl:if>
	</xsl:template>

	<!-- шаблон для создания контрола для ввода дат-->
	<xsl:template name="datebox" match="field[@type='date']" mode="control">
		<xsl:param name="name" select="@name"/>
		<xsl:param name="readOnly" select="false()"/>

		<axsl:variable name="formattedValue">
			<axsl:value-of select="substring({$name},9,2)"/>.<axsl:value-of select="substring({$name},6,2)"/>.<axsl:value-of select="substring({$name},1,4)"/>
		</axsl:variable>
		
		<xsl:if test="$readOnly">
			<axsl:value-of select="$formattedValue"/>
		</xsl:if>
		
		<xsl:if test="not($readOnly)">
			<input type="text" name="{$name}" size="10">
				<axsl:attribute name="value">
					<axsl:value-of select="$formattedValue"/>
				</axsl:attribute>
			</input>
		</xsl:if>
	</xsl:template>

	<!-- шаблон для создания контрола для ввода булевых значений -->
	<xsl:template name="checkbox" match="field[@type='boolean']" mode="control">
		<xsl:param name="name" select="@name"/>
		<input type="checkbox" name="{$name}" value="true">
			<axsl:if test="{$name}='true'">
				<axsl:attribute name="checked"/>
			</axsl:if>
		</input>
	</xsl:template>

	<!-- шаблон для создания контрола для ввода всего остального -->
	<xsl:template name="textbox" match="field" priority="-1" mode="control">
		<xsl:param name="name"  select="@name"/>
		<xsl:param name="readOnly" select="false()"/>
		
		<xsl:if test="$readOnly">
			<axsl:value-of select="{$name}"/>
		</xsl:if>
		
		<xsl:if test="not($readOnly)">
			<input name="{$name}" type="text" size="30">
				<axsl:attribute name="value">
					<axsl:value-of select="{$name}"/>
				</axsl:attribute>
			</input>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios/><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet 
	version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:date="xalan://java.util.Date"
	>
<xsl:output
		method="xml"
		indent="yes"
		encoding="windows-1251"

		/>
	<!--struts-config PUBLIC &quot;-//Apache Software Foundation//DTD Struts Configuration 1.2//EN&quot; &quot;http://struts.apache.org/dtds/struts-config_1_2.dtd&quot;-->
<xsl:param name="overrideFile" select="''"/>

<xsl:strip-space elements="*"/>

<xsl:key name="formKey" match="/struts-config/form-beans/form-bean" use="@name"/>
<xsl:key name="gexceptionKey" match="/struts-config/global-exceptions/exception" use="@key"/>
<xsl:key name="gforwardKey" match="/struts-config/global-forwards/forward" use="@name"/>
<xsl:key name="actionKey" match="/struts-config/action-mappings/action" use="@path"/>
<xsl:key name="messageKey" match="/struts-config/message-resources" use="@key"/>

<xsl:variable name="global" select="."/>
<xsl:variable name="override" select="document($overrideFile)"/>

<xsl:template match="/">
<xsl:comment>
##############################################################################################################
# DON'T EDIT THIS FILE
# НЕ РЕДАКТИРУЙТЕ ЭТОТ ФАЙЛ
# ВСЕ ИЗМЕНЕНИЯ ВСЕ РАВНО НЕ СОХРАНЯТСЯ
#
# Generated: <xsl:value-of select="date:new()"/>
# Global   : config/global-struts-config.xml
# Override : <xsl:value-of select="$overrideFile"/>
##############################################################################################################
</xsl:comment>

	<xsl:apply-templates select="$override/*" mode="validate"/>
	<xsl:apply-templates select="$global/*"/>
</xsl:template>

<xsl:template match="/struts-config
                     | /struts-config/form-beans
					 | /struts-config/global-exceptions
					 | /struts-config/global-forwards
					 | /struts-config/action-mappings">
	<xsl:copy><xsl:copy-of select="@*"/><xsl:apply-templates/></xsl:copy>
</xsl:template>

<!-- формы -->
<xsl:template match="/struts-config/form-beans/form-bean">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'formKey'"/>
		<xsl:with-param name="value" select="@name"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/struts-config/form-beans/form-bean" mode="validate">
	<xsl:call-template name="validateOverride">
		<xsl:with-param name="key" select="'formKey'"/>
		<xsl:with-param name="value" select="@name"/>
	</xsl:call-template>
</xsl:template>

<!-- исключения -->
<xsl:template match="/struts-config/global-exceptions/exception">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'gexceptionKey'"/>
		<xsl:with-param name="value" select="@key"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/struts-config/global-exceptions/exception" mode="validate">
	<xsl:call-template name="validateOverride">
		<xsl:with-param name="key" select="'gexceptionKey'"/>
		<xsl:with-param name="value" select="@key"/>
	</xsl:call-template>
</xsl:template>

<!-- форварды -->
<xsl:template match="/struts-config/global-forwards/forward">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'gforwardKey'"/>
		<xsl:with-param name="value" select="@name"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/struts-config/global-forwards/forward" mode="validate">
	<xsl:call-template name="validateOverride">
		<xsl:with-param name="key" select="'gforwardKey'"/>
		<xsl:with-param name="value" select="@name"/>
	</xsl:call-template>
</xsl:template>

<!-- экшены -->
<xsl:template match="/struts-config/action-mappings/action">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'actionKey'"/>
		<xsl:with-param name="value" select="@path"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/struts-config/action-mappings/action" mode="validate">
	<xsl:call-template name="validateOverride">
		<xsl:with-param name="key" select="'actionKey'"/>
		<xsl:with-param name="value" select="@path"/>
	</xsl:call-template>
</xsl:template>

<!-- сообщения -->
<xsl:template match="/struts-config/message-resources">
	<xsl:call-template name="processOverride">
		<xsl:with-param name="key" select="'messageKey'"/>
		<xsl:with-param name="value" select="@key"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/struts-config/message-resources" mode="validate">
	<xsl:call-template name="validateOverride">
		<xsl:with-param name="key" select="'messageKey'"/>
		<xsl:with-param name="value" select="@key"/>
	</xsl:call-template>
</xsl:template>

<xsl:template match="/struts-config/plug-in">
	<xsl:copy-of select="."/>
</xsl:template>
    
<!-- Контроллер -->
<xsl:template match="/struts-config/controller">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="comment()">
	<xsl:comment><xsl:value-of select="."/></xsl:comment>
</xsl:template>

<xsl:template name="processOverride">
	<xsl:param name="key"/>
	<xsl:param name="value"/>

	<xsl:variable name="globalElem" select="."/>

	<xsl:for-each select="$override">
		<xsl:variable name="overridedElem" select="key($key,$value)"/>

		<xsl:if test="boolean($overridedElem)">
			<xsl:comment><xsl:value-of select="$overrideFile"/></xsl:comment>
			<xsl:copy-of select="$overridedElem"/>
		</xsl:if>

		<xsl:if test="not(boolean($overridedElem))">
			<xsl:comment>struts/struts-config.xml</xsl:comment>
			<xsl:copy-of select="$globalElem"/>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

<xsl:template name="validateOverride">
	<xsl:param name="key"/>
	<xsl:param name="value"/>

	<xsl:for-each select="$global">
		<xsl:variable name="globalElem" select="key($key,$value)"/>
		<xsl:if test="not(boolean($globalElem))">
			<xsl:message terminate="yes">
##############################################################################################
# В global-struts-config.xml не определен <xsl:value-of select="$key"/>:<xsl:value-of select="$value"/>
# Убедитесь в наличии этого элемента.
# Выполнение прервано в соответствии с правилами описанными в документе "Работа с конфигурациями"
##############################################################################################
			</xsl:message>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

</xsl:stylesheet><!-- Stylesheet edited using Stylus Studio - (c) 2004-2005. Progress Software Corporation. All rights reserved. -->
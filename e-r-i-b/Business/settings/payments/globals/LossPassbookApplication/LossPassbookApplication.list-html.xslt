<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="no"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>
	<xsl:template match="/">
		<xsl:if test="count(/list-data/entity-list/entity) != 0">
				<xsl:apply-templates select="/list-data/entity-list"/>
		</xsl:if>
	</xsl:template>
	<!-- список -->
	<xsl:template match="/list-data/entity-list">
		<!-- заголовок списка -->
				<tr class="tblInfHeader">
					<td width="20">
						<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
					</td>
					<td width="80">Дата</td>
					<td width="30">Номер</td>
					<td width="50%">Номер&nbsp;вклада</td>
					<td width="80">Статус документа</td>
				</tr>
				<xsl:apply-templates/>
				<!-- footer -->
	</xsl:template>
	<!-- строка таблицы -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<tr>
			<xsl:attribute name="style">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>

			<!--Эти два столбца не удалять!!! Нужны для возможности удаления-->
			<td style="display:none">
				<xsl:value-of select="state.category"/>
			</td>
			<td style="display:none">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>
			</td>

			<td class="listItem" style="align: center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="@key"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;
				<a href="{$webRoot}/private/payments/default-action.do?id={@key}">
					<xsl:value-of select="depositAccount"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="list&#x2D;form &#x2D;> html" userelativepaths="yes" externalpreview="no" url="list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
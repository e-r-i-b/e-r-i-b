<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="no"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
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
					<td width="30">Номер</td>
					<td width="80">Дата</td>
					<td width="50%">Счет&nbsp;списания</td>
					<td width="50%">Счет зачисления</td>
					<td width="100">Сумма</td>
					<td width="100">Валюта</td>
					<td width="80">Статус документа</td>
				</tr>
				<xsl:apply-templates/> 				<!-- footer -->    			
	</xsl:template>
	<!-- строка таблицы -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<tr>
			<xsl:attribute name="style">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
<!--
			Эти два столбца не удалять!!! Нужны для определения возможности отзыва
-->
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

			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem" align="center">
				&nbsp;
				<a href="#" onClick="loadNewAction('','');window.location='{$webRoot}/private/payments/default-action.do?id={@key}'">
					<xsl:value-of select="documentNumber"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="chargeOffAccount"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="receiverAccount"/>&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;<xsl:value-of select="format-number(chargeOffAmount.decimal, '#0.00')"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="chargeOffAmount.currency.code"/>&nbsp;
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
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
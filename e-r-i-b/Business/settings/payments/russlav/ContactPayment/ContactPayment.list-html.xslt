<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="count(/list-data/entity-list/entity) != 0">
				<xsl:apply-templates select="/list-data/entity-list"/>
			</xsl:when>
			<xsl:otherwise>
				<table id="messageTab" width="100%">
					<tbody>
						<tr>
							<td class="messageTab" align="center">
								<br/>Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;платежа,<br/>
								 соответствующего&nbsp;заданному&nbsp;фильтру!
							</td>
						</tr>
					</tbody>
				</table>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- список -->
	<xsl:template match="/list-data/entity-list">
		<!-- заголовок списка -->
		<table class="userTab" id="tableTitle" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr class="titleTable">
					<td width="20">
						<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
					</td>
					<td>Форма</td>
					<td>Дата</td>
					<td>Номер</td>
					<td>Счет&nbsp;списания</td>
					<td>Сумма&nbsp;списания</td>
					<td>Статус</td>
				</tr>

				<xsl:apply-templates/>
				<!-- footer -->
			</tbody>
		</table>
	</xsl:template>

	<!-- строка таблицы -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<xsl:element name="tr">
			<xsl:attribute name="class">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem">
				&nbsp;<xsl:call-template name="form2text">
					<xsl:with-param name="code">
						<xsl:value-of select="formName"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(documentDate,9,2)"/>.<xsl:value-of select="substring(documentDate,6,2)"/>.<xsl:value-of select="substring(documentDate,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;
				<a href="{$webRoot}/private/payments/default-action.do?id={@key}&amp;state={state.category}">
					<xsl:value-of select="documentNumber"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="chargeOffAccount"/>&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				<xsl:value-of select="format-number(chargeOffAmount.decimal, '#0.00')"/>&nbsp;
				<xsl:value-of select="amount.currency.code"/>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
<!--
			Два последних столбца не удалять!!! Нужны для определения возможности отзыва и редактирования
-->
			<td style="display:none">
				<xsl:value-of select="formName"/>
			</td>
			<td style="display:none">
				<xsl:value-of select="state.category"/>
			</td>
		</xsl:element>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='S'">Отправлен в банк получателя</xsl:when>
			<xsl:when test="$code='A'">Получен банком получателем</xsl:when>
			<xsl:when test="$code='X'">Выдан</xsl:when>
			<xsl:when test="$code='C'">Аннулирование</xsl:when>
			<xsl:when test="$code='M'">Изменение</xsl:when>
			<xsl:when test="$code='L'">Отозван</xsl:when>
			<xsl:when test="$code='N'">Возвращен</xsl:when>
			<xsl:when test="$code='W'">Принят</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<xsl:template name="form2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='ContactPayment'">Перевод</xsl:when>
			<xsl:when test="$code='RecallContactPayment'">Отзыв</xsl:when>
			<xsl:when test="$code='EditContactPayment'">Редактирование</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="list&#x2D;data &#x2D;> html" userelativepaths="yes" externalpreview="no" url="list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
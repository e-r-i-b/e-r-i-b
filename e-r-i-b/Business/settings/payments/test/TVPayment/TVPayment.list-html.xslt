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
					<td width="80">Дата</td>
					<td width="30">Номер</td>
					<td width="50%">Счет&nbsp;списания</td>
					<td width="100">Сумма</td>
					<td width="100">Оператор</td>
					<td width="80">Статус</td>
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
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="@key"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;
				<xsl:if test="state.code!='TEMPLATE'">
					<a href="{$webRoot}/private/payments/showconfirmed.do?id={@key}">
						<xsl:value-of select="chargeOffAccount"/>
					</a>
				</xsl:if>
				<xsl:if test="state.code='TEMPLATE'">
					<xsl:value-of select="chargeOffAccount"/>
				</xsl:if>
				&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;<xsl:value-of select="format-number(chargeOffAmount.decimal, '#0.00')"/>&nbsp;
				<xsl:value-of select="amount.currency.code"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:choose>
					<xsl:when test="attributes.tvProvider.value='NTV+'">НТВ-Плюс</xsl:when>
					<xsl:when test="attributes.tvProvider.value='KomkorTV'">Акадо</xsl:when>
					<xsl:when test="attributes.tvProvider.value='KosmosTV'">Космос-ТВ</xsl:when>
				</xsl:choose>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
		</xsl:element>
	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>

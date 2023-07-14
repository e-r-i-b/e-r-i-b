<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
		<!ENTITY nbsp "&#160;">
		]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:variable name="formNameValue" select='//recalled-document-form-name'/>
	<xsl:param name="paymentdescriptor" select="document('paymentDescriptors')/entity-list/entity[@key=$formNameValue/text()]"/>

	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>


	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="amount" name="amount" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="amount"/>
			    </xsl:attribute>
		</input>

		<input id="amountCurrency" name="amountCurrency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="amountCurrency"/>
			    </xsl:attribute>
		</input>

		<input id="recalled-document-form-name" name="recalled-document-form-name" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="recalled-document-form-name"/>
			    </xsl:attribute>
		</input>

		<input id="recalledDocumentNumber" name="recalledDocumentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="recalledDocumentNumber"/>
			    </xsl:attribute>
		</input>

		<input id="parentId" name="parentId" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="parentId"/>
	            </xsl:attribute>
		</input>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер отзыва</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="documentNumber" name="documentNumber" size="30"
					       value="{documentNumber}" disabled="true"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата отзыва</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="documentDate" name="documentDate" size="30">
					<xsl:attribute name="value">
						<xsl:value-of select="documentDate"/>
					</xsl:attribute>
				</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер отзываемого перевода</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="recalledDocumentNumber" name="recalledDocumentNumber" size="30" disabled="true">
						<xsl:attribute name="value">
							<xsl:value-of select="recalledDocumentNumber"/>
						</xsl:attribute>
				</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Tип документа</xsl:with-param>
			<xsl:with-param name="rowValue"><input type="text" id="recalledDocumentType" name="recalledDocumentType" size="30" disabled="true" value=""/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта документа</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="currency" name="currency" type="text" value="{amountCurrency}" size="30" disabled="true"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="30" disabled="true"/></xsl:with-param>
		</xsl:call-template>

		<script type="text/javascript">
			function setCurrencyCode()
				{
					var currency = document.getElementById("сurrencyCodeSelect");
					var currencyCodeElem       = document.getElementById("currency");
					currencyCodeElem.value  = currency.value;
				}
			function init()
			 {
				 var currencyElem    = document.getElementById("currency");
				 if (currencyElem.value=="")
					 currencyElem.value = "RUB"

	  			 document.getElementById('recalledDocumentType').value='<xsl:value-of select="$paymentdescriptor"/>';
			 }
			 init();
		</script>

	</xsl:template>
    <xsl:template match="/form-data" mode="view">

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер отзыва</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата отзыва</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentDate"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер отзываемого перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="recalledDocumentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Tип документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="$paymentdescriptor"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amountCurrency"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amount"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус документа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:with-param>
		</xsl:call-template>

    </xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="standartRow">

		<xsl:param name="id"/>
		<xsl:param name="required" select="'true'"/>
		<xsl:param name="rowName"/>
		<xsl:param name="rowValue"/>

		<tr>
			<td class="{$styleClass}" style="{$styleSpecial}" id="{$id}">
					<xsl:copy-of select="$rowName"/>
				<xsl:if test="$required = 'true' and $mode = 'edit'">
					<span id="asterisk_{$id}" class="asterisk">*</span>
				</xsl:if>
			</td>
			<td>
				<xsl:copy-of select="$rowValue"/>
			</td>
		</tr>

	</xsl:template>

	<xsl:template name="titleRow">
		<xsl:param name="rowName"/>
		<tr>
			<td colspan="2" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
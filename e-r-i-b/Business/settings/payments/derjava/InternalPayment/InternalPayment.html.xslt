<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>
	<xsl:param name="personAvailable" select="true()"/>

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
		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Документ №</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата</xsl:with-param>
			<xsl:with-param name="rowValue">
				<input type="text" id="documentDate" name="documentDate" size="10">
					<xsl:attribute name="value">
						<xsl:value-of select="documentDate"/>
					</xsl:attribute>
				</input>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="fromAccount" select="fromAccountSelect"/>
				<xsl:if test="$personAvailable">
					<select id="fromAccountSelect" name="fromAccountSelect">
						<xsl:if test="$isTemplate = 'true'">
							<option value="">Не задан</option>
						</xsl:if>
						<xsl:for-each select="document('active-accounts.xml')/entity-list/*">
							<option>
								<xsl:attribute name="value">
									<xsl:value-of select="./@key"/>
								</xsl:attribute>
								<xsl:if test="$fromAccount = ./@key">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								<xsl:value-of select="./@key"/>&nbsp;
								[<xsl:value-of select="./field[@name='type']"/>]
								<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
								<xsl:value-of select="./field[@name='currencyCode']"/>
							</option>
						</xsl:for-each>
					</select>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="fromAccountSelect" name="fromAccountSelect" disabled="disabled"><option selected="selected">Счет клиента</option></select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет зачисления</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="toAccount" select="toAccountSelect"/>
				<xsl:if test="$personAvailable">
					<select id="toAccountSelect" name="toAccountSelect">
						<xsl:if test="$isTemplate = 'true'">
							<option value="">Не задан</option>
						</xsl:if>
						<xsl:for-each select="document('active-accounts.xml')/entity-list/*">
							<option>
								<xsl:attribute name="value">
									<xsl:value-of select="./@key"/>
								</xsl:attribute>
								<xsl:if test="$toAccount = ./@key">
									<xsl:attribute name="selected">true</xsl:attribute>
								</xsl:if>
								<xsl:value-of select="./@key"/>&nbsp;
								[<xsl:value-of select="./field[@name='type']"/>]
								<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
								<xsl:value-of select="./field[@name='currencyCode']"/>
							</option>
						</xsl:for-each>
					</select>
				</xsl:if>
				<xsl:if test="not($personAvailable)">
					<select id="toAccountSelect" name="toAccountSelect" disabled="disabled"><option selected="selected">Счет клиента</option></select>
				</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="24"/></xsl:with-param>
		</xsl:call-template>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Документ №</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Дата</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentDate"/></xsl:with-param>
		</xsl:call-template>

		<xsl:variable name="adDate" select="admissionDate"/>
		<xsl:if test="not($adDate = '')">
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата приема платежа</xsl:with-param>
				<xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
			</xsl:call-template>
	    </xsl:if>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет списания</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fromAccountSelect"/>
				[<xsl:value-of select="fromAccountType"/>]
				<xsl:value-of select="amountCurrency"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Счет зачисления</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="toAccountSelect"/>
				[<xsl:value-of select="toAccountType"/>]
				<xsl:value-of select="amountCurrency"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of  select="amount"/>&nbsp;<xsl:value-of select="amountCurrency"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма комиссии</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="commissionAmount"/></xsl:with-param>
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
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
			<xsl:when test="$code='RECALLED'">Отозван</xsl:when>
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
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
	<xsl:param name="isTemplate" select="'isTemplate'"/>

	<xsl:variable name="styleClass" select="'label120'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>
	
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/fields/field"/>
	<xsl:variable name="formData" select="/form-data"/>

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

		<input id="appointment" name="appointment" type="hidden">
			<xsl:attribute name="value">
					<xsl:value-of select="appointment"/>
	            </xsl:attribute>
		</input>

		<input id="receiverKey" name="receiverKey" type="hidden">
			<xsl:attribute name="value">
					<xsl:value-of select="receiverKey"/>
	            </xsl:attribute>
		</input>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Номер перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Списать со счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect">
							<xsl:if test="$isTemplate = 'true'">
								<option value="">Не задан</option>
							</xsl:if>
							<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$payerAccount = ./@key">
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
						<select id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
							<option value="" selected="selected">Счет клиента</option>
						</select>
					</xsl:if>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма</xsl:with-param>
			<xsl:with-param name="rowValue"><input id="amount" name="amount" type="text" value="{amount}" size="24"/>&nbsp;RUB</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="titleRow">
			<xsl:with-param name="rowName">Получатель платежа</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">Наименование</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="receiverName" select="receiverName"/>
				<input type="hidden" name="receiverName" value="{$receiverName}"/>
				<xsl:value-of select="receiverName"/>
			</xsl:with-param>
		</xsl:call-template>

			<xsl:if test="count($extendedFields) != 0">
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Дополнительные поля платежа</xsl:with-param>
				</xsl:call-template>
			</xsl:if>

			<xsl:for-each select="$extendedFields">
				<xsl:call-template name="standartRow">
					<xsl:with-param name="required" select="@mandatory='true'"/>
					<xsl:with-param name="rowName"><xsl:value-of select="@description"/></xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:variable name="fieldName" select="@name"/>
						<xsl:variable name="fieldValue" select="$formData/*[name()=$fieldName]"/>
						<xsl:variable name="len" select="@length"/>

						<xsl:if test="number($len)&gt;0">
							<input id="{$fieldName}" name="{$fieldName}" type="text" size="{number($len)+1}" maxlength="{$len}" value="{$fieldValue}" hint="@comment"/>
						</xsl:if>
						<xsl:if test="not(number($len)&gt;0)">
							<input id="{$fieldName}" name="{$fieldName}" type="text" size="25" maxlength="128" value="{$fieldValue}"/>
						</xsl:if>
						&nbsp;  <br/><br/>
							<xsl:value-of select="@comment"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:for-each>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">

		 <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер перевода</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>

		 <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Списать со счета</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="payerAccountType"/>]
				<xsl:value-of select="payerAccountCurrency"/>
			</xsl:with-param>
		</xsl:call-template>

		 <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Сумма</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="amount"/>&nbsp;RUB &nbsp;&nbsp;Комиссия:&nbsp; <xsl:value-of select="commissionAmount"/></xsl:with-param>
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

		<xsl:call-template name="titleRow">
			<xsl:with-param name="rowName">Получатель платежа</xsl:with-param>
		</xsl:call-template>

		 <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Наименование</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="receiverName"/></xsl:with-param>
		</xsl:call-template>

			<xsl:if test="count($extendedFields) != 0">
				<xsl:call-template name="titleRow">
					<xsl:with-param name="rowName">Дополнительные поля платежа</xsl:with-param>
				</xsl:call-template>
			</xsl:if>

			<!-- Выберем дополнительные поля -->
			<xsl:for-each select="$extendedFields">
				 <xsl:call-template name="standartRow">
					<xsl:with-param name="rowName"><xsl:value-of select="@description"/></xsl:with-param>
					<xsl:with-param name="rowValue">
						<xsl:variable name="fieldName" select="@name"/>
						<xsl:value-of select="$formData/*[name()=$fieldName]"/>
						&nbsp;
					</xsl:with-param>
				</xsl:call-template>				
			</xsl:for-each>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='INITIAL'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Обрабатывается</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">Ожидание обработки</xsl:when>
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
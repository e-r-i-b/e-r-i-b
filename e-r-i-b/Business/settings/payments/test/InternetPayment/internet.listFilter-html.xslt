<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
	</xsl:template>	
	
	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
		<table style="margin-bottom: 2px;" class="filterBorder" onkeypress="onEnterKey(event);" cellpadding="1" cellspacing="0">
			<tbody>
			<tr>
				<td>Статус</td>
				<td>
					<select name="state">
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="state"/>
							<xsl:with-param name="code" select="''"/>
							<xsl:with-param name="text" select="'Все'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="state"/>
							<xsl:with-param name="code" select="'I'"/>
							<xsl:with-param name="text" select="'Введен'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="state"/>
							<xsl:with-param name="code" select="'W'"/>
							<xsl:with-param name="text" select="'Обрабатывается'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="state"/>
							<xsl:with-param name="code" select="'S'"/>
							<xsl:with-param name="text" select="'Исполнен'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="state"/>
							<xsl:with-param name="code" select="'E'"/>
							<xsl:with-param name="text" select="'Отказан'"/>
						</xsl:call-template>
					</select>
				</td>
				<td>&nbsp;</td>
            </tr>
			<tr>
				<td nowrap="true">Период&nbsp;c</td>
				<td nowrap="true">
					<input name="fromDate" value="{fromDate}" class="filterInput" type="text"/>&nbsp;по&nbsp;
					<input name="toDate" value="{toDate}" class="filterInput" type="text"/>
				</td>
			</tr>
			<tr>
				<td nowrap="true">Сумма&nbsp;c&nbsp;</td>
				<td nowrap="true">
					<input name="minAmount" value="{minAmount}" class="filterInput" type="text"/>&nbsp;по&nbsp;
					<input name="maxAmount" value="{maxAmount}" class="filterInput" type="text"/>&nbsp;
					<select name="currencyCode">
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="currencyCode"/>
							<xsl:with-param name="code" select="''"/>
							<xsl:with-param name="text" select="'Все'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="currencyCode"/>
							<xsl:with-param name="code" select="'RUB'"/>
							<xsl:with-param name="text" select="'RUB'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="currencyCode"/>
							<xsl:with-param name="code" select="'USD'"/>
							<xsl:with-param name="text" select="'USD'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="currencyCode"/>
							<xsl:with-param name="code" select="'EUR'"/>
							<xsl:with-param name="text" select="'EUR'"/>
						</xsl:call-template>
					</select>
				</td>
                <td>&nbsp;</td>
            </tr>
			<tr>
				<td>Счет списания</td>
				<td>
					<xsl:variable name="selected" select="chargeOffAccount"/>
					<select id="payerAccount" name="payerAccount">
						<option value="">Все</option>
						<xsl:for-each select="document('accounts.xml')/entity-list/*">
							<option>
								<xsl:attribute name="value">
									<xsl:value-of select="./@key"/>
								</xsl:attribute>	
								<xsl:if test="$selected = ./@key">
									<xsl:attribute name="selected">true</xsl:attribute>								
								</xsl:if>
								<xsl:value-of select="./@key"/>&nbsp;
								[<xsl:value-of select="./field[@name='type']"/>]
								<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
								<xsl:value-of select="./field[@name='currencyCode']"/>
							</option>
						</xsl:for-each> 
					</select>
				</td>
                <td>&nbsp;</td>
            </tr>
			<tr>
				<td>Оператор</td>
				<td colspan="2">
					<select name="operator">
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="operator"/>
							<xsl:with-param name="code" select="''"/>
							<xsl:with-param name="text" select="'Все'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="operator"/>
							<xsl:with-param name="code" select="'Stream'"/>
							<xsl:with-param name="text" select="'МТУ-Интел'"/>
						</xsl:call-template>
						<xsl:call-template name="option">
							<xsl:with-param name="value" select="operator"/>
							<xsl:with-param name="code" select="'Corbina'"/>
							<xsl:with-param name="text" select="'Корбина-Телеком'"/>
						</xsl:call-template>
                        <xsl:call-template name="option">
							<xsl:with-param name="value" select="operator"/>
							<xsl:with-param name="code" select="'Zebra'"/>
							<xsl:with-param name="text" select="'Зебра-Телеком'"/>
						</xsl:call-template>
                        <xsl:call-template name="option">
							<xsl:with-param name="value" select="operator"/>
							<xsl:with-param name="code" select="'ROL'"/>
							<xsl:with-param name="text" select="'РОЛ'"/>
						</xsl:call-template>
                    </select>
				</td>
			</tr>
		</tbody>
		</table>
	</xsl:template>
	
	<xsl:template name="option">
		<xsl:param name="value"/>
		<xsl:param name="code"/>
		<xsl:param name="text"/>
		<option value="{$code}">
			<xsl:if test="$code=$value">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>
			<xsl:value-of select="$text"/>
		</option>		
	</xsl:template>

</xsl:stylesheet>
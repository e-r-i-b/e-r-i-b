<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
		<script type="text/javascript">
          function initTemplates() {
           }
          function clearMasks(event){
             clearInputTemplate("fromDate",DATE_TEMPLATE);
             clearInputTemplate("toDate",DATE_TEMPLATE);
           }
           function checkData() {
             if (!checkSumRange ("minAmount","maxAmount","Сумма с","Сумма по")) return false;
             if (!checkPeriod ("fromDate", "toDate", "Период с", "Период по")) return false;
             return true;
           }
           initTemplates();
           setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
           setSelectBoxValue ("payerAccount","<xsl:value-of select="/list-data/filter-data/payerAccount"/>");
           setSelectBoxValue ("receiverAccount","<xsl:value-of select="/list-data/filter-data/receiverAccount"/>");
		</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
		<span class="filterLabel">Статус</span>
		<select name="state" onkeydown="onTabClick(event,'fromDate')">
			<option value="">Все</option>
			<option value="I">Введен</option>
			<option value="W">Обрабатывается</option>
			<option value="S">Исполнен</option>
			<option value="E">Отказан</option>
		</select>
		<br/>
		<span class="filterLabel">Период&nbsp;c</span>
		<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
		&nbsp;по&nbsp;
		<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		<br/>
		<span class="filterLabel">Сумма&nbsp;c</span>
		<input name="minAmount" value="{minAmount}" class="filterInput" type="text" size="10"/>
		&nbsp;по&nbsp;
		<input name="maxAmount" value="{maxAmount}" class="filterInput" type="text" size="10"/>
		<br/>
		<span class="filterLabel">Счет списания</span>
		<select id="payerAccount" name="payerAccount">
			<option value="">Все</option>
			<xsl:for-each select="document('foreign-currency-accounts.xml')/entity-list/*">
				<option value="{./@key}">
					<xsl:value-of select="./@key"/>&nbsp;
						[<xsl:value-of select="./field[@name='type']"/>]
				</option>
			</xsl:for-each>
		</select>
		<br/>
		<span class="filterLabel">Счет зачисления</span>
		<select id="receiverAccount" name="receiverAccount">
			<option value="">Все</option>
			<xsl:for-each select="document('foreign-currency-accounts.xml')/entity-list/*">
				<option value="{./@key}">
					<xsl:value-of select="./@key"/>&nbsp;
						[<xsl:value-of select="./field[@name='type']"/>]
				</option>
			</xsl:for-each>
		</select>
	</xsl:template>
</xsl:stylesheet>

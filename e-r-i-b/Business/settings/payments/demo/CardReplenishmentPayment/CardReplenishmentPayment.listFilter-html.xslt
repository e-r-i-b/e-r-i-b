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
		<script language="javascript">
          function clearMasks(event){
             clearInputTemplate("fromDate",DATE_TEMPLATE);
             clearInputTemplate("toDate",DATE_TEMPLATE);
           }
           function checkData() {
             if (!checkSumRange ("minAmount","maxAmount","Сумма с","Сумма по")) return false;
             if (!checkPeriod ("fromDate", "toDate", "Период с", "Период по")) return false;
             return true;
           }
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");
			setSelectBoxValue ("currencyCode","<xsl:value-of select="/list-data/filter-data/currencyCode"/>");
			setSelectBoxValue ("cardNumber","<xsl:value-of select="/list-data/filter-data/cardNumber"/>");
		</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
		<span class="filterLabel">Статус</span>
			<select name="state" style="filterSelect" onkeydown="onTabClick(event,'fromDate')">
			<option value="">Все</option>
			<option value="INITIAL,SAVED">Введен</option>
			<option value="DISPATCHED">Обрабатывается</option>
			<option value="DELAYED_DISPATCH">Ожидание обработки</option>
            <option value="EXECUTED">Исполнен</option>
            <option value="REFUSED">Отказан</option>
            <option value="RECALLED">Отозван</option>
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
		&nbsp;
		<select name="currencyCode">
			<option value="">Все</option>
			<option value="RUB">RUB</option>
			<option value="USD">USD</option>
			<option value="EUR">EUR</option>
		</select>
		<br/>
		<span class="filterLabel">Карта</span>
		<select name="cardNumber">
			<option value=''>Все</option>

			<xsl:for-each select="document('active-cards.xml')/entity-list/entity">
			<xsl:variable name="number" select="@key"/>
			<option value="{field[@name='id']/text()}">
				<xsl:value-of select="concat(substring($number, 1, 1), '..', substring($number, string-length($number)-3, 4))"/> [<xsl:value-of select="field[@name='type']/text()"/>]
			</option>
			</xsl:for-each>
		</select>
	</td>	
	</xsl:template>
</xsl:stylesheet>
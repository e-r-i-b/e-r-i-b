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
             if (!checkSumRange ("minAmount","maxAmount","����� �","����� ��")) return false;
             if (!checkPeriod ("fromDate", "toDate", "������ �", "������ ��")) return false;
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
		<span class="filterLabel">������</span>
			<select name="state" style="filterSelect" onkeydown="onTabClick(event,'fromDate')">
			<option value="">���</option>
			<option value="INITIAL,SAVED">������</option>
			<option value="DISPATCHED">��������������</option>
			<option value="DELAYED_DISPATCH">�������� ���������</option>
            <option value="EXECUTED">��������</option>
            <option value="REFUSED">�������</option>
            <option value="RECALLED">�������</option>
		</select>
		<br/>
		<span class="filterLabel">������&nbsp;c</span>
		<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
		&nbsp;��&nbsp;
		<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		<br/>
		<span class="filterLabel">�����&nbsp;c</span>
		<input name="minAmount" value="{minAmount}" class="filterInput" type="text" size="10"/>
		&nbsp;��&nbsp;
		<input name="maxAmount" value="{maxAmount}" class="filterInput" type="text" size="10"/>
		&nbsp;
		<select name="currencyCode">
			<option value="">���</option>
			<option value="RUB">RUB</option>
			<option value="USD">USD</option>
			<option value="EUR">EUR</option>
		</select>
		<br/>
		<span class="filterLabel">�����</span>
		<select name="cardNumber">
			<option value=''>���</option>

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
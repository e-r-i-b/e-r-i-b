<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/list-data/filter-data"/>
		<script language="javascript">
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state"/>");
			setSelectBoxValue ("card","<xsl:value-of select="/list-data/filter-data/card"/>");
		</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
		<span class="filter">������</span>
		<select name="state" style="font-weight:normal;">
			<option value="">���</option>
			<option value="SAVED">������</option>
			<option value="DISPATCHED">������</option>
			<option value="ACCEPTED">�������</option>
			<option value="EXECUTED">��������</option>
			<option value="REFUSED">�������</option>
		</select>
		<br/>
		<span class="filter">������&nbsp;c</span>
		<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
		&nbsp;��&nbsp;
		<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
		<br/>
		<span class="filter">�����&nbsp;</span>
		<select name="card" style="font-weight:normal;">
			<option value=''>���</option>

			<xsl:for-each select="document('cards.xml')/entity-list/entity">
			<xsl:variable name="number" select="@key"/>
			<option value="{field[@name='id']/text()}">
				<xsl:value-of select="concat(substring($number, 1, 1), '..', substring($number, string-length($number)-3, 4))"/>
			</option>
			</xsl:for-each>
		</select>
	</td>	
	</xsl:template>
</xsl:stylesheet>
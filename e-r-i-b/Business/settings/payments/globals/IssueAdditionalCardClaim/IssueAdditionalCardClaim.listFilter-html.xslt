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
		<script language="javascript">function initTemplates() {
			}
			function clearMasks(event){
			 clearInputTemplate("fromDate",DATE_TEMPLATE);
			 clearInputTemplate("toDate",DATE_TEMPLATE);
			}
			function checkData() {
			 if (!checkPeriod ("fromDate", "toDate", "������ �", "������ ��")) return false;
			 return true;
			}
			initTemplates();
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state.code"/>");</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
	<td>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="filter">�����</td>
				<td  class="filter" style="font-weight:normal;">
					<select name="cardNumber" id="cardNumber">
						<option value=''>���</option>
						<xsl:for-each select="document('cards.xml')/entity-list/entity">
						<xsl:variable name="number" select="@key"/>
						<option value="{field[@name='id']/text()}">
							<xsl:value-of select="concat(substring($number, 1, 1), '..', substring($number, string-length($number)-3, 4))"/> [<xsl:value-of select="field[@name='type']/text()"/>]
						</option>
						</xsl:for-each>
					</select>
				</td>
			</tr>
			<tr>
				<td class="filter">�����</td>
				<td  class="filter"><input name="number" type="text" maxlength="255" size="15"/>
				</td>
			</tr>
			<tr>
				<td  class="filter">������</td>
				<td  class="filter" style="font-weight:normal;">
					<select name="state" onkeydown="onTabClick(event,'fromDate')">
						<option value="">���</option>
						<option value="SAVED">������</option>
						<option value="DISPATCHED">������</option>
						<option value="ACCEPTED">�������</option>
						<option value="EXECUTED">��������</option>
						<option value="REFUSED">�������</option>
					</select>
				</td>
			</tr>
			<tr>
				<td  class="filter">������&nbsp;c</td>
				<td  class="filter"><input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
		&nbsp;��&nbsp;<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/></td>
			</tr>
		</table>
	</td>	
	</xsl:template>
</xsl:stylesheet>
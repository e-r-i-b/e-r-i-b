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
			setSelectBoxValue ("state","<xsl:value-of select="/list-data/filter-data/state"/>");</script>
	</xsl:template>

	<!-- filter -->
	<xsl:template match="/list-data/filter-data">
		<td>
		<table>
		    <tr>
				<td  class="filter">������</td>
				<td class="filter">
					<select name="state" onkeydown="onTabClick(event,'fromDate')">
						<option selected="selected" value="">���</option>
						<option value="DRAFT">��������</option>
						<option value="SAVED">������</option>
						<option value="DISPATCHED">������</option>
						<option value="CONSIDERATION">� ������������</option>
						<option value="COMPLETION">��������� ���������</option>
						<option value="EXECUTED">������ �����</option>
						<option value="APPROVED">���������</option>
						<option value="REFUSED">�������</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="filter">�����</td>
				<td class="filter"><input value="{number}" class="filterInput" name="number" type="text" maxlength="255" size="15"/>
				</td>
			</tr>
			<tr>
				<td  class="filter">������&nbsp;c</td>
				<td class="filter">
					<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
					&nbsp;��&nbsp;
					<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
				</td>
			</tr>
		</table>
		</td>
	</xsl:template>
</xsl:stylesheet>
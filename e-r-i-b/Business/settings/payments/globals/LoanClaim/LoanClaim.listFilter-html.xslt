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
			 if (!checkPeriod ("fromDate", "toDate", "Период с", "Период по")) return false;
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
				<td  class="filter">Статус</td>
				<td class="filter">
					<select name="state" onkeydown="onTabClick(event,'fromDate')">
						<option selected="selected" value="">Все</option>
						<option value="DRAFT">Черновик</option>
						<option value="SAVED">Введен</option>
						<option value="DISPATCHED">Принят</option>
						<option value="CONSIDERATION">В рассмотрении</option>
						<option value="COMPLETION">Требуется доработка</option>
						<option value="EXECUTED">Кредит выдан</option>
						<option value="APPROVED">Утвержден</option>
						<option value="REFUSED">Отказан</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="filter">Номер</td>
				<td class="filter"><input value="{number}" class="filterInput" name="number" type="text" maxlength="255" size="15"/>
				</td>
			</tr>
			<tr>
				<td  class="filter">Период&nbsp;c</td>
				<td class="filter">
					<input name="fromDate" value="{fromDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);onTabClick(event,'toDate')"/>
					&nbsp;по&nbsp;
					<input name="toDate" value="{toDate}" class="filterInput" type="text" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE)"/>
				</td>
			</tr>
		</table>
		</td>
	</xsl:template>
</xsl:stylesheet>
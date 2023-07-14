<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>
	
	<xsl:variable name="isShowCounter" select="/list-data/filter-data/isShowCounter = 'true'"/>
	<xsl:variable name="isShowTarif"      select="/list-data/filter-data/isShowTarif = 'true'"/>

	
	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="count(/list-data/entity-list/entity) != 0">
				<xsl:apply-templates select="/list-data/entity-list"/>
			</xsl:when>
			<xsl:otherwise>
				<table id="messageTab" width="100%">
					<tbody>
						<tr>
							<td class="messageTab" align="center">
								<br/>��&nbsp;�������&nbsp;��&nbsp;������&nbsp;�������,<br/>
								 ����������������&nbsp;���������&nbsp;�������!
							</td>
						</tr>
					</tbody>
				</table>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="/list-data/entity-list">
		<!-- ��������� ������ -->
		<table class="userTab" id="tableTitle" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr class="titleTable">
					<td rowspan="2" width="20">
						<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
					</td>
					<td rowspan="2">����</td>
					<td rowspan="2">�����</td>
					<td rowspan="2">����&nbsp;��������</td>
					<td rowspan="2">���</td>
					<td rowspan="2">�����</td>
					<td rowspan="2">�����</td>
					<xsl:if test="$isShowCounter">
						<td colspan="3">�������</td>
					</xsl:if>
					<xsl:if test="$isShowTarif">
						<td rowspan="2">�����,<br/>���.</td>
					</xsl:if>
					<td rowspan="2">������</td>
				</tr>
				<tr class="titleTable">
					<xsl:if test="$isShowCounter">
						<td>�������</td>
						<td>����.</td>
						<td>������</td>
					</xsl:if>
				</tr>
				
				<xsl:apply-templates/>
				<!-- footer -->
			</tbody>
		</table>
	</xsl:template>

	<!-- ������ ������� -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<xsl:element name="tr">
			<xsl:attribute name="class">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="@key"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;
				<xsl:if test="state.code!='TEMPLATE'">
					<a href="{$webRoot}/private/payments/showconfirmed.do?id={@key}">
						<xsl:value-of select="chargeOffAccount"/>
					</a>
				</xsl:if>
				<xsl:if test="state.code='TEMPLATE'">
					<xsl:value-of select="chargeOffAccount"/>
				</xsl:if>
				&nbsp;
			</td>
			<td  class="listItem">
				&nbsp;<xsl:value-of select="attributes.abonentName.value"/>&nbsp;
			</td>			
			<td class="listItem" align="right">
				&nbsp;
				<xsl:call-template name="month2text">
					<xsl:with-param name="code">
						<xsl:value-of select="attributes.monthSelect.value"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
				<xsl:value-of select="attributes.yearSelect.value"/>&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;<xsl:value-of select="format-number(chargeOffAmount.decimal, '#0.00')"/>&nbsp;
				<xsl:value-of select="amount.currency.code"/>&nbsp;
			</td>			
			<xsl:if test="$isShowCounter">
				<td class="listItem" align="right">
					&nbsp;<xsl:value-of select="attributes.counterCurr.value"/>&nbsp;
				</td>
				<td class="listItem" align="right">
					&nbsp;<xsl:value-of select="attributes.counterPrev.value"/>&nbsp;
				</td>
				<td class="listItem" align="right">
					&nbsp;<xsl:value-of select="attributes.consumption.value"/>&nbsp;
				</td>
			</xsl:if>
			<xsl:if test="$isShowTarif">
				<td class="listItem" align="right">
					&nbsp;
					<xsl:if test="attributes.tarif.value != ''">
						<xsl:value-of select="format-number(attributes.tarif.value, '#0.00')"/>&nbsp;
					</xsl:if>
				</td>
			</xsl:if>			
			<td class="listItem">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
		</xsl:element>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">������</xsl:when>
			<xsl:when test="$code='W'">��������������</xsl:when>
			<xsl:when test="$code='S'">��������</xsl:when>
			<xsl:when test="$code='E'">�������</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
		<xsl:template name="month2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='1'">������</xsl:when>
			<xsl:when test="$code='2'">�������</xsl:when>
			<xsl:when test="$code='3'">����</xsl:when>
			<xsl:when test="$code='4'">������</xsl:when>
			<xsl:when test="$code='5'">���</xsl:when>
			<xsl:when test="$code='6'">����</xsl:when>
			<xsl:when test="$code='7'">����</xsl:when>
			<xsl:when test="$code='8'">������</xsl:when>
			<xsl:when test="$code='9'">��������</xsl:when>
			<xsl:when test="$code='10'">�������</xsl:when>
			<xsl:when test="$code='11'">������</xsl:when>
			<xsl:when test="$code='12'">�������</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>

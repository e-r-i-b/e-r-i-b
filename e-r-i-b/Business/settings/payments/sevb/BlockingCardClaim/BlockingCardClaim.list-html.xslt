<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<?altova_samplexml list-data.xml?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="no"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>
	<xsl:template match="/">
		<xsl:if test="count(/list-data/entity-list/entity) != 0">
				<xsl:apply-templates select="/list-data/entity-list"/>
		</xsl:if>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="/list-data/entity-list">
		<!-- ��������� ������ -->
		<table class="userTab" id="tableTitle" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr class="tblInfHeader">
					<td width="20">
						<input name="isSelectAll" type="checkbox" style="border:none" onclick="switchSelection()"/>
					</td>
					<td width="80">����</td>
					<td width="50">�����</td>
					<td>�����</td>
					<td>������� ���������� �����</td>
					<td nowrap="true">������</td>
				</tr>
				<xsl:apply-templates/>
			</tbody>
		</table>				<!-- footer -->
	</xsl:template>

	<!-- ������ ������� -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<tr>
			<xsl:attribute name="style">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
			<xsl:variable name="stateNumber" select="position()"/>

			<!--��� ��� ������� �� �������!!! ����� ��� ����������� ��������-->
			<td style="display:none">
				<xsl:value-of select="state.category"/>
			</td>
			<td style="display:none">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>
			</td>

			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;
				<a href="{$webRoot}/private/payments/default-action.do?id={@key}&amp;state={state.category}">
					<xsl:value-of select="@key"/>
				</a>&nbsp;
			</td>
			<td class="listItem">
				<xsl:variable name="card" select="attributes.card.value"/>
				&nbsp;<xsl:value-of select="concat(substring($card, 1, 4), ' XXXX XXXX ', substring($card, string-length($card)-3, 4))"/>&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="attributes.reason.value"/>
				<xsl:variable name="lost" select="conditionsOfLost"/>
					<xsl:choose>
						<xsl:when test="$lost!='�� �����������'">
							&nbsp;(<xsl:value-of select="conditionsOfLost"/>)&nbsp;
						</xsl:when>
					</xsl:choose>
				&nbsp;
			</td>
			<td class="listItem">
				<script type="text/javascript">
					var stateNumber = "state<xsl:value-of select="$stateNumber"/>";
					var stateDescription = "stateDescription<xsl:value-of select="$stateNumber"/>";
					<![CDATA[
					document.write("<div id='"+stateNumber+"'>");
					document.write("<a onmouseover=\"showLayer('"+stateNumber+"','"+stateDescription+"','default',40);\" onmouseout=\"hideLayer('"+stateDescription+"');\" style='text-decoration:underline'>");
					]]>
					document.write("<xsl:call-template name='state2text'><xsl:with-param name='code'><xsl:value-of select='state.code'/></xsl:with-param></xsl:call-template>");
					<![CDATA[
					document.write("</a></div>");
					]]>
				</script>
				<xsl:if test="state.code='E'">
					<script type="text/javascript">
						var reason= '<xsl:value-of select="refusingReason"/>';
						<![CDATA[
						document.write("<div id='"+stateDescription+"' onmouseover=\"showLayer('"+stateNumber+"','"+stateDescription+"','default',40);\" onmouseout=\"hideLayer('"+stateDescription+"');\" class='layerFon' style='position:absolute; display:none; width:200px; height:90px;overflow:auto;'>");
						]]>
						document.write('������� ������: '+reason);
						<![CDATA[
						document.write("</div>");
						]]>
					</script>
				</xsl:if>
			</td>
		</tr>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">�������</xsl:when>
			<xsl:when test="$code='W'">�������</xsl:when>
			<xsl:when test="$code='A'">��������</xsl:when>
			<xsl:when test="$code='S'">���������</xsl:when>
			<xsl:when test="$code='D'">��������</xsl:when>
			<xsl:when test="$code='E'">��������</xsl:when>
			<xsl:when test="$code='F'">��������������</xsl:when>			
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
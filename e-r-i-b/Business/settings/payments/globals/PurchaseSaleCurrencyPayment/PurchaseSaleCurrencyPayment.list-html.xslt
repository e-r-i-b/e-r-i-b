<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>

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
				<tr class="tblInfHeader">
					<td width="20">
						<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
					</td>
					<td>�����</td>
					<td>����</td>
					<td>��������</td>
					<td>����&nbsp;��������</td>
					<td>�����&nbsp;��������</td>
					<td>������</td>
					<td>����&nbsp;����������</td>
					<td>�����&nbsp;����������</td>
					<td>������</td>
					<td style="width:100">������ ���������</td>
				</tr>

				<xsl:apply-templates/> 			
	</xsl:template>

	<!-- ������ ������� -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<xsl:element name="tr">
			<xsl:attribute name="class">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
			<xsl:variable name="stateNumber" select="position()"/>
<!--
			��� ��� ������� �� �������!!! ����� ��� ����������� ����������� ������
-->
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
				&nbsp;
				<a href="{$webRoot}/private/payments/default-action.do?id={@key}">
					<xsl:value-of select="documentNumber"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;
				<xsl:if test="attributes.type.value = 'BUY'">
					�������
				</xsl:if>
				<xsl:if test="attributes.type.value = 'SALE'">
					�������
				</xsl:if>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="chargeOffAccount"/>&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;
				<xsl:value-of select="format-number(chargeOffAmount.decimal, '#0.00')"/>&nbsp;
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;
				<xsl:choose>
					<xsl:when test="attributes.type.value = 'BUY'">
						<xsl:value-of select="attributes.rur-currency.value"/>
					</xsl:when>
					<xsl:when test="attributes.type.value = 'SALE'">
						<xsl:value-of select="attributes.foreign-currency.value"/>
					</xsl:when>
				</xsl:choose>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;
				<xsl:if test="state.category='initial'">
					<a href="{$webRoot}/private/payments/confirm.do?id={@key}">
						<xsl:value-of select="receiverAccount"/>
					</a>
				</xsl:if>
				<xsl:if test="state.category!='initial'">
					<xsl:value-of select="receiverAccount"/>
				</xsl:if>
				&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;
                <xsl:if test="not(destinationAmount='')">
				    <xsl:value-of select="format-number(destinationAmount, '#0.00')"/>&nbsp;
                </xsl:if>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;
				<xsl:choose>
					<xsl:when test="attributes.type.value = 'BUY'">
						<xsl:value-of select="attributes.foreign-currency.value"/>
					</xsl:when>
					<xsl:when test="attributes.type.value = 'SALE'">
						<xsl:value-of select="attributes.rur-currency.value"/>
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
					document.write("</a></div>&nbsp;");
					]]>
				</script>
			</td>
			<xsl:if test="state.description != ''">
				<script type="text/javascript">
					<![CDATA[
					document.write("<div id='"+stateDescription+"' onmouseover=\"showLayer('"+stateNumber+"','"+stateDescription+"','default',40);\" onmouseout=\"hideLayer('"+stateDescription+"');\" class='layerFon' style='position:absolute; display:none; width:120px; height:90px;overflow:auto;'>");
					]]>
					document.write("'<xsl:value-of select="state.description"/>'");
					<![CDATA[
					document.write("</div>");
					]]>
				</script>
			</xsl:if>
		</xsl:element>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">������</xsl:when>
			<xsl:when test="$code='INITIAL'">������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">��������������</xsl:when>
			<xsl:when test="$code='DELAYED_DISPATCH'">�������� ���������</xsl:when>
			<xsl:when test="$code='REFUSED'">�������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='RECALLED'">�������</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
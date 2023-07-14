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
		<table class="userTab" id="tableTitle" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr class="titleTable">
					<td width="20">
						<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
					</td>
					<td>����</td>
					<td>�����</td>
					<td>����&nbsp;��������</td>
					<td>�����&nbsp;��������</td>
					<td>����&nbsp;����������</td>
					<td>�����&nbsp;����������</td>
					<td>������</td>
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
			<xsl:variable name="stateNumber" select="position()"/>
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
					<a href="{$webRoot}/private/payments/default-action.do?id={@key}">
						<xsl:value-of select="chargeOffAccount"/>
					</a>
				</xsl:if>
				<xsl:if test="state.code='TEMPLATE'">
					<xsl:value-of select="chargeOffAccount"/>
				</xsl:if>
				&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;
				<xsl:if test="not(chargeOffAmount.decimal='')">
					<xsl:value-of select="chargeOffAmount.decimal"/>&nbsp;
					<xsl:value-of select="chargeOffAmount.currency.code"/>
				</xsl:if>
				&nbsp;
			</td>			
			<td class="listItem">
				&nbsp;
				<xsl:if test="state.code='SAVED'">
					<a href="{$webRoot}/private/payments/confirm.do?id={@key}">
						<xsl:value-of select="receiverAccount"/>
					</a>
				</xsl:if>
				<xsl:if test="state.code!='SAVED'">
					<xsl:value-of select="receiverAccount"/>
				</xsl:if>
				&nbsp;<xsl:value-of select="attributes.buy-amount-currency.value"/>
				&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				&nbsp;
				<xsl:if test="not(destinationAmount='')">
					<xsl:value-of select="destinationAmount"/>&nbsp;
					<xsl:value-of select="destinationAmount.currency.code"/>
				</xsl:if>
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
					document.write("<div id='"+stateDescription+"' onmouseover=\"showLayer('"+stateNumber+"','"+stateDescription+"','default',40);\" onmouseout=\"hideLayer('"+stateDescription+"');\" class='layerFon' style='position:absolute; display:none; width:130px; height:80px;overflow:auto;'>");
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
            <xsl:when test="$code='DELAYED_DISPATCH'">�������� ���������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">��������������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='RECALLED'">�������</xsl:when>
			<xsl:when test="$code='REFUSED'">�������</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="list&#x2D;data &#x2D;> html" userelativepaths="yes" externalpreview="no" url="list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
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

	<!-- список -->
	<xsl:template match="/list-data/entity-list">
		<!-- заголовок списка -->
				<tr class="tblInfHeader">
					<td width="20">
						<input name="isSelectAll" type="checkbox" style="border:none" onclick="switchSelection()"/>
					</td>
					<td width="80">Дата</td>
					<td width="50">Номер</td>
					<td nowrap="true">Карта</td>
					<td nowrap="true">Статус документа</td>
				</tr>
				<xsl:apply-templates/>
				<!-- footer -->
	</xsl:template>

	<!-- строка таблицы -->
	<xsl:template name="tableRow" match="/list-data/entity-list/entity">
		<tr>
			<xsl:attribute name="style">ListLine<xsl:value-of select="position() mod 2"/></xsl:attribute>
			<xsl:variable name="stateNumber" select="position()"/>
			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(dateCreated,9,2)"/>.<xsl:value-of select="substring(dateCreated,6,2)"/>.<xsl:value-of select="substring(dateCreated,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;
				<a href="{$webRoot}/private/claims/default-action.do?id={@key}">
					<xsl:value-of select="@key"/>
				</a>&nbsp;
			</td>
			<td class="listItem" nowrap="true">
				<xsl:variable name="card" select="attributes.card.value"/>
				&nbsp;<xsl:value-of select="concat(substring($card, 1, 1), '..', substring($card, string-length($card)-3, 4))"/>&nbsp;
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
				<xsl:if test="state.code='REFUSED'">
					<script type="text/javascript">
						var reason= '<xsl:value-of select="refusingReason"/>';
						<![CDATA[
						document.write("<div id='"+stateDescription+"' onmouseover=\"showLayer('"+stateNumber+"','"+stateDescription+"','default',40);\" onmouseout=\"hideLayer('"+stateDescription+"');\" class='layerFon' style='position:absolute; display:none; width:200px; height:90px;overflow:auto;'>");
						]]>
						document.write('Причина отказа: '+reason);
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
			<xsl:when test="$code='SAVED'">Введен</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Принят</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрен</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отказан</xsl:when>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="list&#x2D;>html" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\DepositReplenishmentClaimData\list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
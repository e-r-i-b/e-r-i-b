<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:param name="webRoot" select="'/PhizIC'"/>

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
					<td width="20">
						<input name="isSelectAll" style="border: medium none ;" onclick="switchSelection()" type="checkbox"/>
					</td>
					<td>�����</td>
					<td>����</td>
					<td>�����</td>
					<td>����&nbsp;��������</td>
					<td>�����&nbsp;��������</td>
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
			<td class="listItem" align="center">
				<input name="selectedIds" value="{@key}" style="border: medium none ;" type="checkbox"/>
			</td>
			<td class="listItem">
				&nbsp;<xsl:call-template name="form2text">
					<xsl:with-param name="code">
						<xsl:value-of select="formName"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;<xsl:value-of select="substring(documentDate,9,2)"/>.<xsl:value-of select="substring(documentDate,6,2)"/>.<xsl:value-of select="substring(documentDate,1,4)"/>&nbsp;
			</td>
			<td class="listItem" align="center">
				&nbsp;
				<a href="{$webRoot}/private/payments/default-action.do?id={@key}&amp;state={state.category}">
					<xsl:value-of select="documentNumber"/>
				</a>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:value-of select="chargeOffAccount"/>&nbsp;
			</td>
			<td class="listItem" align="right" nowrap="true">
				<xsl:value-of select="format-number(chargeOffAmount.decimal, '#0.00')"/>&nbsp;
				<xsl:value-of select="amount.currency.code"/>
				&nbsp;
			</td>
			<td class="listItem">
				&nbsp;<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state.code"/>
					</xsl:with-param>
				</xsl:call-template>&nbsp;
			</td>
<!--
			��� ��������� ������� �� �������!!! ����� ��� ����������� ����������� ������ � ��������������
-->
			<td style="display:none">
				<xsl:value-of select="formName"/>
			</td>
			<td style="display:none">
				<xsl:value-of select="state.category"/>
			</td>
		</xsl:element>
	</xsl:template>

	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">������</xsl:when>
			<xsl:when test="$code='S'">��������� � ���� ����������</xsl:when>
			<xsl:when test="$code='A'">������� ������ �����������</xsl:when>
			<xsl:when test="$code='X'">�����</xsl:when>
			<xsl:when test="$code='C'">�������������</xsl:when>
			<xsl:when test="$code='M'">���������</xsl:when>
			<xsl:when test="$code='L'">�������</xsl:when>
			<xsl:when test="$code='N'">���������</xsl:when>
			<xsl:when test="$code='W'">������</xsl:when>
			<xsl:when test="$code='S'">��������</xsl:when>
			<xsl:when test="$code='E'">�������</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<xsl:template name="form2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='ContactPayment'">�������</xsl:when>
			<xsl:when test="$code='RecallContactPayment'">�����</xsl:when>
			<xsl:when test="$code='EditContactPayment'">��������������</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="list&#x2D;data &#x2D;> html" userelativepaths="yes" externalpreview="no" url="list&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
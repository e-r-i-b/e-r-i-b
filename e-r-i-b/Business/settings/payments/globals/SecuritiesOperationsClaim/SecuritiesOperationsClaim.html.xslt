<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/form-data" mode="edit">
	    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<script type="text/javascript" language="JavaScript">
		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			    <xsl:with-param name="required">false</xsl:with-param>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden" value="{documentNumber}"/>
			    </xsl:with-param>    
		    </xsl:call-template>			
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="fullName" name="fullName" type="text" size="30" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Телефон (10 цифр)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="phone" name="phone" type="text" value="{phone}" size="30"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">E-mail</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="email" name="email" type="text" value="{email}" size="30"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">				
				<xsl:with-param name="rowName">Операции с ценными бумагами</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="operationSelect" name="operationSelect">
						<option value='Покупка/продажа векселя АКБ "Русславбанк"'>Покупка/продажа векселя АКБ "Русславбанк"</option>
						<option value='Операции на организованном рынке ценных бумаг (ОРЦБ)'>Операции на организованном рынке ценных бумаг (ОРЦБ)</option>
						<option value='Депозитарное обслуживание ценных бумаг'>Депозитарное обслуживание ценных бумаг</option>
					</select>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Проводились ли ранее операции с ценными бумагами</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:choose>
						<xsl:when test="notFirstCheckBox = 'true'">
							<input type="checkbox" id="notFirstCheckBox" name="notFirstCheckBox" value="true" checked="true"/>
						</xsl:when>
						<xsl:otherwise>
							<input type="checkbox" id="notFirstCheckBox" name="notFirstCheckBox" value="true"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:with-param>
			</xsl:call-template>
            <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Комментарий</xsl:with-param>
				<xsl:with-param name="rowValue">
					<textarea id="ground" name="ground" rows="4" cols="61"><xsl:value-of  select="ground"/></textarea>
				</xsl:with-param>
			</xsl:call-template>

        <script type="text/javascript" language="JavaScript">
			document.getElementById("operationSelect").value = '<xsl:value-of select="operationSelect"/>';
	        var firstName = '<xsl:value-of select="$currentPerson/entity/field[@name='firstName']"/>';
			var surName = '<xsl:value-of select="$currentPerson/entity/field[@name='surName']"/>';
			var patrName = '<xsl:value-of select="$currentPerson/entity/field[@name='patrName']"/>';
	        setElement("fullName", surName + " " + firstName + " " + patrName);
        </script>
	</xsl:template>

    <xsl:template match="/form-data" mode="view">
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер документа</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fullName"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Телефон</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="phone"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">E-mail</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="email"/>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Операции с ценными бумагами</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="operationSelect"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Проводились ли ранее операции с ценными бумагами</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:choose>
					<xsl:when test="notFirstCheckBox = 'true'">
						<input type="checkbox" checked="true" disabled="true"/>
					</xsl:when>
					<xsl:otherwise>
						<input type="checkbox" disabled="true"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Комментарий</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of  select="ground"/>
			</xsl:with-param>
		</xsl:call-template>
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус документа</xsl:with-param>
			<xsl:with-param name="rowValue">
				<div id="state">
					<span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
						<xsl:call-template name="state2text">
							<xsl:with-param name="code">
								<xsl:value-of select="state"/>
							</xsl:with-param>
						</xsl:call-template>
					</span>
				</div>
			</xsl:with-param>
		</xsl:call-template>			
	<xsl:if test="state='REFUSED'">
		<div id="stateDescription" onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="layerFon" style="position:absolute; display:none; width:400px; height:45px;overflow:auto;">
			Причина отказа: <xsl:value-of select="refusingReason"/>
		</div>
	</xsl:if>
    </xsl:template>
	<xsl:template name="standartRow">
		<xsl:param name="id"/>
		<xsl:param name="required" select="'true'"/>
		<xsl:param name="rowName"/>
		<xsl:param name="rowValue"/>
		<tr>
			<td class="{$styleClass}" style="{$styleSpecial}" id="{$id}">
					<xsl:copy-of select="$rowName"/>
				<xsl:if test="$required = 'true' and $mode = 'edit'">
					<span id="asterisk_{$id}" class="asterisk">*</span>
				</xsl:if>
			</td>
			<td>
				<xsl:copy-of select="$rowValue"/>
			</td>
		</tr>
	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрен</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="no" name="html" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\SecuritiesOperationsClaimDebugData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/><scenario default="yes" name="html &#x2D; view" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\SecuritiesOperationsClaimDebugData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
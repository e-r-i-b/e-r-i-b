<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

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

    <xsl:template match="/form-data" mode="edit">
	    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
		<script type="text/javascript" language="JavaScript">
		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>

				<script type="text/javascript" language="javascript">

		<![CDATA[
		function TermOfLease(description)
		{
			this.id          = description;
			this.description = description;
		}

		function CellType(description, presence)
		{
			this.id           = description;
			this.description  = description;
			this.presence     = presence;
			this.termsOfLease = new Array();

			this.addTermOfLease = function(termOfLease)
			{
				this.termsOfLease[this.termsOfLease.length] = termOfLease;
			}

			this.getTermsOfLease = function()
			{
				return this.termsOfLease;
			}
		}

		function Office(id, description)
		{
		    this.id          = id;
			this.description = description;
			this.cellTypes   = new Array();

			this.addCellType = function(cellType)
			{
				this.cellTypes[this.cellTypes.length] = cellType;
			}

			this.getCellTypes = function()
			{
				return this.cellTypes;
			}
		}

		function createOptions(objectArray)
		{
			var result = new Array();
			
			for ( var i = 0; i <  objectArray.length; i++ )
			{
				var object    = objectArray[i];
				var option    = new Option(object.description, object.id);
				option.object = object;
				result[i] = option;
			}

			if ( result.length == 0 )
				result[0] = new Option("нет элементов", "");

			return result;
		}

		function getElementById(id)
		{
			var element = document.getElementById(id);

			if ( element == null )
				throw new Error("Елемент [id = " + id + "] не найден");

			return element;
		}

		function updateSelect(id, options)
		{
			var select            = getElementById(id);
			select.options.length = 0;

			for ( var i = 0 ; i < options.length; i++ )
			{
				select.options[i] = options[i];
			}
		}

		function cellTypeChanged()
		{
			var cellTypeSelect = getElementById("cellType");
			var option         = cellTypeSelect.options[cellTypeSelect.selectedIndex];
			var cellType       = option.object; 
			var termsOfLease   = cellType != null ? cellType.getTermsOfLease() : new Array();
			var options        = createOptions(termsOfLease);
			updateSelect("termOfLease", options);
			
			getElementById("presence").checked = cellType != null ? cellType.presence : false; 
		}

		function officeChanged()
		{
			var officeSelect = getElementById("office");
			var office       = option.object;
			var option       = officeSelect.options[officeSelect.selectedIndex];

			var cellTypes    = office.getCellTypes();
			var options      = createOptions(cellTypes); 
			
			updateSelect("cellType", options);
			cellTypeChanged();
		}
		]]>

		var offices = new Array();
		var office;
		<xsl:for-each select="document('bankcell-dictionaries.xml')/entity-list/office">
			office = new Office('<xsl:value-of select="@id"/>', '<xsl:value-of select="description"/>'); 	
			var cellType;

			<xsl:for-each select="./cell-type">
				cellType = new CellType('<xsl:value-of select="description"/>', <xsl:value-of select="presence"/>);

				<xsl:for-each select="./term-of-lease">
					cellType.addTermOfLease(new TermOfLease('<xsl:value-of select="."/>'));
				</xsl:for-each>					

				office.addCellType(cellType);
			</xsl:for-each>				

			offices[offices.length] = office;
        </xsl:for-each>

		function init()
		{
			var officeSelect = getElementById("office");
			var options      = createOptions(offices);
            updateSelect("office", options);
            if (offices.length >0)
            {
                var officeSelectValue = '';

                if ( officeSelectValue != '' )
                    officeSelect.value = officeSelectValue;

                var option       = officeSelect.options[officeSelect.selectedIndex];
                var office       = option.object;
                var cellTypes    = office.getCellTypes();
                var options      = createOptions(cellTypes);
                updateSelect("cellType", options);
                var cellTypeSelect = '';

                if ( cellTypeSelect != '' )
                    getElementById("cellType").value = cellTypeSelect;

                cellTypeChanged();

                var termOfLeaseSelect = '';
                if ( termOfLeaseSelect != '' )
                    getElementById("termOfLease").value = termOfLeaseSelect;
            }
			var firstName = '<xsl:value-of select="$currentPerson/entity/field[@name='firstName']"/>';
			var surName = '<xsl:value-of select="$currentPerson/entity/field[@name='surName']"/>';
			var patrName = '<xsl:value-of select="$currentPerson/entity/field[@name='patrName']"/>';
	        setElement("fullName", surName + " " + firstName + " " + patrName);
		}
		</script>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
			    <xsl:with-param name="rowName">Номер документа</xsl:with-param>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden" value="{documentNumber}"/>
			    </xsl:with-param>
		    </xsl:call-template>
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Ф.И.О.</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="fullName" name="fullName" type="text" size="40" readonly="true"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Телефон</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="phone" name="phone" type="text" value="{phone}" size="64"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">E-mail</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="email" name="email" type="text" value="{email}" size="24"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Офис банка</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="office" name="office" onchange="javascript:officeChanged();"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Размер ячейки</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="cellType" name="cellType" onchange="javascript:cellTypeChanged()"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Наличие свободной ячейки</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="checkbox" id="presence" disabled="true"/>
				</xsl:with-param>
			</xsl:call-template>
            <xsl:call-template name="standartRow">
		        <xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Срок аренды</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="termOfLease" name="termOfLease"/>
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
		init();
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
					<xsl:value-of  select="phone"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">E-mail</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of  select="email"/>
				</xsl:with-param>
			</xsl:call-template>
            <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Офис банка</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of  select="office"/>
				</xsl:with-param>
			</xsl:call-template>
	        <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Размер ячейки</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of  select="cellType"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Срок аренды</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:value-of  select="termOfLease"/>
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
<scenarios ><scenario default="no" name="html" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\..\..\settings\claims\BankcellLeasingClaimDebugData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/><scenario default="yes" name="xml" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\settings\claims\BankcellLeasingClaimDebugData\form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
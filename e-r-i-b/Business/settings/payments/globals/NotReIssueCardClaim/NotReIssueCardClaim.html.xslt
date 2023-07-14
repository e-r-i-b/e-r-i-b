<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:call-template name="initCardsScript"/>
				<xsl:call-template name="editForm"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

	<xsl:template name="editForm">
		<xsl:call-template name="editHTML"/>
		<xsl:call-template name="editInitControls"/>
		<xsl:call-template name="editInitValues"/>
	</xsl:template>

<xsl:variable name="formData" select="/form-data"/>

	<xsl:template name="editHTML" >
		<script type="text/javascript" language="JavaScript">
		document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';
		</script>
            <xsl:call-template name="standartRow">
			    <xsl:with-param name="rowName">����� ���������</xsl:with-param>
			    <xsl:with-param name="required">false</xsl:with-param>
			    <xsl:with-param name="rowValue">
			        <xsl:value-of select="$formData/documentNumber"/>
			        <input id="documentNumber" name="documentNumber" type="hidden"/>
			    </xsl:with-param>
		    </xsl:call-template>		
		<xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">�.�.�.</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="fullName" name="fullName" type="text" size="30" readonly="true" maxlength="100"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">�����</xsl:with-param>
				<xsl:with-param name="rowValue">
					<select id="card" name="card" onchange="refreshText();"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">���������� ������� (�� 10 �� 20 ����)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="phone" name="phone" type="text" size="30" maxlength="20"/>
				</xsl:with-param>
			</xsl:call-template>
		    <xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">������������� ����:</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input id="account" name="account" type="text" size="30" readonly="true" maxlength="20"/>
				</xsl:with-param>
			</xsl:call-template>
	    <script type="text/javascript">
		<![CDATA[
		function refreshText()
		{
			var cardSelect = document.getElementById('card');
			if (cardSelect.selectedIndex < 0) return;
			var cardObject = cardSelect.options[cardSelect.selectedIndex].cardObject;
			if ( cardObject == null )	return;
			setValue("account", cardObject.account);
		}
		]]>
		setElement("fullName", '<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>');
		</script>
	</xsl:template>

	<xsl:template name="editInitControls">
		<script type="text/javascript">
		<![CDATA[
		function isEmptyString(value)
		{
			return value == null || value == "";
		}

		function createOptions(array, func, nullOptionText)
		{
			var options = new Array();
			for ( var i = 0; i < array.length; i++ )
			{
				options[i] = func(array[i]);
			}


			if ( options.length == 0 && !isEmptyString(nullOptionText) )
				options[options.length] = new Option(nullOptionText, "");
			return options;
		}

		function updateSelect(id, options)
		{
			var select  = document.getElementById(id);
			select.options.length = 0;
			for ( var i = 0 ; i < options.length; i++ )
			{
				select.options[i] = options[i];
			}
		}

				var cardOptionCreator = function(value)
								   {
								   		var option = new Option(value.number + ' [' + value.type + '] ', value.id);
										option.cardObject = value;
										return option;
								   }

		updateSelect('card',createOptions(cards, cardOptionCreator, "��� ����"));
		]]>
		</script>
	</xsl:template>
	<xsl:template name="editInitValues">
		<script type="text/javascript">
		function setValue(elementId, value)
		{

			var elem = document.getElementById(elementId);
			if(elem.value != null)
				elem.value = value;
			else if(elem.innerHTML != null)
				elem.innerHTML = value;
		}

		function setInitValue(elementId, value)
		{
			var elem = document.getElementById(elementId);
			if (elem != null)
			{
				if (elem.value != null)
					elem.value = value;
				else if(elem.innerHTML != null)
					elem.innerHTML = value;
			}
		}

		<xsl:call-template name="init-values">
		   <xsl:with-param name="form-data" select="$formData"/>
		</xsl:call-template>
		refreshText();
		</script>
	</xsl:template>

	<xsl:template name="initCardsScript">
		<script type="text/javascript">
			<xsl:variable name="cards" select="document(concat($data-path,'cards.xml'))"/>
			var cards = new Array();
			var card;
			<xsl:for-each select="$cards/entity-list/entity">
			<xsl:variable name="state" select="field[@name='cardState']/text()"/>
			    <xsl:if test="$state = 'active' or $state = 'replenishment'">
					card          = new Object()
					card.id       = '<xsl:value-of select="field[@name='cardLinkId']/text()"/>';
					card.number   = '<xsl:value-of select="concat(substring(@key, 1, 1), '..', substring(@key, string-length(@key)-3, 4))"/>';
					card.type     = '<xsl:value-of select="field[@name='type']/text()"/>';
					card.account       = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
					cards[cards.length] = card;
				</xsl:if>
			</xsl:for-each>
		</script>
	</xsl:template>
	<xsl:template match="/form-data" mode="view">
	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">����� ���������</xsl:with-param>
			<xsl:with-param name="rowValue"><xsl:value-of select="documentNumber"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">�.�.�.</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fullName"/>
			</xsl:with-param>
		</xsl:call-template>
        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">�����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="concat(substring(card, 1, 1), '..', substring(card, string-length(card)-3, 4), ' ', cardType)"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">���������� �������</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="phone"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������������� ����</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="account"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">������ ���������</xsl:with-param>
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
			������� ������: <xsl:value-of select="refusingReason"/>
		</div>
	</xsl:if>
    </xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">��������</xsl:when>
			<xsl:when test="$code='DISPATCHED'">����������� ������</xsl:when>
			<xsl:when test="$code='ACCEPTED'">�������</xsl:when>
			<xsl:when test="$code='EXECUTED'">��������</xsl:when>
			<xsl:when test="$code='REFUSED'">��������� ������</xsl:when>
		</xsl:choose>
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

	<xsl:template name="init-values">
	<xsl:param name="form-data"/>
	<xsl:choose>
		<xsl:when test="$form-data">
			<xsl:for-each select="$form-data/*">
				<xsl:if test="string-length(text()) > 0">
			setInitValue('<xsl:value-of select="name()"/>', '<xsl:value-of select="text()"/>');
				</xsl:if>
			</xsl:for-each>
		</xsl:when>
	</xsl:choose>
	</xsl:template>
<xsl:template match="state">

</xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
 	<xsl:param name="data-path" select="''"/>
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/loan"/>
	<xsl:variable name="products" select="document('loan-products.xml')"/>

	<xsl:variable name="styleClass" select="'Width220 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>
	<!--<xsl:variable name="description">Подача в банк заявки на кредит.</xsl:variable>-->
	<!--<xsl:variable name="title">Заявка на кредит.</xsl:variable>-->
	<xsl:variable name="loanParamsGroupName">Параметры 111запрашиваемого кредита</xsl:variable>

	<xsl:variable name="formData" select="/form-data"/>

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

		<input type="hidden" name="product" value="{product}"/>
		<input type="hidden" name="kind" value="{kind}"/>
		<input type="hidden" name="office" value="{office}"/>

		<xsl:call-template name="titleRow">
		    <xsl:with-param name="id">$$params</xsl:with-param>
			<xsl:with-param name="rowName"><xsl:value-of select="$loanParamsGroupName"/></xsl:with-param>
		</xsl:call-template>
<!--
			TODO БАРДАК!!
-->		<input type="hidden" id="documentDate" name="documentDate">
			<xsl:attribute name="value">
				<xsl:value-of select="documentDate"/>
			</xsl:attribute>
		</input>

			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Дата документа</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="hidden" id="product-name" name="product-name" value="{product-name}"/>
							<xsl:value-of select="documentDate"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Название кредитного продукта</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="hidden" id="product-name" name="product-name" value="{product-name}"/>
							<xsl:value-of select="product-name"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Название вида кредитного продукта</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="hidden" id="kind-name" name="kind-name" value="{kind-name}"/>
							<xsl:value-of select="kind-name"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Офис Банка</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="hidden" id="office-name" name="office-name" value="{office-name}"/>
							<xsl:value-of select="office-name"/>
				</xsl:with-param>
			</xsl:call-template>			
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Запрашиваемая сумма кредита</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="client-request-amount" name="client-request-amount" size="10" value="{client-request-amount}" onchange="handleAction('onchange','client-request-amount')"/>
				</xsl:with-param>
			</xsl:call-template>			
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Валюта</xsl:with-param>
				<xsl:with-param name="rowValue">
					<xsl:variable name="product" select="product"/>
							<xsl:variable name="office" select="office"/>
							<xsl:variable name="currencies" select="$products/loan-products/loan-product[@id=$product]/conditions/dynamic/condition[./value[@name='selected-office']/text()=$office]/value[@name='currency']"/>
							<select id="loan-currency" name="loan-currency" onchange="showAllowedDurations();handleAction('onchange','loan-currency')">
								<xsl:for-each select="set:distinct($currencies)">
									<option value="{text()}">
										<xsl:if test="text() = $formData/*[name()='loan-currency']/text()">
											<xsl:attribute name="selected"/>
										</xsl:if>
										<xsl:value-of select="text()"/>
									</option>
								</xsl:for-each>
							</select>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="required" select="'false'"/>
				<xsl:with-param name="rowName">Допустимые&#160;сроки&#160;кредита&#160;(месяцы)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="allowedDurations" class="culcInput" disabled="true"/>
				</xsl:with-param>
			</xsl:call-template>
			<xsl:call-template name="standartRow">
				<xsl:with-param name="rowName">Срок кредита(месяцев)</xsl:with-param>
				<xsl:with-param name="rowValue">
					<input type="text" id="client-request-term" name="client-request-term" value="{client-request-term}"/>
				</xsl:with-param>
			</xsl:call-template>

			<xsl:apply-templates select="$extendedFields/fields" mode="edit"/>
		<xsl:call-template name="java-script-edit"/>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">
		<xsl:call-template name="titleRow">
			<xsl:with-param name="id">$$params</xsl:with-param>
			<xsl:with-param name="rowName"><xsl:value-of select="$loanParamsGroupName"/></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Название кредитного продукта</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="product-name/text()"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Запрашиваемая сумма кредита</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="client-request-amount"/>
			</xsl:with-param>
		</xsl:call-template>	
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Валюта</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="loan-currency"/>
			</xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Срок кредита(месяцев)</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="client-request-term"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:apply-templates select="$extendedFields/fields" mode="view"/>
			<xsl:call-template name="titleRow">
				<xsl:with-param name="rowName">&nbsp;</xsl:with-param>
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
        <xsl:call-template name="java-script-view"/>
	</xsl:template>

	<xsl:template match="field" mode="view">
        <xsl:variable name="fieldName" select="@name"/>
		<xsl:call-template name="standartRow">
            <xsl:with-param name="rowId"><xsl:value-of select="concat('$$row_',$fieldName)"/></xsl:with-param>
			<xsl:with-param name="rowName"><xsl:value-of select="@description"/></xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:variable name="value" select="$formData/*[name()=$fieldName]/text()"/>
				<xsl:if test="string-length($value)">
					<xsl:variable name="dictionary" select="@dictionary"/>
					<xsl:choose>
						<xsl:when test="$dictionary">
							<xsl:value-of
									select="$extendedFields//dictionaries/entity-list[@name=$dictionary]/entity[@key=$value]/text()"/>
						</xsl:when>
						<xsl:when test="@type='boolean'">
							<input type="checkbox" disabled="true">
								<xsl:if test="$value='true'">
									<xsl:attribute name="checked"/>
								</xsl:if>
							</input>
						</xsl:when>
						<xsl:otherwise>
							<xsl:if test="@type!='date' or $value!='..'">
								<xsl:value-of select="$value"/>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:if>
                <input id="{$fieldName}" name="{$fieldName}" type="hidden" value="{$value}"/>
				&nbsp;
			</xsl:with-param>
		</xsl:call-template>		
	</xsl:template>

	<xsl:template match="group" mode="view">
		<xsl:call-template name="titleRow">
			<xsl:with-param name="rowName"><xsl:value-of select="@description"/></xsl:with-param>
		</xsl:call-template>
    <xsl:apply-templates mode="view"/>
	</xsl:template>

	<xsl:template match="group" mode="edit">
		<xsl:call-template name="titleRow">
			<xsl:with-param name="id"><xsl:value-of select="@name"/></xsl:with-param>
			<xsl:with-param name="rowName"><xsl:value-of select="@description"/></xsl:with-param>
		</xsl:call-template>
		<xsl:apply-templates mode="edit"/>
	</xsl:template>

	<xsl:template match="group" mode="submenu">
		createSubmenu('<xsl:value-of select="@name"/>','<xsl:value-of select="@description"/>');
<!--
		<xsl:apply-templates select="group" mode="submenu"/>
-->
	</xsl:template>

	<xsl:template match="field" mode="edit">
		<xsl:variable name="fieldName" select="@name"/>
		<xsl:variable name="dictionary" select="@dictionary"/>
		<xsl:variable name="fieldValue" select="$formData/*[name()=$fieldName]"/>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowId"><xsl:value-of select="concat('$$row_',$fieldName)"/></xsl:with-param>
			<xsl:with-param name="rowName"><xsl:if test="@hint">
					<xsl:attribute name="title">
						<xsl:value-of select="@hint"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="@description"/>
			</xsl:with-param>
			<xsl:with-param name="required"><xsl:value-of select="string(@mandatory)"/></xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:choose>
					<xsl:when test="$dictionary">
						<select id="{$fieldName}" name="{$fieldName}"
						        onchange="handleAction('onchange','{$fieldName}')">
							<xsl:if test="@value">
								<xsl:attribute name="disabled">false</xsl:attribute>
							</xsl:if>
							<xsl:apply-templates select="actions/action"/>
							<xsl:apply-templates select="$extendedFields/dictionaries/entity-list[@name=$dictionary]">
								<xsl:with-param name="selected" select="$fieldValue"/>
							</xsl:apply-templates>
						</select>
					</xsl:when>
					<xsl:when test="@type='boolean'">
						<input id="{$fieldName}" type="checkbox" onchange="handleAction('onchange','{$fieldName}')">
							<xsl:apply-templates select="actions/action"/>
							<xsl:if test="$fieldValue='true'">
								<xsl:attribute name="checked"/>
							</xsl:if>
						</input>
						<xsl:variable name="name" select="concat('$$bool_',$fieldName)"/>
						<input id="{$name}" name="{$fieldName}" type="hidden"/>
					</xsl:when>
					<xsl:otherwise>
						<input id="{$fieldName}" name="{$fieldName}" type="text" size="25" maxlength="128"
						       onchange="handleAction('onchange','{$fieldName}')">
							<xsl:if test="@readonly='true'">
								<xsl:attribute name="readonly"/>
							</xsl:if>
							<xsl:if test="@size">
								<xsl:attribute name="size">
									<xsl:value-of select="number(@size)+1"/>
								</xsl:attribute>
								<xsl:attribute name="maxlength">
									<xsl:value-of select="@size"/>
								</xsl:attribute>
							</xsl:if>

							<xsl:if test="@type='date'">
								<xsl:attribute name="size">11</xsl:attribute>
								<xsl:attribute name="maxlength">10</xsl:attribute>
							</xsl:if>

							<xsl:if test="@value">
								<xsl:attribute name="disabled">false</xsl:attribute>
							</xsl:if>

							<xsl:if test="string-length($fieldValue)>0">
								<xsl:attribute name="value">
									<!--TODO грязный хак:(-->
									<xsl:if test="@type!='date' or $fieldValue!='..'">
										<xsl:value-of select="$fieldValue"/>
									</xsl:if>
								</xsl:attribute>
							</xsl:if>
							<xsl:if test="string-length(@input-template)>0">
								<xsl:attribute name="onkeydown">
									enterNumericTemplateFld(event,this,'<xsl:value-of select="@input-template"/>');
								</xsl:attribute>
							</xsl:if>
							<xsl:apply-templates select="actions/action"/>
						</input>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="entity-list">
		<xsl:param name="selected"/>
		<xsl:apply-templates select="entity">
			<xsl:with-param name="selected" select="$selected"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="entity">
		<xsl:param name="selected"/>
		<option>
			<xsl:attribute name="value">
				<xsl:value-of select="@key"/>
			</xsl:attribute>
			<xsl:if test="@key = $selected">
				<xsl:attribute name="selected"/>
			</xsl:if>
			<xsl:value-of select="text()"/>
		</option>
	</xsl:template>

	<xsl:template match="action">
		<xsl:variable name="actionType" select="@type"/>
		<xsl:attribute name="{$actionType}">
			handleAction('<xsl:value-of select="@type"/>','<xsl:value-of select="../../@name"/>')
		</xsl:attribute>
	</xsl:template>

	<xsl:template name="java-script-edit">
		<script type="text/javascript">
			<xsl:variable name="product" select="product"/>
			var guarantorsCount =<xsl:value-of select="$products/loan-products/loan-product[@id=$product]/@guarantors-count"/>;
			<xsl:call-template name="generateSubMenu"/>

			var currentConditions = new Array();
			<xsl:variable name="office" select="office"/>
			<xsl:variable name="currentConditions" select="$products/loan-products/loan-product[@id=$product]/conditions/dynamic/condition[./value[@name='selected-office']/text()=$office]"/>
			<xsl:for-each select="$currentConditions">
				var durations = new Array();
				<xsl:for-each select="set:distinct(value[@name='duration'])">
					var duration = '<xsl:value-of select="text()"/>';
					durations[durations.length] = parseInt(duration.substring(2, duration.length));
				</xsl:for-each>

				var condition = new Object();
				condition.currency = '<xsl:value-of select="value[@name='currency']"/>';
				condition.durations = durations;

				currentConditions[currentConditions.length] = condition;
			</xsl:for-each>                                                         

			<![CDATA[

			function getField(id){
     			return document.getElementById(id);
			}

			function setFieldValue(id, val)
			{
				var elem = getField(id);
				elem.value = val;
				if (elem.type == "checkbox"){
					var boolVal = false;
					if (val == "true")
						boolVal = true;
					elem.checked = boolVal;
				}
			}

			function setFieldEnabled(id, val)
			{
     			var row = document.getElementById("$$row_"+id);
				row.style.display = val ? "" : "none"
//				getField(id).disabled = !val
			}

			var mainHandler= new HandlerChain();

			function handleAction(action, field){
			   mainHandler.doHandle(field, action);
			}

			function HandlerChain()
			{
			   this.handlers = new Array();
			}

			function ActionHandler(action, handler)
			{
			   this.action = action;
			   this.handler = handler;
			}

			function FieldActionHandler(field, action, handler)
			{
			   this.field = field;
			   this.action = action;
			   this.handler = handler;
			}

			HandlerChain.prototype.addHandler= function(handler){
			   this.handlers[this.handlers.length] = handler;
			}

			HandlerChain.prototype.doHandle = function(field, action){
			   var i;
			   for (i=0; i<this.handlers.length;i++){
				  this.handlers[i].doHandle(field, action);
			   }
			}

			ActionHandler.prototype.doHandle= function(field, action){
			  if (this.action != action){
				 return;
			  }
			  this.handler();
			}

			FieldActionHandler.prototype.doHandle = function(field, action){
			  if (this.field != field){
				 return;
			  }
			  if (this.action != action){
				 return;
			  }
			  this.handler();
			}

			function getCurrentDurations()
			{
			  var condition = getCurrentCondition();
			  if (condition == null)
				return null;

			  return condition.durations;
			}

			function showAllowedDurations()
			{
				var allowedDurations = document.getElementById("allowedDurations");
				var durations = getCurrentDurations();
				if (durations == null || durations.length == 0)
					allowedDurations.value = "-";
				else
				{
					var durationsStr = "";
					for (var i=0; i < durations.length; i++)
					{
						if (i != 0)
							durationsStr += ", ";
						durationsStr += durations[i];
					}

					var length = durationsStr.length;
					if (length < 20) length = 20;
					allowedDurations.size = length;
					allowedDurations.value = durationsStr;
				}
			}

			function getCurrentCondition()
			{
				var currentCurrency = getField("loan-currency");
	            if (currentCurrency.options.length == 0) return null;
                var currentCurrencyValue = currentCurrency.options[currentCurrency.selectedIndex].value;

                var currentCondition = null;
				for (var i=0; i<currentConditions.length; i++)
				{
					if (currentConditions[i].currency == currentCurrencyValue)
						return currentConditions[i];
				}

				return null;
			}

			]]>

			showAllowedDurations();
			<!--<xsl:call-template name="DurationSetter"/>-->
			<xsl:call-template name="registerHandlers"/>
		</script>
	</xsl:template>

    <xsl:template name="java-script-view">
        <script type="text/javascript">
            <![CDATA[

            function getField(id){
                 return document.getElementById(id);
            }

            function setFieldValue(id, val)
            {
                var elem = getField(id);
                elem.value = val;
                if (elem.type == "checkbox"){
                    var boolVal = false;
                    if (val == "true")
                        boolVal = true;
                    elem.checked = boolVal;
                }
            }

            function setFieldEnabled(id, val)
            {
                 var row = document.getElementById("$$row_"+id);
                row.style.display = val ? "" : "none"
//				getField(id).disabled = !val
            }
            ]]>
            <xsl:apply-templates select="$extendedFields//field/@enabled" mode="view"/>
        </script>
    </xsl:template>

	<xsl:template name="registerHandlers">
		<xsl:for-each select="$extendedFields//field">
			<xsl:for-each select="actions/action">
				f = function(){
					<xsl:value-of select="text()"/>
				}
				mainHandler.addHandler(
					new FieldActionHandler( '<xsl:value-of select="../../@name"/>',
							'<xsl:value-of select="@type"/>',f));
				<xsl:if test="@call-onload = 'true'">
					f();
				</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
		<xsl:apply-templates select="$extendedFields//field/@value"/>
		<xsl:apply-templates select="$extendedFields//field/@enabled"/>
		<xsl:apply-templates select="$extendedFields//field/@type[../@type='boolean']" mode="boolean"/>
	</xsl:template>

	<xsl:template match="@value">
		f = function(){
			var newValue = <xsl:value-of select="../@value"/>;
			setFieldValue('<xsl:value-of select="../@name"/>', newValue);
        };

		mainHandler.addHandler(new ActionHandler('onchange',f));
		f();
	</xsl:template>

	<xsl:template match="@enabled">
		f = function(){
			var newValue = <xsl:value-of select="../@enabled"/>;
			setFieldEnabled('<xsl:value-of select="../@name"/>', newValue);
		};

		mainHandler.addHandler(new ActionHandler('onchange',f));
		f();
	</xsl:template>

    <xsl:template match="@enabled" mode="view">
        f = function(){
            var newValue = <xsl:value-of select="../@enabled"/>;
            setFieldEnabled('<xsl:value-of select="../@name"/>', newValue);
        };
        f();
    </xsl:template>

	<xsl:template match="@type" mode="boolean">
		f = function(){
			if (getFieldValue('<xsl:value-of select="../@name"/>')){
				setFieldValue('<xsl:value-of select="concat('$$bool_',../@name)"/>', "true");
			}else{
				setFieldValue('<xsl:value-of select="concat('$$bool_',../@name)"/>', "false");
			}
		};
		mainHandler.addHandler(new FieldActionHandler('<xsl:value-of select="../@name"/>','onchange',f));
		f();
	</xsl:template>

	<xsl:template name="generateSubMenu">
		<![CDATA[
	    var elementId ="auto-generated-sub-menu";
        document.getElementById(elementId).style.marginBottom='7px';
		function createSubmenu(id, text){
			document.getElementById(elementId).innerHTML+=
                '<a href="javascript:goto(\''+id+'\')" class="elSubmenu">- '+
                text+
                '</a><br/>';
		}
		function goto(id){
			var el = document.getElementById(id);
			el.scrollIntoView();
			/*alert(el.childNodes.length);
			for ( var i=0; i < el.childNodes.length;i++ ){
				if ( (el.childNodes.item(i).tagName == "INPUT")){
					if ( !el.childNodes.item(i).disabled && el.childNodes.item(i).id.length > 0 ){
						el.childNodes.item(i).focus();
						return;
					}
				}
			}*/
		}
		]]>
		createSubmenu('$$params','<xsl:value-of select="$loanParamsGroupName"/>');
		<xsl:apply-templates select="$extendedFields//fields/group" mode="submenu"/>
	</xsl:template>

    <xsl:template name="standartRow">

	<xsl:param name="id"/>
	<xsl:param name="rowId"/>
	<xsl:param name="required" select="'true'"/>
	<xsl:param name="rowName"/>
	<xsl:param name="rowValue"/>

	<tr id="{$rowId}">
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
	<xsl:template name="titleRow">
		<xsl:param name="rowName"/>
		<xsl:param name="id"/>
		<tr>
			<td colspan="2" id="{$id}" class="{$styleClassTitle}"><xsl:copy-of select="$rowName"/></td>
		</tr>
	</xsl:template>

    <xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='DRAFT'">Черновик</xsl:when>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='COMPLETION'">Требуется доработка</xsl:when>
			<xsl:when test="$code='CONSIDERATION'">В рассмотрении</xsl:when>
			<xsl:when test="$code='EXECUTED'">Кредит выдан</xsl:when>
			<xsl:when test="$code='APPROVED'">Утвержден</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
		<!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\..\..\..\кредиты\foram&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\AccountClosingClaimData\'"/><advancedProp name="sInitialMode" value=""/><advancedProp name="bXsltOneIsOkay" value="true"/><advancedProp name="bSchemaAware" value="true"/><advancedProp name="bXml11" value="false"/><advancedProp name="iValidation" value="0"/><advancedProp name="bExtensions" value="true"/><advancedProp name="iWhitespace" value="0"/><advancedProp name="sInitialTemplate" value=""/><advancedProp name="bTinyTree" value="true"/><advancedProp name="bWarnings" value="true"/><advancedProp name="bUseDTD" value="false"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="no" url="..\..\..\..\..\..\..\..\..\..\..\кредиты\foram&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/><parameterValue name="data&#x2D;path" value="'..\..\..\..\..\..\..\..\settings\claims\AccountClosingClaimData\'"/><advancedProp name="sInitialMode" value=""/><advancedProp name="bSchemaAware" value="true"/><advancedProp name="bXsltOneIsOkay" value="true"/><advancedProp name="bXml11" value="false"/><advancedProp name="iValidation" value="0"/><advancedProp name="bExtensions" value="true"/><advancedProp name="iWhitespace" value="0"/><advancedProp name="sInitialTemplate" value=""/><advancedProp name="bTinyTree" value="true"/><advancedProp name="bUseDTD" value="false"/><advancedProp name="bWarnings" value="true"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="foram&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="extendedFields.xml" srcSchemaRoot="fields" AssociatedInstance="file:///d:/WORK/IKFL/кредиты/extendedFields.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"><block path="xsl:choose" x="303" y="0"/><block path="xsl:choose/=[0]" x="257" y="0"/><block path="xsl:choose/xsl:when/xsl:apply&#x2D;templates" x="253" y="0"/><block path="xsl:choose/=[1]" x="257" y="22"/><block path="xsl:choose/xsl:when[1]/xsl:apply&#x2D;templates" x="213" y="0"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

    <xsl:variable name="styleClass" select="'form-row'"/>
    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>
    <xsl:variable name="blockinCardClaimReasons" select="document('blocking-card-claim-reasons.xml')/entity-list/entity"/>

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

    <xsl:template match="/form-data" mode="edit" >
        <xsl:variable name="cards" select="document('stored-active-or-arrested-not-virtual-cards-with-owner.xml')/entity-list/entity"/>

        <script type="text/javascript">
			var cards = new Array();
            var USING_STORED_CARDS_RESOURCE_MESSAGE    = 'Информация по Вашим картам может быть неактуальной.';
            
			<xsl:for-each select="$cards">
			    card          = new Object()
                id = '<xsl:value-of select="field[@name='code']/text()"/>';
                card.owner    = '<xsl:value-of select="field[@name='cardOwner']/text()"/>';
                card.account  = '<xsl:value-of select="field[@name='cardAccount']/text()"/>';
                cards[id] = card;
                <xsl:if test="field[@name='isUseStoredResource'] = 'true' ">
                    addMessage(USING_STORED_CARDS_RESOURCE_MESSAGE);
                </xsl:if>
			</xsl:for-each>
		</script>
		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">card</xsl:with-param>
			<xsl:with-param name="rowName">Карта</xsl:with-param>
			<xsl:with-param name="rowValue">
                    <xsl:variable name="cardNumber" select="cardNumber"/>
                    <xsl:variable name="linkId" select="cardLink"/>
                    <select id="card" name="card" onchange="refreshText();">
                        <xsl:choose>
                            <xsl:when test="count($cards) = 0">
                                <option value="">Нет доступных карт</option>
                            </xsl:when>
                            <xsl:when test="string-length($cardNumber) = 0 and string-length($linkId) = 0">
                                <option value="">Выберите карту списания</option>
                            </xsl:when>
                        </xsl:choose>
                        <xsl:for-each select="$cards">
                            <xsl:variable name="id" select="field[@name='code']/text()"/>
                            <option>
                                <xsl:attribute name="value">
                                    <xsl:value-of select="$id"/>
                                </xsl:attribute>
                                <xsl:if test="$cardNumber= ./@key or $id=$linkId">
                                    <xsl:attribute name="selected"/>
                                </xsl:if>
                                <xsl:value-of select="./field[@name='name']"/>&nbsp;&nbsp;
                                <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                                <xsl:if test="./field[@name='amountDecimal'] != ''">
                                    <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                                </xsl:if>
                                <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                            </option>
                        </xsl:for-each>
                    </select>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
		    <xsl:with-param name="id">fullName</xsl:with-param>
		    <xsl:with-param name="required" select="'false'"/>
			<xsl:with-param name="rowName">ФИО владельца</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b>
                    <span id="showFullName"></span>
                    <input id="fullName" name="fullName" value="{fullName}" type="hidden"/>
                </b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">reason</xsl:with-param>
            <xsl:with-param name="rowName">Причина блокировки</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="blockReason" select="reason"/>
                <select id="reason" name="reason">
                    <xsl:if test="count($blockinCardClaimReasons) = 0">
                        <option value="">Нет доступных причин</option>
                    </xsl:if>
                    <xsl:for-each select="$blockinCardClaimReasons">
                        <xsl:variable name="id" select="./@key"/>
                        <option>
                            <xsl:attribute name="value">
                                <xsl:value-of select="$id"/>
                            </xsl:attribute>
                            <xsl:if test="$blockReason = ./@key">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            <xsl:value-of select="./text()"/>
                        </option>
                    </xsl:for-each>
                </select>
            </xsl:with-param>
        </xsl:call-template>

		<input id="account" name="account" type="hidden" size="30" readonly="true"/>
	    <script type="text/javascript">
		<![CDATA[
        function setValue(elementId, value)
		{

			var elem = document.getElementById(elementId);
			if(elem.value != null)
				elem.value = value;
			else if(elem.innerHTML != null)
				elem.innerHTML = value;
		}

		function refreshText()
		{
			var cardSelect = document.getElementById('card').value;
			if (!isEmpty(cardSelect))
			{
                var cardObject = cards[cardSelect];
			    if (!$.isEmptyObject(cardObject))
                {
                    setValue("showFullName", cardObject.owner);
                    setValue("fullName", cardObject.owner);
                    setValue("account",  cardObject.account);
                    return;
                }
            }
            setValue("showFullName", "");
            setValue("fullName", "");
            setValue("account",  "");
		}

		refreshText();
		]]>
		</script>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Карта</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>&nbsp;
                (<xsl:value-of select="cardName"/>)
            </xsl:with-param>
		</xsl:call-template>

	    <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">ФИО владельца</xsl:with-param>
			<xsl:with-param name="rowValue">
				<xsl:value-of select="fullName"/>
			</xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Причина блокировки</xsl:with-param>
			<xsl:with-param name="rowValue">
			    <xsl:variable name="blockReason" select="reason"/>
                <xsl:if test="count($blockinCardClaimReasons) = 0">
                    Нет доступных причин
                </xsl:if>
                <xsl:for-each select="$blockinCardClaimReasons">
                    <xsl:choose>
                        <xsl:when test="$blockReason = ./@key">
                            <xsl:value-of select="./text()"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус заявки</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
                    <span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:choose>
                            <xsl:when test="$app = 'PhizIA'">
                                <xsl:call-template name="employeeState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="clientState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                    </span>
                </div>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>
	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введена (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введена (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрена</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнена</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказана (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлена (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
		</xsl:choose>
    </xsl:template>
	<xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
			<xsl:when test="$code='ACCEPTED'">Одобрена</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнена</xsl:when>
			<xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">Приостановлена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
		</xsl:choose>
    </xsl:template>

    <!-- шаблон формирующий div описания -->
<xsl:template name="buildDescription">
   <xsl:param name="text"/>

   <xsl:variable name="delimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Подробнее.</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="firstDelimiter">
   <![CDATA[
   <a href="#" onclick="payInput.openDetailClick(this); return false;">Как заполнить это поле?</a>
   <div class="detail" style="display: none">
   ]]>
   </xsl:variable>

   <xsl:variable name="end">
   <![CDATA[ </div>
   ]]>
   </xsl:variable>

   <div class="description" style="display: none">

    <xsl:variable name="nodeText" select="xalan:nodeset($text)"/>

   <xsl:for-each select="$nodeText/node()">

   <xsl:choose>
		<xsl:when test=" name() = 'cut' and position() = 1 ">
		    <xsl:value-of select="$firstDelimiter" disable-output-escaping="yes"/>
		</xsl:when>
        <xsl:when test="name() = 'cut' and position() != 1">
            <xsl:value-of select="$delimiter" disable-output-escaping="yes"/>
        </xsl:when>
   		<xsl:otherwise>
		<xsl:copy />
		</xsl:otherwise>
   </xsl:choose>
   </xsl:for-each>

   <xsl:if test="count($nodeText/cut) > 0">
   <xsl:value-of select="$end" disable-output-escaping="yes"/>
   </xsl:if>
	</div>

</xsl:template>

<xsl:template name="standartRow">

	<xsl:param name="id"/>
    <xsl:param name="lineId"/>                      <!--идентификатор строки-->
	<xsl:param name="required" select="'true'"/>    <!--параметр обязатьльности заполнения-->
	<xsl:param name="rowName"/>                     <!--описание поля-->
	<xsl:param name="rowValue"/>                    <!--данные-->
	<xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
	<!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

	<xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
	<!-- Определение имени инпута или селекта передаваемого в шаблон -->
	<!-- inputName - fieldName или имя поле вытащеное из rowValue -->
	<xsl:variable name="inputName">
	<xsl:choose>
		<xsl:when test="string-length($fieldName) = 0">
				<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
					<xsl:value-of select="$nodeRowValue/input/@name" />
					<xsl:if test="count($nodeRowValue/select[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/select/@name" />
					</xsl:if>
                    <xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
						<xsl:value-of select="$nodeRowValue/textarea/@name" />
					</xsl:if>
				</xsl:if>
		</xsl:when>
		<xsl:otherwise>
				<xsl:copy-of select="$fieldName"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>

    <xsl:variable name="readonly">
		<xsl:choose>
			<xsl:when test="string-length($inputName)>0">
				<xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
			</xsl:when>
			<xsl:otherwise>
				0
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

<!--  Поиск ошибки по имени поля
В данной реализации ошибки обрабатывает javascript
                <xsl:if test="$mode = 'edit'">
                    <xsl:if test="boolean($validationErrors/entity[@key=$fieldName])">
                        <xsl:copy-of select="$validationErrors/entity[@key=$fieldName]"/>
                    </xsl:if>
                </xsl:if>
-->

<div class="{$styleClass}">
<xsl:if test="string-length($lineId) > 0">
            <xsl:attribute name="id">
                <xsl:copy-of select="$lineId"/>
            </xsl:attribute>
</xsl:if>
<xsl:if test="$readonly = 0 and $mode = 'edit'">
    <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
</xsl:if>

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>

				<xsl:if test="$readonly = 0 and $mode = 'edit'">
					<xsl:call-template name="buildDescription">
						<xsl:with-param name="text" select="$description"/>
	    			</xsl:call-template>
				</xsl:if>
                <div class="errorDiv" style="display: none;">
				</div>
	</div>
    <div class="clear"></div>
</div>

<!-- Устанавливаем события onfocus поля -->
	<xsl:if test="string-length($inputName) > 0 and $readonly = 0 and $mode = 'edit'">
		<script type="text/javascript">
		if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
		document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
		</script>
	</xsl:if>

</xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>
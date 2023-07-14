<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:cu="java://com.rssl.phizic.business.cards.CardsUtil"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
        >

    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
    <xsl:output method="html" version="1.0" indent="yes"/>
    <xsl:param name="mode" select="'view'"/>
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

    <xsl:template match="/form-data" mode="edit">
        <input name="offerId"        id="offerId"  type="hidden" value="{offerId}"/>
        <input name="card-id"        id="card-id"  type="hidden" value="{card-id}"/>
        <input name="feedbackType"   id="feedbackType" type="hidden" value="{feedbackType}"/>
        <xsl:variable name="offerExpDate" select="dh:formatXsdDateToString(offerExpDate)"/>
        <input name="offerExpDate"   id="offerExpDate" type="hidden" value="{$offerExpDate}"/>
        <input id="documentNumber" name="documentNumber" type="hidden" value="{documentNumber}"/>
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Ваша кредитная карта</b></xsl:with-param>
        </xsl:call-template>
         <div class="limitFormRows">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">
                    <xsl:variable name="imgCode" select="cu:getCardImageCode(cardDesc)"/>
                    <img src="{$resourceRoot}/commonSkin/images/cards_type/icon_cards_{$imgCode}64.gif"/>
                </xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="cardLimitType">
                        <xsl:value-of select="cardName"/>, <xsl:value-of select="mu:getCurrencySign(cardCurrency)"/>
                    </div>
                    <div class="cardLimitNum">
                        <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>
                    </div>
                    <input id="cardDesc" name="cardDesc" type="hidden" value="{cardDesc}"/>
                    <input id="cardName" name="cardName" type="hidden" value="{cardName}"/>
                    <input id="cardCurrency" name="cardCurrency" type="hidden" value="{cardCurrency}"/>
                    <input id="cardNumber" name="cardNumber" type="hidden" value="{cardNumber}"/>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Действующий кредитный лимит:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="currentLimit" value="{currentLimit}"/>
                    <input type="hidden" name="currentLimitCurrency" value="{currentLimitCurrency}"/>
                    <xsl:value-of select="format-number(currentLimit, '### ##0,00', 'sbrf')"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(currentLimitCurrency)"/>
                </xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">
                <div class="newSum">
                    Новый кредитный<br /> лимит:
                </div>
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="newLimit" value="{newLimit}"/>
                <input type="hidden" name="newLimitCurrency" value="{newLimitCurrency}"/>
                <span class="summ newLimitSum">
                    <xsl:value-of select="format-number(newLimit, '### ##0,00', 'sbrf')"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign(newLimitCurrency)"/>
                </span>
            </xsl:with-param>
        </xsl:call-template>
            <div class="limitsSeparate"> </div>
        </div>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Карта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="cardName"/>&nbsp;<xsl:value-of select="mask:getCutCardNumber(cardNumber)"/> </b><br/>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b> <xsl:value-of select="mu:getCurrencySign(cardCurrency)"/> </b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Действующий кредитный лимит:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="format-number(currentLimit, '### ##0,00', 'sbrf')"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign(currentLimitCurrency)"/>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Новый кредитный лимит:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="format-number(newLimit, '### ##0,00', 'sbrf')"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign(newLimitCurrency)"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Статус заявки:</xsl:with-param>
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
    </xsl:template>

    <xsl:template name="state2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='EXECUTED'">Принято</xsl:when>
            <xsl:when test="$code='REFUSED'">Не принято</xsl:when>
            <xsl:otherwise>Черновик</xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>
        <div class="paymentRowTtl">
            <xsl:if test="string-length($lineId) > 0">
                <xsl:attribute name="id">
                    <xsl:copy-of select="$lineId"/>
                </xsl:attribute>
            </xsl:if>
            <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
        </div>
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
        <xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
        <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->
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

 <xsl:variable name="styleClass">
		<xsl:choose>
			<xsl:when test="$isAllocate = 'true'">
				<xsl:value-of select="'form-row'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'form-row-addition'"/>
			</xsl:otherwise>
		</xsl:choose>
 </xsl:variable>

<div class="{$styleClass}">
    <xsl:if test="string-length($lineId) > 0">
        <xsl:attribute name="id">
            <xsl:copy-of select="$lineId"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="string-length($rowStyle) > 0">
        <xsl:attribute name="style">
            <xsl:copy-of select="$rowStyle"/>
        </xsl:attribute>
    </xsl:if>
    <xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
        <xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
    </xsl:if>

	<div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
        <div class="paymentInputDiv">
            <xsl:copy-of select="$rowValue"/>
        </div>

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
</xsl:stylesheet>
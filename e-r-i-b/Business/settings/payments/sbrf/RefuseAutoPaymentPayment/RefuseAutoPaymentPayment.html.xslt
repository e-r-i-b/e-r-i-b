<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:recipient="java://com.rssl.phizic.web.util.RecipientUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:param name="mode" select="'view'"/>
    <xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
    <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>

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

<xsl:template mode="edit" match="/form-data">
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Получатель:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="receiverName" value="{receiverName}"/>
            <xsl:value-of select="receiverName"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName"><xsl:value-of select="requisiteName"/>:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="requisite" value="{requisite}"/>
            <xsl:value-of select="requisite"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="cardNumber" select="cardNumber"/>
    <xsl:variable name="card" select="document('cards.xml')/entity-list/entity[@key=$cardNumber]"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="cardId" value="{$card/field[@name='cardLinkId']}"/>
            <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>&nbsp;
            <xsl:if test="$card != ''">
                [<xsl:value-of select="$card/field[@name='name']"/>]&nbsp;
                <xsl:value-of select="format-number($card/field[@name='amountDecimal'], '### ##0,00','sbrf')"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign($card/field[@name='currencyCode'])"/>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="executionEventType!='BY_INVOICE' and executionEventType!='REDUSE_OF_BALANCE'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="summ">
                    <input type="hidden" name="sellAmount" value="{sellAmount}"/>
                    <xsl:value-of select="format-number(sellAmount, '### ##0,00','sbrf')"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign('RUB')"/>
                </span>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <input type="hidden" name="executionEventType" value="{executionEventType}"/>
    <input type="hidden" name="autoPaymentFloorLimit" value="{autoPaymentFloorLimit}"/>
    <input type="hidden" name="autoPaymentFloorCurrency" value="{autoPaymentFloorCurrency}"/>
    <input type="hidden" name="autoPaymentStartDate" value="{autoPaymentStartDate}"/>
    <input type="hidden" name="autoPaymentName" value="{autoPaymentName}"/>
    <xsl:call-template name="regularParameters"/>
</xsl:template>

<xsl:template mode="view" match="/form-data">

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Получатель:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="receiverName"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName"><xsl:value-of select="requisiteName"/>:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="requisite"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>&nbsp;
            [<xsl:value-of select="cardName"/>]&nbsp;
            <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="executionEventType!='BY_INVOICE' and executionEventType!='REDUSE_OF_BALANCE'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="summ">
                    <xsl:value-of select="format-number(sellAmount, '### ##0,00','sbrf')"/>&nbsp;
                    <xsl:value-of select="mu:getCurrencySign('RUB')"/>
                </span>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="regularParameters"/>

    <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус платежа</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
                        <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
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
    <xsl:template name="regularParameters">
        <xsl:call-template name="titleRow">
           <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Оплачивается:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="executionEventType='BY_INVOICE'">при выставлении счета</xsl:when>
                    <xsl:when test="executionEventType='ONCE_IN_MONTH'">каждый месяц</xsl:when>
                    <xsl:when test="executionEventType='ONCE_IN_QUARTER'">каждый квартал</xsl:when>
                    <xsl:when test="executionEventType='ONCE_IN_HALFYEAR'">каждое полугодие</xsl:when>
                    <xsl:when test="executionEventType='ONCE_IN_YEAR'">каждый год</xsl:when>
                    <xsl:when test="executionEventType='REDUSE_OF_BALANCE'">
                        При снижении баланса <xsl:value-of select="requisite"/> до&nbsp;
                        <xsl:value-of select="format-number(autoPaymentFloorLimit,'### ##0,00','sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/>
                    </xsl:when>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="autoPaymentStartDate"  select="autoPaymentStartDate"/>
        <xsl:variable name="month" select="substring($autoPaymentStartDate, 4, 2)"/>
        <xsl:choose>
            <xsl:when test="executionEventType='BY_INVOICE'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Максимальный размер платежа:</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:value-of select="format-number(autoPaymentFloorLimit,'### ##0,00','sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="executionEventType!='REDUSE_OF_BALANCE'">
                <xsl:choose>
                    <xsl:when test="executionEventType='ONCE_IN_QUARTER'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Месяцы оплаты:</xsl:with-param>
                            <xsl:with-param name="required">false</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:variable name="period">
                                    <xsl:if test="($month mod 3) = 1">
                                        <xsl:value-of select="'01|04|07|10'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 3) = 2">
                                        <xsl:value-of select="'02|05|08|11'"/>
                                    </xsl:if>
                                    <xsl:if test="($month mod 3) = 0">
                                        <xsl:value-of select="'03|06|09|12'"/>
                                    </xsl:if>
                                </xsl:variable>

                                <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"  select="$period"/>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="executionEventType='ONCE_IN_YEAR'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Месяц оплаты:</xsl:with-param>
                            <xsl:with-param name="required">false</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <xsl:call-template name="monthsToString">
                                    <xsl:with-param name="value"  select="$month"/>
                                    <xsl:with-param name="source" select="$months"/>
                                </xsl:call-template>
                                <xsl:value-of select="concat('. ', substring(autoPaymentStartDate,1,2), ' число')"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="executionEventType = 'REDUSE_OF_BALANCE'">
                <xsl:variable name="totalPeriod">
                    <xsl:choose>
                        <xsl:when test="string-length(autoPaymentTotalAmountPeriod) > 0">
                            <xsl:value-of select="autoPaymentTotalAmountPeriod"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="recipient:getAutoPayTotalAmountPeriod(linkId)"/>
                        </xsl:otherwise>    
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="totalLimit">
                    <xsl:if test="string-length(autoPaymentTotalAmountPeriod) > 0">
                        <xsl:value-of select="autoPaymentTotalAmountLimit"/>
                    </xsl:if>
                </xsl:variable>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Сумма:</xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                    <span class="summ">
                        <input type="hidden" name="sellAmount" value="{sellAmount}"/>
                        <xsl:value-of select="format-number(sellAmount, '### ##0,00','sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign('RUB')"/>
                    </span>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="string-length($totalPeriod) > 0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="rowName">
                            Максимальная сумма в
                            <xsl:call-template name="periodTotalAmount2text">
                                <xsl:with-param name="code" select="$totalPeriod"/>
                            </xsl:call-template>:
                        </xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <xsl:choose>
                               <xsl:when test="string-length($totalLimit) > 0">
                                   <xsl:value-of select="$totalLimit"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(autoPaymentTotalAmountCurrency)"/>
                               </xsl:when>
                               <xsl:otherwise>
                                   Не задано
                               </xsl:otherwise>
                           </xsl:choose>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:when>
        </xsl:choose>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Название:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="autoPaymentName"/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        </xsl:choose>
    </xsl:template>


<!--получение списка месяцев в строку-->
	<xsl:template name="monthsToString">
	    <xsl:param name="value"/>
	    <xsl:param name="source"/>

        <xsl:variable name="delimiter" select="'|'"/>

        <xsl:choose>
            <xsl:when test="contains($value, $delimiter)">
                <xsl:for-each select="xalan:tokenize($value, $delimiter)">
                    <xsl:value-of select="concat($source[@key = current()]/text(), ' ')"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat($source[@key = $value]/text(), ' ')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Переводим название месяца в родительский падеж по номеру месяца -->
    <xsl:template name="parentalCaseMonth">
      <xsl:param name="numberMonth"/>
      <xsl:param name="source"/>
         <xsl:variable name="nameMonth" select="$source[@key = $numberMonth]"/>
          <xsl:choose>
              <xsl:when test="$numberMonth = '03' or $numberMonth = '08'">
                  <xsl:value-of select="concat($nameMonth,'a')"/>
              </xsl:when>
              <xsl:otherwise>
                  <xsl:value-of select="concat(substring($nameMonth, 0, string-length($nameMonth)),'я')"/>
              </xsl:otherwise>
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

<xsl:template name="titleRow">
    <xsl:param name="lineId"/>
    <xsl:param name="rowName"/>
    <xsl:param name="rowValue"/>
    <div>
        <xsl:if test="string-length($lineId) > 0">
            <xsl:attribute name="id">
                <xsl:copy-of select="$lineId"/>
            </xsl:attribute>
        </xsl:if>
        <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
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

    <div class="paymentLabel">
        <span class="paymentTextLabel">
            <xsl:if test="string-length($id) > 0">
                <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
            <xsl:copy-of select="$rowName"/>
        </span>
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

<xsl:template name="periodTotalAmount2text">
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='IN_DAY'">день</xsl:when>
        <xsl:when test="$code='IN_WEEK'">неделю</xsl:when>
        <xsl:when test="$code='IN_TENDAY'">декаду</xsl:when>
        <xsl:when test="$code='IN_MONTH'">месяц</xsl:when>
        <xsl:when test="$code='IN_QUARTER'">квартал</xsl:when>
        <xsl:when test="$code='IN_YEAR'">год</xsl:when>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>
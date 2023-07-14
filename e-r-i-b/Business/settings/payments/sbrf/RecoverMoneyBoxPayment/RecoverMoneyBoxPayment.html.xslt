<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
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

<xsl:template match="/form-data">

    <div class="productTitleDetailInfo" style="border-bottom:none;">
        <div id="productNameText" name="productNameText" class="productTitleMargin word-wrap" style="margin-left: 10px;">
            <span class="productTitleDetailInfoText">
                <span id="moneyBoxName" name="moneyBoxNameTitle"><xsl:value-of select="moneyBoxName"/></span>
            </span>
        </div>
    </div>
    <div style="float:none;"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="id">moneyBoxType</xsl:with-param>
        <xsl:with-param name="rowName">Вид копилки</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="moneyBoxType = 'FIXED_SUMMA'">
                    Фиксированная сумма
                </xsl:when>
                <xsl:when test="moneyBoxType = 'PERCENT_BY_ANY_RECEIPT'">
                    Процент от зачислений
                </xsl:when>
                <xsl:when test="moneyBoxType = 'PERCENT_BY_DEBIT'">
                    Процент от расходов
                </xsl:when>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>



    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Счет зачисления</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="au:getFormattedAccountNumber(receiverAccountSelect)"/>
            &nbsp;[<xsl:value-of select="receiverAccountName"/>]&nbsp;
            <xsl:value-of select="mu:getCurrencySign(receiverResourceCurrency)"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Карта списания</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
            &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
            <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:choose>
        <xsl:when test="moneyBoxType = 'FIXED_SUMMA'">
            <xsl:variable name="eventTypes" select="document('money-box-events-types.xml')/entity-list/entity"/>
            <xsl:variable name="eventType"  select="executionEventType"/>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="id">executionEventType</xsl:with-param>
                <xsl:with-param name="rowName">Периодичность</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="eventTypeDescription">
                        <xsl:for-each select="$eventTypes">
                            <xsl:if test="./@key = 'ONCE_IN_MONTH' or ./@key = 'ONCE_IN_QUARTER' or ./@key = 'ONCE_IN_WEEK' or ./@key = 'ONCE_IN_YEAR'">
                                <xsl:if test="$eventType = ./@key">
                                    <xsl:value-of select="./text()"/>
                                </xsl:if>
                            </xsl:if>
                        </xsl:for-each>
                    </xsl:variable>
                    <xsl:value-of select="$eventTypeDescription"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
               <xsl:with-param name="rowName">Дата первого пополнения</xsl:with-param>
               <xsl:with-param name="rowValue">
                   <xsl:variable name="longOfferStartDate">
                        <xsl:choose>
                            <xsl:when test="contains(longOfferStartDate, '-')">
                                <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:copy-of select="longOfferStartDate"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:value-of select="$longOfferStartDate"/>
               </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName"></xsl:with-param>
                <xsl:with-param name="rowValue">
                    Пополнение будет осуществляться
                    <div id="awaysPeriodicDescription" style="font-weight:bold;"></div>
                </xsl:with-param>
            </xsl:call-template>

            <script type="text/javascript">
                var daysOfWeekDesc = ["понедельникам", "вторникам", "средам", "четвергам", "пятницам", "субботам", "воскресеньям"];
                var monthOfYearDesc = ["января", "февраля", "марта", "апреля", "мая", "июня", "июля",   "августа", "сентября", "октября","ноября", "декабря"]
                var descriptionElement = document.getElementById('awaysPeriodicDescription');
                var eventTypeValue = '<xsl:value-of select="moneyBoxType"/>';
                <xsl:variable name="longOfferStartDate">
                    <xsl:choose>
                        <xsl:when test="contains(longOfferStartDate, '-')">
                            <xsl:copy-of select="concat(substring(longOfferStartDate, 9, 2), '.', substring(longOfferStartDate, 6, 2), '.', substring(longOfferStartDate, 1, 4))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:copy-of select="longOfferStartDate"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                var startDateValue = Str2Date('<xsl:value-of select="$longOfferStartDate"/>');
                if(eventTypeValue == 'FIXED_SUMMA')
                {
                    var periodicValue = '<xsl:value-of select="executionEventType"/>';
                    var description = "";
                    if(periodicValue == 'ONCE_IN_WEEK')
                    {
                        description = "Раз в неделю, по " + daysOfWeekDesc[(startDateValue.getDay()+6)%7];
                    }
                    else if(periodicValue == 'ONCE_IN_MONTH')
                    {
                        description = "Раз в месяц, " + startDateValue.getDate() + "-го числа";
                    }
                    else if(periodicValue == 'ONCE_IN_QUARTER')
                    {
                        description = "Раз в квартал, " + startDateValue.getDate() + "-го числа " + (startDateValue.getMonth()%3 + 1) + "-го месяца";
                    }
                    else if(periodicValue == 'ONCE_IN_YEAR')
                    {
                        description = "Раз в год, " + startDateValue.getDate()+"-го " + monthOfYearDesc[startDateValue.getMonth()];
                    }
                    descriptionElement.innerHTML = description;
                }
            </script>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">sellAmountRow</xsl:with-param>
                <xsl:with-param name="id">sellAmount</xsl:with-param>
                <xsl:with-param name="rowName">Сумма пополнения</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:if test="string-length(sellAmount)>0">
                        <span class="summ">
                            <xsl:value-of select="sellAmount"/>&nbsp;
                            <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                        </span>
                    </xsl:if>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:when>
        <xsl:when test="moneyBoxType = 'PERCENT_BY_ANY_RECEIPT' or moneyBoxType = 'PERCENT_BY_DEBIT'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">percentRow</xsl:with-param>
                <xsl:with-param name="id">percent</xsl:with-param>
                <xsl:with-param name="rowName">% от суммы</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="percent"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="lineId">maxSumWriteRow</xsl:with-param>
                <xsl:with-param name="id">maxSumWrite</xsl:with-param>
                <xsl:with-param name="description">Введите сумму, которую необходимо перевести</xsl:with-param>
                <xsl:with-param name="rowName">Максимальная сумма</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="sellAmount"/>&nbsp; <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:when>
    </xsl:choose>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Статус</xsl:with-param>
        <xsl:with-param name="rowValue"><b>
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
            </div></b>
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

    <xsl:template name="moneyBoxState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='FIXED'">Фиксированная сумма</xsl:when>
            <xsl:when test="$code='ENROLL'">По зачислению</xsl:when>
            <xsl:when test="$code='FLOW'">По расходу</xsl:when>
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
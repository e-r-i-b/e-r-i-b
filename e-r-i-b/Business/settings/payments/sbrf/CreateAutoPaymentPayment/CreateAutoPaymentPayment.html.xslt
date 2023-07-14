<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:spu="com.rssl.phizic.web.util.RecipientUtil"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="months" select="document('months.xml')/entity-list/entity"/>
    <xsl:variable name="autoPaymentTypes" select="document('auto-payment-types.xml')/entity-list"/>
    <xsl:param name="mode" select="'view'"/>
    <xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>

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
    <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards.xml')/entity-list/*"/>
    <xsl:variable name="fromResource" select="$activeCards[@key = $formData/fromAccountSelect/text() or ./field[@name = 'code'] = $formData/fromResource/text()]"/>
    <xsl:variable name="fromResourceNumber" select="$fromResource/@key"/>
    <xsl:variable name="serviceProvider" select="document(concat('serviceProvider.xml?id=',recipient))/entity-list/entity"/>
    <xsl:variable name="codeService" select="$serviceProvider/field[@name = 'code']"/>
    <xsl:variable name="supportedTypes" select="document(concat('auto-payment-allowed-types.xml?provider-id=', recipient, '&amp;requisite=', requisite, '&amp;card-number=',$fromResourceNumber))/entity-list"/>
    <xsl:variable name="receiverName" select="$serviceProvider/field[@name = 'name']"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Получатель:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="recipient" value="{recipient}"/>
            <input type="hidden" name="codeService" value="{$codeService}"/>
            <input type="hidden" name="receiverName" value="{$receiverName}"/>
            <xsl:value-of select="$receiverName"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="fromResource" value="{$fromResource/field[@name = 'code']/text()}"/>
            <xsl:value-of select="mask:getCutCardNumber($fromResourceNumber)"/>
            &nbsp;[ <xsl:value-of select="$fromResource/field[@name = 'name']"/>]
            &nbsp;<xsl:value-of select="$fromResource/field[@name = 'amountDecimal']"/>
            &nbsp;<xsl:value-of select="mu:getCurrencySign($fromResource/field[@name = 'currencyCode'])"/>
        </xsl:with-param>
    </xsl:call-template>
    <input type="hidden" name="requisiteName" value="{requisiteName}"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">
           <xsl:value-of select="requisiteName"/>:
        </xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="requisite" value="{requisite}"/>
            <xsl:value-of select="requisite"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">amountRow</xsl:with-param>
        <xsl:with-param name="rowName">Сумма:</xsl:with-param>
        <xsl:with-param name="required">true</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="text" size="30" maxlength="30" name="sellAmount" value="{sellAmount}" class="moneyField"/>
            &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
        </xsl:with-param>
    </xsl:call-template>

     <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Настройки автоплатежа</b></xsl:with-param>
     </xsl:call-template>

    <input type="hidden" name="firstPaymentDate"  value="{firstPaymentDate}"/>
    <input type="hidden" name="autoPaymentFloorLimit" id="autoPaymentFloorLimit" value="{autoPaymentFloorLimit}"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId">eventType</xsl:with-param>
        <xsl:with-param name="rowName">Оплачивать:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:variable name="autoPaymentType" select="autoPaymentType"/>
            <xsl:choose>
                <xsl:when test="count($supportedTypes/entity) > 1">
                    <select id="listType" onchange="changeType(true)" name="autoPaymentType">
                        <xsl:for-each select="$supportedTypes/entity">
                           <xsl:variable name="key" select="./@key"/>
                           <option value="{$key}">
                               <xsl:if test="$key = $autoPaymentType">
                                   <xsl:attribute name="selected"/>
                               </xsl:if>
                               <xsl:value-of select="$autoPaymentTypes/entity/field[@name=$key]/text()"/>
                           </option>
                        </xsl:for-each>
                    </select>
                </xsl:when>
                <xsl:when test="count($supportedTypes/entity) = 1">
                    <xsl:variable name="key" select="$supportedTypes/entity[1]/@key"/>
                    <b><xsl:value-of select="$autoPaymentTypes/entity/field[@name=$key]/text()"/></b>
                    <input type="hidden" name="autoPaymentType" id="listType" value="{$key}"/>
                </xsl:when>
                <xsl:otherwise>
                    Не задан тип автоплатежа
                    <input type="hidden" name="autoPaymentType" id="listType" value="NONE" disabled="true"/>
                    <script type="text/javascript">
                        $(document).ready(function() {
                            payInput.fieldError("autoPaymentType", "В адрес данного поставщика услуг невозможно оформить автоплатеж.");
                        });
                    </script>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>


    <xsl:variable name="firstPaymentDate">
        <xsl:choose>
            <xsl:when test="contains(firstPaymentDate, '-')">
                <xsl:copy-of select="concat(substring(firstPaymentDate, 9, 2), '.', substring(firstPaymentDate, 6, 2), '.', substring(firstPaymentDate, 1, 4))"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="firstPaymentDate"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>
    <xsl:variable name="month"  select="substring($firstPaymentDate, 4, 2)"/>

    <!-- Содержимое которое меняется в зависимости от выбранного типа -->
    <div id="changeContent">

        <div id="ONCE_IN_MONTH;ONCE_IN_QUARTER;ONCE_IN_YEAR">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата начала действия:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="autoPaymentStartDate">
                        <xsl:choose>
                            <xsl:when test="contains(autoPaymentStartDate, '-')">
                                <xsl:copy-of select="concat(substring(autoPaymentStartDate, 9, 2), '.', substring(autoPaymentStartDate, 6, 2), '.', substring(autoPaymentStartDate, 1, 4))"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:copy-of select="autoPaymentStartDate"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <input name="autoPaymentStartDate" id="autoPaymentStartDate" class="dot-date-pick" size="10" value="{$autoPaymentStartDate}"
                           onchange="javascript:changeType(false);" onkeyup="javascript:changeType(false);"
                        />
                </xsl:with-param>
            </xsl:call-template>
        </div>

        <xsl:if test="$supportedTypes/entity[@key='REDUSE_OF_BALANCE']">
             <!-- При снижении баланса лицевого счета до указанной суммы -->
            <xsl:variable name="floorLimits" select="$supportedTypes/entity[@key='REDUSE_OF_BALANCE']/field[@name = 'thresholdAutoPayLimit']"/>
            <div id="REDUSE_OF_BALANCE">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Минимальный баланс:</xsl:with-param>
                    <xsl:with-param name="required">true</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="floorLimit" select="autoPaymentFloorLimit"/>
                        <xsl:choose>
                            <xsl:when test="count($floorLimits)>0">
                                <select id="REDUSE_OF_BALANCE_FLOOR_LIMIT" onchange="changeType(false)">
                                    <xsl:for-each select="$floorLimits">
                                        <xsl:sort select="/text()" data-type="number"/>
                                        <option value="{./text()}">
                                            <xsl:if test="number(./text())=number($floorLimit)">
                                                <xsl:attribute name="selected"/>
                                            </xsl:if>
                                            <xsl:value-of select="format-number(./text(), '### ##0,00','sbrf')"/>
                                        </option>
                                    </xsl:for-each>
                                </select>
                            </xsl:when>
                            <xsl:otherwise>
                                <input type="text" id="REDUSE_OF_BALANCE_FLOOR_LIMIT" onkeyup="changeType(false)" size="10" value="{$floorLimit}" class="moneyField"/>
                            </xsl:otherwise>
                        </xsl:choose>
                        &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Сумма:</xsl:with-param>
                    <xsl:with-param name="required">true</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="text" size="30" maxlength="30" name="sellAmount" value="{sellAmount}" class="moneyField"/>
                        &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="reduseOfBalanceEntity" select="$supportedTypes/entity[@key='REDUSE_OF_BALANCE']"/>
                <xsl:if test="$reduseOfBalanceEntity/field[@name = 'isTotalMaxSum'] = 'true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">
                            Максимальная сумма в
                            <xsl:call-template name="periodTotalAmount2text">
                               <xsl:with-param name="code" select="$reduseOfBalanceEntity/field[@name = 'totalMaxSumPeriod']"/>
                           </xsl:call-template>:
                        </xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="autoPaymentTotalAmountLimit" size="10" class="moneyField" value="{autoPaymentTotalAmountLimit}"/>
                            &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
                        </xsl:with-param>
                        <xsl:with-param name="description" select="'Введите максимальную сумму платежей в указанный период, в пределах которой будет осуществляться автоматическое пополнение баланса. Для отключения ограничений очистите поле.'"/>
                    </xsl:call-template>
                </xsl:if>
            </div>
        </xsl:if>
        <xsl:if test="$supportedTypes/entity[@key='BY_INVOICE']">
            <div id="BY_INVOICE">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Максимальный размер платежа:</xsl:with-param>
                    <xsl:with-param name="description">Автоматическая оплата счета не произойдет, если сумма выставленного счета превышает указанную сумму.<cut/>Например, если в поле «Максимальная сума платежа» установлено значение «100 руб.», а сумма счета к оплате будет 103 руб., оплата не произойдет.</xsl:with-param>
                    <xsl:with-param name="required">true</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="amount">
                            <xsl:choose>
                                <xsl:when test="string-length(autoPaymentFloorLimit)>0"><xsl:value-of select="autoPaymentFloorLimit"/></xsl:when>
                                <xsl:otherwise><xsl:value-of select="sellAmount"/></xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <input type="text" id="BY_INVOICE_FLOOR_LIMIT" onkeyup="changeType(false)" size="10" value="{$amount}" class="moneyField"/>
                        &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>
    </div>
    <!-- ///////////////////////////////////////////////////////////// -->
    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'reportRow'"/>
        <xsl:with-param name="rowName">Сводка:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
           <b> <div id="report"></div></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'firstPaymentDateRow'"/>
        <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
          <b><div id="firstPaymentDate"></div></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="lineId" select="'autoPaymentNameRow'"/>
        <xsl:with-param name="rowName">Название:</xsl:with-param>
        <xsl:with-param name="required">true</xsl:with-param>
        <xsl:with-param name="description">Укажите название, которое будет отображаться в списке Ваших Автоплатежей и в SMS-оповещениях по услуге. Например, <i>мой мобильный</i>.</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="string-length(autoPaymentName)>0">
                    <input type="text" name="autoPaymentName" size="20" value="{autoPaymentName}"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="20 >= string-length(requisite)">
                            <input type="text" name="autoPaymentName" size="20" value="{requisite}"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <input type="text" name="autoPaymentName" size="20" value=""/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>
    <script type="text/javascript">

        Date.format = 'dd.mm.yyyy';         //устанавливаем для календаря формат даты

        var eventTypes = new Object();
        <xsl:for-each select="$autoPaymentTypes/entity">
            <xsl:for-each select="./field">
                eventTypes.<xsl:value-of select="./@name"/> = "<xsl:value-of select="./text()"/>";
            </xsl:for-each>
        </xsl:for-each>
        var eventHints = new Object();
        <xsl:for-each select="$supportedTypes/entity">
            eventHints.<xsl:value-of select="./@key"/> = "<xsl:value-of select="./field[@name='hint']/text()"/>";
        </xsl:for-each>

        var WRONG_DAY_ERROR = "Вы неправильно ввели число месяца оплаты платежа.";

        <!-- изменнение типа автоплатежа -->
        function changeType(flag)
        {
            var event = ensureElement("listType").value;
            $("#eventType>div.paymentValue>div.description").text(eventHints[event]);
            if(flag)
            {
                $("#changeContent>div").each(function(index) {
                    var self = this;
                    hideOrShow(self, self.id.indexOf(event) == -1);
                    <!-- блокируем/разблокируем поля в скрытых формах -->
                    $("input, textarea, select", self).each(function(){
                        this.disabled = self.id.indexOf(event) == -1;
                    });
                    <!-- определяемся с общим полем суммы -->
                    $("#amountRow").each(function(){
                        var isHideAmount = event =='BY_INVOICE' || event=='REDUSE_OF_BALANCE';

                        this.disabled = isHideAmount;
                        hideOrShow(this, isHideAmount);
                    });
                    <!-- определяемся со сводкой и ближайшим платежом -->
                    $("#reportRow, #firstPaymentDateRow").each(function(){
                        hideOrShow(this, event=='REDUSE_OF_BALANCE');
                    });
                    <!-- определяемся с признаком обязательности поля с названием -->
                    $("#autoPaymentNameRow span.asterisk").each(function(){
                        hideOrShow(this, event=='REDUSE_OF_BALANCE');
                    });
                });
            }

            var autoPaymentStartDate = getElement("autoPaymentStartDate");
            var firstPaymentDate = getElement("firstPaymentDate");

            if (isPeriodic(event))
            {
                var day = autoPaymentStartDate.value.substr(0, 2);
                var date = autoPaymentStartDate.value;

                firstPaymentDate.value = date;
                $("#firstPaymentDate").text(date);
                $("#report").text(eventTypes[event] + " " + parseInt(day) + "-го числа");
            }
            else if(event == 'REDUSE_OF_BALANCE')
            {
                var floorLimit = ensureElement("REDUSE_OF_BALANCE_FLOOR_LIMIT").value;
                getElement("autoPaymentFloorLimit").value = floorLimit;
                clearReportArea();
            }
            else if(event == 'BY_INVOICE')
            {
                $("#report").text("При выставлении счета на сумму, указанную в счете");
                $("#firstPaymentDate").text(eventTypes[event]);
                var floorLimit = ensureElement("BY_INVOICE_FLOOR_LIMIT").value;
                getElement("autoPaymentFloorLimit").value = floorLimit;
            }
        }

        function clearReportArea()
        {
            getElement("firstPaymentDate").value = "";
            $("#firstPaymentDate").text("");
            $("#report").text("");
        }

        changeType(true);

    </script>
</xsl:template>

<xsl:template mode="view" match="/form-data">

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Получатель:</xsl:with-param>
        <xsl:with-param name="rowValue">
           <b><xsl:value-of select="receiverName"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
        <xsl:with-param name="rowValue"><b>
            <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
            &nbsp; [ <xsl:value-of select="fromResourceName"/>]
            &nbsp;<xsl:value-of select="fromResourceRest"/>
            &nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
      </b> </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">
             <xsl:value-of select="requisiteName"/>:
        </xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="requisite" value="{requisite}"/>
            <b><xsl:value-of select="requisite"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="autoPaymentType != 'BY_INVOICE' and autoPaymentType != 'REDUSE_OF_BALANCE'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span class="summ">
                   <xsl:value-of select="sellAmount"/>
                   &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
                </span>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Настройки автоплатежа</b></xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="autoPaymentType" select="autoPaymentType"/>
    <xsl:variable name="autoPaymentTypeText" select="$autoPaymentTypes/entity/field[@name=$autoPaymentType]"/>

    <xsl:variable name="autoPaymentStartDate">
       <xsl:choose>
           <xsl:when test="contains(autoPaymentStartDate, '-')">
               <xsl:copy-of select="concat(substring(autoPaymentStartDate, 9, 2), '.', substring(autoPaymentStartDate, 6, 2), '.', substring(autoPaymentStartDate, 1, 4))"/>
           </xsl:when>
           <xsl:otherwise>
               <xsl:value-of select="autoPaymentStartDate"/>
           </xsl:otherwise>
       </xsl:choose>
    </xsl:variable>

    <xsl:call-template name="standartRow">
       <xsl:with-param name="rowName">Оплачивать:</xsl:with-param>
       <xsl:with-param name="rowValue">
           <b> <xsl:value-of select="$autoPaymentTypeText"/></b>
       </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="firstPaymentDate">
       <xsl:choose>
           <xsl:when test="contains(firstPaymentDate, '-')">
               <xsl:copy-of select="concat(substring(firstPaymentDate, 9, 2), '.', substring(firstPaymentDate, 6, 2), '.', substring(firstPaymentDate, 1, 4))"/>
           </xsl:when>
           <xsl:otherwise>
               <xsl:value-of select="firstPaymentDate"/>
           </xsl:otherwise>
       </xsl:choose>
    </xsl:variable>

    <xsl:variable name="isPeriodic" select="$autoPaymentType = 'ONCE_IN_MONTH' or $autoPaymentType = 'ONCE_IN_QUARTER' or $autoPaymentType = 'ONCE_IN_YEAR'"/>
    <xsl:variable name="month"      select="substring($firstPaymentDate, 4, 2)"/>

    <xsl:if test="$isPeriodic">
       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Дата начала действия:</xsl:with-param>
           <xsl:with-param name="rowValue">
               <b>
                   <xsl:value-of select="$autoPaymentStartDate"/>
               </b>
           </xsl:with-param>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Сводка:</xsl:with-param>
           <xsl:with-param name="rowValue">
                <b><xsl:value-of select="concat($autoPaymentTypeText,' ',substring($autoPaymentStartDate,1,2),' ','-го числа')"/></b>
           </xsl:with-param>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
           <xsl:with-param name="rowValue">
               <b>
                   <xsl:choose>
                       <xsl:when test="isStartDateChanged = 'true'">
                           <span class="text-red">
                               <xsl:value-of select="$firstPaymentDate"/>
                           </span>
                       </xsl:when>
                       <xsl:when test="isStartDateChanged != 'true'">
                           <xsl:value-of select="$firstPaymentDate"/>
                       </xsl:when>
                   </xsl:choose>
               </b>
           </xsl:with-param>
       </xsl:call-template>
    </xsl:if>

    <xsl:if test="$autoPaymentType = 'REDUSE_OF_BALANCE'">
       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Минимальный баланс:</xsl:with-param>
           <xsl:with-param name="required">false</xsl:with-param>
           <xsl:with-param name="rowValue">
              <b><xsl:value-of select="autoPaymentFloorLimit"/></b>
              &nbsp;<b><xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/></b>
           </xsl:with-param>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Сумма:</xsl:with-param>
           <xsl:with-param name="required">false</xsl:with-param>
           <xsl:with-param name="rowValue">
               <span class="summ">
                  <xsl:value-of select="sellAmount"/>
                  &nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
               </span>
           </xsl:with-param>
       </xsl:call-template>

       <xsl:if test="isAutoPaymentTotalAmountLimit = 'true'">
           <xsl:call-template name="standartRow">
               <xsl:with-param name="rowName">
                   Максимальная сумма в
                   <xsl:call-template name="periodTotalAmount2text">
                       <xsl:with-param name="code" select="autoPaymentTotalAmountPeriod"/>
                   </xsl:call-template>:
               </xsl:with-param>
               <xsl:with-param name="required">false</xsl:with-param>
               <xsl:with-param name="rowValue">
                   <b>
                       <xsl:choose>
                           <xsl:when test="string-length(autoPaymentTotalAmountLimit) > 0">
                               <xsl:value-of select="autoPaymentTotalAmountLimit"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(autoPaymentTotalAmountCurrency)"/>
                           </xsl:when>
                           <xsl:otherwise>
                               Не задана
                           </xsl:otherwise>
                       </xsl:choose>
                   </b>
               </xsl:with-param>
           </xsl:call-template>
       </xsl:if>
    </xsl:if>
    <xsl:if test="$autoPaymentType = 'BY_INVOICE'">
       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Максимальный размер платежа:</xsl:with-param>

           <xsl:with-param name="required">false</xsl:with-param>
           <xsl:with-param name="rowValue">
              <b><xsl:value-of select="autoPaymentFloorLimit"/></b>
              &nbsp;<b><xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/></b>
           </xsl:with-param>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Сводка:</xsl:with-param>
           <xsl:with-param name="rowValue">
               <b>При выставлении счета на сумму, указанную в счете</b>
           </xsl:with-param>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
           <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
           <xsl:with-param name="rowValue">
              <b>При выставлении счета</b>
           </xsl:with-param>
       </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="standartRow">
       <xsl:with-param name="rowName">Название:</xsl:with-param>
       <xsl:with-param name="rowValue">
           <b><xsl:value-of select="autoPaymentName"/></b>
       </xsl:with-param>
    </xsl:call-template>

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

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
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
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        </xsl:choose>
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
            <xsl:copy-of select="."/>
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

<!--добавление option для select'a-->
<xsl:template name="addOption">
    <xsl:param name="value"/>
    <xsl:param name="selected"/>
    <xsl:param name="source"/>

    <option>
        <xsl:attribute name="value">
            <xsl:value-of select="$value"/>
        </xsl:attribute>
        <xsl:if test="contains($value, $selected)">
            <xsl:attribute name="selected"/>
        </xsl:if>
        <xsl:call-template name="monthsToString">
            <xsl:with-param name="value"  select="$value"/>
            <xsl:with-param name="source" select="$source"/>
        </xsl:call-template>
    </option>
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


</xsl:stylesheet>
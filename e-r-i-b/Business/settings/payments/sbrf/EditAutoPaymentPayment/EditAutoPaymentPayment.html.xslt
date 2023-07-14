<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:spu="com.rssl.phizic.web.util.RecipientUtil"
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
    <xsl:variable name="cardNumber" select="cardNumber"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Получатель:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="recipient"    value="{recipient}"/>
            <input type="hidden" name="receiverName" value="{receiverName}"/>
            <xsl:value-of select="receiverName"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="card" select="document('cards.xml')/entity-list/entity[@key=$cardNumber]"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="fromResource" value="{$card/field[@name='code']}"/>
            <input type="hidden" name="cardNumber" value="{cardNumber}"/>
            <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>&nbsp;
            <xsl:if test="$card != ''">
                [<xsl:value-of select="$card/field[@name='name']"/>]&nbsp;
                <xsl:value-of select="format-number($card/field[@name='amountDecimal'], '### ##0,00','sbrf')"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign($card/field[@name='currencyCode'])"/>
            </xsl:if>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName"><xsl:value-of select="requisiteName"/>:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="hidden" name="requisite" value="{requisite}"/>
            <input type="hidden" name="requisiteName" value="{requisiteName}"/>
            <xsl:value-of select="requisite"/>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="executionEventType != 'BY_INVOICE' and executionEventType != 'REDUSE_OF_BALANCE'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="required">true</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" size="30" maxlength="30" name="sellAmount" value="{sellAmount}" class="moneyField"/>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

     <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Настройка автоплатежа</b></xsl:with-param>
     </xsl:call-template>

    <input type="hidden" name="firstPaymentDate"  value="{firstPaymentDate}"/>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплачивать:</xsl:with-param>
        <xsl:with-param name="required">true</xsl:with-param>
        <xsl:with-param name="rowValue">
            <xsl:variable name="executionEventType" select="executionEventType"/>
            <xsl:variable name="executionEventTypes" select="$autoPaymentTypes/entity[./field[@name=$executionEventType]]/field"/>
            <xsl:choose>
                <xsl:when test="count($executionEventTypes) > 1">
                    <select id="listType" onchange="changeType(true)" name="executionEventType">
                       <xsl:for-each select="$autoPaymentTypes/entity[./field[@name=$executionEventType]]/field">
                           <option value="{./@name}">
                               <xsl:if test="./@name = $executionEventType"><xsl:attribute name="selected"/></xsl:if>
                               <xsl:value-of select="./text()"/>
                           </option>
                        </xsl:for-each>
                    </select>
                </xsl:when>
                <xsl:otherwise>
                   <input type="hidden" value="{$executionEventTypes/@name}" name="executionEventType" id="listType"/>
                   <xsl:value-of select="$executionEventTypes/text()"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="firstPaymentDate"  select="firstPaymentDate"/>
    <xsl:variable name="month"  select="substring($firstPaymentDate, 4, 2)"/>

    <!-- Содержимое которое меняется в зависимости от выбранного типа -->
    <div id="changeContent">

        <div id="ONCE_IN_MONTH;ONCE_IN_QUARTER;ONCE_IN_YEAR">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата начала действия:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input name="autoPaymentStartDate" id="autoPaymentStartDate" class="dot-date-pick" size="10" value="{autoPaymentStartDate}"
                           onchange="javascript:changeType(false);" onkeyup="javascript:changeType(false);"
                        />
                </xsl:with-param>
            </xsl:call-template>
        </div>

        <!-- При снижении баланса лицевого счета до указанной суммы -->
        <div id="REDUSE_OF_BALANCE;BY_INVOICE">
            <xsl:variable name="serviceProviderEntity" select="document(concat('serviceProvider.xml?id=',recipient))/entity-list/entity"/>
            <xsl:variable name="floorLimits" select="$serviceProviderEntity/field[@name = 'thresholdAutoPayLimit']"/>
            <xsl:variable name="isTotalMaxSumSupported" select="$serviceProviderEntity/field[@name = 'ThresholdAutoPayScheme#IsTotalMaxSumSupported']"/>
            <xsl:variable name="totalMaxSumPeriod" select="$serviceProviderEntity/field[@name = 'ThresholdAutoPayScheme#TotalMaxSumPeriod']"/>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">
                    <xsl:choose>
                        <xsl:when test="executionEventType='BY_INVOICE'">Максимальный размер платежа:</xsl:when>
                        <xsl:when test="executionEventType='REDUSE_OF_BALANCE'">Минимальный баланс:</xsl:when>
                    </xsl:choose>
                </xsl:with-param>
                <xsl:with-param name="description">
                    <xsl:choose>
                        <xsl:when test="executionEventType='BY_INVOICE'">Автоматическая оплата счета не произойдет, если сумма выставленного счета превышает указанную сумму.<cut/>Например, если в поле «Максимальная сума платежа» установлено значение «100 руб.», а сумма счета к оплате будет 103 руб., оплата не произойдет. </xsl:when>
                    </xsl:choose>
                </xsl:with-param>
                <xsl:with-param name="required">true</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="floorLimit" select="autoPaymentFloorLimit"/>
                    <xsl:choose>
                        <xsl:when test="executionEventType='REDUSE_OF_BALANCE' and count($floorLimits)>0">
                            <select id="floorLimit" name="autoPaymentFloorLimit" onchange="changeType(false)">
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
                        <xsl:otherwise >
                            <input type="text" id="floorLimit" onkeyup="changeType(false)" size="10" name="autoPaymentFloorLimit" value="{$floorLimit}" class="moneyField"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    &nbsp;<xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="executionEventType = 'REDUSE_OF_BALANCE'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Сумма:</xsl:with-param>
                    <xsl:with-param name="required">true</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <input type="text" size="30" maxlength="30" name="sellAmount" value="{sellAmount}" class="moneyField"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="executionEventType = 'REDUSE_OF_BALANCE' and $isTotalMaxSumSupported = 'true'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">
                        Максимальная сумма в
                        <xsl:call-template name="periodTotalAmount2text">
                            <xsl:with-param name="code" select="$totalMaxSumPeriod"/>
                        </xsl:call-template>:
                    </xsl:with-param>
                    <xsl:with-param name="required">false</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="valueTotalAmount">
                            <xsl:if test="$totalMaxSumPeriod = autoPaymentTotalAmountPeriod">
                                <xsl:value-of select="autoPaymentTotalAmountLimit"/>
                            </xsl:if>
                        </xsl:variable>
                        <input type="text" name="autoPaymentTotalAmountLimit" value="{$valueTotalAmount}"/>&nbsp;<xsl:value-of select="mu:getCurrencySign('RUB')"/>
                        <!-- чтоб не пропадала валюта -->
                        <input type="hidden" name="autoPaymentTotalAmountCurrency" value="{autoPaymentTotalAmountCurrency}"/>
                        <!-- и значение максимальной суммы изза условия выше -->
                        <input type="hidden" name="autoPaymentTotalAmountPeriod" value="{autoPaymentTotalAmountPeriod}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </div>
    </div>
    <!-- ///////////////////////////////////////////////////////////// -->
    <xsl:if test="executionEventType != 'REDUSE_OF_BALANCE'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сводка:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
               <b><div id="report"></div></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
              <b><div id="firstPaymentDate"></div></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Название:</xsl:with-param>
        <xsl:with-param name="required" select="string(executionEventType != 'REDUSE_OF_BALANCE')"/>
        <xsl:with-param name="description">Укажите название автоплатежа, по которому Вы сможете отличить данный автоплатеж в списке.</xsl:with-param>
        <xsl:with-param name="rowValue">
            <input type="text" name="autoPaymentName" size="20" value="{autoPaymentName}"/>
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

        var WRONG_DAY_ERROR = "Вы неправильно ввели число месяца оплаты платежа.";

        setCurrentDateToInput("autoPaymentStartDate");
        <!-- изменнение типа автоплатежа -->
        function changeType(flag)
        {
            var event = ensureElement("listType").value;

            if(flag)
            {
                $("#changeContent>div").each(function(index) {
                    hideOrShow(this, this.id.indexOf(event) == -1);
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
            else if(event == 'BY_INVOICE')
            {
                $("#report").text("При выставлении счета на сумму, указанную в счете");
                $("#firstPaymentDate").text(eventTypes[event]);
            }
            function clearReportArea()
            {
                getElement("firstPaymentDate").value = "";
                $("#firstPaymentDate").text("");
                $("#report").text("");
            }
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
        <xsl:with-param name="rowName"><xsl:value-of select="requisiteName"/>:</xsl:with-param>
        <xsl:with-param name="required">false</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="requisite"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b>
                <xsl:value-of select="mask:getCutCardNumber(cardNumber)"/>&nbsp;
                [<xsl:value-of select="cardName"/>]&nbsp;
                <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
            </b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:if test="executionEventType != 'BY_INVOICE' and executionEventType != 'REDUSE_OF_BALANCE'">
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

    <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Параметры автоплатежа (регулярной операции)</b></xsl:with-param>
    </xsl:call-template>

    <xsl:variable name="executionEventType" select="executionEventType"/>
    <xsl:call-template name="standartRow">
        <xsl:with-param name="rowName">Повторяется:</xsl:with-param>
        <xsl:with-param name="rowValue">
            <b><xsl:value-of select="$autoPaymentTypes/entity/field[@name=$executionEventType]"/></b>
        </xsl:with-param>
    </xsl:call-template>

    <xsl:choose>
        <xsl:when test="executionEventType='BY_INVOICE' ">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Максимальный размер платежа:</xsl:with-param>
                <xsl:with-param name="required">false</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:value-of select="format-number(autoPaymentFloorLimit,'### ##0,00','sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:when>
        <xsl:when test="executionEventType='REDUSE_OF_BALANCE'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Минимальный баланс:</xsl:with-param>
                <xsl:with-param name="required">false</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:value-of select="format-number(autoPaymentFloorLimit,'### ##0,00','sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(autoPaymentFloorCurrency)"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>

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

            <xsl:if test="isAutoPaymentTotalAmountLimit = 'true'">
                <xsl:call-template name="standartRow">
                   <xsl:with-param name="rowName">
                       Максимальная сумма платежей в
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
        </xsl:when>
        <xsl:otherwise>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата начала действия:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <xsl:value-of select="autoPaymentStartDate"/>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:otherwise>
    </xsl:choose>

    <xsl:if test="executionEventType != 'REDUSE_OF_BALANCE'">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сводка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="executionEventType='BY_INVOICE'">При выставлении счета на сумму, указанную в счете</xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$autoPaymentTypes/entity/field[@name=$executionEventType]"/>&nbsp;
                            <xsl:value-of select="substring(autoPaymentStartDate,1,2)"/>&nbsp;-го&nbsp;числа.
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Ближайший платеж:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="executionEventType='BY_INVOICE'">При выставлении счета</xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="isStartDateChanged = 'true'">
                                    <span class="text-red">
                                        <xsl:value-of select="firstPaymentDate"/>
                                    </span>
                                </xsl:when>
                                <xsl:when test="isStartDateChanged != 'true'"><xsl:value-of select="firstPaymentDate"/></xsl:when>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
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
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
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
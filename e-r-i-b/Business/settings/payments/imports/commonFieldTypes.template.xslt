<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<!-- шаблоны полей для платежей основного приложения -->
<!-- подключается тэгом <xsl:import href="commonFieldTypes.template.xslt"/> -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:dh="java://com.rssl.phizic.business.documents.DocumentHelper"
                xmlns:xalan = "http://xml.apache.org/xalan">
<xsl:param name="mode" select="'view'"/>

<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

<!-- шаблон для отображения поля комиссии с детализацией микрооперациями -->
<xsl:template name="writeDownOperations">
    <xsl:param name="operations"/>                  <!-- микрооперации списания -->
    <xsl:param name="commissionCurrency"/>          <!-- валюта счета списания, она же валюта комиссии -->
    <xsl:param name="useIncludeWord"/>              <!-- использовать в названиях полей детализации(Комиссия и перерасчет, Включая комиссию и перерасчет) слово "Включая" -->
    <xsl:param name="documentState"/>               <!-- статус документа -->
    <xsl:param name="prepareMode"/>                 <!-- в случае если комисия в документе рассчитывается отдельным шагом(prepare) -->

    <xsl:variable name="operationsCount" select="count(document('writeDownOperations.xml')/Operations/Operation)"/>    <!-- общее число ВСЕХ микроопераций списания, включая не отображаемые -->

    <xsl:if test="count($operations)>0">
        <xsl:variable name="totalSum" select="sum($operations[./TurnOver = 'RECEIPT']/CurAmount) - sum($operations[./TurnOver = 'CHARGE']/CurAmount)"/>
        <xsl:variable name="commissionRowName">
            <xsl:choose>
                <xsl:when test="count($operations[./TurnOver = 'CHARGE']) = count($operations)">
                    <xsl:choose>
                        <xsl:when test="$useIncludeWord = 'true'">
                            <xsl:value-of select="'Включая комиссию'"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="'Комиссия'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:when test="count($operations)=1">
                    <xsl:for-each select="$operations">
                        <xsl:choose>
                            <xsl:when test="$useIncludeWord = 'true'">
                                Включая <xsl:call-template name="lowercase"><xsl:with-param name="input" select="./Name"/></xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="./Name"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="count($operations[./TurnOver = 'RECEIPT']) = count($operations)">
                            <xsl:choose>
                                <xsl:when test="$useIncludeWord = 'true'">
                                    <xsl:value-of select="'Включая перерасчет'"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="'Перерасчет'"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="$useIncludeWord = 'true'">
                                    <xsl:value-of select="'Включая комиссию и перерасчет'"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="'Комиссия и перерасчет'"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">
                <xsl:choose>
                    <xsl:when test="count($operations)>1">
                        <span class="black_link relative" onclick="hideOrShow('WriteDownDiv')">
                            <xsl:value-of select="$commissionRowName"/>:
                        </span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span style="position:relative;">
                            <xsl:value-of select="$commissionRowName"/>:
                        </span>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="rowValue"><b>
                <div style="text-align:right;word-wrap:break-word; position:relative;">
                <xsl:variable name="style">
                    <xsl:choose>
                        <xsl:when test="$totalSum>0">
                            color:green;
                        </xsl:when>
                        <xsl:otherwise>
                            color:red;
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:variable name="commissionAmountRowValue">
                    <xsl:if test="$totalSum>0"><xsl:value-of select="'+'"/></xsl:if>
                    <xsl:value-of select="format-number($totalSum, '### ##0,00', 'sbrf')"/>
                </xsl:variable>
                <span style="{$style}">
                    <xsl:choose>
                        <xsl:when test="count($operations)>1">
                            <span class="black_link">
                                <xsl:attribute name="onclick">hideOrShow('WriteDownDiv')</xsl:attribute>
                                <span class="black_link" style="float:left; padding-right:3px;">
                                    <xsl:value-of select="$commissionAmountRowValue"/>
                                </span>
                                <div style="width:35px; float:left; text-align:left">
                                    <span class="black_link" style="width:auto; float:left; text-align:left">
                                        <xsl:value-of select=" mu:getCurrencySign($commissionCurrency)"/>
                                    </span>
                                </div>
                            </span>
                        </xsl:when>
                        <xsl:otherwise>
                            <span style="float:left; padding-right:3px;">
                                <xsl:value-of select="$commissionAmountRowValue"/>
                            </span>
                            <span style="width:35px; float:left; text-align:left">
                                <xsl:value-of select=" mu:getCurrencySign($commissionCurrency)"/>
                            </span>
                        </xsl:otherwise>
                    </xsl:choose>
                </span>
                </div></b>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:if>

    <xsl:if test="count($operations)>1">
        <div id="WriteDownDiv" style="display:none;">
            <xsl:for-each select="$operations">
                <xsl:variable name="name" select="./Name"/>
                <xsl:variable name="curAmnt" select="./CurAmount"/>
                <xsl:variable name="turnOver" select="./TurnOver"/>
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">
                        <span style="position:relative;">
                            <xsl:value-of select="$name"/>
                        </span>
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div style="text-align:right;word-wrap:break-word;position:relative;">
                        <xsl:choose>
                            <xsl:when test="$turnOver = 'RECEIPT'">
                                <div style="color:green;">
                                <div style="float:left; padding-right:3px;">
                                    <xsl:value-of select="'+'"/>
                                    <xsl:value-of  select="format-number($curAmnt, '### ##0,00', 'sbrf')"/>
                                </div>
                                <div style="width:35px; float:left; text-align:left">
                                     <xsl:value-of select=" mu:getCurrencySign($commissionCurrency)"/>
                                </div>
                                </div>
                            </xsl:when>
                            <xsl:otherwise>
                                <div style="color:red;">
                                <div style="float:left; padding-right:3px;">
                                    <xsl:value-of select="'-'"/>
                                    <xsl:value-of  select="format-number($curAmnt, '### ##0,00', 'sbrf')"/>
                                </div>
                                <div style="width:35px; float:left; text-align:left">
                                    <xsl:value-of select=" mu:getCurrencySign($commissionCurrency)"/>
                                </div>
                                </div>
                            </xsl:otherwise>
                        </xsl:choose>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </div>
    </xsl:if>

    <script type="text/javascript">
        <xsl:if test="count($operations)>1">
            var writeDownDiv =  document.getElementById('WriteDownDiv');
            if (typeof(writeDownDiv) != 'undefined' &amp;&amp; writeDownDiv != null)
            {
                writeDownDiv.style.display = 'none';
            }
        </xsl:if>
    </script>

    <xsl:if test="count($operations) = 0 and (($documentState != 'SAVED' and $documentState != 'WAIT_CONFIRM') or $operationsCount!='0' or $prepareMode = 'true')">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName"><span style="position:relative;">Комиссия:</span></xsl:with-param>
            <xsl:with-param name="rowValue">
                <div style="text-align:right;word-wrap:break-word; position:relative; white-space:normal;">
                    <div style="float: left; padding: 3px 3px 4px 7px;">
                        <xsl:value-of select="format-number(0, '### ##0,00', 'sbrf')"/>
                    </div>
                    <div style="width:35px; float:left; text-align:left; padding: 3px 0px 4px 0px;">
                        <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </div>
                </div>
            </xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
        </xsl:call-template>
    </xsl:if>
</xsl:template>

<!-- шаблон для отображения поля суммы операции для операций переводов средств-->
<xsl:template name="transferSumRows">
    <xsl:param name="chargeOffAmount"/>             <!-- сумма перевода в валюте списания -->
    <xsl:param name="destinationAmount"/>           <!-- сумма перевода в валюте зачисления -->
    <xsl:param name="fromResourceCurrency"/>        <!-- валюта счета списания, она же валюта комиссии -->
    <xsl:param name="toResourceCurrency"/>          <!-- валюта счета зачисления-->
    <xsl:param name="documentState"/>               <!-- статус документа -->
    <xsl:param name="exactAmount"/>                 <!-- какая сумма - сумма опарации -->
    <xsl:param name="needUseTotalRow"/>             <!-- признак использования поля "[БУДЕТ] ПЕРЕВЕДЕНО" -->
    <xsl:param name="tariffPlanESB"/>

    <xsl:variable name="fullOperations" select="document('writeDownOperations.xml')/Operations/Operation"/>  <!-- все микрооперации списания (включая неотображаемые)-->
    <xsl:variable name="operations" select="document('writeDownOperations.xml')/Operations/Operation[./Name != 'Частичная выдача' and ./Name != 'Закрытие счета']"/>  <!-- отображаемые микрооперации списания -->
    <xsl:variable name="operationsCount" select="count($fullOperations)"/>    <!-- общее число ВСЕХ микроопераций списания, включая не отображаемые -->
    <xsl:variable name="totalCommissionSum" select="sum($operations[./TurnOver = 'RECEIPT']/CurAmount) - sum($operations[./TurnOver = 'CHARGE']/CurAmount)"/> <!-- ОБЩАЯ СУММА КОМИССИИ -->
    <xsl:variable name="isConversion" select="($toResourceCurrency!=$fromResourceCurrency)"/>
    <xsl:variable name="operationAmount">
        <xsl:choose>
            <xsl:when test="$exactAmount='destination-field-exact'"><xsl:value-of select="$destinationAmount"/></xsl:when>
            <xsl:otherwise><xsl:value-of select="$chargeOffAmount"/></xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <xsl:variable name="operationCurrency">
        <xsl:choose>
            <xsl:when test="$exactAmount='destination-field-exact'"><xsl:value-of select="$toResourceCurrency"/></xsl:when>
            <xsl:otherwise><xsl:value-of select="$fromResourceCurrency"/></xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <div class="commission-block">
        <table><tr><td>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName"><div style="font-size:16px; font-weight:bold; position:relative;">Сумма перевода:</div></xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="summblock">
                        <div class="currentSumm">
                            <xsl:value-of select="format-number(translate($operationAmount, ',','.'), '### ##0,00', 'sbrf')"/>
                         </div>
                        <div class="currencyBlock">
                            <span>
                                <xsl:value-of select="mu:getCurrencySign($operationCurrency)"/>
                            </span>
                        </div>
                    </div>
                </xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowStyle" select="'margin-top: 36px;'"/>
            </xsl:call-template>

            <xsl:variable name="writeDownAmount">
                <xsl:choose>
                    <xsl:when test="not($isConversion)">
                        <xsl:value-of select="$operationAmount"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$chargeOffAmount"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:variable>

            <xsl:if test="($documentState!='SAVED' and $documentState!='WAIT_CONFIRM') or $operationsCount!='0'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="lineId">totalSellAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName">
                        <div style="font-size:16px; font-weight:bold; position:relative;">С моего счета<xsl:if test="$documentState!='EXECUTED'"> будет</xsl:if> списано:</div>
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div class="summblock">
                            <div class="currentSumm">
                                <xsl:value-of select="format-number($writeDownAmount - $totalCommissionSum, '### ##0,00', 'sbrf')"/>
                            </div>
                            <div class="currencyBlock">
                                <span>
                                    <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
                                </span>
                            </div>
                        </div>
                    </xsl:with-param>
                    <xsl:with-param name="rowStyle" select="'margin-bottom: 18px;'"/>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="writeDownOperations">
                <xsl:with-param name="commissionCurrency" select="$fromResourceCurrency"/>
                <xsl:with-param name="useIncludeWord" select="'true'"/>
                <xsl:with-param name="operations" select="$operations"/>
                <xsl:with-param name="documentState" select="$documentState"/>
                <xsl:with-param name="prepareMode" select="'false'"/>
            </xsl:call-template>

            <xsl:if test="(($documentState!='SAVED' and $documentState!='WAIT_CONFIRM') or $operationsCount!='0') and $isConversion and $needUseTotalRow='true'">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">
                        <div style="font-size:16px; font-weight:bold;position:relative;"><xsl:choose><xsl:when test="$documentState!='EXECUTED'">Будет п</xsl:when><xsl:otherwise>П</xsl:otherwise></xsl:choose>ереведено:</div>
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div class="summblock">
                            <div class="currentSumm">
                                <xsl:value-of select="format-number(translate($destinationAmount, ',','.'), '### ##0,00', 'sbrf')"/>
                            </div>
                            <div class="currencyBlock">
                                <span>
                                    <xsl:value-of select="mu:getCurrencySign($toResourceCurrency)"/>
                                </span>
                            </div>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </td></tr></table>
    </div>

    <xsl:if test="count($operations) = 0 and not(($documentState!='SAVED' and $documentState!='WAIT_CONFIRM') or $operationsCount!='0')">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName"><span style="position:relative;">Комиссия:</span></xsl:with-param>
            <xsl:with-param name="rowValue">
                <div class="linear allWidth">
                    <i><xsl:value-of select="dh:getSettingMessage('commission.prepare.transfer.message')"/></i>
                </div>
            </xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
        </xsl:call-template>
    </xsl:if>

    <script type="text/javascript">
        <xsl:if test="$operationsCount!='0'">
            <xsl:variable name="commissionValue">
                <xsl:if test="$totalCommissionSum>0"><xsl:value-of select="'+'"/></xsl:if>
                        <xsl:value-of select="format-number($totalCommissionSum, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
            </xsl:variable>
            <xsl:variable name="message">
                  <xsl:value-of select="dh:getCommissionMessage($commissionValue, $tariffPlanESB)"/>
            </xsl:variable>
            addMessage("<xsl:value-of select="$message"/>");
        </xsl:if>
    </script>

</xsl:template>

<!-- шаблон для отображения поля суммы операции для операций закрытия/открытия счетов-->
<xsl:template name="accountsSumRows">
    <xsl:param name="fromResourceCurrency"/>        <!-- валюта счета списания, она же валюта комиссии -->
    <xsl:param name="dstCurAmt"/>                   <!-- сумма зачисления -->
    <xsl:param name="toResourceCurrency"/>          <!-- валюта счета зачислений -->
    <xsl:param name="documentState"/>               <!-- статус документа -->

    <xsl:variable name="fullOperations" select="document('writeDownOperations.xml')/Operations/Operation"/>  <!-- все микрооперации списания (включая неотображаемые)-->
    <xsl:variable name="operations" select="document('writeDownOperations.xml')/Operations/Operation[./Name != 'Частичная выдача' and ./Name != 'Закрытие счета']"/>  <!-- отображаемые микрооперации списания -->
    <xsl:variable name="operationsCount" select="count($fullOperations)"/>    <!-- общее число ВСЕХ микроопераций списания, включая не отображаемые -->
    <xsl:variable name="totalCommissionSum" select="sum($operations[./TurnOver = 'RECEIPT']/CurAmount) - sum($operations[./TurnOver = 'CHARGE']/CurAmount)"/> <!-- ОБЩАЯ СУММА КОМИССИИ -->
    <!-- На форме для отображения поля "сумма на вкладе" используем значение равное сумме всех микроопераций с turnover='CHARGE' минус сумма всех микроопераций с turover='RECEIPT'.-->
    <xsl:variable name="srcCurAmt" select="sum($fullOperations[./TurnOver = 'CHARGE']/CurAmount) - sum($fullOperations[./TurnOver = 'RECEIPT']/CurAmount)"/>

    <div class="commission-block">
        <table><tr><td>
            <xsl:if test="not($srcCurAmt='' or $srcCurAmt=0.00 or $srcCurAmt=0)">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="lineId">chargeOffAmountRow</xsl:with-param>
                    <xsl:with-param name="rowName"><div style="font-size:16px; font-weight:bold; position:relative;">Сумма на вкладе:</div></xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div style="text-align:right;word-wrap:break-word; position:relative; white-space:normal; font-weight: bold;font-size:16px;">
                            <div style="float: left; padding: 7px 3px 4px 7px;">
                                <input type="hidden" name="chargeOffAmount" value="{$srcCurAmt}"/>
                                <xsl:value-of select="format-number($srcCurAmt, '### ##0,00', 'sbrf')"/>
                            </div>
                            <div style="width:35px; float:left; text-align:left; padding: 7px 0px 4px 0px;">
                                <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
                            </div>
                        </div>
                    </xsl:with-param>
                    <xsl:with-param name="rowStyle" select="'margin-top: 36px;'"/>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="writeDownOperations">
                <xsl:with-param name="commissionCurrency" select="$fromResourceCurrency"/>
                <xsl:with-param name="useIncludeWord" select="'fasle'"/>
                <xsl:with-param name="operations" select="$operations"/>
                <xsl:with-param name="documentState" select="$documentState"/>
                <xsl:with-param name="prepareMode" select="'true'"/>
            </xsl:call-template>

            <xsl:if test="not($dstCurAmt = '' or $dstCurAmt = 0.00)">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">
                        <div style="font-size:16px; font-weight:bold; position:relative;">
                            <xsl:choose>
                                <xsl:when test="$documentState!='EXECUTED'">
                                    Будет переведено:
                                </xsl:when>
                                <xsl:otherwise>
                                    Переведено:
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div class="summblock">
                            <div class="currentSumm">
                                <xsl:value-of select="format-number($dstCurAmt, '### ##0,00', 'sbrf')"/>
                            </div>
                            <div class="currencyBlock">
                                <span>
                                    <xsl:value-of select="mu:getCurrencySign($toResourceCurrency)"/>
                                </span>
                            </div>
                        </div>
                    </xsl:with-param>
                    <xsl:with-param name="rowStyle" select="'margin-top: 18px;'"/>
                </xsl:call-template>
            </xsl:if>

        </td></tr></table>
    </div>

    <script type="text/javascript">
        <xsl:if test="$operationsCount!='0'">
            <xsl:variable name="message">
                <xsl:value-of select="dh:getSettingMessage('commission.info.message')"/>&nbsp;<xsl:if test="$totalCommissionSum>0"><xsl:value-of select="'+'"/></xsl:if>
                <xsl:value-of select="format-number($totalCommissionSum, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/>
            </xsl:variable>
            addMessage("<xsl:value-of select="$message"/>");
        </xsl:if>
    </script>

</xsl:template>

<!-- шаблон для отображения сумм комиссий в чеке -->
<xsl:template name="writeDownOperationsCheck">
    <xsl:param name="fromResourceCurrency"/>

    <xsl:variable name="operations" select="document('writeDownOperations.xml')/Operations/Operation[./Name != 'Частичная выдача' and ./Name != 'Закрытие счета']"/>  <!-- отображаемые микрооперации списания -->
    <xsl:variable name="totalCommissionSum" select="sum($operations[./TurnOver = 'RECEIPT']/CurAmount) - sum($operations[./TurnOver = 'CHARGE']/CurAmount)"/> <!-- ОБЩАЯ СУММА КОМИССИИ -->
    <div class="checkSize">КОМИССИЯ И ПЕРЕРАСЧЕТ:
        <xsl:if test="$totalCommissionSum > 0"><xsl:value-of select="'+'"/></xsl:if>
        <xsl:value-of select="format-number($totalCommissionSum, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$fromResourceCurrency"/>
    </div>

    <xsl:for-each select="$operations">
        <xsl:variable name="name" select="./Name"/>
        <xsl:variable name="curAmnt" select="./CurAmount"/>
        <xsl:variable name="turnOver" select="./TurnOver"/>
        <div class="checkSize"><xsl:value-of select="$name"/>:
        <xsl:choose>
            <xsl:when test="$turnOver = 'RECEIPT'">
                <xsl:value-of select="'+'"/>
                <xsl:value-of select="format-number($curAmnt, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$fromResourceCurrency"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'-'"/>
                <xsl:value-of select="format-number($curAmnt, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$fromResourceCurrency"/>
            </xsl:otherwise>
        </xsl:choose>
        </div>
    </xsl:for-each>

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
                <xsl:attribute name="id">
                    <xsl:copy-of select="$id"/>
                    TextLabel
                </xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
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
		<xsl:when test="name() = 'cut' and position() = 1 ">
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

<xsl:template name="uppercase">
    <xsl:param name="input"/>
    <xsl:value-of select="translate($input,
    'йцукенгшщзхъфывапролджэячсмитьбю',
    'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ')"/>
</xsl:template>

<xsl:template name="lowercase">
    <xsl:param name="input"/>
    <xsl:value-of select="translate($input,
    'ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ',
    'йцукенгшщзхъфывапролджэячсмитьбю')"/>
</xsl:template>

<xsl:template name="abs">
    <xsl:param name="input"/>

    <xsl:variable name="num" select="(1-2*(number($input) &lt; 0))*number($input)"/>
    <xsl:value-of select="format-number($num, '### ##0,00', 'sbrf')"/>
</xsl:template>

</xsl:stylesheet>

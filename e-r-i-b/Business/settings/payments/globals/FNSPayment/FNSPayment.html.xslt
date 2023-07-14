<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="isTemplate" select="'isTemplate'"/>

    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
    <xsl:variable name="styleSpecial" select="''"/>

    <xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit' or $mode = 'view'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>


	<xsl:template match="/form-data" mode="edit">
        
        <input type="hidden" name="receiverId" value="{receiverId}"/>
        <input type="hidden" name="recipient" value="{recipient}"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentDate" value="{documentDate}"/>
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <!--Плательщик-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Плательщик</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">payerLastName</xsl:with-param>
           <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="payerLastName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">payerFirstName</xsl:with-param>
           <xsl:with-param name="rowName">Имя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="payerFirstName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">payerMiddleName</xsl:with-param>
           <xsl:with-param name="rowName">Отчество:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="payerMiddleName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">payerInn</xsl:with-param>
            <xsl:with-param name="rowName">ИНН:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="payerInn"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <!--Получатель-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
            <xsl:with-param name="rowValue">
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">receiverNameOrder</xsl:with-param>
            <xsl:with-param name="rowName">Наименование:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverNameOrder"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">inn</xsl:with-param>
            <xsl:with-param name="rowName">ИНН:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="inn"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">kpp</xsl:with-param>
            <xsl:with-param name="rowName">КПП:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="kpp"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">receiverAccount</xsl:with-param>
            <xsl:with-param name="rowName">Расчетный счет:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverAccount"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <!--Банк-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Банк получателя</b></xsl:with-param>
            <xsl:with-param name="rowValue"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">receiverBankName</xsl:with-param>
            <xsl:with-param name="rowName">Наименование:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverBankName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">receiverBIC</xsl:with-param>
            <xsl:with-param name="rowName">БИК:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverBankCode"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">receiverCorAccount</xsl:with-param>
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Корр. счет:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="receiverCorAccount"/></b>
                <input type="hidden" name="receiverCorAccount" value="{receiverCorAccount}"/>
            </xsl:with-param>
        </xsl:call-template>

        <!--Реквизиты платежа-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Реквизиты платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">statusPayer</xsl:with-param>
            <xsl:with-param name="rowName">Статус:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="statusPayer"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">kbk</xsl:with-param>
            <xsl:with-param name="rowName">КБК:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="kbk"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">okato</xsl:with-param>
            <xsl:with-param name="rowName">ОКАТО:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="okato"/></b>
             </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxGround</xsl:with-param>
            <xsl:with-param name="rowName">Основание платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="taxGround"/></b>
                <input type="hidden" name="taxGround" value="{taxGround}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxPeriod</xsl:with-param>
            <xsl:with-param name="rowName">Налоговый период:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="taxPeriod"/></b>
                <input type="hidden" name="taxPeriod" value="{taxPeriod}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">RecIdentifier</xsl:with-param>
            <xsl:with-param name="rowName">Индекс документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="RecIdentifier"/></b>
                <input type="hidden" name="RecIdentifier" value="{RecIdentifier}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">taxType</xsl:with-param>
            <xsl:with-param name="rowName">Тип платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="taxType"/></b>
                <input type="hidden" name="taxType" value="{taxType}"/>
            </xsl:with-param>
        </xsl:call-template>


        <!--Оплата-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Оплата</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">payerAccountSelect</xsl:with-param>
            <xsl:with-param name="rowName">Перевести с:</xsl:with-param>
            <xsl:with-param name="required">false</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$mode = 'edit'">
                        <xsl:variable name="payerAccount" select="fromAccountSelect"/>
                        <xsl:if test="$personAvailable">
                            <select id="fromResource" name="fromResource">
                                <xsl:if test="$isTemplate = 'true'">
                                    <option value="">Не задан</option>
                                </xsl:if>

                                <xsl:variable name="activeCardsDictionaryName">
                                    <xsl:choose>
                                        <xsl:when test="spu:isCreditCardSupported(recipient)">active-not-virtual-cards.xml</xsl:when>
                                        <xsl:otherwise>active-not-virtual-not-credit-cards.xml</xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>

                                <xsl:variable name="activeCards" select="document($activeCardsDictionaryName)/entity-list/*"/>

                                <xsl:for-each select="$activeCards">
                                    <option>
                                        <xsl:variable name="id" select="concat('card:',field[@name='cardLinkId']/text())"/>
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="$id"/>
                                        </xsl:attribute>
                                        <xsl:if test="$payerAccount = ./@key">
                                            <xsl:attribute name="selected">true</xsl:attribute>
                                        </xsl:if>
                                        <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                                        [<xsl:value-of select="./field[@name='name']"/>]
                                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                                        <xsl:variable name="currencyCode"><xsl:value-of select="./field[@name='currencyCode']"/></xsl:variable>
                                        <xsl:value-of select="mu:getCurrencySign($currencyCode)"/>
                                    </option>
                                </xsl:for-each>
                            </select>
                        </xsl:if>
                        <xsl:if test="not($personAvailable)">
                            <select id="payerAccountSelect" name="payerAccountSelect" disabled="disabled">
                                <option value="" selected="selected">Счет клиента&nbsp;</option>
                            </select>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <b><xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:variable name="fromResourceCurrency"><xsl:value-of select="fromResourceCurrency"/></xsl:variable>
                        <xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">amount</xsl:with-param>
            <xsl:with-param name="rowName">Сумма оплаты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="amount"/>&nbsp;
                <input type="hidden" name="amount" value="{amount}"/>
                <xsl:variable name="currency"><xsl:value-of select="currency"/></xsl:variable>
                 <input type="hidden" name="currency" value="{currency}"/>
                <xsl:value-of select="mu:getCurrencySign($currency)"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Назначение платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="groundByKBK"/></b>
                <input type="hidden" name="groundByKBK" value="{groundByKBK}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$mode = 'view'">

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус платежа:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><div id="state">
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

            <xsl:if test="string-length(promoCode)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Промо-код:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div id="promoCode">
                            <xsl:value-of select="promoCode"/>
                            <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="javascript:openFAQ('/PhizIC/faq.do#r2');"></a>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

        </xsl:if>

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
        <xsl:param name="required" select="'false'"/>    <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->

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
            <xsl:when test="$code='WAIT_CONFIRM'">
                <xsl:choose>
                    <xsl:when test="$isTemplate = 'true'">Активный</xsl:when>
                    <xsl:otherwise>Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре")</xsl:otherwise>
                </xsl:choose>
            </xsl:when>
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
            <xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">Приостановлен<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">
                <xsl:choose>
                    <xsl:when test="$isTemplate = 'true'">Активный</xsl:when>
                    <xsl:otherwise>Подтвердите в контактном центре</xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$code='UNKNOW' and $webRoot='/PhizIA'">Приостановлен</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <xsl:param name="rowValue"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>&nbsp;<xsl:copy-of select="$rowValue"/>
        </div>
    </xsl:template>

</xsl:stylesheet>

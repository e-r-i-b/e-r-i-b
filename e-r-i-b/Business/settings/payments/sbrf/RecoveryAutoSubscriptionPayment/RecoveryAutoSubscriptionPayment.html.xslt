<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:pu="java://com.rssl.phizic.web.util.PersonInfoUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">

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
    <xsl:param name="longOffer" select="false()"/>
    <xsl:param name="isTemplate" select="'isTemplate'"/>

	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="styleClass" select="'form-row'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="view"/>
                <xsl:apply-templates mode="view-auto-sub-info"/>
            </xsl:when>

            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
                <xsl:apply-templates mode="view-auto-sub-info"/>
            </xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <input type="hidden" name="bankDetails" value="{bankDetails}"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Получатель</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverName"/></b>
                    <input name="receiverName" type="hidden" value="{receiverName}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:if test="string-length(nameService)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Услуга</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="nameService"/></b>
                    <input name="nameService" type="hidden" value="{nameService}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">ИНН</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverINN"/></b>
                    <input name="receiverINN" type="hidden" value="{receiverINN}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Счет</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverAccount"/></b>
                    <input name="receiverAccount" type="hidden" value="{receiverAccount}"/>
                </xsl:with-param>
            </xsl:call-template>

        <xsl:if test="$bankDetails = 'true'">
            <xsl:call-template name="titleRow">
                <xsl:with-param name="rowName">&nbsp;<b>Банк получателя</b></xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverBankName"/></b>
                    <input name="receiverBankName" type="hidden" value="{receiverBankName}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">БИК</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverBIC"/></b>
                    <input name="receiverBIC" type="hidden" value="{receiverBIC}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="string-length(receiverCorAccount)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Кор. счет</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="receiverCorAccount"/></b>
                        <input name="receiverCorAccount" type="hidden" value="{receiverCorAccount}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:if>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Плательщик</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Списать с</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:if test="$mode = 'view'">
                     <b>
                        <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:if>
                <xsl:if test="$mode = 'edit'">
                    <xsl:variable name="activeCards" select="document('active-not-virtual-cards-all-visible.xml')/entity-list"/>
                    <xsl:variable name="fromResource" select="fromResource"/>
                    <input type="hidden" name="fromResource" value="{fromResource}"/>
                    <xsl:variable name="cardNode" select="$activeCards/entity[$fromResource=./field[@name='code']]"/>
                    <xsl:variable name="cutCardNumber" select="mask:getCutCardNumber($cardNode/@key)"/>
                    <b>
                        <xsl:if test="string-length($cutCardNumber)>0">
                            <xsl:value-of select="$cutCardNumber"/>&nbsp;
                            [<xsl:value-of select="$cardNode/field[@name='name']"/>]
                             <xsl:value-of select="format-number($cardNode/field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                            <xsl:value-of select="mu:getCurrencySign($cardNode/field[@name='currencyCode'])"/>
                        </xsl:if>
                    </b>
                </xsl:if>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="currency" select="destinationCurrency"/>
        <input name="destinationCurrency" type="hidden" value="{destinationCurrency}"/>

        <xsl:for-each select="$extendedFields">
            <xsl:variable name="name" select="./NameVisible"/>
            <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
            <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
            <xsl:variable name="type" select="./Type"/>
            <xsl:variable name="id" select="./NameBS"/>
            <xsl:variable name="hint" select="./Comment"/>
            <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
            <xsl:variable name="groupName" select="./GroupName"/>
<!--скрытые редактируемые поля - это персчитываемые биллингом поля, значения которых необходимо показывать только на форме просмотра(подтвержедения)-->
            <xsl:if test="not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName"><xsl:value-of select="$name"/></xsl:with-param>
                    <xsl:with-param name="description"><xsl:value-of select="$hint"/></xsl:with-param>
                    <xsl:with-param name="rowValue"><b>
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:variable name="sum" select="./IsSum"/>
                        <xsl:if test="string-length($currentValue)>0 ">
                            <xsl:variable name="formatedValue">
                                <xsl:choose>
                                    <xsl:when test="($type='string' or $type='number') and ($mainSum='true')">
                                        <xsl:value-of select="format-number($currentValue, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($currency)"/>
                                    </xsl:when>
                                    <xsl:when test="$type='list'">
                                        <xsl:for-each select="./Menu/MenuItem">
                                            <xsl:variable name="code">
                                                <xsl:choose>
                                                    <xsl:when test="./Id != ''">
                                                        <xsl:value-of select="./Id"/>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <xsl:value-of select="./Value"/>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </xsl:variable>
                                            <xsl:if test="$code = $currentValue">
                                                <xsl:value-of select="./Value"/>
                                            </xsl:if>
                                        </xsl:for-each>
                                    </xsl:when>

                                    <xsl:when test="$type='set'">
                                        <xsl:choose>
                                            <xsl:when test="contains($currentValue, '@')">
                                                <xsl:for-each select="xalan:tokenize($currentValue, '@')">
                                                    <xsl:value-of select="current()"/>
                                                    <br/>
                                                </xsl:for-each>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:value-of select="$currentValue"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="$currentValue"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <xsl:choose>
                                <xsl:when test="$mainSum='true'">
                                    <span class="summ"><xsl:value-of select="$formatedValue"/></span>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:choose>
                                        <xsl:when test="$formData/*[name()=$id]/@changed='true'">
                                            <b><xsl:copy-of select="$formatedValue"/></b>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:copy-of select="$formatedValue"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="$sum='true'">&nbsp;руб.</xsl:if>
                            <input type="hidden" name="{$id}" value="{$currentValue}"/>
                        </xsl:if></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>

        <xsl:if test="$isTemplate != 'true'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowStyle">display:none</xsl:with-param>
                <xsl:with-param name="rowName">Плановая дата исполнения</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="admissionDate"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>

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
    </xsl:template>

    <xsl:template match="/form-data" mode="view-auto-sub-info">
        <xsl:variable name="autoSubTypes" select="document('auto-sub-payment-types.xml')/entity-list"/>
        <xsl:variable name="autoSubEventType" select="autoSubEventType"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Настройки автоплатежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата регистрации:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="autoSubStartDate">
                    <xsl:choose>
                        <xsl:when test="contains(autoSubStartDate, '-')">
                            <xsl:copy-of select="concat(substring(autoSubStartDate, 9, 2), '.', substring(autoSubStartDate, 6, 2), '.', substring(autoSubStartDate, 1, 4))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:copy-of select="autoSubStartDate"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <b><xsl:value-of select="$autoSubStartDate"/></b>
                <input type="hidden" name="autoSubStartDate" value="{$autoSubStartDate}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(autoSubUpdateDate)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата изменения:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="autoSubUpdateDate">
                        <xsl:choose>
                            <xsl:when test="contains(autoSubUpdateDate, '-')">
                                <xsl:copy-of select="concat(substring(autoSubUpdateDate, 9, 2), '.', substring(autoSubUpdateDate, 6, 2), '.', substring(autoSubUpdateDate, 1, 4))"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:copy-of select="autoSubUpdateDate"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <b><xsl:value-of select="$autoSubUpdateDate"/></b>
                    <input type="hidden" name="autoSubUpdateDate" value="{$autoSubUpdateDate}"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:variable name="autoSubType" select="autoSubType"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="lineId">autoSubType</xsl:with-param>
            <xsl:with-param name="rowName">Тип</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$autoSubTypes/entity[@key=$autoSubType]/@description"/></b>
                <input type="hidden"    name="autoSubEventType"     value="{autoSubEventType}"/>
                <input type="hidden"    name="autoSubType"          value="{autoSubType}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="autoSubType = 'ALWAYS'">
            <div id="ALWAYS">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Оплачивать:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="$autoSubTypes/entity[@key='ALWAYS']/field[@name=$autoSubEventType]/text()"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Дата ближайшего платежа</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="nextPayDateAlways">
                            <xsl:choose>
                                <xsl:when test="contains(nextPayDateAlways, '-')">
                                    <xsl:copy-of select="concat(substring(nextPayDateAlways, 9, 2), '.', substring(nextPayDateAlways, 6, 2), '.', substring(nextPayDateAlways, 1, 4))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="nextPayDateAlways"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <b><xsl:value-of select="$nextPayDateAlways"/></b>
                        <input type="hidden" name="nextPayDateAlways" value="{$nextPayDateAlways}"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Сумма:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="alwaysAmount"/> р.</b>
                        <input type="hidden" name="alwaysAmount" value="{alwaysAmount}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>

        <xsl:if test="autoSubType = 'INVOICE'">
            <div id="INVOICE">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Оплачивать:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <b><xsl:value-of select="$autoSubTypes/entity[@key='INVOICE']/field[@name=$autoSubEventType]/text()"/></b>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Ожидаемая дата оплаты счета:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:variable name="nextPayDateInvoice">
                            <xsl:choose>
                                <xsl:when test="contains(nextPayDateInvoice, '-')">
                                    <xsl:copy-of select="concat(substring(nextPayDateInvoice, 9, 2), '.', substring(nextPayDateInvoice, 6, 2), '.', substring(nextPayDateInvoice, 1, 4))"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="nextPayDateInvoice"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        <b><xsl:value-of select="$nextPayDateInvoice"/></b>
                        <input type="hidden" name="nextPayDateInvoice" value="{$nextPayDateInvoice}"/>
                    </xsl:with-param>
                </xsl:call-template>

                <xsl:if test="string-length(invoiceMaxAmount)>0">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Максимальный размер платежа</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="invoiceMaxAmount"/> р.</b>
                            <input type="hidden"    name="invoiceMaxAmount"  value="{invoiceMaxAmount}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </div>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Название:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b class="word-wrap"><xsl:value-of select="autoSubName"/></b>
                <input type="hidden"    name="autoSubName"          value="{autoSubName}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$mode = 'view'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус платежа:</xsl:with-param>
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
        </xsl:if>
    </xsl:template>

    <xsl:template name="standartRow">

        <xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--идентификатор строки-->
        <xsl:param name="required" select="'false'"/>   <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="description"/>                	<!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="rowStyle"/>                    <!-- Стил поля -->
        <xsl:param name="edit"/>

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
                <xsl:when test="$edit = 'false'">
                    1
                </xsl:when>
                <xsl:when test="string-length($inputName)>0">
                    <xsl:value-of select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
                </xsl:when>
                <xsl:otherwise>
                    0
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

    <div class="{$styleClass}">
        <xsl:if test="string-length($lineId) > 0">
            <xsl:attribute name="id"><xsl:copy-of select="$lineId"/>Row</xsl:attribute>
        </xsl:if>
        <xsl:if test="string-length($rowStyle) > 0">
            <xsl:attribute name="style">
                <xsl:copy-of select="$rowStyle"/>
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

<xsl:template name="employeeState2text">
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
        <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='SEND'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
        <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
        <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
        <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='PARTLY_EXECUTED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if>")</xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
    </xsl:choose>
</xsl:template>
<xsl:template name="clientState2text">
    <xsl:param name="code"/>
    <xsl:choose>
        <xsl:when test="$code='SAVED'">Черновик</xsl:when>
        <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
        <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
        <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
        <xsl:when test="$code='SEND'">Исполняется банком</xsl:when>
        <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
        <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
        <xsl:when test="$code='ERROR' and $webRoot='/PhizIA'">Приостановлен</xsl:when>
        <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
        <xsl:when test="$code='PARTLY_EXECUTED'">Исполняется банком</xsl:when>
        <xsl:when test="$code='UNKNOW' and $webRoot='/PhizIA'">Приостановлен</xsl:when>
        <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре<xsl:if test="reasonForAdditionalConfirm = 'IMSI'"> (Смена SIM-карты)</xsl:if></xsl:when>
        <xsl:when test="$code='TEMPLATE'">Сверхлимитный</xsl:when>
        <xsl:when test="$code='SAVED_TEMPLATE'">Черновик</xsl:when>
    </xsl:choose>
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

<xsl:template name="autoSubTypeEvent">
    <xsl:param name="events"/>
    <xsl:param name="name"/>
    <xsl:param name="value"/>

    <select name="{$name}" id="{$name}">
        <xsl:for-each select="$events/*">
           <xsl:variable name="nameEvent" select="./@name"/>
           <option value="{$nameEvent}">
               <xsl:if test="$nameEvent = $value">
                   <xsl:attribute name="selected"/>
               </xsl:if>
               <xsl:value-of select="./text()"/>
           </option>
        </xsl:for-each>
    </select>
</xsl:template>

</xsl:stylesheet>
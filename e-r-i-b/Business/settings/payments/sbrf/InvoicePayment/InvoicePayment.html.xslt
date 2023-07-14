<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

    <xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="mainSumKey" select="document('extendedFields.xml')/Attributes/Attribute[./IsMainSum='true']/NameBS/text()"/>
    <xsl:variable name="mainSumValue" select="$formData/*[name()=$mainSumKey]"/>
    <xsl:variable name="styleClass" select="'form-row'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>

    <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

<!-- Верхний блок счета -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">
                <b><xsl:value-of select="nameService"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">
                <b><xsl:value-of select="receiverName"/></b>
            </xsl:with-param>
        </xsl:call-template>

<!-- Счет выставлен по услуге -->
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет выставлен по услуге</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="subscriptionName"/>
            </xsl:with-param>
        </xsl:call-template>

<!-- Блок получатель -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName"><b>Получатель</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ИНН</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverINN"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverAccount"/></b>
                <input name="receiverAccount" type="hidden" value="{receiverAccount}"/>
            </xsl:with-param>
        </xsl:call-template>

<!-- Блок банк получателя -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Банк получателя</b></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverBankName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverBankName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">БИК</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverBIC"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverCorAccount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Корсчет</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="receiverCorAccount"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

<!-- Блок детали платежа -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Детали платежа</b></xsl:with-param>
        </xsl:call-template>

        <xsl:for-each select="$extendedFields">
            <xsl:variable name="name" select="./NameVisible"/>
            <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
            <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
            <xsl:variable name="type" select="./Type"/>
            <xsl:variable name="id" select="./NameBS"/>
            <xsl:variable name="mainSum" select="./IsMainSum = 'true'"/>
<!--скрытые редактируемые поля - это персчитываемые биллингом поля, значения которых необходимо показывать только на форме просмотра(подтвержедения)-->
            <xsl:if test="not($mainSum) and not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName"><xsl:value-of select="$name"/></xsl:with-param>
                    <xsl:with-param name="rowValue"><b>
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:variable name="sum" select="./IsSum"/>
                        <xsl:if test="string-length($currentValue)>0 ">
                            <xsl:variable name="formattedValue">
                                <xsl:choose>
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
                                <xsl:when test="$formData/*[name()=$id]/@changed='true'">
                                    <b><xsl:copy-of select="$formattedValue"/></b>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="$formattedValue"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="$sum='true'">&nbsp;руб.</xsl:if>
                        </xsl:if></b>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>

<!--Счет списания-->
        <xsl:variable name="fromAccountSelect" select="fromAccountSelect"/>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет списания:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="not($fromAccountSelect= '')">
                    <b>
                        <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:value-of select="fromResourceRest"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

<!--Сумма платежа-->
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName"><b>Сумма:</b></xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="format-number($mainSumValue, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:variable name="commission" select="commission"/>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="format-number($commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/>
            </xsl:with-param>
        </xsl:call-template>

<!-- Статус -->
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус платежа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
                    <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:call-template name="clientState2text">
                            <xsl:with-param name="code">
                                <xsl:value-of select="state"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </span>
                 </div>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="standartRow">

    	<xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--идентификатор строки-->
    	<xsl:param name="rowName"/>                     <!--описание поля-->
    	<xsl:param name="rowValue"/>                    <!--данные-->
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

            <div class="paymentLabel">
                <span class="paymentTextLabel">
                    <xsl:if test="string-length($id) > 0">
                        <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
                    </xsl:if>
                    <xsl:copy-of select="$rowName"/>
                </span>
            </div>
            <div class="paymentValue">
                        <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>
                            <div class="errorDiv" style="display: none;">
                        </div>
            </div>
            <div class="clear"></div>
        </div>

    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнено</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказано</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре</xsl:when>
        </xsl:choose>
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

</xsl:stylesheet>
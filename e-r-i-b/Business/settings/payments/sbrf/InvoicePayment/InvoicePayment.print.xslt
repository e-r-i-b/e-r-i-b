<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
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

        <!--Блок Отправитель-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowValue">
                Отправитель
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">
                Счет списания:
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="mask:getCutCardNumberPrint(fromAccountSelect)"/>
            </xsl:with-param>
        </xsl:call-template>

        <!--Блок Получатель-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowValue">
                Получатель
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="receiverName"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">ИНН</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="receiverINN"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="receiverAccount"/>
            </xsl:with-param>
        </xsl:call-template>

        <!-- Блок банк получателя -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowValue">Банк получателя</xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverBankName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Наименование</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="receiverBankName"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">БИК</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="receiverBIC"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(receiverCorAccount)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Корсчет</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:value-of select="receiverCorAccount"/>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <!-- Блок Детали платежа -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowValue">Детали платежа</xsl:with-param>
        </xsl:call-template>

        <!-- Счет выставлен по услуге -->
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Счет выставлен по услуге</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="subscriptionName"/>
            </xsl:with-param>
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
                    <xsl:with-param name="rowValue">
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
                                    <xsl:copy-of select="$formattedValue"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:copy-of select="$formattedValue"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            <xsl:if test="$sum='true'">руб.</xsl:if>
                        </xsl:if>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
        </xsl:for-each>


        <!--Сумма платежа-->
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
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

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowValue">
                Статус платежа
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус</xsl:with-param>
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

        <!--Печать-->
        <xsl:call-template name="stamp"/>

    </xsl:template>


    <!--Шаблон для вывода данных. Формат: Ключ: значение-->
    <xsl:template name="standartRow">
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <div class="checkSize">
           <xsl:copy-of select="$rowName"/>:
           <xsl:copy-of select="$rowValue"/>
        </div>
    </xsl:template>

    <!--Шаблон выбора статуса -->
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

    <!--Шаблон заголовка-->
    <xsl:template name="titleRow">
        <xsl:param name="rowValue"/>
        <div class="checkSize" style="margin:15px 0 5px;">
            <xsl:value-of select="$rowValue"/>
        </div>
    </xsl:template>

    <!--Шаблон штампа-->
    <xsl:template name="stamp">
          <br/>
          <div id="stamp" class="stamp">
             <xsl:value-of select="bn:getBankNameForPrintCheck()"/>
             <xsl:variable name="imagesPath" select="concat($resourceRoot, '/images/')"/>
             <xsl:choose>
                 <xsl:when test="state='DELAYED_DISPATCH'">
                    <img src="{concat($imagesPath, 'stampDelayed_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='DISPATCHED' or state='ERROR' or state ='UNKNOW'">
                     <img src="{concat($imagesPath, 'stampDispatched_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='EXECUTED'">
                     <img src="{concat($imagesPath, 'stampExecuted_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='REFUSED'">
                     <img src="{concat($imagesPath, 'stampRefused_blue.gif')}" alt=""/>
                 </xsl:when>
             </xsl:choose>
         </div>
         <br/>
      </xsl:template>
</xsl:stylesheet>
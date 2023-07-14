<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="styleClass" select="'form-row'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>

    <xsl:param name="application" select="'application'"/>
    <xsl:param name="app">
       <xsl:value-of select="$application"/>
    </xsl:param>

    <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">

<!-- Услуга -->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">
                <b><xsl:value-of select="subscriptionName"/></b>
            </xsl:with-param>
        </xsl:call-template>

<!-- Информация о поставщике-->
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">
                <b><xsl:value-of select="receiverName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Услуга:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:value-of select="nameService"/>
            </xsl:with-param>
        </xsl:call-template>

<!-- extendedFields -->
        <xsl:for-each select="$extendedFields">
            <xsl:variable name="name" select="./NameVisible"/>
            <xsl:variable name="isHideInConfirmation" select="./HideInConfirmation = 'true'"/>
            <xsl:variable name="hidden" select="./IsVisible = 'false'"/>
            <xsl:variable name="type" select="./Type"/>
            <xsl:variable name="id" select="./NameBS"/>
<!--скрытые редактируемые поля - это персчитываемые биллингом поля, значения которых необходимо показывать только на форме просмотра(подтвержедения)-->
            <xsl:if test="not($isHideInConfirmation) and not($hidden) and not($type='calendar')">
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
                        <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;
                        <xsl:value-of select="fromResourceRest"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </b>
                </xsl:if>
            </xsl:with-param>
        </xsl:call-template>

<!--Информация о выставлении счетов-->
        <xsl:if test="string-length(invoiceAccountName)>0">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Название выставляемого счета:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="invoiceAccountName"/></b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Группа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="groupName"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowValue">
                Счета по данной услуге выставляются
                <br/>
                <b><xsl:value-of select="invoiceInfo"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$app = 'PhizIA'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="state">
                        <xsl:call-template name="employeeState2text">
                            <xsl:with-param name="code">
                                <xsl:value-of select="state"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>

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

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
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
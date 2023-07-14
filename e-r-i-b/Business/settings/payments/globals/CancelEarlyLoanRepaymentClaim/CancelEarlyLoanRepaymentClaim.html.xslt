<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
		<!ENTITY nbsp "&#160;">
		]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:output method="html" version="1.0"  indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

	<xsl:template match="/">
		<xsl:apply-templates mode="view"/>
	</xsl:template>


    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата и время отмены заявки:</xsl:with-param>
            <xsl:with-param name="rowValue"><b><xsl:value-of select="documentDate"/></b></xsl:with-param>
        </xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Идентификатора заявки в ЕРИБ:</xsl:with-param>
			<xsl:with-param name="rowValue"><b><xsl:value-of select="documentNumber"/></b></xsl:with-param>
		</xsl:call-template>

		<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Статус заявки:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b>
                    <div id="state">
                        <span onmouseover="showLayer('state','stateDescription');"
                           onmouseout="hideLayer('stateDescription');" class="link">
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
                </b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Канал подачи заявки:</xsl:with-param>
            <xsl:with-param name="rowValue"><b>ЕРИБ</b></xsl:with-param>
        </xsl:call-template>

    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
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

            <div class="paymentLabel">
                <span class="paymentTextLabel">
                    <xsl:if test="string-length($id) > 0">
                        <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
                    </xsl:if>
                    <xsl:copy-of select="$rowName"/>
                </span>
            </div>
            <div class="paymentValue">
                        <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/></div>

                        <div class="errorDiv" style="display: none;">
                        </div>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>

</xsl:stylesheet>
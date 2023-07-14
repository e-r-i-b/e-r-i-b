<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
        xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0"  indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClassTitle" select="'rowTitles'"/>
	<xsl:variable name="styleSpecial" select="''"/>

    <xsl:template match="/">
        <xsl:apply-templates mode="editForm"/>
    </xsl:template>

	<xsl:template match="/form-data" mode="editForm">
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">reportCardNumber</xsl:with-param>
            <xsl:with-param name="rowName">Карта</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><span><xsl:value-of select="report-card-name"/>&nbsp;<xsl:value-of select="mask:getCutCardNumber(report-card-number)"/></span></b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">eMail</xsl:with-param>
            <xsl:with-param name="rowName">E-mail</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="e-mail"/></b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">period</xsl:with-param>
            <xsl:with-param name="rowName">Период</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="report-order-type = 'IR'">
                        <b><xsl:value-of select="report-start-date-formated"/> &#8212; <xsl:value-of select="report-end-date-formated"/></b>
                    </xsl:when>
                    <xsl:otherwise>
                        <b><xsl:value-of select="report-start-date-formated"/> </b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">reportLang</xsl:with-param>
            <xsl:with-param name="rowName">Язык отчета</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="report-lang = 'RUS'"><b>Русский</b></xsl:when>
                    <xsl:otherwise><b>Английский</b></xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="id">reportFormat</xsl:with-param>
            <xsl:with-param name="rowName">Формат отчета</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="report-format"/></b>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус заявки</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
                    <span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
                        <xsl:call-template name="state2text">
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

<!--  Поиск ошибки по имени поля
В данной реализации ошибки обрабатывает javascript
                <xsl:if test="$mode = 'edit'">
                    <xsl:if test="boolean($validationErrors/entity[@key=$fieldName])">
                        <xsl:copy-of select="$validationErrors/entity[@key=$fieldName]"/>
                    </xsl:if>
                </xsl:if>
-->
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
    </div>
	<div class="paymentValue">
        <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/></div>
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

<xsl:template name="state2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='EXECUTED'">Исполнено</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
        </xsl:choose>
</xsl:template>

</xsl:stylesheet>
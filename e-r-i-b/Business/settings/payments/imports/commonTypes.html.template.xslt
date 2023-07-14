<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<!-- шаблоны полей для платежей -->
<!-- подключается тэгом <xsl:import href="commonTypes.html.template.xslt"/> -->
<xsl:stylesheet version     ="1.0"
                xmlns:xsl   ="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu    ="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan = "http://xml.apache.org/xalan">

    <xsl:output method="html" version="1.0" indent="yes"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>

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
                <xsl:if test="string-length($rowName) > 0">
                    <xsl:copy-of select="$rowName"/>
                </xsl:if>
    	    </span>
            <xsl:if test="$required = 'true' and $mode = 'edit'">
                <span id="asterisk_{$id}" class="asterisk" name="asterisk_{$lineId}">*</span>
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

    <xsl:template name="button" mode="edit">
        <xsl:param name="id"/>
        <xsl:param name="onclick"/>
        <xsl:param name="typeBtn"/>
        <xsl:param name="btnId"/>
        <xsl:param name="style" select="'display:none;'"/>
        <xsl:param name="btnText" select="'Удалить'"/>
        <div id="{$id}" style="{$style}">
            <div id="{$btnId}" class="clientButton">
                <xsl:variable name="className">
                    <xsl:choose>
                         <xsl:when test="$typeBtn != ''">
                             <xsl:copy-of select="$typeBtn"/>
                         </xsl:when>
                         <xsl:otherwise>buttonGrey</xsl:otherwise>
                     </xsl:choose>
                </xsl:variable>
                <div class="{$className}" onclick="{$onclick}">
                    <div class="left-corner"></div>
                    <div class="text">
                        <span><xsl:copy-of select="$btnText"/></span>
                    </div>
                    <div class="right-corner"></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="liveSearchTemplate">
        <!-- id элемента в который обёрнут данный компонент. Наличие его обязательно! -->
        <xsl:param name="boundToElement"/>

        <!-- Название js переменной ссылающейся на объект с данными -->
        <xsl:param name="boundToData"          select="''"/>

        <!-- Название поля отправляемого на сервер -->
        <xsl:param name="postedValueInputName" select="''"/>

        <xsl:param name="onSelect"        select="''"/>
        <xsl:param name="changedElements" select="''"/>

        <div class="liveSearchComponent">
            <div class="liveSearchSelectBlock" style="z-index: 9">
                <div class="liveSearchSelect">
                    <div class="liveSearchSelectLeft"></div>
                    <div class="liveSearchSelectRight"></div>
                    <div class="liveSearchSelectCenter">
                        <div class="liveSearchSelectCenterBlock">
                            <span class="liveSearchSelectTopText"></span>
                        </div>
                        <div class="liveSearchSelectHide">
                            <div class="liveSearchSelectArrow"></div>
                        </div>
                    </div>
                </div>

                <div class="liveSearchDropDownContainer mcs_container" style="display: none">
                    <div class="customScrollBox">
                        <div class="container">
                            <div class="content">
                                <div class="searchBlockList">
                                    <ul></ul>
                                </div>
                            </div>
                        </div>

                        <div class="dragger_container">
                            <div class="dragger">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>

            <div style="clear: both"></div>

            <div class="liveSearchSelectList" style="display: none">
                <div class="searchSelectShadow" style="z-index: 20">
                    <div class="liveSearchBlock">
                        <span>
                            <input type="text" class="customPlaceholder inputPlaceholder searchInput" title="поиск"/>
                        </span>
                        <a class="roundCloseIcon"></a>
                    </div>

                    <div class="liveSearchResultContainer mcs_container" style="display: none">
                        <div class="customScrollBox">
                            <div class="container">
                                <div class="content">
                                    <div class="searchBlockList">
                                        <ul></ul>
                                    </div>
                                </div>
                            </div>

                            <div class="dragger_container">
                                <div class="dragger">&nbsp;</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" class="liveSearchValueInput"/>
        </div>

        <script type="text/javascript">
            $(document).ready(function() {

                var properties =
                {
                    liveSearchValueInputName : '<xsl:value-of select="$postedValueInputName"/>'
                };

                var liveSearch = new LiveSearch();
                <xsl:if test="not($onSelect = '')">
                    liveSearch.setOnSelectCallback(function(selected)
                    {
                        <xsl:choose>
                            <xsl:when test="not($changedElements = '')">
                                <xsl:value-of select="$onSelect"/>(selected, <xsl:value-of select="$changedElements"/>);
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$onSelect"/>(selected);
                            </xsl:otherwise>
                        </xsl:choose>
                    });
                </xsl:if>

                liveSearch.init('<xsl:value-of select="$boundToElement"/>', <xsl:value-of select="$boundToData"/>, properties);
            });
        </script>
    </xsl:template>

    <xsl:template name="liveSearchInput">
        <!-- id элемента в который обёрнут данный компонент. Наличие его обязательно! -->
        <xsl:param name="boundToElement"/>
        <!-- URL для запросов. Наличие не обязательно. -->
        <xsl:param name="url"          select="''"/>
        <!-- Значение атрибута name поля ввода. Наличие не обязательно. -->
        <xsl:param name="inputName"    select="''"/>
        <!--
           - Название js переменной. Поскольку на форме может быть несколько таких полей, названия должны быть
           - разными. Должно отвечать требованиям конвенции о наименовании js переменных.
           -->
        <xsl:param name="varName"      select="'default'"/>
        <!-- Тип запроса. (Используется на форме расширенной заявки на кредит, наличие не обязательно) -->
        <xsl:param name="pageType"     select="''"/>
        <!-- Событие, возникающее при выборе элемента выпадающего списка. Наличие не обязательно. -->
        <xsl:param name="onSelect"     select="''"/>
        <!-- Событие, возникающее когда поле ввода очищено. Наличие не обязательно. -->
        <xsl:param name="onClear"     select="''"/>
        <!--Событие, возникающее когда изменяется значения поля со справочником. Наличие не обязательно.-->
        <xsl:param name="onKeyDown"     select="''"/>
        <!-- Начальное значение поля воода. Наличие не обязательно. -->
        <xsl:param name="initialValue" select="''"/>
        <!-- Максимальное количество символов в поле ввода. Наличие не обязательно. -->
        <xsl:param name="length" select="''"/>
        <!--
           - Если на форме несколько таких полей то необходимо указывать данный признак так, чтобы для разных
           - полей он был бы разным. Ежели только одно поле - то необходимости указывать данный признак нет.
           -->
        <xsl:param name="blockIndex" select="''"/>
        <!-- Ссылки на js-объекты которые зависимы от изменений в описываемом ниже поле живого поиска.
           - Наличие не обязательно. -->
        <xsl:param name="mutableJSObjects" select="''"/>

        <!--
           - Если на странице располагается несколько таких полей - признак необходимо устанавливать тем больший,
           - чем выше он отображается
           -->
        <xsl:param name="z-index"        select="'10'"/>

        <xsl:param name="requiredParameters" select="''"/>

        <!-- ******************************* Html представление ************************************ -->
        <div class="liveSearchSelectList inputPosition">
            <div class="searchSelectShadow" style="box-shadow: 0 0 0 0;">
                <div class="liveSearchBlockNoFrame">
                    <span>
                        <input name="{$inputName}" type="text" maxlength="{$length}" class="searchInput"  value="{$initialValue}"/>
                    </span>
                </div>

                <div class="liveSearchResultContainer mcs_container">
                    <div class="customScrollBox">
                        <div class="container">
                            <div class="content">

                            </div>
                        </div>

                        <div class="dragger_container">
                            <div class="dragger">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ******************************* Инициализация ************************************ -->
        <script type="text/javascript">
            var <xsl:value-of select="$varName"/> = new LiveSearchInput();
            $(document).ready(function()
            {
                <xsl:value-of select="$varName"/>.bind2($('#<xsl:value-of select="$boundToElement"/>')[0], <xsl:value-of select="$varName"/>.elements);
                <xsl:value-of select="$varName"/>.setConfig({z_index: '<xsl:value-of select="$z-index"/>', options: {url: '<xsl:value-of select="$url"/>'}});

                <xsl:if test="not($onKeyDown = '')">
                    $("input[name=<xsl:value-of select="$inputName"/>]").keydown(function()
                    {
                        <xsl:value-of select="$onKeyDown"/>('<xsl:value-of select="$blockIndex"/>');
                    });
                </xsl:if>
                <xsl:if test="not($onClear = '')">
                    <!--если из поля удалили значение, то нужно очистить соответствующие ему поля-->
                    $("input[name=<xsl:value-of select="$inputName"/>]").change(function()
                    {
                        if ($(this).val() == "")
                        {
                            <xsl:choose>
                                <xsl:when test="not($mutableJSObjects = '')">
                                    <xsl:value-of select="$onClear"/>(<xsl:value-of select="$varName"/>, '<xsl:value-of select="$blockIndex"/>', <xsl:value-of select="$mutableJSObjects"/>);
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$onClear"/>(<xsl:value-of select="$varName"/>, '<xsl:value-of select="$blockIndex"/>');
                                </xsl:otherwise>
                            </xsl:choose>
                        }
                    });
                </xsl:if>

                <xsl:if test="not($onSelect = '')">
                    <xsl:value-of select="$varName"/>.onComplete = function(selected)
                    {
                        <xsl:choose>
                            <xsl:when test="not($mutableJSObjects = '')">
                                <xsl:value-of select="$onSelect"/>(selected, '<xsl:value-of select="$blockIndex"/>', <xsl:value-of select="$mutableJSObjects"/>);
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$onSelect"/>(selected, '<xsl:value-of select="$blockIndex"/>');
                            </xsl:otherwise>
                        </xsl:choose>
                    };
                </xsl:if>

                <xsl:if test="not($requiredParameters = '')">
                    <xsl:value-of select="$varName"/>.setRequiredParameters([<xsl:value-of select="$requiredParameters"/>]);
                </xsl:if>

                <xsl:if test="not($pageType = '')">
                    <xsl:value-of select="$varName"/>.addParameter('pageType', '<xsl:value-of select="$pageType"/>');
                </xsl:if>
            });
        </script>
    </xsl:template>

    <!--Подсказка в виде знака вопроса со всплывающим сообщением-->
    <xsl:template name="popUpMessage">
        <xsl:param name="text"/>
        <xsl:param name="app"/>

        <xsl:if test="$app != 'PhizIA'">
            <div class="simpleHintBlock float">
                <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                <div class="clear"></div>
                <div class="layerFon simpleHint">
                    <div class="floatMessageHeader"></div>
                    <div class="layerFonBlock"><xsl:value-of select="$text"/></div>
                </div>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template name="valueSlider">
        <xsl:param name="id"                  select="''"/>
        <xsl:param name="readOnly"            select="'false'"/>
        <xsl:param name="valueAlign"          select="'left'"/>
        <xsl:param name="formattedFieldClass" select="''"/>
        <xsl:param name="data"                select="node()"/>
        <xsl:param name="inputName"           select="''"/>

        <div id="{$id}">
            <div class="scrollInputSmall">
                <xsl:variable name="templateClasses" select="concat($formattedFieldClass, ' ', 'fakeSliderInput')"/>

                <input type="text">
                    <xsl:attribute name="class">
                        <xsl:choose>
                            <xsl:when test="$valueAlign = 'right'">
                                <xsl:value-of select="concat($templateClasses, ' ', 'alignRight')"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$templateClasses"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>

                    <xsl:if test="$readOnly = 'true'">
                        <xsl:attribute name="readonly">
                            <xsl:value-of select="'true'"/>
                        </xsl:attribute>
                    </xsl:if>
                </input>

                <xsl:copy-of select="$data"/>
            </div>

            <div class="scrollerinlineSmall">
                <div class="dragdealerSmall sliderContainer">
                    <div class="orangeScroll">
                        <div class="orangeScrollCenter">
                            <div class="orangeScrollMain sliderStrip"></div>
                        </div>

                        <div class="clear"></div>
                        <div class="orangeScrollInner handle sliderArrow"></div>
                    </div>
                </div>
            </div>

            <input name="{$inputName}" type="hidden" class="sliderInput"/>
        </div>
    </xsl:template>

    <xsl:template name="loanTiles">
        <xsl:param name="loanOffers"        select="node()"/>
        <xsl:param name="conditions"        select="node()"/>
        <xsl:param name="onSelectLoanOffer" select="''"/>
        <xsl:param name="onSelectCondition" select="''"/>

        <xsl:if test="not(count($loanOffers) = 0)">
            <div class="clear"></div>

            <div class="specialOffer">
                <div class="changeBlock">
                    <div class="changeBlockTitle">Специальное предложение</div>
                    <div class="clear"></div>

                    <div class="changeBlockTitleInner">
                        <xsl:for-each select="$loanOffers">
                            <xsl:variable name="loanOffer" select="current()"/>

                            <xsl:for-each select="current()/entity-list/entity[@key='CONDITION']">
                                <xsl:variable name="conditionAmount" select="current()/field[@name='amount']/text()"/>

                                <xsl:if test="not($conditionAmount = '' or $conditionAmount = 0)">
                                    <xsl:call-template name="loanTile">
                                        <xsl:with-param name="offer"             select="$loanOffer"/>
                                        <xsl:with-param name="condition"         select="current()"/>
                                        <xsl:with-param name="onSelectLoanOffer" select="$onSelectLoanOffer"/>
                                    </xsl:call-template>
                                </xsl:if>

                            </xsl:for-each>
                        </xsl:for-each>

                        <div class="clear"></div>
                    </div>
                </div>

                <xsl:if test="count($conditions) > 0">
                    <div class="selectConditions" id="loanRules">
                        <xsl:attribute name="onclick">
                            <xsl:value-of select="concat('onClick(this);', $onSelectCondition)"/>
                        </xsl:attribute>

                        <div class="conditionsBlock">
                            <table class="textBlock">
                                <tr>
                                    <td style="vertical-align: middle; text-align: center">Оформление кредита на обычных условиях</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </xsl:if>
            </div>

            <div class="clear" style="padding-bottom: 10px"></div>
        </xsl:if>
    </xsl:template>

    <xsl:template name="loanTile">
        <xsl:param name="offer"             select="node()"/>
        <xsl:param name="condition"         select="node()"/>
        <xsl:param name="onSelectLoanOffer" select="''"/>

        <xsl:variable name="duration"        select="$condition/field[@name='period']/text()"/>
        <xsl:variable name="conditionAmount" select="$condition/field[@name='amount']/text()"/>

        <div class="selectConditions">
            <xsl:attribute name="id">
                <xsl:value-of select="concat($offer/field[@name='id']/text(), '_', $duration)"/>
            </xsl:attribute>
            <xsl:attribute name="onclick">
                <xsl:value-of select="concat('onClick(this);', $onSelectLoanOffer)"/>
            </xsl:attribute>

            <div class="conditionsBlock css3">
                <div class="chosenConditionBlock"></div>
                <div class="sumSelectConditions">
                    <div>
                        <span class="conditionAmount">
                            <xsl:value-of select="format-number($conditionAmount, '### ##0,00', 'sbrf')"/>
                        </span>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign($offer/field[@name='currency']/text())"/>
                    </div>

                    <div class="interestRate">
                        <xsl:value-of select="$condition/field[@name='period']"/>&nbsp;месяцев,
                        <xsl:value-of select="format-number($offer/field[@name='rate'], '### ##0,00', 'sbrf')"/>%
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
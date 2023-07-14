<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dvu="java://com.rssl.phizic.web.util.DepartmentViewUtil"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set">

	<xsl:output method="html" version="1.0" indent="yes"/>

	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
 	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'Width220 LabelAll labelPadding'"/>
    <xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="'padding-right: 15px'"/>
    <xsl:variable name="styleValue" select="'font-weight:bold;'"/>

    <xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
        <xsl:apply-templates mode="view"/>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
        <script type="text/javascript">
            doOnLoad(
                function(){
                    $('#phone').text("<xsl:value-of select="dvu:getDepartmentPhoneNumber(tb, osb, vsp)"/>");
                    document.getElementById('showTerms').style.display = <xsl:value-of  select="string-length(additionalTerms)>0"/>?"":"none";
                    $("#period").text("<xsl:value-of select="grace-period-duration"/>");
                    $("#interestRateGrace").text("<xsl:value-of select="grace-period-interest-rate"/>");
                    $("#additionalTermsText").html('<xsl:value-of select="additionalTerms"/>');
                });

            function showOrHideAdditionalTerms()
            {
                if($('#additionalTermsText').css('display') == 'none')
                {
                 $('#additionalTermsText').css('display', '');
                $("#showTerms").text("Скрыть");
                }
                else
                {
                 $('#additionalTermsText').css('display', 'none');
                $("#showTerms").text("Подробнее");
                }
            }
        </script>
        <xsl:variable name="currencySign" select="mu:getCurrencySign(currency)"/>
        <div class="grayContentBlock allWidthBlock">
            <div class="grayBlockTitle">
                <div class="{$styleClassTitle}"><xsl:value-of  select="creditCard"/></div>
            </div>

            <xsl:call-template name="conditionsRow"/>

            <table class="paymentTableRow" width="100%">
                <tbody>
                    <tr>
                        <th class="align-left">Кредитный лимит</th>
                        <th class="align-left">Процентная ставка</th>
                        <th class="align-left">Годовое обслуживание</th>
                    </tr>
                    <tr>
                        <td>
                            <span style="{$styleValue}">
                                <span class="amount"><xsl:value-of select="mu:formatAmountWithNoCents(string(amount))"/></span>
                            </span>&nbsp;
                            <xsl:value-of select="$currencySign"/>
                        </td>
                        <td style="{$styleValue}"><span class="amount">
                            <xsl:value-of  select="interest-rate"/>&nbsp;%</span>
                        </td>
                        <td style="{$styleValue}">
                            <span class="amount">
                                <xsl:value-of select="mu:formatAmountWithNoCents(string(next-year-service))"/>&nbsp;<xsl:value-of select="$currencySign"/><br/>
                                Первый год — &nbsp;<xsl:value-of select="mu:formatAmountWithNoCents(string(first-year-service))"/>&nbsp;<xsl:value-of select="$currencySign"/>
                            </span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="clear"></div>
        <div id="cardParams">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Срок выпуска карты:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="gettingDate" style="{$styleValue}">
                        <xsl:choose>
                            <xsl:when test="tb = 99 or tb = 38">
                                5 рабочих дней
                            </xsl:when>
                            <xsl:otherwise>
                                5-10 рабочих дней
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                </xsl:with-param>
            </xsl:call-template>
        </div>

        <div id="additionalInfo" class="additionalInfoLoan">
            <div class="{$styleClassTitle} rowTitle18 rowTitleView">Дополнительная информация</div>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">ФИО получателя карты:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of select="ph:getFormattedPersonName(firstName, surName, patrName)"/></xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Где я хочу получить карту:</xsl:with-param>
                <xsl:with-param name="rowValue"><xsl:value-of  select="credit-card-office"/></xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус заявки:</xsl:with-param>
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
        <xsl:param name="lineId"/>                          <!--идентификатор строки-->
        <xsl:param name="required" select="'true'"/>        <!--параметр обязатьльности заполнения-->
        <xsl:param name="rowName"/>                         <!--описание поля-->
        <xsl:param name="rowValue"/>                        <!--данные-->
        <xsl:param name="description"/>                	    <!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>                        <!-- Стиль поля -->
        <xsl:param name="isAllocate" select="'true'"/>      <!-- Выделить при нажатии -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                       <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="useValueStyle" select="'true'"/>   <!-- Признак использования стиля для значения -->

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
            <xsl:if test="string-length($rowName)>0">
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
            </xsl:if>
            <div class="paymentValue">
                <div class="paymentInputDiv">
                    <xsl:if test="$useValueStyle = 'true'">
                        <xsl:attribute name="style">
                            <xsl:value-of select="$styleValue"/>
                        </xsl:attribute>
                    </xsl:if>
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

    <xsl:template name="conditionsRow">
	    <xsl:param name="rowName"/>

        <xsl:variable name="name">
            <xsl:if test="$mode = 'edit'">
                    <xsl:copy-of select="$rowName"/>
            </xsl:if>
        </xsl:variable>
            <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName" select="$name"/>
                <xsl:with-param name="useValueStyle" select="'false'"/>
                <!--<xsl:with-param name="isAllocate" select="'false'"/>-->
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="rowValue">
                    <div class="marginText">Льготный период кредитования до <span id="period" style="{$styleValue}"></span>&nbsp;<b>дней</b>
                    <input type="hidden" name="grace-period-duration" id="grace-period-duration"/></div>

                    <div class="marginText">Процентная ставка в льготный период <span id="interestRateGrace" style="{$styleValue}"></span>&nbsp;<b>%</b>
                    <input type="hidden" name="grace-period-interest-rate" id="grace-period-interest-rate"/></div>

                    <input type="hidden" name="additionalTerms" id="additionalTerms"/>
                    <div id="additionalTermsText" style="display:none"></div>
                    <div class="showTermsBlock"><a id="showTerms" class="blueGrayLinkDotted" onclick='showOrHideAdditionalTerms()'>Подробнее</a></div>
                </xsl:with-param>
            </xsl:call-template>
	</xsl:template>

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='ADOPTED'">Принята (статус для клиента: "Исполняется банком")</xsl:when>
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
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='ADOPTED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>

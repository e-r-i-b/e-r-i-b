<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
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
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/loan"/>
	<xsl:variable name="products" select="document('loan-products.xml')"/>

	<xsl:variable name="styleClass" select="'Width220 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>


	<xsl:variable name="formData" select="/form-data"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
					<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
        

        <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>

        <input type="hidden" name="loan" value="{loan}"/>

        <xsl:variable name="loanInfo" select="document(concat('loanOffer.xml?offer=',loan))/entity-list/entity"/>
        <xsl:variable name="conditions" select="$loanInfo/entity-list/entity"/>

        <input type="hidden" name="passport-number" value="{$loanInfo/field[@name = 'pasportNumber']}"/>
        <input type="hidden" name="passport-series" value="{$loanInfo/field[@name = 'pasportSeries']}"/>
        <input type="hidden" name="tb" value="{$loanInfo/field[@name = 'tb']}"/>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Информация о кредите</b></xsl:with-param>
        </xsl:call-template>
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

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Наименование кредита:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="$loanInfo/field[@name = 'name']"/></b>
                <input type="hidden" name="creditType" value="{$loanInfo/field[@name = 'name']}"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Срок:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="duration"/>&nbsp;мес.</b>
                <input type="hidden" name="duration" value="{duration}"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="amount != ''">
                        <input type="text" name="amount" value="{mu:getFormatAmountWithNoCents(amount,'.')}" size="50" class="moneyField"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="selectedDuration">
                            <xsl:value-of select="duration"/>
                        </xsl:variable>
                        <input type="text" name="amount" size="50" class="moneyField">
                            <xsl:for-each select="$conditions/entity-list/entity">
                                <xsl:variable name="durationValue" select="field[@name='duration']/text()"/>
                                <xsl:if test="$selectedDuration = $durationValue">
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="field[@name='amount']/text()"/>
                                    </xsl:attribute>
                                </xsl:if>
                            </xsl:for-each>
                        </input>
                    </xsl:otherwise>
                </xsl:choose>
                &nbsp;
                <xsl:variable name="currency" select="$loanInfo/field[@name = 'currency']"/>
                <input type="hidden" name="currency" value="{$currency}"/>
                <xsl:value-of select="mu:getCurrencySign($currency)"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Персональная информация</b></xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="substring($person/field[@name = 'surName'], 1, 1)" />.
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Имя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="$person/field[@name = 'firstName']" />
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Отчество:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b>
                    <xsl:value-of select="$person/field[@name = 'patrName']" />
                </b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Работодатель:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="hirer" value="{hirer}" maxlength="50" size="50"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
            <xsl:with-param name="helpMsg">10 цифр. Например 4955005550</xsl:with-param>
            <xsl:with-param name="rowValue">
                 <input type="text" name="mobilePhone" value="{mobilePhone}" maxlength="10" size="30">
                     <xsl:if test="$formData/*[name()='mobilePhone']/@masked = 'true'">
                        <xsl:attribute name="class">masked-phone-number</xsl:attribute>
                    </xsl:if>
                 </input>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Средний доход в месяц:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="averageIncomePerMonth" value="{averageIncomePerMonth}" size="50" class="moneyField"/>
                &nbsp;
                <xsl:value-of select="mu:getCurrencySign('RUB')"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

      <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName"></xsl:with-param>
            <xsl:with-param name="rowValue">
            <xsl:choose>
                <xsl:when test="getPaidOnAccount ='true'">
                    <input type="checkbox" id="getPaidOnAccount" onChange="chg('getPaidOnAccount')" checked="checked" name="getPaidOnAccount" value="{getPaidOnAccount}" style="vertical-align: middle;"/>
                </xsl:when>
                <xsl:otherwise>
                    <input type="checkbox" id="getPaidOnAccount" onChange="chg('getPaidOnAccount')" name="getPaidOnAccount" value="{getPaidOnAccount}" style="vertical-align: middle;"/>
                </xsl:otherwise>     
            </xsl:choose>
                <b> получаю зарплату на карту/счет в Сбербанке</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
      </xsl:call-template>

        <script type="text/javascript">
		<![CDATA[
            function chg(elementId)
            {
                var t = document.getElementById(elementId);

                if (t.checked)
                    setValue(elementId, "true")
                else
                    setValue(elementId, "false");
            }

            function setValue(elementId, value)
		    {
                var elem = document.getElementById(elementId);

                if(elem.value != null)
                    elem.value = value;
                else if(elem.innerHTML != null)
                    elem.innerHTML = value;
		    }
		]]>
        </script>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Информация о кредите</b></xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Дата документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Вид кредита:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="creditType"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b class="summ"><xsl:value-of select="mu:getFormatAmountWithNoCents(amount,'.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(currency)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Срок:</xsl:with-param>
            <xsl:with-param name="rowValue">
                 <b><xsl:value-of select="duration"/>&nbsp;мес.</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName">&nbsp;<b>Персональная информация</b></xsl:with-param>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="substring(surName, 1, 1)"/>.</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Имя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="firstName"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Отчество:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="patrName"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Работодатель:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="hirer"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="mobilePhone"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Средний доход в месяц:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="averageIncomePerMonth"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Получаю зарплату на карту/счет в Сбербанке:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:if test="getPaidOnAccount='true'">
                    <input type="checkbox" id="getPaidOnAccount" checked="checked"  name="getPaidOnAccount" value="{getPaidOnAccount}" disabled="true"/>
               </xsl:if>
               <xsl:if test="getPaidOnAccount='false'">
                   <input type="checkbox" id="getPaidOnAccount"  name="getPaidOnAccount" value="{getPaidOnAccount}" disabled="true"/>
               </xsl:if>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
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
         <xsl:param name="helpMsg"/>                  	<!-- Описание поля, данные после будут отображаться с лева  -->
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
                    <xsl:if test="$helpMsg!=''">
                        <div class="paymentInputFloatDiv"><xsl:copy-of select="$rowValue"/> </div>
                        <div class="helpMsgDiv"><xsl:copy-of select="$helpMsg"/></div>
                    </xsl:if>
                    <xsl:if test="$helpMsg=''">
                        <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>
                    </xsl:if>
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
            <xsl:when test="$code='ADOPTED'">Принята (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
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

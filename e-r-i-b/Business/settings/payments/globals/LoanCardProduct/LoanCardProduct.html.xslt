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
	<!--<xsl:variable name="description">Подача в банк заявки на кредит.</xsl:variable>-->
	<!--<xsl:variable name="title">Заявка на кредит.</xsl:variable>-->

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

       <input type="hidden" id="income" name="income" value="{income}"/>
       <input type="hidden" name="loan" value="{loan}"/>
       <input type="hidden" name="changeDate" value="{changeDate}"/>
       <xsl:variable name="loanInfo" select="document(concat('loanCardProduct.xml?loan=',loan))/entity-list/entity"/>
       <xsl:variable name="limit" select="document(concat('cardAmountStep.xml?loan=',loan))"/>

       <input type="hidden" name="min-limit" value="{$loanInfo/field[@name = 'min-limit']}"/>
       <input type="hidden" name="max-limit" value="{$loanInfo/field[@name = 'max-limit']}"/>
       <input type="hidden" name="max-limit-include" value="{$loanInfo/field[@name = 'max-limit-include']}"/>

       <input type="hidden" name="additionalTerms" value="{$loanInfo/field[@name = 'additionalTerms']}"/>
       <input type="hidden" name="grace-period-duration" value="{$loanInfo/field[@name = 'grace-period-duration']}"/>
       <input type="hidden" name="grace-period-interest-rate" value="{$loanInfo/field[@name = 'grace-period-interest-rate']}"/>

        <input type="hidden" name="cardProductId" value="{$loanInfo/field[@name = 'cardProductId']}"/> 

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
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Кредитная карта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="creditCard" value="{$loanInfo/field[@name = 'name']}"/>
                <b><xsl:value-of  select="$loanInfo/field[@name = 'name']"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="interest-rate" value="{$loanInfo/field[@name = 'interestRate']}"/>
                <b><xsl:value-of  select="$loanInfo/field[@name = 'interestRate']"/> %</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">
                Годовое обслуживание<br/>Первый/последующие годы:
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:variable name="yearPaymentCurrency" select="$loanInfo/field[@name = 'firstYearPaymentCurrency']"/>
                <xsl:variable name="nextYearPaymentCurrency" select="$loanInfo/field[@name = 'nextYearPaymentCurrency']"/>
                <input type="hidden" name="first-year-service" value="{$loanInfo/field[@name = 'firstYearPaymentDecimal']}"/>
                <input type="hidden" name="first-year-service-currency" value="{$yearPaymentCurrency}"/>
                <input type="hidden" name="next-year-service-currency" value="{$nextYearPaymentCurrency}"/>
                <input type="hidden" name="next-year-service" value="{$loanInfo/field[@name = 'nextYearPaymentDecimal']}"/>
                <b><xsl:value-of  select="$loanInfo/field[@name = 'firstYearPaymentDecimal']"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($yearPaymentCurrency)"/> /
                <xsl:value-of  select="$loanInfo/field[@name = 'nextYearPaymentDecimal']"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($nextYearPaymentCurrency)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>        

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Кредитный лимит:</xsl:with-param>
            <xsl:with-param name="rowValue">
                 <xsl:variable name="amountForm">
                    <xsl:value-of select="amount"/>
                 </xsl:variable>
                 <select  name="amount" style="width: 320px;">
                    <xsl:for-each select="$limit/entity-list/entity" >
                        <xsl:variable name="amountLimit" select="field[@name='decimal']/text()"/>
                        <option>
                            <xsl:if test="$amountForm = $amountLimit">
                                <xsl:attribute name="selected">true</xsl:attribute>
                            </xsl:if>
                            <xsl:attribute name="value">
                                <xsl:value-of select="$amountLimit"/>    
                            </xsl:attribute>
                            <xsl:value-of select="$amountLimit"/>
                        </option>
                    </xsl:for-each>
                 </select>
                <xsl:variable name="currency" select="$loanInfo/field[@name = 'currency']"/>
                <input type="hidden" name="currency" value="{$currency}"/>&nbsp;
                <xsl:value-of select="mu:getCurrencySign($currency)"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:variable name="person" select="document('currentPersonData.xml')/entity-list/entity"/>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input type="hidden" name="surName" value="{$person/field[@name = 'surName']}"/>
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
                    <input type="hidden" name="firstName" value="{$person/field[@name = 'firstName']}"/>
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
                    <input type="hidden" name="patrName" value="{$person/field[@name = 'patrName']}"/>
                    <b>
                        <xsl:value-of select="$person/field[@name = 'patrName']" />
                    </b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Домашний телефон:</xsl:with-param>
            <xsl:with-param name="description">Номер телефона указывается без 8, содержит 10 цифр.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="homePhone != ''">
                        <input type="text" name="homePhone" value="{homePhone }" maxlength="35" size="50"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <input type="text" name="homePhone" value="{$person/field[@name = 'homePhone']}" maxlength="35" size="50"/>
                    </xsl:otherwise>
                </xsl:choose>

            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
       </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Рабочий телефон:</xsl:with-param>
            <xsl:with-param name="description">Номер телефона указывается без 8, содержит 10 цифр.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="workPhone != ''">
                        <input type="text" name="workPhone" value="{workPhone}" maxlength="35" size="50"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <input type="text" name="workPhone" value="{$person/field[@name = 'jobPhone']}" maxlength="35" size="50"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
            <xsl:with-param name="description">Номер телефона указывается без 8, содержит 10 цифр.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="mobilePhone" value="{mobilePhone}" maxlength="35" size="50">
                    <xsl:if test="$formData/*[name()='mobilePhone']/@masked = 'true'">
                        <xsl:attribute name="class">masked-phone-number</xsl:attribute>
                    </xsl:if>
                </input>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">E-mail:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="email != ''">
                        <input type="text" name="email" value="{email}" maxlength="70" size="50"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <input type="text" name="email" value="{$person/field[@name = 'email']}" maxlength="70" size="50"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Желательная дата и время звонка от сотрудника банка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="freeTime" value="{freeTime}" maxlength="50" size="50"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

    </xsl:template>

	<xsl:template match="/form-data" mode="view">
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
            <xsl:with-param name="rowName">Кредитная карта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="creditCard"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="interest-rate"/> %</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">
                Годовое обслуживание<br/>Первый/последующие годы:
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="first-year-service"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(first-year-service-currency)"/> /
                <xsl:value-of  select="next-year-service"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(next-year-service-currency)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Кредитный лимит:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="mu:getFormatAmountWithNoCents(amount,'.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(currency)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
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
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Домашний телефон:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="mpnu:maskPhone(homePhone)"/></b>
            </xsl:with-param>                          
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Рабочий телефон:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="mpnu:maskPhone(workPhone)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="mpnu:maskPhone(mobilePhone)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">E-mail:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="email"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Желательная дата и время звонка от сотрудника банка:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="freeTime"/></b>
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

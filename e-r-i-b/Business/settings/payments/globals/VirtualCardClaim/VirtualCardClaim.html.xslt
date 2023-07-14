<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
        >
	<xsl:output method="html" version="1.0"  indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="data-path" select="''"/>

	<xsl:variable name="styleClass" select="'label120 LabelAll'"/>
	<xsl:variable name="styleClassTitle" select="'pmntInfAreaTitle'"/>
	<xsl:variable name="styleSpecial" select="''"/>

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
       <xsl:variable name="cardProducts" select="document('virtualCards.xml')"/>

       <script type="text/javascript">
           var jsonCardProduct = {};
           <xsl:for-each select="$cardProducts/entity-list/entity" >
                jsonCardProduct[<xsl:value-of select="field[@name = 'id']"/>] = {'name' : '<xsl:value-of select="sh:escapeStringForJavaScript(field[@name = 'name'])"/>', 'data' : [<xsl:value-of select="field[@name = 'kind']"/>] };       
           </xsl:for-each>
       </script>

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
            <xsl:with-param name="rowName">Вид карты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="сardProduct" id="сardProduct" value="{сardProduct}"/>
                <input type="hidden" name="kindCardProduct" id="kindCardProduct" value="{kindCardProduct}"/>
                <input type="hidden" name="subKindCardProduct" id="subKindCardProduct" value="{subKindCardProduct}"/>

                <xsl:variable name="cardProductIdOriginal" select="cardProductId"/>
                <select  name="cardProductId" id="cardProductId" style="width: 320px;" onchange="selectCardProduct(this);return false;">
                    <xsl:if test="string-length($cardProductIdOriginal) = 0">
                        <option value="">Выберите вид карты</option>
                    </xsl:if>
                    <xsl:for-each select="$cardProducts/entity-list/entity" >
                        <xsl:variable name="cardProductName" select="field[@name='name']/text()"/>
                        <xsl:variable name="cardProductId" select="field[@name='id']/text()"/>
                        <option>
                             <xsl:attribute name="value">
                                <xsl:value-of select="$cardProductId"/>
                            </xsl:attribute>
                            <xsl:if test="$cardProductIdOriginal = $cardProductId">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
                            <xsl:value-of select="$cardProductName"/>
                        </option>
                    </xsl:for-each>
                </select>
                &nbsp; &nbsp;
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="currencyNameCardProduct" id="currencyNameCardProduct" value="{currencyNameCardProduct}"/>
                <select  name="currencyCodeCardProduct" id="currencyCodeCardProduct" onchange="selectCurrency(this);"  style="width: 320px;">
                </select>
                 &nbsp; &nbsp;

            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="substring($person/field[@name = 'surName'], 1, 1)"/>.</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Имя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$person/field[@name = 'firstName']"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Отчество:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="$person/field[@name = 'patrName']"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
            <xsl:with-param name="description">Укажите номер телефона, к которому будет подключена услуга «Мобильный банк» для виртуальной карты.
                <cut/>Номер телефона указывается без 8, содержит 10 цифр.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="text" name="mobilePhone" value="{mobilePhone}" maxlength="10" size="50" class="masked-phone-number"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Оператор мобильной связи:</xsl:with-param>
            <xsl:with-param name="description">Выбранный оператор должен соответствовать указанному Вами  номеру телефона.</xsl:with-param>
            <xsl:with-param name="rowValue">
                <span id="mobileOperatorNameSpan" class="label">
                    <b><xsl:value-of  select="mobileOperator"/></b>
                </span>&nbsp;&nbsp;
                <a id="moblieOperatorLink" href="#" onclick="showMobileOperators();" class="depositProductInfoLink link">Выбрать из справочника</a>
                <input type="hidden" name="mobileOperator" id="mobileOperatorName" value="{mobileOperator}"/>
                <input type="hidden" name="codeMobileOperator" id="mobileOperatorCode" value="{codeMobileOperator}"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Тариф мобильного банка:</xsl:with-param>
            <xsl:with-param name="description">
                Экономный пакет – в результате операции по карте не приходит смс оповещение на  мобильный телефон.<br/>
                Полный пакет – в результате операции приходит смс оповещение на мобильный телефон.
            </xsl:with-param>
            <xsl:with-param name="rowValue">
                <select name="mobileTariff">
                        <option>
					        <xsl:attribute name="value">1</xsl:attribute>
					        <xsl:if test="mobileTariff = '1'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        Экономный пакет
				        </option>
				        <option>
					        <xsl:attribute name="value">2</xsl:attribute>
					        <xsl:if test="mobileTariff = '2'">
						        <xsl:attribute name="selected">true</xsl:attribute>
					        </xsl:if>
					        Полный пакет
				        </option>
                </select>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <script type="text/javascript">
        <![CDATA[

            function getCardProductElement()
            {
                return document.getElementById("сardProduct");
            }

            function setCurrencyCode(code)
            {
                document.getElementById("currencyNameCardProduct").value = code;
            }

            function blockSelectCurrency(selectCurrency)
            {
                selectCurrency.options.length = 0;

                getCardProductElement().value = "";
                document.getElementById("currencyNameCardProduct").value = "";
                document.getElementById("kindCardProduct").value = "";
                document.getElementById("subKindCardProduct").value = "";
            }

            function selectCardProduct(obj)
            {

                var selectCurrency = document.getElementById("currencyCodeCardProduct");

                var id = obj[obj.selectedIndex].value;
                var valueByKey = jsonCardProduct[id];
                if (isEmpty(valueByKey))
                {
                    blockSelectCurrency(selectCurrency);
                    return;
                }

                getCardProductElement().value = valueByKey['name'];

                var data = valueByKey['data'];

                if (isEmpty(data) || data.length <= 0)
                {
                    blockSelectCurrency(selectCurrency);
                    return;
                }

                selectCurrency.options.length = 0;
                for(var i = 0; i < data.length; i++)
                {
                    selectCurrency.options[i] = new Option(currencySignMap.get(data[i]['currency']['code']), data[i]['currency']['code']);

                }

                document.getElementById("kindCardProduct").value = data[0]['kind'];
                document.getElementById("subKindCardProduct").value = data[0]['subkind'];
                setCurrencyCode(data[0]['currency']['code']);

            }

            function setKindSubKind(code)
            {
                var selectTypeCard = document.getElementById("cardProductId");
                var id = selectTypeCard[selectTypeCard.selectedIndex].value;
                var valueByKey = jsonCardProduct[id];
                var data = valueByKey['data'];

                for(var i = 0; i < data.length; i++)
                {
                    if(code == data[i]['currency']['code'])
                    {
                        document.getElementById("kindCardProduct").value = data[i]['kind'];
                        document.getElementById("subKindCardProduct").value = data[i]['subkind'];
                        break;
                    }
                }
            }

            function selectCurrency(obj)
            {
                if (obj == null)
                    return;

                var currencyCode= obj[obj.selectedIndex].value;
                setCurrencyCode(currencyCode);
                setKindSubKind(currencyCode);
            }

            function setCurrencyNumber(number)
            {
                var currencies = document.getElementById("currencyCodeCardProduct");
                for(var i = 0; i < currencies.options.length; i++)
                 {
                    if (currencies.options[i].value == number)
                    {
                        currencies.selectedIndex = i;
                        selectCurrency(currencies);
                        break;
                    }
                 }
            }


            // заполняем поля формы по мобильному оператору, выбранному в справочнике
            function mobileOperatorClick(name, code)
            {
                document.getElementById("mobileOperatorNameSpan").innerHTML = name;
                document.getElementById("mobileOperatorName").value = name;
                document.getElementById("mobileOperatorCode").value = code;

                win.close('mobileOperatorId');
            }

            selectCardProduct(document.getElementById("cardProductId"));

         ]]>
            
         setCurrencyNumber('<xsl:value-of select="currencyCodeCardProduct"/>');
         document.getElementById("mobileOperatorNameSpan").innerHTML = document.getElementById("mobileOperatorName").value;

        </script>
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
                <xsl:choose>
                    <xsl:when test="state = 'ADOPTED'">
                        <b><xsl:value-of select="operationDate"/></b>
                    </xsl:when>
                    <xsl:otherwise>
                        <b><xsl:value-of select="creationDate"/></b>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Вид карты:</xsl:with-param>
            <xsl:with-param name="rowValue">
                 <b><xsl:value-of  select="сardProduct"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Валюта:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="mu:getCurrencySign(currencyNameCardProduct)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="substring(surName, 1, 1)"/>.</b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Имя:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="firstName"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

       <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Отчество:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="patrName"/></b>
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
            <xsl:with-param name="rowName">Оператор мобильной связи:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="mobileOperator"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'true'"/>
            <xsl:with-param name="rowName">Тариф мобильного банка:</xsl:with-param>
            <xsl:with-param name="rowValue">
				<xsl:if test="mobileTariff = '1'">
					<b>Экономный пакет</b>
				</xsl:if>
				<xsl:if test="mobileTariff = '2'">
					<b>Полный пакет</b>
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
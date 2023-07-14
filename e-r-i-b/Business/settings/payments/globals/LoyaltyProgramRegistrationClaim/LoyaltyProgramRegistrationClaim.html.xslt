<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan = "http://xml.apache.org/xalan"
                xmlns:lh = "java://com.rssl.phizic.config.loyalty.LoyaltyHelper">
	<xsl:output method="html" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

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
        <script>
            <![CDATA[
                var AGREEMENT_MESSAGE = "Для регистрации в Программе ознакомьтесь и выразите свое согласие с Правилами Программы «Спасибо от Сбербанка»";
                $("#warnings").addClass("alignCenter");
                function checkPayment()
                {
                    var checked = getElement('agreeForAll').checked;
                    if (!checked)
                    {
                        removeAllMessages();
                        removeAllErrors();
                        $("#payment .form-row").each(function(){
                            payInput.formRowClearError(this);
                        });
                        addMessage(AGREEMENT_MESSAGE);
                        payInput.fieldError("agreeForAll")
                        window.scrollTo(0,0);
                    }
                    else
                        removeMessage(AGREEMENT_MESSAGE);
                    return checked;
                }

                function openLoyaltyInfo()
                {
                    window.open('http://www.spasibosberbank.ru/disclaimer/',   'loyaltyInfo', 'menubar=1,toolbar=0,scrollbars=1');
                    var agreeForAll = ensureElement('agreeForAll');
                    if (agreeForAll != null)
                    {
                        agreeForAll.disabled = false;
                    }
                }
            ]]>
        </script>
        <xsl:variable name="phoneList" select="document('loyalty_phones.xml')/entity-list/*"/>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="documentNumber"/></b>
                <input id="documentNumber" name="documentNumber" type="hidden" value="{documentNumber}"/>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер телефона:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <xsl:variable name="phone" select="phone"/>
                <xsl:choose>
                    <xsl:when test="count($phoneList) = 0">
                        <select id="phone" name="phone" >
                            <option value="">Нет доступных телефонов</option>
                        </select>
                    </xsl:when>
                    <xsl:when test="count($phoneList) = 1">
                        <b><xsl:value-of select="lh:formatPhone($phoneList)" disable-output-escaping="yes"/></b>
                        <input id="phone" name="phone" type="hidden" value="{$phoneList/@key}"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <select id="phone" name="phone" >
                            <option value="">Выберите номер телефона</option>
                            <xsl:for-each select="$phoneList">
                                <option>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="./@key"/>
                                    </xsl:attribute>
                                    <xsl:if test="$phone = ./@key">
                                        <xsl:attribute name="selected">true</xsl:attribute>
                                    </xsl:if>
                                    <span><xsl:value-of select="lh:formatPhone(./field[@name='value'])" disable-output-escaping="yes"/></span>
                                </option>
                             </xsl:for-each>
                        </select>
                    </xsl:otherwise>
                </xsl:choose>
			</xsl:with-param>
            <xsl:with-param name="description">
                Если Вы хотите подключить бонусную программу к номеру телефона, которого нет в списке, то подключите нужный номер к услуге Мобильный банк
            </xsl:with-param>
		</xsl:call-template>

       <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Электронная почта:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <input id="email" name="email" value="{email}" type="text" size="46" maxlength="100"/>
			</xsl:with-param>
		</xsl:call-template>

        <div class="loyaltySeparate"></div>

         <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName"/>
            <xsl:with-param name="rowValue">
                 <xsl:choose>
                    <xsl:when test="agreeForAll = 'true'">
                        <input type="checkbox" id="agreeForAll"  name="agreeForAll" value="true" class="vertical-middle float"  checked="true"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <input type="checkbox" id="agreeForAll"  name="agreeForAll" value="true" class="vertical-middle float" disabled="true"/>
                    </xsl:otherwise>
                </xsl:choose>
                &nbsp; <div class="float conditionText">С <span class="loyaltyInfo"><a class="orangeText" href="#" onclick="openLoyaltyInfo()"><span>Правилами  программы «Спасибо от Сбербанка»</span><span class="loyaltyInfoIcon"></span></a></span><br/> ознакомлен и согласен</div>
                <div class="clear"></div>
                Ознакомьтесь с Правилами программы «Спасибо от Сбербанка», щелкнув по ссылке. Затем установите флажок в поле «С Правилами Программы «Спасибо от Сбербанка» ознакомлен и согласен» и нажмите на кнопку «Зарегистрироваться».
            </xsl:with-param>
        </xsl:call-template>

    </xsl:template>

    <xsl:template match="/form-data" mode="view">

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="documentNumber"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Номер телефона:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><xsl:value-of  select="lh:formatPhone(phone)" disable-output-escaping="yes"/></b>
			</xsl:with-param>
		</xsl:call-template>

       <xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">Электронная почта:</xsl:with-param>
			<xsl:with-param name="rowValue">
                <b><xsl:value-of  select="email"/></b>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Дата исполнения:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of  select="documentDate"/></b>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName">Статус</xsl:with-param>
            <xsl:with-param name="rowValue">
                <div id="state">
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
                </div>
            </xsl:with-param>
        </xsl:call-template>

    </xsl:template>

	<xsl:template name="employeeState2text">
		<xsl:param name="code"/>
		<xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
		</xsl:choose>
	</xsl:template>
    <xsl:template name="clientState2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='SAVED'">Черновик</xsl:when>
			<xsl:when test="$code='INITIAL'">Черновик</xsl:when>
			<xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
		</xsl:choose>
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
    <xsl:param name="isAllocate" select="'true'"/>  <!-- Выделить при нажатии -->
    <!-- Необязательный параметр -->
	<xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->

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

    <xsl:template name="titleRow">
        <xsl:param name="lineId"/>
        <xsl:param name="rowName"/>
        <div id="{$lineId}" class="{$styleClassTitle}">
            <xsl:copy-of select="$rowName"/>
        </div>
    </xsl:template>

</xsl:stylesheet>

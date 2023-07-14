<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp  "&#160;">
        <!ENTITY mdash "&#8212;">
        <!ENTITY ensp  "&#8194;">
        ]>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:pu="java://com.rssl.phizic.security.PermissionUtil">

    <xsl:output method="html" version="1.0"  indent="yes"/>

    <xsl:decimal-format name="amountFormat" decimal-separator="," grouping-separator=" "/>

	<xsl:param name="mode"         select="'edit'"/>
	<xsl:param name="webRoot"      select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>

    <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
  	<xsl:variable name="styleClass"    select="'label120 LabelAll'"/>

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
        <xsl:call-template name="shortLoanHeader">
            <xsl:with-param name="showExtended" select="onlyShortClaim = 'false' and pu:impliesService('ExtendedLoanClaim')"/>
        </xsl:call-template>


        <xsl:call-template name="shortUserInfo"/>

        <xsl:call-template name="loanOffersClaimTerms">
            <xsl:with-param name="element" select="'popupTerms'"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:call-template name="loanOffersClaimTerms">
            <xsl:with-param name="element" select="'popupTerms'"/>
        </xsl:call-template>

        <xsl:call-template name="shortUserInfo"/>
        <script type="text/javascript">
            $(document).ready(function() {
                $(".forBankStamp").addClass("forLoanStamp");
            });
        </script>
    </xsl:template>


    <xsl:template name="shortLoanHeader">
        <xsl:param name="showExtended" select="'true'"/>

        <script type="text/javascript">
            doOnLoad(function()
            {

                $(".pointerBlock").hover(
                   function()
                   {
                       $(".bigPointerHidden").css("display","block");
                   },
                   function()
                   {
                       $(".bigPointerHidden").css("display","none");
                   });

                $(".showPointer, .hintLinks a, .searchBlockList li").hover(
                   function()
                   {
                       $(this).addClass("needLightHover");
                   },
                   function()
                   {
                       $(this).removeClass("needLightHover");
                });
            });
            function clearPhoneNumber(phoneNumberRow)
            {
                phoneNumberRow.value='';
            }
        </script>

        <input name="loanOfferId"        type="hidden" value="{loanOfferId}"/>
        <input name="condId"             type="hidden" value="{condId}"/>
        <input name="condCurrId"         type="hidden" value="{condCurrId}"/>
        <input name="loanPeriod"         type="hidden" value="{loanPeriod}"/>
        <input name="loanAmount"         type="hidden" value="{loanAmount}"/>
        <input name="loanCurrency"       type="hidden" value="{loanCurrency}"/>
        <input name="productName"        type="hidden" value="{productName}"/>
        <input name="currMinPercentRate" type="hidden" value="{currMinPercentRate}"/>
        <input name="currMaxPercentRate" type="hidden" value="{currMaxPercentRate}"/>
        <input name="loanRate"           type="hidden" value="{loanRate}"/>

    </xsl:template>

    <xsl:template name="shortUserInfo">
        <xsl:call-template name="titleRow">
            <xsl:with-param name="rowName" select="'Краткая информация о Вас'"/>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
            <xsl:with-param name="rowName"    select="'ФИО:'"/>
            <xsl:with-param name="required"   select="false()"/>
            <xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="$mode = 'edit'">
                            <xsl:variable name="userFullName" select="$currentPerson/entity/field[@name='formatedPersonName']"/>
                            <b class="float">
                                <span>
                                    <xsl:value-of select="$userFullName"/>
                                </span>
                                <input name="fio" type="hidden" value="{$userFullName}"/>
                            </b>
                            <div class="simpleHintBlock">
                                <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                                <div class="clear"></div>
                                <div class="layerFon simpleHint">
                                    <div class="floatMessageHeader"></div>
                                    <div class="layerFonBlock"> В целях безопасности Ваши личные данные маскированы </div>
                                </div>
                            </div>
                        </xsl:when>
                        <xsl:otherwise>
                            <b class="float">
                                <span><xsl:value-of select="fio"/></span>
                            </b>
                        </xsl:otherwise>
                    </xsl:choose>
            </xsl:with-param>
        </xsl:call-template>

    	<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName"  select="'Место работы:'"/>
			<xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="$mode = 'edit'">
                            <input name="jobLocation" type="text" value="{jobLocation}" size="50"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <span><xsl:value-of select="jobLocation"/></span>
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
			</xsl:with-param>
		</xsl:call-template>

    	<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName"  select="'Средний доход в месяц:'"/>
			<xsl:with-param name="rowValue">
                    <xsl:choose>
                        <xsl:when test="$mode = 'edit'">
                            <input name="incomePerMonth" type="text" value="{incomePerMonth}" size="20"/> &nbsp;руб.
                        </xsl:when>
                        <xsl:otherwise>
                            <span><xsl:value-of select="format-number(incomePerMonth, '### ###,##', 'amountFormat')"/>&nbsp;руб.</span>
                        </xsl:otherwise>
                    </xsl:choose>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:variable name="formData" select="/form-data"/>

    	<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName"     select="'Контактный телефон:'"/>
            <xsl:with-param name="description" select="'Номер телефона указывается без 8, содержит 10 цифр.'"/>
			<xsl:with-param name="rowValue">
                <b>
                    <xsl:choose>
                        <xsl:when test="$mode = 'edit'">
                            <input type="text" name="phoneNumber" value="{phoneNumber}" maxlength="10" size="20">
                                <xsl:if test="$formData/*[name()='phoneNumber']/@masked = 'true'">
                                    <xsl:attribute name="class">masked-phone-number</xsl:attribute>
                                </xsl:if>
                            </input>
                        </xsl:when>
                        <xsl:otherwise>
                            <span><xsl:value-of select="phoneNumber"/></span>
                        </xsl:otherwise>
                    </xsl:choose>
                </b>
			</xsl:with-param>
		</xsl:call-template>

    	<xsl:call-template name="standartRow">
			<xsl:with-param name="rowName">&nbsp;</xsl:with-param>
            <xsl:with-param name="required" select="false()"/>
			<xsl:with-param name="rowValue">
                <xsl:choose>
                        <xsl:when test="$mode = 'edit'">
                            <xsl:choose>
                                <xsl:when test="processingEnabled = 'true'">
                                    <input name="processingEnabled" type="checkbox" checked="checked" onclick="onProcessingEnabledClick(this);"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <input name="processingEnabled" type="checkbox" onclick="onProcessingEnabledClick(this);"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            &nbsp;<span>Да, я согласен на <a class="orangeText" id="popupTerms" href="#"><span>обработку персональных данных</span></a></span>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:choose>
                                <xsl:when test="processingEnabled = 'true'">
                                    <input name="processingEnabled" type="checkbox" checked="checked" disabled="true"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <input name="processingEnabled" type="checkbox"  disabled="true"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            &nbsp;<span>Да, я согласен на обработку персональных данных</span>
                        </xsl:otherwise>
                </xsl:choose>
			</xsl:with-param>
		</xsl:call-template>

        <xsl:if test="$mode = 'view'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName"  select="'Статус заявки:'"/>
                <xsl:with-param name="rowValue">
                    <div id="state">
                        <span onmouseover="showLayer('state','stateDescription','hand');" onmouseout="hideLayer('stateDescription');" class="link">
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
        </xsl:if>
    </xsl:template>

    <xsl:template name="loanOffersClaimTerms">
        <xsl:param name="element" select="''"/>

        <xsl:variable name="loanOffersTerm" select="document('loan-offers-terms.xml')/entity-list/entity/field/text()"/>

        <script type="text/javascript">
            doOnLoad(function() {
                $('#<xsl:value-of select="$element"/>').click(function() {

                    openPopupTerms();
                    return false;
                });
                var state = '<xsl:value-of select="state"/>';
                if (state == 'DISPATCHED')
                    addMessage('Спасибо! Мы приняли Вашу заявку. Наш сотрудник свяжется с Вами в ближайшее время и проконсультирует по дальнейшим шагам');
            });

            function openPopupTerms()
            {
                var width   = 900;
                var height  = 600;
                var top     = (screen.height - height) /2;
                var left    = (screen.width  - width)  /2;

                var options = 'height=' + height + ', width=' + width + ',top=' + top + ', left=' + left + 'directories=0, titlebar=0, toolbar=0, location=0, status=0, menubar=0';

                var win = window.open('', 'terms', options, false);
                    win.document.write(document.getElementById('popupTermsWindowDiv').innerHTML);
                    win.document.close();
            }

            function onProcessingEnabledClick(element)
            {
                element.value = element.checked;
            }
        </script>

        <div id="popupTermsWindowDiv" style="display: none;">
            <div style="margin: 50px">
                <h1>Обработка персональных данных</h1>

                <p>
                    <xsl:value-of select="$loanOffersTerm"/>
                </p>

                <div style="text-align: center; margin-top: 50px">
                    <a href="#" onclick="window.close();">Закрыть</a>
                </div>
            </div>
        </div>
    </xsl:template>

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
            <![CDATA[</div>]]>
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
						<xsl:copy/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<xsl:if test="count($nodeText/cut) &gt; 0">
				<xsl:value-of select="$end" disable-output-escaping="yes"/>
			</xsl:if>
		</div>
	</xsl:template>

	<xsl:template name="standartRow">
		<xsl:param name="id"/>
        <!--идентификатор строки-->
		<xsl:param name="lineId"/>
        <!--параметр обязатьльности заполнения-->
        <xsl:param name="required" select="'true'"/>
        <!--описание поля-->
        <xsl:param name="rowName"/>
        <!--данные-->
        <xsl:param name="rowValue"/>
        <!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="description"/>
        <!-- Стиль поля -->
        <xsl:param name="rowStyle"/>
        <!-- Выделить при нажатии -->
        <!-- Необязательный параметр -->
        <xsl:param name="isAllocate" select="'true'"/>
        <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="fieldName"/>

        <xsl:variable name="nodeRowValue" select="xalan:nodeset($rowValue)"/>
        <!-- Определение имени инпута или селекта передаваемого в шаблон -->
        <!-- inputName - fieldName или имя поле вытащеное из rowValue -->
        <xsl:variable name="inputName">
			<xsl:choose>
				<xsl:when test="string-length($fieldName) = 0">
					<xsl:if test="(count($nodeRowValue/input[@name]) + count($nodeRowValue/select[@name]) + count($nodeRowValue/textarea[@name])) = 1">
						<xsl:value-of select="$nodeRowValue/input/@name"/>
						<xsl:if test="count($nodeRowValue/select[@name]) = 1">
							<xsl:value-of select="$nodeRowValue/select/@name"/>
						</xsl:if>
						<xsl:if test="count($nodeRowValue/textarea[@name]) = 1">
							<xsl:value-of select="$nodeRowValue/textarea/@name"/>
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
				<xsl:when test="string-length($inputName)&gt;0">
					<xsl:value-of
                            select="count($nodeRowValue/input[@name=$inputName]/@readonly ) + count($nodeRowValue/select[@name=$inputName]/@readonly )+ count($nodeRowValue/textarea[@name=$inputName]/@readonly )"/>
				</xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
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
			<xsl:if test="string-length($lineId) &gt; 0">
				<xsl:attribute name="id">
					<xsl:copy-of select="$lineId"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="string-length($rowStyle) &gt; 0">
				<xsl:attribute name="style">
					<xsl:copy-of select="$rowStyle"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$readonly = 0 and $mode = 'edit' and $isAllocate='true'">
				<xsl:attribute name="onclick">payInput.onClick(this)</xsl:attribute>
			</xsl:if>

			<div class="paymentLabel">
				<span class="paymentTextLabel">
					<xsl:if test="string-length($id) &gt; 0">
						<xsl:attribute name="id">
							<xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
					</xsl:if>
					<xsl:copy-of select="$rowName"/>
				</span>
				<xsl:if test="$required = 'true' and $mode = 'edit'">
					<span id="asterisk_{$id}" class="asterisk">*</span>
				</xsl:if>
			</div>
			<div class="paymentValue">
				<div class="paymentInputDiv">
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
        <xsl:if test="string-length($inputName) &gt; 0 and $readonly = 0 and $mode = 'edit'">
			<script type="text/javascript">
                if (document.getElementsByName('<xsl:value-of select="$inputName"/>').length == 1)
                {
        		    document.getElementsByName('<xsl:value-of select="$inputName"/>')[0].onfocus = function() { payInput.onFocus(this); };
                }
            </script>
		</xsl:if>
	</xsl:template>

    <xsl:template name="titleRow">
		<xsl:param name="rowName"/>
		<div class="rowTitles">
			<xsl:copy-of select="$rowName"/>
		</div>
	</xsl:template>

	 <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='ADOPTED'">Принята (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='ADOPTED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:dvu="java://com.rssl.phizic.web.util.DepartmentViewUtil"
                xmlns:sh="java://com.rssl.phizic.utils.StringHelper"
                xmlns:pu="java://com.rssl.phizic.web.util.PersonInfoUtil"
                xmlns:lch="java://com.rssl.phizic.business.loanCardClaim.LoanCardClaimHelper"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:set="http://exslt.org/sets"
                exclude-result-prefixes="set"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:gHelper="java://com.rssl.phizic.business.login.GuestHelper">

    <xsl:import href="commonTypes.html.template.xslt"/>

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
    <xsl:param name="isGuest" select="isGuest"/>

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
        <xsl:variable name="products"   select="document(concat('loan-card-claim-source.xml?offerId=', offerId, '&amp;preapproved=', preapproved))"/>
        <xsl:variable name="personData" select="document('currentPersonData.xml')/entity-list/entity"/>
        <script type="text/javascript">

            doOnLoad(
                function(){
                    $('[name="regionId"]').val("<xsl:value-of select="pu:getPersonRegionId()"/>");
                });

            var amountSliderConfig =
            {
                formatting:
                {
                    enabled            : true,
                    decimalSeparator   : ',',
                    thousandsSeparator : ' ',
                    handler            : formatFieldsByClassname,
                    field              : 'formattedField'
                }
            };

            var amountSlider = new ValueSlider().extendConfiguration(amountSliderConfig);
            var cardProducts = new Array();
            var cardProduct;
			<xsl:for-each select="$products/entity-list/entity">
                <xsl:variable name="period" select="field[@name='period']/text()"/>
                <xsl:variable name="interestRateGrace" select="field[@name='interestRate']/text()"/>
                cardProduct      = new Object()
                cardProduct.id   = '<xsl:value-of select="field[@name='id']"/>';
                cardProduct.name = '<xsl:value-of select="field[@name='name']/text()"/>';
                <xsl:choose>
                    <xsl:when test="string-length($period)>0 and string-length($interestRateGrace)>0">
                        cardProduct.period            = '<xsl:value-of select="field[@name='period']/text()"/>';
                        cardProduct.interestRateGrace = '<xsl:value-of select="field[@name='interestRate']/text()"/>';
                    </xsl:when>
                    <xsl:otherwise>
                        cardProduct.period = '0';
                        cardProduct.interestRateGrace = '0';
                    </xsl:otherwise>
                </xsl:choose>
                cardProduct.additionalTerms = '<xsl:value-of select="sh:formatStringForJavaScript(field[@name='additionalTerms']/text(), false())"/>';
                cardProduct.minLimit        = '<xsl:value-of select="field[@name='minLimit']/text()"/>';
                cardProduct.maxLimit        = '<xsl:value-of select="field[@name='maxLimit']/text()"/>';
                cardProduct.rate            = '<xsl:value-of select="field[@name='rate']/text()"/>';
                cardProduct.firstYear       = '<xsl:value-of select="field[@name='firstYear']/text()"/>';
                cardProduct.nextYear        = '<xsl:value-of select="field[@name='nextYear']/text()"/>';
                cardProduct.currency        = '<xsl:value-of select="field[@name='currency']/text()"/>';
                cardProduct.currencySign    = '<xsl:value-of select="mu:getCurrencySign(field[@name='currency']/text())"/>';
                cardProduct.offerId         = '<xsl:value-of select="field[@name='offerId']/text()"/>';
                cardProduct.cardTypeCode    = '<xsl:value-of select="field[@name='cardTypeCode']/text()"/>';
                cardProduct.offerType       = '<xsl:value-of select="field[@name='offerType']/text()"/>';
                cardProduct.cardNumber      = '<xsl:value-of select="field[@name='cardNumber']/text()"/>';

                cardProducts[cardProduct.id] = cardProduct;
			</xsl:for-each>

            $(document).ready(function(){
                amountSlider.bind2( document.getElementById('amountSlider') );

                refreshCardProduct();
                <xsl:if test="string-length(creditCard)>0">
                    var cardProduct = cardProducts[<xsl:value-of select="loan"/>];
                    $("#additionalTermsText").html(cardProduct.additionalTerms);
                    $("#amount").val("<xsl:value-of select="amount"/>");
                    $("#credit-card-office").val("<xsl:value-of select="credit-card-office"/>");
                    <xsl:if test="string-length(credit-card-office)>0">
                        $("#credit-card-office-link").text("<xsl:value-of select="credit-card-office"/>");
                    </xsl:if>
                    $("#tb").val("<xsl:value-of select="tb"/>");
                    $("#osb").val("<xsl:value-of select="osb"/>");
                    $("#vsp").val("<xsl:value-of select="vsp"/>");
                    $("#offerId").val(cardProduct.offerId);
                    refreshGettingDate("<xsl:value-of select="tb"/>");
                    document.getElementById('showTerms').style.display = (cardProduct.additionalTerms.length>0)?"inline-block":"none";
                </xsl:if>
            });

            function refreshCardProduct()
            {
                var creditCardTypeSelect = ensureElement("creditCardTypeSelect").value;
                var cardProduct  = cardProducts[creditCardTypeSelect];
                if (cardProduct != undefined)
                {
                    var amountFormat = {decimalSeparator : ',', thousandSeparator: ' ', numberOfDecimals: 0, showDecimalsIfEmpty: true};
                    var creditFormat = {decimalSeparator : ',', thousandSeparator: ' ', numberOfDecimals: 2, showDecimalsIfEmpty: false};

                    $("#period").text(cardProduct.period);
                    $("#interestRateGrace").text(cardProduct.interestRateGrace);
                    document.getElementById('showTerms').style.display = (cardProduct.additionalTerms.length>0)?"":"none";
                    $("#additionalTermsText").html(cardProduct.additionalTerms);

                    amountSlider.extendValues({minimum: parseFloat(cardProduct.minLimit), maximum: parseFloat(cardProduct.maxLimit)});

                    $("#amount").val(cardProduct.maxLimit);
                    $("#rate").text(cardProduct.rate+" %");
                    $("#currencySlider").text(cardProduct.currencySign);
                    $("#limitCurrent").text(cardProduct.currencySign);
                    $("#currency").val(cardProduct.currency);
                    $("#creditCard").val(cardProduct.name);
                    $("#loan").val(cardProduct.id);
                    $("#interest-rate").val(cardProduct.rate);
                    $("#first-year-service").val(cardProduct.firstYear);
                    $("#next-year-service").val(cardProduct.nextYear);
                    $("#grace-period-interest-rate").val(cardProduct.interestRateGrace);
                    $("#grace-period-duration").val(cardProduct.period);
                    $("#additionalTerms").val(cardProduct.additionalTerms);
                    $("#cardTypeCode").val(cardProduct.cardTypeCode);
                    $("#offerType").val(cardProduct.offerType);
                    $("#cardNumber").val(cardProduct.cardNumber);

                    $("#yearNext").text ( $().number_format(cardProduct.nextYear,  amountFormat) + "&nbsp;" + cardProduct.currencySign);
                    $("#yearFirst").text( $().number_format(cardProduct.firstYear, amountFormat) + "&nbsp;" + cardProduct.currencySign);
                    $("#maxLimitValue").text( $().number_format(cardProduct.maxLimit,  creditFormat));
                    $("#productMaxSum").text( $().number_format(cardProduct.maxLimit,  creditFormat));
                }
            }

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

            function showDepartmetsList()
           {
                var regionId = $('[name="regionId"]').val();
                var win  =  window.open("/PhizIC/private/credit-card-office/list.do?regionId="+regionId, 'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1050, height=700");
	            win.moveTo(screen.width / 2 - 410, screen.height / 2 - 325);
                return false;
           };

            function setCreditCardOfficeInfo(info)
           {
               setElement('credit-card-office', info["name"]+', '+info["address"]);
               document.getElementById("credit-card-office-link").innerHTML =info["name"]+', '+info["address"];
               setElement('tb', info["tb"]);
               setElement('osb', info["osb"]);
               setElement('vsp', info["vsp"]);
               setElement('regionId', info["regionId"]);
               refreshGettingDate(info["tb"]);
           };

             function refreshGettingDate(tb)
           {
               if (tb == '99' || tb == '38')
                 $('#gettingDate')[0].innerHTML = "5 рабочих дней";
               else if (tb != '')
                 $('#gettingDate')[0].innerHTML = "5-10 рабочих дней";
           };
        </script>
        <xsl:variable name="offerId" select="offerId"/>
        <xsl:variable name="hasPreapprovedClaim" select="string-length($offerId)>0"/>
        <xsl:variable name="descriptionCardsLink" select="lch:detailedDescriptionOfCardsLink()"/>
        <xsl:variable name="hasPhoneInMB" select="ph:isMBConnected()"/>
        <xsl:if test="$offerId =''">
            <a href="{$descriptionCardsLink}" class="orangeText" target="_blank"><span>Подробное описание карт на сайте Сбербанка</span><span class="offerInfoIcon"></span></a>
        </xsl:if>
        <div class="grayContentBlock allWidthBlock">
            <xsl:if test="$offerId !=''">
                <div class="preapproverLoanCardClaimIcon">
                    <div class="size16">Вам одобрена кредитная карта с лимитом до <span id="productMaxSum" class="bold"></span></div><br/>
                    <span class="paymentLabel" style="float:none">Для получения карты просто укажите отделение Сбербанка, где Вам удобнее ее забрать.</span>
                </div>
                <div class="dashedLine"></div>
            </xsl:if>
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Кредитная карта:</xsl:with-param>
                <xsl:with-param name="useValueStyle" select="'false'"/>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="cardId"><xsl:value-of select="loan"/></xsl:variable>
                    <div class="float">
                        <select id="creditCardTypeSelect" name="creditCardTypeSelect" onchange="refreshCardProduct();" style="width:340px;">
                            <xsl:for-each select="$products/entity-list/*">
                                <xsl:variable name="currentCardId">
                                    <xsl:value-of select="./field[@name='id']"/>
                                </xsl:variable>
                                <option>
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="$currentCardId"/>
                                    </xsl:attribute>
                                    <xsl:choose>
                                        <xsl:when test="string-length($cardId)>0">
                                            <xsl:if test="$cardId = $currentCardId">
                                                <xsl:attribute name="selected">true</xsl:attribute>
                                            </xsl:if>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:if test="./field[@name='default'] = 'true'">
                                                <xsl:attribute name="selected">true</xsl:attribute>
                                            </xsl:if>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:value-of select="./field[@name='name']"/>
                                </option>
                            </xsl:for-each>
                        </select>
                    </div>
                    <xsl:if test="$offerId != ''">
                        <div class="simpleHintBlock float">
                            <a class="imgHintBlock imageWidthText simpleHintLabel" onclick="return false;"></a>
                            <div class="clear"></div>
                            <div class="layerFon simpleHint">
                                <div class="floatMessageHeader"></div>
                                <div class="layerFonBlock"> Список карт с уже одобренным лимитом, которые можно заказать через сайт, ограничен. Если вы хотите оформить другую карту - обратитесь в ближайшее отделение Сбербанка с паспортом. </div>
                            </div>
                        </div>
                    </xsl:if>
                    <input type="hidden" name="creditCard"   id="creditCard"/>
                    <input type="hidden" name="loan"         id="loan"/>
                    <input type="hidden" name="offerId"      id="offerId"      value="{offerId}"/>
                    <input type="hidden" name="cardTypeCode" id="cardTypeCode" value="{cardTypeCode}"/>
                    <input type="hidden" name="offerType"    id="offerType"    value="{offerType}"/>
                    <input type="hidden" name="cardNumber"   id="cardNumber"   value="{cardNumber}"/>
                    <input type="hidden" name="preapproved"  value="{preapproved}"/>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="conditionsRow">
                <xsl:with-param name="rowName">Условия и преимущества:</xsl:with-param>
            </xsl:call-template>
        </div>
        <div class="clear"></div>
        <div id="cardParams">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">
                    Кредитный лимит:<br/>
                    <span class="maxValue">до <span id="maxLimitValue"></span></span>
                </xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="amountSlider">
                        <div class="scrollInputSmall">
                            <input type="text" class="formattedField fakeSliderInput"/> &nbsp;<span id="limitCurrent"></span>
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

                        <input name="amount" type="hidden" class="sliderInput"/>
                    </div>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
                <xsl:with-param name="rowValue"><div id="rate"/></xsl:with-param>
            </xsl:call-template>


            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Годовое обслуживание:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div id="yearNext"/>
                    Первый год - <span id="yearFirst"></span>
                </xsl:with-param>
            </xsl:call-template>

            <xsl:if test="$offerId!=''">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Срок выпуска карты:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                         <div id="gettingDate"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>
            <input type="hidden" name="currency" id="currency"/>
            <input type="hidden" name="interest-rate" id="interest-rate"/>
            <input type="hidden" id="next-year-service" name="next-year-service"/>
            <input type="hidden" id="first-year-service" name="first-year-service"/>
        </div>

        <div id="additionalInfo" class="additionalInfoLoan">
            <div class="{$styleClassTitle} rowTitle18">Краткая информация обо мне</div>
            <xsl:variable name="firstName" select="$personData/field[@name = 'firstName']"/>
            <xsl:variable name="patrName"  select="$personData/field[@name = 'patrName']"/>
            <xsl:variable name="surName"   select="$personData/field[@name = 'surName']"/>
            <xsl:variable name="birthDay" select="$personData/field[@name = 'birthDay']"/>
            <xsl:variable name="passportSeries" select="$personData/field[@name = 'REGULAR_PASSPORT_RF-series']"/>
            <xsl:variable name="passportNumber" select="$personData/field[@name = 'REGULAR_PASSPORT_RF-number']"/>
            <!--Даем редактировать ФИО только гостю, у которого нет МБ и ПП -->
            <xsl:choose>
                <xsl:when test="$isGuest and not($hasPhoneInMB) and not($hasPreapprovedClaim)">
                     <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="id" select="'surNameRow'"/>
                        <xsl:with-param name="lineId" select="'surNameRow'"/>
                        <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input name="surName" type="text" size="20" maxlength="20" value="{surName}"/>
                        </xsl:with-param>
                    </xsl:call-template>

                    <xsl:call-template name="standartRow">
                                 <xsl:with-param name="required" select="'true'"/>
                                 <xsl:with-param name="id" select="'firstNameRow'"/>
                                 <xsl:with-param name="lineId" select="'firstNameRow'"/>
                                 <xsl:with-param name="rowName">Имя:</xsl:with-param>
                                 <xsl:with-param name="rowValue">
                                     <input name="firstName" type="text" size="20" maxlength="20" value="{firstName}"/>
                                 </xsl:with-param>
                             </xsl:call-template>

                    <xsl:call-template name="standartRow">
                         <xsl:with-param name="required" select="'false'"/>
                         <xsl:with-param name="id" select="'patrNameRow'"/>
                         <xsl:with-param name="lineId" select="'patrNameRow'"/>
                         <xsl:with-param name="rowName">Отчество:</xsl:with-param>
                         <xsl:with-param name="rowValue">
                             <input name="patrName" type="text" size="20" maxlength="20" value="{patrName}"/>
                         </xsl:with-param>
                     </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                        <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">ФИО:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <b class="float">
                                <xsl:value-of select="ph:getFormattedPersonFIO($firstName, $surName, $patrName)"/>
                            </b>
                            <input type="hidden" name="firstName" value="{$firstName}"/>
                            <input type="hidden" name="patrName"  value="{$patrName}"/>
                            <input type="hidden" name="surName"   value="{$surName}"/>
                        </xsl:with-param>
                        <xsl:with-param name="isAllocate" select="'false'"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>

            <!--Поле мобильный телефон отображаем в любом случае-->
            <xsl:variable name="mobilePhone" select="$personData/field[@name = 'mobilePhone']"/>
            <xsl:choose>
                <xsl:when test="string-length($mobilePhone) > 0 and canEditMobilePhone = 'false'">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <b>+<xsl:value-of select="mask:maskPhoneNumber($mobilePhone)"/></b>
                            </xsl:with-param>
                            <xsl:with-param name="required" select="'false'"/>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                        </xsl:call-template>
                    <input type="hidden" name="canEditMobilePhone" value="{false()}"/>
                     <xsl:if test="not($isGuest)">
                        <input type="hidden" name="mobilePhone" value="{mobilePhone}"/>
                     </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="not($isGuest)">
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
                            <xsl:with-param name="rowValue">
                                <input name="mobilePhone" type="text" size="20" maxlength="20" value="{mobilePhone}"/>
                            </xsl:with-param>
                            <xsl:with-param name="required" select="'true'"/>
                            <xsl:with-param name="isAllocate" select="'false'"/>
                            <xsl:with-param name="description">Укажите номер телефона – ровно 10 цифр. Например, 9115108989.</xsl:with-param>
                        </xsl:call-template>
                        <input type="hidden" name="canEditMobilePhone" value="{true()}"/>
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>

            <!--Поля "дата рождения" и "номер и серия паспорта" отображаем только гостям-->
            <xsl:choose>
                <xsl:when test="$isGuest">
                    <xsl:choose>
                        <!--Даем редактировать дату рождения и паспортные данные только гостю, у которого нет МБ и ПП -->
                        <xsl:when test="not($hasPhoneInMB) and not($hasPreapprovedClaim)">
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'birthdayRow'"/>
                                <xsl:with-param name="lineId" select="'birthdayRow'"/>
                                <xsl:with-param name="rowName">Дата рождения:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input type="text" name="birthDay" value="{birthDay}" maxlength="20" size="20" class="dot-date-pick"/>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'passportRow'"/>
                                <xsl:with-param name="lineId" select="'passportRow'"/>
                                <xsl:with-param name="rowName">Серия паспорта:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="passportSeries" type="text" size="20" maxlength="20" value="{passportSeries}"/>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'passportRow'"/>
                                <xsl:with-param name="lineId" select="'passportRow'"/>
                                <xsl:with-param name="rowName">Номер паспорта:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <input name="passportNumber" type="text" size="20" maxlength="20" value="{passportNumber}"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'birthdayRow'"/>
                                <xsl:with-param name="lineId" select="'birthdayRow'"/>
                                <xsl:with-param name="rowName">Дата рождения:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <span class="floatLeft"><b><xsl:value-of select="mask:getMaskedDate()"/></b></span>
                                    <input type="hidden" name="birthDay" value="{dh:formatXsdDateToString(birthDay)}"/>
                                </xsl:with-param>
                            </xsl:call-template>

                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="required" select="'true'"/>
                                <xsl:with-param name="id" select="'passportRow'"/>
                                <xsl:with-param name="lineId" select="'passportRow'"/>
                                <xsl:with-param name="rowName">Серия и номер паспорта:</xsl:with-param>
                                <xsl:with-param name="rowValue">
                                    <span class="floatLeft"><xsl:value-of select="sh:maskText($passportSeries)"/> &nbsp; <xsl:value-of select="sh:maskText($passportNumber)"/></span>
                                    <input type="hidden" name="passportSeries" value="{$passportSeries}"/>
                                    <input type="hidden" name="passportNumber" value="{$passportNumber}"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:otherwise>
                    <input type="hidden" name="passportSeries"  value="{$passportSeries}"/>
                    <input type="hidden" name="passportNumber"  value="{$passportNumber}"/>
                    <input type="hidden" name="birthDay"        value="{dh:formatXsdDateToString(birthDay)}"/>
                </xsl:otherwise>
            </xsl:choose>

            <!--Поле электронная почта отображаем в любом случае-->
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Электронная почта</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <input name="email" type="text" size="20" maxlength="20" value="{email}"/>
                </xsl:with-param>
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <!--Окно с выбором отделения отображаем только если есть ПП. Если ПП нет, отображаем поле "удобное время для звонка"-->
            <xsl:choose>
                <xsl:when test="$hasPreapprovedClaim">
                    <xsl:if test="ph:isCreditCardOfficeExist()">
                         <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Где я хочу получить карту:</xsl:with-param>
                            <xsl:with-param name="required" select="'true'"/>
                            <xsl:with-param name="rowValue">
                                <a href="" id="credit-card-office-link" onclick="showDepartmetsList();return false;">
                                                   Выберите отделение
                                </a>
                                <input type="hidden" name="credit-card-office" id="credit-card-office"/>
                                <input type="hidden" name="tb" id="tb"/>
                                <input type="hidden" name="osb" id="osb"/>
                                <input type="hidden" name="vsp" id="vsp"/>
                                <input type="hidden" name="regionId" id="regionId"/>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="id" select="'freeTimeRow'"/>
                        <xsl:with-param name="lineId" select="'freeTimeRow'"/>
                        <xsl:with-param name="rowName">Удобное время для звонка:</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input name="freeTime" type="text" size="20" maxlength="20" value="{freeTime}"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">
        <script type="text/javascript">
            doOnLoad(
                function(){
                    $('#phone').text("<xsl:value-of select="dvu:getDepartmentPhoneNumber(tb, osb, vsp)"/>");
                    $("#period").text("<xsl:value-of select="grace-period-duration"/>");
                    $("#interestRateGrace").text("<xsl:value-of select="grace-period-interest-rate"/>");
                    $("#additionalTermsText").append('<xsl:value-of select="additionalTerms"/>');
                });

            function showOrHideAdditionalTerms()
            {
                if($('#additionalTermsText').css('display') == 'none')
                {
                    $('#additionalTermsText').css('display', '');
                }
                else
                {
                    $('#additionalTermsText').css('display', 'none');
                }
            }
        </script>
        <xsl:if test="$app = 'PhizIC'">
            <xsl:variable name="currencySign" select="mu:getCurrencySign(currency)"/>
            <div class="grayContentBlock allWidthBlock">
                <div class="grayBlockTitle">
                    <div class="{$styleClassTitle}" onclick='showOrHideAdditionalTerms()'>
                        <span id="showTerms">
                            <xsl:value-of  select="creditCard"/>
                        </span>
                    </div>
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
            <xsl:variable name="isGuest" select="ph:isGuest()"/>
            <xsl:variable name="isMobileBankExist" select="ph:isRegisteredGuest()"/>
            <xsl:if test="(not ($isGuest)) or ($isGuest and $isMobileBankExist)">
                <xsl:variable name="hasLoanNewCardOffer" select="lch:hasLoanNewCardOffer()"/>
                <xsl:if test="(not ($isGuest)) or $hasLoanNewCardOffer">
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
                </xsl:if>
            </xsl:if>
            <div id="additionalInfo" class="additionalInfoLoan">
                <div class="{$styleClassTitle} rowTitle18 rowTitleView">Краткая информация обо мне</div>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">ФИО получателя карты:</xsl:with-param>
                    <xsl:with-param name="rowValue"><xsl:value-of select="ph:getFormattedPersonName(firstName, surName, patrName)"/></xsl:with-param>
                </xsl:call-template>

                <xsl:variable name="offerId" select="offerId"/>
                <xsl:variable name="hasPreapprovedClaim" select="string-length($offerId)>0"/>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <xsl:choose>
                            <xsl:when test="$isGuest">
                                <xsl:value-of select="mask:maskPhoneNumber(ownerGuestPhone)"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="mask:maskPhoneNumber(mobilePhone)"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:with-param>
                </xsl:call-template>

                <!--Поля "дата рождения" и "номер и серия паспорта" отображаем только гостям-->
                <xsl:if test="$isGuest">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Дата рождения:</xsl:with-param>
                        <xsl:with-param name="rowValue"><xsl:value-of select="mask:getMaskedDate()"/></xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="rowName">Серия и номер паспорта:</xsl:with-param>
                        <xsl:with-param name="rowValue"><xsl:value-of select="passportSeries"/>&nbsp;<xsl:value-of select="passportNumber"/></xsl:with-param>
                    </xsl:call-template>
                </xsl:if>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Электронная почта:</xsl:with-param>
                    <xsl:with-param name="rowValue"><xsl:value-of select="email"/></xsl:with-param>
                </xsl:call-template>

                <xsl:choose>
                    <xsl:when test="$hasPreapprovedClaim">
                        <xsl:variable name="preapproved" select="preapproved = 'true'"/>
                        <xsl:if test="$preapproved">
                            <xsl:call-template name="standartRow">
                                <xsl:with-param name="rowName">Где я хочу получить карту:</xsl:with-param>
                                <xsl:with-param name="rowValue"><xsl:value-of  select="credit-card-office"/></xsl:with-param>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="standartRow">
                            <xsl:with-param name="rowName">Удобное время для звонка:</xsl:with-param>
                            <xsl:with-param name="rowValue"><xsl:value-of select="freeTime"/></xsl:with-param>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>

                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Статус заявки:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                       <div id="state">
                            <span onmouseover="showLayer('state','stateDescription','default',0,646);"
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
                    </xsl:with-param>
                </xsl:call-template>
            </div>
        </xsl:if>

        <xsl:if test="$app = 'PhizIA'">
            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName" select="'Номер документа'"/>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="documentNumber"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName" select="'Дата документа'"/>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="documentDate"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Кредитная карта:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="creditCard"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Процентная ставка:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="interest-rate"/>%</b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Годовое обслуживание. Первый/последующие годы:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="mu:getFormatAmountWithNoCents(next-year-service, '.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(next-year-service-currency)"/>&nbsp;в год (первый год -
                    <xsl:value-of     select="mu:getFormatAmountWithNoCents(first-year-service, '.')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(first-year-service-currency)"/>)</b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Кредитный лимит:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of  select="mu:getFormatAmountWithNoCentsGrouping(amount,'.')"/>&nbsp;<xsl:value-of  select="mu:getCurrencySign(currency)"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Фамилия:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="substring(surName, 1, 1)"/>.</b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Имя:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="firstName"/></b>
                </xsl:with-param>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Отчество:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="patrName"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Домашний телефон:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="homePhone"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Рабочий телефон:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="workPhone"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Мобильный телефон:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="mobilePhone"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">E-mail:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="email"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Желательная дата и время звонка от сотрудника банка:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b><xsl:value-of select="freeTime"/></b>
                </xsl:with-param>
                <xsl:with-param name="required"   select="'false'"/>
                <xsl:with-param name="isAllocate" select="'false'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус заявки:</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <b>
                        <div id="state">
                            <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link">
                                <xsl:call-template name="employeeState2text">
                                    <xsl:with-param name="code">
                                        <xsl:value-of select="state"/>
                                    </xsl:with-param>
                                </xsl:call-template>
                            </span>
                        </div>
                    </b>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
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
                    <xsl:choose>
                        <xsl:when test="$mode = 'edit'">
                            <div class="marginText">Льготный период кредитования до <span id="period" style="{$styleValue}"></span>&nbsp;<b>дней</b>
                            <input type="hidden" name="grace-period-duration" id="grace-period-duration"/></div>

                            <div class="marginText">Процентная ставка в льготный период <span id="interestRateGrace" style="{$styleValue}"></span>&nbsp;<b>%</b>
                            <input type="hidden" name="grace-period-interest-rate" id="grace-period-interest-rate"/></div>

                            <input type="hidden" name="additionalTerms" id="additionalTerms"/>
                            <div id="additionalTermsText" style="display:none"></div>
                            <div class="showTermsBlock"><a id="showTerms" class="blueGrayLinkDotted" onclick='showOrHideAdditionalTerms()'>Подробнее</a></div>
                        </xsl:when>
                        <xsl:otherwise>
                            <div id="additionalTermsText" style="display:none">
                                <div class="marginText">Льготный период кредитования до <span id="period" style="{$styleValue}"></span>&nbsp;<b>дней</b>
                                <input type="hidden" name="grace-period-duration" id="grace-period-duration"/></div>

                                <div class="marginText">Процентная ставка в льготный период <span id="interestRateGrace" style="{$styleValue}"></span>&nbsp;<b>%</b>
                                <input type="hidden" name="grace-period-interest-rate" id="grace-period-interest-rate"/></div>

                                <input type="hidden" name="additionalTerms" id="additionalTerms"/>
                            </div>
                        </xsl:otherwise>
                    </xsl:choose>

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

<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                xmlns:dep="java://com.rssl.phizic.web.util.DepartmentViewUtil"
                xmlns:sh="java://com.rssl.phizic.config.SettingsHelper"
                xmlns:date="xalan://java.util.Date">
    <xsl:import href="commonFieldTypes.template.xslt"/>
    <xsl:output method="html" version="1.0"  indent="yes"/>
    <xsl:param name="mode" select="'edit'"/>
    <xsl:param name="webRoot" select="'webRoot'"/>
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

        <script type="text/javascript" language="JavaScript">
            <xsl:variable name="loanTermEnd" select="document('loanInfo.xml')/info/loanTermEnd"/>

            var asyncDatePickSettings = new Object();
            asyncDatePickSettings.url = '<xsl:value-of select="$webRoot"/>/private/async/workDays/list.do';
            asyncDatePickSettings.tb = <xsl:value-of select="document('loanInfo.xml')/info/loanTb"/>;
            asyncDatePickSettings.startDate = '<xsl:value-of  select="sh:getStarDateEarlyLoanRepayment()"/>';
            asyncDatePickSettings.endDate = '<xsl:value-of  select="sh:getEndDateEarlyLoanRepayment($loanTermEnd)"/>';

            <xsl:choose>
                <xsl:when test="$formData/partial='true'">
                    asyncDatePickSettings.plannedPayments = [];
                    asyncDatePickSettings.showPlannedPaymentsText = false;
                </xsl:when>
                <xsl:otherwise>
                    asyncDatePickSettings.plannedPayments = [];
                    asyncDatePickSettings.dateToPayment = {};
                    <xsl:variable name="plannedPayments" select="document('paymentSchedule.xml')/schedule/*"/>
                    <xsl:for-each select="$plannedPayments">
                        var asyncDatePickCurrentDate = "<xsl:value-of select="date"/>";
                        var asyncDatePickCurrentAmount = '<xsl:value-of select="format-number(balance + amount, '### ##0,00', 'sbrf')"/>';
                        asyncDatePickSettings.plannedPayments.push(new Date(asyncDatePickCurrentDate));
                        asyncDatePickSettings.dateToPayment[asyncDatePickCurrentDate] = asyncDatePickCurrentAmount;
                    </xsl:for-each>

                    asyncDatePickSettings.showPlannedPaymentsText = true;
                    asyncDatePickSettings.daySelectedHandler = function(date){
                        if(asyncDatePickSettings.dateToPayment[date.toISOString().substring(0, 10)]!==undefined)
                        {
                            $('#earlyRepaymentFullPaymentForTodayLabel').hide();
                            $('#earlyRepaymentFullPaymentLabel').html(asyncDatePickSettings.dateToPayment[date.toISOString().substring(0, 10)]).show();
                            $('#earlyRepaymentFullPaymentDescription2').hide();
                            $('#earlyRepaymentFullPaymentDescription1').show();
                        }
                        else
                        {
                            $('#earlyRepaymentFullPaymentLabel').hide();
                            $('#earlyRepaymentFullPaymentForTodayLabel').show();
                            $('#earlyRepaymentFullPaymentDescription1').hide();
                            $('#earlyRepaymentFullPaymentDescriptionDate').html(date.getDate() + ' ' + monthToStringByNumber(date.getMonth()) + ' ' + date.getFullYear());
                            $('#earlyRepaymentFullPaymentDescription2').show();
                        }
                    }
                </xsl:otherwise>
            </xsl:choose>

            function init(){
                payInput.additErrorBlock = "errorDivBase";
                payInput.ACTIVE_CLASS_NAME = "";
                payInput.ERROR_CLASS_NAME = "form-row-addition form-row-new error-row-new";
            }
            doOnLoad(function(){
                init();
            });
        </script>

        <xsl:variable name="activeAccounts" select="document('stored-active-debit-allowed-external-phiz-accounts.xml')/entity-list/*"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-own-cards.xml')/entity-list/*"/>
        <xsl:variable name="loanCurrency" select="mu:getCurrencySign(document('loanInfo.xml')/info/loanCurrency)"/>

        <input type="hidden" name="partial" value="{partial}"/>
        <input type="hidden" name="loanLinkId" value="{loanLinkId}"/>

        <div class="earlyLoanRepaymentClaim">

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Номер документа</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="noLines">
                        <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                        <xsl:value-of select="documentNumber"/>
                    </div>
                </xsl:with-param>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>


            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'true'"/>
                <xsl:with-param name="rowName">Счёт списания</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:call-template name="resources">
                        <xsl:with-param name="name" select="'fromResource'"/>
                        <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                        <xsl:with-param name="activeAccounts" select="$activeAccounts"/>
                        <xsl:with-param name="activeCards" select="$activeCards"/>
                    </xsl:call-template>
                </xsl:with-param>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Дата платежа</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <xsl:variable name="defaultDate">
                        <xsl:choose>
                            <xsl:when test="documentDate!=''">
                                <xsl:value-of select="dh:formatXsdDateToString(documentDate)"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="dh:formatXsdDateToString(document('loanInfo.xml')/info/defaultDate)"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>
                    <input name="documentDate" id="documentDate" class="async-date-pick" size="10" value="{$defaultDate}"/>
                </xsl:with-param>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>

            <xsl:choose>
                <xsl:when test="$formData/partial = 'true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'true'"/>
                        <xsl:with-param name="rowName">Сумма</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <input type="text" name="amount" value="{$formData/amount}"/>&nbsp;<span class="secondaryText"><xsl:value-of select="$loanCurrency"/></span>
                        </xsl:with-param>
                        <xsl:with-param name="description">
                            Минимальная сумма <xsl:value-of select="format-number(document('loanInfo.xml')/info/minPayment, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrency"/>, максимальная &#8212;  <xsl:value-of select="format-number(document('loanInfo.xml')/info/maxPayment, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="$loanCurrency"/> (остаток основного долга и проценты на дату платежа).
                        </xsl:with-param>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select="'false'"/>
                        <xsl:with-param name="rowName">Предварительная сумма</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <span id="earlyRepaymentFullPaymentForTodayLabel" style="display:none;"><xsl:value-of select="format-number(document('loanInfo.xml')/info/fullPayment, '### ##0,00', 'sbrf')"/></span>
                            <span id="earlyRepaymentFullPaymentLabel"><xsl:value-of select="format-number(document('loanInfo.xml')/info/defaultPayment, '### ##0,00', 'sbrf')"/></span>&nbsp;<xsl:value-of select="$loanCurrency"/>
                        </xsl:with-param>
                        <xsl:with-param name="description">
                            <span id="earlyRepaymentFullPaymentDescription1">Обеспечьте сумму на счете к дате платежа. Сумма рассчитана предварительно и может увеличиться при несвоевременной оплате по кредиту.</span>
                            <div id="earlyRepaymentFullPaymentDescription2" style="display:none;">Сумма полной задолженности по кредиту рассчитана на дату <span class="bold"><xsl:value-of select="dh:formatDateWithMonthString(dh:toCalendar(date:new()))"/></span>. Для осуществления полного погашения кредита в дату <span id="earlyRepaymentFullPaymentDescriptionDate" class="bold"/> обеспечьте на счете средства с учетом погашения дополнительно начисленных процентов.</div>
                        </xsl:with-param>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                    </xsl:call-template>
                    <script>
                        <xsl:variable name="defaultDate">
                            <xsl:choose>
                                <xsl:when test="documentDate!=''">
                                    <xsl:value-of select="dh:formatXsdDateToString(documentDate)"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="dh:formatXsdDateToString(document('loanInfo.xml')/info/defaultDate)"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>
                        asyncDatePickSettings.daySelectedHandler(Date.fromString('<xsl:value-of select="$defaultDate"/>','dd.mm.yyyy'));
                    </script>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <div class="earlyLoanRepaymentClaim">
            <xsl:variable name="state" select="state"/>

            <xsl:if test="(state = 'DISPATCHED' or state = 'EXECUTED' or state = 'DELAYED_DISPATCH' or state = 'OFFLINE_DELAYED' or state = 'WAIT_CONFIRM' or state = 'REFUSED' or state = 'RECALLED' or state = 'UNKNOW')">
                <div class="payData">
                    <div id="state" class="inlineBlock p2pState">
                        <div class="stateType state_{$state}"></div>
                        <span onmouseover="showLayer('state','stateDescription');" onmouseout="hideLayer('stateDescription');" class="link stateColor_{$state}">
                            <xsl:call-template name="clientState2text">
                                <xsl:with-param name="code">
                                    <xsl:value-of select="state"/>
                                </xsl:with-param>
                            </xsl:call-template>
                        </span>
                    </div>

                    <div style="float: right;text-align: right;font: 14px/18px Arial;color: #888;">
                        <xsl:value-of select="documentDate"/>
                    </div>
                    <div class="clear"> </div>

                    <div class="bankDetailData">
                        <xsl:variable name="osb" select="dep:getOsb(departmentId)"/>
                        <xsl:choose>
                            <xsl:when test="$osb != 'null'">
                                <div class="b_name"><xsl:value-of  select="dep:getNameFromOsb($osb)"/></div>
                                <xsl:variable name="bic" select="dep:getBicFromOsb($osb)"/>
                                <div>БИК: <xsl:value-of  select="$bic"/></div>
                                <div>Корр. счет: <xsl:value-of  select="dep:getCorrByBIC($bic)"/></div>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="defaultBic" select="dep:getDefaultBankBic()"/>
                                <div class="b_name"><xsl:value-of  select="dep:getDefaultBankName()"/></div>
                                <div>БИК: <xsl:value-of  select="$defaultBic"/></div>
                                <div>Корр. счет: <xsl:value-of  select="dep:getCorrByBIC($defaultBic)"/></div>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>

                </div>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Номер документа</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="noLines">
                        <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                        <xsl:value-of select="documentNumber"/>
                    </div>
                </xsl:with-param>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select="'false'"/>
                <xsl:with-param name="rowName">Счёт списания</xsl:with-param>
                <xsl:with-param name="rowValue">
                    <div class="linear">
                        <xsl:choose>
                            <xsl:when test="fromResourceType='com.rssl.phizic.business.resources.external.CardLink'">
                                <xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="au:getFormattedAccountNumber(fromAccountSelect)"/>
                            </xsl:otherwise>
                        </xsl:choose>
                        &nbsp;[<xsl:value-of select="fromAccountName"/>]&nbsp;<xsl:value-of select="format-number(selectedResourceRest, '### ##0,00', 'sbrf')"/>&nbsp;
                        <xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/>
                    </div>
                </xsl:with-param>
                <xsl:with-param name="readField" select="'allWidth'"/>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="required" select = "'false'"/>
                <xsl:with-param name="rowName">Дата платежа</xsl:with-param>
                <xsl:with-param name="rowValue"><div class="linear"><xsl:value-of select="dh:formatXsdDateToString(documentDate)"/></div></xsl:with-param>
                <xsl:with-param name="readField" select="'allWidth'"/>
                <xsl:with-param name="spacerClass" select="'spacer'"/>
            </xsl:call-template>

            <xsl:choose>
                <xsl:when test="$formData/partial='true'">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select = "'false'"/>
                        <xsl:with-param name="rowName">Сумма</xsl:with-param>
                        <xsl:with-param name="rowValue"><div class="linear"><span class="bold"><xsl:value-of select="format-number(amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/></span></div></xsl:with-param>
                        <xsl:with-param name="readField" select="'allWidth'"/>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="required" select = "'false'"/>
                        <xsl:with-param name="rowName">Предварительная сумма</xsl:with-param>
                        <xsl:with-param name="rowValue"><div class="linear"><xsl:value-of select="format-number(amount, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(fromResourceCurrency)"/></div></xsl:with-param>
                        <xsl:with-param name="readField" select="'allWidth'"/>
                        <xsl:with-param name="spacerClass" select="'spacer'"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

    <xsl:template name="standartRow">

        <xsl:param name="id"/>
        <xsl:param name="lineId"/>                      <!--идентификатор строки-->
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowNameHelp"/>                 <!--подсказка к полю-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <xsl:param name="description"/>                 <!-- Описание поля, данные после <cut />  будут прятаться под ссылку подробнее -->
        <xsl:param name="rowStyle"/>                    <!-- Стиль поля -->
        <xsl:param name="spacerClass" select="''"/>     <!-- доп класс -->
        <xsl:param name="spacerHint"/>                  <!-- Подсказка к полю ввода -->
        <xsl:param name="readField"/>                   <!-- нередактируемые поля -->
        <!-- Необязательный параметр -->
        <xsl:param name="fieldName"/>                   <!-- Имя поля. Если не задано, то пытаемся получить имя из rowValue -->
        <xsl:param name="isAllocate" select="'false'"/>  <!-- Выделить при нажатии -->
        <xsl:param name="required"/>

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
                    <xsl:value-of select="'form-row form-row-new'"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'form-row-addition form-row-new'"/>
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

            <xsl:if test="$spacerClass != ''">
                <div class="{$spacerClass}"></div>
            </xsl:if>

            <xsl:if test="$spacerHint != ''">
                <div class="spacerHint">
                    <xsl:copy-of select="$spacerHint"/>
                </div>
            </xsl:if>
            <div class="paymentLabelNew">
                <span class="paymentTextLabel">
                    <xsl:if test="string-length($id) > 0">
                        <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
                    </xsl:if>
                    <xsl:if test="string-length($rowName) > 0">
                        <xsl:copy-of select="$rowName"/>
                    </xsl:if>
                </span>
                <xsl:if test="string-length($rowNameHelp) > 0">
                    <xsl:copy-of select="$rowNameHelp"/>
                </xsl:if>
            </div>
            <div class="paymentValue paymentValueNew ">
                <div class="paymentInputDiv autoInputWidth {$readField}"><xsl:copy-of select="$rowValue"/>

                    <xsl:if test="$readonly = 0 and $mode = 'edit'">
                        <div class="description">
                            <xsl:copy-of select="$description"/>
                        </div>
                    </xsl:if>
                    <xsl:if test="$mode = 'edit'">
                        <script type="text/javascript">
                            $(document).ready(function(){
                                $('.errorRed').hover(function(){
                                        $(this).find('.errorDiv').addClass('autoHeight');
                                        $(this).find('.showFullText').hide();
                                   }, function() {
                                        $(this).find('.errorDiv').removeClass('autoHeight');
                                        $(this).find('.showFullText').show();
                                });
                            });
                        </script>
                    </xsl:if>
                    <div class="errorDivBase errorDivPmnt">
                        <div class="errorRed">
                            <div class="errMsg">
                                <div class="errorDiv"></div>
                                <div class="showFullText">
                                    <div class="pointers"></div>
                                </div>
                            </div>
                            <div class="errorRedTriangle"></div>
                        </div>
                    </div>
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

    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:param name="activeAccounts"/>
        <xsl:param name="activeCards"/>

        <select id="{$name}" name="{$name}" class="selectSbtM" onchange="refreshForm();">
            <xsl:variable name="recType" select="receiverSubType"/>

            <xsl:if test="$recType='ourAccount' or $recType='externalAccount'">
                <xsl:for-each select="$activeAccounts">
                    <option>
                        <xsl:variable name="id" select="field[@name='code']/text()"/>
                        <xsl:attribute name="value">
                            <xsl:value-of select="$id"/>
                        </xsl:attribute>
                        <xsl:if test="$accountNumber=./@key or $linkId=$id">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        <xsl:value-of select="au:getShortAccountNumber(./@key)"/>&nbsp;
                        [<xsl:value-of select="./field[@name='name']"/>]
                        <xsl:if test="./field[@name='amountDecimal'] != ''">
                            <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                        </xsl:if>
                        <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                    </option>
                </xsl:for-each>
            </xsl:if>
            <xsl:for-each select="$activeCards">
                <xsl:variable name="id" select="field[@name='code']/text()"/>
                <option>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$id"/>
                    </xsl:attribute>
                    <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                        <xsl:attribute name="selected"/>
                    </xsl:if>
                    <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                    [<xsl:value-of select="./field[@name='name']"/>]
                    <xsl:if test="./field[@name='amountDecimal'] != ''">
                        <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                    </xsl:if>
                    <xsl:value-of select="mu:getCurrencySign(./field[@name='currencyCode'])"/>
                </option>
            </xsl:for-each>
        </select>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='UNKNOWN'">Отправка заявки в банк</xsl:when>
            <xsl:when test="$code='ERROR'">Ошибка регистрации</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Принято</xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
		<!ENTITY nbsp "&#160;">
		]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                xmlns:xalan = "http://xml.apache.org/xalan">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="application" select="'application'"/>
   	<xsl:param name="app">
       <xsl:value-of select="$application"/>
   	</xsl:param>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:decimal-format name="sbrf" decimal-separator="," grouping-separator=" "/>
	<xsl:param name="personAvailable" select="true()"/>
    <xsl:param name="skinUrl" select="'skinUrl'"/>
	<xsl:variable name="formNameValue" select='//recalled-document-form-name'/>

    <xsl:template match="/">
	   <xsl:choose>
			<xsl:when test="$mode = 'edit' or $mode = 'view'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
        </xsl:choose>
	</xsl:template>

    <xsl:template match="/form-data" mode="edit">

        <input type="hidden" name="receiverId" value="{receiverId}"/>

        <xsl:if test="$mode = 'edit'">
            <xsl:variable name="imageHelpUrl" select="spu:getImageHelpUrl(recipient, $webRoot)"/>

            <!-- window для отображения картинки -->
            <div id="providerImageHelpWin" class="window farAway">
                <div class="workspace-box shadow">
                    <div class="shadowRT r-top">
                        <div class="shadowRTL r-top-left"><div class="shadowRTR r-top-right"><div class="shadowRTC r-top-center"></div></div></div>
                    </div>
                    <div class="shadowRCL r-center-left">
                        <div class="shadowRCR r-center-right">
                            <div class="shadowRC r-content">
                                <div id="providerImageHelp">
                                    <div class="imageHelp" id="imageHelpProvider"></div>
                                </div>
                                <div class="clear"></div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <div class="shadowRBL r-bottom-left">
                        <div class="shadowRBR r-bottom-right">
                            <div class="shadowRBC r-bottom-center"></div>
                        </div>
                     </div>
                </div>
                <div class="closeImg" title="закрыть" onclick="win.close('providerImageHelp');"></div>
            </div>

            <xsl:if test="string-length($imageHelpUrl) > 0">
                <div class="dashedBorder">
                    <xsl:call-template name="standartRow">
                        <xsl:with-param name="lineId">imageHelpProviderHeader</xsl:with-param>
                        <xsl:with-param name="rowName"><div class="imageHelpLabel">Образец квитанции:</div></xsl:with-param>
                        <xsl:with-param name="required">false</xsl:with-param>
                        <xsl:with-param name="rowValue">
                            <script type="text/javascript" src="{$resourceRoot}/scripts/providerImageHelp.js"></script>
                            <div id="imageHelpProviderHeader">
                                <input type="hidden" id="imageHelpSrc" value="{$imageHelpUrl}"/>
                                <div class="imageHelpTitleContainer">
                                    <a class="imageHelpTitle imageHelpHeaderControl closed" onclick="paymentImageHelpHeaderAction(); return false;" href="#">показать</a>
                                </div>
                                <div class="imageHelp" style="display:none"></div>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>
                </div>
            </xsl:if>
        </xsl:if>
        
        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="rowName">Номер документа:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <input type="hidden" name="documentNumber" value="{documentNumber}"/>
                <xsl:if test="$mode = 'edit'">
                    <input type="hidden" name="recipient" value="{recipient}"/>
                    <input type="hidden" name="receiverId" value="{receiverId}"/>
                </xsl:if>
                <b><xsl:value-of  select="documentNumber"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId"   select="'receiverNameRow'"/>
            <xsl:with-param name="rowName">Получатель:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="receiverName"/></b>
                <input type="hidden" name="receiverName" value="{receiverName}"/>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId"   select="'RecIdentifierRow'"/>
            <xsl:with-param name="rowName">Номер заказа:</xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="S19682282111A39699192505 != ''">
                        <b><xsl:value-of select="S19682282111A39699192505"/></b>
                        <input type="hidden" name="S19682282111A39699192505" value="{S19682282111A39699192505}"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <b><xsl:value-of select="RecIdentifier"/></b>
                        <input type="hidden" name="RecIdentifier" value="{RecIdentifier}"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>
        <xsl:call-template name="standartRow">
             <xsl:with-param name="rowName">Оплата с:</xsl:with-param>
             <xsl:with-param name="lineId"   select="'fromAccountSelectRow'"/>
             <xsl:with-param name="rowValue">
                <xsl:choose>
                    <xsl:when test="$mode = 'edit'">
                        <xsl:call-template name="resources">
                            <xsl:with-param name="name">fromResource</xsl:with-param>
                            <xsl:with-param name="accountNumber" select="fromAccountSelect"/>
                            <xsl:with-param name="linkId" select="fromResource"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="$mode = 'view'">
                         <b><xsl:value-of select="mask:getCutCardNumber(fromAccountSelect)"/>
                         <xsl:if test="fromAccountName != ''">
                            &nbsp;[<xsl:value-of select="fromAccountName"/>]
                         </xsl:if>
                         <xsl:variable name="fromResourceCurrency"><xsl:value-of select="fromResourceCurrency"/></xsl:variable>
                         &nbsp;<xsl:value-of select="mu:getCurrencySign($fromResourceCurrency)"/></b>
                    </xsl:when>
                </xsl:choose>
             </xsl:with-param>
         </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId"   select="'orderNumberRow'"/>
            <xsl:with-param name="rowName">Информация о заказе:</xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
            <xsl:with-param name="rowValue">
                <div class="goodsList">
                <xsl:variable name="fields" select="document('internetOrderFields.xml')/fields/field"/>
                <xsl:if test="count($fields)>0">
                    <table id="details" class="simpleTable">
                        <tr class="internetOrderTabHead">
                            <th class="internetOrderTabHeadFirst titleTable">Товар</th>
                            <th class="internetOrderTabHeadSecond">Количество</th>
                            <th class="internetOrderTabHeadSecond">Сумма</th>
                        </tr>
                        <xsl:for-each select="$fields">
                            <tr class="internetOrderTabLine">
                                <td class="internetOrderTabItem">
                                    <xsl:value-of  select="./name"/>
                                    <xsl:variable name="itemDesc" select="./description"/>
                                    <xsl:if test="string-length($itemDesc)!=0">
                                        <br/>
                                        <br/>
                                        <span style="color:silver;"><xsl:value-of  select="$itemDesc"/></span>
                                    </xsl:if>
                                </td>
                                <td class="internetOrderTabTd">
                                    <xsl:variable name="count" select="./count"/>
                                    <xsl:if test="string-length($count)!=0">
                                        <xsl:value-of  select="$count"/>&nbsp;шт.
                                    </xsl:if>
                                </td>
                                <td class="internetOrderTabTd rightPaddingCell">
                                    <xsl:variable name="itemPrice" select="./price"/>
                                    <xsl:if test="string-length($itemPrice)!=0">
                                        <xsl:value-of  select="$itemPrice/amount"/>&nbsp;
                                        <xsl:value-of select="mu:getCurrencySign($itemPrice/currency)"/>
                                    </xsl:if>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                    <div class="totalOrderSum">
                        <xsl:variable name="currency"><xsl:value-of select="currency"/></xsl:variable>
                        Итого: <xsl:value-of select="amount"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($currency)"/>
                    </div>
                </xsl:if>
                </div>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:call-template name="standartRow">
            <xsl:with-param name="required" select="'false'"/>
            <xsl:with-param name="lineId"   select="'amountRow'"/>
            <xsl:with-param name="rowName">Сумма:</xsl:with-param>
            <xsl:with-param name="rowValue">
                <b><xsl:value-of select="amount"/>
                <input type="hidden" name="amount" value="{amount}"/>
                <xsl:variable name="currency"><xsl:value-of select="currency"/></xsl:variable>
                <input type="hidden" name="currency" value="{currency}"/>
                &nbsp;<xsl:value-of select="mu:getCurrencySign($currency)"/></b>
            </xsl:with-param>
            <xsl:with-param name="isAllocate" select="'false'"/>
        </xsl:call-template>

        <xsl:if test="$mode = 'view'">
            <xsl:if test="string-length(commission)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="rowName">Комиссия:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                            <b><xsl:value-of select="format-number(commission, '### ##0,00', 'sbrf')"/>&nbsp;<xsl:value-of select="mu:getCurrencySign(commissionCurrency)"/></b>
                            <input name="commission" type="hidden" value="{commission}"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:variable name="refund" select="document('refundGoods.xml')/RefundList/Refund"/>
            <xsl:if test="count($refund)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="required" select="'false'"/>
                    <xsl:with-param name="lineId"   select="'orderNumberRow'"/>
                    <xsl:with-param name="rowName">Информация о возвратах:</xsl:with-param>
                    <xsl:with-param name="isAllocate" select="'false'"/>
                    <xsl:with-param name="rowValue">
                        <div class="goodsList">
                            <xsl:for-each select="$refund">
                                <xsl:variable name="fields" select="./fields/field"/>
                                <xsl:if test="count($fields)>0">
                                    <table id="details"  class="simpleTable">
                                        <tr class="internetOrderTabHead">
                                            <th class="internetOrderTabHeadFirst">Товар</th>
                                            <th class="internetOrderTabHeadSecond">Количество</th>
                                            <th class="internetOrderTabHeadSecond">Сумма</th>
                                        </tr>
                                        <xsl:for-each select="$fields">
                                            <tr class="internetOrderTabLine">
                                                <td class="internetOrderTabItem">
                                                    <xsl:value-of  select="./name"/>
                                                    <xsl:variable name="itemDesc" select="./description"/>
                                                    <xsl:if test="string-length($itemDesc)!=0">
                                                        <br/>
                                                        <br/>
                                                        <span style="color:silver;"><xsl:value-of  select="$itemDesc"/></span>
                                                    </xsl:if>
                                                </td>
                                                <td class="internetOrderTabTd">
                                                    <xsl:variable name="count" select="./count"/>
                                                    <xsl:if test="string-length($count)!=0">
                                                        <xsl:value-of  select="$count"/>&nbsp;шт.
                                                    </xsl:if>
                                                </td>
                                                <td class="internetOrderTabTd">
                                                    <xsl:variable name="itemPrice" select="./price"/>
                                                    <xsl:if test="string-length($itemPrice)!=0">
                                                        <xsl:value-of  select="$itemPrice/amount"/>&nbsp;
                                                        <xsl:value-of select="mu:getCurrencySign($itemPrice/currency)"/>
                                                    </xsl:if>
                                                </td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </xsl:if>
                                <div class="totalOrderSum">
                                    <xsl:variable name="currency"><xsl:value-of select="./Price/Currency"/></xsl:variable>
                                    Итого возвратов: <xsl:value-of select="./Price/Amount"/>&nbsp;<xsl:value-of select="mu:getCurrencySign($currency)"/>
                                </div><div class="clear"></div>
                            </xsl:for-each>
                        </div>
                    </xsl:with-param>
                    <xsl:with-param name="isAllocate" select="'false'"/>
                </xsl:call-template>
            </xsl:if>

            <xsl:call-template name="standartRow">
                <xsl:with-param name="rowName">Статус платежа:</xsl:with-param>
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

            <xsl:if test="string-length(promoCode)>0">
                <xsl:call-template name="standartRow">
                    <xsl:with-param name="rowName">Промо-код:</xsl:with-param>
                    <xsl:with-param name="rowValue">
                        <div id="promoCodeInternetOrder">
                            <xsl:value-of select="promoCode"/>
                            <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="javascript:openFAQ('/PhizIC/faq.do#r2');"></a>
                        </div>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:if>

            <xsl:if test="count($refund)>0">
                <div class="internetOrderBottomMargin">&nbsp;</div>
            </xsl:if>

        </xsl:if>

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

	<div class="paymentLabel">
	    <span class="paymentTextLabel">
	        <xsl:if test="string-length($id) > 0">
	            <xsl:attribute name="id"><xsl:copy-of select="$id"/>TextLabel</xsl:attribute>
            </xsl:if>
	        <xsl:copy-of select="$rowName"/>
	    </span>
        <xsl:if test="$required = 'true' and $mode = 'edit'">
            <span id="asterisk_{$id}" class="asterisk" name="asterisk_{$lineId}">*</span>
		</xsl:if>
    </div>
	<div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/> </div>
                <div class="errorDiv" style="display: none;">
				</div>
	</div>
    <div class="clear"></div>
</div>


</xsl:template>

    <xsl:template name="employeeState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='INITIAL'">Введен (статус для клиента: "Черновик")</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Обрабатывается (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отказан (статус для клиента: "Отклонено банком")</xsl:when>
            <xsl:when test="$code='RECALLED'">Отозван (статус для клиента: "Заявка была отменена")</xsl:when>
            <xsl:when test="$code='ERROR'">Приостановлен (статус для клиента: "Исполняется банком")<xsl:if test="checkStatusCountResult = 'true'"> (Превышение количества проверок статуса)</xsl:if></xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Ожидает дополнительной обработки (статус для клиента: "Подтвердите в контактном центре")</xsl:when>
            <xsl:when test="$code='UNKNOW'">Приостановлен (статус для клиента: "Исполняется банком")</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="clientState2text">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='SAVED'">Черновик</xsl:when>
            <xsl:when test="$code='INITIAL'">Черновик</xsl:when>
            <xsl:when test="$code='DELAYED_DISPATCH'">Ожидается обработка</xsl:when>
            <xsl:when test="$code='DISPATCHED'">Исполняется банком</xsl:when>
            <xsl:when test="$code='EXECUTED'">Исполнен</xsl:when>
            <xsl:when test="$code='REFUSED'">Отклонено банком</xsl:when>
            <xsl:when test="$code='RECALLED'">Заявка была отменена</xsl:when>
            <xsl:when test="$code='ERROR'">Исполняется банком</xsl:when>
            <xsl:when test="$code='WAIT_CONFIRM'">Подтвердите в контактном центре</xsl:when>
            <xsl:when test="$code='UNKNOW'">Исполняется банком</xsl:when>
            <xsl:when test="$code='OFFLINE_DELAYED'">Ожидается обработка</xsl:when>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="resources">
        <xsl:param name="name"/>
        <xsl:param name="accountNumber"/>
        <xsl:param name="linkId"/>
        <xsl:variable name="serviceProvider" select="document(concat('serviceProvider.xml?id=',recipient))/entity-list/entity"/>

        <xsl:variable name="activeCardsDictionaryName">
            <xsl:choose>
                <xsl:when test="$serviceProvider/field[@name = 'isFacilitator'] = 'true' and $serviceProvider/field[@name = 'isCreditCardSupported'] = 'true'">active-rur-not-virtual-cards.xml</xsl:when>
                <xsl:when test="$serviceProvider/field[@name = 'isFacilitator'] = 'true' ">active-rur-not-credit-not-virtual-cards.xml</xsl:when>
                <xsl:when test="$serviceProvider/field[@name = 'isCreditCardSupported'] = 'true'">active-not-virtual-cards.xml</xsl:when>
                <xsl:otherwise>active-not-virtual-not-credit-cards.xml</xsl:otherwise>
            </xsl:choose>
        </xsl:variable>

        <xsl:variable name="activeCards" select="document($activeCardsDictionaryName)/entity-list/*"/>

        <xsl:choose>
            <xsl:when test="string-length($activeCards) = 0">
               Нет доступных карт
            </xsl:when>
            <xsl:otherwise>
                <select id="{$name}" name="{$name}">
                     <xsl:if test="string-length($accountNumber) = 0 and string-length($linkId) = 0">
                         <option value="">Выберите карту оплаты</option>
                     </xsl:if>
                     <xsl:for-each select="$activeCards">
                         <xsl:variable name="id" select="concat('card:',field[@name='cardLinkId']/text())"/>
                         <option>
                             <xsl:attribute name="value">
                                 <xsl:value-of select="$id"/>
                             </xsl:attribute>
                             <xsl:if test="$accountNumber= ./@key or $linkId=$id">
                                 <xsl:attribute name="selected"/>
                             </xsl:if>
                             <xsl:value-of select="mask:getCutCardNumber(./@key)"/>&nbsp;
                             [<xsl:value-of select="./field[@name='name']"/>]
                             <xsl:value-of select="format-number(./field[@name='amountDecimal'], '### ##0,00', 'sbrf')"/>&nbsp;
                             <xsl:variable name="currencyCode"><xsl:value-of select="./field[@name='currencyCode']"/></xsl:variable>
                             <xsl:value-of select="mu:getCurrencySign($currencyCode)"/>
                         </option>
                     </xsl:for-each>
                </select>                
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>

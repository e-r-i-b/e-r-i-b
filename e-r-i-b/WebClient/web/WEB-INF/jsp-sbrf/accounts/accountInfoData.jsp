<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<script type="text/javascript">
   function openPrintAccountInfo(event)
   {
       var url = "${phiz:calculateActionURL(pageContext,'/private/accounts/printAcc.do')}";
       var params = "?id=" + ${form.id};
       openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
   }

   function depositMinBalances (elem, interestRate, minBalance)
   {
       this.elem         = elem;
       this.interestRate = interestRate;
       this.minBalance   = minBalance;
       this.current      = elem.checked;
   }

   var depositMinBalancesContainer =
   {
       array: [],

       add: function (elem, interestRate, minBalance)
       {
           this.array.push(new depositMinBalances(elem, interestRate, minBalance));
       },

       findSelected : function()
       {
           for (var i=0; i<this.array.length; i++)
           {
               var item = this.array[i];
               if (item.elem.checked)
               {
                   return item;
               }
           }

           return null;
       },

       findDepositMinBalanceByElem : function(elem)
       {
           for (var i=0; i<this.array.length; i++)
           {
               var item = this.array[i];
               if (item.elem.id == elem.id)
               {
                   return item;
               }
           }

           return null;
       }
   };

   function saveDepositMinBalance(accountId)
   {
       var windowLocation = "${phiz:calculateActionURL(pageContext, '/private/payments/payment')}";
       var selMinBalance  = depositMinBalancesContainer.findSelected();

       window.location = windowLocation + "?form=ChangeDepositMinimumBalanceClaim&accountId=" + accountId + "&interestRate=" + selMinBalance.interestRate + "&minDepositBalance=" + selMinBalance.minBalance;
   }

    function openPrintBankDetails(event)
    {
        var url = "${phiz:calculateActionURL(pageContext,'/private/accounts/bankDetails.do')}";
        var params = "?id=" + ${form.id};
        openWindow(event, url + params, "PrintBankDetails", "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1024");
    }

   $(document).ready(function() {
       if (depositMinBalancesContainer.array.length == 0)
       {
           return;
       }

       depositMinBalancesContainer.array[0].elem.checked = 'checked';
   });
</script>
<c:set var="showBottomData" value="false" scope="request"/>
<c:set var="showInMainCheckBox" value="true" scope="request"/>
<c:set var="detailsPage" value="true" scope="request"/>
<c:set var="showPFRHistoryFull" value="${phiz:impliesService('PFRHistoryFullService')}"/>
<c:set var="showPFRHistoryFull" value="${showPFRHistoryFull and phiz:isPensionPlusNotClosedNotBlockedAccount(accountLink.id)}"/>

<c:set var="nameOrNumber">
    <c:choose>
        <c:when test="${not empty target}">
            «${target.name}»
        </c:when>
        <c:when test="${not empty accountLink.name}">
            «${accountLink.name}»
        </c:when>
        <c:otherwise>
            ${phiz:getFormattedAccountNumber(account.number)}
        </c:otherwise>
    </c:choose>
</c:set>

<c:set var="pattern">
    <c:choose>
        <c:when test="${not empty accountLink.name}">
            «${accountLink.patternForFavouriteLink}»
        </c:when>
        <c:otherwise>
            ${accountLink.patternForFavouriteLink}
        </c:otherwise>
   </c:choose>
</c:set>

<div class="abstractContainer3 <c:if test='${not empty target}'>targetFavouriteLink</c:if>">
    <c:set var="baseInfo" value="${type} "/>
    <div class="favouriteLinksButton">
        <tiles:insert definition="addToFavouriteButton" flush="false">
            <tiles:put name="formName"><c:out value='${baseInfo}${nameOrNumber}'/></tiles:put>
            <tiles:put name="patternName"><c:out value='${baseInfo}${pattern}'/></tiles:put>
            <tiles:put name="typeFormat">ACCOUNT_LINK</tiles:put>
            <tiles:put name="productId">${form.id}</tiles:put>
        </tiles:insert>
    </div>
</div>

<div class="clear"></div>

<c:choose>
   <c:when test="${not empty target}">
       <c:set var="isDetailInfoPage" value="true" scope="request"/>
       <%@ include file="targetTemplate.jsp"%>
   </c:when>
   <c:otherwise>
       <%@ include file="accountTemplate.jsp"%>
   </c:otherwise>
</c:choose>
<br/>
<div class="tabContainer">
    <tiles:insert definition="paymentTabs" flush="false">
        <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('account')}"/>
        <c:set var="count">
            <c:choose>
                <c:when test="${not empty target}">
                    <c:out value="2"/>
                </c:when>
                <c:when test="${!showTemplates}">
                    <c:out value="3"/>
                </c:when>
                <c:otherwise>
                    <c:out value="4"/>
                </c:otherwise>
            </c:choose>
        </c:set>
        <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
            <c:set var="count" value="${count+1}"/>
        </c:if>
        <tiles:put name="count" value="${count}"/>
        <tiles:put name="tabItems">
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="first"/>
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Последние операции"/>
                <tiles:put name="action" value="/private/accounts/operations?id=${accountLink.id}"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="true"/>
                <tiles:put name="title" value="Информация по вкладу"/>
                <tiles:put name="action" value="/private/accounts/info.do?id=${accountLink.id}"/>
            </tiles:insert>
            <c:if test="${empty target}">
                <c:if test="${showTemplates}">
                    <tiles:insert definition="paymentTabItem" flush="false">
                        <tiles:put name="active" value="false"/>
                        <tiles:put name="title" value="Шаблоны и платежи"/>
                        <tiles:put name="action" value="/private/account/payments.do?id=${accountLink.id}"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="position" value="last"/>
                    <tiles:put name="active" value="false"/>
                    <tiles:put name="title" value="Графическая выписка"/>
                    <tiles:put name="action" value="/private/accounts/graphic-abstract.do?id=${accountLink.id}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="position" value="last"/>
                    <tiles:put name="active" value="false"/>
                    <tiles:put name="title">
                        <div style="float:left">
                            <div style="display:inline-block ;vertical-align:middle;"><c:out value="Копилка"/></div>
                            <div class="il-newIconMoneyBoxSmall" style="display:inline-block ;vertical-align:middle;"></div>
                        </div>
                    </tiles:put>
                    <tiles:put name="action" value="/private/accounts/moneyBoxList.do?id=${accountLink.id}"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
    <div class="abstractFilter">

        <c:if test="${showPFRHistoryFull}">
            <%--Окно с фильтром для расширенной выписки ПФР--%>
            <c:set var="abstractUrl" value="${phiz:calculateActionURL(pageContext,'/private/pfr/pfrHistoryFull.do?isWindow=true&fromResource=account:')}"/>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="pfrHistoryFull"/>
                <tiles:put name="loadAjaxUrl" value="${abstractUrl}${accountLink.id}"/>
            </tiles:insert>
        </c:if>

        <c:if test="${empty target}">
            <div class="productTitleDetailInfo">
                <div id="productNameText" name="productNameText" class="word-wrap">
                    <span class="productTitleDetailInfoText">
                        <c:out value="${form.fields.accountName}"/>
                        <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                    </span>
                </div>
                <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                    <html:text property="field(accountName)" size="30" maxlength="56" styleId="fieldAccountName" styleClass="productTitleDetailInfoEditTextBox"/>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.saveAccountName"/>
                        <tiles:put name="commandHelpKey" value="button.saveAccountName.help"/>
                        <tiles:put name="bundle" value="accountInfoBundle"/>
                    </tiles:insert>
                    <div class="errorDiv clear" style="display:none;"></div>
                </div>
                <div class="product-type uppercase">
                    <bean:message key="title.account.type" bundle="accountInfoBundle"/> ${accountLink.description}
                </div>
            </div>
            <script type="text/javascript">
                function showEditProductName() {
                    $("#productNameText").hide();
                    $("#productNameEdit").show();
                    $("#fieldAccountName")[0].selectionStart = $("#fieldAccountName")[0].selectionEnd = $("#fieldAccountName").val().length;
                }
                function openPfrHistoryFull(){
                    win.open('pfrHistoryFull');
                    $('.form-row.error:first').removeClass('error').addClass('active-help');
                    removeAllErrors('errMessagesBlock');
                    $('#failTextMessage').hide();
                    $('#startTextMessage').show();
                }
            </script>
        </c:if>

        <div class="clear"></div>
        <div class="abstractContainer2">
            <%--Реквизиты перевода на счет вклада--%>
            <c:if test="${phiz:isVSPOffice(account.office)}">
                <div class="inlineBlock">
                    <tiles:insert definition="clientButton" flush="false" service="AccountBankDetailsService">
                        <tiles:put name="commandTextKey" value="button.printDetails"/>
                        <tiles:put name="commandHelpKey" value="button.printDetails.help"/>
                        <tiles:put name="bundle" value="accountInfoBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="image"    value="revizity.gif"/>
                        <tiles:put name="imageHover"    value="revizityHover.gif"/>
                        <tiles:put name="imagePosition" value="left"/>
                        <tiles:put name="onclick" value="openPrintBankDetails(event);"/>
                    </tiles:insert>
                </div>
            </c:if>
            <div class="inlineBlock">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.calc"/>
                    <tiles:put name="commandHelpKey" value="button.calc.help"/>
                    <tiles:put name="bundle" value="accountInfoBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="openWindow(event, 'http://www.sberbank.ru/ru/person/contributions/deposits/');"/>
                    <tiles:put name="image"   value="calculator.gif"/>
                    <tiles:put name="imageHover"     value="calculatorHover.gif"/>
                    <tiles:put name="imagePosition" value="left"/>
                </tiles:insert>
            </div>
            <div class="printButtonRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print"/>
                    <tiles:put name="commandHelpKey" value="button.print.help"/>
                    <tiles:put name="bundle" value="accountInfoBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="image"    value="print-check.gif"/>
                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                    <tiles:put name="imagePosition" value="left"/>
                    <tiles:put name="onclick">openPrintAccountInfo(event);</tiles:put>
                </tiles:insert>
            </div>
            <div class="clear"></div>
            <c:if test="${showPFRHistoryFull}">
                <div class="secondLineBtn">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.PFRHistoryFull"/>
                        <tiles:put name="commandHelpKey" value="button.PFRHistoryFull.help"/>
                        <tiles:put name="bundle" value="pfrBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="image" value="pfr-reference.gif"/>
                        <tiles:put name="imageHover" value="pfr-reference-hover.gif"/>
                        <tiles:put name="imagePosition" value="left"/>
                        <tiles:put name="onclick">openPfrHistoryFull();</tiles:put>
                    </tiles:insert>
                </div>
            </c:if>
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
    <div class="hasLayout accountProductInfo" onkeypress="onEnterKey(event)">
        <fieldset>
            <table class="additional-product-info firstColumnFix">
                <c:if test="${not empty target}">
                    <tr>
                        <td class="align-left grayBlockTitle">Параметры цели</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="align-right field">Название цели:</td>
                        <td>
                            <html:hidden name="form" property="field(dictionaryTarget)"/>
                            <c:choose>
                                <c:when test="${target.dictionaryTarget == 'OTHER'}">
                                    <html:text name="form" property="field(targetName)" size="25"/>
                                </c:when>
                                <c:otherwise>
                                    <span class="word-wrap bold"><c:out value="${target.name}"/></span>
                                </c:otherwise>
                            </c:choose>
                            <br/><br/>
                            <html:text name="form" property="field(targetNameComment)" styleClass="customPlaceholder" title="введите комментарий" size="25"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-right field">Дата достижения:</td>
                        <td>
                            <input type="text" name="field(targetPlanedDate)" class="date-pick dp-applied" size="10"
                                  value="<bean:write name="org.apache.struts.taglib.html.BEAN" property="field(targetPlanedDate)" format="dd/MM/yyyy"/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-right field">Сумма цели:</td>
                        <td>
                            <html:text name="form" property="field(targetAmount)" styleClass="moneyField"/>
                            <bean:message key="currency.rub" bundle="financesBundle"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="dottedBorderTop align-left grayBlockTitle">Параметры вклада</td>
                        <td class="dottedBorderTop"></td>
                    </tr>
                </c:if>

                <tr>
                    <td class="align-right field fixColumn">Название типа вклада:</td>
                    <td><span class="word-wrap">${accountLink.description}</span></td>
                </tr>
                <tr>
                    <td class="align-right field">Сумма вклада:</td>
                    <td>
                        <span class="bold">
                            ${phiz:formatAmount(account.balance)}
                        </span>
                    </td>
                </tr>
                <c:set var="emptyPeriod" value="00-00-000"/>
                <tr>
                    <td class="align-right field">Срок вклада:</td>
                    <td>
                        <span class="bold">
                            <c:choose>
                                <c:when test="${not empty account.period && account.period != emptyPeriod}">
                                    <c:out value="${phiz:getFormatedPeriod(account.period)}"/>
                                </c:when>
                                <c:otherwise>
                                    <bean:message key="label.termless" bundle="accountInfoBundle"/>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Процентная ставка:</td>
                    <td>
                        <c:if test="${!empty account.interestRate && account.interestRate >= 0}">
                            <span class="bold"><bean:write name="account" property="interestRate" format="0.00"/>&nbsp;%</span>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Номер счета вклада:</td>
                    <td><span class="bold">${phiz:getFormattedAccountNumber(account.number)}</span></td>
                </tr>

                <tr>
                    <td class="align-right field">Сумма неснижаемого остатка:</td>
                    <td>
                        <c:if test="${not empty account.minimumBalance}">
                           <span class="bold">
                                   ${phiz:formatAmount(account.minimumBalance)}
                           </span>
                        </c:if>
                    </td>
                </tr>

                <c:if test="${phiz:impliesService('ChangeDepositMinimumBalanceClaim')}">
                    <c:set var="minBalances" value="${phiz:getMinBalancesToChange(accountLink)}"/>

                    <c:if test="${not empty minBalances}">
                            <tr>
                                <td class="align-right field"></td>
                                <td class="active-help" style="border-width: 0">
                                    <div>
                                        <div style="margin: 10px">Увеличьте сумму неснижаемого остатка &mdash; получите большую процентную ставку.</div>

                                        <fieldset>
                                            <table>
                                                <c:forEach var="item" varStatus="status" items="${minBalances}">
                                                    <c:set var="minBalance"   value="${item.first}"/>
                                                    <c:set var="interestRate" value="${item.second}"/>
                                                    <tr>
                                                        <td></td>

                                                        <td class="align-left">
                                                            <input id="interestRate${status.count}" name="depositMinBalances" type="radio"/>
                                                        </td>

                                                        <td class="align-right">
                                                            <label for="interestRate${status.count}">
                                                                ${phiz:formatDecimalToAmount(minBalance, 2)} &mdash; ${interestRate} % <i>${interestRate == account.interestRate ? 'Текущая ставка' : ''}</i>
                                                            </label>
                                                        </td>
                                                    </tr>

                                                    <script type="text/javascript">
                                                        depositMinBalancesContainer.add(document.getElementById('interestRate${status.count}'), '${interestRate}', '${minBalance}');
                                                    </script>
                                                </c:forEach>
                                            </table>
                                        </fieldset>

                                        <div style="margin: 10px; clear: both">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="btnId"          value="btnChangeDepMinBalance"/>
                                                <tiles:put name="commandTextKey" value="button.changeDepositMinBalance"/>
                                                <tiles:put name="commandHelpKey" value="button.changeDepositMinBalance.help"/>
                                                <tiles:put name="bundle"         value="claimsBundle"/>
                                                <tiles:put name="onclick"        value="saveDepositMinBalance('${accountLink.id}')"/>
                                            </tiles:insert>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                    </c:if>
                </c:if>

                <tr>
                    <td class="align-right field">Максимальная сумма снятия:</td>
                    <td>
                        <c:if test="${not empty account.maxSumWrite}">
                           <span class="bold">
                                   ${phiz:formatAmount(account.maxSumWrite)}
                           </span>
                            <a class="imgHintBlock" title="Часто задаваемые вопросы" onclick="openFAQ('${faqLink}')" ></a>
                        </c:if>
                    </td>
                </tr>

                <c:if test="${not empty account.maxBalance}">
                    <c:set var="maxBalance" value="${phiz:formatDecimalToAmount(account.maxBalance, 2)}"/>

                    <c:if test="${maxBalance != '0,00'}">
                        <tr>
                            <td class="align-right field">Максимальная сумма вклада:</td>
                            <td>
                                <span class="bold inlineBlock" >
                                    ${maxBalance}&nbsp;${phiz:getCurrencySign(account.currency.code)}
                                </span>
                                <div class="inlineBlock">
                                    <tiles:insert definition="floatMessageShadow" flush="false">
                                        <tiles:put name="id" value="maxBalance-pupupHelp"/>
                                        <tiles:put name="hintClass" value=""/>
                                        <tiles:put name="data"><a href="#" class="imgHintBlock" onclick="return false;"></a></tiles:put>
                                        <tiles:put name="showHintImg" value="false"/>
                                        <tiles:put name="text">${form.accountMaxBalanceMessage}</tiles:put>
                                        <tiles:put name="dataClass" value="dataHint"/>
                                    </tiles:insert>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </c:if>

                <tr>
                    <td class="align-right field">Порядок уплаты процентов:</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty account.interestTransferCard}">
                                <c:set var="interestCard" value="${phiz:getCardLink(account.interestTransferCard)}"/>
                                <span class="bold">
                                    на счет банковской карты <c:out value="${phiz:getCutCardNumber(account.interestTransferCard)}"/>&nbsp;[&nbsp;<c:out value="${interestCard.description}"/>&nbsp;]&nbsp;<c:out
                                        value="${phiz:getCurrencyName(interestCard.currency)}"/>
                                </span>
                            </c:when>
                            <c:when test="${not empty account.interestTransferAccount}">
                                <c:set var="interestAccount" value="${phiz:getAccountLink(account.interestTransferAccount)}"/>
                                <span class="bold">
                                    на вкладной счет  <c:out value="${phiz:getFormattedAccountNumber(account.interestTransferAccount)}"/>&nbsp;<c:out
                                        value="${phiz:getCurrencyName(interestAccount.currency)}"/>
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="bold">
                                    капитализация процентов на счете по вкладу
                                </span>
                            </c:otherwise>
                        </c:choose>

                        <c:set var="isImpliesChangeInterestDestService" value="${phiz:impliesServiceRigid('AccountChangeInterestDestinationClaim')}"/>
                        <c:set var="availableCapitalization" value="${phiz:isAvailableCapitalizationByProductId(account.kind)}"/>
                        <c:set var="isEmptyInterestCard" value="${empty account.interestTransferCard}"/>
                        <c:set var="haveCardsWithTBLogined" value="${phiz:haveCardsWithTBLogined()}"/>

                        <c:if test="${isImpliesChangeInterestDestService
                        and availableCapitalization and account.kind ne 61
                        and (not isEmptyInterestCard or haveCardsWithTBLogined)
                        and (account.accountState=='OPENED' or account.accountState=='LOST_PASSBOOK' or account.accountState=='ARRESTED')}">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.editInterestResource"/>
                                <tiles:put name="commandHelpKey" value="button.editInterestResource.help"/>
                                <tiles:put name="bundle" value="accountInfoBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                                <tiles:put name="image" value="change.gif"/>
                                <tiles:put name="imageHover" value="change-hover.gif"/>
                                <tiles:put name="imagePosition" value="right"/>
                                <tiles:put name="action" value="/private/payments/payment.do?form=AccountChangeInterestDestinationClaim&accountCode=account:${accountLink.id}"/>
                            </tiles:insert>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Текущее состояние:</td>
                    <td>
                       <span class="bold">
                           <c:choose>
                               <c:when test="${account.accountState == 'OPENED' or account.accountState == 'LOST_PASSBOOK'}">
                                   Действующий
                               </c:when>
                               <c:otherwise>
                                   Закрытый
                               </c:otherwise>
                           </c:choose>
                       </span>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Пролонгация:</td>
                    <td>
                        <c:if test="${!empty account.prolongationAllowed}">
                           <span class="bold">
                               <c:choose>
                                   <c:when test="${account.prolongationAllowed}">
                                       осуществляется
                                   </c:when>
                                   <c:otherwise>
                                       не осуществляется
                                   </c:otherwise>
                               </c:choose>
                           </span>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${not empty account.period && account.period != emptyPeriod}">
                    <tr>
                        <td class="align-right field">Дата открытия:</td>
                        <td>
                            <c:if test="${!empty account.openDate}">
                               <span class="bold">
                                   <bean:write name="account" property="openDate.time" format="dd.MM.yyyy"/>
                               </span>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="align-right field">Дата окончания срока действия:</td>
                    <td>
                        <c:if test="${not empty account.demand}">
                             <c:choose>
                                 <c:when test="${not account.demand}">
                                     <c:if test="${not empty account.closeDate}">
                                         <span class="bold inlineBlock">
                                             <bean:write name="account" property="closeDate.time" format="dd.MM.yyyy"/>
                                         </span>
                                         <c:if test="${phiz:isTodayCloseDayAndHoliday(account.closeDate)}">
                                             <div class="inlineBlock">
                                                 <tiles:insert definition="floatMessageShadow" flush="false">
                                                     <tiles:put name="id" value="closeDate-pupupHelp"/>
                                                     <tiles:put name="hintClass" value=""/>
                                                     <tiles:put name="data"><a href="#" class="imgHintBlock" onclick="return false;"></a></tiles:put>
                                                     <tiles:put name="showHintImg" value="false"/>
                                                     <tiles:put name="text">${form.accountCloseDateDisclaimMessage}</tiles:put>
                                                     <tiles:put name="dataClass" value="dataHint"/>
                                                 </tiles:insert>
                                             </div>
                                         </c:if>
                                     </c:if>
                                 </c:when>
                                 <c:otherwise>
                                     <span class="bold">
                                          бессрочный
                                     </span>
                                 </c:otherwise>
                             </c:choose>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Сберкнижка:</td>
                    <td>
                        <c:if test="${not empty account.passbook}">
                           <span class="bold">
                               <c:choose>
                                   <c:when test="${account.passbook}">
                                       Выдавалась
                                   </c:when>
                                   <c:otherwise>
                                       Не выдавалась
                                   </c:otherwise>
                               </c:choose>
                           </span>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Списание:</td>
                    <td>
                        <c:if test="${not empty account.debitAllowed}">
                           <span class="bold">
                               <c:choose>
                                   <c:when test="${account.debitAllowed}">
                                       разрешено
                                   </c:when>
                                   <c:otherwise>
                                       запрещено
                                   </c:otherwise>
                               </c:choose>
                           </span>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">Зачисление:</td>
                    <td>
                        <c:if test="${not empty account.creditAllowed}">
                           <span class="bold">
                               <c:choose>
                                   <c:when test="${account.creditAllowed}">
                                       разрешено
                                   </c:when>
                                   <c:otherwise>
                                       запрещено
                                   </c:otherwise>
                               </c:choose>
                           </span>
                        </c:if>
                    </td>
                </tr>

                <tr>
                    <td class="align-right field">"Зеленая улица":</td>
                    <td>
                        <c:if test="${not empty account.creditCrossAgencyAllowed and not empty account.debitCrossAgencyAllowed}">
                           <span class="bold">
                           <c:choose>
                               <c:when test="${account.creditCrossAgencyAllowed and account.debitCrossAgencyAllowed}">
                                   разрешены расходные операции/разрешены приходные операции
                               </c:when>
                               <c:when test="${account.creditCrossAgencyAllowed}">
                                   разрешены расходные операции
                               </c:when>
                               <c:when test="${account.debitCrossAgencyAllowed}">
                                   разрешены приходные операции
                               </c:when>
                               <c:otherwise>
                                   операции запрещены
                               </c:otherwise>
                           </c:choose>
                           </span>
                        </c:if>
                    </td>
                </tr>

                <%--СМС-алиас вклада, если у клиента есть ЕРМБ-профиль--%>
                <c:if test="${isERMBConnectedPerson == true}">
                    <tr class="form-row">
                        <td class="align-right valign-top">
                            <span class="paymentTextLabel">SMS-идентификатор</span>:
                        </td>
                        <td>
                            <html:text property="field(clientSmsAlias)" size="30" maxlength="20"/>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.saveClientSmsAlias"/>
                                <tiles:put name="commandHelpKey" value="button.saveClientSmsAlias.help"/>
                                <tiles:put name="bundle" value="accountInfoBundle"/>
                            </tiles:insert>
                            <div class="errorDiv" style="display:none;"></div>
                        </td>
                    </tr>
                    <tr class="form-row">
                        <td class="align-right valign-top">
                            <span class="paymentTextLabel">Автоматический SMS-идентификатор</span>:
                        </td>
                        <td>
                           <span class="bold">
                               <c:out value="${form.fields.autoSmsAlias}"/>
                           </span>
                        </td>
                    </tr>
                </c:if>
            </table>
        </fieldset>
    </div>
</div>
<div class="clear"></div>

<c:if test="${not empty target}">
   <div class="buttonsArea">
       <tiles:insert definition="clientButton" flush="false">
           <tiles:put name="commandTextKey" value="button.cancel"/>
           <tiles:put name="commandHelpKey" value="button.cancel.help"/>
           <tiles:put name="bundle" value="accountInfoBundle"/>
           <tiles:put name="viewType" value="simpleLink"/>
           <tiles:put name="action" value="/private/accounts/info.do?id=${accountLink.id}"/>
       </tiles:insert>
       <tiles:insert definition="commandButton" flush="false">
           <tiles:put name="isDefault" value="true"/>
           <tiles:put name="commandKey" value="button.saveTargetParams"/>
           <tiles:put name="commandHelpKey" value="button.saveTargetParams.help"/>
           <tiles:put name="bundle" value="accountInfoBundle"/>
       </tiles:insert>
   </div>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.back.to.targetList"/>
        <tiles:put name="commandHelpKey" value="button.back.to.targetList.help"/>
        <tiles:put name="bundle" value="financesBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <tiles:put name="action" value="/private/finances/targets/targetsList"/>
    </tiles:insert>
</c:if>

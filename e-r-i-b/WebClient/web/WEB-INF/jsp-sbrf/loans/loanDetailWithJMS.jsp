<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/loans/detail" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="loanLink" value="${form.loanLink}" scope="request"/>
<c:set var="loan" value="${loanLink.loan}" scope="request"/>
<c:set var="loanOffice" value="${loan.office}" scope="request"/>
<c:set var="scheduleAbstract" value="${form.scheduleAbstract}" scope="request"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="detailsPage" value="true"/>
<c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<c:set var="delimiter" value=", "/>
<c:set var="isOverdue" value="${not empty loan.nextPaymentDate and loan.state == 'overdue'}"/>
<c:set var="paymentDate" value="${phiz:formatDayWithStringMonth(loan.closestPaymentDate)}"/>

<c:forEach items="${form.loanAccountInfo}" var="acct" varStatus="statusCount">
    <c:choose>
        <c:when test="${statusCount.first}">
            <c:set var="accountAmount" value="${acct.amount}"/>
        </c:when>
        <c:otherwise>
            <c:set var="accountAmount"  value="${phiz:getMoneyOperation(acct.amount, accountAmount, '+')}"/>
        </c:otherwise>
    </c:choose>
</c:forEach>

<c:if test="${not empty form.loanAccountInfo}">
    <c:set var="chooseToResource" value="${form.loanAccountInfo[0].nameToPay}"/>
</c:if>

<c:if test="${not empty accountAmount}">
    <c:set var="isEnough" value="${phiz:compareMoney(accountAmount, loan.recPayment) >= 0}"/>
    <c:set var="needMoney" value="${phiz:getMoneyOperation(loan.recPayment, accountAmount, '-')}"/>
</c:if>

<tiles:insert definition="accountInfo">
<tiles:put name="mainmenu" value="Loans"/>
<tiles:put name="menu" type="string"/>

<tiles:put name="breadcrumbs">
    <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Кредиты"/>
            <tiles:put name="action" value="/private/loans/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:write name="loanLink" property="name"/> ${phiz:formatAmount(loanLink.loan.loanAmount)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
<div id="cards">
    <div id="card-detail">
        <c:if test="${loanLink != null}">
            <jsp:include page="annLoanMessageWindow.jsp" flush="false"/>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <c:set var="showInMainCheckBox" value="${phiz:impliesService('ReceiveLoansOnLogin')}"/>
                    <c:set var="bottomDataInfo" value="false"/>
                    <c:set var="loanLinkName">
                        «${loanLink.name}»
                    </c:set>
                    <div class="abstractContainer3">
                        <c:set var="baseInfo">
                            <bean:message key="favourite.link.loan" bundle="paymentsBundle"/>
                        </c:set>
                        <div class="favouriteLinksButton">
                            <tiles:insert definition="addToFavouriteButton" flush="false">
                                <tiles:put name="formName"><c:out value='${baseInfo} ${loanLinkName}'/></tiles:put>
                                <tiles:put name="patternName"><c:out value='${baseInfo} «${loanLink.patternForFavouriteLink}»'/></tiles:put>
                                <tiles:put name="typeFormat">LOAN_LINK</tiles:put>
                                <tiles:put name="productId">${form.id}</tiles:put>
                            </tiles:insert>
                        </div>
                    </div>
                     <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplateWithJMS.jsp"%>
                    <div class="tabContainer">
                        <c:if test="${not scheduleAbstract.isAvailable}">
                            <c:set var="position" value="-last"/>
                        </c:if>
                        <tiles:insert definition="paymentTabs" flush="false">
                            <tiles:put name="count" value="3"/>
                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first${position}"/>
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="title" value="Детальная информация"/>
                                    <tiles:put name="action" value="/private/loans/detail?id=${loanLink.id}"/>
                                </tiles:insert>
                                <c:if test="${scheduleAbstract.isAvailable}">
                                    <tiles:insert definition="paymentTabItem" flush="false">
                                        <tiles:put name="position" value="last"/>
                                        <tiles:put name="active" value="false"/>
                                        <tiles:put name="title" value="График платежей"/>
                                        <tiles:put name="action" value="/private/loans/info.do?id=${loanLink.id}"/>
                                    </tiles:insert>
                                </c:if>
                                <c:if test="${phiz:impliesService('EarlyLoanRepaymentClaim') && form.earlyLoanRepaymentPossible}">
                                    <tiles:insert definition="paymentTabItem" flush="false">
                                        <tiles:put name="position" value="irrelevant"/>
                                        <tiles:put name="active" value="false"/>
                                        <tiles:put name="title" value="Досрочное погашение"/>
                                        <tiles:put name="action" value="/private/loans/earlyloanrepayment.do?id=${loanLink.id}"/>
                                    </tiles:insert>
                                </c:if>
                            </tiles:put>
                        </tiles:insert>

                        <div class="productTitleDetailInfo">
                            <div id="productNameText" name="productNameText" class="word-wrap">
                                <span class="productTitleDetailInfoText">
                                    <c:out value="${form.fields.loanName}"/>
                                    <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                                </span>
                            </div>
                            <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                                <html:text property="field(loanName)" size="30" maxlength="56" styleId="fieldLoanName" styleClass="productTitleDetailInfoEditTextBox"/>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.saveLoanName"/>
                                    <tiles:put name="commandHelpKey" value="button.saveLoanName.help"/>
                                    <tiles:put name="bundle" value="loansBundle"/>
                                </tiles:insert>
                                <div class="errorDiv clear" style="display:none;"></div>
                            </div>
                            <div class="product-type uppercase">
                                <bean:message key="title.loan.type" bundle="loansBundle"/> ${loan.loanType}
                            </div>
                        </div>
                        <script type="text/javascript">
                         function showEditProductName() {
                             $("#productNameText").hide();
                             $("#productNameEdit").show();
                             $("#fieldLoanName")[0].selectionStart = $("#fieldLoanName")[0].selectionEnd = $("#fieldLoanName").val().length;
                         }
                        </script>

                        <div class="abstractContainer2">
                            <div class="inlineBlock">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.show.tarif"/>
                                    <tiles:put name="commandHelpKey" value="button.show.tarif.help"/>
                                    <tiles:put name="bundle"         value="loansBundle"/>
                                    <tiles:put name="onclick"        value="openWindow(event, 'http://www.sberbank.ru/ru/person/credits/');"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="revizity.gif"/>
                                    <tiles:put name="imageHover" value="revizityHover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                </tiles:insert>
                            </div>
                            <div class="inlineBlock">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.show.calculator"/>
                                    <tiles:put name="commandHelpKey" value="button.show.calculator.help"/>
                                    <tiles:put name="bundle"         value="loansBundle"/>
                                    <tiles:put name="onclick"  value="openWindow(event, 'http://www.sberbank.ru/ru/person/credits');"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="calculator.gif"/>
                                    <tiles:put name="imageHover" value="calculatorHover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                </tiles:insert>
                            </div>
                            <c:if test="${not empty form.applicationNumber}">
                                <c:set var="oneTimePassword" value="${form.oneTimePassword}"/>
                                <div class="inlineBlock">
                                    <c:choose>
                                        <c:when test="${oneTimePassword}">
                                            <%--введён одноразовый пароль при входе--%>
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.loan.conditions"/>
                                                <tiles:put name="commandHelpKey" value="button.loan.conditions.help"/>
                                                <tiles:put name="bundle"         value="loansBundle"/>
                                                <tiles:put name="onclick">loanConditions()</tiles:put>
                                                <tiles:put name="viewType" value="buttonGrey"/>
                                                <tiles:put name="image" value="rekvizity.gif"/>
                                                <tiles:put name="imageHover" value="rekvizityHover.gif"/>
                                                <tiles:put name="imagePosition" value="left"/>
                                            </tiles:insert>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${not empty form.confirmableObject}">
                                                <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
                                                <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
                                            </c:if>
                                            <%--если пароль не вводился, то просмотр подтверждаем--%>
                                            <div>
                                                <tiles:insert definition="confirmOfferButton" flush="false">
                                                    <tiles:put name="ajaxUrl" value="/private/async/credit/offert/text/confirm"/>
                                                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                    <tiles:put name="preConfirmCommandKey" value="button.confirmTextOffert"/>
                                                    <tiles:put name="messageBundle" value="loansBundle"/>
                                                    <tiles:put name="needShowAnotherStrategy" value="false"/>
                                                    <tiles:put name="viewTypeConfirmSMS" value="blueGrayLinkDotted"/>
                                                    <tiles:put name="imageSms" value="rekvizity.gif"/>
                                                    <tiles:put name="imageSmsHover" value="rekvizityHover.gif"/>
                                                    <tiles:put name="imageSmsPosition" value="left"/>
                                                </tiles:insert>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:if>
                            <div class="printButtonRight">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.print"/>
                                    <tiles:put name="commandHelpKey" value="button.print.help"/>
                                    <tiles:put name="bundle"         value="loansBundle"/>
                                    <tiles:put name="onclick">printLoanInfo(event)</tiles:put>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="print-check.gif"/>
                                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                </tiles:insert>
                            </div>
                        </div>

                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="loanConditionsWin"/>
                            <tiles:put name="data">
                                <html:hidden property="id"/>
                                <div class="title">
                                    <h1>Подтверждение просмотра условий кредитования</h1>
                                </div>

                                <div class="info">
                                    Внимание. В тексте индивидуальных условий кредитования будут указаны Ваши персональные данные, в целях предотвращения мошеннических действий введите пароль из SMS-сообщения для просмотра условий. Убедитесь, что реквизиты выполняемой операции совпадают с текстом SMS-сообщения. Будьте осторожны, никому не сообщайте пароль
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <div class="clear"></div>

                        <fieldset>
                            <table class="productMainDetail">
                                <tr>
                                    <th colspan="3">Текущий платёж</th>
                                </tr>
                                <tr>
                                    <td class="field fixColumn">Платёж ${paymentDate}</td>
                                    <td><span class="word-wrap">${phiz:productFormatAmount(loan.recPayment)}</span></td>
                                    <td class="" style="width:177px"></td>
                                </tr>
                                <tr class="size13">
                                    <td class="field">Основной долг</td>
                                    <td><span class="word-wrap">${phiz:formatAmount(loan.mainDebt)}</span></td>
                                    <td class="" style="width:177px"></td>
                                </tr>
                                <tr class="size13">
                                    <td class="field">Выплаты по процентам</td>
                                    <td><span class="word-wrap">${phiz:formatAmount(loan.interestPayments)}</span></td>
                                    <td class="" style="width:177px"></td>
                                </tr>
                                <c:if test="${isOverdue}">
                                    <tr>
                                        <td class="field fixColumn">Просроченная задолжность по основному долгу</td>
                                        <td><span class="word-wrap">${phiz:productFormatAmount(loan.overdueMainDebts)}</span></td>
                                        <td class="" style="width:177px"></td>
                                    </tr>
                                    <tr>
                                        <td class="field fixColumn">Просроченная задолжность по процентам</td>
                                        <td><span class="word-wrap">${phiz:productFormatAmount(loan.overdueInterestDebts)}</span></td>
                                        <td class="" style="width:177px"></td>
                                    </tr>
                                </c:if>

                                <tr>
                                    <td colspan="2">
                                        <div class="payTools css3">
                                            <table class="payToolsData">
                                                <tbody>
                                                <tr>
                                                    <td>
                                                        Средства для погашения
                                                        <div class="inlineBlock">
                                                            <tiles:insert definition="hintMouseOverTemplate" flush="false">
                                                                <tiles:put name="id" value="fundsForPayment"/>
                                                                <tiles:put name="color" value="whiteHint"/>
                                                                <tiles:put name="data">
                                                                    Средства списываются со счетов в порядке приоритета. При нехватке средств на счете списание производится со следующего.
                                                                </tiles:put>
                                                            </tiles:insert>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <c:if test="${not empty accountAmount}">
                                                        ${phiz:productFormatAmountWithoutSpaces(accountAmount)}
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <c:forEach items="${form.loanAccountInfo}" var="acctInfo">
                                                    <tr class="size13">
                                                        <td>
                                                            <div class="payToolsProduct">${acctInfo.name}</div>
                                                        </td>
                                                        <td>${phiz:formatAmountWithoutSpaces(acctInfo.amount)}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </td>
                                    <td class="alignTop">
                                        <c:if test="${not empty accountAmount}">
                                            <c:choose>
                                                <c:when test="${isEnough}">
                                                    <p class="messDetailTxt">На привязанных к кредиту счетах достаточно средств для оплаты</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="messDetailTxtNotEnough">На привязанных к кредиту счетах не хватает ${phiz:productFormatAmountWithoutSpaces(needMoney)}</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.loan.fill.account"/>
                                            <tiles:put name="commandHelpKey" value="button.loan.fill.account.help"/>
                                            <tiles:put name="bundle"         value="loansBundle"/>
                                            <tiles:put name="onclick">fillAccount();</tiles:put>
                                            <tiles:put name="viewType" value="buttonWhite"/>
                                        </tiles:insert>
                                    </td>
                                </tr>
                             </table>
                        </fieldset>
                        <div class="clear"></div>
                        <fieldset>
                            <table class="productMainDetail">
                                <tr>
                                    <th colspan="3">О кредите</th>
                                </tr>
                                <tr>
                                    <td class="field fixColumn">Первоначальная сумма</td>
                                    <td><span class="word-wrap">${phiz:formatAmount(loan.loanAmount)}</span></td>
                                    <td></td>
                                </tr>
                                <tr class="beforeSize13">
                                    <td class="field">Осталось погасить</td>
                                    <td><span class="word-wrap">${phiz:formatAmount(loan.fullRepaymentAmount)}</span></td>
                                    <td></td>
                                </tr>
                                <tr class="size13">
                                    <td class="field">Сумма основного долга</td>
                                    <td><span class="word-wrap">${phiz:formatAmount(loan.mainDebtAmount)}</span></td>
                                    <td></td>
                                </tr>
                                <tr class="size13 afterSize13">
                                    <td class="field">Сумма выплат по процентам</td>
                                    <td><span class="word-wrap">${phiz:formatAmount(loan.interestPaymentsAmount)}</span></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Процентная ставка</td>
                                    <td><span class="word-wrap">${loan.creditingRate}%</span></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Дата открытия</td>
                                    <td><span class="word-wrap">${phiz:formatDateWithStringMonth(loan.termStart)}</span></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Срок окончания кредита</td>
                                    <td><span class="word-wrap">${phiz:formatDateWithStringMonth(loan.termEnd)}</span></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Номер кредитного договора</td>
                                    <td><span class="word-wrap">${loan.agreementNumber}</span></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Номер ссудного счёта</td>
                                    <td><span class="word-wrap">${loan.accountNumber}</span></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Способ погашения</td>
                                    <td>
                                        <span class="word-wrap">
                                            <c:choose>
                                                <c:when test="${loan.isAnnuity}">
                                                    Аннуитентный
                                                    <p class="productDetailDesc">Погашается равными долями</p>
                                                </c:when>
                                                <c:otherwise>
                                                    Дифференцированный
                                                    <p class="productDetailDesc">Погашение разными долями</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="field">Отделение обслуживания кредита</td>
                                    <td><span class="word-wrap">${loan.agencyAddress}</span></td>
                                    <td></td>
                                </tr>
                                <c:set var="newBorrower" value="${phiz:stringListToStringWithoutFinalDelimiter(loan.newBorrower, delimiter)}"/>
                                <c:if test="${not empty newBorrower}">
                                    <tr>
                                        <td class="field">Заемщик</td>
                                        <td><span class="word-wrap">${newBorrower}</span></td>
                                        <td></td>
                                    </tr>
                                </c:if>
                                <c:set var="newCoBorrower" value="${phiz:stringListToStringWithoutFinalDelimiter(loan.newCoBorrower, delimiter)}"/>
                                <c:if test="${not empty newCoBorrower}">
                                    <tr>
                                        <td class="field">Созаемщики</td>
                                        <td><span class="word-wrap">${newCoBorrower}</span></td>
                                        <td></td>
                                    </tr>
                                </c:if>
                                <c:set var="guarantor" value="${phiz:stringListToStringWithoutFinalDelimiter(loan.guarantor, delimiter)}"/>
                                <c:if test="${not empty guarantor}">
                                    <tr>
                                        <td class="field">Поручители</td>
                                        <td><span class="word-wrap">${guarantor}</span></td>
                                        <td></td>
                                    </tr>
                                </c:if>
                             </table>
                        </fieldset>
                    </div>
                </tiles:put>
            </tiles:insert>
        </c:if>
        <div class="clear"></div>
        <c:if test="${not empty form.anotherLoans}">
                <div id="another-cards">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <c:set var="loanCount" value="${phiz:getClientProductLinksCount('LOAN')}"/>
                        <tiles:put name="title">
                            Остальные кредиты
                            (<a href="${phiz:calculateActionURL(pageContext, '/private/loans/list')}" class="blueGrayLink">показать все ${loanCount}</a>)
                        </tiles:put>
                        <tiles:put name="data">
                            <div class="another-items">
                                <c:set var="loanInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/loans/detail.do?id=')}"/>
                                <c:forEach items="${form.anotherLoans}" var="others">
                                    <div class="another-container">
                                        <c:choose>
                                            <c:when test="${others.loan.isAnnuity}">
                                                <c:set var="loanImagePath" value="${image}/credit_type/icon_creditAnuitet32.jpg"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="loanImagePath" value="${image}/credit_type/icon_creditDiffer32.jpg"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <a href="${loanInfoUrl}${others.id}">
                                            <img src="${loanImagePath}" alt="${others.loan.description}"/>
                                        </a>
                                        <a class="another-number" href="${loanInfoUrl}${others.id}"><bean:write name="others" property="name"/></a>
                                        <div class="another-name">
                                            <a class="another-name decoration-none" href="${loanInfoUrl}${others.id}">${phiz:formatAmount(others.loan.loanAmount)}</a>
                                            <c:set var="state" value="${others.loan.state}"/>
                                            <c:set var="className">
                                                <c:if test="${state eq 'overdue' or state eq 'closed'}">
                                                    red
                                                </c:if>
                                            </c:set>

                                            <span class="${className}">
                                                <span class="prodStatus status" style="font-weight:normal;">
                                                    <c:if test="${not empty className}">
                                                        <nobr>(${state.description})</nobr>
                                                    </c:if>
                                                </span>
                                            </span>
                                        </div>
                                    </div>
                                </c:forEach>
                                &nbsp;
                                <div class="clear"></div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:if>
    </div>
</div>
</tiles:put>
</tiles:insert>
</html:form>

    <script type="text/javascript">
        doOnLoad(function(){
          <c:if test="${isOverdue}">
            var errorDiv = $("#errors");
            var newImg = document.createElement('img');
            newImg.setAttribute('src', "${image}/noticeAttention.png");
            newImg.setAttribute('class', "overdueLoanImg");
            errorDiv.find(".title").before( newImg );
            errorDiv.find(".title").text("<bean:message key="loan.overdue.message.title" bundle="loansBundle"/>");
            errorDiv.find(".messageContainer").text("<bean:message key="loan.overdue.message.text" bundle="loansBundle"/>");
            errorDiv.show();
          </c:if>
          <c:if test="${form.confirmEntered}">
            loanConditions();
          </c:if>
        });
    </script>

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
                     <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp"%>
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
                                <bean:message key="title.loan.type" bundle="loansBundle"/> ${loan.description}
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

                        <div class="clear"></div>

                        <fieldset>
                            <table class="additional-product-info firstColumnFix">
                                <tr>
                                    <td class="align-right field fixColumn">Тип кредита:</td>
                                    <td><span class="bold word-wrap">${loan.description}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Сумма кредита:</td>
                                    <td><span class="bold">${phiz:formatAmount(loan.loanAmount)}</span></td>
                                </tr>
                                <c:if test="${loan.rate != null}">
                                    <tr>
                                        <td class="align-right field">Процентная ставка:</td>
                                        <td><span class="bold">${loan.rate}%</span></td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td class="align-right field">Способ погашения:</td>
                                    <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${loan.isAnnuity}">
                                                    аннуитетный
                                                </c:when>
                                                <c:otherwise>
                                                    дифференцированный
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Кредит открыт:</td>
                                    <td><span class="bold">${phiz:formatDateWithStringMonth(loan.termStart)}</span></td>
                                </tr>
                                <c:if test="${loan.termDuration != null}">
                                    <tr>
                                        <td class="align-right field">Срок кредита:</td>
                                        <td><span class="bold">${phiz:getFormatedPeriod(loan.termDuration)}</span></td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td class="align-right field">Дата закрытия кредита:</td>
                                    <td><span class="bold">${phiz:formatDateWithStringMonth(loan.termEnd)}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">ФИО заемщика:</td>
                                    <td>
                                        <c:set var="client" value="${loan.borrower}"/>
                                        <c:if test="${not empty client}">
                                            <span class="bold"><c:out value="${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}"/></span>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Номер договора:</td>
                                    <td><span class="bold">${loan.agreementNumber}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Номер ссудного счета:</td>
                                    <td><span class="bold">${loan.accountNumber}</span></td>
                                </tr>
                                <tr>
                                    <td class="align-right field">Место оформления:</td>
                                    <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${!empty loanOffice.address}">
                                                    ${loanOffice.address}
                                                </c:when>
                                                <c:otherwise>
                                                    №&nbsp;
                                                        ${loanOffice.code.branch}/
                                                    ${loanOffice.code.office}
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </td>
                                </tr>
                                <%--СМС-алиас кредита, если у клиента есть ЕРМБ-профиль--%>
                                <c:if test="${isERMBConnectedPerson == true}">
                                    <tr class="form-row">
                                        <td class="align-right valign-top"><span class="paymentTextLabel">SMS-идентификатор</span>:</td>
                                        <td>
                                            <html:text property="field(clientSmsAlias)" size="30" maxlength="20" />
                                            <tiles:insert definition="commandButton" flush="false">
                                                <tiles:put name="commandKey" value="button.saveClientSmsAlias"/>
                                                <tiles:put name="commandHelpKey" value="button.saveClientSmsAlias.help"/>
                                                <tiles:put name="bundle" value="accountInfoBundle"/>
                                            </tiles:insert>
                                            <div class="errorDiv" style="display:none;"></div>
                                        </td>
                                    </tr>
                                    <tr class="form-row">
                                        <td class="align-right valign-top"><span class="paymentTextLabel">Автоматический SMS-идентификатор</span>:</td>
                                        <td>
                                            <span class="bold">
                                                <c:out value="${form.fields.autoSmsAlias}"/>
                                            </span>
                                        </td>
                                    </tr>
                                </c:if>
                             </table>
                        </fieldset>
                        <div class="clear"></div>
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

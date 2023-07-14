<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<script type="text/javascript">
    function openExtendedAbstract(event)
    {
        var url = "${phiz:calculateActionURL(pageContext,'/private/accounts/print.do')}";
        var params = "?sel=a:" + ${form.id};
        var typePeriod = "${form.filters.typePeriod}";
        if (typePeriod == 'period')
        {
            params = addParam2List(params, "filter(fromPeriod)", "fromDateString");
            params = addParam2List(params, "filter(toPeriod)", "toDateString");
        }
        else
        {
            params += "&typePeriod=" + typePeriod;
        }
        if ($('#ExtendedAbstractWindowOpened').val() != '0')
            return;
        $('#ExtendedAbstractWindowOpened').val('1');
        openWindow(event, url + params, "PrintAbstract", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
    }

    function openPrintAccountInfo(event, abstract)
    {

        var url = "${phiz:calculateActionURL(pageContext,'/private/accounts/printAcc.do')}";
        var params = "?id=" + ${form.id}+"&printAbstract=" + abstract;

        var typePeriod = "${form.filters.typePeriod}";
        if (typePeriod == 'period')
        {
            params = addParam2List(params, "filter(fromPeriod)", "fromDateString");
            params = addParam2List(params, "filter(toPeriod)", "toDateString");
        }
        else
        {
            params += "&typePeriod=" + typePeriod;
        }

        openWindow(event, url + params, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
    }

    function getTypePeriod()
    {
        var typePeriods = document.getElementsByName('filter(typePeriod)');
        var i = 0;
        while (typePeriods)
        {
            var el = typePeriods[i++];
            if (el.checked)
            {
                return el.value;
            }
        }
        return "";
    }

</script>

<c:set var="showBottomData" value="false" scope="request"/>
<c:set var="showInMainCheckBox" value="true" scope="request"/>
<c:set var="detailsPage" value="true" scope="request"/>

<c:set var="nameOrNumber">
    <c:choose>
        <c:when test="${not empty target}">
            «${target.name}»
        </c:when>
        <c:when test="${empty accountLink.name}">
            ${phiz:getFormattedAccountNumber(account.number)}
        </c:when>
        <c:otherwise>
            «${accountLink.name}»
        </c:otherwise>
    </c:choose>
</c:set>

<c:set var="pattern">
    <c:choose>
        <c:when test="${empty accountLink.name}">
            ${accountLink.patternForFavouriteLink}
        </c:when>
        <c:otherwise>
            «${accountLink.patternForFavouriteLink}»
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
        <%@ include file="targetTemplate.jsp" %>
    </c:when>
    <c:otherwise>
        <div class="productdetailInfo">
            <%@ include file="accountTemplate.jsp" %>
        </div>
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
                <tiles:put name="active" value="true"/>
                <tiles:put name="title" value="Последние операции"/>
                <tiles:put name="action" value="/private/accounts/operations?id=${accountLink.id}"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="false"/>
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
    <div class="abstractContainer2">

        <div class="extendedAccountsButton extendedAccountsButton2">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.extended.abstract2"/>
                <tiles:put name="commandHelpKey" value="button.extended.abstract2.help"/>
                <tiles:put name="bundle" value="accountInfoBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="image" value="revizity.gif"/>
                <tiles:put name="imageHover"     value="revizityHover.gif"/>
                <tiles:put name="imagePosition" value="left"/>
                <tiles:put name="onclick">openExtendedAbstract(event);</tiles:put>
            </tiles:insert>
        </div>
        <input id="ExtendedAbstractWindowOpened" type="hidden" name="loan" value="0"/>
    </div>
    <div class="clear separateAbstract">&nbsp;</div>
    <div class="abstractContainer2">
        <c:set var="periodName" value="Period"/>
        <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${periodName})"/></c:set>
        <c:if test="${not haveErrorMsg}">
            <div class="inlineBlock">
                <tiles:insert definition="filterDataPeriod" flush="false">
                    <tiles:put name="week" value="false"/>
                    <tiles:put name="month" value="false"/>
                    <tiles:put name="name" value="${periodName}"/>
                    <tiles:put name="buttonKey" value="button.filter"/>
                    <tiles:put name="buttonBundle" value="accountInfoBundle"/>
                    <tiles:put name="needErrorValidate" value="false"/>
                    <tiles:put name="isNeedTitle" value="false"/>
                </tiles:insert>
            </div>
        </c:if>

        <c:if test="${not abstractEmpty}">
            <div class="printTransactionsButton printButtonRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print.transaction"/>
                    <tiles:put name="commandHelpKey" value="button.print.transaction.help"/>
                    <tiles:put name="bundle" value="accountInfoBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="image" value="print-check.gif"/>
                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                    <tiles:put name="imagePosition" value="left"/>
                    <tiles:put name="onclick">openPrintAccountInfo(event, true)</tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </div>
    <div class="clear">&nbsp;</div>
    <c:choose>
        <c:when test="${not empty form.abstractMsgError}">
            <div class="emptyText ">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <c:out value="${form.abstractMsgError}"/>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:when>
        <c:when test="${not abstractEmpty}">
            <%--Вывод списка операций--%>
            <tiles:insert definition="simpleTableTemplate" flush="false">
                <tiles:put name="id" value="detailInfoTable"/>
                <tiles:put name="grid">
                    <sl:collection id="transaction" property="accountAbstract.transactions" model="simple-pagination">
                        <sl:collectionItem title="Тип операции" styleClass="align-left leftPaddingCell">
                            <c:choose>
                                <c:when test="${!empty transaction.description}">
                                    <c:out value="${transaction.description}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${!empty transaction.creditSum}">
                                            Зачисление
                                        </c:when>
                                        <c:when test="${!empty transaction.debitSum}">
                                            Списание
                                        </c:when>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="Дата" styleTitleClass=" ">
                            ${phiz:formatDateDependsOnSysDate(transaction.date, false, false)}
                        </sl:collectionItem>
                        <sl:collectionItem styleClass="align-right" title="Зачисление" styleTitleClass=" align-right">
                            <c:if test="${!empty transaction.creditSum and transaction.creditSum.decimal != '0.00'}"> <%-- из v6 приходит в случае пустой суммы зачисления приходит не null, а 0.00  --%>
                                ${phiz:formatAmount(transaction.creditSum)}
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem styleClass="align-right" styleTitleClass="align-right" title="Списание">
                            <c:if test="${!empty transaction.debitSum and transaction.debitSum.decimal != '0.00'}">  <%-- из v6 приходит в случае пустой суммы списания приходит не null, а 0.00  --%>
                                ${phiz:formatAmount(transaction.debitSum)}
                            </c:if>
                        </sl:collectionItem>
                        <%-- <sl:collectionItem styleClass="align-right">
                                ${transaction.recipient}
                            </sl:collectionItem>--%>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>

            <div class="clear"></div>
        </c:when>
        <c:otherwise>
            <div class="emptyText greenBlock">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <c:choose>
                            <%--Если пришла ошибка при получении операций--%>
                            <c:when test="${haveErrorMsg}">
                                <c:out value="${form.abstractMsgError}"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="message.empty" bundle="accountInfoBundle"/>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:otherwise>
    </c:choose>
</div>




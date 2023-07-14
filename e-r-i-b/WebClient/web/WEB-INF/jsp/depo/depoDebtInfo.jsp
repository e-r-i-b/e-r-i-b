<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/depo/info/debt" onsubmit="return setEmptyAction(event)">

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="depoAccountLink" value="${form.depoAccountLink}" scope="request"/>
<c:set var="depoAccount" value="${depoAccountLink.depoAccount}" scope="request"/>
<tiles:insert definition="news">
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Счета депо"/>
            <tiles:put name="action" value="/private/depo/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><span class="bold">${depoAccountLink.accountNumber}</span></tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <c:if test="${not empty form.depoDebtInfo}">
            <div id="debtInfo">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Информация о задолженности"/>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${image}/icon_depositariumDebt.jpg"/>
                            <tiles:put name="description">
                                На этой странице Вы можете просмотреть информацию о задолженности перед Депозитарием по
                                выбранному счету депо. Задолженность отображается по состоянию на указанную дату с учетом
                                суммы НДС.
                             </tiles:put>
                        </tiles:insert>
                        <c:set var="nameOrNumber">
                            <c:choose>
                               <c:when test="${not empty depoAccountLink.name}">
                                  <c:out value="«${depoAccountLink.name}»"/>
                               </c:when>
                               <c:otherwise>
                                  ${depoAccountLink.accountNumber}
                               </c:otherwise>
                            </c:choose>
                        </c:set>

                        <c:set var="baseInfo">
                            <bean:message key="favourite.link.depo" bundle="paymentsBundle"/>
                        </c:set>
                        <tiles:insert definition="addToFavouriteButton" flush="false">
                            <tiles:put name="formName"><c:out value="${baseInfo} ${nameOrNumber}"/></tiles:put>
                            <tiles:put name="patternName"><c:out value="${baseInfo} ${depoAccountLink.patternForFavouriteLink}"/></tiles:put>
                            <tiles:put name="typeFormat">DEPO_LINK</tiles:put>
                            <tiles:put name="productId">${form.id}</tiles:put>
                        </tiles:insert>
                        
                        <div class="clear"></div>
                        <div class="debtInfo">
                            <c:set var="curretntDate" value="${phiz:currentDate()}"/>
                            На дату: <span class="bold">${phiz:formatDateWithStringMonth(curretntDate)}</span>
                        </div>
                        <div class="debtInfo">
                            Счет депо: <span class="bold">${depoAccountLink.accountNumber}</span>
                         </div>
                        <div class="debtInfo">
                            Итоговая задолженность: <span class="summ">${phiz:formatAmount(form.depoDebtInfo.totalDebt)}</span>
                        </div>
                        <div class="depoDebtList">
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="id" value="depoPaymentTable"/>
                                <tiles:put name="grid">
                                    <sl:collection id="listElement" property="depoDebtInfo.debtItems" model="simple-pagination">
                                        <sl:collectionItem styleClass="align-left" property="recNumber" title="Номер счета"/>
                                        <sl:collectionItem styleClass="align-center" title="Дата выставления счета">
                                         ${phiz:formatDateWithStringMonth(listElement.recDate)}
                                        </sl:collectionItem>
                                        <sl:collectionItem styleClass="align-left" title="Расчетный период">
                                            <c:if test="${not empty listElement}">
                                                <bean:write name="listElement" property="startDate.time" format="dd.MM.yyyy"/> - <bean:write name="listElement" property="endDate.time" format="dd.MM.yyyy"/>
                                            </c:if>
                                         </sl:collectionItem>
                                        <c:set var="sumNDS" value="${phiz:getMoneyOperation(listElement.amount, listElement.amountNDS, '+')}"/>
                                        <sl:collectionItem styleClass="align-right"  title="Сумма счета">${phiz:formatAmount(sumNDS)}</sl:collectionItem>
                                        <c:if test="${phiz:impliesService('DepoDebtPayment')}">
                                            <sl:collectionItem styleClass="align-center" title="" >
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.pay"/>
                                                    <tiles:put name="commandHelpKey" value="button.pay.debt.help"/>
                                                    <tiles:put name="bundle"         value="depoBundle"/>
                                                    <tiles:put name="viewType"       value="buttonGrey"/>
                                                    <tiles:put name="action" value="private/payments/payment.do?form=JurPayment&depoLinkId=${depoAccountLink.id}&debtRecNumber=${listElement.recNumber}"/>
                                                </tiles:insert>
                                            </sl:collectionItem>
                                        </c:if>
                                    </sl:collection>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty form.depoDebtInfo.debtItems}"/>
                                <tiles:put name="emptyMessage"><bean:message key="label.debt.empty" bundle="depoBundle"/></tiles:put>
                            </tiles:insert>
                        </div>

                        <div class="debtButtons">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.goto.depoAccounts.list"/>
                                <tiles:put name="commandHelpKey" value="button.goto.depoAccounts.list"/>
                                <tiles:put name="bundle" value="depoBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                                <tiles:put name="action" value="/private/depo/list"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.update.debt"/>
                                <tiles:put name="commandHelpKey" value="button.update.debt"/>
                                <tiles:put name="bundle"         value="depoBundle"/>
                                <tiles:put name="onclick">updateDebt(event)</tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </tiles:put>
</tiles:insert>
    <script type="text/javascript">
        var url = "${phiz:calculateActionURL(pageContext,'/private/depo/info/debt')}";
        function updateDebt(event)
        {
          window.location = url + "?id="+${depoAccountLink.id}+"&refresh=true";
        }
    </script>
</html:form>

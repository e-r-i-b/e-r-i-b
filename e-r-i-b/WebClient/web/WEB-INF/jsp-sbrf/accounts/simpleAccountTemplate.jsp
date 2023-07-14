<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<!-- Общий шаблон для вкладов и целей  -->

<%--
bottomDataInfo - данные низа. Если не пустое то отображается содержимое данной переменной а опереции не отображаются.
showInMainCheckBox - признак, указывающий на необходимость отображения checkbox'а отвечающего за отображение
                    данного вклада на главной странице. Значение по умолчанию false
accountLink - текущий вклада
resourceAbstract - выписка текущего вклада
abstractError - ошибка при получении выписки
showInThisWidgetCheckBox - признак, указывающий на необходимость отображения checkbox'а, отвечающего за отображение
                       данного вклада в виджете.
--%>

<tiles:insert definition="productTemplate" flush="false">
    <c:set var="hasTarget" value="${isTarget}"/>
    <c:set var="rurPaymentAccess" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'RurPayment')}"/>
    <c:set var="newRurPaymentAccess" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'NewRurPayment')}"/>
    <c:set var="externalPhizAccountPaymentAccess" value="${phiz:impliesServiceRigid('ExternalPhizAccountPayment')}"/>

    <tiles:put name="productViewBacklight" value="false"/>
    <c:choose>
        <c:when test="${detailsPage}">
            <tiles:put name="operationsBlockPosition" value="rightPosition"/>
            <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle "/>
            <tiles:put name="additionalProductClass" value="mainProductDetailInfo"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
        </c:otherwise>
    </c:choose>
    <tiles:put name="id" value="${accountLink.id}"/>
    <tiles:put name="productType" value="account"/>
    <tiles:put name="src" value="${src}"/>
    <tiles:put name="title" value="${title}"/>
    <tiles:put name="comment" value="${comment}"/>
    <tiles:put name="status" value="${status}"/>
    <tiles:put name="rightComment" value="${rightComment}"/>

    <tiles:put name="img" value="${img}"/>
    <tiles:put name="alt" value="${alt}"/>
    <tiles:put name="productImgAdditionalData" value="${productImgAdditionalData}"/>

    <c:if test="${showInMainCheckBox}">
        <tiles:put name="showInMainCheckBox" value="${not isBlocked}"/>
        <tiles:put name="inMain" value="${accountLink.showInMain}"/>
    </c:if>
    <tiles:put name="showInThisWidgetCheckBox" value="${showInThisWidgetCheckBox}"/>

    <tiles:put name="leftData">
        <c:if test="${not empty imgStatus && (isLock || isLostPassbook || isArrested)}">
            <span class="detailStatus float">${imgStatus}</span>
            <c:if test="${isArrested}">
                <div class="float">
                    <tiles:insert definition="hintTemplate" flush="false">
                        <tiles:put name="color" value="whiteHint"/>
                        <tiles:put name="data">
                            Операции со счетом ограничены. За дополнительной информацией обратитесь в Контактный центр по телефону +7 (495) 500-55-50
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:if>
            <br/><br/>
            <div class="clear"></div>
        </c:if>
        ${leftData}
    </tiles:put>
    <tiles:put name="centerData" value="${centerData}"/>
    <tiles:put name="additionalData" value="${additionalData}"/>
    <tiles:put name="additionalProductInfo">
        ${additionalProductInfo}
    </tiles:put>
    <tiles:put name="productNumbers">
        ${productNumbers}
    </tiles:put>

    <c:set var="showInSys" value="${not empty accountLink && not empty accountLink.showInSystem && accountLink.showInSystem}"/>
    <tiles:put name="rightData">
        <c:if test="${hasTarget && showInSys && accountState == 'OPENED'}">
            <div class="float" onclick="cancelBubbling(event); return false;">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.targetAccountPayment"/>
                    <tiles:put name="commandHelpKey" value="button.targetAccountPayment.help"/>
                    <tiles:put name="bundle" value="financesBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="/private/payments/payment.do?form=InternalPayment&toResource=account:${accountLink.id}"/>
                </tiles:insert>
            </div>
        </c:if>

        <tiles:insert definition="listOfOperation" flush="false">
            <tiles:put name="productOperation" value="true"/>
            <c:if test="${not empty accountLink}">
                <tiles:put name="isLock" value="${not phiz:isPermittedForFinancialTransactions(account)}"/>
            </c:if>

            <c:if test="${detailsPage && not hasTarget}">
                <tiles:put name="nameOfOperation">Операции по вкладу, счету</tiles:put>
            </c:if>

            <c:choose>
                <c:when test="${empty accountLink and hasTarget}">
                    <tiles:putList name="items">
                        <tiles:add>
                            <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/removeAccountTarget.jsp" flush="false"/>
                        </tiles:add>
                    </tiles:putList>
                </c:when>
                <c:when test="${isLock}">
                    <c:choose>
                        <c:when test="${hasTarget}">
                            <tiles:putList name="items">
                                <tiles:add>
                                    <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/removeAccountTarget.jsp" flush="false"/>
                                </tiles:add>
                            </tiles:putList>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="isLock" value="true"/>
                            <tiles:putList name="items">
                                <tiles:add>Оплатить</tiles:add>
                            </tiles:putList>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <tiles:putList name="items">
                        <c:if test="${!isLock}">
                            <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'RurPayJurSB') and !hasTarget and debitAllowed and phiz:impliesServiceRigid('ExternalJurAccountPayment') and !isArrested}">
                                <c:set var="name" value="Оплатить"/>
                                <tiles:add>
                                    <a href="${phiz:calculateActionURL(pageContext, '/private/payments.do')}?fromResource=account:${accountLink.id}">${name}</a>
                                </tiles:add>
                            </c:if>

                            <c:if test="${!hasTarget && phiz:impliesOperation('CreateFormPaymentOperation', 'InternalPayment') and account.creditAllowed}">
                                <c:set var="name" value="Пополнить вклад"/>
                                <tiles:add>
                                    <a href="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=InternalPayment&toResource=account:${accountLink.id}">${name}</a>
                                </tiles:add>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'InternalPayment') and showInSys and debitAllowed and !isArrested}">
                                <c:set var="name" value="Перевести между своими счетами и картами"/>
                                <tiles:add>
                                    <a class="operationSeparate" href="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=InternalPayment&fromResource=account:${accountLink.id}&receiverSubType=ourAccount">${name}</a>
                                </tiles:add>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'JurPayment') and debitAllowed and account.currency.code == 'RUB' and !hasTarget and phiz:impliesService('ExternalJurAccountPayment') and !isArrested}">
                                <c:set var="name" value="Перевести организации"/>
                                <tiles:add>
                                    <a href="${phiz:calculateActionURL(pageContext, 'private/payments/jurPayment/edit.do')}?fromResource=account:${accountLink.id}">${name}</a>
                                </tiles:add>
                            </c:if>

                            <c:if test="${(rurPaymentAccess or newRurPaymentAccess) and !hasTarget and debitAllowed and externalPhizAccountPaymentAccess and !isArrested}">
                                <c:set var="name" value="Перевести частному лицу"/>
                                <c:choose>
                                    <c:when test="${newRurPaymentAccess}">
                                        <c:set var="params" value="form=NewRurPayment&receiverSubType=ourAccount"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="params" value="form=RurPayment&receiverSubType=ourAccount"/>
                                    </c:otherwise>
                                </c:choose>
                                <tiles:add>
                                    <a href="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?${params}&fromResource=account:${accountLink.id}">${name}</a>
                                </tiles:add>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'ChangeDepositMinimumBalanceClaim')}">
                                <c:set var="minBalances" value="${phiz:getMinBalancesToChange(accountLink)}"/>
                                <c:if test="${not empty minBalances}">
                                    <c:set var="minimumBalance" value="${minBalances[0].first}"/>
                                    <c:set var="interestRate"   value="${minBalances[0].second}"/>
                                    <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment')}?form=ChangeDepositMinimumBalanceClaim&accountId=${accountLink.id}&interestRate=${interestRate}&minDepositBalance=${minimumBalance}</c:set>

                                    <tiles:add><a href="${url}">Изменить неснижаемый остаток</a></tiles:add>
                                </c:if>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'AccountOpeningClaimWithClose') && balanceAsCents != 0 and !hasTarget and !isArrested}">
                                <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/deposits/products/list.do')}?form=AccountOpeningClaimWithClose&fromResource=account:${accountLink.id}</c:set>
                                <tiles:add><a class="operationSeparate" href="${url}">Открытие вклада (закрыть счет списания)</a></tiles:add>
                            </c:if>

                            <c:if test="${phiz:impliesService('CreateMoneyBoxPayment') && phiz:isAccountForMoneyBoxMatched(accountLink)}">
                                <tiles:insert definition="moneyBoxWindowCreator" flush="false"/>
                                <tiles:add><a href="#" onclick="openCreateMoneyBoxToAccountWindow('${accountLink.code}');">Подключить копилку</a></tiles:add>
                            </c:if>

                            <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'AccountClosingPayment') and !hasTarget and !isArrested}">
                                <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=AccountClosingPayment&fromResource=account:${accountLink.id}</c:set>
                                <tiles:add><a href="${url}">Закрыть вклад</a></tiles:add>
                            </c:if>

                            <c:if test="${hasTarget}">
                                <tiles:add>
                                    <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/removeAccountTarget.jsp" flush="false"/>
                                </tiles:add>
                            </c:if>
                        </c:if>
                        <c:if test="${!isLock and phiz:impliesService('LossPassbookApplication') and account.passbook and !hasTarget and !isLostPassbook}">
                            <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=LossPassbookApplication&accountLink=account:${accountLink.id}</c:set>
                            <tiles:add><a class="block" href="${url}">Заявить об утере<br /> сберкнижки</a></tiles:add>
                        </c:if>
                    </tiles:putList>
                </c:otherwise>
            </c:choose>
        </tiles:insert>
    </tiles:put>

    <c:if test="${showBottomData != 'false'}">
        <tiles:put name="bottomData">
            <c:if test="${(accountLink.showOperations && not empty resourceAbstract.transactions || !accountLink.showOperations) && empty bottomDataInfo}">
                <c:set var="showOperations" value="${phiz:isShowOperations(accountLink)}"/>
                <tiles:insert definition="simpleTableTemplate" flush="false">
                    <tiles:put name="hideable" value="true"/>
                    <tiles:put name="id" value="${accountLink.id}"/>
                    <tiles:put name="productType" value="account"/>
                    <tiles:put name="show" value="${showOperations}"/>
                    <c:choose>
                        <c:when test="${showOperations}">
                            <c:choose>
                                <c:when test="${not empty abstractError}">
                                    <tiles:put name="grid"><div class="emptyMiniAbstract">${abstractError}</div></tiles:put>
                                </c:when>
                                <c:when test="${not empty resourceAbstract.transactions }">
                                    <tiles:put name="grid">
                                        <tiles:insert page="/WEB-INF/jsp/accounts/miniAbstract.jsp" flush="false">
                                            <tiles:put name="resourceAbstract" beanName="resourceAbstract"/>
                                        </tiles:insert>
                                    </tiles:put>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="ajaxDataURL">${phiz:calculateActionURL(pageContext, '/private/async/extract.do')}?type=acc&id=${accountLink.id}</tiles:put>
                        </c:otherwise>
                    </c:choose>
                </tiles:insert>
            </c:if>
            ${bottomDataInfo}
        </tiles:put>
    </c:if>
</tiles:insert>

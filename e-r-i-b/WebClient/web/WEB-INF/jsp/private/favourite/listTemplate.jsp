<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<div  class="autoPaymentListBorder">
    <tiles:insert definition="simpleTableTemplate" flush="false">
        <tiles:put name="grid">
            <sl:collection id="link" indexId="ind" property="${fieldName}" model="sort-list" styleClass="rowOver ${paymentsCount==1 ? 'no-sort' : 'sort'}">
                <c:set var="isLongOffer" value="${phiz:isInstance(link, 'com.rssl.phizic.business.resources.external.LongOfferLink')}"/>
                <c:set var="isAutoSubscription" value="${phiz:isInstance(link, 'com.rssl.phizic.business.resources.external.AutoSubscriptionLink')}"/>
                <c:set var="isAutoPayment" value="${phiz:isInstance(link, 'com.rssl.phizic.business.resources.external.AutoPaymentLink')}"/>
                <c:set var="payment" value="${link.value}"/>

                <html:hidden property="sortAutoPayments" value="${phiz:stringEncode(link.externalId)}_${link.codePrefix}"/>
                <sl:collectionItem styleClass="align-left repeatLink sortableCells" title="&nbsp;Название" styleTitleClass="sortableCells">
                    <div class="relative">
                        <img src="${globalImagePath}/slip.gif" class="slipImage"/>

                        <%-- Строим ссылку для перехода при нажатии на просмотр автоплатежа. --%>
                        <c:choose>
                            <c:when test="${not isAutoSubscription}">
                                <c:set var="curId"><bean:write name='link' property="id" ignore="true"/></c:set>
                                <c:set var="curUrl">
                                    ${isLongOffer ? longOfferInfoUrl : autoPaymentInfoUrl}${curId}
                                </c:set>
                            </c:when>
                            <c:otherwise>
                                <c:set var="curId"><bean:write name='payment' property="number" ignore="true"/></c:set>
                                <c:set var="curUrl">
                                    ${autoSubsctiptionUrl}${curId}
                                </c:set>
                            </c:otherwise>
                        </c:choose>

                         <c:set var="textClassproductTemplate" value="sortableHiddenTextWithHint"/>
                         <c:set var="autoPayStatus"><bean:message bundle="paymentsBundle" key="payment.autoSub.state.${state}"/></c:set>
                         <c:if test="${state == 'Paused'}">
                             <c:if test="${not empty payment}">
                                 <%-- Извлекаем из бандлов текстовый статус автоплатежа. --%>
                                 <c:if test="${isAutoSubscription}">
                                     <c:set var="statusHint" value="${payment.autoPayStatusType.hint}"/>
                                 </c:if>
                             </c:if>
                         </c:if>
                         <c:if test="${state == 'WaitForAccept'}">
                             <c:if test="${not empty payment}">
                                 <%-- Извлекаем из бандлов текстовый статус автоплатежа. --%>
                                 <c:choose>
                                     <c:when test="${isAutoSubscription}">
                                         <c:if test="${payment.autoPayStatusType == 'WaitForAccept' || payment.autoPayStatusType == 'New'}">
                                             <c:set var="autoPayStatus"><bean:message bundle="paymentsBundle" key="payment.autoSub.state.WaitForAccept2"/></c:set>
                                         </c:if>
                                         <c:set var="statusHint" value="${payment.autoPayStatusType.hint}"/>
                                     </c:when>
                                     <c:otherwise>
                                         <c:set var="statusHint" value="${payment.reportStatus.hint}"/>
                                     </c:otherwise>
                                 </c:choose>
                             </c:if>
                         </c:if>
                            <%-- Отображаем ссылку перехода на просмотр автоплатежа. --%>
                        <span class="sortableHiddenTextWithHint" onclick="window.location = '${curUrl}'">
                            <bean:write name="link" property="name" ignore="true"/>
                        </span>
                        <div class="shading"></div>
                    </div>
                    <%-- Отображаем статус автоплатежа --%>
                    <c:if test="${not empty autoPayStatus}">
                        <c:choose>
                            <c:when test="${state == 'Active' || state == 'Archive'}">
                                <a class="text-highlight no-border">${autoPayStatus}</a>
                            </c:when>
                            <c:when test="${state == 'WaitForAccept'}">
                                <a id="state${type}_${ind}" class="text-highlight text-red" onmouseout="hideLayer('stateDescription_${ind}');" onmouseover="showLayer('state${type}_${ind}','stateDescription_${ind}');">${autoPayStatus}
                                    <span id="stateDescription_${ind}" onmouseover="showLayer('state${type}_${ind}','stateDescription_${ind}','default');" onmouseout="hideLayer('stateDescription_${ind}');" class="layerFon stateDescription">
                                    <div class="floatMessageHeader"></div>
                                    <div class="layerFonBlock">${statusHint}</div>
                                </span></a>
                            </c:when>
                            <c:when test="${state == 'Paused'}">
                                <a id="${ind}_${type}state" class="text-highlight" onmouseout="hideLayer('${ind}_${type}stateDescription');" onmouseover="showLayer('${ind}_${type}state','${ind}_${type}stateDescription');">${autoPayStatus}
                                    <span id="${ind}_${type}stateDescription" onmouseover="showLayer('${ind}_${type}state','${ind}_${type}stateDescription','default');" onmouseout="hideLayer('${ind}_${type}stateDescription');" class="layerFon stateDescription">
                                        <div class="floatMessageHeader"></div>
                                        <div class="layerFonBlock">${statusHint}</div>
                                    </span></a>
                            </c:when>
                        </c:choose>
                     </c:if>

                </sl:collectionItem>
                <sl:collectionItem title="Условие исполнения" styleClass="repeatLink2 sortableCells font12">
                    <span onclick="window.location = '${curUrl}'">
                        <c:if test="${isLongOffer}"><img src="${imagePath}/icon_longOffer.png" alt=""/>&nbsp;</c:if>
                        <c:set var="TermsOfExecution">
                            <c:choose>
                                <c:when test="${isAutoSubscription}">
                                    <c:set var="paymentType" value="${payment.type.simpleName}"/>
                                    <c:choose>
                                        <c:when test="${'InternalCardsTransferLongOffer' == paymentType || 'ExternalCardsTransferToOurBankLongOffer' == paymentType  ||
                                            'ExternalCardsTransferToOtherBankLongOffer' == paymentType}">
                                            <c:out value="${link.startExecutionDetail}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="executionType" value="autoSubscription.executionType.${payment.executionEventType}"/>
                                            <c:set var="autoPayType" value="autoSubscription.type.${payment.sumType}.suffix"/>
                                            <bean:message bundle="autoPaymentInfoBundle" key="${executionType}"/>
                                            <bean:message bundle="autoPaymentInfoBundle" key="${autoPayType}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <bean:write name="link" property="executionEventType" ignore="true"/>
                                </c:otherwise>
                            </c:choose>
                        </c:set>
                        <c:choose>
                            <c:when test="${empty TermsOfExecution}">
                                <span class="emptyPlase">&nbsp;</span>
                            </c:when>
                            <c:otherwise>${TermsOfExecution}</c:otherwise>
                        </c:choose>
                    </span>
                </sl:collectionItem>
                <sl:collectionItem styleClass="align-right repeatLink3 sortableCells" styleTitleClass="align-right sortableCells" title="Сумма">
                    <span onclick="window.location = '${curUrl}'">
                        <c:set var="cost">
                            <c:choose>
                                <c:when test="${payment.sumType eq 'SUMMA_OF_RECEIPT'}">
                                    <b>${payment.sumType.description}</b>
                                </c:when>
                                <c:when test="${isLongOffer && payment.executionEventType == 'ON_REMAIND'}">
                                    <b>свыше <bean:write name="payment" property="amount.decimal" ignore="true" format="###,##0.00"/>&nbsp;${phiz:getCurrencySign(payment.amount.currency.code)}</b>
                                </c:when>
                                <c:when test="${payment.sumType eq 'REMAIND_OVER_SUMMA'}">
                                    <span class="emptyPlase">&nbsp;</span>
                                </c:when>
                                <c:when test="${payment.executionEventType == 'BY_INVOICE'}">
                                    <c:set var="currency" value="${payment.floorLimit.currency.code}"/>
                                    <b><bean:write name="payment" property="amount.decimal" ignore="true" format="###,##0.00"/>&nbsp;${phiz:getCurrencySign(currency)}</b>
                                </c:when>
                                <c:when test="${not empty payment.percent}">
                                    <b><bean:write name="payment" property="percent"/>%</b>
                                </c:when>
                                <c:when test="${not empty payment.amount}">
                                    <c:set var="currency" value="${payment.amount.currency.code}"/>
                                    <b><bean:write name="payment" property="amount.decimal" ignore="true" format="###,##0.00"/>&nbsp;${phiz:getCurrencySign(currency)}</b>
                                </c:when>
                            </c:choose>
                        </c:set>
                        <c:choose>
                            <c:when test="${empty cost}">
                                <span class="emptyPlase">&nbsp;</span>
                            </c:when>
                            <c:otherwise>${cost}</c:otherwise>
                        </c:choose>
                    </span>
                </sl:collectionItem>
                <sl:collectionItem title="" styleClass="editColumn sortableCells" styleTitleClass="">
                    <tiles:insert definition="listOfOperation" flush="false">
                        <c:choose>
                            <c:when test="${isAutoPayment}">
                                <tiles:putList name="items">
                                    <c:choose>
                                        <c:when test="${payment.reportStatus == 'ACTIVE' }">
                                            <c:if test="${impliesEditAutoPaymentPayment}">
                                                <tiles:add><a href="${EditAutoPaymentURL}${curId}">Редактировать</a></tiles:add>
                                            </c:if>
                                            <c:if test="${impliesRefuseAutoPaymentPayment}">
                                                <tiles:add><a href="${refuseAutoPaymentURL}${curId}">Отключить</a></tiles:add>
                                            </c:if>
                                        </c:when>
                                        <%--Примечание: Запрос на отмену можно посылать для автоплатежей в состоянии: Новый, Активный(учтен в предыдущем ифе), Обновляется, Заблокирован. В остальных случаях будет получен отказ.--%>
                                        <c:when test="${payment.reportStatus == 'NEW' || payment.reportStatus == 'UPDATING' || payment.reportStatus == 'BLOCKED'}">
                                            <c:if test="${impliesRefuseAutoPaymentPayment}">
                                                <tiles:add><a href="${refuseAutoPaymentURL}${curId}">Отключить</a></tiles:add>
                                            </c:if>
                                        </c:when>
                                    </c:choose>
                                </tiles:putList>
                            </c:when>
                            <c:when test="${isAutoSubscription}">
                                <c:set var="autoPaymentCardLink" value="${link.cardLink}"/>
                                <c:set var="isCardArrested" value="${autoPaymentCardLink != null && autoPaymentCardLink.card.cardAccountState == 'ARRESTED'}"/>
                                <tiles:putList name="items">
                                    <c:choose>
                                        <c:when test="${payment.autoPayStatusType == 'Active'}">
                                            <c:if test="${impliesEditAutoSubscriptionPayment && !isCardArrested}">
                                                <tiles:add><a href="${EditAutoSubscriptionPaymentURL}${curId}">Редактировать</a></tiles:add>
                                            </c:if>
                                            <c:if test="${impliesDelayAutoSubscriptionPayment}">
                                                <tiles:add><a href="${DelayAutoSubscriptionPaymentUrl}${curId}">Приостановить</a></tiles:add>
                                            </c:if>
                                            <c:if test="${impliesCloseAutoSubscriptionPayment}">
                                                <tiles:add><a href="${CloseAutoSubscriptionURL}${curId}">Отключить</a></tiles:add>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${payment.autoPayStatusType == 'Paused'}">
                                            <c:if test="${impliesEditAutoSubscriptionPayment && !isCardArrested}">
                                                <tiles:add><a href="${EditAutoSubscriptionPaymentURL}${curId}">Редактировать</a></tiles:add>
                                            </c:if>
                                            <c:if test="${impliesRecoveryAutoSubscriptionPayment && !isCardArrested}">
                                                <tiles:add><a href="${RecoveryAutoSubscriptionPaymentURL}${curId}">Возобновить</a></tiles:add>
                                            </c:if>
                                            <c:if test="${impliesCloseAutoSubscriptionPayment}">
                                                <tiles:add><a href="${CloseAutoSubscriptionURL}${curId}">Отключить</a></tiles:add>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${payment.autoPayStatusType == 'ErrorRegistration' || payment.autoPayStatusType == 'Confirmed'}">
                                            <c:if test="${impliesDelayAutoSubscriptionPayment}">
                                                <tiles:add><a href="${DelayAutoSubscriptionPaymentUrl}${curId}">Приостановить</a></tiles:add>
                                            </c:if>
                                            <c:if test="${impliesCloseAutoSubscriptionPayment}">
                                                <tiles:add><a href="${CloseAutoSubscriptionURL}${curId}">Отключить</a></tiles:add>
                                            </c:if>
                                        </c:when>
                                    </c:choose>
                                </tiles:putList>
                            </c:when>
                            <c:when test="${isLongOffer}">
                                <tiles:putList name="items">
                                    <c:if test="${impliesRefuseLongOffer}">
                                        <tiles:add><a href="${refuseLongOfferUrl}${curId}">Отключить</a></tiles:add>
                                    </c:if>
                                </tiles:putList>
                            </c:when>
                        </c:choose>
                    </tiles:insert>
                </sl:collectionItem>
            </sl:collection>
            <c:if test="${state == 'Paused'}">
                   <span id="${ind}_${type}stateDescription" onmouseover="showLayer('${ind}_${type}state','${ind}_${type}stateDescription','default');" onmouseout="hideLayer('${ind}_${type}stateDescription');" class="layerFon stateDescription">
                        <div class="floatMessageHeader"></div>
                        <div class="layerFonBlock">${statusHint}</div>
                   </span>
            </c:if>
        </tiles:put>
    </tiles:insert>
</div>
<c:if test="${paymentsCount > 1}">
    <div class="sortAutoPaymentAndTemplateText">
        Перемещайте автоплатежи в нужном Вам порядке
    </div>
</c:if>


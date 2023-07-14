<tiles:importAttribute/>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%--
  Выводит шаблоны и автоплатежи для конкретной карты или счета.

  templates - список шаблонов.

  --%>

<c:set var="longOfferInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/longOffers/info?id=')}"/>
<c:set var="autoPaymentInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/autopayments/info?id=')}"/>

<%-- Вывод шаблонов --%>
<tiles:insert definition="simpleTableTemplate" flush="false">
    <tiles:put name="id" value="detailInfoTable"/>
    <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do?')}"/>
    <tiles:put name="grid">
        <sl:collection id="template" model="simple-pagination" property="templates">
            <sl:collectionItem styleClass="align-left" title="Шаблоны платежей">
                <c:choose>
                    <c:when test="${!isLock}">
                        <a href="${actionUrl}id=<bean:write name='template' property='id' ignore='true'/>&objectFormName=${template.formType.name}&stateCode=${template.state.code}">
                            <bean:write name='template' property="templateInfo.name" ignore="true"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <bean:write name='template' property="templateInfo.name" ignore="true"/>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="Сумма платежа">
                <b><c:choose>
                    <c:when test="${not empty template.chargeOffAmount}">
                        ${phiz:formatAmount(template.chargeOffAmount)}
                    </c:when>
                    <c:otherwise>
                        ${phiz:formatAmount(template.destinationAmount)}
                    </c:otherwise>
                </c:choose></b>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${empty form.templates}"/>
    <tiles:put name="emptyMessage"><span class="text-dark-gray normal"><bean:message key="message.empty" bundle="cardInfoBundle"/></span></tiles:put>
</tiles:insert>
<br><br>
<%-- Вывод автоплатежей и регулярных операций --%>
<div class="regular-payments" id="regular-payments" >
    <c:set var="urlRegular">/private/async/favourite/list/list-auto-payments.do?productId=<bean:write name="form" property="id"/>&card=<bean:write name="form" property="cardOperation"/>&account=<bean:write name="form" property="accountOperation"/></c:set>
    <jsp:include page="${urlRegular}"/>
</div>

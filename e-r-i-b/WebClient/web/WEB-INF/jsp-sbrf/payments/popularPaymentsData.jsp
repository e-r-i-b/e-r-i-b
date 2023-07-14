<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>


<div class="popularPaymentsBlock">
    <div class="popularPaymentsTitle"> Последние платежи </div>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.goto.history"/>
        <tiles:put name="commandHelpKey" value="button.goto.history"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <tiles:put name="action" value="/private/payments/common.do?&status=all"/>
    </tiles:insert>
    <div class="clear"></div> 
    <c:url var="copyPaymentAction" value="/private/payments/payment.do"/>
    <c:set var="currPayment" value="0"/>
    <c:set var="isFirst" value="true"/>
    <c:set var="payments" value="${frm.payments}"/>
    <c:set var="paymentsCount" value="${fn:length(payments)}"/>
    <div panel="wideTable">
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="grid">
                <c:if test="${not empty frm.payments}">
                    <sl:collection id="businessDocument"  name="payments" model="${isWidget == 'true' ? 'list' : 'simple-pagination'}" styleClass="rowOver" indexId="index">
                        <c:set var="linkPayment" value="${phiz:getLinkPaymentByDocument(businessDocument)}"/>
                        <c:set var="copyUrl" value="${phiz:calculateActionURL(pageContext, linkPayment)}"/>
                        <c:set var="paymentDate" value="${businessDocument.executionDate}"/>
                        <c:choose>
                            <c:when test="${index == paymentsCount - 1}">
                                <c:set var="lastElement" value="lastElement"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="lastElement" value=""/>
                            </c:otherwise>
                        </c:choose>
                        <sl:collectionItem styleClass="align-left ${lastElement} tablePadding">
                             <c:if test="${not empty paymentDate}">
                                <span class="paymentDate">
                                   ${phiz:formatDateDependsOnSysDate(paymentDate, true, false)}
                                </span>
                             </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem styleClass="align-left ${lastElement}">
                            <c:set var="paymentFormDescription" value="paymentform.${businessDocument.formName}"/>
                            <span>
                                <c:choose>
                                    <c:when test="${businessDocument.formName == 'RurPayment'}">
                                        <c:choose>
                                            <c:when test="${businessDocument.receiverSubType == 'externalAccount'}">
                                                Перевод частному лицу в другой банк:
                                            </c:when>
                                            <c:otherwise>
                                                Перевод клиенту Сбербанка:
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                        <c:otherwise>
                                            <bean:message bundle="paymentsBundle" key="${paymentFormDescription}" failIfNone="false"/>:
                                        </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.InternalTransfer')}">
                                        <c:choose>
                                            <%--Счет зачисления КАРТА--%>
                                            <c:when test="${businessDocument.destinationResourceType == 'CARD'}">
                                                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popular/card-title.jsp" flush="false">
                                                    <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                                                    <tiles:put name="cardName" value="${businessDocument.toAccountName}"/>
                                                </tiles:insert>
                                            </c:when>
                                            <%--Счет зачисления ОМС--%>
                                            <c:when test="${businessDocument.destinationResourceType == 'IM_ACCOUNT'}">
                                                <c:if test="${not empty businessDocument.destinationResourceLink}">
                                                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popular/imaccount-title.jsp" flush="false">
                                                        <tiles:put name="imAccountLink" beanName="businessDocument" beanProperty="destinationResourceLink"/>
                                                    </tiles:insert>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popular/account-title.jsp" flush="false">
                                                    <tiles:put name="accountNumber" value="${businessDocument.receiverAccount}"/>
                                                    <tiles:put name="accountName" value="${businessDocument.toAccountName}"/>
                                                </tiles:insert>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                        <c:choose>
                                            <c:when test="${businessDocument.destinationResourceType == 'EXTERNAL_CARD'}">
                                                <span class="bold">
                                                    ${phiz:getCutCardNumber(businessDocument.receiverAccount)}
                                                </span>
                                                <span class="word-wrap whole-words"><c:out value="${businessDocument.receiverName}"/></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="bold">
                                                    ${phiz:getFormattedAccountNumber(businessDocument.receiverAccount)}
                                                </span>
                                                    <span class="word-wrap whole-words">
                                                        <c:set var="isRurPayment" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RurPayment')}"/>
                                                        <c:choose>
                                                            <c:when test="${isRurPayment and businessDocument.receiverType eq 'ph' and (businessDocument.receiverSubType eq 'ourAccount' or businessDocument.receiverSubType eq 'externalAccount')}">
                                                                <c:set var="receiverFirstName" value="${businessDocument.receiverFirstName}"/>
                                                                <c:set var="receiverSurName"   value="${businessDocument.receiverSurName}"/>
                                                                <c:set var="receiverPatrName"  value="${businessDocument.receiverPatrName}"/>
                                                                <c:out value="${phiz:getFormattedPersonName(receiverFirstName, receiverSurName, receiverPatrName)}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${businessDocument.receiverName}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanPayment')}">
                                        <span class="word-wrap whole-words">
                                            <c:out value="${phiz:getLoanLink(businessDocument.accountNumber).name}"/>
                                        </span>
                                        <c:out value="${businessDocument.accountNumber}"/>
                                    </c:when>
                                </c:choose>
                            </span>
                        </sl:collectionItem>
                        <sl:collectionItem  styleClass="align-right amount ${lastElement}" >
                            <span class="bold">
                                <c:choose>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.chargeOffAmount}">
                                        ${phiz:formatAmount(businessDocument.chargeOffAmount)}
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.destinationAmount}">
                                            ${phiz:formatAmount(businessDocument.destinationAmount)}
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                         </sl:collectionItem>
                        <sl:collectionItem  styleClass="align-right greenRepeatLink listOfOperationWidth ${lastElement}">
                            <c:set var="listOfOperationCount" scope="request" value="0"/>
                            <tiles:insert definition="listOfOperation" flush="false">
                                <tiles:putList name="items">
                                    <c:set var="supportedActions"  value="${phiz:getDocumentSupportedActions(businessDocument)}"/>
                                    <c:if test="${supportedActions['isCopyAllowed']}">
                                        <tiles:add><a onclick="repeatPayment('${copyURL}?id=${businessDocument.id}');">Повторить</a></tiles:add>
                                    </c:if>
                                    <c:if test="${supportedActions['isTemplateSupported']}">
                                        <tiles:add><a onclick="saveTemplate('${businessDocument.id}');">Создать шаблон</a></tiles:add>
                                    </c:if>
                                    <c:if test="${supportedActions['isAutoPaymentAllowed']}">
                                        <tiles:add><a onclick="saveAutoPayment('${businessDocument.id}');">Подключить автоплатеж</a></tiles:add>
                                    </c:if>
                                </tiles:putList>
                            </tiles:insert>
                        </sl:collectionItem>
                        <sl:collectionItem hidden="true">
                            <span class="onclickFunc" onclick="repeatPayment('${copyURL}?id=${businessDocument.id}');"></span>
                        </sl:collectionItem>
                    </sl:collection>
                </c:if>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty payments}"/>
            <tiles:put name="emptyMessage">
                <bean:message bundle="paymentsBundle" key="text.emptyMessage"/>
            </tiles:put>
        </tiles:insert>
    </div>
    <c:if test="${isWidget}">
        <%--для компактого режима виджета "последние платежи"--%>
        <div panel="compactTable" >
           <tiles:insert definition="simpleTableTemplate" flush="false">
                <tiles:put name="grid">
                    <c:if test="${not empty frm.payments}">
                        <sl:collection id="businessDocument"  name="payments" model="${isWidget == 'true' ? 'list' : 'simple-pagination'}" styleClass="rowOver"  indexId="index">
                            <c:set var="linkPayment" value="${phiz:getLinkPaymentByDocument(businessDocument)}"/>
                            <c:set var="copyUrl" value="${phiz:calculateActionURL(pageContext, linkPayment)}"/>
                            <c:set var="paymentDate" value="${businessDocument.executionDate}"/>
                            <c:choose>
                                <c:when test="${index == paymentsCount - 1}">
                                    <c:set var="lastElement" value="lastElement"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="lastElement" value=""/>
                                </c:otherwise>
                            </c:choose>
                            <sl:collectionItem styleClass="${lastElement}">
                            <div class="align-left date-description">
                                <c:if test="${not empty paymentDate}">
                                    <span class="paymentDate">
                                       ${phiz:formatDateDependsOnSysDate(paymentDate, true, false)}
                                    </span>
                                 </c:if>
                                <c:set var="paymentFormDescription" value="paymentform.${businessDocument.formName}"/>
                                <span>
                                    <bean:message bundle="paymentsBundle" key="${paymentFormDescription}" failIfNone="false"/>:

                                    <c:choose>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.InternalTransfer')}">
                                            <c:choose>
                                                <%--Счет зачисления КАРТА--%>
                                                <c:when test="${businessDocument.destinationResourceType == 'CARD'}">
                                                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popular/card-title.jsp" flush="false">
                                                        <tiles:put name="cardNumber" value="${businessDocument.receiverAccount}"/>
                                                        <tiles:put name="cardName" value="${businessDocument.toAccountName}"/>
                                                    </tiles:insert>
                                                </c:when>
                                                <%--Счет зачисления ОМС--%>
                                                <c:when test="${businessDocument.destinationResourceType == 'IM_ACCOUNT'}">
                                                    <c:if test="${not empty businessDocument.destinationResourceLink}">

                                                        <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popular/imaccount-title.jsp" flush="false">
                                                            <tiles:put name="imAccountLink" beanName="businessDocument" beanProperty="destinationResourceLink"/>
                                                        </tiles:insert>
                                                    </c:if>
                                                </c:when>
                                                <c:otherwise>
                                                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/popular/account-title.jsp" flush="false">
                                                        <tiles:put name="accountNumber" value="${businessDocument.receiverAccount}"/>
                                                        <tiles:put name="accountName" value="${businessDocument.toAccountName}"/>
                                                    </tiles:insert>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractAccountsTransfer')}">
                                            <c:choose>
                                                <c:when test="${businessDocument.destinationResourceType == 'EXTERNAL_CARD'}">
                                                    <span class="bold">
                                                        ${phiz:getCutCardNumber(businessDocument.receiverAccount)}
                                                    </span>
                                                    <span class="word-wrap whole-words"><c:out value="${businessDocument.receiverName}"/></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="bold">
                                                        ${phiz:getFormattedAccountNumber(businessDocument.receiverAccount)}
                                                    </span>
                                                        <span class="word-wrap whole-words">
                                                            <c:set var="isRurPayment" value="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.RurPayment')}"/>
                                                            <c:choose>
                                                                <c:when test="${isRurPayment and businessDocument.receiverType eq 'ph' and (businessDocument.receiverSubType eq 'ourAccount' or businessDocument.receiverSubType eq 'externalAccount')}">
                                                                    <c:set var="receiverFirstName" value="${businessDocument.receiverFirstName}"/>
                                                                    <c:set var="receiverSurName"   value="${businessDocument.receiverSurName}"/>
                                                                    <c:set var="receiverPatrName"  value="${businessDocument.receiverPatrName}"/>

                                                                    <c:out value="${phiz:getFormattedPersonName(receiverFirstName, receiverSurName, receiverPatrName)}"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:out value="${businessDocument.receiverName}"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.LoanPayment')}">
                                            <span class="word-wrap whole-words">
                                                <c:out value="${phiz:getLoanLink(businessDocument.accountNumber).name}"/>
                                            </span>
                                            <c:out value="${businessDocument.accountNumber}"/>
                                        </c:when>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="amount-retry">
                                <span class="bold">
                                    <c:choose>
                                        <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.chargeOffAmount}">
                                            ${phiz:formatAmount(businessDocument.chargeOffAmount)}
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.AbstractPaymentDocument') && not empty businessDocument.destinationAmount}">
                                                ${phiz:formatAmount(businessDocument.destinationAmount)}
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                                <span>
                                    <a class="greenRepeatLink" style="display:inline" onclick="repeatPayment('${copyURL}?id=${businessDocument.id}');">повторить</a>
                                </span>
                            </div>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:if> 
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty payments}"/>
                <tiles:put name="emptyMessage">
                    <bean:message bundle="paymentsBundle" key="text.emptyMessage"/>
                </tiles:put>
            </tiles:insert>
        </div>
    </c:if>
    <div class="productDivider"></div>
</div>

<script type="text/javascript">
    function repeatPayment(url){
        window.location = url + "copying=true";
    }
    $(document).ready(function(){
        $(".grid .ListLine1, .grid .ListLine0").each(function() {
            if ($(this).find('.onclickFunc').length>0)
                this.onclick = $(this).find('.onclickFunc')[0].onclick;
        });
    });

    <%-- согласовано с Кузнецовой, переходим на форму просмотра платежа, где клиент будет создавать быстрый шаблон --%>
    var templateUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/view.do')}";
    function saveTemplate(paymentId)
    {
        window.location = templateUrl + "?id="+paymentId;
    }

    var autoPaymentUrl = "${phiz:calculateActionURL(pageContext,'/private/payments/view.do')}";
    function saveAutoPayment(paymentId)
    {
        changeFormAction(autoPaymentUrl + "?id="+paymentId); createCommandButton('button.makeLongOffer','').click('', false)
    }

</script>

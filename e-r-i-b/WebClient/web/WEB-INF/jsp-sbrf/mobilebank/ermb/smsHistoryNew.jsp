<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<div id="popularPayments" class="newProfileContentWidth mbSmsPay">
    <tiles:insert definition="mainWorkspace" flush="false">
        <tiles:put name="title" value="Мобильный банк"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="formHeader" flush="false">
               <tiles:put name="image" value="${image}/ico_mobileBank.jpg"/>
               <tiles:put name="description">
                   <h3>На этой странице можно изменить настройки подключения к Мобильному банку, просмотреть SMS-запросы и шаблоны, а также историю отправленных в банк запросов. Для этого перейдите на соответствующую вкладку. </h3>
               </tiles:put>
           </tiles:insert>
            <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="selectedTab" value="history"/>
            <%@ include file="/WEB-INF/jsp-sbrf/mobilebank/ermb/mobileBankHeader.jsp" %>
            <div class="titleAndButton">
                <h2 class="popularPaymentsTitle inline"> Ваши последние SMS-запросы </h2>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.goto.history"/>
                    <tiles:put name="commandHelpKey" value="button.goto.history"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="/private/payments/common.do?&status=all"/>
                </tiles:insert>
            </div>
            <c:set var="payments" value="${frm.payments}"/>

            <tiles:insert definition="simpleTableTemplate" flush="false">
                    <tiles:put name="grid">
                        <sl:collection id="businessDocument"  name="payments" model="simple-pagination" styleClass="rowOver">
                             <c:choose>
                                    <c:when test="${phiz:isInstance(businessDocument, 'com.rssl.phizic.business.documents.payments.JurPayment')}">
                                        <c:url var="copyURL" value="/private/payments/jurPayment/edit.do">
                                            <c:param name="copying" value="true"/>
                                            <c:param name="id" value="${businessDocument.id}"/>
                                        </c:url>
                                        <c:set var="serviceProvider" value="${phiz:getServiceProvider(businessDocument.receiverInternalId)}"/>
                                        <c:if test="${not empty serviceProvider}">
                                            <c:url var="copyURL" value="/private/payments/servicesPayments/edit.do">
                                                <c:param name="copying" value="true"/>
                                                <c:param name="id" value="${businessDocument.id}"/>
                                            </c:url>
                                        </c:if>
                                        <c:set var="serviceId" value="RurPayJurSB"/>
                                    </c:when>
                                    <c:when test="${businessDocument.formName == 'JurPayment'}">
                                        <c:url var="copyURL" value="/private/payments/jurPayment/edit.do">
                                            <c:param name="copying" value="true"/>
                                            <c:param name="id" value="${businessDocument.id}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                         <c:url var="copyURL" value="/private/payments/payment.do">
                                             <c:param name="copying" value="true"/>
                                             <c:param name="id" value="${businessDocument.id}"/>
                                         </c:url>
                                    </c:otherwise>
                                </c:choose>
                            <c:set var="paymentDate" value="${businessDocument.executionDate}"/>
                            <sl:collectionItem styleClass="align-left grayPaymentDate">
                                 <c:if test="${not empty paymentDate}">
                                    <span class="paymentDate">
                                       ${phiz:formatDateDependsOnSysDate(paymentDate, true, false)}
                                    </span>
                                 </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem styleClass="align-left">
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
                                                    <span class="word-wrap"><c:out value="${businessDocument.receiverName}"/></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="bold">
                                                        ${phiz:getFormattedAccountNumber(businessDocument.receiverAccount)}
                                                    </span>
                                                        <span class="word-wrap">
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
                                            <span class="word-wrap">
                                                <c:out value="${phiz:getLoanLink(businessDocument.accountNumber).name}"/>
                                            </span>
                                            <c:out value="${businessDocument.accountNumber}"/>
                                        </c:when>
                                    </c:choose>
                                </span>
                            </sl:collectionItem>
                            <sl:collectionItem  styleClass="align-right amount" >
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
                            <sl:collectionItem hidden="true">
                                <span class="onclickFunc" onclick="repeatPayment('${copyURL}');"></span>
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty payments}"/>
                    <tiles:put name="emptyMessage">
                        Пока Вы не совершили ни одной операции с помощью SMS-запросов. Для того чтобы перевести деньги, оплатить услуги и выполнить другие операции, перейдите на вкладку &laquo;<bean:message key="label.mainMenu.payments" bundle="commonBundle"/>&raquo;.
                    </tiles:put>
                </tiles:insert>
        </tiles:put>
    </tiles:insert>
</div>

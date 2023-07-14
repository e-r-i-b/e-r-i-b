<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form styleId="viewInvoiceFormId" action="/private/basket/invoice/view" onsubmit="return setEmptyAction();" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="invoice" value="${form.invoice}"/>
    <c:set var="subscriptionName" value="${form.subscriptionName}"/>
    <c:set var="bankName" value="${form.bankName}"/>
    <c:set var="bankAccount" value="${form.bankAccount}"/>
    <c:set var="availableOperations" value="${form.operationAvailable}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/invoice/process.do')}"/>
    <c:set var="paymentsUrl" value="${phiz:calculateActionURL(pageContext, '/private/payments.do')}"/>

    <html:hidden property="id" styleId="invoice_${invoice.id}"/>

    <div class="invoiceView">

        <div class="top">
            <div class="serviceTitle word-wrap">
                <c:out value="${invoice.nameService}"/>
            </div>
            <div class="serviceProviderTitle">
                <c:out value="${invoice.recName}"/>
            </div>
        </div>

        <c:if test="${!form.confirmSubscription}">
            <tiles:insert definition="warningBlock" flush="false">
                <tiles:put name="regionSelector" value="autoGeneratedMessage${invoice.id}"/>
                <tiles:put name="isDisplayed" value="true"/>
                <tiles:put name="data">
                    <bean:message key="invoice.generated.view.message" bundle="basketBundle"/>
                </tiles:put>
            </tiles:insert>
        </c:if>

            <%-- ��������� --%>
        <tiles:insert definition="warningBlock" flush="false">
            <tiles:put name="regionSelector" value="editEntityWarnings${invoice.id}"/>
            <tiles:put name="isDisplayed" value="${isEditable && needUngroupSubscriptions}"/>
        </tiles:insert>

            <%-- ������ --%>
        <tiles:insert definition="errorBlock" flush="false">
            <tiles:put name="regionSelector" value="editEntityErrors${invoice.id}"/>
            <tiles:put name="isDisplayed" value="false"/>
        </tiles:insert>

        <div id="invoicePaymentFormDiv${invoice.id}">
        <div class="body">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="���� ��������� �� ������:"/>
                <tiles:put name="data">
                    <span class="invoiceSubscriptionTitle">
                        <c:out value="${subscriptionName}"/>
                    </span>
                </tiles:put>
            </tiles:insert>

            <h2 class="additionalInfoHeader fontForH2">����������</h2>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="������������:"/>
                <tiles:put name="data">
                    <c:out value="${invoice.recName}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="���:"/>
                <tiles:put name="data">
                    <c:out value="${invoice.recInn}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="����:"/>
                <tiles:put name="data">
                    <c:out value="${invoice.recAccount}"/>
                </tiles:put>
            </tiles:insert>

            <h2 class="additionalInfoHeader fontForH2">���� ����������</h2>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="������������:"/>
                <tiles:put name="data">
                    <c:out value="${bankName}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="���:"/>
                <tiles:put name="data">
                    <c:out value="${invoice.recBic}"/>
                </tiles:put>
            </tiles:insert>
            <c:if test="${not empty invoice.recCorAccount || not empty bankAccount}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title" value="�������:"/>
                    <tiles:put name="data">
                        <c:choose>
                            <c:when test="${not empty invoice.recCorAccount}">
                                <c:out value="${invoice.recCorAccount}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${bankAccount}"/>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <h2 class="additionalInfoHeader fontForH2">������ �������</h2>

            <c:forEach var="field" items="${invoice.requisitesAsList}">
                <c:choose>
                    <c:when test="${not field.mainSum && field.visible}">
                        <tiles:insert definition="formRow" flush="false">
                            <tiles:put name="title" value="${field.name}"/>
                            <tiles:put name="data">
                                <c:out value="${field.value}"/>
                            </tiles:put>
                        </tiles:insert>
                    </c:when>
                    <c:when test="${field.mainSum}">
                        <c:set var="mainSum" value="${field}"/>
                    </c:when>
                </c:choose>
            </c:forEach>

        </div>

        <div class="bottom">
            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="���� ��������:"/>
                <tiles:put name="data">
                    <c:choose>
                        <c:when test="${phiz:getBasketInteractMode() == 'esb'}">
                            <select name="fromResource">
                                <c:forEach var="cardLink" items="${form.chargeOffResources}">
                                    <c:set var="card" value="${cardLink.value}"/>
                                    <option value="${cardLink.code}">
                                        <c:out value="${phiz:getCutCardNumber(cardLink.number)}"/>&nbsp;
                                        [<c:out value="${phiz:getCardUserName(cardLink)}"/>]&nbsp;
                                        <c:if test="${not empty card.availableLimit}">
                                            <c:out value="${phiz:formatAmount(card.availableLimit)}"/>
                                        </c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <c:set var="cardNumber" value="${invoice.cardNumber}"/>
                            <c:set var="cardLink" value="${phiz:getCardLink(cardNumber)}"/>
                            <c:set var="card" value="${cardLink.value}"/>
                            <c:out value="${phiz:getCutCardNumber(cardNumber)}"/>&nbsp;
                            [<c:out value="${phiz:getCardUserName(cardLink)}"/>]&nbsp;
                            <c:if test="${not empty card.availableLimit}">
                                <c:out value="${phiz:formatAmount(card.availableLimit)}"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title">
                    <span class="mainSumRow">�����</span>
                </tiles:put>
                <tiles:put name="data">
                    <div class="fullSumRow">
                        <span class="mainSumRow">
                            <bean:write name="mainSum" property="value" format="0.00"/>
                        </span>
                        <span class="currencyRow">���.</span>
                    </div>
                </tiles:put>
            </tiles:insert>

            <c:if test="${not empty invoice.commission}">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        <span class="commissionTitle">��������</span>
                    </tiles:put>
                    <tiles:put name="data">
                        <span class="commissionValue">
                            <bean:write name="invoice" property="commission" format="0.00"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
            </c:if>
            <div class="confirmArea" style="display: none;">
                <tiles:insert definition="formRow" flush="false">
                    <tiles:put name="title">
                        ������� SMS-������:
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="float">
                            <input type="text" name="$$confirmSmsPassword" size="10"/>
                        </div>
                        <div class="float rightBtnsInvoice">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.dispatch"/>
                                <tiles:put name="commandHelpKey" value="button.dispatch.help"/>
                                <tiles:put name="bundle" value="securityBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                                <tiles:put name="onclick">processInvoice(this, 'confirm')</tiles:put>
                            </tiles:insert>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>

            <c:if test="${availableOperations}">
                <div class="buttonsArea iebuttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.pay"/>
                        <tiles:put name="commandHelpKey" value="button.pay.help"/>
                        <tiles:put name="bundle" value="basketBundle"/>
                        <tiles:put name="onclick" value="processInvoice(this, 'pay')"/>
                    </tiles:insert>
                </div>
            </c:if>
        </div>
        <div style="display: none">
            <tiles:insert definition="confirmationButton" flush="false">
                <tiles:put name="winId" value="deleteConfirmationinvoice${invoice.id}"/>
                <tiles:put name="title"><bean:message key="message.confirm.delete.title" bundle="basketBundle"/></tiles:put>
                <tiles:put name="currentBundle"  value="basketBundle"/>
                <tiles:put name="confirmCommandKey" value="button.removeInvoice"/>
                <tiles:put name="buttonViewType" value="listDeleteInvoiceButton"/>
                <tiles:put name="message"><bean:message key="message.confirm.delete" bundle="basketBundle"/></tiles:put>
                <tiles:put name="afterConfirmFunction" value="processInvoice(this, 'remove')"/>
            </tiles:insert>
        </div>
        </div>

        <div class="invoiceOperations">
            <html:hidden property="field(chooseDelayDateInvoice)" name="form" styleId="chooseDelayDateInnerinvoice_${invoice.id}"/>
            <div class="delayButtonMargin">
                <a onclick="" class="delayButton delayIcon" id="delayButton_${invoice.id}">
                    <span class="blueGrayLinkDotted"><bean:message key="button.delay" bundle="basketBundle"/></span>
                </a>
            </div>
            <div class="removeButtonMargin">
                <a class="removeButton removeButtonIcon float">
                    <span class="redButton"><bean:message key="button.remove" bundle="basketBundle"/></span>
                </a>
                <div class="clear"></div>
                <span class="removeInvoiceHint">
                    <bean:message key="button.remove.hint" bundle="basketBundle"/>
                </span>
            </div>
            <div class="clear"></div>
            <c:if test="${form.accessRecoverAutoSub}">
                <tiles:insert definition="clientButton" flush="false" service="CloseInvoiceSubscriptionClaim">
                    <tiles:put name="commandTextKey" value="button.recover.autosub.view"/>
                    <tiles:put name="commandHelpKey" value="button.recover.autosub.view.help"/>
                    <tiles:put name="bundle" value="basketBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="recoverAutoSub();"/>
                    <tiles:put name="image" value="autoIcon.png"/>
                    <tiles:put name="imageHover" value="autoIconHover.png"/>
                    <tiles:put name="imagePosition" value="left"/>
                </tiles:insert>
            </c:if>
        </div>
    </div>

</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--todo CHG059738 �������--%>
<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="helpLink" value="${phiz:calculateActionURLWithAnchor(pageContext,'/help.do?id=/private/mobilebank/main', 'p4')}"/>
<c:set var="faqSMSTemplate" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'p4')}"/>
<c:set var="faqSMSPayment" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q20')}"/>
<c:set var="faqSMSPaymCard" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'q16')}"/>

<%-- ����������� --%>

<script type="text/javascript">
    function removeSmsCommand(recipientCode, payerCode)
    {
        addField ('hidden', 'selectedRecipientCode', recipientCode);
        addField ('hidden', 'selectedPayerCode', payerCode);
        return true;
    }
</script>

<c:forEach var="registration" items="${form.registrationProfiles}" varStatus="i">
    <c:set var="mainCard" value="${registration.mainCard}"/>
    <c:set var="mainPhone" value="${registration.mainPhoneNumber}"/>
    <c:set var="phoneCode" value="${phiz:getMobileBankPhoneCode(mainPhone)}"/>
    <c:set var="cardCode" value="${phiz:getMobileBankCardCode(mainCard.cardNumber)}"/>

    <c:set target="${form}" property="card" value="${mainCard}"/>

    <c:choose>
        <c:when test="${registration.tariff.code=='F'}">
            <c:set var="registrationTariff" value="������ �����"/>
        </c:when>
        <c:when test="${registration.tariff.code=='S'}">
            <c:set var="registrationTariff" value="��������� �����"/>
        </c:when>
        <c:otherwise>
            <c:set var="registrationTariff" value="�� ������"/>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${registration.status.code=='A'}">
            <c:set var="registrationStatus" value="�������"/>
            <c:set var="registrationColor" value="italicInfo"/>
        </c:when>
        <c:when test="${registration.status.code=='I'}">
            <c:set var="registrationStatus" value="�������������"/>
            <c:set var="registrationColor" value="text-red"/>
        </c:when>
        <c:when test="${registration.status.code=='PB'}">
            <c:set var="registrationStatus" value="�������������"/>
            <c:set var="registrationColor" value="text-red"/>
        </c:when>
        <c:when test="${registration.status.code=='CB'}">
            <c:set var="registrationStatus" value="�������������"/>
            <c:set var="registrationColor" value="text-red"/>
        </c:when>
        <c:otherwise>
            <c:set var="registrationStatus" value="�� ��������"/>
            <c:set var="registrationColor" value="italicInfo"/>
        </c:otherwise>
    </c:choose>

    <%-- ������ �� �������� � ����������� �� �������� ����� ����������� --%>
    <c:if test="${not empty mainCard.cardlink and not empty mainCard.cardlink.id}">
        <c:url var="mainCardPageLink" value="/private/cards/info.do">
            <c:param name="id"
                     value="${mainCard.cardlink.id}"/>
        </c:url>
    </c:if>

    <%-- ������ �� �������� ������ ����������� ��� ������������ ������� --%>
        <c:url var="createTemplateFromPaymentPageLink"
               value="/private/mobilebank/payments/create-template-from-payment.do">
            <c:param name="phoneCode" value="${phoneCode}"/>
            <c:param name="cardCode" value="${cardCode}"/>
        </c:url>

    <%-- ������ �� �������� ������� ������� --%>
    <c:url var="removeSmsCommandPageLink"
           value="/private/mobilebank/payments/list.do">
            <c:param name="phoneCode" value="${phoneCode}"/>
            <c:param name="cardCode" value="${cardCode}"/>
    </c:url>

    <c:set var="cardLink" value="${mainCard.cardlink}" scope="request"/>
    <c:set var ="card" value="${cardLink.card}"/>
    <c:set var="description" value="${cardLink.description}"/>
    <c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>

    <c:choose>
        <c:when test="${not empty card.cardLevel and card.cardLevel == 'MQ'}">
            <c:set var="imgCode" value="mq" />
        </c:when>
        <c:otherwise>
            <c:set var="imgCode" value="${phiz:getCardImageCode(description)}"/>
        </c:otherwise>
    </c:choose>

    <div id="mobilebank">
    <tiles:insert definition="mainWorkspace" flush="false">
        <tiles:put name="title" value="��������� ����"/>
    <tiles:put name="data">

        <tiles:insert page="header.jsp" flush="false">
            <tiles:put name="registrationStatus" value="${registrationStatus}"/>
            <tiles:put name="registrationColor" value="${registrationColor}"/>
            <tiles:put name="registrationTariff" value="${registrationTariff}"/>
            <tiles:put name="title"> ${phiz:getCutPhoneNumber(mainPhone)} </tiles:put>
            <tiles:put name="cardName" value="${phiz:getCardUserName(cardLink)}"/>
            <tiles:put name="cardNumber">
                ${phiz:getCutCardNumber(cardLink.number)}<br />
                ����� ��� �������� SMS-��������: <b>900</b>
            </tiles:put>
            <tiles:put name="amount" value="${phiz:formatAmount(cardLink.card.availableLimit)}" />
            <tiles:put name="rightData" value=""/>
            <c:if test="${not empty cardLink.id}">
                <tiles:put name="src" value="${cardInfoUrl}${cardLink.id}"/>
            </c:if>
            <tiles:put name="mainCardPageLink" value="${mainCardPageLink}" />
        </tiles:insert>

        <div class="tabContainer">
            <c:set var="paymentsPageLink" value="/private/mobilebank/payments/list.do?phoneCode=${phoneCode}&cardCode=${cardCode}"/>
            <tiles:insert definition="paymentTabs" flush="false">
                <tiles:put name="count" value="2"/>
                <tiles:put name="tabItems">
                    <tiles:insert definition="paymentTabItem" flush="false" service="FavouriteManagment">
                        <tiles:put name="position" value="first"/>
                        <tiles:put name="active" value="false"/>
                        <tiles:put name="title" value="������ �����������"/>
                        <tiles:put name="action" value="/private/mobilebank/main.do"/>
                    </tiles:insert>
                    <tiles:insert definition="paymentTabItem" flush="false" service="FavouriteManagment">
                        <tiles:put name="position" value="last"/>
                        <tiles:put name="active" value="true"/>
                        <tiles:put name="title" value="SMS-������� � �������"/>
                        <tiles:put name="action" value="${paymentsPageLink}"/>
                    </tiles:insert>

                </tiles:put>
            </tiles:insert>
        <div class="smsTemplatesTitle">
            <input type="hidden" name="phoneCode" value="${phoneCode}"/>
            <input type="hidden" name="cardCode" value="${cardCode}"/>
            <h2 class="inline"> SMS-������� �� ������ </h2>
            <html:link href="" onclick="openFAQ('${faqSMSPayment}'); return false;" styleClass="text-green">
                ��� ������������ SMS-�������?
            </html:link>
        </div>
        <c:choose>
            <c:when test="${not empty mainCard.paymentSmsTemplates}">
                <div class="templates roundTitleTbl">
                    <c:set target="${form}"
                           property="smsCommands"
                           value="${mainCard.paymentSmsTemplates}"/>
                    <tiles:insert page="sms-command-list.wide.jsp" flush="false">
                        <tiles:put name="headerExamples">
                            ������ <span class="italic">(������ 100�.)</span>
                        </tiles:put>
                        <tiles:put name="deletable" value="${true}"/>
                        <tiles:put name="reg_index" value="${i.index}"/>
                    </tiles:insert>
                </div>
            </c:when>
            <c:otherwise>
                <div class="infoSMSTemplateBlock">
                    <h3>� ��� ��� SMS-�������� </h3>
                </div>
            </c:otherwise>
        </c:choose>

        <c:set var="onClick" value="loadNewAction('',''); window.location='${createTemplateFromPaymentPageLink}';"/>

        <div class="button-block right">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add-template"/>
                    <tiles:put name="commandHelpKey" value="button.add-template"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <c:if test="${card.cardState != 'blocked'}">
                    <tiles:put name="onclick" value="${onClick}"/>
                </c:if>
                <c:if test="${card.cardState eq 'blocked' or card.cardState eq 'delivery'}">
                    <tiles:put name="onclick" value="addMessage('�� �� ������ ������� SMS-������, ������ ��� �� ����� ����� ��������� ��������� ��������.');"/>
                </c:if>
            </tiles:insert>
        </div>
        <div class="clear"></div>

        <c:if test="${not empty mainCard.paymentSmsRequests}">
        <div class="smsTemplatesTitle">
            <h2 class="inline"> SMS-������� �� ������ � ����� </h2>
            <html:link href="" onclick="openFAQ('${faqSMSPaymCard}'); return false;"
                       title="��� ���������� �� SMS-��������?"
                       styleClass="text-green">
                ��� ���������� �� SMS-��������?
            </html:link>
        </div>
        <c:set target="${form}"
               property="smsCommands"
               value="${mainCard.paymentSmsRequests}"/>
            <div class="roundTitleTbl">
                <tiles:insert page="sms-command-list.wide.jsp" flush="false">
                    <tiles:put name="headerExamples">
                        ������ SMS-�������
                    </tiles:put>
                    <tiles:put name="reg_index" value="${i.index}"/>
                </tiles:insert>
            </div>
        </c:if>
        </div>
    </tiles:put>
    </tiles:insert>
    </div>
</c:forEach>

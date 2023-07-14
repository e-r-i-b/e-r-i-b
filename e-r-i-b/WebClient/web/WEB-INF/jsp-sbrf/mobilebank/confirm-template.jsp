<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--todo CHG059738 удалить--%>
<tiles:importAttribute/>
<html:form action="/private/mobilebank/payments/confirm-template">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.update)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <%-- ссылка на страницу со списком SMS-шаблонов платежей по основной карте подключения --%>
    <c:url var="listPaymentTemplatesPageLink" value="/private/mobilebank/payments/list.do">
        <c:param name="phoneCode" value="${form.phoneCode}"/>
        <c:param name="cardCode" value="${form.cardCode}"/>
    </c:url>

    <%-- ссылка на страницу редактирования SMS-шаблона платежа --%>
    <%--
    <c:url var="editTemplatePageLink"
           value="/private/mobilebank/payments/create-template.do">
        <c:param name="phoneCode" value="${form.phoneCode}"/>
        <c:param name="cardCode" value="${form.cardCode}"/>
    </c:url>
--%>

    <tiles:insert definition="mobilebank">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мобильный банк"/>
                <tiles:put name="action" value="/private/mobilebank/main.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Создание SMS-шаблона"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">

            <script type="text/javascript">
                function cancel()
                {
                    loadNewAction('', '');
                    window.location = '${listPaymentTemplatesPageLink}';
                }
            </script>

            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Создание SMS-шаблона"/>
                <tiles:put name="data" type="string">
                    <div id="mobilebank">
                        <html:hidden name="form" property="phoneCode"/>
                        <html:hidden name="form" property="cardCode"/>
                        <html:hidden name="form" property="updateId"/>
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_SmsTemplate.png"/>
                            <tiles:put name="description">
                                <h3>Внимательно проверьте реквизиты SMS-шаблона. После этого подтвердите операцию SMS-паролем.</h3>
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                        <div id="paymentStripe">
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="создание SMS-шаблона"/>
                                <tiles:put name="future" value="false"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="заполнение реквизитов"/>
                                <tiles:put name="future" value="false"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="подтверждение"/>
                                <tiles:put name="current" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="просмотр"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>

                        <tiles:insert page="template-form.jsp" flush="false"/>

                        <div>
                             <div style="position:relative;height:28px;">&nbsp;</div>
                             <div class="buttonsArea">
                                 <tiles:insert definition="clientButton" flush="false">
                                     <tiles:put name="commandTextKey" value="button.cancel"/>
                                     <tiles:put name="commandHelpKey" value="button.cancel"/>
                                     <tiles:put name="bundle" value="mobilebankBundle"/>
                                     <tiles:put name="viewType" value="buttonGrey"/>
                                     <tiles:put name="onclick" value="cancel();"/>
                                 </tiles:insert>
                                 <c:choose>
                                     <c:when test="${not empty confirmRequest}">
                                         <c:set var="confirmTemplate" value="confirm_${confirmRequest.strategyType}"/>
                                         <tiles:insert definition="${confirmTemplate}" flush="false">
                                             <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                             <tiles:put name="confirmableObject" value="SMSTemplate"/>
                                             <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                             <tiles:put name="message" type="string">
                                                 Проверьте правильность составления SMS-шаблона
                                                 и нажмите кнопку "Подтвердить".
                                             </tiles:put>
                                             <tiles:put name="data">
                                                 <tiles:insert page="template-form.jsp" flush="false"/>
                                             </tiles:put>
                                        </tiles:insert>
                                     </c:when>
                                     <c:otherwise>
                                         <tiles:insert definition="commandButton" flush="false">
                                             <tiles:put name="commandKey"     value="button.preConfirm"/>
                                             <tiles:put name="commandHelpKey" value="button.preConfirm"/>
                                             <tiles:put name="bundle"         value="mobilebankBundle"/>
                                             <tiles:put name="isDefault"        value="true"/>
                                         </tiles:insert>
                                     </c:otherwise>
                                 </c:choose>
                             </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
            </tiles:insert> <!-- end of teaser -->
        </tiles:put>
    </tiles:insert> <!-- end of mobilebank -->

</html:form>
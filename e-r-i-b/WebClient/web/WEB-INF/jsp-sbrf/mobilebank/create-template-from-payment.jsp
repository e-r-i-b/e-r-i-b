<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%--todo CHG059738 удалить--%>
<tiles:importAttribute/>
<html:form action="/private/mobilebank/payments/create-template-from-payment">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm16')}"/>
    <c:set var="firstRegistrationForm" value="true"/>

    <%--ссылка на страницу создани€ смс-шаблона из шаблона платежа--%>
    <c:url var="createSmsFormTemplateLink" value="/private/mobilebank/payments/create-template.do">
        <c:param name="phoneCode" value="${form.phoneCode}"/>
        <c:param name="cardCode" value="${form.cardCode}"/>
    </c:url>
    <%-- ссылка на страницу со списком платежей по основной карте подключени€ --%>
    <c:url var="paymentsPageLink" value="/private/mobilebank/payments/list.do">
        <c:param name="phoneCode" value="${form.phoneCode}"/>
        <c:param name="cardCode" value="${form.cardCode}"/>
    </c:url>

    <%-- ссылка на страницу выбора поставщиков дл€ добавлени€ нового шаблона --%>
    <c:url var="selectServiceProviderPageLink"
           value="/private/mobilebank/payments/select-category-provider.do">
        <c:param name="phoneCode" value="${form.phoneCode}"/>
        <c:param name="cardCode" value="${form.cardCode}"/>
    </c:url>

    <tiles:insert definition="mobilebank">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="ћобильный банк"/>
                <tiles:put name="action" value="/private/mobilebank/main.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="—оздание SMS-шаблона"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">

            <script type="text/javascript">
                function createNew()
                {
                    loadNewAction('','');
                    window.location='${selectServiceProviderPageLink}';
                }

                function cancel()
                {
                    loadNewAction('', '');
                    window.location = '${paymentsPageLink}';
                }
            </script>

            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="—оздание SMS-шаблона"/>
                <tiles:put name="data" type="string">
                    <div id="mobilebank">
                        <html:hidden name="form" property="phoneCode"/>
                        <html:hidden name="form" property="cardCode"/>
                        <%@ include file="templateHeader.jsp" %>
                        <h4>¬аши шаблоны дл€ создани€ SMS-шаблонов</h4>
                            <html:link href="" onclick="openFAQ('${faqLink}'); return false;" styleClass="text-green">
                                почему здесь не все мои шаблоны?
                            </html:link>
                        <%-- —писок с шаблонами платежей --%>
                        <br/>
                        <c:set var="haveChoise" value="${false}"/>
                        <input type="hidden" id="selectedTemplateId" name="selectedTemplateId">
                         <tiles:insert definition="simpleTableTemplate" flush="false">
                             <tiles:put name="grid">
                                <sl:collection id="template" property="templates" model="simple-pagination">
                                    <sl:collectionItem>
                                        <html:link href="${createSmsFormTemplateLink}&selectedTemplateId=${template.id}">
                                            <c:out value="${template.name}"/>
                                        </html:link>
                                    </sl:collectionItem>
                                </sl:collection>
                            </tiles:put>
                            <tiles:put name="emptyMessage">
                                ” ¬ас нет шаблонов платежей,
                                на основе которых можно было бы создать
                                SMS-шаблоны
                            </tiles:put>
                        </tiles:insert>

                        <div class="teplate-button-block">
                           “акже ¬ы можете 
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.create-new-template"/>
                                <tiles:put name="commandHelpKey" value="button.create-new-template"/>
                                <tiles:put name="bundle" value="mobilebankBundle"/>
                                <tiles:put name="onclick" value="createNew();"/>
                            </tiles:insert>
                             или

                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="button.cancel"/>
                                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                                <tiles:put name="bundle"            value="paymentsBundle"/>
                                <tiles:put name="viewType"          value="buttonGrey"/>
                                <tiles:put name="action"            value="/private/mobilebank/main"/>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--todo CHG059738 удалить--%>
<tiles:importAttribute/>
<html:form action="/private/mobilebank/payments/create-template">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <%-- ссылка на страницу со списком шаблонов платежей по основной карте подключения --%>
    <c:url var="paymentsPageLink" value="/private/mobilebank/payments/list.do">
        <c:param name="phoneCode" value="${form.phoneCode}"/>
        <c:param name="cardCode" value="${form.cardCode}"/>
    </c:url>

    <%-- ссылка на страницу выбора поставщиков для добавляемого шаблона --%>
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
                    window.location = '${paymentsPageLink}';
                }
                function goback()
                {
                    loadNewAction('', '');
                    window.location = '${selectServiceProviderPageLink}';
                }
            </script>

            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Создание SMS-шаблона"/>
                <tiles:put name="data" type="string">
                    <div id="mobilebank">
                    <html:hidden name="form" property="phoneCode"/>
                    <html:hidden name="form" property="cardCode"/>
                    <html:hidden name="form" property="template"/>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/payments/servicesPaymentData.jsp" flush="false">
                        <tiles:put name="header" type="string">
                            <tiles:insert definition="formHeader" flush="false">
                                <tiles:put name="image" value="${imagePath}/icon_SmsTemplate.png"/>
                                <tiles:put name="description">
                                    Для создания SMS-шаблона заполните необходимые поля и нажмите на кнопку «Сохранить SMS-шаблон».<br/>
                                    <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
                                    <span class="text-red">*</span>
                                    <span class="text-gray">.</span>
                                </tiles:put>
                            </tiles:insert>
                            <div class="clear"></div>
                        </tiles:put>
                        <tiles:put name="stripe" type="string">
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="создание SMS-шаблона"/>
                                <tiles:put name="future" value="'false"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="заполнение реквизитов"/>
                                <tiles:put name="current" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="подтверждение"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="просмотр"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="paymentFieldsHtml">
                            <tiles:insert page="/WEB-INF/jsp-sbrf/payments/servicePaymentFields.jsp" flush="false">
                                <tiles:put name="noProviderMessage">
                                    Не найдено ни одного поставщика!
                                </tiles:put>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="fieldDisabled" value="${false}"/>
                        <tiles:put name="templateId" value="${form.template}"/>
                        <tiles:put name="backToServicesButton" type="string">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                                <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                                <tiles:put name="bundle" value="mobilebankBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="goback();"/>
                                <tiles:put name="image"       value="backIcon.png"/>
                                <tiles:put name="imageHover"     value="backIconHover.png"/>
                                <tiles:put name="imagePosition"  value="left"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="cancelButton" type="string">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="mobilebankBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="cancel();"/>
                            </tiles:insert>
                        </tiles:put>
                        <tiles:put name="nextButton" type="string">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.save"/>
                                <tiles:put name="commandHelpKey" value="button.save"/>
                                <tiles:put name="bundle" value="mobilebankBundle"/>
                                <tiles:put name="isDefault" value="true"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                    </div>
                    <%-- Эмулируем нажатие на кнопку при Enter. Хидден должен идти после всех командных кнопок --%>
                    <input type="hidden" name="operation.button.save" value="save" />
                </tiles:put>
            </tiles:insert>
            <script type="text/javascript">
                $(document).ready(function(){
                    initialize();
                });
            </script>
        </tiles:put>

    </tiles:insert>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--todo CHG059738 удалить--%>
<tiles:importAttribute/>
<html:form action="/private/mobilebank/payments/view-added-template">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.update)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

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
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Создание SMS-шаблона"/>
                <tiles:put name="data" type="string">
                    <div id="mobilebank">
                        <html:hidden name="form" property="phoneCode"/>
                        <html:hidden name="form" property="cardCode"/>
                        <html:hidden name="form" property="updateId"/>

                        <script type="text/javascript">
                            addMessage("Новый SMS-шаблон создан успешно.");
                        </script>
                        
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_SmsTemplate.png"/>
                            <tiles:put name="description">
                                <h3>На этой странице Вы можете посмотреть реквизиты SMS-шаблона. Список всех
                                    SMS-шаблонов Вы можете увидеть на странице «Мобильный банк» -
                                    «SMS-шаблоны и запросы».</h3>
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
                                <tiles:put name="future" value="false"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="просмотр"/>
                                <tiles:put name="current" value="true"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>


                        <tiles:insert page="template-form.jsp" flush="false"/>

                        <div>
                             <div class="confirmButtonsArea">
                                 <tiles:insert definition="commandButton" flush="false">
                                     <tiles:put name="commandKey" value="button.back-to-payments"/>
                                     <tiles:put name="commandHelpKey" value="button.back-to-payments"/>
                                     <tiles:put name="bundle" value="mobilebankBundle"/>
                                     <tiles:put name="isDefault" value="true"/>
                                     <tiles:put name="viewType" value="blueGrayLink"/>
                                 </tiles:insert>
                             </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
            </tiles:insert> <!-- end of teaser -->
        </tiles:put>
    </tiles:insert> <!-- end of mobilebank -->

</html:form>
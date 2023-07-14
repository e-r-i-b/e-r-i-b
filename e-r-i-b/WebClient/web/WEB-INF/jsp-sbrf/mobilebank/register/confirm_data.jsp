<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.ConfirmRegistrationForm"--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="confirmRequest" value="${form.confirmRequest}"/>
<c:url var="confirmURL" value="${form.confirmActionPath}"/>

<%-- для создания окна подтверждения --%>
<tiles:insert definition="window"  flush="false">
     <tiles:put name="id" value="oneTimePasswordWindow"/>
</tiles:insert>

<html:hidden name="form" property="id"/>
<html:hidden name="form" property="returnURL"/>

<tiles:insert definition="roundBorder" flush="false">
    <c:if test="${param.afterLogin == 'true'}">
        <tiles:put name="color" value="greenTop"/>
    </c:if>
    <tiles:put name="title">
        <div class="align-left">Подключение услуги "Мобильный Банк"</div>
    </tiles:put>
    <tiles:put name="data">

        <%-- Заголовок --%>
        <tiles:insert definition="formHeader" flush="false">
            <tiles:put name="description">
                <h3>Внимательно проверьте параметры подключения услуги "Мобильный банк".
                    После этого подтвердите операцию SMS-паролем.
                    SMS-пароль будет отправлен на телефон, подключаемый к услуге.
                </h3>
            </tiles:put>
        </tiles:insert>

        <%-- Линия жизни --%>
        <div id="paymentStripe" <c:if test="${param.afterLogin == 'true'}">class="login-register-stripe"</c:if>>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="выбор пакета"/>
               <tiles:put name="future" value="false"/>
           </tiles:insert>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="заполнение заявки"/>
               <tiles:put name="future" value="false"/>
           </tiles:insert>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="подтверждение"/>
               <tiles:put name="current" value="true"/>
           </tiles:insert>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="регистрация заявки"/>
           </tiles:insert>
           <div class="clear"></div>
        </div>

        <%-- Форма --%>
        <%@ include file="view_fields.jsp"  %>

        <%-- Кнопки --%>
        <div class="buttonsArea">
            <div class="float">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.register.back"/>
                    <tiles:put name="commandHelpKey" value="button.register.back"/>
                    <tiles:put name="bundle" value="mobilebankBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="${form.backActionPath}"/>
                </tiles:insert>
            </div>
            <c:choose>
                <c:when test="${not empty form.returnURL}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.register.skip"/>
                        <tiles:put name="commandHelpKey" value="button.register.skip"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                        <tiles:put name="onclick">goTo('${form.returnURL}');</tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="skip"/>
                        <tiles:put name="commandTextKey" value="button.register.skip"/>
                        <tiles:put name="commandHelpKey" value="button.register.skip"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            &nbsp;&nbsp;
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.register.confirmSMS"/>
                <tiles:put name="commandHelpKey" value="button.register.confirmSMS"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="onclick">
                    confirmOperation.openConfirmWindow('preconfirm','${confirmURL}');
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/userprofile/basket/payments/payment" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentsBasket">
        <%-- заголовок --%>
        <tiles:put name="pageTitle" type="string">
            <c:out value="${metadata.form.description}"/>
        </tiles:put>

        <tiles:put name="mainmenu" value="Payments"/>
        <tiles:put name="submenu" type="string" value="${listFormName}"/>

        <%-- собственно данные --%>
        <tiles:put name="data" type="string">
            <html:hidden property="form"/>
            <tiles:insert page="../paymentContext.jsp" flush="false"/>
            <tiles:insert definition="profileTemplate" flush="false">
                <tiles:put name="activeItem" value="searchAccounts"/>
                <tiles:put name="title" value="Настройка автопоиска счетов по услуге"/>
                <tiles:put name="data">
                    <div id="paymentStripe">
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="выбор услуги"/>
                            <tiles:put name="width" value="230px"/>
                            <tiles:put name="future" value="false"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="заполнение формы"/>
                            <tiles:put name="width" value="230px"/>
                            <tiles:put name="current" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="stripe" flush="false">
                            <tiles:put name="name" value="подтверждение"/>
                            <tiles:put name="width" value="230px"/>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                    <div onkeypress="onEnterKey(event);">
                        <div id="paymentForm">
                            ${form.html}
                        </div>
                    </div>
                    <div class="clear"></div>
                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.back"/>
                            <tiles:put name="commandHelpKey" value="button.back.help"/>
                            <tiles:put name="bundle" value="claimsBundle"/>
                            <tiles:put name="action" value="/private/payments"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.save"/>
                            <tiles:put name="commandTextKey" value="button.start.autosearch"/>
                            <tiles:put name="commandHelpKey" value="button.start.autosearch.help"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="stateObject" value="document"/>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.prev"/>
                        <tiles:put name="commandHelpKey" value="button.prev.help"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="blueGrayLink"/>
                        <tiles:put name="stateObject" value="document"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <tiles:insert page="/WEB-INF/jsp-sbrf/basket/invoiceSubscription/profileDocumentsWin.jsp" flush="false"/>
</html:form>
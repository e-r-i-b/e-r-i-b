<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<%--
компонент дл€ отображени€ общей части дл€ страниц выбора за€вки на кредтный продукт или карту
data - html данные, различные дл€ каждого типа за€вки
dataSize - кол-во данных, пришедших на форму, служит дл€ вывода сообщени€ о пустоте
title - название страницы, формы
emptyMessage - сообщение в случае пустых данных
description - сообщение формы
breadcrumbs - остальна€ часть "хлебных крошек"
imageId - название иконки
mainmenu - пункт меню в котором находимс€
oneTimePassword - введЄн ли одноразовый пароль при входе
confirmRequest -  если oneTimePassword = false надо передавать
confirmStrategy - если oneTimePassword = false надо передавать
--%>

<tiles:insert definition="paymentMain">
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

   <%-- <c:out value="${confirmStrategy}"/>
    <c:out value="${confirmRequest}"/>--%>
    <tiles:put name="mainmenu" value="${empty mainmenu ? 'Payments': mainmenu}"/>
    <tiles:put name="pageTitle" type="string">${title}</tiles:put>

    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        ${breadcrumbs}
    </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="title" value="${title}"/>
            <tiles:put name="id" value="none"/>
            <tiles:put name="showHeader" value="false"/>
            <tiles:put name="data">
                <c:choose>
                    <c:when test="${dataSize}">

                        <tiles:put name="stripe" type="string"/>
                        <script type="text/javascript">
                            function toCancel()
                            {

                            }
                        </script>
                        ${data}


                        <tiles:put name="buttons">
                            <script type="text/javascript">

                                function checkData()
                                {
                                    checkIfOneItem("offerId");

                                    if (this.afterCheckIfOneItem)
                                        checkIfOneItem("offerId", this.afterCheckIfOneItem);
                                    else
                                        checkIfOneItem("offerId");

                                    if (getSelectedQnt('offerId') == 0)
                                    {
                                        addMessage('¬ыберите кредитный продукт');
                                        return false;
                                    }
                                    $('input:radio:checked').click();

                                    if (getSelectedQnt('agree') == 0)
                                    {
                                        addMessage('ƒл€ выдачи одобренной суммы подтвердите ваше согласие с »ндивидуальными услови€ми кредитовани€.');
                                        return false;
                                    }
                                    return true;
                                }

                                function openRefuseMoneyWindow()
                                {
                                    win.open('refuseMoneyWin');
                                }

                                function refuseMoney()
                                {
                                    var params = 'operation=button.refuseMoney&claimId=' + '${param.claimId}' + '&appNum=' + '${param.appNum}';
                                    ajaxQuery(params, "${phiz:calculateActionURL(pageContext, "/private/async/credit/offert/confirm")}",
                                            function(data){
                                                window.location = '${phiz:calculateActionURL(pageContext,"private/loans/list.do")}';
                                            }, null, false);

                                }
                            </script>
                            <div class="buttonsArea offerButtons">
                                <div class="orderseparate offerSeparate"></div>
                                <c:choose>
                                    <c:when test="${oneTimePassword}">
                                        <div class="floatRight">
                                            <tiles:insert definition="clientButton" flush="false">
                                                <tiles:put name="commandTextKey" value="button.refuseMoney"/>
                                                <tiles:put name="commandHelpKey" value="button.refuseMoney"/>
                                                <tiles:put name="bundle"         value="offertBundle"/>
                                                <tiles:put name="onclick">openRefuseMoneyWindow()</tiles:put>
                                                <tiles:put name="viewType" value="buttonRed"/>
                                            </tiles:insert>
                                            <tiles:insert definition="window" flush="false">
                                                <tiles:put name="id" value="refuseMoneyWin"/>
                                                <tiles:put name="data">
                                                    <html:hidden property="id"/>
                                                    <div class="title">
                                                        <h1><bean:message bundle="offertBundle" key="labal.credit.are.you.sure"/></h1>
                                                    </div>

                                                    <div class="info">
                                                        <bean:message bundle="offertBundle" key="labal.credit.will.do.it.again"/>
                                                     </div>
                                                    <div class="buttonsArea">
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.back.to.credit"/>
                                                            <tiles:put name="commandHelpKey" value="button.back.to.credit"/>
                                                            <tiles:put name="bundle" value="offertBundle"/>
                                                            <tiles:put name="onclick" value="win.close('refuseMoneyWin');"/>
                                                        </tiles:insert>
                                                        <tiles:insert definition="clientButton" flush="false">
                                                            <tiles:put name="commandTextKey" value="button.refuse.issue"/>
                                                            <tiles:put name="commandHelpKey" value="button.refuse.issue"/>
                                                            <tiles:put name="bundle" value="offertBundle"/>
                                                            <tiles:put name="onclick" value="refuseMoney();"/>
                                                            <tiles:put name="viewType" value="buttonGrey"/>
                                                        </tiles:insert>
                                                        <div class="clear"></div>
                                                      </div>
                                                </tiles:put>
                                            </tiles:insert>
                                        </div>
                                        <div class="float offerMainBtn">
                                            <tiles:insert definition="confirmButtons" flush="false">
                                                <tiles:put name="ajaxUrl" value="/private/async/credit/offert/confirm"/>
                                                <tiles:put name="preConfirmCommandKey" value="button.preConfirm"/>
                                                <tiles:put name="messageBundle" value="offertBundle"/>
                                                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                                <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                                <tiles:put name="hasCapButton" value="false"/>
                                                <tiles:put name="hasCard" value="false"/>
                                                <tiles:put name="hasPush" value="false"/>
                                                <tiles:put name="anotherStrategy" value="false"/>
                                            </tiles:insert>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <%--<tiles:insert definition="clientButton" flush="fase">--%>
                                            <%--<tiles:put name="commandTextKey" value="button.viewOffert"/>--%>
                                            <%--<tiles:put name="commandHelpKey" value="button.viewOffert"/>--%>
                                            <%--<tiles:put name="bundle" value="offertBundle"/>--%>
                                            <%--<tiles:put name="onclick" value="openWindowOffert(event);"/>--%>
                                        <%--</tiles:insert>--%>
                                        <tiles:insert definition="confirmButtons" flush="false">
                                            <tiles:put name="ajaxUrl" value="/private/async/credit/offert/text/confirm"/>
                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                            <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                            <tiles:put name="preConfirmCommandKey" value="button.viewOffert"/>
                                            <tiles:put name="messageBundle" value="offertBundle"/>
                                        </tiles:insert>

                                    </c:otherwise>
                                </c:choose>


                            </div>
                        </tiles:put>
                    </c:when>
                    <c:otherwise>

                        <div class="form-row error">
                                ${emptyMessage}
                        </div>
                        <div class="clear"></div>


                    </c:otherwise>
                </c:choose>
            </tiles:put>
        </tiles:insert>
    </tiles:put>


</tiles:insert>
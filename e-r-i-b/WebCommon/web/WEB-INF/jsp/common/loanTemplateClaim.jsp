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
компонент для отображения общей части для страниц выбора заявки на кредтный продукт или карту
data - html данные, различные для каждого типа заявки
dataSize - кол-во данных, пришедших на форму, служит для вывода сообщения о пустоте
title - название страницы, формы
emptyMessage - сообщение в случае пустых данных
description - сообщение формы
breadcrumbs - остальная часть "хлебных крошек"
imageId - название иконки
backToIncome   - выводить ссылку "Назад к выбору дохода"
hideLinkBackTo - нужно ли выводить ссылку назад
loanCardClaimAvailable - для отключения кнопки "далее" при выборе кредитной карты
mainmenu - пункт меню в котором находимся
--%>

   <tiles:insert definition="paymentMain">
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <tiles:put name="mainmenu"  value="${empty mainmenu ? 'Payments': mainmenu}"/>
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
                    <tiles:put name="id" value="${imageId}"/>   
                    <tiles:put name="data">
                     <c:choose>
                        <c:when test="${dataSize}">
                         <tiles:put name="description">
                             ${description}
                         </tiles:put>

                        <tiles:put name="stripe" type="string">
                           <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="выбор условий"/>
                                <tiles:put name="current" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="оформление заявки"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="подтверждение"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="статус"/>
                            </tiles:insert>
                        </tiles:put>
                            <script type="text/javascript">
                                function toCancel()
                                {
                                    <c:choose>
                                        <c:when test="${not empty mainmenu}">
                                            <c:if test="${mainmenu == 'Cards'}">
                                                window.location = "../cards/list.do";
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            window.location = "../payments.do";
                                        </c:otherwise>
                                    </c:choose>
                                }
                            </script>
                                            ${data}


                        <tiles:put name="buttons">
                            <script type="text/javascript">
                                function checkData()
                                {
                                    checkIfOneItem("loanId");

                                    if (this.afterCheckIfOneItem)
                                        checkIfOneItem("loanId", this.afterCheckIfOneItem);
                                    else
                                        checkIfOneItem("loanId");

                                    if (getSelectedQnt('loanId') == 0)
                                    {
                                        addMessage('Выберите кредитный продукт');
                                        return false;
                                    }
                                    $('input:radio:checked').click();
                                    return true;
                                }
                            </script>
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="onclick" value="toCancel()"/>
                                    <tiles:put name="commandTextKey" value="button.cancel"/>
                                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="bundle" value="loansBundle"/>
                                </tiles:insert>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.next"/>
                                    <tiles:put name="commandTextKey" value="button.next"/>
                                    <tiles:put name="commandHelpKey" value="button.next.help"/>
                                    <tiles:put name="bundle" value="loansBundle"/>
                                    <tiles:put name="validationFunction">checkData()</tiles:put>
                                    <tiles:put name="enabled" value="${loanCardClaimAvailable}"/>
                                </tiles:insert>
                            </div>
                            <c:choose>
                                <c:when test="${backToIncome}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="action" value="/private/payments/income_level.do"/>
                                        <tiles:put name="commandTextKey" value="button.back.to.income"/>
                                        <tiles:put name="commandHelpKey" value="button.back.to.income"/>
                                        <tiles:put name="viewType" value="blueGrayLink"/>
                                        <tiles:put name="bundle" value="loansBundle"/>
                                    </tiles:insert>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${empty hideLinkBackTo}">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                                            <tiles:put name="bundle" value="paymentsBundle"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="action" value="/private/payments"/>
                                            <tiles:put name="image"       value="backIcon.png"/>
                                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                                            <tiles:put name="imagePosition"  value="left"/>
                                        </tiles:insert>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>       
                    </c:when>    
                    <c:otherwise>

                            <div class="form-row error" >
                                ${emptyMessage}
                            </div>
                            <div class="clear"></div>


                    </c:otherwise>
            </c:choose>
                    </tiles:put>
          </tiles:insert>
      </tiles:put>


   </tiles:insert>
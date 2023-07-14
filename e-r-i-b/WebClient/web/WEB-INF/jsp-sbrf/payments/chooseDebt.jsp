<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<html:form action="/private/payments/servicesPayments/edit"
           onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="paymentCurrent">
    <tiles:put name="mainmenu" value="Payments"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
            <tiles:put name="action" value="/private/payments.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="${paymentService.name}"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
    <div id="payment">
        <tiles:insert definition="roundBorder" flush="false">
        <tiles:put name="data">
            <tiles:insert page="paymentContext.jsp" flush="false"/>
            <c:set var="imageId" value="${paymentService.imageId}"/>
            <c:choose>
                <c:when test="${not empty imageId}">
                    <img class="icon" src="${pageContext.request.contextPath}/images?id=${imageId}" alt=""/>
                </c:when>
                <c:otherwise>
                    <img class="icon" src="${imagePathGlobal}/icon_pmnt_other.jpg" alt=""/>
                 </c:otherwise>
            </c:choose>
            <h1>${paymentService.name}</h1>
            <h3>Укажите задолженность, которую Вы хотите оплатить. Для этого установите переключатель в интересующее Вас поле и нажмите на кнопку &laquo;Оплатить&raquo;.</h3><br/>  <!-- Заголовок платежки -->
            <div class="clear"></div>
            <div id="paymentForm">
                <c:forEach var="debt" items="${form.debts}">
                    <tiles:insert definition="fieldWithHint" flush="false">
                        <tiles:put name="externalId" value="field(debtCode)"/>
                        <tiles:put name="fieldName" value="${debt.description}"/>
                        <tiles:put name="data">
                            <c:forEach var="row" items="${debt.rows}">
                                <html:radio property="field(debtCode)" styleId="field(debtCode)${row.code}" value="${row.code}"/>
                                <span>
                                    <c:out value="${row.description}"/>
                                    <nobr>
                                        <bean:write name="row" property="debt.decimal" format="0.00"/>&nbsp;<bean:write name="row" property="debt.currency.code"/>
                                    </nobr>
                                </span><br />
                            </c:forEach>
                            <c:if test="${form.debtCode!=null}">
                                <script type="text/javascript">
                                    var el = document.getElementById("field(debtCode)${form.debtCode}");
                                    if(el!=null)
                                        el.checked = true;
                                </script>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                 </c:forEach>
            </div>
               <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.prev"/>
                    <tiles:put name="commandHelpKey" value="button.prev"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                </tiles:insert>
             <div class="clear"></div>
             <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="action" value="/private/payments"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.next"/>
                    <tiles:put name="commandHelpKey" value="button.next"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                </tiles:insert>
            </div>
             <div class="clear"></div>
        </tiles:put>
        </tiles:insert>
    </div>
    </tiles:put>
</tiles:insert>

</html:form>

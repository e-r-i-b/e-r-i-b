<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>
<html:form action="/private/payments/servicesPayments/edit"
           onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
    <c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
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
            <tiles:insert page="paymentContext.jsp" flush="false"/>
            <div id="payment">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="data">
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
                        <h3>Для продолжения совершения платежа заполните поля данной формы и нажмите на кнопку &laquo;Оплатить&raquo;.</h3><br/>  
                        <div class="clear"></div>
                        <div id="paymentForm" onkeypress="onEnterKey(event);">
                            <c:forEach var="field" items="${form.keyFields}">
                                <tiles:insert definition="fieldWithHint" flush="false">
                                    <tiles:put name="fieldName" value="${field.name}"/>
                                    <tiles:put name="externalId" value="${field.externalId}"/>
                                    <tiles:put name="description">${field.description}</tiles:put>
                                    <tiles:put name="required" value="${field.required}"/>
                                    <tiles:put name="hint">${field.hint}</tiles:put>
                                    <tiles:put name="data">
                                         <c:choose>
                                            <c:when test="${field.type == 'list'}">
                                                <html:select property="field(${field.externalId})">
                                                    <c:set var="values" value="${field.values}"/>
                                                    <c:forEach var="value" items="${values}">
                                                        <html:option value="${value}">
                                                            <c:out value="${value}"/>
                                                        </html:option>
                                                    </c:forEach>
                                                </html:select>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="className" value=""/>
                                                <c:if test="${field.type == 'date'}"><c:set var="className" value="dot-date-pick"/></c:if>
                                                <input id="${field.externalId}" name="field(${field.externalId})" value="${form.fields[field.externalId]}" class="${className}" type="text"/>
                                            </c:otherwise>
                                        </c:choose>                                                                                                                                                              
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

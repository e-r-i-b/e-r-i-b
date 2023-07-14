<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/autopayment/freeDetatilAutoSub/providers">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="left.menu.autopayments" bundle="autopaymentsBundle"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="AutoSubscriptions"/>

        <!--меню-->
        <tiles:put name="menu" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>

        <tiles:put name="data" type="string">
            <c:set var="sizeResult" value="${fn:length(form.providers)}"/>
            <c:set var="searchPage" value="${form.searchPage}"/>
            <div id="listProviders">
                <c:forEach var="element" items="${form.providers}" varStatus="stat">
                    <c:set var="providerId" value="${element.id}"/>
                    <c:set var="nameProvider" value="${element.name}"/>
                    <c:set var="innProvider" value="${element.INN}"/>
                    <c:set var="accountProvider" value="${element.account}"/>
                    <c:set var="imageId" value="${element.imageId}"/>

                    <div class="cardProvider">
                        <c:choose>
                            <c:when test="${not empty imageId}">
                                <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="image" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg" />
                            </c:otherwise>
                        </c:choose>

                        <tiles:insert definition="simpleProviderTemplate" flush="false">
                            <tiles:put name="image" value="${image}"/>
                            <tiles:put name="data">
                                <phiz:link action="/autopayment/servicesPayments" styleClass="fastSearchLink bold"
                                           operationClass="CreateESBAutoPayOperation" serviceId="CreateEmployeeAutoPayment">
                                    <phiz:param name="recipient" value="${providerId}"/>
                                    <phiz:param name="fromResource" value="${param['fromResource']}"/>
                                    <c:out value="${nameProvider}"/>
                                </phiz:link>
                                <div>ИНН: ${innProvider}</div>
                                <div>Расчетный счет: ${accountProvider}</div>
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </c:forEach>
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</html:form>
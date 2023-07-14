<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<div class="fullWidth">
    <div class="form-row">
        <div class="paymentLabel">
            <span class="paymentTextLabel">Получатель:</span>
        </div>
        <div class="paymentValue word-wrap" id="nameProvider">
        </div>
        <div class="clear"></div>
    </div>
</div>
<c:set var="prividerCount" value="${phiz:size(form.providers)}"/>
<c:set var="serviceFieldName"><bean:message key="label.choose.service" bundle="paymentServicesBundle"/></c:set>
<c:if test="${prividerCount == 1}">
    <c:set var="serviceFieldName"><bean:message key="label.service.one" bundle="paymentServicesBundle"/></c:set>
</c:if>
<div class="fullWidth">
    <div class="form-row">
        <div class="paymentLabel">
            <span class="paymentTextLabel">${serviceFieldName}</span><span id="asterisk_payment" class="asterisk">*</span>:
        </div>
        <div class="paymentValue">
            <c:choose>
                <c:when test="${empty form.providers}">
                    <span style="color: red"><c:out value="${noProviderMessage}"/></span>
                </c:when>
                <c:when test="${prividerCount == 1}">
                    <c:set var="provider" value="${form.providers[0]}"/>
                    <c:out value="${provider.nameService}"/>
                    <html:hidden name="form" property="recipient"  value="${provider.id}"/>
                </c:when>
                <c:otherwise>
                    <html:select name="form" property="recipient" onchange="pf.showProvider(this.value); $('#phoneFieldParam').val('');" styleId="recipientId">
                        <c:forEach items="${form.providers}" var="provider" >
                            <html:option value="${provider.id}"><c:out value="${provider.nameService}"/></html:option>
                        </c:forEach>
                    </html:select>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="clear"></div>
    </div>
</div>

<tiles:insert page="../fromResourceField.jsp" flush="false"/>

<div id="paymentForm" style="display: none;" class="fullWidth"></div>

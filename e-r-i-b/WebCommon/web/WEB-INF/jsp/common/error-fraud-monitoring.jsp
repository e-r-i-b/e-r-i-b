<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    Страница отображаемая клиенту при блокировке профиля, в следствии активности ФМ
--%>

<tiles:importAttribute/>

<c:set var="definition">
    <c:choose>
        <c:when test="${messageKey == 'com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException'}">fraudMonitoring</c:when>
        <c:otherwise>main</c:otherwise>
    </c:choose>
</c:set>
<tiles:insert definition="${definition}">
    <tiles:put name="data" type="string">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange"/>
            <tiles:put name="data">
                <div class="message">
                    <bean:message bundle="commonBundle" key="${messageKey}"/>
                </div>
                <div class="clear"></div>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

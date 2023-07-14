<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute ignore="true"/>

<%--
Шаг подтверждения
confirmRequest - реквест подтверждения
autoStart - автовыбор предпочтительного способа подтверждения (есть только на подтверждении логина)

--%>
<confirmStage>
    <tiles:insert definition="confirmType" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
    </tiles:insert>

    <c:if test="${confirmRequest.preConfirm || autoStart}">
        <tiles:insert definition="confirmInfo" flush="false">
            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        </tiles:insert>
    </c:if>
</confirmStage>
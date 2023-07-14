<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<tiles:insert definition="commandButton" flush="false">
    <tiles:put name="commandKey" value="${confirmCommandKey}"/>
    <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
    <tiles:put name="bundle" value="securityBundle"/>
    <c:if test="${validationFunction!=''}">
        <tiles:put name="validationFunction" value="${validationFunction}"/>
    </c:if>
    <tiles:put name="isDefault" value="true"/>
</tiles:insert>

<script type="text/javascript">
    <c:if test="${confirmRequest.error}">
        addError('${confirmRequest.errorMessage}');
    </c:if>
    <c:forEach items="${confirmRequest.messages}" var="infoMessage">
        addMessage('${infoMessage}');
    </c:forEach>    
</script>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:choose>
    <c:when test="${phiz:impliesServiceRigid('NewClientProfile')}">
        <%@ include file="/WEB-INF/jsp/common/help/sbrf/client/help_configureNew.jsp" %>
    </c:when>
    <c:when test="${phiz:impliesServiceRigid('ClientProfile')}">
        <%@ include file="/WEB-INF/jsp/common/help/sbrf/client/help_configureOLD.jsp" %>
    </c:when>
</c:choose>



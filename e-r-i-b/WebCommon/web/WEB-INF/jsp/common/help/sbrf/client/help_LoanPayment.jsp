<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:choose>
    <c:when test="${phiz:impliesServiceRigid('NewRurPayment')}">
        <%@ include file="/WEB-INF/jsp/common/help/sbrf/client/help_LoanPaymentNew.jsp" %>
    </c:when>
    <c:when test="${phiz:impliesServiceRigid('RurPayment')}">
        <%@ include file="/WEB-INF/jsp/common/help/sbrf/client/help_LoanPaymentOld.jsp" %>
    </c:when>
</c:choose>



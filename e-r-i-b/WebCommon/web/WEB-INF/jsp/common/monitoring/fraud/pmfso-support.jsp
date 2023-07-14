<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:if test="${phiz:isFSORSAActive()}">
    <%-- запись значения deviceTokenFSO во Flash-cookie --%>
    <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-set.jsp"%>
    <%-- передача значения deviceTokenFSO --%>
    <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso.jsp"%>
</c:if>
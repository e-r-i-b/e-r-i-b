<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp" %>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="defaultStatus">${STATUS_SIM_ERROR}</tiles:put>
</tiles:insert>

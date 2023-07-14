<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="status" value="${STATUS_RESET_MGUID}"/>
    <tiles:put name="errorDescription" value="Приложение не зарегистрировано."/>
</tiles:insert>

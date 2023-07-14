<%@page contentType="text/xml;charset=windows-1251" language="java"%>

<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<%@include file="/WEB-INF/jsp/common/layout/status.jsp"%>
<tiles:insert definition="response" flush="false">
    <tiles:put name="status"           value="${STATUS_RESET_MGUID}"/>
    <tiles:put name="errorDescription" value="Приложение не зарегистрировано."/>
</tiles:insert>

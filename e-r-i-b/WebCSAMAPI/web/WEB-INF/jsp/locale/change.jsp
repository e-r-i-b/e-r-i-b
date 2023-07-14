<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common/layout/status.jsp"%>

<tiles:insert definition="response" flush="false">
    <tiles:put name="defaultStatus">${STATUS_OK}</tiles:put>
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>
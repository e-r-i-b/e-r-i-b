<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common/layout/status.jsp"%>

<tiles:insert definition="response" flush="false">
    <tiles:put name="defaultStatus">${STATUS_LOGIC_ERROR}</tiles:put>
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>
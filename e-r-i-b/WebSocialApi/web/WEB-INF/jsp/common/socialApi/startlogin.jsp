<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>

<tiles:insert definition="iphone" flush="false">
    <tiles:put name="defaultStatus">${STATUS_END_SESSION}</tiles:put>    
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>
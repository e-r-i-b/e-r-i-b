<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 01.11.2010
  Time: 13:49:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>

<tiles:insert definition="iphone" flush="false">
    <tiles:put name="defaultStatus">${STATUS_END_SESSION}</tiles:put>    
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp" %>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="status">${STATUS_CARD_NUM_ERROR}</tiles:put>
    <tiles:put name="data">
        <loginCompleted>false</loginCompleted>
    </tiles:put>
</tiles:insert>

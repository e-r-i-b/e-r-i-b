<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%@ include file="/WEB-INF/jsp/common/layout/status.jsp"%>
<tiles:insert definition="response" flush="false">
    <tiles:put name="status">${STATUS_CARD_NUM_ERROR}</tiles:put>
    <tiles:put name="data">
        <loginCompleted>false</loginCompleted>
    </tiles:put>
</tiles:insert>

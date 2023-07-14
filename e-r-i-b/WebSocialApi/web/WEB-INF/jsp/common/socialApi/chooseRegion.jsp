<%--
  User: Barinov
  Date: 03.08.2012
  Time: 17:15:14
--%>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="data">
        <loginCompleted>false</loginCompleted>
        <chooseRegionStage>true</chooseRegionStage>
    </tiles:put>
</tiles:insert>
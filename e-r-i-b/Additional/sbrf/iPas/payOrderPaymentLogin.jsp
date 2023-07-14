<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="login">
<tiles:put name="pageTitle" type="string" value="Регистрация."/>
<tiles:put name="data" type="string">
    <tiles:insert page="/WEB-INF/jsp-sbrf/LoginPasswordiPasData.jsp" flush="false">
        <tiles:put name="action" value="/payOrderPaymentLogin"/>
    </tiles:insert>
</tiles:put>
</tiles:insert>

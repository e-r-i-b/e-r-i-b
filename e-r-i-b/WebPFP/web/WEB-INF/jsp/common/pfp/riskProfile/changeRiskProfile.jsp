<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

&nbsp;
<tiles:insert definition="webModulePage">
    <tiles:put name="data">
        {"noErrors": true}
    </tiles:put>
</tiles:insert>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/depo/info/form">
    <tiles:insert definition="depoForm" flush="false">
        <tiles:put name="activeTabNumber" value="0"/>
        <tiles:put name="content">
            <div class="personal-info-items">
                <%@ include file="personalData.jsp"%>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
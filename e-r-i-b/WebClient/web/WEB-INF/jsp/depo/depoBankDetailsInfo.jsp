<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/depo/info/bank">
    <tiles:insert definition="depoForm" flush="false">
        <tiles:put name="activeTabNumber" value="2"/>
        <tiles:put name="content">
            <div class="bank-items">
                 <%@ include file="bankDetailsInfo.jsp"%>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
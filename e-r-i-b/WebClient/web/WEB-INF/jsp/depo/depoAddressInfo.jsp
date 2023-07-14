<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>

<html:form action="/private/depo/info/address">
    <tiles:insert definition="depoForm" flush="false">
        <tiles:put name="activeTabNumber" value="1"/>
        <tiles:put name="content">
            <div class="address-items">
               <%@ include file="addressInfo.jsp"%>
            </div>   
        </tiles:put>
    </tiles:insert>
</html:form>
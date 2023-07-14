<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<tiles:insert definition="mainWorkspace" flush="false">
    <tiles:put name="data">
        <c:set var="needUDBOtext"><bean:message bundle="commonBundle" key="text.needUDBO"/></c:set>
        <p>${fn:replace(needUDBOtext, '%product%', product)}</p>
    </tiles:put>
</tiles:insert>
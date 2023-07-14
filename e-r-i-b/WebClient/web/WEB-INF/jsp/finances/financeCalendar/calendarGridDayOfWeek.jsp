<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<td class="dayHeader">
    <div class="dayHeaderName">
        ${dayOfWeenName}
    </div>
    <div class="dayHeaderBottom ${additionalClazz}"></div>
</td>
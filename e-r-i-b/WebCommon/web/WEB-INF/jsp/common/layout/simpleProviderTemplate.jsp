<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>

<table>
    <tr>
        <td valign="top" class="substrateProviderIcon">
            <img src="${image}" border="0"/>
        </td>
        <td valign="top">
            ${data}
        </td>
    </tr>
</table>
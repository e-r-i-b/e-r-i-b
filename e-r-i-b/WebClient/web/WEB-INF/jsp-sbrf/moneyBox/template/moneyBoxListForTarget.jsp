<%--
  Created by IntelliJ IDEA.
  User: saharnova
  Date: 26.09.14
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%-- Шаблон для копилки на странице детальной информации по карте.
id              - идентификатор цели. Необязательный параметр.
type            - тип цели (com.rssl.phizic.business.finances.targets.TargetType) или ACCOUNT.
title           - Заголовок для цели/вклада.
data            - список копилок, подключенных к данной цели/вкладу (шаблон moneyBoxCardTemplate).
--%>

<tiles:importAttribute/>
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
<c:choose>
    <c:when test="${type == 'ACCOUNT'}">
        <c:set var="img"><img src="${commonImagePath}/deposits_type/account.jpg" border="0"/></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="img"><img src="${commonImagePath}/account_targets/${type}.png" border="0"/></c:set>
    </c:otherwise>
</c:choose>

<div id="moneyBoxTarget${id}" class="moneyBoxList">
    <table>
        <tr>
            <td class="alignTop">
                <div class="moneyBoxImgTarget">
                    ${img}
                </div>
            </td>
            <td>
                <div class="moneyBoxData">
                    <span class="moneyBoxTemplateCardTitleTarget">${title}</span>
                    ${data}
                </div>
            </td>
        </tr>
    </table>
</div>
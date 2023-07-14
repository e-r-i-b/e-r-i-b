<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<tiles:importAttribute/>

<c:if test="${security}">
    <div class="clear"></div>
    <div><h2>Настройка видимости продуктов</h2></div>
    <div class="clear"></div>
</c:if>
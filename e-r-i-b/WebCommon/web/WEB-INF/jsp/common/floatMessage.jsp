<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
 id - id блока
 text - текст подсказки
 --%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<div class="float" onmouseover="hideOrShow('${id}');" onmouseout="hideOrShow('${id}');">
    <div><a class="imgHintBlock save-template-hint"></a></div>
    <div id="${id}"  class="floatMessage" style="display: none;">
        <div class="floatMessageHeader"></div>
        <div class="floatMessageBlock">
            <span>${text}</span>
        </div>
    </div>
</div>
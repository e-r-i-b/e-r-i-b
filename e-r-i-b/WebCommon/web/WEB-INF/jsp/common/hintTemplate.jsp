<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
 color - стиль подсказки
 data - текст подсказки
 --%>
<tiles:importAttribute/>

<div class="showHintBlock">
    <a href="#" class="imgHintBlock" onclick="return false;"></a>
    <div class="hintsBlock" style="display: none;">
        <div class="${color}Triangle"></div>
        <tiles:insert definition="roundBorder" flush="false">
            <tiles:put name="color" value="${color}"/>
            <tiles:put name="data">
                ${data}
            </tiles:put>
        </tiles:insert>
    </div>
</div>
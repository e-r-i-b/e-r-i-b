<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--
Компонент для отображения "ползунка" для выбора интервала
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
minValue - минимальное значение
maxValue - максимальное значение
step - шаг с которым увеличиваются значения
startFieldName - имя поля для начала интервала
endFieldName - имя поля для конца интервала
callback - функция, которая вызывается после перемещения движка
--%>

<script type="text/javascript">
    var rangeScrollManager${id} = new RangeScroll();
    $(document).ready(function(){
        rangeScrollManager${id}.initScroll('${id}', ${minValue}, ${maxValue}, ${step}, '${startFieldName}', '${endFieldName}' <c:if test="${not empty callback}">, function(){${callback}}</c:if>);
    });

</script>

<div id="rangeScrollArea${id}" class="rangeScrollArea">
    <input type="hidden" name="${startFieldName}" value="">
    <input type="hidden" name="${endFieldName}" value="">

    <div class="scaleDescriptions"></div>
    <div class="scaleDivisions">
        <div class="scaleDivision first"></div>
    </div>
    <div class="clear"></div>

    <div id="rangeScroll${id}" class="rangeScrollBlock dragdealer">
        <div class="rangeScrollBackground">
            <div class="rangeScrollBgLeft"></div>
            <div class="rangeScrollBgCenter"></div>
            <div class="rangeScrollBgRight"></div>
        </div>
        <div class="rangeScrollColorBlock">
            <div class="rangeScroll green">
                <div class="scrollLeft"></div>
                <div class="scrollCenter"></div>
                <div class="scrollRight"></div>
            </div>
        </div>
        <div class="clear"></div>

        <div id="horizScroll1Inner" class="scrollInner handle"></div>
        <div id="horizScroll2Inner" class="scrollInner handle"></div>
    </div>
    <div class="clear"></div>
</div>


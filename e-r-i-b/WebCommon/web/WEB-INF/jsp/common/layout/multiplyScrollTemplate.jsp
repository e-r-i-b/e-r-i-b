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
Компонент для отображения "ползунка" с несколькими движками
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
dataName - поле формы, значением которого является Map со значениями
minValue - минимальное значение
maxValue - максимальное значение
unit - единицы измерения
callback - функция, которая вызывается после перемещения движка
--%>

<script type="text/javascript">
    $(document).ready(function(){
        <c:set var="value" value="0"/>
        <logic:iterate id="item" name="form" property="${dataName}" indexId="i">
            <c:set var="value" value="${value + item.value}"/>
            multiplyScrollUtils.addMultiplyScroll(${i}, '${id}', '${item.key}',multiplyScrollUtils.${item.key}_COLOR, '${item.key.description}', ${value}, ${minValue}, ${maxValue}, '${unit}'
                     <c:if test="${not empty callback}">, function(){${callback}}</c:if>);
        </logic:iterate>
        $('#multiplyScroll${id} .scrollInner:last').remove(); // последний движок нам не нужен
        
        multiplyScrollUtils.recalcMultiplyScrollValues('${id}', ${maxValue}, '${unit}'); // заполнение легенды

        var fullWidth = $('#multiplyScroll${id}').width() - 18; // 18 - отступы
        drawDivisions('multiplyScrollArea${id}', fullWidth, 10);
        multiplyScrollUtils.drawValuesOnDivisions('multiplyScrollArea${id}', fullWidth, ${minValue}, ${maxValue}, 11);
        $('#multiplyScroll${id} .scaleDivision:last').hide(); // последнее деление не отображаем
    });

</script>

<div id="multiplyScrollArea${id}">
    <div class="multiplyScrollDescRight">${minValue}${unit}</div>
    <div id="multiplyScroll${id}" class="multiplyScrollBlock dragdealer">
        <div class="multiplyScrollBackground">
            <div class="multiplyScrollBgLeft"></div>
            <div class="multiplyScrollBgCenter"></div>
            <div class="multiplyScrollBgRight"></div>
        </div>
        <div class="multiplyScrollsBlock"></div>
        <div class="clear"></div>
        
        <div class="scaleDivisions">
            <div class="scaleDivision first" style="display:none;"></div>
        </div>
        <div class="clear"></div>

        <div class="scaleDescriptions"></div>
    </div>
    <div class="multiplyScrollDescLeft">${maxValue}${unit}</div>
    <div class="clear"></div>
    
    <div class="multiplyScrollLegend"></div>
    <div class="clear"></div>
</div>


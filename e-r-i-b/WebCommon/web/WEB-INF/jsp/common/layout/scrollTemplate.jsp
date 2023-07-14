<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<%--
 омпонент дл€ отображени€ "ползунка"
id - идентификатор блока, об€зателен дл€ заполнени€, если таких компонентов на странице несколько
description - заголовок блока
minValue - минимальное значение
maxValue - максимальное значение
unit - единица измерени€
currValue - текущее значение
fieldName - название пол€, в которое необходимо положить значение
size - размер инпута
--%>

<c:if test="${currValue == ''}"><c:set var="currValue" value="${minValue}"/> </c:if>

<script type="text/javascript">
    var horizDrag${id};
    doOnLoad(function(){
        horizDrag${id} = new Dragdealer('horizScroll${id}',
        {
            snap: true,
            xStep: ${maxValue - minValue + 1},
            fieldName: '${fieldName}',
            minValue:   ${minValue},
            maxValue:   ${maxValue},
            step:       1,
            round:      0
        });

        horizDrag${id}.animationCallback = function(x, y){
            setHorizPosition('${id}', horizDrag${id});
        };

        horizDrag${id}.initScroll(${currValue});
        setScrollVal('${id}', horizDrag${id});
    });
</script>

<div class="scrollInput">
    <input type="text" name="${fieldName}" value="${currValue}" onchange="setScrollVal('${id}', horizDrag${id});" <c:if test="${not empty size}">size="${size}"</c:if> />
</div>
<div class="scroll" id="horizScroll${id}">
    <div class="scrollRight"></div>
    <div class="scrollLeft"></div>
    <div class="scrollCenter">
        <div id="Main${id}" class="scrollMain">
            <div class="innerScroll" id="InnerScroll${id}"></div>
        </div>
        <div class="scrollDescRight">${maxValue} ${unit}</div>
        <div class="scrollDescLeft">${minValue} ${unit}</div>
    </div>
    <div id="horizScroll${id}Inner" class="scrollInner handle"></div>
</div>

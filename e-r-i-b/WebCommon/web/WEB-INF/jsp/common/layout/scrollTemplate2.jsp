<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
Компонент для отображения "ползунка"
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
minValue - минимальное значение
maxValue - максимальное значение
unit - единица измерения
minUnit - форма названия единицы измерения для минимального значения
maxUnit - форма названия единицы измерения для максимального значения
currValue - текущее значение
fieldName - название поля, в которое необходимо положить значение
inputData - данные, которые необходимо отображать после <input>
step - шаг сетки
round - знак, до которого округляем значения
callback - функция выполняемая при смене значения ползунка
valuesPosition - расположение подписей значений, по умолчанию - на одной линии {inline|bottom}
maxInputLength - ограничение длинны поля для ввода.
--%>

<c:if test="${empty minUnit && not empty unit}">
    <c:set var="minUnit" value="${unit}"/>
</c:if>
<c:if test="${empty maxUnit && not empty unit}">
    <c:set var="maxUnit" value="${unit}"/>
</c:if>

<c:if test="${empty minValue}">
    <c:set var="minValue" value="${currValue}"/>
</c:if>
<c:if test="${empty maxValue}">
    <c:set var="maxValue" value="${currValue}"/>
</c:if>
<c:set var="fieldValue"><bean:write name="org.apache.struts.taglib.html.BEAN" property="${fieldName}"/></c:set>
<c:if test="${not empty fieldValue}"><%-- Если значение в поле не пусто, то выставляем его--%>
    <c:set var="currValue" value="${fieldValue}"/>
</c:if>
<c:if test="${empty currValue}">
    <c:set var="currValue" value="${minValue}"/>
</c:if>

<script type="text/javascript">
    var horizDrag${id};
    doOnLoad(function(){

        horizDrag${id} = new Dragdealer('horizScroll${id}',
        {
            snap: true,
            xStep: ${(maxValue - minValue)/step + 1},
            fieldName: '${fieldName}',
            minValue:   ${minValue},
            maxValue:   ${maxValue},
            step:       ${step},
            round:      ${round}
        });

        horizDrag${id}.animationCallback = function(x, y){
            setHorizPosition('${id}', horizDrag${id});
            <c:if test="${not empty callback}">
                ${callback}
            </c:if>
        };

        horizDrag${id}.initScroll(${currValue});
        setScrollVal('${id}', horizDrag${id});
    });

    function updateScroll(id, dragDealer,currValue,minValue,maxValue,modifyUnit)
    {
        $('input[name='+dragDealer.fieldName +']').val(currValue);
        dragDealer.xStep = (maxValue - minValue)/dragDealer.step + 1;
        dragDealer.currValue = currValue;
        dragDealer.minValue = minValue;
        dragDealer.maxValue = maxValue;
        dragDealer.initScroll(currValue);
        setScrollVal(id, dragDealer);
        if (isEmpty(modifyUnit))
        {
            $('#'+id+'scrollDescLeft').html(minValue + ' ' + $('#minUnit'+id).val());
            $('#'+id+'scrollDescRight').html(maxValue + ' ' + $('#maxUnit'+id).val());
        }
        else
        {
            $('#'+id+'scrollDescLeft').html(minValue + ' '+ modifyUnit(minValue));
            $('#'+id+'scrollDescRight').html(maxValue + ' '+ modifyUnit(maxValue));
        }
    }

    function onScrollEnterPress${id}(e)
    {
        var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
        if(kk == 13)
        {
            setScrollVal('${id}', horizDrag${id});
            <c:if test="${not empty callback}">${callback}</c:if>
            cancelBubbling(e);
        }
    }
</script>


<div class="scrollInput <c:if test="${dataPosition == 'right'}">scrollInputPosRight</c:if>">
    <c:if test="${dataPosition == 'right'}">
    <div class="float">
    </c:if>
    <input type="text" name="${fieldName}" value="${currValue}" onchange="setScrollVal('${id}', horizDrag${id}); <c:if test="${not empty callback}">${callback}</c:if>"
           onkeydown="onScrollEnterPress${id}(event);" size="4" <c:if test="${not empty maxInputLength}"> maxlength="${maxInputLength}"</c:if/>>
    <c:if test="${dataPosition == 'right'}">
    </div>
    </c:if>
    ${inputData}
</div>

<div class="scroller${valuesPosition}">
    <c:if test="${not empty minUnit}">
        <input id="minUnit${id}" type="hidden" name="minUnit${id}" value="${minUnit}"/>
    </c:if>
    <c:if test="${not empty maxUnit}">
        <input id="maxUnit${id}" type="hidden" name="maxUnit${id}" value="${maxUnit}"/>
    </c:if>
    <c:choose>
        <c:when test="${valuesPosition == 'bottom'}">
            <div class="dragdealer">

                <div id="horizScroll${id}" class="greenScroll">

                    <div class="scrollRight"></div>
                    <div class="scrollLeft"></div>
                    <div class="scrollCenter">
                        <div id="Main${id}" class="scrollMain">
                            <div class="innerScroll" id="InnerScroll${id}"></div>
                        </div>
                    </div>
                    <div class="scrollShadow"></div>

                    <div class="clear"></div>

                    <div id="horizScroll${id}Inner" class="scrollInner handle"></div>
                </div>

            </div>
            <div class="values">
                <div id="${id}scrollDescLeft" class="scrollDescLeft">${minValue}${minUnit}</div>
                <div id="${id}scrollDescRight" class="scrollDescRight">${maxValue}${maxUnit}</div>
            </div>
        </c:when>
        <c:otherwise>

            <div id="${id}scrollDescLeft" class="scrollDescLeft">${minValue}${minUnit}</div>

            <div class="dragdealer">

                <div id="horizScroll${id}" class="greenScroll">

                    <div class="scrollRight"></div>
                    <div class="scrollLeft"></div>
                    <div class="scrollCenter">
                        <div id="Main${id}" class="scrollMain">
                            <div class="innerScroll" id="InnerScroll${id}"></div>
                        </div>
                    </div>
                    <div class="scrollShadow"></div>

                    <div class="clear"></div>

                    <div id="horizScroll${id}Inner" class="scrollInner handle"></div>
                </div>

            </div>

            <div id="${id}scrollDescRight" class="scrollDescRight">${maxValue}${maxUnit}</div>
        </c:otherwise>
    </c:choose>

</div>


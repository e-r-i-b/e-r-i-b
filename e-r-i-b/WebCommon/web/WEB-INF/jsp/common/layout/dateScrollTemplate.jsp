<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
Компонент для отображения "ползунка"
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
minDate - минимальное значение
maxDate - максимальное значение
currDate - текущее значение
fieldName - название поля, в которое необходимо положить значение
callback - функция выполняемая при смене значения ползунка
--%>


<c:if test="${empty currYear && empty currMonth && empty currDay}">
    <c:set var="currYear" value="${minYear}"/>
    <c:set var="currMonth" value="${minMonth}"/>
    <c:set var="currDay" value="${minDay}"/>
</c:if>

<script type="text/javascript">
    var horizDrag${id};
    doOnLoad(function(){

        var minDate = new Date('${minYear}', '${minMonth-1}', '${minDay}');
        var maxDate = new Date('${maxYear}', '${maxMonth-1}', '${maxDay}');
        var currDate = new Date('${currYear}', '${currMonth-1}', '${currDay}');

        var maxVal = (maxDate.getFullYear() - minDate.getFullYear())*12 + (maxDate.getMonth() - minDate.getMonth());
        var currVal = (currDate.getFullYear() - minDate.getFullYear())*12 + (currDate.getMonth() - minDate.getMonth());

        horizDrag${id} = new Dragdealer('horizScroll${id}',
        {
            snap: true,
            xStep: maxVal+1,
            fieldName: '${fieldName}',
            minValue: 0,
            maxValue: maxVal,
            step: 1
        });

        horizDrag${id}.animationCallback = function(x, y){
            drawActivScale('${id}');
            setDateValue(horizDrag${id}, x, minDate);
            <c:if test="${not empty callback}">
                ${callback}
            </c:if>
        };

        horizDrag${id}.initScroll(currVal);
        drawActivScale('${id}');

        var fullWidth = $('#dateScroller${id} .dragdealer').width() - 10;
        drawDivisions('dateScroller${id}', fullWidth, maxVal);
        drawYearsOnDivisions('${id}', minDate, maxDate, maxVal);

        $('input[name=${fieldName}]').bind('change', function(){
            redrawActiveScale(horizDrag${id},'${id}', minDate, maxDate);
        });
    });
</script>


<div id="dateScroller${id}" class="dateScroller greyBgScroll">
    <div class="scrollBgRight"></div>
    <div class="scrollBgLeft"></div>
    <div class="scrollBgCenter">

        <div id="${id}scrollDescLeft" class="scrollDescLeft">${minMonth}/${minYear}</div>

        <div class="dragdealer">

            <div class="scrollScale">
                <div class="scrollLeft"></div>
                <div class="scrollCenter"></div>
                <div class="scrollRight"></div>

                <div id="Main${id}" class="scrollMain">
                    <div class="innerScrollRight"></div>
                    <div class="innerScrollLeft"></div>
                    <div class="innerScroll" id="InnerScroll${id}"></div>
                </div>
                <div class="clear"></div>

                <div class="scaleDivisions">
                    <div class="scaleDivision first"></div>
                </div>
                <div class="clear"></div>

                <div class="scaleDescriptions">
                </div>
            </div>

            <div id="horizScroll${id}" class="horizScroll">
                <div id="horizScroll${id}Inner" class="scrollInner handle"></div>
            </div>
        </div>

        <div id="${id}scrollDescRight" class="scrollDescRight">${maxMonth}/${maxYear}</div>
    </div>

    <div class="clear"></div>
</div>


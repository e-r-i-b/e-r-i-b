<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<%--
Компонент для отображения "градусника"
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
value - значение "градусника"
maxValue - максимальное значение, которое может принимать "градусник"
color - цвет шкалы (green, orange, red)
showDribbleHint - отображать ли "капельку" с подсказкой
clazz - класс (например, если надо переопределить ширину градусника)
--%>


<script type="text/javascript">
    if (window.isClientApp != undefined)
    {
        $(document).ready(function()
        {
            var thermometer = thermometerManager.getThermometer('${id}');
            if(!thermometer)
            {
                thermometer = new Thermometer('${id}', ${value}, ${maxValue}, ${showDribbleHint},'${color}');
                thermometer.calculateWidths();
                thermometer.scale();
                thermometer.redraw();
                <c:if test="${not empty enterScript}">
                    thermometer.addMouseenter(function(){${enterScript}});
                </c:if>
                <c:if test="${not empty leaveScript}">
                    thermometer.addMouseleave(function(){${leaveScript}});
                </c:if>
                thermometerManager.addThermometer(thermometer);
            }
        });
    };
</script>

<div class="${clazz}">
    <c:if test="${not empty leftData}">
        <div class="thermometerLeftData">
            ${leftData}
        </div>
    </c:if>

    <div id="thermometer${id}" class="thermometer ${color}Thermometer float <c:if test="${!showDribbleHint}">noDribbleHint</c:if>">
        <div class="thermometerBg">
            <div class="thermometerLeft"></div>
            <div class="thermometerCenter"></div>
            <div class="thermometerRight"></div>
            <div class="clear"></div>
        </div>

        <div class="thermometerScaleBg">
            <div class="thermometerLeft"></div>
            <div class="thermometerCenter"></div>
            <div class="thermometerRight"></div>
            <div class="clear"></div>
        </div>

        <div class="thermometerScale">
            <div class="thermometerLeft"></div>
            <div class="thermometerCenter"></div>
            <div class="thermometerRight"></div>
            <div class="clear"></div>
        </div>

        <c:if test="${showDribbleHint}">
            <div class="dribbleHint">
                <div class="dribbleHintPick"></div>

                <div class="dribbleCenter"><span>${phiz:getStringInNumberFormat(value)}</span> руб.</div>
            </div>
        </c:if>
    </div>

    <c:if test="${not empty rightData}">
        <div class="thermometerRightData">
            ${rightData}
        </div>
    </c:if>

    <div class="clear"></div>
</div>

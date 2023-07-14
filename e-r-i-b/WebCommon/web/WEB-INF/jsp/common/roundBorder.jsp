<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    компонент для закругления углов
    data - данные
    info - информационное сообщение пользователю
    title - заголовок блока
    contentTitle - заголовок блока в контенте (при необходимости)
    control - верхний правый блок управления с кнопками
    color - цвет(стиль) закругления
        Доступны следующие  стили:
            lightGreen - светло зеленый используется в фильтрре
            greenTop - рамка с зеленой полосой сверху и местом для заголовка
            greenBold - жирная зеленая рамка с тенью
            gray - серый
            lightGray - светло серая рамка без закругления. Используется для всплывающих плашек
            greenGradient - серая рамка, нижняя часть раскрашена в зеленый градиент (блок помощи в правом меню)
            green - тонкая зеленая рамка (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            shadow - закругление с тенью
            sTable - серое закругления для таблицы с операциями на плашке продуктов (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            red - карсная толстая рамка (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            orange - оранжевая толстая рамка. (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            whiteTop - рамка с белой полосой сверху и местом для заголовка
            orangeTop - рамка с оранжевой полосой сверху и местом для заголовка
            whiteAndGray - светло-серая рамка без границы
            creamyTop - кремовая рамка для личного меню
            hoar - серая рамка для блока операций

    Примечание: так как IE6 не поддерживает цепочки CSS стилей (class1.class2) то для того
                чтобы блоки могли вкладываться друг в друга приходиться городить конструкции типа ${color}RTL.
                Классы типа r-top необходимы для описания общих характеристик всех закругленных блоков.
                Сокращения типа RT r-top расшифровываются как (round top).
    outerData - данные за пределом формы
    rightDataArea - данные в правой колонке

--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>

<%-- оборачиваем title для фиксации и обрезания лишнего --%>
<c:if test="${not empty title}">
    <c:set var="title">
        <div class="${color}Title" <c:if test="${!empty controlLeft or !empty control}">style="float:left;"</c:if>>
            <span>${title}</span>
        </div>
    </c:set>
</c:if>


<c:if test="${(color == 'greenTop' || color == 'whiteTop' || color == 'orangeTop' || color == 'gray' || color == 'greenGradient' || color == 'creamyTop'  || color == '') && !empty control}">
    <c:set var="title">
        ${title}
        <div class="${color}CBT">${control}</div>
        <div class="clear"></div>
    </c:set>
</c:if>


<c:if test="${!empty controlLeft}">
    <c:set var="title">
        ${title}
        <div class="${color}CTL">${controlLeft}</div>
        <div class="clear"></div>
    </c:set>
</c:if>

<c:if test="${!empty contentTitle}">
    <c:set var="contentTitle">
        <span class="contentTitle size24">
            ${contentTitle}
        </span>
        <div class="clear"></div>
    </c:set>
</c:if>

<c:if test="${not empty rightDataArea}">
    <div class="rightDataArea">
        ${rightDataArea}
    </div>
</c:if>

<div class="workspace-box ${color} <c:if test="${not empty rightDataArea}">float</c:if>" <c:if test="${not empty style}">style="${style}"</c:if>>
    <c:set var="blinking" value=""/>
    <c:if test="${color == 'orangeTop'}">
        <c:set var="blinking" value="blinking"/>
        <div class="whiteTopRT blinking">
            <div class="whiteTopRTL r-top-left"><div class="whiteTopRTR r-top-right"><div class="whiteTopRTC r-top-center"></div></div></div>
        </div>
    </c:if>

    <div class="${color}RT r-top ${blinking}">
        <div class="${color}RTL r-top-left"><div class="${color}RTR r-top-right"><div class="${color}RTC r-top-center">
            ${title}
        <div class="clear"></div>
        </div></div></div>
    </div>
    <div class="${color}RCL r-center-left">
        <div class="${color}RCR r-center-right">
            <div class="${color}RC r-content">
                <div class="r-content-data">
                    ${contentTitle}
                    ${data}
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="${color}RBL r-bottom-left">
        <div class="${color}RBR r-bottom-right">
            <div class="${color}RBC r-bottom-center"></div>
        </div>
     </div>
</div>

<div class="clear"></div>
<c:if test="${not empty outerData}">
    <div class="outerData">
        ${outerData}
    </div>
</c:if>
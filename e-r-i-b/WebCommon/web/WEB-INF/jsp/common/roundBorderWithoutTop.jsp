<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    компонент для закругления углов
    data - данные
    top - данные вверху страницы
    color - цвет(стиль) закругления
        Доступны следующие  стили:
            lightGreen - светло зеленый используется в фильтрре
            greenTop - рамка с зеленой полосой сверху и местом для заголовка
            greenBold - жирная зеленая рамка с тенью
            gray - серый
            green - тонкая зеленая рамка (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            shadow - закругление с тенью
            sTable - серое закругления для таблицы с операциями на плашке продуктов (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            red - карсная толстая рамка (не используется, при желании можно удалить картинки и стили из roundBorder.css)
            orange - оранжевая толстая рамка. (не используется, при желании можно удалить картинки и стили из roundBorder.css)

    Примечание: так как IE6 не поддерживает цепочки CSS стилей (class1.class2) то для того
                чтобы блоки могли вкладываться друг в друга приходиться городить конструкции типа ${color}RTL.
                Классы типа r-top необходимы для описания общих характеристик всех закругленных блоков.
                Сокращения типа RT r-top расшифровываются как (round top).

--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>

<div class="workspace-box ${color} withoutTop">
    <div class="top-content">
        ${top}
    </div>
    <div class="clear"></div>
    <div class="${color}RC r-content">
        ${data}
        <div class="clear"></div>
    </div>
</div>
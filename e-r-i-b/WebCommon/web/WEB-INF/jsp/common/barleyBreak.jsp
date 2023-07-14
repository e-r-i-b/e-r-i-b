<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>


<tiles:importAttribute/>
<%--
   Компонент реализующий механизм пятнашек
   totalColums - колво столбцов (2 или 3) по умолчанию 3
   data - массив данных для пятнашек
   styleClass - имя дополнительного сласса стиля по умолчанию пусто
--%>

<c:forEach items="${data}" var="curData">
    <div class="col${totalColums} ${styleClass}">
        ${curData}
    </div>
</c:forEach>
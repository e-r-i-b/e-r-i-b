<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%--  нопка скачивани€ мобильной версии приложени€.
    url  - ссылка дл€ перехода по кнопке
    text - отображаемый текст кнопки.
    btnType - тип кнопки, возможные значение simple, download
    onClickAction - действие по нажатию кнопки
--%>
<tiles:importAttribute/>
<a href="${URL}" class="${btnType}Button"><c:out value="${text}"/></a>
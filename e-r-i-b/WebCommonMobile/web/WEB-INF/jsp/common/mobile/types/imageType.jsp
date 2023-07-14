<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute ignore="true"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<%--
    Компонента для типа Image. Ожидается либо id либо imageData, либо явно заданный url.
    если пришел сам imageData то image не тянем
    иначе по id тянем image и дальше смотрим:
        если в самом Image задан url (extendImage), то отправляем его в тэге staticImage;
        иначе (extendImage не задан), то отправляем id в тэге dbImage.
    Иначе (пришел url) - отправляем url в тэге staticImage.

    Аргументы:
    name - название тэга
    id - id файла в БД. Ожидается либо id, либо imageData, либо явно заданный url.
    updateTime - время обновления файла в БД. Если не пришел, то ищем сами по id-шнику.
    url - url изображения. Ожидается либо id, либо imageData, либо явно заданный url.
    imageData - изображение. Ожидается либо id, либо imageData, либо явно заданный url.
--%>

<c:if test="${(not empty id or not empty url or not empty imageData) and not empty name}">
    <${name}>
        <c:choose>
            <%--приоритет у imageId--%>
            <c:when test="${not empty id or not empty imageData}">
                <c:if test="${empty imageData}">
                    <c:set var="imageData" value="${phiz:getImageById(id)}"/>
                </c:if>
                <c:set var="extendImage" value="${phiz:getFullPathToExtendImage(imageData)}"/>
                <c:choose>
                    <%--если задан id, то приоритет у ссылки на внешний источник--%>
                    <c:when test="${not empty extendImage}">
                        <staticImage>
                            <url>${extendImage}</url>
                        </staticImage>
                    </c:when>
                    <%--если ссыла на внешний источник отсутствует, то отправляем id--%>
                    <c:otherwise>
                        <dbImage>
                            <id>${empty id ? imageData.id : id}</id>
                            <%--workaround: перекладываем updateTime в новую переменную--%>
                            <c:set var="updateTimeForward" value="${updateTime}"/>
                            <c:if test="${empty updateTimeForward}">
                                <c:set var="updateTimeForward" value="${imageData.updateTime}"/>
                            </c:if>
                            <tiles:insert definition="mobileDateTimeType" flush="false">
                                <tiles:put name="name" value="updated"/>
                                <tiles:put name="calendar" beanName="updateTimeForward"/>
                            </tiles:insert>
                        </dbImage>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <%--если imageId не задан, используется url--%>
            <c:when test="${not empty url}">
                <staticImage>
                    <url>${imagePath}${url}</url>
                </staticImage>
            </c:when>
        </c:choose>
    </${name}>
</c:if>
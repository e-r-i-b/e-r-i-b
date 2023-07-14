<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
Отображение одной услуги в иерархии услуг в результатах поиска ПУ
    name - имя тега
    item - массив resultset-а
    index - индекс элемента массива с идентификатором текущей услуги
Используется рекурсивный вызов. Максимальная глубина вложенности в иерархии = 3.
--%>
<tiles:importAttribute/>
<c:if test="${not empty item[index]}">
    <${name}>
        <id>${item[index]}</id>
        <title><c:out value="${item[index + 1]}"/></title>
        <description><c:out value="${item[index + 1]}"/></description>
        <tiles:insert definition="imageType" flush="false">
            <tiles:put name="name" value="imgURL"/>
            <tiles:put name="id" value="${item[index + 3]}"/>
            <c:set var="categoryImgUpdate" value="${item[index + 4]}"/>
            <c:if test="${not empty categoryImgUpdate}">
                <tiles:put name="updateTime" beanName="categoryImgUpdate"/>
            </c:if>
            <tiles:put name="url" value="${item[index + 5]}"/>
        </tiles:insert>
        <guid><c:out value="${item[index+6]}"/></guid>
        <c:if test="${not empty item[index + 7]}">
            <tiles:insert page="parent.jsp" flush="false">
                <tiles:put name="name" value="parent"/>
                <tiles:put name="item" beanName="item"/>
                <tiles:put name="index" value="${index + 7}"/>
            </tiles:insert>
        </c:if>
    </${name}>
</c:if>


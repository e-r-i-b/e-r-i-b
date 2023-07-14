<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<tiles:importAttribute/>

<%--
    className - название класса элемента, который нужно скроллировать
    data - скроллируемые данные
--%>

<c:if test="${!empty data}">
    <div class="${className}">
        <div class="scrollbar">
            <div class="arrow-up"></div>
            <div class="track">
                <div class="thumb">
                    <div class="end"></div>
                </div>
            </div>
            <div class="arrow-down"></div>
        </div>
        <div class="viewport">
            <div class="overview">
                ${data}
            </div>
        </div>
    </div>
</c:if>
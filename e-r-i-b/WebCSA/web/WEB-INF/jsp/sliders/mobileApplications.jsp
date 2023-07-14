<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="tabData">
    <tiles:put name="id" value="mobile_applications"/>
    <tiles:put name="name" value="Мобильные приложения"/>
    <tiles:put name="content">
        <p class="size14">Мобильный доступ к Вашим счетам</p>
        <%-- Заполняется данными, полученными аяксом из базы, csa.js метод init(path, skinPath) --%>
        <ul id="mobile_applications_sliders" class="slider"></ul>
    </tiles:put>
</tiles:insert>
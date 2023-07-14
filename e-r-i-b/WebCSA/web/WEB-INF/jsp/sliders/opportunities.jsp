<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="tabData">
    <tiles:put name="id" value="opportunities"/>
    <tiles:put name="name" value="Возможности"/>
    <tiles:put name="content">
        <%-- Заполняется данными, полученными аяксом из базы, csa.js метод init(path, skinPath) --%>
        <ul id="opportunities_sliders" class="slider"></ul>
    </tiles:put>
</tiles:insert>
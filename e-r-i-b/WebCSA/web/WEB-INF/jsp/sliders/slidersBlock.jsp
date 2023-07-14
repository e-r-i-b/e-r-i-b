<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<ul id="${id}" class="slider">
    <c:forEach var="slide" items="${sliders}">
        <li>
            ${slide}
            <div class="clear"></div>
        </li>
    </c:forEach>
</ul>

<script type="text/javascript">
    $("#${id}").ready(function(){
        initSliders('#${id}');
    });
</script>
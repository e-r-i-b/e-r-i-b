<%-- Маркировка DMP-ЕРИБ Pixel для открытой части--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${csa:isPixelMetricActive()}">
    <script type="text/javascript">
        var imgMetric = document.createElement('img');
        imgMetric.width = 1;
        imgMetric.height = 1;
        imgMetric.style.display = 'none';
        imgMetric.src = 'https://counter.sberbank.ru/img/p.png&r=' + Math.random();
        document.body.insertBefore(imgMetric, document.body.firstChild);
    </script>
</c:if>

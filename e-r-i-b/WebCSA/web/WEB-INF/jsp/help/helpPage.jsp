<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

    <c:set var="form" value="${HelpForm}"/>

    <%--нужды хелпа--%>
    <c:set var="globalUrl" value="${skinUrl}/skins" scope="request"/>
    <c:set var="skinUrl" value="${globalUrl}/sbrf" scope="request"/>


    <%-- вставляем содержимое хелпа --%>
    <c:import charEncoding="windows-1251" url="/WEB-INF/jsp/help/source/${form.path}"/>

    <%-- если нужно, задаем позицию на странице --%>
    <c:if test="${not empty form.sharp}">
        <script type="text/javascript">
            function loadBody()
            {
                document.location.href = document.location.href + '#${form.sharp}';
            }
            doOnLoad(loadBody);
        </script>
    </c:if>
</html>
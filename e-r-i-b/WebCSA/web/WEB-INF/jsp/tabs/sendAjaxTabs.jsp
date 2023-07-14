<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<c:set var="form" value="${AsyncListTabsForm}"/>

&nbsp;
<script type="text/javascript">
    if(window.isClientApp != undefined)
    {
        var listItemHTML = '';
        <c:forEach var="tab" items="${form.tabs}">
            listItemHTML = "<li><p><span class=\"word-wrap\">" + "${csa:escapeForJS(tab.tabText, true)}" + "</span></p><div class=\"clear\"></div></li>";
            $("#" + "${tab.tabName}".toLowerCase() + "_sliders").append(listItemHTML);
        </c:forEach>

        $(document).ready(initFirstTab);
    }
</script>
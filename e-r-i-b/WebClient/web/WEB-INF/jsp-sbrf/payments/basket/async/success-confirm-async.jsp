<%--
  User: osminin
  Date: 01.06.14
  Time: 10:48
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

&nbsp;
<script type="text/javascript">
    if (window.win != undefined)
        win.close(confirmOperation.windowId);

    location.href = "${phiz:calculateActionURL(pageContext, "/private/userprofile/basket.do")}";
</script>
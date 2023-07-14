<%--
  Created by IntelliJ IDEA.
  User: bogdanov
  Date: 03.07.2013
  Time: 10:47:16
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

&nbsp;
<script type="text/javascript">
    if (window.win != undefined)
        win.close(confirmOperation.windowId);

    location.href = "${phiz:calculateActionURL(pageContext, "/private/payments/internetShops/orderList.do")}";
</script>
<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 24.09.2007
  Time: 16:59:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:import charEncoding="windows-1251" url="/WEB-INF/jsp/common/help/sbrf/client/${$$helpLink}"/>

<script type="text/javascript">
	function loadBody()
	{
		if( '${$$sharp}' != '' )
		{
		document.location.href = document.location.href + '${$$sharp}';
		}
	}
    doOnLoad(loadBody);
</script>
<%@ include file="/WEB-INF/jsp/common/analytics.jsp"  %>
</html>
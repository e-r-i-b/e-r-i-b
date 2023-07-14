<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 24.09.2007
  Time: 16:59:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<body onload="javascript:loadBody()">

<c:import charEncoding="windows-1251" url="/WEB-INF/jsp/common/help/sbercred/admin/${$$helpLink}"/>

<script type="text/javascript">
	function loadBody()
	{
		if( '${$$sharp}' != '' )
		{
		document.location.href = document.location.href + '${$$sharp}';
		}
	}
</script>
</body>
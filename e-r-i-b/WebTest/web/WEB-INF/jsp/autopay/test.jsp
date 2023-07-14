<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Test contents</title></head>
<body>
<h3>Test contents</h3>
<ul>
    <li><a href='${phiz:calculateActionURL(pageContext,'/docStateUpdateTest')}'> docStateUpdateTest </a></li>
    <li><a href='${phiz:calculateActionURL(pageContext,'/secDicInfoTest')}'> secDicInfoTest </a></li>
</ul>
</body>
</html>

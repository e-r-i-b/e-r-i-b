<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 02.04.2009
  Time: 11:38:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<tiles:insert definition="test">
<tiles:put type="page" name="data" value ="/WEB-INF/jsp/skins/adminMaketData.jsp"/>
</tiles:insert>

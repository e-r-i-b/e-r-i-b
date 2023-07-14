<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 01.04.2009
  Time: 17:18:06
  To change this template use File | Settings | File Templates.

  —оздан только чтобы на тестовой странице скина можно было позвать tiles:impportAttribute.
  Ќе дописывать head-html!!!
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:html>
    <tiles:insert attribute="data"/>
</html:html>
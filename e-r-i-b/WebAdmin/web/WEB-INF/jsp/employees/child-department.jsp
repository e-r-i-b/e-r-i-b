<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 22.04.2009
  Time: 11:21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-nested" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<c:set var="form" value="${EmployeeDepartmentListForm}"/>
<c:set var="departmentRootId" value="${form.departmentRootId}"/>
<c:set var="childs" value="${form.children}"/>

&nbsp;
<script type="text/javascript">
    var child;
    var childs = new Array();

    <logic:iterate id="child" name="childs">
        <c:set var="id"             value="${child[0]}"/>
        <c:set var="name"           value="${child[1]}"/>
        <c:set var="allowed"        value="${child[2]}"/>
        <c:set var="countChild"     value="${child[3]}"/>
        <c:set var="allowedAdmin"   value="${child[4]}"/>

        child                   = new Object();
        child.id                = '${id}';
        child.name              = '${name}';
        child.allowed           = '${allowed}';
        child.has               = '${countChild}';
        child.allowed_admin     = '${allowedAdmin}';
        childs[childs.length]   = child;
    </logic:iterate>
    parent.addRow(${departmentRootId}, childs);
</script>
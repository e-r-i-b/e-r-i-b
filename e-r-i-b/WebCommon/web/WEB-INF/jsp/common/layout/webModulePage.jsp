<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/web-module-tags" prefix="webmodule" %>

<tiles:importAttribute/>

<%-- ������ ������� html-�������� ������ � ������� ���-���������� ��������� --%>

<webmodule:page description="${description}" breadcrumbs="${breadcrumbs}" filter="${filter}" submenu="${submenu}" title="${title}">
    ${data}
</webmodule:page>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
���������� ������������.
������ ������ ������������ �� ��������� question ������������ � ������ bundle.
� ������ �������������� ������ ��������� ���� � ��������. � ������ �������������� ��������� ��������� ��������
�� ��������� okAction
--%>
<tiles:importAttribute/>

<c:set var="text"><bean:message key="${question}" bundle="${bundle}"/></c:set>

<script type="text/javascript">
    doOnLoad(function ()
    {
        if (confirm("${phiz:escapeForJS(text, true)}"))
        {
            ${okAction};
        }
    });
</script>

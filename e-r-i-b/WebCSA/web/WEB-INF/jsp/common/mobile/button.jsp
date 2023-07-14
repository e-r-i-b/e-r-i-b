<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%-- ������ ���������� ��������� ������ ����������.
    url  - ������ ��� �������� �� ������
    text - ������������ ����� ������.
    btnType - ��� ������, ��������� �������� simple, download
    onClickAction - �������� �� ������� ������
--%>
<tiles:importAttribute/>
<a href="${URL}" class="${btnType}Button"><c:out value="${text}"/></a>
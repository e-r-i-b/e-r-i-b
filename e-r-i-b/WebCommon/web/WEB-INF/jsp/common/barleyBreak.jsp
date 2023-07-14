<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>


<tiles:importAttribute/>
<%--
   ��������� ����������� �������� ��������
   totalColums - ����� �������� (2 ��� 3) �� ��������� 3
   data - ������ ������ ��� ��������
   styleClass - ��� ��������������� ������ ����� �� ��������� �����
--%>

<c:forEach items="${data}" var="curData">
    <div class="col${totalColums} ${styleClass}">
        ${curData}
    </div>
</c:forEach>
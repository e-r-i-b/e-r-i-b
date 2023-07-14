<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    ��������� ��� ����������� ����� �� ������ ���������� CSS3
    data - ������
    color - ����(�����) �����������
        �������� ���������  �����:
            greenBold - ������ ������� �����
            red - ���� � ������� �����. (������������ ��� ��������� � ��������)
            orange - ���� � ��������� �����. (������������ ��� ��������� � ����������������)
            infMesRed - ���� � ������� �����. (������������ ��� ��������� � ��������)
            infMesOrange - ���� � ��������� �����. (������������ ��� ��������� � ����������������)
            infMesGreen - ���� � ������� �����.
--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>


<div class="workspace-box roundBorder ${color}">
    ${data}
    <div class="clear"></div>
</div>
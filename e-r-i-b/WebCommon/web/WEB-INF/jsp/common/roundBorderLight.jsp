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
    title - ��������� �����
--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>


<div class="workspace-box roundBorder ${color}">
    <div class="r-content">
        <c:if test="${title != ''}">
            <div class="roundTitle">
                ${title}
            </div>
        </c:if>
        ${data}
        <div class="clear"></div>
    </div>
</div>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    ��������� ��� ����������� �����
    data - ������
    top - ������ ������ ��������
    color - ����(�����) �����������
        �������� ���������  �����:
            lightGreen - ������ ������� ������������ � ��������
            greenTop - ����� � ������� ������� ������ � ������ ��� ���������
            greenBold - ������ ������� ����� � �����
            gray - �����
            green - ������ ������� ����� (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            shadow - ����������� � �����
            sTable - ����� ����������� ��� ������� � ���������� �� ������ ��������� (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            red - ������� ������� ����� (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)
            orange - ��������� ������� �����. (�� ������������, ��� ������� ����� ������� �������� � ����� �� roundBorder.css)

    ����������: ��� ��� IE6 �� ������������ ������� CSS ������ (class1.class2) �� ��� ����
                ����� ����� ����� ������������ ���� � ����� ����������� �������� ����������� ���� ${color}RTL.
                ������ ���� r-top ���������� ��� �������� ����� ������������� ���� ������������ ������.
                ���������� ���� RT r-top ���������������� ��� (round top).

--%>

<tiles:importAttribute/>

<c:if test="${color == ''}">
    <c:set var="color" value=""/>
</c:if>

<div class="workspace-box ${color} withoutTop">
    <div class="top-content">
        ${top}
    </div>
    <div class="clear"></div>
    <div class="${color}RC r-content">
        ${data}
        <div class="clear"></div>
    </div>
</div>
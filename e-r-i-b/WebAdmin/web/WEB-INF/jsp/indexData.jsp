<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<div class="textCard">
    <tiles:insert definition="infoBlock" flush="false">
        <tiles:put name="image" value="iconBig_help.gif"/>
        <tiles:put name="otherShadow" value="true"/>
        <tiles:put name="data">
            ��� ����������� ������ � �������� �������� ������ ����� �������� ����. �������� ��������� ������� �������� ���� ������� �� ����� ���� �������, ����������� ��� ��������������� �������.
            ��� ���������� ������ � ��� ���������� ����� ����������� ������ "�����".
        </tiles:put>
    </tiles:insert>
</div>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
��������� ��� ����������� �������
tabItems - ����� �������, ������� �� ����������� tabItem
count - ���������� �������, ���� 2 �������, �� count=2, ���� 3 �������, �� count=3, ����� �����
--%>

<tiles:importAttribute/>
<c:set var="clazz" value="secondMenuContainer"/>
<div class="${clazz}">
    <ul class="newSecondMenu newSecondMenu${count}">
        ${tabItems}
    </ul>
</div>
<div class="clear"></div>
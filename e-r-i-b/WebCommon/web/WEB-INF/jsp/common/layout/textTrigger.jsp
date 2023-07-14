<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
��������� ����������� ���������, ��������� ������ checkbox
titles - ������ ��������
values - ������ ��������
����������! titles � values ����������� ����� �����, �������������, �� ������ � ������������������ ������ ����.
selectedValue - ��������� ��������
name - ��� ����������
onclick - js ������� ������� ���������� ��������� ��� ������������ �������. �� ��������� �����.
--%>

<tiles:importAttribute/>

<input type="hidden" name="${name}" value="${selectedValue}" />

<c:forEach items="${titles}" var="title" varStatus="status">

   <c:set var="className" value="greenSelector"/>
    <c:if test="${values[status.index] != selectedValue}">
        <c:set var="className" value="greenSelector transparent"/>
    </c:if>
    <div class="${className}" id="${name}_${values[status.index]}" onclick="textTriggerChange ('${name}', '${values[status.index]}'); (function () {${onclick}})(); return false;">
               <span>
                   ${title}
               </span>
    </div>
</c:forEach>

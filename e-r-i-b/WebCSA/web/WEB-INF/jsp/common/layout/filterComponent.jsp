<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:importAttribute ignore="true"/>
<%--
    ��������� ��� ����������� �������

    data                - ������ �������
    hiddenData          - ������ ������������ �������
    buttonKey           - �������� ��� ������ ����������
    buttonBundle        - �����, ��� ������ �������� ��� ������ ����������
    validationFunction  - ������� ���������
    hideFilterButton    - ������� ������������� ������� ���������� ������ �������. �� ��������� false
--%>
<c:if test="${empty filterComponentCounter}">
    <c:set var="filterComponentCounter" value="0" scope="request"/>
</c:if>

<c:set var="filterComponentCounter" value="${filterComponentCounter+1}"  scope="request"/>

<div class="filter-wrapper">
    <div class="filter" onkeypress="onCsaFilterEnterKey(event);">
        ${data}
        <%-- ������ ������� --%>
        <c:if test="${hideFilterButton}">
            <c:set var="style" value="display: none;"/>
        </c:if>
        <span class="filterButton" style="${style}"></span>
        <%-- ���������� ������ --%>
        <c:if test="${not empty hiddenData}">
            <%-- �������� �������, ���������� � ����������� �� ����� --%>           
            <c:set var="fForm" value="${csa:currentForm(pageContext)}"/>
            <c:set var="filterCounter" value="filterCounter${filterComponentCounter}"/>
            <c:set var="counterState">${fForm.filterState[filterCounter] == null?"false":fForm.filterState[filterCounter]}</c:set>
            <input type="hidden" name="filterState(filterCounter${filterComponentCounter})" value='<c:out value="${counterState}"/>'/>

            <div class="clear"></div>
            <div class="filterMore">
                ${hiddenData}
            </div>
        </c:if>
   </div>
</div>
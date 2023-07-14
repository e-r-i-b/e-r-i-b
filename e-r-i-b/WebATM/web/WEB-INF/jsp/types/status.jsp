<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    ���� � ���������� ����������� Status
    STATUS_OK � STATUS_LOGIC_ERROR ������������ � login.jsp � atm.jsp
    STATUS_END_SESSION ������������ � login.jsp
    STATUS_CRITICAL_ERROR ��������������� � jsp exceptions
    STATUS_PRODUCT_ERROR ������������ � products/list
    STATUS_ACCESS_DENIED ������ ��������
    STATUS_BANNED ������ � ����������� ��������� (��������, �� ���������� �� ������ �������� ������������)
--%>
<c:set var="STATUS_OK" value="0"/><%-- ������ ����������� --%>
<c:set var="STATUS_LOGIC_ERROR" value="1"/><%-- ���������� ������ --%>
<c:set var="STATUS_CRITICAL_ERROR" value="2"/><%-- ����������� ������ --%>
<c:set var="STATUS_END_SESSION" value="3"/><%-- ������ ����� ���������� --%>
<c:set var="STATUS_PRODUCT_ERROR" value="4"/><%-- ������ ���������� �������� --%>
<c:set var="STATUS_ACCESS_DENIED" value="5"/><%-- ������ �������� --%>
<c:set var="STATUS_BANNED" value="6"/><%-- ������ � ����������� ��������� --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
    ���� � ���������� ����������� Status
    STATUS_OK � STATUS_LOGIC_ERROR ������������ � login.jsp � iphone.jsp
    STATUS_END_SESSION ������������ � login.jsp
    STATUS_CRITICAL_ERROR ��������������� � jsp exceptions
    STATUS_PRODUCT_ERROR ������������ � products/list
    STATUS_ACCESS_DENIED ������ ��������
    STATUS_BANNED ������ � ����������� ��������� (��������, �� ���������� �� ������ �������� ������������)
    STATUS_RESET_MGUID ������ � ����������� ��������� (��������, ������� ������������ ����������� ����������)
    STATUS_SIM_ERROR ��� �������� SMS ���������� ������ SIM-����� (��� ����������� ����������)
    STATUS_CARD_NUM_ERROR ����������� ����� ����� ��� ����������� � mAPI
--%>
<c:set var="STATUS_OK" value="0"/>
<%-- ������ ����������� --%>
<c:set var="STATUS_LOGIC_ERROR" value="1"/>
<%-- ���������� ������ --%>
<c:set var="STATUS_CRITICAL_ERROR" value="2"/>
<%-- ����������� ������ --%>
<c:set var="STATUS_END_SESSION" value="3"/>
<%-- ������ ����� ���������� --%>
<c:set var="STATUS_PRODUCT_ERROR" value="4"/>
<%-- ������ ���������� �������� --%>
<c:set var="STATUS_ACCESS_DENIED" value="5"/>
<%-- ������ �������� --%>
<c:set var="STATUS_BANNED" value="6"/>
<%-- ������ � ����������� ��������� --%>
<c:set var="STATUS_RESET_MGUID" value="7"/>
<%-- ������ � ����������� ���������, ���������� �������� mGUID --%>
<c:set var="STATUS_SIM_ERROR" value="9"/>
<%-- ��� �������� sms ���������� ������ ���-����� --%>
<c:set var="STATUS_CARD_NUM_ERROR" value="10"/>
<%-- ����������� ����� ����� ��� ����������� � mAPI --%>


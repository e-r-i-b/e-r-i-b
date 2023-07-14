<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="availableIMAPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation','IMAPayment')}"/>
    <%--������� ����� ������ �������--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="InternalPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">������� ����� ������ ������� � �������</tiles:put>
        <tiles:put name="hint">
            ��������� ������ �� ���� �����, ����� ��� ����.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_InternalPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="InternalPayment"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="InternalPayment"/>
    </tiles:insert>
    <%--������� �������� ����--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="RurPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">������� ������� ���������</tiles:put>
        <tiles:put name="hint">
            ��������� ������ �������� ���� �� �����, ����� ��� ����.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_RurPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="RurPayment"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="RurPayment"/>
    </tiles:insert>
    <%-->������� ����������� � ������ �����--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="JurPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">������� ����������� � ������ ����� </tiles:put>
        <tiles:put name="hint">
            ��������� ������ � �������� ������.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_JurPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/jurPayment/edit.do">
            <phiz:param name="category" value="${categoryId}"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="JurPayment"/>
    </tiles:insert>
    <%--����� ������--%>
    <tiles:insert definition="categoryTemplate" flush="false" service="ConvertCurrencyPayment" operation="CreateFormPaymentOperation">
        <tiles:put name="title">����� ������</tiles:put>
        <tiles:put name="hint">
            ������, ������� ��� �������� ����������� ������.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/iconPmntList_ConvertCurrencyPayment.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="category" value="${categoryId}"/>
            <phiz:param name="form" value="ConvertCurrencyPayment"/>
        </c:url></tiles:put>
        <tiles:put name="serviceId" value="ConvertCurrencyPayment"/>
    </tiles:insert>
    <c:if test="${availableIMAPayment}">
        <%--�������/������� �������--%>
        <tiles:insert definition="categoryTemplate" flush="false" service="IMAPayment" operation="CreateFormPaymentOperation">
            <tiles:put name="title">�������/������� �������</tiles:put>
            <tiles:put name="hint">
                ������ ��� ������� ����������� ������.
            </tiles:put>
            <tiles:put name="imagePath" value="${imagePath}/iconPmntList_IMAPayment.png"/>
            <tiles:put name="url"><c:url value="/private/payments/payment.do">
                <phiz:param name="category" value="${categoryId}"/>
                <phiz:param name="form" value="IMAPayment"/>
            </c:url></tiles:put>
            <tiles:put name="serviceId" value="IMAPayment"/>
        </tiles:insert>
    </c:if>

    <%--<tiles:insert definition="categoryTemplate" flush="false">
        <tiles:put name="title">���������� ���������� ������</tiles:put>
        <tiles:put name="hint">
            ������ �������� �� ���������� ����.
        </tiles:put>
        <tiles:put name="imagePath" value="${imagePath}/icon_pmnt_money.png"/>
        <tiles:put name="url"><c:url value="/private/payments/payment.do">
            <phiz:param name="form" value="InternalPayment"/>
        </c:url></tiles:put>
    </tiles:insert>--%>

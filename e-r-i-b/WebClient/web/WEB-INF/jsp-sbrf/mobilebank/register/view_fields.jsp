<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.ConfirmRegistrationForm"--%>

<%-- 1. ����� FULL/ECONOM --%>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">����� ������������:</tiles:put>
    <tiles:put name="data">
        <c:choose>
            <c:when test="${form.tariff == 'FULL'}">
                <b>������ �����</b>
            </c:when>
            <c:when test="${form.tariff == 'ECONOM'}">
                <b>��������� �����</b>
            </c:when>
        </c:choose>
    </tiles:put>
</tiles:insert>

<%-- 2. ������������� ����� �������� --%>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">����� ��������:</tiles:put>
    <tiles:put name="data">
        <b>${form.maskedPhone}</b>
    </tiles:put>
    <tiles:put name="description">
        ������ &laquo;��������� ����&raquo; ����� ���������� �� ���� ����� ��������.
    </tiles:put>
</tiles:insert>

<%-- 3. ������������� ����� ����� --%>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title">����� �����:</tiles:put>
    <tiles:put name="data">
        <b><c:out value="${form.maskedCard}"/></b>
    </tiles:put>
    <tiles:put name="description">
        ��� ������ ����� ����� ���������� ������ &laquo;��������� ����&raquo;.
    </tiles:put>
</tiles:insert>

<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute ignore="true"/>
<%--
  ���������� ��� ������������ ������ ���� Currency
  currency - ������ ���� Currency
--%>

<c:if test="${not empty currency}">
        <currency>
            <code>${phiz:normalizeMetalCode(currency.code)}</code> <%--���� �������� �������� � ���������������� ����--%>
            <name>${phiz:getCurrencySign(currency.code)}</name>
        </currency>
</c:if>

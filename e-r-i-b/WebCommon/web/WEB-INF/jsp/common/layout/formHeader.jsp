<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<%--
    ������ ��� ����������� ������ � ��������� �� ������ ��������� � �������������� (������, �������, ����� ����������� � �.�.)
    description   - �������� �����
    image   -  ������ (����� �������������)
    alt - ����� ��������
    width - ������ ������
    height - ������ ������
--%>
<tiles:importAttribute/>
<table class="paymentHeader">
    <tr>
        <c:if test="${not empty image}">
            <td>
                <img class="icon" src="${image}" alt="${alt}" <c:if test="${not empty width}">width="${width}"</c:if> <c:if test="${not empty height}">height="${height}"</c:if> />
            </td>
        </c:if>
        <td>
           ${description}
        </td>
    </tr>
</table>
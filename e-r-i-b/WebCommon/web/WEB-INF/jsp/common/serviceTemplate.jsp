<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<%--
    ���� � ����������� ���������� (�������� ������� � ��������, �������� ��������� � ���� ������ ���������� �����)
    title   - ��������� �����
    links    - ������ ������
    imagePath - ���� � �����������
    defaultImagePath - ���� � ����������� �� ���������
    url  -  ������, �� ������� ��������� ������� �� ���������
    serviceId  - �������������� ������
    availableAutopayment  - ������� ����������� �����������
    titleClass  - ����� ��� �������� ���������/������
    templateClass  - �������������� �����
--%>


<div class="payment ${templateClass}">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <td class="paymentIcon">
                <c:choose>
                    <c:when test="${not empty imagePath}">
                        <c:choose>
                            <c:when test="${disabled}">
                                <img src="${imagePath}" alt="" border="0"/>
                            </c:when>
                            <c:when test="${empty serviceId}">
                                <phiz:link url="${fn:trim(url)}">
                                    <img src="${imagePath}" alt="" border="0"/>
                                </phiz:link>
                            </c:when>
                            <c:otherwise>
                                <phiz:link url="${fn:trim(url)}" serviceId="${serviceId}">
                                    <img src="${imagePath}" alt="" border="0"/>
                                </phiz:link>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <img src="${defaultImagePath}" alt="" border="0"/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="paddLeft10">
                <div class="${titleClass}">
                    <c:choose>
                        <c:when test="${disabled}">
                            ${title}
                        </c:when>
                        <c:otherwise>
                            <phiz:link url="${fn:trim(url)}" serviceId="${serviceId}" styleClass="paymentLinkTitle orangeText"><span class="word-wrap">${title}</span></phiz:link>
                            <c:if test="${!empty hint}"><span class="hidable" style="display:none;">${hint}</span></c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
                ${availableAutopayment}
                <ul class="paymentLinks">${links}</ul>
            </td>
        </tr>
    </table>
</div>


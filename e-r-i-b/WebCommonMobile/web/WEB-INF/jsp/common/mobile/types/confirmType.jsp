<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<tiles:importAttribute ignore="true"/>

<%--
���� ���������� �������������
confirmRequest - ������� �������������

--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<%--anotherStrategy �������� �� ���� ������ � ��������������--%>
<c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
<confirmType>
    <c:choose>
        <%-- ��� ������������� (full � light �����) --%>
        <c:when test="${confirmRequest.strategyType == 'none' or confirmRequest.strategyType == 'composite'}">
            <strategy>
                <type>none</type>
                <order>0</order>
            </strategy>
        </c:when>
        <%-- ������������� ��������� (limited) --%>
        <c:when test="${confirmRequest.strategyType == 'DENY'}">
            <strategy>
                <type>deny</type>
                <order>0</order>
            </strategy>
        </c:when>
        <%-- ������ ��������� ��������� ������������� � �����������  --%>
        <c:otherwise>
            <c:if test="${confirmRequest.strategyType eq 'sms' || anotherStrategy}">
                <strategy>
                    <type>smsp</type>
                    <c:choose>
                        <c:when test="${confirmRequest.strategyType eq 'sms'}">
                            <order>0</order>
                        </c:when>
                        <c:otherwise>
                            <order>1</order>
                        </c:otherwise>
                    </c:choose>
                </strategy>
            </c:if>
            <c:if test="${confirmRequest.strategyType eq 'push' || anotherStrategy}">
                <strategy>
                    <type>pushp</type>
                    <c:choose>
                        <c:when test="${confirmRequest.strategyType eq 'push'}">
                            <order>0</order>
                        </c:when>
                        <c:otherwise>
                            <order>1</order>
                        </c:otherwise>
                    </c:choose>
                </strategy>
            </c:if>
        </c:otherwise>
    </c:choose>
</confirmType>
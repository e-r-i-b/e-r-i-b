<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<tiles:importAttribute ignore="true"/>

<%--
���� ���������� �������������
confirmRequest - ������� �������������

--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<%--anotherStrategy �������� �� ���� ������ � ��������������--%>
<c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
<confirmType>

            <strategy>
                <type>none</type>
                <order>0</order>
            </strategy>
 
</confirmType>
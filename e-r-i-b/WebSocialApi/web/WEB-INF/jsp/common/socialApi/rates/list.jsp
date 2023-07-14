<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${GetCurrencyRatesMobileForm}"/>
<c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>
<c:set var="rates">
    <c:if test="${empty form.type or form.type == 'currency'}">
        <%-- ���� USD � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="USD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� EUR � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="EUR"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
    <c:if test="${empty form.type or form.type == 'metal'}">
        <%-- ���� ������ � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A98"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� ������� � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A99"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� ������� � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A76"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� �������� � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A33"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
    <c:if test="${not empty form.type and form.type eq 'extended'}">
        <%-- ���� GBP (����� ����������) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="GBP"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� CHF (����������� �����) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="CHF"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� JPY (�������� ����) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="JPY"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� SEK (�������� �����) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="SEK"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� NOK (���������� �����) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="NOK"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� CAD (��������� ������) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="CAD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� SGD (������������ ������) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="SGD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� AUD (������������� ������) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="AUD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- ���� DKK (������� �����) � ����� --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="DKK"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
</c:set>

<tiles:insert definition="iphone" flush="false">
    <tiles:put name="data">
        <c:if test="${fn:trim(rates) != ''}">
            <rates>
                ${rates}
            </rates>
        </c:if>
    </tiles:put>
</tiles:insert>
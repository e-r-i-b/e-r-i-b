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
        <%-- курс USD к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="USD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс EUR к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="EUR"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
    <c:if test="${empty form.type or form.type == 'metal'}">
        <%-- курс золота к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A98"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс серебра к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A99"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс платины к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A76"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс паллади€ к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="A33"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
    </c:if>
    <c:if test="${not empty form.type and form.type eq 'extended'}">
        <%-- курс GBP (фунты стерлингов) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="GBP"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс CHF (швейцарский франк) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="CHF"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс JPY (€понска€ йена) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="JPY"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс SEK (шведска€ крона) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="SEK"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс NOK (норвежска€ крона) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="NOK"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс CAD (канадский доллар) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="CAD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс SGD (сингапурский доллар) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="SGD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс AUD (австралийский доллар) к рублю --%>
        <tiles:insert definition="mobileRate" flush="false">
            <tiles:put name="mainCode" value="RUB" />
            <tiles:put name="code" value="AUD"/>
            <tiles:put name="department" beanName="department"/>
        </tiles:insert>
        <%-- курс DKK (датска€ крона) к рублю --%>
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
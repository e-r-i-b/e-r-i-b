<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--���� ������ ����� � ��������--%>
<tiles:insert definition="currencyRateTemplate" flush="false">
    <tiles:put name="title" value="�����"/>
    <%--����� �������/������� �����--%>
    <tiles:put name="currencyRateItems">
        <tiles:insert definition="currencyRateTemplateItem" flush="false">
            <tiles:put name="fromCurrencyCode" value="EUR"/>
            <tiles:put name="fromCurrencyTitle" value="EUR"/>
            <tiles:put name="needShowDynamic" value="true"/>
        </tiles:insert>
        <tiles:insert definition="currencyRateTemplateItem" flush="false">
            <tiles:put name="fromCurrencyCode" value="USD"/>
            <tiles:put name="fromCurrencyTitle" value="USD"/>
            <tiles:put name="needShowDynamic" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="currencyRateBottomLink" value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=ConvertCurrencyPayment')}"/>
    <tiles:put name="currencyRateBottomLinkTitle" value="����� ������"/>

    <%--����� �������/������� ��������--%>
    <tiles:put name="metallRateItems">
        <tiles:insert definition="currencyRateTemplateItem" flush="false">
            <tiles:put name="fromCurrencyCode" value="A98"/>
            <tiles:put name="fromCurrencyTitle" value="������"/>
        </tiles:insert>
        <tiles:insert definition="currencyRateTemplateItem" flush="false">
            <tiles:put name="fromCurrencyCode" value="A99"/>
            <tiles:put name="fromCurrencyTitle" value="�������"/>
        </tiles:insert>
        <tiles:insert definition="currencyRateTemplateItem" flush="false">
            <tiles:put name="fromCurrencyCode" value="A76"/>
            <tiles:put name="fromCurrencyTitle" value="�������"/>
        </tiles:insert>
        <tiles:insert definition="currencyRateTemplateItem" flush="false">
            <tiles:put name="fromCurrencyCode" value="A33"/>
            <tiles:put name="fromCurrencyTitle" value="��������"/>
        </tiles:insert>
    </tiles:put>

    <c:set var="imaLinkTitle" value="������� � ������� �������"/>
    <c:set var="imaLink" value="private/payments/payment.do?form=IMAPayment"/>

    <c:if test="${not phiz:haveThePersonIMAccount()}">
        <c:set var="imaLinkTitle" value=""/>
        <c:set var="imaLink" value=""/>
        <%--������ ���� ����� �� "������ �� �������� ���" � ���� ��: "�������� ��� � ��������� �� ������", "�������� ��� � ��������� � �����"--%>
        <c:if test="${phiz:impliesService('IMAOpeningClaim') &&
                     (phiz:impliesService('IMAOpeningFromCardClaim') || phiz:impliesService('IMAOpeningFromAccountClaim'))}">
            <c:set var="imaLinkTitle" value="������� ������������� ����"/>
            <c:set var="imaLink" value="private/ima/products/list.do?form=IMAOpeningClaim"/>
        </c:if>
    </c:if>

    <tiles:put name="metallRateBottomLink" value="${phiz:calculateActionURL(pageContext, imaLink)}"/>
    <tiles:put name="metallRateBottomLinkTitle" value="${imaLinkTitle}"/>
</tiles:insert>


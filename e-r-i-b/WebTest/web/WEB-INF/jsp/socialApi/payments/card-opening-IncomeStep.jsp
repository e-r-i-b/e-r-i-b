<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>���������� ������ �� ��������� �����(����� ������)</title>

    </head>

    <body>
    <h1>���������� ������ �� ��������� �����(����� ������)</h1>

    <html:form action="/mobileApi/cardOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/payments/incomeLevel.do"/>
            <tiles:put name="operation" value="next"/>

            <tiles:put name="data">
                <c:if test="${not empty form.response and not empty form.response.incomeStage}">
                    <c:set var="incomeStage" value="${form.response.incomeStage}"/>
                    <table>
                        <caption>������� ����� ������� �������</caption>

                        <c:forEach var="option" items="${incomeStage.option}">
                            <tr>
                                <td>
                                    <html:radio property="id" value="${option.id}"/> ID: <c:out value="${option.id}"/>
                                </td>
                                <td>
                                    <c:if test="${not empty option.minIncome}">
                                        �� <c:out value="${option.minIncome.amount}"/>   <c:out value="${phiz:normalizeMetalCode(option.minIncome.currency.code)}"/>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${not empty option.maxIncome}">
                                        �� <c:out value="${option.maxIncome.amount}"/>   <c:out value="${phiz:normalizeMetalCode(option.maxIncome.currency.code)}"/>
                                    </c:if>
                                </td>
                                <td>
                                      <c:if test="${option.maxIncomeInclude}">*</c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>

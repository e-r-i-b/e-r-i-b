<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>
<fieldset>
    <table class="additional-deposit-info">
        <c:forEach  items="${form.depositorAccounts}" var="account">
            <c:set var="rub" value="${account.currency.code == 'RUB'}"/>
            <c:choose>
                <c:when test="${rub}" >
                    <tr>
                        <td class="align-right field"><span class="bold depoAddress">�������� ����</span></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="align-right field">����� ��������� �����:</td>
                        <td><span class="bold">${account.accountNumber}</span></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td class="align-right field"><span class="bold depoAddress">�������� ����</span></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="align-right field">������ �����:</td>
                        <td><span class="bold">${account.currency.code}</span></td>
                    </tr>
                    <tr>
                        <td class="align-right field">����� ��������� �����:</td>
                        <td><span class="bold">${account.accountNumber}</span></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            <tr>
                <td class="align-right field">��� ����������� �����:</td>
                <td><span class="bold">${account.cardType}</span></td>
            </tr>
            <tr>
                <td class="align-right field">����� ����������� �����:</td>
                <td><span class="bold">${account.cardId}</span></td>
            </tr>
            <tr>
                <td class="align-right field">����, ��� ������ 
                    <c:choose>
                        <c:when test="${rub}">��������</c:when>
                        <c:otherwise>��������</c:otherwise>
                    </c:choose>
                    ����:</td>
                <td><span class="bold">${account.bankName}</span></td>
            </tr>
            <tr>
                <td class="align-right field">��� <c:if test="${not rub}">, SWIFT</c:if>:</td>
                <td><span class="bold">${account.BIC}</span></td>
            </tr>
            <tr>
                <td class="align-right field">����������������� ����:</td>
                <td><span class="bold">${account.corAccount}</span></td>
            </tr>
            <tr>
                <td class="align-right field">����, ��� ������ ����������������� ����:</td>
                <td><span class="bold">${account.corBankName}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���������� �����:</td>
                <td><span class="bold">${account.destination}</span></td>
            </tr>
        </c:forEach>
        <tr>
            <td class="align-right field"><span class="bold depoAddress">�������������� ��������� ������</span></td>
            <td>&nbsp;</td>
        </tr>
        <c:if test="${not empty form.recIncomeMethod}">
            <tr>
                <td class="align-right field">������ ��������� �������:</td>
                <td><span class="bold">${form.recIncomeMethod}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.recInstryctionMethod}">
            <tr>
                <td class="align-right field">������ ������ ��������� �� ��������� ����� ����:</td>
                <td><span class="bold">${form.recInstryctionMethod}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.recInfoMethod}">
            <tr>
                <td class="align-right field">������ �������� ���������� ��������� ����� ����:</td>
                <td><span class="bold">${form.recInfoMethod}</span></td>
            </tr>
        </c:if>
        <tr>
            <td class="align-right field">���� ���������� ������:</td>
            <td><span class="bold">${phiz:formatDateWithStringMonth(form.startDate)}</span></td>
        </tr>
    </table>
</fieldset>
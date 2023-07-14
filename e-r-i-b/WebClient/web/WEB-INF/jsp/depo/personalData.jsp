<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>
<fieldset>
    <table class="additional-deposit-info">
        <tr>
            <td class="align-right field">���:</td>
            <td><span class="bold">${form.INN}</span></td>
        </tr>
        <tr>
            <td class="align-right field">���� ��������:</td>
            <td>
                <span class="bold">${phiz:formatDateWithStringMonth(form.birthday)}</span>
            </td>
        </tr>
        <tr>
            <td class="align-right field">����� ��������:</td>
            <td><span class="bold">${form.birthPlace}</span></td>
        </tr>
        <tr>
            <td class="align-right field">��������:</td>
            <td><span class="bold"><bean:message key="passport.type.${form.idType}"  bundle="depoBundle"/> </span></td>
        </tr>
        <tr>
            <td class="align-right field">����� ���������:</td>
            <td><span class="bold">${form.idSeries}</span></td>
        </tr>
        <tr>
            <td class="align-right field">����� ���������:</td>
            <td><span class="bold">${form.idNum}</span></td>
        </tr>
        <tr>
            <td class="align-right field">���� ������:</td>
            <td><span class="bold">${phiz:formatDateWithStringMonth(form.idIssueDate)}</span></td>
        </tr>
        <c:if test="${not empty form.idExpDate}">
            <tr>
                <td class="align-right field">������������ ��:</td>
                <td><span class="bold">${phiz:formatDateWithStringMonth(form.idExpDate)}</span></td>
            </tr>
        </c:if>
        <tr>
            <td class="align-right field">��� ����� ��������:</td>
            <td><span class="bold">${form.idIssuedBy}</span></td>
        </tr>
        <c:if test="${not empty form.idIssuedCode}">
            <tr>
                <td class="align-right field">��� �������������:</td>
                <td>
                    <span class="bold">${form.idIssuedCode}</span>
                </td>
            </tr>
        </c:if>
        <tr>
            <td class="align-right field">�����������:</td>
            <td>
                <span class="bold">${form.citizenship}</span>
            </td>
        </tr>
        <tr>
            <td class="align-right field">�������������� ����������:</td>
            <td>
                <span class="bold">${form.additionalInfo}</span>
            </td>
        </tr>
    </table>
</fieldset>
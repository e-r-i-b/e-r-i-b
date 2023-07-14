<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>
<fieldset>
    <table class="additional-deposit-info">
        <c:if test="${form.registrationAddress  != ''}">
            <c:set var="addr" value="${form.registrationAddress}" />
            <tr>
                <td class="align-right field"><span class="bold depoAddress">����� �����������</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${form.regAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�������� ������:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������(����������, �������, ����,<br/>���������� �����, ���������� �������):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���������� �����:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">��������:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>
        <c:if test="${form.residenceAddress != ''}">
            <c:set var="addr" value="${form.residenceAddress}" />
            <tr>
                <td class="align-right field"><span class="bold">����� ����������</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${form.resAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�������� ������:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������(����������, �������, ����,<br/>���������� �����, ���������� �������):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���������� �����:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">��������:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>

        <c:if test="${form.forPensionAddress  != ''}">
            <c:set var="addr" value="${form.forPensionAddress}" />
            <tr>
                <td class="align-right field"><span class="bold depoAddress">����� ��� ��������� ������ �������� ������������</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${form.forPensionAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�������� ������:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������(����������, �������, ����,<br/>���������� �����, ���������� �������):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���������� �����:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">��������:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>

        <c:if test="${form.mailAddress  != ''}">
            <c:set var="addr" value="${form.mailAddress}" />
            <tr>
                <td class="align-right field"><span class="bold">����� ��� �������� �����������</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${form.mailAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�������� ������:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������(����������, �������, ����,<br/>���������� �����, ���������� �������):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���������� �����:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">��������:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>
        <c:if test="${form.workAddress  != ''}">
            <c:set var="addr" value="${form.workAddress}" />
            <tr>
                <td class="align-right field"><span class="bold">����� ����� ������</span></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${form.workAddressCountry}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�������� ������:</td>
                <td><span class="bold">${addr.postalCode}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������(����������, �������, ����,<br/>���������� �����, ���������� �������):</td>
                <td><span class="bold">${addr.province}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.district}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.city}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���������� �����:</td>
                <td><span class="bold">${addr.settlement}</span></td>
            </tr>
            <tr>
                <td class="align-right field">�����:</td>
                <td><span class="bold">${addr.street}</span></td>
            </tr>
            <tr>
                <td class="align-right field">���:</td>
                <td><span class="bold">${addr.house}</span></td>
            </tr>
            <tr>
                <td class="align-right field">������:</td>
                <td><span class="bold">${addr.building}</span></td>
            </tr>
            <tr>
                <td class="align-right field">��������:</td>
                <td><span class="bold">${addr.flat}</span></td>
            </tr>
        </c:if>

        <tr>
            <td class="align-right field"><span class="bold depoAddress">�������������� ���� �����:</span> </td>
            <td>&nbsp;</td>
        </tr>
        <c:if test="${not empty form.homeTel}">
            <tr>
                <td class="align-right field">�������� �������:</td>
                <td><span class="bold">${form.homeTel}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.workTel}">
            <tr>
                <td class="align-right field">������� �������:</td>
                <td><span class="bold">${form.workTel}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.mobileTel}">
            <tr>
                <td class="align-right field">��������� �������:</td>
                <td><span class="bold">${form.phoneOperator} ${form.mobileTel}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.fax}">
            <tr>
                <td class="align-right field">����:</td>
                <td><span class="bold">${form.fax}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.privateEmail}">
            <tr>
                <td class="align-right field">������������ E-mail:</td>
                <td><span class="bold">${form.privateEmail}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.workEmail}">
            <tr>
                <td class="align-right field">������� E-mail:</td>
                <td><span class="bold">${form.workEmail}</span></td>
            </tr>
        </c:if>
    </table>
</fieldset>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    creditContract - ������� �������
    isCredit - ������ ��� �����
--%>
<tiles:importAttribute/>
<div id="creditDetail" class="cred-hist-item-opened css3">
    <div class="cred-hist-item-opened-title">����� �������� � ��������� ��������</div>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">��������</td>
            <td>
                <c:out value="${creditContract.bankName}"/>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>��� ��������������</td>
            <td>
                <c:out value="${creditContract.financeType}"/>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ��������������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.purposeOfFinance}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>��� �����������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.typeOfSecurity}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>��� ��������� �����</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.applicantType}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ���������� ��������</td>
            <td><c:out value="${creditContract.duration.value} ${creditContract.duration.unit}"/></td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>������� ��������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.reasonForClosure}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>�����������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.comment}"/>
                </tiles:insert>
            </td>
        </tr>
    </table>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">������</td>
            <td>${creditContract.amount.currency}</td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <c:choose>
                <c:when test="${isCredit}">
                    <td>����� �������</td>
                    <td>${creditContract.amount.value}</td>
                </c:when>
                <c:otherwise>
                    <td>��������� �����</td>
                    <td>${creditContract.creditLimit.value}</td>
                </c:otherwise>
            </c:choose>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <c:choose>
                <c:when test="${isCredit}">
                    <td>������ �������</td>
                </c:when>
                <c:otherwise>
                    <td>����������� ������</td>
                </c:otherwise>
            </c:choose>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.instalment.value}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>����� � �������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.balance.value}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>����� ���������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.arrearsBalance.value}"/>
                </tiles:insert>
            </td>
        </tr>
    </table>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">���� ��������</td>
            <td>${phiz:�alendarToString(creditContract.openDate)}</td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ���������� �������</td>
            <c:set var="lastPaymentDate" value="${creditContract.lastPaymentDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:�alendarToString(lastPaymentDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>��������� ����������� ������</td>
            <c:set var="lastMissedPaymentDate" value="${creditContract.lastMissedPaymentDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:�alendarToString(lastMissedPaymentDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ���������� ������������</td>
            <c:set var="fulfilmentDate" value="${creditContract.fulfilmentDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:�alendarToString(fulfilmentDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� �������� (�����������)</td>
            <c:set var="closedDate" value="${creditContract.closedDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:�alendarToString(closedDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ��������� ������������</td>
            <c:set var="litigationDate" value="${creditContract.litigationDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:�alendarToString(litigationDate)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ��������</td>
            <c:set var="writeOffDate" value="${creditContract.writeOffDate}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:�alendarToString(writeOffDate)}"/>
                </tiles:insert>
            </td>
        </tr>
    </table>

    <div class="cred-hist-item-opened-title">���������� � �������</div>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">������� ��� ��������</td>
            <td>${phiz:getFormattedPersonFIO(creditContract.firstName, creditContract.surname, creditContract.patronymic)}</td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� � ����� ��������</td>
            <td><c:out value="${phiz:getMaskedDate()} ${creditContract.birthPlace}"/></td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>�������� �������������� ��������</td>
            <td><c:out value="${creditContract.personDocumentType}, ${phiz:getMaskedText(creditContract.personDocumentNumber)}"/></td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>����� �����������</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.registrationAddress}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>����� ����������</td>
            <td>
                <c:out value="${creditContract.residentialAddress}"/>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>��������� �������</td>
            <c:set var="mobilePhoneNumber" value="${creditContract.mobilePhoneNumber}"/>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${phiz:getCutPhoneNumber(mobilePhoneNumber)}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>��� ����������� ����</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.inn}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>����� ����. �����. ����. ���.</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.pensionNumber}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>����� ������������� ��</td>
            <td>
                <tiles:insert definition="creditContractValue" flush="false">
                    <tiles:put name="value" value="${creditContract.entrepreneurNumber}"/>
                </tiles:insert>
            </td>
        </tr>
        <tr class="cred-hist-item-opened-table-line">
            <td>���� ��</td>
            <td><span class="gray">�</span></td>
        </tr>
    </table>

    <div class="cred-hist-item-opened-title">�������� ��������� �������</div>
    <table class="cred-hist-item-opened-table">
        <tr class="cred-hist-item-opened-table-line">
            <td class="cred-hist-item-opened-table-col1">������������:</td>
            <td><c:out value="${creditContract.subscriberName}"/></td>
        </tr>
    </table>
    <div class="clear"></div>
    <a onclick="hideOrShowCreditDetail(true);" href="#" class="cred-hist-item-contract-hide">������ ���������� � ��������</a>
</div>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
    <head>
        <title>�������� ������ �� ����������� ������ �� ������</title>
    </head>

    <body>
    <h1>�������� ������ �� ����������� ������ �� ������</h1>

    <c:set var="useXsdRelease16" value="${phiz:isXsd16ReleaseForEtsmUse()}"/>
    <c:set var="useXsdRelease19" value="${phiz:isXsd19ReleaseForEtsmUse()}"/>
    <c:choose>
        <c:when test="${useXsdRelease19 == true}">
            �������� ��������� � ������� XSD 19 ������ (��� ������������ �� ������ 16 ������ ���������� �������� ������� use-xsd-release-19-scheme � ������� LoanClaimConfig)
        </c:when>
        <c:when test="${useXsdRelease16 == true}">
            �������� ��������� � ������� XSD 16 ������ (��� ������������ �� ������ 13 ������ ���������� �������� ������� use-new-xsd-scheme � ������� LoanClaimConfig)
        </c:when>
        <c:otherwise>
            �������� ��������� � ������� XSD 13 ������ (��� ������������ �� ������ 16 ������ ���������� �������� ������� use-new-xsd-scheme � ������� LoanClaimConfig)
        </c:otherwise>
    </c:choose>

    <br/>
    <br/>

    <html:form action="/credit/loanclaim" show="true">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

        <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
            <tr><td>����� ������:</td><td><html:text property="claimNumber" size="20"/></td></tr>
            <tr><td>������ ������:</td><td><html:text property="claimStatus" size="1"/></td></tr>
            <tr><td>operUID:</td><td><html:text property="operUID" size="32"/></td></tr>
        </table>
        <h2> ���� � ������� �� ����������� �������:</h2>
        <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
            <tr><td>�����:</td><td><html:text property="approvedAmount" size="20"/></td></tr>
            <tr><td>������:</td><td><html:text property="approvedInterestRate" size="20"/></td></tr>
            <tr><td>������:</td><td><html:text property="approvedPeriod" size="10"/></td></tr>
        </table>
        <c:if test="${useXsdRelease16 or useXsdRelease19}">
            <h2> ���� � ������� �� �������������� ����������� �������:</h2>
            <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                <tr><td>�����:</td><td><html:text property="preApprovedAmount" size="20"/></td></tr>
                <tr><td>������:</td><td><html:text property="preApprovedInterestRate" size="20"/></td></tr>
                <tr><td>������:</td><td><html:text property="preApprovedPeriod" size="10"/></td></tr>
            </table>
            <c:if test="${useXsdRelease19}">
                <h2> ���� � �������</h2>
                <table style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <tr><td>����� ������ � TSM:</td><td><html:text property="applicationNumber" size="20"/></td></tr>
                    <tr><td>������� ������������� ��������� �����:</td><td><html:text property="needVisitOfficeReason" size="100"/></td></tr>
                    <tr><td>��� ��:</td><td><html:text property="fioKI" size="20"/></td></tr>
                    <tr><td>����� ��:</td><td><html:text property="loginKI" size="10"/></td></tr>
                    <tr><td>��� ��:</td><td><html:text property="fioTM" size="10"/></td></tr>
                    <tr><td>����� ��:</td><td><html:text property="loginTM" size="10"/></td></tr>
                    <tr><td>������������� ��� ���������� ������ � �������: ���. ���� (2 �����), ��� (4 �����), ��� (5 ����):</td><td><html:text property="department" size="10"/></td></tr>
                    <tr><td>����� ������:</td><td><html:text property="channel" size="10"/></td></tr>
                    <tr><td>���� ���������� ������ ��������:</td><td><html:text property="agreementDate" size="10"/></td></tr>
                    <tr><td>������ ������ �������:</td><td><html:text property="applicationType" size="10"/></td></tr>
                    <tr><td>����� ����� ������ � ���������:</td><td><html:text property="acctIdType" size="25"/></td></tr>
                    <tr><td>����� ���������� ����� (����� ��������):</td><td><html:text property="cardNum" size="19"/></td></tr>
                    <tr><td>��������� - ����� ������-��������:</td><td><html:text property="businessProcess" size="20"/></td></tr>
                    <tr><td>��������� - ������� ���������� ��������:</td><td><html:text property="insuranceProgram" size="20"/></td></tr>
                    <tr><td>��������� - ������� ��������� ��������� � ������:</td><td><html:checkbox property="includeInsuranceFlag"/></td></tr>
                    <tr><td>��������� - ����� ��������� ������:</td><td><html:text property="insurancePremium" size="20"/></td></tr>
                    <tr><td>��� ��������:</td><td><html:text property="type" size="10"/></td></tr>
                    <tr><td>��� ��������:</td><td><html:text property="productCode" size="10"/></td></tr>
                    <tr><td>��� �����������:</td><td><html:text property="subProductCode" size="10"/></td></tr>
                    <tr><td>����� �������:</td><td><html:text property="loanAmount" size="10"/></td></tr>
                    <tr><td>���� ������� � �������:</td><td><html:text property="loanPeriod" size="10"/></td></tr>
                    <tr><td>������ �������:</td><td><html:text property="currency" size="10"/></td></tr>
                    <tr><td>��� ��������� �������:</td><td><html:text property="paymentType" size="10"/></td></tr>
                    <tr><td>�������:</td><td><html:text property="surName" size="10"/></td></tr>
                    <tr><td>���:</td><td><html:text property="firstName" size="10"/></td></tr>
                    <tr><td>��������:</td><td><html:text property="patrName" size="10"/></td></tr>
                    <tr><td>���� ��������:</td><td><html:text property="birthDay" size="12"/></td></tr>
                    <tr><td>�����������:</td><td><html:text property="citizen" size="10"/></td></tr>
                    <tr><td>����� ���������:</td><td><html:text property="documentSeries" size="4"/></td></tr>
                    <tr><td>����� ���������:</td><td><html:text property="documentNumber" size="6"/></td></tr>
                    <tr><td>���� ������ ���������:</td><td><html:text property="passportIssueDate" size="10"/></td></tr>
                    <tr><td>��� �������������:</td><td><html:text property="passportIssueByCode" size="10"/></td></tr>
                    <tr><td>����� �������� ��������:</td><td><html:text property="passportIssueBy" size="10"/></td></tr>
                    <tr><td>������� � ����������� �������� ������� � ����� ��������:</td><td><html:checkbox property="hasOldPassport"/></td></tr>
                    <tr><td>����� ���������:</td><td><html:text property="oldDocumentSeries" size="4"/></td></tr>
                    <tr><td>����� ���������:</td><td><html:text property="oldDocumentNumber" size="6"/></td></tr>
                    <tr><td>���� ������ ���������:</td><td><html:text property="oldPassportIssueDate" size="10"/></td></tr>
                    <tr><td>����� �������� ��������:</td><td><html:text property="oldPassportIssueBy" size="10"/></td></tr>
                    <tr><td>������� ��������������� �����������.:</td><td><html:checkbox property="peapprovedFlag"/></td></tr>
                </table>
            </c:if>
        </c:if>
        <br/>
        <br/>
        <html:submit property="operation" value="send"/>
    </html:form>
    <a href="${phiz:calculateActionURL(pageContext,'/crm/loanclaim/messages')}">��������� � ������</a>
    </body>
</html:html>
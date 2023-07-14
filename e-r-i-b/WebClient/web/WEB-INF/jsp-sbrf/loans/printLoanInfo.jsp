<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/loans/info/print">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="user" value="${form.user}"/>
<c:set var="loanLink" value="${form.loanLink}" scope="request"/>
<c:set var="loan" value="${loanLink.loan}" scope="request"/>
<c:set var="loanOffice" value="${loan.office}" scope="request"/>
<c:set var="loanParentOffice" value="${phiz:getTerBank(loanOffice.code)}" scope="request"/>

<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">
<br/>
<table width="100%">
    <tr>
        <td>
            �������� ������ ���
        </td>
    </tr>
    <tr>
         <td>
             <c:choose>
                 <c:when test="${!empty loanParentOffice.name}">
                     ${loanParentOffice.name}
                 </c:when>
                 <c:otherwise>
                     ${loanOffice.code.region}
                 </c:otherwise>
             </c:choose>
         </td>
    </tr>
    <tr>
        <td>
            <c:choose>
                <c:when test="${!empty loanOffice.name}">
                    ${loanOffice.name}
                </c:when>
                <c:otherwise>
                    �&nbsp;${loanOffice.code.branch}/${loanOffice.code.office}
                </c:otherwise>
            </c:choose>
        </td>
    </tr>

    <tr>
        <td style="font-size:11pt;text-align:center;padding-top:20mm;font: bold 11pt Arial;">
            �������������� ���������� �� �������
        </td>
    </tr>
    <tr>
        <td>
            ���� ������������: ${phiz:formatDateWithStringMonth(phiz:currentDate())}
        </td>
    </tr>
    <tr>
        <td>
            ��� �������:&nbsp;
            <c:if test="${not empty user}">
                <c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/>
            </c:if>
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
        <td>
            ��������: <bean:write name="loanLink" property="name"/>
        </td>
    </tr>
    <tr>
        <td>
            ��� �������: ${loan.description}
        </td>
    </tr>
    <c:if test="${loan.termDuration != null}">
        <tr>
            <td>
                ���� �������: ${phiz:getFormatedPeriod(loan.termDuration)}
            </td>
        </tr>
    </c:if>
    <tr>
        <td>
            ���� �������� �������: ${phiz:formatDateWithStringMonth(loan.termStart)}
        </td>
    </tr>
    <tr>
        <td>
            ���� ��������: ${phiz:formatDateWithStringMonth(loan.termEnd)}
        </td>
    </tr>
    <tr>
        <td>
            �����: ${phiz:formatAmount(loan.loanAmount)}
        </td>
    </tr>
    <c:if test="${loan.balanceAmount != null}">
        <tr>
            <td>
                ����� ��� ������� ��������� �������: ${phiz:formatAmount(loan.balanceAmount)}
            </td>
        </tr>
    </c:if>
    <c:if test="${loan.rate != null}">
        <tr>
            <td>
                ���������� ������: ${loan.rate}%
            </td>
        </tr>
    </c:if>
    <tr>
        <td>
            ������ �������: ${loanLink.personRole.description}
        </td>
    </tr>
    <tr>
        <td>
            <c:choose>
                <c:when test="${!empty loanOffice.address}">
                    <c:set var="address" value="${loanOffice.address}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="address" value="� ${loanOffice.code.branch}/${loanOffice.code.office}"/>
                </c:otherwise>
            </c:choose>
            ����� ����������: <c:out value="${address}"/>
        </td>
    </tr>
    <tr>
        <td>
            ����� ��������: ${loan.agreementNumber}
        </td>
    </tr>
    <tr>
        <td>
            ������� ����: ${loanLink.number}
        </td>
    </tr>
    <tr>
        <td>��� ��������:
            <c:set var="client" value="${loan.borrower}"/>
            <c:if test="${not empty client}">
                <c:out value="${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}"/>
            </c:if>
        </td>
    </tr>

</table>
</tiles:put>
</tiles:insert>
</html:form>
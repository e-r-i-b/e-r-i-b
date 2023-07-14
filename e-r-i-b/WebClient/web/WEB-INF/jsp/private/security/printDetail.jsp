<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<tiles:importAttribute/>
<html:form action="/private/security/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="user" value="${form.user}"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="securityLink" value="${form.link}" scope="request"/>
    <c:set var="securityAccount" value="${securityLink.securityAccount}"/>
    <c:set var="periodExpired" value="${securityAccount.termStartDt <= phiz:currentDate()}" />

    <tiles:insert definition="printDoc" flush="false">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>

        <tiles:put name="data" type="string">
            <table style="width:800px; margin-left: 130px;">
                <tr>
                    <td style="font-size:11pt; text-align:center; padding-top:20px; line-height: 1.5">
                        ���������� �� ��������������� ����������� �� ������������ <br/> ��� "�������� ������" <c:out value="${securityAccount.serialNumber}"/>
                    </td>
                </tr>
                <tr>
                    <td style="padding:20px;">
                        <table class="printSecAccInfo">
                            <tr>
                                <td class="align-right">������������ ������ ������:</td>
                                <td>
                                    <bean:message key="security.name" bundle="securitiesBundle"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">������������� ������ ������:</td>
                                <td>
                                    <c:out value="${securityAccount.serialNumber}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">������ ������, ������������ ������������:</td>
                                <td>
                                    <c:set var="securityAmount" value="${phiz:getSecurityAmount(securityAccount)}"/>
                                    ${phiz:formatAmount(securityAmount)}
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">��������� �������, ��������� ����������:</td>
                                <td>
                                    <c:out value="${securityAccount.issuerBankId}"/> <c:out value="${securityAccount.issuerBankName}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">���� ������ ������ ������:</td>
                                <td>
                                    ${phiz:�alendarToString(securityAccount.composeDt)}
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">���� ���������(����):</td>
                                <td>
                                     <c:if test="${securityAccount.composeDt < securityAccount.termStartDt}">
                                        ${phiz:getPeriodInDays(securityAccount.composeDt, securityAccount.termStartDt)}
                                     </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">���� ������������� ����� �� �����������:</td>
                                <td>
                                    ${phiz:�alendarToString(securityAccount.termStartDt)}
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">������ �������� �� ����������� �������:</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${periodExpired == true}">
                                            0% �������
                                        </c:when>
                                        <c:otherwise>
                                            ${securityAccount.incomeRate}% �������
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">����� ������������� ���������:</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${periodExpired == true}">
                                            0
                                        </c:when>
                                        <c:otherwise>
                                            ${phiz:formatAmount(securityAccount.incomeAmt)}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right">����� �������� �����������:</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${securityLink.onStorageInBank}">
                                            � �����
                                        </c:when>
                                        <c:otherwise>
                                            �� �����
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>

                            <c:set var="registrarData">
                                <c:if test="${not empty securityAccount.docNum and not empty securityAccount.docDt}">
                                    <tr>
                                        <td class="align-right field">������� �������������� ��������:</td>
                                        <td>�${securityAccount.docNum} �� ${phiz:�alendarToString(securityAccount.docDt)}</td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty securityAccount.bankName}">
                                    <tr>
                                        <td class="align-right field">��������� �������, ���������� ���������� �� ��������:</td>
                                        <td><c:out value="${securityAccount.bankId}"/> <c:out value="${securityAccount.bankName}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty securityAccount.bankPostAddr}">
                                    <tr>
                                        <td class="align-right ">����� �������, ���������� ���������� �� ��������:</td>
                                        <td><c:out value="${securityAccount.bankPostAddr}"/></td>
                                    </tr>
                                </c:if>
                            </c:set>
                            <c:if test="${securityLink.onStorageInBank && not empty registrarData}">
                                <tr><td style="padding-top: 10px"></td></tr>
                                <tr><td colspan="2" style="border-top: 1px dotted #000000">������������� ��������:</td> </tr>
                                ${registrarData}
                             </c:if>
                            <tr><td style="padding-top: 10px"></td></tr>
                            <tr><td colspan="2" style="border-top: 1px dotted #000000">������ ���������:</td> </tr>
                            <tr>
                                <td class="align-right labelAbstractPrint">
                                    ��� �������:&nbsp;
                                </td>
                                <td>
                                    <c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right labelAbstractPrint">
                                    ���� ���������� ���������:&nbsp;
                                </td>
                                <td>
                                    <script type="text/javascript">
                                        var date = new Date;
                                        var day = date.getDate() + "";
                                        var month = date.getMonth() + 1 + "";
                                        var year = date.getFullYear() + "";
                                        document.write(day + "." + month + "." + year);
                                    </script>
                                </td>
                            </tr>
                            <tr>
                                <td class="align-right labelAbstractPrint ">
                                    <bean:message bundle="commonBundle" key="text.SBOL.executor"/>
                                </td>
                                <td>
                                    <bean:message bundle="commonBundle" key="text.SBOL.formedIn"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
             </table>
        </tiles:put>
    </tiles:insert>
</html:form>

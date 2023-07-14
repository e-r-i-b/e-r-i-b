<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:insert definition="personsPrint">
<tiles:put name="data" type="string">
<body onLoad="setDefaultValue();" Language="JavaScript">
<c:set var="action" value="${phiz:calculateActionURL(pageContext,'')}"/>
<html:form action="/private/payments/print-edit-claim">
<c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
<c:if test="${phiz:isInstance(frm.document, 'com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription') and frm.document.longOffer}">
    <jsp:include page="./subscriptions/support-payment-${frm.metadata.name}.jsp" flush="false" />
</c:if>
<c:set var="person" value="${frm.activePerson}"/>
<c:set var="document" value="${frm.document}"/>
<c:set var="originalData" value="${frm.originalData}"/>
<c:set var="changedData" value="${frm.changedData}"/>

<table cellpadding="0" cellspacing="0" width="172mm" border="1px"
       style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:15mm;table-layout:fixed;">
    <tr>
        <td style="width:86mm">
            ����� ���/���
        </td>
        <td style="width:86mm">
            <c:set var="employeeDepartment" value="${phiz:getDepartmentById(frm.fields.employeeDepartmentId)}"/>
            <c:set var="OSB" value="${phiz:getOSB(employeeDepartment)}"/>
            <c:if test="${not empty OSB}">
                <c:out value="${OSB.code.branch}"/>/
            </c:if>
            <c:if test="${employeeDepartment!=null}">
                <c:out value="${employeeDepartment.code.office}"/>
            </c:if>
        </td>
    </tr>
</table>

<table cellpadding="0" cellspacing="0" width="172mm"
       style="margin-left:15mm;margin-right:12mm;margin-top:10mm;margin-bottom:15mm;table-layout:fixed;">
    <col style="width:172mm">
    <tr>
        <td>
            <table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
                <tr>
                    <td align="center">
                        <b><c:out value="��������� �� ${printActionType} ������ �����������"/></b>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        �, <c:out value="${person.surName}"/> <c:out value="${person.firstName}"/> <c:out value="${person.patrName}"/>, �����
                        ���� <c:out value="${printActionDescription}"/> �������������� ������ "<c:out value="${originalData['auto-sub-friendy-name']}"/>",
                        ������������� � ������������ ������������� �������� � �����
                        � <c:out value="${phiz:getCutCardNumber(originalData['from-resource'])}"/>  <c:if test="${document.type.simpleName == 'ExternalCardsTransferToOurBankLongOffer'}">� ������ <c:out value="${document.receiverName}"/></c:if>.
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table cellspacing="0" class="textDoc" width="100%">
                            <c:if test="${isAutoSubscription}">
                                <tr>
                                    <td class="textPadding" width="50%">
                                        ��� ����������:
                                    </td>
                                    <td class="textPadding" width="50%">
                                        <c:out value="${document.receiverINN}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="textPadding">
                                        ����.���� ����������:
                                    </td>
                                    <td class="textPadding">
                                        <c:out value="${document.receiverCorAccount}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="textPadding">
                                        ��� ����� ����������:
                                    </td>
                                    <td class="textPadding">
                                        <c:out value="${document.receiverBIC}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="textPadding">
                                        ��������� ���� ����������:
                                    </td>
                                    <td class="textPadding">
                                        <c:out value="${document.receiverAccount}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="textPadding">
                                        ��� ����������:
                                    </td>
                                    <td class="textPadding">
                                        <c:out value="${document.receiverKPP}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><br></td>
                                </tr>
                                <c:set var="extendedFields" value="${document.extendedFields}"/>
                                <c:forEach items="${extendedFields}" var="field">
                                    <c:if test="${field.visible}">
                                        <tr>
                                            <td>
                                                <c:out value="${field.name}"/>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${field.type == 'set'}">
                                                        <c:out value="${fn:replace(field.value,'@',', ')}"/>
                                                    </c:when>
                                                    <c:when test="${field.type == 'money'}">
                                                        <c:out value="${field.value}"/>&nbsp;���.
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${field.value}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${isAutoTransfer}">
                                <tr>
                                    <td class="textPadding">
                                        ��������� ���� ����������:
                                    </td>
                                    <td class="textPadding">
                                        <c:out value="${phiz:getCutCardNumber(document.receiverAccount)}"/>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td><br></td>
                            </tr>
                            <tr>
                                <c:set var="nextPayDate" value="${originalData['auto-sub-next-pay-date']}"/>
                                <c:choose>
                                    <c:when test="${document.attributes['auto-sub-type'] == 'ALWAYS'}">
                                        <td class="textPadding">
                                            ���� ���������� �������:
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="textPadding">
                                            ��������� ���� ������ �����:
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="textPadding">
                                    <c:if test="${!empty nextPayDate}">
                                        <bean:write name="nextPayDate" property="time" format="dd.MM.yyyy"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    ������ ���������� �����������:
                                </td>
                                <td>
                                    <bean:message key="${originalData['long-offer-event-type']}" bundle="autopaymentsBundle"/>
                                </td>
                            </tr>
                            <tr>
                                <c:choose>
                                    <c:when test="${document.attributes['auto-sub-type'] == 'ALWAYS'}">
                                        <c:set var="summ" value="${phiz:formatAmount(originalData['destination-amount'])}"/>
                                        <c:set var="summDescription" value="������������� ����� �������:"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="summ" value="${phiz:formatAmount(originalData['auto-sub-invoice-max-decimal'])}"/>
                                        <c:set var="summDescription" value="������������ ������ �������:"/>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${not empty summ}">
                                    <td class="textPadding">
                                        <c:out value="${summDescription}"/>
                                    </td>
                                    <td>
                                        <c:out value="${summ}"/>
                                    </td>
                                </c:if>
                            </tr>
                            <tr>
                                <td><br></td>
                            </tr>
                            <tr><td colspan="2">����� �������� �������� ���������� ����������� �� ���������:</td></tr>
                                <c:if test="${not empty changedData['from-resource']}">
                                    <tr>
                                    <td>���� ��������:</td>
                                    <td><c:out value="${phiz:getCutCardNumber(document.chargeOffCard)}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty changedData['auto-sub-friendy-name']}">
                                    <tr>
                                    <td>��������:</td>
                                    <td><c:out value="${document.friendlyName}"/></td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty changedData['long-offer-event-type']}">
                                    <tr>
                                    <td>������ ���������� �����������:</td>
                                    <td>${document.startExecutionDetail}</td>
                                    </tr>
                                </c:if>
                                <c:choose>
                                    <c:when test="${document.attributes['auto-sub-type'] == 'ALWAYS'}">
                                        <c:if test="${not empty changedData['auto-sub-next-pay-date']}">
                                            <c:set var="nextPayDate" value="${document.nextPayDate}"/>
                                            <tr>
                                            <td>���� ���������� �������:</td>
                                            <td><bean:write name="nextPayDate" property="time" format="dd.MM.yyyy"/></td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${not empty changedData['destination-amount']}">
                                            <c:set var="summ" value="${phiz:formatAmount(document.exactAmount)}"/>
                                            <tr>
                                            <td>������������� ����� �������:</td>
                                            <td><c:out value="${summ}"/></td>
                                            </tr>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${not empty changedData['auto-sub-next-pay-date']}">
                                            <c:set var="nextPayDate" value="${document.nextPayDate}"/>
                                            <tr>
                                            <td>��������� ���� ������ �����:</td>
                                            <td><bean:write name="nextPayDate" property="time" format="dd.MM.yyyy"/></td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${not empty changedData['auto-sub-invoice-max-decimal']}">
                                            <c:set var="summ" value="${phiz:formatAmount(document.maxSumWritePerMonth)}"/>
                                            <tr>
                                            <td>
                                                ������������ ������ �������:
                                            </td>
                                            <td><c:out value="${summ}"/></td>
                                            </tr>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        � ��������������� � �������� � "��������� �������������� ������ "����������" (��� ������ ����� ��� � ���� �����)".
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                    </td>
                </tr>
                <tr>
                    <table cellspacing="0" class="textDoc" width="100%">
                        <tr>
                            <td align="left">
                                <b>����</b> &nbsp;<fmt:formatDate value="${phiz:currentDate().time}"
                                                                  pattern="dd.MM.yyyy"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="left">
                                <b>�������.</b>
                            </td>
                        </tr>
                    </table>
                </tr>
            <tr><td><br></td></tr>
            </table>
            </html:form>
</body>
</tiles:put>
</tiles:insert>

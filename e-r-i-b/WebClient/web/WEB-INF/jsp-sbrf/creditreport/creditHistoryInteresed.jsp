<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    historyRequests - ������� ��������� �������
    historyRequestsHalfYear - ������� ��������� ������� �� ��������� ��� ����
--%>
<tiles:importAttribute/>
<script type="text/javascript">
    function openDetail(itemDiv)
    {
        var selectedClass = 'cred-hist-check-item_selected';
        if (itemDiv.getAttribute("class").indexOf(selectedClass)!=-1)
            itemDiv.setAttribute('class', 'credit-history-check-item');
        else
            itemDiv.setAttribute('class', 'credit-history-check-item cred-hist-check-item_selected');
    }
    function showAllHistoryRequests(allRequestsDiv)
    {
        var selectedAllClass = 'showAllHistoryRequest_selected';
        var allDiv = document.getElementById("showAllHistoryRequest");
        allDiv.setAttribute("class", "showAllHistoryRequest_selected");
    }
</script>
<c:if test="${historyRequestsCount != 0 || historyRequestsHalfYearCount!=0}">
    <div class="credit-history-who-was-interesed cred-hist_who-asked">
        <div class="credit-history-who-was-interesed-title cred-hist__title-question">
            <div class="float">��� ���������� ��� ��������� �������</div>
            <div class="float">
                <tiles:insert definition="hintTemplate" flush="false">
                    <tiles:put name="color" value="whiteHint"/>
                    <tiles:put name="data">
                        ���������� � ��������, ������� ������ ����� � ������ ����������� ��� �������� ����� ��������� �������.
                    </tiles:put>
                </tiles:insert>
            </div>
        </div>
        <div class="clear"></div>
        <c:choose>
            <c:when test="${historyRequestsHalfYearCount!=0}">
                <p class="credit-history-who-was-interesed-state">����� ���� <span>${historyRequestsCount+historyRequestsHalfYearCount}</span> ��������, �� ��� <span>${historyRequestsHalfYearCount}</span> �� ��������� �������</p>
            </c:when>
            <c:otherwise>
                <p class="credit-history-who-was-interesed-state">�� ��������� ������� ����� �� ���������� ���� ��������� �������. ��� ����, ����� ���������� ��� ������� ����� ��������� �������, ������� �� ������ ���� ������� ���� ��������� �������.</p>
            </c:otherwise>
        </c:choose>


        <c:forEach var="historyRequest" items="${historyRequestsHalfYear}">
            <c:if test="${not (year eq historyRequest.yearOfDateRequest)}">
                <c:set var="year" value="${historyRequest.yearOfDateRequest}"/>
                <div class="cred-hist-who-asked-year">${year}</div>
            </c:if>
            <div class="credit-history-check-item">
                <div class="credit-history-check-item-date">${historyRequest.dateRequestDDmmmYYYY}</div>
                <div class="credit-history-check-item-content">
                    <a class="credit-history-check-item-content-title" onclick="openDetail(this.parentElement.parentElement)">${historyRequest.reasonForEnquiry}</a>
                    <p class="credit-history-check-item-content-desc">${historyRequest.creditName} ${phiz:formatDecimalToAmountRound(historyRequest.creditLimit.value,true)} ${historyRequest.creditLimit.currency} ${historyRequest.subscriberName}</p>
                </div>
                <div class="cred-hist-check-item-opened">
                    <div class="cred-hist-item-opened-title">����� ����������</div>
                    <table class="cred-hist-item-opened-table">
                        <tr class="cred-hist-item-opened-table-line">
                            <td class="cred-hist-who-asked-opened-table-col1">���� �������</td>
                            <td class="bold">${historyRequest.enquiryDateFormat}</td>
                        </tr>
                        <tr class="cred-hist-item-opened-table-line">
                            <td>�������</td>
                            <td class="bold">${historyRequest.reasonForEnquiry}</td>
                        </tr>
                        <tr class="cred-hist-item-opened-table-line">
                            <td>��� �������</td>
                            <td class="bold">${historyRequest.financeType}</td>
                        </tr>
                        <tr class="cred-hist-item-opened-table-line">
                            <td>������/ ����� ������������</td>
                            <td class="bold">${phiz:formatDecimalToAmountRound(historyRequest.creditLimit.value, true)} ${historyRequest.creditLimit.currency}</td>
                        </tr>
                        <tr class="cred-hist-item-opened-table-line">
                            <td>���� ������������</td>
                            <td class="bold">${historyRequest.durationOfAgreement}</td>
                        </tr>
                    </table>
                    <div class="cred-hist-item-opened-title">�������� �������</div>

                    <table class="cred-hist-item-opened-table">
                        <tr class="cred-hist-item-opened-table-line">
                            <td class="cred-hist-who-asked-opened-table-col1">������������</td>
                            <td class="bold">${historyRequest.subscriberName}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </c:forEach>
        <div id="showAllHistoryRequest" class="showAllHistoryRequest">
            <c:forEach var="historyRequest" items="${historyRequests}">
                <c:if test="${not (year eq historyRequest.yearOfDateRequest)}">
                    <c:set var="year" value="${historyRequest.yearOfDateRequest}"/>
                    <div class="cred-hist-who-asked-year">${year}</div>
                </c:if>
                <div class="credit-history-check-item">
                    <div class="credit-history-check-item-date">${historyRequest.dateRequestDDmmmYYYY}</div>
                    <div class="credit-history-check-item-content">
                        <a class="credit-history-check-item-content-title" onclick="openDetail(this.parentElement.parentElement)">${historyRequest.reasonForEnquiry}</a>
                        <p class="credit-history-check-item-content-desc">${historyRequest.creditName} ${phiz:formatDecimalToAmountRound(historyRequest.creditLimit.value, true)} ${historyRequest.creditLimit.currency} ${historyRequest.subscriberName}</p>
                    </div>
                    <div class="cred-hist-check-item-opened">
                        <div class="cred-hist-item-opened-title">����� ����������</div>
                        <table class="cred-hist-item-opened-table">
                            <tr class="cred-hist-item-opened-table-line">
                                <td class="cred-hist-who-asked-opened-table-col1">���� �������</td>
                                <td class="bold">${historyRequest.enquiryDateFormat}</td>
                            </tr>
                            <tr class="cred-hist-item-opened-table-line">
                                <td>�������</td>
                                <td class="bold">${historyRequest.reasonForEnquiry}</td>
                            </tr>
                            <tr class="cred-hist-item-opened-table-line">
                                <td>��� �������</td>
                                <td class="bold">${historyRequest.financeType}</td>
                            </tr>
                            <tr class="cred-hist-item-opened-table-line">
                                <td>������/ ����� ������������</td>
                                <td class="bold">${phiz:formatDecimalToAmountRound(historyRequest.creditLimit.value, true)} ${historyRequest.creditLimit.currency}</td>
                            </tr>
                            <tr class="cred-hist-item-opened-table-line">
                                <td>���� ������������</td>
                                <td class="bold">${historyRequest.durationOfAgreement}</td>
                            </tr>
                        </table>
                        <div class="cred-hist-item-opened-title">�������� �������</div>

                        <table class="cred-hist-item-opened-table">
                            <tr class="cred-hist-item-opened-table-line">
                                <td class="cred-hist-who-asked-opened-table-col1">������������</td>
                                <td class="bold">${historyRequest.subscriberName}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${historyRequestsCount != 0}">
            <a onclick="showAllHistoryRequests(this)" class="credit-history-check-show-all">��� ������� ���� ��������� �������</a>
        </c:if>
    </div>
</c:if>

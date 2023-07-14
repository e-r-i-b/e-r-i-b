<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%--
    updated - ���� ���������� ����������
    mustUpdated - ������������� ���������� �������
    rating - ������� � ���� �����
    providerId - ���������
    waitingNew - ����� ���������
    bkiError - ����� �� ������� ��-�� ������
--%>
<tiles:importAttribute/>
<script type="text/javascript">
    function setMustUpdated()
    {
        ajaxQuery("", "${phiz:calculateActionURL(pageContext, '/private/async/credit/history/mustUpdate')}", function(data){}, null, true);
        $(".credit-history__desc-tooltip").hide();
        return true;
    }
</script>
<div class="credit-history__status-title">
    ��������� ${phiz:formatDateWithMonthString(updated)}
    <c:if test="${not bkiError and (mustUpdated or waitingNew)}">
        <div class="credit-history__desc-tooltip">
            <c:choose>
                <c:when test="${waitingNew}">
                    �� ������� ���� ����������� ��������� �������. ����������, ���������
                </c:when>
                <c:otherwise>
                    ������, ���������� ��������.
                    <phiz:link action="/private/payments/creditReportPayment/edit">
                        <phiz:param name="recipient" value="${providerId}"/>
                        <c:out value="��������?"/>
                    </phiz:link>
                </c:otherwise>
            </c:choose>
            <div class="credit-history__desc-tooltip-close" onclick="setMustUpdated();"></div>
            <div class="credit-history__desc-tooltip-row"></div>
        </div>
    </c:if>
</div>
<c:set var="ratingWord" value=""/>
<c:choose>
    <c:when test="${rating == 1}">
        <c:set var="ratingWord" value="����� ������ ��������� �������"/>
    </c:when>
    <c:when test="${rating == 2}">
        <c:set var="ratingWord" value="������ ��������� �������"/>
    </c:when>
    <c:when test="${rating == 3}">
        <c:set var="ratingWord" value="������� ��������� �������"/>
    </c:when>
    <c:when test="${rating == 4}">
        <c:set var="ratingWord" value="����� ������� ��������� �������"/>
    </c:when>
    <c:when test="${rating == 5}">
        <c:set var="ratingWord" value="�������� ��������� �������"/>
    </c:when>
</c:choose>
<div class="credit-history__battery-line">
    <div class="credit-history__battery credit-history_battery-power${rating}"></div>
    <div class="credit-history__battery-content">
        <div class="credit-history__battery-content-line"></div>
        <div class="credit-history__battery-content-title">
            ��� ��������� �������
            <tiles:insert definition="hintTemplate" flush="false">
                <tiles:put name="color" value="whiteHint"/>
                <tiles:put name="data">
                    ��������� ������� ���������� ��������� ������ ���� ��������� ������� � ��������� �� �� ���������.
                    ���� �� ������� ��� ������� �� �������� �������, �� ������ ������� ����� �������� �������� � ��������
                    ����� ��������� �������, �� ��� ��������� ������� ����� ������� �������� ���� ��������, ���
                    ��������� ������� ������ �� ����������� ��������� �������, �� �� �������� ������������ ���������
                    ��� �������� ������� � �������������� �������.
                </tiles:put>
            </tiles:insert>
        </div>
        <span class="credit-history__battery-content-number credit-history__battery-content-number${rating}">${rating}</span>
        <div class="credit-history__battery-content-n-text">${ratingWord}</div>
    </div>
</div>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<%--
    report - ÓÚ˜ÂÚ
--%>
<tiles:importAttribute/>
<script type="text/javascript">
    function showBlock()
    {
        $(".closedCredits").toggle();
        return true;
    }
    function selectCredit(creditId)
    {
        var detailUrl = "${phiz:calculateActionURL(pageContext,'/private/credit/report')}";
        window.location = detailUrl + "?creditId=" + creditId;
        return true;
    }
</script>
<div class="credit-history-closed">
    <a href="#?" class="credit-history-closed__title" onclick="showBlock();">«‡Í˚Ú˚Â ÍÂ‰ËÚ˚</a>
    <p class="credit-history-active__current-state">${report.closedCount}</p>
    <div class="closedCredits">
        <c:if test="${report.closedCreditsCount != 0}">
            <table class="credit-history-items-table cred-hist-closed-credits">
                <thead>
                <tr>
                    <td class="cred-hist-table-col-first"> –≈ƒ»“Œ–</td>
                    <td class="cred-hist-closed-col-cred"> –≈ƒ»“</td>
                    <td class="cred-hist-closed-col-time">—–Œ   –≈ƒ»“¿</td>
                    <td class="cred-hist-closed-col-reason">œ–»◊»Õ¿ «¿ –€“»ﬂ</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${report.closedCredits}" var="credit">
                    <tr>
                        <td class="cred-hist-table-col-first"><a onclick="selectCredit(${credit.id});" class="underlined-link"><span>${credit.bankName}</span></a></td>
                        <td class="cred-hist-closed-col-cred">${credit.amount}</td>
                        <td class="cred-hist-closed-col-time">${credit.duration}</td>
                        <td class="cred-hist-closed-col-reason">${credit.reasonForClosure}<br />${phiz:formatDateWithMonthString(credit.closedDate)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${report.closedCardsCount != 0}">
            <table class="credit-history-items-table cred-hist-closed-credits">
                <thead>
                <tr>
                    <td class="cred-hist-table-col-first"> –≈ƒ»“Õ¿ﬂ  ¿–“¿</td>
                    <td class="cred-hist-closed-col-cred cards"></td>
                    <td class="cred-hist-closed-col-time cards">À»Ã»“</td>
                    <td class="cred-hist-closed-col-reason">œ–»◊»Õ¿ «¿ –€“»ﬂ</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${report.closedCards}" var="card">
                    <tr>
                        <td class="cred-hist-table-col-first"><a onclick="selectCredit(${card.id});"class="underlined-link"><span>${card.bankName}</span></a></td>
                        <td class="cred-hist-closed-col-cred cards"></td>
                        <td class="cred-hist-closed-col-time cards">${card.creditLimit}</td>
                        <td class="cred-hist-closed-col-reason">${card.reasonForClosure}<br />${phiz:formatDateWithMonthString(card.closedDate)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <div class="credit-history-in-total cred-hist-closed-in-total">
            <table class="credit-history-in-total-table">
                <c:forEach items="${report.totalClosedCredits}" var="payment">
                    <c:if test="${payment != ''}">
                        <tr>
                            <td><div class="credit-history-in-total-title">»ÚÓ„Ó ‚˚ÔÎ‡Ú ÔÓ ÍÂ‰ËÚ‡Ï:</div></td>
                            <td class="cred-hist-in-total-values"><div class="credit-history-in-total-value">${payment}</div></td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

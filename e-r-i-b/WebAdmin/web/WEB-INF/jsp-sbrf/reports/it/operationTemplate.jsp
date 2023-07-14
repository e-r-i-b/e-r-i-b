<c:forEach var="tb" items="${operations}" varStatus="numString">
    <tr class="ListLine0">
        <c:choose>
            <c:when test="${numString.count == 1}">
                <td class="listItem"><span class='bold'>${numType}</span></td>
                <td class="listItem"><span class='bold'><bean:message key="label.proactive.${catName}" bundle="${bundle}"/></span></td>
            </c:when>
            <c:otherwise><td class="listItem" colspan="2">&nbsp;</td></c:otherwise>
        </c:choose>
        <c:if test="${allBank == false}"><td class="listItem">${tb.key}</td></c:if>
        <td class="listItem"><c:out value="${tb.value[catName]['Count']}" default="0"/></td>
        <td class="listItem"><c:out value="${tb.value[catName]['PercentError']}" default="0"/></td>
        <td class="listItem"><c:out value="${tb.value[catName]['SmallTimeOperation']}" default="0"/></td>
        <td class="listItem"><c:out value="${tb.value[catName]['AverageTimeOperation']}" default="0"/></td>
        <td class="listItem"><c:out value="${tb.value[catName]['LongTimeOperation']}" default="0"/></td>
    </tr>
</c:forEach>
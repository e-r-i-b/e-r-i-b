<tr class="ListLine0"><td class="listItem"><span class='bold'>${numType}</span></td>
<td class="listItem" colspan="7"><span class='bold'><bean:message key="label.proactive.${catName}" bundle="${bundle}"/></span></td></tr>
<c:forTokens  var="name" items="${operationsName}" delims="," varStatus="numOper">
    <c:forEach var="tb" items="${operations}" varStatus="numString">
        <tr class="ListLine0">
            <c:choose>
                <c:when test="${numString.count == 1}"><td class="listItem">${numType}${numOper.count}</td>
                    <td class="listItem"><bean:message key="label.proactive.${name}" bundle="${bundle}"/></td>
                </c:when>
            <c:otherwise><td class="listItem" colspan="2">&nbsp;</td></c:otherwise></c:choose>
            <c:if test="${allBank == false}"><td class="listItem">${tb.key}</td></c:if>
            <td class="listItem"><c:out value="${tb.value[name]['Count']}" default="0"/></td>
            <td class="listItem"><c:out value="${tb.value[name]['PercentError']}" default="0"/></td>
            <td class="listItem"><c:out value="${tb.value[name]['SmallTimeOperation']}" default="0"/></td>
            <td class="listItem"><c:out value="${tb.value[name]['AverageTimeOperation']}" default="0"/></td>
            <td class="listItem"><c:out value="${tb.value[name]['LongTimeOperation']}" default="0"/></td>
        </tr>
    </c:forEach>
</c:forTokens>

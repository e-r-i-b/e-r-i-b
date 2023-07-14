<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:insert definition="printAutoSubscriptions">
<html>
	<body onload="javascript:print()">
    <c:catch var="errorJSP">
		<table style="width:100%;height:400px" cellspacing="0" cellpadding="0">
			<tr height="20px"><td>&nbsp;<td/></tr>
			<tr><td align="center" valign="top">
				<table cellspacing="0" cellpadding="0" border="1px" width="100%" class="tbl">
					<tr align="center" class="titleDoc">
						<td width="10%">Дата оформления</td>
						<td width="20%">Название</td>
						<td width="20%">Получатель платежа</td>
                        <td width="10%">№ карты</td>
                        <td width="20%">Периодичность</td>
                        <td width="10%">Сумма платежа</td>
                        <td width="10%">Статус</td>
					</tr>
				<logic:iterate id="metadataBean" name="PrintAutoSubscriptionForm" property="data">
					<c:set var="form" value="${PrintAutoSubscriptionForm}"/>
                    <tr align="center" class="listItem">
                        <c:set var="date"><fmt:formatDate value="${metadataBean.startDate.time}" pattern="dd.MM.yyyy HH:mm"/></c:set>
                        <td><c:out value=""/>${date}</td>
                        <td><c:out value="${metadataBean.friendlyName}"/></td>
                        <td><c:out value="${phiz:getReceiverNameForView(metadataBean.value)}"/></td>
                        <td><c:out value="${phiz:getCutCardNumber(metadataBean.cardLink.number)}"/></td>
                        <td>
                            <c:if test="${metadataBean.nextPayDate !=null}">
                                <c:set var="nextPayDate">
                                    <fmt:formatDate value="${metadataBean.nextPayDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                                </c:set>
                                <c:choose>
                                <c:when test="${metadataBean.sumType=='BY_BILLING'}">
                                    <c:out value="${nextPayDate}"/>
                                </c:when>
                                <c:when test="${metadataBean.sumType=='FIXED_SUMMA_IN_RECIP_CURR' && metadataBean.executionEventType!='ON_REMAIND'}">
                                    <c:choose>
                                        <c:when test="${metadataBean.value.executionEventType == 'ONCE_IN_WEEK'}">
                                            <c:out value="еженедельно"/>
                                        </c:when>
                                        <c:when test="${metadataBean.value.executionEventType == 'ONCE_IN_MONTH'}">
                                            <c:out value="ежемесячно"/>
                                        </c:when>
                                        <c:when test="${metadataBean.value.executionEventType == 'ONCE_IN_QUARTER'}">
                                            <c:out value="ежеквартально"/>
                                        </c:when>
                                        <c:when test="${metadataBean.value.executionEventType == 'ONCE_IN_YEAR'}">
                                            <c:out value="ежегодно"/>
                                        </c:when>
                                    </c:choose>
                                    <c:out value="${metadataBean.nextPayDate.time.date}го числа"/>
                                </c:when>
                                <c:when test="${metadataBean.sumType=='RUR_SUMMA'}">
                                    <c:out value="${metadataBean.startExecutionDetail}"/>
                                </c:when>
                                </c:choose>
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${metadataBean.sumType=='BY_BILLING'}">
                                    <c:out value="0,00 ${phiz:getCurrencySign(metadataBean.amount.currency.code)}"/>     
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${phiz:formatAmount(metadataBean.amount)}"/>        
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><c:out value="${metadataBean.autoPayStatusType.description}"/></td>
                    </tr>
				</logic:iterate>
				</table>
			</td></tr>
		</table>
	</body>
    </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>
</html>
</tiles:insert>    

<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/reports/operations/tb/report">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set value="reportsBundle" var="bundle"/>
    <tiles:insert definition="reportsList">

        <tiles:put name="mainmenu" value="ReportsAndMonitoring"/>
        <tiles:put name="submenu" type="string" value="BusinessReport"/>
        <tiles:put name="pageTitle" type="string"><bean:message key="label.countOperationTB" bundle="${bundle}"/></tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="date_report"><bean:message key="label.periodFromTo" bundle="${bundle}" arg0="${phiz:dateToString(form.reportStartDate.time)}" arg1="${phiz:dateToString(form.reportEndDate.time)}"/></c:set>
            <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="text"><bean:message key="label.countOperationTB" bundle="${bundle}"/> <c:out value="${date_report}"/></tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage"><bean:message key="label.empty" bundle="${bundle}"/></tiles:put>

                    <tiles:put name="data">
                        <tr class="tblInfHeader">
                            <th class="titleTable"><bean:message key="label.numberTB" bundle="${bundle}"/>,<bean:message key="label.nameTB" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.typesOperations" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.currencyOperation" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.countOperation" bundle="${bundle}"/></th>
                            <th class="titleTable"><bean:message key="label.ammountOperation" bundle="${bundle}"/></th>
                        </tr>

                        <c:set var="operationsTB" value="${phiz:getOperationsReportByTB(form.data)}" />

                        <c:forEach var="tb" items="${operationsTB}">

                            <%-- считаем кол-во €чеек,  кот. необходимо объединить дл€ вывода номера и названи€ тб --%>
                            <c:set var="rowspanTb" value="45"/>
                            <c:forEach var="t" items="${tb.value}">

                                <c:set var="index" value="-1"/>
                                <c:forEach var="t2" items="${t.value}">
                                     <c:set var="index" value="${index + 1}"/>
                                </c:forEach>
                                <c:if test="${index < 0}"><c:set var="index" value="0"/></c:if>
                                <c:set var="rowspanTb" value="${rowspanTb + index}"/>
                            </c:forEach>

                            <tr>
                                <td rowspan="${rowspanTb}"><c:out value="${tb.key}"/><c:out value="${tb.value.size}"/></td>
                                <td colspan="4"><bean:message key="label.informationOperation" bundle="${bundle}"/></td>
                            </tr>
                            <tr>
                            <c:set var="operationsName"
                                    value="entryInSystem,clientProfil,confirmation,titlePage,cardsReport,cardsReportForLast10Operation,stopCard,stopAccount,operationsHistory,standingOrderReport,countStandingOrder,cancelStandingOrder,createTemplates,mobileTemplateView,mobileTemplateCreate,accountDEPO,creditInfo,metalAccountInfo,printCheck,infoFromPFRF" />

                             <c:forTokens items="${operationsName}" delims="," var="name">
                                 <c:choose >
                                     <c:when   test="${empty tb.value[name]}">
                                         <tr>
                                             <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                             <td>&nbsp;</td>
                                             <td><c:out value="0"/></td>
                                             <td>&nbsp;</td>
                                         </tr>
                                     </c:when>
                                     <c:otherwise>
                                         <c:forEach var="operations" items="${tb.value[name]}">
                                             <tr>
                                                 <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                                 <td><c:out value="${operations['Currency']}" default="${separator}"/>&nbsp;</td>
                                                 <td><c:out value="${operations['Count']}" default="0"/>&nbsp;</td>
                                                 <td><c:out value="${operations['Amount']}" default="${separator}"/>&nbsp;</td>
                                             </tr>
                                         </c:forEach>
                                     </c:otherwise>
                                 </c:choose>
                             </c:forTokens>

                            <tr>
                                <td colspan="4"><bean:message key="label.conversion" bundle="${bundle}"/></td>
                            </tr>

                            <c:set var="operationsName"
                                    value="fromCardToCard,fromCardToAccount,fromAccountToCard,fromAccountToAccount,toForeignAccount" />

                            <c:forTokens items="${operationsName}" delims="," var="name">
                                <c:choose >
                                    <c:when   test="${empty tb.value[name]}">
                                        <tr>
                                            <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                            <td>&nbsp;</td>
                                            <td><c:out value="0"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="operations" items="${tb.value[name]}">
                                            <tr>
                                                <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                                <td><c:out value="${operations['Currency']}"/>&nbsp;</td>
                                                <td><c:out value="${operations['Count']}" default="0"/>&nbsp;</td>
                                                <td><c:out value="${operations['Amount']}" default="${separator}"/>&nbsp;</td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:forTokens>

                            <tr>
                                <td colspan="4"><bean:message key="label.repaymentOfCredit" bundle="${bundle}"/></td>
                            </tr>
                            <c:set var="operationsName"
                                value="creditFromCard,creditFromAccount" />
                            <c:forTokens items="${operationsName}" delims="," var="name">
                                <c:choose >
                                    <c:when   test="${empty tb.value[name]}">
                                        <tr>
                                            <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                            <td>&nbsp;</td>
                                            <td><c:out value="0"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="operations" items="${tb.value[name]}">
                                            <tr>
                                                <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                                <td><c:out value="${operations['Currency']}" default="${separator}"/>&nbsp;</td>
                                                <td><c:out value="${operations['Count']}" default="0"/>&nbsp;</td>
                                                <td><c:out value="${operations['Amount']}" default="${separator}"/>&nbsp;</td>
                                             </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:forTokens>

                            <tr>
                                <td colspan="4"><bean:message key="label.paymentForServices" bundle="${bundle}"/></td>
                            </tr>
                            <c:forTokens items="${operationsName}" delims="," var="name">
                                <c:choose >
                                    <c:when   test="${empty tb.value[name]}">
                                        <tr>
                                            <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                            <td>&nbsp;</td>
                                            <td><c:out value="0"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="operations" items="${tb.value[name]}">
                                            <tr>
                                                <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                                <td><c:out value="${operations['Currency']}" default="${separator}"/>&nbsp;</td>
                                                <td><c:out value="${operations['Count']}" default="0"/>&nbsp;</td>
                                                <td><c:out value="${operations['Amount']}" default="${separator}"/>&nbsp;</td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:forTokens>

                            <tr>
                                <td colspan="4"><bean:message key="label.claims" bundle="${bundle}"/></td>
                            </tr>

                            <c:set var="operationsName"
                                    value="loanProduct,loanOffer,loanCardProduct,loanCardOffer,virtualCard" />

                            <c:forTokens items="${operationsName}" delims="," var="name">
                                <c:choose >
                                    <c:when   test="${empty tb.value[name]}">
                                        <tr>
                                            <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                            <td>&nbsp;</td>
                                            <td><c:out value="0"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="operations" items="${tb.value[name]}">
                                            <tr>
                                                <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                                <td><c:out value="${operations['Currency']}" default="${separator}"/>&nbsp;</td>
                                                <td><c:out value="${operations['Count']}" default="0"/>&nbsp;</td>
                                                <td><c:out value="${operations['Amount']}" default="${separator}"/>&nbsp;</td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:forTokens>

                            <tr>
                                <td colspan="4"><bean:message key="label.otherOperations" bundle="${bundle}"/></td>
                            </tr>

                            <c:set var="operationsName"
                                    value="initPayment,initTemplates,newsView,support" />
                            
                            <c:forTokens items="${operationsName}" delims="," var="name">
                                <c:choose >
                                    <c:when   test="${empty tb.value[name]}">
                                        <tr>
                                            <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                            <td>&nbsp;</td>
                                            <td><c:out value="0"/></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="operations" items="${tb.value[name]}">
                                            <tr>
                                                <td><bean:message key="label.${name}" bundle="${bundle}"/></td>
                                                <td><c:out value="${operations['Currency']}" default="${separator}"/>&nbsp;</td>
                                                <td><c:out value="${operations['Count']}" default="0"/>&nbsp;</td>
                                                <td><c:out value="${operations['Amount']}" default="${separator}"/>&nbsp;</td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:forTokens>
                        </c:forEach>
                    </tiles:put>    
        </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

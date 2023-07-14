<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<html:form action="/private/loyalty/detail" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<tiles:insert definition="list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="loyaltyProgramLink" value="${form.link}" scope="request"/>
    <c:set var="operations" value="${form.operations}"/>
    <c:set var="operationsEmpty"     value="${empty operations}"/>
    <c:set var="showPassword" value="true"/>

    <c:set var="detailsPage" value="true"/>

    <tiles:put name="mainmenu" value="LoyaltyProgram"/>
    <tiles:put name="enabledLink" value="false"/>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="data" type="string">
        <div id="loaltyProgramDetail">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <script type="text/javascript">
                        function openPrintLoyaltyProgramInfo(event)
                        {
                            var params = addParam2List("","filter(fromDate)");
                            params = addParam2List(params,"filter(toDate)");

                            var url = "${phiz:calculateActionURL(pageContext, '/private/loyalty/detail/print')}";
                            url += '?' + params;
                            openWindow(event, url, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
                        }
                    </script>
                    <c:set var="model"    value="simple-pagination"/>
                    <c:set var="hideable" value="false"/>
                    <c:set var="recCount" value="10"/>
                    <c:set var="loyaltyProgramOperations" value="${form.operations}" scope="request"/>

                    <%@include file="loyaltyProgramTemplate.jsp" %>

                    <div class="tabContainer">
                        <tiles:insert definition="paymentTabs" flush="false">
                            <tiles:put name="count" value="1"/>
                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="position" value="first-last"/>
                                    <tiles:put name="title"><bean:message bundle="loyaltyBundle" key="label.last.operations"/></tiles:put>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                        <div id="operationsInfo">
                            <c:set var="backError" value="${form.regError || form.backError}"/>
                            <c:set var="validateErrors" value="${form.validateErrors}"/>
                            <c:set var="overMaxItem" value="${form.overMaxItem}"/>
                            <div class="loyaltyError">
                                <tiles:insert definition="warningBlock" flush="false">
                                    <tiles:put name="regionSelector" value="loyaltyErrors"/>
                                    <tiles:put name="isDisplayed" value="${backError}"/>
                                    <tiles:put name="data">
                                        <div id="loyaltyErrorDiv">
                                            <c:if test="${backError}">
                                                <bean:message bundle="loyaltyBundle" key="regErrorMessage"/>
                                            </c:if>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                            <c:if test="${!backError}">
                                <div class="loyaltyError">
                                    <tiles:insert definition="warningBlock" flush="false">
                                        <tiles:put name="regionSelector" value="loyaltyWarnings"/>
                                        <tiles:put name="isDisplayed" value="${operationsEmpty or overMaxItem or not empty validateErrors }"/>
                                        <tiles:put name="data">
                                            <c:choose>
                                                <c:when test="${overMaxItem}">
                                                    <c:set var="startDate"><bean:write name="form" property="operations[${phiz:size(loyaltyProgramOperations)-1}].operationDate.time" format="dd.MM.yyyy"/></c:set>
                                                    <c:set var="endDate"><bean:write name="form" property="operations[0].operationDate.time" format="dd.MM.yyyy"/></c:set>
                                                    <bean:message bundle="loyaltyBundle" key="verymoreItems.message" arg0="${startDate}" arg1="${endDate}"/>
                                                </c:when>
                                                <c:when test="${not empty validateErrors}">
                                                    <c:forEach var="error" items="${validateErrors}">
                                                        <c:out value="${error}"/><br/>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise><bean:message bundle="loyaltyBundle" key="empty.message"/></c:otherwise>
                                            </c:choose>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </c:if>

                            <input type="hidden" id="showPeriod" name="showPeriod" value="${form.showPeriod}" />

                            <div class="newPeriodShower">
                                <tiles:insert definition="filterDataPeriod" flush="false">
                                    <tiles:put name="week" value="false"/>
                                    <tiles:put name="month" value="extra"/>
                                    <tiles:put name="name" value="Date"/>
                                    <tiles:put name="buttonKey" value="button.filter"/>
                                    <tiles:put name="buttonBundle" value="commonBundle"/>
                                    <tiles:put name="needErrorValidate" value="false"/>
                                    <tiles:put name="isNeedTitle" value="false"/>
                                </tiles:insert>

                                <c:if test="${not operationsEmpty}">
                                    <div class="add-to-favourite">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.print"/>
                                            <tiles:put name="commandHelpKey" value="button.print"/>
                                            <tiles:put name="bundle" value="loyaltyBundle"/>
                                            <tiles:put name="viewType" value="blueGrayLink"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="image" value="print-check.gif"/>
                                            <tiles:put name="imageHover" value="print-check-hover.gif"/>
                                            <tiles:put name="imagePosition" value="left"/>
                                            <tiles:put name="onclick">openPrintLoyaltyProgramInfo(event);</tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                                <div class="clear"></div>
                            </div>
                            <c:set var="style" value="detailInfoTable" scope="request"/>
                            <c:choose>
                                <c:when test="${not operationsEmpty}">
                                    <tiles:insert definition="simpleTableTemplate" flush="false" >
                                        <tiles:put name="id" value="detailInfoTable"/>
                                        <tiles:put name="grid">
                                            <sl:collection id="schedule" model="simple-pagination" name="loyaltyProgramOperations" bundle="loyaltyBundle">
                                                <sl:collectionItem title="label.table.partners">
                                                    <div class="partnerBonus">
                                                        <span class="word-wrap">${schedule.operationInfo}</span>
                                                    </div>
                                                </sl:collectionItem>
                                                <sl:collectionItem title="label.table.date" styleClass="align-center" styleTitleClass="align-center">
                                                   ${phiz:formatDateDependsOnSysDate(schedule.operationDate, true, false)}
                                                </sl:collectionItem>
                                                <sl:collectionItem title="label.table.amount" styleClass="align-right" styleTitleClass="align-right">
                                                    ${phiz:formatAmount(schedule.moneyOperationalBalance)}
                                                </sl:collectionItem>
                                                <sl:collectionItem title="label.table.differenceAmount" styleClass="align-right" styleTitleClass="align-right">
                                                    <c:choose>
                                                        <c:when test="${schedule.operationKind == 'debit'}"><span class="text-green">+${phiz:formatBigDecimal(schedule.operationalBalance)}</span></c:when>
                                                        <c:when test="${schedule.operationKind == 'credit'}">
                                                            <c:set var="balance" value="${phiz:formatBigDecimal(schedule.operationalBalance)}"/>
                                                            <span>&minus;${fn:replace(balance, '-', '')}</span>
                                                        </c:when>
                                                        <c:otherwise><span>${phiz:formatBigDecimal(schedule.operationalBalance)}</span></c:otherwise>
                                                    </c:choose>
                                                </sl:collectionItem>
                                            </sl:collection>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
</html:form>

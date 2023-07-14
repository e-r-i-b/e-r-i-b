<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<div class="workspace-box credit-history-box">
    <div class="credit-history">
        <div class="cred-hist-request-success css3">
            <c:choose>
                <c:when test="${documentState == 'EXECUTED' and history eq 'false'}">
                    <div class="cred-hist-req-suc-title">
                        <bean:message key="payment.send" bundle="creditHistoryBundle"/>
                    </div>
                    <div class="cred-hist-req-suc-content">
                        <p class="cred-hist-req-suc__text"><bean:message key="credit.report.redirect.message" bundle="creditHistoryBundle"/></p>
                        <div class="cred-hist-req-suc__button">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.get.credit.history.now"/>
                                <tiles:put name="commandHelpKey" value="button.get.credit.history.now"/>
                                <tiles:put name="bundle"  value="creditHistoryBundle"/>
                                <tiles:put name="action"  value="/private/credit/report.do"/>
                            </tiles:insert>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:set var="title" value=""/>
                    <c:set var="state" value=""/>
                    <c:set var="stateDescription" value=""/>
                    <c:choose>
                        <c:when test="${documentState == 'EXECUTED' and history eq 'true'}">
                            <c:choose>
                                <c:when test="${creditReportStatus == 'WAITING'}">
                                    <c:set var="title">
                                        <bean:message key="credit.report.waiting" bundle="creditHistoryBundle"/>
                                    </c:set>
                                    <c:set var="state">Обрабатывается</c:set>
                                    <c:set var="stateDescription">Запрос на получение отчета сформирован</c:set>
                                </c:when>
                                <c:when test="${creditReportStatus == 'RECEIVED'}">
                                    <c:set var="title">
                                        <bean:message key="credit.report.received" bundle="creditHistoryBundle"/>
                                    </c:set>
                                    <c:set var="state">Исполнен</c:set>
                                    <c:set var="stateDescription">Отчет сформирован</c:set>
                                </c:when>
                                <c:when test="${creditReportStatus == 'ERROR' || timeOut}">
                                    <c:set var="title">
                                        <bean:message key="credit.history.bki.error" bundle="creditHistoryBundle"/>
                                    </c:set>
                                    <c:set var="state" value="Ошибка"/>
                                    <c:set var="stateDescription" value="Отчет не сформирован"/>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:when test="${documentState == 'DELAYED_DISPATCH'}">
                            <c:set var="title">
                                <bean:message key="payment.delayed" bundle="creditHistoryBundle"/>
                            </c:set>
                            <c:set var="state">Не отправлен</c:set>
                            <c:set var="stateDescription">Запрос не отправлен</c:set>
                        </c:when>
                        <c:when test="${documentState == 'REFUSED'}">
                            <c:set var="title">
                                <bean:message key="payment.refused" bundle="creditHistoryBundle"/>
                            </c:set>
                            <c:set var="state">Не отправлен</c:set>
                            <c:set var="stateDescription">Запрос не отправлен</c:set>
                        </c:when>
                        <c:when test="${documentState == 'SAVED'}">
                            <c:set var="title">
                                <bean:message key="payment.saved" bundle="creditHistoryBundle"/>
                            </c:set>
                            <c:set var="state">Не отправлен</c:set>
                            <c:set var="stateDescription">Запрос не отправлен</c:set>
                        </c:when>
                        <c:when test="${documentState == 'DISPATCHED'}">
                            <c:set var="title">
                                <bean:message key="payment.dispatched" bundle="creditHistoryBundle"/>
                            </c:set>
                            <c:set var="state">Не отправлен</c:set>
                            <c:set var="stateDescription">Запрос не отправлен</c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="title">
                                <bean:message key="payment.draft" bundle="creditHistoryBundle"/>
                            </c:set>
                            <c:set var="state">Не отправлен</c:set>
                            <c:set var="stateDescription">Запрос не отправлен</c:set>
                        </c:otherwise>
                    </c:choose>
                    <div class="cred-hist-req-suc-title">
                        <c:out value="${title}"/>
                    </div>
                    <div class="cred-hist-req-suc-content">
                        <p class="cred-hist-req-suc__text state">Статус</p>
                        <div id="creditReportStateDescription" onmouseover="showLayer('creditReportState','creditReportStateDescription','default', -300);" onmouseout="hideLayer('creditReportStateDescription');" class="layerFon creditReportStateDescription" style="left: 300px">
                           <div class="floatMessageHeader"></div>
                           <div class="layerFonBlock">
                               ${stateDescription}
                           </div>
                        </div>
                        <div id="creditReportState">
                            <span onmouseover="showLayer('creditReportState','creditReportStateDescription', 'default', -300);"
                               onmouseout="hideLayer('creditReportStateDescription');" class="link" style="left: 300px"> <c:out value="${state}"/>
                            </span>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
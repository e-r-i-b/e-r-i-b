<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/async/extract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:choose>
        <%-- CARD --%>
        <c:when test="${form.type=='card'}">
            <c:choose>
                <c:when test="${form.error}">
                    <div class="emptyMiniAbstract">
                        <c:choose>
                            <c:when test="${!empty form.abstractMsgError}">
                                <c:set var="oldStateLabel"><bean:message key="card.info.label.state.delivery.old" bundle="cardInfoBundle"/></c:set>
                                <c:choose>
                                    <c:when test="${form.cardEntity.cardState == 'delivery' and fn:containsIgnoreCase(form.abstractMsgError,oldStateLabel)}">
                                        <bean:message key="card.info.label.state.delivery" bundle="cardInfoBundle"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${form.abstractMsgError}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="card.info.label.state.delivery.error" bundle="cardInfoBundle"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:when test="${empty form.cardAbstract || not empty form.cardAbstract
                                                                && empty form.cardAbstract.transactions}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="card.info.label.emptyCardAbstract" bundle="cardInfoBundle"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="grid">
                        <tiles:insert page="/WEB-INF/jsp/cards/miniAbstract.jsp" flush="false">
                            <tiles:put name="resourceAbstract" beanName="form" beanProperty="cardAbstract"/>
                        </tiles:insert>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <%-- ACC --%>
        <c:when test="${form.type == 'acc'}">
            <c:choose>
                <c:when test="${form.error}">
                    <div class="emptyMiniAbstract">
                        <c:choose>
                            <c:when test="${!empty form.abstractMsgError}">
                                <c:out value="${form.abstractMsgError}"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="account.info.label.error" bundle="accountInfoBundle"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:when test="${empty form.accountAbstract || not empty form.accountAbstract
                                                                && empty form.accountAbstract.transactions}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="account.info.label.emptyAccountAbstract" bundle="accountInfoBundle"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="grid">
                        <tiles:insert page="/WEB-INF/jsp/accounts/miniAbstract.jsp" flush="false">
                            <tiles:put name="resourceAbstract" beanName="form" beanProperty="accountAbstract"/>
                        </tiles:insert>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <%-- LOAN --%>
        <c:when test="${form.type=='loan'}">
            <c:choose>
                <c:when test="${form.error}">
                    <div class="emptyMiniAbstract">
                        <c:choose>
                            <c:when test="${!empty form.abstractMsgError}">
                                <c:out value="${form.abstractMsgError}"/>
                            </c:when>
                            <c:otherwise>
                                <bean:message key="loan.info.label.error" bundle="loansBundle"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:when test="${empty form.loanAbstract || not empty form.loanAbstract
                                                                && empty form.loanAbstract.schedules}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="loan.info.label.emptyLoanAbstract" bundle="loansBundle"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="grid">
                        <tiles:insert page="/WEB-INF/jsp/loans/miniAbstract.jsp" flush="false">
                            <tiles:put name="resourceAbstract" beanName="form" beanProperty="loanAbstract"/>
                        </tiles:insert>
                    </div>
                </c:otherwise>

            </c:choose>
        </c:when>
        <%-- IMA --%>
        <c:when test="${form.type=='ima'}">
            <c:choose>
                <c:when test="${form.error}">
                    <div class="emptyMiniAbstract">
                        <c:choose>
                            <c:when test="${!empty form.abstractMsgError}">
                                <c:out value="${form.abstractMsgError}"/> 
                            </c:when>
                            <c:otherwise>
                                <bean:message key="ima.info.label.error" bundle="imaBundle"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:when test="${empty form.imAccountAbstract || not empty form.imAccountAbstract
                                                                && empty form.imAccountAbstract.transactions}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="ima.info.label.emptyIMAAbstract" bundle="imaBundle"/>
                    </div>
                </c:when>
                <c:otherwise>
                  <div class="grid">
                        <tiles:insert page="/WEB-INF/jsp/ima/miniAbstract.jsp" flush="false">
                            <tiles:put name="resourceAbstract" beanName="form" beanProperty="imAccountAbstract"/>
                        </tiles:insert>
                  </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:when test="${form.type=='loyaltyProgram'}">
            <c:choose>
                <c:when test="${form.error}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="loyalty.info.label.error" bundle="loyaltyBundle"/>
                    </div>
                </c:when>
                <c:when test="${empty form.loyaltyProgramOperations}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="loyalty.info.label.emptyLoyaltyAbstract" bundle="loyaltyBundle"/>
                    </div>
                </c:when>
                <c:otherwise>
                  <div class="grid">
                    <tiles:insert page="/WEB-INF/jsp/private/loyaltyProgram/miniAbstract.jsp" flush="false">
                        <tiles:put name="loyaltyProgramOperations" beanName="form" beanProperty="loyaltyProgramOperations"/>
                    </tiles:insert>
                  </div>
                </c:otherwise>
            </c:choose>
        </c:when>
        <%--PFR--%>
        <c:when test="${form.type=='pfr'}">
            <c:choose>
                <c:when test="${form.error}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="pfr.claims.error" bundle="pfrBundle"/>
                    </div>
                </c:when>
                <c:when test="${empty form.pfrClaims}">
                    <div class="emptyMiniAbstract">
                        <bean:message key="pfr.claims.main.empty" bundle="pfrBundle"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="grid">
                        <tiles:insert page="/WEB-INF/jsp/private/pfr/miniAbstract.jsp" flush="false">
                            <tiles:put name="pfrClaims" beanName="form" beanProperty="pfrClaims"/>
                        </tiles:insert>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</html:form>

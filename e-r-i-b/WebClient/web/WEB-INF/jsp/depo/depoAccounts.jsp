<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html:form action="/private/depo/list" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<tiles:insert definition="depoAccountInfo">
<tiles:put name="enabledLink" value="false"/>
<tiles:put name="menu" type="string"/>
<tiles:put name="data" type="string">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="personInf" value="${phiz:getPersonInfo()}"/>
    <c:if test="${personInf.isRegisteredInDepo}">
        <div class="mainWorkspace productListLink">
            <tiles:insert definition="paymentsPaymentCardsDiv" service="SecuritiesTransferClaim" operation="CreateFormPaymentOperation"
                              flush="false">
                <tiles:put name="serviceId" value="SecuritiesTransferClaim"/>
            </tiles:insert>

            <tiles:insert definition="paymentsPaymentCardsDiv" service="SecurityRegistrationClaim" operation="CreateFormPaymentOperation"
                              flush="false">
                <tiles:put name="serviceId" value="SecurityRegistrationClaim"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </c:if>
    <div id="deposits">
        <c:choose>
            <c:when test="${not empty form.depoAccounts}">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Счета депо"/>
                    <tiles:put name="data">
                        <c:set var="elementsCount" value="${fn:length(form.depoAccounts)-1}"/>
                        <logic:iterate id="listElement" name="ShowDepoAccountsListForm" property="depoAccounts" indexId="i">
                            <c:set var="depoAccountLink" value="${listElement}" scope="request"/>
                            <%@ include file="/WEB-INF/jsp/depo/depoAccountTemplate.jsp"%>
                            <c:if test="${elementsCount != i}">
                                <div class="productDivider"></div>
                            </c:if>
                        </logic:iterate>

                    </tiles:put>
                </tiles:insert>
                
                <%--<tiles:insert definition="hidableRoundBorder" flush="false">
                    <tiles:put name="title" value="Закрытые счета депо"/>
                    <tiles:put name="color" value="whiteTop"/>
                    <tiles:put name="data">

                    </tiles:put>
                </tiles:insert>--%>

            </c:when>
            <c:otherwise>
                <div id="infotext">
                    <c:set var="creationType">${personInf.creationType}</c:set>
                    <c:choose>
                        <c:when test="${creationType == 'UDBO'}">
                             <%@ include file="/WEB-INF/jsp/depo/depoAccountsEmpty.jsp"%>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert page="/WEB-INF/jsp-sbrf/needUDBO.jsp"  flush="false">
                                <tiles:put name="product" value="счетам Депо" />
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</tiles:put>
</tiles:insert>
</html:form>
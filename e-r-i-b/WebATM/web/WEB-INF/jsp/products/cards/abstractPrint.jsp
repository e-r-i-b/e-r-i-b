<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ include file="/WEB-INF/jsp/types/status.jsp"%>
<html:form action="/private/accounts/print">
    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:set var="form" value="${PrintAccountAbstractForm}"/>
            <c:set var="cardAccountAbstract" value="${form.cardAccountAbstract}"/>

                <c:if test="${!form.backError}">
                    <c:forEach items="${cardAccountAbstract}" var="listElement">

                        <c:set var="resourceAbstract" value="${listElement.value}"/>
                        <sl:collection id="transaction" name="resourceAbstract" property="transactions" model="xml-list" title="operations">
                            <sl:collectionItem title="operation">
                                    <%@ include file="transaction.jsp"%>
                            </sl:collectionItem>
                        </sl:collection>
                        <%-- Остатки по балансам --%>
                        <c:if test="${!empty resourceAbstract.openingBalance || !empty resourceAbstract.closingBalance}">
                            <balances>
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="openingBalance" />
                                    <tiles:put name="money" beanName="resourceAbstract" beanProperty="openingBalance"/>
                                </tiles:insert>
                                <tiles:insert definition="atmMoneyType" flush="false">
                                    <tiles:put name="name" value="closingBalance" />
                                    <tiles:put name="money" beanName="resourceAbstract" beanProperty="closingBalance"/>
                                </tiles:insert>
                            </balances>
                        </c:if>
                    </c:forEach>
                </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
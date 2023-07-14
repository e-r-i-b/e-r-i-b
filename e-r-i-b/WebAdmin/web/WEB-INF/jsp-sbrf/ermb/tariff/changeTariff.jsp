<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/ermb/change/tariff/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <tiles:insert definition="ermbTariffList">
        <tiles:put name="pageTitle" type="string">
            <c:if test="${not empty form.data}">
                <bean:message key="ermb.tariff" bundle="ermbBundle"/>
                <c:out value="${form.changeTariff.name}"/>
                <bean:message key="ermb.tariff.message.change" bundle="ermbBundle"/>
            </c:if>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <c:if test="${not empty form.data}">
                 <tiles:insert definition="commandButton" flush="false">
                     <tiles:put name="commandKey"     value="button.save"/>
                     <tiles:put name="commandHelpKey" value="button.save"/>
                     <tiles:put name="bundle"      value="ermbBundle"/>
                     <tiles:put name="viewType" value="blueBorder"/>
                     <tiles:put name="validationFunction" value="selectTest();"/>
                 </tiles:insert>
             </c:if>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.back.to.tariff.list"/>
                <tiles:put name="commandHelpKey" value="button.back.to.tariff.list"/>
                <tiles:put name="bundle"         value="ermbBundle"/>
                <tiles:put name="action" value="/ermb/tariff/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function selectTest()
                {
                    return checkOneSelection('selectedIds', 'Выберите один тариф.')
                }
            </script>
            <html:hidden property="id"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text">
                    <bean:message key="ermb.tariff.available" bundle="ermbBundle"/>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="tariff" model="list" property="data" bundle="ermbBundle">
                        <sl:collectionParam id="selectType" value="radio"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="label.ermb.name" property="name"/>
                        <sl:collectionItem title="label.ermb.connection">
                            <c:choose>
                                <c:when test="${empty tariff.connectionCost}">
                                    <bean:message key="ermb.free" bundle="ermbBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${phiz:formatAmount(tariff.connectionCost)}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="ermb.grace.period">
                            <c:out value="${tariff.gracePeriod}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">
                    <bean:message key="ermb.tariff" bundle="ermbBundle"/>
                    <c:out value="${form.changeTariff.name}"/>
                    <bean:message key="ermb.tariff.message.change.no.tariff" bundle="ermbBundle"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/resources/visibility" onsubmit="return setEmptyAction(event);">

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="personEdit">
<tiles:put name="submenu" type="string" value="ProductsVisibility"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="products.visibility.title" bundle="personsBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
	<tiles:insert definition="clientButton" flush="false" operation="GetPersonsListOperation">
		<tiles:put name="commandTextKey" value="button.close"/>
		<tiles:put name="commandHelpKey" value="button.close"/>
		<tiles:put name="bundle" value="commonBundle"/>
		<tiles:put name="image"  value=""/>
        <tiles:put name="action" value="/persons/list.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
    <c:set var="person" value="${form.activePerson}"/>
    <c:if test="${not empty person && person.creationType == 'UDBO'}">
        <%--Счета--%>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="titleAccount"/>
            <tiles:put name="text" value="Счета"/>
            <tiles:put name="data">
                <c:forEach var="listElement" items="${form.accounts}">
                    <c:if test="${not empty listElement}">
                        <tr>
                        <td>
                            <bean:write name="listElement" property="name"/>&nbsp;
                            <bean:write name="listElement" property="number"/>&nbsp;
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedAccountESIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_es"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedAccountMobileIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_mobile"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedAccountSocialIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_social"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedAccountIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_system"/>
                        </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.accounts}"/>
	        <tiles:put name="emptyMessage" value="У пользователя нет счетов!"/>
        </tiles:insert>

        <%--Карты--%>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="titleCard"/>
            <tiles:put name="text" value="Карты"/>
            <tiles:put name="data">
                <c:forEach var="listElement" items="${form.cards}">
                    <c:if test="${not empty listElement}">
                        <tr>
                        <td class="nullBorderR">
                            <bean:write name="listElement" property="name"/>&nbsp;
                            <c:out value="${phiz:getCutCardNumber(listElement.number)}"/>&nbsp;                            
                            <c:if test="${not empty listElement.value}">
                                <bean:define id="resourceCard" name="listElement" property="value"/>
                                <bean:write name="resourceCard" property="type"/>
                                <c:if test="${resourceCard.main}">&nbsp;(основная)</c:if>
                                <c:if test="${!resourceCard.main}">&nbsp;(дополнит.)</c:if>
                            </c:if>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedCardMobileIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_mobile"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedCardSocialIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_social"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedCardIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_system"/>
                        </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.cards}"/>
	        <tiles:put name="emptyMessage" value="У пользователя нет карт!"/>
        </tiles:insert>

        <%--Кредиты--%>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="titleLoan"/>
            <tiles:put name="text" value="Кредиты"/>
            <tiles:put name="data">
                <c:forEach var="listElement" items="${form.loans}">
                    <c:if test="${not empty listElement}">
                        <tr>
                        <td>
                            <bean:write name="listElement" property="name"/>&nbsp;
                            <bean:write name="listElement" property="number"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedLoanMobileIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_mobile"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedLoanSocialIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_social"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedLoanIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_system"/>
                        </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.loans}"/>
	        <tiles:put name="emptyMessage" value="У пользователя нет кредитов!"/>
        </tiles:insert>

        <%--Метал.счета--%>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="titleIMA"/>
            <tiles:put name="text" value="Металлические счета"/>
            <tiles:put name="data">
                <c:forEach var="listElement" items="${form.imAccounts}">
                    <c:if test="${not empty listElement}">
                        <tr>
                        <td>
                            <bean:write name="listElement" property="number"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedIMAccountMobileIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_mobile"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedIMAccountSocialIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_social"/>
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedIMAccountIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_system"/>
                        </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.imAccounts}"/>
	        <tiles:put name="emptyMessage" value="У пользователя нет металлических счетов!"/>
        </tiles:insert>

        <%--Счета ДЕПО--%>
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="titleDepoAcc"/>
            <tiles:put name="text" value="Счета депо"/>
            <tiles:put name="data">
                <c:forEach var="listElement" items="${form.depoAccounts}">
                    <c:if test="${not empty listElement}">
                        <tr>
                        <td>
                            <c:choose>
                                <c:when test="${empty listElement.name}">
                                    <bean:write name="listElement" property="accountNumber"/>
                                </c:when>
                                <c:otherwise>
                                   <bean:write name="listElement" property="name"/>(<bean:write name="listElement" property="accountNumber"/>)
                                </c:otherwise>
                            </c:choose>&nbsp;
                            ${listItem.depoAccount.agreementNumber}&nbsp;
                        </td>
                        <td class="Width180">
                            <html:multibox name="form" property="selectedDepoAccountIds" value="${listElement.id}" disabled="true"/>
                            <bean:message bundle="personsBundle" key="label.show_system"/>
                        </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.depoAccounts}"/>
	        <tiles:put name="emptyMessage" value="У пользователя нет счетов депо!"/>
        </tiles:insert>

        <%--ПФР--%>
        <c:choose>
            <c:when test="${form.pfrLink != null}">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="titlePFR"/>
                    <tiles:put name="text" value="ПФР"/>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="pfrLink">
                            <c:if test="${not empty listElement}">
                                <sl:collectionItem>
                                    <bean:write name="form" property="SNILS"/>
                                </sl:collectionItem>
                                <sl:collectionItem styleClass="Width180">
                                    <html:checkbox name="form" property="pfrLinkSelected" disabled="true"/>
                                    <bean:message bundle="personsBundle" key="label.show_system"/>
                                </sl:collectionItem>
                            </c:if>
                        </sl:collection>
                    </tiles:put>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <table width="100%" cellpadding="4">
                    <tr>
                        <td class="messageTab" align="center">У пользователя нет ПФР!</td>
                    </tr>
                </table>
            </c:otherwise>
        </c:choose>
    </c:if>
</tiles:put>
</tiles:insert>
</html:form>
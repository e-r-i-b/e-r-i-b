<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/loans/claims/archiveList">
	<tiles:insert definition="loansList">
		<tiles:put name="submenu" type="string" value="LoanClaimsArchive"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="list.claim.archive.title" bundle="loansBundle"/>
		</tiles:put>

		<c:set var="form" value="${ListLoanClaimForm}"/>
		<c:set var="loanKinds" value="${form.loanKinds}"/>
		<c:set var="loanOffices" value="${form.loanOffices}"/>

		<%-- кнопки --%>
		<tiles:put name="menu" type="string">
	    </tiles:put>

<%-- фильтр --%>
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client.surName"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="surName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client.firstName"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client.patrName"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="patrName"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.client.type"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(clientType)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="A">Анонимный</html:option>
				<html:option value="R">Зарегистрированный</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.client.passport"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="passport"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.claim.number"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="claimNumber"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.claim.client-request-amount"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="ClientRequestAmount"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.claim.state"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<html:select property="filter(state)" styleClass="select" value="">
				<html:option value="">Все</html:option>
				<html:option value="APPROVED">Утверждена</html:option>
				<html:option value="REFUSED">Отказана</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.loan.kind"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="data">
			<c:if test="${not(empty loanKinds)}">
			<html:select property="filter(loanKind)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:options collection="loanKinds" property="id" labelProperty="name"/>
			</html:select>
		</c:if>
		</tiles:put>
	</tiles:insert>
	<c:if test="${form.employeeFromMainOffice}">
		<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.loan.office"/>
			<tiles:put name="bundle" value="loansBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<c:if test="${not(empty loanOffices)}">
					<html:select property="filter(office)" styleClass="select">
						<html:option value="">Все</html:option>
						<html:options collection="loanOffices" property="synchKey" labelProperty="name"/>
					</html:select>
				</c:if>
			</tiles:put>
		</tiles:insert>
	</c:if>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.period"/>
		<tiles:put name="bundle" value="loansBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
</tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">

		<%--<div id="tableSpace">--%>
		<tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="ClaimsTable"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.fromarchive"/>
                    <tiles:put name="commandHelpKey" value="button.fromarchive.help"/>
                    <tiles:put name="bundle"         value="loansBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            return checkSelection('selectedIds', 'Выберите заявки');
                        }
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data" bundle="loansBundle" selectBean="claim">
                    <sl:collectionParam id="selectType" value="checkbox"/>
                    <sl:collectionParam id="selectName" value="selectedIds"/>
                    <sl:collectionParam id="selectProperty" value="id"/>

                    <c:set var="claim" value="${listElement[0]}"/>
					<c:set var="client" value="${listElement[1]}"/>
					<c:set var="state" value="${claim.state.code}"/>
					<c:set var="stateInWED" value="${state == 'DISPATHCED' or state == 'EXECUTED' or state == 'D'}"/>  <%--TODO непонятен статус D--%>

                    <sl:collectionItem title="label.claim.number">
                        <sl:collectionItemParam
                                id="action"
                                value="/loans/claims/claimArchive.do?id=${claim.id}"
                                condition="${phiz:impliesService('LoanClaims')}"
                                >
                                <sl:collectionItemParam id="value" value="б/н (без номера)" condition="${fn:length(claim.documentNumber) != 6}"/>
                                <sl:collectionItemParam id="value" value="${claim.documentNumber}" condition="${fn:length(claim.documentNumber) == 6}"/>
                        </sl:collectionItemParam>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.claim.rs-loans-number">
                        <sl:collectionItemParam id="value" value="${claim.attributes['rs-loans-number'].stringValue}" condition="${not stateInWED}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.claim.date">
                        <c:if test="${not empty claim}">
                            &nbsp;<bean:write name="claim" property="dateCreated.time" format="dd.MM.yyyy"/>&nbsp;
                        </c:if>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.client.fio" name="client" property="fullName"/>
                    <sl:collectionItem title="label.claim.client-request-amount" value="${claim.attributes['client-request-amount'].stringValue}"/>
                    <sl:collectionItem title="label.claim.client-request-term" value="${claim.attributes['client-request-term'].stringValue}"/>
                    <sl:collectionItem title="label.claim.bank-accept-amount">
                        <sl:collectionItemParam id="value" value="${claim.attributes['bank-accept-amount'].stringValue}" condition="${not stateInWED}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.claim.bank-accept-term">
                        <sl:collectionItemParam id="value" value="${claim.attributes['bank-accept-term'].stringValue}" condition="${not stateInWED}"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="label.claim.currency" value="${claim.attributes['currency'].stringValue}"/>
                    <sl:collectionItem title="label.claim.state">
                        <c:if test="${not empty claim.state.code}">
                            &nbsp;<bean:message key="claim.state.${claim.state.code}" bundle="loansBundle"/>
                        </c:if>
                    </sl:collectionItem>
                </sl:collection>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.data}"/>
            <tiles:put name="emptyMessage" value="Не найдено ни одного кредитной заявки"/>
        </tiles:insert>

		</tiles:put>
	</tiles:insert>
</html:form>
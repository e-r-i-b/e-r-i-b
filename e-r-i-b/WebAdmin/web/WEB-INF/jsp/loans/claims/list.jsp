<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/loans/claims/list">
	<tiles:insert definition="loansList">
		<tiles:put name="submenu" type="string" value="LoanClaims"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="list.claim.title" bundle="loansBundle"/>
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
		<tiles:put name="name" value="document"/>
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
			<html:select property="filter(state)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="DISPATCHED,STATEMENT_READY">Принята</html:option>
				<html:option value="CONSIDERATION">В рассмотрении</html:option> 
				<html:option value="COMPLETION">Требуется доработка</html:option>
				<html:option value="EXECUTED">Кредит выдан</html:option>
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
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="creditsClaims"/>
                <tiles:put name="buttons">
                    <script type="text/javascript">
                        function addComment()
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkSelection("selectedIds", "Выберите заявки")) // если не выбрана ни одна заявка, вывести сообщение и прекратить обработку запроса
                                return;
                            
                            var ids = document.getElementsByName("selectedIds");

                            var addUrl = "${phiz:calculateActionURL(pageContext,'/loans/claims/comment')}";
                            var str = '';  // иначе имя первого элемента будет равно undefinedclaimIds

                            for (var i = 0; i < ids.length; i++)
                            {
                                if (ids[i].checked)
                                {
                                    if (str != '')
                                        str = str + "&";
                                    str = str + "claimIds=" + ids[i].value;
                                }
                            }
                            window.location = addUrl + "?" + str;
                        }
                    </script>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.refuse"/>
                        <tiles:put name="commandHelpKey" value="button.refuse.help"/>
                        <tiles:put name="bundle"         value="loansBundle"/>
                        <tiles:put name="confirmText"    value="Вы действительно хотите отказать выделенные заявки?"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите заявки');
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.accept"/>
                        <tiles:put name="commandHelpKey" value="button.accept.help"/>
                        <tiles:put name="bundle"         value="loansBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите заявки');
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.completion"/>
                        <tiles:put name="commandHelpKey" value="button.completion.help"/>
                        <tiles:put name="bundle"         value="loansBundle"/>
                        <tiles:put name="confirmText"    value="Вы действительно хотите отправить на редактирование клиенту выделенные заявки?"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите заявки');
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"     value="button.comment"/>
                        <tiles:put name="commandHelpKey" value="button.comment.help"/>
                        <tiles:put name="bundle"         value="loansBundle"/>
                        <%--<tiles:put name="confirmText"    value="Вы действительно хотите изменить комментарии в выделенных заявках?"/>--%>
                        <tiles:put name="onclick"        value="addComment();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.archive"/>
                        <tiles:put name="commandHelpKey" value="button.archive.help"/>
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
                        <c:set var="stateInWED" value="${state == 'DISPATCHED' or state=='STATEMENT_READY' or state == 'REFUSED' or state == 'D'}"/> <%--TODO непонятно что такое - D--%>
                        <c:set var="stateInUS" value="${state == 'APPROVED' or state == 'EXECUTED'}"/>

                        <sl:collectionItem title="label.claim.number">
                            &nbsp;
							<c:if test="${not empty claim.comment}">
								<img src="${imagePath}/comment.gif" alt="V"/>
							</c:if>
							<sl:collectionItemParam
                                    id="action"
                                    value="/loans/claims/claim.do?id=${claim.id}"
                                    condition="${phiz:impliesService('LoanClaims')}"
                                    >
                                    <sl:collectionItemParam id="value" value="${claim.documentNumber}" condition="${fn:length(claim.documentNumber) == 6}"/>
                                    <sl:collectionItemParam id="value" value="б/н (без номера)" condition="${fn:length(claim.documentNumber) != 6}"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.claim.rs-loans-number">
                            <sl:collectionItemParam id="value" value="${claim.claimNumber}" condition="${not stateInWED}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.claim.date">
                            <c:if test="${not empty claim}">
                                &nbsp;<bean:write name="claim" property="dateCreated.time" format="dd.MM.yyyy"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.client.fio" name="client" property="fullName"/>
                        <sl:collectionItem title="label.claim.client-request-amount" value="${claim.attributes['client-request-amount'].stringValue}"/>
                        <sl:collectionItem title="label.claim.client-request-term" value="${claim.attributes['client-request-term'].stringValue}"/>
                        <sl:collectionItem title="label.claim.bank-accept-amount">
                            <sl:collectionItemParam
                                    id="value"
                                    value="${claim.attributes['bank-accept-amount'].stringValue}"
									condition="${not stateInWED}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.claim.currency">
                            <sl:collectionItemParam
                                    id="value"
                                    value="${claim.attributes['loan-currency'].stringValue}"
                                    condition="${stateInUS}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.claim.state">
                            <c:if test="${not empty claim.state.code}">
                                &nbsp;<bean:message key="claim.state.${claim.state.code}" bundle="loansBundle"/>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одной кредитной заявки"/>
            </tiles:insert>

			<script type="text/javascript">
                // чтобы показывалась полоса прокрутки в IE и FireFox
				var widthClient = getClientWidth();
				if (navigator.appName=='Microsoft Internet Explorer' || navigator.appName == 'Netscape')
				document.getElementById("workspaceDiv").style.width = widthClient - leftMenuSize - 110;
			</script>

		</tiles:put>
	</tiles:insert>
</html:form>
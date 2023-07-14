<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz" %>

<html:form action="/templates/offer/list">
	<c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
	<c:set var="bundle" value="templatesBundle"/>

	<tiles:insert definition="loansList" flush="false">
		<tiles:put name="submenu"        type="string" value="CreditOfferTemplate"/>
		<tiles:put name="messagesBundle" type="string" value="templatesBundle"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="title.credit.loan.offer" bundle="${bundle}"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="bundle"         value="${bundle}"/>
				<tiles:put name="commandKey"     value="button.template.offer.add"/>
				<tiles:put name="commandHelpKey" value="button.template.offer.add"/>
				<tiles:put name="action"         value="/templates/offer/edit"/>
				<tiles:put name="viewType"       value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="filter" type="string">
			<c:set var="colCount" value="2" scope="request"/>
			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label"     value="label.credit.loan.offer.id"/>
				<tiles:put name="bundle"    value="${bundle}"/>
				<tiles:put name="name"      value="id"/>
				<tiles:put name="maxlength" value="21"/>
			</tiles:insert>

			<tiles:insert definition="filterEntryField" flush="false">
				<tiles:put name="label"  value="label.credit.loan.offer.status"/>
				<tiles:put name="bundle" value="${bundle}"/>
				<tiles:put name="data">
					<html:select property="filter(status)" styleClass="select">
						<html:option value="">Все</html:option>
						<html:option value="INTRODUCED">
							<bean:message key="label.credit.loan.offer.INTRODUCED" bundle="${bundle}"/>
						</html:option>
						<html:option value="OPERATE">
							<bean:message key="label.credit.loan.offer.OPERATE" bundle="${bundle}"/>
						</html:option>
						<html:option value="ARCHIVE">
							<bean:message key="label.credit.loan.offer.ARCHIVE" bundle="${bundle}"/>
						</html:option>
					</html:select>
				</tiles:put>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<script type="text/javascript">
				function remove()
				{
					checkIfOneItem("selectedIds");
					return checkSelection("selectedIds", '<bean:message bundle="${bundle}" key="credit.loan.offer.checkSelection"/>');
				}

				function edit()
				{
					var sel = getSelectedQnt("selectedIds");
					if (sel == 0)
					{
						clearLoadMessage();
						return groupError('<bean:message bundle="${bundle}" key="credit.loan.offer.checkSelection"/>');
					}

					var url = "${phiz:calculateActionURL(pageContext, '/templates/offer/edit')}";
					window.location = url + "?id=" + getRadioValue(document.getElementsByName("selectedIds"));
				}
			</script>

			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="creditOfferTemplates"/>
				<tiles:put name="buttons">
					<tiles:insert definition="clientButton" flush="false">
						<tiles:put name="bundle"         value="${bundle}"/>
						<tiles:put name="commandTextKey" value="button.template.offer.edit"/>
						<tiles:put name="commandHelpKey" value="button.template.offer.edit"/>
						<tiles:put name="onclick"        value="edit();"/>
					</tiles:insert>
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="bundle"             value="${bundle}"/>
						<tiles:put name="commandKey"         value="button.template.offer.remove"/>
						<tiles:put name="commandHelpKey"     value="button.template.offer.remove"/>
						<tiles:put name="validationFunction" value="remove();"/>
					</tiles:insert>
				</tiles:put>
				<tiles:put name="grid">
					<sl:collection id="listElement" model="list" property="data">
						<sl:collectionParam id="selectType"     value="radio"/>
						<sl:collectionParam id="selectName"     value="selectedIds"/>
						<sl:collectionParam id="selectProperty" value="id"/>

						<sl:collectionItem title="ID" property="id"/>
						<sl:collectionItem title="Период действия">
							<c:out value="С ${phiz:formatDateToStringOnPattern(listElement.from, 'dd.MM.yyyy HH:mm')}"/>

							<c:if test="${listElement.status eq 'ARCHIVE'}">
								&nbsp;<c:out value="по ${phiz:formatDateToStringOnPattern(listElement.to, 'dd.MM.yyyy HH:mm')}"/>
							</c:if>
						</sl:collectionItem>
						<sl:collectionItem title="Статус">
							<c:choose>
								<c:when test="${listElement.status eq 'INTRODUCED'}">
									<bean:message key="label.credit.loan.offer.INTRODUCED" bundle="${bundle}"/>
								</c:when>
								<c:when test="${listElement.status eq 'OPERATE'}">
									<bean:message key="label.credit.loan.offer.OPERATE" bundle="${bundle}"/>
								</c:when>
								<c:when test="${listElement.status eq 'ARCHIVE'}">
									<bean:message key="label.credit.loan.offer.ARCHIVE" bundle="${bundle}"/>
								</c:when>
							</c:choose>
						</sl:collectionItem>
					</sl:collection>
				</tiles:put>
				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage">
					<bean:message key="label.credit.loan.offer.empty" bundle="${bundle}"/>
				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
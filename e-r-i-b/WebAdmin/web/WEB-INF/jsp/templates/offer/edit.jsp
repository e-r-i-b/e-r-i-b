<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz" %>

<html:form action="/templates/offer/edit">
	<c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
	<c:set var="bundle" value="templatesBundle"/>

	<tiles:insert definition="loansEdit">
		<tiles:put name="submenu"        type="string" value="CreditOfferTemplate"/>
		<tiles:put name="messagesBundle" type="string" value="templatesBundle"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="title.edit.credit.loan.offer" bundle="${bundle}"/>
		</tiles:put>
		<tiles:put name="descTitle">
			<bean:message key="label.credit.loan.offer.description" bundle="${bundle}"/>
		</tiles:put>
		<tiles:put name="data" type="string">
			<script type="text/javascript">
				doOnLoad(function() {
					addClearMasks(null, function(event)
					{
						clearInputTemplate('fields(fromDate)', '__.__.____');
						clearInputTemplate('fields(fromTime)', '__:__');
					});
				});

                function validateText()
                {
                    var text = $('#text').val();
                    var attributes = ${phiz:getCreditOfferTemplateAttributesJson()};

                    var notContainsAll = attributes.some(function(attr){
                        return text.indexOf(attr) < 0;
                    });

                    if (notContainsAll)
                    {
                        return confirm('<bean:message key="credit.loan.offer.save.validate" bundle="${bundle}"/>')
                    }

                    return true;
                }
			</script>

			<tiles:insert definition="simpleFormRow" flush="false">
				<tiles:put name="title">
					<bean:message key="label.credit.loan.offer.id" bundle="${bundle}"/>:
				</tiles:put>
				<tiles:put name="data">
					<html:text property="fields(id)" value="${form.template.id}" size="9" maxlength="50" readonly="true"/>
				</tiles:put>
			</tiles:insert>

			<tiles:insert definition="simpleFormRow" flush="false">
				<tiles:put name="title">
					<bean:message key="label.credit.loan.offer.from" bundle="${bundle}"/>
				</tiles:put>
				<tiles:put name="data">
					<c:set var="fromDate" value="${phiz:formatDateToStringOnPattern(form.template.from, 'dd.MM.yyyy')}"/>
					<c:set var="fromTime" value="${phiz:formatDateToStringOnPattern(form.template.from, 'HH:mm')}"/>

					<html:text property="fields(fromDate)" value="${fromDate}" maxlength="10" styleClass="dot-date-pick"/>
					<html:text property="fields(fromTime)" value="${fromTime}" maxlength="5"  styleClass="short-time-template" size="5"/>
				</tiles:put>
				<tiles:put name="isNecessary" value="true"/>
			</tiles:insert>

			<tiles:insert definition="simpleFormRow" flush="false">
				<tiles:put name="title">
					<bean:message key="label.credit.loan.offer.title" bundle="${bundle}"/>:
				</tiles:put>
				<tiles:put name="data">
					<html:textarea styleId="text" property="fields(text)" value="${form.template.text}" cols="100" rows="10"/>
				</tiles:put>
				<tiles:put name="isNecessary" value="true"/>
			</tiles:insert>

			<tiles:insert definition="simpleFormRow" flush="false">
				<tiles:put name="data">
					<tiles:insert definition="bbCodeButtons"  flush="false">
						<tiles:put name="showCreditFio"       value="true"/>
						<tiles:put name="showDULSeries"       value="true"/>
						<tiles:put name="showDULNumber"       value="true"/>
						<tiles:put name="showDULLocation"     value="true"/>
						<tiles:put name="showDULIssue"        value="true"/>
						<tiles:put name="showPSK"             value="true"/>
						<tiles:put name="showAmount"          value="true"/>
						<tiles:put name="showDuration"        value="true"/>
						<tiles:put name="showInterest"        value="true"/>
						<tiles:put name="showOrder"           value="true"/>
						<tiles:put name="showAccountIssuance" value="true"/>
						<tiles:put name="showBorrower"        value="true"/>
						<tiles:put name="showRegistration"    value="true"/>
						<tiles:put name="textAreaId"          value="text"/>
						<tiles:put name="showBold"            value="true"/>
						<tiles:put name="showItalics"         value="true"/>
						<tiles:put name="showUnderline"       value="true"/>
						<tiles:put name="showColor"           value="true"/>
						<tiles:put name="showHyperlink"       value="true"/>
					</tiles:insert>
				</tiles:put>
			</tiles:insert>

			<html:hidden property="id"/>
		</tiles:put>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.dict.cancel"/>
				<tiles:put name="commandHelpKey" value="button.dict.cancel.help"/>
				<tiles:put name="bundle"         value="templatesBundle"/>
				<tiles:put name="image"          value=""/>
				<tiles:put name="action"         value="/templates/offer/list.do"/>
			</tiles:insert>
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="bundle"                value="${bundle}"/>
				<tiles:put name="commandKey"            value="button.save"/>
				<tiles:put name="commandHelpKey"        value="button.save.help"/>
                <tiles:put name="validationFunction"    value="validateText();"/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
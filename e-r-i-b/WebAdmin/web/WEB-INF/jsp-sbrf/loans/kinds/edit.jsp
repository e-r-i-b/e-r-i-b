<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/loans/kinds/edit"  onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="loansBundle"/>

	<tiles:insert definition="loansEdit">
		<tiles:put name="submenu" type="string" value="LoanKinds"/>

		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey"     value="button.list.kind.help"/>
				<tiles:put name="bundle"  value="${bundle}"/>
				<tiles:put name="action"  value="/loans/kinds/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
            <input type="hidden" name="id" value="${form.id}"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="EditLoanKindForm"/>
                <tiles:put name="name">
                    <bean:message key="title.kind.edit" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="title.kind.edit.description" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="imageId" value="iconMid_credits.gif"/>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.kind.name" bundle="${bundle}"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="30" maxlength="25" styleClass="contactInput"/>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.kind.help"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="${bundle}"/>
                        <tiles:put name="action"  value="/loans/kinds/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
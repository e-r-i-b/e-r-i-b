<%--
  User: Pakhomova
  Date: 15.07.2008
  Time: 12:22:47
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/dictionary/banks/national/edit" onsubmit="return setEmptyAction(event);">

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="dictionariesEdit">
	<tiles:put name="submenu" type="string" value="Edit"/>

	<tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"         value="commonBundle"/>
			<tiles:put name="image"          value=""/>
			<tiles:put name="action"         value="/private/multiblock/dictionary/banks/national.do"/>
		</tiles:insert>
	</tiles:put>

	<tiles:put name="data" type="string">

	<tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="id" value="editDepartments"/>
        <tiles:put name="name">
            <bean:message bundle="dictionariesBundle" key="bank.edit.label"/>
        </tiles:put>
        <tiles:put name="description">
            <bean:message bundle="dictionariesBundle" key="bank.edit.description"/>
        </tiles:put>
        <tiles:put name="data">

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.name" bundle="dictionariesBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:text property="field(name)" size="60"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.bic" bundle="dictionariesBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:text property="field(bic)" size="30" maxlength="9"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.corr.account" bundle="dictionariesBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:text property="field(corrAccount)" size="30" maxlength="20"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.short.name" bundle="dictionariesBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(shortName)" size="60"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.place" bundle="dictionariesBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="field(place)" size="60"/>
                </tiles:put>
            </tiles:insert>

        </tiles:put>
        <tiles:put name="buttons">
            <tiles:insert definition="commandButton" flush="false" operation="EditBankOperation">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="dictionariesBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/dictionaries/bank/locale/save')}"/>
            <c:if test="${not empty frm.synchKey}">
                <tiles:insert definition="languageSelectForEdit" flush="false">
                    <tiles:put name="selectId" value="chooseLocale"/>
                    <tiles:put name="idName" value="stringId"/>
                    <tiles:put name="entityId" value="${frm.synchKey}"/>
                    <tiles:put name="styleClass" value="float"/>
                    <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
        <div style="float: left">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"     value="back.to.list"/>
                <tiles:put name="commandHelpKey" value="back.to.list.help"/>
                <tiles:put name="bundle"  value="localeBundle"/>
                <tiles:put name="action" value="/private/multiblock/dictionary/banks/national"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </div>
	</tiles:put>

</tiles:insert>

</html:form>
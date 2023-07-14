<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/bannedaccount/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="editUrl" value="${phiz:calculateActionURL(pageContext,'/bannedaccount/edit')}"/>

    <tiles:insert definition="bannedAccountsMain">
        <c:set var="bundle" value="bannedaccountsBundle"/>
        <tiles:put name="submenu" type="string" value="BannedAccount"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="action" value="/bannedaccount/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>


        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function doEdit()
                {
                    if (!checkOneSelection("selectedIds", "¬ыберите одну маску счета дл€ редактировани€"))
                        return;
                    checkIfOneItem("selectedIds");
                    var url = "${phiz:calculateActionURL(pageContext,'/bannedaccount/edit')}";
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = url+ "?id=" + id;
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="BannedAccountTable"/>
                <tiles:put name="text" value="ћаски счетов,  запрещенных дл€ переводов (платежей)"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', '¬ыберите маску счета');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText" value="”далить выбранную маску?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="id" value="bannedList"/>
                <tiles:put name="grid">
                    <sl:collection id="bannedAccount" model="list" property="data" bundle="${bundle}">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="title.accountNumber">
                            <sl:collectionItemParam id="value">
                                <phiz:link action="/bannedaccount/edit" serviceId="BannedAccountManagment">
                                    <phiz:param name="id" value="${bannedAccount.id}"/>
                                    <c:out value="${fn:replace(bannedAccount.accountNumber,'_','*')}"/>
                                </phiz:link>
                            </sl:collectionItemParam>
                        </sl:collectionItem>

                        <sl:collectionItem title="title.BIC">
                            <sl:collectionItemParam id="value">
                                <c:out value="${bannedAccount.bicList}"/>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                    </sl:collection>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                </tiles:put>
                <tiles:put name="emptyMessage" value="Ќе задано ни одного запрещенного счета дл€ переводов."/>
            </tiles:insert>
      </tiles:put>
    </tiles:insert>
</html:form>

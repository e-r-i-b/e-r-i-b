<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/loanclaim/credit/type/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="loanclaimList">
        <tiles:put name="submenu" value="CreditProductType"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle"         value="loanclaimBundle"/>
                <tiles:put name="action" value="/loanclaim/credit/type/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
            <c:if test="${not empty form.data}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit"/>
                    <tiles:put name="bundle"         value="loanclaimBundle"/>
                    <tiles:put name="onclick"        value="redirect()"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove"/>
                    <tiles:put name="bundle"         value="ermbBundle"/>
                    <tiles:put name="validationFunction"    value="doRemove();"/>
                    <tiles:put name="confirmText"><bean:message bundle="loanclaimBundle" key="label.product.type.remove.question"/></tiles:put>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                $(document).ready(function(){
                    var ids = $('input[name="selectedIds"]');
                    if (ids != null && ids.length == 1)
                    {
                        $(ids[0]).attr("checked", 'true');
                    }

                });

                function doRemove()
                {
                    return checkSelection("selectedIds", '<bean:message bundle="loanclaimBundle" key="label.product.type.remove.checkSelection"/>');
                }

                function selectTest()
                {
                    if (checkSelection("selectedIds", '<bean:message bundle="loanclaimBundle" key="label.product.type.checkSelection"/>'))
                        return  $('input[name="selectedIds"]:checked')[0].value;
                }

                function redirect()
                {
                    var selectId = selectTest();
                    if (selectId != null)
                    {
                        <c:set var="u" value="/loanclaim/credit/type/edit.do"/>
                        var url = "${phiz:calculateActionURL(pageContext,u)}";
                        window.location = url + "?id=" + selectId;
                    }
                }
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text">
                    <bean:message key="label.loan.claim.list.form.name" bundle="loanclaimBundle"/>
                </tiles:put>

                <tiles:put name="grid">
                    <sl:collection id="creditType" model="list" property="data" bundle="loanclaimBundle">
                        <sl:collectionParam id="selectType" value="radio"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="code"/>
                        <sl:collectionItem title="label.loan.claim.product.type"><c:out value="${creditType.name}"/></sl:collectionItem>
                        <sl:collectionItem title="label.loan.claim.credit.product.type.code" property="code"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Ќе найдено ни одного типа кредитного продукта"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>

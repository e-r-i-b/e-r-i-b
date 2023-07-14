<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/loanclaim/credit/product/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="loanclaimList">
        <tiles:put name="submenu" value="CreditProduct"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle"         value="loanclaimBundle"/>
                <tiles:put name="action" value="/loanclaim/credit/product/edit.do"/>
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
                    <tiles:put name="confirmText"    value="”далить выбранный кредитный продукт?"/>
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

                function selectTest()
                {
                    var select = $('input[name="selectedIds"]:checked');
                    if (select.length == 0)
                    {
                        alert('¬ыберите один  кредитный продукт дл€ редактировани€');
                        return;
                    }
                    return select[0].value;
                }

                function redirect()
                {
                    var selectId = selectTest();
                    if (selectId != null)
                    {
                        <c:set var="u" value="/loanclaim/credit/product/edit.do"/>
                        var url = "${phiz:calculateActionURL(pageContext,u)}";
                        window.location = url + "?id=" + selectId;
                    }
                }
            </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text">
                    <bean:message key="label.loan.claim.credit.products" bundle="loanclaimBundle"/>
                </tiles:put>

                <tiles:put name="grid">
                    <sl:collection id="creditProdcut" model="list" property="data" bundle="loanclaimBundle">
                        <sl:collectionParam id="selectType" value="radio"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="label.loan.claim.credit.product">
                            <c:out value="${creditProdcut.name}"/>
                         </sl:collectionItem>
                        <sl:collectionItem title="label.loan.claim.credit.product.code" property="code"/>
                        <sl:collectionItem title="label.loan.claim.ensuring">
                            <c:choose>
                                <c:when test="${creditProdcut.ensuring}">
                                    <bean:message key="label.loan.claim.required" bundle="loanclaimBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <bean:message key="label.loan.claim.not.required" bundle="loanclaimBundle"/>
                                </c:otherwise>
                            </c:choose>

                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Ќе найдено ни одного кредитного продукта"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>

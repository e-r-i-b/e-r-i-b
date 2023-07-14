<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/complex/investment/fund/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
<tiles:insert definition="editPFPComplexProduct">
<tiles:put name="submenu" type="string" value="complexFundInvestmentProductEdit"/>
<tiles:put name="data" type="string">
<tiles:insert definition="paymentForm" flush="false">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:put name="id"  value="pfpComplexInvestmentFund"/>
<tiles:put name="name">
    <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.name"/>
</tiles:put>
<tiles:put name="description">
    <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.description"/>
</tiles:put>
<tiles:put name="data">
<script type="text/javascript">
    function openAccountsDictionaries()
    {
        window.open("${phiz:calculateActionURL(pageContext, '/pfp/products/account/list')}?action=dictionary&filter(forComplex)=fund",
                'accountDictionary',
                "resizable=1,menubar=1,toolbar=1,scrollbars=1");
    }

    function setAccount(data)
    {
        $("[name=fields(accountId)]").val(data["id"]);
        $("[name=fields(accountName)]").val(data["name"]);
    }


    function openFundsDictionaries()
    {
        window.open("${phiz:calculateActionURL(pageContext, '/pfp/products/fund/list')}?action=dictionary&filter(forComplex)=fund",
                'fundDictionary',
                "resizable=1,menubar=1,toolbar=1,scrollbars=1");
    }

    function setFund(data)
    {
        $(".hidableElement").show();
        if ($("#fundProduct" + data['id']).length == 1)
        {
            alert('<bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.fund.add.exist"/>');
            return false;
        }
        var line =  '<tr id="fundProduct' + data['id'] + '" class="ListLine0">' +
                '<td class="listItem">' +
                '<input type="checkbox"  class="checkboxMargin" name="selectedIds" value="' + data['id'] + '">&nbsp;' +
                '<input type="hidden" checked name="fundProductIds" value="' + data['id'] + '">' +
                '</td>' +
                '<td>' +
                '<input type="hidden" value="' + data['name'] +'" name="fields(fundProductNameFor' + data['id'] + ')">' +
                data['name'] +
                '</td>' +
                '</tr>';
        $("#fundProducts > tbody").append(line);
        return true;
    }

    function removeFund()
    {
        checkIfOneItem("selectedIds");
        if (!checkSelection("selectedIds",
                '<bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.fund.remove.checkSelection"/>'))
            return false;

        if (!confirm('<bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.fund.remove.confirm"/>'))
            return false;

        $(":checkbox:checked").each(function(){$(this).parent().parent().remove();});
        if ($("[name=selectedIds]").size() == 0)
            $(".hidableElement").hide();
        return true;
    }
</script>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.account"/>
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:hidden property="fields(accountId)"/>
        <html:text property="fields(accountName)" size="58" readonly="true"/>
        <input type="button" class="buttWhite" onclick="openAccountsDictionaries();" value="..."/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.fund"/>
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <c:set var="buttonDisplayStyle" value=""/>
        <c:if test="${empty EditComplexInvestmentProductForm.fundProductIds}">
            <c:set var="buttonDisplayStyle" value=" display:none;"/>
        </c:if>
        <%--������--%>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey"    value="products.complex.investment.fund.label.edit.field.fund.add"/>
            <tiles:put name="commandHelpKey"    value="products.complex.investment.fund.label.edit.field.fund.add"/>
            <tiles:put name="bundle"            value="pfpProductBundle"/>
            <tiles:put name="onclick"           value="openFundsDictionaries();"/>
            <tiles:put name="viewType"          value="buttonGrayNew"/>
        </tiles:insert>
        <div class="hidableElement" style="${buttonDisplayStyle}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="products.complex.investment.fund.label.edit.field.fund.remove"/>
                <tiles:put name="commandHelpKey"    value="products.complex.investment.fund.label.edit.field.fund.remove"/>
                <tiles:put name="bundle"            value="pfpProductBundle"/>
                <tiles:put name="onclick"           value="removeFund()"/>
                <tiles:put name="viewType"          value="buttonGrayNew"/>
            </tiles:insert>
        </div>

        <%--�������--%>
        <div class="smallDynamicGrid hidableElement" style="${buttonDisplayStyle}">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="fundProducts"/>
                <tiles:put name="head">
                    <th width="20px">
                        <input type="checkbox" onclick="switchSelection(this,'selectedIds');">
                    </th>
                    <th>
                        <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.fund.table.title"/>
                    </th>
                </tiles:put>
                <tiles:put name="data">
                    <logic:iterate id="productId" name="EditComplexInvestmentProductForm" property="fundProductIds">
                        <c:set var="fieldName" value="fundProductNameFor${productId}"/>
                        <tr id="fundProduct${productId}" class="ListLine0">
                            <td class="listItem">
                                <input type="checkbox" class="checkboxMargin" value="${productId}" name="selectedIds">
                                <input type="hidden" value="${productId}" name="fundProductIds" checked>
                            </td>
                            <td>
                                <html:hidden property="fields(${fieldName})"/>
                                    ${form.fields[fieldName]}
                            </td>
                        </tr>
                    </logic:iterate>
                    <%--����� ����� �� ������������ ��������� emptyMessage--%>
                    <div></div>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>

<fieldset>
    <legend><bean:message bundle="pfpProductBundle" key="product.base.minSum.label.fields"/></legend>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="pfpProductBundle" key="product.base.minSum.label.field.START_CAPITAL"/>
        </tiles:put>
        <tiles:put name="data">
            <html:text property="fields(START_CAPITALminSum)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
            <b><bean:message bundle="pfpProductBundle" key="product.base.minSum.label.field.unit"/></b>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="pfpProductBundle" key="product.base.minSum.label.field.QUARTERLY_INVEST"/>
        </tiles:put>
        <tiles:put name="data">
            <html:text property="fields(QUARTERLY_INVESTminSum)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
            <b><bean:message bundle="pfpProductBundle" key="product.base.minSum.label.field.unit"/></b>
        </tiles:put>
    </tiles:insert>
</fieldset>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpProductBundle" key="product.base.segment.field.name"/>
    </tiles:put>
    <tiles:put name="data">
        <jsp:include page="setSegmentCodeTypeArea.jsp"/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.image"/>
    </tiles:put>
    <tiles:put name="data">
        <tiles:insert definition="imageInput" flush="false"/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="simpleFormRow" flush="false">
    <tiles:put name="title">
        <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.edit.field.description"/>
    </tiles:put>
    <tiles:put name="isNecessary" value="true"/>
    <tiles:put name="data">
        <html:textarea property="fields(description)" cols="63" rows="3"/>
    </tiles:put>
</tiles:insert>

<c:set var="productTypeParameters" value="${form.productTypeParameters}"/>
<c:set var="tableParams" value="${productTypeParameters.tableParameters}"/>
<c:if test="${productTypeParameters.useOnTable=='true'}">
    <fieldset>
        <legend><bean:message key="label.table.params" bundle="pfpProductBundle"/></legend>
        <c:set var="showCheckBox" value="true"/>
        <c:forEach var="tableParameter" items="${tableParams.columns}">
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <c:out value="${tableParameter.value}"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:text name="form" property="field(tableParam${tableParameter.orderIndex})" maxlength="100"/>
                    <c:if test="${showCheckBox == 'true'}">
                        <html:checkbox name="form" property="field(useIcon)"/>&nbsp;
                        <bean:message key="label.table.params.use.icon" bundle="pfpProductBundle"/>
                        <c:set var="showCheckBox" value="false"/>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </c:forEach>
    </fieldset>
</c:if>
</tiles:put>
<tiles:put name="buttons">
    <tiles:insert definition="clientButton" flush="false" operation="ListComplexFundInvestmentProductsOperation">
        <tiles:put name="commandTextKey"    value="products.complex.investment.fund.button.cancel"/>
        <tiles:put name="commandHelpKey"    value="products.complex.investment.fund.button.cancel.help"/>
        <tiles:put name="bundle"            value="pfpProductBundle"/>
        <tiles:put name="action"            value="/pfp/products/complex/investment/fund/list.do"/>
    </tiles:insert>
    <tiles:insert definition="commandButton" flush="false" operation="EditComplexFundInvestmentProductOperation">
        <tiles:put name="commandKey"         value="button.save"/>
        <tiles:put name="commandTextKey"     value="products.complex.investment.fund.button.save"/>
        <tiles:put name="commandHelpKey"     value="products.complex.investment.fund.button.save.help"/>
        <tiles:put name="bundle"             value="pfpProductBundle"/>
        <tiles:put name="postbackNavigation" value="true"/>
        <tiles:put name="validationFunction" value="checkSegment();"/>
    </tiles:insert>
</tiles:put>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>
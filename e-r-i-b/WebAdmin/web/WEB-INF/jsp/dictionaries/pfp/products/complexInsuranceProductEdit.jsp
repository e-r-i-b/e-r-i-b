<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/complex/insurance/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <tiles:insert definition="editPFPComplexProduct">
        <tiles:put name="submenu" type="string" value="complexInsuranceProductEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpComplexInsurance"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function openAccountsDictionaries()
                        {
                            window.open("${phiz:calculateActionURL(pageContext, '/pfp/products/account/list')}?action=dictionary&filter(forComplex)=insurance",
                                    'accountDictionary',
                                    "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                        }

                        function openInsurancesDictionaries()
                        {
                            window.open("${phiz:calculateActionURL(pageContext, '/pfp/insurance/productList')}?action=dictionary&filter(forComplex)=true",
                                    'insuranceDictionary',
                                    "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                        }

                        function setAccount(data)
                        {
                            $("[name=fields(accountId)]").val(data["id"]);
                            $("[name=fields(accountName)]").val(data["name"]);
                        }

                        function setInsurance(data)
                        {
                            $(".hidableElement").show();
                            if ($("#insuranceProduct" + data['id']).length == 1)
                            {
                                alert('<bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.insurance.table.add.exist"/>');
                                return false;
                            }
                            var line =  '<tr id="insuranceProduct' + data['id'] + '" class="ListLine0">' +
                                            '<td class="listItem">' +
                                                '<input class="checkboxMargin" type="checkbox" name="selectedIds" value="' + data['id'] + '">&nbsp;' +
                                                '<input type="hidden" name="insuranceProductIds" value="' + data['id'] + '" checked">' +
                                            '</td>' +
                                            '<td>' +
                                                '<input type="hidden" value="' + data['name'] +'" name="fields(productDescrtiptionFor' + data['id'] + ')">' +
                                                data['name'] +
                                            '</td>' +
                                        '</tr>';
                            $("#insuranceProducts tbody").append(line);
                            return true;
                        }

                        function removeInsurance()
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.insurance.table.remove.checkSelection"/>'))
                                return false;

                            if (!confirm('<bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.insurance.table.remove.confirm"/>'))
                                return false;

                            $(":checkbox:checked").each(function(){$(this).parent().parent().remove();});
                            if ($("[name=selectedIds]").size() == 0)
                                $(".hidableElement").hide();
                            return true;
                        }
                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.account"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:hidden property="fields(accountId)"/>
                            <html:text property="fields(accountName)" size="50" readonly="true"/>
                            <input type="button" class="buttWhite" onclick="openAccountsDictionaries();" value="..."/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.insurance"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="buttonDisplayStyle" value=""/>
                            <c:if test="${empty EditComplexInsuranceProductForm.insuranceProductIds}">
                                <c:set var="buttonDisplayStyle" value=" display:none;"/>
                            </c:if>

                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey"    value="products.complex.insurance.label.edit.field.insurance.table.add"/>
                                <tiles:put name="commandHelpKey"    value="products.complex.insurance.label.edit.field.insurance.table.add"/>
                                <tiles:put name="bundle"            value="pfpProductBundle"/>
                                <tiles:put name="onclick"           value="openInsurancesDictionaries();"/>
                                <tiles:put name="viewType"          value="buttonGrayNew"/>
                            </tiles:insert>

                            <div class="float hidableElement" style="${buttonDisplayStyle}">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="products.complex.insurance.label.edit.field.insurance.table.remove"/>
                                    <tiles:put name="commandHelpKey"    value="products.complex.insurance.label.edit.field.insurance.table.remove"/>
                                    <tiles:put name="bundle"            value="pfpProductBundle"/>
                                    <tiles:put name="onclick"           value="removeInsurance();"/>
                                    <tiles:put name="viewType"          value="buttonGrayNew"/>
                                </tiles:insert>
                            </div>
                            <%--Таблица--%>
                            <div class="smallDynamicGrid hidableElement" style="${buttonDisplayStyle}">
                                <tiles:insert definition="tableTemplate" flush="false">
                                    <tiles:put name="id" value="insuranceProducts"/>
                                    <tiles:put name="head">
                                        <th width="20px">
                                            <input type="checkbox" onclick="switchSelection(this,'selectedIds');"/>
                                        </th>
                                        <th>
                                            <bean:message bundle='pfpProductBundle' key='products.complex.insurance.label.edit.field.insurance.table.title'/>
                                        </th>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <logic:iterate id="productId" name="EditComplexInsuranceProductForm" property="insuranceProductIds">
                                            <c:set var="fieldName" value="productDescrtiptionFor${productId}"/>
                                            <tr id="insuranceProduct${productId}" class="ListLine0">
                                                <td class="listItem">
                                                    <input class="checkboxMargin" type="checkbox" value="${productId}" name="selectedIds"/>
                                                    <input type="hidden" value="${productId}" name="insuranceProductIds" checked/>
                                                </td>
                                                <td>
                                                    <html:hidden property="fields(${fieldName})"/>
                                                    ${form.fields[fieldName]}
                                                </td>
                                            </tr>
                                        </logic:iterate>
                                        <%--Нужно чтобы не отображалось сообщения emptyMessage--%>
                                        <div></div>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.minSum"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(minSum)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.minSum.unit"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.insurance.minSum"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(minSumInsurance)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.insurance.minSum.unit"/></b>
                        </tiles:put>
                    </tiles:insert>

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
                            <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.image"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.edit.field.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="3"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="productTypeParameters" value="${form.productTypeParameters}"/>
                    <c:set var="tableParams" value="${productTypeParameters.tableParameters}"/>
                    <c:if test="${productTypeParameters.useOnTable}">
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
                    <tiles:insert definition="clientButton" flush="false" operation="ListComplexInsuranceProductsOperation">
                        <tiles:put name="commandTextKey"    value="products.complex.insurance.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="products.complex.insurance.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpProductBundle"/>
                        <tiles:put name="action"            value="/pfp/products/complex/insurance/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditComplexInsuranceProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="products.complex.insurance.button.save"/>
                        <tiles:put name="commandHelpKey"     value="products.complex.insurance.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpProductBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="checkSegment()"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/account/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <tiles:insert definition="editPFPProduct">
        <tiles:put name="submenu" type="string" value="accountProductEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpAccount"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpProductBundle" key="account.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpProductBundle" key="account.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function openDepositDictionaries()
                        {
                            window.open("${phiz:calculateActionURL(pageContext, '/deposits/dictionary')}",
                                    'depositDictionary',
                                    "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                        }

                        function setDepositInfo(data)
                        {
                            $("[name=fields(accountId)]").val(data.productId);
                            $("[name=fields(accountName)]").val(data.name);
                        }

                        function doCheckSBOLProduct()
                        {
                            $('#openAccountsButton').removeAttr('disabled');
                        }

                        function doNotCheckSBOLProduct()
                        {
                            $('#openAccountsButton').attr('disabled', 'disabled');
                            $('[name=fields(accountId)]').val('');
                            $("[name=fields(accountName)]").val('');
                        }

                        function checkData()
                        {
                            var accountId = $('[name=fields(accountId)]').val();
                            var checkSBOLProduct = $('[name=checkSBOLProduct]:checked').val();

                            if (checkSBOLProduct == '1' && accountId == '')
                            {
                                alert("Выберете вклад для открытия в СБОЛ");
                                return false;
                            }
                            return $("[name=fields(forSomebodyComplex)]").is(':checked') || checkSegment();
                        }
                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="account.label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="50"/>
                            <div class="clear"></div>
                            <span class="notForComplex">
                                <html:checkbox property="fields(universal)" styleId="universalProduct"/>
                                <label for="universalProduct"><bean:message bundle="pfpProductBundle" key="label.universal.product"/></label>
                            </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="account.label.edit.field.account"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="account" value="${form.fields['accountId']}"/>
                            <html:hidden property="fields(accountId)"/>

                            <input name="checkSBOLProduct" id="openDeposit" type="radio" <c:if test="${empty account}">checked=""</c:if>
                                   value="0" onclick="doNotCheckSBOLProduct();"/> <label for="openDeposit">Не открывать вклад</label>
                            <br/>

                            <input name="checkSBOLProduct" type="radio" <c:if test="${not empty account}">checked=""</c:if> value="1" onclick="doCheckSBOLProduct();"/>
                            <html:text property="fields(accountName)" disabled="true"/>
                            <input id="openAccountsButton" type="button" class="buttWhite" onclick="openDepositDictionaries();"
                                   value="..." <c:if test="${empty account}">disabled=""</c:if>/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:hidden property="fields(forComplex)"/>
                            <html:checkbox property="fields(forSomebodyComplex)" onclick="changeForComplex();" styleId="forSomebodyComplex1" value="true"/>&nbsp;
                            <label for="forSomebodyComplex1"><bean:message bundle="pfpProductBundle" key="account.label.edit.field.forComplex"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="account.label.edit.field.complexKind"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(complexKind)" onchange="selectComplexKind();" styleClass="forComplex">
                                <html:option value="insurance" bundle="pfpProductBundle" key="account.label.edit.field.complexKind.account.insurance"/>
                                <html:option value="fund"      bundle="pfpProductBundle" key="account.label.edit.field.complexKind.account.fund"/>
                                <html:option value="ima"       bundle="pfpProductBundle" key="account.label.edit.field.complexKind.account.fund.ima"/>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductBundle" key="account.label.edit.field.advisableSum"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(advisableSum)">
                                    <html:option value="advisableSumToProduct" bundle="pfpProductBundle" key="account.label.edit.field.advisableSum.advisableSumToProduct"/>
                                    <html:option value="monthlyExpends" bundle="pfpProductBundle" key="account.label.edit.field.advisableSum.monthlyExpends"/>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                    </div>

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
                                <html:text property="fields(QUARTERLY_INVESTminSum)" size="20" maxlength="15" styleClass="moneyField"/>
                                <b><bean:message bundle="pfpProductBundle" key="product.base.minSum.label.field.unit"/></b>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <div class="forAccountInsuranceProduct">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductBundle" key="account.label.edit.field.maxSum"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <b><bean:message bundle="pfpProductBundle" key="account.label.edit.field.maxSum.formula"/></b>
                                <html:text property="fields(sumFactor)" size="20" maxlength="15"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="product.label.income"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.min.prefix"/>&nbsp;</b>
                            <html:text property="fields(minIncome)" size="10" maxlength="5"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.max.prefix"/>&nbsp;</b>
                            <html:text property="fields(maxIncome)" size="10" maxlength="5"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.postfix"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="product.label.income.default"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(defaultIncome)" size="10" maxlength="5"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.postfix"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductBundle" key="product.base.segment.field.name"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <jsp:include page="setSegmentCodeTypeArea.jsp"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="label.universal.enabled"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:radio property="fields(enabled)" value="true"/>
                            <bean:message bundle="pfpProductBundle" key="label.universal.enabled.yes"/>
                            <br/>
                            <html:radio property="fields(enabled)" value="false"/>
                            <bean:message bundle="pfpProductBundle" key="label.universal.enabled.no"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="account.label.edit.field.image"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="account.label.edit.field.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="3"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="productTypeParameters" value="${form.productTypeParameters}"/>
                    <c:set var="diagramParams" value="${productTypeParameters.diagramParameters}"/>
                    <c:if test="${productTypeParameters.useOnDiagram=='true'}">
                        <div class="chartParams">
                            <fieldset>
                                <legend><bean:message key="label.diagram.params" bundle="pfpProductBundle"/></legend>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.diagram.params.axisX" bundle="pfpProductBundle"/>
                                    </tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <html:text name="form" property="field(axisXParam)"/>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.diagram.params.axisY" bundle="pfpProductBundle"/>
                                    </tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <html:text name="form" property="field(axisYParam)"/>
                                    </tiles:put>
                                </tiles:insert>
                            </fieldset>
                       </div>
                    </c:if>
                    <c:set var="tableParams" value="${productTypeParameters.tableParameters}"/>
                    <c:if test="${productTypeParameters.useOnTable=='true'}">
                        <div class="tableParams">
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
                        </div>
                    </c:if>

                    <script type="text/javascript">
                        var notForComplexClassElements      = $(".notForComplex");
                        var notForComplexNaxSumFormulaField = $("[name=fields(sumFactor)]");
                        var forComplex                      = $('[name=fields(forComplex)]');
                        var forComplexCheckBox              = $('[name=fields(forSomebodyComplex)]');
                        var forComplexSpans                 = $("span.forComplex");
                        var forComplexSelects               = $("select.forComplex");
                        var forComplexInsurance             = $(".forAccountInsuranceProduct");
                        var chartParams                     = $(".chartParams");
                        var tableParams                     = $(".tableParams");

                        function changeForComplex()
                        {
                            if (forComplexCheckBox.attr('checked'))
                            {
                                notForComplexClassElements.hide();
                                forComplexSpans.show();
                                forComplexSelects.attr("disabled", "");
                                chartParams.hide();
                                tableParams.hide();
                            }
                            else
                            {
                                notForComplexClassElements.show();
                                forComplexSpans.hide();
                                forComplexSelects.attr("disabled", "disabled");
                                chartParams.show();
                                tableParams.show();
                            }
                            selectComplexKind();
                        }

                        function selectComplexKind()
                        {
                            forComplex.val(forComplexSelects.val());
                            if (forComplexCheckBox.attr('checked') && forComplexSelects.val() == 'insurance')
                            {
                                forComplexInsurance.show();
                            }
                            else
                            {
                                forComplexInsurance.hide();
                                notForComplexNaxSumFormulaField.val("");
                            }

                            if (!forComplexCheckBox.attr('checked'))
                            {
                                forComplex.val("none");
                            }
                        }

                        $(document).ready(function(){changeForComplex();});
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListAccountProductsOperation">
                        <tiles:put name="commandTextKey"    value="account.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="account.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpProductBundle"/>
                        <tiles:put name="action"            value="/pfp/products/account/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditAccountProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="account.button.save"/>
                        <tiles:put name="commandHelpKey"     value="account.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpProductBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="checkData();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
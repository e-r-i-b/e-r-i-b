<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/ima/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <tiles:insert definition="editPFPProduct">
        <tiles:put name="submenu" type="string" value="imaProductEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpIMA"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpProductBundle" key="ima.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpProductBundle" key="ima.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function openIMADictionaries()
                        {
                            window.open("${phiz:calculateActionURL(pageContext, '/ima/dictionary')}",
                                    'depositDictionary',
                                    "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                        }

                        function setIMAInfo(data)
                        {
                            $("[name=fields(imaId)]").val(data.type);
                            $("[name=fields(imaAdditionalId)]").val(data.subType);
                            $("[name=fields(imaName)]").val(data.name);
                        }

                        function doCheckSBOLProduct()
                        {
                            $('#openIMAButton').removeAttr('disabled');
                        }

                        function doNotCheckSBOLProduct()
                        {
                            $('#openIMAButton').attr('disabled', 'disabled');
                            $('[name=fields(imaId)]').val('');
                            $('[name=fields(imaAdditionalId)]').val('');
                            $("[name=fields(imaName)]").text('');
                        }

                        function checkData()
                        {
                            var imaId = $('[name=fields(imaId)]').val();
                            var checkSBOLProduct = $('[name=checkSBOLProduct]:checked').val();

                            if (checkSBOLProduct == '1' && imaId == '')
                            {
                                alert("Выберете ОМС для открытия в СБОЛ");
                                return false;
                            }
                            return true;
                        }
                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="ima.label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="50" maxlength="250"/>
                            <div class="clear"></div>
                            <span class="notForComplex">
                                <html:checkbox property="fields(universal)"/>
                                <bean:message bundle="pfpProductBundle" key="label.universal.product"/>
                            </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="ima.label.edit.field.ima"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="ima" value="${form.fields['imaId']}"/>
                            <html:hidden property="fields(imaId)"/>
                            <html:hidden property="fields(imaAdditionalId)"/>

                            <input name="checkSBOLProduct" type="radio" <c:if test="${empty ima}">checked=""</c:if>
                                   value="0" onclick="doNotCheckSBOLProduct();"/> Не открывать ОМС
                            <br/>

                            <input name="checkSBOLProduct" type="radio" <c:if test="${not empty ima}">checked=""</c:if> value="1" onclick="doCheckSBOLProduct();"/>
                            <html:text property="fields(imaName)" disabled="true"/>
                            <input id="openIMAButton" type="button" class="buttWhite"onclick="openIMADictionaries();"
                                   value="..." <c:if test="${empty ima}">disabled=""</c:if>/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:hidden property="fields(forComplex)"/>
                            <html:checkbox property="fields(forSomebodyComplex)" value="true" onchange="changeForComplex();"/>&nbsp;<bean:message bundle="pfpProductBundle" key="ima.label.edit.field.forComplex"/>
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
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="fields(QUARTERLY_INVESTminSum)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                                <b><bean:message bundle="pfpProductBundle" key="product.base.minSum.label.field.unit"/></b>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

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

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="form.edit.fields.risk"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(riskId)" styleClass="required">
                                <c:forEach var="risk" items="${form.fields['riskList']}">
                                    <html:option value="${risk.id}">${risk.name}</html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="form.edit.fields.period"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(periodId)" styleClass="required">
                                <c:forEach var="period" items="${form.fields['periodList']}">
                                    <html:option value="${period.id}">${period.period}</html:option>
                                </c:forEach>
                            </html:select>
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
                            <bean:message bundle="pfpProductBundle" key="ima.label.edit.field.image"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductBundle" key="ima.label.edit.field.description"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:textarea property="fields(description)" cols="58" rows="3"/>
                            </tiles:put>
                        </tiles:insert>
                     </div>
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
                        var forComplexField = $('[name=fields(forComplex)]');
                        var forComplexCheckbox = $('[name=fields(forSomebodyComplex)]');
                        var forComplexClassSelector = $(".notForComplex");
                        var chartParams                     = $(".chartParams");
                        var tableParams                     = $(".tableParams");

                        function changeForComplex()
                        {
                            if (forComplexCheckbox.attr('checked'))
                            {
                                forComplexField.val("ima");
                                forComplexClassSelector.hide();
                                chartParams.hide();
                                tableParams.hide();
                            }
                            else
                            {
                                forComplexField.val("none");
                                forComplexClassSelector.show();
                                chartParams.show();
                                tableParams.show();
                            }
                        }

                        function checkData()
                        {
                            return forComplexCheckbox.is(':checked') || checkSegment();
                        }

                        $(document).ready(function(){changeForComplex();});
                    </script>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListIMAProductsOperation">
                        <tiles:put name="commandTextKey"    value="ima.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="ima.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpProductBundle"/>
                        <tiles:put name="action"            value="/pfp/products/ima/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditIMAProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="ima.button.save"/>
                        <tiles:put name="commandHelpKey"     value="ima.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpProductBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="checkData();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
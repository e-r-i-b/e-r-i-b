<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/trustManaging/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="editPFPProduct">
        <tiles:put name="submenu" type="string" value="trustManagingProductEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function checkData()
                        {
                            var eachResult = true;
                            $('.required').each(function(){
                                if ($(this).val() == '')
                                {
                                    eachResult = false;
                                    alert('<bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.message.fill.required"/>');
                                    return false;
                                }
                            });
                            if (!eachResult)
                                return false;

                            if (getImageObject('').isEmpty())
                            {
                                alert('<bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.message.fill.required"/>');
                                return false;
                            }
                            return true;
                        }
                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="50" maxlength="250" styleClass="required"/>
                            <div class="clear"></div>
                            <html:checkbox property="fields(universal)"/>
                            <bean:message bundle="pfpProductBundle" key="label.universal.product"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend><bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.minSum"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.minSum.START_CAPITAL"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="fields(START_CAPITALminSum)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.minSum.unit"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.minSum.QUARTERLY_INVEST"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="fields(QUARTERLY_INVESTminSum)" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                                <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.minSum.unit"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.income"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.income.min.prefix"/>&nbsp;
                            <html:text property="fields(minIncome)" size="10" maxlength="5"/>&nbsp;
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.income.max.prefix"/>&nbsp;
                            <html:text property="fields(maxIncome)" size="10" maxlength="5"/>&nbsp;
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.income.postfix"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.income.default"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(defaultIncome)" size="10" maxlength="5" styleClass="required"/>&nbsp;
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.income.postfix"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.risk"/>
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
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.period"/>
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

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.segment.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <jsp:include page="setSegmentCodeTypeArea.jsp"/>
                        </tiles:put>
                    </tiles:insert>

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
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.image"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpTrustManagingProductBundle" key="form.edit.fields.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="3" styleClass="required"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="productTypeParameters" value="${form.productTypeParameters}"/>
                    <c:set var="diagramParams" value="${productTypeParameters.diagramParameters}"/>
                    <c:if test="${productTypeParameters.useOnDiagram=='true'}">
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
                    </c:if>
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
                    <tiles:insert definition="clientButton" flush="false" operation="ListTrustManagingProductOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpTrustManagingProductBundle"/>
                        <tiles:put name="action"            value="/pfp/products/trustManaging/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditTrustManagingProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpTrustManagingProductBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="checkData();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
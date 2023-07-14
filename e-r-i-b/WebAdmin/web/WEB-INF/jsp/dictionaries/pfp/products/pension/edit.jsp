<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/pension/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <tiles:insert definition="editPFPProduct">
        <tiles:put name="submenu" type="string" value="pensionProductEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpAccount"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpPensionBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpPensionBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function checkData()
                        {
                            var eachResult = true;
                            $('.required').each(function(){
                                if ($(this).val() == '')
                                {
                                    eachResult = false;
                                    alert('<bean:message bundle="pfpPensionBundle" key="form.edit.fields.message.required"/>');
                                    return false;
                                }
                            });
                            if (!eachResult)
                                return false;

                            return checkSegment();
                        }
                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="50" styleClass="required" maxlength="50"/>
                            <div class="clear"></div>
                            <span class="notForComplex">
                                <html:checkbox property="fields(universal)" styleId="universaProduct"/>
                                <label for="universaProduct"><bean:message bundle="pfpProductBundle" key="label.universal.product"/></label>
                            </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.pensionFund"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(pensionFundId)" styleClass="required">
                                <c:forEach var="pensionFund" items="${form.fields['pensionFundList']}">
                                    <html:option value="${pensionFund.id}">${pensionFund.name}</html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.fee.entry"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(entryFee)" size="10" styleClass="required"/>
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.fee.entry.unit"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.fee.quarterly"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(quarterlyFee)" size="10" styleClass="required"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.fee.quarterly.unit"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.period"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.period.min"/>&nbsp;
                            <html:text property="fields(minPeriod)" size="10" maxlength="5" styleClass="required"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.period.max"/>&nbsp;
                            <html:text property="fields(maxPeriod)" size="10" maxlength="5" styleClass="required"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.period.unit"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.period.default"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(defaultPeriod)" size="10" maxlength="5" styleClass="required"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.period.default.unit"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.income"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.income.min"/>&nbsp;
                            <html:text property="fields(minIncome)" size="10" maxlength="5"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.income.max"/>&nbsp;
                            <html:text property="fields(maxIncome)" size="10" maxlength="5"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.income.unit"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.income.default"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(defaultIncome)" size="10" maxlength="5" styleClass="required"/>&nbsp;
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.income.default.unit"/>
                        </tiles:put>
                    </tiles:insert>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpPensionBundle" key="form.edit.fields.targetGroup"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <jsp:include page="../setSegmentCodeTypeArea.jsp"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="label.universal.enabled"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="fields(enabled)" styleId="enabledYes" value="true"/>
                            <label for="enabledYes"><bean:message bundle="pfpProductBundle" key="label.universal.enabled.yes"/></label>
                            <br/>
                            <html:radio property="fields(enabled)" styleId="enabledNo" value="false"/>
                            <label for="enabledNo"><bean:message bundle="pfpProductBundle" key="label.universal.enabled.no"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.icon"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionBundle" key="form.edit.fields.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="3" styleClass="required"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListPensionProductOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpPensionBundle"/>
                        <tiles:put name="action"            value="/pfp/products/pension/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditPensionProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpPensionBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="checkData();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
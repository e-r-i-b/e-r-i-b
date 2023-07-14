<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/period/investment/edit" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fields" value="${form.fields}"/>
    <tiles:insert definition="editPFPInvestmentPeriod">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="pfpInvestmentPeriodBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpInvestmentPeriodBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function validateEditPeriodForm()
                        {
                            var eachResult = true;
                            $('.required').each(function(){
                                if ($(this).val() == '')
                                {
                                    eachResult = false;
                                    alert('<bean:message bundle="pfpInvestmentPeriodBundle" key="form.edit.fields.required"/>');
                                    return false;
                                }
                            });
                            return eachResult;
                        }
                    </script>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInvestmentPeriodBundle" key="form.edit.fields.period"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(period)" size="58" maxlength="50" styleClass="required"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListInvestmentPeriodOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpInvestmentPeriodBundle"/>
                        <tiles:put name="action"            value="/pfp/period/investment/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditInvestmentPeriodOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpInvestmentPeriodBundle"/>
                        <tiles:put name="isDefault"          value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="validateEditPeriodForm();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
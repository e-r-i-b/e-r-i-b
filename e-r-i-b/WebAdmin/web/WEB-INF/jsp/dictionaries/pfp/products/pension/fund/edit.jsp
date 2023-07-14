<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/pension/fund/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fields" value="${form.fields}"/>
    <tiles:insert definition="editPFPPensionFund">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="pfpPensionFundBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpPensionFundBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function validateEditFundForm()
                        {
                            if ($('[name=fields(name)]').val() == '')
                            {
                                alert('<bean:message bundle="pfpPensionFundBundle" key="form.edit.fields.name.message.required"/>');
                                return false;
                            }
                            return true;
                        }
                    </script>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionFundBundle" key="form.edit.fields.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="58" maxlength="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpPensionFundBundle" key="form.edit.fields.image"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListPensionFundOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpPensionFundBundle"/>
                        <tiles:put name="action"            value="/pfp/products/pension/fund/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditPensionFundOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpPensionFundBundle"/>
                        <tiles:put name="isDefault"          value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="validateEditFundForm();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
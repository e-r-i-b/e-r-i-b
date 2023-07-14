<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/loanKind/edit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <tiles:insert definition="editPFPProduct">
        <tiles:put name="submenu" type="string" value="loanKindProductEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpDictionary"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="58" maxlength="256" styleClass="required"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.amount"/>
                        </tiles:put>
                        <tiles:put name="data">
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.amount.from"/></span>&nbsp;
                            <html:text property="fields(fromAmount)" size="7" maxlength="15" styleClass="moneyField required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.amount.to"/></span>&nbsp;
                            <html:text property="fields(toAmount)" size="7" maxlength="15" styleClass="moneyField required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.amount.unit"/></span>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.period"/>
                        </tiles:put>
                        <tiles:put name="data">
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.period.from"/></span>&nbsp;
                            <html:text property="fields(fromPeriod)" size="7" maxlength="3" styleClass="required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.period.to"/></span>&nbsp;
                            <html:text property="fields(toPeriod)" size="7" maxlength="3" styleClass="required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.period.unit"/></span>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.period.default"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(defaultPeriod)" size="7" maxlength="3" styleClass="required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.period.unit"/></span>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.rate"/>
                        </tiles:put>
                        <tiles:put name="data">
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.rate.from"/></span>&nbsp;
                            <html:text property="fields(fromRate)" size="7" maxlength="7" styleClass="required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.rate.to"/></span>&nbsp;
                            <html:text property="fields(toRate)" size="7" maxlength="7" styleClass="required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.rate.unit"/></span>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.rate.default"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(defaultRate)" size="7" maxlength="7" styleClass="required"/>
                            &nbsp;<span class="bold"><bean:message bundle="pfpProductBundle" key="loanKind.label.edit.field.rate.unit"/></span>&nbsp;
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.image"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListLoanKindProductsOperation">
                        <tiles:put name="commandTextKey"    value="loanKind.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="loanKind.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpProductBundle"/>
                        <tiles:put name="action"            value="/pfp/loanKind/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditLoanKindProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="loanKind.button.save"/>
                        <tiles:put name="commandHelpKey"     value="loanKind.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpProductBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="isDefault"          value="true"/>
                        <tiles:put name="validationFunction">
                            function(){
                                var eachResult = true;
                                $('.required').each(function(){
                                    if ($(this).val() == '')
                                    {
                                        eachResult = false;
                                        alert('<bean:message bundle="pfpProductBundle" key="loanKind.message.required"/> ' + $(this).parent().parent().find('.LabelAll').text().trim());
                                        return false;
                                    }
                                });
                                if (!eachResult)
                                    return false;

                                if (getImageObject('').isEmpty())
                                {
                                    alert('<bean:message bundle="pfpProductBundle" key="loanKind.message.required"/> <bean:message bundle="pfpProductBundle" key="loanKind.label.edit.image"/>');
                                    return false;
                                }
                                return true;
                            }
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

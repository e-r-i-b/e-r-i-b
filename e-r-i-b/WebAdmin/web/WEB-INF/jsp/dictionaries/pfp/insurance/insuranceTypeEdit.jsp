<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/insurance/typeEdit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
    <tiles:insert definition="editPFPInsuranceProduct">
        <tiles:put name="submenu" type="string" value="insuranceTypeEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpDictionary"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpInsuranceBundle" key="type.label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpInsuranceBundle" key="type.label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="type.label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="58" maxlength="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="type.label.edit.field.parentName"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <script type="text/javascript">
                                function openInsuranceTypeDictionaries()
                                {
                                    window.open("${phiz:calculateActionURL(pageContext, '/pfp/insurance/typeList')}?action=parentDictionary",
                                            'typeDictionary', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                                }

                                function setInsuranceType(data)
                                {
                                    $("[name=fields(parentId)]").val(data["id"]);
                                    $("[name=fields(parentName)]").val(data["name"]);
                                }
                            </script>
                            <html:text property="fields(parentName)" size="54" readonly="true"/>
                            <html:hidden property="fields(parentId)"/>
                            <input type="button" class="buttWhite" onclick="openInsuranceTypeDictionaries();" value="..."/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="type.label.edit.field.image"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="type.label.edit.field.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="3"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListInsuranceTypeOperation">
                        <tiles:put name="commandTextKey"    value="type.button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="type.button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpInsuranceBundle"/>
                        <tiles:put name="action"            value="/pfp/insurance/typeList.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditInsuranceTypeOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandTextKey"     value="type.button.save"/>
                        <tiles:put name="commandHelpKey"     value="type.button.save.help"/>
                        <tiles:put name="bundle"             value="pfpInsuranceBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
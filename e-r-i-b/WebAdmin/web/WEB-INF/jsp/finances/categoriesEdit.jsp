<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/finances/categories/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="operations">
        <bean:write name="form" property="field(operations)"/>
    </c:set>
    <tiles:insert definition="editCardOperationCategory">
        <tiles:put name="submenu" type="string" value="ListCardOperationCategory"/>
        <c:set var="isDefault">
            <bean:write name="form" property="field(isDefault)"/>
        </c:set>
        <c:set var="isInternal">
            <bean:write name="form" property="field(forInternalOperations)"/>
        </c:set>

        <tiles:put name="data" type="string">
            <c:if test="${operations}">
                <script type="text/javascript">
                    function confirmSaveCategory()
                    {
                        return (!isDataChanged() || confirm("К данной категории привязаны операции. Сохранить изменения?"));
                    }
                </script>
            </c:if>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message bundle="financesOptionsBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="financesOptionsBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="financesOptionsBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="financesOptionsBundle" key="label.edit.transfer"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <c:if test="${isInternal}">
                                <span class="bold"><bean:message bundle="financesOptionsBundle" key="label.category.type.internalTransfer"/></span>
                                <br/>
                            </c:if>
                            <html:checkbox name="form" property="field(transfer)"><bean:message bundle="financesOptionsBundle" key="label.category.type.transfer"/></html:checkbox>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="financesOptionsBundle" key="label.edit.type"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(type)" value="true" disabled="${isDefault}"><bean:message bundle="financesOptionsBundle" key="label.category.type.income"/></html:radio>
                            <html:radio property="field(type)" value="false" disabled="${isDefault}"><bean:message bundle="financesOptionsBundle" key="label.category.type.outcome"/></html:radio>
                            <html:hidden property="field(isDefault)"/>
                            <html:hidden property="field(forInternalOperations)"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="financesOptionsBundle" key="label.edit.visibility"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="field(visibility)" value="true"><bean:message bundle="financesOptionsBundle" key="label.edit.visibility.visible"/></html:radio>
                            <html:radio property="field(visibility)" value="false"><bean:message bundle="financesOptionsBundle" key="label.edit.visibility.hidden"/></html:radio>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="financesOptionsBundle" key="label.id.mAPI"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text name="form" property="field(idInmAPI)" size="30" maxlength="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <c:choose>
                        <c:when test="${isDefault}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="financesOptionsBundle" key="label.annotation"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <bean:message bundle="financesOptionsBundle" key="label.annotation.text"/>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:when test="${isInternal}">
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="financesOptionsBundle" key="label.edit.mcc"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <html:textarea disabled="true" style="height: 80px;width: 320px;" property="field(mcc)"/>
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="financesOptionsBundle" key="label.edit.mcc"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <html:textarea style="height: 80px;width: 320px;" property="field(mcc)"/>
                                </tiles:put>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="financesOptionsBundle" key="label.color"/>:
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="financesOptionsBundle" key="label.color.hint"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <span class="bold">#</span>
                            <html:text name="form" property="field(color)" size="6" maxlength="6"/>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditCardOperationCategoryOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle"         value="financesOptionsBundle"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <c:if test="${operations}">
                            <tiles:put name="validationFunction" value="confirmSaveCategory();"/>
                        </c:if>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="ListCardOperationCategoryOperation">
                       <tiles:put name="commandTextKey" value="button.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.cancel"/>
                       <tiles:put name="bundle"         value="financesOptionsBundle"/>
                       <tiles:put name="action"         value="/finances/categories/list.do"/>
                    </tiles:insert>

                    <c:if test="${not empty form.id}">
                        <tiles:insert definition="languageSelectForEdit" operation="EditCardOperationCategoryResourcesOperation" flush="false">
                            <tiles:put name="selectId" value="chooseLocale"/>
                            <tiles:put name="entityId" value="${form.id}"/>
                            <tiles:put name="styleClass" value="float"/>
                            <tiles:put name="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/finances/categories/language/save')}"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
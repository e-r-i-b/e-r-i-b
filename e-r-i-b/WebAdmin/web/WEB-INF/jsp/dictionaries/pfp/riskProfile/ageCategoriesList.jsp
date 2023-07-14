<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/riskProfile/ageCategory/list">
    <tiles:insert definition="listPFPRiskProfile">
        <tiles:put name="submenu" type="string" value="ageCategoryProductList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.pageTitle"/>
        </tiles:put>
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditAgeCategoryOperation">
                <tiles:put name="commandTextKey"    value="ageCategory.button.add"/>
                <tiles:put name="commandHelpKey"    value="ageCategory.button.add.help"/>
                <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                <tiles:put name="action"            value="/pfp/riskProfile/ageCategory/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <%-- данные --%>
        <tiles:put name="data" type="string">
            <c:if test="${not phiz:isConcertedAgeCategories()}">
                <table cellpadding="4" width="100%">
                    <tbody>
                        <tr>
                            <td align="center" class="messageTab">
                                <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.tableWarning"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="ageCategoryProductList"/>
                <tiles:put name="grid">
                    <tiles:importAttribute/>
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpRiskProfileBundle">
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="ageCategory.label.table.age" property="minAge">
                            <sl:collectionItemParam id="value">
                                <c:set var="listElementMinAge" value="${listElement.minAge}"/>
                                <c:set var="listElementMaxAge" value="${listElement.maxAge}"/>
                                <phiz:link action="/pfp/riskProfile/ageCategory/edit" operationClass="EditAgeCategoryOperation">
                                    <phiz:param name="id" value="${listElement.id}"/>
                                    <c:choose>
                                        <c:when test="${empty listElementMinAge}">
                                            <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.maxAge"/>&nbsp;
                                            <c:out value="${listElementMaxAge}"/>
                                            &nbsp;${phiz:numeralDeclension(listElementMaxAge, "", "года", "лет", "лет")}
                                        </c:when>
                                        <c:when test="${empty listElementMaxAge}">
                                            <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.infinityAge"/>&nbsp;
                                            <c:out value="${listElementMinAge}"/>
                                            &nbsp;${phiz:numeralDeclension(listElementMinAge, "", "года", "лет", "лет")}
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.minAge"/>&nbsp;
                                            <c:out value="${listElementMinAge}"/>&nbsp;
                                            <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.maxAge"/>&nbsp;
                                            <c:out value="${listElementMaxAge}"/>
                                            &nbsp;${phiz:numeralDeclension(listElementMaxAge, "", "года", "лет", "лет")}
                                        </c:otherwise>
                                    </c:choose>
                                </phiz:link>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="ageCategory.label.table.weight" property="weight"/>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function doRemove()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection("selectedIds", '<bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.checkSelection"/>');
                            }
                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.checkSelection"/>') ||
                                    (!checkOneSelection("selectedIds", '<bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.checkOneSelection"/>')))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/pfp/riskProfile/ageCategory/edit')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditAgeCategoryOperation">
                            <tiles:put name="commandTextKey"    value="ageCategory.button.edit"/>
                            <tiles:put name="commandHelpKey"    value="ageCategory.button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveAgeCategoryOperation">
                            <tiles:put name="commandKey"            value="button.remove"/>
                            <tiles:put name="commandTextKey"        value="ageCategory.button.remove"/>
                            <tiles:put name="commandHelpKey"        value="ageCategory.button.remove.help"/>
                            <tiles:put name="bundle"                value="pfpRiskProfileBundle"/>
                            <tiles:put name="validationFunction"    value="doRemove();"/>
                            <tiles:put name="confirmText"><bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.table.removeQuestion"/></tiles:put>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.tableEmpty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

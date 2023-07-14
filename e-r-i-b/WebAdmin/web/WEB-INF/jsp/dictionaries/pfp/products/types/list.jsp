<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/types/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="listPFPProductType">
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="pfpProductTypeBundle" key="form.list.page.title"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="listPFPProductType"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="pfpProductTypeBundle">
                        <sl:collectionParam id="selectType"     value="checkbox"/>
                        <sl:collectionParam id="selectName"     value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>
                        <sl:collectionItem title="form.list.table.column.type">
                            <c:if test="${not empty listElement}">
                                <phiz:link action="/pfp/products/types/edit" operationClass="EditProductTypeParametersOperation">
                                    <phiz:param name="id" value="${listElement.id}"/>
                                    <bean:message bundle="pfpProductTypeBundle" key="type.${listElement.type}"/>
                                </phiz:link>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.column.name" property="name"/>
                        <sl:collectionItem title="form.list.table.column.use">
                            <sl:collectionItemParam id="value">
                                <c:if test="${not empty listElement}">
                                    <bean:message bundle="pfpProductTypeBundle" key="form.list.table.column.use.${listElement.use}"/>
                                </c:if>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.column.targetGroup">
                            <sl:collectionItemParam id="value">
                                <c:if test="${not empty listElement}">
                                    <c:set var="targetGroup" value=""/>
                                    <c:set var="delimeter" value=""/>
                                    <logic:iterate id="segment" collection="${listElement.targetGroup}">
                                        <c:set var="targetGroup">${targetGroup}${delimeter}<bean:message bundle="pfpProductTypeBundle" key="targetGroup.${segment}"/></c:set>
                                        <c:set var="delimeter" value=",&nbsp;"/>
                                    </logic:iterate>
                                    ${targetGroup}
                                </c:if>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                        <sl:collectionItem title="form.list.table.column.useOnView">
                            <sl:collectionItemParam id="value">
                                <c:if test="${not empty listElement}">
                                    <bean:message bundle="pfpProductTypeBundle" key="form.list.table.column.useOnDiagram.${listElement.useOnDiagram}.useOnTable.${listElement.useOnTable}"/>
                                </c:if>
                            </sl:collectionItemParam>
                        </sl:collectionItem>
                    </sl:collection>

                    <tiles:put name="buttons">
                        <script type="text/javascript">
                            function goToEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", '<bean:message bundle="pfpProductTypeBundle" key="form.list.message.selection.empty"/>') ||
                                   (!checkOneSelection("selectedIds", '<bean:message bundle="pfpProductTypeBundle" key="form.list.message.selection.many"/>')))
                                    return;

                                var url = "${phiz:calculateActionURL(pageContext,'/pfp/products/types/edit')}";
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = url + "?id=" + id;
                            }
                        </script>
                        <tiles:insert definition="clientButton" flush="false" operation="EditProductTypeParametersOperation">
                            <tiles:put name="commandTextKey"    value="button.edit"/>
                            <tiles:put name="commandHelpKey"    value="button.edit.help"/>
                            <tiles:put name="bundle"            value="pfpProductTypeBundle"/>
                            <tiles:put name="onclick"           value="goToEdit();"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="pfpProductTypeBundle" key="form.list.message.data.empty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>

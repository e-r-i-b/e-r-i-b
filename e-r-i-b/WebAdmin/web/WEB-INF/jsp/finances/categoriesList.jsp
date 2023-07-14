<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/finances/categories/list" onsubmit="return setEmptyAction(event);">
    <c:set var="standalone" value="${empty param['action']}"/>
    <c:choose>
        <c:when test="${standalone}">
             <c:set var="layoutDefinition" value="listCardOperationCategory"/>
        </c:when>
        <c:otherwise>
            <c:set var="layoutDefinition" value="dictionary"/>
        </c:otherwise>
    </c:choose>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="${layoutDefinition}">
        <tiles:put name="submenu" type="string" value="ListCardOperationCategory"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="financesOptionsBundle" key="label.pageTitle"/>
        </tiles:put>
        <tiles:put name="menu" type="string">
            <c:if test="${standalone}">
                <tiles:insert definition="clientButton" flush="false" operation="EditCardOperationCategoryOperation">
                    <tiles:put name="commandTextKey" value="button.add"/>
                    <tiles:put name="commandHelpKey" value="button.add"/>
                    <tiles:put name="bundle"         value="financesOptionsBundle"/>
                    <tiles:put name="action"         value="/finances/categories/edit.do"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${standalone}">
                            <tiles:insert definition="clientButton" flush="false" operation="EditCardOperationCategoryOperation">
                                <tiles:put name="commandTextKey" value="button.edit"/>
                                <tiles:put name="commandHelpKey" value="button.edit"/>
                                <tiles:put name="bundle" value="financesOptionsBundle"/>
                                <tiles:put name="onclick" value="doEdit();"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false" operation="RemoveCardOperationCategoryOperation">
                                <tiles:put name="commandKey"     value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove"/>
                                <tiles:put name="bundle"         value="financesOptionsBundle"/>
                                <tiles:put name="validationFunction">
                                    doRemove()
                                </tiles:put>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add"/>
                                <tiles:put name="commandHelpKey" value="button.add"/>
                                <tiles:put name="bundle" value="financesOptionsBundle"/>
                                <tiles:put name="onclick" value="doSelect();"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="financesOptionsBundle"/>
                                <tiles:put name="onclick" value="closeWindow();"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="item" bundle="financesOptionsBundle" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.name">
                            <c:choose>
                                <c:when test="${item.isDefault}">
                                    <b><c:out value="${item.name}"/></b>
                                    <i><bean:message key="label.isDefault" bundle="financesOptionsBundle"/></i>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${item.name}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.transfer">
                            <c:choose>
                                <c:when test="${item.forInternalOperations}">
                                    <bean:message key="label.type.internalTransfer" bundle="financesOptionsBundle"/>
                                </c:when>
                                <c:when test="${item.isTransfer}">
                                    <bean:message key="label.type.transfer" bundle="financesOptionsBundle"/>
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.type" >
                            <c:choose>
                                <c:when test="${item.income}">
                                    <bean:message key="label.category.type.income" bundle="financesOptionsBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <bean:message key="label.category.type.outcome" bundle="financesOptionsBundle"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.mcc">
                            ${phiz:getMCCByCategoryAsString(item.externalId)}
                        </sl:collectionItem>
                        <sl:collectionItem title="label.id.mAPI">
                            <c:out value="${item.idInmAPI}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.visibility">
                            <c:choose>
                                <c:when test="${item.visible}">
                                    <bean:message key="label.visibility.visible" bundle="financesOptionsBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <bean:message key="label.visibility.hidden" bundle="financesOptionsBundle"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        <c:choose>
            <c:when test="${standalone}">
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/finances/categories/edit')}"/>
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", 'ƒл€ редактировани€ нужно выбрать только одну категорию.') || (!checkOneSelection("selectedIds", 'ƒл€ редактировани€ нужно выбрать только одну категорию.')))
                        return;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = '${url}?id=' + id;
                }
                function doRemove()
                {
                    checkIfOneItem("selectedIds");
                    if (checkSelection('selectedIds', 'ƒл€ удалени€ выберите хот€ бы одну категорию.'))
                    {
                        return confirm("¬ы действительно хотите удалить выбранные категории операций?");
                    }
                    return false;
                }
            </c:when>
            <c:otherwise>
                function closeWindow()
                {
                    window.close();
                }
                function doSelect()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkSelection("selectedIds", '¬ыберите категорию.') || (!checkOneSelection("selectedIds", '¬ы можете выбрать только одну категорию.')))
                        return;
                    var checkedItem = $('[name="selectedIds"]:checked');
                    window.opener.setCategoryInfo(checkedItem.val(), checkedItem.parent().parent().children()[1].innerHTML);
                    window.close();
                }
            </c:otherwise>
        </c:choose>

    </script>
</html:form>
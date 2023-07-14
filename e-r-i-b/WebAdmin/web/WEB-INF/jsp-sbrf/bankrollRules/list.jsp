<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html:form action="/ermb/rules/list" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
     <tiles:importAttribute/>
     <tiles:insert definition="ermbMain">
         <tiles:put name="submenu" type="string" value="ErmbBankrollProductRules"/>
         <tiles:put name="pageTitle" type="string">
             <bean:message key="ermb.main" bundle="ermbBundle"/>
         </tiles:put>
         <tiles:put name="menu" type="string">
             <tiles:insert definition="clientButton" flush="false" operation="BankrollProductRuleEditOperation">
                 <tiles:put name="commandTextKey" value="button.add"/>
                 <tiles:put name="commandHelpKey" value="button.add"/>
                 <tiles:put name="bundle" value="commonBundle"/>
                 <tiles:put name="action"  value="/ermb/rules/edit.do"/>
                 <tiles:put name="viewType" value="blueBorder"/>
             </tiles:insert>
         </tiles:put>

         <%-- данные --%>
         <tiles:put name="data" type="string">
             <script type="text/javascript">
                 var addUrl = "${phiz:calculateActionURL(pageContext,'/ermb/rules/edit')}";

                 function doEdit()
                 {
                     checkIfOneItem("selectedIds");

                     if (!checkOneSelection("selectedIds", "Пожалуйста, выберите одну запись"))
                     {
                         return;
                     }

                     window.location = addUrl + "?id=" + getRadioValue(document.getElementsByName("selectedIds"));
                 }
             </script>
            <div class="ListBankrollProductRules">
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id"    value="ListBankrollProductRules"/>
                    <tiles:put name="text"  value="Список правил"/>
                    <tiles:put name="emptyMessage">Не найдено ни одного правила.</tiles:put>
                    <tiles:put name="buttons">

                        <tiles:insert definition="commandButton" operation="BankrollProductRuleActivateOrDeactivateOperation" flush="false">
                            <tiles:put name="commandKey" value="button.activate"/>
                            <tiles:put name="commandTextKey" value="button.activate"/>
                            <tiles:put name="commandHelpKey" value="button.activate"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection("selectedIds", "Для выполнения действия необходимо указать одну или несколько записей из списка");
                                }
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="commandButton" operation="BankrollProductRuleActivateOrDeactivateOperation" flush="false">
                            <tiles:put name="commandKey" value="button.deactivate"/>
                            <tiles:put name="commandTextKey" value="button.deactivate"/>
                            <tiles:put name="commandHelpKey" value="button.deactivate"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection("selectedIds", "Для выполнения действия необходимо указать одну или несколько записей из списка");
                                }
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="clientButton"  operation="BankrollProductRuleEditOperation" flush="false">
                            <tiles:put name="commandTextKey" value="button.edit"/>
                            <tiles:put name="commandHelpKey" value="button.edit"/>
                            <tiles:put name="bundle"         value="ermbBundle"/>
                            <tiles:put name="onclick"        value="doEdit()"/>
                        </tiles:insert>

                        <tiles:insert definition="commandButton"  operation= "BankrollProductRuleRemoveOperation" flush="false">
                            <tiles:put name="commandKey" value="button.remove"/>
                            <tiles:put name="commandTextKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove"/>
                            <tiles:put name="bundle" value="ermbBundle"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                            <tiles:put name="validationFunction">
                                function()
                                {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection("selectedIds", "Для выполнения действия необходимо указать одну или несколько записей из списка");
                                }
                            </tiles:put>
                            <tiles:put name="confirmText">Удалить выбранные правила?</tiles:put>
                        </tiles:insert>
                    </tiles:put>

                    <tiles:put name="grid">
                         <sl:collection id="rule" name="form" property="rules" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                             <sl:collectionItem title="Название" value="${fn:substring(rule.name, 0, 50)}">
                                <sl:collectionItemParam id="action" value="/ermb/rules/edit?id=${rule.id}"/>
                             </sl:collectionItem>
                             <sl:collectionItem title="Условия исполнения" value="${fn:substring(rule.condition, 0, 200)}"/>

                             <c:choose>
                                 <c:when test="${phiz:isCommonAttributeUseInProductsAvailable()}">
                                    <sl:collectionItem title="Подключенные продукты и признаки" value="${fn:substring(rule.productsNotification, 0, 200)}"/>
                                 </c:when>
                                 <c:otherwise>
                                     <sl:collectionItem title="Видимость" property="productsVisibility"/>
                                     <sl:collectionItem title="Оповещения" property="productsNotification"/>
                                 </c:otherwise>
                             </c:choose>

                             <c:set var="isActive">
                                 <c:choose>
                                     <c:when test="${rule.status}">включено</c:when>
                                     <c:otherwise>выключено</c:otherwise>
                                 </c:choose>
                             </c:set>
                             <sl:collectionItem title="Включено">${isActive}</sl:collectionItem>
                         </sl:collection>
                    </tiles:put>
                 </tiles:insert>
             </div>

         </tiles:put>
    </tiles:insert>

</html:form>
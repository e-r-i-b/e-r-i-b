<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/schemes/list">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="standalone" value="${not form.filters.replication}"/>

    <c:choose>
        <c:when test="${standalone}">
             <c:set var="layoutDefinition" value="schemesMain"/>
        </c:when>
        <c:otherwise>
            <c:set var="layoutDefinition" value="dictionary"/>
        </c:otherwise>
    </c:choose>
	<tiles:insert definition="${layoutDefinition}">
        <tiles:put name="submenu" type="string" value="List"/>
		<tiles:put name="pageTitle" type="string">
			<bean:message key="list.title" bundle="schemesBundle"/>
		</tiles:put>
        <c:if test="${standalone}">
            <tiles:put name="menu" type="string">
                <tiles:insert definition="clientButton" flush="false" operation="EditSchemeOperation">
                    <tiles:put name="commandTextKey" value="button.add.person"/>
                    <tiles:put name="commandHelpKey" value="button.add.person"/>
                    <tiles:put name="bundle" value="schemesBundle"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="action" value="/schemes/user/edit?scope=user"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false" operation="EditSchemeOperation">
                    <tiles:put name="commandTextKey" value="button.add.employee"/>
                    <tiles:put name="commandHelpKey" value="button.add.employee"/>
                    <tiles:put name="bundle" value="schemesBundle"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="action" value="/schemes/employee/edit?scope=employee"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </tiles:put>
        </c:if>

		<tiles:put name="data" type="string">
            <c:if test="${not standalone}">
               <c:set var="currentNodeId" value="${phiz:getCurrentNode().id}"/>
               <tiles:insert definition="paymentForm" flush="false">
                   <tiles:put name="name" value="Номера блоков для репликации"/>
                   <tiles:put name="additionalStyle" value="${additionalStyle}"/>
                   <tiles:put name="alignTable" value="${formAlign}"/>
                   <tiles:put name="tableStyle" value="tableStyleHeight"/>
                   <tiles:put name="data" type="string">
                       <c:forEach items="${phiz:getNodes()}" var="nodeInfo">
                           <c:if test="${nodeInfo.id ne currentNodeId}">
                               <tr>
                                   <td  class="Width120 alignRight"><input type="checkbox" name="selectedNodes" value="${nodeInfo.id}"></td>
                                   <td><c:out value="${nodeInfo.id}"/></td>
                               </tr>
                           </c:if>
                       </c:forEach>
                   </tiles:put>
               </tiles:insert>
            </c:if>

			<c:set var="canEdit" value="${phiz:impliesOperation('EditSchemeOperation','SchemeManagement')}"/>
            <c:set var="employee" value="${phiz:getEmployeeInfo()}"/>
            <c:set var="caadmin" value="${employee.CAAdmin}"/>
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="SchemeList"/>
                <tiles:put name="buttons">
                    <c:choose>
                        <c:when test="${standalone}">
                            <tiles:insert definition="commandButton" flush="false" operation="RemoveSchemeOperation">
                                <tiles:put name="commandKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle" value="schemesBundle"/>
                                <tiles:put name="validationFunction">
                                    checkSelection('selectedIds', 'Выберите схемы прав')
                                </tiles:put>
                                <tiles:put name="confirmText" value="Удалить выбранные схемы?"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.sync"/>
                                <tiles:put name="commandHelpKey" value="button.sync.help"/>
                                <tiles:put name="bundle" value="schemesBundle"/>
                                <tiles:put name="onclick" value="window.open('?${replicateAction}filters(replication)=true${id}', 'ExternalSystem','resizable=1,menubar=0,toolbar=0,scrollbars=1');"/>
                            </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.synchronize"/>
                                <tiles:put name="commandHelpKey" value="button.synchronize.help"/>
                                <tiles:put name="bundle" value="schemesBundle"/>
                                <tiles:put name="validationFunction">
                                    checkSelection('selectedIds', 'Выберите схемы прав') &&
                                        checkSelection('selectedNodes', 'Выберите номера блоков')
                                </tiles:put>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
				<tiles:put name="grid">
                    <html:hidden property="filters(replication)"/>
					<sl:collection id="item" property="data" model="list">
						<sl:collectionParam id="selectType" value="checkbox" condition="${canEdit}"/>
                        <c:choose>
                            <c:when test="${phiz:isMultiblockMode()}">
                                <sl:collectionParam id="selectProperty" value="externalId" condition="${canEdit}"/>
                                <c:set var="itemId" value="${item.externalId}"/>
                            </c:when>
                            <c:otherwise>
                                <sl:collectionParam id="selectProperty" value="id" condition="${canEdit}"/>
                                <c:set var="itemId" value="${item.id}"/>
                            </c:otherwise>
                        </c:choose>
						<sl:collectionParam id="selectName" value="selectedIds" condition="${canEdit}"/>

						<sl:collectionItem title="Наименование" property="name">
                            <c:choose>
                                <c:when test="${item.category eq 'C'}">
                                    <sl:collectionItemParam id="action" value="/schemes/user/edit?id=${itemId}" condition="${canEdit}"/>
                                </c:when>
                                <c:otherwise>
                                    <sl:collectionItemParam id="action" value="/schemes/employee/edit?id=${itemId}" condition="${canEdit}"/>
                                </c:otherwise>
                            </c:choose>
						</sl:collectionItem>
                        <sl:collectionItem title="&nbsp;" styleClass="Width20 align-center">
                            <c:if test="${not empty item and item.CAAdminScheme and caadmin}">
                                <img src="${skinUrl}/images/caadmin.gif"
                                     alt="Доступно только сотрудникам ЦА"
                                     title="Доступно только сотрудникам ЦА">
                            </c:if>
                            <c:if test="${not empty item and item.VSPEmployeeScheme}">
                                <img src="${skinUrl}/images/VSP.png" alt="Доступно сотрудникам ВСП" title="Доступно сотрудникам ВСП">
                            </c:if>
                        </sl:collectionItem>
						<sl:collectionItem title="Тип">
							<c:if test="${not empty item}">
								<%--TODO с этим нужно что-то делать. в момент парсинга внутренностей тега происходит вычисление без item, т.к. итерация по коллекции еще не началась--%>
								<bean:message key="label.scheme.category.${item.category}" bundle="schemesBundle"/>
                                <input type="hidden" name="categories(${itemId})" value="${item.category}"/>
							</c:if>
						</sl:collectionItem>
					</sl:collection>
				</tiles:put>
				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Пока не создано ни одной схемы прав."/>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
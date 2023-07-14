<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="standalone"     value="${empty param['dictionary']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="informMessageList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/inform/template/list" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="${layout}">
        <tiles:put name="submenu" type="string" value="TemplateInformMessages"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="template.list.title" bundle="informMessagesBundle"/>
        </tiles:put>
        <c:if test="${not standalone}">
            <tiles:put name="menu" type="string">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle"         value="informMessagesBundle"/>
                    <tiles:put name="onclick"        value="window.close()"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </tiles:put>
        </c:if>

        <%-- Фильтр --%>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.key.word"/>
                <tiles:put name="bundle" value="informMessagesBundle"/>
                <tiles:put name="name" value="keyWord"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="template.name"/>
                <tiles:put name="bundle" value="informMessagesBundle"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>
        </tiles:put>

        <%-- Данные --%>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function createMessageFromTemplate()
                {
                    if (!checkOneSelection("selectedIds", "Выберите один шаблон!"))
				        return;
                    openTemplate(getRadioValue(document.getElementsByName("selectedIds")));                    
                    return;
                }

                function openTemplate(id)
                {
                    <c:choose>
                        <c:when test="${standalone}">
                            window.location = "${phiz:calculateActionURL(pageContext,'/inform/message/view')}"
                                              +"?id=" + id;
                        </c:when>
                        <c:otherwise>
                            window.opener.location = "${phiz:calculateActionURL(pageContext,'/inform/message/edit')}"
                                              +"?id=" + id;
                            window.close();
                        </c:otherwise>
                    </c:choose>
                }
            </script>

            <%-- Таблица с данными --%>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="InformMessagesList"/>
                <tiles:put name="text">
                    <bean:message key="list.title" bundle="informMessagesBundle"/>
                </tiles:put>

                <%-- Данные таблицы --%>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="informMessagesBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.create">
                            <c:if test="${not empty listElement and not empty listElement.startPublishDate}">
                               <bean:write name="listElement" property="startPublishDate.time" format="dd.MM.yyyy"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="template.name">
                            <c:if test="${not empty listElement and not empty listElement.name}">
                                <a href="#" onclick="openTemplate('${listElement.id}');"><bean:write name="listElement" property="name"/></a>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.pages">
                            <c:if test="${not empty listElement and not empty listElement.pages}">
                                ${phiz:pagesToString(listElement.pages, 40)}
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.type">
                            <c:if test="${not empty listElement}">
                               <c:choose>
                                   <c:when test="${listElement.vievType == 'POPUP_WINDOW'}">
                                        <bean:message key="label.popup" bundle="informMessagesBundle"/>
                                   </c:when>
                                   <c:otherwise>
                                        <bean:message key="label.static" bundle="informMessagesBundle"/>
                                   </c:otherwise>
                               </c:choose>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.tb">
                            <c:if test="${not empty listElement and not empty listElement.departments}">
                                ${phiz:departmentsToString(listElement.departments, 25)}
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.text">
                            <c:if test="${not empty listElement and not empty listElement.text}">
                                <c:set var="text" value="${listElement.text}"/>
                                <c:if test="${fn:length(text) > 40}">
                                    <c:set var="text" value="${phiz:substring(text, 0, 40)}..."/>
                                </c:if>
                                <c:out value="${text}"/>
                           </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>

                <%-- Кнопки --%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditInformMessagesOperation">
                          <tiles:put name="commandTextKey" value="button.choose"/>
                          <tiles:put name="commandHelpKey" value="button.choose.help"/>
                          <tiles:put name="bundle" value="informMessagesBundle"/>
                          <tiles:put name="onclick"  value="createMessageFromTemplate();"/>
                    </tiles:insert>
                    <c:if test="${standalone}">
                        <tiles:insert definition="commandButton" flush="false" operation="RemoveInformMessagesOperation">
                              <tiles:put name="commandKey"     value="button.delete"/>
                              <tiles:put name="commandHelpKey" value="button.delete.help"/>
                              <tiles:put name="bundle"         value="informMessagesBundle"/>
                              <tiles:put name="validationFunction">
                                  function()
                                  {
                                    checkIfOneItem("selectedIds");
                                    return checkSelection('selectedIds', 'Выберите шаблон для удаления!');
                                  }
                              </tiles:put>
                              <tiles:put name="confirmText"><bean:message key="confirm.templateText" bundle="informMessagesBundle"/></tiles:put>
                        </tiles:insert>
                    </c:if>
                </tiles:put>

            </tiles:insert>

        </tiles:put>

    </tiles:insert>
</html:form>
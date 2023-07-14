<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/advertising/block/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="advertisingBlockList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="list.title" bundle="advertisingBlockBundle"/>
        </tiles:put>
        <%-- Пункт левого меню --%>
        <tiles:put name="submenu" type="string" value="AdvertisingBlock"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="action"  value="/advertising/block/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <%-- Фильтр --%>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterDataSpan" flush="false">
                <tiles:put name="label" value="label.filter.date"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="name" value="Date"/>
                <tiles:put name="template" value="DATE_TEMPLATE"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.filter.name"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.filter.orderIndex"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="name" value="orderIndex"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.department.name"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="data">
                    <html:text  property="filter(departmentName)" readonly="true" style="width:200px"/>
                    <html:hidden  property="filter(departmentId)"/>
                    <input type="button" class="buttWhite smButt" onclick="openAllowedTBDictionary(setDepartmentInfo);" value="..."/>

                    <script type="text/javascript">
                        var addedDepartments = new Array();
                        function openAllowedTBDictionary(callback)
                        {
                            window.setDepartmentInfo = callback;
                            win = window.open(document.webRoot+'/dictionaries/allowedTerbanks.do?type=oneSelection',
                                           'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px");
                            win.moveTo(50, 50);
                        }

                        function setDepartmentInfo(result)
                        {
                           setElement("filter(departmentName)",result['name']);
                           setElement("filter(departmentId)",result['region']);
                        }
                   </script>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.filter.state"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(state)" styleClass="select">
                        <html:option value=""><bean:message key="label.filter.all" bundle="advertisingBlockBundle"/></html:option>
                        <html:option value="ACTIVE"><bean:message key="label.filter.active" bundle="advertisingBlockBundle"/></html:option>
                        <html:option value="NOTACTIVE"><bean:message key="label.filter.notactive" bundle="advertisingBlockBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            
            <%-- Таблица с данными --%>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="AdvertisingBlocksList"/>
                <tiles:put name="text">
                    <bean:message key="advertBlocks.title" bundle="advertisingBlockBundle"/>
                </tiles:put>

                <%-- Данные таблицы --%>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="advertisingBlockBundle">
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.list.period">
                            <c:if test="${not empty listElement}">
                                <c:if test="${not empty listElement.periodFrom}">
                                   <bean:write name="listElement" property="periodFrom.time" format="dd.MM.yyyy"/>
                                </c:if>
                                -
                                <c:if test="${not empty listElement.periodTo}">
                                   <bean:write name="listElement" property="periodTo.time" format="dd.MM.yyyy"/>
                                </c:if>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.list.name">
                            <c:if test="${not empty listElement and not empty listElement.name}">
                                <phiz:link action="/advertising/block/edit"
                                          operationClass="EditAdvertisingBlockOperation">
                                          <phiz:param name="id" value="${listElement.id}"/>
                                          <bean:write name="listElement" property="name"/>
                                </phiz:link>    
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.list.tb">
                            <c:if test="${not empty listElement and not empty listElement.departments}">
                                ${phiz:departmentsToString(listElement.departments, 25)}
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.list.orderIndex">
                            <c:if test="${not empty listElement and not empty listElement.orderIndex}">
                                <bean:write name="listElement" property="orderIndex"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.list.state">
                            <c:if test="${not empty listElement and not empty listElement.state}">
                                <c:if test="${listElement.state == 'ACTIVE'}"><bean:message key="label.active" bundle="advertisingBlockBundle"/></c:if>
                                <c:if test="${listElement.state == 'NOTACTIVE'}"><bean:message key="label.notactive" bundle="advertisingBlockBundle"/></c:if>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>

                <tiles:put name="emptyMessage"><bean:message key="emptyMessage" bundle="advertisingBlockBundle"/></tiles:put>
                <script type="text/javascript">
                    var addUrl = "${phiz:calculateActionURL(pageContext,'/advertising/block/edit')}";
                    function doEdit(type)
                    {
                        checkIfOneItem("selectedIds");
                        if (!checkOneSelection("selectedIds", 'Пожалуйста, выберите одну запись'))
                            return;
                        var id = getRadioValue(document.getElementsByName("selectedIds"));
                        window.location = addUrl + "?id=" + id;
                    }

                </script>

                <%-- Кнопки --%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle"  value="advertisingBlockBundle"/>
                        <tiles:put name="onclick" value="doEdit();"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false" operation="RemoveAdvertisingBlockOperation">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="advertisingBlockBundle"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите рекламные блоки для удаления!');
                            }
                        </tiles:put>
                        <tiles:put name="confirmText"><bean:message key="confirm.text" bundle="advertisingBlockBundle"/></tiles:put>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>

        </tiles:put>


    </tiles:insert>
</html:form>

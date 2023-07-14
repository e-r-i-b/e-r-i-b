<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>


<c:set var="form" value="${ListLocaleForm}"/>
<c:set var="url"/>
<c:choose>
    <c:when test="${form.isCSA == true}">
        <c:set var="url" value="/configure/locale/csa/list"/>
        <c:set var="editUrl" value="/configure/locale/csa/edit"/>
        <c:set var="loadUrl" value="/configure/locale/csa/load"/>
        <c:set var="instance" value="CSA"/>
        <c:set var="submenuTab" value="LocalesCSA"/>
        <c:set var="unloadEribMessagesOperation" value="UnloadCSAEribMessagesOperation"/>
        <c:set var="loadEribMessagesOperation" value="LoadCSAEribMessagesOperation"/>
        <c:set var="removeLocaleOperation" value="RemoveCSALocaleOperation"/>
        <c:set var="editLocaleOperation" value="EditCSALocaleOperation"/>
        <c:set var="canEdit" value="${phiz:impliesOperation('EditCSALocaleOperation', '*')}"/>
    </c:when>
    <c:otherwise>
        <c:set var="url" value="/configure/locale/list"/>
        <c:set var="editUrl" value="/configure/locale/edit"/>
        <c:set var="loadUrl" value="/configure/locale/load"/>
        <c:set var="instance" value=""/>
        <c:set var="submenuTab" value="Locales"/>
        <c:set var="unloadEribMessagesOperation" value="UnloadEribMessagesOperation"/>
        <c:set var="loadEribMessagesOperation" value="LoadEribMessagesOperation"/>
        <c:set var="removeLocaleOperation" value="RemoveLocaleOperation"/>
        <c:set var="editLocaleOperation" value="EditLocaleOperation"/>
        <c:set var="canEdit" value="${phiz:impliesOperation('EditLocaleOperation', '*')}"/>
    </c:otherwise>
</c:choose>
<html:form action="${url}" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="localeList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="settings.locales.menuitem" bundle="configureBundle"/>
        </tiles:put>
        <%-- Пункт левого меню --%>
        <tiles:put name="submenu" type="string" value="${submenuTab}"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false" operation="${unloadEribMessagesOperation}">
                <tiles:put name="bundle" value="localeBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
                <tiles:put name="commandKey" value="button.unload"/>
                <tiles:put name="commandHelpKey" value="button.unload.help"/>
                <tiles:put name="validationFunction">validate();</tiles:put>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="${loadEribMessagesOperation}">
                <tiles:put name="bundle" value="localeBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
                <tiles:put name="commandTextKey" value="button.load"/>
                <tiles:put name="commandHelpKey" value="button.load.help"/>
                <tiles:put name="onclick">doLoad();</tiles:put>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="${editLocaleOperation}">
                <tiles:put name="bundle" value="localeBundle"/>
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="action" value="${editUrl}"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="${editLocaleOperation}">
                <tiles:put name="commandTextKey" value="button.edit"/>
                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                <tiles:put name="bundle"         value="localeBundle"/>
                <tiles:put name="onclick"        value="doEdit();"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false" operation="${removeLocaleOperation}">
                <tiles:put name="commandKey"     value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                <tiles:put name="bundle"         value="localeBundle"/>
                <tiles:put name="validationFunction">
                    doRemove()
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <%-- Фильтр --%>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.name"/>
                <tiles:put name="bundle" value="localeBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="size" value="100"/>
                <tiles:put name="maxlength" value="100"/>
            </tiles:insert>

        </tiles:put>

        <tiles:put name="data" type="string">

            <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="pensionProductList"/>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data" bundle="localeBundle">
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <sl:collectionItem hidden="true"/>
                    <c:choose>
                        <c:when test="${empty listElement.id}">
                            <tr class="tblInfHeader">
                                <th class="titleTable" style="width:20px" rowspan="2">
                                    <input type="checkbox" name="isSelectAll" onclick="switchSelection('isSelectAll','selectedIds');">
                                </th>
                                <th class="titleTable" rowspan="2">
                                    <bean:message bundle="localeBundle" key="locale.name"/>
                                </th>
                                <th class="titleTable" rowspan="2">
                                    <bean:message bundle="localeBundle" key="locale.state"/>
                                </th>
                                <th class="titleTable" rowspan="2">
                                    <bean:message bundle="localeBundle" key="locale.id"/>
                                </th>
                                <c:if test="${not form.isCSA}">
                                    <th class="titleTable" rowspan="2">
                                        <bean:message bundle="localeBundle" key="locale.flag"/>
                                    </th>
                                </c:if>
                                <th class="titleTable" colspan="5">
                                    <bean:message bundle="localeBundle" key="locale.channels"/>
                                </th>

                            </tr>
                            <tr class="tblInfHeader">
                                <th class="noBackground">
                                    <bean:message bundle="localeBundle" key="locale.eribAvailable"/>
                                </th>
                                <th class="noBackground">
                                    <bean:message bundle="localeBundle" key="locale.mapiAvailable"/>
                                </th>
                                <th class="noBackground">
                                    <bean:message bundle="localeBundle" key="locale.atmApiAvailable"/>
                                </th>
                                <th class="noBackground">
                                    <bean:message bundle="localeBundle" key="locale.webApiAvailable"/>
                                </th>
                                <th class="noBackground">
                                    <bean:message bundle="localeBundle" key="locale.ermbAvailable"/>
                                </th>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <input type="checkbox" name="selectedIds" value="${listElement.id}">
                            </td>
                            <td>
                                <phiz:link action="${editUrl}" operationClass="EditLocaleOperation">
                                    <phiz:param name="localeId" value="${listElement.id}"/>
                                    <c:out value="${listElement.name}"/>
                                </phiz:link>
                            </td>
                            <td>
                                <bean:message bundle="localeBundle" key="locale.state.${listElement.state}"/>
                            </td>
                            <td><c:out value="${listElement.id}"/></td>
                            <c:if test="${not form.isCSA}">
                                <c:set var="imageData" value="${phiz:getImageById(listElement.imageId )}"/>
                                <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>

                                <td>
                                    <img src="${image}"/>
                                </td>
                            </c:if>
                            <td>
                                <c:choose>
                                    <c:when test="${listElement.eribAvailable}">
                                        <img src="${imagePath}/availableIcon.png"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/unavailableIcon.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${listElement.mapiAvailable}">
                                        <img src="${imagePath}/availableIcon.png"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/unavailableIcon.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${listElement.atmApiAvailable}">
                                        <img src="${imagePath}/availableIcon.png"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/unavailableIcon.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${listElement.webApiAvailable}">
                                        <img src="${imagePath}/availableIcon.png"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/unavailableIcon.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${listElement.ermbAvailable}">
                                        <img src="${imagePath}/availableIcon.png"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/unavailableIcon.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </sl:collection>

                <tiles:put name="buttons">

                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">
                    <bean:message bundle="localeBundle" key="list.empty"/>
                </tiles:put>
            </tiles:put>
            </tiles:insert>

            <script type="text/javascript">
                doOnLoad(function(){
                    if (${form.fields.relocateToDownload != null && form.fields.relocateToDownload == true})
                    {
                        <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=LocaleMessagesFileType&clientFileName=${form.fields.clientFileName}"/>
                        clientBeforeUnload.showTrigger=false;
                        goTo('${downloadFileURL}');
                        clientBeforeUnload.showTrigger=false;
                    }
                });
                function validate()
                {
                    checkIfOneItem("selectedIds");
                    return checkSelection("selectedIds", '<bean:message bundle="localeBundle" key="locale.list.select.item"/>') &&
                            checkOneSelection("selectedIds", '<bean:message bundle="localeBundle" key="locale.list.select.one.item"/>');
                }
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                    if(!checkSelection("selectedIds", '<bean:message bundle="localeBundle" key="locale.list.select.item.edit"/>') || !checkOneSelection("selectedIds", '<bean:message bundle="localeBundle" key="locale.list.select.one.item"/>'))
                        return;
                    var id = document.getElementsByName("selectedIds")[0];
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext, editUrl)}"/>
                    var localeId = getRadioValue(document.getElementsByName("selectedIds"));

                    if (isNotEmpty(localeId))
                        window.location = '${url}?localeId=' + localeId;
                    else
                        window.location = '${url}';
                }
                function doRemove()
                {
                    checkIfOneItem("selectedIds");
                    if (checkSelection('selectedIds', '<bean:message bundle="localeBundle" key="locale.list.select.item.remove"/>'))
                    {
                        return confirm("<bean:message bundle="localeBundle" key="locale.list.remove.item"/>");
                    }
                    return false;
                }

                function doLoad()
                {
                    checkIfOneItem("selectedIds");
                    checkIfOneItem("selectedIds");
                    if(!checkSelection("selectedIds", '<bean:message bundle="localeBundle" key="locale.list.select.item"/>') || !checkOneSelection("selectedIds", '<bean:message bundle="localeBundle" key="locale.list.select.one.item"/>'))
                        return;
                    var id = document.getElementsByName("selectedIds")[0];
                    <c:set var="url" value="${phiz:calculateActionURL(pageContext,loadUrl)}"/>
                    var localeId = getRadioValue(document.getElementsByName("selectedIds"));
                    if (isNotEmpty(localeId))
                        window.location = '${url}?localeId=' + localeId;
                    else
                        window.location = '${url}';
                }
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>

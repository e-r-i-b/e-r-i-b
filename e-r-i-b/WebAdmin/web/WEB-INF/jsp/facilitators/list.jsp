<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/dictionaries/facilitator/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="resOnPage" value="${form.paginationSize}"/>
    <c:set var="searchByFacilitator" value="${form.searchByFacilitator}"/>

    <tiles:insert definition="providersMain">
        <tiles:put name="submenu" type="string" value="Facilitators"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="providerBundle" key="facilitators.listPage.title"/></tiles:put>
        <tiles:put name="filter" type="string">
            <script type="text/javascript">
                function showMessage()
                {
                    if(error != null) $('#filterErrorMessage').text(error);
                    if(message != null) $('#filterErrorMessage').text(message);
                }
            </script>

            <%--  row 1 --%>
            <%--Поиск по--%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="facilitators.filter.findBy"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="size" value="46"/>
                <tiles:put name="data">
                    <html:radio property="searchByFacilitator" name="form" value="true" styleId="facilitator"/><label for="facilitator"><bean:message bundle="providerBundle" key="facilitators.filter.searchType.facilitator"/></label><br/>
                    <html:radio property="searchByFacilitator" name="form" value="false" styleId="serviceProvider"/><label for="serviceProvider"><bean:message bundle="providerBundle" key="facilitators.filter.searchType.serviceProvider"/></label>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"></tiles:insert>
            <tiles:insert definition="filterEmptytField" flush="false"></tiles:insert>

            <%--  row 2 --%>
            <%--Наименование--%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.name"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="name" value="name"/>
                <tiles:put name="maxlength" value="160"/>
                <tiles:put name="size" value="46"/>
            </tiles:insert>

            <%--ИНН--%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="filter.INN"/>
                <tiles:put name="bundle" value="providerBundle"/>
                <tiles:put name="name" value="inn"/>
                <tiles:put name="maxlength" value="12"/>
                <tiles:put name="size" value="14"/>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false"></tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="FacilitatorListTable"/>

                <c:set var="sizeResult" value="${fn:length(form.data)}"/>
                <c:set var="pageSize" value="${form.paginationSize}"/>
                <c:set var="pageOffset" value="${form.paginationOffset}"/>
                <c:set var="pageItems">
                    <c:choose>
                        <c:when test="${sizeResult < pageSize}">
                            ${sizeResult}
                        </c:when>
                        <c:otherwise>
                            ${pageSize}
                        </c:otherwise>
                    </c:choose>
                </c:set>

                <c:if test="${sizeResult != 0}">
                    <tiles:put name="grid">
                        <table cellspacing="0" cellpadding="0" class="depositProductInfo" align="CENTER" width="100%">
                            <%--Шапка таблицы--%>
                            <tr>
                                <th><bean:message bundle="providerBundle" key="facilitators.table.columns.name"/></th>
                                <c:if test="${not searchByFacilitator}">
                                    <th><bean:message bundle="providerBundle" key="facilitators.table.columns.facilitator"/></th>
                                </c:if>
                                <th><bean:message bundle="providerBundle" key="facilitators.table.columns.inn"/></th>
                            </tr>

                            <c:forEach var="listElement" items="${form.data}" varStatus="stat">
                                <c:if test="${stat.count le pageSize}">
                                    <c:set var="id" value="${listElement[0]}"/>
                                    <c:set var="name" value="${listElement[1]}"/>
                                    <c:set var="inn" value="${listElement[2]}"/>
                                    <c:set var="editActionLink" value="/private/dictionaries/facilitator/edit.do?id=${id}"/>
                                    <c:set var="editActionLinkName" value="${editActionLink}"/>
                                    <c:if test="${not searchByFacilitator}">
                                        <c:set var="idEndpointProvider" value="${listElement[3]}"/>
                                        <c:set var="nameFacilitator" value="${listElement[4]}"/>
                                    </c:if>

                                    <tr>
                                        <td>
                                            <c:if test="${not searchByFacilitator}">
                                                <c:set var="editActionLinkName" value="${editActionLink}&idEndpointProvider=${idEndpointProvider}"/>
                                            </c:if>
                                            <html:link action="${editActionLinkName}">${name}</html:link>
                                        </td>
                                        <c:if test="${not searchByFacilitator}">
                                            <td>
                                                <html:link action="${editActionLink}">${nameFacilitator}</html:link>
                                            </td>
                                        </c:if>
                                        <td>${inn}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>

                        <%--Пагинация--%>
                        <c:set var="minPaginationSize" value="10"/>
                        <c:set var="mediumPaginationSize" value="20"/>
                        <c:set var="maxPaginationSize" value="50"/>
                        <c:if test="${pageOffset > 0 || sizeResult > minPaginationSize}">
                            <table cellspacing="0" cellpadding="0" class="tblNumRec">
                                <tr><td colspan="6">&nbsp;</td></tr>
                                <tr>
                                    <td style="width:50%;">&nbsp;</td>
                                    <td nowrap="nowrap" style="border:0;">
                                        <c:choose>
                                            <c:when test="${pageOffset>0}">
                                                <a href="#" onclick="addElementToForm('paginationOffset','${pageOffset}');
                                                        setElement('paginationOffset', '${pageOffset-pageSize}');
                                                        addElementToForm('paginationSize', '${pageSize}');
                                                        callOperation(event,'button.filter');">
                                                    <div class="activePaginLeftArrow"></div>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="inactivePaginLeftArrow"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td nowrap="nowrap" style="padding: 5px;">
                                    <span class="tblNumRecIns">
                                        ${pageOffset + 1}
                                        &nbsp;-&nbsp;
                                        ${pageOffset + pageItems}
                                    </span>
                                    </td>
                                    <td nowrap="nowrap" style="border:0;">
                                        <c:choose>
                                            <c:when test="${pageSize < sizeResult}">
                                                <a href="#" onclick="addElementToForm('paginationOffset','${pageOffset}');
                                                        setElement('paginationOffset', '${pageOffset+pageSize}');
                                                        addElementToForm('paginationSize', '${pageSize}');
                                                        callOperation(event,'button.filter');">
                                                    <div class="activePaginRightArrow"></div>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="inactivePaginRightArrow"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td nowrap="nowrap" align="right" width="100%">
                                        <div>
                                            <div class="floatRight">
                                                <div class="float paginationItemsTitle">Показывать по:</div>
                                                <input type="hidden" value="${resOnPage}" name="resOnPage">
                                                <c:choose>
                                                    <c:when test="${resOnPage == minPaginationSize}">
                                                        <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">${minPaginationSize}</span></div></div>
                                                        <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '${mediumPaginationSize}'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">${mediumPaginationSize}</a></div>
                                                        <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '${maxPaginationSize}'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">${maxPaginationSize}</a></div>
                                                    </c:when>
                                                    <c:when test="${resOnPage == mediumPaginationSize}">
                                                        <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '${minPaginationSize}'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">${minPaginationSize}</a></div>
                                                        <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">${mediumPaginationSize}</span></div></div>
                                                        <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '${maxPaginationSize}'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">${maxPaginationSize}</a></div>
                                                    </c:when>
                                                    <c:when test="${resOnPage == maxPaginationSize}">
                                                        <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '${minPaginationSize}'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">${minPaginationSize}</a></div>
                                                        <div class="paginationSize float"><a onclick="addElementToForm('paginationSize', '${mediumPaginationSize}'); callOperation(event,'button.filter');" href="#" style="padding-right:2px;">${mediumPaginationSize}</a></div>
                                                        <div class="float ${phiz:isIE() ? "circleImage" : "circle"}" style="display:inline-block"><div class="greenSelector"><span style="padding-right:2px;">${maxPaginationSize}</span></div></div>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                    </tiles:put>
                </c:if>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">
                    <c:choose>
                        <c:when test="${searchByFacilitator}">
                            <bean:message bundle="providerBundle" key="facilitators.emptyFacilitators.message"/>
                        </c:when>
                        <c:otherwise>
                            <bean:message bundle="providerBundle" key="facilitators.emptyProviders.message"/>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

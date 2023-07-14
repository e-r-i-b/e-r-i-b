<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/audit/log/messages" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${AuditMessageLogForm}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.message.page.name" bundle="logBundle"/>
        </tiles:put>
            <tiles:put name="menu" type="string">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.close"/>
                    <tiles:put name="commandHelpKey" value="button.close"/>
                    <tiles:put name="bundle" value="logBundle"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="onclick" value="window.close();"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:put name="fastSearchFilter" value="true"/>
            <c:set var="colCount" value="2" scope="request"/>
            <html:hidden property="filter(operationUID)"/>
            <%-- row1 --%>
            <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label" value="label.client"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="size" value="50"/>
                    <tiles:put name="maxlength" value="255"/>
                    <tiles:put name="name" value="fio"/>
                    <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.period"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    с&nbsp;
                    <span style="font-weight:normal;overflow:visible;cursor:default;">
                        <input type="text"
                                size="10" name="filter(fromDate)" class="dot-date-pick"
                                value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                        <input type="text"
                                size="8" name="filter(fromTime)" class="time-template"
                                value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                                onkeydown="onTabClick(event,'filter(toDate)')"/>
                    </span>
                        &nbsp;по&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                                size="10" name="filter(toDate)" class="dot-date-pick"
                                value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                        <input type="text"
                                size="8" name="filter(toTime)" class="time-template"
                                value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                    </span>
                </tiles:put>
            </tiles:insert>

               <%-- row2 --%>
            <tiles:insert definition="filter2TextField" flush="false">
                <tiles:put name="label" value="label.document"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name"   value="series"/>
                <tiles:put name="size"   value="5"/>
                <tiles:put name="maxlength"  value="16"/>
                <tiles:put name="isDefault" value="Серия"/>
                <tiles:put name="name2"   value="number"/>
                <tiles:put name="size2"   value="10"/>
                <tiles:put name="maxlength2"  value="16"/>
                <tiles:put name="default2" value="Номер"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="label.system"/>
                    <tiles:put name="bundle" value="logBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="data">
                        <html:select property="filter(system)" styleClass="select">
                            <html:option value="">Все</html:option>
                            <c:forEach var="system" items="${form.systemList}">
                                <c:if test="${system != 'cms'}"> <%-- cms не отображаем --%>
                                    <html:option value="${system}">
                                        <bean:message key="message.log.system.${system}" bundle="logBundle"/>
                                    </html:option>
                                </c:if>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
            </tiles:insert>

            <%-- row3 --%>
            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="label.birthDay"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="birthday"/>
            </tiles:insert>

            <tiles:insert definition="filter2TextField" flush="false">
                <tiles:put name="label" value="label.request"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="name"   value="requestTag"/>
                <tiles:put name="size"   value="40"/>
                <tiles:put name="maxlength"  value="256"/>
                <tiles:put name="isDefault"><bean:message key="label.request.tag" bundle="logBundle"/></tiles:put>
                <tiles:put name="name2"   value="requestWord"/>
                <tiles:put name="size2"   value="40"/>
                <tiles:put name="maxlength2"  value="256"/>
                <tiles:put name="default2"><bean:message key="label.request.word" bundle="logBundle"/></tiles:put>
            </tiles:insert>

            <%-- row4 --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.application"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.responce"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="name"   value="responceWord"/>
                <tiles:put name="size"   value="40"/>
                <tiles:put name="maxlength"  value="256"/>
                <tiles:put name="isDefault"><bean:message key="label.responce.word" bundle="logBundle"/></tiles:put>
            </tiles:insert>

            <%-- row 5 --%>
           <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.department.name"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="data">
                    <html:text  property="filter(departmentName)" readonly="true" style="width:200px"/>
                    <html:hidden  property="filter(departmentId)"/>
                    <c:if test="${phiz:impliesOperation('ListDepartmentsOperation', 'DepartmentsManagement') or phiz:impliesOperation('ListDepartmentsOperation','DepartmentsViewing')}">
                        <input type="button" class="buttWhite" style="height:18px;width:18px;"onclick="openDepartmentsDictionary(setDepartmentInfo, getElementValue('field(departmentName)'))" value="..."/>
                    </c:if>

                    <script type="text/javascript">
                       function setDepartmentInfo(result)
                       {
                           setElement("filter(departmentName)",result['name']);
                           setElement("filter(departmentId)",result['id']);
                       }
                       addClearMasks(null,
                               function(event)
                               {
                                   clearInputTemplate('filter(fromDate)', '__.__.____');
                                   clearInputTemplate('filter(toDate)', '__.__.____');
                                   clearInputTemplate('filter(fromTime)', '__:__:__');
                                   clearInputTemplate('filter(toTime)', '__:__:__');
                               });
                   </script>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label" value="label.requestresponce"/>
                    <tiles:put name="bundle" value="logBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="isFastSearch" value="true"/>
                    <tiles:put name="size" value="87"/>
                    <tiles:put name="maxlength" value="100"/>
                    <tiles:put name="isDefault"><bean:message key="label.text.word" bundle="logBundle"/></tiles:put>
                    <tiles:put name="name" value="requestresponceWord"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.autocomplete.js"></script>
        <script type="text/javascript">
            jQuery.fn.setType = function(name, type, idGreyText)
            {
                $("input[name=" + name + "]").autocomplete(
                    "${phiz:calculateActionURL(pageContext, "/log/messages")}",
                    {
                        extraParams: "operation=button.ajaxSearch&field(type)=" + type,
                        delay:500,
                        minChars:3,
                        maxItemsToShow:10,
                        matchSubset:0,
                        width:350,
                        autoFill:true,
                        greyStyle:true,
                        greyStyleElementId:idGreyText,
                        lineSeparator:'@'
                    }
                );
            }

            $(document).ready(function(){
                // для каждого поля свой типа запроса
                $("input[name=filter(requestTag)]").setType("filter(requestTag)", "R", "requestTagHidden");
            });
        </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="auditMessageLog"/>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
                        <sl:collectionItem title="label.datetime">
                            <fmt:formatDate value="${listElement.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.system">
                            <c:choose>
                                <c:when test="${listElement.system != null}">
                                    <bean:message key="message.log.system.${listElement.system}" bundle="logBundle"/>
                                </c:when>
                                <c:otherwise><bean:message key="system.log.application.other" bundle="logBundle"/></c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem name="logEntry">
                            <sl:collectionItemParam id="title">
                                <bean:message key="label.request.tag" bundle="logBundle"/>
                                <input type="button" id="requestButton" title="Для того чтобы изменить язык отображения, нажмите на эту кнопку" onclick="changeElemntValue(this, 'Ru', 'En');copyValueFromElementToElementById(this, 'request');hideOrShowByClass('request_tag_en', 'request_tag_ru');" value="Ru" class="changeLanguageButton"/>
                                <html:hidden styleId="request" property="field(request)"/>
                            </sl:collectionItemParam>
                            <div class="request_tag_en cursorArrow" style="display:block;" title="${empty listElement.messageTranslate ? listElement.messageType  : listElement.messageTranslate}">
                                <c:out value="${listElement.messageType}"/>&nbsp;
                            </div>
                            <div class="request_tag_ru cursorArrow" style="display:none;" title="${listElement.messageType}">
                                <c:out value="${empty listElement.messageTranslate ? listElement.messageType  : listElement.messageTranslate}"/>&nbsp;
                            </div>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.messages">
                            <input type="button" class="buttWhite smButt" onclick="openMessageDetails(${listElement.id}, '${listElement.type}');" value="..."/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.application">
                            <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                                <tiles:put name="application" value="${listElement.application}"/>
                            </tiles:insert>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.department.name">
                            <c:if test="${not empty listElement.departmentName}">
                                <c:out value="${listElement.departmentName}"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.record.num" name="listElement" property="id"/>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.list}"/>
            </tiles:insert>
            <script type="text/javascript">
                function openMessageDetails(id, type)
                {
                    openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/messageDetail')}?id="+id +"&messageType="+type,'Сообщение');    ///&&&&&&&&&&&&
                }

                doOnLoad(function()
                {
                    autoClickLanguageButton("requestButton", "request", "En");
                    autoClickLanguageButton("responseButton", "response", "En");
                });
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>

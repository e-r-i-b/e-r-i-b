<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<c:set var="standalone" value="${empty param['filter(operationUID)']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="erkcMain"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/erkc/log/messages">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="${layout}">
        <tiles:put name="submenu" type="string" value="System"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.message.page.name" bundle="logBundle"/>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <html:hidden property="filter(operationUID)"/>
            <tiles:put name="fastSearchFilter" value="true"/>
            <c:set var="colCount" value="2" scope="request"/>
            <%-- row1 --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.client"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="size" value="50"/>
                <tiles:put name="maxlength" value="255"/>
                <tiles:put name="name" value="fio"/>
                <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
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
                                value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"
                                />
                        <input type="text"
                                size="8" name="filter(fromTime)"
                                value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                                class="time-template"
                                onkeydown="onTabClick(event,'filter(toDate)')"/>
                    </span>
                        &nbsp;по&nbsp;
                    <span style="font-weight:normal;cursor:default;">
                        <input type="text"
                                size="10" name="filter(toDate)" class="dot-date-pick"
                                value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                        <input type="text"
                                size="8" name="filter(toTime)"
                                value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"
                                class="time-template"/>
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
                <tiles:put name="editable" value="false"/>
                <tiles:put name="editable2" value="false"/>
                <tiles:put name="needClearing" value="false"/>
                <tiles:put name="needClearing2" value="false"/>
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
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
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
           <html:hidden  property="filter(departmentId)"/>
           <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.department.name"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="size" value="50"/>
                <tiles:put name="name" value="departmentName"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
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
            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.autocomplete.js"></script>
            <script type="text/javascript">
                jQuery.fn.setType = function(name, type, idGreyText)
                {
                    $("input[name=" + name + "]").autocomplete(
                        "${phiz:calculateActionURL(pageContext, "/erkc/log/messages")}",
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
                    $('input[name="filter(birthday)"]').next().remove();
                });
                addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('filter(fromDate)','__.__.____');
                        clearInputTemplate('filter(toDate)','__.__.____');
                        clearInputTemplate('filter(fromTime)','__:__:__');
                        clearInputTemplate('filter(toTime)','__:__:__');
                    });
            </script>
        </tiles:put>

        <tiles:put name="data" type="string">
            <jsp:include page="/WEB-INF/jsp-sbrf/log/message/messageLogData.jsp" flush="false"/>
        </tiles:put>
    </tiles:insert>
</html:form>

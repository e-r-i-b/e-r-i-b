<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/migration/info">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="info" value="${form.migrationInfo}"/>

    <c:set var="migrationState">${info.state}</c:set>
    <c:set var="EMPTY_STATE"        value=""/>
    <c:set var="INITIALIZE_STATE"   value="INITIALIZE"/>
    <c:set var="WAIT_STATE"         value="WAIT"/>
    <c:set var="PROCESS_STATE"      value="PROCESS"/>
    <c:set var="WAIT_STOP_STATE"    value="WAIT_STOP"/>
    <c:set var="STOP_STATE"         value="STOP"/>

    <c:set var="URL" value="${phiz:calculateActionURL(pageContext, '/private/async/persons/migration/info')}"/>
    <tiles:insert definition="personList">
        <tiles:put name="pageTitle"><bean:message bundle="migrationClientsBundle" key="form.migration.title"/></tiles:put>
        <tiles:put name="submenu" value="EditMigrationInfo"/>
        <tiles:put name="data" type="string">

            <div class="relative">
                <div class="floatRight clientButton">
                    <script type="text/javascript">
                        var dataTimeout;
                        var migrationState = '${migrationState}';

                        var EMPTY_STATE         = '${EMPTY_STATE}';
                        var INITIALIZE_STATE    = '${INITIALIZE_STATE}';
                        var WAIT_STATE          = '${WAIT_STATE}';
                        var PROCESS_STATE       = '${PROCESS_STATE}';
                        var WAIT_STOP_STATE     = '${WAIT_STOP_STATE}';
                        var STOP_STATE          = '${STOP_STATE}';

                        function clearDataTimeout()
                        {
                            window.clearTimeout(dataTimeout);
                        }

                        function setDataTimeout(time)
                        {
                            dataTimeout = window.setTimeout("doRefresh()", time);
                        }

                        function doRefresh()
                        {
                            ajaxQuery("currentId=" + $('#currentId').val(), "${URL}", refreshData, "json", true);
                        }

                        function refreshData(data)
                        {
                            if(data == undefined)
                                return;

                            $('#goodCountValueContainer').html(data.goodCount);
                            $('#badCountValueContainer').html(data.badCount);
                            $('#endDateValueContainer').html(data.endDate);
                            clearDataTimeout();

                            migrationState = data.state;

                            if (migrationState == WAIT_STATE || migrationState == PROCESS_STATE || migrationState == WAIT_STOP_STATE)
                                setDataTimeout(data.timeout);

                            updateButtons();
                        }

                        function updateButton(buttonId, disableCallback)
                        {
                            $('#' + buttonId).find('.buttonGreen').toggleClass('disabled', !disableCallback());
                        }

                        function updateButtons()
                        {
                            updateButton('initializeButton', validateInitialize);
                            updateButton('startButton',      validateStart);
                            updateButton('stopButton',       validateStop);
                        }

                        function notState(state, availableStates)
                        {
                            return $.inArray(state, availableStates) == -1;
                        }

                        function validateInitialize()
                        {
                            return notState(migrationState, [WAIT_STATE, PROCESS_STATE, WAIT_STOP_STATE]);
                        }

                        function validateStart()
                        {
                            return notState(migrationState, [WAIT_STATE, PROCESS_STATE, WAIT_STOP_STATE]);
                        }

                        function validateStop()
                        {
                            return notState(migrationState, [EMPTY_STATE, INITIALIZE_STATE, STOP_STATE, WAIT_STOP_STATE]);
                        }

                        doOnLoad(function(){updateButtons();});
                        <c:if test="${migrationState eq WAIT_STATE or migrationState eq PROCESS_STATE or migrationState eq WAIT_STOP_STATE}">
                            doOnLoad(function(){setDataTimeout(${form.dataTimeout});});
                        </c:if>
                    </script>
                    <div id="initializeButton">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"            value="button.initialize"/>
                            <tiles:put name="commandTextKey"        value="form.migration.button.initialize"/>
                            <tiles:put name="commandHelpKey"        value="form.migration.button.initialize"/>
                            <tiles:put name="bundle"                value="migrationClientsBundle"/>
                            <tiles:put name="validationFunction"    value="validateInitialize"/>
                        </tiles:insert>
                    </div>
                    <div id="startButton">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"            value="button.start"/>
                            <tiles:put name="commandTextKey"        value="form.migration.button.start"/>
                            <tiles:put name="commandHelpKey"        value="form.migration.button.start"/>
                            <tiles:put name="bundle"                value="migrationClientsBundle"/>
                            <tiles:put name="validationFunction"    value="validateStart"/>
                        </tiles:insert>
                    </div>
                    <div id="stopButton">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"            value="button.stop"/>
                            <tiles:put name="commandTextKey"        value="form.migration.button.stop"/>
                            <tiles:put name="commandHelpKey"        value="form.migration.button.stop"/>
                            <tiles:put name="bundle"                value="migrationClientsBundle"/>
                            <tiles:put name="validationFunction"    value="validateStop"/>
                        </tiles:insert>
                    </div>
                </div>
                <div class="clear"></div>
                <div id="migrationInfoFields">
                    <html:hidden property="currentId" styleId="currentId"/>
                    <div class="MaxSize">
                        <table onkeypress="onEnterKey(event);">
                            <tr>
                                <td class="title">
                                    <bean:message key="form.migration.info.count.total" bundle="migrationClientsBundle"/>
                                </td>
                                <td class="value">
                                    <c:choose>
                                        <c:when test="${not empty info.totalCount}">
                                            ${info.totalCount}
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="form.migration.info.count.total.empty" bundle="migrationClientsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="empty">
                                    &nbsp;
                                </td>
                                <td class="title">
                                    <bean:message key="form.migration.info.date" bundle="migrationClientsBundle"/>
                                </td>
                                <td class="value">
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="title">
                                    <bean:message key="form.migration.info.count.good" bundle="migrationClientsBundle"/>
                                </td>
                                <td id="goodCountValueContainer" class="value">
                                    <c:choose>
                                        <c:when test="${not empty info.goodCount}">
                                            ${info.goodCount}
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="form.migration.info.count.good.empty" bundle="migrationClientsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="empty">
                                    &nbsp;
                                </td>
                                <td class="title">
                                    <bean:message key="form.migration.info.date.start" bundle="migrationClientsBundle"/>
                                </td>
                                <td class="value">
                                    <c:choose>
                                        <c:when test="${not empty info.startDate}">
                                            <fmt:formatDate value="${info.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="form.migration.info.date.start.empty" bundle="migrationClientsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="title">
                                    <bean:message key="form.migration.info.count.bad" bundle="migrationClientsBundle"/>
                                </td>
                                <td id="badCountValueContainer" class="value">
                                    <c:choose>
                                        <c:when test="${not empty info.badCount}">
                                            ${info.badCount}
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="form.migration.info.count.bad.empty" bundle="migrationClientsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="empty">
                                    &nbsp;
                                </td>
                                <td class="title">
                                    <bean:message key="form.migration.info.date.end" bundle="migrationClientsBundle"/>
                                </td>
                                <td id="endDateValueContainer" class="value">
                                    <c:choose>
                                        <c:when test="${not empty info.endDate}">
                                            <fmt:formatDate value="${info.endDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message key="form.migration.info.date.end.empty" bundle="migrationClientsBundle"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="title">
                                    &nbsp;
                                </td>
                                <td class="value">
                                    &nbsp;
                                </td>
                                <td class="empty">
                                    &nbsp;
                                </td>
                                <td class="title">
                                    <bean:message key="form.migration.info.batch.size" bundle="migrationClientsBundle"/>
                                </td>
                                <td class="value">
                                    <html:text property="batchSize" maxlength="6" size="4" style="text-align: right;"/>
                                    <bean:message key="form.migration.info.batch.size.unit" bundle="migrationClientsBundle"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
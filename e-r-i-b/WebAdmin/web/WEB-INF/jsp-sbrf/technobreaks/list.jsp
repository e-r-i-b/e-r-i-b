    <%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/technobreak/list">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="technobreakList">
        <tiles:put name="submenu" type="string" value="ListTechnoBreak"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="technobreak.dictionary.header" bundle="technobreaksBundle"/>
        </tiles:put>
        <tiles:put name="filter" type="string">
            <%-- Период --%>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.period"/>
                <tiles:put name="bundle" value="technobreaksBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="white-space:nowrap;">
                        с&nbsp;
                        <span style="font-weight:normal;overflow:visible;cursor:default;">
                            <input type="text"
                                    size="10" name="filter(fromDate)"
                                    maxsize="10" class="dot-date-pick"
                                    value="<bean:write name='form' property='filter(fromDate)' format='dd.MM.yyyy'/>"/>
                            <input type="text"
                                    size="8" name="filter(fromTime)"
                                    maxsize="8" class="time-template"
                                    value="<bean:write name='form' property='filter(fromTime)' format='HH:mm:ss'/>"
                                    onkeydown="onTabClick(event,'field(toDate)');"/>
                        </span>
                        &nbsp;по&nbsp;
                        <span style="font-weight:normal;cursor:default;">
                            <input type="text"
                                    size="10" name="filter(toDate)"
                                    maxsize="10"   class="dot-date-pick"
                                    value="<bean:write name='form' property='filter(toDate)' format='dd.MM.yyyy'/>"/>

                            <input type="text"
                                    size="8" name="filter(toTime)"
                                    maxsize="8"   class="time-template"
                                    value="<bean:write name='form' property='filter(toTime)' format='HH:mm:ss'/>"/>
                        </span>
                    </span>
                </tiles:put>
            </tiles:insert>
            <%-- Внешняя система --%>
            <tiles:insert definition="filterEntryField" flush="false">
               <tiles:put name="label" value="label.external.system"/>
               <tiles:put name="bundle" value="technobreaksBundle"/>
               <tiles:put name="mandatory" value="false"/>
               <tiles:put name="data">
                    <script type="text/javascript">
                        function setAdapterInfo(adapterInfo)
                        {
                            setElement('filter(adapterName)', adapterInfo["name"]);
                            setElement('filter(adapterUUID)', adapterInfo["UUID"]);
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
                    <html:hidden property="filter(adapterUUID)" styleId="adapterUUID"/>
                    <html:text property="filter(adapterName)" size="50" styleId="adapterName"/>
                    <input type="button" class="buttWhite smButt"
                                   onclick="openAdaptersDictionary(setAdapterInfo)"
                                   value="..."/>
               </tiles:put>
            </tiles:insert>

            <%-- Периодичность --%>
            <tiles:insert definition="filterEntryField" flush="false">
               <tiles:put name="label" value="label.periodic"/>
               <tiles:put name="bundle" value="technobreaksBundle"/>
               <tiles:put name="mandatory" value="false"/>
               <tiles:put name="data">
                   <html:select property="filter(periodic)">
                        <html:option value="SINGLE">
                            <bean:message key="label.periodic.SINGLE" bundle="technobreaksBundle"/>
                        </html:option>
                        <html:option value="EVERYDAY">
                            <bean:message key="label.periodic.EVERYDAY" bundle="technobreaksBundle"/>
                        </html:option>
                        <html:option value="WORKDAY">
                            <bean:message key="label.periodic.WORKDAY" bundle="technobreaksBundle"/>
                        </html:option>
                        <html:option value="WEEKEND">
                            <bean:message key="label.periodic.WEEKEND" bundle="technobreaksBundle"/>
                        </html:option>
                        <html:option value="BEFOREWEEKEND">
                            <bean:message key="label.periodic.BEFOREWEEKEND" bundle="technobreaksBundle"/>
                        </html:option>
                        <html:option value="">
                            <bean:message key="label.periodic.ALL" bundle="technobreaksBundle"/>
                        </html:option>
                    </html:select>
               </tiles:put>
            </tiles:insert>

            <%-- Текст сообщения --%>
            <tiles:insert definition="filterEntryField" flush="false">
               <tiles:put name="label" value="label.message"/>
               <tiles:put name="bundle" value="technobreaksBundle"/>
               <tiles:put name="mandatory" value="false"/>
               <tiles:put name="data">
                   <html:text property="filter(message)" size="50" maxlength="200"/>
               </tiles:put>
            </tiles:insert>

             <%-- Показывать только действующие --%>
            <tiles:insert definition="filterEntryField" flush="false">
               <tiles:put name="label" type="string" value="label.empty.message"/>
               <tiles:put name="bundle" value="technobreaksBundle"/>
               <tiles:put name="mandatory" value="false"/>
               <tiles:put name="data">
                   <html:checkbox property="filter(showWorking)" value="true"/>
                   <b><bean:message key="label.show.working" bundle="technobreaksBundle"/></b>
               </tiles:put>
            </tiles:insert>

        </tiles:put>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditTechnoBreakOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="technobreaksBundle"/>
                <tiles:put name="action" value="/technobreak/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/technobreak/edit')}"/>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="cardsLimitsList"/>
                <tiles:put name="buttons">
                    <script type="text/javascript">
                        function doEdit()
                        {
                            if (!checkOneSelection("selectedIds", 'Укажите одну запись'))
                                return;

                            var id = getRadioValue(document.getElementsByName("selectedIds"));
                            var active = $('#'+id).val();
                            if(active == 'true')
                            {
                                window.location = '${url}?id=' + id;
                            }
                            else
                            {
                                alert("Вы не можете отредактировать или удалить неактуальный технологический перерыв.");
                            }
                        }
                    </script>
                    <tiles:insert definition="clientButton" flush="false" operation="EditTechnoBreakOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit.help"/>
                        <tiles:put name="bundle" value="technobreaksBundle"/>
                        <tiles:put name="onclick" value="doEdit()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle" value="technobreaksBundle"/>
                        <tiles:put name="validationFunction">
                            checkSelection('selectedIds', 'Выберите технологический перерыв для удаления');
                        </tiles:put>
                        <tiles:put name="confirmText" value="Удалить выбранный технологический перерыв?"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="item" property="data" model="list" bundle="technobreaksBundle">
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="label.external.system">
                            <c:choose>
                                <c:when test="${empty item}">
                                    <c:set var="isActive" value="${false}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="isActive" value="${phiz:isActiveTechnoBreak(item)}"/>
                                </c:otherwise>
                            </c:choose>

                            <input type="hidden" id="${item.id}" value="${isActive}">
                            <c:choose>
                                <c:when test="${isActive}">
                                    <a href="${url}?id=${item.id}">${phiz:getExternalSystemName(item.adapterUUID)}</a>
                                </c:when>
                                <c:otherwise>
                                    ${phiz:getExternalSystemName(item.adapterUUID)}&nbsp;
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.period">

                            <c:if test="${not empty item}">
                                <bean:message key="label.periodic.${item.periodic}" bundle="technobreaksBundle"/>
                                <c:choose>
                                    <c:when test="${item.periodic == 'SINGLE'}">
                                        <bean:message key="label.from" bundle="technobreaksBundle"/>
                                        <bean:write name="item" property="fromDate.time" format="dd.MM.yyyy HH:mm:ss"/>

                                        <bean:message key="label.to" bundle="technobreaksBundle"/>
                                        <bean:write name="item" property="toDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                                    </c:when>
                                    <c:otherwise>
                                        <bean:message key="label.from" bundle="technobreaksBundle"/>
                                        <bean:write name="item" property="fromDate.time" format="HH:mm:ss"/>

                                        <bean:message key="label.to" bundle="technobreaksBundle"/>
                                        <bean:write name="item" property="toDate.time" format="HH:mm:ss"/>

                                        <bean:message key="label.inperiod" bundle="technobreaksBundle"/>

                                        <bean:message key="label.from" bundle="technobreaksBundle"/>
                                        <bean:write name="item" property="fromDate.time" format="dd.MM.yyyy"/>

                                        <bean:message key="label.to" bundle="technobreaksBundle"/>
                                        <bean:write name="item" property="toDate.time" format="dd.MM.yyyy"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </sl:collectionItem>

                        <sl:collectionItem title="label.message">
                            <c:choose>
                                <c:when test="${fn:length(item.message) > 50}">${fn:substring(item.message, 0, 50)}...</c:when>
                                <c:otherwise>${item.message}</c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.autoEnabled">
                            <c:choose>
                                <c:when test="${item.autoEnabled}"><bean:message key="label.autoEnabled.yes" bundle="technobreaksBundle"/></c:when>
                                <c:otherwise><bean:message key="label.autoEnabled.no" bundle="technobreaksBundle"/></c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage" value="Не найдено ни одного технологического перерыва. Пожалуйста, задайте другие параметры поиска."/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
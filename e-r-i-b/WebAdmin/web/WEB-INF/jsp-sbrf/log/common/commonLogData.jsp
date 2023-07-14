<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <script type="text/javascript">
        /**
         *  Очищает фильтр по sessionId
         */
        function clearSessionId()
        {
            setElement("filter(sessionId)", "");
        }

        //Переопределяем для очистки sessionId дня применения фильра без параметра sessionId.
        findCommandButton('button.filter').click = function(forceRedirect)
        {
            clearSessionId();
            if (!this.firstClick)
            {
                return false;
            }

            if(forceRedirect)
            {
                var frm = document.forms[0];
                frm.elements.namedItem('$$forceRedirect').value = forceRedirect;
            }

            loadNewAction('','');
            this.firstClick = false;
            callOperation(null, this.id);
        }

        function getCountChekedElementsByClass(className)
        {
            var elementsCount = 0;
            var elements = $("." + className);

            if (elements == undefined || elements.length <= 0)
                return elementsCount;

            for (var i = 0; i < elements.length; i++)
            {
                if (elements[i].checked)
                    elementsCount++;
            }
            return elementsCount;
        }

        function openMessageDetails(id, type)
        {
            openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/messageDetail')}?id="+id +"&messageType="+type,'Сообщевввние');
        }

        function openSystemLogDetails(id)
        {
            openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/systemLog')}?id="+id,'Сообщение');
        }

        function openOperationDetails(id)
        {
            openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/operationParameters')}?type=simple&id="+id,'Параметры');
        }

        $(document).ready(function(){
            $(".logJournal").click(function(){
                if (!$(this).attr("checked") && getCountChekedElementsByClass("logJournal") < 1)
                {
                    $(this).attr("checked", true);
                    alert("Хотя бы один журнал должен быть выбран");
                }
            });
        });
	</script>
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="comlogList"/>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
                    <c:set var="logEntry" value="${listElement}"/>
                    <%-- 1. Дата и время --%>
                    <sl:collectionItem title="label.datetime">
                        <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </sl:collectionItem>
                    <%-- 2. Тип журнала --%>
                    <sl:collectionItem title="label.log.type">
                        <div align="center">
                            <c:if test="${not empty logEntry}">
                                <bean:message key="label.log.type.${logEntry.log}" bundle="logBundle"/>
                            </c:if>
                        </div>
                    </sl:collectionItem>
                   <%-- 3. Идентификатор пользователя (LOGIN_ID) --%>
                    <sl:collectionItem title="label.loginId">
                        <div align="center">
                            <c:choose>
                                <c:when test="${not empty logEntry.loginId}">
                                    ${logEntry.loginId}
                                </c:when>
                                <c:otherwise>&mdash;</c:otherwise>
                            </c:choose>
                        </div>
                    </sl:collectionItem>
                    <%-- 4. ФИО пользователя --%>
                    <sl:collectionItem title="label.fullName">
                        <c:choose>
                                <c:when test="${(not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName)}">
                                      <bean:write name="logEntry" property="surName"/>&nbsp;
                                      <bean:write name="logEntry" property="firstName"/>&nbsp;
                                      <bean:write name="logEntry" property="patrName"/>
                                </c:when>
                                <c:otherwise>&mdash;</c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                    <%-- 5. Тип запроса --%>
                    <sl:collectionItem>
                        <sl:collectionItemParam id="title">
                            <bean:message key="label.request.tag" bundle="logBundle"/>
                            <input type="button" id="requestButton" title="Для того чтобы изменить язык отображения, нажмите на эту кнопку" onclick="changeElemntValue(this, 'Ru', 'En');copyValueFromElementToElementById(this, 'request');hideOrShowByClass('request_tag_en', 'request_tag_ru');" value="Ru" class="changeLanguageButton"/>
                            <html:hidden styleId="request" property="field(request)"/>
                        </sl:collectionItemParam>
                        <c:choose>
                            <c:when test="${logEntry.log == 'MESSAGE' || logEntry.log == 'MESSAGE_F'}">
                                <div class="request_tag_en cursorArrow" title="${empty logEntry.requestTypeTranslate ? logEntry.requestType  : logEntry.requestTypeTranslate}">
                                    ${logEntry.requestType}&nbsp;
                                </div>
                                <div class="request_tag_ru cursorArrow" style="display:none;" title="${logEntry.requestType}">
                                    ${empty logEntry.requestTypeTranslate ? logEntry.requestType : logEntry.requestTypeTranslate}&nbsp;
                                </div>
                            </c:when>
                            <c:otherwise><div align="center">&mdash;</div></c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                    <%-- 6. Тип ответа - поле удалено --%>
                    <%-- 7. Параметры операции + сообщения --%>
                    <sl:collectionItem title="label.operation.parameter.messages">
                        <div align="center">
                            <c:choose>
                                <c:when test="${logEntry.log == 'USER'}">
                                    <c:if test="${!logEntry.paramsEmpty}">
                                        <input type="button" class="buttWhite smButt"
                                               onclick="openOperationDetails(${logEntry.id.id});"
                                               value="..."/>
                                    </c:if>
                                </c:when>
                                <c:when test="${logEntry.log == 'MESSAGE'}">
                                    <input type="button" class="buttWhite smButt"
                                           onclick="openMessageDetails(${logEntry.id.id}, 'OTHER');"
                                           value="..."/>
                                </c:when>
                                <c:when test="${logEntry.log == 'MESSAGE_F'}">
                                    <input type="button" class="buttWhite smButt"
                                           onclick="openMessageDetails(${logEntry.id.id}, 'FINANCE');"
                                           value="..."/>
                                </c:when>
                                <c:when test="${logEntry.log == 'SYSTEM'}">
                                    <input type="button" class="buttWhite smButt"
                                           onclick="openSystemLogDetails(${logEntry.id.id});"
                                           value="..."/>
                                </c:when>
                                <c:otherwise><div align="center">&mdash;</div></c:otherwise>
                            </c:choose>
                        </div>
                    </sl:collectionItem>
                    <%-- 8. Наименование операции --%>
                    <sl:collectionItem title="label.operation.name">
                        <div align="center">
                            <c:choose>
                                <c:when test="${logEntry.log == 'USER'}">
                                    ${logEntry.operation}
                                </c:when>
                                <c:otherwise><div align="center">&mdash;</div></c:otherwise>
                            </c:choose>
                        </div>
                    </sl:collectionItem>
                    <%-- 9. Приложение --%>
                    <sl:collectionItem title="label.application">
                        <div align="center">
                            <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                                <tiles:put name="application" value="${logEntry.application}"/>
                            </tiles:insert>
                        </div>
                    </sl:collectionItem>
                    <%-- 10. Подразделение --%>
                    <sl:collectionItem title="label.department.name">
                       <c:choose>
                                <c:when test="${not empty logEntry.departmentName}">
                                      <bean:write name="logEntry" property="departmentName"/>
                                </c:when>
                                <c:otherwise>&mdash;</c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                    <%-- 11. IP-адрес --%>
                    <sl:collectionItem title="label.ip.address">
                        <c:choose>
                            <c:when test="${not empty logEntry.ipAddress}">
                                ${logEntry.ipAddress}
                            </c:when>
                            <c:otherwise><div align="center">&mdash;</div></c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                    <%-- 12. Идентификатор сессии --%>
                    <sl:collectionItem title="label.session.id">
                        <c:choose>
                            <c:when test="${not empty logEntry.sessionId}">
                                ${logEntry.sessionId}
                            </c:when>
                            <c:otherwise><div align="center">&mdash;</div></c:otherwise>
                        </c:choose>
                    </sl:collectionItem>
                    <%-- 13. Тип сообщения --%>
                    <sl:collectionItem title="label.message.type">
                        <div align="center">
                            <c:choose>
                                <c:when test="${logEntry.log == 'SYSTEM'}">
                                    <c:set var="messageLevel" value="${logEntry.messageType}"/>
                                    <c:choose>
                                        <c:when test="${messageLevel=='I'}">
                                            <bean:message key="system.log.layer.I" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${messageLevel=='D'}">
                                            <bean:message key="system.log.layer.D" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${messageLevel=='E'}">
                                            <bean:message key="system.log.layer.E" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${messageLevel=='W'}">
                                            <bean:message key="system.log.layer.W" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${messageLevel=='F'}">
                                            <bean:message key="system.log.layer.F" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${messageLevel=='T'}">
                                            <bean:message key="system.log.layer.T" bundle="logBundle"/>
                                        </c:when>
                                        <c:otherwise>&mdash;</c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:when test="${logEntry.log == 'USER'}">
                                    <c:choose>
                                        <c:when test="${logEntry.messageType=='B'}">
                                            <bean:message key="user.log.type.security" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${logEntry.messageType=='S'}">
                                            <bean:message key="user.log.type.success" bundle="logBundle"/>
                                        </c:when>
                                        <c:when test="${logEntry.messageType=='E'}">
                                            <bean:message key="user.log.type.system.error" bundle="logBundle"/>
                                        </c:when>
                                       <c:when test="${logEntry.messageType=='U'}">
                                            <bean:message key="user.log.type.user.error" bundle="logBundle"/>
                                        </c:when>
                                        <c:otherwise>&mdash;</c:otherwise>
                                     </c:choose>
                                </c:when>
                                <c:otherwise>&mdash;</c:otherwise>
                            </c:choose>
                        </div>
                    </sl:collectionItem>
                    <%-- 14. Модуль --%>
                    <sl:collectionItem title="label.module">
                        <div align="center">
                            <c:choose>
                                <c:when test="${logEntry.log == 'SYSTEM'}">
                                    ${logEntry.module}
                                </c:when>
                                <c:otherwise>&mdash;</c:otherwise>
                            </c:choose>
                        </div>
                    </sl:collectionItem>
                    <%-- 15. Система --%>
                    <sl:collectionItem title="label.system">
                        <div align="center">
                            <c:choose>
                                <c:when test="${logEntry.log == 'MESSAGE' || logEntry.log == 'MESSAGE_F'}">
                                    ${logEntry.system}
                                </c:when>
                                <c:otherwise>&mdash;</c:otherwise>
                            </c:choose>
                        </div>
                    </sl:collectionItem>
                    <%-- 16. № записи --%>
                    <sl:collectionItem title="label.record.num" name="logEntry" property="id"/>
                    <sl:collectionItem title="label.node.id" name="logEntry" property="nodeId"/>
                </sl:collection>
            </tiles:put>

            <script type="text/javascript">
               var widthClient = getClientWidth();
               if (navigator.appName=='Microsoft Internet Explorer')
                   document.getElementById("workspaceDiv").style.width = widthClient - leftMenuSize - 120;

               <c:if test="${form.fromStart}">
                   //показываем фильтр при старте
               try
               {
                   switchFilter(this);
               }
               catch (e) {}
               </c:if>
            </script>

            <tiles:put name="isEmpty" value="${empty form.list}"/>
            <tiles:put name="emptyMessage">
                <c:choose>
                    <c:when test="${form.fromStart}">
                        Для поиска записей в системе предусмотрен фильтр.
                        Чтобы им воспользоваться, в полях фильтра задайте критерии поиска и нажмите на кнопку «Применить».
                    </c:when>
                    <c:otherwise>
                        Не найдено ни одной записи, соответствующей заданному фильтру!
                    </c:otherwise>
                </c:choose>
            </tiles:put>
          </tiles:insert>
          <script type="text/javascript">
            doOnLoad(function()
            {
                    autoClickLanguageButton("requestButton", "request", "En");
                    autoClickLanguageButton("responseButton", "response", "En");
            });
          </script>

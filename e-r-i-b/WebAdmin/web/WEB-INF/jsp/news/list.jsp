<%--
  User: Zhuravleva
  Date: 19.07.2006
  Time: 12:52:17
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="form" value="${ListNewsForm}"/>
<c:set var="submenuValue"/>
<c:set var="titleText"/>
<c:set var="url"/>
<c:choose>
    <c:when test="${form.mainNews == true}">
        <c:set var="submenuValue" value="List"/>
        <c:set var="titleText" value="События системы"/>
        <c:set var="url" value="/news/list"/>
    </c:when>
    <c:otherwise>
        <c:set var="submenuValue" value="ListNewsLoginPage"/>
        <c:set var="titleText" value="События на странице входа"/>
        <c:set var="url" value="/news/login/page/list"/>
    </c:otherwise>
</c:choose>
<html:form action="${url}">


<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>


<tiles:insert definition="newsMain">

<tiles:put name="submenu" type="string" value="${submenuValue}"/>
<tiles:put name="pageTitle" type="string">
    <bean:message key="list.title" bundle="newsBundle"/>
</tiles:put>
    <tiles:put name="menu" type="string">
	    <script type="text/javascript">
		    var addUrl = "${phiz:calculateActionURL(pageContext,'/news/edit')}";
		    function doEdit(type)
		    {
                checkIfOneItem("selectedIds");
			    if (!checkOneSelection("selectedIds", 'Выберите одно событие'))
				    return;
			    var id = getRadioValue(document.getElementsByName("selectedIds"));
                var state = document.getElementsByName("state"+id)[0].value;
                if (type != 'view' && state == "NOT_PUBLISHED")
                {
                    alert("Невозможно отредактировать событие со статусом 'Cнято с публикации'");
                    return;
                }

			    if (type=='view')
                {
                    window.location = addUrl + "?id=" + id + "&type=view" + "&mainNews=${form.mainNews}";
                }
                else
                {
                    window.location = addUrl + "?id=" + id + "&mainNews=${form.mainNews}";
                }
		    }

            function doRemove(id)
            {
                checkIfOneItem("selectedIds");
                if(!checkSelection('selectedIds', 'Выберите событие'))
                    return;
                var ids = document.getElementsByName("selectedIds");
                for (var i = 0; i < ids.length; i++)
                {
                    if (ids.item(i).checked)
                    {
                        var id = "state"+getRadioValue(ids[i]);
                        var state = getElementValue(id);
                        if (state == "PUBLISHED")
                        {
                            alert("Данная операция доступна только для события со статусом 'Новое' и 'Снято с публикации'");
                            return false;
                        }
                    }
                }
                return true;
            }

            function editDistribution()
            {
                checkIfOneItem("selectedIds");
                if (!checkOneSelection("selectedIds", 'Выберите одно событие'))
                    return;
                var id = getRadioValue(document.getElementsByName("selectedIds"));
                var state = document.getElementsByName("state"+id)[0].value;
                if (state == "NOT_PUBLISHED")
                {
                    alert("Отправка уведомлений по событиям в статусе 'Cнято с публикации' запрещена.");
                    return;
                }

                var distribution = document.getElementById("distribution"+id);
                if (distribution != undefined)
                {
                    alert("Повторная рассылка по одному событию запрещена.");
                    return;
                }

                var win = window.open("${phiz:calculateActionURL(pageContext,'/news/distribution/edit.do')}", "", "width=1000,height=200,resizable=0,menubar=0,toolbar=0,scrollbars=1");
                win.moveTo(screen.width / 2 - 375, screen.height / 2 - 200);
            }

            function createDistribution(mailCount, timeout)
            {
                var params = "?id=";
                params += getRadioValue(document.getElementsByName("selectedIds"));
                params += "&mainNews=";
                params += "${form.mainNews}";
                params += "&field(mailCount)=";
                params += mailCount;
                params += "&field(timeout)=";
                params += timeout;

                window.location = "${phiz:calculateActionURL(pageContext, '/news/distribution/create')}" + params;
            }
	    </script>

       <tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.add"/>
			<tiles:put name="commandHelpKey" value="button.add.help"/>
			<tiles:put name="bundle"  value="newsBundle"/>
			<tiles:put name="image"   value=""/>
	        <tiles:put name="action"  value="/news/edit.do?mainNews=${form.mainNews}"/>
           <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </tiles:put>

    <tiles:put name="filter" type="string">
        <tiles:insert definition="filterDataSpan" flush="false">
            <tiles:put name="label" value="label.date"/>
            <tiles:put name="bundle" value="newsBundle"/>
            <tiles:put name="name" value="Date"/>
            <tiles:put name="template" value="DATE_TEMPLATE"/>
        </tiles:insert>
        <tiles:insert definition="filterTextField" flush="false">
            <tiles:put name="label" value="label.title"/>
            <tiles:put name="bundle" value="newsBundle"/>
            <tiles:put name="name" value="title"/>
        </tiles:insert>

        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.state"/>
            <tiles:put name="bundle" value="newsBundle"/>
            <tiles:put name="data">
                <html:select property="filter(state)" styleClass="select">
                    <html:option value="">Все</html:option>
                    <html:option value="NEW">Новое</html:option>
                    <html:option value="PUBLISHED">Опубликовано</html:option>
                    <html:option value="NOT_PUBLISHED">Снято с публикации</html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="filterEntryField" flush="false">
            <tiles:put name="label" value="label.important"/>
            <tiles:put name="bundle" value="newsBundle"/>
            <tiles:put name="data">
                <html:select property="filter(important)" styleClass="select">
                    <html:option value="">Все</html:option>
                    <html:option value="HIGH">Высокая</html:option>
                    <html:option value="LOW">Низкая</html:option>
                </html:select>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
	    <tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="systemsNews"/>
			<tiles:put name="text" value="${titleText}"/>
            <tiles:put name="buttons">
                <c:if test="${phiz:impliesService('NewsDistributionsManagement')}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.distribution"/>
                        <tiles:put name="commandHelpKey" value="button.distribution.help"/>
                        <tiles:put name="bundle"  value="newsBundle"/>
                        <tiles:put name="image"   value=""/>
                        <tiles:put name="onclick"  value="editDistribution()"/>
                    </tiles:insert>
                </c:if>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle"  value="newsBundle"/>
                    <tiles:put name="onclick" value="doEdit();"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.view"/>
                    <tiles:put name="commandHelpKey" value="button.view.help"/>
                    <tiles:put name="bundle"  value="newsBundle"/>
                    <tiles:put name="onclick" value="doEdit('view');"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle"  value="newsBundle"/>
                    <tiles:put name="validationFunction" value="doRemove()"/>
                    <tiles:put name="confirmText" value="Вы действительно хотите удалить выбранное событие?"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data">
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="personsBundle">
                        <c:set var="distribution" value="${form.distributions[listElement.id]}"/>
                        <sl:collectionParam id="selectType" value="checkbox"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="Дата и время события" >
                            <c:if test="${not empty listElement.newsDate}">
                                <c:set var="newsDate" value="${listElement.newsDate.time}"/>
                                <bean:write name='newsDate' format="dd.MM.yyyy HH:mm:ss"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="Название события">
                            <c:choose>
                                <c:when test="${listElement.state != 'NOT_PUBLISHED'}">
                                    <phiz:link action="/news/edit" serviceId="NewsManagment">
                                        <phiz:param name="id" value="${listElement.id}"/>
                                        <phiz:param name="mainNews" value="${form.mainNews}"/>
                                        <c:out value="${listElement.title}"/>
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${listElement.title}"/>
                                </c:otherwise>
                            </c:choose>
                        </sl:collectionItem>
                        <sl:collectionItem title="Важность">
                            <c:set var="important" value="${listElement.important}"/>
                            <sl:collectionItemParam id="value" value="Высокая" condition="${important=='HIGH'}"/>
                            <sl:collectionItemParam id="value" value="Низкая" condition="${important=='LOW'}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="Статус">
                            <c:if test="${not empty listElement.state}">
                                <input name="state${listElement.id}" value="${listElement.state}" type="hidden"/>
                                <bean:message key="label.state.${listElement.state}" bundle="newsBundle"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="Логин сотрудника,<br> выполнившего рассылку">
                            <c:if test="${not empty distribution}">
                                ${distribution.login.userId}
                                <input id="distribution${listElement.id}" value="${distribution.id}" type="hidden"/>
                            </c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="Дата рассылки">
                            <c:if test="${not empty distribution}"><fmt:formatDate value="${distribution.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/></c:if>
                        </sl:collectionItem>
                        <sl:collectionItem title="Статус рассылки">
                            <c:if test="${not empty distribution}">
                                <c:set var="stateName" value="${distribution.state}"/>
                                <sl:collectionItemParam id="value" value="В обработке" condition="${stateName=='PROCESSED'}"/>
                                <sl:collectionItemParam id="value" value="Отправлено" condition="${stateName=='SENT'}"/>
                                <sl:collectionItemParam id="value" value="Ошибка рассылки" condition="${stateName=='ERROR'}"/>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
            </tiles:put>
            <tiles:put name="isEmpty" value="${empty form.data}"/>
			<tiles:put name="emptyMessage">Событий нет!</tiles:put>
		</tiles:insert>
    </tiles:put>
</tiles:insert>
</html:form>
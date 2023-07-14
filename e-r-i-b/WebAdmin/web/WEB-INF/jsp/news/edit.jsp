<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/news/edit" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="cancelLink"/>
<c:set var="submenuValue"/>
<c:choose>
    <c:when test="${form.mainNews == true}">
        <c:set var="submenuValue" value="List"/>
        <c:set var="cancelLink" value="/news/list.do"/>
    </c:when>
    <c:otherwise>
        <c:set var="submenuValue" value="ListNewsLoginPage"/>
        <c:set var="cancelLink" value="/news/login/page/list.do"/>
    </c:otherwise>
</c:choose>

<tiles:insert definition="newsEdit">
    <tiles:put name="submenu" type="string" value="${submenuValue}"/>

    <tiles:put name="menu" type="string">
	    <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey"     value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
            <tiles:put name="bundle"  value="newsBundle"/>
            <tiles:put name="image"   value=""/>
            <tiles:put name="action" value="${cancelLink}"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </tiles:put>

    <c:set var="state" value="${form.fields.state}"/>
    <c:set var="automaticPublish" value="${form.fields.automaticPublish}"/>
    <c:set var="cancelPublish" value="${form.fields.cancelPublish}"/>
    <c:set var="view" value="${form.type=='view' || state == 'NOT_PUBLISHED'}"/>
    <c:set var="readonly" value=""/>
    <c:if test="${view}">
        <tiles:put name="needSave" value="false"/>
        <c:set var="readonly" value="readonly"/>
    </c:if>
	<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="date" value="${phiz:currentDate()}"/>
    <c:set var="currentDate"><bean:write name="date" property="time" format="dd.MM.yyyy"/></c:set>
    <script type="text/javascript">
        function openAllowedTBDictionary(callback)
        {
            window.setDepartmentInfo = callback;
            var win = window.open(document.webRoot+'/dictionaries/allowedTerbanks.do?type=manySelection',
                           'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth - 100) + "px,height=" + (getClientHeight - 100) + "px, left=50, top=50");
        }

        function setDepartmentInfo(resource)
        {
           setElement('field(TB)', resource['region']);
           setElement('field(departmentsName)', resource['name']);
        }

        var addUrl = "${phiz:calculateActionURL(pageContext,'/news/edit')}";
        function addCopy(template)
        {
            window.location = addUrl + "?template=" + template + "&mainNews=${form.mainNews}";
        }

        function enablePublish(param)
        {
            var date = getElement("field("+param+"PublishDate)");
            var time = getElement("field("+param+"PublishTime)");


            var disabled = (document.getElementsByName("field("+param+"Publish)")[0].checked) ? false : true;

            date.disabled = disabled;
            time.disabled = disabled;
        }

        function setCurrentDate(param)
        {
            var date = getElementValue("field("+param+"PublishDate)");
            if (date == '')
            {
                setElement("field("+param+"PublishDate)", '${currentDate}');
            }

        }
        function setTime(param)
        {
            var publishTime = getElementValue("field("+param+"PublishTime)");
            if (publishTime == '')
            {
                setElement("field("+param+"PublishTime)", '00:00');
            }
        }
	</script>
    <c:if test="${not view}">
        <script type="text/javascript">
            $(document).ready(function()
            {
              enablePublish("automatic");
              enablePublish("cancel");
              initAreaMaxLengthRestriction("shortText", 150);
              initAreaMaxLengthRestriction("newsText", 5000);
             });
        </script>
    </c:if>
	<tiles:insert definition="paymentForm" flush="false">
	<tiles:put name="id" value="news"/>
	<c:choose>
        <c:when test="${view}">
            <bean:message key="news.view.label" bundle="newsBundle"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="name">
                <bean:message key="news.edit.label" bundle="newsBundle"/>
            </tiles:put>
        </c:otherwise>
    </c:choose>
	<tiles:put name="description">
        <bean:message key="news.edit.description" bundle="newsBundle"/>
    </tiles:put>
    <tiles:put name="buttons">
         <c:if test="${not view}">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="newsBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </c:if>
        <c:if test="${view}">
            <c:if test="${state == 'NEW' && (not automaticPublish)}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.publish"/>
                    <tiles:put name="commandHelpKey" value="button.publish.help"/>
                    <tiles:put name="bundle"  value="newsBundle"/>
                </tiles:insert>
            </c:if>
            <c:if test="${state == 'PUBLISHED' &&(not cancelPublish)}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.remove_publish"/>
                    <tiles:put name="commandHelpKey" value="button.remove_publish.help"/>
                    <tiles:put name="bundle"  value="newsBundle"/>
                </tiles:insert>
            </c:if>
            <c:if test="${state != 'NOT_PUBLISHED'}">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle"  value="newsBundle"/>
                    <tiles:put name="action" value="/news/edit.do?id=${form.id}&mainNews=${form.mainNews}"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"     value="button.copy"/>
                <tiles:put name="commandHelpKey" value="button.copy.help"/>
                <tiles:put name="bundle"  value="newsBundle"/>
                <tiles:put name="onclick"        value="addCopy(${form.id});"/>
            </tiles:insert>
        </c:if>
        <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/news/locale/save')}"/>
        <c:if test="${not empty form.id}">
            <tiles:insert definition="languageSelectForEdit" flush="false">
                <tiles:put name="selectId" value="chooseLocale"/>
                <tiles:put name="entityId" value="${form.id}"/>
                <tiles:put name="styleClass" value="float"/>
                <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
            </tiles:insert>
        </c:if>

    </tiles:put>
	<tiles:put name="data">
        <div style="width:35%;float:left;">
            <fieldset>
                <legend>
                    <bean:message key="label.dateTime" bundle="newsBundle"/>
                </legend>
                <div class="smallWidth">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="news.date" bundle="newsBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="newsDate"><bean:write name="form" property="field(newsDate)" format="dd.MM.yyyy"/></c:set>
                            <html:text property="field(newsDate)" value="${newsDate}" styleClass="dot-date-pick"  disabled="${view}" maxlength="10"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div class="smallWidth">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="news.time" bundle="newsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="newsDateTime"><bean:write name="form" property="field(newsDateTime)" format="HH:mm"/></c:set>
                            <html:text property="field(newsDateTime)" value="${newsDateTime}" styleClass="pmntDate short-time-template" disabled="${view}" maxlength="8"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </fieldset>
        </div>
        <div>
            <fieldset>
                <legend>
                    <bean:message key="news.automatic" bundle="newsBundle"/>
                </legend>
                <div class="leftAlign">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <html:checkbox property="field(automaticPublish)" value="true" style="border:0px solid white;" disabled="${view}" onclick="enablePublish('automatic');setCurrentDate('automatic');setTime('automatic');"/>Опубликовать с
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="automaticPublishDate"><bean:write name="form" property="field(automaticPublishDate)" format="dd.MM.yyyy"/></c:set>
                            <html:text property="field(automaticPublishDate)" value="${automaticPublishDate}" styleClass="dot-date-pick" onkeydown="setTime('automatic');" disabled="true" maxlength="10"/>
                            <c:set var="automaticPublishTime"><bean:write name="form" property="field(automaticPublishTime)" format="HH:mm"/></c:set>
                            <html:text property="field(automaticPublishTime)" value="${automaticPublishTime}" styleClass="pmntDate short-time-template" disabled="true" maxlength="8"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
                <div class="leftAlign">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <html:checkbox property="field(cancelPublish)" value="true" style="border:0px solid white;" disabled="${view}" onclick="enablePublish('cancel');setCurrentDate('cancel');setTime('cancel');"/>Снять с публикации с
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="cancelPublishDate"><bean:write name="form" property="field(cancelPublishDate)" format="dd.MM.yyyy"/></c:set>
                            <html:text property="field(cancelPublishDate)" value="${cancelPublishDate}" styleClass="dot-date-pick" onkeydown="setTime('cancel');" disabled="true" maxlength="10"/>
                            <c:set var="cancelPublishTime"><bean:write name="form" property="field(cancelPublishTime)" format="HH:mm"/></c:set>
                            <html:text property="field(cancelPublishTime)" value="${cancelPublishTime}" styleClass="pmntDate short-time-template" disabled="true" maxlength="8"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </fieldset>
        </div>
        <div class="clear"></div>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="news.important" bundle="newsBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:select property="field(important)" styleClass="select" disabled="${view}">
                    <html:option value="HIGH"><bean:message key="news.important.high" bundle="newsBundle"/></html:option>
                    <html:option value="LOW"><bean:message key="news.important.low" bundle="newsBundle"/> </html:option>
                </html:select>

            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.state" bundle="newsBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="needMargin" value="true"/>
            <tiles:put name="data">
                <div class="bold"><bean:message key="label.state.${state}" bundle="newsBundle"/> <html:hidden property="field(state)" /> </div>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <%--TODO BUG023004: Выбор ТБ для отображения новости.--%>
                ТБ, которому будет отображаться
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <div name="departmentName">
                    <html:text   property="field(departmentsName)" styleId="field(departmentsName)" readonly="true" size="60"/>
                    <html:hidden  property="field(TB)" styleId="field(TB)"/>
                    <input  type="button" class="buttWhite smButt" onclick="openAllowedTBDictionary(setDepartmentInfo);" value="..."/>
                </div>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                Тип отображения
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:select property="field(type)" styleClass="select" disabled="${view}">
                    <c:choose>
                        <c:when test="${form.mainNews == true}">
                            <html:option value="MAIN_PAGE">На главной странице</html:option>
                            <html:option value="PUSH">push notification</html:option>
                        </c:when>
                        <c:otherwise>
                            <html:option value="LOGIN_PAGE">На странице входа</html:option>
                        </c:otherwise>
                    </c:choose>
                </html:select>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="news.title" bundle="newsBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:text  readonly="${view}"  property="field(title)" styleId="titleText" size="60" maxlength="60"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="news.short.text" bundle="newsBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <table>
                    <tr>
                        <td align="center"><button onmousedown="changeSelText('b', ['shortText', 'newsText']);"><b>Ж</b></button></td>
                        <td align="center"><button onmousedown="changeSelText('i', ['shortText', 'newsText']);"><i>К</i></button></td>
                        <td align="center"><button onmousedown="changeSelText('u', ['shortText', 'newsText']);"><u>Ч</u></button></td>
                        <td align="center"><button onmousedown="hideOrShow('color');"><span style="font-size:8pt">Выделение цветом</span></button></td>
                        <td align="center"><button onmousedown="addHyperlink(['shortText', 'newsText']);"><span style="font-size:8pt">Гиперссылка</span></button>
                            <input  id="url" type="hidden" size="65" value=""/></td>
                    </tr>
                    <tr id="color" style="display:none;">
                        <td colspan="5">
                            <table id="palitcolor" style="border:1px; cursor:pointer;">
                                <tr>
                                    <td bgcolor="yellow"><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'yellow');" src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="olive" ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'olive');"  src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="fuchia"><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'fuchia');" src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="red"   ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'red');"    src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="purple"><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'purple');" src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="maroon"><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'maroon');" src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="navy"  ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'navy');"   src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="blue"  ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'blue');"   src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="aqua"  ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'aqua');"   src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="lime"  ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'lime');"   src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="green" ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'green');"  src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="teal"  ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'teal');"   src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="silver"><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'silver');" src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                    <td bgcolor="gray"  ><img onmousedown="changeSelText('c', ['shortText', 'newsText'], 'gray');"   src="${globalImagePath}/1x1.gif" width="14" height="14" border="0"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <html:textarea  readonly="${view}"  property="field(shortText)" styleId="shortText"  cols="58" rows="2"/>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="news.text" bundle="newsBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:textarea  readonly="${view}"  property="field(text)" styleId="newsText" cols="58" rows="9"/>
            </tiles:put>
        </tiles:insert>

        </tiles:put>
    </tiles:insert>
    <div style="float: left">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey"     value="back.to.list"/>
            <tiles:put name="commandHelpKey" value="back.to.list.help"/>
            <tiles:put name="bundle"  value="localeBundle"/>
            <tiles:put name="action" value="${cancelLink}"/>
            <tiles:put name="viewType" value="blueBorder"/>
        </tiles:insert>
    </div>
	</tiles:put>
</tiles:insert>
</html:form>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/inform/message/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="informMessageEdit">
    <tiles:put name="submenu" type="string" value="InformMessages"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="edit.title" bundle="informMessagesBundle"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                var addedPages = new Array();
                var _superReinitField =  window.reinitField;

                function callPagesDictionaries(event)
                {
                    var space = 100;
                    var h = getClientHeight()-space;
                    var w = getClientWidth()-space;
                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1,scrollbars=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=" + space/2 +
                                 ", top=" + space/2;
                    var pwin = openWindow(null, "${phiz:calculateActionURL(pageContext, '/dictionaries/pages/list.do')}", "dialog", winpar);

                    pwin.focus();
                }

                function setPageInfo(results)
                {
                    var countNew = 0;
                    for(var i = 0; i < results.length; i++)
                    {
                        var res = results[i];
                        var id = res['id'];
                        var name = res['name'];
                        if(document.getElementById(id) == null)
                        {
                            countNew++;
                            addedPages.push(id);
                            $("#lastTableElement").before(
                            '<tr id="'+ id +'">'+
                                 '<td>'+
                                      '<input type="checkbox" name="selectedPages" value="'+id+'" class="pageCheckbox"/>'+
                                      '<input type="hidden" name="selectedIds" value="'+id+'" style="border:none;"/>'+
                                 '</td>'+
                                 '<td>'+
                                      '<span id="'+id+'_id_name">'+
                                       name+
                                      '</span>'+
                                 '</td>'+
                            '</tr>'
                            );
                        }
                    }
                    $("#pagesTable").css("display", "");
                    if (countNew != 0)
                        document.getElementById('mainCheck').checked = false;
                }

                function deletePages()
                {
                    checkIfOneItem("selectedPages");
                    if (!checkSelection("selectedPages", "Выберите страницу!"))
                               return;
                    var ids = document.getElementsByName("selectedPages");
                    var size = ids.length;
                    var c = 0;
                    for (var i = 0; i < size; i++)
                    {
                        if (ids.item(c).checked)
                        {
                            var id_id = ids.item(c).value;
                            $('#'+id_id).remove();
                        }
                        else
                        {
                            c++;
                        }
                    }
                    if (ids.length <= 0)
                    {
                        $("#pagesTable").css("display", "none");
                    }
                }

                function openTemplates(event)
                {
                    var space = 100;
                    var h = getClientHeight()-space;
                    var w = getClientWidth()-space;
                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1,scrollbars=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=" + space/2 +
                                 ", top=" + space/2;
                    var pwin = openWindow(null, "${phiz:calculateActionURL(pageContext, '/inform/template/list.do')}?dictionary=true", "dialog", winpar);
                    pwin.focus();
                }

                function setIndefinitely()
                {
                    var check = getRadioValue(document.getElementsByName("field(indefinitely)"));
                    var disable = (check != null && check);
                    var date = getElement("field(toDate)");
                    var time = getElement("field(toTime)");
                    date.disabled = disable;
                    time.disabled = disable;
                    if(disable)
                    {
                        date.value = '';
                        time.value = '';
                    }
                }

                function removeAddedPages()
                {

                    for (var i = 0; i < addedPages.length; i++)
                    {
                         $('#'+addedPages[i]).remove();
                    }
                    if (document.getElementsByName("selectedPages").length <= 0)
                    {
                        $("#pagesTable").css("display", "none");
                    }
                }

                window.reinitField =  function()
                {
                    removeAddedPages();
                    _superReinitField.call(this);
                    setIndefinitely();
                }

                function saveTemplate()
                {
                    var result = prompt('Введите имя шаблона', '${form.fields.name}');
                    if(result == null)
                       return;

                    if(result == '')
                    {
                        alert('Введите название шаблона.');
                        return;
                    }

                    $('#saveAsTemplate').val(true);
                    $('#templateName').val(result);
                    new CommandButton('button.save').click();
                }

                function getCurrentColor()
                {
                    return $('#textCotor').css('color');
                }

                function setColorOnPanel(radio)
                {
                    var element = document.getElementById('textCotor');
                    if (radio == 'HIGH')
                        element.className = 'highImportanceText';
                    else if (radio == 'MEDIUM')
                        element.className = 'mediumImportanceText';
                    else if (radio == 'LOW')
                        element.className = 'lowImportanceText';
                }

                // Если сотрудник изменил важность сообщения и на данный момент часть текста была выделена цветом
                // то необходимо изменить цвет этой части текста в соответствии с важностью сообщения.
                function changeColor(radio)
                {
                    setColorOnPanel(radio);

                    var newColor = getCurrentColor();
                    var textArea = document.getElementById('text');
                    var text = textArea.value;
                    text = text.split(oldColor).join(newColor);
                    oldColor = newColor;
                    textArea.value = text;
                }
            </script>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description">
                    <bean:message key="label.description" bundle="informMessagesBundle"/>
                </tiles:put>

                <tiles:put name="data">
                    <html:hidden property="field(saveAsTemplate)"  styleId="saveAsTemplate"/>
                    <html:hidden property="field(name)" value="${form.fields.name}" styleId="templateName"/>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.selectTemplate"/>
                                <tiles:put name="commandHelpKey" value="button.selectTemplate.help"/>
                                <tiles:put name="bundle" value="informMessagesBundle"/>
                                <tiles:put name="onclick" value="openTemplates()"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.publication.place" bundle="informMessagesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <table>
                                <tr>
                                    <td>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.add"/>
                                            <tiles:put name="commandHelpKey" value="button.add.help"/>
                                            <tiles:put name="bundle" value="informMessagesBundle"/>
                                            <tiles:put name="onclick" value="callPagesDictionaries()"/>
                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                        </tiles:insert>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.delete"/>
                                            <tiles:put name="commandHelpKey" value="button.delete.help"/>
                                            <tiles:put name="bundle" value="informMessagesBundle"/>
                                            <tiles:put name="onclick" value="deletePages()"/>
                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                        </tiles:insert>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="pageList" id="pagesTable" style="display:none;">
                                            <tiles:insert definition="tableTemplate" flush="false">
                                                <tiles:put name="id" value="PagesList"/>

                                                <tiles:put name="head">
                                                    <th class="titleTable" width="20px">
                                                        <input type="checkbox" id="mainCheck" onclick="switchSelection(this,'selectedPages')">
                                                    </th>
                                                    <th class="titleTable">Страницы</th>
                                                </tiles:put>
                                                <tiles:put name="data">
                                                    <%@ include file="pagesList.jsp"%>
                                                </tiles:put>
                                                <tiles:put name="isEmpty" value="${false}"/>
                                                <tiles:put name="emptyMessage"><bean:message key="message.empty" bundle="informMessagesBundle"/></tiles:put>
                                            </tiles:insert>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.publication" bundle="informMessagesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:radio property="fields(viewType)" value="STATIC_MESSAGE"/><bean:message key="label.static" bundle="informMessagesBundle"/><br/>
                            <html:radio property="fields(viewType)" value="POPUP_WINDOW"/><bean:message key="label.popup" bundle="informMessagesBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.available.tb" bundle="informMessagesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <%@ include file="departmentsList.jsp"%>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.publication.period" bundle="informMessagesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="fromDate"><bean:write name="form" property="fields.fromDate" format="dd.MM.yyyy"/></c:set>
                            <c:set var="fromTime"><bean:write name="form" property="fields.fromTime" format="HH:mm:ss"/></c:set>
                            с &nbsp; <html:text property="field(fromDate)" value="${fromDate}" maxlength="10" styleClass="dot-date-pick"/>
                            <html:text property="field(fromTime)" value="${fromTime}" maxlength="8" styleClass="time-template"/></br>
                            <c:set var="toDate"><bean:write name="form" property="fields.toDate" format="dd.MM.yyyy"/></c:set>
                            <c:set var="toTime"><bean:write name="form" property="fields.toTime" format="HH:mm:ss"/></c:set>
                            по       <html:text property="field(toDate)" value="${toDate}" disabled="true" maxlength="10" styleClass="dot-date-pick"/>
                            <html:text property="field(toTime)" value="${toTime}" disabled="true" maxlength="8" styleClass="time-template"/>
                            <html:checkbox property="field(indefinitely)" onclick="setIndefinitely();"/> бессрочно
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.importance" bundle="informMessagesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <table cellpadding="0" cellspacing="0">
                                <tr>
                                    <td><html:radio  property="field(importance)" value="HIGH" style="border:0" onclick="changeColor('HIGH');"/></td>
                                    <td class="highImportance">
                                        <bean:message key="label.importance.high" bundle="informMessagesBundle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><html:radio  property="field(importance)" value="MEDIUM" style="border:0" onclick="changeColor('MEDIUM');"/></td>
                                    <td class="mediumImportance">
                                        <bean:message key="label.importance.medium" bundle="informMessagesBundle"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><html:radio  property="field(importance)" value="LOW" style="border:0" onclick="changeColor('LOW');"/></td>
                                    <td class="lowImportance">
                                        <bean:message key="label.importance.low" bundle="informMessagesBundle"/>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.message" bundle="informMessagesBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea styleId="text" property="field(text)" cols="50" rows="4" style="text-align:justify;"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <table class="formatPanel">
                                <tr>
                                    <td><button onmousedown="changeSelText('b', ['text']);"><b>Ж</b></button></td>
                                    <td><button onmousedown="changeSelText('i', ['text']);"><i>К</i></button></td>
                                    <td><button onmousedown="changeSelText('u', ['text']);"><u>Ч</u></button></td>
                                    <td><button onmousedown="changeSelText('c', ['text'], getCurrentColor());"><span id="textCotor" class="mediumImportanceText"><b><u>Т</u></b></span></button></td>
                                    <td><button onmousedown="addHyperlink(['text']);"><span style="font-size:8pt">Гиперссылка</span></button></td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                <script type="text/javascript">
                     if(${not empty form.pages && form.pages != null})
                     {
                        $("#pagesTable").css("display", "");
                     }
                     setColorOnPanel('${form.fields.importance}');
                     setIndefinitely();
                     var oldColor = getCurrentColor();
                     addClearMasks(null,
                             function(event)
                             {
                                 clearInputTemplate('field(fromDate)', '__.__.____');
                                 clearInputTemplate('field(toDate)', '__.__.____');
                                 clearInputTemplate('field(fromTime)', '__:__:__');
                                 clearInputTemplate('field(toTime)', '__:__:__');
                             });
                </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditInformMessagesOperation">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="informMessagesBundle"/>
                        <tiles:put name="action"  value="/inform/message/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="EditInformMessagesOperation">
                        <tiles:put name="commandTextKey"     value="button.saveTemplate"/>
                        <tiles:put name="commandHelpKey" value="button.saveTemplate.help"/>
                        <tiles:put name="bundle"  value="informMessagesBundle"/>
                        <tiles:put name="onclick" value="saveTemplate();"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditInformMessagesOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="informMessagesBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>
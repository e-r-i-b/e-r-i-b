<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/mail/area/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="commonImgPath" value="${globalUrl}/commonSkin/images"/>
    <tiles:insert definition="contactCenterAreaEdit">
        <tiles:put name="submenu" type="string" value="ContactCenterArea"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.edit.title" bundle="contactCenterAreaBundle"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description">
                    <bean:message key="label.edit.description" bundle="contactCenterAreaBundle"/>
                </tiles:put>

                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.area.name" bundle="contactCenterAreaBundle"/>:
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="30"  maxlength="50" styleId="name"/>
                        </tiles:put>
                    </tiles:insert>

                    <%-- Тербанки, которые входят в площадку --%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.area.TB" bundle="contactCenterAreaBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add.tb"/>
                                <tiles:put name="commandHelpKey" value="button.add.tb.help"/>
                                <tiles:put name="bundle" value="contactCenterAreaBundle"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                                <tiles:put name="onclick"  value="openAllowedTBDictionary(setDepartmentInfo)"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle" value="contactCenterAreaBundle"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                                <tiles:put name="onclick"  value="deleteDepartments()"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <table>
                                <c:if test="${not empty form.departments && form.departments != null}">
                                    <c:forEach items="${form.departments}" var="department">
                                        <tr id="dep${department}">
                                            <td width="20px">
                                                <input type="checkbox" name="selectedDepartments" value="${department}" class="departmentCheckbox"/>
                                                <input type="hidden" name="selectedTBs" value="${department}" style="border:none;"/>
                                            </td>
                                            <td>
                                                <span id="${department}_id_name">
                                                    <c:out value="${phiz:getDepartmentName(department, null, null)}"/>
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <tr id="lastTableElement" style="display:none;">
                                    <td></td>
                                    <td></td>
                                </tr>
                            </table>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditAreaOperation">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="contactCenterAreaBundle"/>
                        <tiles:put name="action"  value="/mail/area/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditAreaOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="contactCenterAreaBundle"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>

            </tiles:insert>
            <script type="text/javascript">
                var addedDepartments = new Array();
                function openAllowedTBDictionary(callback)
                {
                    window.setDepartmentInfo = callback;
                    var h = getClientHeight() - 100;
                    var w = getClientWidth() - 100;

                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=" + (getClientWidth() - w) / 2 +
                                 ", top=" + (getClientHeight() - h) / 2;
                    win = openWindow(null, document.webRoot+'/dictionaries/allowedTerbanks.do?type=manySelection',
                                   'Departments', winpar);
                    win.focus();
                }

                function setDepartmentInfo(resource)
                {
                    var ids   = new Array();
                    var names = new Array();
                    ids = resource['region'].split(',');
                    names = resource['name'].split(',');
                    for(var i = 0; i < ids.length; i++)
                    {
                        var id = ids[i];
                        var name = names[i];
                        if(document.getElementById('dep'+id) == null)
                        {
                            addedDepartments.push(id);
                            $("#lastTableElement").before(
                            '<tr id="dep'+ id +'">'+
                                 '<td>'+
                                      '<input type="checkbox" name="selectedDepartments" value="'+id+'" class="departmentCheckbox"/>'+
                                      '<input type="hidden" name="selectedTBs" value="'+id+'" style="border:none;"/>'+
                                 '</td>'+
                                 '<td>'+
                                      '<span id="'+id+'_id_name"></span>'+
                                 '</td>'+
                            '</tr>'
                            );
                             $("#"+id+"_id_name").text(name);
                        }
                    }
                }

                function deleteDepartments()
                {
                    checkIfOneItem("selectedDepartments");
                    if (!checkSelection("selectedDepartments", "Выберите департамент!"))
                               return;
                    $('[name=selectedDepartments]:checked').parents("[id^=dep]").remove();
                }
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
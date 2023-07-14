<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/dictionaries/allowedTerbanks" onsubmit="return setEmptyAction(event);">
<c:set var="frm" value="${ListAllowedTerbanksForm}"/>

<tiles:insert definition="dictionary">
    <tiles:put name="submenu" type="string" value="allowedDepartments"/>

    <tiles:put name="data" type="string">

        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="AllowedDepartmentListTable"/>
            <tiles:put name="text">Список тербанков</tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.choose"/>
                    <tiles:put name="commandHelpKey" value="button.choose"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="setSelectedDepartmentsData(event)"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="newsBundle"/>
                    <tiles:put name="onclick" value="javascript:window.close()"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="data">
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="no-pagination" property="terbanks" bundle="personsBundle" styleClass="depositProductInfo">

                        <sl:collectionParam id="selectType" value="${frm.type == 'oneSelection'? 'radio' : 'checkbox'}"/>
                        <sl:collectionParam id="selectName" value="selectedIds"/>
                        <sl:collectionParam id="selectProperty" value="id"/>

                        <sl:collectionItem title="Наименование ТБ">
                            <input type="hidden" value="${listElement.name}" id="name${listElement.id}"/>
                            <c:out value="${listElement.name}"/></sl:collectionItem>
                        <sl:collectionItem hidden="true" property="region"/>
                        <sl:collectionItem hidden="true" property="synchKey"/>

                        <sl:collectionParam id="onRowDblClick"  value="setSelectedDepartmentsData(event);"  condition="${frm.type == 'oneSelection'}"/>
                      </sl:collection>
                </tiles:put>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    <script type="text/javascript">
        function setSelectedDepartmentsData(event)
        {
            var ids = document.getElementsByName("selectedIds");
            preventDefault(event);
            var res = new Array();
            var id='';
            var name = '';
            var region = '';
            var sKey = '';

            for (var i=0; i<ids.length; i++)
            {
                if (ids.item(i).checked)
                {
                    var checked = 1;
                    if (id != "") id = id + ',';
                    var valueId = ids.item(i).value;
                    id = id+valueId;
                    var r = ids.item(i).parentNode.parentNode;
                    if (name != "") name = name + ', ';
                    name = name + trim($("#name"+valueId).val());
                    if(region != "") region = region+',';
                    region = region + trim(r.cells[2].innerHTML);
                    if(sKey != "") sKey = sKey+',';
                    sKey = sKey + trim(r.cells[3].innerHTML);
                }
            }
            if (checked){
                res['name'] = name;
                res['id']   = id;
                res['region'] = region;
                res['sKey'] = sKey;
                window.opener.setDepartmentInfo(res);
                window.close();
                return;
            }
            else
                alert("Выберите подразделения!");
        }
    </script>
</tiles:insert>

</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/whitelist/list" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
     <tiles:insert definition="whiteListUrlEdit">
        <%-- Пункт левого меню --%>
        <tiles:put name="submenu" type="string" value="WhiteListUrl"/>
         <tiles:put name="menu" type="string">
             <div id="buttonsRow" class="floatRight">
                 <tiles:insert definition="clientButton" flush="false">
                     <tiles:put name="commandTextKey" value="button.add"/>
                     <tiles:put name="commandHelpKey" value="button.add"/>
                     <tiles:put name="bundle" value="whiteListUrlBundle"/>
                     <tiles:put name="onclick">addMask();</tiles:put>
                 </tiles:insert>
                 <div id="removeButton" class="floatRight">
                     <tiles:insert definition="clientButton" flush="false">
                         <tiles:put name="commandTextKey" value="button.remove"/>
                         <tiles:put name="commandHelpKey" value="button.remove"/>
                         <tiles:put name="bundle" value="whiteListUrlBundle"/>
                         <tiles:put name="onclick">removeUrlMask();</tiles:put>
                     </tiles:insert>
                 </div>
             </div>
         </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name"><bean:message bundle="whiteListUrlBundle" key="label.list.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="whiteListUrlBundle" key="label.list.description"/></tiles:put>
                <tiles:put name="data">
                    <div id="emptyUrlList" class="align-center" style="display: none;">
                        <bean:message bundle="whiteListUrlBundle" key="label.table.empty"/>
                    </div>
                    <table id="urlMaskTable" class="standartTable" cellspacing="0" cellpadding="0" width="100%">
                        <tbody>
                            <tr class="tblInfHeader">
                                <th class="titleTable" width="20px">
                                    <input type="checkbox" id="isSelectAll"  name="isSelectAll" onclick="switchSelection('isSelectAll','selectedIds');"/>
                                </th>
                                <th class="titleTable"><bean:message bundle="whiteListUrlBundle" key="label.table.row.title"/></th>
                            </tr>
                            <c:set var="maskCount" value="${phiz:size(form.data)}"/>
                            <input type="hidden"  name="fields(urlMaskCount)" value="${maskCount-1}"/>
                            <c:if test="${maskCount > 0}">
                                <c:forEach var="i" begin="0" end="${maskCount-1}">
                                    <c:set var="url" value="${form.data[i].url}"/>
                                    <c:set var="id" value="${form.data[i].id}"/>
                                    <input type="hidden"  name="fields(urlMaskListIds${i})" value="${id}"/>
                                    <c:set var="fieldName" value="urlMask${i}"/>
                                    <tr id="${fieldName}" class="ListLine0">
                                        <td  class="listItem"> <input type="checkbox" value="${i}" name="selectedIds" id="chkMask${i}"/> </td>
                                        <td>
                                            <input type="hidden" id="urlMaskList${i}" name="fields(urlMaskList${i})" value="${url}"/>
                                            <a href="#" id="urlMaskHref${i}" onclick="openEditUrlMaskWindow(true, '${i}');">${url}</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>
                    <script type="text/javascript">
                        var stringCount = ${maskCount-1};
                        function mask(id, mask)
                        {
                            this.id = id;
                            this.mask = mask;
                        }

                        var currMask = null;
                        function getCurrMask()
                        {
                            var tmp = currMask;
                            currMask = null;
                            return tmp;
                        }
                        //update: true - редактирование, false - добавление
                        function openEditUrlMaskWindow(update, id)
                        {
                            var url = "${phiz:calculateActionURL(pageContext,'/whitelist/add.do')}" + "?update=" + update;
                            var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1,width=1000,height=350"+
                                 ", left=" + (screen.width - 1000) / 2 +
                                 ", top=" + (screen.height - 450) / 2;
                            currMask = new mask(id, $("#urlMaskList"+id).val());
                            window.open(url, "EditUurlMasks", winpar);
                        }

                        //Открывает окно добавления
                        function addMask()
                        {
                            openEditUrlMaskWindow(false);
                        }
                        //Добавляет строку в таблицу
                        function drawRow(urlMask)
                        {
                            showEmptyMessage(false);
                            var table = $("#urlMaskTable").children();

                            var newRow = $(table).html()+"<tr id='urlMask"+stringCount + "' class='ListLine0'>"
                                            +"<td  class='listItem'> <input type='checkbox' value='"+stringCount+"' name='selectedIds' id='chkMask"+stringCount+"'/></td>"
                                            +"<td>"
                                                +"<input type='hidden' id='urlMaskList"+stringCount+"' name='fields(urlMaskList"+stringCount+")' value='"+urlMask+"'/>"
                                                +"<a href='#' id='urlMaskHref"+stringCount+"' onclick='openEditUrlMaskWindow(true,"+stringCount+" );'>"+urlMask+"</a>"
                                            +"</td>"
                                         +"</tr>";
                            $(table).html(newRow);
                            $("input[name=fields(urlMaskCount)]").val(stringCount);
                        }

                        function removeUrlMask()
                        {
                            checkIfOneItem("selectedIds");
                            if(!checkSelection("selectedIds",'<bean:message bundle="whiteListUrlBundle" key="label.table.remove"/>'))
                                return;
                            $('input[name=selectedIds]:checked').each(function(){$(this).parent().parent().remove();});
                            if ($('input[name=selectedIds]').length == 0)
                                showEmptyMessage(true);
                        }
                        function redrawRow(id, newMask)
                        {
                            $("#urlMaskList"+id).val(newMask);
                            $("#urlMaskHref"+id).html(newMask);
                        }
                        function addOrUpdateMask(currMask, update)
                        {
                            var isNew = !update;
                            var id = currMask.id;
                            var newMask = currMask.mask;

                            var tmp = $('input[name=selectedIds]');
                            for(var i = tmp.length - 1; i >= 0; --i)
                            {
                               var strId = $(tmp[i]).val();
                                if($("#urlMaskList"+strId).val() == newMask && id != strId)
                                    return  false;
                            }
                            if(isNew)
                            {
                                stringCount ++;
                                drawRow(newMask);
                            }
                            else
                            {
                                redrawRow(id, newMask);
                            }
                            return true;
                        }
                        function showEmptyMessage(show)
                        {
                            if (show)
                            {
                                $("#urlMaskTable").hide();
                                $("#emptyUrlList").show();
                            }
                            else
                            {
                                $("#urlMaskTable").show();
                                $("#emptyUrlList").hide();
                            }
                        }

                        function init()
                        {
                            if ($("input[name=fields(urlMaskCount)]").val() < 0)
                                showEmptyMessage(true);
                        }
                        init();
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="whiteListUrlBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

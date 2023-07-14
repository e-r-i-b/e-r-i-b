<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${ExceptionEntryEditForm}"/>
<c:set var="exceptionEntryType" value="${form.exceptionEntryType}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:choose>
    <c:when test="${exceptionEntryType eq 'internal'}">
        <c:set var="currentAction" value="/configure/exceptions/edit"/>
        <c:set var="listAction" value="/configure/exceptions/list"/>
    </c:when>
    <c:otherwise>
        <c:set var="currentAction" value="/configure/exceptions/external/edit"/>
        <c:set var="listAction" value="/configure/exceptions/external/list"/>
    </c:otherwise>
</c:choose>
<html:form action="${currentAction}" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="logMain">
        <c:choose>
            <c:when test="${exceptionEntryType eq 'internal'}">
                <tiles:put name="submenu" type="string" value="ExceptionEntryList"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="submenu" type="string" value="ExternalExceptionEntryEdit"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="label.${exceptionEntryType}.edit.header" bundle="exceptionEntryBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="label.${exceptionEntryType}.edit.description" bundle="exceptionEntryBundle"/>
                </tiles:put>
                <tiles:put name="additionalStyle" value="width750"/>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.${exceptionEntryType}.edit.id" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:if test="${not form.newEntry}">
                                <html:hidden property="id"/>
                            </c:if>
                            <div id="deletedGroups" class="hidden">
                                <c:forEach var="deletedGroup" items="${form.deletedGroupIds}">
                                    <html:hidden property="deletedGroupIds" value="${deletedGroup}"/>
                                </c:forEach>
                            </div>
                            <html:text property="field(id)" readonly="${not form.newEntry}" size="30" maxlength="10"/>
                            <c:if test="${form.newEntry}">
                                <input type="button" class="buttWhite smButt" onclick="openDictionary();" value="..."/>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${exceptionEntryType eq 'external'}">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.${exceptionEntryType}.edit.error" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(errorCode)" readonly="true" size="30"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.${exceptionEntryType}.edit.system" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(system)" readonly="true" size="30"/>
                        </tiles:put>
                    </tiles:insert>
                    </c:if>

                    <c:if test="${exceptionEntryType eq 'internal'}">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.${exceptionEntryType}.edit.application" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(application)" readonly="true" size="30"/>
                        </tiles:put>
                    </tiles:insert>
                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.${exceptionEntryType}.edit.operation" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(operation)" readonly="true" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.${exceptionEntryType}.edit.detail" bundle="exceptionEntryBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:textarea property="field(detail)" readonly="true" cols="63" rows="5"/>
                        </tiles:put>
                    </tiles:insert>
                    <div id="messageSettingsBlocks">
                        <c:forEach items="${form.groupIds}" var="i">
                            <fieldset class="messageSettingsBlock">
                                <legend><bean:message key="label.settings.legend" bundle="exceptionEntryBundle"/>&nbsp;
                                    <c:if test="${i > 0}">
                                        <a><span onclick="removeBlock(this);">
                                           <bean:message key="button.remove" bundle="exceptionEntryBundle"/>
                                        </span></a>
                                    </c:if>
                                </legend>
                                <html:hidden property="groupIds" value="${i}"/>
                                <c:set var="fieldName" value="group_${i}"/>

                                <c:if test="${exceptionEntryType eq 'external'}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message key="label.settings.channel" bundle="exceptionEntryBundle"/>
                                        </tiles:put>
                                        <tiles:put name="isNecessary" value="true"/>
                                        <tiles:put name="data">
                                            <table class="standartTable" id="channelTable_${i}">
                                                <tr>
                                                    <td>
                                                        <input type="checkbox" onclick="selectAllChannels(${i})"/>
                                                    </td>
                                                    <td>
                                                        <bean:message key="label.list.channel" bundle="exceptionEntryBundle"/>
                                                        <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" style="padding: 5px 0 0 7px" onmouseover="showHint(event);" onmouseout="hideHint()"/>
                                                    </td>
                                                </tr>
                                                <c:forEach items="${form.channels}" var="channel">
                                                        <tr>
                                                            <td>
                                                                <html:checkbox name="form" property="field(${channel}_${i})" value="true"/>
                                                            </td>
                                                            <td>
                                                                <span id="${channel}_id_name">
                                                                    <bean:message key="channel.${channel}" bundle="exceptionEntryBundle"/>
                                                                </span>
                                                            </td>
                                                        </tr>
                                                </c:forEach>
                                            </table>
                                        </tiles:put>
                                    </tiles:insert>
                                </c:if>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="labet.settings.TB" bundle="exceptionEntryBundle"/>
                                    </tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.add"/>
                                            <tiles:put name="commandHelpKey" value="button.add.help"/>
                                            <tiles:put name="bundle" value="exceptionEntryBundle"/>
                                            <tiles:put name="onclick" value="openAllowedTBDictionary(${i})"/>
                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                        </tiles:insert>

                                        <span id="pagesTable_${i}" <c:if test="${empty form.numOfDepartmentsInBlock[fieldName]}"> style="display: none"</c:if>>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.remove"/>
                                            <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                            <tiles:put name="bundle" value="exceptionEntryBundle"/>
                                            <tiles:put name="onclick" value="deleteDepartments(${i})"/>
                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                        </tiles:insert>
                                        </span>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="data">
                                        <html:hidden property="numOfDepartmentsInBlock(group_${i})" value="${form.numOfDepartmentsInBlock[fieldName]}"/>
                                        <table id="terbanksTable${i}" class="standartTable">
                                            <tr>
                                                <td>
                                                    <input type="checkbox" id="checkbox_${i}" onclick="selectAllTb(${i});"/>
                                                </td>
                                                <td>
                                                    <bean:message key="label.list.TB" bundle="exceptionEntryBundle"/>
                                                    <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" style="padding: 5px 0 0 7px" onmouseover="showHint(event, 'tb');" onmouseout="hideHint()"/>
                                                </td>
                                            </tr>
                                            <c:if test="${not empty form.numOfDepartmentsInBlock[fieldName] and form.numOfDepartmentsInBlock[fieldName] > 0}">
                                                <c:forEach begin="0" end="${form.numOfDepartmentsInBlock[fieldName]-1}" var="j">
                                                    <c:set var="departmentFieldName" value="department_${i}_${j}"/>
                                                    <c:set var="department" value="${form.fields[departmentFieldName]}"/>
                                                    <tr id="dep_${department}_${i}">
                                                        <td>
                                                            <input type="checkbox" name="selectedDepartments_${i}" value="${department}" class="departmentCheckbox"/>
                                                            <input type="hidden" name="field(department_${i}_${j})" value="${department}" style="border:none;"/>
                                                        </td>
                                                        <td>
                                                            <span id="${department}_id_name_${i}">
                                                                <c:out value="${phiz:getDepartmentName(department, null, null)}"/>
                                                            </span>
                                                        </td>
                                                    </tr>

                                                </c:forEach>
                                            </c:if>
                                        </table>
                                        <div id="exceptionMessageDepartments${i}" style="display:none;"></div>
                                    </tiles:put>
                                </tiles:insert>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.${exceptionEntryType}.edit.message" bundle="exceptionEntryBundle"/>
                                    </tiles:put>
                                    <tiles:put name="isNecessary" value="true"/>
                                    <tiles:put name="data">
                                        <html:textarea styleId="exceptionMessage" property="field(message_${i})" cols="63" rows="5"/>
                                    </tiles:put>
                                </tiles:insert>

                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="data">
                                        <tiles:insert definition="bbCodeButtons" flush="false">
                                            <tiles:put name="textAreaId" value="exceptionMessage"/>
                                            <tiles:put name="showFio" value="true"/>
                                            <tiles:put name="showBold" value="true"/>
                                            <tiles:put name="showItalics" value="true"/>
                                            <tiles:put name="showUnderline" value="true"/>
                                            <tiles:put name="showColor" value="true"/>
                                            <tiles:put name="showHyperlink" value="true"/>
                                            <tiles:put name="showTextAlign" value="true"/>
                                        </tiles:insert>
                                    </tiles:put>
                                </tiles:insert>
                            </fieldset>

                        </c:forEach>
                    </div>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.settings.add.message"/>
                                <tiles:put name="commandHelpKey" value="button.settings.add.message.help"/>
                                <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                                <tiles:put name="onclick"        value="addMessageBlock(getBlockNumber());"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                        <tiles:put name="action"         value="${listAction}"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="exceptionEntryBundle"/>
                        <tiles:put name="validationFunction" value="checkExceptionEntrySelection();"/>
                    </tiles:insert>
                    <c:if test="${not empty form.hash}">
                        <tiles:insert definition="languageSelectForEdit" flush="false">
                            <tiles:put name="selectId" value="chooseLocale"/>
                            <tiles:put name="idName"   value="stringId"/>
                            <tiles:put name="entityId" value="${form.hash}"/>
                            <tiles:put name="styleClass" value="float"/>
                            <tiles:put name="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/configure/exceptions/locale/save')}"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    <div id="defaultMessageBlock" style="display:none">
        <fieldset class="messageSettingsBlock">
            <legend><bean:message key="label.settings.legend" bundle="exceptionEntryBundle"/>&nbsp;<a><span onclick="removeBlock(this);">
            <bean:message key="button.remove" bundle="exceptionEntryBundle"/></span></a></legend>
            <html:hidden property="groupIds" value="block_Id_Parameter"/>

            <c:if test="${exceptionEntryType eq 'external'}">
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.settings.channel" bundle="exceptionEntryBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <table class="standartTable" id="channelTable">
                            <tr>
                                <td>
                                    <input type="checkbox" onclick="selectAllChannels()"/>
                                </td>
                                <td>
                                    <bean:message key="label.list.channel" bundle="exceptionEntryBundle"/>
                                    <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" style="padding: 5px 0 0 7px" onmouseover="showHint(event);" onmouseout="hideHint()"/>
                                </td>
                            </tr>
                            <c:forEach items="${form.channels}" var="channel">
                                <tr>
                                        <td>
                                            <html:checkbox property="field(${channel}_block_Id_Parameter)" value="true" name="form"/>
                                        </td>
                                        <td>
                                            <span id="${channel}_id_name">
                                                <bean:message key="channel.${channel}" bundle="exceptionEntryBundle"/>
                                            </span>
                                        </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="labet.settings.TB" bundle="exceptionEntryBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:hidden property="numOfDepartmentsInBlock(group_block_Id_Parameter)"/>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.add"/>
                        <tiles:put name="commandHelpKey" value="button.add.help"/>
                        <tiles:put name="bundle" value="exceptionEntryBundle"/>
                        <tiles:put name="onclick" value="openAllowedTBDictionary('block_Id_Parameter')"/>
                        <tiles:put name="viewType" value="buttonGrayNew"/>
                    </tiles:insert>
                    <span id="defaultPagesTable" style="display: none">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.remove"/>
                            <tiles:put name="commandHelpKey" value="button.remove.help"/>
                            <tiles:put name="bundle" value="exceptionEntryBundle"/>
                            <tiles:put name="onclick" value="deleteDepartments('block_Id_Parameter')"/>
                            <tiles:put name="viewType" value="buttonGrayNew"/>
                        </tiles:insert>
                    </span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="data">
                    <table id="emptyTerbanksTable" class="standartTable">
                        <tr>
                            <td>
                                <input type="checkbox" id="emptyCheckbox" onclick="selectAllTb();"/>
                            </td>
                            <td>
                                <bean:message key="label.list.TB" bundle="exceptionEntryBundle"/>
                                <img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px" style="padding: 5px 0 0 7px" onmouseover="showHint(event, 'tb');" onmouseout="hideHint()"/>
                            </td>
                        </tr>
                    </table>
                    <div id="defaultMessageDepartments" style="display:none;"></div>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.${exceptionEntryType}.edit.message" bundle="exceptionEntryBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:textarea styleId="defaultExceptionMessage" property="field(defaultMessageField)" cols="63" rows="5"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="data">
                    <tiles:insert definition="bbCodeButtons" flush="false">
                        <tiles:put name="textAreaId" value="defaultExceptionMessage"/>
                        <tiles:put name="showFio" value="true"/>
                        <tiles:put name="showBold" value="true"/>
                        <tiles:put name="showItalics" value="true"/>
                        <tiles:put name="showUnderline" value="true"/>
                        <tiles:put name="showColor" value="true"/>
                        <tiles:put name="showHyperlink" value="true"/>
                        <tiles:put name="showTextAlign" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </fieldset>
    </div>
    <div id="hintMessage" style="display: none; position: absolute; border: 1px solid black">
        <bean:message key="hint.message.channel" bundle="exceptionEntryBundle"/>
    </div>
    <div id="hintMessageTb" style="display: none; position: absolute; border: 1px solid black">
        <bean:message key="hint.message.TB" bundle="exceptionEntryBundle"/>
    </div>
    <script type="text/javascript">
        var blocksCounter = $('.messageSettingsBlock:visible').size();
        var existBlocksNumbers = [];
        var deletedBlockNumbers = [];
        <c:forEach items="${form.groupIds}" var="i">
        existBlocksNumbers.push('${i}');
        </c:forEach>
        <c:forEach var="deletedGroup" items="${form.deletedGroupIds}">
        deletedBlockNumbers.push('${deletedGroup}');
        </c:forEach>

        function setExceptionEntryInfo(exceptionEntry)
        {
            <c:if test="${not form.newEntry}">
                setElement('id', exceptionEntry["id"]);
            </c:if>
            setElement('field(id)', exceptionEntry["id"]);
            setElement('field(operation)', exceptionEntry["operation"]);
            setElement('field(detail)', exceptionEntry["detail"]);
            <c:if test="${exceptionEntryType eq 'internal'}">
                setElement('field(application)', exceptionEntry["application"]);
            </c:if>
            <c:if test="${exceptionEntryType eq 'external'}">
                setElement('field(errorCode)', exceptionEntry["errorCode"]);
                setElement('field(system)', exceptionEntry["system"]);
            </c:if>
        }

        function openDictionary()
        {
            window.open(document.webRoot+'${listAction}.do?decoratedException=false&dictionary=true',
		           'Department', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth - 100) + "px,height=" + (getClientHeight - 100) + "px, left=50, top=50");
        }

        function checkExceptionEntrySelection()
        {
            if(!$("input[name=field(id)]").val())
            {
                alert('Укажите идентификатор ошибки, при возникновении которой в приложении будет выведено соответствующее ему сообщение.');
                return false;
            }
            $("input[name=id]").val($("input[name=field(id)]").val());
            return true;

        }

        function deleteDepartments(blockId)
        {
            checkIfOneItem("selectedPages");
            if (!checkSelection("selectedDepartments_"+blockId, "Выберите департамент!"))
                return;
            $('[name=selectedDepartments_'+blockId+']:checked').parents('[id^=dep]').remove();


            if ( $('[name=selectedDepartments_'+blockId+']').size() <= 0)
            {
                $("#pagesTable_"+blockId).css("display", "none");
            }
            $('[name=numOfDepartmentsInBlock(group_'+blockId+')]').val($('[name^=field(department_'+blockId+']').size());

            $('[name^=field(department_'+blockId+']').each(
                    function(index,e){
                        $(e).attr('name', 'field(department_' + blockId + '_'+ index+')');
                    }
            );
        }

        function openAllowedTBDictionary(blockId)
        {
            window.setDepartmentInfo = function(data){
                setDepartmentInformation(data, (blockId == null ? '' : blockId))
            };
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

        function setDepartmentInformation(resource, blockId)
        {
            var numInBlock = 0;
            var ids   = new Array();
            var names = new Array();
            ids = resource['region'].split(',');
            names = resource['name'].split(',');

            for(var i = 0; i < ids.length; i++)
            {
                var id = ids[i];
                var name = names[i];
                var blockNum = blockId;
                <c:if test="${exceptionEntryType eq 'internal'}">
                    blockNum = '';
                </c:if>
                if($("[id^="+id+"_id_name_"+blockNum+"]").size() > 0)
                {
                    win.alert('Для одного или нескольких тербанков сообщение уже задано. Проверьте настраиваемые значения тербанков.');
                    break;
                }

                $("#terbanksTable"+blockId).append(
                    '<tr id="dep_'+ id +'_' + blockId + '"><td><input type="checkbox" name="selectedDepartments_'+blockId+'" value="'+id+'" class="departmentCheckbox"/>'+
                            '<input type="hidden" name="field(department_' + blockId + '_)" value="'+id+'" style="border:none;"/></td>'+
                            '<td><span id="'+id+'_id_name_'+blockId+'"></span></td></tr>'
                );

                $("#"+id+"_id_name_"+blockId).text(name);
            }
            if ( $('[name=selectedDepartments_'+blockId+']').size() > 0)
            {
                $("#pagesTable_"+blockId).css("display", "block");
            }
            $('[name^=field(department_'+blockId+']').each(
                    function(index,e){
                        $(e).attr('name', 'field(department_' + blockId + '_'+ index+')');
                    }
            );
            $('[name=numOfDepartmentsInBlock(group_'+blockId+')]').val($('[name^=field(department_'+blockId+']').size());
        }

        function addMessageBlock(blockNumber)
        {
            $('#messageSettingsBlocks')
                    .append($('#defaultMessageBlock')
                            .html()
                            .replace(/defaultExceptionMessage/g, 'messageBlock' + blockNumber)
                            .replace(/defaultMessageDepartments/g, 'exceptionMessageDepartments' + blockNumber)
                            .replace(/emptyTerbanksTable/g, 'terbanksTable' + blockNumber)
                            .replace(/emptyCheckbox/g, 'checkbox_' + blockNumber)
                            .replace(/channelTable/g, 'channelTable_' + blockNumber)
                            .replace(/selectAllTb()/g, 'selectAllTb(' + blockNumber + ')')
                            .replace(/selectAllChannels()/g, 'selectAllChannels(' + blockNumber + ')')
                            .replace(/block_Id_Parameter/g, blockNumber)
                            .replace(/defaultPagesTable/g, 'pagesTable_'+blockNumber)
                            .replace(/defaultMessageField/g, 'message_'+blockNumber)
                    );
        }

        function removeBlock(elem)
        {
            var deleteBlockNumber = $(elem).parents('.messageSettingsBlock').find('[name=groupIds]').val();
            if( ($.inArray( deleteBlockNumber, existBlocksNumbers ) > -1) && ($.inArray( deleteBlockNumber, deletedBlockNumbers ) < 0))
            {
                $('#deletedGroups').append('<input type="hidden" name="deletedGroupIds" value="'+deleteBlockNumber+'">');
                deletedBlockNumbers.push(deleteBlockNumber);
            }
            $(elem).parents('.messageSettingsBlock').remove();
        }

        function getBlockNumber()
        {
            return blocksCounter++;
        }

        function selectAllTb(id)
        {
            var checked = $('#checkbox_'+id).attr('checked');
            $('[name^=selectedDepartments_'+id+']').each(
                function(index,e){
                    $(e).attr('checked', checked);
                }
            );
        }

        function selectAllChannels(id)
        {
            var checked = $('#channelTable_' + id + ' input').attr('checked');
            $('#channelTable_' + id + ' input').each(
                function(index,e){
                    $(e).attr('checked', checked);
                }
            );
        }

        function showHint(el, type)
        {
            el = el || window.event;
            if (el.pageX == null && el.clientX != null )
            {
                var html = document.documentElement;
                var body = document.body;

                el.pageX = e.clientX + (html && html.scrollLeft || body && body.scrollLeft || 0) - (html.clientLeft || 0);
                el.pageY = e.clientY + (html && html.scrollTop || body && body.scrollTop || 0) - (html.clientTop || 0);
            }
            var x = el.pageX + 10;
            var y = el.pageY + 10;
            var elem = document.getElementById('hintMessage');
            if(type == 'tb')
            {
                elem = document.getElementById('hintMessageTb');
            }
            elem.style.top = y + 'px';
            elem.style.left = x + 'px';
            elem.style.display = 'inline';
        }

        function hideHint()
        {
            var elem = document.getElementById('hintMessage');
            elem.style.display = 'none';
            elem = document.getElementById('hintMessageTb');
            elem.style.display = 'none';
        }
    </script>
</html:form>
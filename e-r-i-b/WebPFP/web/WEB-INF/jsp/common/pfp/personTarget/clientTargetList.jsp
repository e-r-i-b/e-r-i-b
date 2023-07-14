<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="profileId" value="${form.id}"/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<tiles:importAttribute/>
<fieldset>
    <legend><bean:message bundle="pfpBundle" key="myTargets"/></legend>
    <c:set var="displayNoneClass" value=""/>    
    <c:if test="${not empty personTargetList}">
        <c:set var="displayNoneClass" value="displayNone"/>
    </c:if>
    <div>
        <table id="pfpTableTargetsContainer" class="pfpTableContainer" cellpadding="0" cellspacing="0">
            <tbody>
                <tr class="tblInfHeader">
                    <th width="20px">&nbsp;</th>
                    <th>Цель</th>
                    <th>Планирую достичь к</th>
                    <th class="alignRight">Стоимость</th>
                    <c:if test="${showThermometer}">
                        <th>Реализация цели</th>
                    </c:if>
                    <th width="20px">&nbsp;</th>
                    <th width="20px">&nbsp;</th>
                </tr>
                <%-- пустая строка --%>
                <tr id="empty_target" class="${displayNoneClass}">
                    <td>
                        <img src="${globalImagePath}/pfp/pfp_target_empty.png" class="targetImg">
                    </td>
                    <td>
                        <div class="targetNameDiv">
                            <span class="word-wrap">
                                <span id="targetName">Моя цель</span>
                            </span>
                        </div>
                    </td>
                    <td class="alignCenter">&mdash;</td>
                    <td class="alignRight">&mdash;</td>
                    <c:if test="${showThermometer}">
                        <td>&mdash;</td>
                    </c:if>

                    <td class="icon">
                        <a class="editIconButton" title="Редактировать" onclick="pfpTarget.openTargetWindow();"></a>
                    </td>
                    <td class="icon">
                        &nbsp;
                    </td>
                </tr>
                <c:forEach items="${personTargetList}" var="target">
                    <%@ include file="/WEB-INF/jsp/common/pfp/personTarget/targetLine.jsp"%>
                </c:forEach>
            </tbody>
        </table>
        <div class="pfpTableButtonAdd">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.addTarget"/>
                <tiles:put name="commandHelpKey" value="button.addTarget.help"/>
                <tiles:put name="viewType" value="linkWithImg"/>
                <tiles:put name="imageUrl" value="${globalImagePath}/add.gif"/>
                <tiles:put name="bundle"  value="pfpBundle"/>
                <tiles:put name="onclick" value="pfpTarget.openTargetWindow();"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </div>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="addPFPTarget"/>
        <tiles:put name="data">
            <jsp:include page="/WEB-INF/jsp/common/pfp/windows/addPFPTarget.jsp"/>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="tooManyTargets"/>
        <tiles:put name="data">
            <jsp:include page="/WEB-INF/jsp/common/pfp/windows/tooManyTargets.jsp"/>
        </tiles:put>
    </tiles:insert>
    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="confirmRemoveTarget"/>
        <tiles:put name="data">
            <jsp:include page="/WEB-INF/jsp/common/pfp/windows/confirmRemoveTarget.jsp"/>
        </tiles:put>
    </tiles:insert>

</fieldset>

<c:set var="editUrl" value="${phiz:calculateActionURL(pageContext,'/async/editPfpTarget')}"/>

<script type="text/javascript">

    var pfpTarget =
    {
        maxTargetCount: ${maxTargetCount},
        currentRemovedTargetId:null,
        editCallbackFunctions : [],
        removeCallbackFunctions : [],
        /**
         * Добавить каллбек функцию в массив
         * @param func - функция
         */
        addEditCallbackFunction : function(func)
        {
            this.editCallbackFunctions.push(func);
        },
        /**
         * Колбек, выполняемый после редактирования цели.
         */
        editCallback: function(personTarget)
        {
            for (var i = 0; i < this.editCallbackFunctions.length; i++)
            {
                this.editCallbackFunctions[i](personTarget);
            }
        },
        /**
         * Добавить каллбек функцию при удалении цели
         * @param func - функция
         */
        addRemoveCallbackFunction : function(func)
        {
            this.removeCallbackFunctions.push(func);
        },
        /**
         * Колбек, выполняемый после редактирования цели.
         */
        removeCallback: function(personTargetId)
        {
            for (var i = 0; i < this.removeCallbackFunctions.length; i++)
            {
                this.removeCallbackFunctions[i](personTargetId);
            }
        },
        openTargetWindow:function()
        {
            clearOldTarget();
            var currentTargetCount = $('tr:[id^=target]').length;
            if(currentTargetCount >= this.maxTargetCount)
            {
                win.open('tooManyTargets');
                return false;
            }
            openTargetsWindow();
            return false;
        },
        removePersonTargetResult: function(data)
        {
            data = trim(data);
            if(data == '')
            {
                reload();
            }
            //если в дате не нашли ключа об удачном удалении, то в ней содержится ошибка
            else if(data.search("removSuccessful") == -1)
            {
                addError(data);
            }
            else
            {
                $('#target'+pfpTarget.currentRemovedTargetId).remove();
                if($('#pfpTableTargetsContainer tr').size() == 2) // только строка с шапкой и пустая строка осталась
                    $('#empty_target').removeClass("displayNone");
                pfpTarget.removeCallback(pfpTarget.currentRemovedTargetId);
                win.close('confirmRemoveTarget');
            }
        },
        doRemovePersonTarget: function()
        {
            var params = 'operation=remove';
            params += '&id='+this.currentRemovedTargetId;
            params += '&profileId=' + ${profileId};
            ajaxQuery(params,'${editUrl}',this.removePersonTargetResult);
        },
        removePersonTarget: function(targetId, removedTargetName)
        {
            this.currentRemovedTargetId = targetId;
            var targetNameSpan = $('#removedTargetName');
            targetNameSpan.html(removedTargetName);
            targetNameSpan.breakWords();
            win.open('confirmRemoveTarget');
        },
        editPersonTarget: function(targetId)
        {
            var target = new Object();
            target.id = targetId;
            target.dictionaryTarget = $('#dictionaryTarget'+targetId).text();
            target.name = trim($('#targetName'+targetId).text());
            target.nameComment = trim($('#targetNameComment'+targetId).text());
            var quarter = $('#targetPlanedQuarter'+targetId).text();
            var year = $('#targetPlanedYear'+targetId).text();
            target.planedDate = {quarter: quarter, year: year};
            target.amount = $('#targetAmount'+targetId).text();
            openTargetsWindow(target);
        },
        addPersonTarget: function(target)
        {
            var params = 'operation=edit';
            params += '&id='+target.id;
            params += '&showThermometer=' + ${showThermometer};
            params += '&profileId=' + ${profileId};
            params += '&field(dictionaryTarget)=' + target.dictionaryTarget;
            params += '&field(nameComment)=' + decodeURItoWin(target.nameComment);
            params += '&field(planedDate)=' + target.plannedDateStr;
            params += '&field(amount)=' + target.amount;
            ajaxQuery(params,'${editUrl}',function(data){pfpTarget.addPersonTargetResult(data,target);});
        },
        addPersonTargetResult: function(data, target)
        {
            data = trim(data);
            if (data == '')
            {
                reload();
                return;
            }
            if(data.search("target") == -1)
            {
                addErrorMessage(data);
                return;
            }
            $('#empty_target').addClass("displayNone");
            $('#empty_target').parent().find("tr:gt(1)").remove();
            $('#empty_target').after($(data).find('tr[id^=target]'));

            var targetList = {};
            $('#empty_target').parent().find("tr:gt(1)").each(function(index, element){
                var target = {
                    id: parseInt($(element).attr('id').substring('target'.length)),
                    name: trim($($(element).find('[id^=targetName]')[0]).text()),
                    nameComment: trim($(element).find('[id^=targetNameComment]').text()),
                    plannedDate: new Date(trim($(element).find('[id^=planedDateTarget]').text())),
                    amount: parseFloatVal(trim($(element).find('[id^=targetAmount]').text())),
                    veryLast: $(element).attr('isVeryLast') == 'true'
                };
                targetList[target.id] = target;

            });

            this.editCallback(targetList);
            win.close('addPFPTarget');
        },
        showThermometerHint: function(hintId, thermometerId, data)
        {
            hintUtils.createElementHint(hintId, '#thermometer' + thermometerId + ' .thermometerScale .thermometerLeft', data, false, 'targetThermometerHint');
        }
    };
</script>

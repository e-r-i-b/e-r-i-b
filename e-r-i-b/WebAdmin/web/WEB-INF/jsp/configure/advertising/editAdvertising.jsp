<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/advertising/block/edit" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="commonImgPath" value="${globalUrl}/commonSkin/images"/>
    <tiles:insert definition="advertisingBlockEdit">
        <tiles:put name="submenu" type="string" value="AdvertisingBlock"/>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                var buttonMoved = false;
                var addedDepartments = new Array();

                function openRequirementsDictionary(callback)
                {
                    window.setRequirementsInfo = callback;
                    win = window.open(document.webRoot+'/dictionaries/productRequirements/list.do',
                                   '', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth() - 100) + "px,height=" + (getClientHeight() - 100) + "px");
                    win.moveTo(screen.width / 2 - 350, screen.height / 2 - 400);
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
                            '<div id="dep'+ id +'">'+
                                    '<input type="checkbox" name="selectedDepartments" value="'+id+'" class="departmentCheckbox"/>'+
                                    '<input type="hidden" name="departments" value="'+id+'" style="border:none;"/>'+
                                    '<span id="'+id+'_id_name"></span>'+
                            '</div>'
                            );
                            $("#"+id+"_id_name").text(name);
                        }
                    }
                }

                function setRequirementsInfo(requirements, accTypes)
                {
                    for(var i = 0; i < requirements.length; i++)
                    {
                        var res = requirements[i];
                        var type = res['type'];
                        var typeName = res['typeName'];
                        var state = res['state'];
                        var stateName = res['stateName'];
                        if(document.getElementById('tr_'+type) == null)
                        {
                            $("#lastReqTableElement").before(
                            '<div id="tr_'+type+'">'+
                                    '<input id="reqInput_'+type+'" type="checkbox" name="selectedReqIds" value="'+type+'"/>'+
                                    '<input type="hidden" name="selectedRequirements" value="'+type+'" style="border:none;"/>'+
                                    '<span id="'+type+'_id_name">'+typeName+'</span>'+
                                    '<input type="hidden" id="requirementState_'+type+'" name="fields(requirementState'+type+')" value="'+state+'"/>'+
                                    '<span id="span_requirementState_'+type+'" class="requirementState"> '+stateName+'</span>'+
                            '</div>'
                            );
                        }
                        else
                        {
                            document.getElementById('requirementState_'+type).value = state;
                            document.getElementById('span_requirementState_'+type).innerHTML = stateName;
                        }
                    }

                    for(var i = 0; i < accTypes.length; i++)
                    {
                        var res = accTypes[i];
                        var type = res['type'];
                        var typeName = res['typeName'];
                        var state = res['state'];
                        var stateName = res['stateName'];
                        if(document.getElementById('tr_'+type) == null)
                        {
                            $("#tr_ACCOUNT").after(
                            '<div id="tr_'+type+'" name="accTypes">'+
                                    '<input type="checkbox" name="selectedAccTypesIds" value="'+type+'"/>'+
                                    '<input type="hidden" name="selectedAccountTypes" value="'+type+'" style="border:none;"/>'+
                                    '<span id="'+type+'_id_name">'+typeName+'</span>'+
                                    '<input type="hidden" id="accTypeState_'+type+'" name="fields(accTypeState'+type+')" value="'+state+'"/>'+
                                    '<span id="span_accTypeState_'+type+'">'+stateName+'</span>'+
                            '</div>'
                            );
                        }
                        else
                        {
                            document.getElementById('accTypeState_'+type).value = state;
                            document.getElementById('span_accTypeState_'+type).innerHTML = stateName;
                        }
                    }

                    if ($('#requirementState_ACCOUNT').val() != '')
                    {
                        var inputs = document.getElementsByName("selectedAccTypesIds");
                        for (var i = 0; i < inputs.length; i++)
                        {
                            var input = inputs.item(i);
                            input.disabled = 'disabled';
                        }
                        $('#reqInput_ACCOUNT').removeAttr('disabled');
                    }
                    else
                    {
                        var inputs = document.getElementsByName("selectedAccTypesIds");
                        var num = 0;
                        var len = inputs.length;
                        for (var i = 0; i < len; i++)
                        {
                            var input = inputs.item(num);
                            input.removeAttribute('disabled');
                            if ($('#accTypeState_'+input.value).val() == '')
                                $('#tr_'+input.value).remove();
                            else
                                num = num + 1;
                        }
                        $('#reqInput_ACCOUNT').attr('disabled', 'disabled');
                    }
                }

                function deleteDepartments()
                {
                    checkIfOneItem("selectedDepartments");
                    if (!checkSelection("selectedDepartments", '<bean:message bundle="advertisingBlockBundle" key="message.TB.select.nothing"/>'))
                        return;

                    $('[name=selectedDepartments]:checked').parent().remove();
                }

                function deleteRequirements()
                {
                    var accIds = document.getElementsByName("accTypes");

                    if (accIds.length == 0)
                        checkIfOneItem("selectedReqIds");

                    if (getSelectedQnt("selectedReqIds") == 0 && getSelectedQnt("selectedAccTypesIds") == 0)
                    {
                        clearLoadMessage();
		                alert('<bean:message bundle="advertisingBlockBundle" key="message.requirements.select.nothing"/>');
                        return;
                    }

                    $('[name=selectedReqIds]:checked').each(function(index, element){
                        var item = $(element);
                        if (item.val() == 'ACCOUNT')
                            $('[name=accTypes]').remove();
                        item.parent().remove();
                    });

                    $('[name=selectedAccTypesIds]:checked').parent().remove();

                    if ($('[name=selectedAccTypesIds]').length == 0)
                        $('#tr_ACCOUNT').remove();
                }

                <%-- мен€ет пор€док отображени€ кнопок --%>
                function moveButton(el, buttonId)
                {
                    buttonMoved = true;
                    var row = $(el).parents("tr:first");
                    var table = $(el).parents("#blockButtons");

                    if ($(el).is(".buttonUp"))
                    {
                        changeButtonOrder(buttonId, row.prev(), 'up');
                        row.insertBefore(row.prev());
                    }
                    else
                    {
                        changeButtonOrder(buttonId, row.next(), 'down');
                        row.insertAfter(row.next());
                    }
                    table.find('tr img').css("visibility","visible");
                    table.find('tr:first .buttonUp').css("visibility","hidden");
                    table.find('tr:last .buttonDown').css("visibility","hidden");
                }

                <%-- измен€ет у кнопок значени€ пор€дка отображени€ --%>
                function changeButtonOrder(buttonId, secondRow, side)
                {
                    var firstButton = $('#buttonOrder' + buttonId);
                    var secondButton = $('#buttonOrder' + secondRow.attr('id'));
                    var firstButtonOrder = parseInt(firstButton.val());
                    var secondButtonOrder = parseInt(secondButton.val());

                    firstButton.val(secondButtonOrder);
                    $('#buttonTitle' + buttonId).text('<bean:message bundle="advertisingBlockBundle" key="message.button.order.title"/> ' + (secondButtonOrder+1));
                    secondButton.val(firstButtonOrder);
                    $('#buttonTitle' + secondRow.attr('id')).text('<bean:message bundle="advertisingBlockBundle" key="message.button.order.title"/> ' + (firstButtonOrder+1));
                }

                function moveAreaBlock(el)
                {
                    var row = $(el).parents("tr:first");

                    if ($(el).is(".buttonUp"))
                    {
                        changeAreaOrder(row, row.prev());
                        row.insertBefore(row.prev());
                    }
                    else
                    {
                        changeAreaOrder(row, row.next());
                        row.insertAfter(row.next());
                    }
                    hideArrows();
                }

                <%-- измен€ет у кнопок значени€ пор€дка отображени€ --%>
                function changeAreaOrder(firstRow, secondRow)
                {
                    var input1 = firstRow.find('input');
                    var input2 = secondRow.find('input');
                    var order1 = parseInt(input1.val());
                    var order2 = parseInt(input2.val());
                    input1.val(order2);
                    input2.val(order1);
                }


                function hideArrows()
                {
                    var table = $("#movedAreaBlocks");
                    table.find('tr img').css("visibility","visible");
                    table.find('tr:first .buttonUp').css("visibility","hidden");
                    table.find('tr:last .buttonDown').css("visibility","hidden");
                }

                addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('fields(periodFrom)','__.__.____');
                        clearInputTemplate('fields(periodTo)','__.__.____');
                    });

                function init()
                {
                    hideArrows();
                    initAreaMaxLengthRestriction("text", 400, true);
                }

                doOnLoad(init);

            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="edit.title" bundle="advertisingBlockBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="label.description" bundle="advertisingBlockBundle"/>
                </tiles:put>
                <%-- ƒанные --%>
                <tiles:put name="data">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.status" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(state)" styleClass="select">
                                    <html:option value="ACTIVE"><bean:message key="label.active" bundle="advertisingBlockBundle"/></html:option>
                                    <html:option value="NOTACTIVE"><bean:message key="label.notactive" bundle="advertisingBlockBundle"/></html:option>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.name" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:text property="fields(name)" size="30"  maxlength="100" styleId="name"/>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.period" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <c:set var="fromDate"><bean:write name="form" property="fields.periodFrom" format="dd.MM.yyyy"/></c:set>
                                <bean:message key="label.from" bundle="advertisingBlockBundle"/>
                                <span class="asterisk">*</span>
                                <html:text property="fields(periodFrom)" value="${fromDate}" maxlength="10" styleClass="dot-date-pick"/>

                                <c:set var="toDate"><bean:write name="form" property="fields.periodTo" format="dd.MM.yyyy"/></c:set>
                                <bean:message key="label.to" bundle="advertisingBlockBundle"/>
                                <html:text property="fields(periodTo)" value="${toDate}" maxlength="10" styleClass="dot-date-pick"/>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.available" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(available)" styleClass="select">
                                    <html:option value="FULL"><bean:message key="label.available.FULL" bundle="advertisingBlockBundle"/></html:option>
                                    <html:option value="MAIN"><bean:message key="label.available.MAIN" bundle="advertisingBlockBundle"/></html:option>
                                    <html:option value="API"><bean:message key="label.available.API" bundle="advertisingBlockBundle"/></html:option>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.TB" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="description">
                                <bean:message key="help.TB" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add.tb.help"/>
                                    <tiles:put name="bundle" value="advertisingBlockBundle"/>
                                    <tiles:put name="onclick" value="openAllowedTBDictionary(setDepartmentInfo)"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.remove"/>
                                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                    <tiles:put name="bundle" value="advertisingBlockBundle"/>
                                    <tiles:put name="onclick" value="deleteDepartments()"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <div>
                                    <c:if test="${not empty form.departments && form.departments != null}">
                                        <c:forEach items="${form.departments}" var="department">
                                            <div id="dep${department}">
                                                <input type="checkbox" name="selectedDepartments" value="${department}" class="departmentCheckbox"/>
                                                <input type="hidden" name="departments" value="${department}" style="border:none;"/>
                                                <span id="${department}_id_name">
                                                    <c:out value="${phiz:getDepartmentName(department, null, null)}"/>
                                                </span>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <div id="lastTableElement" style="display:none;">
                                    </div>
                                </div>
                            </tiles:put>
                        </tiles:insert>

                        <fieldset>
                            <div width="100%">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.requirements" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="description">
                                    <bean:message key="help.requirements" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.add"/>
                                    <tiles:put name="commandHelpKey" value="button.add.req.help"/>
                                    <tiles:put name="bundle" value="advertisingBlockBundle"/>
                                    <tiles:put name="onclick" value="openRequirementsDictionary(setRequirementsInfo)"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.remove"/>
                                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                    <tiles:put name="bundle" value="advertisingBlockBundle"/>
                                    <tiles:put name="onclick" value="deleteRequirements()"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                </tiles:insert>
                                </tiles:put>
                                </tiles:insert>
                            <div>
                                <c:if test="${not empty form.requirements && form.requirements != null}">
                                    <c:forEach items="${form.requirements}" var="requirement">
                                        <c:set var="type" value="${requirement.requirementType}"/>
                                        <c:set var="state" value="${requirement.requirementState}"/>
                                        <div id="tr_${type}">
                                            <input id="reqInput_${type}" type="checkbox" name="selectedReqIds" value="${type}" <c:if test="${state == null}">disabled="disabled"</c:if> />
                                            <input type="hidden" name="selectedRequirements" value="${type}" style="border:none;"/>
                                            <span id="${type}_id_name"><bean:message key="type.${type}" bundle="productRequirementsBundle"/></span>
                                            <input type="hidden" id="requirementState_${type}" name="fields(requirementState${type})" value="${state}"/>
                                            <span id="span_requirementState_${type}" class="requirementState">
                                                 <c:if test="${state != null}">
                                                     <bean:message key="label.${state}" bundle="productRequirementsBundle"/>
                                                 </c:if>
                                            </span>
                                        </div>

                                        <c:if test="${type == 'ACCOUNT'}">
                                            <c:forEach items="${form.accountTypes}" var="accountType">
                                                <c:set var="product" value="${accountType.depositProduct}"/>
                                                <c:set var="prodState" value="${accountType.requirementState}"/>
                                                <div id="tr_${product.productId}" name="accTypes">
                                                    <input type="checkbox" name="selectedAccTypesIds" value="${product.productId}" <c:if test="${prodState == null}">disabled="disabled"</c:if> />
                                                    <input type="hidden" name="selectedAccountTypes" value="${product.productId}" style="border:none;"/>
                                                    <span id="${product.productId}_id_name">${product.name}</span>
                                                    <input type="hidden" id="accTypeState_${product.productId}" name="fields(accTypeState${product.productId})" value="${accountType.requirementState}"/>
                                                    <span id="span_accTypeState_${product.productId}">
                                                    <c:if test="${prodState != null}">
                                                         <bean:message key="label.${prodState}" bundle="productRequirementsBundle"/>
                                                    </c:if>
                                                    </span>
                                                </div>
                                            </c:forEach>
                                        </c:if>

                                    </c:forEach>
                                </c:if>
                                <div id="lastReqTableElement" style="display:none;">
                                </div>
                            </div>
                                </div>
                        </fieldset>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.banner.areaBlocks" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <table id="areaBlocks" cellspacing="6">
                                    <tr>
                                        <td>
                                            <table id="movedAreaBlocks" cellspacing="6">
                                                <c:forEach items="${form.areas}" var="area" varStatus="i">
                                                    <tr>
                                                        <td align="center" class="areaBlock">
                                                            <div class="areaTitle">
                                                                <span ><bean:message key="label.areaBlock.${area.areaName}" bundle="advertisingBlockBundle"/></span>
                                                                <input type="hidden" id="${area.areaName}Order" name="fields(${area.areaName}AreaOrder)" value="${i.index}"/>
                                                            </div>
                                                            <div class="areaButtons">
                                                                <img src="${skinUrl}/images/bth_up.gif" title="<bean:message key='message.order.up' bundle='advertisingBlockBundle'/>" class="buttonUp areaBlockMove" style="visibility: visible;" onmousedown="moveAreaBlock(this)"/>
                                                                <img src="${skinUrl}/images/bth_down.gif" title="<bean:message key='message.order.down' bundle='advertisingBlockBundle'/>" class="buttonDown areaBlockMove" style="visibility: visible;" onmousedown="moveAreaBlock(this)"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="areaBlock">
                                            <div class="paginationCircle">
                                                <img src="${commonImgPath}/paginationCircle.jpg" style="margin-left: -12px;"/>
                                            </div>
                                            <div class="paginationCircle">
                                                <img src="${commonImgPath}/paginationCircle.jpg"/>
                                            </div>
                                            <div class="paginationCircle">
                                                <img src="${commonImgPath}/paginationCircle.jpg" style="margin-left: -12px;"/>
                                            </div>
                                        </td>
                                    <tr>
                                </table>
                            </tiles:put>
                        </tiles:insert>

                        <fieldset>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.banner.title" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text styleId="title" property="fields(title)" size="60" maxlength="100"/>
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert definition="bbCodeButtons" flush="false">
                                        <tiles:put name="textAreaId" value="title"/>
                                        <tiles:put name="showBirthday" value="true"/>
                                        <tiles:put name="showPhone" value="true"/>
                                        <tiles:put name="showFio" value="true"/>
                                        <tiles:put name="showBold" value="true"/>
                                        <tiles:put name="showItalics" value="true"/>
                                        <tiles:put name="showUnderline" value="true"/>
                                        <tiles:put name="showColor" value="true"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>

                        <fieldset>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.text" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:textarea styleId="text" property="fields(text)" cols="55" rows="4"/>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <tiles:insert definition="bbCodeButtons" flush="false">
                                        <tiles:put name="textAreaId" value="text"/>
                                        <tiles:put name="showBirthday" value="true"/>
                                        <tiles:put name="showPhone" value="true"/>
                                        <tiles:put name="showFio" value="true"/>
                                        <tiles:put name="showBold" value="true"/>
                                        <tiles:put name="showItalics" value="true"/>
                                        <tiles:put name="showUnderline" value="true"/>
                                        <tiles:put name="showColor" value="true"/>
                                        <tiles:put name="showHyperlink" value="true"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>

                            <table id="blockButtons" width="100%">
                                <c:forEach var="i" begin="0" end="2" step="1">
                                    <tr id="${i}">
                                        <td style="padding:0;">
                                            <fieldset>
                                                    <tiles:insert definition="simpleFormRow" flush="false">
                                                    <tiles:put name="title">
                                                        <span id="buttonTitle${i}"><bean:message key="message.button.order.title" bundle="advertisingBlockBundle"/> ${i+1}</span>
                                                    </tiles:put>
                                                        <tiles:put name="data">
                                                        <div>
                                                            <div style="float:left">
                                                                <html:checkbox property="fields(buttonShow${i})"/><bean:message key="label.button.show" bundle="advertisingBlockBundle"/>
                                                                <c:set var="messageButtonTitle" value="<bean:message key='message.button.title' bundle='advertisingBlockBundle'/>"/>
                                                                <html:text property="fields(buttonTitle${i})"
                                                                           styleClass="customPlaceholder"
                                                                           size="20"
                                                                           maxlength="50"
                                                                           title="${messageButtonTitle}"
                                                                           styleId="buttonTitleName${i}"/>
                                                                <img src="${skinUrl}/images/bbCodeButtons/iconSm_Colored.gif" class="colorBtnImg" onmousedown="hideOrShow('colorButtonTitle${i}');"/>
                                                                <div id="colorButtonTitle${i}" style="display:none;">
                                                                    <div class="colorDiv" style="background-color: yellow;" onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'yellow');"></div>
                                                                    <div class="colorDiv" style="background-color: olive;"  onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'olive');"></div>
                                                                    <div class="colorDiv" style="background-color: fuchsia;" onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'fuchsia');"></div>
                                                                    <div class="colorDiv" style="background-color: red;"    onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'red');"></div>
                                                                    <div class="colorDiv" style="background-color: purple;" onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'purple');"></div>
                                                                    <div class="colorDiv" style="background-color: maroon;" onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'maroon');"></div>
                                                                    <div class="colorDiv" style="background-color: navy;"   onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'navy');"></div>
                                                                    <div class="colorDiv" style="background-color: blue;"   onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'blue');"></div>
                                                                    <div class="colorDiv" style="background-color: aqua;"   onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'aqua');"></div>
                                                                    <div class="colorDiv" style="background-color: lime;"   onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'lime');"></div>
                                                                    <div class="colorDiv" style="background-color: green;"  onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'green');"></div>
                                                                    <div class="colorDiv" style="background-color: teal;"   onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'teal');"></div>
                                                                    <div class="colorDiv" style="background-color: silver;" onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'silver');"></div>
                                                                    <div class="colorDiv" style="background-color: gray;"   onmousedown="changeSelText('c', ['buttonTitleName${i}'], 'gray');"></div>
                                                                    <div class="clear"></div>
                                                                </div>

                                                                <input type="hidden" value="${i}" id="buttonOrder${i}" name="fields(buttonOrder${i})"/>
                                                            </div>

                                                            <div class="clear"></div>
                                                            <div>
                                                                <div class="advUrlField valignTop">
                                                                    <html:text property="fields(buttonURL${i})" size="50"  maxlength="256" styleId="buttonURL${i}"/>
                                                                </div>
                                                                <div class="clear"/>
                                                                <div class="descriptionBlock">
                                                                    <bean:message key="label.button.url.help" bundle="advertisingBlockBundle"/>
                                                                </div>
                                                            </div>
                                                            <div>
                                                                <tiles:insert definition="imageInput" flush="false">
                                                                    <tiles:put name="id"                            value="Button${i}"/>
                                                                    <tiles:put name="externalSourceSelectorVisible" value="false"/>
                                                                    <tiles:put name="maxSize"                       value="57"/>
                                                                </tiles:insert>
                                                            </div>
                                                        </div>
                                                        </tiles:put>
                                                    </tiles:insert>
                                                    <div style="float:right; padding: 2px 0;">
                                                        <div style="width:23px; float:left; padding: 0 2px;">
                                                            <c:choose>
                                                                <c:when test="${i > 0}">
                                                                    <img src="${skinUrl}/images/bth_up.gif" title="<bean:message key='message.order.up' bundle='advertisingBlockBundle'/>" class="buttonUp" style="visibility: visible;" onmousedown="moveButton(this, ${i})"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="${skinUrl}/images/bth_up.gif" title="<bean:message key='message.order.up' bundle='advertisingBlockBundle'/>" class="buttonUp" style="visibility: hidden;" onmousedown="moveButton(this, ${i})"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div style="width:23px; float:left; padding: 0 2px;">
                                                            <c:choose>
                                                                <c:when test="${i < 2}">
                                                                    <img src="${skinUrl}/images/bth_down.gif" title="<bean:message key='message.order.down' bundle='advertisingBlockBundle'/>" class="buttonDown" style="visibility: visible;" onmousedown="moveButton(this, ${i})"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="${skinUrl}/images/bth_down.gif" title="<bean:message key='message.order.down' bundle='advertisingBlockBundle'/>" class="buttonDown" style="visibility: hidden;" onmousedown="moveButton(this, ${i})"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                            </fieldset>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>

                        <fieldset>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.image" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <tiles:insert definition="imageInput" flush="false">
                                        <tiles:put name="id" value="ImageArea"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.image.url" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="description">
                                    <bean:message key="help.banner.url" bundle="advertisingBlockBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text property="fields(imageLinkURL)" size="40"  maxlength="256" styleId="imageLinkURL"/>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.showTime" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <tiles:insert definition="scrollTemplate" flush="false">
                                    <tiles:put name="id" value="1"/>
                                    <tiles:put name="minValue" value="5"/>
                                    <tiles:put name="maxValue" value="120"/>
                                    <tiles:put name="unit" value="сек."/>
                                    <tiles:put name="currValue" value="${form.fields.showTime}"/>
                                    <tiles:put name="fieldName" value="fields(showTime)"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message key="label.orderIndex" bundle="advertisingBlockBundle"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <tiles:insert definition="scrollTemplate" flush="false">
                                    <tiles:put name="id" value="2"/>
                                    <tiles:put name="minValue" value="1"/>
                                    <tiles:put name="maxValue" value="100"/>
                                    <tiles:put name="currValue" value="${form.fields.orderIndex}"/>
                                    <tiles:put name="fieldName" value="fields(orderIndex)"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>
                </tiles:put>
                <c:set var="viewURL" value="${phiz:calculateActionURL(pageContext, '/advertising/block/view')}"/>
                <script type="text/javascript">

                    function  viewBunner()
                    {
                        var id = '${form.id}';
                        if (id == '' || id == 0)
                        {
                            alert('<bean:message bundle="advertisingBlockBundle" key="message.saveBeforeView"/>');
                            return;
                        }
                        window.open("${viewURL}?skinUrl=/PhizIC-res/skins/sbrf&id=${form.id}", "", "menubar=1,resizable=1,location=1,status=1,scrollbars=1,width=800,height=400");
                    }

                    function saveValidate(){
                        if (!matchRegexp($("input[name=fields(showTime)]").val(), /^([5-9]|[1-9][0-9]|1[0-1][0-9]|120)$/))
                        {
                            alert('<bean:message bundle="advertisingBlockBundle" key="message.validator.time"/>');
                            return false;
                        }
                        if (!matchRegexp($("input[name=fields(orderIndex)]").val(), /^([1-9]|[1-9][0-9]|100)$/))
                        {
                            alert('<bean:message bundle="advertisingBlockBundle" key="message.validator.priority"/>');
                            return false;
                        }
                        return true;
                    }

                    function changeButtonData()
                    {
                        return buttonMoved;
                    }

                </script>

                <%--  нопки --%>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
                        <tiles:put name="commandTextKey" value="button.preview"/>
                        <tiles:put name="commandHelpKey" value="button.preview.help"/>
                        <tiles:put name="bundle" value="advertisingBlockBundle"/>
                        <tiles:put name="viewType" value="blueGrayLink"/>
                        <tiles:put name="onclick"  value="viewBunner()"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="advertisingBlockBundle"/>
                        <tiles:put name="action"  value="/advertising/block/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditAdvertisingBlockOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="validationFunction" value="saveValidate();"/>
                        <tiles:put name="bundle"  value="advertisingBlockBundle"/>
                    </tiles:insert>

                    <c:if test="${not empty form.id}">
                         <tiles:insert definition="languageSelectForEdit" flush="false">
                             <tiles:put name="selectId" value="chooseLocale"/>
                             <tiles:put name="entityId" value="${form.id}"/>
                             <tiles:put name="styleClass" value="float"/>
                             <tiles:put name="needSaveChangedData" value="changeButtonData();"/>
                             <tiles:put name="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/advertising/block/language/save')}"/>
                         </tiles:insert>
                    </c:if>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

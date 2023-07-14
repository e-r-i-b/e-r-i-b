<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/offers/notification/edit" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="commonImgPath" value="${globalUrl}/commonSkin/images"/>
<tiles:insert definition="OfferNotificationEdit">

<tiles:put name="data" type="string">
<script type="text/javascript">
    var addedDepartments = new Array();

    function setDepartmentInfo(resource)
    {
        var ids   = resource['region'].split(',');
        var names = resource['name'].split(',');
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

    function deleteDepartments()
    {
        checkIfOneItem("selectedPages");
        if (!checkSelection("selectedDepartments", "Выберите департамент!"))
            return;
        var ids = document.getElementsByName("selectedDepartments");
        var size = ids.length;
        var c = 0;
        for (var i = 0; i < size; i++)
        {
            if (ids.item(c).checked)
            {
                var id_id = ids.item(c).value;
                $('#dep'+id_id).remove();
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


    <%-- меняет порядок отображения кнопок --%>
    function moveButton(el, buttonId)
    {
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

    <%-- изменяет у кнопок значения порядка отображения --%>
    function changeButtonOrder(buttonId, seconrRow, side)
    {
        var order1 = parseInt($('#buttonOrder' + buttonId).val());
        var order2 = parseInt($('#buttonOrder' + seconrRow.attr('id')).val());

        $('#buttonOrder' + buttonId).val(order2);
        $('#buttonTitle' + buttonId).text("Кнопка " + (order2+1));
        $('#buttonOrder' + seconrRow.attr('id')).val(order1);
        $('#buttonTitle' + seconrRow.attr('id')).text("Кнопка " + (order1+1));
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

    <%-- изменяет у кнопок значения порядка отображения --%>
    function changeAreaOrder(firstRow, secondRow, side)
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
    function hideOrShowDay()
    {
        var select =  $("#frequencyDisplay").val();
        if (select == 'PERIOD')
        {
            $("#frequencyDisplayDay").show();
        }
        else
        {
            $("#frequencyDisplayDay").hide();
        }
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
    function  viewOffer()
    {
        var id = '${form.id}';
        if (id == '' || id == 0)
        {
            alert("<bean:message key="message.preview.warning" bundle="offerNotificationBundle"/>");
            return;
        }
        window.open("${phiz:calculateActionURL(pageContext, '/offers/notification/view.do')}?skinUrl=/PhizIC-res/skins/sbrf&id=${form.id}", "", "menubar=1,resizable=1,location=1,status=1,scrollbars=1,width=800,height=400");
    }
</script>
<tiles:insert definition="paymentForm" flush="false">
<tiles:put name="name">
    <bean:message key="edit.title" bundle="offerNotificationBundle"/>
</tiles:put>
<tiles:put name="description">
    <bean:message key="label.description" bundle="offerNotificationBundle"/>
</tiles:put>
<%-- Данные --%>
<tiles:put name="data">

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.status" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <html:select property="fields(state)" styleClass="select">
                <html:option value="ACTIVE"><bean:message key="label.active" bundle="offerNotificationBundle"/></html:option>
                <html:option value="NOTACTIVE"><bean:message key="label.notactive" bundle="offerNotificationBundle"/></html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.productType" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <html:select property="fields(productType)" styleClass="select">
                <html:option value="LOAN"><bean:message key="label.loan" bundle="offerNotificationBundle"/></html:option>
                <html:option value="LOAN_CARD"><bean:message key="label.loanCard" bundle="offerNotificationBundle"/></html:option>
            </html:select>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.name" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
                <html:text property="fields(name)" size="30"  maxlength="40" styleId="name"/>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.period" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <c:set var="fromDate"><bean:write name="form" property="fields.periodFrom" format="dd.MM.yyyy"/></c:set>
            <bean:message key="label.from" bundle="offerNotificationBundle"/>
            <span class="asterisk">*</span>
            <html:text property="fields(periodFrom)" value="${fromDate}" maxlength="10" styleClass="dot-date-pick"/>

            <c:set var="toDate"><bean:write name="form" property="fields.periodTo" format="dd.MM.yyyy"/></c:set>
            <bean:message key="label.to" bundle="offerNotificationBundle"/>
            <html:text property="fields(periodTo)" value="${toDate}" maxlength="10" styleClass="dot-date-pick"/>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.frequencyDisplay" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="data">
            <html:select property="fields(frequencyDisplay)" styleClass="select" onchange="hideOrShowDay();" styleId="frequencyDisplay">
                <html:option value="ONE"><bean:message key="label.ONE" bundle="offerNotificationBundle"/></html:option>
                <html:option value="EVERY_TIME"><bean:message key="label.EVERY_TIME" bundle="offerNotificationBundle"/></html:option>
                <html:option value="PERIOD"><bean:message key="label.PERIOD" bundle="offerNotificationBundle"/></html:option>
            </html:select>
            <c:if test="${form.fields.frequencyDisplay !='PERIOD'}"><c:set var="displayDay" value="display:none"/></c:if>
            <span id="frequencyDisplayDay" style="${displayDay}">
            <html:text property="fields(frequencyDisplayDay)" size="30"  maxlength="100" styleId="frequencyDisplayDay" />
            <bean:message key="label.PERIOD.day" bundle="offerNotificationBundle"/>
            </span>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.TB" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="isNecessary" value="true"/>
        <tiles:put name="description">
            <bean:message key="help.TB" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.add.tb"/>
                <tiles:put name="commandHelpKey" value="button.add.tb.help"/>
                <tiles:put name="bundle" value="offerNotificationBundle"/>
                <tiles:put name="onclick" value="openAllowedTBDictionary(setDepartmentInfo)"/>
                <tiles:put name="viewType" value="buttonGrayNew"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                <tiles:put name="bundle" value="offerNotificationBundle"/>
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

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.areaBlocks" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <table id="areaBlocks" cellspacing="6">
                <tr>
                    <td align="center" class="areaBlock">
                        <div class="areaTitle">
                            <bean:message key="label.areaBlock.title" bundle="offerNotificationBundle"/>
                            <input type="hidden" id="titleOrder" name="fields(titleAreaOrder)" value="0"/>
                        </div>
                    </td>
                <tr>
                <tr>
                    <td>
                        <table id="movedAreaBlocks" cellspacing="6">
                            <c:forEach items="${form.areas}" var="area" varStatus="i">
                                <c:if test="${i.index>0}">
                                    <tr>
                                        <td align="center" class="areaBlock">
                                            <div class="areaTitle">
                                                <span><bean:message key="label.areaBlock.${area.areaName}" bundle="offerNotificationBundle"/></span>
                                                <input type="hidden" id="${area.areaName}Order" name="fields(${area.areaName}AreaOrder)" value="${i.index}"/>
                                            </div>
                                            <div class="areaButtons">
                                                <img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp areaBlockMove" style="visibility: visible;" onmousedown="moveAreaBlock(this)"/>
                                                <img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown areaBlockMove" style="visibility: visible;" onmousedown="moveAreaBlock(this)"/>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
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
                <bean:message key="label.notification.title" bundle="offerNotificationBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <html:text styleId="title" property="fields(title)" style="text-align:justify;" size="60" maxlength="100"/>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="data">
                <tiles:insert definition="bbCodeButtons" flush="false">
                    <tiles:put name="textAreaId" value="title"/>
                    <tiles:put name="showFio" value="true"/>
                    <tiles:put name="showCreditLimit" value="true"/>
                    <tiles:put name="showCreditDuration" value="true"/>
                    <tiles:put name="showPercentRate" value="true"/>
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
                <bean:message key="label.text" bundle="offerNotificationBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <html:textarea styleId="text" property="fields(text)" cols="55" rows="4"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="data">
                <tiles:insert definition="bbCodeButtons" flush="false">
                    <tiles:put name="textAreaId" value="text"/>
                    <tiles:put name="showFio" value="true"/>
                    <tiles:put name="showCreditLimit" value="true"/>
                    <tiles:put name="showCreditDuration" value="true"/>
                    <tiles:put name="showPercentRate" value="true"/>
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
                                <span id="buttonTitle${i}">Кнопка ${i+1}</span>
                            </tiles:put>
                            <tiles:put name="data">
                                <div>
                                    <div style="float:left">
                                        <html:checkbox property="fields(buttonShow${i})"/><bean:message key="label.button.show" bundle="offerNotificationBundle"/>
                                        <html:text property="fields(buttonTitle${i})" styleClass="customPlaceholder" size="20" maxlength="20" title="Название" styleId="buttonTitleName${i}"/>
                                        <img src="${skinUrl}/images/bbCodeButtons/iconSm_Colored.gif" onmousedown="hideOrShow('colorButtonTitle${i}');"/>
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
                                            <html:text property="fields(buttonURL${i})" size="50"  maxlength="256" styleId="buttonURL"/>
                                        </div>
                                        <div class="clear"/>
                                        <div class="descriptionBlock">
                                            <bean:message key="label.button.url.help" bundle="offerNotificationBundle"/>
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
                            <div style="width:19px; float:left; padding: 0 2px;">
                                <c:choose>
                                    <c:when test="${i > 0}">
                                        <img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp" style="visibility: visible;" onmousedown="moveButton(this, ${i})"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp" style="visibility: hidden;" onmousedown="moveButton(this, ${i})"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div style="width:19px; float:left; padding: 0 2px;">
                                <c:choose>
                                    <c:when test="${i < 2}">
                                        <img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown" style="visibility: visible;" onmousedown="moveButton(this, ${i})"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown" style="visibility: hidden;" onmousedown="moveButton(this, ${i})"/>
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
                <bean:message key="label.image" bundle="offerNotificationBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <tiles:insert definition="imageInput" flush="false">
                    <tiles:put name="id" value="ImageArea"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.image.url" bundle="offerNotificationBundle"/>
            </tiles:put>
            <tiles:put name="description">
                <bean:message key="help.url" bundle="offerNotificationBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <html:text property="fields(imageLinkURL)" size="40"  maxlength="256" styleId="imageLinkURL"/>
            </tiles:put>
        </tiles:insert>
    </fieldset>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.showTime" bundle="offerNotificationBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="scrollTemplate" flush="false">
                <tiles:put name="id" value="1"/>
                <tiles:put name="minValue" value="5"/>
                <tiles:put name="maxValue" value="120"/>
                <tiles:put name="currValue" value="${form.fields.showTime}"/>
                <tiles:put name="fieldName" value="fields(showTime)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message key="label.orderIndex" bundle="offerNotificationBundle"/>
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
<script type="text/javascript">
    function saveValidate(){
        if (!matchRegexp($("input[name=fields(showTime)]").val(), /^([5-9]|[1-9][0-9]|1[0-1][0-9]|120)$/))
        {
            alert("<bean:message key="message.time.error" bundle="offerNotificationBundle"/>");
            return false;
        }
        if (!matchRegexp($("input[name=fields(orderIndex)]").val(), /^([1-9]|[1-9][0-9]|100)$/))
        {
            alert("<bean:message key="message.priority.error" bundle="offerNotificationBundle"/>");
            return false;
        }
        return true;
    }
</script>

<%-- Кнопки --%>
<tiles:put name="buttons">
    <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
        <tiles:put name="commandTextKey" value="button.preview"/>
        <tiles:put name="commandHelpKey" value="button.preview.help"/>
        <tiles:put name="bundle" value="advertisingBlockBundle"/>
        <tiles:put name="viewType" value="blueGrayLink"/>
        <tiles:put name="onclick"  value="viewOffer()"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false" operation="EditOfferNotificationOperation">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="offerNotificationBundle"/>
        <tiles:put name="action"  value="/offers/notification/list"/>
    </tiles:insert>
    <tiles:insert definition="commandButton" flush="false" operation="EditOfferNotificationOperation">
        <tiles:put name="commandKey"     value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.help"/>
        <tiles:put name="validationFunction" value="saveValidate();"/>
        <tiles:put name="bundle"  value="offerNotificationBundle"/>
    </tiles:insert>
</tiles:put>
<tiles:put name="alignTable" value="center"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>

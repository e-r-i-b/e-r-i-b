<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/quick/pay/edit" enctype="multipart/form-data" onsubmit="clearImageInputs(); return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="commonImgPath" value="${globalUrl}/commonSkin/images"/>
    <tiles:insert definition="quickPaymentPanelEdit">

        <tiles:put name="submenu" type="string" value="QuickPaymentPanel"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="edit.title" bundle="quickPaymentPanelBundle"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="description">
                    <bean:message key="label.description" bundle="quickPaymentPanelBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.status" bundle="quickPaymentPanelBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(state)" styleClass="select">
                                <html:option value="ACTIVE"><bean:message key="label.active" bundle="quickPaymentPanelBundle"/></html:option>
                                <html:option value="NOTACTIVE"><bean:message key="label.notactive" bundle="quickPaymentPanelBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.name" bundle="quickPaymentPanelBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="30"  maxlength="100" styleId="name"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.period" bundle="quickPaymentPanelBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="fromDate"><bean:write name="form" property="fields.periodFrom" format="dd.MM.yyyy"/></c:set>
                            <bean:message key="label.from" bundle="quickPaymentPanelBundle"/>
                            <span class="asterisk">*</span>
                            <html:text property="fields(periodFrom)" value="${fromDate}" maxlength="10" styleClass="dot-date-pick"/>

                            <c:set var="toDate"><bean:write name="form" property="fields.periodTo" format="dd.MM.yyyy"/></c:set>
                            <bean:message key="label.to" bundle="quickPaymentPanelBundle"/>
                            <html:text property="fields(periodTo)" value="${toDate}" maxlength="10" styleClass="dot-date-pick"/>
                        </tiles:put>
                    </tiles:insert>
                   <%-- Тербанк для которого отображаем ПБО--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.TB" bundle="quickPaymentPanelBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">
                            <bean:message key="label.TB.hint" bundle="quickPaymentPanelBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add"/>
                                <tiles:put name="commandHelpKey" value="button.add.help"/>
                                <tiles:put name="bundle" value="quickPaymentPanelBundle"/>
                                <tiles:put name="onclick" value="openAllowedTBDictionary(setDepartmentInfo)"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                <tiles:put name="bundle" value="quickPaymentPanelBundle"/>
                                <tiles:put name="onclick" value="deleteDepartments()"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <c:if test="${not empty form.departments && form.departments != null}">
                                <c:forEach items="${form.departments}" var="department">
                                    <div id="dep${department}">
                                        <input type="checkbox" name="selectedDepartments" value="${department}" class="departmentCheckbox"/>
                                        <input type="hidden" name="selectedIds" value="${department}" style="border:none;"/>
                                        <span id="${department}_id_name">
                                            <c:out value="${phiz:getDepartmentName(department, null, null)}"/>
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <div id="lastTableElement" style="display:none;"></div>
                        </tiles:put>
                    </tiles:insert>
                    <%-- Блок оплаты своего мобильного --%>
                    <c:set var="providersCount" value="${form.fields.blocksCount}"/>
                    <div id="providerMobileBlock">
                        <fieldset class="providerBlock">
                            <html:hidden property="fields(orderIndex0)" styleId="providerBlockOrder0" value="0"/>
                            <html:hidden property="fields(providerId0)" styleId="providerId0"/>
                            <input type="hidden" name="orderIds" value="0" style="border:none;"/>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.provider" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="description">
                                    <bean:message key="label.provider.description" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text property="fields(providerName0)" size="40"  maxlength="25" styleId="providerName0" readonly="true"/>
                                    <input id="openProviderDictionary" type="button" class="buttWhite smButt" onclick="openServiceProvidersList(function(d){return setProviderInfo1(d, 0)});" value="..."/>
                                </tiles:put>
                            </tiles:insert>
                            <div style="display:none;">
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <bean:message key="label.provider.name" bundle="quickPaymentPanelBundle"/>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <input type="checkbox" checked="checked" name="fields(providerNameShow0)" class="icon0" value="true"/><bean:message key="label.show.name" bundle="quickPaymentPanelBundle"/><br/>
                                        <html:text property="fields(providerAlias0)" size="40"  maxlength="14" styleId="providerAlias0"/>
                                    </tiles:put>
                                </tiles:insert>
                            </div>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.icon" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:checkbox property="fields(providerShow0)" /><bean:message key="label.show.provider.icon" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="description">
                                    <bean:message key="label.icon.description" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:radio property="fields(isDefaultImage0)" value="true" styleClass="icon0" onclick="hideOrShowImageInput(this);"/>
                                    <bean:message key="label.use.default.image" bundle="quickPaymentPanelBundle"/>
                                    <div id="providerImage0">
                                        <c:set var="providerImage" value="providerImage0"/>
                                        <c:set var="imageData" value="${form.fields[providerImage]}"/>
                                        <html:hidden property="fields(providerImageId0)"/>
                                        <c:choose>
                                            <c:when test="${not empty imageData}">
                                                <c:set var="image" value="${phiz:getAddressImageConsiderMultiBlock(imageData, pageContext)}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="image" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <img src="${image}" alt="Поставщик"/>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <html:radio property="fields(isDefaultImage0)" value="false" styleClass="icon0" onclick="hideOrShowImageInput(this);"/>
                                    <bean:message key="label.use.other.image" bundle="quickPaymentPanelBundle"/>&nbsp;
                                    <span id="imageSize0"></span>
                                    <div id="imageInputCell0" style="display:none">
                                        <tiles:insert definition="imageInput" flush="false">
                                            <tiles:put name="id" value="icon0"/>
                                        </tiles:insert>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.provider.field.name" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="description">
                                    <bean:message key="label.field.description" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text property="fields(providerField0)" size="40"  maxlength="25" styleId="providerField0" readonly="true"/>
                                    <input id="openProviderFieldDictionary" type="button" class="buttWhite smButt" onclick="openServiceProviderFieldsList(function(d){return setProviderFieldData1(d)})" value="..."/>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.provider.field.amount" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="description">
                                    <bean:message key="label.field.amount.description" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="data">
                                    <html:text property="fields(providerFieldSumm0)" size="40"  maxlength="25" styleId="providerFieldSumm0" readonly="true"/>
                                    <input id="openProviderFieldDictionary" type="button" class="buttWhite smButt" onclick="openServiceProviderFieldsList(function(d){return setProviderFieldData2(d)})" value="..."/>
                                </tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message key="label.provider.field.summ" bundle="quickPaymentPanelBundle"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <html:text property="fields(summ0)" size="40"  maxlength="10" styleId="summ0" styleClass="moneyField"/>
                                </tiles:put>
                            </tiles:insert>
                        </fieldset>
                    </div>
                    <%-- Блок поставщиков услуг--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="description">
                            <bean:message key="button.add.block.description" bundle="quickPaymentPanelBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add.block"/>
                                <tiles:put name="commandHelpKey" value="button.add.block"/>
                                <tiles:put name="bundle" value="quickPaymentPanelBundle"/>
                                <tiles:put name="onclick" value="addProviderBlock()"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                    <div id="providerBlock">
                        <html:hidden property="fields(blocksCount)" styleId="providersCount"/>
                        <c:set var="numOfStep" value="1"/>
                        <div id="forAddBlockDiv" style="display:none"></div>
                        <c:forEach items="${form.orderIds}" var="i" begin="1">
                            <c:set var="providerField" value="providerField${i}"/>
                            <c:if test="${empty form.fields[providerField]}">
                                <fieldset class="providerBlock">
                                    <html:hidden property="fields(orderIndex${i})" styleId="providerBlockOrder${i}" value="${i}"/>
                                    <html:hidden property="fields(providerId${i})" styleId="providerId${i}"/>
                                    <input type="hidden" name="orderIds" value="${i}" style="border:none;"/>
                                    <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
                                        <tiles:put name="commandTextKey" value="button.delete.block"/>
                                        <tiles:put name="commandHelpKey" value="button.delete.block"/>
                                        <tiles:put name="bundle" value="quickPaymentPanelBundle"/>
                                        <tiles:put name="viewType" value="blueGrayLink"/>
                                        <tiles:put name="onclick"  value="removeProviderBlock(this)"/>
                                    </tiles:insert>
                                    <div style="float:right; padding: 2px 0;">
                                        <div style="width:19px; float:left; padding: 0 2px;">
                                            <c:choose>
                                                <c:when test="${numOfStep == providersCount-1}">
                                                    <img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown" style="visibility: hidden;" onmousedown="moveButton(this)"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown" style="visibility: visible;" onmousedown="moveButton(this)"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div style="float:right; padding: 2px 0;">
                                        <div style="width:19px; float:left; padding: 0 2px;">
                                            <c:choose>
                                                <c:when test="${numOfStep == 1}">
                                                    <img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp" style="visibility: hidden;" onmousedown="moveButton(this)"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp" style="visibility: visible;" onmousedown="moveButton(this)"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message key="label.provider" bundle="quickPaymentPanelBundle"/>
                                        </tiles:put>
                                        <tiles:put name="isNecessary" value="true"/>
                                        <tiles:put name="description">
                                            <bean:message key="label.provider.description" bundle="quickPaymentPanelBundle"/>
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:text property="fields(providerName${i})" size="40"  maxlength="25" styleId="providerName${i}" readonly="true"/>
                                            <input id="openProviderDictionary" type="button" class="buttWhite" onclick="openServiceProvidersList(function(d){return setProviderInfo1(d, ${i})});" value="..."/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message key="label.provider.name" bundle="quickPaymentPanelBundle"/>
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:checkbox property="fields(providerNameShow${i})" styleClass="icon${i}" onchange="changeImageSize(this);"/><bean:message key="label.show.name" bundle="quickPaymentPanelBundle"/><br/>
                                            <html:text property="fields(providerAlias${i})" size="40"  maxlength="14" styleId="providerAlias${i}"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message key="label.icon" bundle="quickPaymentPanelBundle"/>
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:checkbox property="fields(providerShow${i})" /><bean:message key="label.show.block" bundle="quickPaymentPanelBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <html:radio property="fields(isDefaultImage${i})" styleClass="icon${i}" value="true" onclick="hideOrShowImageInput(this);"/>
                                            <bean:message key="label.use.default.image" bundle="quickPaymentPanelBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <div id="providerImage${i}">
                                                <c:set var="providerImage" value="providerImage${i}"/>
                                                <c:set var="imageData" value="${form.fields[providerImage]}"/>
                                                <html:hidden property="fields(providerImageId${i})"/>
                                                <c:choose>
                                                    <c:when test="${not empty imageData}">
                                                        <c:set var="image" value="${phiz:getAddressImageConsiderMultiBlock(imageData, pageContext)}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="image" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <img src="${image}" alt="Поставщик"/>
                                            </div>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <html:radio property="fields(isDefaultImage${i})" value="false" styleClass="icon${i}" onclick="hideOrShowImageInput(this);"/>
                                            <bean:message key="label.use.other.image" bundle="quickPaymentPanelBundle"/>&nbsp;
                                            <span id="imageSize${i}"></span>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <div id="imageInputCell${i}">
                                                <tiles:insert definition="imageInput" flush="false">
                                                    <tiles:put name="id" value="icon${i}"/>
                                                </tiles:insert>
                                            </div>
                                        </tiles:put>
                                    </tiles:insert>
                                </fieldset>
                                <c:set var="numOfStep" value="${numOfStep+1}"/>
                            </c:if>
                        </c:forEach>
                    </div>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditAdvertisingBlockOperation">
                        <tiles:put name="commandTextKey" value="button.preview"/>
                        <tiles:put name="commandHelpKey" value="button.preview.help"/>
                        <tiles:put name="bundle" value="advertisingBlockBundle"/>
                        <tiles:put name="viewType" value="blueGrayLink"/>
                        <tiles:put name="onclick"  value="viewPanel()"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="EditQuickPaymentPanelOperation">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="quickPaymentPanelBundle"/>
                        <tiles:put name="action"  value="/quick/pay/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditQuickPaymentPanelOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="quickPaymentPanelBundle"/>
                        <tiles:put name="validationFunction" value="validateImageSize();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <c:set var="imageInput">
                &nbsp;
                 <tiles:insert definition="imageInput" flush="false">
                     <tiles:put name="id" value="defaultID"/>
                     <tiles:put name="forJS" value="true"/>
                 </tiles:insert>
            </c:set>
            <script type="text/javascript">
                var providersCount = parseInt($("#providersCount").val());
                var currentNumOfProvider = providersCount;
                var addedDepartments = new Array();

                function  viewPanel()
                {
                    var id = '${form.id}';
                    if (id == '' || id == 0)
                    {
                        alert("Для предварительного просмотра панели, пожалуйста, сохраните её.");
                        return;
                    }
                    window.open("${phiz:calculateActionURL(pageContext, '/quick/pay/view.do')}?skinUrl=/PhizIC-res/skins/sbrf&id=${form.id}", "", "menubar=1,resizable=1,location=1,status=1,scrollbars=1,width=800,height=400");
                }

                function setOrder()
                {
                    for(var i = 0; i < providersCount; i++)
                        $($("[id^=providerBlockOrder]")[i]).val(i);
                    var firstBlock = $('.providerBlock img');
                    $('.providerBlock img').css("visibility","visible");
                    $('.providerBlock img.buttonUp:first').css("visibility","hidden");
                    $('.providerBlock img.buttonDown:last').css("visibility","hidden");
                }

                function openServiceProvidersList(callback)
                {
                    window.setProviderInfo = callback;
                    var h = getClientHeight - 100;
                    var w = getClientWidth - 100;

                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1,scrollbars=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=" + (getClientWidth() - w) / 2 +
                                 ", top=" + (getClientHeight() - h) / 2;
                    win = openWindow(null, document.webRoot+'/private/dictionaries/provider/list.do?action=getProvider&filter(kind)=B',
                                   'Providers', winpar);
                    win.moveTo(50, 50);
                    win.focus();
                }

                function setProviderInfo1(resource, num)
                {
                    var id       = resource['id'];
                    for(var i = 0; i < providersCount; i++)
                        if($($("[id^=providerId]")[i]).val() == id)
                        {
                            return '<bean:message key="duplicate.provider" bundle="quickPaymentPanelBundle"/>';
                        }
                    var name     = resource['name'];
                    var name2    = resource['alias'];
                    var imageUrl = resource['imageUrl'];
                    var imageId  = resource['imageId'];
                    $('#providerId'+num).val(id);
                    $('#providerId'+num).val(id);
                    $('#providerName'+num).val(name);
                    $('#providerAlias'+num).val((name2 == undefined || name2 == null)? null : name2.substr(0,14));
                    $('#providerImage'+num).html('<input type="hidden" name="fields(providerImageId'+num+')" value="'+imageId+'"/><img src="'+imageUrl+'" alt="Поставщик"/>');
                    $('#providerImage'+num).parents('tr:first').prev().html(
                        '<td></td>'+
                        '<td colspan="2">'+
                            '<input type="radio" name="fields(isDefaultImage'+num+')" value="true" checked="checked" class="icon'+num+'" onclick="hideOrShowImageInput(this);"><bean:message key="label.use.default.image" bundle="quickPaymentPanelBundle"/>'+
                        '</td>'
                    );
                    $('#providerImage'+num).parents('tr:first').next().html(
                        '<td></td>'+
                        '<td colspan="2">'+
                            '<html:radio property="fields(isDefaultImage'+num+')" value="false" styleClass="icon'+num+'" onclick="hideOrShowImageInput(this);"/><bean:message key="label.use.other.image" bundle="quickPaymentPanelBundle"/>&nbsp;<span id="imageSize'+num+'"></span>'+
                        '</td>'
                    );
                    if(num==0)
                    {
                        $("#providerField0").val('');
                        $("#providerFieldSumm0").val('');
                        $("#summ0").val('');
                    }
                    $('.providerBlock').each(function()
                    {
                        var show = $(this).find('[name^=fields(providerNameShow]').attr('checked');
                        $(this).find('[id^=imageSize]').text((show == true ||show =='on')?'55px*40px':'55px*55px');
                    });
                    return null;
                }

                function openServiceProviderFieldsList(callback)
                {
                    var providerId = $("#providerId0").val();
                    if(providerId == undefined || providerId == null || providerId=='')
                    {
                        alert("Выбрите поставщика.");
                        return;
                    }

                    window.setProviderFieldData = callback;
                    var h = getClientHeight - 100;
                    var w = getClientWidth - 100;
                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
                                 ", width=" + w +
                                 ", height=" + h +
                                 ", left=" + (getClientWidth() - w) / 2 +
                                 ", top=" + (getClientHeight() - h) / 2;
                    win = openWindow(null, document.webRoot+'/dictionaries/provider/fields/list.do?id='+providerId+'&action=getProviderField',
                                   'Providers', winpar);
                    win.moveTo(50, 50);
                    win.focus();
                }

                function setProviderFieldData1(resource)
                {
                    if($("#providerFieldSumm0").val() == resource['field'])
                    {
                        return 'Поле уже используется для ввода суммы';
                    }
                    $("#providerField0").val(resource['field']);
                    return null;
                }

                function setProviderFieldData2(resource)
                {
                    if($("#providerField0").val() == resource['field'])
                    {
                        return 'Поле уже используется для ввода номера телефона';
                    }
                    $("#providerFieldSumm0").val(resource['field']);
                    return null;
                }

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
                            '<div id="dep'+ id +'">'+
                                  '<input type="checkbox" name="selectedDepartments" value="'+id+'" class="departmentCheckbox"/>'+
                                  '<input type="hidden" name="selectedIds" value="'+id+'" style="border:none;"/>'+
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

                function removeProviderBlock(element)
                {
                    if (!confirm('<bean:message key="confirm.remove" bundle="quickPaymentPanelBundle"/>'))
			            return;
                    $(element).parents('fieldset').remove();
                    $("#providersCount").val(--providersCount);
                    setOrder();
                }

                function moveButton(element)
                {
                    var row = $(element).parents('fieldset');
                    if($(element).is(".buttonUp"))
                    {
                        row.insertBefore(row.prev());
                    }
                    else
                    {
                        row.insertAfter(row.next());
                    }
                    setOrder();

                }

                function addProviderBlock()
                {
                    if(providersCount > 5)
                    {
                        alert('<bean:message key="button.add.block.description" bundle="quickPaymentPanelBundle"/>');
                        return;
                    }
                    var num = parseInt(currentNumOfProvider);
                    var im = "${phiz:escapeForJS(imageInput, true)}".replace(/defaultID/g, 'icon'+num);
                    $("#forAddBlockDiv").after(
                    '<fieldset class="providerBlock">'+
                        '<html:hidden property="fields(orderIndex'+num+')" styleId="providerBlockOrder'+num+'" value="0"/> '+
                        '<html:hidden property="fields(providerId'+num+')" styleId="providerId'+num+'"/>'+
                        '<input type="hidden" name="orderIds" value="'+num+'" style="border:none;"/>'+
                                '<div><a class="pointer" onclick="removeProviderBlock(this);">Удалить блок</a></div>'+
                                '<div>'+
                                    '<div style="float:right; padding: 2px 0;">'+
                                        '<div style="width:19px; float:left; padding: 0 2px;">'+
                                            '<img src="${skinUrl}/images/bth_down.gif" title="вниз" class="buttonDown" style="visibility: visible;" onmousedown="moveButton(this)"/>'+
                                        '</div>'+
                                    '</div>'+
                                    '<div style="float:right; padding: 2px 0;">'+
                                        '<div style="width:19px; float:left; padding: 0 2px;">'+
                                            '<img src="${skinUrl}/images/bth_up.gif" title="вверх" class="buttonUp" style="visibility: visible;" onmousedown="moveButton(this)"/>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'+

                            '<div class="form-row">'+
                                '<div class="paymentLabel">'+
                                    '<bean:message key="label.provider" bundle="quickPaymentPanelBundle"/>'+
                                    '<span class="asterisk">*</span>'+
                                '</div>'+
                                '<div class="paymentValue">'+
                                    '<html:text property="fields(providerName'+num+')" size="40"  maxlength="25" styleId="providerName'+num+'"/>'+
                                    '<input id="openProviderDictionary" type="button" class="buttWhite smButt" '+
                                        'onclick="openServiceProvidersList(function(d){return setProviderInfo1(d, '+num+')});" value="..."/>'+ '</div>'+
                                '<div class="clear"></div>'+
                                '<div class="descriptionBlock">'+
                                    '<bean:message key="label.provider.description" bundle="quickPaymentPanelBundle"/>'+
                                '</div>'+
                                '<div class="clear"></div>'+
                            '</div>'+

                                    '<div class="form-row">'+
                                    '<div class="paymentLabel">'+
                                        '<bean:message key="label.provider.name" bundle="quickPaymentPanelBundle"/>'+
                                    '</div>'+
                                    '<div class="paymentValue">'+
                                        '<input type="checkbox" name="fields(providerNameShow'+num+')" class="icon'+num+'" checked="checked" value="true" onchange="changeImageSize(this);"/><bean:message key="label.show.name" bundle="quickPaymentPanelBundle"/><br/>'+
                                        '<html:text property="fields(providerAlias'+num+')" size="40"  maxlength="14" styleId="providerAlias'+num+'"/>'+
                                    '</div>'+
                                    '<div class="clear"></div>'+
                                    '</div>'+

                            '<div class="form-row">'+
                                '<div class="paymentLabel">'+
                                    '<bean:message key="label.icon" bundle="quickPaymentPanelBundle"/>'+
                                '</div>'+
                                '<div class="paymentValue">'+
                                    '<input type="checkbox" name="fields(providerShow'+num+')" checked="checked" value="true"/><bean:message key="label.show.block" bundle="quickPaymentPanelBundle"/>'+
                                    '<table>'+
                                        '<tr>'+
                                        '<td></td>'+
                                        '<td></td>'+
                                        '<td></td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td></td>'+
                                        '<td id="providerImage'+num+'"></td>'+
                                        '<td></td>'+
                                        '</tr>'+
                                        '<tr>'+
                                        '<td></td>'+
                                        '<td></td>'+
                                        '<td></td>'+
                                        '</tr>'+
                                        '<tr id="imageInputCell'+num+'"style="display:none">'+
                                        '<td></td>'+
                                        '<td colspan="2">'+
                                        im +
                                        '</td>'+
                                        '</tr>'+
                                    '</table>'+
                                '</div>'+
                                '<div class="clear"></div>'+
                            '</div>'+

                    '</fieldset>');
                    addImage(new jsImageObject('icon'+num, DISC_IMAGE_KIND, 50));
                    $('[name=field(imageSourceKindicon'+num+')]')[0].checked=true;
                    currentNumOfProvider++;
                    $("#providersCount").val(++providersCount);
                    setOrder();
                }

                function changeImageSize(elem)
                {
                    var e = $(elem).parents('fieldset:first');
                    var show = $(elem).attr('checked');
                    var alias = $(e).find('[name^=fields(providerAlias]');
                    alias.attr('readonly', !show);
                    if(!show)
                        alias.val('');
                    $(e).find('[id^=imageSize]').text((show == true)?'55px*40px':'55px*55px');
                    var imageId = $(elem).attr('class');
                    var image = getImageObject(imageId);
                    if(image != undefined && image != null)
                        image.clear();
                }

                function hideOrShowImageInput(elem)
                {
                    var imageInputCell = $(elem).parents('.providerBlock').find('[id^=imageInputCell]');
                    var hide = $(elem).val();
                    if(hide=="true")
                        imageInputCell.hide();
                    else
                        imageInputCell.show();

                }

                $(document).ready(function()
                {
                    $('.providerBlock').each(function()
                    {
                        var show = $(this).find('[name^=fields(providerNameShow]').attr('checked');
                        var alias= $(this).find('[name^=fields(providerAlias]');
                        alias.attr('readonly', !show);
                        if(!show)
                            alias.val('');
                        $(this).find('[id^=imageSize]').text((show == true ||show =='on')?'55px*40px':'55px*55px');
                        var defaultImage = $(this).find('[name^=fields(isDefaultImage][checked="true"]').val();
                        var imageInputCell = $(this).find('[id^=imageInputCell]');
                        if(defaultImage=="true")
                            imageInputCell.hide();
                        else
                            imageInputCell.show();

                    });
                });

                function clearImageInputs()
                {
                    $('.providerBlock').each(function()
                    {
                        var radoElem = $(this).find('[name^=fields(isDefaultImage][checked="true"]');
                        if(radoElem == undefined || radoElem == null) return;
                        var imageId = radoElem.attr('class');
                        var defaultImage = radoElem.val();
                        var image = getImageObject(imageId);

                        if(defaultImage=="true")
                            image.clear();

                    });
                }

                function validateImageSize()
                {
                    var needConfirm = false;
                    $('[name^=fields(providerNameShow]:checked').each(function(){
                        if($(this).attr('class') != 'icon0')
                        {
                            var parents = $(this).parents('.providerBlock');
                            var value = parents.find('[id^=providerAlias]').val();
                            var isDefaultImage = parents.find('[name^=fields(isDefaultImage]:checked').val();

                            if(($(this).val() == "true" || $(this).val()=='on') && (value == null || value.length == 0) && isDefaultImage=='false')
                            {
                                needConfirm = true;
                            }
                        }
                    });

                    if(needConfirm)
                    {
                        return confirm("Вы выбрали размер загружаемого изображения 55х40 и не указали название. Продолжить?");
                    }
                    return true;
                }
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                $(document).ready(function()
                {
                    regionChoose();
                });
            </script>

            <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsAndOperation.js"></script>
            <script type="text/javascript">

                var actionFilterURL = "${phiz:calculateActionURL(pageContext, '/private/async/filter')}";
                function clearFilter()
                {
                    ajaxQuery("url=/private/credit-card-office/list", actionFilterURL, clearFilterParams, null, true);
                }

                function additionalClearFilter(){
                    var regionName = $('[name="filter(regionName)"]').val();
                    if ($.trim(regionName) == '')
                        regionName = 'Все регионы';
                    $('#regionSearchNameSpan').text(regionName);
                }

                function sendDepartmentData()
                {
                    var ids = document.getElementsByName("selectedIds");
                    var synchKey = getRadioValue(ids);
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var r = ids.item(i).parentNode.parentNode;
                            var a = new Array(5);


                            a['name'] = trim(r.cells[1].innerHTML);
                            a['address'] = trim(r.cells[2].innerHTML);
                            a['tb'] = buildOffice(r.cells[3]);
                            a['osb'] = buildOffice(r.cells[4]);
                            a['vsp'] = buildOffice(r.cells[5]);
                            a['regionId'] = $('[name="filter(regionId)"]').val();

                            <c:if test="${form.guest}">
                                if ($('[name="filter(regionCodeTB)"]').val() != a['tb'])
                                {
                                    alert("Необходимо нажать кнопку Найти");
                                    return;
                                }
                                if ($('[name="filter(regionId)"]').val() != "")
                                    a['regionId']  =  $('[name="filter(regionId)"]').val();
                                else
                                    a['regionId']  =  $('[name="regionId"]').val();
                                a['regionName'] = $('[name="filter(regionName)"]').val();
                            </c:if>
                            window.opener.setCreditCardOfficeInfo(a);
                            window.close();
                            return;
                        }
                    }
                    addError("Пожалуйста, выберите отделение для получения карты.", "errors");
                }

                function buildOffice(cell)
                {
                    return trim(cell.innerHTML.replace(/&nbsp;/g,''));
                }
            </script>

            <%-- Блок "подождите" --%>
            <div id="loading" style="left:-3300px;">
                <div id="loadingImg"><img src="${skinUrl}/images/ajax-loader64.gif"/></div>
                <span>Пожалуйста, подождите,<br/> Ваш запрос обрабатывается.</span>
            </div>

            <div id="workspace" style="position:absolute; width:100%">
                <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp" flush="false">
                    <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                    <c:set var="bundleName" value="${messagesBundle}"/>
                </tiles:insert>
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title">Выбор отделения для получения карты</tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                            <tiles:put name="width" value="64px"/>
                            <tiles:put name="height" value="64px"/>
                            <tiles:put name="description">
                                Выберите отделение из списка, в котором вам будет удобно забрать карту, либо воспользуйтесь поиском. Для получения карты у вас должна быть постоянная или временная регистрация в регионе, в котором находится выбранное отделение.
                            </tiles:put>
                        </tiles:insert>
                        <%-- Фильтр --%>
                        <div id="selectDepartment">
                            <tiles:insert definition="filter" flush="false">
                                <tiles:put name="showButtonOpenMore" value="false"/>
                                <tiles:put name="clearButtonBundle" value="paymentsBundle"/>
                                <tiles:put name="clearButtonStyle" value="blueGrayLinkDotted"/>
                                <tiles:put name="showClearButton" value="true"/>
                                <%--Для очистки полей фильтра с дефолтными значениями--%>
                                <tiles:put name="data">
                                    <input type="hidden" name="filter(regionId)" value="${form.filters.regionId}"/>
                                    <input type="hidden" name="guest" value="${form.guest}"/>
                                    <input type="hidden" name="regionId" value="${form.regionId}"/>
                                    <input type="hidden" name="filter(regionName)" value="${form.filters.regionName}"/>
                                    <input type="hidden" name="filter(regionCodeTB)" value="${form.filters.regionCodeTB}"/>
                                    <table>
                                        <tr>
                                            <td class="vertical-middle alignRight">Регион обслуживания:&nbsp;</td>
                                            <td>
                                                <div class="selectRegion">
                                                    <div id="reigonSearchName" class="regionSelect" onclick="win.open('searchRegionsDiv'); return false">

                                                        <span id="regionSearchNameSpan" title="${form.filters.regionName}">
                                                            <c:choose>
                                                                <%--  Текущий регион --%>
                                                                <c:when test="${empty form.filters.regionName}">
                                                                    Все регионы
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${form.filters.regionName}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </span>
                                                    </div>

                                                    <tiles:insert definition="window" flush="false">
                                                        <tiles:put name="id" value="searchRegionsDiv"/>
                                                        <tiles:put name="loadAjaxUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/sbnkd-regions/list')}?isOpening=true&id=0&saveRegion=false&oldId=${form.regionId}"/>
                                                        <tiles:put name="styleClass" value="regionsDiv"/>
                                                        <tiles:put name="closeCallback" value="onCloseRegionWindow"/>
                                                    </tiles:insert>

                                                    <script type="text/javascript">
                                                        function onCloseRegionWindow() {
                                                            $('[name="regionId"]').val($('[name="filter(regionId)"]').val());
                                                            setTimeout(function() {findCommandButton('button.search').click('', false)}, getCommandButtonDelay());
                                                            return true;
                                                        }
                                                    </script>
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="alignRight">Наименование отделения:</td><td><html:text property="filter(name)" styleClass="departmentName"/></td>
                                        </tr>
                                        <tr>
                                            <td class="alignRight">Населенный пункт/город:&nbsp;</td><td><html:text property="filter(region)"/></td>
                                        </tr>
                                        <tr>
                                            <td class="alignRight">Улица:</td><td><html:text property="filter(street)"/></td>
                                        </tr>
                                    </table>
                                    <div class="filterDivider">&nbsp;</div>
                                </tiles:put>
                                <tiles:put name="buttonKey" value="button.search"/>
                                <tiles:put name="buttonBundle" value="contactBundle"/>
                            </tiles:insert>
                        </div>
                        <%-- /Фильтр --%>
                        <div class="clear">&nbsp;</div>

                        <tiles:insert definition="simpleTableTemplate" flush="false">
                            <tiles:put name="grid">
                                <sl:collection id="listElement" model="simple-pagination" property="data" >
                                    <sl:collectionParam id="selectType" value="radio"/>
                                    <sl:collectionParam id="selectName" value="selectedIds"/>
                                    <sl:collectionParam id="selectProperty" value="id"/>

                                    <sl:collectionItem title="Наименование" property="name"/>
                                    <sl:collectionItem title="Адрес" property="address"/>
                                    <sl:collectionItem hidden="true" property="code.fields.region"  />
                                    <sl:collectionItem hidden="true" property="code.fields.branch"  />
                                    <sl:collectionItem hidden="true" property="code.fields.office"  />

                                    <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                    <sl:collectionParam id="onRowDblClick" value="sendDepartmentData();"/>
                                </sl:collection>
                            </tiles:put>
                            <tiles:put name="isEmpty" value="${empty form.data}"/>
                            <tiles:put name="emptyMessage">
                                По указанным Вами параметрам не было найдено ни одного отделения. Пожалуйста, измените параметры поиска.
                            </tiles:put>
                        </tiles:insert>
                        <script type="text/javascript">
                            if (${phiz:size(form.data) == 1}){
                                document.getElementsByName('selectedIds').item(0).checked = true;
                            }
                        </script>
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle"         value="dictionaryBundle"/>
                                <tiles:put name="onclick"        value="window.close();"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                            </tiles:insert>
                            <c:if test="${not empty form.data}">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.choose"/>
                                    <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                    <tiles:put name="bundle"         value="dictionaryBundle"/>
                                    <tiles:put name="onclick"        value="sendDepartmentData();"/>
                                </tiles:insert>
                            </c:if>
                        </div>
                        <div class="clear"></div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<script src="${phiz:getYandexMapComponentUrl()}" type="text/javascript"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.autocomplete2.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/json2.js"></script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/departmentMapSelection.js"></script>
<script type="text/javascript">
    document.config =
    {
        serviceUrl : '${phiz:getYandexMapServiceUrl()}'
    };

    $(document).ready(function ()
    {
        var list = $("#onList");
        $("#onMap").click(function ()
        {
            $(".depsOnMap").show();
            $(".depsOnList").hide();
            $(".listShadow").hide();
            $("#onList").removeClass("activeButton");
            $("#onMap").addClass("activeButton");
            $("#map").css("display", "block");
        });
        list.bind("click", function ()
        {
            $(".depsOnMap").hide();
            $(".depsOnList").show();
            $(".listShadow").show();
            $("#onList").addClass("activeButton");
            $("#onMap").removeClass("activeButton");
            $("#map").css("display", "none");
        });

        $("#bridge").live("click", function() {
            showDepartmentById($(this).val());
        });
    });

    var departments = [];
    var map;
    var serviceOiBUrl = "${phiz:getYandexMapServiceUrl()}";
    var clusterer;
    var selectedDepartment;
    <c:if test="${phiz:impliesService('SelectionOfficeByMapService')}">
        var showMapData = function (data)
        {
            var geoObjects = mapDataLoaded(map, data);
            clusterer.add(geoObjects);
            $.merge(departments, geoObjects);
        };
        var success = function (data)
        {
            if (!data || data == null || !yandexMapAvailable())
            {
            }
            else
            {
                ymaps.ready(function()
                {
                    map = createMap("map");
                    if (confirm("Вы хотите определить свое местоположение ?"))
                        setUserPlace(map);
                    else
                        setUserRegion("${phiz:getYandexMapClientRegionName(form.document)}");
                    var idList = getVisibleQidList(map.getBounds());
                    var tbCode = "${phiz:getYandexMapClientTBCode(form.document)}";
                    clusterer = createClusterer(map);
                    loadData(idList, tbCode, showMapData);
                    initAutocomplete(map, "searchString");
                });
            }
        };
        loadData([3354], "042", success);
    </c:if>

    function find()
    {
        var text = $("#searchString").val();
        if (trim(text) == "")
            return false;
        var geoCoder = ymaps.geocode(text, {
            results: 1
        });
        geoCoder.then(function (result) {
            result.geoObjects.each(function (object) {
                var geometry = object.geometry,
                        bounds = object.properties.get("boundedBy");
                if (bounds) {
                    map.setBounds(bounds);
                } else {
                    map.setCenter(geometry.getCoordinates());
                }
            });
        });
        return false;
    }

    function chooseDepartment()
    {
        if ($('.onList').hasClass('activeButton'))
            return;
        if (!selectedDepartment)
            return;
        var code = selectedDepartment.code;
        if (!code)
            return;
        code = parseFloat(code).toFixed(0) + "";
        var a = {};
        a['name'] = selectedDepartment.name;
        a['region'] = code.substr(0, 2);
        a['branch'] = code.substr(2, 4);
        a['office'] = code.substr(7, code.length);
        setOfficeInfo(a);
        win.close('selectDepartment');
        return false;
    }

    function showDepartmentById(id)
    {
        var department = findDepartmentById(id);
        if (!department)
        {
            var idList = getVisibleQidList(map.getBounds());
            loadData(idList, null, showMapData);
        }
        department = findDepartmentById(id);
        if (department)
        {
            map.setZoom(16);
            var geometry = department.geometry,
                bounds = department.properties.get("boundedBy");
            if (bounds)
                map.setBounds(bounds);
            else
                map.setCenter(geometry.getCoordinates());
            department.events.fire('click');
        }
    }
</script>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="selectDepartment"/>
    <tiles:put name="data">
        <div class="marker"></div>
        <div id="depByMapDiv">
            <span class="title">
                <h1 class="completeTitle">Выбор отделения обслуживания кредита</h1>
            </span>

            <div class="dataFullSpace">
                <div id="ymaps-search-view" class="simpleFilter">
                    <div class="searchSpace">
                        <input id="searchString" class="customPlaceholder search inputPlaceholder" type="text" placeholder="Введите название улицы или населенного пункта" onkeypress="find();"/>

                        <div id="ymaps-find-button" class="commandButton ">
                            <div class="whiteFltrBtn">
                                <a>
                                    <div class="left-corner"></div>
                                    <div class="text">
                                        <span onclick="find();">Найти</span>
                                    </div>
                                    <div class="right-corner"></div>
                                </a>
                            </div>
                            <div class="clear"></div>
                        </div>

                        <div id="search-result-region" class="search-result-region"></div>
                    </div>
                </div>
                <!-- Индикатор загрузки. Отображается при необходимости. По умолчанию выключен -->
                <div class="loadingDetector"></div>

                <div class="mapControl">
                    <div id="receiverSubTypeControl">
                        <!-- Для активной переключалки добавляем класс activeButton -->
                        <div id="onMap" class="inner firstButton activeButton">
                            На карте
                        </div>
                        <div id="onList" class="inner lastButton">
                            Списком
                        </div>
                    </div>
                </div>

                <div class="fixHeightData">
                    <!-- Новое отображение сообщения об ошибке. Возможно потребуется создать новый компонент -->
                    <div class="redErrorType" style="display: none">
                        Не удалось загрузить адреса отделений. <span class="deepBlueDashed">Обновите карту</span> или <span class="deepGraySolid">воспользуйтесь упрощенным поиском</span>
                    </div>

                    <!-- Если отделений не найдено, прячем карту (.depsOnMap), список (.depsOnList) и сообщение об ошибках (.redErrorType) и выводим эту текстовочку -->
                    <div class="emptyDepListShowHide">
                        <div class="emptyDepList"> Мы не нашли отделений Сбербанка рядом с вами.<br/> Воспользуйтесь картой или введите в поиске ближайший<br/> населенный пункт.</div>
                    </div>

                    <div id="map" style="width: 737px; height: 501px;"></div>

                    <div id="ymaps-list-region" class="depsOnList"></div>
                </div>
                <div class="clear"></div>
                <div class="buttonsArea">
                    <div class="clientButton">
                        <div class="commandButton" onclick="chooseDepartment()">
                            <div class="buttonGreen select-department disabled">
                                <a tabindex="20">
                                    <div class="left-corner"></div>
                                    <div class="text">
                                        <span>Выбрать отделение</span>
                                    </div>
                                    <div class="right-corner"></div>
                                </a>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>

            <input id="bridge" type="hidden"/>
        </div>
        <div class="clear"></div>
    </tiles:put>

    <script type="text/template" id="ymaps-departments-list-layout-view-template">
        <div id="ymaps-departments-list-region">
        </div>
    </script>

    <script type="text/template" id="ymaps-departments-list-container-template">

        <table id="chooseDepOnListHeader" class="chooseDepOnList">
            <thead>
                <tr id="depOnlistHead" class="listTtl">
                    <th class="listIndent"></th>
                    <th class="align-left depListAddress">Адрес</th>
                    <th class="align-right depDistanceHeader">Расстояние</th>
                    <th class="align-right depNameHdr">Номер отделения</th>
                    <th class="showOnMapHdr"><div class="showOnMap"></div></th>
                    <th class="listIndent"></th>
                </tr>
            </thead>
        </table>
        <div class="chooseDepOnListHeight">
            <table id="chooseDepOnList" class="chooseDepOnList">
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="listShadow" style="display: block;"></div>
    </script>

    <script type="text/template" id="ymaps-departments-list-item">
        <td class="listIndent"></td>
        <td class="align-left depListAddress">{{= postAddress}}</td>
        <td class="align-right depDistance">{{= distance}}</td>
        <td class="align-right depName">{{= name}}</td>
        <td class="showOnMapList">
            <div class="showOnMap"></div>
        </td>
        <td class="listIndent"></td>
    </script>

    <script type="text/template" id="ymaps-search-collection-view-template">
    </script>

    <script type="text/template" id="ymaps-search-item-view-template">
        <div class="autocomplete-suggestion">
            <span>{{= displayName}}</span>
        </div>
    </script>

    <script type="text/javascript" data-main="${initParam.resourcesRealPath}/scripts/require/data/sberbankMapsData" src="${initParam.resourcesRealPath}/scripts/require/require.js"></script>
</tiles:insert>

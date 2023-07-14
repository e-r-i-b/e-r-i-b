document.debug = false;

function getServiceUrl(type)
{
    if (!type || type === undefined)
    {
        throw new Error('Должен быть установлен тип запроса');
    }

    var serviceUrl = null;

    if (document.debug && document.debug !== undefined)
    {
        serviceUrl = window.resourceRoot + '/scripts/test/%type.json'.replace('%type', type);
    }
    else
    {
        serviceUrl = serviceOiBUrl + '?url=http://oib-rs/oib-rs/%type/filial?'.replace('%type', type);
    }

    return function(options)
    {
        if (document.debug && document.debug !== undefined)
        {
            return serviceUrl;
        }

        if (options && options !== undefined)
        {
            var parameters = '';

            for (var field in options)
            {
                if (parameters.length > 0)
                {
                    parameters += '&';
                }

                parameters += 'name=value'.replace('name', field).replace('value', options[field]);
            }
        }

        return serviceUrl += parameters;
    };
}

function mapAvailable()
{
    return yandexMapAvailable() && serviceAvailable();
}

function yandexMapAvailable()
{
    try
    {
        if (!ymaps)
            return false;
    }
    catch(e)
    {
        return false;
    }
    return true;
}

function serviceAvailable()
{
    return true;
}

function initAutocomplete(map, searchString)
{
    $("#" + searchString)
            .autocomplete({
                source: [],
                minLength: 3,
                delay: 0,
                select: function (event, ui) {
                    if ($(this).data("value") !== ui.item.value) {
                        var geometry = ui.item.data.geometry,
                                bounds = ui.item.data.properties.get("boundedBy");

                        if (bounds) {
                            map.setBounds(bounds);

                        } else {
                            map.setCenter(geometry.getCoordinates());
                        }

                        $(this).data("value", ui.item.value);
                        var idList = getVisibleQidList(map.getBounds());
                        loadData(idList, null, showMapData);
                    }
                }
            })
            .live("keyup", (function (evt) {
                if ($.inArray(evt.which, [35, 36, 37, 38, 39, 40]) == -1) {
                    $(this).removeData("value");

                    clearTimeout($(this).data("keyup-timeout"));

                    var $self = $(this), value = $self.val();

                    if (value) {
                        $(this).data("keyup-timeout", setTimeout(function () {
                            var array = [],
                                    geoCoder = ymaps.geocode(value, {
                                        results: 5
                                    });

                            geoCoder.then(function (result) {
                                result.geoObjects.each(function (object) {
                                    array.push({
                                        label: object.properties.get("name") + " " + object.properties.get("description"),
                                        value: object.properties.get("name") + " " + object.properties.get("description"),
                                        data: object
                                    });
                                });

                                $self.autocomplete({source: array});
                                $self.trigger("focus");
                            });
                        }, 10));
                    }
                }
            }));
}

function createMap(blockName)
{
    return new ymaps.Map(blockName,
    {
        center: [55.751574, 37.573856],
        zoom: 10,
        controls: ['zoomControl', 'typeSelector', "geolocationControl"]
    },
    {
        searchControlProvider: 'yandex#search'
    });
}

function setUserPlace(map)
{
    var geolocation = ymaps.geolocation;
    geolocation.get({
        provider: 'auto',
        mapStateAutoApply: true
    }).then(function (result)
        {
            var selfLocationGeoObject = new ymaps.Placemark(result.geoObjects.position, {},
                {
                    cursor: "normal",
                    zIndex: 0,
                    zIndexHover: 0,
                    iconLayout: "default#image",
                    iconImageHref: globalUrl + '/commonSkin/images/location-map.png',
                    iconImageSize: [38, 38],
                    iconImageOffset: [-19, -19]
                });
            map.geoObjects.add(selfLocationGeoObject);
        });
}

function setUserRegion(region)
{
    var geoCoder = ymaps.geocode(region, {
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

}

function loadData(idList, tbCode, action)
{
    for (var i = 0; i < idList.length; i++)
    {
        var parameters = {tid : idList[i], pipe : 'branchesPipe', "filter[flags][p21]" : true};
        if (!isEmpty(tbCode))
            parameters["tbCode"] = tbCode;
        jQuery.ajax
        ({
            url     : getServiceUrl('bytid').call(this, parameters),
            success : function(data, status, xhr)
            {
                var obj = JSON.parse(data);
                action(obj);
            }
        });
        if (document.debug && document.debug !== undefined)
            return;
    }
}

function loadById(typeId, action)
{
    jQuery.ajax
    ({
        url     : getServiceUrl('byId').call(this, {'id[]' : typeId, pipe : 'branchesPipe'}),
        success : function (data, status, xhr)
        {
            var obj = JSON.parse(data);
            action(obj);
        }
    });
}

function loadByBounds()
{

}

function mapDataLoaded(map, data)
{
    var geoObjects = [];
    if (data && data.length > 0) {
        for (var x = 0; x < data.length; x++) {
            if (data[x].coordinates.latitude && data[x].coordinates.longitude) {
                if (!findDepartmentById(data[x].id))
                {
                    var m = createMarker(data[x], map);
                    map.geoObjects.add(m);
                    geoObjects.push(m);
                }
            }
        }
    }
    return geoObjects;
}

function findDepartmentById(id)
{
    var result;
    for (var i = 0; i < departments.length; i++)
    {
        if (departments[i].properties.get("data").id == id)
        {
            result = departments[i];
            break;
        }
    }
    return result;
}

function createClusterer(map)
{
    var clusterGreenIcons = [{
        href: globalUrl + '/commonSkin/images/map-cluster-green.png',
        size: [61, 61],
        offset: [-30, -30]
    }];
    var clusterYellowIcons = [{
        href: globalUrl + '/commonSkin/images/map-cluster-yellow.png',
        size: [61, 61],
        offset: [-30, -30]
    }];

    var customBalloonContentLayout = ymaps.templateLayoutFactory.createClass(
        "<div class='cluster-balloon' style='width: 359px; height: 210px;'></div>",
        {
            build: function () {
                customBalloonContentLayout.superclass.build.call(this);
                var geoObjects = this.getData().cluster.getGeoObjects();
                var objectCount = geoObjects.length;
                loadById(geoObjects[0].properties.get("data").id, function(data)
                {
                    var current = 0;
                    makeAndShowDataDepartment(data[0], current);
                });

                function arrowClick(event)
                {
                    event.stopImmediatePropagation();
                    if ($(this).hasClass("inactivePage"))
                        return;
                    var current = event.data.current;
                    loadById(geoObjects[current].properties.get("data").id, function(data)
                    {
                        makeAndShowDataDepartment(data[0], current);
                    });
                }

                function makeAndShowDataDepartment(data, current)
                {
                    var html = makeBalloonContent(data, false);
                    $(".cluster-balloon").html(html);
                    var balloon = $(".cluster-balloon div");
                    balloon.addClass("item" + current);
                    updateScroll(balloon.attr("class"), objectCount);
                    var prevArrow = balloon.find(".prevDepArrow");
                    var nextArrow = balloon.find(".nextDepArrow");
                    prevArrow.bind("click", {current: current - 1}, arrowClick);
                    nextArrow.bind("click", {current: current + 1}, arrowClick);
                    selectedDepartment = data;
                }

                function updateScroll(className, itemCount)
                {
                    var number = parseInt(className.substr(4, className.length - 4));
                    var nextArrow = $("." + className + " .nextDepArrow");
                    var prevArrow = $("." + className + " .prevDepArrow");
                    if (number < itemCount - 1)
                        nextArrow.removeClass("inactivePage");
                    else
                        nextArrow.addClass("inactivePage");
                    if (number > 0)
                        prevArrow.removeClass("inactivePage");
                    else
                        prevArrow.addClass("inactivePage");
                }
            }
        });
    var result = new ymaps.Clusterer({
        clusterIcons: clusterGreenIcons,
        clusterDisableClickZoom: true,
        clusterOpenBalloonOnClick: true,
        clusterBalloonPanelMaxMapArea: 0,
        clusterBalloonShadow: false,
        clusterBalloonCloseButton: false,
        hideIconOnBalloonOpen: false,
        disableClickZoom: true,
        gridSize: 64
    });

    result.events.add('click', function(e) {
        e.stopImmediatePropagation();
        e.preventDefault();

        var cluster = e.get('target');
        if (cluster.options.get("clusterIcons")[0].href == clusterGreenIcons[0].href)
        {
            cluster.options.set("clusterIcons", clusterYellowIcons);
            cluster.options.set('clusterBalloonContentLayout', customBalloonContentLayout);
            deleteSelectedDepartment(map);
            $(".buttonGreen.select-department").removeClass("disabled");
        }
        else
        {
            cluster.options.set("clusterIcons", clusterGreenIcons);
            $(".buttonGreen.select-department").addClass("disabled");
            map.balloon.close();
        }
    });
    map.geoObjects.add(result);
    return result;
}

function createMarker(object, map)
{
    if (object.coordinates)
    {
        object.coordinates = [
            object.coordinates.latitude,
            object.coordinates.longitude
        ];

        var iconGreenHref = globalUrl + "/commonSkin/images/map-point-green.png";
        var iconYellowHref = globalUrl + "/commonSkin/images/map-point-yellow.png";
        var marker = new ymaps.Placemark(object.coordinates, {data: object}, {
            iconLayout: 'default#image',
            iconImageHref: iconGreenHref,
            iconImageSize: [28, 40],
            iconImageOffset: [-18, -40],
            hideIconOnBalloonOpen: false,
            openEmptyBalloon: true,
            balloonCloseButton: false,
            balloonShadow: false,
            balloonPanelContentLayout: "branch#balloonPanelLayout",
            balloonOffset: [-5, -27],
            balloonAutoPan: true,
            balloonPanelMaxMapArea: 0,
            balloonPanelMaxHeightRation: 0.6,
            visible: true
        });
        marker.events.add('click', function(e) {
            e.stopImmediatePropagation();

            var placemark = e.get('target');
            if (placemark.options.get("iconImageHref") == iconYellowHref)
            {
                placemark.options.set("iconImageHref", iconGreenHref);
                $(".buttonGreen.select-department").addClass("disabled");
            }
            else
            {
                deleteSelectedDepartment(map);
                placemark.options.set("iconImageHref", iconYellowHref);
                $(".buttonGreen.select-department").removeClass("disabled");
            }
            var dataById = placemark.properties.get("dataById");
            if (dataById)
            {
                placemark.properties.set('balloonContent', makeBalloonContent(dataById[0], true));
                selectedDepartment = dataById[0];
            }
            else
                loadById(placemark.properties.get("data").id, function(data)
                {
                    placemark.properties.set("dataById", data);
                    placemark.properties.set('balloonContent', makeBalloonContent(data[0], true));
                    selectedDepartment = data[0];
                });
        });
        return marker;
    }
}

function deleteSelectedDepartment(map)
{
    for (var i = 0; i < departments.length; i++)
    {
        if (departments[i].options.get("iconImageHref") == globalUrl + "/commonSkin/images/map-point-yellow.png")
            departments[i].options.set("iconImageHref", globalUrl + "/commonSkin/images/map-point-green.png");
    }
}

function makeBalloonContent(data, isPlacemark)
{
    var nextDep = isPlacemark ? "" :
        "   <div class='nextDep'>" +
        "       <div class='prevDepArrow'></div>" +
        "       <div class='nextDepArrow'></div>" +
        "   </div>";
    var metroStation = data.metroStation ?
        "       <p class='depNearestMetro'>" + data.metroStation + "</p>" : "";
    var text =
        "<div>" + nextDep +
        "   <div class='depPositionInfo'>" +
        "       <p class='depName'>" + data.name + "</p>" +
        "       <p class='depAddress'>" + data.address + "</p>" + metroStation +
        "   </div>" +
        "   <div class='depAnotherInfo'>" +
        "       <div class='fullInfo2Col'>" +
        "           <p class='addInfoType'>Время работы:</p>" +
        "           <p class='addInfData'>" + formatWorkTime(data.workTimeList) + "</p>" +
        "       </div>" +
        "       <div class='fullInfo2Col'>" +
        "           <p class='addInfoType'>Телефоны: </p>" +
        "           <p class='addInfData'>" + data.phone + "</p>" +
        "       </div>" +
        "   </div>" +
        "</div>";
    return text;
}

function formatWorkTime(workTimeList)
{
    var current = null;
    var days = [];
    for (var i = 0; i < workTimeList.length; i++)
    {
        if (current == null)
            current = {firstDay: workTimeList[i].weekDayName, lastDay: null, begin: workTimeList[i].timeList[0], end: workTimeList[i].timeList[1]};
        else if (current.begin == workTimeList[i].timeList[0] && current.end == workTimeList[i].timeList[1])
        {
            current.lastDay = workTimeList[i].weekDayName;
            if (i == workTimeList.length - 1)
                days.push(current);
        }
        else
        {
            days.push(current);
            current = {firstDay: workTimeList[i].weekDayName, lastDay: null, begin: workTimeList[i].timeList[0], end: workTimeList[i].timeList[1]};
            if (i == workTimeList.length - 1)
                days.push(current);
        }
    }
    var result = "";
    for (i = 0; i < days.length; i++)
    {
        if (i != 0)
            result += ", ";
        result += days[i].firstDay + ".";
        if (days[i].lastDay)
            result += "-" + days[i].lastDay + ".";
        result += " ";
        result += days[i].begin + " &#8211; " + days[i].end;
    }
    return result;
}

function getVisibleQidList(bounds)
{
    return toQIdList(bounds);
}

function toQIdList(bounds) {
    var bZoom = 20;

    var lat = getQCoord(bounds[0][0], bZoom);
    var lon = getQCoord(bounds[0][1], bZoom);
    var startLon = lon;
    var latEnd = getQCoord(bounds[1][0], bZoom);
    var lonEnd = getQCoord(bounds[1][1], bZoom);
    var latLonList = [];
    var c = 0;
    for (; lat <= latEnd; lat++) {
        for (lon = startLon; lon <= lonEnd; lon++) {
            latLonList[c] = mixBit(lat, lon);
            c++;
        }
    }
    return latLonList;
}

function getQCoord(c, bZoom)
{
    var cc = c * 1000000;
    cc = cc.toPrecision(8);
    var m = 0xFFFFFFFF << bZoom;
    cc = (cc & m) >>> bZoom;
    return cc;
}

function mixBit(v1, v2)
{
    var r = 0;
    var mask = 0x01;
    for (var i = 0, j = 0; i < 32; i++) {
        r = r | ((v1 & mask) << j);
        r = r | ((v2 & mask) << j + 1);
        mask = mask << 1;
        j++;
    }

    return r;
}
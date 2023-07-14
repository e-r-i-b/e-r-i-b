//скрипты для страницы "Все платежи и переводы"
var MIN_SEARCH_STRING_MESSAGE = "Пожалуйста, введите в поле для поиска организации не менее 3-х символов.";
function hideOrShowCategory()
{
    var element = $(".paymentsContainer");

    if (element.css('display') == "block")
    {
        element.hide();
    }
    else
    {
        element.show();
    }
}

// Функция устанавливающая параметры блокируюшего дива
 function setTintedRegionsDiv(show)
 {
     var regionsDiv = document.getElementById("searchRegionsDivWin");
     if(show == null) return ;
     TintedNet.setTinted(regionsDiv, show);
 }

 function afterConfirm()
 {
     var searchInput = document.getElementById("searchServices");
     searchInput.refresh = true;
     clearParams();
     new CommandButton('button.search').click();
     win.close('searchRegionsDiv');
     win.close('regionsDiv');
     setTintedRegionsDiv(false);
 }

//   Функция, пейджинга страницы поиска
function changeSearchPage(countInPage,paginationType, searchPage, searchString)
{
    if (searchString == $("#searchServices").val())
    {
        $("#provCountInPage").val(countInPage);
        $("#paginationType").val(paginationType);
        $('#searchPage').val(searchPage);
        var pageListString = $('#pageList').val();
        //запоминаем, сколько поставщиков на странице нарисовали
        if (pageListString.split('|').length <= searchPage+1)
             $('#pageList').val(pageListString + countInPage + "|");
        if (paginationType == 'next')
        {
            $('#searchPage').val(searchPage+1);
        }
        else
        {
           $('#searchPage').val(searchPage-1);
        }
    }
    else{
        clearParams();
    }
    new CommandButton('button.search').click();
}

//   Функция изменяющая ширину поля для поиска
function changeRegionWidth(maxWidthInput, maxWidthRegion)
{
    var MAX_WIDTH_INPUT = maxWidthInput; // начальная ширина для input
    var MAX_WIDTH_REGION = maxWidthRegion; //максимальная ширина для названия региона
    var regionWidth = $("#reigonSearchName").width();
    var nameRegion = document.getElementById('reigonSearchNameSpan').title;
    if (regionWidth > MAX_WIDTH_REGION)
    {
        $("#reigonSearchName").css("width", MAX_WIDTH_REGION);
        $("#reigonSearchName").css("max-width", MAX_WIDTH_REGION);
        $("#searchServices").css("width", MAX_WIDTH_INPUT - MAX_WIDTH_REGION);
        $("#searchServices").css("max-width", MAX_WIDTH_INPUT - MAX_WIDTH_REGION);
        $("#regionBlackout").css("display", "");
    }
    else
    {
        $("#searchServices").css("width", MAX_WIDTH_INPUT - regionWidth);
        $("#searchServices").css("max-width", MAX_WIDTH_INPUT - regionWidth);
        $("#reigonSearchName").css("width", regionWidth);
        $("#reigonSearchName").css("max-width", regionWidth);
        $("#regionBlackout").css("display", "none");
    }
}

var regionSelectorWindowId = 'searchRegionsDiv';
function updateRegionsField(regionSelector)
{
    $('[name=field(regionId)]').val(regionSelector.currentRegionId);
    $('[name=field(regionName)]').val(regionSelector.currentRegionName);
}

function updateRegionsFieldForCard(regionSelector)
{
    $('[name=filter(regionId)]').val(regionSelector.currentRegionId);
    $('[name=filter(regionName)]').val(regionSelector.currentRegionName);
    $('[name=filter(regionCodeTB)]').val(regionSelector.currentRegionCodeTB);
    $('#regionSearchNameSpan').text(regionSelector.currentRegionName);
    setTintedRegionsDiv(false);
    win.close('searchRegionsDiv');
}

function afterActionCallback(regionSelector, msg)
{
    if (trim(msg) == '')
    {
        updateRegionsField(regionSelector);
        setTintedRegionsDiv(true);
        win.open('confirmSaveRegion');
    }
    else
    {
        $("#" + regionSelectorWindowId).html(msg);
    }
}
var regionSelectorParameters =
{
    windowId: regionSelectorWindowId,
    click:
    {
        getParametersCallback: function(id)
        {
            return "needSave=false&setCnt=true" + (id > 0? '&id=' + id: '');
        },
        afterActionCallback: afterActionCallback,
        updateRegionsFieldForCard: updateRegionsFieldForCard
    },
    choose:
    {
        getParametersCallback: function(id)
        {
            return 'needSave=false&setCnt=true&select=true&id=' + id;
        },
        afterActionCallback: afterActionCallback,
        updateRegionsFieldForCard: updateRegionsFieldForCard
    }
};
$(document).ready(function()
{
    initializeRegionSelector(regionSelectorParameters);
    //регистрируем слушателя изменения региона в шапке
    getRegionSelector('regionsDiv').addListener(function(regionSelector){
        updateRegionsField(regionSelector);
        afterConfirm();});
});

function closeRegionsDivWin()
{
    win.close('confirmSaveRegion');
    setTintedRegionsDiv(false);
}
function clearParams()
{
    $('input[name=provCount]').val(0);
    $('input[name=searchPage]').val(0);
    $('input[name=pageList]').val("");
    $("#provCountInPage").val(0);
}
function changeOrderType(type)
{
    $('input[name=searchType]').val(type);
     clearParams();
     new CommandButton('button.search').click();
}

var providerZIndex = 10;
/* Проставляем z-index, чтобы корректно отображалось в ie6,7 */
function setZIndex(id)
{
    var parent = document.getElementById('provider' + id);
    var otherRegions = document.getElementById('otherRegions' + id);
    $(parent).css('z-index', providerZIndex);
    if (otherRegions != undefined)
        $('#otherRegions'+id).css('z-index', providerZIndex);
    providerZIndex--;
}

doOnLoad(function()
{
    $('#confirmSaveRegionWin .closeImg').bind('click', closeRegionsDivWin);
});
//отображение результатов поиска в остальных регионах
function showProviders(data){
    if(data == null)
        return;
    $("#homeRegion").hide();
    $(".otherRegionsAreaShow").hide();
    if ($("#needAdd").val() == "true")
    {
        $("#homeRegion").show();
    }
    var htmlData = trim(data).replace(/^&nbsp;+/, "");
    $("#otherRegion").html(htmlData);
    $("#otherRegion").show();
    $('.word-wrap').breakWords();
}

function saveRegion(){
    clearParams();
    new CommandButton('button.saveRegion').click();
}

function hideSearchResult(){
    $("#otherRegion").hide();
    $(".otherRegionsAreaHide").hide();
    $("#homeRegion").show();
    $(".otherRegionsAreaShow").show();
}

function findProvider(countInPage, paginationType, provCount, firstPageProviderCount, searchPage, firstPage, searchStringContainerId, pageType, additionalParameters )
{
    if (!validateSearchStringLength() || searchStringContainerId != undefined && $("#" + searchStringContainerId).val() != $("#searchServices").val()){
        new CommandButton('button.search').click();
        return;
    }
    $("#needAdd").val(false);
    var pageListString = "";
    if (firstPage != 'true')
    {
        pageListString = $('#pageListByRegion').val();

        //запоминаем, сколько поставщиков на странице нарисовали
        if (pageListString.split('|').length <= searchPage+1)
             pageListString = pageListString + countInPage + "|";

        if (paginationType == 'next')
            searchPage = searchPage + 1;
        else
           searchPage = searchPage - 1;
    }
    if (firstPageProviderCount < 8 && searchPage == 0 )
        $("#needAdd").val(true);
    var parameters = "searchServices=" +encodeURIComponent($("#searchServices").val()) + "&paginationType="+paginationType +
                     "&provCountInPage=" + countInPage + "&provCount=" + provCount+
                     "&firstPageProviderCount="+firstPageProviderCount + "&searchPage="+ searchPage
                    + "&pageListByRegion=" + pageListString
                    + "&pageType=" + pageType;
    if (additionalParameters)
    {
        parameters += '&' + additionalParameters;
    }
    ajaxQuery(parameters, showProvidersURL, showProviders, null, true);
}

function validateSearchStringLength()
{
    var searchInput = $("#searchServices").val();
    if (searchInput.length >= 0 && searchInput.length < 3)
        return false;
    return true;
}

function search(){
    if (searchBtClick())
    {
        clearParams();
        new CommandButton('button.search').click();
    }
}
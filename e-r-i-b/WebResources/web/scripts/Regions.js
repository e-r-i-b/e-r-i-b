/*
* скрипт для работы с регионами (выбором региона в различных местах)
* 1. селектор инициализируется методом initializeRegionSelector, в который передается параметр (возможные значения см. this.parameters)
* 2. получение селектора организуется по идентификатору всплывающего окна со списком регионов: getRegionSelector(winId)
* 3. добавление слушателя изменения региона организуется через regionSelector.addListener
*/

function regionSelector(parameters)
{
    var dictionaryURL;
    if (parameters.customDictionaryUrl != null)
        dictionaryURL = document.webRoot + parameters.customDictionaryUrl; //урл справочника регионов;
    else
        dictionaryURL = document.webRoot + '/dictionaries/regions/list.do'; //урл справочника регионов;

    this.parameters =
    {
        windowId:      null, //идентификатор окна выбора региона
        dictionaryURL: dictionaryURL, //урл справочника регионов
        //настройки действия "клик"
        click:
        {
            //использовать ли аякс
            useAjax: true,
            //футкции получения параметров аякс запросов (параметры: идентификатор региона)
            getParametersCallback: function(id){return 'id=' + id;},
            //футкция которая должна сработать после "клика" по региону
            //1. если используется аякс, то в качестве колбека аякса (параметры: текущий regionSelector, данные аякса)
            //2. в противном случае непосредственно после "клика" (параметры: текущий regionSelector)
            afterActionCallback: function(){}
        },
        //настройки действия "выбора"
        choose:
        {
            //использовать ли аякс
            useAjax: true, 
            //футкции получения параметров аякс запросов (параметры: идентификатор региона)
            getParametersCallback: function(id){return 'id=' + id;},
            //футкция которая должна сработать после "выбора" региона
            //1. если используется аякс, то в качестве колбека аякса (параметры: текущий regionSelector, данные аякса)
            //2. в противном случае непосредственно после "выбора" (параметры: текущий regionSelector)
            afterActionCallback: function(){}
        }
    };

    $.extend(true, this.parameters, parameters);

    this.lock = false;
    this.currentRegionId = null;
    this.currentRegionName = null;
    this.listeners = new Array();

    this.chooseRegion = function(id, name, codeTB, saveRegion, actionType)
    {
        if (this.lock)
            return;
        this.lock = true;
        document.body.style.cursor = "progress";
        var myself = this;
        this.currentRegionId = id;
        this.currentRegionName = name;
        this.currentRegionCodeTB = codeTB;
        this.saveRegion = saveRegion;

        if (!saveRegion)
        {
            myself.parameters[actionType].updateRegionsFieldForCard(myself);
            document.body.style.cursor = "auto";
            this.lock = false;
            return;
        }
        var newCallback = function(myself, data)
        {
            myself.parameters[actionType].afterActionCallback(myself, data);
            document.body.style.cursor = "auto";
            myself.lock = false;
        };

        if (this.parameters[actionType].useAjax)
        {
            var params = this.parameters[actionType].getParametersCallback(id);
            ajaxQuery(params, this.parameters.dictionaryURL, function(data){newCallback(myself, data);});
        }
        else
        {
            newCallback(myself);
        }
    };

    this.addListener = function(listener)
    {
        this.listeners[this.listeners.length] = listener;
    };

    this.notifyListeners = function(myself)
    {
        try
        {
            for (var i = 0; i < this.listeners.length; i++)
                this.listeners[i](myself);
        }
        catch (err)
        {}
        return true;
    };
};

var regionSelectors = new Array();

function initializeRegionSelector(parameters)
{
    var newRegionSelector = new regionSelector(parameters);
    regionSelectors[parameters.windowId + "Win"] = newRegionSelector;
    return newRegionSelector;
}

function getRegionSelector(winId)
{
    return regionSelectors[winId + "Win"];
}

var clickRegion = {
    //клик по региону
    click: function (id, name, codeTB, saveRegion)
    {
        regionSelectors[$(win.active).attr('id')].chooseRegion(id, name, codeTB, saveRegion, 'click');
    },
    //выбор региона
    choose: function(id, name, codeTB, saveRegion)
    {
        regionSelectors[$(win.active).attr('id')].chooseRegion(id, name, codeTB, saveRegion, 'choose');
    }
};

function regionChoose()
{
    var regionsWindowId= 'regionsDiv';
    var afterRegionChooseCallback = function(myself, data)
    {
        if (trim(data) == 'OK')
        {
            win.close(regionsWindowId);
            var name = myself.currentRegionName;
            var regionChanged = trim($('#regionNameSpan').text()) != name;

            $('#regionNameSpan').html(name);

            if (name == 'Все регионы')
                $('.regionsAlphabetList .currentRegionName span').text('регион не выбран');
            else
                $('.regionsAlphabetList .currentRegionName span').text(name);

            if (regionChanged)
                myself.notifyListeners(myself);
        }
        else if(trim(data) == '')
        {
            location.reload();
        }
        else
        {
            $("#" + regionsWindowId).html(data);
        }
    };
    initializeRegionSelector(
    {
        windowId: regionsWindowId,
        click:
        {
            getParametersCallback: function(id){return id > 0? 'id=' + id: '';},
            afterActionCallback: afterRegionChooseCallback
        },
        choose:
        {
            getParametersCallback: function(id){return 'select=true&id=' + id;},
            afterActionCallback: afterRegionChooseCallback
        }
    });
}
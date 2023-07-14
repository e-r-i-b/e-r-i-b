/*
* ������ ��� ������ � ��������� (������� ������� � ��������� ������)
* 1. �������� ���������������� ������� initializeRegionSelector, � ������� ���������� �������� (��������� �������� ��. this.parameters)
* 2. ��������� ��������� ������������ �� �������������� ������������ ���� �� ������� ��������: getRegionSelector(winId)
* 3. ���������� ��������� ��������� ������� ������������ ����� regionSelector.addListener
*/

function regionSelector(parameters)
{
    var dictionaryURL;
    if (parameters.customDictionaryUrl != null)
        dictionaryURL = document.webRoot + parameters.customDictionaryUrl; //��� ����������� ��������;
    else
        dictionaryURL = document.webRoot + '/dictionaries/regions/list.do'; //��� ����������� ��������;

    this.parameters =
    {
        windowId:      null, //������������� ���� ������ �������
        dictionaryURL: dictionaryURL, //��� ����������� ��������
        //��������� �������� "����"
        click:
        {
            //������������ �� ����
            useAjax: true,
            //������� ��������� ���������� ���� �������� (���������: ������������� �������)
            getParametersCallback: function(id){return 'id=' + id;},
            //������� ������� ������ ��������� ����� "�����" �� �������
            //1. ���� ������������ ����, �� � �������� ������� ����� (���������: ������� regionSelector, ������ �����)
            //2. � ��������� ������ ��������������� ����� "�����" (���������: ������� regionSelector)
            afterActionCallback: function(){}
        },
        //��������� �������� "������"
        choose:
        {
            //������������ �� ����
            useAjax: true, 
            //������� ��������� ���������� ���� �������� (���������: ������������� �������)
            getParametersCallback: function(id){return 'id=' + id;},
            //������� ������� ������ ��������� ����� "������" �������
            //1. ���� ������������ ����, �� � �������� ������� ����� (���������: ������� regionSelector, ������ �����)
            //2. � ��������� ������ ��������������� ����� "������" (���������: ������� regionSelector)
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
    //���� �� �������
    click: function (id, name, codeTB, saveRegion)
    {
        regionSelectors[$(win.active).attr('id')].chooseRegion(id, name, codeTB, saveRegion, 'click');
    },
    //����� �������
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

            if (name == '��� �������')
                $('.regionsAlphabetList .currentRegionName span').text('������ �� ������');
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
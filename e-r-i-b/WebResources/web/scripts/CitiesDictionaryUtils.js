/* —крипты дл€ работы со справочником городов */

var citiesUtils = {

    clickSearch: function(elem)
    {
        setElement('field(searchPage)', 0);
        this.changePage(elem);
    },

    changeResOnPage: function(elem, num)
    {
        setElement('field(resOnPage)', num);
        setElement('field(searchPage)', 0);
        this.changePage(elem);
    },

    changePage: function(elem)
    {
        var parent = $(elem).closest('#citiesListWeatherWidget').parent();
        var id = $(parent).attr('id');

        var params = "&operation=button.searchCities";
        params += "&resOnPage="+getField('resOnPage').value;
        params += "&searchPage="+getField('searchPage').value;
        params += "&field(cityName)="+decodeURItoWin($(parent).find('*[name=field(cityName)]').val());

        var actionURL = document.webRoot + "/dictionaries/cities/list.do";
        var self = this;
        ajaxQuery (params, actionURL, function(data){self.citiesAjaxResult(data, id);});
    },

    citiesAjaxResult: function(data, id)
    {
        data = trim(data);
        //если вернулась пуста€ строка, то веро€тнее всего произошл тайм аут сессии, перезагружаем страницу
        if (data == '')
        {
            window.location.reload();
        }
        else
        {
            $('#'+id).html(data);
        }
    },

    closeSearch: function(elem)
    {
        var winId = $(elem).closest('#citiesListWeatherWidget').parent().attr('id');
        win.close(winId);
    }
};
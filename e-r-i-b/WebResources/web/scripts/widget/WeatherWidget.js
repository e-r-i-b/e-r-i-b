define("widget/WeatherWidget", [
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/query",
    "dojo/on",
    "dojo/dom-style",
    "dojo/dom-attr",
    "widget/_WidgetBase",
    "dojo/io/script"
], function(lang, declare, query, on, domStyle, domAttr, _WidgetBase, dojoIOScript)
{
    /**
     * Погодный виджет
     */
    return declare("widget.WeatherWidget", [ _WidgetBase ],
    {
        ///////////////////////////////////////////////////////////////////////
        // Умолчательные настройки виджета

        defaultSettings: function()
        {
            return lang.mixin(this.inherited(arguments), {
                title: "Погода"
            });
        },

        ///////////////////////////////////////////////////////////////////////
        // Поля виджета

        /**
         * Адрес экшена, который отвечает за отображение справочника городов
         * Определён в jsp
         * [readonly]
         */
        cityURL: undefined,

        /**
         * Префикс пути к картинкам
         * Определён в jsp
         * [readonly]
         */
        imageBaseURL: undefined,

        serviceKey: "",

        /**
         * Информация о погоде в виде js-объекта
         */
        weather: undefined,

        /**
         * Находимся в процессе получения данных о погоде с сервера 
         */
        weatherLoading: false,

        /**
         * Код от погодного сервиса и соответствующие состояния погоды
         */
        weatherCodes: [],

        /**
         *
         */
        cityName: "",
        enCityName: "",
        ///////////////////////////////////////////////////////////////////////
        // Кнопки и граф.компоненты виджета

        fieldSize: undefined,

        fieldCity: undefined,

        fieldTitle: undefined,

        buttonCancel: undefined,

        buttonSave: undefined,

        onStartup: function()
        {
            this.inherited(arguments);

            // 1. Подключаем поля
            this.fieldSize = this.findField("size");
            // Поскольку fieldSize - это кастомный селект,
            // событие onchange должно быть установлено таким образом 
            this.fieldSize.onchange = lang.hitch(this, function(){
                this.editSettings.size = this.fieldSize.value;
            });

            this.fieldCity = this.findField("city");
            on(this.fieldCity, "click", lang.hitch(this, function(){
                win.open(this.codename);
            }));

            this.fieldTitle = this.findField("title");
            
            // 2. Подключаем кнопки
            this.buttonCancel = this.findButton("cancel");
            on(this.buttonCancel, "click", lang.hitch(this, function(){
                this.reset();
                if (this.validate()) {
                    this.mode = "view";
                }
                this.refresh();
            }));

            this.buttonSave = this.findButton("save");
            on(this.buttonSave, "click", lang.hitch(this, function() {
                this.update();
                if (this.validate()) {
                    this.save();
                    this.mode = "view";
                }
                this.refresh();
            }));

            // 3. Инициализируем оконную систему для возможности выбора региона
            this.initCityWindow();

            // 4. Инициализируем коды состояний погоды
            this.initWeatherCodes();
        },

        onRefresh: function()
        {
            this.inherited(arguments);

            // 1. Настраиваем панель просмотра
            this.setLabelText("city", this.panelView, this.cityName);

            if (this.weather == undefined)
            {
                this.getWeather();
            }
        },

        onUpdate: function()
        {
            this.inherited(arguments);

            var title = this.fieldTitle.value;
            if (trim(title) == "")
                title = this.defaultSettings().title;
            this.editSettings.title = title;

            var changed = this.editSettings.city != $('#cityCode'+this.codename).val();
            if (changed)
            {
                this.editSettings.city = $('#cityCode'+this.codename).val();
                this.cityName = $('#cityName'+this.codename).text();
                this.enCityName = $('#enCityName'+this.codename).val();

                this.getWeather();
            }
        },

        ///////////////////////////////////////////////////////////////////////

        // TODO: переделать на использование dojo
        initCityWindow: function()
        {
            win.init(this.domNode);

            var self = this;
            win.aditionalData(self.codename,
            {
                onOpen: function (id)
                {
                    var element = document.getElementById(id);
                    element.dataWasLoaded = false;

                    win.loadAjaxData(self.codename, self.cityURL, function ()
                    {
                        element.dataWasLoaded = true;
                        win.open(self.codename, true);
                    });
                    return false;
                }
            });
        },

        initWeatherCodes: function()
        {
            this.weatherCodes[395] = "Сильный снег с метелью";
            this.weatherCodes[393] = "Местами легкий снег с метелью";
            this.weatherCodes[389] = "Сильный дождь с громом";
            this.weatherCodes[386] = "Местами легкий дождь с громом";
            this.weatherCodes[377] = "Сильный град";
            this.weatherCodes[374] = "Град";
            this.weatherCodes[371] = "Сильный снегопад";
            this.weatherCodes[368] = "Легкий снегопад";
            this.weatherCodes[365] = "Сильный дождь со снегом";
            this.weatherCodes[362] = "Дождь со снегом";
            this.weatherCodes[359] = "Проливной дождь";
            this.weatherCodes[356] = "Сильный дождь";
            this.weatherCodes[353] = "Дождь";
            this.weatherCodes[350] = "Град";
            this.weatherCodes[338] = "Сильный снег";
            this.weatherCodes[335] = "Местами сильные снегопады";
            this.weatherCodes[332] = "Умеренный снегопад";
            this.weatherCodes[329] = "Местами умеренный снегопад";
            this.weatherCodes[326] = "Легкий снегопад";
            this.weatherCodes[323] = "Местами легкий снегопад";
            this.weatherCodes[320] = "Сильный дождь со снегом";
            this.weatherCodes[317] = "Легкий дождь со снегом";
            this.weatherCodes[314] = "Сильный ледяной дождь";
            this.weatherCodes[311] = "Легкий ледяной дождь";
            this.weatherCodes[308] = "Сильный дождь";
            this.weatherCodes[305] = "Временами сильные дожди";
            this.weatherCodes[302] = "Умеренный дождь";
            this.weatherCodes[299] = "Временами умеренные дожди";
            this.weatherCodes[296] = "Легкий дождь";
            this.weatherCodes[293] = "Местами легкие дожди";
            this.weatherCodes[284] = "Сильные заморозки";
            this.weatherCodes[281] = "Заморозки";
            this.weatherCodes[266] = "Легкие заморозки";
            this.weatherCodes[263] = "Местами легкие заморозки";
            this.weatherCodes[260] = "Холодный туман";
            this.weatherCodes[248] = "Туман";
            this.weatherCodes[230] = "Метель";
            this.weatherCodes[227] = "Метель";
            this.weatherCodes[200] = "Грозовые вспышки";
            this.weatherCodes[185] = "Заморозки";
            this.weatherCodes[182] = "Дождь со снегом";
            this.weatherCodes[179] = "Снег";
            this.weatherCodes[176] = "Дождь";
            this.weatherCodes[143] = "Туман";
            this.weatherCodes[122] = "Сильная облачность";
            this.weatherCodes[119] = "Облачно";
            this.weatherCodes[116] = "Переменная облачность";
            this.weatherCodes[113] = "Ясно";
        },

        getWeather: function()
        {
            if (this.weatherLoading)
                return;
            
            this.weatherLoading = true;
            var weatherUrl = 'http://free.worldweatheronline.com/feed/weather.ashx?q='+ this.enCityName +'&format=json&num_of_days=5&key=' + this.serviceKey;
			var self = this;

            dojoIOScript.get({
                url: weatherUrl,
                content: {q: "#dojo"},
                callbackParamName: "callback"
            }).then(function(data){

                if (data == undefined) return;
                var w = data.data.current_condition[0];
                var result = {
                    today : {
                        weatherCode: w.weatherCode,
                        temperature: w.temp_C,
                        humidity:    w.humidity, // влажность
                        wind:        w.windspeedKmph,
                        windDir:     w.winddirDegree,
                        night:       w.weatherIconUrl[0].value.indexOf("_night.png") > 0
                    },

                    forecasts : []
                };

                var forecasts = data.data.weather;
                for (var i = 1; i < forecasts.length; i++)
                {
                    result.forecasts[i-1] =
                    {
                        day:  (new Date(forecasts[i].date)).getDay(),
                        low:  forecasts[i].tempMinC,
                        high: forecasts[i].tempMaxC,
                        weatherCode: forecasts[i].weatherCode
                    };
                };

                self.weather = result;
                self.weatherLoading = false;
                self.drawWeather();
                $('#waitWeatherWidget'+self.codename).hide();
                $('#weatherWidgetBlock'+self.codename).show();
            });
        },

        drawWeather: function()
        {
            var weather = this.weather;
            // 1.1 Текущая погода
            if (weather != undefined) {
                query("[panel='today']", this.panelView).forEach(lang.hitch(this, function(panel)
                {
                    this.setImageUrl("icon",        panel, this.formatImageUrl(this.formatIcon(weather.today.weatherCode, weather.today.night)));
                    this.setImageTitle("icon",      panel, this.formatCondition(weather.today.weatherCode));
                    this.setLabelText("temp",       panel, this.formatTemperature(weather.today.temperature));
                    this.setLabelText("condition",  panel, this.formatCondition(weather.today.weatherCode));
                    this.setLabelText("wind",       panel, this.formatWind(weather.today.wind, weather.today.windDir));
                    this.setLabelText("humidity",   panel, this.formatWindHumidity(weather.today.humidity));
                }));
            }

            // 1.2 Прогноз на следующие дни
            query("[panel='forecast']", this.panelView).forEach(lang.hitch(this, function(panel, index)
            {
                domStyle.set(panel, "display", (this.settings.size == "wide" ? "block" : "none"));
                if (weather != undefined) {
                    var forecast = weather.forecasts[index];
                    this.setImageUrl("icon",  panel, this.formatImageUrl(this.formatIcon(forecast.weatherCode)));
                    this.setImageTitle("icon",  panel, this.formatCondition(forecast.weatherCode));
                    this.setLabelText("day",  panel, this.formatDay(forecast.day));
                    this.setLabelText("low",  panel, this.formatTemperature(forecast.low));
                    this.setLabelText("high", panel, this.formatTemperature(forecast.high));
                }
            }));

            // 2. Выставляем значения для полей режима редактирования
            var city = this.editSettings.city;

            this.toggleNodeAttrib(this.fieldSize, "disabled", this.canResize());

            this.fieldSize.value = this.editSettings.size;

            this.fieldTitle.value = this.editSettings.title;

            this.fieldCity.innerHTML = (city != undefined) ? this.cityName : "выбрать";
            $('#cityCode'+this.codename).val(city);
            $('#enCityName'+this.codename).val(this.enCityName);
        },

        formatCondition: function(weatherCode)
        {
            if (trim(weatherCode) == "")
                return "";

            var code = parseInt(weatherCode);
            if (this.weatherCodes[code] != undefined)
                return this.weatherCodes[code];

            return "";
        },

        formatTemperature: function(temperature)
        {
            if (trim(temperature) == "")
                return "";

            var sign = "";
            if (temperature > 0) sign = "+";
            return sign + parseInt(temperature) + "°";
        },

        formatWind: function(wind, direction)
        {
            if (trim(wind) == "")
                return "";

            wind = parseInt(parseInt(wind)*10/36);
            return "Ветер: " + this.formatWindDirection(direction) + ", " + wind + "м/с";
        },

        formatWindDirection: function(direction)
        {
            if (trim(direction) == "")
                return "";

            var dir = parseInt(direction);
            if (dir < 20) return "С";
            if (dir < 35) return "ССВ";
            if (dir < 55) return "СВ";
            if (dir < 70) return "ВСВ";
            if (dir < 110) return "В";
            if (dir < 125) return "ВЮВ";
            if (dir < 145) return "ЮВ";
            if (dir < 160) return "ЮЮВ";
            if (dir < 200) return "Ю";
            if (dir < 215) return "ЮЮЗ";
            if (dir < 235) return "ЮЗ";
            if (dir < 250) return "ЗЮЗ";
            if (dir < 290) return "З";
            if (dir < 305) return "ЗСЗ";
            if (dir < 325) return "СЗ";
            if (dir < 340) return "ССЗ";
            return "С";
        },

        formatWindHumidity: function(humidity)
        {
            if (trim(humidity) == "")
                return "";

            return "Влажность: " + humidity + "%";
        },

        formatImageUrl: function(imageLocalPath)
        {
            return this.imageBaseURL + imageLocalPath;
        },

        formatIcon: function(weatherCode, night)
        {
            if (trim(weatherCode) == "")
                return "";

            var code = parseInt(weatherCode);
            if (this.weatherCodes[code] != undefined)
                if (night)
                    return code + "_night.png";
                else
                    return code + ".png";

            return "dunno.png";
        },

        formatDay: function(num)
        {
            return Date.abbrDayNames[num];
        },

        getHeightScrollableData: function(widgetViewHeight)
        {
            return this.weatherHeight();
        }
    });
});

// класс объекта окна хранимого в хеше окон
function windowItem (zIndex, element)
{
    // ссылка на самого себя
    this.element = element;
    // метод для получения текущего z-index окна и для его
    this.zIndex = function (newZIndex) {
        if (newZIndex != null)
            this.element.style.zIndex = newZIndex;
        else
            return parseInt(this.element.style.zIndex);
    };
    // метод определения состояния видимости окна
    this.opened = function () {
        return !($(this.element).hasClass("farAway"));
    };

    this.params = {
        closeCallback: null, // callback функция до закрытия окна
        onOpen: null, // Событие при открытии окна. Первым параметром будет идти id окна
        onAjaxLoad : null // событие после загрузки данных через ajax
    }
    // хранилище старых размеров окна (или текущих если они не изменились)
    this.height = 0;
    this.width = 0;
    // при создании объекта устанавливаем диво
    this.zIndex(zIndex);
    this.tinted = false;
}
// Класс оконного механизма
function Windows ()
{
    this.MIN_Z_INDEX = 30;
    // хеш объектов типа windowItem всех окон текущей страницы
    this.window = {};
    // текущее активное окно или null
    this.active = null;
    // интервал событий
    this.eventInterval = 0;
    //признак инициализации
    this.isInit = false;
    //позиционировать окно относительно всей страницы.
    this.globalWorkSpace = false;

    this.ajaxDataLoaded = false;

    // метод вызываемый событием по скролу главного окна
    this.scroll = function () {

        if (this.active == null ) return ;
        this.align();
    };
    // проверка на необходимость события onresize
    this.resizeEventCheck = function () {
     var elem = this.active;
     if (elem == null)
     {
         clearInterval(this.eventInterval);
         return ;
     }

     var old = this.window[elem.id];
     if (old.width == elem.offsetWidth && old.height == elem.offsetHeight ) return ;

     old.width = elem.offsetWidth;
     old.height = elem.offsetHeight;
     this.onresize();
    };

    /**
     * Загрузка ajax данных
     * @param id - id окна
     * @param url - url адрес данных
     * @param callBack function - которая вызовется после удачной загрузки данных
     */
    this.loadAjaxData = function (id, url, callBack) {
        if (this.ajaxDataLoaded)
            return;
        this.ajaxDataLoaded = true;
        // делаем замыкание для того, чтобы при быстром двойном клике не происходил еще один ajax-запрос
        // без завршения первого
        var self = this;
        ajaxQuery(null, url, function(data)
        {
            var opWindow =  $('#' + id);
            $(opWindow).empty();
            if (trim(data) == '')
            {
                self.ajaxDataLoaded = false;
                return;
            }
            $(opWindow).append($(data));

            var item = self.window[id + "Win"];
            if(item != null && item.params.onAjaxLoad != null)
            {
                if(item.params.onAjaxLoad(id, data) == false)
                    return;
            }

            if (callBack != null) callBack(id);
            self.ajaxDataLoaded = false;
        });
    };

    /**
     * Метод для затемнения всех окон
     * @param show флаг отвечающий за включение/выключения затемнения
     */
    this.tinedWindows = function (show) {
      if (show && this.active)
       clearTimeout(this.active.divFloat.floatTimer); // убиваем таймер скольжения
      // перебераем все окна на странице и устанавливаем/снимаем для них сетки
      for(var k in this.window)
        if (this.window[k].opened())
        {
            var content = findChildByClassName(this.window[k].element, "r-content");
            TintedNet.setTinted(content, show);
            this.window[k].tinted = !show;
        }

    };

    /**
     * Открыть окно
     * @param id string - наименование окна
     * @param ignoreEvents boolean - параметр отвечающий не выполнение событий. По умолчанию false
     * @param notCloseWindowId string - идентификатор окна, которое не следует закрывать.
     */
    this.open = function(id, ignoreEvents, notCloseWindowId) {
      if(!this.isInit)
            return;

      // берем элемент который проинициализировали
      var winItem = this.window[id + "Win"];
      if (winItem == undefined || winItem.element == undefined) {
          alert('Окно с ID "'+id+'" отсутствует');
          return;
      }

      var element = winItem.element;
      // проверяем что окно уже открыто
      if(this.active == element)
         return;

      if(this.active != null)
      {
          var classes = this.active.className.split(' ');
          var elemClasses = element.className.split(' ');
          for(var i in classes)
            if(classes[i]!='window' && classes[i] != notCloseWindowId)
                if(elemClasses.contains(classes[i]))
                {
                    this.close(this.active);
                    break;
                }
      }
      // если установлено событие на открытие
      if (winItem.params.onOpen != null && ignoreEvents != true)
        if (winItem.params.onOpen(id) == false) return;
      // Устанавливаем окно как текущее
      this.active = element;

      var myself = this;
      // Перебрать массив найти максимальный z-index  и увеличить его еще на 1
      var maxZ = this.MIN_Z_INDEX;
      for(var k in this.window)
        if (this.window[k].zIndex() > maxZ && this.window[k].opened())
            maxZ = this.window[k].zIndex();

      $(element).removeClass("farAway");
      // устанавливаем позицую
      this.positioning(element);
      winItem.zIndex(maxZ+11);
      // сохраняем текущие размеры окна
      winItem.width = element.offsetWidth;
      winItem.height = element.offsetWidth;
      // эмулируем событие onresize
      this.eventInterval = setInterval( function() { myself.resizeEventCheck(); } , 200);

      setTintedDiv(true, this.globalWorkSpace);
    };

    // Закрыть окно
    // @param: string id - наименование окна либо объект this по которому будет искаться окно родитель
    this.close = function(id) {
      var winItem;

      // получаем окно с учетом введенного параметра id
      if (typeof(id) == 'string')
        winItem = this.window[id + "Win"];
      else
      {
        // если первый параметр объект ищием родительское окно
        var elementWin = findParentByClassName(id, 'window');
        if (elementWin == document) {
            alert ("Родительское окно не найдено.");
            return ;
        }
        winItem = this.window[elementWin.id];
      }

      // проверка на оплошность программиста
      if (winItem == undefined) {
        alert('Окно с ID "'+id+'" отсутствует');
        return;
      }

      // проверка на установленую функцию closeCallback и если closeCallback возращает false то окно не закрываем
      if (winItem.params.closeCallback != null && !winItem.params.closeCallback())
          return ;

      var element = winItem.element;  
      element.style.zIndex = element.style.zIndex - 1;
      $(element).css('left', '');
      $(element).addClass("farAway");
      var maxZ = 0;
      this.active = null;
      // ищем открытые окна
      for(var k in this.window)
        if (this.window[k].zIndex() > maxZ && this.window[k].opened()) {
            maxZ = this.window[k].zIndex();
            this.active = this.window[k].element;
        }
      // если открытых окон не найдено выполняем необходимые процедуры
      if (this.active == null)
      {
        setTintedDiv(false, this.globalWorkSpace);
        clearInterval(this.eventInterval);
      }
    };

    // Инициализация оконного механизма.
    this.init = function(rootNode) {
      // получаем рабочую область
      var parent = getWorkSpace(this.globalWorkSpace);
      // вытаскиваем все элементы с класcом  window (все окна)
      var windows = findChildsByClassName(rootNode, 'window');
      var _self = this; // ссылка на себя для организации замыкания
      // заполняем Хеш window окнами и устанавливаем им дефолтные значения
      for (var i = 0; i < windows.length; i++)
      {
        // нет идентификатора, не знаем как к нему обращаться
        if((windows[i].id) == "" || windows[i].id == null)
           continue;

        var winElement = $(parent).children("#" + windows[i].id).get(0);
        // если есть уже это окно в прямых потомках
        if (winElement != null && winElement !== windows[i])
            $(winElement).replaceWith(windows[i]);
        else
        {
            if (parent)
            {
                parent.appendChild(windows[i]);
            }
        }

        this.window[windows[i].id] = new windowItem (this.MIN_Z_INDEX, windows[i]);
        windows[i].divFloat = new DivFloat(windows[i]);
      }

      addSwitchableEvent(window, "scroll", function () { _self.scroll(); }, "modalWindowScrollEvent");
      this.isInit = true;
    };

    this.setGlobalWorkSpace = function(globalWorkSpace, rootNode)
    {
        var lastParent = getWorkSpace(this.globalWorkSpace);
        this.globalWorkSpace = globalWorkSpace;
        // получаем рабочую область
        var parent = getWorkSpace(globalWorkSpace);
        // вытаскиваем все элементы с класcом  window (все окна)
        var windows = findChildsByClassName(lastParent, 'window');

        for (var i = 0; i < windows.length; i++)
        {
            // нет идентификатора, не знаем как к нему обращаться
            if((windows[i].id) == "" || windows[i].id == null)
               continue;

            var winElement = rootNode.getElementById(windows[i].id);
            // если есть уже это окно в прямых потомках
            lastParent.removeChild(windows[i]);
            parent.appendChild(windows[i]);
        }
    }

    // метод позиционирования окна
    this.positioning = function (obj) {
     var ws = getWorkSpace (this.globalWorkSpace);
     var objWidth = obj.offsetWidth;
     var marginLeft = ws.offsetWidth/2 - objWidth/2;
     var screen = screenSize();
     obj.style.marginLeft = marginLeft+"px";
     if (screen.h < obj.offsetHeight) obj.style.top = getScrollTop() + "px";
     else
        obj.style.top = workCenter(obj).topCenter - obj.offsetHeight/2 + "px";
    };

    // метод центровки окна с эфектом плавного скольжения
    this.align = function () {
     // не применяем эффект скольжения для мобильных устройств
     if (isMobileDevice()) return;

     var obj = this.active;
     clearTimeout(obj.divFloat.floatTimer);
     var screen = screenSize();
     var height = obj.offsetHeight;
     var fromPx = 0;
     var workSpace = workCenter(obj, this.globalWorkSpace);
     var topPx = Math.round(workSpace.topCenter - height / 2);
     if (obj.style.top != '')
         fromPx = parseInt(obj.style.top);
     if (topPx < workSpace.abs.top/2) topPx = Math.round(workSpace.abs.top/2);
     // если экран меньше окна, то необходимо произвести дополнительные расчеты.
     if (screen.h < height)
     {
         var padding = screen.h/10; // верха или низа должен быть 1/10 размера экрана
         if (fromPx < topPx) // скролл вниз
         {
             topPx = (screen.h + $(document).scrollTop()) - padding - height;
             if ( topPx < fromPx ) return; // направление скролла не должно измениться
         }
         else // скролл вверх
         {
             if ( topPx < fromPx && topPx < $(document).scrollTop() ) topPx = $(document).scrollTop() + padding;
             if ( topPx > fromPx ) return; // направление скролла не должно измениться
         }
     }

     obj.divFloat.floatEffect(fromPx, topPx);
    };

    // resize event
    this.onresize = function (){
       this.align();
    };

    // метод установки дополнительных атрибутов для окна
    // @param: id - наименование окна
    // @param: data - хеш с установками
    this.aditionalData = function (id, data)
    {
        if (this.window[id+"Win"] == undefined) return ;
        for (var k in data)
            if (k in this.window[id+"Win"].params) this.window[id+"Win"].params[k] = data[k];
    };

    /**
     * Получить сущность окна по идентификатору
     * @param id идентификатор
     * @returns сущность окна
     */
    this.getWinItem = function(id)
    {
        return this.window[id + "Win"];
    }
}
// резервируем глобальную переменную для окного механизма
var win = new Windows();
// инициализируем окна после полной загрузки DOM
$(document).ready( function () { win.init(document); } );

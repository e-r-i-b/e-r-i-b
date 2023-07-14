define(['marionette', 'backbone', 'underscore'], function(Mn, Backbone, _)
{
	/**
	 * Сервис предоставляет возможность работать с api яндекс карт
	 *
	 * @param   options
	 * @returns {{getInstance: Function}} возвращает ссылку на сервис
	 * @constructor
	 */
	function YaGeoService(options)
	{
		var _this    = this;
		this.enabled = false;

		if (options)
		{
			if (options.ymaps)
			{
				_this.ymaps   = options.ymaps;
				_this.enabled = true;
			}
		}

		return {

			getInstance : function()
			{
				return _this;
			}
		}
	}

	YaGeoService.prototype =
	{
		constructor : YaGeoService,

		/**
		 * @returns {boolean} доступно или нет выполнение запросов к картам. Требует доработки
		 */
		isEnabled : function()
		{
			return this.enabled && this.ymaps.geolocation;
		},

		getSearchResult : function(context, search, callback, failCallback)
		{
			if (this.isEnabled())
			{
				this.ymaps.geocode(search, {results : 5}).then(function(response)
				{
					var rawValues = [];

					response.geoObjects.each(function(object)
					{
						var metadata = object.properties.get('metaDataProperty').GeocoderMetaData;

						var rawValue =
						{
							origin       : search,
							name         : object.properties.get('name'),
							description  : object.properties.get('description'),
							coordinates  : object.geometry.getCoordinates(),
							bounds       : object.properties.get('boundedBy')
						};

						rawValues.push(_.extend(rawValue, metadata));
					});

					callback.call(context, rawValues);
				},
				function(error)
				{
					if (_.isFunction(failCallback))
					{
						failCallback.call(context);
					}
				});
			}

			return {};
		},

		/**
		 * Выполняет какую-либо функцию переданную в параметре callback в контексте context.
		 * Данная функция выполнится только если/когда(асинхронно) будут получены координаты текущего положения клиента.
		 *
		 * @param context
		 * @param callback
		 * @param failCallback
		 */
		getCurrentPosition : function(context, callback, failCallback)
		{
			if (this.isEnabled())
			{
				this.ymaps.geolocation.get().then(function(result)
				{
					var position = result['geoObjects'].position;
					var bounds   = result['geoObjects'].get(0)['properties'].get('boundedBy');

					callback.call(context,
					{
						llat  : bounds  [0][0],
						llon  : bounds  [0][1],
						rlat  : bounds  [1][0],
						rlon  : bounds  [1][1],
						cbLat : position[0],
						cbLon : position[1]
					});
				},
				function()
				{
					if (_.isFunction(failCallback))
					{
						failCallback.call(context);
						return;
					}

					throw new Error('Not a function');
				});
			}
		},

		/**
		 *
		 * @param startPoint
		 * @param endPoint
		 * @returns {*}
		 */
		getDistance : function(startPoint, endPoint)
		{
			if (this.isEnabled())
			{
				return this.ymaps.formatter.distance(this.ymaps.coordSystem.geo.getDistance(startPoint, endPoint));
			}

			return '';
		}
	};

	/**
	 *
	 * @returns {{getInstance: Function}}
	 * @constructor
	 */
	function SberbankUrlService(options)
	{
		// vars
		var _this = this;
		// defaults
		var _path =
		{
			service :
			{
				url        : options.serviceUrl || 'http://www.sberbank.ru/portalserver/proxy/',
				attributes : {}
			},

			proxy :
			{
				url        : 'http://oib-rs/oib-rs/%type/%unit?',
				type       : 'byBounds',
				unit       : 'filial',
				attributes : {}
			}
		};

		return {

			getInstance : function()
			{
				_this.path = _.clone(_path);
				return _this;
			}
		}
	}

	SberbankUrlService.prototype =
	{
		constructor : SberbankUrlService,

		appendServiceUrl : function(serviceUrl)
		{
			if (_.isString(serviceUrl))
			{
				this.path.service.url = serviceUrl;
			}

			return this;
		},

		appendServiceAttr : function(attributes)
		{
			if (_.isObject(attributes))
			{
				_.extend(this.path.service.attributes, attributes);
			}

			return this;
		},

		appendProxyUrl : function(proxyUrl)
		{
			if (_.isString(proxyUrl) && proxyUrl.length > 0)
			{
				this.path.proxy.url = proxyUrl;
			}

			return this;
		},

		appendProxyType : function(type)
		{
			if (_.isString(type))
			{
				this.path.proxy.type = type;
			}

			return this;
		},

		appendProxyUnit : function(unit)
		{
			if (_.isString(unit))
			{
				this.path.proxy.unit = unit;
			}

			return this;
		},

		appendProxyAttr : function(attributes)
		{
			if (_.isObject(attributes))
			{
				_.extend(this.path.proxy.attributes, attributes);
			}

			return this;
		},

		build : function(search)
		{
			if (document.debug && document.debug === true)
			{
				if (search)
				{
					return window.resourceRoot + '/scripts/test/byBoundsSearch.json';
				}

				return window.resourceRoot + '/scripts/test/%type.json'.replace('%type', this.path.proxy.type);
			}

			// Сначала proxy
			var proxyObj = _.clone(this.path.proxy);
			var proxyUrl = proxyObj.url;
				proxyUrl = proxyUrl.replace('%type', proxyObj.type);
				proxyUrl = proxyUrl.replace('%unit', proxyObj.unit);

			_.each(_.keys(proxyObj.attributes), function(key)
			{
				if (proxyUrl.endsWith('?') == false)
				{
					proxyUrl += '&';
				}

				proxyUrl += 'key=value'.replace('key', key).replace('value', proxyObj.attributes[key]);
			});

			proxyUrl = escape(proxyUrl).replace(/\//g, '%2F');

			// Теперь url банка
			var serviceObj = _.clone(this.path.service);
			var serviceUrl = serviceObj.url;

			if (serviceUrl.endsWith('/') == true)
			{
				serviceUrl += '?';
			}

			if (serviceUrl.endsWith('?') == false)
			{
				serviceUrl += '/?';
			}

			_.each(_.keys(serviceObj.attributes), function(key)
			{
				if (serviceUrl.endsWith('?') == false)
				{
					serviceUrl += '&';
				}

				serviceUrl += 'key=value'.replace('key', key).replace('value', serviceObj.attributes[key]);
			});

			return serviceUrl + '&url='.concat(proxyUrl);
		}
	};

	/**
	 * Модели данных.
	 *
	 * ВНИМАНИЕ! Вызов методов моделей напрямую(т.е. делать new Offices().set()) может привести к непредсказуемым последствиям.
	 * Сами потом баги ловить будете.
	 */
	// Отделение
	var Office  = Backbone.Model.extend({});

	// Список отделений
	var Offices = Backbone.Collection.extend
	({
		url        : '/Require/src/scripts/test/byBounds.json',
		model      : Office,
		geoService : null,
		urlService : null,

		// Вызыватся непосредственно при создании экземпляра
		initialize : function(models, options)
		{
			this.geoService = new YaGeoService(options);
			this.urlService = new SberbankUrlService(options);
		},

		// Вызывается в случае если сервер вернул что-то и это что-то валидный json
		set : function(collection, options)
		{
			var coordinates = options.current;

			collection = _.reject(collection, function(office)
			{
				return _.isEmpty(office);
			});

			_.each(collection, function(office)
			{
				var officeCoords = [
					office.coordinates.latitude,
					office.coordinates.longitude
				];

				var clientCoords = [
					coordinates.cbLat,
					coordinates.cbLon
				];

				_.extend(office,
				{
					distance : this.geoService.getInstance().getDistance(clientCoords, officeCoords)

				}, this);

			}, this);

			Backbone.Collection.prototype.set.call(this, collection, options);
		},

		fetch : function()
		{
			return this.fetchByConfig({});
		},

		fetchByModel : function(model)
		{
			if (model)
			{
				var config =
			    {
				    destination :
				    {
					    llat  : model.get('bounds')     [0][0],
					    llon  : model.get('bounds')     [0][1],
					    rlat  : model.get('bounds')     [1][0],
					    rlon  : model.get('bounds')     [1][1],
					    cbLat : model.get('coordinates')[0],
					    cbLon : model.get('coordinates')[1]
				    },

				    reset : true,
				    //TODO Потом убрать
				    search: true
			    };

				this.fetchByConfig(config);
			}
			else
			{
				this.fetchByConfig({});
			}
		},

		fetchByConfig : function(config)
		{
			this.geoService.getInstance().getCurrentPosition(this, function(result)
			{
				var local = _.extend
				({
					reset       : false,
					current     : result,
					destination : _.has(config, 'destination') ? config.destination : result,
					paging      :
					{
						page : 0,
						size : 10
					}

				}, config);

				var urlService = this.urlService.getInstance();
					urlService.appendProxyUnit('filial');
					urlService.appendProxyType('byBounds');
					urlService.appendProxyAttr(local.destination);
					urlService.appendProxyAttr
					({
						page : local.paging.page || 0,
						size : local.paging.size || 9
					});

					urlService.appendServiceAttr
					({
						pipe : 'branchesPipe'
					});

				// Формируем url до сервиса банка todo потом убрать
				this.url = urlService.build(_.has(config, 'search'));

				return Backbone.Collection.prototype.fetch.call(this, { current : local.current, reset : local.reset });
			},
			function()
			{
				this.trigger('geo:error');
			});

			return {};
		},

		// Компаратор для сортировки. В данном случае по расстоянию до отделения
		comparator : function(model)
		{
			return Number( model.get('distance').split('&')[0] );
		}
	});

	var SearchResult  = Backbone.Model.extend
	({
		initialize : function()
		{
			var value = this.get('name') + '&nbsp<em>%value</em>'.replace('%value', this.get('description'));

			_.each(this.get('origin').split(/\s+/), function(word)
			{
				if (word.trim() !== '')
				{
					var begin = value.search(new RegExp(word, 'gi'));
					var end   = begin + word.length;

					if (begin > -1)
					{
						var substring = value.substring(begin, end);

						value = value.replace(substring, ('<strong>' + substring + '</strong>'));
					}
				}
			});

			this.set('displayName', _.escape(value));
		}
	});

	var SearchResults = Backbone.Collection.extend
	({
		model : SearchResult,

		initialize : function(models, options)
		{
			this.geoService = new YaGeoService(options);

			// bind to events
			this.on('fetch:start', this.fetch);
		},

		fetch : function(value)
		{
			var _this = this;

			_this.geoService.getInstance().getSearchResult(_this, value, function(values)
			{
				this.set    (values);
				this.trigger('fetch:end');
			});

			return {};
		}
	});
	/**
	 * Описание отображаемых элементов
	 */
	var OfficeItemView = Mn.ItemView.extend
	({
		tagName   : 'tr',
		className : 'listTtl',
		template  : '#ymaps-departments-list-item',

		initialize : function()
		{
			var _this  = this;
			this.el.id = this.model.id;

			this.$el.click(function()
			{
				_this.trigger('on:office:select', this);
			})
		},

		ui :
		{
			showOnMap : '.showOnMap'
		},

		events :
		{
			'click @ui.showOnMap' : 'onShowOnMap'
		},

		onShowOnMap : function()
		{
			this.trigger('on:office:show', this);
		},

		isSelected : function()
		{
            return this.$el.hasClass('activeDep');
		}
	});

	var OfficesCompositeView = Mn.CompositeView.extend({

		template           : '#ymaps-departments-list-container-template',
		childViewContainer : 'tbody',
		childView          : OfficeItemView,

		childEvents :
		{
			'on:office:select' : 'onOfficeSelect',
			'on:office:show'   : 'onOfficeShow'
		},

		initialize : function(options)
		{
			this.collection = new Offices(null, options);

			this.listenTo(this.collection, 'geo:error',  this.onGeolocationError);
			this.listenTo(this.collection, 'sync error', this.onCollectionLoadComplete);

			this.on('on:offices:reload', this.onOfficesReload);
			this.on('on:shutdown',       this.onShutdown);
		},

		onBeforeRender : function()
		{
			this.collection.fetch();
		},

		onShutdown : function()
		{
			// Ничего не делаем (сейчас регион с вьюхой удаляется). возможно понадобится в будущем
		},

		onOfficesReload : function(model)
		{
			this.collection.fetchByModel(model);
		},

		onGeolocationError : function()
		{
			this.trigger('error:collection:loaded');
		},

		onCollectionLoadComplete : function(method)
		{
			// Если нужно будет отображать ошибки возникшие при загрузку - это сюда
			this.trigger(method.length > 0 ? 'collection:loaded' : 'empty:collection:loaded');
		},

		onOfficeShow : function(view)
		{
			this.trigger('on:office:show', view.model);
		},

		onOfficeSelect : function(source)
		{
			var newSel = this.children.findByModel(source.model);
			var oldSel = this.children.find(function(child)
			{
				return (child.isSelected() && child !== newSel);
			});

			if (newSel)
			{
                source.$el.addClass('activeDep');
			}

			if (oldSel)
			{
                oldSel.$el.removeClass('activeDep');
			}

			this.trigger('on:office:select');
		},

		getSelectedModel : function()
		{
			var selected = this.children.find(function(child)
			{
				return child.isSelected();
			});

			if (selected)
			{
				return selected.model;
			}
		}
	});

	var YMapsSearchItemView = Mn.ItemView.extend
	({
		tag       : 'div',
		template  : '#ymaps-search-item-view-template',

		initialize : function()
		{
			this.$el.on('click', this, function(event)
			{
				event.data.trigger('on:search:select', event.data);
				return false;
			});
		},

		onRender : function()
		{
			var element = this.$el.find('span');
			if (element)
			{
				element.html(element.text().replace(/'"/g, ''));
			}
		}
	});

	var YMapsSearchCollectionView = Mn.CompositeView.extend
	({
		template    : '#ymaps-search-collection-view-template',
		className   : 'autocomplete-suggestions',
		childView   : YMapsSearchItemView,

		childEvents :
		{
			'on:search:select' : 'onSearchSelect'
		},

		collectionEvents :
		{
			'fetch:end' : 'onSearchEnd'
		},

		initialize : function(options)
		{
			this.collection = new SearchResults(null, options);

			Mn.bindEntityEvents(this, this.collection, this.collectionEvents);

			this.on('on:search:start', this.onSearchStart, this);
		},

		onSearchStart : function(value)
		{
			this.collection.trigger('fetch:start', value);
		},

		onSearchEnd : function()
		{
			this.trigger('on:search:end');
		},

		onSearchSelect : function(view)
		{
			this.trigger('on:search:select', view);
		},

		isEmpty : function()
		{
			return this.children.length == 0;
		},

		getAny : function()
		{
			if (this.isEmpty())
			{
				return null;
			}

			return this.children.first();
		}
	});

	var YMapsSearchLayoutView = Mn.LayoutView.extend({

		el       : '#ymaps-search-view',
		template : false,

		regions :
		{
			searchResultRegion : '#search-result-region'
		},

		ui :
		{
			'search' : '#searchString',
			'find'   : '#ymaps-find-button'
		},

		events :
		{
			'click    @ui.find'   : 'onFind',
			'keyup    @ui.search' : 'onSearchStart',
			'focusin  @ui.search' : 'onSearchStart',
			'focusout @ui.search' : 'onSearchStart'
		},

		onBeforeRender : function()
		{
			var resultView = new YMapsSearchCollectionView(this.options);

			this.showChildView('searchResultRegion', resultView);

			this.listenTo(resultView, 'on:search:end',    this.onSearchEnd);
			this.listenTo(resultView, 'on:search:select', this.onSearchSelect);

			this.on('on:shutdown', this.onShutdown);
		},

		onShutdown : function()
		{
			// Просто отписываемся от всех событий
			this.unbindUIElements();
			this.undelegateEvents();
		},

		onFind : function()
		{
			var view = this.getResultView().getAny();
			if (view)
			{
				this.onSearchSelect(view);
			}
			else
			{
				this.onSearchSelect(null);
			}

			return false;
		},

		onSearchStart : function(event)
		{
			var _this = this;

			if (event.type === 'focusout')
			{
				if (!this.getResultView().isEmpty())
				{
					_this.hideSearchResult(true);
				}
				// Глушилка остальных обработчиков
				return false;
			}

			if ([35, 36, 37, 38, 39, 40].indexOf(event.keyCode) > -1)
			{
				return false;
			}

			// Если нажат escape сбрасываем все
			if (event.keyCode === 27)
			{
				// Говорим что ничего не выбрано и заставляем отображать список по-умолчанию
				this.onSearchSelect(null);
				// Глушилка остальных обработчиков
				return false;
			}

			var value = event.currentTarget.value;

			// Если нажат backspace
			if (event.keyCode === 8)
			{
				if (value.length == 0)
				{
					// Говорим что ничего не выбрано и заставляем отображать список по-умолчанию
					this.onSearchSelect(null);
					// Глушилка остальных обработчиков
					return false;
				}
			}

			this.getResultView().trigger('on:search:start', value);
			// Глушилка остальных обработчиков
			return false;
		},

		onSearchEnd : function()
		{
			if (this.getResultView().isEmpty())
			{
				// Ничего не найдено
				this.hideSearchResult(true);
			}
			else
			{
				this.hideSearchResult(false);
			}
		},

		onSearchSelect : function(view)
		{
			var search = '';

			// Передаем вышестоящему слушателю
			if (view)
			{
				var model = view.model;

				// Отправляем модель следующему слушателю
				this.trigger('on:search:select', model);

				// Формируем значение для строки поиска
				search = model.get('name') + ' ' + model.get('description');
			}
			else
			{
				// Отправляем модель следующему слушателю и говорим что нужен сброс(отобразить результаты по-умолчанию)
				this.trigger('on:search:select', null);
			}

			// Отображаем значение в строке поиска
			this.ui.search.val(search);

			// Прячем результаты поиска
			this.hideSearchResult(true);
		},

		getResultRegion : function()
		{
			return this.getRegion('searchResultRegion');
		},

		getResultView : function()
		{
			return this.getResultRegion().currentView;
		},

		hideSearchResult : function(hide)
		{
			if (hide)
			{
				var _this = this;

				// Дабы не перебивать другие события
				setTimeout(function()
				{
					_this.getResultRegion().$el.hide();

				}, 150);
			}
			else
			{
				this.getResultRegion().$el.show();
			}
		}
	});

	var YMapsView = Mn.LayoutView.extend
	({
		el       : '#depByMapDiv',
		template : false,

		ui :
		{
			'selectButton' : '.select-department',
			'showListTab'  : '#onList',
			'showMapTab'   : '#onMap',
			'LED'          : '.loadingDetector',
			'emptyScreen'  : '.emptyDepListShowHide',
			'errorScreen'  : '.redErrorType'
		},

		events :
		{
			'click @ui.selectButton' : 'onSelectButton',
			'click @ui.showListTab'  : 'onShowList',
			'click @ui.showMapTab'   : 'onShowMap'
		},

		regions :
		{
			ymapsMapRegion  : '#map',
			ymapsListRegion : '#ymaps-list-region'
		},

		childs : {},

		onRender : function()
		{
			if (this.isShowListTabEnabled())
			{
				this.onShowList();
			}
			else
			{
				this.onShowMap();
			}
		},

		isShowMapTabEnabled : function()
		{
			return this.ui.showMapTab.hasClass('activeButton');
		},

		isShowListTabEnabled : function()
		{
			return this.ui.showListTab.hasClass('activeButton');
		},

		onShowList : function()
		{
			this.ui.showMapTab.removeClass('activeButton');
			this.ui.showListTab.addClass  ('activeButton');

			// Добавляем новый регион
			this.regionManager.addRegion('ymapsListRegion', this.regions.ymapsListRegion);

			// ---------------------------------------------------------------
			this.childs.filialsView = new OfficesCompositeView(this.options);

			this.listenTo(this.childs.filialsView, 'before:render', function()
			{
				// Прячем пока не загрузятся данные
				this.getRegion('ymapsListRegion').$el.hide();

				// Отображаем индикатор загрузки
				this.ui.LED.show();

				this.ui.emptyScreen.hide();
				this.ui.errorScreen.hide();
			});

			this.listenTo(this.childs.filialsView, 'collection:loaded', function()
			{
				// Данные пришли
				// Скрываем индикатор загрузки
				this.ui.LED.hide();

				// Отображаем результат
				this.getRegion('ymapsListRegion').$el.show();
                calcCellsTblWidth();

				this.ui.emptyScreen.hide();
				this.ui.errorScreen.hide();
			});

			this.listenTo(this.childs.filialsView, 'empty:collection:loaded', function()
			{
				// Ничего не пришло :(
				this.getRegion('ymapsListRegion').$el.hide();

				// Скрываем индикатор загрузки
				this.ui.LED.hide();

				this.ui.emptyScreen.show();
				this.ui.errorScreen.hide();
			});

			this.listenTo(this.childs.filialsView, 'error:collection:loaded', function()
			{
				this.ui.LED.hide();

				this.ui.emptyScreen.hide();
				this.ui.errorScreen.show();
			});

			this.listenTo(this.childs.filialsView, 'on:office:select', function()
			{
				this.enableSelectDepartment();
			});

			this.listenTo(this.childs.filialsView, 'on:office:show', function(model)
			{
				var bridge = this.$el.find('#bridge');
					bridge.val(model.get('id'));
					bridge.trigger('click');

				this.ui.showMapTab.trigger('click');
			});

			/*
			 * Список офисов каждый раз добавляется заново поскольку при событии
			 * 'отображать как карту' регион со списком уничтожается
			 */
			this.getRegion('ymapsListRegion').show(this.childs.filialsView);
			// ---------------------------------------------------------------
			this.childs.searchView  = new YMapsSearchLayoutView(this.options);

			this.listenTo(this.childs.searchView, 'on:search:select', function(model)
			{
				// Прячем пока не загрузятся данные
				this.getRegion('ymapsListRegion').$el.hide();

				// Отображаем индикатор загрузки
				this.ui.LED.show();

				// Собссно отправляем запрос на обновление списка отделений
				this.childs.filialsView.trigger('on:offices:reload', model);
			});

			/*
			 * Для view описывающей элементы поиска нужна только привязка к элементам DOM`а
			 * которые не уничтожаются
			 */
			this.childs.searchView.render();
			// ---------------------------------------------------------------
		},

		onShowMap : function()
		{
			this.ui.showMapTab.addClass    ('activeButton');
			this.ui.showListTab.removeClass('activeButton');

			// Удаляем содержимое всего региона
			this.regionManager.removeRegion('ymapsListRegion');

			_.each(this.childs, function(child)
			{
				child.trigger('on:shutdown');
			});

			// Скрываем все что могло отображаться
			this.ui.LED.hide();
			this.ui.emptyScreen.hide();
			this.ui.errorScreen.hide();
		},

		enableSelectDepartment : function()
		{
			this.ui.selectButton.removeClass('disabled');
		},

		onSelectButton : function()
		{
			var selectButton = this.ui.selectButton;
			if (selectButton.hasClass('disabled'))
			{
				return false;
			}

			if (this.isShowListTabEnabled())
			{
				var model = this.getRegion('ymapsListRegion').currentView.getSelectedModel();

				// -----------------------------
				// Это конечно форменный трындец
				var officeCode = model.get('code');
				window.setOfficeInfo
				({
					name   : model.get('name'),
					region : officeCode.substring(0, 3),
					branch : officeCode.substring(3, 7),
					office : officeCode.substring(8, officeCode.length)
				});

				win.close('selectDepartment');
				// -----------------------------
			}

			return false;
		}
	});

	return {

		viewType : YMapsView
	}
});
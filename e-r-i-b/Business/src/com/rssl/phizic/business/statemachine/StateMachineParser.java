package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.doc.FormFilter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.statemachine.forms.Form;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author khudyakov
 * @ created 25.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class StateMachineParser
{
	private static final String NULL_HANDLER_FILTER_NAME = "com.rssl.common.forms.doc.NullHandlerFilter";

	/**
	 * Мапа машин состояний, полученных в процессе разбора
	 */
	private final Map<String, StateMachine> stateMachines = new HashMap<String, StateMachine>();

	/**
	 * Разбирает файл с описанием машин состояний
	 * @param stateMachineXmlFile - имя файлового ресурса с описанием машин состояний
	 * @return список разобранных машин состояний
	 */
	public final List<StateMachine> parse(String stateMachineXmlFile) throws BusinessException
	{
		stateMachines.clear();
		internalParse(stateMachineXmlFile);
		return new ArrayList<StateMachine>(stateMachines.values());
	}

	protected void internalParse(String stateMachineXmlFile) throws BusinessException
	{
		try
		{
			Document pfpStateMachine = XmlHelper.loadDocumentFromResource(stateMachineXmlFile);
			Element pfpRoot = pfpStateMachine.getDocumentElement();
			parseStateMachineConfig(pfpRoot);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	// парсинг машины состояния из документа
	protected void parseStateMachine(String stateMachineName, Element root) throws Exception
	{
		Element element = XmlHelper.selectSingleNode(root, "states");
		element.setAttribute("name", stateMachineName);
		parseStateMachine(element);
	}

	private void parseStateMachine(Element element) throws Exception
	{
		StateMachine machine = new StateMachine();
		String stateMachineName = element.getAttribute("name");
		machine.setName(stateMachineName);
		machine.setDescription(element.getAttribute("description"));
		machine.setSequencesHandlers(new HashMap<String, List<Handler>>());
		machine.setSequencesNextStates(new HashMap<String, List<ObjectState>>());
		machine.setSaveNodeInfo(Boolean.parseBoolean(element.getAttribute("saveNodeInfo")));
		stateMachines.put(stateMachineName, machine);

		//парсим цепочки хэндлеров
		Element sequences = XmlHelper.selectSingleNode(element, "sequences-handlers");
		parseSequenceHandlers(sequences, machine.getSequencesHandlers());

		// парсим цепочки next-state'ов
		Element sequencesNextStates = XmlHelper.selectSingleNode(element, "sequences-next-states");
		parseSequenceNextStates(sequencesNextStates, machine.getSequencesNextStates());

		Map<String , MachineState> states = parseMachineState(element);
		MachineState initalState = null;
		String initalStateCode = element.getAttribute("inital-state");
		for (String stateId : states.keySet())
		{
			if (stateId.equals(initalStateCode))
			{
				initalState = states.get(stateId);
			}
		}
		if (initalState == null)
		{
			throw new ConfigurationException("Не найдено начальное состояние для state-machine " + machine.getName());
		}
		machine.setStates(states);
	}

	protected void parseStateMachineConfig(Element root) throws Exception
	{
		//читаем описание машины состояний
		XmlHelper.foreach(root, "states", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				parseStateMachine(element);
			}
		});
	}

	private Map<String , MachineState> parseMachineState(Element element) throws Exception
	{
		//читаем возможные состояния машины состояний
		final Map<String , MachineState> states = new HashMap<String, MachineState>();
		XmlHelper.foreach(element, "state", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				MachineState state = new MachineState();

				state.setId(element.getAttribute("id"));
				state.setDescription(element.getAttribute("description"));
				state.setClientForm(element.getAttribute("client-form"));
				state.setEmployeeForm(element.getAttribute("employee-form"));
				state.setSystemResolver(element.getAttribute("system-resolver"));
				state.setForms(parseForms(XmlHelper.selectSingleNode(element, "forms")));
				state.setInitHandlers(parseHandlers(XmlHelper.selectSingleNode(element, "handlers")));
				state.setEvents(parseEvents(XmlHelper.selectSingleNode(element, "events")));
				states.put(state.getId(), state);
			}
		});
		return states;
	}

	private void parseSequenceHandlers(Element element, final Map<String, List<Handler>> sequences) throws Exception
	{
		if(element == null)
			return;

		XmlHelper.foreach(element, "sequence", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				if(sequences.containsKey(name))
					throw new ConfigurationException("Уже имеется sequence c именем " + name);

				sequences.put(name, parseHandlers(element));
			}
		});
	}

	private void parseSequenceNextStates(Element element, final Map<String, List<ObjectState>> sequences) throws Exception
	{
		if(element == null)
			return;

		XmlHelper.foreach(element, "sequence", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String name = element.getAttribute("name");
				if(sequences.containsKey(name))
					throw new ConfigurationException("Уже имеется sequence c именем " + name);

				sequences.put(name, parseEventStates(element, ""));
			}
		});
	}

	private List<Event> parseEvents(Element element) throws Exception
	{
		//читаем возможные события в сотоянии
		final List<Event> events = new ArrayList<Event>();

		if (element != null)
		{
			XmlHelper.foreach(element, "event", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Event event = new Event();
					event.setName(element.getAttribute("name"));
					event.setDescription(element.getAttribute("description"));
					Element nextStates = XmlHelper.selectSingleNode(element, "next-states");
					//устанавливаем дефолтный статус для события
					ObjectState defaultState = new ObjectState();
					defaultState.setState(nextStates.getAttribute("default"));
					defaultState.setStateHandlers(parseHandlers(XmlHelper.selectSingleNode(nextStates, "handlers")));
					event.setDefaultState(defaultState);
					//сетим список возможных статусов для события с классами, реализующими
					// проверку условий установки данного статуса
					event.setNextStates(parseEventStates(nextStates, ""));
					event.setPostNextStates(parseEventStates(XmlHelper.selectSingleNode(element, "post-next-states"), "post-"));
					event.setType(element.getAttribute("type"));
					event.setLock(Boolean.parseBoolean(element.getAttribute("lock")));
                    event.setHandlers(parseHandlers(XmlHelper.selectSingleNode(element, "handlers")));

					events.add(event);
				}
			});
		}
		return events;
	}

	private List<ObjectState> parseEventStates(Element element, String prefix) throws Exception
	{
		final List<ObjectState> states = new ArrayList<ObjectState>();
		if(element != null)
		{
			final String tag = prefix + "next-state";
			XmlHelper.foreach(element, tag + "|sequence-next-states-ref", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String name = element.getNodeName();

					if(tag.equals(name))
					{
						ObjectState state = new ObjectState();
						state.setState(element.getAttribute("state"));
						state.setStateCondition(element.getAttribute("condition"));
						state.setEnabledSource(parseSource(XmlHelper.selectSingleNode(element, "enabled")));
						state.setStateHandlers(parseHandlers(XmlHelper.selectSingleNode(element,"handlers")));
						state.setClientMessage(element.getAttribute("client-message"));
						state.setDescription(element.getAttribute("description"));
						states.add(state);
					}
					else if("sequence-next-states-ref".equals(name))
					{
						Element stateMachineElement = getStateMachineNode(element);
						StateMachine stateMachie = stateMachines.get(stateMachineElement.getAttribute("name"));
						List<ObjectState> objectStates = stateMachie.getSequencesNextStates().get(element.getAttribute("name"));
						if(objectStates == null)
							throw new ConfigurationException("Не найдена последовательность хэндлеров с именем " + element.getAttribute("name"));

						states.addAll(objectStates);
					}
				}
			});
		}
		return states;
	}

	private List<Handler> parseHandlers(Element element) throws Exception
	{
		//читаем хендлеры
		final List<Handler> handlers = new ArrayList<Handler>();

		if (element != null)
		{
			XmlHelper.foreach(element, "handler|sequence-handlers-ref", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String name = element.getNodeName();

					if("handler".equals(name))
					{
						Handler handler = new Handler();
						handler.setClassName(element.getAttribute("class"));
						handler.setParameters(parseParameters("parameter", element));
						handler.setEnabledSource(parseSource(XmlHelper.selectSingleNode(element, "enabled")));
						handlers.add(handler);
					}
					else if("sequence-handlers-ref".equals(name))
					{
						Element stateMachineElement = getStateMachineNode(element);
						StateMachine stateMachine = stateMachines.get(stateMachineElement.getAttribute("name"));
						List<Handler> sequenceHandlers = stateMachine.getSequencesHandlers().get(element.getAttribute("name"));
						if(handlers == null)
							throw new ConfigurationException("Не найдена последовательность хэндлеров с именем " + element.getAttribute("name"));

						handlers.addAll(sequenceHandlers);
					}
				}
			});
		}
		return handlers;
	}

	private List<Form> parseForms(Element element) throws Exception
	{
		//читаем хендлеры
		final List<Form> forms = new ArrayList<Form>();
		if (element == null)
		{
			return forms;
		}

		XmlHelper.foreach(element, "form", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				forms.add(new Form(element.getAttribute("url"), Application.valueOf(element.getAttribute("application")), parseFilters(element)));
			}
		});
		return forms;
	}

	private List<FormFilter> parseFilters(Element element) throws Exception
	{
		//читаем параметры
		final List<FormFilter> filters = new ArrayList<FormFilter>();
		XmlHelper.foreach(element, "filters/filter", new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				filters.add(parseFilter(element));
			}
		});
		return filters;
	}

	private FormFilter parseFilter(Element element) throws Exception
	{
		FormFilter filter = ClassHelper.newInstance(element.getAttribute("class"));
		filter.setParameters(parseParameters("parameters/parameter", element));
		return filter;
	}

	/**
	 * Ищем корневой элемент машины состояния
	 * @param children элемент, относительно которого ищем
	 * @return элемент states
	 */
	private Element getStateMachineNode(Element children)
	{
		if ("states".equals(children.getNodeName()))
			return children;

		Node parent = children.getParentNode();
		if(parent == null)
			throw new ConfigurationException("Не найден корневой элемент машины состояния");

		 return getStateMachineNode((Element) parent);

	}

	private EnabledSource parseSource(Element element) throws Exception
	{
		//условие вкл./выкл. хендлера
		EnabledSource source = new EnabledSource();

        if (element != null)
		{
			source.setClassName( element.getAttribute("class"));
			source.setParameters(parseParameters("parameter", element));
		}
		else
        {
            source.setClassName(NULL_HANDLER_FILTER_NAME);
            source.setParameters(new HashMap<String, String>());
        }
		return source;
	}

	private Map<String, String> parseParameters(String xPath, Element element) throws Exception
	{
		//читаем параметры
		final Map<String, String> parameters = new HashMap<String, String>();

		XmlHelper.foreach(element, xPath, new ForeachElementAction()
		{
			public void execute(Element element)
			{
				parameters.put(element.getAttribute("name"), element.getAttribute("value"));
			}
		});
		return parameters;
	}
}

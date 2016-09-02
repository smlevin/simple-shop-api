package ru.checkout.shop.api.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.checkout.shop.api.config.ContextMain;

import java.io.File;
import java.net.InetSocketAddress;

/**
 * Application entry point.
 */
@Service("shopApiServer")
@Lazy
public class ShopApiServer {
	private static final Log log = LogFactory.getLog(ShopApiServer.class);

	private Server server;

	@Value("${shop.server.log.path:/data/logs/web/yyyy_mm_dd.request.log}")
	private String logPath;

	@Value("${shop.server.bind.host:localhost}")
	private String bindHost;

	@Value("${shop.server.bind.port:5555}")
	private int bindPort;

	@Value("${shop.server.context.path:/}")
	private String contextPath;

	@Value("${webserver.request.log.retain.days:30}")
	private int requestLogRetainDays;

	public void start() throws Exception {
		server = new Server(new InetSocketAddress(bindHost, bindPort));
		server.setStopAtShutdown(true);
		server.setHandler(createHandlers());
		server.start();
		log.info("Checkout shop server has been started");
	}

	private Handler createHandlers() throws Exception {
		HandlerCollection result = new HandlerCollection();

		WebAppContext webAppContext = createWebContext();
		result.addHandler(webAppContext);

		RequestLogHandler logHandler = new RequestLogHandler();
		logHandler.setRequestLog(createRequestLog());
		result.addHandler(logHandler);

		return result;
	}

	private RequestLog createRequestLog() {
		NCSARequestLog requestLog = new NCSARequestLog();

		File logPathFile = new File(logPath);
		logPathFile.getParentFile().mkdirs();

		requestLog.setFilename(logPathFile.getPath());
		requestLog.setRetainDays(requestLogRetainDays);
		requestLog.setExtended(false);
		requestLog.setAppend(true);
		requestLog.setLogTimeZone("GMT");
		requestLog.setLogLatency(true);
		return requestLog;
	}

	public WebAppContext createWebContext() throws Exception {
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath(contextPath);
		//webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		//webAppContext.setResourceBase("src/main/webapp");
		//webAppContext.setParentLoaderPriority(true);
		webAppContext.setWar(getClass().getResource("/META-INF/webapp/").toString());
		//webAppContext.setMaxFormContentSize(maxFormContentSize);
		return webAppContext;
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ContextMain(args).startContext();
		context.getBean(ShopApiServer.class).start();
	}
}

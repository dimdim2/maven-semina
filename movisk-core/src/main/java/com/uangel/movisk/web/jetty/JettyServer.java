package com.uangel.movisk.web.jetty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppClassLoader;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.util.Scanner;

public class JettyServer {

	private Integer port;
	private String webapp;
	private String webappConfiguration;
	private List<File> classpaths;
	private Server server;
	private WebAppContext webApp;

	public JettyServer(Integer port, String webapp, String webappConfiguration, String... requiredClasspaths) {
		this.port = port;
		this.webapp = webapp;
		this.webappConfiguration = webappConfiguration;
		this.classpaths = new ArrayList<File>();
		for (String each : requiredClasspaths) {
			classpaths.add(new File(each));
		}
	}

	public void start() throws Exception {
		server = new Server();

		prepareServerConfiguration();
		prepareHttpConnector();
		prepareWebAppContext();
		prepareReloadable();

		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

	private void prepareServerConfiguration() {
		server.setStopAtShutdown(true);
	}

	private void prepareReloadable() {
		Scanner scanner = new Scanner();
		scanner.setReportExistingFilesOnStartup(false);
		scanner.setScanInterval(3);
		scanner.setScanDirs(classpaths);
		scanner.setRecursive(true);
		scanner.addListener(new ChangeClassListener(webApp, classpaths));
		scanner.start();
	}

	private void prepareWebAppContext() throws IOException {
		webApp = new WebAppContext(webapp, "/");
		webApp.setDefaultsDescriptor(webappConfiguration);
		server.addHandler(webApp);

		DefaultHandler defaultHandler = new DefaultHandler();
		defaultHandler.setServeIcon(false);
		server.addHandler(defaultHandler);
	}

	private void prepareHttpConnector() {
		SelectChannelConnector httpConnector = new SelectChannelConnector();
		httpConnector.setForwarded(true);
		httpConnector.setPort(port);
		server.addConnector(httpConnector);
	}

	@Override
	public String toString() {
		return "WebServer [classpaths=" + classpaths + ", port=" + port + "]";
	}

	static class ChangeClassListener implements Scanner.BulkListener {

		private WebAppContext webApp;
		private List<File> classpaths;

		public ChangeClassListener(WebAppContext webApp, List<File> classpaths) {
			super();
			this.webApp = webApp;
			this.classpaths = classpaths;
		}

		@SuppressWarnings("rawtypes")
		public void filesChanged(List changes) throws Exception {
			try {
				webApp.stop();
				webApp.setClassLoader(null);
				webApp.setServerClasses(new String[] { "org.mortbay.jetty.plus.jaas.", "org.mortbay.jetty." });
				WebAppClassLoader loader = new WebAppClassLoader(webApp);
				for (File each : classpaths) {
					loader.addClassPath(each.getCanonicalPath());
				}

				webApp.setClassLoader(loader);

				webApp.start();
			} catch (Exception e) {
				System.out.println("Error reconfiguring/restarting webapp after change in watched files");
				e.printStackTrace();
			}
		}

	}

}

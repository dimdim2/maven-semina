package com.uangel.movisk.svc;

import com.uangel.movisk.web.jetty.JettyServer;

public class JettyServerLauncher {

	public static void main(String[] args) throws Exception {
		final Integer port = 7070;
		final String webapp = "webapp";
		final String webappConfiguration = "src/main/java/com/uangel/movisk/svc/jetty-web.xml";
		final String coreTargetClasses = "../movisk-core/target/classes";
		final String targetClasses = "target/classes";
		final String webXml = webapp + "/WEB-INF/web.xml";

		JettyServer server = new JettyServer(port, webapp, webappConfiguration, coreTargetClasses, targetClasses, webXml);
		server.start();
	}
}

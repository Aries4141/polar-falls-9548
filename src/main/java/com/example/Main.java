package com.example;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.TagLibConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

/**
 * 
 * This class launches the web application in an embedded Jetty container. This
 * is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String webappDirLocation = "src/main/webapp/";

		// The port that we should run on can be set into an environment
		// variable
		// Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		Server server = new Server(Integer.valueOf(webPort));

		WebAppContext root = new WebAppContext();
		root.setAttribute(
				"org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				".*/build/classes/");

		root.setConfigurations(new Configuration[] {
				new AnnotationConfiguration() , new WebXmlConfiguration()/*,
				new WebInfConfiguration(), new TagLibConfiguration(),
				new PlusConfiguration(), new MetaInfConfiguration(),
				new FragmentConfiguration(), new EnvConfiguration() */});

		root.setContextPath("/");
		root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
		root.setResourceBase(webappDirLocation);
		root.setConfigurationDiscovered(true);
		
		// Parent loader priority is a class loader setting that Jetty accepts.
		// By default Jetty will behave like most web containers in that it will
		// allow your application to replace non-server libraries that are part
		// of the
		// container. Setting parent loader priority to true changes this
		// behavior.
		// Read more here:
		// http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
		root.setParentLoaderPriority(true);

		server.setHandler(root);

		server.start();
		server.join();
	}

}

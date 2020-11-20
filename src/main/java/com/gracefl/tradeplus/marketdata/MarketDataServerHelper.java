package com.gracefl.tradeplus.marketdata;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

//@Service
//@Transactional
//@Controller
//public class MarketDataServerHelper implements ServletContextAware {
public class MarketDataServerHelper {

    public void initMarketDataServer(ServletContext servletContext) {
        try {
            // We don't want to include H2 when we are packaging for the "prod" profile and won't
            // actually need it, so we have to load / invoke things at runtime through reflection.
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> servletClass = Class.forName("com.gracefl.tradeplus.marketdata.MarketDataServerWebServlet", true, loader);
            Servlet servlet = (Servlet) servletClass.getDeclaredConstructor().newInstance();

            ServletRegistration.Dynamic marketDataServerServlet = servletContext.addServlet("marketDataServer", servlet);
            //marketDataServer.addMapping("/marketDataServer/*");
            //marketDataServer.setInitParameter("-properties", "src/main/resources/");
            marketDataServerServlet.setLoadOnStartup(1);

        } catch (ClassNotFoundException | LinkageError | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Failed to load and initialize com.gracefl.tradeplus.marketdata.MarketDataServerWebServlet", e);

        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Failed to instantiate com.gracefl.tradeplus.marketdata.MarketDataServerWebServlet", e);
        }
    }


}

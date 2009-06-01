package org.thoughtworks.iokebot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.wave.api.*;  

import ioke.lang.Message;
import ioke.lang.Runtime;
import ioke.lang.IokeObject;
import ioke.lang.exceptions.ControlFlow;

import ioke.lang.ext.ikanserve.IokeApplicationFactory;
import ioke.lang.ext.ikanserve.IokeApplication;
import ioke.lang.ext.ikanserve.IokeServletContextListener;

public class IokebotServlet extends AbstractRobotServlet {
    private ServletContext sc;
    
    private static String myParticipantId = "iokebot@appspot.com";

    @Override
    public void init() {
        sc = getServletConfig().getServletContext();
    }

    private IokeApplicationFactory getIokeFactory() {
        return (IokeApplicationFactory)sc.getAttribute(IokeServletContextListener.FACTORY_KEY);
    }

    private HttpServletRequest request;

    @Override
    public void service(final HttpServletRequest request, final HttpServletResponse response) 
        throws ServletException, IOException {
        this.request = request;
        super.service(request, response);
    }        

    @Override
    public void processEvents(RobotMessageBundle events) {
        final IokeApplicationFactory factory = getIokeFactory();
        IokeApplication app = null;
        try {
            app = factory.getApplication();
            Runtime r = app.getRuntime();
            r.evaluateString("use(\"iokebot.ik\")", r.message, r.ground);
            Object server = r.evaluateString("IokeBot mimic", r.message, r.ground);
            IokeObject.setCell(server, null, null, "events", r.registry.wrap(events));
            IokeObject.setCell(server, null, null, "request", r.registry.wrap(request));
            IokeObject.setCell(server, null, null, "servletContext", r.registry.wrap(sc));
            IokeObject msg = r.newMessage("dispatch");
            ((Message)IokeObject.data(msg)).sendTo(msg, r.ground, server);
        } catch(ControlFlow cf) {
            sc.log("controlflow while dispatching application: " + cf);
        } finally {
            if (app != null) {
                factory.finishedWithApplication(app);
            }
        }
    }
    
}


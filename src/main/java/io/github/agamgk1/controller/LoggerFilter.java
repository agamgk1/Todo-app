package io.github.agamgk1.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//klasa filtra
// musi byc metoda doFilter wywołana na łańcuch inaczej apikacja nie pójdzie dalej tylko zatrzyma sie na tym filtrze
@Component
public class LoggerFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            var httpRequest = (HttpServletRequest) request;
            logger.info("[do filter]"+httpRequest.getMethod()+" "+ httpRequest.getRequestURI());
        }
        chain.doFilter(request,response);
        logger.info("[doFilter 2]");

    }
}

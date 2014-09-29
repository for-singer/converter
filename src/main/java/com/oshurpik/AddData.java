package com.oshurpik;

import com.oshurpik.ejb.RateBean;
import com.oshurpik.entity.ExchangeRate;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;


@Component("addData")
public class AddData implements HttpRequestHandler {
    
    @Autowired
    private RateBean rateBean;
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<ExchangeRate> list = rateBean.addData();
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddData</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddData at " + request.getContextPath() + " has been added</h1>");
            
            for (ExchangeRate er : list) {
                out.println(er.toString());
                out.println("<br>");
            }
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
}

package com.oshurpik;

import com.oshurpik.ejb.ConverterBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("converter")
public class Converter implements HttpRequestHandler {

    @Autowired
    private ConverterBean converterBean;    
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String amount = request.getParameter("amount");
        String fromCurrency = request.getParameter("from_currency");
        String toCurrency = request.getParameter("to_currency");

        String errorMessage = converterBean.validateParams(amount, fromCurrency, toCurrency);

        String result = new String();
        boolean hasError = false;
        
        if (errorMessage == null) {
            result = converterBean.convert(fromCurrency, toCurrency, Double.parseDouble(amount));
        }
        else {
            hasError = true;
            result = String.valueOf(errorMessage);
        }
        
        printResponse(response, result, hasError);
    }
    
    private void printResponse(HttpServletResponse response, String result, boolean  hasError)
			throws IOException {
        PrintWriter out = response.getWriter();
        
        try {
            response.setContentType("text/html;charset=UTF-8");            
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Currency Converter</title>");            
            out.println("</head>");
            out.println("<body>");
            if (!hasError) {
                out.println("<h3>Amount: " + result + "</h3>");
            }
            else {
                out.println("<h3>Error: " + result + "</h3>");
            }
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

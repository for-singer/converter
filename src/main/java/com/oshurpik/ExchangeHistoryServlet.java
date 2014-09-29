package com.oshurpik;

import com.oshurpik.ejb.ExchangeHistoryBean;
import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeHistory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Component("exchangeHistory")
public class ExchangeHistoryServlet implements HttpRequestHandler {
    
    @Autowired
    private ExchangeHistoryBean exchangeHistoryBean;
   
        
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<ExchangeHistory>  exchangeHistoryAll = exchangeHistoryBean.list();
        List<ExchangeHistory>  exchangeHistoryForFromCurrency = exchangeHistoryBean.findByFromCurrencyName(request.getParameter("from_currency"));
        List exchangeHistoryNumberOfTransationsForEachFromCurrency = exchangeHistoryBean.getNumberOfTransationsForEachFromCurrency();
        List exchangeHistoryMaxNumberOfTransationsForFromCurrency = exchangeHistoryBean.getFromCurrencyWithMaxTransactionCount();
        

        PrintWriter out = response.getWriter();
        
        
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Currency Converter</title>");            
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h3>All records:</h3>");
            if (exchangeHistoryAll.size() > 0) {
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>From currency</th>");
                out.println("<th>To currency</th>");
                out.println("<th>Amount</th>");
                out.println("<th>Trans date</th>");
                out.println("</tr>");
                for(ExchangeHistory item : exchangeHistoryAll) {
                    out.println("<tr>");
                    out.println("<td>" + item.getFromCurrency().getName() + "</td>");
                    out.println("<td>" + item.getToCurrency().getName() + "</td>");
                    out.println("<td>" + item.getAmount() + "</td>");
                    out.println("<td>" + item.getTransDate() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            else {
                out.println("<p>Data is empty</p>");
            }
            
            
            out.println("<h3>Records for url param 'from_currency':</h3>");
            if (exchangeHistoryForFromCurrency.size() > 0) {
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>From currency</th>");
                out.println("<th>To currency</th>");
                out.println("<th>Amount</th>");
                out.println("<th>Trans date</th>");
                out.println("</tr>");
                for(ExchangeHistory item : exchangeHistoryForFromCurrency) {
                    out.println("<tr>");
                    out.println("<td>" + item.getFromCurrency().getName() + "</td>");
                    out.println("<td>" + item.getToCurrency().getName() + "</td>");
                    out.println("<td>" + item.getAmount() + "</td>");
                    out.println("<td>" + item.getTransDate() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            else {
                out.println("<p>Data is empty for param 'from_currency'='" + request.getParameter("from_currency") + "'</p>");
            }
            
            
            out.println("<h3>Number Of transations for each from currency:</h3>");
            if (exchangeHistoryNumberOfTransationsForEachFromCurrency.size() > 0) {
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>From currency</th>");
                out.println("<th>To currency</th>");
                out.println("<th>Amount</th>");
                out.println("</tr>");
                for(Object item : exchangeHistoryNumberOfTransationsForEachFromCurrency) {
                    out.println("<tr>");
                    out.println("<td>" + ((Currency)((Object[])((Object)item))[0]).getName() + "</td>");
                    out.println("<td>" + ((Currency)((Object[])((Object)item))[1]).getName() + "</td>");
                    out.println("<td>" + ((Object[])((Object)item))[2] + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            else {
                out.println("<p>Data is empty</p>");
            }
            
            out.println("<h3>Currency with max transaction count:</h3>");
            if (exchangeHistoryMaxNumberOfTransationsForFromCurrency.size() > 0) {
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>From currency</th>");
                out.println("<th>To currency</th>");
                out.println("<th>Amount</th>");
                out.println("</tr>");
                for(Object item : exchangeHistoryMaxNumberOfTransationsForFromCurrency) {
                    out.println("<tr>");
                    out.println("<td>" + ((Currency)((Object[])((Object)item))[0]).getName() + "</td>");
                    out.println("<td>" + ((Currency)((Object[])((Object)item))[1]).getName() + "</td>");
                    out.println("<td>" + ((Object[])((Object)item))[2] + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            else {
                out.println("<p>Data is empty</p>");
            }
//            }
//            else {
//                out.println("<h3>Error: " + result + "</h3>");
//            }
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }
}

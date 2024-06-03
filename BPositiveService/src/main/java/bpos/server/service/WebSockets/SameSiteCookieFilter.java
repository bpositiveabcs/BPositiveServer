package bpos.server.service.WebSockets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class SameSiteCookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        addSameSiteCookieAttribute((HttpServletResponse) response);
    }

    private void addSameSiteCookieAttribute(HttpServletResponse response) {
        for (String header : response.getHeaders("Set-Cookie")) {
            if (header.startsWith("JSESSIONID")) {
                response.setHeader("Set-Cookie", String.format("%s; %s", header, "SameSite=None; Secure"));
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No specific initialization required
    }

    @Override
    public void destroy() {
        // No specific cleanup required
    }
}

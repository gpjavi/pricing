package com.pricing.infrastructure.config.spring.rest.logging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
@AllArgsConstructor
@Slf4j
@Configuration
@ConditionalOnProperty(
    prefix = "logging.custom",
    name = "access-logging-filter",
    havingValue = "true", matchIfMissing = true
)
public class AccessLogFilter implements Filter {

  private final ObjectMapper objectMapper;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    var requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) req);

    long startTime = System.currentTimeMillis();
    chain.doFilter(requestWrapper, res);
    long time = System.currentTimeMillis() - startTime;

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    var accessLogSB = new StringBuilder();

    accessLogSB.append(request.getMethod());
    accessLogSB.append(" ").append(request.getRequestURI());

    if (null != request.getQueryString()) {
      accessLogSB.append(" ").append(request.getQueryString());
    }
    accessLogSB.append(" status: ").append(response.getStatus());
    accessLogSB.append(" time: ").append(time);
    accessLogSB.append(" client: ").append(request.getRemoteAddr());

    if (requestWrapper.getContentAsByteArray().length > 0) {
      try {
        var jsonNode = objectMapper.readValue(requestWrapper.getContentAsByteArray(),
            JsonNode.class);
        accessLogSB.append(" body: ").append(jsonNode.toString());
      } catch (Exception e) {
        var requestBody = new String(requestWrapper.getContentAsByteArray());
        accessLogSB.append(" body: malformed json ").append(requestBody);
      }
    }

    log.info(accessLogSB.toString());
  }

  @Override
  public void init(FilterConfig filterConfig) {
    //Implementation not necessary.
  }

  @Override
  public void destroy() {
    //Implementation not necessary.
  }
}

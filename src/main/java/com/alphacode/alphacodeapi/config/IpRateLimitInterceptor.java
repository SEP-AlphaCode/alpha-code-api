package com.alphacode.alphacodeapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class IpRateLimitInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    // config
    private static final int MAX_REQUESTS = 20;      // tối đa 20 request
    private static final int WINDOW_SECONDS = 60;    // trong 60 giây

    public IpRateLimitInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        String key = "rate-limit:" + ip;

        // tăng counter trong Redis
        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            // lần đầu thì set TTL cho key
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
        }

        if (count != null && count > MAX_REQUESTS) {
            response.setStatus(429);
            response.getWriter().write("Too many requests from IP: " + ip + ". Please try again later.");
            return false;
        }

        return true; // cho phép request
    }
}

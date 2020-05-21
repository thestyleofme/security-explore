package org.abigballofmud.security.distributed.zuul.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

/**
 * <p>
 * ZuulFilter
 * </p>
 *
 * @author isacc 2020/5/20 0:56
 * @since 1.0
 */
@Component
public class AuthZuulFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
        // 获取令牌内容
        RequestContext ctx = RequestContext.getCurrentContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2Authentication)) {
            // 无token访问网关内资源 目前仅oauth服务直接暴露
            return null;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = Optional.ofNullable(oAuth2Authentication.getUserAuthentication())
                .orElseThrow(() -> new IllegalStateException("Authentication is null"));
        // 用户身份
        String principal = userAuthentication.getName();
        // 用户权限
        List<String> authorities = userAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        HashMap<String, Object> jsonToken = new HashMap<>(requestParameters);
        jsonToken.put("principal", principal);
        jsonToken.put("authorities", authorities);
        // 组装明文token，转发给微服务，放入header，名称未json-token
        ctx.addZuulRequestHeader("json-token",
                Base64Utils.encodeToString(new ObjectMapper().writeValueAsString(jsonToken).getBytes()));
        return null;
    }
}

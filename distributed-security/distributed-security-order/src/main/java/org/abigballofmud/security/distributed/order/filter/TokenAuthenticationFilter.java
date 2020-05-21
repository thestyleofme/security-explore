package org.abigballofmud.security.distributed.order.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.abigballofmud.security.distributed.common.model.UserDTO;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>
 * TokenAuthenticationFilter
 * </p>
 *
 * @author isacc 2020/5/21 1:48
 * @since 1.0
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public TokenAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("json-token");
        if (!StringUtils.isEmpty(token)) {
            String json = new String(Base64Utils.decodeFromString(token), StandardCharsets.UTF_8);
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            String[] authorities = (String[]) ((List) map.get("authorities")).toArray(new String[0]);
            // UserDTO userDTO = UserDTO.builder()
            //         .username(String.valueOf(map.get("principal"))).build();
            UserDTO userDTO = objectMapper.readValue(String.valueOf(map.get("principal")), UserDTO.class);
            // 将用户信息和权限填充到用户身份token对象中
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDTO,
                            null,
                            AuthorityUtils.createAuthorityList(authorities));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(httpServletRequest));
            // 将authenticationToken填充到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

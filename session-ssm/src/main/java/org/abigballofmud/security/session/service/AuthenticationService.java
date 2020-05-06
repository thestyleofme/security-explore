package org.abigballofmud.security.session.service;

import org.abigballofmud.security.session.model.AuthenticationRequest;
import org.abigballofmud.security.session.model.UserDTO;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 1:02
 * @since 1.0
 */
public interface AuthenticationService {

    /**
     * 用户认证
     *
     * @param authenticationRequest AuthenticationRequest
     * @return UserDTO
     * @author isacc 2019/12/10 1:04
     */
    UserDTO authentication(AuthenticationRequest authenticationRequest);
}

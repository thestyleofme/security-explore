package org.abigballofmud.security.session.model;

import lombok.Data;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 1:04
 * @since 1.0
 */
@Data
public class AuthenticationRequest {

    private String username;

    private String password;
}

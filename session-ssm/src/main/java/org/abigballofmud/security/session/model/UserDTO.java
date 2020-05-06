package org.abigballofmud.security.session.model;

import java.util.Set;

import lombok.*;

/**
 * <p>
 * description
 * </p>
 *
 * @author isacc 2019/12/10 1:05
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO {

    //=================用户身份信息================

    private String id;
    private String username;
    private String password;
    private String fullName;
    private String mobile;

    /**
     * 用户权限
     */
    private Set<String> authorities;

}

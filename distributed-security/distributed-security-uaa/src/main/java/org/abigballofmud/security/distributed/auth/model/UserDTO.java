package org.abigballofmud.security.distributed.auth.model;

import lombok.*;

/**
 * <p>
 * UserDTO
 * </p>
 *
 * @author isacc 2020/5/13 1:36
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO {

    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}

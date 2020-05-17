package org.abigballofmud.security.distributed.auth.model;

import lombok.*;

/**
 * <p>
 * PermissionDTO
 * </p>
 *
 * @author isacc 2020/5/14 1:28
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PermissionDTO {

    private String id;
    private String code;
    private String description;
    private String url;
}

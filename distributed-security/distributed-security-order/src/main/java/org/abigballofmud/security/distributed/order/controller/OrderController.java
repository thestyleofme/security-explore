package org.abigballofmud.security.distributed.order.controller;

import org.abigballofmud.security.distributed.common.model.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * OrderController
 * </p>
 *
 * @author isacc 2020/5/18 2:32
 * @since 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1() {
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDTO.getFullname() + " r1";
    }
}

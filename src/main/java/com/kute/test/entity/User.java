package com.kute.test.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.security.Principal;

/**
 * created by bailong001 on 2019/12/14 19:00
 */
@Data
@Accessors(chain = true)
public class User implements Principal {

    private String name;

    @Override
    public String getName() {
        return name;
    }

}

package com.kute.test.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * created by bailong001 on 2019/12/14 11:39
 */
@Data
@Accessors(chain = true)
public class HelloMessage implements Serializable {
    private String name;
    private String content;
}

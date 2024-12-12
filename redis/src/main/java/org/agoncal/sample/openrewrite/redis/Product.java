
package org.agoncal.sample.openrewrite.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RedisHash("product")
public class Product implements Serializable {
    private String id;
    private String name;

}
package io.uouo.wechat.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * KeyItem
 *
 * @author biezhi
 * @since 2018/1/19
 */
@Data
public class KeyItem implements Serializable {

    @SerializedName("Key")
    private Integer key;

    @SerializedName("Val")
    private Integer val;
}

package io.uouo.wechat.api.request;

import io.uouo.wechat.api.response.FileResponse;

/**
 * 下载文件请求
 *
 * @author biezhi
 * @since 2018/1/18
 */
public class FileRequest extends ApiRequest<FileRequest, FileResponse> {

    public FileRequest(String url) {
        super(url, FileResponse.class);
    }

}

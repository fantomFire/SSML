package zhonghuass.ssml.http;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private T data;
    private String status;
    private String msg;


    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (status.equals(Api.RequestSuccess)) {
            return true;
        } else {
            return false;
        }
    }
    public String getMessage(){
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
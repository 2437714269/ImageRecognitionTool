package com.acer.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 统一返回对象
 * @author acer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRestResponse<T> {
    private Integer status;

    private String msg;

    private T data;

    public ApiRestResponse (Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }



    /**
     * 无返回值返回
     * @param <T> 类型
     * @return ApiRestResponse对象
     */
    public static <T> ApiRestResponse<T> success() {
        ApiRestResponse apiRestResponse = new ApiRestResponse<>();
        apiRestResponse.setStatus(200);
        apiRestResponse.setMsg("成功");
        return apiRestResponse;
    }

    /**
     * 请求成功带返回值
     * @param result 返回值中的data信息
     * @param <T> 类型
     * @return ApiRestResponse对象
     */
    public static <T> ApiRestResponse<T> success(Integer status,String msg,T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setStatus(status);
        response.setMsg(msg);
        response.setData(result);
        return response;
    }

    /**
     * 失败时返回
     * @param code 状态码
     * @param msg  错误信息
     * @return ApiRestResponse对象
     */
    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse(code, msg);
    }

}



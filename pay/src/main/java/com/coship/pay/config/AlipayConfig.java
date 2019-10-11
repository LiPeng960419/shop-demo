package com.coship.pay.config;

/**
 * @Author: lipeng 910138
 * @Date: 2019/10/10 20:54
 */
public class AlipayConfig {

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101300675271";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+8Gs/tMHZaLMW3RZ/X8DucbZpP2DLu+cUFdMf/itq+oKEvT9aXTTEH4Q0KlU/mcc+enVKVG9Dsyeoy5zCEVyGEDhwOYgNd7BrrKHusJj9wgUOT6RWYarKcOCoZ8Uc18CovYICfWsND1oeUQcAtIlZVE9sH1cAhM/Orawbn/VLQC2Vfk2oTOqEsCadgAKAbm7iT3QPkHmJIHJTZfpDyY1uLQQayNhCnfBOpafDt4a85U6oYti4mON9bDYiCrcGf13r+2/vD+hd9xzkatJ6amEsNPOeP6tq3Vgh3l16/BQy3cvAWfHHGNIF/pagukfoPDbdKNQHhftttEgSlVgkGbn1AgMBAAECggEAE9N4/2tweXeyqNwaGu3BZcDu9Oy8y0reiqpdesP0lC4g4VGZQ3PMJ9Ux8Ay5P1pjYuxqQ2aTCw+OPauuwkP1PIJwqc5/fw5/hh6tzdAOgsLjrYG9ia6ogk7INRRZVk5c1eifmEyXkgqXC/DT7lHQtTLY8Fwst4QS494Kse+hE8gPGArNSbllvcTtssiS2jhS1HGLLh6VSu+Vha+brPqiHd0TGwu/aCUuaeD4AwR1D5uDqdlZuQSLmLqm2b4LXR1Kqvq0l50YxoElshXg43KPbPvJ3Cwt2O78LLh2TUEO/4YD+PX/TZAGs5bwaPRck0QFtWJthTgP34X3QPILbFslPQKBgQDyW5sXhSlhm9CDMON5UXJXxp7NIa5XCVH1qk5cWAdE/7Pa5E1vxGZthTcOYQjBYDsseYe+NUrK3ss0YrDzrFXjw0U8BNdNA1xXutbhIqp+UHgloJwZIbN87DOG98XstAB1TkbuVkJv2/uBnhmIJNdWPlCec2gNHh7oFg3zIwRDpwKBgQDJr92rQ6dfj0bPkAnovpvlRW77leaFTk3ZWDSlPBCQt6LE6jup9G63E1b6cazZcsNdrulqQI7kKvpBf10utkRrvZhjAJ3J1DGA6X2PaD9iERLiO0IPqsEoTYgdSQnUQnKEfh0V5A2i6+p6n89z0t7qbIEy/wJj2XbzAaJHTrV5AwKBgQC/V+/EzA91zuz7BvgxakpaXqg10Geys9sPmBwx8BYdNbysDx/MJEWyJkDni1i8Q9u0K2culyirrubEPz7xw5g0MJFS54eMeCDJ6uuH6DSvcZzKKFnVxbPN5zZvUuPjE+JguyJpPZVKd2XEWixSPrP8lUpVL0xxPtyr4+2zjmvpdQKBgEHK+BH9gOUgdSYSw9jEDVEOhHTX4ppMAANNzea1IGZFMXjxPENl9JQOYApU+OD6mha+j3BU3Qg7qOWi6b/qP4myELsDIXYMnZWDFa2pgTu5fCKG2DcKKfa+zA9ZmzY98uObzFsuyPAD67rS1TbgbE2m1cLQA3SVJUOCnfCLEZIHAoGACayrQ8yIeLT+AeR5Xo98fmSYdgEmjeSy8ep6i8wlkBe2gnAwXbumWcC9IIcRTHOP6anyaRpJox5gEl1nlhG8qk8GD5X+dMeZWo5Gvt9whyeKmMWgqzxlIbazjZRLuck69EWOdL2nB2RXloJkdHZk7ErtiJaxLHyNsK8S0uwJK4I=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMLY/WfXQUeTCdcMfPVs/qYNNOlZLPrSzMTmUORO+fNX5lzjoEBaPe0UE/yE0AY1aAVchDc92kgUToVxVwWP3jtnSuiuu/EMR9PV7bqvSI0G0BGBOemNSwwuBtmHic+o5GFFXNPs1Gy+kh/0HfiMIZW7fZcMv7isQwRFEK72f5opk96/86kh5c7Or0o2ly/48BQqBuWsHi36KsuNeyJQsvPPSvOmFDNjhUz7gLxoJd3wpRDYMjZOkDPb0cwCUtku0wXxkQ1jXOFoYGzoS1PTLwcEMxo5so6xkJRM0AbsOIY8rHLtwwgMFkDqWRQfVGn05E3ojBpjmsCq9LucjQET2wIDAQAB";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://309412cc.ngrok.io/callBack/asynCallBack";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://309412cc.ngrok.io/callBack/synCallBack";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}
package com.myp.baseFile;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.myp.utils.security.RSAUtil;
import com.myp.utils.security.AESHelper;
import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseService {

    public final Logger logger = LogManager.getLogger("com.myp.controllers");

    public void dealException(Exception e,String fileName,int lineNumber){
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        String errorStr = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>错误开始<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n====文件:"+fileName+"\n====行数："+lineNumber+"\n====错误信息："+buffer.toString()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>错误结束<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n\n\n";

        logger.error(errorStr);

    }

    //进行验签
    public boolean checkIdentity(BaseParam param){
        //原始文本为 com=bianla&timestamp=时间戳
        //时间戳由前端发送过来
        String finalString = "com=wql&timestamp="+param.getTimestamp();
        System.out.println("签名："+param.getSignature());
        String sign = param.getSignature();
        System.out.println("param:"+param.getEncryptedData());
        boolean success = RSAUtil.verifyIdentify(finalString,sign);
        return success;
    }

    // AES加密数据直接解析成对象
    public <T> T decryptedAESDataToEntity(BaseParam param , Class<T> clazz) {

        String dataStr = AESHelper.decryptString(param.getEncryptedData());
        return JSON.parseObject(dataStr,clazz);
    }
}

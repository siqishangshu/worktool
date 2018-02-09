package cn.mysic.crypt;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-09 13:35:00
 */

public class RSAUtil {
    private static String src = "rsa security";
    public static void main(String[] args) {
        jdkRSA();
    }

    public static void jdkRSA(){
        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] res = signature.sign();
            System.out.println("签名:"+HexBin.encode(res));

            //3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(res);
            System.out.println("验证："+bool);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

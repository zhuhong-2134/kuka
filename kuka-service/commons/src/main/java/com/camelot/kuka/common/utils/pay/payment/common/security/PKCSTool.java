package com.camelot.kuka.common.utils.pay.payment.common.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.PKCS9Attributes;
import sun.security.pkcs.SignerInfo;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * JVM 提供商：SUN和IBM，
 * 支持PKCS1/PKCS7签名和验签
 * 签名算法仅支持：SHA1withRSA
 *
 */
public class PKCSTool {
	/** 签名 */
	private static final int SIGNER = 1;
	/** 验证 */
	private static final int VERIFIER = 2;
	/** 用途 */
	private int mode = 0;
	/** 摘要算法 */
	private String digestAlgorithm = "SHA1";
	/** 签名算法 */
	private String signingAlgorithm = "SHA1withRSA";
	/** 签名证书链 */
	private Certificate[] certificates = null;
	/** 签名私钥 */
	private PrivateKey privateKey = null;
	/** 根证书 */
	private Certificate rootCertificate = null;
	/** JVM 提供商 */
	private static char jvm = 0;
	private static Class algorithmId = null;
	private static Class derValue = null;
	private static Class objectIdentifier = null;
	private static Class x500Name = null;
	private static boolean debug = false;

	/**
	 * 私有构造方法
	 */
	private PKCSTool(int mode) {
		this.mode = mode;
	}
	
	private static void init() {
		if (jvm != 0)
			return;
		String vendor = System.getProperty("java.vm.vendor");
		if (vendor == null)
			vendor = "";
		String vendorUC = vendor.toUpperCase();
		try {
			if (vendorUC.indexOf("IBM") >= 0) {
				jvm = 'I';
				algorithmId = Class
						.forName("com.ibm.security.x509.AlgorithmId");
				derValue = Class.forName("com.ibm.security.util.DerValue");
				objectIdentifier = Class
						.forName("com.ibm.security.util.ObjectIdentifier");
				x500Name = Class.forName("com.ibm.security.x509.X500Name");
			} 
			else if (vendorUC.indexOf("ORACLE CORPORATION") >= 0){
				jvm = 'S';
				algorithmId = Class.forName("sun.security.x509.AlgorithmId");
				derValue = Class.forName("sun.security.util.DerValue");
				objectIdentifier = Class.forName("sun.security.util.ObjectIdentifier");
				x500Name = Class.forName("sun.security.x509.X500Name");
			}
			else {
				jvm = 'S';
				algorithmId = Class.forName("sun.security.x509.AlgorithmId");
				derValue = Class.forName("sun.security.util.DerValue");
				objectIdentifier = Class
						.forName("sun.security.util.ObjectIdentifier");
				x500Name = Class.forName("sun.security.x509.X500Name");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Not support JRE: " + vendor);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 匹配私钥用法
	 * 
	 * @param keyUsage
	 * @param usage
	 * @return
	 */
	private static boolean matchUsage(boolean[] keyUsage, int usage) {
		if (usage == 0 || keyUsage == null)
			return true;
		for (int i = 0; i < Math.min(keyUsage.length, 32); i++) {
			if ((usage & (1 << i)) != 0 && !keyUsage[i])
				return false;
		}
		return true;
	}

	/**
	 * 取得签名工具 加载证书库, 取得签名证书链和私钥
	 * 
	 * @param keyStorePath
	 *            证书库路径
	 * @param keyStorePassword
	 *            证书库口令
	 * @param keyPassword
	 * 			  签名私钥口令，与证书库口令相同
	 * @param pkcsType
	 * 			     签名方法,P1签名时：null；P7签名时：PKCS7
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PKCSTool getSigner(String keyStorePath,
                                     String keyStorePassword, String keyPassword, String pkcsType)
			throws GeneralSecurityException, IOException {
		init();
		// 加载证书库
		KeyStore keyStore = null;
		FileInputStream fis = null;
		try {
			if (keyStorePath.toLowerCase().endsWith(".pfx")) {
				keyStore = KeyStore.getInstance("PKCS12");
			}
			fis = new FileInputStream(keyStorePath);
			keyStore.load(fis, keyStorePassword.toCharArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				fis.close();
		}
		// 在证书库中找到签名私钥，并验证合法性
		Enumeration aliases = keyStore.aliases();
		String keyAlias = null;
		if (aliases != null) {
			while (aliases.hasMoreElements()) {
				keyAlias = (String) aliases.nextElement();
				Certificate[] certs = keyStore.getCertificateChain(keyAlias);
				if (certs == null || certs.length == 0)
					continue;
				X509Certificate cert = (X509Certificate) certs[0];
				if (matchUsage(cert.getKeyUsage(), 1)) {
					try {
						cert.checkValidity();
					} catch (CertificateException e) {
						continue;
					}
					break;
				}
			}
		}
		// 没有找到可用签名私钥
		if (keyAlias == null)
			throw new GeneralSecurityException(
					"None certificate for sign in this keystore");
		PKCSTool tool = new PKCSTool(SIGNER);
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
				keyPassword.toCharArray());
		// 没有私钥抛异常
		if (privateKey == null) {
			throw new GeneralSecurityException(keyAlias
					+ " could not be accessed");
		}
		tool.privateKey = privateKey;
		if ("PKCS7".equals(pkcsType)) {
			X509Certificate[] certificates = null;
			if (keyStore.isKeyEntry(keyAlias)) {
				// 检查证书链
				Certificate[] certs = keyStore.getCertificateChain(keyAlias);
				for (int i = 0; i < certs.length; i++) {
					if (!(certs[i] instanceof X509Certificate))
						throw new GeneralSecurityException("Certificate[" + i
								+ "] in chain '" + keyAlias
								+ "' is not a X509Certificate.");
				}
				// 转换证书链
				certificates = new X509Certificate[certs.length];
				for (int i = 0; i < certs.length; i++)
					certificates[i] = (X509Certificate) certs[i];
			} else if (keyStore.isCertificateEntry(keyAlias)) {
				// 只有单张证书
				Certificate cert = keyStore.getCertificate(keyAlias);
				if (cert instanceof X509Certificate) {
					certificates = new X509Certificate[] { (X509Certificate) cert };
				}
			} else {
				throw new GeneralSecurityException(keyAlias
						+ " is unknown to this keystore");
			}
			tool.certificates = certificates;
		}
		return tool;
	}

	/**
	 * P1签名
	 * 
	 * @param data 签名原文对应的byte数组，签名原文请使用UTF-8字符集
	 * @return signature 签名结果，签名结果为Base64编码
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public String p1Sign(byte[] data) throws Exception {
		if (mode != SIGNER)
			throw new IllegalStateException(
					"call a PKCS1Tool instance not for signature.");
		Signature signer = Signature.getInstance(signingAlgorithm);
		signer.initSign(privateKey);
		signer.update(data, 0, data.length);
		byte[] signedAttributes = signer.sign();
		return (new BASE64Encoder()).encode(signedAttributes);
	}

	/**
	 * P7签名
	 * 
	 * @param data 签名原文对应的byte数组，签名原文请使用UTF-8字符集
	 * @return signature 签名结果，签名结果为Base64编码
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public String p7Sign(byte[] data) throws Exception {
		if (mode != SIGNER)
			throw new IllegalStateException(
					"call a PKCS7Tool instance not for signature.");
		Signature signer = Signature.getInstance(signingAlgorithm);
		signer.initSign(privateKey);
		signer.update(data, 0, data.length);
		byte[] signedAttributes = signer.sign();
		ContentInfo contentInfo = null;
		Field data_oidField = ContentInfo.class.getField("DATA_OID");
		Object data_oid = data_oidField.get(null);
		Constructor contentInfoConstructor = ContentInfo.class
				.getConstructor(new Class[] { data_oid.getClass(), derValue });
		contentInfo = (ContentInfo) contentInfoConstructor
				.newInstance(new Object[] { data_oid, null });
		// 根证书
		X509Certificate x509 = (X509Certificate) certificates[certificates.length - 1];
		BigInteger serial = x509.getSerialNumber();
		// X500Name
		Constructor x500NameConstructor = x500Name
				.getConstructor(new Class[] { String.class });
		Object x500NameObject = x500NameConstructor
				.newInstance(new Object[] { x509.getIssuerDN().getName() });
		// AlgorithmId
		Method algorithmIdGet = algorithmId.getMethod("get",
				new Class[] { String.class });
		Object digestAlgorithmId = algorithmIdGet.invoke(null,
				new Object[] { digestAlgorithm });
		Field algorithmIdfield = algorithmId.getField("RSAEncryption_oid");
		Object rsaOid = algorithmIdfield.get(null);
		Constructor algorithmConstructor = algorithmId
				.getConstructor(new Class[] { objectIdentifier });
		Object algorithmRsaOid = algorithmConstructor
				.newInstance(new Object[] { rsaOid });
		// SignerInfo
		Constructor signerInfoConstructor = SignerInfo.class
				.getConstructor(new Class[] { x500Name, BigInteger.class,
						algorithmId, PKCS9Attributes.class, algorithmId,
						byte[].class, PKCS9Attributes.class });
		// 签名信息
		SignerInfo si = (SignerInfo) signerInfoConstructor
				.newInstance(new Object[] { x500NameObject,// X500Name,
															// issuerName,
						serial, // x509.getSerialNumber(), BigInteger serial,
						digestAlgorithmId, // AlgorithmId, digestAlgorithmId,
						null, // PKCS9Attributes, authenticatedAttributes,
						algorithmRsaOid, // AlgorithmId,
											// digestEncryptionAlgorithmId,
						signedAttributes, // byte[] encryptedDigest,
						null // PKCS9Attributes unauthenticatedAttributes)
				});

		SignerInfo[] signerInfos = { si };

		// 构造PKCS7数据
		Object digestAlgorithmIds = Array.newInstance(algorithmId, 1);
		Array.set(digestAlgorithmIds, 0, digestAlgorithmId);
		// PKCS7
		Constructor pkcs7Constructor = PKCS7.class.getConstructor(new Class[] {
				digestAlgorithmIds.getClass(), ContentInfo.class,
				X509Certificate[].class, signerInfos.getClass() });
		PKCS7 p7 = (PKCS7) pkcs7Constructor.newInstance(new Object[] {
				digestAlgorithmIds, contentInfo, certificates, signerInfos });
		ByteArrayOutputStream baout = new ByteArrayOutputStream();
		p7.encodeSignedData(baout);
		// Base64编码
		return (new BASE64Encoder()).encode(baout.toByteArray());
	}

	/**
	 * 取得验签名工具 加载信任根证书和验签用公钥证书
	 * 验签用的摘要算法：SHA1
	 * @param rootCertStresm
	 *            根证书输入流，P1验签时该参数为null
	 * @param verifyCertStream
	 *            签名公钥证书输入流
	 * @param digestAlgorithm
	 * 			      
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PKCSTool getVerifier(InputStream rootCertStream,
                                       InputStream verifyCertStream)
			throws GeneralSecurityException, IOException {
		init();
		Certificate[] rootCertificates = null;
		Certificate[] verifyCertificates = null;
		CertificateFactory certificatefactory = CertificateFactory
				.getInstance("X.509");
		try {
			if (verifyCertStream != null) {
				verifyCertificates = new Certificate[] { certificatefactory
						.generateCertificate(verifyCertStream) };
			}
			if (rootCertStream != null) {
				rootCertificates = new Certificate[] { certificatefactory
						.generateCertificate(rootCertStream) };
			}
		} catch (Exception exception) {
			if (debug)
				exception.printStackTrace();
			if (rootCertStream != null) {
				InputStream is = new ByteArrayInputStream(
						new BASE64Decoder().decodeBuffer(rootCertStream));
				rootCertificates = new Certificate[] { certificatefactory
						.generateCertificate(is) };
			}
			if (verifyCertStream != null) {
				InputStream signIs = new ByteArrayInputStream(
						new BASE64Decoder().decodeBuffer(verifyCertStream));
				verifyCertificates = new Certificate[] { certificatefactory
						.generateCertificate(signIs) };
			}
		}
		PKCSTool tool = new PKCSTool(VERIFIER);
		if (rootCertStream != null) {
			tool.rootCertificate = rootCertificates[0];
		}
		tool.certificates = verifyCertificates;
		return tool;
	}

	/**
	 * P1验证签名(无CRL)
	 * 
	 * @param signature
	 *            签名结果,签名结果为Base64编码
	 * @param data
	 *            签名原文对应的byte数组 ，签名原文请使用UTF-8字符集
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 * @throws CertificateException
	 * @throws NoSuchProviderException
	 */
	public void p1Verify(String signature, byte[] data)
			throws IOException, NoSuchAlgorithmException, SignatureException,
			InvalidKeyException, CertificateException, NoSuchProviderException {
		if (mode != VERIFIER)
			throw new IllegalStateException(
					"call a PKCS1Tool instance not for verify.");
		byte[] sign = new BASE64Decoder().decodeBuffer(signature);
		Signature verify = Signature.getInstance(signingAlgorithm);
		verify.initVerify(certificates[0]);
		verify.update(data);
		boolean result = verify.verify(sign, 0, sign.length);
		if (result == false)
			throw new SignatureException("Verify fail.");
		X509Certificate cert = (X509Certificate) certificates[0];
		// 证书是否过期验证
		cert.checkValidity();
		if (cert.equals(rootCertificate)) {
			// 验证证书签名
			cert.verify(rootCertificate.getPublicKey());
		}
	}

	/**
	 * P7验证签名(无CRL)
	 * 
	 * @param signature
	 *            签名结果，签名结果为Base64编码
	 * @param data
	 *            签名原文对应的byte数组，签名原文请使用UTF-8字符集
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 * @throws CertificateException
	 * @throws NoSuchProviderException
	 */
	public void p7Verify(String signature, byte[] data)
			throws IOException, NoSuchAlgorithmException, SignatureException,
			InvalidKeyException, CertificateException, NoSuchProviderException {
		if (mode != VERIFIER)
			throw new IllegalStateException(
					"call a PKCS7Tool instance not for verify.");
		byte[] sign = new BASE64Decoder().decodeBuffer(signature);
		PKCS7 p7 = new PKCS7(sign);
		X509Certificate[] certs = p7.getCertificates();
		if (debug)
			for (int i = 0; i < certs.length; i++) {
				X509Certificate cert = certs[i];
				System.out.println("SIGNER " + i + "=\n" + cert);
				System.out.println("SIGNER " + i + "=\n"
						+ new BASE64Encoder().encode(cert.getEncoded()));
			}

		// 验证签名本身、证书用法、证书扩展
		SignerInfo[] sis = p7.verify(data);
		if (sis == null)
			throw new SignatureException(
					"Signature failed verification, data has been tampered");
		//验证签名结果
		for (int i = 0; i < sis.length; i++) {
			SignerInfo si = sis[i];
			X509Certificate cert = si.getCertificate(p7);
			// 证书是否过期验证，如果不用系统日期可用cert.checkValidity(date);
			cert.checkValidity();
			if (!cert.equals(rootCertificate)) {
				// 验证证书签名
				cert.verify(rootCertificate.getPublicKey());
			}
		}
	}
}

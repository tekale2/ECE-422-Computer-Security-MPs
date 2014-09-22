package my_new;
// include necessary files
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.GeneralSecurityException;
import java.nio.charset.Charset;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class my_class {
	  public static void main(String[] args) throws IOException {
	        // get a handle for necessary files 
		  	File ciphe = new File("aes_weak_ciphertext.hex");
	        FileInputStream cipe = null;
	        File ket = new File("aes_key.hex");
	        FileInputStream kety = null;
	        File IVV = new File("aes_IV.hex");
		    FileInputStream Ic = null;
		    InputStreamReader sr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(sr);
			
		    try {

		      //String key = "";
		     // byte[] ciphertext = encrypt(key, "1234567890123456");
		     // String text = "";
			 // convert to string and then convert string data to raw hex bytes
		    	cipe = new FileInputStream(ciphe);
		      byte [] ciphertext = new byte[(int)ciphe.length()];
		      cipe.read(ciphertext);
		      	kety = new FileInputStream(ket);
		      byte [] key = new byte [(int)ket.length()];
		      kety.read(key);
		      Ic = new FileInputStream(IVV);
		      byte [] my = new byte[(int)IVV.length()];
				 Ic.read(my);
		      String keyr = new String(key); 
		      String temp2 = new String(ciphertext); 
		      byte [] cypher = hexStringToByteArray(temp2);
			  // cypher holds the text data to be decrypted
		      String temp3 = new String(my);
			  //ICC the initialization vector
		      byte [] ICC = hexStringToByteArray(temp3);
			  //temp1 holds the key
		      byte [] temp1 = hexStringToByteArray(keyr);
		      int k=1;
			  //brute force for unknown key
			//while(k!=0)
			//{
		      System.out.println("decrypted value:" + (decrypt(temp1, cypher,ICC)));
		    //  k =Integer.parseInt(br.readLine());
		     // temp1[31]++;
			//}

		    } catch (GeneralSecurityException e) {
		      e.printStackTrace();
		    }
		    catch (FileNotFoundException e) {
				
	            System.out.println("File not found" + e);
	
	        }
	
	        catch (IOException ioe) {
	
	            System.out.println("Exception while reading file " + ioe);
	
	        }
	
	        finally {
	
	            // close the streams using close method
	
	            try {
	
	                if (kety != null) {
	
	                    kety.close();
	
	                }
	                if(cipe!= null){
	                	cipe.close();
	                }
	                if(Ic!=null){
	                	Ic.close();
	                }
	
	            }
	
	            catch (IOException ioe) {
	
	                System.out.println("Error while closing stream: " + ioe);
	
	            }
	
	        }
		  }
		//encryption function. not needed in this mp
		  public static byte[] encrypt(String key, String value)
		      throws GeneralSecurityException {

		    byte[] raw = key.getBytes(Charset.forName("US-ASCII"));
		    if (raw.length != 16) {
		      throw new IllegalArgumentException("Invalid key size.");
		    }

		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, skeySpec,
		        new IvParameterSpec(new byte[16]));
		    return cipher.doFinal(value.getBytes(Charset.forName("US-ASCII")));
		  }
		  // converts a string with hex characters to raw hex byte array.
		  public static byte[] hexStringToByteArray(String s) {
			    int len = s.length();
			    byte[] data = new byte[len / 2];
			    for (int i = 0; i < len; i += 2) {
			        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
			                             + Character.digit(s.charAt(i+1), 16));
			    }
			    return data;
			}
            // AES decryption in CBC(cipher block chaining) mode, key size=256bit
			// initialization vector IV = 128 bits. 
		  public static String decrypt(byte[] key, byte [] encrypted, byte[]my)
		      throws GeneralSecurityException {
		    
		    SecretKeySpec aesKey = new SecretKeySpec(key, "AES");

		    Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
	        cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(my));
		    byte[] original = cipher.doFinal(encrypted);

		    return new String(original, Charset.forName("UTF-8"));
		  }
		  
		}
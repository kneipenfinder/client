package pes.kneipenfinder;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.util.Base64;
import android.util.Base64DataException;

import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Peter and Leif on 17.10.2014.
 * Version: 1
 */
public class serverCommunication {

    // Globale Variablen
    private String serverURL;
    private Map<String, String> parameters = new HashMap<String, String>();
    private String sessionID;
    private SecretKeySpec secretKeySpec;
    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
    IvParameterSpec spec = new IvParameterSpec(iv);
    private JSONObject json;
    Context context;
    private errorHandling eHandling;

    // Konstruktor
    public serverCommunication(String server, Context context){
        this.context = context;
        serverURL = server;
    }

    // Speichert die übergebenen Parameter in einer Map (Key-Value-Zuweisung)
    public boolean setParameter(String key, String value){
        try{
            parameters.put(key, URLEncoder.encode(value, "UTF-8"));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String secureCom(String payload){

        String encrypt = encryptMessage(payload, secretKeySpec);
        setParameter("action","secureCom");
        setParameter("sessionID", sessionID);
        setParameter("payload", encrypt);
        String respond = request();
        respond = decryptMessage(respond, secretKeySpec);
        return respond;

    }

    // Ermittelt alle Parameter aus der ParameterMap, konkateniert diese und sendet einen Request zum Server
    public String request(){
        String parametersString = "";
        for(Map.Entry e : parameters.entrySet()){
            if(parametersString == null || parametersString.isEmpty()) {
                parametersString = parametersString + e.getKey() + "=" + e.getValue();
            }else
            {
                parametersString = parametersString + "&" + e.getKey() + "=" + e.getValue();
            }
        }
        clearParameter();
        String response = httpRequest(parametersString);
        clearParameter();
        return response;
    }

    // Initiiert den Http Handshake (Diffie-Hellmann Verschlüsselung) mit dem Server
    public boolean initiateHandshake(){
        setParameter("action", "requestPG");
        String respond = request();
        try {
            json = new JSONObject(respond);
            sessionID = json.getString("sessionID");
            String p = json.getString("p");
            String g = json.getString("g");

            BigInteger secretRandomBI = generateRandomNumber(p);
            String serverPublicKey = json.getString("serverPublicKey");
            BigInteger gBI = new BigInteger(g);
            BigInteger pBI = new BigInteger(p);
            BigInteger serverPublicKeyBI = new BigInteger(serverPublicKey);

            BigInteger publicKeyBI = gBI.modPow(secretRandomBI, pBI);
            BigInteger privateKeyBI = serverPublicKeyBI.modPow(secretRandomBI, pBI);
            secretKeySpec = generateKey(privateKeyBI.toString());

            setParameter("action","handshake");
            setParameter("sessionID",sessionID);
            setParameter("publicKey",publicKeyBI.toString());
            String res = request();
            json = new JSONObject(res);
            String handshakeStatus = json.getString("handshakeStatus");
            return(handshakeStatus.equals("OK"));

        }catch(Exception e){
            return false;
        }
    }

    // Ruft einen Asynchronen Task auf, der die Kommunikation mit dem Server uebernimmt
    // Dies ist notwendig, da Kommunikationen nicht im Main-Task stattfinden dürfen
    public String httpRequest(String parameters){
        String result="";
        asyncHttpRequest task = new asyncHttpRequest(serverURL,parameters, context);
        try{
            result = task.execute(serverURL,parameters).get();
        }catch (Exception e){
            result = e.toString();
        }
        return result;
    }

    // Erzeuge eine zufällige Zahl, die zwischen 0 und (2 ^ BITS in p) -1
    // Endlosschleife möglich, allerdings Wahrscheinlichkeit derart gering => Erstmal zu vernachlässigen
    public BigInteger generateRandomNumber(String pString){
        BigInteger p = new BigInteger(pString);
        BigInteger number;
        Random rnd = new Random();
        do {
            number = new BigInteger(p.bitLength(), rnd);
        } while (number.compareTo(p) >= 0);
        return number;
    }

    // Loescht den gesamten Inhalt der Parameter Map
    public void clearParameter(){
        parameters.clear();
    }

    // Erzeugt ein Key-Objekt aus dem erzeugten Schluessel, mit dem die Nachricht verschluesselt werden kann
    public SecretKeySpec generateKey(String key){
        try{
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(key.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(sha.digest(), 0, keyBytes, 0, keyBytes.length);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            return secretKey;
        }catch(Exception e){
            eHandling = new errorHandling(context,"Es ist ein unerwarteter Fehler aufgetreten", "Schlüssel");
            System.out.println("Beim Erstellen des Schluesselobjekts ist ein Fehler aufgetreten: " + e.toString());
            return null;
        }
    }

    // Verschluesselt die message
    public String encryptMessage(String messageToEncrypt, SecretKeySpec key){
        String messageEncrypted;
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(cipher.ENCRYPT_MODE, key, spec);

            byte[] encrypted = cipher.doFinal(messageToEncrypt.getBytes("UTF-8"));
            String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");
            return encryptedText;

        }catch (Exception e){
            eHandling = new errorHandling(context, "Es ist ein unerwarteter Fehler aufgetreten", "Verschlüsseln");
            System.out.println("Beim Verschluesseln der Nachricht ist ein Fehler aufgetreten: " + e.toString());
            return null;
        }

    }

    // Entschluesselt die message
    public String decryptMessage(String messageToDecrypt, SecretKeySpec key){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] bytes = Base64.decode(messageToDecrypt, Base64.DEFAULT);
            byte[] cipherData = cipher.doFinal(bytes);
            String decyptedMessage = new String(cipherData);
            return decyptedMessage;
        }catch(Exception e){
            eHandling = new errorHandling(context, "Es ist ein unerwarteter Fehler aufgetreten", "Entschlüsseln");
            System.out.println("Beim Entschluesseln der Nachricht ist ein Fehler aufgetreten: " + e.toString());
            return e.toString();
        }
    }
}

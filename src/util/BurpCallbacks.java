package util;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IResponseInfo;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import model.SentinelHttpMessage;
import model.SentinelHttpService;

/**
 *
 * @author unreal
 */
public class BurpCallbacks {

    static private BurpCallbacks burpCallbacks = null;
    private IBurpExtenderCallbacks callback;
    private PrintWriter stdout;

    public void init(IBurpExtenderCallbacks callback) {
        this.callback = callback;
        stdout = new PrintWriter(callback.getStdout(), true);
    }

    public IBurpExtenderCallbacks getBurp() {
        return callback;
    }

    public void print(String s) {
        stdout.println(s);
    }

    public static BurpCallbacks getInstance() {
        if (burpCallbacks == null) {
            burpCallbacks = new BurpCallbacks();
        }
        return burpCallbacks;
    }

    public void sendRessource(SentinelHttpMessage sentinelMessage, boolean followRedirect) {
        if (getBurp() == null) {
            BurpCallbacks.getInstance().print("sendRessource: No burp available");
            return;
        }

        try {
            IHttpRequestResponse r = null;
            long timeStart = System.currentTimeMillis();
            r = getBurp().makeHttpRequest(sentinelMessage.getHttpService(), sentinelMessage.getRequest());
            long time = System.currentTimeMillis() - timeStart;
            sentinelMessage.setLoadTime(time);

            if (followRedirect) {
                int n = 0;
                while (isRedirect(r.getResponse()) && ++n <= 10) {
                    BurpCallbacks.getInstance().print("Is redir, following...");
                    r = followRedirect(r.getResponse());
                }
                if (n >= 10) {
                    String s = "Redirected 10 times, aborting...";
                    sentinelMessage.setResponse(s.getBytes());
                } else {
                    sentinelMessage.setResponse(r.getResponse());
                }
            } else {
                sentinelMessage.setResponse(r.getResponse());
            }
        } catch (Exception ex) {
            BurpCallbacks.getInstance().print("sendRessource(): " + ex.getLocalizedMessage());
        }
    }

    private boolean isRedirect(byte[] response) {
        IResponseInfo responseInfo = BurpCallbacks.getInstance().getBurp().getHelpers().analyzeResponse(response);
        if (responseInfo.getStatusCode() == 302) {
            return true;
        } else {
            return false;
        }
    }

    private IHttpRequestResponse followRedirect(byte[] response) {
        IResponseInfo responseInfo = BurpCallbacks.getInstance().getBurp().getHelpers().analyzeResponse(response);
        String redirStr = null;

        for (String header : responseInfo.getHeaders()) {
            if (header.toLowerCase().startsWith("location: ")) {
                String[] h = header.split(": ");
                if (h.length == 2) {
                    redirStr = h[1];
                }
            }
        }

        if (redirStr == null) {
            BurpCallbacks.getInstance().print("302 found, but could not extract location header!");
            return null;
        }
        URL redirUrl = null;
        try {
            redirUrl = new URL(redirStr);
        } catch (MalformedURLException ex) {
            BurpCallbacks.getInstance().print("302 found, but could not convert location header!");
            return null;
        }

        byte[] req = BurpCallbacks.getInstance().getBurp().getHelpers().buildHttpRequest(redirUrl);
        int port = redirUrl.getPort();
        if (port == -1) {
            port = redirUrl.getDefaultPort();
        }
        IHttpService httpService = new SentinelHttpService(
                redirUrl.getHost(), port, redirUrl.getProtocol());
        IHttpRequestResponse res = getBurp().makeHttpRequest(httpService, req);

        return res;
    }

    public void sendToRepeater(SentinelHttpMessage httpMessage) {
        try {
            String s = "";
            if (httpMessage.getTableIndexAttack() >= 0) {
                s = "Sentinel " + httpMessage.getTableIndexMain() + "/" + httpMessage.getTableIndexAttack();
            } else {
                s = "Sentinel " + httpMessage.getTableIndexMain();
            }
            
            this.getBurp().sendToRepeater(
                    httpMessage.getHttpService().getHost(),
                    httpMessage.getHttpService().getPort(),
                    (httpMessage.getHttpService().getProtocol().equals("http") ? false : true),
                    httpMessage.getRequest(),
                    s);
        } catch (Exception ex) {
            BurpCallbacks.getInstance().print(ex.getLocalizedMessage());
        }
    }
}

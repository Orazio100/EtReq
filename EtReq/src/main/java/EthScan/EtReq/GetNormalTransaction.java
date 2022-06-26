package EthScan.EtReq;

import java.io.IOException;
import java.util.List;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.google.gson.Gson;

public class GetNormalTransaction {
	private String status;
	private String message;
	private List<NormalTransaction> result;
	
	
	public String toString() {
		String t =  "\nstatus: "+ status
					+"\nmessage: " + message;
		
		for(NormalTransaction nt : result) {
			
			t+= "\n --------------------------------------------- \n";
			t+= nt.toString();
			
		}
				
		return t;
		
		
	}
	
	
	public static GetNormalTransaction Get(String url) throws IOException{
		
		
try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
        	
            final HttpGet httpget = new HttpGet(url);

            final HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {

                @Override
                public String handleResponse(final ClassicHttpResponse response) throws IOException {
                    final int status = response.getCode();
                    if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                        final HttpEntity entity = response.getEntity();
                        try {
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } catch (final ParseException ex) {
                            throw new ClientProtocolException(ex);
                        }
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            
            final String responseBody = httpclient.execute(httpget, responseHandler);
    	        
            return new Gson().fromJson(responseBody.toString(), GetNormalTransaction.class);
              
        }
	}
	
	public GetNormalTransaction() {};
	

	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<NormalTransaction> getResult() {
		return result;
	}

	public void setResult(List<NormalTransaction> result) {
		this.result = result;
	}

	

}

/**
 * 
 */
package com.mcac0006.siftscience;

import com.mcac0006.siftscience.event.domain.Event;
import com.mcac0006.siftscience.label.domain.Label;
import com.mcac0006.siftscience.result.domain.SiftScienceResponse;
import com.mcac0006.siftscience.result.domain.SiftScienceSyncResponse;
import com.mcac0006.siftscience.score.domain.SiftScienceScore;
import lombok.Getter;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This helper will take care of marshalling the content you wish to send to Sift Science and 
 * also POST send it to Sift Science.
 * 
 * <strong>This class is synchronous.</strong>
 * 
 * @author <a href="mailto:matthew.cachia@gmail.com">Matthew Cachia</a>
 *
 */
public class SiftScienceHelper {

	@Getter
	private ObjectMapper mapper;
	
	private String apiKey;
	
	public SiftScienceHelper(final String apiKey) {
		
		if (apiKey == null || apiKey.isEmpty()) {
			throw new RuntimeException("The API Key apiKey must not be null or empty.");
		}
		
		this.apiKey = apiKey;
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	/**
	 * Sends an event ($transaction, $create_account, etc ...) to Sift Sciecne.
	 * 
	 * @param event - the content regarding the user (or session) in question.
	 * @return the Sift Science response which denotes whether the request has been processed successfully or not.
	 */
    public SiftScienceSyncResponse sendSync(final Event event) {

        event.setApiKey(apiKey);

        try {
            String response = Request.Post("https://api.siftscience.com/v203/events?return_action=true")
                    .bodyString(mapper.writeValueAsString(event), ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
            final SiftScienceSyncResponse siftResult = mapper.readValue(response, SiftScienceSyncResponse.class);
            return siftResult;

        } catch (JsonGenerationException e) {
            throw new RuntimeException("Error generating JSON content to send.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error generating JSON content to send.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error generating JSON content to send.", e);
        }
    }

    public SiftScienceResponse send(final Event event) {

        event.setApiKey(apiKey);

        try {
            String response = Request.Post("https://api.siftscience.com/v203/events")
                    .bodyString(mapper.writeValueAsString(event), ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
            final SiftScienceResponse siftResult = mapper.readValue(response, SiftScienceResponse.class);
            return siftResult;

        } catch (JsonGenerationException e) {
            throw new RuntimeException("Error generating JSON content to send.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error generating JSON content to send.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error generating JSON content to send.", e);
        }
    }

	/**
	 * Sends a Label ($label) to Sift Science.
	 * 
	 * @param userId - the user in question
	 * @param label - the content regarding the user in question.
	 * @return the Sift Science response which denotes whether the request has been processed successfully or not.
	 */
	public SiftScienceResponse send(final String userId, final Label label) {
		
		label.setApiKey(apiKey);
		
		try {
			
            Response response = Request.Post("https://api.siftscience.com/v203/users/" + userId + "/labels")
                    .bodyString(mapper.writeValueAsString(label), ContentType.APPLICATION_JSON).execute();
            final SiftScienceResponse siftResult = mapper.readValue(response.returnContent().asString(), SiftScienceResponse.class);
			return siftResult;
			
		} catch (JsonGenerationException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (IOException e) {
			throw new RuntimeException("Erro IO with Sift.", e);
		}
	}

	/**
	 * Removes a label a Label ($label) to Sift Science.
	 *
	 * @param userId - the user in question
	 * @return the Sift Science response which denotes whether the request has been processed successfully or not.
	 */
	public int removeLabel(final String userId) {

		try {
			URI uri = new URIBuilder("https://api.siftscience.com/v203/users/" + userId+"/labels/").addParameter("api_key", apiKey).build();
            Response response = Request.Delete(uri).execute();
            return response.returnResponse().getStatusLine().getStatusCode();

		} catch (JsonGenerationException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (IOException e) {
			throw new RuntimeException("Error IO with sift.", e);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Error generating URI content to send.", e);
		}
	}


	/**
	 * Retrieve a risk assessment of a particular user. This is particularly useful to consult with Sift Science 
	 * before you proceed with any (user-invoked or system-invoked) operations (such as a purchase) on that user.
	 * 
	 * @param userId - the user would you like to run a risk assessment on.
	 * @return a Sift Science score wrapped in a {@link SiftScienceScore} instance containing information such as the 
	 *         fraud score and the reason. 
	 *         
	 *         Refer to the class' JavaDocs for more information.
	 */
	public SiftScienceScore getScore(final String userId) {
		
		try {
            URI uri = new URIBuilder("https://api.siftscience.com/v203/score/" + userId).addParameter("api_key", apiKey).build();
            String response = Request.Get(uri).execute().returnContent().asString();
            final SiftScienceScore score = parseScore(response);
            return score;
			
		} catch (JsonGenerationException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (IOException e) {
			throw new RuntimeException("Error generating JSON content to send.", e);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Error Creating URL to send.", e);
        }

    }

    public SiftScienceScore parseScore(String response) throws IOException {
        return mapper.readValue(response, SiftScienceScore.class);
    }
}

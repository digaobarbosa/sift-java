/**
 * 
 */
package com.mcac0006.siftscience.score.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Sift Science will provide a set of reasons to justify its score on a particular user. 
 * Each Reason is wrapped into a {@link Reason} instance.
 * 
 * An example of a reason would be:
 * <pre><code>
 * name - Users per device
 * value - 2
 * details - user0001
 * </code></pre>
 * 
 * which would ultimately mean that there are two users (the user in question and user0001) 
 * who are sharing the same device.
 * 
 * @author <a href="mailto:matthew.cachia@gmail.com">Matthew Cachia</a>
 *
 */
@EqualsAndHashCode(of={"name","value"})
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reason {

	/**
	 * An informal description of the reason.
	 * 
	 * e.g. "Users per device", "Users per browser+IP", "Transaction billing bin"
	 */
	private String name;
	
	/**
	 * A value related to this reason. This value gives numerical information on the 
	 * particular reason. e.g. "Users per device" will give you a number to explain 
	 * just how many users are sharing that device.
	 * 
	 */
	private String value;
	
	/**
	 * Some further information on the reason. These are key-value optional pairs.
	 * 
	 * E.g. "users": "user1,user2,user3"
	 * 
	 * <strong>All of the information here is optional and might not feature in other 
	 * score queries.</strong> It is therefore highly discouraged from using them for 
	 * mission-critical logic.
	 */
	private Map<String, String> details = new HashMap<String, String>();


	public void addDetails(final String key, final String value) {
		details.put(key, value);
	}

	public final Map<String, String> getDetails() {
		return details;
	}

}

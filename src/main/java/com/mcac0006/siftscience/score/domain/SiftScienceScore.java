/**
 * 
 */
package com.mcac0006.siftscience.score.domain;

import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonProperty;

import com.mcac0006.siftscience.label.domain.Label;

/**
 * This class represents the score which is received from Sift Science about 
 * a particular user.
 * 
 * This information is mostly used passively (such as manual investigation by 
 * a fraud agent) or actively (such as your product consults with the result before 
 * if serves the user's transaction).
 * 
 * Refer to <a href="https://siftscience.com/docs/getting-scores">Getting Sift Scores</a> for more information.
 * 
 * @author <a href="mailto:matthew.cachia@gmail.com">Matthew Cachia</a>
 *
 */
@ToString(of={"userId","score"})
@Data
@EqualsAndHashCode(of = {"userId","errorMessage","score"})
public class SiftScienceScore {

	
	/**
	 * The user ID in question.
	 */
	@JsonProperty(value="user_id")
	private String userId;
	
	/**
	 * The score which Sift Science recommends for this particular {@link #userId}.
	 * 
	 * The score is from 0.00 to 1.00, 0.00 being entirely legitimate, 1.00 being entirely a fraud.
	 */
	private Float score;
	
	/**
	 * Sift Science will justify the score given for thus {@link #userId} into an array of {@link Reason} 
	 * instances.
	 * 
	 * Refer to {@link Reason} for more information.
	 */
	private Reason[] reasons;
	
	/**
	 * If this user has been labeled by you or your system, this will contain the last label given by Sift Science.
	 */
	@JsonProperty(value="latest_label")
	private ScoreLabel latestLabel;
	
	/**
	 * Return a status for the response. Refer to the 
	 * <a href="https://siftscience.com/docs/references/events-api#error-codes">Error Codes</a> section for more info.
	 */
	private Short status;
	
	@JsonProperty(value="error_message")
	private String errorMessage;


	public final void setStatus(Short status) {
		this.status = status;
	}

	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

}

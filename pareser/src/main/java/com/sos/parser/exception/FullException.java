/**
 * 
 */
package com.sos.parser.exception;

/**
 * @author lweyrich
 *
 */
public class FullException extends RuntimeException 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1045489030417225853L;

	/**
	 * 
	 */
	public FullException() 
	{
		super();
	}

	/**
	 * @param message
	 */
	public FullException(String message) 
	{
		super(message);
		// do nothing
	}

	/**
	 * @param cause
	 */
	public FullException(Throwable cause) 
	{
		super(cause);
		// do nothing
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FullException(String message, Throwable cause) 
	{
		super(message, cause);
		// do nothing
	}

}

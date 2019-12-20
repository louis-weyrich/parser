/**
 * 
 */
package com.sos.parser.exception;

/**
 * @author louis.weyrich
 *
 */
public class ArrayOverflowException extends RuntimeException
{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8209250364442482076L;

	/**
	 * 
	 */
	public ArrayOverflowException()
	{
		// Do Nothing
	}

	/**
	 * @param arg0
	 */
	public ArrayOverflowException(String arg0)
	{
		super(arg0);
	}

	/**
	 * @param cause
	 */
	public ArrayOverflowException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ArrayOverflowException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ArrayOverflowException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

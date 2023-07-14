package com.rssl.phizic.business.messaging.info;

/**
 * @author Roshka
 * @ created 22.06.2006
 * @ $Author$
 * @ $Revision$
 */

public class MessagingInfoException extends Exception
{
	/**
	 * Constructs a new throwable with <code>null</code> as its detail message. The cause is not initialized, and
	 * may subsequently be initialized by a call to {@link #initCause}.
	 * <p/>
	 * <p>The {@link #fillInStackTrace()} method is called to initialize the stack trace data in the newly
	 * created throwable.
	 */
	public MessagingInfoException()
	{
	}

	/**
	 * Constructs a new throwable with the specified cause and a detail message of <tt>(cause==null ? null :
	 * cause.toString())</tt> (which typically contains the class and detail message of <tt>cause</tt>). This
	 * constructor is useful for throwables that are little more than wrappers for other throwables (for example,
	 * {@link java.security.PrivilegedActionException}).
	 * <p/>
	 * <p>The {@link #fillInStackTrace()} method is called to initialize the stack trace data in the newly
	 * created throwable.
	 *
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A
	 *              <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @since 1.4
	 */
	public MessagingInfoException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Constructs a new throwable with the specified detail message.  The cause is not initialized, and may
	 * subsequently be initialized by a call to {@link #initCause}.
	 * <p/>
	 * <p>The {@link #fillInStackTrace()} method is called to initialize the stack trace data in the newly
	 * created throwable.
	 *
	 * @param message the detail message. The detail message is saved for later retrieval by the {@link
	 *                #getMessage()} method.
	 */
	public MessagingInfoException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a new throwable with the specified detail message and cause.  <p>Note that the detail message
	 * associated with <code>cause</code> is <i>not</i> automatically incorporated in this throwable's detail
	 * message.
	 * <p/>
	 * <p>The {@link #fillInStackTrace()} method is called to initialize the stack trace data in the newly
	 * created throwable.
	 *
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()}
	 *                method).
	 * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A
	 *                <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @since 1.4
	 */
	public MessagingInfoException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
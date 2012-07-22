package uk.co.unclealex.callerid.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.callerid.phonenumber.model.PhoneNumber;

public class CallRecord implements Serializable, Comparable<CallRecord> {

	private Date i_callTime;
	private PhoneNumber i_phoneNumber;
	private boolean i_blocked;
	private SortedSet<CallRecordContact> i_contacts;
	
	protected CallRecord() {
		// Default constructor for serialisation.
		super();
	}

	public CallRecord(Date callTime, PhoneNumber phoneNumber, boolean blocked, SortedSet<CallRecordContact> contacts) {
		super();
		i_callTime = callTime;
		i_phoneNumber = phoneNumber;
		i_blocked = blocked;
		i_contacts = contacts;
	}

	@Override
	public int compareTo(CallRecord o) {
		return o.getCallTime().compareTo(getCallTime());
	}
	
	public Date getCallTime() {
		return i_callTime;
	}

	public PhoneNumber getPhoneNumber() {
		return i_phoneNumber;
	}

	public boolean isBlocked() {
		return i_blocked;
	}

	public SortedSet<CallRecordContact> getContacts() {
		return i_contacts;
	}
	
	
}

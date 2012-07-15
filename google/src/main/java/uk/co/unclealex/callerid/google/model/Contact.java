/**
 * Copyright 2012 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 *
 * @author unclealex72
 *
 */

package uk.co.unclealex.callerid.google.model;

import java.util.Arrays;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import uk.co.unclealex.callerid.phonenumber.model.TelephoneNumber;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * A contact represents a single Google contact. Only their name and telephone
 * numbers are stored.
 * 
 * @author alex
 * 
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class Contact {

  /**
   * Create a new contact.
   * @param name The name of the contact.
   * @param telephoneNumbers The telephone numbers owned by this contact.
   * @return A new contact.
   */
  public static Contact create(String name, TelephoneNumber... telephoneNumbers) {
    return create(name, Arrays.asList(telephoneNumbers));
  }
  
  /**
   * Create a new contact.
   * @param name The name of the contact.
   * @param telephoneNumbers The telephone numbers owned by this contact.
   * @return A new contact.
   */
  public static Contact create(String name, Iterable<TelephoneNumber> telephoneNumbers) {
    return new Contact(name, telephoneNumbers, ContactTelephoneNumber.FROM_NUMBER);
  }

  /**
   * The contact's primary id.
   */
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.NATIVE)
  private Integer id;

  /**
	 * The name of the contact.
	 */
  @Unique
	private String name;

	/**
	 * A list of telephone numbers associated with the contact.
	 */
	@Persistent
	@Element(dependent="true")
	private List<ContactTelephoneNumber> telephoneNumbers;

	/**
	 * Instantiates a new contact.
	 *
	 * @param name the name
	 * @param telephoneNumbers the telephone numbers
	 */
	protected <E> Contact(String name, Iterable<E> telephoneNumbers, Function<E, ContactTelephoneNumber> f) {
		super();
		this.name = name;
		this.telephoneNumbers = Lists.newArrayList(Iterables.transform(telephoneNumbers, f));
	}

  /**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
	  return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the telephone numbers.
	 *
	 * @return the telephone numbers
	 */
	public List<ContactTelephoneNumber> getTelephoneNumbers() {
		return telephoneNumbers;
	}

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Integer getId() {
    return id;
  }
}

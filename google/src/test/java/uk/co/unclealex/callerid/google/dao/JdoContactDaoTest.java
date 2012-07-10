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

package uk.co.unclealex.callerid.google.dao;

import java.util.Collections;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.callerid.google.dao.JdoContactDaoTest.Context;
import uk.co.unclealex.callerid.google.model.Contact;
import uk.co.unclealex.callerid.phonenumber.model.TelephoneNumber;
import uk.co.unclealex.hbase.testing.DatanucleusContext;
import uk.co.unclealex.hbase.testing.HBaseTestContainer;
import uk.co.unclealex.hbase.testing.HBaseTestContainer.Port;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author alex
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Context.class })
@Transactional
@TransactionConfiguration
public class JdoContactDaoTest {

  static HBaseTestContainer container = new HBaseTestContainer().withPort(Port.ZOOKEEPER_CLIENT, 2181);

  @BeforeClass
  public static void setup() throws Exception {
    container.start();
  }

  @AfterClass
  public static void close() {
    container.stop();
  }

  @Autowired
  private ContactDao contactDao;

  @Autowired
  private PersistenceManagerFactory persistenceManagerFactory;

  @SuppressWarnings("unchecked")
  @Before
  public void clear() {
    PersistenceManager persistenceManager = getPersistenceManagerFactory().getPersistenceManager();
    persistenceManager.newQuery(Contact.class, "").deletePersistentAll();
    Assert.assertFalse(
        "Not all object references were removed.",
        ((Iterable<Object>) persistenceManager.newQuery(Contact.class, "").execute()).iterator().hasNext());
  }

  @Test
  public void testFindByTelephoneNumber() {
    TelephoneNumber numberOne = createTelephoneNumber("44", "800", "118118");
    TelephoneNumber numberTwo = createTelephoneNumber("1", "214", "555976");
    TelephoneNumber numberThree = createTelephoneNumber("1", "714", "888888");
    TelephoneNumber numberFour = createTelephoneNumber("33", "999", "000000");
    Contact tom = createContact("tom", numberOne, numberTwo);
    Contact dick = createContact("dick", numberThree, numberOne);
    @SuppressWarnings("unused")
    Contact harry = createContact("harry", numberFour);
    List<Contact> contacts = getContactDao().findByTelephoneNumber(numberOne);
    List<Contact> actualContacts = Lists.newArrayList(contacts);
    Collections.sort(actualContacts, Contact.NAME_COMPARATOR);
    List<Contact> expectedContacts = Lists.newArrayList(tom, dick);
    Collections.sort(expectedContacts, Contact.NAME_COMPARATOR);
    Assert.assertArrayEquals(
        "The wrong contacts were returned for a telephone number.",
        Iterables.toArray(expectedContacts, Contact.class),
        Iterables.toArray(actualContacts, Contact.class));
  }

  protected TelephoneNumber createTelephoneNumber(String internationalPrefix, String stdCode, String number) {
    TelephoneNumber telephoneNumber = new TelephoneNumber(internationalPrefix, stdCode, number);
    getPersistenceManagerFactory().getPersistenceManager().makePersistent(telephoneNumber);
    return telephoneNumber;
  }

  protected Contact createContact(String name, TelephoneNumber... telephoneNumbers) {
    Contact contact = new Contact(name, telephoneNumbers);
    PersistenceManager persistenceManager = getPersistenceManagerFactory().getPersistenceManager();
    persistenceManager.makePersistent(contact);
    return contact;
  }

  @Configuration
  @ImportResource({ "classpath:application-context-persistence.xml", "classpath:application-context-google.xml" })
  public static class Context extends DatanucleusContext {
    public HBaseTestContainer getContainer() {
      return container;
    }
  }

  public ContactDao getContactDao() {
    return contactDao;
  }

  public void setContactDao(ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  public PersistenceManagerFactory getPersistenceManagerFactory() {
    return persistenceManagerFactory;
  }

  public void setPersistenceManagerFactory(PersistenceManagerFactory persistenceManagerFactory) {
    this.persistenceManagerFactory = persistenceManagerFactory;
  }

}

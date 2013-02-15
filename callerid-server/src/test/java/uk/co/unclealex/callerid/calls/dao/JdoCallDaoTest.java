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
 * @author alex
 *
 */

package uk.co.unclealex.callerid.calls.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.callerid.calls.model.Call;

/**
 * @author alex
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-callerid-calls-test.xml")
@Transactional
@TransactionConfiguration
public class JdoCallDaoTest {

  @Autowired
  CallDao callDao;
  @Autowired
  PersistenceManagerFactory persistenceManagerFactory;

  @Before
  public void setUp() {
    for (Call call : callDao.getAll()) {
      callDao.delete(call);
    }
    flush();
  }
  
  @Test
  public void testCreate() {
    Date now = new Date();
    Call call = new Call(now, "441256999888", "Brian");
    callDao.store(call);
    flush();
    @SuppressWarnings("unchecked")
    List<Call> actualCalls = (List<Call>) persistenceManager().newQuery(Call.class).execute();
    Assert.assertEquals("The wrong number of calls were returned.", 1, actualCalls.size());
    Call actualCall = actualCalls.get(0);
    Assert.assertEquals("The call had the wrong time.", now, actualCall.getCallTime());
    Assert.assertEquals("The call had the wrong telephone number.", "441256999888", actualCall.getTelephoneNumber());
    Assert.assertEquals("The call had the wrong contact name.", "Brian", actualCall.getContactName());
  }

  @Test
  public void testUpdate() {
    Date now = new Date();
    Call call = new Call(now, "441256999888", "Brian");
    callDao.store(call);
    flush();
    callDao.updateContactName(call.getId(), "Freddie");
    flush();
    @SuppressWarnings("unchecked")
    List<Call> actualCalls = (List<Call>) persistenceManager().newQuery(Call.class).execute();
    Assert.assertEquals("The wrong number of calls were returned.", 1, actualCalls.size());
    Call actualCall = actualCalls.get(0);
    Assert.assertEquals("The call had the wrong time.", now, actualCall.getCallTime());
    Assert.assertEquals("The call had the wrong telephone number.", "441256999888", actualCall.getTelephoneNumber());
    Assert.assertEquals("The call had the wrong contact name.", "Freddie", actualCall.getContactName());
  }

  @Test
  public void testGetAllByDate() {
    Calendar cal = new GregorianCalendar();
    Date firstCallTime = cal.getTime();
    cal.add(Calendar.HOUR_OF_DAY, -1);
    Date secondCallTime = cal.getTime();
    cal.add(Calendar.HOUR_OF_DAY, -1);
    Date thirdCallTime = cal.getTime();
    Call firstCall = new Call(firstCallTime, "33900404040", "Brian");
    Call secondCall = new Call(secondCallTime, "44800400100", "Freddie");
    Call thirdCall = new Call(thirdCallTime, "33900505050", "Brian");
    callDao.store(secondCall);
    callDao.store(firstCall);
    callDao.store(thirdCall);
    flush();
    PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
    List<Call> allByTimeReceived = callDao.getAllByTimeReceived();
    Assert.assertArrayEquals(
        "The wrong calls were returned.",
        new Call[] { pm.detachCopy(firstCall), pm.detachCopy(secondCall), pm.detachCopy(thirdCall) },
        allByTimeReceived.toArray(new Call[0]));
  }

  @Test
  public void testMostRecentCallForNumberWithNoPreviousCall() {
    callDao.store(new Call(new Date(), "44800400100", "Mike"));
    flush();
    Assert.assertNull(
        "A contact name was returned for a call that has never been made.",
        callDao.getMostRecentContactNameForPhoneNumber("44800500500"));
  }

  @Test
  public void testMostRecentCallForNumberWithPreviousCalls() {
    Calendar cal = new GregorianCalendar();
    callDao.store(new Call(cal.getTime(), "44800400100", "Brian"));
    cal.add(Calendar.DAY_OF_YEAR, -1);
    callDao.store(new Call(cal.getTime(), "44800500500", "Mike"));
    cal.add(Calendar.DAY_OF_YEAR, -1);
    callDao.store(new Call(cal.getTime(), "44800500500", null));
    cal.add(Calendar.DAY_OF_YEAR, -1);
    callDao.store(new Call(cal.getTime(), "44800500500", "Ian"));
    flush();
    Assert.assertEquals(
        "The wrong name was recevied for a most recent call.",
        "Mike",
        callDao.getMostRecentContactNameForPhoneNumber("44800500500"));
  }

  @Test
  public void testMostRecentCallForNumberWithPreviousCallsAndNullOverrides() {
    Calendar cal = new GregorianCalendar();
    callDao.store(new Call(cal.getTime(), "44800400100", "Brian"));
    cal.add(Calendar.DAY_OF_YEAR, -1);
    callDao.store(new Call(cal.getTime(), "44800500500", null));
    cal.add(Calendar.DAY_OF_YEAR, -1);
    callDao.store(new Call(cal.getTime(), "44800500500", "Mike"));
    cal.add(Calendar.DAY_OF_YEAR, -1);
    callDao.store(new Call(cal.getTime(), "44800500500", "Ian"));
    flush();
    Assert.assertNull(
        "A name was returned for a call where null was the most recent name.",
        callDao.getMostRecentContactNameForPhoneNumber("44800500500"));
  }

  protected void flush() {
    persistenceManager().flush();
  }

  protected PersistenceManager persistenceManager() {
    return persistenceManagerFactory.getPersistenceManager();
  }

}
/**
 * 
 */
package uk.co.unclealex.callerid.client.views;

import javax.inject.Inject;

import uk.co.unclealex.callerid.client.presenters.GoogleAuthenticationPresenter.Display;
import uk.co.unclealex.callerid.client.util.CanWaitSupport;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Copyright 2011 Alex Jones
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
public class GoogleAuthentication extends SimplePanel implements Display {

	@UiTemplate("GoogleAuthentication.ui.xml")
	public interface Binder extends UiBinder<Widget, GoogleAuthentication> {
    // No extra method
  }
	
	private static final Binder binder = GWT.create(Binder.class);

	private final CanWaitSupport i_canWaitSupport;
	
	@UiField PopupPanel popupPanel;
	@UiField Anchor authenticationAnchor;
	@UiField TextBox successCode;
	@UiField Button submitButton;
	
	@Inject
	public GoogleAuthentication(CanWaitSupport canWaitSupport) {
	  i_canWaitSupport = canWaitSupport;
		add(binder.createAndBindUi(this));
		canWaitSupport.wrap(successCode, submitButton);
	}

	@Override
	public void startWaiting(String message, int waitingHandler) {
	  getCanWaitSupport().startWaiting(message, waitingHandler);
	}
	
  @Override
  public void stopWaiting(int waitingHandler) {
    getCanWaitSupport().stopWaiting(waitingHandler);
  }
	
	public HasText getSuccessCode() {
		return successCode;
	}

	public Button getSubmitButton() {
		return submitButton;
	}

	public PopupPanel getPopupPanel() {
		return popupPanel;
	}

  public CanWaitSupport getCanWaitSupport() {
    return i_canWaitSupport;
  }

  @Override
  public Anchor getAuthenticationAnchor() {
    return authenticationAnchor;
  }



}

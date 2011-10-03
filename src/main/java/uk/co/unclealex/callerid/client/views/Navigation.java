/**
 * 
 */
package uk.co.unclealex.callerid.client.views;

import javax.inject.Inject;

import uk.co.unclealex.callerid.client.presenters.NavigationPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
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
public class Navigation extends Composite implements Display {

	@UiTemplate("Navigation.ui.xml")
	public interface Binder extends UiBinder<Widget, Navigation> {
    // No extra method
  }

	private static final Binder binder = GWT.create(Binder.class);

	public static interface Style extends CssResource {
		String selected();
	}
	
	@UiField HasClickHandlers contactsLink;
	@UiField HasClickHandlers callsLink;
	@UiField Style style;
	
	@Inject
	public Navigation() {
		initWidget(binder.createAndBindUi(this));
	}

	/*
	@UiFactory
	public Anchor createAnchor() {
		return new Anchor(true);
	}
*/
	@Override
	public void select(HasClickHandlers hasClickHandlers) {
		((IsWidget) hasClickHandlers).asWidget().addStyleName(getStyle().selected());
	}
	
	@Override
	public void deselect(HasClickHandlers hasClickHandlers) {
		((IsWidget) hasClickHandlers).asWidget().removeStyleName(getStyle().selected());
	}
	
	public Style getStyle() {
		return style;
	}

	public HasClickHandlers getContactsLink() {
		return contactsLink;
	}

	public HasClickHandlers getCallsLink() {
		return callsLink;
	}
}
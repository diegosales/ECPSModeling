/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package ecpsmodeling.parser;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import java.util.ArrayList;
import org.eclipse.swt.SWT;

public class ActuatorsThreadsPage extends WizardPage {
	protected ArrayList<ActuationFunction> actFunctionsList;
	
	protected ArrayList<Actuator> actuatorsList;
	
	protected ArrayList<AADLThread> threads;

	private Composite container;

	protected Label pinformation;
	protected Label sinformation;

	protected Tree periodicTable;
	protected Tree sporadicTable;

	protected Button btRemovePeriodicThread;
	protected Button btRemoveSporadicThread;
	protected Button btEditPeriodicThread;
	protected Button btEditSporadicThread;
	protected Button btAddPeriodicThread;
	protected Button btAddSporadicThread;

	public ActuatorsThreadsPage() {
		super("Actuators Threads Specification");
		setTitle("Actuators Threads Specification");
		setDescription("Organize the System Threads that Manage the Actuators:");
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;

		container = new Composite(parent, SWT.NONE);
		container.setLayout(layout);
		container.setSize(300, 200);

		pinformation = new Label(container, SWT.NONE);
		GridData ilayout = new GridData();
		ilayout.horizontalSpan = 3;
		pinformation.setLayoutData(ilayout);
		pinformation.setText("Periodic Threads:");

		// ---------------------- Table ---------------------------
		periodicTable = new Tree(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		periodicTable.setLinesVisible(true);
		periodicTable.setHeaderVisible(true);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 3;

		periodicTable.setLayoutData(data);
		periodicTable.setSize(300, 50);

		final TreeColumn column = new TreeColumn(periodicTable, SWT.LEFT);
		column.setText("Thread");
		column.setWidth(150);

		final TreeColumn column1 = new TreeColumn(periodicTable, SWT.NONE);
		column1.setText("Period (ms)");
		column1.setWidth(70);

		final TreeColumn column2 = new TreeColumn(periodicTable, SWT.NONE);
		column2.setText("Priority");
		column2.setWidth(80);

		final TreeColumn column3 = new TreeColumn(periodicTable, SWT.NONE);
		column3.setText("Actuators");
		column3.setWidth(100);

		final TreeColumn column4 = new TreeColumn(periodicTable, SWT.NONE);
		column4.setText("Functions");
		column4.setWidth(100);

		btAddPeriodicThread = new Button(container, SWT.NONE);
		btEditPeriodicThread = new Button(container, SWT.NONE);
		btRemovePeriodicThread = new Button(container, SWT.NONE);

		periodicTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btEditPeriodicThread.setEnabled(true);
				// btRemovePeriodicThread.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stubperiodicTable
			}
		});

		periodicTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				editPeriodicThread(parent.getDisplay());
			}
		});

		btAddPeriodicThread.setText("Add Periodic Thread");
		btAddPeriodicThread.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					addPeriodicThread(parent.getDisplay());
				}
			}
		});

		btEditPeriodicThread.setText("Edit Periodic Thread");
		btEditPeriodicThread.setEnabled(false);
		btEditPeriodicThread.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					editPeriodicThread(parent.getDisplay());
				}
			}
		});

		btRemovePeriodicThread.setText("Remove Periodic Thread");
		btRemovePeriodicThread.setEnabled(false);

		sinformation = new Label(container, SWT.NONE);
		ilayout.horizontalSpan = 3;
		sinformation.setLayoutData(ilayout);
		sinformation.setText("Sporadic Threads:");

		// ---------------------- Table ---------------------------
		sporadicTable = new Tree(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sporadicTable.setLinesVisible(true);
		sporadicTable.setHeaderVisible(true);

		GridData data2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		data2.horizontalSpan = 3;

		sporadicTable.setLayoutData(data2);
		sporadicTable.setSize(300, 50);

		final TreeColumn scolumn = new TreeColumn(sporadicTable, SWT.LEFT);
		scolumn.setText("Thread");
		scolumn.setWidth(150);

		final TreeColumn scolumn1 = new TreeColumn(sporadicTable, SWT.NONE);
		scolumn1.setText("Period");
		scolumn1.setWidth(70);

		final TreeColumn scolumn2 = new TreeColumn(sporadicTable, SWT.NONE);
		scolumn2.setText("Priority");
		scolumn2.setWidth(80);

		final TreeColumn scolumn3 = new TreeColumn(sporadicTable, SWT.NONE);
		scolumn3.setText("Actuators");
		scolumn3.setWidth(100);

		final TreeColumn scolumn4 = new TreeColumn(sporadicTable, SWT.NONE);
		scolumn4.setText("Functions");
		scolumn4.setWidth(100);
		
		btAddSporadicThread = new Button(container, SWT.NONE);
		btEditSporadicThread = new Button(container, SWT.NONE);
		btRemoveSporadicThread = new Button(container, SWT.NONE);

		sporadicTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btEditSporadicThread.setEnabled(true);
				// btRemoveSporadicThread.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});

		sporadicTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				editSporadicThread(parent.getDisplay());
			}
		});

		btAddSporadicThread.setText("Add Sporadic Thread");
		btAddSporadicThread.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					addSporadicThread(parent.getDisplay());
				}
			}
		});

		btEditSporadicThread.setText("Edit Sporadic Thread");
		btEditSporadicThread.setEnabled(false);
		btEditSporadicThread.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					editSporadicThread(parent.getDisplay());
				}
			}
		});

		btRemoveSporadicThread.setText("Remove Sporadic Thread");
		btRemoveSporadicThread.setEnabled(false);

		threads = new ArrayList<>();
		actuatorsList = new ArrayList<>();
		actFunctionsList = new ArrayList<>();

		setControl(container);
		// setPageComplete(false);
		setPageComplete(true);
	}

	private void addPeriodicThread(Display display) {
		ActuationThreadShell add = new ActuationThreadShell(display, actuatorsList, "Add Periodic Thread", true,
				actFunctionsList);

		if (add.isConfirm()) {
			updateActuatorsList(add.getActuators(), true);
			updateFunctionList(add.getFunctions());

			addThread(add.getName(), add.getPeriod(), add.getPriority(), add.getThreadActuators(), add.geTemplate(),
					true, add.getThreadFunctions());
		}

		checkSpecified();

	}

	protected void addSporadicThread(Display display) {
		ActuationThreadShell addSporadic = new ActuationThreadShell(display, actuatorsList, "Add Sporadic Thread",
				false, actFunctionsList);

		if (addSporadic.isConfirm()) {
			updateActuatorsList(addSporadic.getActuators(), false);
			updateFunctionList(addSporadic.getFunctions());

			addThread(addSporadic.getName(), addSporadic.getPeriod(), addSporadic.getPriority(),
					addSporadic.getThreadActuators(), addSporadic.geTemplate(), false,
					addSporadic.getThreadFunctions());
		}

		checkSpecified();

	}

	private void updateActuatorsList(ArrayList<Actuator> actuators, boolean bperiodic) {
		actuatorsList.removeIf(p -> p.isPeriodic() == bperiodic);

		for (int i = 0; i < actuators.size(); i++) {
			actuatorsList.add(actuators.get(i));
		}
	}

	private void updateFunctionList(ArrayList<ActuationFunction> iFunctions) {
		actFunctionsList.clear();
		for (int i = 0; i < iFunctions.size(); i++) {
			actFunctionsList.add(iFunctions.get(i));
		}
	}
	
	
	private void addThread(String name, int period, int priority, ArrayList<Actuator> threadActuators, String sTemplate,
			boolean bperiodic, ArrayList<ActuationFunction> threadFunctions) {
		AADLThread aux = new AADLThread();
		TreeItem treeItem;
		TreeItem subitem;

		aux.setName(name);
		aux.setPeriod(period);
		aux.setPeriodic(bperiodic);
		aux.setActuators(threadActuators);
		aux.setPriority(priority);
		aux.setTemplate(sTemplate);
		// aux.setFunctions(threadFunctions);

		if (bperiodic) {
			treeItem = new TreeItem(periodicTable, SWT.NONE);

			aux.setIndex(periodicTable.getItemCount() - 1);
		} else {
			treeItem = new TreeItem(sporadicTable, SWT.NONE);

			aux.setIndex(sporadicTable.getItemCount() - 1);
		}

		treeItem.setText(0, name);
		treeItem.setText(1, String.valueOf(period));
		treeItem.setText(2, String.valueOf(priority));

		if (threadActuators.size() >= threadFunctions.size()) {
			for (int i = 0; i < threadActuators.size(); i++) {
				subitem = new TreeItem(treeItem, SWT.NONE);
				if (i < threadFunctions.size()) {
					subitem.setText(3, threadActuators.get(i).getName());
					subitem.setText(4, threadFunctions.get(i).getName());
				} else {
					subitem.setText(3, threadActuators.get(i).getName());
				}
			}
		} else {
			for (int i = 0; i < threadFunctions.size(); i++) {
				subitem = new TreeItem(treeItem, SWT.NONE);
				if (i < threadActuators.size()) {
					subitem.setText(3, threadActuators.get(i).getName());
					subitem.setText(4, threadFunctions.get(i).getName());
				} else {
					subitem.setText(4, threadFunctions.get(i).getName());
				}
			}
		}
		threads.add(aux);
	}

	// Edit the properties of the specified sensor
	public void editPeriodicThread(Display display) {
		AADLThread thread = getPeriodicThread(periodicTable.getSelection()[0].getText(0));

		ActuationThreadShell edit = new ActuationThreadShell(display, actuatorsList, thread, "Edit Periodic Thread",
				true, actFunctionsList);

		if (edit.isConfirm()) {
			updateActuatorsList(edit.getActuators(), true);
			updateFunctionList(edit.getFunctions());

			editThread(edit.getName(), edit.geTemplate(), edit.getThreadActuators(), true, thread, edit.getPeriod(),
					edit.getPriority(), edit.getThreadFunctions());

			checkSpecified();
		}
	}

	protected void editSporadicThread(Display display) {
		AADLThread thread = getSporadicThread(sporadicTable.getSelection()[0].getText(0));

		ActuationThreadShell editSporadic = new ActuationThreadShell(display, actuatorsList, thread, "Edit Sporadic Thread",
				false, actFunctionsList);

		if (editSporadic.isConfirm()) {
			updateActuatorsList(editSporadic.getActuators(), false);
			updateFunctionList(editSporadic.getFunctions());
			
			editThread(editSporadic.getName(), editSporadic.geTemplate(), editSporadic.getThreadActuators(), false, thread, editSporadic.getPeriod(),
					editSporadic.getPriority(), editSporadic.getThreadFunctions());

			checkSpecified();
		}
	}

	private void editThread(String name, String geTemplate, ArrayList<Actuator> threadActuators, boolean bperiodic,
			AADLThread thread, int period, int priority, ArrayList<ActuationFunction> threadFunctions) {
		TreeItem[] treeItem;
		TreeItem newTreeItem;
		TreeItem newSubItem;

		/*
		 * Update Array
		 */
		thread.setName(name);
		thread.setPeriod(period);
		thread.setPeriodic(bperiodic);
		thread.setActuators(threadActuators);
		thread.setPriority(priority);
		thread.setTemplate(geTemplate);
		// aux.setFunctions(threadFunctions);

		if (bperiodic) {
			treeItem = periodicTable.getSelection();
			for (int i = 0; i < treeItem.length; i++) {
				treeItem[i].dispose();
			}

			newTreeItem = new TreeItem(periodicTable, SWT.NONE, thread.index);

		} else {
			treeItem = sporadicTable.getSelection();
			for (int i = 0; i < treeItem.length; i++) {
				treeItem[i].dispose();
			}

			newTreeItem = new TreeItem(sporadicTable, SWT.NONE, thread.index);
		}

		newTreeItem.setText(0, name);
		newTreeItem.setText(1, String.valueOf(period));
		newTreeItem.setText(2, String.valueOf(priority));
		
		if (threadActuators.size() >= threadFunctions.size()) {
			for (int i = 0; i < threadActuators.size(); i++) {
				newSubItem = new TreeItem(newTreeItem, SWT.NONE);
				if (i < threadFunctions.size()) {
					newSubItem.setText(3, threadActuators.get(i).getName());
					newSubItem.setText(4, threadFunctions.get(i).getName());
				} else {
					newSubItem.setText(3, threadActuators.get(i).getName());
				}
			}
		} else {
			for (int i = 0; i < threadFunctions.size(); i++) {
				newSubItem = new TreeItem(newTreeItem, SWT.NONE);
				if (i < threadActuators.size()) {
					newSubItem.setText(3, threadActuators.get(i).getName());
					newSubItem.setText(4, threadFunctions.get(i).getName());
				} else {
					newSubItem.setText(4, threadFunctions.get(i).getName());
				}
			}
		}
	}

	private AADLThread getSporadicThread(String tname) {
		if (!threads.isEmpty()) {
			for (int i = 0; i < threads.size(); i++) {
				if (threads.get(i).getName().equals(tname) && !threads.get(i).isPeriodic())
					return threads.get(i);
			}
		}
		return null;
	}

	private AADLThread getPeriodicThread(String tname) {
		if (!threads.isEmpty()) {
			for (int i = 0; i < threads.size(); i++) {
				if (threads.get(i).getName().equals(tname) && threads.get(i).isPeriodic())
					return threads.get(i);
			}
		}
		return null;
	}

	/*
	 * Verify the input table and according the vector amount of each input port
	 * the signals are inserted into the sensors list specification
	 */
	public void populateThreadsTable(ArrayList<Actuator> actuators, ArrayList<ActuationFunction> iActFuntion) {
		// If table have data clear it
		if (periodicTable.getItemCount() > 0 || sporadicTable.getItemCount() > 0)
			clearData();

		actuatorsList.clear();
		actFunctionsList.clear();

		for (int i = 0; i < actuators.size(); i++) {
			actuatorsList.add(actuators.get(i));
		}
		
		for (int i = 0; i < iActFuntion.size(); i++) {
			actFunctionsList.add(iActFuntion.get(i));
		}

		checkSpecified();
	}

	// clear all data of actuators
	private void clearData() {
		periodicTable.removeAll();
		sporadicTable.removeAll();
		threads.clear();
	}

	private void checkSpecified() {
		boolean checkPeriodic = false;
		boolean checkSporadic = false;
		boolean checkFunction = false;

		if (actuatorsList.size() > 0) {
			for (int i = 0; i < actuatorsList.size(); i++) {
				if (actuatorsList.get(i).isPeriodic())
					checkPeriodic = true;
				else
					checkSporadic = true;
			}

			if (checkPeriodic)
				btAddPeriodicThread.setEnabled(true);
			else
				btAddPeriodicThread.setEnabled(false);

			if (checkSporadic)
				btAddSporadicThread.setEnabled(true);
			else
				btAddSporadicThread.setEnabled(false);
		}
		
		if(actFunctionsList.size()> 0){
			checkFunction = true;
		}

		if (!checkPeriodic && !checkSporadic && !checkFunction) {
			setPageComplete(true);
			btAddSporadicThread.setEnabled(false);
			btAddPeriodicThread.setEnabled(false);
		} else
			setPageComplete(false);
	}

	protected ArrayList<AADLThread> getThreads() {
		return threads;
	}

	public void nextStep() {
		btAddPeriodicThread.setEnabled(false);
		btAddSporadicThread.setEnabled(false);
		btEditPeriodicThread.setEnabled(false);
		btEditSporadicThread.setEnabled(false);
		btRemovePeriodicThread.setEnabled(false);
		btRemoveSporadicThread.setEnabled(false);
		setPageComplete(true);
	}

}

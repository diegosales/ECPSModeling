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

import java.util.ArrayList;

import org.eclipse.swt.events.MouseListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.SWT;

public class PostReadingPage extends WizardPage {
	private Composite container;

	protected Tree table;

	protected ArrayList<Sensing> senSubsystems;
	protected ArrayList<String> outputs;

	protected Label information;

	protected Button btRemoveSubsystem;
	protected Button btEditSubsystem;
	protected Button btAddSubsystem;

	public PostReadingPage() {
		super("Post-reading Specification");
		setTitle("Post-reading Specification");
		setDescription("Detail the subsystems:");
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;

		senSubsystems = new ArrayList<>();
		outputs = new ArrayList<String>();

		container = new Composite(parent, SWT.NONE);
		container.setLayout(layout);
		container.setSize(300, 200);

		information = new Label(container, SWT.NONE);
		GridData ilayout = new GridData();
		ilayout.horizontalSpan = 3;
		information.setLayoutData(ilayout);
		information.setText("Analyze the input ports of the mathematical model:");

		// ---------------------- Table ---------------------------
		table = new Tree(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 3;

		table.setLayoutData(data);
		table.setSize(300, 100);

		final TreeColumn column = new TreeColumn(table, SWT.LEFT);
		column.setText("Subsystem");
		column.setWidth(150);

		final TreeColumn column2 = new TreeColumn(table, SWT.NONE);
		column2.setText("Sensors");
		column2.setWidth(150);

		final TreeColumn column3 = new TreeColumn(table, SWT.NONE);
		column3.setText("Outputs");
		column3.setWidth(150);

		btAddSubsystem = new Button(container, SWT.NONE);
		btEditSubsystem = new Button(container, SWT.NONE);
		btRemoveSubsystem = new Button(container, SWT.NONE);

		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					btEditSubsystem.setEnabled(true);
					btRemoveSubsystem.setEnabled(true);
				}
			}
		});

		table.addMouseListener(new MouseListener() {
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
				editSubsystemProperties(parent.getDisplay());
			}
		});

		btAddSubsystem.setText("Add Subsystem");
		btAddSubsystem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					addSubsystem(parent.getDisplay());
				}
			}
		});
		// btAddSubsystem.setEnabled(false);

		btEditSubsystem.setText("Edit Subsystem");
		btEditSubsystem.setEnabled(false);
		btEditSubsystem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					editSubsystemProperties(parent.getDisplay());
				}
			}
		});

		btRemoveSubsystem.setText("Remove Subsystem");
		btRemoveSubsystem.setEnabled(false);

		setControl(container);
		setPageComplete(false);
	}

	public void populateSignals(Table table) {
		if(table.getItemCount() > 0)
			clearData();
		// System.out.println("Begin");
		outputs.clear();
		for (int i = 0; i < table.getItemCount(); i++) {
			// System.out.println("Item "+i);
			Label port = (Label) table.getItem(i).getData("port");
			Text size = (Text) table.getItem(i).getData("size");
			Button postReading = (Button) table.getItem(i).getData("postReading");
			// System.out.println("Pre IF");
			if (postReading.getSelection()) {
				// System.out.println("IF");
				// System.out.println("Size "+size.getText());
				for (int z = 0; z < Integer.valueOf(size.getText()); z++) {
					// System.out.println(port.getText() + (z + 1));
					outputs.add(port.getText() + (z + 1));
				}
			}
		}
		checkConditions();
	}

	public void clearData(){
		table.removeAll();
		senSubsystems.clear();		
	}
	
	public void addSubsystem(Display display) {
		SenSubsystemShell add = new SenSubsystemShell(display, outputs);
		if (add.isConfirm()) {
			outputs = add.getOutputs();
			addSubsys(add.getName(), add.getInputs(), add.getSubsys());
		}
	}

	public void addSubsys(String name, ArrayList<String> inputs, ArrayList<String> outputs) {
		Sensing aux = new Sensing();
		TreeItem treeItem = new TreeItem(table, SWT.NONE);
		TreeItem subitem;
		aux.setName(name);
		aux.setInputs(inputs);
		aux.setOutputs(outputs);
		aux.setIndex(table.getItemCount() - 1);
		senSubsystems.add(aux);

		treeItem.setText(0, name);
		treeItem.setText(1, "sensors");
		treeItem.setText(2, "outputs");

		// System.out.println("IS: " + inputs.size() + " OS: " +
		// outputs.size());
		if (inputs.size() >= outputs.size()) {
			// System.out.println("IS >= OS");
			for (int i = 0; i < inputs.size(); i++) {
				subitem = new TreeItem(treeItem, SWT.NONE);
				if (i < outputs.size()) {
					// System.out.println("I: " + inputs.get(i));
					subitem.setText(1, inputs.get(i));
					// System.out.println("O: " + outputs.get(i));
					subitem.setText(2, outputs.get(i));
				} else {
					// System.out.println("Else");
					// System.out.println("I: " + inputs.get(i));
					subitem.setText(1, inputs.get(i));
				}
			}
		} else {
			// System.out.println("IS < OS");
			for (int i = 0; i < outputs.size(); i++) {
				subitem = new TreeItem(treeItem, SWT.NONE);
				if (i < inputs.size()) {
					// System.out.println("I: " + inputs.get(i));
					subitem.setText(1, inputs.get(i));
					// System.out.println("O: " + outputs.get(i));
					subitem.setText(2, outputs.get(i));
				} else {
					// System.out.println("Else");
					// System.out.println("O: " + outputs.get(i));
					subitem.setText(2, outputs.get(i));
				}
			}
		}
		checkConditions();
	}

	public void checkConditions() {
		if (outputs.isEmpty()) {
			setPageComplete(true);
			btAddSubsystem.setEnabled(false);
		} else {
			setPageComplete(false);
			btAddSubsystem.setEnabled(true);
		}
	}

	public Sensing getItemByName(String name) {
		if (!senSubsystems.isEmpty()) {
			for (int i = 0; i < senSubsystems.size(); i++) {
				if (senSubsystems.get(i).getName().equals(name))
					return senSubsystems.get(i);
			}
		}
		return null;
	}

	public void editSubsys(String name, ArrayList<String> inputs, ArrayList<String> outputs, Sensing item) {
		TreeItem[] treeItem = table.getSelection();
		for (int i = 0; i < treeItem.length; i++) {
			treeItem[i].dispose();
		}

		TreeItem newTreeItem = new TreeItem(table, SWT.NONE, item.index);
		TreeItem newSubItem;

		/*
		 * Update Array
		 */
		item.setName(name);
		item.setInputs(inputs);
		item.setOutputs(outputs);
		senSubsystems.set(item.index, item);

		newTreeItem.setText(0, name);
		newTreeItem.setText(1, "sensors");
		newTreeItem.setText(2, "outputs");

		// System.out.println("IS: " + inputs.size() + " OS: " +
		// outputs.size());
		if (inputs.size() >= outputs.size()) {
			// System.out.println("IS >= OS");
			for (int i = 0; i < inputs.size(); i++) {
				newSubItem = new TreeItem(newTreeItem, SWT.NONE);
				if (i < outputs.size()) {
					// System.out.println("I: " + inputs.get(i));
					newSubItem.setText(1, inputs.get(i));
					// System.out.println("O: " + outputs.get(i));
					newSubItem.setText(2, outputs.get(i));
				} else {
					// System.out.println("Else");
					// System.out.println("I: " + inputs.get(i));
					newSubItem.setText(1, inputs.get(i));
				}
			}
		} else {
			// System.out.println("IS < OS");
			for (int i = 0; i < outputs.size(); i++) {
				newSubItem = new TreeItem(newTreeItem, SWT.NONE);
				if (i < inputs.size()) {
					// System.out.println("I: " + inputs.get(i));
					newSubItem.setText(1, inputs.get(i));
					// System.out.println("O: " + outputs.get(i));
					newSubItem.setText(2, outputs.get(i));
				} else {
					// System.out.println("Else");
					// System.out.println("O: " + outputs.get(i));
					newSubItem.setText(2, outputs.get(i));
				}
			}
		}
		checkConditions();
	}

	public void editSubsystemProperties(Display display) {
		Sensing aux = getItemByName(table.getSelection()[0].getText(0));
		SenSubsystemShell edit = new SenSubsystemShell(display, outputs, aux);
		if (edit.isConfirm()) {
			outputs = edit.getOutputs();
			editSubsys(edit.getName(), edit.getInputs(), edit.getSubsys(), aux);
		}
	}

	public ArrayList<Sensing> getSenSubsystems() {
		return senSubsystems;
	}
}
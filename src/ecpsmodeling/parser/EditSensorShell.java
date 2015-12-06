package ecpsmodeling.parser;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class EditSensorShell {

	final Shell shell;
	
	protected boolean confirm = false;
	
	protected String txtSensor;
	protected String txtSampling;
	protected String txtProtocol;
	protected String txtPriority;
	
	protected Spinner priority;
	protected Spinner sampling;
	
	protected Text sensor;
		
	protected Combo protocol;
	
	protected Label lsensor;
	protected Label lsampling;
	protected Label lpriority;
	protected Label lprotocol;

	protected Button cancel;
	protected Button ok;
		
	public EditSensorShell(TableItem item, Display display) {
		shell = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.BORDER | SWT.CLOSE);

		GridLayout layout = new GridLayout();		
		layout.numColumns = 2;
		layout.marginLeft = 10;
		layout.marginTop = 8;
		
		shell.setLayout(layout);
		shell.setSize(295, 210);
		shell.setText("Sensor Specification");
		
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		
		createControl();
		init(item);
		
		shell.open();

		// Set up the event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in event queue
				display.sleep();
			}
		}
	}

	private void createControl(){
		GridData ilayout = new GridData();
		ilayout.widthHint = 150;
		
		lsensor = new Label(shell, SWT.NONE);
		lsensor.setText("Sensor:");
		
		sensor = new Text(shell, SWT.SINGLE | SWT.BORDER);
		sensor.setLayoutData(ilayout);		
		
		lsampling = new Label(shell, SWT.NONE);
		lsampling.setText("Sampling (ms):");
				
		sampling = new Spinner(shell, SWT.BORDER);
		sampling.setLayoutData(ilayout);
		
		lprotocol = new Label(shell, SWT.NONE);
		lprotocol.setText("Protocol:");
		
		protocol = new Combo(shell, SWT.NONE);
		String[] items = {"I2C", "SPI", "Serial", "PWM"};
		protocol.setItems(items);
		protocol.setLayoutData(ilayout);
		
		lpriority = new Label(shell, SWT.NONE);
		lpriority.setText("Priority:");
		
		priority = new Spinner(shell, SWT.BORDER);
		priority.setLayoutData(ilayout);
		
		GridData blayout = new GridData();
		blayout.widthHint = 80;
		
		ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(blayout);
		ok.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				confirm = true;
				
				txtSensor = sensor.getText();
				txtSampling = sampling.getText();
				txtProtocol = protocol.getText();
				txtPriority = priority.getText();
				
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(blayout);
		cancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void init(TableItem item) {
		sensor.setText(item.getText(0));
		if(!item.getText(1).isEmpty())
			sampling.setSelection(Integer.valueOf(item.getText(1)));
		if(!item.getText(2).isEmpty())
			priority.setSelection(Integer.valueOf(item.getText(2)));			
		if(!item.getText(3).isEmpty()){
			for(int i=0; i< protocol.getItemCount(); i++){
				if(protocol.getItem(i).equals(item.getText(3)))
					protocol.select(i);
			}
		}
	}

	public String getPriority() {
		return txtPriority;
	}

	public String getSampling() {
		return txtSampling;
	}

	public String getSensor() {
		return txtSensor;
	}

	public String getProtocol() {
		return txtProtocol;
	}

	public boolean isConfirm() {
		return confirm;
	}	
}

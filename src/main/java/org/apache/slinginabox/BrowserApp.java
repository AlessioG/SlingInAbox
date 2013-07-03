package org.apache.slinginabox;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;

public class BrowserApp {
	public static void main (String args[]) {
		Display display = new Display();
		final Shell shell = new Shell (display);
		final Browser browser;

		try {
			browser = new Browser (shell, SWT.NONE);
		}
		catch (SWTError e) {
			System.out.println ("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}

		shell.setLayout (new FillLayout());

		browser.addProgressListener (new ProgressListener() {
			public void changed (ProgressEvent event) {
				if (event.total == 0) return;
				int ratio = event.current * 100 / event.total;
			}
			public void completed (ProgressEvent event) {
				System.out.println ("done");
			}
		});
		browser.addStatusTextListener (new StatusTextListener() {
			public void changed (StatusTextEvent event) {
				System.out.println ("status:" + event);
			}
		});
		browser.addLocationListener (new LocationListener() {
			public void changed (LocationEvent event) {
				System.out.println ("loc:" + event);
			}
			public void changing (LocationEvent event) {
			}
		});

		shell.open();
		browser.setUrl (args[0]);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
